package com.voterapp.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.voterapp.pojos.User;

import static com.voterapp.utils.DBUtil.getConnection;

public class UserDaoImpl implements UserDAO {
	private Connection connection;
	private PreparedStatement pst1, pst2, pst3, pst4;
	
	public UserDaoImpl() throws SQLException {
		connection = getConnection();
		pst1 = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
		pst2 = connection.prepareStatement("INSERT INTO users (first_name, last_name, email, password, dob, status, role) VALUES (?, ?, ?, ?, ?, 0, 'voter')");
		pst3 = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
		pst4 = connection.prepareStatement("UPDATE users SET status = 1 WHERE id = ?");
		System.out.println("User DAO Created!");
	}
	
	@Override
	public User authenticateUser(String email, String password) throws SQLException {
		pst1.setString(1, email);
		pst1.setString(2, password);
		try (ResultSet rs1 = pst1.executeQuery()) {
			if(rs1.next())
				return new User(rs1.getInt(1), rs1.getString(2), rs1.getString(3), email, password, rs1.getDate(6), rs1.getBoolean(7), rs1.getString(8));
		}
		
		return null;
	}
	
	@Override
	public int createUser(String firstName, String lastName, String email, String password, Date dob)
			throws SQLException {
		pst2.setString(1, firstName);
		pst2.setString(2, lastName);
		pst2.setString(3, email);
		pst2.setString(4, password);
		pst2.setDate(5, dob);
		
		return pst2.executeUpdate();
	}
	
	@Override
	public User findUser(String email) throws SQLException {
		pst3.setString(1, email);
		
		try(ResultSet rs1 = pst3.executeQuery()) {
			if(rs1.next())
				return new User(rs1.getInt(1), rs1.getString(2), rs1.getString(3), rs1.getString(4), rs1.getString(5), rs1.getDate(6), rs1.getBoolean(7), rs1.getString(8));
		}
		
		return null;
	}
	
	@Override
	public String updateVotingStatus(int voterId) throws SQLException {
		pst4.setInt(1, voterId);
		int updatedRows = pst4.executeUpdate();
		if(updatedRows == 1) {
			return "Voter status updated!";
		} else {
			return "Voter status update failed!";
		}
	}

	@Override
	public void cleanUp() throws SQLException {
		if(pst1 != null) {
			pst1.close();
			pst1 = null;
		}
		if(pst2 != null) {
			pst2.close();
			pst2 = null;
		}
		if(pst3 != null) {
			pst3.close();
			pst3 = null;
		}
		System.out.println("User DAO is cleaned up!");
	}
}
