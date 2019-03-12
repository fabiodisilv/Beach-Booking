package it.pkg.business.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.pkg.business.BeachBookingService;
import it.pkg.business.BusinessException;
import it.pkg.model.Beach;
import it.pkg.model.Booking;
import it.pkg.model.Parking;
import it.pkg.model.accuweather.forecast.Forecast;
import it.pkg.model.accuweather.location.Location;
import archetype.it.clients.basic.GetNearParkingsRequest;
import archetype.it.clients.basic.GetNearParkingsResponse;
import archetype.it.clients.basic.ParkingPT;
import archetype.it.clients.basic.ParkingService;
import archetype.it.clients.basic.ParkingType;

@Service
public class BeachBookingServiceImpl implements BeachBookingService, java.io.Serializable {

	@Value("#{cfg.getbeachesurl}")
	private String getbeachurl;

	@Value("#{cfg.bookbeachurl}")
	private String bookbeachurl;

	@Value("#{cfg.deletebookingurl}")
	private String deletebookingurl;

	@Value("#{cfg.getforecasturl}")
	private String getforecasturl;

	@Value("#{cfg.getlocationkeyurl}")
	private String getlocationkeyurl;

	@Value("#{cfg.accuweatherkey}")
	private String accuweatherkey;

	@Value("#{cfg.register}")
	private String register;

	@Value("#{cfg.login}")
	private String login;

	@Value("#{cfg.logout}")
	private String logout;

	@Value("#{cfg.checkkey}")
	private String checkkey;

	@Value("#{cfg.getlistofbooking}")
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
	public String register(String username, String password) throws BusinessException {
		final String mediaType = MediaType.APPLICATION_JSON;

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(register);
		String result = target.resolveTemplate("username", username).resolveTemplate("password", password)
				.request(mediaType).get(new GenericType<String>() {
				});

		return result;
	}

	@Override
	public String login(String username, String password) throws BusinessException {
		final String mediaType = MediaType.APPLICATION_JSON;

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(login);
		String key = target.resolveTemplate("username", username).resolveTemplate("password", password)
				.request(mediaType).get(new GenericType<String>() {
				});

		return key;
	}

	@Override
	public Boolean logout(String key) throws BusinessException {
		final String mediaType = MediaType.APPLICATION_JSON;

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(logout);
		Boolean result = target.resolveTemplate("key", key).request(mediaType).get(new GenericType<Boolean>() {
		});

		return result;
	}

	@Override
	public Boolean checkKey(String key) throws BusinessException {
		final String mediaType = MediaType.APPLICATION_JSON;

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(checkkey);
		Boolean result = target.resolveTemplate("key", key).request(mediaType).get(new GenericType<Boolean>() {
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
