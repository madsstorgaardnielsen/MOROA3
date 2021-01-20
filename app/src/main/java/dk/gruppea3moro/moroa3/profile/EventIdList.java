package dk.gruppea3moro.moroa3.profile;

import java.util.ArrayList;

public class EventIdList {
    public ArrayList<String> eventIds;
    public static final String SAVEDLIST = "saved_event_list";

    public EventIdList(ArrayList<String> eventIds) {
        this.eventIds = eventIds;
    }
}
