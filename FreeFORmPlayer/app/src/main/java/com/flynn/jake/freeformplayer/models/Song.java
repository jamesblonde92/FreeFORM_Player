package com.flynn.jake.freeformplayer.models;

import android.net.Uri;

/**
 * Created by countrynerd on 12/1/15.
 */
public class Song {

    private long mSongID;
    private String mName;
    private String mArtist;
    private String mAlbum;
    private String mGenre;
    private int mYear;
    private Uri mUri;

    public Song(long songID, String name, String genre, String artist, String album,  int year, Uri uri){
        mSongID = songID;
        mName = name;
        mArtist = artist;
        mAlbum = album;
        mGenre = genre;
        mYear = year;
        mUri = uri;
    }

    public int getSongID() {
        return (int)mSongID;
    }

    public void setSongID(int songID) {
        mSongID = songID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public void setAlbum(String album) {
        mAlbum = album;
    }

    public void setSongID(long songID) {
        mSongID = songID;
    }

    public Uri getUri() {
        return mUri;
    }

    public void setUri(Uri uri) {
        mUri = uri;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        mYear = year;
    }

    public String toString(){
        return mName;
    }

}
