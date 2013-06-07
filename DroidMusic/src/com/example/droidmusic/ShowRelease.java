package com.example.droidmusic;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import webservice.Release;
import webservice.musicbrainz;

public class ShowRelease extends Activity {
	private String releaseID;
	private String artistName;
	private String releaseName;
	private String releaseType;
	musicbrainz wsmusic = new musicbrainz();
	Release release = new Release("","","","");
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_release);
        Intent intent = getIntent();
        releaseID = intent.getStringExtra(ReleaseList.EXTRA_RELEASE_ID);
        artistName = intent.getStringExtra(ReleaseList.EXTRA_ARTIST_NAME);
        releaseName = intent.getStringExtra(ReleaseList.EXTRA_RELEASE_NAME);
        releaseType = intent.getStringExtra(ReleaseList.EXTRA_RELEASE_TYPE);
        setTitle(releaseName);
        TextView text06 = (TextView)findViewById(R.id.textViewAlbum06);
        text06.setText(releaseName);
        TextView text05 = (TextView)findViewById(R.id.textViewAlbum05);
        text05.setText(artistName);
        TextView text01 = (TextView)findViewById(R.id.textViewAlbum01);
        text01.setText(releaseType);
        
        release = wsmusic.getReleaseAlbum(releaseID);
        TextView text02 = (TextView)findViewById(R.id.textViewAlbum02);
        text02.setText(release.getreleaseDate());
        TextView text03 = (TextView)findViewById(R.id.textViewAlbum03);
        text03.setText(release.getreleaseTracks());
        TextView text04 = (TextView)findViewById(R.id.textViewAlbum04);
        text04.setText(release.getreleaseCDs());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_show_release, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	final Intent intent = new Intent(Intent.ACTION_SEND);
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.ShareRelease:
        	 	intent.setType("text/plain");
        	 	intent.putExtra(Intent.EXTRA_SUBJECT, "Hey! Check out "+releaseName+" from "+artistName+" on MusicBrainz");
        	 	intent.putExtra(Intent.EXTRA_TEXT, "http://musicbrainz.org/release/"+releaseID);
        	 	startActivity(Intent.createChooser(intent, "Share")); 
            	return true;
            case R.id.ShareReleaseLastFM:
        	 	intent.setType("text/plain");
        	 	intent.putExtra(Intent.EXTRA_SUBJECT, "Hey! Check out "+releaseName+" from "+artistName+" on Last.fm");
        	 	intent.putExtra(Intent.EXTRA_TEXT, "http://www.last.fm/music/"+artistName.replaceAll(" ", "+")+"/"+releaseName.replaceAll(" ", "+"));
        	 	startActivity(Intent.createChooser(intent, "Share")); 
            	return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    

}
