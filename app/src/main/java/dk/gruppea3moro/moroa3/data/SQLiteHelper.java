package dk.gruppea3moro.moroa3.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import dk.gruppea3moro.moroa3.data.SQLiteContract.*;

public class SQLiteHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final  int    DATABASE_VERSION   = 1;
    public static final  String DATABASE_NAME      = "database.db";
    private static final String TEXT_TYPE          = " TEXT";
    private static final String DATE_TYPE          = " SMALLDATETYPE";
    private static final String COMMA_SEP          = ",";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
            events.TABLE_NAME + " (" +
            events.COLUMN_NAME_TITLE + " STRING PRIMARY KEY," +
            events.COLUMN_NAME_SUBTEXT + TEXT_TYPE + COMMA_SEP +
            events.COLUMN_NAME_PRICE + TEXT_TYPE + COMMA_SEP +
            events.COLUMN_NAME_EVENTLINK + TEXT_TYPE + COMMA_SEP +
            events.COLUMN_NAME_IMAGELINK + TEXT_TYPE + COMMA_SEP +
            events.COLUMN_NAME_STARTDATE + DATE_TYPE + COMMA_SEP +
            events.COLUMN_NAME_ENDDATE + DATE_TYPE + COMMA_SEP +
            events.COLUMN_NAME_ZONE + TEXT_TYPE + COMMA_SEP +
            events.COLUMN_NAME_ADDRESSNAME + TEXT_TYPE + COMMA_SEP +
            events.COLUMN_NAME_STREETNAME + TEXT_TYPE + COMMA_SEP +
            events.COLUMN_NAME_STREETNUMBER + TEXT_TYPE + COMMA_SEP +
            events.COLUMN_NAME_ADDITIONALTEXT + TEXT_TYPE + COMMA_SEP +
            events.COLUMN_NAME_ZIPCODE + TEXT_TYPE + COMMA_SEP +
            events.COLUMN_NAME_AREA + TEXT_TYPE + COMMA_SEP +
            events.COLUMN_NAME_STEMNINGTAGS + TEXT_TYPE + COMMA_SEP +
            events.COLUMN_NAME_TYPETAGS + TEXT_TYPE +
             " )";


    private static final String SQL_DELETE_ALL_ENTRIES = "DROP TABLE IF EXISTS " + events.TABLE_NAME;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    // Method is called during an upgrade of the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");

        db.execSQL(SQL_DELETE_ALL_ENTRIES);
        onCreate(db);
    }

}
