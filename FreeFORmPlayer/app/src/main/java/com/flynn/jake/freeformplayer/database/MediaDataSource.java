package com.flynn.jake.freeformplayer.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.provider.MediaStore;

import com.flynn.jake.freeformplayer.models.Song;

import java.util.ArrayList;

/**
 * 
 * Created by countrynerd on 11/8/15.
 *
 */

public class MediaDataSource extends SQLiteOpenHelper {

    //-------Variables------//

    private static final String DB_NAME = "media.db";
    private static final int DB_VERSION = 1;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static final String SONG_TABLE = "Songs";
    public static final String COLUMN_SONG_ID = "SongID";
    public static final String COLUMN_SONGS_NAME = "SongName";
    public static final String COLUMN_SONGS_GENRE = "GenreName";
    public static final String COLUMN_SONGS_ARTIST = "ArtistName";
    public static final String COLUMN_SONGS_YEAR = "SongYear";
    public static final String COLUMN_SONGS_ALBUM = "AlbumName";
    public static final String COLUMN_SONG_URI = "SongURI";

    //-------End Variables------//

    //------Constructors------//

    public MediaDataSource(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String cmd = "CREATE TABLE " + SONG_TABLE + "(" +
                COLUMN_SONG_ID + " INTEGER PRIMARY KEY," +
                COLUMN_SONGS_NAME + " TEXT," +
                COLUMN_SONGS_GENRE + " TEXT," +
                COLUMN_SONGS_ARTIST + " TEXT," +
                COLUMN_SONGS_ALBUM + " TEXT," +
                COLUMN_SONGS_YEAR + " INTEGER," +
                COLUMN_SONG_URI + " TEXT)";

        db.execSQL(cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //------End Constructors------//

    //------CRUD Methods------//


    public ArrayList<Song> readSong(){
        SQLiteDatabase database = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + SONG_TABLE + " ORDER BY SongName";

        Cursor cursor = database.rawQuery(selectQuery,null);
        ArrayList<Song> songs = new ArrayList<Song>();
        if (cursor.moveToFirst())
        {
            do {
                Song newSong = new Song(Long.parseLong(cursor.getString(0)), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        Integer.parseInt(cursor.getString(5)), null);
                songs.add(newSong);
            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return songs;
    }

    public ArrayList<Song> readSongFromCategory(String category, String keyword){
        SQLiteDatabase database = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + SONG_TABLE + " WHERE " + category + " = " + keyword + " ORDER BY SongName";

        Cursor cursor = database.rawQuery(selectQuery,null);
        ArrayList<Song> songs = new ArrayList<Song>();
        if (cursor.moveToFirst())
        {
            do {
                Song newSong = new Song(Long.parseLong(cursor.getString(0)), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        Integer.parseInt(cursor.getString(5)), null);
                songs.add(newSong);
            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return songs;
    }

    public ArrayList<String> readCategory(String queryKeyword){ // this can be parametrized by sending in the query to get artist, genres, songs from that artist, etc.
        SQLiteDatabase database = this.getWritableDatabase();

        String selectQuery = "SELECT " + queryKeyword +" FROM " + SONG_TABLE + " GROUP BY " + queryKeyword + " ORDER BY " + queryKeyword;

        Cursor cursor = database.rawQuery(selectQuery,null);
        ArrayList<String> artists = new ArrayList<String>();
        if (cursor.moveToFirst())
        {
            do {
                String newArtist = cursor.getString(0);
                artists.add(newArtist);
            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return artists;
    }

    public void addSong(Song newSong){
        SQLiteDatabase database = this.getWritableDatabase();

        // Implementations details
        ContentValues songValues = new ContentValues();
        songValues.put(COLUMN_SONGS_NAME, newSong.getName());
        songValues.put(COLUMN_SONG_ID, newSong.getSongID());
        songValues.put(COLUMN_SONGS_ARTIST, newSong.getArtist());
        songValues.put(COLUMN_SONGS_GENRE, newSong.getGenre());
        songValues.put(COLUMN_SONGS_ALBUM, newSong.getAlbum());
        songValues.put(COLUMN_SONGS_YEAR, newSong.getYear());
        database.insert(SONG_TABLE, null, songValues);
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
