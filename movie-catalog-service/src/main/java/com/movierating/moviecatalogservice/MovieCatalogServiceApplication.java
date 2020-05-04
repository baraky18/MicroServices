package com.movierating.moviecatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker //annotation provided by hystrix to enable this MS to use circuit breaking code of hystrix
/*
 * annotation provided by hystrix to enable the the hystrix dashboard at(localhost:<port>/hystrix
 * un stream http://localhost:<port>/actuator/hystrix.stream
 */
@EnableHystrixDashboard 
public class MovieCatalogServiceApplication {

	@Bean
	/*
	 * this annotation is saying that we are going to do a service discovery (using eureka) in a load balanced way
	 * meaning, we are going to provide RestTemplate only the service name that we want to call and the URL
	 * will be given to us by eureka
	 */
	@LoadBalanced
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
	
	@Bean
	public WebClient.Builder getWebClientBuilder(){
		return WebClient.builder();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogServiceApplication.class, args);
	}

}
