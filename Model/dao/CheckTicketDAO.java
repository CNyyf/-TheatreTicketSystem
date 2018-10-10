package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.Ticket;

public class CheckTicketDAO extends baseDAO {

	public Ticket getTicket(Long ticketId) {
		String sql = "select * "
				+ "from theatre_ticket.ticket "
				+ "where ticket_id = ? ";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, ticketId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				Ticket ticket = new Ticket();
				ticket.setAll(rs);
				return ticket;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Ticket> searchTicketsByMemberId(String memberId) {
		String sql = "select * "
				+ "from theatre_ticket.ticket "
				+ "where member_id = ? ";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			ResultSet rs = pstmt.executeQuery();
			ArrayList<Ticket> resultList = new ArrayList<Ticket>();
			while(rs.next()) {
				Ticket ticket = new Ticket();
				ticket.setAll(rs);
				resultList.add(ticket);
			}
			return resultList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean checkTicket(Long ticketId) {
		Ticket ticket = getTicket(ticketId);
		if(ticket.getState() != 1) {
			return false;
		}
		String sql = "update theatre_ticket.ticket "
				+ "set state = 0 "
				+ "where ticket_id = ? ";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, ticketId);
			int rows_count = pstmt.executeUpdate();
			if(rows_count > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
