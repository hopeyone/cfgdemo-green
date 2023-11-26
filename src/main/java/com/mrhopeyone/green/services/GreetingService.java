package com.mrhopeyone.green.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

	@Value("${app.mainmessage}")
	String message;
	
	@Value("${spring.application.name: unknown}")
	String appName;

	
	public String getAppName() 
	{
		return appName;
	}
	
	public String getMessage()
	{
		return message;
	}
}
