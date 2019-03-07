package it.univaq.disim.sose.beachbooking.beachbooking.business;

import java.util.List;

import it.univaq.disim.sose.beachbooking.beachbooking.model.Beach;
import it.univaq.disim.sose.beachbooking.beachbooking.model.Parking;

public interface BeachBookingService {

	List<Beach> getBeaches(String city) throws BusinessException;

	List<Parking> getNearParkings(int zone) throws BusinessException;

}
