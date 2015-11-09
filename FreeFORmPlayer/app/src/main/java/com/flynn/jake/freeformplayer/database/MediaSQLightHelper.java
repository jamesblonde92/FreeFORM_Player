package com.flynn.jake.freeformplayer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by countrynerd on 11/8/15.
 */
public class MediaSQLightHelper extends SQLiteOpenHelper {

    //-------Variables-------//

    private static final String DB_NAME = "Media.db";
    private static final int DB_VERSION = 1;

    //-------End Variables-------//


    //--------Media table functionality--------//

    private static String CREATE_MEDIA =
    "CREATE TABLE "+ MediaContract.MediaInfo.MEDIA_TABLE + "(" +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            MediaContract.MediaInfo.COLUMN_MEDIA_TYPE + " TEXT," +
            MediaContract.MediaInfo.COLUMN_MEDIA_PATH + " TEXT," +
            MediaContract.MediaInfo.COLUMN_MEDIA_SIZE + " INTEGER,"  +
            MediaContract.MediaInfo.COLUMN_MEDIA_FORMAT + " TEXT)";

    private static String CREATE_SONGS =
            "CREATE TABLE" + MediaContract.SongAttributes.SONG_TABLE + "(" +
            BaseColumns._ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
            MediaContract.SongAttributes.COLUMN_SONGS_NAME + " TEXT," +
            MediaContract.SongAttributes.COLUMN_SONGS_GENRE + " TEXT," +
            MediaContract.SongAttributes.COLUMN_SONGS_ARTIST + " TEXT," +
            MediaContract.SongAttributes.COLUMN_SONGS_LENGTH + " INTEGER," +
            "FOREIGN KEY(" + MediaContract.SongAttributes.COLUMN_FOREIGN_KEY_MEDIA + ") REFERENCES MediaInfo(_ID)";

    private static String CREATE_ALBUMS =
            "CREATE TABLE" + MediaContract.Albums.ALBUM_TABLE + "(" +
            BaseColumns._ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
            MediaContract.Albums.COLUMN_ALBUM_NAME + " TEXT," +
            MediaContract.Albums.COLUMN_ALBUM_YEAR + " INTEGER," +
            MediaContract.Albums.COLUMN_ALBUM_ARTIST + " TEXT," +
            "FOREIGN KEY(" + MediaContract.Albums.COLUMN_FOREIGN_KEY_SONGS + ") REFERENCES SongAttributes(_ID)";

    private static String CREATE_ART =
            "CREATE TABLE" + MediaContract.SongArt.ART_TABLE + "(" +
            BaseColumns._ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
            MediaContract.SongArt.COLUMN_ART_HEIGHT + " INTEGER," +
            MediaContract.SongArt.COLUMN_ART_WIDTH + " INTEGER," +
            "FOREIGN KEY(" + MediaContract.SongArt.COLUMN_FOREIGN_KEY_SONGS + ") REFERENCES SongAttributes(_ID)";

    private static String CREATE_VIDEOS =
            "CREATE TABLE" + MediaContract.Videos.VIDEO_TABLE + "(" +
            BaseColumns._ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
            MediaContract.Videos.COLUMN_VIDEO_NAME + " TEXT," +
            MediaContract.Videos.COLUMN_VIDEO_LENGTH + " INTEGER," +
            "FOREIGN KEY(" + MediaContract.Videos.COLUMN_FOREIGN_KEY_MEDIA + ") REFERENCES MediaInfo(_ID)";

    //--------End Media table functionality--------//


    //-------Constructor-------//

    public MediaSQLightHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    //-------End Constructor-------//

    //-------Methods-------//

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MEDIA);
        db.execSQL(CREATE_SONGS);
        db.execSQL(CREATE_ALBUMS);
        db.execSQL(CREATE_ART);
        db.execSQL(CREATE_VIDEOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //-------End Methods-------//
}
