package com.flynn.jake.freeformplayer.models.VideoEntities;

import com.flynn.jake.freeformplayer.models.Media;

/**
 * Created by countrynerd on 11/8/15.
 */
public class Video extends Media {

    private static String mName;
    private static int mLength;

    public Video(String type, String path, int size, String format, String name, int length) {
        super(type, path, size, format);
        mName = name;
        mLength = length;
    }

    public static String getName() {
        return mName;
    }

    public static void setName(String name) {
        mName = name;
    }

    public static int getLength() {
        return mLength;
    }

    public static void setLength(int length) {
        mLength = length;
    }

}