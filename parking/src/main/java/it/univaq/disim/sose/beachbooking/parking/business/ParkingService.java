package it.univaq.disim.sose.beachbooking.parking.business;

import java.util.List;

import it.univaq.disim.sose.beachbooking.parking.business.model.Parking;

public interface ParkingService {

	List<Parking> getNearParkings(int zone) throws BusinessException;

}
