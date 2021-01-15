package dk.gruppea3moro.moroa3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import dk.gruppea3moro.moroa3.model.AppState;
import dk.gruppea3moro.moroa3.model.EventDTO;

public class MyProfileListFragment extends Fragment {

    private final View.OnClickListener mOnClickListener = new RVOnClickListener();
    private final View.OnLongClickListener mOnLongClickListener = new RVOnClickListener();
    SharedPreferences sharedPreferences;

    RecyclerView recyclerView;
    ArrayList<EventDTO> eventDTOs;

    ImageView removeSaved_imageView, showevent_imageView_RV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_profile_list, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        removeSaved_imageView = root.findViewById(R.id.removeSaved_imageView);
        showevent_imageView_RV = root.findViewById(R.id.showevent_imageView_RV);

        recyclerView = new RecyclerView(getContext());

        //TODO nedenstående slettes og erstattes med gemte i stedet for at hente alle.
        // Er lavet så man kan se det virker







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
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            System.out.println("onCreateViewHolder ");
            View itemView = getLayoutInflater().inflate(R.layout.fragment_my_profile_list, parent, false);
            showevent_imageView_RV = itemView.findViewById(R.id.showevent_imageView_RV);


            //Set OnClickListener to inner class RVOnClickListener
            itemView.setOnLongClickListener(mOnLongClickListener);
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
            dateTV.setText(currentEvent.getStart().getDanishDayFormat());
            timeTV.setText(currentEvent.getStart().getTimeFormat());

            //Let Picasso handle the image
            Picasso.get().load(currentEvent.getImageLink())
                    .placeholder(R.drawable.default_event)
                    .into(imageView);
        }
    };

    class RVOnClickListener implements View.OnClickListener, View.OnLongClickListener {
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

        @Override
        public boolean onLongClick(View v) {
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Gør intet
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //TODO Fjern eventet
                        break;
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Vil du fjerne eventet fra dine gemte?").setNegativeButton("Ja", dialogClickListener)
                    .setPositiveButton("Nej", dialogClickListener).show();
            return false;
        }
    }
}