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
        event.setStartTime(fields[5]);
        event.setStartDate(fields[6]);
        event.setEndTime(fields[7]);
        event.setEndDate(fields[8]);
        event.setAddressDTO(new AddressDTO(fields[9], fields[10], fields[11], fields[12], fields[13], fields[14])); //TODO kan give problemer hvis nr. er eks 23A
        event.setMoods(parseTags(fields[15]));
        event.setTypes(parseTags(fields[16]));
        return event;
    }

    public ArrayList<String> parseTags(String textTags) {
        ArrayList<String> tags = new ArrayList<>();
        String[] tagArray = textTags.split(";");
        Collections.addAll(tags, tagArray);
        return tags;
    }


    @Override
    public ArrayList<EventDTO> searchEvents(SearchCriteria sc) throws IOException {
        //TODO find en måde at udfylde metoden på
        return getAllEvents();
    }

    @Override
    public ArrayList<EventDTO> getNextNEvents(int offset, int numberOfEvents, SearchCriteria sc) throws IOException {
        return getAllEvents();
    }

    @Override
    public EventDTO getFeaturedEvent() throws IOException {
        return getAllEvents().get(0);
    }
}
