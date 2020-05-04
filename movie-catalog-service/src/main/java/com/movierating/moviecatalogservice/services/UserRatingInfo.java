package com.movierating.moviecatalogservice.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.movierating.moviecatalogservice.models.Rating;
import com.movierating.moviecatalogservice.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class UserRatingInfo {

	@Autowired
	private RestTemplate restTemplate;
	
	/*
	 * I have refactored this into a new service class for 2 reasons: 
	 * 1. to have a separate fallback method to each MS so that if only one fails, the second one can still get some response
	 * 2. the fallback method is called bu hystrix only if it's in separate class because hystrix is implementing
	 * the fallback mechanism by creating a proxy class of the class that's annotated with @HystrixCommand and 
	 * put the call to the fallback method inside
	 */
	@HystrixCommand( 
			fallbackMethod="getFallbackUserRating", //this method will use the circuit breaking code
			commandProperties = { //these properties allow us to configure params for fallback mechanism
					//this is the timeout that determines if the MS that we are calling is down
					@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000"),
					//this is the number of last requests that it needs to see before going to the fallback method
					@HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="6"),
					//this is the percentage that requests that needs to fail in order to call the fallback method
					@HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"),
					//this is the sleep time of the circuit breaker after a MS failed to try again
					@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="5000")
			}) 
	public UserRating getUserRating(String userId) {
		return restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);
	}
	
	public UserRating getFallbackUserRating(String userId) {
		UserRating userRating = new UserRating();
		userRating.setUserRating(Arrays.asList(new Rating("0", 0)));
		return userRating;
	}
}
