package dk.gruppea3moro.moroa3.findevent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dk.gruppea3moro.moroa3.MainActivity;
import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.data.EventRepository;
import dk.gruppea3moro.moroa3.model.AppState;
import dk.gruppea3moro.moroa3.model.EventDTO;
import dk.gruppea3moro.moroa3.model.SearchCriteria;


public class ShowResultFragment extends Fragment {

    private final View.OnClickListener mOnClickListener = new RVOnClickListener();

    RecyclerView recyclerView;
    ShowResultViewModel showResultViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Create RecyclerView -  empty at first
        recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        //Get search criteria - either from "Right Now" or form parent fragment "Find Event"
        SearchCriteria sc = getSearchCriteria();

        //Set ViewModel
        showResultViewModel = ViewModelProviders.of(this).get(ShowResultViewModel.class);
        showResultViewModel.init(sc);
        showResultViewModel.getResultEventsLD().observe(this, new Observer<List<EventDTO>>() {
            @Override
            public void onChanged(List<EventDTO> eventDTOS) {
                adapter.notifyDataSetChanged();
            }
        });

        //TODO lav LOADING-animation med MaterialIO eller lign.
        Toast.makeText(getContext(), getString(R.string.loading), Toast.LENGTH_SHORT).show();

        //return recyclerview
        return recyclerView;
    }
/*
    public void refreshSearch() {
        //Gets SearchCriteria from appstate
        SearchCriteria searchCriteria = AppState.get().getSearchCriteria();

        //Get events with EventRepository from BackgroundThread
        Executor bgThread = Executors.newSingleThreadExecutor();
        Handler uiThread = new Handler();
        bgThread.execute(() -> {
            //Gets event from searchCriteria via. EventRepository
            eventDTOs = EventRepository.get().searchEvents(getContext(), searchCriteria,null);

            uiThread.post(() -> {
                // Inflate the layout (recyclerview) for this fragment
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(adapter);
            });
        });
    }

 */

    RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
        @Override
        public int getItemCount() {
            if (showResultViewModel.getResultEventsLD().getValue() ==null){
                return 0;
            } else{
                return showResultViewModel.getResultEventsLD().getValue().size();
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            System.out.println("onCreateViewHolder ");
            View itemView = getLayoutInflater().inflate(R.layout.showevent_recyclerview, parent, false);

            //Set OnClickListener to inner class RVOnClickListener
            itemView.setOnClickListener(mOnClickListener);
            return new RecyclerView.ViewHolder(itemView) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {
            //Get views
            System.out.println("onBindViewHolder " + position);
            TextView titleTV = vh.itemView.findViewById(R.id.title_textView_RV);
            TextView areaTV = vh.itemView.findViewById(R.id.area_textView_RV);
            TextView dateTV = vh.itemView.findViewById(R.id.date_textView_RV);
            TextView timeTV = vh.itemView.findViewById(R.id.time_textView_RV);
            ImageView imageView = vh.itemView.findViewById(R.id.showevent_imageView_RV);

            //Get current event
            EventDTO currentEvent = showResultViewModel.getResultEventsLD().getValue().get(position);
            System.out.println(currentEvent);

            //Set views from current event data
            titleTV.setText(currentEvent.getTitle());
            areaTV.setText(currentEvent.getAddressDTO().getArea()); //TODO fix evt. indfør koordinater
            dateTV.setText(currentEvent.getStart().getDanishDayFormat());
            timeTV.setText(currentEvent.getStart().getTimeFormat());

            //Let Picasso handle the image
            Picasso.get().load(currentEvent.getImageLink())
                    .placeholder(R.drawable.moro_logo)
                    .into(imageView);


        }
    };

    class RVOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //Get posistion of clicked event
            int position = recyclerView.getChildLayoutPosition(view);

            //Get event at that position
            EventDTO event = showResultViewModel.getResultEventsLD().getValue().get(position);

            //Fragment transaction with event as argument
            Fragment f = AppState.getFragmentFromLayoutId(R.id.fragment_show_event);
            EventRepository.get().setLastViewedEventMLD(event);
            Bundle b = new Bundle();
            b.putSerializable("event", event);
            f.setArguments(b);
            AppState.get().pushToBackstackDequeTop(R.id.fragment_show_event);
            ((MainActivity) getActivity()).loadFragment(f);

        }
    }
/*
    @Override
    public void onResume() {
        super.onResume();
        if (AppState.get().isRefreshSearch()) {
            refreshSearch();
        }
    }

 */

    public SearchCriteria getSearchCriteria(){
        SearchCriteria sc;
        //Check if in FindEventFragment or from "Right Now"
        if (getParentFragment() instanceof FindEventFragment){
            sc = ((FindEventFragment)(getParentFragment())).getSearchCriteria();
        } else {
            sc = AppState.getRightNowSearchCriteria();
        }
        return sc;
    }
}