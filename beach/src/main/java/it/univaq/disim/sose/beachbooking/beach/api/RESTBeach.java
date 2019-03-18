package it.univaq.disim.sose.beachbooking.beach.api;

import java.sql.Date;
import java.util.List;

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
	@Produces("application/json")
	@Path("getbeaches/{city}")
	public List<Beach> getbeaches(@PathParam("city") String city) {

		LOGGER.info("CALLED getbeaches ON beachrestcontroller");
		//get the list of the beaches in the city
		return service.getBeaches(city);
	}

	@GET
	@Produces("application/json")
	@Path("bookbeach/{beach_id}/{date}/{username}")
	public Booking bookbeach(@PathParam("beach_id") Long beachId, @PathParam("date") Date date,
			@PathParam("username") String username) {

		LOGGER.info("CALLED bookbeach ON beachrestcontroller");

		//book a beach
		Booking booking = new Booking();

		booking.setBeachId(beachId);
		booking.setDate(date);
		booking.setUsername(username);
		booking.setCanceled(false);
		booking.setId(service.bookBeach(beachId, date, username));

		return booking;
	}

	@GET
	@Path("deletebooking/{id}")
	public void deletebooking(@PathParam("id") Long id) {

		LOGGER.info("CALLED deletebooking ON beachrestcontroller");

		//delete a booking
		service.deleteBooking(id);
	}

	@GET
	@Produces("application/json")
	@Path("getlistofbooking/{username}")
	public List<Booking> getlistofbooking(@PathParam("username") String username) {

		LOGGER.info("CALLED bookbeach ON beachrestcontroller");

		//get the list of booking per user
		return service.getListOfBooking(username);

	}

}