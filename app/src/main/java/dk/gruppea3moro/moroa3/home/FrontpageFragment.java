package dk.gruppea3moro.moroa3.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import dk.gruppea3moro.moroa3.MainActivity;
import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.model.AppState;

public class FrontpageFragment extends Fragment implements View.OnClickListener {

    TextView rightNowButton, findEventButton;
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
        AppState.get().setBotNavSelectGreater(true);
        if (v == findEventButton) {
            //Put the selected fragment to top of backstack-deque.
            AppState.get().pushToBackstackDequeTop(R.id.fragment_find_event);
            //Set selection on bottom navigation bar
            ma.setBottonNavSelection(R.id.fragment_find_event);
            //Get fragment object and load
            Fragment f = AppState.getFragmentFromLayoutId(R.id.fragment_find_event);
            //((MainActivity) getActivity()).loadFragment(f);
            ((MainActivity) getActivity()).loadFragmentRightEntering(f);
        } else if (v == rightNowButton) {
            //Put the selected fragment to top of backstack-deque.
            AppState.get().pushToBackstackDequeTop(R.id.fragment_right_now);
            //Set selection on bottom navigation bar
            ma.setBottonNavSelection(R.id.fragment_right_now);
            //Get fragment object and load
            Fragment f = AppState.get().getFragmentFromLayoutId(R.id.fragment_right_now);
            //((MainActivity) getActivity()).loadFragment(f);
            ((MainActivity) getActivity()).loadFragmentRightEntering(f);
        } else if (v == featuredEventFL) {
            openFeaturedEventFragment();
        }
    }

    public void openFeaturedEventFragment() {
        //Push to backstack
        AppState.get().pushToBackstackDequeTop(R.id.fragment_show_event);

        //Make fragment transaction
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFL, new ShowEventFragment())
                .commit();
    }
}