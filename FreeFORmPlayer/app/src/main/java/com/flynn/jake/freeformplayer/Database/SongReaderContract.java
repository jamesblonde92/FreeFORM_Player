package com.flynn.jake.freeformplayer.Database;
import android.provider.BaseColumns;

/**
 * Created by countrynerd on 11/4/15.
 */

public final class SongReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public SongReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class SongEntry implements BaseColumns {
        public static final String SONG_LIST = "file";
        public static final String FILE_ID = "fileID";
        public static final String COLUMN_TITLE = "title";
        public static final String FILE_LOCATION = "fileLocation";
    }
}