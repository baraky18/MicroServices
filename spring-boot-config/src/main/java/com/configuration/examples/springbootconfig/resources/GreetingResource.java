package com.configuration.examples.springbootconfig.resources;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.configuration.examples.springbootconfig.configuration.DbSettings;

@RestController
@RefreshScope //this tells spring that whenever there's a change in the configuration MS, we will pick the refreshed values for this file only
public class GreetingResource {

	@Value("${my.greeting}")
	private String greetingMessage; //this is the way to configure fields in a property file
	
	@Value("${app.description}")
	private String appDesc;
	
	@Value("${app.default: default value}")
	private String defaultValue; //this is a way to put default value in case the property doesn't exist
	
	@Value("${my.list.values}")
	private List<String> myListValues; //this is a way to configure list comma separated
	
	@Value("#{${map.values}}")
	private Map<String, String> mapValues; 
	
	@Autowired
	private DbSettings dbSettings;
	
	@GetMapping("/greeting")
	public String greeting(){
		return greetingMessage + " " + appDesc + " " + defaultValue + " " + myListValues + " " + mapValues
				+ " " + dbSettings.getConnection() + " " + dbSettings.getHost() + " " + dbSettings.getPort();
	}
}
