package it.univaq.disim.sose.beachbooking.beachbooking.business;

import java.sql.Date;
import java.util.List;

import it.univaq.disim.sose.beachbooking.beachbooking.model.Beach;
import it.univaq.disim.sose.beachbooking.beachbooking.model.Booking;
import it.univaq.disim.sose.beachbooking.beachbooking.model.Parking;
import it.univaq.disim.sose.beachbooking.beachbooking.model.User;
import it.univaq.disim.sose.beachbooking.beachbooking.model.accuweather.forecast.Forecast;

public interface BeachBookingService {
	
	String register(User user) throws BusinessException;

	String login(User user) throws BusinessException;

	void logout(String key) throws BusinessException;
	
	Boolean checkKey(String key) throws BusinessException;

	List<Beach> getBeaches(String city) throws BusinessException;

	List<Parking> getNearParkings(int zone) throws BusinessException;

	Booking bookBeach(Long beachId, Date date, String username) throws BusinessException;

	Boolean deleteBooking(Long id) throws BusinessException;
	
	Forecast getForecast(String city) throws BusinessException;
	
	List<Booking> getListOfBooking(String username) throws BusinessException;
	
}
