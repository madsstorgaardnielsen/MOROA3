package dk.gruppea3moro.moroa3.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import dk.gruppea3moro.moroa3.model.AddressDTO;
import dk.gruppea3moro.moroa3.model.DateTime;
import dk.gruppea3moro.moroa3.model.EventDTO;
import dk.gruppea3moro.moroa3.model.TagDTO;

public class SheetReader {

    final String EVENT_SHEET_ID = "1mZFpWlSVm7v-_lLbbCaWo_OgdN-lP3XmvMTTu1wbqFY";
    final String TAG_SHEET_ID = "1omoan2jWUlqZ8AYA2HesJh8U-mUKXqwTwV_tOw8PdFU";

    public ArrayList<EventDTO> getAllEvents() throws IOException {
        //Result arraylist
        ArrayList<EventDTO> events = new ArrayList<>();

        //URL
        String url = "https://docs.google.com/spreadsheets/d/" + EVENT_SHEET_ID + "/export?format=tsv&id=" + EVENT_SHEET_ID;
        //System.out.println(url);

        //Reader
        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));

        //Skip first line
        String line = br.readLine();
        line = br.readLine();
        while (line != null) {
            try {
                events.add(createEventDTO(line));
            } catch (Exception e) {
                //System.out.println("DATA: " + line);
                e.printStackTrace();
            }
            line = br.readLine();
        }
        br.close();
        return events;
    }

    public EventDTO createEventDTO(String line) {
        EventDTO event = new EventDTO();
        String[] fields = line.split("\t",-1);
        int[] mandatoryIndexes = {0,1,3,4,5,6,17,18};
        for (int i = 0; i < mandatoryIndexes.length; i++) {
            if (fields[mandatoryIndexes[i]].equals("")){
                throw new NullPointerException("Mandatory field in event is empty" + fields);
            }
        }

        //Set mandatory fields - might throw exception, and event will be dropped
        event.setTitle(fields[0]);
        event.setSubtext(fields[1]);
        event.setEventLink(fields[3]);
        event.setImageLink(fields[4]);
        event.setStart(new DateTime(fields[6], fields[5]));
        event.setZone(fields[17]);
        event.setId(fields[18]);

        //Non-mandatory events - if empty string it is no problem
        event.setPrice(Integer.parseInt(fields[2]));
        event.setEnd(new DateTime(fields[8], fields[7]));
        event.setAddressDTO(new AddressDTO(fields[9], fields[10], fields[11], fields[12], fields[13], fields[14]));
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

    public ArrayList<TagDTO> getAllTags() throws IOException {
        //Result arraylist
        ArrayList<TagDTO> tags = new ArrayList<>();

        //URL
        String url = "https://docs.google.com/spreadsheets/d/" + TAG_SHEET_ID + "/export?format=tsv&id=" + TAG_SHEET_ID;
        //System.out.println(url);

        //Reader
        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));

        //Skip first line
        String line = br.readLine();
        line = br.readLine();
        while (line != null) {
            try {
                tags.add(createTagDTO(line));
            } catch (Exception e) {
                //System.out.println("DATA: " + line);
                e.printStackTrace();
            }
            line = br.readLine();
        }
        br.close();
        return tags;
    }

    public TagDTO createTagDTO(String line) {
        String[] fields = line.split("\t");
        TagDTO tagDTO = new TagDTO(fields[0], fields[1], fields[2], fields[3]);
        tagDTO.setCategory(fields[0]);
        return tagDTO;
    }

}
