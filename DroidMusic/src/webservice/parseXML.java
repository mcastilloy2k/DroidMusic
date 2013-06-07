package webservice;

import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import android.util.Log;

public class parseXML {
	
	@SuppressWarnings("unchecked")
	public Release GetRelease(String xml, String ID)
	{
		String date = "";
		String tracks = "";
		String cds = "";
		try
		{
			SAXBuilder builder = new SAXBuilder(false);
			Reader in = new StringReader(xml);
			Document doc = builder.build(in);
			Element root =  doc.getRootElement(); 
			Namespace ns = root.getNamespace();
			Element ReleaseList = root.getChild("release-list",ns);	
			List<Element> Releases = ReleaseList.getChildren("release",ns);	
			Iterator<Element> j = Releases.iterator();
			while(j.hasNext())
			{
				
				Element e=(Element)j.next();
				Element dateE = e.getChild("date",ns);
				date = dateE.getText();
				
				Element mediumE = e.getChild("medium-list",ns);
				cds = mediumE.getAttributeValue("count");
				Element tracksE = mediumE.getChild("track-count",ns);
				tracks = tracksE.getText();
				
				//Log.e(parseXML.class.toString(),"type="+type);
				Element rgE = e.getChild("release-list",ns);
				Element idE = (Element)rgE.getChildren("release",ns).get(0);
				String id = idE.getAttributeValue("id");
				Log.e(parseXML.class.toString(),"idAlbum="+id);
			}	
			
			
			Log.e(parseXML.class.toString(),"Successful Reading");
		}
		catch(Exception ex)
		{
			Log.e(parseXML.class.toString(),"FAILED HARD"+ex.toString());
		}		
		Release release = new Release(ID,date,tracks,cds);
		return release;
	}
	
	public String GetArtistBio(String xml)
	{
		String bio = "";
		try
		{
			SAXBuilder builder = new SAXBuilder(false);
			Reader in = new StringReader(xml);
			Document doc = builder.build(in);
			Element root =  doc.getRootElement(); 
			Element artistBio = root.getChild("query").getChild("pages").getChild("page").getChild("extract");
			bio = artistBio.getText();
			
			Log.e(parseXML.class.toString(),"Successful Reading");
		}
		catch(Exception ex)
		{
			Log.e(parseXML.class.toString(),"FAILED HARD"+ex.toString());
		}		
		return bio;
	}
	
	
	@SuppressWarnings("unchecked")
	public Vector<Artist> GetArtists(String xml)
	{
		Vector<Artist> VectorArtists =  new Vector<Artist>();
		try
		{
			SAXBuilder builder = new SAXBuilder(false);
			Reader in = new StringReader(xml);
			Document doc = builder.build(in);
			Element root =  doc.getRootElement(); 
			Namespace ns = root.getNamespace();
			Element ArtistList = root.getChild("artist-list",ns);	
			List<Element> Artists = ArtistList.getChildren("artist",ns);	
			Iterator<Element> j = Artists.iterator();
			while(j.hasNext())
			{
				
				Element e=(Element)j.next();
				String id = e.getAttributeValue("id");
				//Log.e(parseXML.class.toString(),"id="+id);
				String type = e.getAttributeValue("type");
				//Log.e(parseXML.class.toString(),"type="+type);
				Element nameE = e.getChild("name",ns);
				String name = nameE.getText();
				//Log.e(parseXML.class.toString(),"name="+name);
				VectorArtists.add(new Artist(id,type,name));

			}			
            
			Log.e(parseXML.class.toString(),"Successful Reading");
		}
		catch(Exception ex)
		{
			Log.e(parseXML.class.toString(),"FAILED HARD"+ex.toString());
		}		
		return VectorArtists;
	}
	
	@SuppressWarnings("unchecked")
	public Vector<Release> GetReleases(String xml)
	{
		Vector<Release> VectorReleases =  new Vector<Release>();
		try
		{
			SAXBuilder builder = new SAXBuilder(false);
			Reader in = new StringReader(xml);
			Document doc = builder.build(in);
			Element root =  doc.getRootElement(); 
			Namespace ns = root.getNamespace();
			Element ReleaseList = root.getChild("release-group-list",ns);	
			List<Element> Releases = ReleaseList.getChildren("release-group",ns);	
			Iterator<Element> j = Releases.iterator();
			while(j.hasNext())
			{
				
				Element e=(Element)j.next();
				Element nameE = e.getChild("title",ns);
				String name = nameE.getText();
				//Log.e(parseXML.class.toString(),"id="+id);
				String type = e.getAttributeValue("type");
				//Log.e(parseXML.class.toString(),"type="+type);
				Element rgE = e.getChild("release-list",ns);
				Element idE = (Element)rgE.getChildren("release",ns).get(0);
				String id = idE.getAttributeValue("id");
				Log.e(parseXML.class.toString(),"idAlbum="+id);
				VectorReleases.add(new Release(id,type,name));
			}			
            
			Log.e(parseXML.class.toString(),"Successful Reading");
		}
		catch(Exception ex)
		{
			Log.e(parseXML.class.toString(),"FAILED HARD"+ex.toString());
		}		
		return VectorReleases;
	}

}