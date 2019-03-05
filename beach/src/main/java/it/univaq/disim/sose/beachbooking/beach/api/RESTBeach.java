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

@Controller("beachrestcontroller")
public class RESTBeach {
	private static Logger LOGGER = LoggerFactory.getLogger(RESTBeach.class);

	@Autowired
	private BeachService service;

	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/{city}")
	public List<Beach> getbeaches(@PathParam("city") String city) {

		LOGGER.info("CALLED getbeahes ON beachrestcontroller");
		return service.getBeaches(city);
	}

	@GET

	@Consumes("application/json")

	@Produces("application/json")

	@Path("/{beach_id}/{date}")
	public Boolean bookbeach(@PathParam("beach_id") Long beach_id, @PathParam("date") Date date) {

		LOGGER.info("CALLED bookbeach ON beachrestcontroller");
		return service.bookBeach(beach_id, date);
	}

	@GET

	@Consumes("application/json")

	@Produces("application/json")

	@Path("/{id}")
	public Boolean deletebooking(@PathParam("id") Long id) {

		LOGGER.info("CALLED deletebooking ON beachrestcontroller");
		return service.deleteBooking(id);
	}

}