package com.jvm.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class Web {
	
	@GetMapping("/web")
	public String callweb() {
		return "Configmap: " + System.getProperty("resmgr") 
			+ " ----------- Secret: " + System.getProperty("publishAuthPwd");
	}

}
