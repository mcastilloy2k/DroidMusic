package com.example.droidmusic;


import java.util.Vector;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import webservice.Artist;
import webservice.musicbrainz;

public class ArtistList extends Activity {
	public static final String EXTRA_ARTIST_NAME = "com.example.droidmusic.ARTIST_NAME";
	public static final String EXTRA_ARTIST_ID = "com.example.droidmusic.ID";
	musicbrainz wsmusic = new musicbrainz();
	Vector<Artist> artistlist;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_list);
        Intent intent = getIntent();
        String artist = intent.getStringExtra(MainActivity.EXTRA_ARTIST_SEARCH);
        artistlist = wsmusic.getArtist(artist);
        ArtistAdapter adapter = new ArtistAdapter(this);
    	ListView ArtistListView = (ListView)findViewById(R.id.listView1);
    	ArtistListView.setAdapter(adapter);
        ArtistListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
            	showArtist(position);
            }
        });
    }
    
    private void showArtist(int pos)
    {
    	final Intent intent = new Intent(this,ShowArtist.class);
    	intent.putExtra(EXTRA_ARTIST_NAME, artistlist.get(pos).getartistName());
    	intent.putExtra(EXTRA_ARTIST_ID, artistlist.get(pos).getartistID());
		startActivity(intent); 
    }
    
    class ArtistAdapter extends ArrayAdapter<Artist> 
	{
    	
    	Activity context;
    	
		ArtistAdapter(Activity context) 
    	{
    		super(context, R.layout.two_col_row, artistlist);
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
			
			holder.Type.setText(artistlist.elementAt(position).getartistType());
			holder.Name.setText(artistlist.elementAt(position).getartistName());
			int colorPos = position % colors.length;
			item.setBackgroundColor(colors[colorPos]);
			return(item);
		}
    } 
    
    static class ViewHolder {
    	TextView Name;
    	TextView Type;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_arstist_list, menu);
        return true;
    }
}
