package webservice;

public class Release {
	public String releaseID;
	public String releaseType;
	public String releaseName;
	public String releaseDate;
	public String releaseTracks;
	public String releaseCDs;

	
	
	public Release(String releaseID, String releaseType, String releaseName)
	{
		this.releaseID = releaseID;
		this.releaseType = releaseType;
		this.releaseName = releaseName;
	}
	
	public Release(String releaseID, String releaseDate, String releaseTracks, String releaseCDs)
	{
		this.releaseID = releaseID;
		this.releaseDate = releaseDate;
		this.releaseTracks = releaseTracks;
		this.releaseCDs = releaseCDs;
	}
	
	public String getreleaseID()
	{
		return this.releaseID;
	}

	public String getreleaseName()
	{
		return this.releaseName;
	}

	public String getreleaseType()
	{
		return this.releaseType;
	}
	
	public String getreleaseDate()
	{
		return this.releaseDate;
	}
	
	public String getreleaseTracks()
	{
		return this.releaseTracks;
	}
	
	public String getreleaseCDs()
	{
		return this.releaseCDs;
	}
}