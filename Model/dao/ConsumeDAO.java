package dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import vo.Member;
import vo.Show;

public class ConsumeDAO extends baseDAO {
	
	public Member getMember(String memberId) {
		String sql = "select * "
				+ "from theatre_ticket.member "
				+ "where member_id = ? ";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				Member member = new Member();
				member.setAll(rs);
				return member;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean recharge(String memberId, BigDecimal amount) {
		if(amount.compareTo(BigDecimal.valueOf(0)) < 1) {
			return false;
		}
		Member member = getMember(memberId);
		if(member == null) {
			return false;
		}
		String sql = "update theatre_ticket.member "
				+ "set account = account + ? "
				+ "where member_id = ? ";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setBigDecimal(1, amount);
			pstmt.setString(2, memberId);
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

	public boolean purchaseByMember(String title, String screening, Integer seat, String memberId, Double discount) {
		if(discount == null || discount < 0) discount = 0.0;
		if(discount > 1) discount = 1.0;
		SearchShowDAO searchShowDao = new SearchShowDAO();
		Show show = searchShowDao.getShow(title, screening);
		if(show == null) {//check show exist
			return false;
		}
		Member member = getMember(memberId);
		if(member == null) {//check member exist
			return false;
		}
		if(seat < 1 || seat > show.getSeatNum().intValue()) {//check seat in [1,2,3,...,seat_num]
			return false;
		}
		BigDecimal price = show.getPrice().multiply(BigDecimal.valueOf(1.0 - discount));
		String sqlInsertTicket = "insert into theatre_ticket.ticket(title,screening,seat,price,member_id,state) values(?,?,?,?,?,1) ";
		String sqlQueryTicket = "select count(1) from theatre_ticket.ticket where title = ? and screening = ? and seat = ? and (state = 1 or state = 0) ";
		String sqlUpdateMember = "update theatre_ticket.member set account = account - ?, consumption = consumption + ? where member_id = ? ";
		String sqlQueryMember = "select * from theatre_ticket.member where member_id = ? ";
		boolean isSuccess = false;
		try {
			conn.setAutoCommit(false);

			PreparedStatement pstmtInsertTicket = conn.prepareStatement(sqlInsertTicket);
			pstmtInsertTicket.setString(1, title);
			pstmtInsertTicket.setString(2, screening);
			pstmtInsertTicket.setInt(3, seat);
			pstmtInsertTicket.setBigDecimal(4, price);
			pstmtInsertTicket.setString(5, memberId);
			PreparedStatement pstmtQueryTicket = conn.prepareStatement(sqlQueryTicket);
			pstmtQueryTicket.setString(1, title);
			pstmtQueryTicket.setString(2, screening);
			pstmtQueryTicket.setInt(3, seat);
			PreparedStatement pstmtUpdateMember = conn.prepareStatement(sqlUpdateMember);
			pstmtUpdateMember.setBigDecimal(1, price);
			pstmtUpdateMember.setBigDecimal(2, price);
			pstmtUpdateMember.setString(3, memberId);
			PreparedStatement pstmtQueryMember = conn.prepareStatement(sqlQueryMember);
			pstmtQueryMember.setString(1, memberId);

			//pstmtInsertTicket
			int countInsertTicket = pstmtInsertTicket.executeUpdate();
			if(countInsertTicket == 1) {
				isSuccess = true;
			}
			
			//pstmtQueryTicket
			if(isSuccess) {
				isSuccess = false;
				ResultSet rsQueryTicket = pstmtQueryTicket.executeQuery();
				if(rsQueryTicket.next()) {
					int resultQueryTicket = rsQueryTicket.getInt(1);
					if(resultQueryTicket == 1) {
						isSuccess = true;
					}
				}
			}
			
			//pstmtUpdateMember
			if(isSuccess) {
				isSuccess = false;
				int countUpdateMember = pstmtUpdateMember.executeUpdate();
				if(countUpdateMember == 1) {
					isSuccess = true;
				}
			}
			
			//pstmtQueryMember
			if(isSuccess) {
				isSuccess = false;
				ResultSet rsQueryMember = pstmtQueryMember.executeQuery();
				if(rsQueryMember.next()) {
					Member memberQuery = new Member();
					memberQuery.setAll(rsQueryMember);
					if(memberQuery.getAccount().compareTo(BigDecimal.valueOf(0)) >= 0) {
						isSuccess = true;
					}
				}
			}
			
		} catch (Exception e) {
			isSuccess = false;
			e.printStackTrace();
		} finally {
			try {
				if(isSuccess) {
					conn.commit();
				}
				else {
					conn.rollback();
				}
				conn.setAutoCommit(true);
			} catch (Exception e) {
				isSuccess = false;
				e.printStackTrace();
			}
		}
		return isSuccess;
	}
	
}
