package dk.gruppea3moro.moroa3.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.model.AppState;
import dk.gruppea3moro.moroa3.model.EventDTO;

public class ShowEventFragment extends Fragment {
    TextView title, subtext, price, startDay, startTime, address, eventLink;
    ImageView image;
    ShowEventViewModel showEventViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_show_event, container, false);
        title = root.findViewById(R.id.titleTVShowEvent);
        subtext = root.findViewById(R.id.descriptionTVShowEvent);
        price = root.findViewById(R.id.priceTVShowEvent);
        startDay = root.findViewById(R.id.dateTVShowEvent);
        startTime = root.findViewById(R.id.timeTVShowEvent);
        address = root.findViewById(R.id.addressTVShowEvent);
        image = root.findViewById(R.id.evnentImageShowEvent);
        eventLink = root.findViewById(R.id.eventLinkShowEvent);

        //Setup ViewModel
        showEventViewModel = ViewModelProviders.of(this).get(ShowEventViewModel.class);
        showEventViewModel.init();
        showEventViewModel.getShownEvent();

        setupEventView();
        return root;
    }

    public void setupEventView() {
        //Get last viewed event from ViewModel
        System.out.println(showEventViewModel.getShownEvent().getValue().toString());
        EventDTO eventDTO = showEventViewModel.getShownEvent().getValue();

        //Set text views
        title.setText(eventDTO.getTitle());
        subtext.setText(eventDTO.getSubtext());

        if (eventDTO.getPrice() < 0.1) {
            price.setText("Pris: Gratis");
        } else {
            price.setText(String.format("Pris: %.0f", eventDTO.getPrice()) + " kr.");
        }

        eventLink.setText("Læs mere: " + eventDTO.getEventLink());
        startDay.setText("Dato: " + eventDTO.getStart().getDanishDayFormat());
        startTime.setText("Start: " + eventDTO.getStart().getTimeFormat());
        address.setText(eventDTO.getAddressDTO().toString());

        //Let Picasso handle the image
        Picasso.get().load(eventDTO.getImageLink()).into(image);
    }
}