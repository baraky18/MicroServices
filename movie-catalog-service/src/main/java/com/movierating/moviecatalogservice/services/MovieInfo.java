package com.movierating.moviecatalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.movierating.moviecatalogservice.models.CatalogItem;
import com.movierating.moviecatalogservice.models.Movie;
import com.movierating.moviecatalogservice.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class MovieInfo {

	@Autowired
	private RestTemplate restTemplate;
	
	//	@Autowired
	//	private WebClient.Builder webClientbuilder;
	
	/*
	 * I have refactored this into a new service class for 2 reasons: 
	 * 1. to have a separate fallback method to each MS so that if only one fails, the second one can still get some response
	 * 2. the fallback method is called bu hystrix only if it's in separate class because hystrix is implementing
	 * the fallback mechanism by creating a proxy class of the class that's annotated with @HystrixCommand and 
	 * put the call to the fallback method inside
	 */
	@HystrixCommand( 
			fallbackMethod="getFallbackCatalogItem", //this method will use the circuit breaking code
			/*
			 * the following properties allows us to define a bulkhead mechanism of threads that call this specific MS.
			 * this is good because whenever 1 MS is slow, we don't want to overload all of the space in the web server
			 * with threads calling to that MS - so we have to limit its calls to allow the other MSs to be called 
			 * without being slow also
			 */
			threadPoolKey="movieInfoPool", //this allows us to differentiate between the bulkheads. all of the fallback methods that will use the same key, will use the same pool size of that key 
			threadPoolProperties={
					@HystrixProperty(name="coreSize", value="20"), //how many threads I allow for this MS (we can assume that each thread is a single request for that MS)
					@HystrixProperty(name="maxQueueSize", value="10") //how many threads I allow to be queued while max number of threads to that MS is already consumed
			}) 
	public CatalogItem getCatalogItem(Rating rating) {
		/*
		 * RestTemplate is the old way to perform a REST call from a MS
		 */
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);

		/*
		 * WebClient is the new way to perform a REST call from a MS.
		 * basically it's a way to perform Async programming in Sync programming
		 */
		//				Movie movie = webClientbuilder.build()
		//					.get()//this is the REST method that we are calling
		//					.uri("http://localhost:8082/movies/" + rating.getMovieId())//URI
		//					.retrieve()//retrieve the object
		//					.bodyToMono(Movie.class)//retrieve an instance of the Movie class using an Asyc call
		//					.block();//we are blocking execution till the object is populated
		return new CatalogItem(movie.getName(), movie.getOverview(), rating.getRating());
	}
	
	public CatalogItem getFallbackCatalogItem(Rating rating) {
		return new CatalogItem("Movie name not found", "", rating.getRating());
	}
}
