package dk.gruppea3moro.moroa3.home;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.squareup.picasso.Picasso;

import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.model.EventDTO;

public class ShowEventFragment extends Fragment implements View.OnClickListener {
    TextView title, subtext, price, startDay, startTime, address, eventLink;
    ImageView image;
    ShowEventViewModel showEventViewModel;
    AppCompatImageView saved_imageView;
    SharedPreferences sharedPreferences;
    private int count = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        View root = inflater.inflate(R.layout.fragment_show_event, container, false);
        title = root.findViewById(R.id.titleTVShowEvent);
        subtext = root.findViewById(R.id.descriptionTVShowEvent);
        price = root.findViewById(R.id.priceTVShowEvent);
        startDay = root.findViewById(R.id.dateTVShowEvent);
        startTime = root.findViewById(R.id.timeTVShowEvent);
        address = root.findViewById(R.id.addressTVShowEvent);
        image = root.findViewById(R.id.eventImageShowEvent);
        eventLink = root.findViewById(R.id.eventLinkShowEvent);
        saved_imageView = root.findViewById(R.id.saved_imageView);

        saved_imageView.setOnClickListener(this);

        if (sharedPreferences.getString("checked", "").equals("unchecked")) {
            System.out.println("UNCHECKED!!!!");
            System.out.println(sharedPreferences.getString("checked", ""));
            saved_imageView.setBackgroundResource(R.drawable.emptyheart);
        } else if (sharedPreferences.getString("checked", "").equals("checked")) {
            System.out.println("CHECKED!!!!");
            System.out.println(sharedPreferences.getString("checked", ""));
            saved_imageView.setBackgroundResource(R.drawable.filledheart);
        } else {
            System.out.println("Checked not set");
            System.out.println(sharedPreferences.getString("checked", ""));
            sharedPreferences.edit().putString("checked", "unchecked").apply();
        }

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
            price.setText(R.string.price_free);
        } else {
            price.setText(String.format("Pris: %.0f", eventDTO.getPrice()) + " kr.");
        }

        eventLink.setText("Læs mere: " + eventDTO.getEventLink());
        startDay.setText(eventDTO.getStart().getDanishDayFormat());
        startTime.setText(eventDTO.getStart().getTimeFormat() + " - " + eventDTO.getEnd().getTimeFormat());
        address.setText(eventDTO.getAddressDTO().toString());

        //Let Picasso handle the image
        Picasso.get().load(eventDTO.getImageLink()).placeholder(R.drawable.moro_logo).into(image);
    }

    @Override
    public void onClick(View v) {
        EventDTO eventDTO = showEventViewModel.getShownEvent().getValue();
        String eventTitle = eventDTO.getTitle();
        String eventDate = eventDTO.getStart().getSqlDateFormat();
        String eventTime = eventDTO.getStart().getSqlTimeFormat();

        if (v == saved_imageView) {
            if (sharedPreferences.getString("checked", "").equals("unchecked")) {
                //TODO Tilføj til gemte events
                sharedPreferences.edit().putString("title" + count, eventTitle).apply();
                sharedPreferences.edit().putString("startDato" + count, eventDate).apply();
                sharedPreferences.edit().putString("startTidspunkt" + count, eventTime).apply();
                count += 1;
                saved_imageView.setBackgroundResource(R.drawable.filledheart);
                sharedPreferences.edit().putString("checked", "checked").apply();
                System.out.println("NOW CHECKED!!!!!!");
                System.out.println(sharedPreferences.getString("checked", ""));
                saved_imageView.setTag("Filled");
            } else {
                //TODO fjern fra gemte events
                saved_imageView.setBackgroundResource(R.drawable.emptyheart);
                sharedPreferences.edit().putString("checked", "unchecked").apply();
                System.out.println("NOW UNCHECKED!!!!!");
                System.out.println(sharedPreferences.getString("checked", ""));
                saved_imageView.setTag("Unfilled");
            }
        }
    }
}