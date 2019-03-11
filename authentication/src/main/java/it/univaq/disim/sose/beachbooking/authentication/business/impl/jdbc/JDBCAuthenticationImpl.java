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

@Service
public class JDBCAuthenticationImpl implements AuthenticationService {

	private static Logger LOGGER = LoggerFactory.getLogger(JDBCAuthenticationImpl.class);

	@Autowired
	private DataSource dataSource;

	@Override
	public String register(String username, String password) throws BusinessException {
		String query = "SELECT username FROM users WHERE username = ?";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = dataSource.getConnection();

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, username);

			resultSet = preparedStatement.executeQuery();

			LOGGER.info("JDBCBeachServiceImpl - register: Executing query");

			if (resultSet.next()) {

				return "Error - User already exist!";
			}

			resultSet.close();
			preparedStatement.close();

			query = "INSERT INTO users (username, password) VALUES (?,?)";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

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
	public String login(String username, String password) throws BusinessException {
		String query = "SELECT username, password FROM users WHERE username = ? ";

		String passwordDB = null;
		String key = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = dataSource.getConnection();

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, username);

			resultSet = preparedStatement.executeQuery();

			LOGGER.info("JDBCBeachServiceImpl - login: Executing query");

			while (resultSet.next()) {

				passwordDB = resultSet.getString("password");

			}

			if (passwordDB == null) {
				return "Error - The user is not registered";
			}

			if (!passwordDB.equals(password)) {
				return "Error - Wrong password";
			}

			resultSet.close();
			preparedStatement.close();

			query = "INSERT INTO authenticated_users(username, `key`) VALUES (?,?)";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			
			key = generateKey();
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

		return key;

	}

	@Override
	public Boolean logout(String key) throws BusinessException {
		String query = "DELETE FROM authenticated_users WHERE `key` = ?";

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = dataSource.getConnection();

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

		return true;

	}

	@Override
	public Boolean checkKey(String key) throws BusinessException {
		String query = "SELECT username FROM authenticated_users WHERE `key` = ? ";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = dataSource.getConnection();

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, key);

			resultSet = preparedStatement.executeQuery();

			LOGGER.info("JDBCBeachServiceImpl - login: Executing query");

			if (resultSet.next()) {
				
				return true;
				
			} else {
				
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

	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	private static String generateKey() {
		int count = 10;
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

}
