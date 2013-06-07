package com.example.droidmusic;

import java.util.Vector;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import webservice.Release;
import webservice.musicbrainz;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class LineGraph{
	private String artist;
	private Vector<Release> releaselist;
	private musicbrainz wsmusic = new musicbrainz();
	
	public LineGraph(String artist, String artistID)
	{
		this.artist = artist;
		releaselist = wsmusic.getRelease(artistID);
	}
	
	public Intent getIntentTracks(Context context) {
		
		TimeSeries series = new TimeSeries("Tracks per album"); 
		Release release = new Release("","","","");
		
		
		for( int i = 0; i < releaselist.size(); i++)
		{
			release = wsmusic.getReleaseAlbum(releaselist.get(i).getreleaseID());
			Integer y = Integer.parseInt(release.getreleaseTracks());
			series.add(i+1,y);
		}
		
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.setLabelsTextSize(25);
		mRenderer.setBackgroundColor(Color.BLACK);
		XYSeriesRenderer renderer = new XYSeriesRenderer(); 
		mRenderer.addSeriesRenderer(renderer);
		
		renderer.setColor(Color.YELLOW);
		renderer.setPointStyle(PointStyle.SQUARE);
		renderer.setLineWidth(7);
		renderer.setFillPoints(true);
		renderer.setFillBelowLine(true);
		renderer.setFillBelowLineColor(Color.CYAN);
		
		Intent intent = ChartFactory.getLineChartIntent(context, dataset, mRenderer, artist+": Tracks Per Album");
		return intent;
		
	}

	public Intent getIntentYear(Context context) {
		
		//TimeSeries series = new TimeSeries("Tracks per album"); 
		TimeSeries series = new TimeSeries("Releases by year"); 
		Release release = new Release("","","","");
		
		
		for( int i = 0; i < releaselist.size(); i++)
		{
			release = wsmusic.getReleaseAlbum(releaselist.get(i).getreleaseID());
			//Integer y = Integer.parseInt(release.getreleaseTracks());
			Integer y = 0;
			String year = release.getreleaseDate();
			if (year.length()>=4)
			{
				y = Integer.parseInt(year.substring(0, 4));
			}
			series.add(i+1,y);
		}
		
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.setLabelsTextSize(25);
		mRenderer.setBackgroundColor(Color.BLACK);
		XYSeriesRenderer renderer = new XYSeriesRenderer(); 
		mRenderer.addSeriesRenderer(renderer);

		renderer.setColor(Color.RED);
		renderer.setPointStyle(PointStyle.SQUARE);
		renderer.setLineWidth(7);
		renderer.setFillPoints(true);
		renderer.setFillBelowLine(true);
		renderer.setFillBelowLineColor(Color.CYAN);
		
		Intent intent = ChartFactory.getLineChartIntent(context, dataset, mRenderer, artist+" Discography");
		return intent;
		
	}

}

