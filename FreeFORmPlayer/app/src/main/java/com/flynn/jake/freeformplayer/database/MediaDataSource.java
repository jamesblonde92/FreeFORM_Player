package com.flynn.jake.freeformplayer.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.flynn.jake.freeformplayer.models.Album;
import com.flynn.jake.freeformplayer.models.Media;
import com.flynn.jake.freeformplayer.models.SongEntities.*;
import com.flynn.jake.freeformplayer.models.VideoEntities.*;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by countrynerd on 11/8/15.
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

        //SQLiteDatabase database = mMediaSQLightHelper.getReadableDatabase();
        //database.close();
    }

    public void open()  {
        mDatabase =  mMediaSQLightHelper.getWritableDatabase();
    }

    public void close(){
        mDatabase.close();
    }

    //------End Constructors------//

    //------ISUD Methods------//

    public void insertVideo(Video video){
        SQLiteDatabase database = mDatabase;
        database.beginTransaction();        // used for thread safe code

        // Implementations details
        long mediaId = makeMediaId(video, database);

        ContentValues videoValues = new ContentValues();
        videoValues.put(MediaContract.Videos.COLUMN_VIDEO_NAME, Video.getName());
        videoValues.put(MediaContract.Videos.COLUMN_VIDEO_LENGTH, Video.getLength());
        videoValues.put(MediaContract.Videos.COLUMN_FOREIGN_KEY_MEDIA, mediaId);
        long videoID = database.insert(MediaContract.Videos.VIDEO_TABLE, null, videoValues);

        database.setTransactionSuccessful();
        database.endTransaction();
        close();
    }

    public void insertSong(Song song){
        SQLiteDatabase database = mDatabase;
        database.beginTransaction();        // used for thread safe code

        // Implementations details
        long mediaId = makeMediaId(song, database);
        ContentValues songValues = new ContentValues();
        songValues.put(MediaContract.SongAttributes.COLUMN_SONGS_NAME, Song.getName());
        songValues.put(MediaContract.SongAttributes.COLUMN_SONGS_LENGTH, Song.getLength());
        songValues.put(MediaContract.SongAttributes.COLUMN_SONGS_ARTIST, Song.getArtistName());
        songValues.put(MediaContract.SongAttributes.COLUMN_SONGS_GENRE, Song.getGenreName());
        songValues.put(MediaContract.SongAttributes.COLUMN_FOREIGN_KEY_MEDIA, mediaId);
        long songID = database.insert(MediaContract.SongAttributes.SONG_TABLE, null, songValues);

        ContentValues albumValues = new ContentValues();
        albumValues.put(MediaContract.Albums.COLUMN_ALBUM_NAME, Album.getAlbumName());
        albumValues.put(MediaContract.Albums.COLUMN_ALBUM_ARTIST, Album.getAlbumArtist());
        albumValues.put(MediaContract.Albums.COLUMN_ALBUM_YEAR, Album.getAlbumYear());
        albumValues.put(MediaContract.Albums.COLUMN_FOREIGN_KEY_SONGS, songID);
        long albumID = database.insert(MediaContract.Albums.ALBUM_TABLE, null, albumValues);

        ContentValues artValues = new ContentValues();
        artValues.put(MediaContract.SongArt.COLUMN_ART_HEIGHT, SongArt.getHeight());
        artValues.put(MediaContract.SongArt.COLUMN_ART_WIDTH, SongArt.getWidth());
        artValues.put(MediaContract.SongArt.COLUMN_FOREIGN_KEY_SONGS, songID);
        long artID = database.insert(MediaContract.SongArt.COLUMN_FOREIGN_KEY_SONGS, null, artValues);

        database.setTransactionSuccessful();
        database.endTransaction();
        close();
    }

    public ArrayList<Media> selectVideo(){
        return  null;
    }

    public ArrayList<Song> selectSong(){
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
        ArrayList<Song> songs = new ArrayList<Song>();
        if (cursor.moveToFirst()){
            do{
                Song song = new Song("Song", getStringFromColumName(cursor, MediaContract.MediaInfo.COLUMN_MEDIA_PATH),
                        getIntFromColumName(cursor, MediaContract.MediaInfo.COLUMN_MEDIA_SIZE),
                        getStringFromColumName(cursor, MediaContract.MediaInfo.COLUMN_MEDIA_FORMAT),
                        getStringFromColumName(cursor, MediaContract.SongAttributes.COLUMN_SONGS_NAME),
                        getStringFromColumName(cursor, MediaContract.SongAttributes.COLUMN_SONGS_GENRE),
                        getStringFromColumName(cursor, MediaContract.SongAttributes.COLUMN_SONGS_ARTIST),
                        getIntFromColumName(cursor, MediaContract.SongAttributes.COLUMN_SONGS_LENGTH));
                songs.add(song);
            }while (cursor.moveToNext());
        }

        
        cursorMedia.close();
        cursor.close();
        database.close();
        return songs;
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

    private long makeMediaId(Media media, SQLiteDatabase database){
        ContentValues mediaValues = new ContentValues();
        mediaValues.put(MediaContract.MediaInfo.COLUMN_MEDIA_FORMAT, media.getFormat());
        mediaValues.put(MediaContract.MediaInfo.COLUMN_MEDIA_PATH, media.getPath());
        mediaValues.put(MediaContract.MediaInfo.COLUMN_MEDIA_SIZE, media.getSize());
        mediaValues.put(MediaContract.MediaInfo.COLUMN_MEDIA_FORMAT, media.getFormat());

        return database.insert(MediaContract.SongAttributes.SONG_TABLE, null, mediaValues);
    }
    //------End Helper Methods------//
}
