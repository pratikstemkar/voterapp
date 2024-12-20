package com.voterapp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static Connection connection;
	private static final String DB_URL = "jdbc:mysql://localhost:3306/voterdb";
	private static final String DB_USER = "D3_86766_Pratik";
	private static final String DB_PASS = "manager";
	
	public static void openConnection() throws SQLException {
		connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		System.out.println("Opened DB Connection!");
	}
	
	public static void closeConnection() throws SQLException {
		if(connection != null) {
			connection.close();
			connection = null;
			System.out.println("DB connection closed!");
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
}
