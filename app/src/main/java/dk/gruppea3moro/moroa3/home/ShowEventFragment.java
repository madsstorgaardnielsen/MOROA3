package dk.gruppea3moro.moroa3.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import dk.gruppea3moro.moroa3.R;
import dk.gruppea3moro.moroa3.model.EventDTO;
import dk.gruppea3moro.moroa3.profile.EventIdList;
import io.sentry.Sentry;

//TODO hele klassen er ret rodet og trænger til en kærlig hånd
public class ShowEventFragment extends Fragment implements View.OnClickListener {
    TextView title, subtext, price, date, address, eventLink;
    ImageView image;
    ShowEventViewModel showEventViewModel;
    AppCompatImageView saved_imageView;
    SharedPreferences sharedPreferences;
    private boolean eventSaved;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sharedPreferences = getContext().getSharedPreferences("saveEvent", Context.MODE_PRIVATE);
        View root = inflater.inflate(R.layout.fragment_show_event, container, false);
        title = root.findViewById(R.id.titleTVShowEvent);
        subtext = root.findViewById(R.id.descriptionTVShowEvent);
        price = root.findViewById(R.id.priceTVShowEvent);
        date = root.findViewById(R.id.dateTVShowEvent);
        address = root.findViewById(R.id.addressTVShowEvent);
        image = root.findViewById(R.id.eventImageShowEvent);
        eventLink = root.findViewById(R.id.eventLinkShowEvent);
        saved_imageView = root.findViewById(R.id.saved_imageView);

        saved_imageView.setOnClickListener(this);
        address.setOnClickListener(this);

        //Setup ViewModel
        showEventViewModel = ViewModelProviders.of(this).get(ShowEventViewModel.class);
        showEventViewModel.init();
        showEventViewModel.getShownEvent().observe(this, new Observer<EventDTO>() {
            @Override
            public void onChanged(EventDTO eventDTO) {
                if (eventDTO != null){
                    checkIfEventIsSaved();

                    if (!eventSaved) {
                        saved_imageView.setBackgroundResource(R.drawable.emptyheart);
                    } else if (eventSaved) {
                        saved_imageView.setBackgroundResource(R.drawable.filledheart);
                    } else {
                        sharedPreferences.edit().putString("checked", "unchecked").apply();
                    }
                    setupEventView();
                }

            }
        });;

        return root;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    public void setupEventView() {
        //Get last viewed event from ViewModel
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
        String dateString;
        if (eventDTO.getEnd().getDanishDayFormat().equals(eventDTO.getStart().getDanishDayFormat())){ //If start and end date is the same
            dateString = eventDTO.getStart().getDanishDayFormat() +" " + eventDTO.getStart().getTimeFormat() + " - " + eventDTO.getEnd().getTimeFormat();
        } else { //If start and end date are not the same
             dateString = eventDTO.getStart().getDanishDateTimeFormat() + " - " +eventDTO.getEnd().getDanishDateTimeFormat();
        }



        date.setText(dateString);
        address.setText(eventDTO.getAddressDTO().toString());
        address.setPaintFlags(address.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        //Let Picasso handle the image
        Picasso.get().load(eventDTO.getImageLink()).into(image);
    }

    @Override
    public void onClick(View v) {
        //When the address is pressed by the user, google maps will be opened with the correct address and the correct event location will be shown
        if (v == address) {
            EventDTO eventDTO = showEventViewModel.getShownEvent().getValue();
            String mapsAddress = eventDTO.getAddressDTO().getAddress();

            String formattedMapsStr = mapsAddress.replaceAll(" ", "+").replaceAll("\n", "+");
            String maps = "https://www.google.com/maps/place/" + formattedMapsStr;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            browserIntent.setData(Uri.parse(maps));
            getActivity().startActivity(browserIntent);
        }

        //If the user clicks the heart to save the event
        if (v == saved_imageView) {
            if (!eventSaved) {
                saveEvent();
                saved_imageView.setBackgroundResource(R.drawable.filledheart);
                saved_imageView.setTag("Filled");

                //if the user clicks the heart to unsave the event
            } else if (eventSaved) {
                saved_imageView.setBackgroundResource(R.drawable.emptyheart);
                try {
                    removeEvent();
                } catch (Exception e) {
                    e.printStackTrace();
                    Sentry.captureException(e);
                }
                saved_imageView.setTag("Unfilled");
            } else {
                System.out.println("NOT CHECKED OR UNCHECKED");
            }
        }
    }

    //Saves the event via SharedPreferences
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

    //Removes the event from shared preferences
    public void removeEvent() throws Exception {
        ArrayList<String> events;
        EventDTO eventDTO = showEventViewModel.getShownEvent().getValue();

        Gson load = new Gson();
        String jsonLoad = sharedPreferences.getString(EventIdList.SAVEDLIST, null);

        if (jsonLoad != null) {
            events = load.fromJson(jsonLoad, EventIdList.class).eventIds;
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
        if (eventDTO == null){
            return false;
        }

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