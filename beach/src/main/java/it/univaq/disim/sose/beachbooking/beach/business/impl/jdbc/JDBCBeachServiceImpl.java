package it.univaq.disim.sose.beachbooking.beach.business.impl.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.disim.sose.beachbooking.beach.business.BeachService;
import it.univaq.disim.sose.beachbooking.beach.business.BusinessException;
import it.univaq.disim.sose.beachbooking.beach.business.model.Beach;

@Service
public class JDBCBeachServiceImpl implements BeachService {

	private static Logger LOGGER = LoggerFactory.getLogger(JDBCBeachServiceImpl.class);

	@Autowired
	private DataSource dataSource;

	@Override
	public List<Beach> getBeaches(String city) throws BusinessException {
		String query = "SELECT id, name, price, rating, city, zone FROM beach WHERE city = ? ";

		List<Beach> beaches = new ArrayList<Beach>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = dataSource.getConnection();

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, city);

			resultSet = preparedStatement.executeQuery();

			LOGGER.info("JDBCBeachServiceImpl - getBeaches: Executing query");

			while (resultSet.next()) {

				Beach beach = new Beach();

				beach.setId(resultSet.getLong("id"));
				beach.setName(resultSet.getString("name"));
				beach.setPrice(resultSet.getDouble("price"));
				beach.setRating(resultSet.getInt("rating"));
				beach.setCity(resultSet.getString("city"));
				beach.setZone(resultSet.getInt("zone"));

				beaches.add(beach);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("JDBCBeachServiceImpl - getBeaches: " + e.getMessage());

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

		return beaches;

	}

	@Override
	public Boolean bookBeach(Long beach_id, Date date) throws BusinessException {
		String query = "INSERT INTO booking (beach_id, date) VALUES(?, ?)";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int updateResult = 0;

		try {

			connection = dataSource.getConnection();

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setLong(1, beach_id);
			preparedStatement.setDate(2, date);

			LOGGER.info("JDBCBeachServiceImpl - bookBeach: Inserting Booking");

			updateResult = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("JDBCBeachServiceImpl - getBeaches: " + e.getMessage());

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

		return true;

	}

	@Override
	public Boolean deleteBooking(Long id) throws BusinessException {

		String query = "UPDATE booking SET canceled = 1 WHERE id = ? ";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int updateResult = 0;

		try {

			connection = dataSource.getConnection();

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setLong(1, id);

			LOGGER.info("JDBCBeachServiceImpl - bookBeach: Deleting Booking");

			updateResult = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("JDBCBeachServiceImpl - getBeaches: " + e.getMessage());

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

		return true;

	}

}
