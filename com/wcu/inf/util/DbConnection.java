/**
 * 
 */
package com.wcu.inf.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Raj
 *
 */
public class DbConnection {

	private static Connection connection = null;

	public static Connection getConnection() {
		if (null == connection) {
			connection = createConnection();
		}
		return connection;
	}

	private DbConnection() {

	}

	private static Connection createConnection() {

		System.out.println("-------- MySQL JDBC Connection Testing ------------");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return null;
		}

		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gowalla", "root", "apple");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}

		if (connection == null) {
			System.out.println("Failed to make connection!");
		}
		return connection;
	}
}
