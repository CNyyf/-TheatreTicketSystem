package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import vo.Show;

public class SearchShowDAO extends baseDAO {
	
	public ArrayList<String> searchTitlesByDay(Timestamp startTime) {
		String sql = "select distinct title "
				+ "from theatre_ticket.show "
				+ "where date_format(start_time,'%Y-%m-%d') = date_format(?,'%Y-%m-%d')";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setTimestamp(1, startTime);
			ResultSet rs = pstmt.executeQuery();
			ArrayList<String> resultList = new ArrayList<String>();
			while(rs.next()) {
				resultList.add(rs.getString("title"));
			}
			return resultList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<String> searchTitlesByDay(String startTimeStr) {
		Timestamp startTime = Timestamp.valueOf(startTimeStr);
		return searchTitlesByDay(startTime);
	}
	
	public ArrayList<Show> SearchShows(String title, Timestamp startTime, Integer state) {
		String sql = "select * from theatre_ticket.show "
				+ "where title = ? ";
		if(startTime != null) sql += "and date_format(start_time,'%Y-%m-%d') = date_format(?,'%Y-%m-%d') ";
		if(state != null) sql += "and state = ? ";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			int pstmtIter = 1;
			pstmt.setString(pstmtIter, title);
			if(startTime != null) {
				pstmtIter++;
				pstmt.setTimestamp(pstmtIter, startTime);
			}
			if(state != null) {
				pstmtIter++;
				pstmt.setInt(pstmtIter, state);
			}
			ResultSet rs = pstmt.executeQuery();
			ArrayList<Show> resultList = new ArrayList<Show>();
			while(rs.next()) {
				Show show = new Show();
				show.setAll(rs);
				resultList.add(show);
			}
			return resultList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Show getShow(String title, String screening) {
		String sql = "select * "
				+ "from theatre_ticket.show "
				+ "where title = ? and screening = ? ";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, screening);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				Show show = new Show();
				show.setAll(rs);
				return show;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
