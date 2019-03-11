package it.univaq.disim.sose.beachbooking.authentication.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import it.univaq.disim.sose.beachbooking.authentication.business.AuthenticationService;
import it.univaq.disim.sose.beachbooking.authentication.business.model.User;

@Controller("authenticationrestcontroller")
public class RESTAuthentication {

	private static Logger LOGGER = LoggerFactory.getLogger(RESTAuthentication.class);

	@Autowired
	private AuthenticationService service;

	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("register/{username}/{password}")
	public String register(@PathParam("username") String username, @PathParam("password") String password) {

		LOGGER.info("CALLED registerUser ON authenticationrestcontroller");

		return service.register(username, password);

	}

	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("login/{username}/{password}")
	public String login(@PathParam("username") String username, @PathParam("password") String password) {

		LOGGER.info("CALLED login ON authenticationrestcontroller");

		return service.login(username, password);

	}

	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("logout/{key}")
	public Boolean logout(@PathParam("key") String key) {

		LOGGER.info("CALLED logout ON authenticationrestcontroller");

		return service.logout(key);

	}

	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("checkkey/{key}")
	public Boolean checkkey(@PathParam("key") String key) {

		LOGGER.info("CALLED logout ON authenticationrestcontroller");

		return service.checkKey(key);

	}

}
