package com.movierating.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;

import com.movierating.moviecatalogservice.models.CatalogItem;
import com.movierating.moviecatalogservice.models.Movie;
import com.movierating.moviecatalogservice.models.UserRating;


@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private RestTemplate restTemplate;
	
//	@Autowired
//	private WebClient.Builder webClientbuilder;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		
		UserRating ratings = restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);
		
		return ratings.getUserRating().stream()
			.map(rating -> {
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
				return new CatalogItem(movie.getName(), "Test", rating.getRating());
			})
			.collect(Collectors.toList());
		
		
	}
}
