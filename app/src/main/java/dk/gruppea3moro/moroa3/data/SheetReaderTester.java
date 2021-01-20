package dk.gruppea3moro.moroa3.data;

import java.io.IOException;
import java.util.ArrayList;

import dk.gruppea3moro.moroa3.model.EventDTO;
import io.sentry.Sentry;


public class SheetReaderTester {
    public static void main(String[] args) {
        SheetReader s = new SheetReader();
        try {
            ArrayList<EventDTO> x = s.getAllEvents();
            System.out.println("Færdig med at læse - nu princtes resultatet");
            for (EventDTO event : x) {
                System.out.println(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Sentry.captureException(e);
        }
    }
}
