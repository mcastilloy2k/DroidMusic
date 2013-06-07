package com.example.droidmusic;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import webservice.musicbrainz;

public class ShowArtist extends Activity {

private musicbrainz wsmusic = new musicbrainz();
public static final String EXTRA_ARTIST_NAME = "com.example.droidmusic.ARTIST_NAME";
public static final String EXTRA_ARTIST_ID = "com.example.droidmusic.ID";
private String artistID;
private String artistName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_artist);
        Intent intent = getIntent();
        artistID = intent.getStringExtra(ArtistList.EXTRA_ARTIST_ID);
        artistName = intent.getStringExtra(ArtistList.EXTRA_ARTIST_NAME);
        setTitle(artistName);
        String html = wsmusic.getArtistBio(artistName);
        WebView wv = (WebView) findViewById(R.id.webView1);        
        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        wv.loadDataWithBaseURL("", html, mimeType, encoding, "");
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.show_artist_albums) {
        	final Intent intent = new Intent(this,ReleaseList.class);
        	intent.putExtra(EXTRA_ARTIST_NAME, artistName);
        	intent.putExtra(EXTRA_ARTIST_ID, artistID);
    		startActivity(intent); 
        }
        if (item.getItemId() == R.id.show_artist_graphs) {
        	LineGraph line = new LineGraph(artistName, artistID);
        	Intent lineIntent = line.getIntentTracks(this);
            startActivity(lineIntent);
        }
        if (item.getItemId() == R.id.show_artist_year) {
        	LineGraph line = new LineGraph(artistName, artistID);
        	Intent lineIntent = line.getIntentYear(this);
            startActivity(lineIntent);
        }
        if (item.getItemId() == R.id.share) {
        	final Intent intent = new Intent(this,Share.class);
        	intent.putExtra(EXTRA_ARTIST_NAME, artistName);
        	intent.putExtra(EXTRA_ARTIST_ID, artistID);
    		startActivity(intent); 
        }
        return super.onOptionsItemSelected(item);
    }



    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_show_artist, menu);
        return true;
    }
}
