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

    public Song(long songID, String name, String artist, String album, String genre, int year, Uri uri){
        mSongID = songID;
        mName = name;
        mArtist = artist;
        mAlbum = album;
        mGenre = genre;
        mYear = year;
        mUri = uri;
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

    public Song setSongID(long songID) {
        mSongID = songID;
        return this;
    }

    public Uri getUri() {
        return mUri;
    }

    public Song setUri(Uri uri) {
        mUri = uri;
        return this;
    }

    public String getGenre() {
        return mGenre;
    }

    public Song setGenre(String genre) {
        mGenre = genre;
        return this;
    }

    public int getYear() {
        return mYear;
    }

    public Song setYear(int year) {
        mYear = year;
        return this;
    }

    public String toString(){
        return mName;
    }

}
