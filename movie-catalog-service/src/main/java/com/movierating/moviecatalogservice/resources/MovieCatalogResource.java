package com.movierating.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.movierating.moviecatalogservice.models.CatalogItem;
import com.movierating.moviecatalogservice.models.UserRating;
import com.movierating.moviecatalogservice.services.MovieInfo;
import com.movierating.moviecatalogservice.services.UserRatingInfo;


@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private MovieInfo movieInfoService;
	
	@Autowired
	private UserRatingInfo userRatingInfoService;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		UserRating ratings = userRatingInfoService.getUserRating(userId);
		return ratings.getUserRating().stream()
				.map(rating -> movieInfoService.getCatalogItem(rating))
				.collect(Collectors.toList());
	}
}
