package dk.gruppea3moro.moroa3.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.provider.BaseColumns;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import dk.gruppea3moro.moroa3.model.EventDTO;
import dk.gruppea3moro.moroa3.model.SearchCriteria;

public class DataController {

    private static DataController instance;
    private EventLoader eventLoader;

    public DataController() {
        eventLoader = new SheetReader();
    }

    public static DataController get() {
        if (instance == null) {
            instance = new DataController();
        }
        return instance;
    }

    private EventLoader getEventLoader() {
        return eventLoader;
    }

    public ArrayList<EventDTO> getAllEvents() {
        try {
            return getEventLoader().getAllEvents();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<EventDTO> getNextNEvents(int offset, int numberOfEvents, SearchCriteria sc) {
        try {
            return getEventLoader().getNextNEvents(offset, numberOfEvents, sc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public EventDTO getFeaturedEvent() {
        try {
            return getEventLoader().getFeaturedEvent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Drawable loadImageFromURL(EventDTO eventDTO) {
        try {
            InputStream is = (InputStream) new URL(eventDTO.getImageLink()).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    public void feedDatabase(Context context){
        //Get dbhelper
        SQLiteHelper dbHelper = new SQLiteHelper(context);

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        //For formatting arraylists to json
        Gson gson = new Gson();

        ArrayList<EventDTO> allEvents = getAllEvents();//Reads the entire google sheet
        for(EventDTO event : allEvents){
            values.put(SQLiteContract.events.COLUMN_NAME_TITLE, event.getTitle());
            values.put(SQLiteContract.events.COLUMN_NAME_SUBTEXT, event.getSubtext());
            values.put(SQLiteContract.events.COLUMN_NAME_PRICE, event.getPrice());
            values.put(SQLiteContract.events.COLUMN_NAME_EVENTLINK, event.getEventLink());
            values.put(SQLiteContract.events.COLUMN_NAME_IMAGELINK, event.getImageLink());
            values.put(SQLiteContract.events.COLUMN_NAME_STARTDATE, event.getSQLstartDate());
            values.put(SQLiteContract.events.COLUMN_NAME_ENDDATE, event.getSQLendDate());
            values.put(SQLiteContract.events.COLUMN_NAME_ZONE, event.getAddressDTO().getAddressName());
            values.put(SQLiteContract.events.COLUMN_NAME_ADDRESSNAME, event.getAddressDTO().getAddressName());
            values.put(SQLiteContract.events.COLUMN_NAME_STREETNAME, event.getAddressDTO().getStreetName());
            values.put(SQLiteContract.events.COLUMN_NAME_STREETNUMBER, event.getAddressDTO().getStreetNumber());
            values.put(SQLiteContract.events.COLUMN_NAME_ADDITIONALTEXT, event.getAddressDTO().getAdditionalText());
            values.put(SQLiteContract.events.COLUMN_NAME_ZIPCODE, event.getAddressDTO().getZipCode());
            values.put(SQLiteContract.events.COLUMN_NAME_AREA, event.getAddressDTO().getArea());
            //TODO her ville det nok være bedst at gemme typer og stemning i separate tabeller
            values.put(SQLiteContract.events.COLUMN_NAME_STEMNINGTAGS, gson.toJson(event.getTypes()));
            values.put(SQLiteContract.events.COLUMN_NAME_TYPETAGS, gson.toJson(event.getMoods()));
        }

        // Insert the new row, returning the primary key value of the new row
        db.insert(SQLiteContract.events.TABLE_NAME, null, values);
    }

    public ArrayList<EventDTO> searchEvents(Context context, SearchCriteria searchCriteria) {
        //Create SQLiteHelper object
        SQLiteHelper dbHelper = new SQLiteHelper(context);

        //Get database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection =null;
        String sortOrder =null;

        //Format startDate and endDate the way SQL reads it
        Date d1 =searchCriteria.getFromDate();
        Date d2 =searchCriteria.getToDate();

        String SQLfromDate = d1.getYear() + "/" + d1.getMonth() +"/"+ d1.getDay() + " " +
            + d1.getHours() + ":" +d1.getMinutes() + ":00";
        String SQLtoDate = d2.getYear() + "/" + d2.getMonth() +"/"+ d2.getDay() + " " +
                + d2.getHours() + ":" +d2.getMinutes() + ":00";

        // Filter results WHERE "title" = 'My Title'
        String selection = SQLiteContract.events.COLUMN_NAME_ENDDATE + " > ? AND "
                +SQLiteContract.events.COLUMN_NAME_STARTDATE + " < ? AND ";
        String[] selectionArgs = {SQLfromDate,SQLtoDate};

        Cursor cursor = db.query(
                SQLiteContract.events.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,           // don't group the rows
                null,             // don't filter by row groups
                sortOrder               // The sort order
        );
        return null;
    }
}
