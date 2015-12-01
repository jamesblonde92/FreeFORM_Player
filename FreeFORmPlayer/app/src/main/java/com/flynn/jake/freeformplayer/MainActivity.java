package com.flynn.jake.freeformplayer;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener,
        MediaPlayer.OnPreparedListener {


    //----------Variables----------//

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ImageView mImageView;
    ArrayList<DrawerItem> mDrawerItemArrayList;
    ActionBarDrawerToggle mDrawerToggle;
    private ArrayList<String> testList = new ArrayList<String>();
    MediaPlayer player;

    private static final String OPEN_DRAWER = "Drawer closed";
    private static final String CLOSED_DRAWER = "Drawer open";

    //----------EndVariables----------//



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //------------Initialize-------------

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        ListView listView = (ListView) findViewById(R.id.listView_songs);
        listView.setOnItemClickListener(this);

        mDrawerItemArrayList = new ArrayList<DrawerItem>();
        mDrawerItemArrayList.add(new DrawerItem(R.drawable.all_songs, " All Songs"));
        mDrawerItemArrayList.add(new DrawerItem(R.drawable.artist, " Artist"));
        mDrawerItemArrayList.add(new DrawerItem(R.drawable.now_playing, " Now Playing"));
        mDrawerItemArrayList.add(new DrawerItem(R.drawable.settings, " Settings"));
        mDrawerItemArrayList.trimToSize();

        //-------------End initialize--------------



        //---------------------External storage search---------------------
        //DO NOT CHANGE PLEASE
        ContentResolver contentResolver = getContentResolver();
        Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        if (cursor == null) {
            // query failed, handle error.
            Toast.makeText(this, "query failed", Toast.LENGTH_SHORT).show();
        }
        else if (!cursor.moveToFirst()) {
            // no media on the device
            Toast.makeText(this, "no media", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "entered else for external search", Toast.LENGTH_SHORT).show();
            int titleColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
            do {

                long thisId = cursor.getLong(idColumn);
                String thisTitle = cursor.getString(titleColumn);
                testList.add(thisTitle + " : " + thisId);
            } while (cursor.moveToNext());
        }
        //------------------------End external storage search---------------------



        //-------------------------Internal storage search---------------------
        //DO NOT CHANGE PLEASE
        contentResolver = getContentResolver();
        uri = android.provider.MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
        cursor = contentResolver.query(uri, null, null, null, null);

        if (cursor == null) {
            // query failed, handle error.
            Toast.makeText(this, "query failed", Toast.LENGTH_SHORT).show();
        }
        else if (!cursor.moveToFirst()) {
            // no media on the device
            Toast.makeText(this, "no media", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "entered else for internal search", Toast.LENGTH_SHORT).show();
            int titleColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
            do {
                long thisId = cursor.getLong(idColumn);
                String thisTitle = cursor.getString(titleColumn);
                testList.add(thisTitle + " : " + thisId);
            } while (cursor.moveToNext());
        }
        //-----------------------End internal storage search---------------------


        //------------list view adapter------------------
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                testList );
        listView.setAdapter(arrayAdapter);
        //-----------end list view adapter-----------------



        //-----------Media Player stuff------------------
        player = new MediaPlayer();
        player.setOnPreparedListener(this);



        //------------------end media player stuff-----------------------------


        //-------------------Nav drawer------------------------
        //DO NOT CHANGE PLEASE
        nav_item_adapter adapter = new nav_item_adapter(MainActivity.this, R.id.drawer_layout, mDrawerItemArrayList);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(this);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open_drawer, R.string.closed_drawer){

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(OPEN_DRAWER);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(CLOSED_DRAWER);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //-----------------End nav drawer----------------------

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        //start playback
        mp.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, "Made by Jake Flynn, Shane Olson, and James Reisenauer", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this, mDrawerItemArrayList.get(position).getTitle(), Toast.LENGTH_LONG).show();
        Toast.makeText(this, "position is:" + position + " And content is: " + testList.get(position), Toast.LENGTH_LONG).show();

        player.stop();
        player.reset();
        String[] tempHolder = testList.get(position).split(":");
        long idNumber = Integer.parseInt(tempHolder[tempHolder.length - 1].trim());

        Uri trackUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                idNumber);
        try {
            Toast.makeText(MainActivity.this, "entered try", Toast.LENGTH_SHORT).show();
            player.setDataSource(getApplicationContext(), trackUri);
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "entered catch", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        try {
            Toast.makeText(MainActivity.this, "prepare try", Toast.LENGTH_SHORT).show();
            player.prepare();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "prepare catch", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
}


