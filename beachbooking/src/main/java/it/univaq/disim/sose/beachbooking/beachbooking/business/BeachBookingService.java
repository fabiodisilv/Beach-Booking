package it.univaq.disim.sose.beachbooking.beachbooking.business;

import java.sql.Date;
import java.util.List;

import it.univaq.disim.sose.beachbooking.beachbooking.model.Beach;
import it.univaq.disim.sose.beachbooking.beachbooking.model.Booking;
import it.univaq.disim.sose.beachbooking.beachbooking.model.Parking;

public interface BeachBookingService {

	List<Beach> getBeaches(String city) throws BusinessException;

	List<Parking> getNearParkings(int zone) throws BusinessException;

	Booking bookBeach(Long beachId, Date date) throws BusinessException;

	Boolean deleteBooking(Long id) throws BusinessException;
	
}
