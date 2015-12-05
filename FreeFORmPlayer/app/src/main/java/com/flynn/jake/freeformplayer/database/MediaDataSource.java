package com.flynn.jake.freeformplayer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.provider.MediaStore;

import com.flynn.jake.freeformplayer.database.MediaContract;
import com.flynn.jake.freeformplayer.database.MediaSQLightHelper;
import com.flynn.jake.freeformplayer.models.Song;

import java.util.ArrayList;

/**
 *
 * Created by countrynerd on 11/8/15.
 *
 */

public class MediaDataSource {


    //-------Variables------//

    private Context mContext;
    private MediaSQLightHelper mMediaSQLightHelper;

    //-------End Variables------//

    //------Constructors------//

    public MediaDataSource(Context context){
        mContext = context;
        mMediaSQLightHelper = new MediaSQLightHelper(mContext);
    }

    public SQLiteDatabase open()  {
        return mMediaSQLightHelper.getWritableDatabase();
    }

    public void close(SQLiteDatabase database){
        database.close();
    }

    //------End Constructors------//

    //------End Constructors------//

    //------CRUD Methods------//


    public ArrayList<Song> readSong(){
        SQLiteDatabase database = open();

        Cursor cursor = database.query(
                MediaContract.SongAttributes.SONG_TABLE,
                new String[]{MediaContract.SongAttributes.COLUMN_SONGS_NAME,
                        MediaContract.SongAttributes.COLUMN_SONGS_ALBUM,
                        MediaContract.SongAttributes.COLUMN_SONGS_ID,
                        MediaContract.SongAttributes.COLUMN_SONGS_ARTIST,
                        MediaContract.SongAttributes.COLUMN_SONGS_YEAR,
                        MediaContract.SongAttributes.COLUMN_SONGS_GENRE},
                null, // Selection
                null, // Selection Args
                null, // Group By
                null, // Having
                null); // Order

        ArrayList<Song> songs = new ArrayList<Song>();
        if (cursor.moveToFirst())
        {
            do {
                Song newSong = new Song(Long.parseLong(getStringFromColumnName(cursor, MediaContract.SongAttributes.COLUMN_SONGS_ID)),
                        getStringFromColumnName(cursor, MediaContract.SongAttributes.COLUMN_SONGS_NAME),
                        getStringFromColumnName(cursor, MediaContract.SongAttributes.COLUMN_SONGS_ARTIST),
                        getStringFromColumnName(cursor, MediaContract.SongAttributes.COLUMN_SONGS_ALBUM),
                        getStringFromColumnName(cursor, MediaContract.SongAttributes.COLUMN_SONGS_GENRE),
                        getIntFromColumnName(cursor, MediaContract.SongAttributes.COLUMN_SONGS_YEAR), null);
                songs.add(newSong);
            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return songs;
    }

    public void addSong(Song newSong){
        SQLiteDatabase database = this.open();

        // Implementations details
        ContentValues songValues = new ContentValues();
        songValues.put(MediaContract.SongAttributes.COLUMN_SONGS_NAME, newSong.getName());
        songValues.put(MediaContract.SongAttributes.COLUMN_SONGS_ID, "" + newSong.getSongID());
        songValues.put(MediaContract.SongAttributes.COLUMN_SONGS_ARTIST, newSong.getArtist());
        songValues.put(MediaContract.SongAttributes.COLUMN_SONGS_GENRE, newSong.getGenre());
        songValues.put(MediaContract.SongAttributes.COLUMN_SONGS_ALBUM, newSong.getAlbum());
        songValues.put(MediaContract.SongAttributes.COLUMN_SONGS_YEAR, newSong.getYear());

        database.close();
    }



    //------End CRUD Methods------//

    //------Helper Methods------//

    private int getIntFromColumnName(Cursor cursor, String columnName){
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getInt(columnIndex);
    }

    private String getStringFromColumnName(Cursor cursor, String columnName){
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }

    //------End Helper Methods------//
}
