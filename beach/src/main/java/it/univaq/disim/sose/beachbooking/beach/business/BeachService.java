package it.univaq.disim.sose.beachbooking.beach.business;

import java.sql.Date;
import java.util.List;

import it.univaq.disim.sose.beachbooking.beach.business.model.Beach;

public interface BeachService {
	
	List<Beach> getBeaches(String city) throws BusinessException;
	
	Long bookBeach(Long beach_id, Date date, String username) throws BusinessException;

	Boolean deleteBooking(Long id) throws BusinessException;
}
