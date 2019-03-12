#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ${package}.business.BeachBookingService;
import ${package}.model.Beach;
import ${package}.model.BeachParkingWeather;
import ${package}.model.Booking;
import ${package}.model.accuweather.forecast.Forecast;

@Controller("${artifactId}restcontroller")
public class RESTBeachBooking {

	private static Logger LOGGER = LoggerFactory.getLogger(RESTBeachBooking.class);

	@Autowired
	private BeachBookingService service;

	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("getbeaches/{key}/{city}")
	public BeachParkingWeather getbeaches(@PathParam("key") String key, @PathParam("city") String city) {

		LOGGER.info("CALLED getbeahes ON ${artifactId}restcontroller");

		Boolean validKey = service.checkKey(key);

		if (validKey) {

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
	@Consumes("application/json")
	@Produces("application/json")
	@Path("bookbeach/{key}/{id}/{date}/{username}")
	public Booking bookbeach(@PathParam("key") String key, @PathParam("id") Long id, @PathParam("date") Date date,
			@PathParam("username") String username) {

		LOGGER.info("CALLED bookbeach ON ${artifactId}restcontroller");

		Boolean validKey = service.checkKey(key);

		if (validKey) {

			return service.bookBeach(id, date, username);

		} else {

			return null;

		}
	}
	
	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("getlistofbooking/{key}/{username}")
	public List<Booking> getlistofbooking(@PathParam("key") String key, @PathParam("username") String username) {

		LOGGER.info("CALLED getlistofbooking ON ${artifactId}restcontroller");

		Boolean validKey = service.checkKey(key);

		if (validKey) {

			return service.getListOfBooking(username);

		} else {

			return null;

		}
	}


	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("deletebooking/{key}/{id}")
	public Boolean deletebooking(@PathParam("key") String key, @PathParam("id") Long id) {

		LOGGER.info("CALLED deletebooking ON ${artifactId}restcontroller");

		Boolean validKey = service.checkKey(key);

		if (validKey) {

			return service.deleteBooking(id);

		} else {

			return null;

		}
	}

	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("getforecast/{key}/{city}")
	public Forecast getforecast(@PathParam("key") String key, @PathParam("city") String city) {

		LOGGER.info("CALLED getforecast ON ${artifactId}restcontroller");

		Boolean validKey = service.checkKey(key);

		if (validKey) {

			return service.getForecast(city);

		} else {

			return null;

		}
	}

	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("register/{username}/{password}")
	public String register(@PathParam("username") String username, @PathParam("password") String password) {

		LOGGER.info("CALLED register ON ${artifactId}restcontroller");

		return service.register(username, password);

	}

	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("login/{username}/{password}")
	public String login(@PathParam("username") String username, @PathParam("password") String password) {

		LOGGER.info("CALLED login ON ${artifactId}restcontroller");

		return service.login(username, password);

	}

	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("logout/{key}")
	public Boolean logout(@PathParam("key") String key) {

		LOGGER.info("CALLED logout ON ${artifactId}restcontroller");

		return service.logout(key);
	}
}
