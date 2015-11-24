package com.flynn.jake.freeformplayer.models;

/**
 * Created by countrynerd on 11/8/15.
 */
public class Album {
    private static  String mAlbumName;
    private static int mAlbumYear;
    private static String mAlbumArtist;

    public Album(String albumName, int albumYear, String albumArtist){
        mAlbumName = albumName;
        mAlbumYear = albumYear;
        mAlbumArtist = albumArtist;
    }

    public static String getAlbumName() {
        return mAlbumName;
    }

    public static void setAlbumName(String albumName) {
        mAlbumName = albumName;
    }

    public static int getAlbumYear() {
        return mAlbumYear;
    }

    public static void setAlbumYear(int albumYear) {
        mAlbumYear = albumYear;
    }

    public static String getAlbumArtist() {
        return mAlbumArtist;
    }

    public static void setAlbumArtist(String albumArtist) {
        mAlbumArtist = albumArtist;
    }
}
