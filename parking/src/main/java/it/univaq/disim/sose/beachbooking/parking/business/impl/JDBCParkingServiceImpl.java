package it.univaq.disim.sose.beachbooking.parking.business.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.disim.sose.beachbooking.parking.business.BusinessException;
import it.univaq.disim.sose.beachbooking.parking.business.ParkingService;
import it.univaq.disim.sose.beachbooking.parking.business.model.Parking;

@Service
public class JDBCParkingServiceImpl implements ParkingService {

	private static Logger LOGGER = LoggerFactory.getLogger(JDBCParkingServiceImpl.class);

	@Autowired
	private DataSource dataSource;

	@Override
	public List<Parking> getNearParkings(Double latitude, Double longitude) throws BusinessException {

		String query = "SELECT id, name, latitude, longitude, capacity, price FROM parkings_bookbooking";

		List<Parking> parkings = new ArrayList<Parking>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = dataSource.getConnection();

			preparedStatement = connection.prepareStatement(query);

			// preparedStatement.setString(1, city);

			resultSet = preparedStatement.executeQuery();

			LOGGER.info("JDBCParkingServiceImpl - getNearParkings: Executing query");

			while (resultSet.next()) {

				Parking parking = new Parking();

				parking.setId(resultSet.getLong("id"));
				parking.setName(resultSet.getString("name"));
				parking.setLatitude(resultSet.getDouble("latitude"));
				parking.setLongitude(resultSet.getDouble("longitude"));
				parking.setCapacity(resultSet.getInt("capacity"));
				parking.setPrice(resultSet.getDouble("price"));

				parkings.add(parking);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("JDBCParkingServiceImpl - getNearParkings: " + e.getMessage());

			throw new BusinessException(e);
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}

		}

		return parkings;

	}

}
