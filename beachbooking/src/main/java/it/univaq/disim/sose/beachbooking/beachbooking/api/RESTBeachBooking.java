package it.univaq.disim.sose.beachbooking.beachbooking.api;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import it.univaq.disim.sose.beachbooking.beachbooking.business.BeachBookingService;
import it.univaq.disim.sose.beachbooking.beachbooking.model.Beach;
import it.univaq.disim.sose.beachbooking.beachbooking.model.BeachParkingWeather;
import it.univaq.disim.sose.beachbooking.beachbooking.model.Booking;
import it.univaq.disim.sose.beachbooking.beachbooking.model.User;
import it.univaq.disim.sose.beachbooking.beachbooking.model.accuweather.forecast.Forecast;

@Controller("beachbookingrestcontroller")
public class RESTBeachBooking {

	private static Logger LOGGER = LoggerFactory.getLogger(RESTBeachBooking.class);

	@Autowired
	private BeachBookingService service;

	@GET
	@Produces("application/json")
	@Path("getbeaches/{key}/{city}")
	public BeachParkingWeather getbeaches(@PathParam("key") String key, @PathParam("city") String city) {

		LOGGER.info("CALLED getbeahes ON beachbookingrestcontroller");

		// check if the key is valid before executing
		Boolean validKey = service.checkKey(key);

		if (validKey) {

			// parallel execution of 2 services
			ExecutorService executor = Executors.newFixedThreadPool(2);

			Callable<List<Beach>> callableBeach = new Callable<List<Beach>>() {
				@Override
				public List<Beach> call() {
					return service.getBeaches(city);
				}
			};

			Callable<Forecast> callableWeather = new Callable<Forecast>() {
				@Override
				public Forecast call() {
					return service.getForecast(city);
				}
			};

			Future<List<Beach>> futureBeach = executor.submit(callableBeach);
			Future<Forecast> futureWeather = executor.submit(callableWeather);

			List<Beach> beaches = null;
			Forecast forecast = null;

			try {

				beaches = futureBeach.get();

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			executor.shutdown();

			BeachParkingWeather beachParkingWeathers = new BeachParkingWeather();

			for (Beach beach : beaches) {

				beach.setNearParkings(service.getNearParkings(beach.getZone()));

			}

			try {

				forecast = futureWeather.get();

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}

			beachParkingWeathers.setBeaches(beaches);
			beachParkingWeathers.setForecast(forecast);

			return beachParkingWeathers;

		} else {

			return null;

		}
	}

	@GET
	@Produces("application/json")
	@Path("bookbeach/{key}/{id}/{date}/{username}")
	public Booking bookbeach(@PathParam("key") String key, @PathParam("id") Long id, @PathParam("date") Date date,
			@PathParam("username") String username) {

		LOGGER.info("CALLED bookbeach ON beachbookingrestcontroller");

		// check if the key is valid before executing
		Boolean validKey = service.checkKey(key);

		if (validKey) {

			return service.bookBeach(id, date, username);

		} else {

			return null;

		}
	}

	@GET
	@Produces("application/json")
	@Path("getlistofbooking/{key}/{username}")
	public List<Booking> getlistofbooking(@PathParam("key") String key, @PathParam("username") String username) {

		LOGGER.info("CALLED getlistofbooking ON beachbookingrestcontroller");

		// check if the key is valid before executing
		Boolean validKey = service.checkKey(key);

		if (validKey) {

			return service.getListOfBooking(username);

		} else {

			return null;

		}
	}

	@GET
	@Produces("application/json")
	@Path("deletebooking/{key}/{id}")
	public void deletebooking(@PathParam("key") String key, @PathParam("id") Long id) {

		LOGGER.info("CALLED deletebooking ON beachbookingrestcontroller");

		// check if the key is valid before executing
		Boolean validKey = service.checkKey(key);

		if (validKey) {

			service.deleteBooking(id);

		}
		
	}

	@GET
	@Produces("application/json")
	@Path("getforecast/{key}/{city}")
	public Forecast getforecast(@PathParam("key") String key, @PathParam("city") String city) {

		LOGGER.info("CALLED getforecast ON beachbookingrestcontroller");

		// check if the key is valid before executing
		Boolean validKey = service.checkKey(key);

		if (validKey) {

			return service.getForecast(city);

		} else {

			return null;

		}
	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("register")
	public String register(User user) {

		LOGGER.info("CALLED register ON beachbookingrestcontroller");

		//call register service
		return service.register(user);

	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("login")
	public String login(User user) {

		LOGGER.info("CALLED login ON beachbookingrestcontroller");

		//call login service
		return service.login(user);

	}

	@GET
	@Produces("application/json")
	@Path("logout/{key}")
	public void logout(@PathParam("key") String key) {

		LOGGER.info("CALLED logout ON beachbookingrestcontroller");

		//call logout service
		service.logout(key);
	}
}
