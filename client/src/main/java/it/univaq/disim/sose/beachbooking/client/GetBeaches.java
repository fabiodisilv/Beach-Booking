package it.univaq.disim.sose.beachbooking.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import it.univaq.disim.sose.beachbooking.client.utils.Utils;

public class GetBeaches {

	public static void main(String[] args) {

		String key = "key";
		String city = "Rimini";
		String prosumerUrl = Utils.getProsumerUrl();

		final String mediaType = MediaType.APPLICATION_JSON;

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(prosumerUrl + "getbeaches/{key}/{city}");
		String output = target.resolveTemplate("key", key).
				resolveTemplate("city", city)
				.request(mediaType)
				.get(new GenericType<String>() {
				});

		System.out.println(output);

	}

}
