package com.movierating.ratingdataservice.models;

import java.util.List;

public class UserRating {

	private List<Rating> userRating;

	public UserRating(List<Rating> ratings) {
		this.userRating = ratings;
	}

	public List<Rating> getUserRating() {
		return userRating;
	}

	public void setUserRating(List<Rating> userRating) {
		this.userRating = userRating;
	}
}
