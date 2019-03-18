package it.univaq.disim.sose.beachbooking.client;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import it.univaq.disim.sose.beachbooking.client.utils.Utils;

public class BookBeach {

	public static void main(String[] args) throws ParseException {

		String key = "key";
		String beachId = "1";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(df.parse("2019-03-14").getTime());
		String username = "username";
		String prosumerUrl = Utils.getProsumerUrl();

		final String mediaType = MediaType.APPLICATION_JSON;

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(prosumerUrl + "bookbeach/{key}/{id}/{date}/{username}");
		String output = target.resolveTemplate("key", key).resolveTemplate("id", beachId).resolveTemplate("date", date)
				.resolveTemplate("username", username).request(mediaType).get(new GenericType<String>() {
				});

		System.out.println(output);

	}

}
