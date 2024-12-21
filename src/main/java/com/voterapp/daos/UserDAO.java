package com.voterapp.daos;

import java.sql.Date;
import java.sql.SQLException;

import com.voterapp.pojos.User;

public interface UserDAO {
	User authenticateUser(String email, String password) throws SQLException;
	int createUser(String firstName, String lastName, String email, String password, Date dob) throws SQLException;
	User findUser(String email) throws SQLException;
	String updateVotingStatus(int voterId) throws SQLException;
	void cleanUp() throws SQLException;
}
