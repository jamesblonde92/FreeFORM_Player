package com.flynn.jake.freeformplayer.UnitTesting;

import android.net.Uri;

import com.flynn.jake.freeformplayer.Utilities;
import com.flynn.jake.freeformplayer.models.Song;


/**
 * Created by Shane on 12/8/2015.
 */
public class Tester {

    public static void main(String args[]){

        System.out.println("==============================================================================\n");

        if (testSongObject()){
            System.out.println("Song object is storing data and functioning according to expectations.");
        }
        else{
            System.out.println("Song object is not functioning properly");
        }

        System.out.println("==============================================================================\n");


        if (testmilliSecondsToTimer(120000)){
            System.out.println("testmilliSecondsToTimer method converts time properly");
        }
        else{
            System.out.println("Song object is not functioning properly");
        }
    }

    public static boolean testmilliSecondsToTimer(long pmilliseconds){
        if(Utilities.milliSecondsToTimer(pmilliseconds).equals("2:00"))
            return true;
        else
            return false;
    }


    public static boolean testSongObject(){

        Song mySong = new Song((long)100, "Stitches", "Pop", "Shawn Mendes", "Unknown", 2015, null);
        String mySongName = mySong.getName();
        long mySongID =  mySong.getSongID();
        String mySongArtist = mySong.getArtist();
        String mySongAlbum = mySong.getAlbum();
        int mySongYear = mySong.getYear();

        if(mySongName != null && mySongID != 0 && mySongArtist != null && mySongAlbum != null && mySongYear != 0)
            return true;
        else
            return false;
    }
}
