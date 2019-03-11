package it.univaq.disim.sose.beachbooking.authentication.business;

public interface AuthenticationService {

	String register(String username, String password) throws BusinessException;

	String login(String username, String password) throws BusinessException;

	Boolean logout(String key) throws BusinessException;

	Boolean checkKey(String key) throws BusinessException;

}
