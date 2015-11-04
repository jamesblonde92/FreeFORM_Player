package com.flynn.jake.freeformplayer;

/**
 * Created by jakeflynn on 11/4/15.
 */
public class DrawerItem {

    private int Id;
    private String Title;

    public DrawerItem(int I, String T){
        this.Id = I;
        this.Title = T;

    }

    public int getId(){
        return this.Id;
    }

    public String getTitle(){
        return this.Title;
    }

}
