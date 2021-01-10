package dk.gruppea3moro.moroa3.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dk.gruppea3moro.moroa3.MainActivity;
import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.findevent.ShowEventFragment;
import dk.gruppea3moro.moroa3.data.EventRepository;
import dk.gruppea3moro.moroa3.model.AppState;
import dk.gruppea3moro.moroa3.model.EventDTO;

public class FrontpageFragment extends Fragment implements View.OnClickListener {

    Button rightNowButton, findEventButton;
    FrameLayout featuredEventFL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_frontpage, container, false);

        //Show featured event fragment
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.featuredEventFrontpageFL, new FeaturedEventFragment())
                .commit();

        //Buttons
        rightNowButton = root.findViewById(R.id.rightNowButtonFP);
        findEventButton = root.findViewById(R.id.findEventButtonFP);
        findEventButton.setOnClickListener(this);
        rightNowButton.setOnClickListener(this);

        //FeaturedEventFrameLayout
        featuredEventFL = root.findViewById(R.id.featuredEventFrontpageFL);
        featuredEventFL.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        MainActivity ma = ((MainActivity) getActivity());
        if (v == findEventButton) {
            //Put the selected fragment to top of backstack-deque.
            AppState.get().pushToBackstackDequeTop(R.id.fragment_find_event);
            //Set selection on bottom navigation bar
            ma.setBottonNavSelection(R.id.fragment_find_event);
            //Get fragment object and load
            Fragment f = AppState.getFragmentFromLayoutId(R.id.fragment_find_event);
            ((MainActivity) getActivity()).loadFragment(f);
        } else if (v == rightNowButton) {
            //Put the selected fragment to top of backstack-deque.
            AppState.get().pushToBackstackDequeTop(R.id.fragment_right_now);
            //Set selection on bottom navigation bar
            ma.setBottonNavSelection(R.id.fragment_right_now);
            //Set search criteria to "right now"
            AppState.get().setSearchCriteriaRightNow();
            //Get fragment object and load
            Fragment f = AppState.get().getFragmentFromLayoutId(R.id.fragment_right_now);
            ((MainActivity) getActivity()).loadFragment(f);
        } else if (v == featuredEventFL) {
            openFeaturedEventFragment();
        }
    }



    public void replaceFeaturedEvent(EventDTO featuredEvent) {
        //Get ready for fragment transaction for featured event
        Bundle b = new Bundle();
        b.putSerializable("event", featuredEvent);
        Fragment featuredEventFragment = new FeaturedEventFragment();
        featuredEventFragment.setArguments(b);

        //If Activity is ready for it
        if (getActivity() != null) {
            //Make fragment transaction
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.featuredEventFrontpageFL, featuredEventFragment)
                    .commit();
        }
    }

    public void openFeaturedEventFragment() {
        //Get featured event from AppState - should have been initialized by now
        EventDTO featuredEvent = AppState.get().getFeaturedEvent();

        //Get ready for fragment transaction for featured event
        Bundle b = new Bundle();
        b.putSerializable("event", featuredEvent);
        AppState.get().setLastViewedEvent(featuredEvent);
        Fragment featuredEventFragment = new ShowEventFragment();
        featuredEventFragment.setArguments(b);

        //Push to backstack
        AppState.get().pushToBackstackDequeTop(R.id.fragment_show_event);

        //Make fragment transaction
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFL, featuredEventFragment)
                .commit();
    }
}