package dk.gruppea3moro.moroa3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dk.gruppea3moro.moroa3.data.DataController;
import dk.gruppea3moro.moroa3.model.AppState;
import dk.gruppea3moro.moroa3.model.EventDTO;
import dk.gruppea3moro.moroa3.model.SearchCriteria;


public class MyProfileListFragment extends Fragment {

    private final View.OnClickListener mOnClickListener = new RVOnClickListener();

    RecyclerView recyclerView;
    ArrayList<EventDTO> eventDTOs;

    ImageView removeSaved_imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_profile_list, container, false);
        removeSaved_imageView = root.findViewById(R.id.removeSaved_imageView);


        recyclerView = new RecyclerView(getContext());


        //TODO nedenstående slettes og erstattes med gemte i stedet for at hente alle.
        // Er lavet så man kan se det virker

        SearchCriteria searchCriteria = AppState.get().getSearchCriteria();
        //Get events with DataController from BackgroundThread
        Executor bgThread = Executors.newSingleThreadExecutor();
        Handler uiThread = new Handler();
        bgThread.execute(() -> {
            //Gets event from searchCriteria via. DataController
            eventDTOs = DataController.get().searchEvents(searchCriteria);

            uiThread.post(() -> {
                // Inflate the layout (recyclerview) for this fragment
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(adapter);
            });
        });

        //return recyclerview
        return recyclerView;

       // return inflater.inflate(R.layout.fragment_my_profile_list, container, false);
    }


    RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
        @Override
        public int getItemCount() {
            return eventDTOs.size();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            System.out.println("onCreateViewHolder ");
            View itemView = getLayoutInflater().inflate(R.layout.fragment_my_profile_list, parent, false);

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
            EventDTO currentEvent = eventDTOs.get(position);
            System.out.println(currentEvent);

            //Set views from current event data
            titleTV.setText(currentEvent.getTitle());
            areaTV.setText(currentEvent.getAddressDTO().getArea()); //TODO fix evt. indfør koordinater
            dateTV.setText(currentEvent.getStartDate());
            timeTV.setText(currentEvent.getStartTime());

            //Let Picasso handle the image
            Picasso.get().load(currentEvent.getImageLink())
                    .placeholder(R.drawable.default_event)
                    .into(imageView);
        }
    };

    class RVOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //Get posistion of clicked event
            int position = recyclerView.getChildLayoutPosition(view);

            //Get event at that position
            EventDTO event = eventDTOs.get(position);

            //Fragment transaction with event as argument
            Fragment f = AppState.getFragmentFromLayoutId(R.id.fragment_show_event);
            AppState.get().setLastViewedEvent(event);
            Bundle b = new Bundle();
            b.putSerializable("event", event);
            f.setArguments(b);
            AppState.get().pushToBackstackDequeTop(R.id.fragment_show_event);
            ((MainActivity) getActivity()).loadFragment(f);

        }
    }


}