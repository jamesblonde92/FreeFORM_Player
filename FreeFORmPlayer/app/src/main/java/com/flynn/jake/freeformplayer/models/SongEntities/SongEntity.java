package com.flynn.jake.freeformplayer.models.SongEntities;

import com.flynn.jake.freeformplayer.models.Album;
import com.flynn.jake.freeformplayer.models.Media;

/**
 * Created by countrynerd on 11/8/15.
 */
public class SongEntity extends Media {
    private static String mName;
    private static String mGenreName;
    private static String mArtistName;
    //private static Album mAlbum;
    //private static SongArt mArt;
    private static int mLength;

    public SongEntity(String type, String path, int size, String format, String name, String genre, String artist, int length){
        super(type, path, size, format);
        mName = name;
        mGenreName = genre;
        mArtistName = artist;
        mLength = length;
    }

    public static String getName() {
        return mName;
    }

    public static void setName(String name) {
        mName = name;
    }

    public static String getGenreName() {
        return mGenreName;
    }

    public static void setGenreName(String genreName) {
        mGenreName = genreName;
    }

    public static String getArtistName() {
        return mArtistName;
    }

    public static void setArtistName(String artistName) {
        mArtistName = artistName;
    }

    public static int getLength() {
        return mLength;
    }

    public static void setLength(int length) {
        mLength = length;
    }
}
