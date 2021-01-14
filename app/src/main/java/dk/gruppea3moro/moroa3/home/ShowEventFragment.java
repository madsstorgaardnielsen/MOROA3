package dk.gruppea3moro.moroa3.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.model.EventDTO;
import dk.gruppea3moro.moroa3.profile.EventIdList;

//TODO hele klassen er ret rodet og trænger til en kærlig hånd
public class ShowEventFragment extends Fragment implements View.OnClickListener {
    TextView title, subtext, price, startDay, startTime, address, eventLink;
    ImageView image;
    ShowEventViewModel showEventViewModel;
    AppCompatImageView saved_imageView;
    SharedPreferences sharedPreferences;
    private boolean eventSaved;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sharedPreferences = getContext().getSharedPreferences("saveEvent", Context.MODE_PRIVATE);
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


        //Setup ViewModel
        showEventViewModel = ViewModelProviders.of(this).get(ShowEventViewModel.class);
        showEventViewModel.init();
        showEventViewModel.getShownEvent();

        checkIfEventIsSaved();

        if (!eventSaved) {
            System.out.println("UNCHECKED!!!!");
            saved_imageView.setBackgroundResource(R.drawable.emptyheart);
        } else if (eventSaved) {
            System.out.println("CHECKED!!!!");
            saved_imageView.setBackgroundResource(R.drawable.filledheart);
        } else {
            System.out.println("Checked not set");
            sharedPreferences.edit().putString("checked", "unchecked").apply();
        }
        setupEventView();
        return root;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
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
        startDay.setText("Dato: " + eventDTO.getStart().getDanishDayFormat());
        startTime.setText("Start: " + eventDTO.getStart().getTimeFormat());
        address.setText(eventDTO.getAddressDTO().toString());

        //Let Picasso handle the image
        Picasso.get().load(eventDTO.getImageLink()).into(image);
    }

    @Override
    public void onClick(View v) {

        if (v == saved_imageView) {
            if (!eventSaved) {
                //TODO Tilføj til gemte events
                saveEvent();
                saved_imageView.setBackgroundResource(R.drawable.filledheart);
                System.out.println("NOW CHECKED!!!!!!");
                saved_imageView.setTag("Filled");
                System.out.println("Eventsaved: "+eventSaved);
            } else if (eventSaved){
                //TODO fjern fra gemte events
                saved_imageView.setBackgroundResource(R.drawable.emptyheart);
                try {
                    removeEvent();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("NOW UNCHECKED!!!!!");
                saved_imageView.setTag("Unfilled");
            } else{
                System.out.println("NOT CHECKED OR UNCHECKED");
            }
        }
    }

    public void saveEvent() {
        ArrayList<String> eventIds = new ArrayList<>();
        EventDTO eventDTO = showEventViewModel.getShownEvent().getValue();

        Gson load = new Gson();
        String jsonLoad = sharedPreferences.getString(EventIdList.SAVEDLIST, null);

        if (jsonLoad != null) {
            eventIds = load.fromJson(jsonLoad, EventIdList.class).eventIds;
        }
        eventIds.add(eventDTO.getId());
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gsonInput = new Gson();
        String json = gsonInput.toJson(new EventIdList(eventIds));
        prefsEditor.putString(EventIdList.SAVEDLIST, json);
        prefsEditor.apply();
        eventSaved = true;

    }

    public void removeEvent() throws Exception {
        ArrayList<String> events;
        EventDTO eventDTO = showEventViewModel.getShownEvent().getValue();

        Gson load = new Gson();
        String jsonLoad = sharedPreferences.getString(EventIdList.SAVEDLIST, null);

        if (jsonLoad != null) {
            events = load.fromJson(jsonLoad,EventIdList.class).eventIds;
            events.remove(eventDTO.getId());
            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
            String json = load.toJson(new EventIdList(events));
            prefsEditor.putString(EventIdList.SAVEDLIST, json);
            prefsEditor.apply();
            eventSaved = false;
        } else if (jsonLoad == null) {
            eventSaved = false;
            throw new Exception("No events saved, in preference manager. Preference manager is empty");
        }
    }

    public boolean checkIfEventIsSaved() {
        ArrayList<String> events;
        EventDTO eventDTO = showEventViewModel.getShownEvent().getValue();

        Gson load = new Gson();
        String jsonLoad = sharedPreferences.getString(EventIdList.SAVEDLIST, null);

        if (jsonLoad == null) {
            eventSaved = false;
            return false;
        }

        //If it wasn't null - continue
        events = load.fromJson(jsonLoad, EventIdList.class).eventIds;

        if (events.contains(eventDTO.getId())) {
            eventSaved = true;
            return true;
        } else {
            eventSaved = false;
            return false;
        }

    }

}