package com.voterapp.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.voterapp.pojos.Candidate;
import static com.voterapp.utils.DBUtil.getConnection;

public class CandidateDaoImpl implements CandidateDAO {
	private Connection connection;
	private PreparedStatement pst1, pst2, pst3, pst4;
	
	public CandidateDaoImpl() throws SQLException {
		connection = getConnection();
		pst1 = connection.prepareStatement("SELECT * FROM candidates");
		pst2 = connection.prepareStatement("UPDATE candidates SET votes = votes + 1 WHERE id = ?");
		pst3 = connection.prepareStatement("SELECT * FROM candidates ORDER BY votes DESC LIMIT 2");
		pst4 = connection.prepareStatement("SELECT party, sum(votes) FROM candidates GROUP BY party");
	}

	@Override
	public List<Candidate> getAllCandidates() throws SQLException {
		List<Candidate> candidates = new ArrayList<>();
		try (ResultSet rs1 = pst1.executeQuery()) {
			while(rs1.next())
				candidates.add(new Candidate(rs1.getInt(1), rs1.getString(2), rs1.getString(3), rs1.getInt(4)));
		}
		return candidates;
	}

	@Override
	public String incrementVotes(int candidateId) throws SQLException {
		pst2.setInt(1, candidateId);
		int updatedRows = pst2.executeUpdate();
		if(updatedRows == 1) {
			return "Candidate Votes updated!";
		} else {
			return "Candidate Votes update failed!";
		}
	}

	@Override
	public List<Candidate> getTop2Candidates() throws SQLException {
		List<Candidate> candidates = new ArrayList<>();
		try(ResultSet rs = pst3.executeQuery()) {
			while(rs.next())
				candidates.add(new Candidate(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
		}
		return candidates;
	}

	@Override
	public Map<String, Integer> getPartyWiseData() throws SQLException {
		Map<String, Integer> partyData = new HashMap<>();
		try (ResultSet rs = pst4.executeQuery()) {
			while(rs.next())
				partyData.put(rs.getString(1), rs.getInt(2));
		}
		return partyData;
	}

	@Override
	public void cleanUp() throws SQLException {
		if(pst1 != null) {
			pst1.close();
			pst1 = null;
		}
		System.out.println("Candidate DAO is cleaned up!");
	}
	
}
