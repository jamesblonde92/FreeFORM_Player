package com.flynn.jake.freeformplayer.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.flynn.jake.freeformplayer.models.Song;

/**
 * 
 * Created by countrynerd on 11/8/15.
 *
 */

public class MediaDataSource {

    //-------Variables------//

    private Context mContext;
    private MediaSQLightHelper mMediaSQLightHelper;
    private SQLiteDatabase mDatabase;

    //-------End Variables------//

    //------Constructors------//

    public MediaDataSource(Context context){
        mContext = context;
        mMediaSQLightHelper = new MediaSQLightHelper(mContext);

        SQLiteDatabase database = mMediaSQLightHelper.getReadableDatabase();
        database.close();
    }

    public void open()  {
        //mDatabase =  mMediaSQLightHelper.getWritableDatabase();
    }

    public void close(){
        mDatabase.close();
    }

    //------End Constructors------//

    //------ISUD Methods------//


    /*
    public void insertSong(Song newSong){
        SQLiteDatabase database = mDatabase;
        database.beginTransaction();        // used for thread safe code

        // Implementations details
        long mediaId = makeMediaId(newSong, database);
        ContentValues songValues = new ContentValues();
        songValues.put(MediaContract.SongAttributes.COLUMN_SONGS_NAME, Song());
        songValues.put(MediaContract.SongAttributes.COLUMN_SONGS_ARTIST, Song.getArtistName());
        songValues.put(MediaContract.SongAttributes.COLUMN_SONGS_GENRE, SongEntity.getGenreName());
        songValues.put(MediaContract.SongAttributes.COLUMN_FOREIGN_KEY_MEDIA, mediaId);
        long songID = database.insert(MediaContract.SongAttributes.SONG_TABLE, null, songValues);


        database.setTransactionSuccessful();
        database.endTransaction();
        close();
    }


    public ArrayList<SongEntity> selectSong(){
        SQLiteDatabase database = mDatabase;

        Cursor cursorMedia = database.query(
                MediaContract.SongAttributes.SONG_TABLE,
                new String[]{MediaContract.SongAttributes.COLUMN_SONGS_NAME,
                        MediaContract.SongAttributes._ID,
                        MediaContract.SongAttributes.COLUMN_SONGS_ARTIST,
                        MediaContract.SongAttributes.COLUMN_SONGS_GENRE,
                        MediaContract.SongAttributes.COLUMN_SONGS_LENGTH},
                null, // Selection
                null, // Selection Args
                null, // Group By
                null, // Having
                null); // Order

        Cursor cursor = database.query(
                MediaContract.SongAttributes.SONG_TABLE,
                new String[]{MediaContract.SongAttributes.COLUMN_SONGS_NAME,
                        MediaContract.SongAttributes._ID,
                        MediaContract.SongAttributes.COLUMN_SONGS_ARTIST,
                        MediaContract.SongAttributes.COLUMN_SONGS_GENRE,
                        MediaContract.SongAttributes.COLUMN_SONGS_LENGTH},
                null, // Selection
                null, // Selection Args
                null, // Group By
                null, // Having
                null); // Order
        ArrayList<SongEntity> songEntities = new ArrayList<SongEntity>();
        if (cursor.moveToFirst()){
            do{
                SongEntity songEntity = new SongEntity("SongEntity", getStringFromColumName(cursor, MediaContract.MediaInfo.COLUMN_MEDIA_PATH),
                        getIntFromColumName(cursor, MediaContract.MediaInfo.COLUMN_MEDIA_SIZE),
                        getStringFromColumName(cursor, MediaContract.MediaInfo.COLUMN_MEDIA_FORMAT),
                        getStringFromColumName(cursor, MediaContract.SongAttributes.COLUMN_SONGS_NAME),
                        getStringFromColumName(cursor, MediaContract.SongAttributes.COLUMN_SONGS_GENRE),
                        getStringFromColumName(cursor, MediaContract.SongAttributes.COLUMN_SONGS_ARTIST),
                        getIntFromColumName(cursor, MediaContract.SongAttributes.COLUMN_SONGS_LENGTH));
                songEntities.add(songEntity);
            }while (cursor.moveToNext());
        }

        
        cursorMedia.close();
        cursor.close();
        database.close();
        return songEntities;
    }

    //------End CRUD Methods------//

    //------Helper Methods------//

    private int getIntFromColumName(Cursor cursor, String columnName){
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getInt(columnIndex);
    }

    private String getStringFromColumName(Cursor cursor, String columnName){
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }

    private long makeSongId(Song song, SQLiteDatabase database){
        ContentValues mediaValues = new ContentValues();
        mediaValues.put(MediaContract.SongAttributes.C, song.getFormat());
        mediaValues.put(MediaContract.SongAttributes.COLUMN_MEDIA_PATH, song.getPath());
        mediaValues.put(MediaContract.SongAttributes.COLUMN_MEDIA_SIZE, song.getSize());
        mediaValues.put(MediaContract.SongAttributes.COLUMN_MEDIA_FORMAT, song.getFormat());

        return database.insert(MediaContract.SongAttributes.SONG_TABLE, null, mediaValues);
    }
    */
    //------End Helper Methods------//
}
