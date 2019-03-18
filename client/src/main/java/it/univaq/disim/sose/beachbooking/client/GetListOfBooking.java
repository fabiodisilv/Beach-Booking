package it.univaq.disim.sose.beachbooking.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import it.univaq.disim.sose.beachbooking.client.utils.Utils;

public class GetListOfBooking {

	public static void main(String[] args) {

		String key = "key";
		String username = "username";
		String prosumerUrl = Utils.getProsumerUrl();

		final String mediaType = MediaType.APPLICATION_JSON;

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(prosumerUrl + "getlistofbooking/{key}/{username}");
		String output = target.resolveTemplate("key", key).resolveTemplate("username", username).request(mediaType)
				.get(new GenericType<String>() {
				});

		System.out.println(output);

	}
}
