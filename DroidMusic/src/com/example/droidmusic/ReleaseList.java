package com.example.droidmusic;

import java.util.Vector;

import com.example.droidmusic.ArtistList.ViewHolder;
import webservice.Release;
import webservice.musicbrainz;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.NavUtils;

public class ReleaseList extends Activity {
	public static final String EXTRA_ARTIST_NAME = "com.example.droidmusic.ARTIST_NAME";
	public static final String EXTRA_RELEASE_NAME = "com.example.droidmusic.RELEASE_NAME";
	public static final String EXTRA_RELEASE_TYPE = "com.example.droidmusic.ARTIST_TYPE";
	public static final String EXTRA_ARTIST_ID = "com.example.droidmusic.ID";
	public static final String EXTRA_RELEASE_ID = "com.example.droidmusic.RELEASE_ID";
	private String artistID;
	private String artistName;
	Vector<Release> releaselist;
	musicbrainz wsmusic = new musicbrainz();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_list);
        Intent intent = getIntent();
        artistID = intent.getStringExtra(ArtistList.EXTRA_ARTIST_ID);
        artistName = intent.getStringExtra(ArtistList.EXTRA_ARTIST_NAME);
        setTitle(artistName);
        releaselist = wsmusic.getRelease(artistID);
        ReleaseAdapter adapter = new ReleaseAdapter(this);
    	ListView ArtistListView = (ListView)findViewById(R.id.listView2);
    	ArtistListView.setAdapter(adapter);
        ArtistListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
            	showRelease(position);
            }
        });
    }
    
    private void showRelease(int pos)
    {
    	final Intent intent = new Intent(this,ShowRelease.class);
    	intent.putExtra(EXTRA_ARTIST_NAME, artistName);
    	intent.putExtra(EXTRA_RELEASE_NAME, releaselist.get(pos).getreleaseName());
    	intent.putExtra(EXTRA_RELEASE_ID, releaselist.get(pos).getreleaseID());
    	intent.putExtra(EXTRA_RELEASE_TYPE, releaselist.get(pos).getreleaseType());
		startActivity(intent); 
    }
    
    class ReleaseAdapter extends ArrayAdapter<Release> 
	{
    	
    	Activity context;
    	
		ReleaseAdapter(Activity context) 
    	{
    		super(context, R.layout.two_col_row, releaselist);
    		this.context = context;
    	}
    	
    	public View getView(int position, View convertView, ViewGroup parent) 
    	{
			View item = convertView;
			ViewHolder holder;
			int[] colors = new int[] {0x30808080, 0x3033B5E5};
    		if(item == null)
    		{
    			LayoutInflater inflater = context.getLayoutInflater();
    			item = inflater.inflate(R.layout.two_col_row, null);
    			
    			holder = new ViewHolder();
    			holder.Name = (TextView) item.findViewById(R.id.TextView02);
    			holder.Type = (TextView) item.findViewById(R.id.TextView01);
    			
    			item.setTag(holder);
    		}
    		else
    		{
    			holder = (ViewHolder)item.getTag();
    		}
			
			holder.Type.setText(releaselist.elementAt(position).getreleaseType());
			holder.Name.setText(releaselist.elementAt(position).getreleaseName());
			int colorPos = position % colors.length;
			item.setBackgroundColor(colors[colorPos]);
			return(item);
		}
    } 
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_show_discography, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
