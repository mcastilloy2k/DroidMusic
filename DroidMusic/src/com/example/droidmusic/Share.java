package com.example.droidmusic;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Share extends Activity {
	private String artistID;
	private String artistName;
	private String artistns;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        Intent intent = getIntent();
        artistID = intent.getStringExtra(ShowArtist.EXTRA_ARTIST_ID);
        artistName = intent.getStringExtra(ShowArtist.EXTRA_ARTIST_NAME);
        setTitle("Share "+artistName);
        TextView textview1 = (TextView)findViewById(R.id.textView1);
        TextView textview2 = (TextView)findViewById(R.id.textView2);
        artistns = artistName.replaceAll(" ","%20");
        textview1.setText("www.last.fm/music/"+artistns);
        textview2.setText("http://musicbrainz.org/artist/"+artistID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_share, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.ShareLastFm) {
            ShareLink("www.last.fm/music/"+artistns,"Last.fm");
        }
        if (item.getItemId() == R.id.ShareMB) {
        	ShareLink("http://musicbrainz.org/artist/"+artistID,"MusicBrainz");
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void ShareLink(String link, String type)
    {
    	final Intent intent = new Intent(Intent.ACTION_SEND);
	 	intent.setType("text/plain");
	 	intent.putExtra(Intent.EXTRA_SUBJECT, "Hey! Check out "+artistName+" on "+type);
	 	intent.putExtra(Intent.EXTRA_TEXT, link);
	 	startActivity(Intent.createChooser(intent, "Share")); 
    	
    }
}
