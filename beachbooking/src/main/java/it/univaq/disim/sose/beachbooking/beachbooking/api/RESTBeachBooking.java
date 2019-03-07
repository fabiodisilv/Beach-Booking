package it.univaq.disim.sose.beachbooking.beachbooking.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import it.univaq.disim.sose.beachbooking.beachbooking.business.BeachBookingService;

@Controller("beachbookingrestcontroller")
public class RESTBeachBooking {
	
	private static Logger LOGGER = LoggerFactory.getLogger(RESTBeachBooking.class);
	
	@Autowired
	private BeachBookingService service;
	
	/*@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/{city}")
	public List<Beach> getbeaches(@PathParam("city") String city) {

		LOGGER.info("CALLED getbeahes ON beachbookingrestcontroller");
		return service.getBeaches(city);
	}*/
	
}
