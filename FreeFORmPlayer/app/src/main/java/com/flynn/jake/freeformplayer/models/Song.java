package com.flynn.jake.freeformplayer.models;

/**
 * Created by countrynerd on 12/1/15.
 */
public class Song {

    private long mSongID;
    private String mName;
    private String mArtist;
    private String mAlbum;

    public Song(long songID, String name, String artist, String album){
        mSongID = songID;
        mName = name;
        mArtist = artist;
        mAlbum = album;
    }

    public long getSongID() {
        return mSongID;
    }

    public Song setSongID(int songID) {
        mSongID = songID;
        return this;
    }

    public String getName() {
        return mName;
    }

    public Song setName(String name) {
        mName = name;
        return this;
    }

    public String getArtist() {
        return mArtist;
    }

    public Song setArtist(String artist) {
        mArtist = artist;
        return this;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public Song setAlbum(String album) {
        mAlbum = album;
        return this;
    }
}
