package dk.gruppea3moro.moroa3.data;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dk.gruppea3moro.moroa3.model.AppState;
import dk.gruppea3moro.moroa3.model.EventDTO;
import dk.gruppea3moro.moroa3.model.SearchCriteria;

import static org.junit.Assert.*;

public class EventRepositoryTest {
    Context appContext;

    @Before
    public void setUp() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void getAllEvents() {
        EventRepository eventRepository = new EventRepository();

        Executor bgThread = Executors.newSingleThreadExecutor();
        bgThread.execute(() -> {
            try {
                ArrayList<EventDTO> allEvents;
                allEvents = eventRepository.getAllEvents();
                assertTrue(allEvents.size() > 0);
            } catch (IOException e) {
                assertTrue(false);
                e.printStackTrace();
            }
        });

    }


    @Test
    public void searchEvents_all() {
        //Tests if all events are returned, when search is made on empty criteria

        EventRepository eventRepository = new EventRepository();
        SearchCriteria searchCriteria = new SearchCriteria();
        ArrayList<EventDTO> allEvents = new ArrayList<>();
        ArrayList<EventDTO> searchResults;

        try {
            allEvents = eventRepository.getAllEvents();

        } catch (IOException e) {
            assertTrue(false);
            e.printStackTrace();
        }
        searchResults = eventRepository.searchEvents(searchCriteria, appContext);

        for (EventDTO eventDTO :
                allEvents) {
            boolean contains = false;
            for (EventDTO event :
                    searchResults) {
                if (eventDTO.getTitle().equals(event.getTitle())) {
                    contains = true;
                }
            }
            assertTrue(contains);
        }
    }

    @Test
    public void searchEvents_now() {

        EventRepository eventRepository = new EventRepository();
        SearchCriteria searchCriteria = AppState.getRightNowSearchCriteria();
        ArrayList<EventDTO> searchResults;


        searchResults = eventRepository.searchEvents(searchCriteria, appContext);


        boolean contains = false;
        for (EventDTO event :
                searchResults) {
            if (event.getEnd()) {
                contains = true;
            }

            assertTrue(contains);
        }


    }


}