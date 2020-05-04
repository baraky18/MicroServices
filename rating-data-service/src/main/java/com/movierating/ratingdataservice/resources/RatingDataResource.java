package com.movierating.ratingdataservice.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movierating.ratingdataservice.models.Rating;
import com.movierating.ratingdataservice.models.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class RatingDataResource {

	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId){
		return new Rating(movieId, 4);
	}
	
	@RequestMapping("users/{userId}")
	public UserRating getUserRating(@PathVariable("userId") String userId){
		List<Rating> ratings = Arrays.asList(
				new Rating("500", 4),
				new Rating("200", 3)
			);
		return new UserRating(ratings);
	}
}
