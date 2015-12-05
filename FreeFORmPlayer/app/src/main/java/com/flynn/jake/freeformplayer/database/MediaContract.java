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
        public static final String SONG_TABLE = "Songs";
        public static final String COLUMN_SONGS_NAME = "SongName";
        public static final String COLUMN_SONGS_GENRE = "GenreName";
        public static final String COLUMN_SONGS_ARTIST = "ArtistName";
        public static final String COLUMN_SONGS_YEAR = "SongYear";
        public static final String COLUMN_SONGS_ALBUM = "AlbumName";
    }

    //------End SongEntity Tables--------//

}
