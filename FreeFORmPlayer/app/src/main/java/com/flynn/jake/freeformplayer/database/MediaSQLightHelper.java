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

    private static final String DB_NAME = "media.db";
    private static final int DB_VERSION = 1;

    //-------End Variables-------//


    //--------Media table functionality--------//


    private static String CREATE_SONGS =
            "CREATE TABLE " + MediaContract.SongAttributes.SONG_TABLE + "(" +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            MediaContract.SongAttributes.COLUMN_SONGS_NAME + " TEXT," +
            MediaContract.SongAttributes.COLUMN_SONGS_GENRE + " TEXT," +
            MediaContract.SongAttributes.COLUMN_SONGS_ARTIST + " TEXT," +
            MediaContract.SongAttributes.COLUMN_SONGS_ALBUM + " TEXT," +
            MediaContract.SongAttributes.COLUMN_SONGS_YEAR + " INTEGER)";


    //--------End Media table functionality--------//


    //-------Constructor-------//

    public MediaSQLightHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    //-------End Constructor-------//

    //-------Methods-------//

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SONGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //-------End Methods-------//
}
