package dk.gruppea3moro.moroa3.data;

import java.io.IOException;
import java.util.ArrayList;

import dk.gruppea3moro.moroa3.model.EventDTO;
import dk.gruppea3moro.moroa3.model.SearchCriteria;
//Loads events from external database into phones local database
public interface EventLoader {

    ArrayList<EventDTO> getAllEvents() throws IOException;

   EventDTO getFeaturedEvent() throws IOException;
}
