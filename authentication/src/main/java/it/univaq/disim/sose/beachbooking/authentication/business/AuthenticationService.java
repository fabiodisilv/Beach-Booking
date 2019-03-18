package it.univaq.disim.sose.beachbooking.authentication.business;

import it.univaq.disim.sose.beachbooking.authentication.business.model.User;

public interface AuthenticationService {

	String register(User user) throws BusinessException;

	String login(User user) throws BusinessException;

	void logout(String key) throws BusinessException;

	Boolean checkKey(String key) throws BusinessException;

}
