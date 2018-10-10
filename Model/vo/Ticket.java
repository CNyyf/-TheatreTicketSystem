package vo;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ticket {

	private Long ticketId;
	private String title;
	private String screening;
	private Integer seat;
	private BigDecimal price;
	private String memberId;
	private Integer state;
	
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getScreening() {
		return screening;
	}
	public void setScreening(String screening) {
		this.screening = screening;
	}
	public Integer getSeat() {
		return seat;
	}
	public void setSeat(Integer seat) {
		this.seat = seat;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public void setAll(ResultSet rs) throws SQLException{
		try {
			this.setTicketId(rs.getLong("ticket_id"));
			this.setTitle(rs.getString("title"));
			this.setScreening(rs.getString("screening"));
			this.setSeat(rs.getInt("seat"));
			this.setPrice(rs.getBigDecimal("price"));
			this.setMemberId(rs.getString("member_id"));
			this.setState(rs.getInt("state"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(this.getClass() != obj.getClass()) return false;
		Ticket ticket = (Ticket) obj;
		return ((ticketId == null && ticket.getTicketId() == null) || ticketId.equals(ticket.getTicketId())) &&
				((title == null && ticket.getTitle() == null) || title.equals(ticket.getTitle())) &&
				((screening == null && ticket.getScreening() == null) || screening.equals(ticket.getScreening())) &&
				((seat == null && ticket.getSeat() == null) || seat.equals(ticket.getSeat())) &&
				((price == null && ticket.getPrice() == null) || price.equals(ticket.getPrice())) &&
				((memberId == null && ticket.getMemberId() == null) || memberId.equals(ticket.getMemberId())) &&
				((state == null && ticket.getState() == null) || state.equals(ticket.getState()));
	}
	
}
