package com.harman.skillserver;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

@Component
public class StartUpInit{


	@PostConstruct 
	public void initTcpServer() {
		System.out.println("started");
	}

	@PreDestroy
	public void deInitServer() {
		System.out.println("destroy");
	}
  
}
