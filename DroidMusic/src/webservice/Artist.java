package webservice;


public class Artist {
	public String artistID;
	public String artistType;
	public String artistName;
	public String artistSortName;
	public String artistCountry;
	public String artistBegin;
	public String artistEnded;
	public String artistAlias;
	public String artistTags;
	
	public Artist(String artistID, String artistType, String artistName, String artistSortName, String artistCountry, String artistBegin, String artistEnded, String artistAlias, String artistTags)
	{
		this.artistID = artistID;
		this.artistType = artistType;
		this.artistName = artistName;
		this.artistSortName = artistSortName;
		this.artistCountry = artistCountry;
		this.artistBegin = artistBegin;
		this.artistEnded = artistEnded;
		this.artistAlias = artistAlias;
		this.artistTags = artistTags;
	}
	
	public Artist(String artistID, String artistType, String artistName)
	{
		this.artistID = artistID;
		this.artistType = artistType;
		this.artistName = artistName;
	}
	
	public String getartistID()
	{
		return this.artistID;
	}

	public String getartistName()
	{
		return this.artistName;
	}

	public String getartistSortName()
	{
		return this.artistSortName;
	}
	
	public String getartistCountry()
	{
		return this.artistCountry;
	}
	
	public String getartistBegin()
	{
		return this.artistBegin;
	}
	
	public String getartistEnded()
	{
		return this.artistEnded;
	}
	
	public String getartistAlias()
	{
		return this.artistAlias;
	}
	
	public String getartistTags()
	{
		return this.artistTags;
	}
	
	public String getartistType()
	{
		return this.artistType;
	}
}
