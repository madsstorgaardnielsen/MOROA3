package dk.gruppea3moro.moroa3.data;

import android.provider.BaseColumns;

public final class SQLiteContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private SQLiteContract() {}

    /* Inner class that defines the table contents */
    public static class events implements BaseColumns {
        public static final String TABLE_NAME = "events";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTEXT = "subtext";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_EVENTLINK = "eventlink";
        public static final String COLUMN_NAME_IMAGELINK = "imagelink";
        public static final String COLUMN_NAME_STARTDATE = "startdate";
        public static final String COLUMN_NAME_ENDDATE = "enddate";
        public static final String COLUMN_NAME_ZONE = "zone";
        public static final String COLUMN_NAME_ADDRESSNAME = "addressname";
        public static final String COLUMN_NAME_STREETNAME = "streetname";
        public static final String COLUMN_NAME_STREETNUMBER = "streetnumber";
        public static final String COLUMN_NAME_ADDITIONALTEXT = "additionaltext";
        public static final String COLUMN_NAME_ZIPCODE = "zipcode";
        public static final String COLUMN_NAME_AREA = "area";
        public static final String COLUMN_NAME_STEMNINGTAGS = "stemningtags";
        public static final String COLUMN_NAME_TYPETAGS = "typetags";

    }
}

