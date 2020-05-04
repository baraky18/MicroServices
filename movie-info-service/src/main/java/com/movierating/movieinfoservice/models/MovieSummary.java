package com.movierating.movieinfoservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieSummary {
	
	private String title;
	private String overview;
	
	public MovieSummary(){
		
	}
	public MovieSummary(String title, String overview) {
		this.title = title;
		this.overview = overview;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOverview() {
		return overview;
	}
	@JsonProperty("overview")
	public void setOverview(String overview) {
		this.overview = overview;
	}
}
