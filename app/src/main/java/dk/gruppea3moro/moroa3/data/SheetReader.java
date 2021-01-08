package dk.gruppea3moro.moroa3.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import dk.gruppea3moro.moroa3.model.AddressDTO;
import dk.gruppea3moro.moroa3.model.DateTime;
import dk.gruppea3moro.moroa3.model.EventDTO;
import dk.gruppea3moro.moroa3.model.SearchCriteria;

public class SheetReader implements EventLoader {

    final String SHEET_ID = "1mZFpWlSVm7v-_lLbbCaWo_OgdN-lP3XmvMTTu1wbqFY";

    @Override
    public ArrayList<EventDTO> getAllEvents() throws IOException {
        //Result arraylist
        ArrayList<EventDTO> events = new ArrayList<>();

        //URL
        String url = "https://docs.google.com/spreadsheets/d/" + SHEET_ID + "/export?format=tsv&id=" + SHEET_ID;
        System.out.println(url);

        //Reader
        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));

        //Skip first line
        String line = br.readLine();
        line = br.readLine();
        while (line != null) {
            events.add(createEventDTO(line));
            line = br.readLine();
        }
        br.close();
        return events;
    }

    public EventDTO createEventDTO(String line) {
        EventDTO event = new EventDTO();
        String[] fields = line.split("\t");
        event.setTitle(fields[0]);
        event.setSubtext(fields[1]);
        event.setPrice(Integer.parseInt(fields[2]));
        event.setEventLink(fields[3]);
        event.setImageLink(fields[4]);
        event.setStart(new DateTime(fields[6],fields[5]));
        event.setEnd(new DateTime(fields[8],fields[7]));
        event.setAddressDTO(new AddressDTO(fields[9], fields[10], fields[11], fields[12], fields[13], fields[14]));
        event.setMoods(parseTags(fields[15]));
        event.setTypes(parseTags(fields[16]));
        event.setZone(fields[17]);
        return event;
    }

    public ArrayList<String> parseTags(String textTags) {
        ArrayList<String> tags = new ArrayList<>();
        String[] tagArray = textTags.split(";");
        Collections.addAll(tags, tagArray);
        return tags;
    }


    @Override
    public EventDTO getFeaturedEvent() throws IOException {
        //TODO fix this
        return getAllEvents().get(0);
    }
}
