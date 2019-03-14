package it.univaq.disim.sose.beachbooking.client.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Utils {

	public static String getProsumerUrl() {

		String prosumerUrl = null;

		try {

			Properties prop = new Properties();
			String propFileName = "resources/config.properties";

			File initialFile = new File(propFileName);

			InputStream inputStream = new FileInputStream(initialFile);

			prop.load(inputStream);

			// get the property value
			prosumerUrl = prop.getProperty("prosumerUrl");

			inputStream.close();

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}

		return prosumerUrl;

	}
}
