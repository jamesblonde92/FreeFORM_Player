package com.flynn.jake.freeformplayer.models.SongEntities;

/**
 * Created by countrynerd on 11/8/15.
 */
public class SongArt {
    private static int mHeight;
    private static int mWidth;

    public SongArt(int height, int width){
        mHeight = height;
        mWidth = width;
    }

    public static int getmHeight() {
        return mHeight;
    }

    public static void setHeight(int height) {
        mHeight = height;
    }

    public static int getWidth() {
        return mWidth;
    }

    public static void setWidth(int width) {
        mWidth = width;
    }
}
