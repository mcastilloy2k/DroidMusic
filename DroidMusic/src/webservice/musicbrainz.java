package webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.util.Log;

public class musicbrainz {

private parseXML parse = new parseXML();	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	 public  String getRequest(String query) {
		 StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(
                query);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e(musicbrainz.class.toString(), "Failed to download file");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
	    }
	 
	 
	 
	 public Vector<Artist>  getArtist(String artist)
	 {
		 //String xml = this.getRequest("http://musicbrainz.org/ws/2/artist/?query="+"artist:God%20Is%20An%20Astronaut");
		 String xml = this.getRequest("http://musicbrainz.org/ws/2/artist/?query="+artist);
		 return parse.GetArtists(xml);
	 }
	 
	 public Vector<Release>  getRelease(String mbid)
	 {
		 
		 String xml = this.getRequest("http://musicbrainz.org/ws/2/release-group/?query=arid:"+mbid);
		 return parse.GetReleases(xml);
	 }
	 
	 public Release  getReleaseAlbum(String reid)
	 {
		 
		 String xml = this.getRequest("http://musicbrainz.org/ws/2/release/?query=reid:"+reid);
		 return parse.GetRelease(xml,reid);
	 }
	 
	 public String getArtistBio(String artist)
	 {
		 String artquery = artist.replaceAll(" ", "%20"); 
		 String xml = this.getRequest("http://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles="+artquery+"&format=xml&exintro=1");
		 return parse.GetArtistBio(xml);
	 }

}
