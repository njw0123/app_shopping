package org.edupoll.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerController {

	@GetMapping("/")
	public String viewSwagger() {

		return "redirect:/swagger-ui/index.html";
	}

}
