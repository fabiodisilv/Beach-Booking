package it.univaq.disim.sose.beachbooking.parking.webservices;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.disim.sose.beachbooking.parking.GetNearParkingsRequest;
import it.univaq.disim.sose.beachbooking.parking.GetNearParkingsResponse;
import it.univaq.disim.sose.beachbooking.parking.ParkingPT;
import it.univaq.disim.sose.beachbooking.parking.ParkingType;
import it.univaq.disim.sose.beachbooking.parking.business.ParkingService;
import it.univaq.disim.sose.beachbooking.parking.business.model.Parking;

@Component(value = "ParkingPTImpl")
public class ParkingPTImpl implements ParkingPT {

	private static Logger LOGGER = LoggerFactory.getLogger(ParkingPTImpl.class);

	@Autowired
	private ParkingService service;

	@Override
	public GetNearParkingsResponse getNearParkings(GetNearParkingsRequest parameters) {

		LOGGER.info("CALLED GetNearParkingsResponse ON PARKINGPT");

		try {

			List<Parking> parkings = service.getNearParkings(parameters.getLatitude(), parameters.getLongitude());

			GetNearParkingsResponse response = new GetNearParkingsResponse();

			for (Parking parking : parkings) {

				ParkingType parkingType = new ParkingType();

				parkingType.setName(parking.getName());
				parkingType.setLatitude(parking.getLatitude());
				parkingType.setLongitude(parking.getLongitude());
				parkingType.setCapacity(parking.getCapacity());
				parkingType.setPrice(parking.getPrice());

				response.getParkings().add(parkingType);
			}

			return response;
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

}
