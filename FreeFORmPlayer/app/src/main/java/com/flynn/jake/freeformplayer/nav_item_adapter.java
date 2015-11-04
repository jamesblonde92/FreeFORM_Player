package com.flynn.jake.freeformplayer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jakeflynn on 11/4/15.
 */
public class nav_item_adapter extends ArrayAdapter<DrawerItem> {

    private Context context;
    private int resId;
    private ArrayList<DrawerItem> drawerItems = new ArrayList<DrawerItem>();

    public nav_item_adapter(Context ctx, int ID, ArrayList<DrawerItem> dItems){
        super(ctx, ID, dItems);
        context = ctx;
        resId = ID;
        drawerItems = dItems;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView != null){
            return convertView;
        }
        else{
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            View v = inflater.inflate(R.layout.nav_drawer_row, null);

            ImageView imageView = (ImageView) v.findViewById(R.id.imageView_icon);
            TextView textView = (TextView) v.findViewById(R.id.textView_Drawer_Name);

            DrawerItem choice = drawerItems.get(position);

            imageView.setImageResource(R.drawable.logo);
            textView.setText(choice.getTitle());

            return v;

        }

    }

}
