package it.univaq.disim.sose.beachbooking.beachbooking.api;

import java.sql.Date;
import java.util.ArrayList;
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
import it.univaq.disim.sose.beachbooking.beachbooking.model.Beach;
import it.univaq.disim.sose.beachbooking.beachbooking.model.BeachInfoParking;
import it.univaq.disim.sose.beachbooking.beachbooking.model.Booking;
import it.univaq.disim.sose.beachbooking.beachbooking.model.accuweather.forecast.Forecast;

@Controller("beachbookingrestcontroller")
public class RESTBeachBooking {

	private static Logger LOGGER = LoggerFactory.getLogger(RESTBeachBooking.class);

	@Autowired
	private BeachBookingService service;

	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("getbeaches/{city}")
	public List<BeachInfoParking> getbeaches(@PathParam("city") String city) {

		LOGGER.info("CALLED getbeahes ON beachbookingrestcontroller");

		List<BeachInfoParking> beachInfoParkings = new ArrayList<BeachInfoParking>();

		List<Beach> beaches = service.getBeaches(city);

		for (Beach beach : beaches) {

			BeachInfoParking beachInfoParking = new BeachInfoParking();

			beachInfoParking.setId(beach.getId());
			beachInfoParking.setName(beach.getName());
			beachInfoParking.setPrice(beach.getPrice());
			beachInfoParking.setRating(beach.getRating());
			beachInfoParking.setCity(beach.getCity());
			beachInfoParking.setZone(beach.getZone());

			beachInfoParking.setNearParkings(service.getNearParkings(beach.getZone()));

			beachInfoParkings.add(beachInfoParking);

		}

		return beachInfoParkings;

	}

	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("bookbeach/{id}/{date}")
	public Booking bookbeach(@PathParam("id") Long id, @PathParam("date") Date date ) {

		LOGGER.info("CALLED bookbeach ON beachbookingrestcontroller");

		return service.bookBeach(id, date);

	}
	
	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("deletebooking/{id}")
	public boolean deletebooking(@PathParam("id") Long id) {

		LOGGER.info("CALLED deletebooking ON beachbookingrestcontroller");

		return service.deleteBooking(id);

	}

	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("getforecast/{city}")
	public Forecast getforecast(@PathParam("city") String city) {

		LOGGER.info("CALLED getforecast ON beachbookingrestcontroller");

		return service.getForecast(city);

	}
	
}
