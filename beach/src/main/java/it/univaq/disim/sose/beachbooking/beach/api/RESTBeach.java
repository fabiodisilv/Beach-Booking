package it.univaq.disim.sose.beachbooking.beach.api;

import java.sql.Date;
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

import it.univaq.disim.sose.beachbooking.beach.business.BeachService;
import it.univaq.disim.sose.beachbooking.beach.business.model.Beach;
import it.univaq.disim.sose.beachbooking.beach.business.model.Booking;

@Controller("beachrestcontroller")
public class RESTBeach {

	private static Logger LOGGER = LoggerFactory.getLogger(RESTBeach.class);

	@Autowired
	private BeachService service;

	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("getbeaches/{city}")
	public List<Beach> getbeaches(@PathParam("city") String city) {

		LOGGER.info("CALLED getbeaches ON beachrestcontroller");
		return service.getBeaches(city);
	}

	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("bookbeach/{beach_id}/{date}/{username}")
	public Booking bookbeach(@PathParam("beach_id") Long beachId, @PathParam("date") Date date,
			@PathParam("username") String username) {

		LOGGER.info("CALLED bookbeach ON beachrestcontroller");

		Booking booking = new Booking();

		booking.setBeachId(beachId);
		booking.setDate(date);
		booking.setUsername(username);
		booking.setCanceled(false);
		booking.setId(service.bookBeach(beachId, date, username));

		return booking;
	}

	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("deletebooking/{id}")
	public Boolean deletebooking(@PathParam("id") Long id) {

		LOGGER.info("CALLED deletebooking ON beachrestcontroller");

		return service.deleteBooking(id);
	}

}