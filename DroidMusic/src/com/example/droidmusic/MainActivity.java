package com.example.droidmusic;


import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {
	public final static String EXTRA_ARTIST_SEARCH = "com.example.droidmusic.ARTIST_SEARCH";
	private SensorManager sensorManager;
	private long lastUpdate;
	public static final String SERVICECMD = "com.android.music.musicservicecommand";
	public static final String CMDNAME = "command";
	public static final String CMDTOGGLEPAUSE = "togglepause";
	public static final String CMDSTOP = "stop";
	public static final String CMDPAUSE = "pause";
	public static final String CMDPREVIOUS = "previous";
	public static final String CMDNEXT = "next";
	private String currentArtist = "";
	private String currentSong = "";
	private String currentAlbum = "";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.setThreadPolicy(
                new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        //Get Current Song Settings
        IntentFilter iF = new IntentFilter();
        iF.addAction("com.android.music.metachanged");
        iF.addAction("com.android.music.playstatechanged");
        iF.addAction("com.android.music.playbackcomplete");
        iF.addAction("com.android.music.queuechanged");
        registerReceiver(mReceiver, iF);
        
        //Sensor settings
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();
        
        final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				//Log.e("Reading", wsmusic.getRequest());
				next(v);
			}
        });
    }
    
    public void next(View v)
    {
    	Intent intent = new Intent(this,ArtistList.class); 
    	final EditText edit1 = (EditText) findViewById(R.id.editText1);
    	String artist = edit1.getText().toString().replaceAll(" ", "%20");
    	intent.putExtra(EXTRA_ARTIST_SEARCH, artist);
		startActivity(intent); 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
		      getAccelerometer(event);
		    }
		
	}
	
	 private void getAccelerometer(SensorEvent event) {
		    float[] values = event.values;
		    // Movement
		    float x = values[0];
		    float y = values[1];
		    float z = values[2];

		    float accelationSquareRoot = (x * x + y * y + z * z)
		        / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
		    long actualTime = System.currentTimeMillis();
		    if (accelationSquareRoot >= 2) //
		    {
		      if (actualTime - lastUpdate < 200) {
		        return;
		      }
		      lastUpdate = actualTime;
		      if (currentSong.equals(""))
		      {
		    	  Toast.makeText(this, "There is no song playing right now", Toast.LENGTH_SHORT)
		          .show();
		      }
		      else
		      {
		    	  Toast.makeText(this, "\""+currentSong+"\" by "+currentArtist+" from \""+currentAlbum+"\"", Toast.LENGTH_SHORT)
		          .show();
		    	  final EditText edit1 = (EditText) findViewById(R.id.editText1);
		    	  edit1.setText(currentArtist);
		      }
		      
		    }
		  }
	 
	 @Override
	  protected void onResume() {
	    super.onResume();
	    // register this class as a listener for the orientation and
	    // accelerometer sensors
	    sensorManager.registerListener(this,
	        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
	        SensorManager.SENSOR_DELAY_NORMAL);
	  }

	  @Override
	  protected void onPause() {
	    // unregister listener
	    super.onPause();
	    sensorManager.unregisterListener(this);
	  }
	  
	  private BroadcastReceiver mReceiver = new BroadcastReceiver() {
	    	@Override
	    	public void onReceive(Context context, Intent intent)
	    	{
	    	String action = intent.getAction();
	    	String cmd = intent.getStringExtra("command");
	    	Log.d("mIntentReceiver.onReceive ", action + " / " + cmd);
	    	currentArtist = intent.getStringExtra("artist");
	    	currentAlbum = intent.getStringExtra("album");
	    	currentSong = intent.getStringExtra("track");
	    	//Log.d("Music","CurrentSong "+artist+":"+album+":"+track);
	    	}
	    };

}
