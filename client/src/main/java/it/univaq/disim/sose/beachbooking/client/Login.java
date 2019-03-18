package it.univaq.disim.sose.beachbooking.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.univaq.disim.sose.beachbooking.client.model.User;
import it.univaq.disim.sose.beachbooking.client.utils.Utils;

public class Login {

	public static void main(String[] args) {

		String username = "username";
		String password = "password";
		String prosumerUrl = Utils.getProsumerUrl();

		final String mediaType = MediaType.APPLICATION_JSON;

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(prosumerUrl + "login");

		User user = new User();

		user.setUsername(username);
		user.setPassword(password);

		Response response = target.request(mediaType).post(Entity.entity(user, mediaType));

		String output = response.readEntity(String.class);

		System.out.println(output);

	}

}
