package com.flynn.jake.freeformplayer.database;

import android.provider.BaseColumns;

/**
 * Created by countrynerd on 11/8/15.
 */
public final class MediaContract {

    public MediaContract(){
        // Empty to make sure no one accidentally instantiating the contract class
    }

    //------Media Info Table--------//
    public static abstract class MediaInfo implements BaseColumns {
        public static final String MEDIA_TABLE = "MediaInfo";
        //public static final String MEDIA_ID = "MEDIA_ID";
        public static final String COLUMN_MEDIA_TYPE = "Type";
        public static final String COLUMN_MEDIA_PATH = "Path";
        public static final String COLUMN_MEDIA_SIZE = "Size";
        public static final String COLUMN_MEDIA_FORMAT = "Format";
    }
    //------End Media Table--------//

    //------Song Tables--------//
    public static abstract class SongAttributes implements BaseColumns {
        public static final String SONG_TABLE = "Songs";
        public static final String COLUMN_FOREIGN_KEY_MEDIA = "MediaId";
        public static final String COLUMN_SONGS_NAME = "SongName";
        public static final String COLUMN_SONGS_GENRE = "GenreName";
        public static final String COLUMN_SONGS_ARTIST = "ArtistName";
        public static final String COLUMN_SONGS_LENGTH = "Length";
    }

    public static abstract class Albums implements BaseColumns {
        public static final String ALBUM_TABLE = "Albums";
        public static final String COLUMN_FOREIGN_KEY_SONGS = "SongsID";
        public static final String COLUMN_ALBUM_NAME = "AlbumName";
        public static final String COLUMN_ALBUM_YEAR = "AlbumYear";
        public static final String COLUMN_ALBUM_ARTIST = "AlbumArtist";
    }

    public static abstract class SongArt implements BaseColumns{
        public static final String ART_TABLE = "SongArt";
        public static final String COLUMN_FOREIGN_KEY_SONGS = "SongsID";
        public static final String COLUMN_ART_HEIGHT = "Height";
        public static final String COLUMN_ART_WIDTH = "Width";
        // Might need more later.
    }
    //------End Song Tables--------//

    //------Video-------//
    public static abstract class Videos implements BaseColumns{
        public static final String VIDEO_TABLE = "Videos";
        public static final String COLUMN_VIDEO_NAME = "Name";
        public static final String COLUMN_VIDEO_LENGTH = "Length";
        public static final String COLUMN_FOREIGN_KEY_MEDIA = "MediaId";
    }
    //-------End Video-------//
}
