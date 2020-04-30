package com.movierating.moviecatalogservice.models;

public class Rating {

	private String movieId;
	private int rating;
	
	//we have to create an empty constructor in order for the marshaling from another MS will work
	public Rating(){
		
	}
	
	public Rating(String movieId, int rating) {
		this.movieId = movieId;
		this.rating = rating;
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}
