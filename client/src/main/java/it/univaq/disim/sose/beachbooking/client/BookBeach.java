package it.univaq.disim.sose.beachbooking.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import it.univaq.disim.sose.beachbooking.client.utils.Utils;

public class BookBeach {

	public static void main(String[] args) throws ParseException {

		String key = "3L1ZLA80AP";
		String id = "1";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(df.parse("2019-03-14").getTime());
		String username = "fabio";
		String prosumerUrl = Utils.getProsumerUrl();

		try {

			URL url = new URL(prosumerUrl + "bookbeach/" + key + "/" + id + "/" + date.toString() + "/" + username);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			// System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
