package it.univaq.disim.sose.beachbooking.authentication.business.impl.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.disim.sose.beachbooking.authentication.business.AuthenticationService;
import it.univaq.disim.sose.beachbooking.authentication.business.BusinessException;
import it.univaq.disim.sose.beachbooking.authentication.business.model.User;
import it.univaq.disim.sose.beachbooking.authentication.utils.Utils;

@Service
public class JDBCAuthenticationImpl implements AuthenticationService {

	private static Logger LOGGER = LoggerFactory.getLogger(JDBCAuthenticationImpl.class);

	@Autowired
	private DataSource dataSource;

	@Override
	public String register(User user) throws BusinessException {
		String query = "SELECT username FROM users WHERE username = ?";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = dataSource.getConnection();

			// check if the user is already registered
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, user.getUsername());

			resultSet = preparedStatement.executeQuery();

			LOGGER.info("JDBCBeachServiceImpl - register: Executing query");

			if (resultSet.next()) {
				// return an error message
				return "Error - User already exist!";
			}

			resultSet.close();
			preparedStatement.close();

			// insert the user into the DB
			query = "INSERT INTO users (username, password) VALUES (?,?)";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("JDBCAuthenticationImpl - register: " + e.getMessage());

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

		return "OK";

	}

	@Override
	public String login(User user) throws BusinessException {
		String query = "SELECT username, password FROM users WHERE username = ? ";

		String passwordDB = null;
		String key = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = dataSource.getConnection();

			// check if the user is registered
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, user.getUsername());

			resultSet = preparedStatement.executeQuery();

			LOGGER.info("JDBCBeachServiceImpl - login: Executing query");

			while (resultSet.next()) {

				passwordDB = resultSet.getString("password");

			}

			if (passwordDB == null) {
				return "Error - The user is not registered";
			}

			if (!passwordDB.equals(user.getPassword())) {
				return "Error - Wrong password";
			}

			resultSet.close();
			preparedStatement.close();

			// check if there is already a valid key
			query = "SELECT username FROM authenticated_users WHERE username = ? ";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user.getUsername());

			resultSet = preparedStatement.executeQuery();

			// if there is a valid key, delete it
			if (resultSet.next()) {
				resultSet.close();
				preparedStatement.close();

				query = "DELETE FROM authenticated_users WHERE username = ? ";

				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, user.getUsername());

				preparedStatement.executeUpdate();

				resultSet.close();
				preparedStatement.close();
			}

			// generate and insert a valid key
			query = "INSERT INTO authenticated_users(username, `key`) VALUES (?,?)";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user.getUsername());

			key = Utils.generateKey();
			preparedStatement.setString(2, key);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("JDBCAuthenticationImpl - getBeaches: " + e.getMessage());

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

		// return the key
		return key;

	}

	@Override
	public void logout(String key) throws BusinessException {
		String query = "DELETE FROM authenticated_users WHERE `key` = ?";

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = dataSource.getConnection();

			// delete the record in authenticated_users
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, key);

			LOGGER.info("JDBCBeachServiceImpl - logout: Executing query");

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

			LOGGER.error("JDBCAuthenticationImpl - logout: " + e.getMessage());

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

	}

	@Override
	public Boolean checkKey(String key) throws BusinessException {
		String query = "SELECT username FROM authenticated_users WHERE `key` = ? ";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = dataSource.getConnection();

			// check if the key is valid
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, key);

			resultSet = preparedStatement.executeQuery();

			LOGGER.info("JDBCBeachServiceImpl - login: Executing query");

			if (resultSet.next()) {

				// return true if the key is in the table authenticated_users
				return true;

			} else {

				// return false otherwise
				return false;

			}

		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("JDBCAuthenticationImpl - getBeaches: " + e.getMessage());

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
	}

}
