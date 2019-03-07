package it.univaq.disim.sose.beachbooking.beachbooking.business.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.univaq.disim.sose.beachbooking.beachbooking.business.BeachBookingService;
import it.univaq.disim.sose.beachbooking.beachbooking.business.BusinessException;
import it.univaq.disim.sose.beachbooking.beachbooking.model.Beach;
import it.univaq.disim.sose.beachbooking.beachbooking.model.Parking;
import it.univaq.disim.sose.beachbooking.clients.beachbooking.GetNearParkingsRequest;
import it.univaq.disim.sose.beachbooking.clients.beachbooking.GetNearParkingsResponse;
import it.univaq.disim.sose.beachbooking.clients.beachbooking.ParkingPT;
import it.univaq.disim.sose.beachbooking.clients.beachbooking.ParkingService;
import it.univaq.disim.sose.beachbooking.clients.beachbooking.ParkingType;

@Service
public class BeachBookingServiceImpl implements BeachBookingService, java.io.Serializable {

	@Value("#{cfg.beachurl}")
	private String beachurl;

	@Override
	public List<Beach> getBeaches(String city) throws BusinessException {

		final String mediaType = MediaType.APPLICATION_JSON;
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(beachurl);
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

}
