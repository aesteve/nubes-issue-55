package controllers;

import com.github.aesteve.vertx.nubes.annotations.Controller;
import com.github.aesteve.vertx.nubes.annotations.routing.http.GET;

@Controller("/data")
public class RestController {

	@GET("/dashboard")
	public String getDashboard(){
		System.out.println("com.collatrice.frontend.controllers.RestController.getDashbaord()");
		return "hello";
	}

}
