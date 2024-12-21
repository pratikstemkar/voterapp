package com.voterapp.daos;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.voterapp.pojos.Candidate;

public interface CandidateDAO {
	List<Candidate> getAllCandidates() throws SQLException;
	String incrementVotes(int candidateId) throws SQLException;
	List<Candidate> getTop2Candidates() throws SQLException;
	Map<String, Integer> getPartyWiseData() throws SQLException;
	void cleanUp() throws SQLException;
}
