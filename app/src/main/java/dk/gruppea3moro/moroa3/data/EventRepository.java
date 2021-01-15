package dk.gruppea3moro.moroa3.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dk.gruppea3moro.moroa3.model.AddressDTO;
import dk.gruppea3moro.moroa3.model.DateTime;
import dk.gruppea3moro.moroa3.model.EventDTO;
import dk.gruppea3moro.moroa3.model.SearchCriteria;
import dk.gruppea3moro.moroa3.profile.EventIdList;

public class EventRepository {

    private static EventRepository instance;
    private SheetReader sheetReader;
    private final MutableLiveData<EventDTO> featuredEventMLD = new MutableLiveData<>();
    private final MutableLiveData<EventDTO> lastViewedEventMLD = new MutableLiveData<>();
    private final MutableLiveData<List<EventDTO>> resultEventsMLD = new MutableLiveData<>();
    private final MutableLiveData<Boolean> couldRefresh = new MutableLiveData<>();
    private final MutableLiveData<Boolean> eventsAvailable = new MutableLiveData<>();


    public EventRepository() {
        sheetReader = new SheetReader();
        couldRefresh.setValue(true);
    }

    public static EventRepository get() {
        if (instance == null) {
            instance = new EventRepository();
        }
        return instance;
    }


    public ArrayList<EventDTO> getAllEvents() throws IOException {
        ArrayList<EventDTO> allEvents = sheetReader.getAllEvents();
        //Set the featured event
        featuredEventMLD.postValue(allEvents.get(0));
        return allEvents;
    }


    public MutableLiveData<EventDTO> getFeaturedEvent() {
        return featuredEventMLD;
    }


    public void setResultEvents(SearchCriteria sc, Context context) {
        Executor bgThread = Executors.newSingleThreadExecutor();
        Handler uiThread = new Handler();
        bgThread.execute(() -> {
            //Gets event from searchCriteria via. EventRepository
            List<EventDTO> eventDTOs = searchEvents(sc, context);

            uiThread.post(() -> resultEventsMLD.setValue(eventDTOs));
        });
    }

    public void setSavedEvents(Context context) {

        Executor bgThread = Executors.newSingleThreadExecutor();
        Handler uiThread = new Handler();
        bgThread.execute(() -> {
            //Gets event from searchCriteria via. EventRepository
            List<EventDTO> eventDTOs = readSavedEvents(context);

            uiThread.post(() -> resultEventsMLD.setValue(eventDTOs));
        });
    }

    private List<EventDTO> readSavedEvents(Context context) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences("saveEvent", Context.MODE_PRIVATE);

        Gson load = new Gson();
        String jsonLoad = sharedPreferences.getString(EventIdList.SAVEDLIST, null);
        ArrayList<String> eventIds = new ArrayList<>();
        if (jsonLoad != null) {
            eventIds = load.fromJson(jsonLoad, EventIdList.class).eventIds;
        } else {
            return new ArrayList<>();
        }

        //Result arraylist
        ArrayList<EventDTO> eventDTOS = new ArrayList<>();

        //Create SQLiteHelper object
        SQLiteHelper dbHelper = new SQLiteHelper(context);

        //Get database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        setEventsAvailable(db);

        String sortOrder = SQLiteContract.events.COLUMN_NAME_STARTDATE + " DESC";
        String selection;
        StringBuilder sb = new StringBuilder();

        String[] selectionArgs = new String[eventIds.size()];

        for (int i = 0; i < eventIds.size(); i++)
            selectionArgs[i] = String.valueOf(eventIds.get(i));

        sb.append(SQLiteContract.events.COLUMN_NAME_ID + " IN  (");
        for (int i = 0; i < eventIds.size(); i++) {

            if (i != eventIds.size() - 1) {
                sb.append("?,");
            } else {
                sb.append("?");
            }
        }
        sb.append(")");

        selection = sb.toString();

        Cursor cursor = db.query(
                SQLiteContract.events.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,           // don't group the rows
                null,             // don't filter by row groups
                sortOrder               // The sort order
        );

        //read results into eventDTO
        readSQLCursor(eventDTOS, cursor);
        db.close();

        eventsAvailable.postValue(true);

        return eventDTOS;

    }

    public MutableLiveData<List<EventDTO>> getResultEventsMLD() {
        return resultEventsMLD;
    }

    public void feedDatabase(Context context) throws IOException {
        //Get dbhelper
        SQLiteHelper dbHelper = new SQLiteHelper(context);

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        //For formatting arraylists to json
        Gson gson = new Gson();

        //Read all events from google sheet
        ArrayList<EventDTO> allEvents = getAllEvents();


        //TODO brug UPDATE i stedet for DELETE og INSERT
        if (allEvents != null) { //If it succeded - safe to delete from SQLite-database
            try {
                EventRepository.get().deleteAllFromDatabase(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (EventDTO event : allEvents) {
            values.put(SQLiteContract.events.COLUMN_NAME_TITLE, event.getTitle());
            values.put(SQLiteContract.events.COLUMN_NAME_SUBTEXT, event.getSubtext());
            values.put(SQLiteContract.events.COLUMN_NAME_PRICE, event.getPrice());
            values.put(SQLiteContract.events.COLUMN_NAME_EVENTLINK, event.getEventLink());
            values.put(SQLiteContract.events.COLUMN_NAME_IMAGELINK, event.getImageLink());
            values.put(SQLiteContract.events.COLUMN_NAME_STARTDATE, event.getStart().getSqlDateTimeFormat());
            values.put(SQLiteContract.events.COLUMN_NAME_ENDDATE, event.getEnd().getSqlDateTimeFormat());
            values.put(SQLiteContract.events.COLUMN_NAME_ZONE, event.getZone());
            values.put(SQLiteContract.events.COLUMN_NAME_ADDRESSNAME, event.getAddressDTO().getAddressName());
            values.put(SQLiteContract.events.COLUMN_NAME_STREETNAME, event.getAddressDTO().getStreetName());
            values.put(SQLiteContract.events.COLUMN_NAME_STREETNUMBER, event.getAddressDTO().getStreetNumber());
            values.put(SQLiteContract.events.COLUMN_NAME_ADDITIONALTEXT, event.getAddressDTO().getAdditionalText());
            values.put(SQLiteContract.events.COLUMN_NAME_ZIPCODE, event.getAddressDTO().getZipCode());
            values.put(SQLiteContract.events.COLUMN_NAME_AREA, event.getAddressDTO().getArea());
            //TODO her ville det nok være bedst at gemme typer og stemning i separate tabeller
            values.put(SQLiteContract.events.COLUMN_NAME_TYPETAGS, gson.toJson(event.getTypes()));
            values.put(SQLiteContract.events.COLUMN_NAME_STEMNINGTAGS, gson.toJson(event.getMoods()));
            values.put(SQLiteContract.events.COLUMN_NAME_ID, event.getId());

            // Insert the new row, returning the primary key value of the new row
            db.insert(SQLiteContract.events.TABLE_NAME, null, values);
        }
        db.close();
        System.out.println("done updating db");
    }

    public ArrayList<EventDTO> searchEvents(SearchCriteria searchCriteria, Context context) {
        //Result arraylist
        ArrayList<EventDTO> eventDTOS = new ArrayList<>();

        //Create SQLiteHelper object
        SQLiteHelper dbHelper = new SQLiteHelper(context);

        //Get database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        setEventsAvailable(db);

        //Default get the events sorted in chronological order - newest first
        String sortOrder = SQLiteContract.events.COLUMN_NAME_STARTDATE + " ASC";
        String selection;
        ArrayList<String> selArgsArrayList = new ArrayList<>();
        String[] selectionArgs = null;

        //Format startDate and endDate the way SQL reads it
        DateTime fromDate = searchCriteria.getFromDate();
        DateTime toDate = searchCriteria.getToDate();

        if (fromDate == null && toDate == null) {
            selectionArgs = null;
            selection = null;
        } else {
            selection = SQLiteContract.events.COLUMN_NAME_ENDDATE + " > ? AND "
                    + SQLiteContract.events.COLUMN_NAME_STARTDATE + " < ?";

            selArgsArrayList.add(fromDate.getSqlDateTimeFormat());
            selArgsArrayList.add(fromDate.getSqlDateTimeFormat());
        }

        if (searchCriteria.getZones().size() > 0) {
            StringBuilder sb = new StringBuilder();
            if (selection != null) {
                sb.append(" AND (");
            } else {
                selection = "";
                sb.append("(");
            }


            sb.append(SQLiteContract.events.COLUMN_NAME_ZONE + " = ?");
            selArgsArrayList.add(searchCriteria.getZones().get(0));
            for (int i = 1; i < searchCriteria.getZones().size(); i++) {
                sb.append(" OR " + SQLiteContract.events.COLUMN_NAME_ZONE + " = ?");
                selArgsArrayList.add(searchCriteria.getZones().get(i));
            }
            sb.append(")");
            selection += sb.toString();
        }

        if (selArgsArrayList.size() > 0) {
            selectionArgs = new String[selArgsArrayList.size()];
            for (int i = 0; i < selectionArgs.length; i++) {
                selectionArgs[i] = selArgsArrayList.get(i);
            }
        }

        Cursor cursor = db.query(
                SQLiteContract.events.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,           // don't group the rows
                null,             // don't filter by row groups
                sortOrder               // The sort order
        );

        //Gson for decoding ArrayLists
        readSQLCursor(eventDTOS, cursor);
        db.close();


        //Remove the events, that don't match either a mood or a type (if these are not null)
        SearchCriteria.popEventsOnMoodsAndTypes(searchCriteria, eventDTOS);

        //Set eventsAvaiable
        eventsAvailable.postValue(true);

        return eventDTOS;
    }

    private void readSQLCursor(ArrayList<EventDTO> eventDTOS, Cursor cursor) {
        Gson gson = new Gson();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                EventDTO eventDTO = new EventDTO();
                //Set all the easy fields
                eventDTO.setTitle(cursor.getString(cursor.getColumnIndex(SQLiteContract.events.COLUMN_NAME_TITLE)));
                eventDTO.setSubtext(cursor.getString(cursor.getColumnIndex(SQLiteContract.events.COLUMN_NAME_SUBTEXT)));
                eventDTO.setEventLink(cursor.getString(cursor.getColumnIndex(SQLiteContract.events.COLUMN_NAME_EVENTLINK)));
                eventDTO.setImageLink(cursor.getString(cursor.getColumnIndex(SQLiteContract.events.COLUMN_NAME_IMAGELINK)));
                eventDTO.setZone(cursor.getString(cursor.getColumnIndex(SQLiteContract.events.COLUMN_NAME_ZONE)));
                eventDTO.setPrice(Double.parseDouble(cursor.getString(cursor.getColumnIndex(SQLiteContract.events.COLUMN_NAME_PRICE))));
                eventDTO.setId((cursor.getString(cursor.getColumnIndex(SQLiteContract.events.COLUMN_NAME_ID))));

                //Set the date related fields
                String startDateString = cursor.getString(cursor.getColumnIndex(SQLiteContract.events.COLUMN_NAME_STARTDATE));
                String endDateString = cursor.getString(cursor.getColumnIndex(SQLiteContract.events.COLUMN_NAME_ENDDATE));
                eventDTO.setStart(new DateTime(startDateString));
                eventDTO.setEnd(new DateTime(endDateString));

                //Address
                eventDTO.setAddressDTO(new AddressDTO(cursor.getString(cursor.getColumnIndex(SQLiteContract.events.COLUMN_NAME_ADDRESSNAME)),
                        cursor.getString(cursor.getColumnIndex(SQLiteContract.events.COLUMN_NAME_STREETNAME)),
                        cursor.getString(cursor.getColumnIndex(SQLiteContract.events.COLUMN_NAME_STREETNUMBER)),
                        cursor.getString(cursor.getColumnIndex(SQLiteContract.events.COLUMN_NAME_ADDITIONALTEXT)),
                        cursor.getString(cursor.getColumnIndex(SQLiteContract.events.COLUMN_NAME_ZIPCODE)),
                        cursor.getString(cursor.getColumnIndex(SQLiteContract.events.COLUMN_NAME_AREA))
                ));

                //Tags
                String typesJson = cursor.getString(cursor.getColumnIndex(SQLiteContract.events.COLUMN_NAME_TYPETAGS));
                String moodsJson = cursor.getString(cursor.getColumnIndex(SQLiteContract.events.COLUMN_NAME_STEMNINGTAGS));
                eventDTO.setTypes(gson.fromJson(typesJson, ArrayList.class));
                eventDTO.setMoods(gson.fromJson(moodsJson, ArrayList.class));

                //Add to result list
                eventDTOS.add(eventDTO);

                cursor.moveToNext();
            }
        }
        cursor.close();
    }


    public void deleteAllFromDatabase(Context context) {
        //Create SQLiteHelper object
        SQLiteHelper dbHelper = new SQLiteHelper(context);

        //Get database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("DELETE FROM " + SQLiteContract.events.TABLE_NAME);
    }

    public void refreshDbInBackground(Context context) {
        Executor bgThread = Executors.newSingleThreadExecutor();

        bgThread.execute(() -> {
            try {
                EventRepository.get().feedDatabase(context);
                couldRefresh.postValue(true);
            } catch (IOException e) {
                e.printStackTrace();
                couldRefresh.postValue(false);
            }
        });

    }

    public MutableLiveData<EventDTO> getLastViewedEventMLD() {
        return lastViewedEventMLD;
    }

    public void setLastViewedEventMLD(EventDTO lastViewedEventMLD) {
        this.lastViewedEventMLD.setValue(lastViewedEventMLD);
    }

    public boolean setEventsAvailable(SQLiteDatabase db) {
        Cursor mCursor = db.rawQuery("SELECT * FROM " + SQLiteContract.events.TABLE_NAME, null);
        boolean rowExists;

        rowExists = mCursor.moveToFirst();
        eventsAvailable.postValue(rowExists);
        mCursor.close();
        return rowExists;
    }

    public MutableLiveData<Boolean> getCouldRefresh() {
        return couldRefresh;
    }

    public MutableLiveData<Boolean> getEventsAvailable() {
        return eventsAvailable;
    }
}
