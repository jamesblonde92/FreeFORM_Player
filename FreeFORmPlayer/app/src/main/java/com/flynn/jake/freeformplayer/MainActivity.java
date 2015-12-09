package com.flynn.jake.freeformplayer;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import com.flynn.jake.freeformplayer.database.MediaDataSource;
import com.flynn.jake.freeformplayer.models.Song;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener,
        MediaPlayer.OnPreparedListener {


    //----------Variables----------//

    private Context mActivity;
    private ListView mListView;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ImageView mImageView;
    private TextView mPlayingSong;
    ArrayList<DrawerItem> mDrawerItemArrayList;
    ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout mPlayControls;
    private ArrayList<Song> songList = new ArrayList<>();
    private MediaPlayer mPlayer;
    private String OPEN_DRAWER = "All Songs";
    private String CLOSED_DRAWER = "Menu";

    private final int SONGS_DISPLAYED = 0;
    private final int ARTISTS_DISPLAYED = 1;
    private final int GENRES_DISPLAYED = 2;
    private final int SEEK_BAR_TOUCH = 3;

    private ImageButton mPlay;
    private ImageButton mPrev;
    private ImageButton mNext;
    private ImageButton mLoop;
    private boolean isLooping = false;

    private TextView mSeekTimer;
    private TextView mDuration;

    private SeekBar mSeekBar;
    private Handler mHandler = new Handler();

    protected MediaDataSource mDataSource;
    private int mPrevPosition;
    private int mNextPosition;
    private int mCategoryDisplayed = SONGS_DISPLAYED;
    private int mSeekBarCategoryDisplayedTemp = SONGS_DISPLAYED;

    private String mDrawerTitle;
    private String mTitle;



    private HashMap mGenreMap = new HashMap<String, String>();
    private String mSubCatagory = "All Songs";

    //----------EndVariables----------//


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataSource = new MediaDataSource(MainActivity.this);

        setContentView(R.layout.activity_main);

        //------------Initialize-------------
        mActivity = this.getActivity();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mPlayControls = (RelativeLayout) findViewById(R.id.playControlsBox);
        mListView = (ListView) findViewById(R.id.listView_songs);
        mPlayingSong = (TextView) findViewById(R.id.playingSongText);
        mListView.setOnItemClickListener(this);

        mPlay = (ImageButton) findViewById(R.id.button_play);
        mNext = (ImageButton) findViewById(R.id.button_next);
        mPrev = (ImageButton) findViewById(R.id.button_prev);
        mLoop = (ImageButton) findViewById(R.id.loopButtonOff);

        mSeekTimer = (TextView) findViewById(R.id.seekTimer);
        mDuration = (TextView) findViewById(R.id.durationText);

        mDrawerItemArrayList = new ArrayList<DrawerItem>();
        mDrawerItemArrayList.add(new DrawerItem(R.drawable.all_songs, " All Songs"));
        mDrawerItemArrayList.add(new DrawerItem(R.drawable.artist, " Artist"));
        mDrawerItemArrayList.add(new DrawerItem(R.drawable.now_playing, " Genre"));
        mDrawerItemArrayList.add(new DrawerItem(R.drawable.settings, " Settings"));
        mDrawerItemArrayList.trimToSize();

        //Anonymous inner class dealing with opening and closing the navDrawer
        mDrawerTitle = "Select an option:";
        mTitle = "Closed";
        mTitle = mDrawerTitle =(String) getTitle();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setTitle("Select an option:");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle.syncState();

        //-------------End initialize--------------



        //-------------SeekBar--------------------
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mSeekBar.setProgress(0);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(fromUser) {
                    mPlayer.seekTo(mPlayer.getDuration() * progress / 100);
                    mSeekTimer.setText("" + Utilities.milliSecondsToTimer(mPlayer.getCurrentPosition()));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        //-------------EndSeekBar------------------


        //---------------------External storage search---------------------
        genereSearch();

        //mDataSource.removeAllDataFromTable();

        Uri intUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        updateList(intUri);

        Uri extUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
        updateList(extUri);

        populateSongListWithSongs();

        //------------list view adapter------------------

        ArrayAdapter<Song> songArrayAdapter = new ArrayAdapter<Song>(
                this,
                android.R.layout.simple_list_item_1,
                songList);
        mListView.setAdapter(songArrayAdapter);

        //-----------end list view adapter-----------------

        //-----------Media Player Stuff------------------

        mPlayer = new MediaPlayer();
        mPlayer.setOnPreparedListener(this);

        //------------------End Media Player Stuff-----------------------------


        //-------------------Nav drawer------------------------
        //DO NOT CHANGE PLEASE
        nav_item_adapter adapter = new nav_item_adapter(MainActivity.this, R.id.drawer_layout, mDrawerItemArrayList);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        populateSongListWithSongs();
                        mCategoryDisplayed = SONGS_DISPLAYED;
                        OPEN_DRAWER = "All Songs";
                        mSubCatagory = "All Songs";
                        mDrawerLayout.closeDrawers();
                        break;
                    case 1:
                        ////ToDo:
                        //Add in logic to only display artist names in the list
                        populateSongListWithCategory("ArtistName");
                        OPEN_DRAWER = "Artists";
                        mCategoryDisplayed = ARTISTS_DISPLAYED;
                        mDrawerLayout.closeDrawers();

                        break;
                    case 2:
                        populateSongListWithCategory("GenreName");
                        OPEN_DRAWER = "Genres";
                        mCategoryDisplayed = GENRES_DISPLAYED;
                        mDrawerLayout.closeDrawers();
                        break;
                    case 3:
                        Toast.makeText(MainActivity.this, "nav drawer item 4", Toast.LENGTH_SHORT).show();
                        mDrawerLayout.closeDrawers();
                        break;
                }
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open_drawer, R.string.closed_drawer) {

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
        mDrawerToggle.syncState();

        //-----------------End nav drawer----------------------


        //-----------------ButtonListeners---------------------

        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                    mPlay.setAlpha(new Float(1.0));
                } else {
                    mPlayer.start();
                    mPlay.setAlpha(new Float(.0));
                }
                mListView.smoothScrollToPosition(mNextPosition-1);
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.stop();
                mPlayer.reset();
                //mPlayer = new MediaPlayer();
                if (!((mNextPosition+1) >= (songList.size()))) {
                    setMediaPlayer(mPlayer, mNextPosition);
                    mNextPosition++;
                    mPrevPosition++;
                }

                if (mNextPosition < 0){
                    mNextPosition = 0;
                    mPrevPosition = songList.size()-1;
                    setMediaPlayer(mPlayer, mNextPosition);
                    mNextPosition++;
                    mPrevPosition = 0;
                }
                    mPlayer.start();
                mListView.smoothScrollToPosition(mNextPosition-1);
            }
        });

        mPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.stop();
                mPlayer.reset();
                //mPlayer = new MediaPlayer();
                if (!(mPrevPosition < 0) && !(mPrevPosition > songList.size())) {
                    setMediaPlayer(mPlayer, mPrevPosition);
                    mNextPosition--;
                    mPrevPosition--;
                }

                if (mPrevPosition > (songList.size()))
                {
                    mPrevPosition = songList.size()-1;
                    mNextPosition = 1;
                    setMediaPlayer(mPlayer, mPrevPosition);
                }

                mPlayer.start();
                mListView.smoothScrollToPosition(mNextPosition-1);

            }
        });

        mLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLooping)
                {
                    isLooping = false;
                    mLoop.setAlpha(new Float(1.0));
                }else {
                    isLooping = true;
                    mLoop.setAlpha(new Float(.0));
                }
            }
        });


        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {

                mPlay.setAlpha(new Float(.0));
                mPlayer.stop();
                mPlayer.reset();
                if (isLooping) {
                    setMediaPlayer(mPlayer, mNextPosition-1);
                }else {
                    setMediaPlayer(mPlayer, mNextPosition);
                    mNextPosition++;
                    mPrevPosition++;
                }
                mPlayer.start();
                mListView.smoothScrollToPosition(mNextPosition-1);

                if(mNextPosition == songList.size()){
                    mNextPosition = 0;
                }else if (mNextPosition < 0){
                    mNextPosition = songList.size()-1;
                }else{
                    //Log.i("Issue in: ", "onCompletion Previous");
                }


                if(mPrevPosition == songList.size()){
                    mPrevPosition = 0;
                }else if (mPrevPosition < 0)
                {
                    mPrevPosition = songList.size()-1;
                }
                else {
                    //Log.i("Issue in: ", "onCompletion Previous");
                }
            }
        });

        //-----------------EndButtonListeners------------------

    }

    private void genereSearch()
    {
        int index;
        long genreId;
        Uri uri;
        Cursor genrecursor;
        Cursor tempcursor;
        String[] proj1 = {MediaStore.Audio.Genres.NAME, MediaStore.Audio.Genres._ID};
        String[] proj2 = {MediaStore.Audio.Media.TITLE};
        String thisGenre = "";

        genrecursor = managedQuery(MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI, proj1, null, null, null);
        if (genrecursor.moveToFirst()) {
            do {
                index = genrecursor.getColumnIndexOrThrow(MediaStore.Audio.Genres.NAME);
                thisGenre = genrecursor.getString(index);
                Log.i("Genre name", thisGenre);

                index = genrecursor.getColumnIndexOrThrow(MediaStore.Audio.Genres._ID);
                genreId = Long.parseLong(genrecursor.getString(index));
                uri = MediaStore.Audio.Genres.Members.getContentUri("external", genreId);

                tempcursor = managedQuery(uri, proj2, null,null,null);
                //Log.i("Tag-Number of songs for this genre", tempcursor.getCount() + "");
                if (tempcursor.moveToFirst()) {
                    do {
                        index = tempcursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
                        mGenreMap.put(tempcursor.getString(index), thisGenre);
                        Log.i("Song name", tempcursor.getString(index));
                    } while(tempcursor.moveToNext());
                }
            } while(genrecursor.moveToNext());
        }
    }

    private void updateList(Uri inURI) {
        //DO NOT CHANGE PLEASE
        ContentResolver contentResolver = getContentResolver();
        MediaDataSource mediaDataSource = new MediaDataSource(this);
        Uri uri = inURI;
        String thisGenre = null;

        try {
            Cursor cursor = contentResolver.query(uri, null, null, null, null);

            if (cursor == null) {
                // query failed, handle error.
                //Toast.makeText(this, "query failed", Toast.LENGTH_SHORT).show();
            } else if (!cursor.moveToFirst()) {
                // no media on the device
                //Toast.makeText(this, "no media", Toast.LENGTH_SHORT).show();
            } else {

                //Toast.makeText(this, "entered else for internal search", Toast.LENGTH_SHORT).show();
                int titleColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
                int idColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
                int artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int albumColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                int yearColumn = cursor.getColumnIndex(MediaStore.Audio.Media.YEAR);

                do {
                    //Toast.makeText(this, MediaStore.Audio.Media.IS_MUSIC, Toast.LENGTH_SHORT).show();
                    long thisId = cursor.getLong(idColumn);
                    String thisTitle = cursor.getString(titleColumn);
                    String thisArtist = cursor.getString(artistColumn);
                    String thisAlbum = cursor.getString(albumColumn);
                    int thisYear = cursor.getInt(yearColumn);
                    if (mGenreMap.containsKey(thisTitle))
                        thisGenre = (String) mGenreMap.get(thisTitle);
                    else
                        thisGenre = "Unknown";
                    Song newSong = new Song(thisId, thisTitle, thisGenre, thisArtist, thisAlbum,  thisYear, inURI);
                    songList.add(newSong);
                    mediaDataSource.addSong(newSong);
                } while (cursor.moveToNext());

            }
            cursor.close();
            //-----------------------End storage search---------------------
        } catch (SecurityException e) {
            //Toast.makeText(MainActivity.this, "Security Exception", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onResume() {
        super.onResume();
    }

    private void populateSongListWithSongs(){
        MediaDataSource dataSource = new MediaDataSource(this.getApplicationContext());

        songList = dataSource.readSong();

        ArrayAdapter<Song> songArrayAdapter = new ArrayAdapter<Song>(
                this,
                android.R.layout.simple_list_item_1,
                songList);
        mListView.setAdapter(songArrayAdapter);
    }

    private void populateSongListWithCategory(String categoryKeyword){
        MediaDataSource dataSource = new MediaDataSource(this.getApplicationContext());

        ArrayList<String> tempArtistList = dataSource.readCategory(categoryKeyword);


        ArrayAdapter<String> songArrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                tempArtistList);
        mListView.setAdapter(songArrayAdapter);
    }

    private void populateSongListWithSongsFromCategory(String category, String keyword){

        MediaDataSource dataSource = new MediaDataSource(this.getApplicationContext());

        songList = dataSource.readSongFromCategory(category, keyword);

        ArrayAdapter<Song> songArrayAdapter = new ArrayAdapter<Song>(
                this,
                android.R.layout.simple_list_item_1,
                songList);
        mListView.setAdapter(songArrayAdapter);
    }

    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try{
            mPlayer.stop();
            mPlayer.release();
            mDataSource.close();
        }catch (Exception e)
        {
            Toast.makeText(MainActivity.this, "Didnt destroy", Toast.LENGTH_SHORT).show();
        }
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
        if(mp != null) {
            mp.start();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item)) return true;

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


        if(mCategoryDisplayed == ARTISTS_DISPLAYED){
            //// TODO:
            //Add in logic to handle if a artist is clicked
            String artistNameKeyword=(String)parent.getItemAtPosition(position);

            populateSongListWithSongsFromCategory("ArtistName", "'"+ artistNameKeyword +"'");
            getSupportActionBar().setTitle(artistNameKeyword);
            mCategoryDisplayed = SONGS_DISPLAYED;
        }

        else if(mCategoryDisplayed == GENRES_DISPLAYED){
            //// TODO:
            //Add in logic to handle if a artist is clicked
            String genreNameKeyword = (String) parent.getItemAtPosition(position);

            populateSongListWithSongsFromCategory("GenreName", "'" + genreNameKeyword + "'");
            getSupportActionBar().setTitle(genreNameKeyword);
            mCategoryDisplayed = SONGS_DISPLAYED;
        }

        else if(view.getId() == R.id.seekBar){

        }


        else {
            mPlayer.stop();
            mPlayer.reset();
            //mPlay.setText(R.string.pause);
            mPlay.setAlpha((float) .0);

            if (!(position + 1 > songList.size() - 1))
                mNextPosition = position + 1;
            else
                mNextPosition = 0;

            if (!(position < 0))
                mPrevPosition = position - 1;
            else
                mPrevPosition = songList.size() - 1;

            long idNumber = songList.get(position).getSongID();

            setSongName(position);
            setMediaPlayer(mPlayer, position);

            mPlayer.start();
        }

    }

    public void setSongName(int position){
        if(songList.get(position)!= null){
            String songName = songList.get(position).getName();
            if (!(songList.get(position).getArtist().equalsIgnoreCase("<unknown>"))) {
                songName = songName + "\n" + songList.get(position).getArtist();
            }
            mPlayingSong.setText(songName);
        }
    }


    private void setMediaPlayer(MediaPlayer player, int position){
        long idNumber = songList.get(position).getSongID();

        Uri trackUri = null;
        trackUri = ContentUris.withAppendedId(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                idNumber);


        if (trackUri == null) {
            //Toast.makeText(MainActivity.this, "trackUri is null", Toast.LENGTH_SHORT);
        } else {
            try {
                //  Toast.makeText(MainActivity.this, "entered try", Toast.LENGTH_SHORT).show();
                player.setDataSource(getApplicationContext(), trackUri);
                player.prepare();
            } catch (Exception e) {
                try {
                    trackUri = ContentUris.withAppendedId(
                            MediaStore.Audio.Media.INTERNAL_CONTENT_URI,
                            idNumber);
                    player.setDataSource(getApplicationContext(), trackUri);
                    player.prepare();

                } catch (Exception t) {
                    //Toast.makeText(MainActivity.this, "prepare catch", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
        setSongName(position);
        mSeekBar.setProgress(0);
        mDuration.setText("" + Utilities.milliSecondsToTimer(player.getDuration()));
        updateProgressBar();
    }

    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 10);
    }


    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mPlayer.getDuration();
            long currentDuration = mPlayer.getCurrentPosition();

            mSeekTimer.setText(""+Utilities.milliSecondsToTimer(currentDuration));

            int progress = (int)(Utilities.getProgressPercentage(currentDuration, totalDuration));
            mSeekBar.setProgress(progress);

            mHandler.postDelayed(this, 100);
        }
    };

    public Context getActivity() {
        return mActivity;
    }
}









