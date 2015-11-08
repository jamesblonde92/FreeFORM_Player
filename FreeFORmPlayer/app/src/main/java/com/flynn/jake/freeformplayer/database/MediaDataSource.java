package com.flynn.jake.freeformplayer.database;

import android.content.Context;

/**
 * Created by countrynerd on 11/8/15.
 */
public class MediaDataSource {

    //-------Variables------//

    private Context mContext;
    private MediaSQLightHelper mMediaSQLightHelper;

    //-------End Variables------//

    //------Constructors------//

    public MediaDataSource(Context context){
        mContext = context;
        mMediaSQLightHelper = new MediaSQLightHelper(context);
    }

    //------End Constructors------//
}
