package com.flynn.jake.freeformplayer.database;


import android.provider.BaseColumns;

import com.flynn.jake.freeformplayer.models.Song;

/**
 * Created by countrynerd on 11/8/15.
 */
public final class MediaContract {

    public MediaContract(){
        // Empty to make sure no one accidentally instantiating the contract class
    }

    //------SongEntity Tables--------//
    public static abstract class SongAttributes implements BaseColumns {
        public static final String SONG_TABLE = "songs";
        public static final String COLUMN_SONGS_ID = "songID";
        public static final String COLUMN_SONGS_IDFROMSONG = "idFromSons";
        public static final String COLUMN_SONGS_NAME = "songName";
        public static final String COLUMN_SONGS_GENRE = "genreName";
        public static final String COLUMN_SONGS_ARTIST = "atistName";
        public static final String COLUMN_SONGS_YEAR = "songYear";
        public static final String COLUMN_SONGS_ALBUM = "albumName";
    }

    //------End SongEntity Tables--------//

}