package com.movierating.moviecatalogservice.models;

public class Movie {

	private String movieId;
	private String name;
	
	//we have to create an empty constructor in order for the marshaling from another MS will work
	public Movie(){
		
	}
	
	public Movie(String movieId, String name) {
		this.movieId = movieId;
		this.name = name;
	}
	public String getMovieId() {
		return movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
