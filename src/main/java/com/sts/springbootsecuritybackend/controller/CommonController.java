package com.sts.springbootsecuritybackend.controller;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {
	
	private Logger log = Logger.getLogger(this.getClass());
	@GetMapping(value = "/")
	public String landingPage(Model model) {
		
		log.info("In /");
		System.out.println("In / request");
		return "landingPage";
	}
	@GetMapping(value="/accessDenied")
	 String accessD()
	{
		System.out.println("No permision");
		return "You don't have permission";
	}
}

