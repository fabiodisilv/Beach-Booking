package it.univaq.disim.sose.beachbooking.beachbooking.business.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.univaq.disim.sose.beachbooking.beachbooking.business.BeachBookingService;
import it.univaq.disim.sose.beachbooking.beachbooking.business.BusinessException;
import it.univaq.disim.sose.beachbooking.beachbooking.model.Beach;
import it.univaq.disim.sose.beachbooking.beachbooking.model.Booking;
import it.univaq.disim.sose.beachbooking.beachbooking.model.Parking;
import it.univaq.disim.sose.beachbooking.beachbooking.model.User;
import it.univaq.disim.sose.beachbooking.beachbooking.model.accuweather.forecast.Forecast;
import it.univaq.disim.sose.beachbooking.beachbooking.model.accuweather.location.Location;
import it.univaq.disim.sose.beachbooking.clients.beachbooking.GetNearParkingsRequest;
import it.univaq.disim.sose.beachbooking.clients.beachbooking.GetNearParkingsResponse;
import it.univaq.disim.sose.beachbooking.clients.beachbooking.ParkingPT;
import it.univaq.disim.sose.beachbooking.clients.beachbooking.ParkingService;
import it.univaq.disim.sose.beachbooking.clients.beachbooking.ParkingType;

@Service
public class BeachBookingServiceImpl implements BeachBookingService, java.io.Serializable {

	@Value("#{cfg.proxyurl}")
	private String proxyUrl;

	@Value("#{cfg.proxyurl + cfg.getbeachesurl}")
	private String getbeachurl;

	@Value("#{cfg.proxyurl + cfg.bookbeachurl}")
	private String bookbeachurl;

	@Value("#{cfg.proxyurl + cfg.deletebookingurl}")
	private String deletebookingurl;

	@Value("#{cfg.getforecasturl}")
	private String getforecasturl;

	@Value("#{cfg.getlocationkeyurl}")
	private String getlocationkeyurl;

	@Value("#{cfg.accuweatherkey}")
	private String accuweatherkey;

	@Value("#{cfg.proxyurl + cfg.register}")
	private String register;

	@Value("#{cfg.proxyurl + cfg.login}")
	private String login;

	@Value("#{cfg.proxyurl + cfg.logout}")
	private String logout;

	@Value("#{cfg.proxyurl + cfg.checkkey}")
	private String checkkey;

	@Value("#{cfg.proxyurl + cfg.getlistofbooking}")
	private String getlistofbooking;

	@Override
	public List<Beach> getBeaches(String city) throws BusinessException {

		final String mediaType = MediaType.APPLICATION_JSON;

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(getbeachurl);
		List<Beach> beaches = target.resolveTemplate("city", city).request(mediaType)
				.get(new GenericType<List<Beach>>() {
				});

		return beaches;
	}

	@Override
	public List<Parking> getNearParkings(int zone) throws BusinessException {

		ParkingService parkingService = new ParkingService();
		ParkingPT parkingPT = parkingService.getParkingPort();
		GetNearParkingsRequest getNearParkingsRequest = new GetNearParkingsRequest();
		getNearParkingsRequest.setZone(zone);
		GetNearParkingsResponse getNearParkingsResponse = parkingPT.getNearParkings(getNearParkingsRequest);

		List<ParkingType> parkingTypes = getNearParkingsResponse.getParkings();

		List<Parking> parkings = new ArrayList<Parking>();

		for (ParkingType parkingType : parkingTypes) {

			Parking parking = new Parking();

			parking.setId(parkingType.getId());
			parking.setName(parkingType.getName());
			parking.setCapacity(parkingType.getCapacity());
			parking.setPrice(parkingType.getPrice());
			parking.setZone(parkingType.getZone());

			parkings.add(parking);
		}

		return parkings;
	}

	@Override
	public Booking bookBeach(Long beachId, Date date, String username) throws BusinessException {

		final String mediaType = MediaType.APPLICATION_JSON;

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(bookbeachurl);
		Booking booking = target.resolveTemplate("id", beachId).resolveTemplate("date", date)
				.resolveTemplate("username", username).request(mediaType).get(new GenericType<Booking>() {
				});

		return booking;

	}

	@Override
	public Boolean deleteBooking(Long id) throws BusinessException {

		final String mediaType = MediaType.APPLICATION_JSON;

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(deletebookingurl);
		Boolean deleteResult = target.resolveTemplate("id", id).request(mediaType).get(new GenericType<Boolean>() {
		});

		return deleteResult;
	}

	@Override
	public Forecast getForecast(String city) throws BusinessException {
		final String mediaType = MediaType.APPLICATION_JSON;

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(getlocationkeyurl);

		List<Location> locations = target.resolveTemplate("accuweatherkey", accuweatherkey)
				.resolveTemplate("city", city).request(mediaType).get(new GenericType<List<Location>>() {
				});

		target = client.target(getforecasturl);
		Forecast forecast = target.resolveTemplate("accuweatherkey", accuweatherkey)
				.resolveTemplate("citykey", locations.get(0).getKey()).request(mediaType)
				.get(new GenericType<Forecast>() {
				});

		return forecast;

	}

	@Override
	public String register(User user) throws BusinessException {
		final String mediaType = MediaType.APPLICATION_JSON;

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(register);
		Response response = target.request(mediaType).post(Entity.entity(user, mediaType));

		String result = response.readEntity(String.class);
		
		return result;
	}

	@Override
	public String login(User user) throws BusinessException {
		final String mediaType = MediaType.APPLICATION_JSON;

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(login);
		Response response = target.request(mediaType).post(Entity.entity(user, mediaType));

		String key = response.readEntity(String.class);
		
		return key;
	}

	@Override
	public void logout(String key) throws BusinessException {
		final String mediaType = MediaType.APPLICATION_JSON;

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(logout);
		target.resolveTemplate("key", key).request(mediaType).get(new GenericType<Boolean>() {
		});

	}

	@Override
	public Boolean checkKey(String key) throws BusinessException {
		final String mediaType = MediaType.APPLICATION_JSON;

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(checkkey);
		Boolean result = target.resolveTemplate("key", key)
				.request(mediaType)
				.get(new GenericType<Boolean>() {
		});

		return result;
	}

	@Override
	public List<Booking> getListOfBooking(String username) throws BusinessException {
		final String mediaType = MediaType.APPLICATION_JSON;

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(getlistofbooking);
		List<Booking> bookings = target.resolveTemplate("username", username).request(mediaType)
				.get(new GenericType<List<Booking>>() {
				});

		return bookings;
	}

}
