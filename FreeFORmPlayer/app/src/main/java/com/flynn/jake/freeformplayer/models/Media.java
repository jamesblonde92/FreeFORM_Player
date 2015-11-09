package com.flynn.jake.freeformplayer.models;

import java.io.Serializable;

/**
 * Created by countrynerd on 11/8/15.
 */
public abstract class Media implements Serializable {
    public static String mType;
    public static String mPath;
    public static int mSize;
    public static String mFormat;

    public Media (String type, String path, int size, String format){
        mType = type;
        mPath = path;
        mSize = size;
        mFormat = format;
    }

    public static String getType() {
        return mType;
    }

    public static void setType(String type) {
        mType = type;
    }

    public static String getPath() {
        return mPath;
    }

    public static void setPath(String path) {
        mPath = path;
    }

    public static int getSize() {
        return mSize;
    }

    public static void setSize(int size) {
        mSize = size;
    }

    public static String getFormat() {
        return mFormat;
    }

    public static void setFormat(String format) {
        mFormat = format;
    }
}
