package vo;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Show {
	
	private String title;
	private String screening;
	private Timestamp startTime;
	private BigDecimal price;
	private BigDecimal seatNum;
	private Integer state;
	
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
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getSeatNum() {
		return seatNum;
	}
	public void setSeatNum(BigDecimal seatNum) {
		this.seatNum = seatNum;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public void setAll(ResultSet rs) throws SQLException{
		try {
			this.setTitle(rs.getString("title"));
			this.setScreening(rs.getString("screening"));
			this.setStartTime(rs.getTimestamp("start_time"));
			this.setPrice(rs.getBigDecimal("price"));
			this.setSeatNum(rs.getBigDecimal("seat_num"));
			this.setState(rs.getInt("state"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(this.getClass() != obj.getClass()) return false;
		Show show = (Show) obj;
		return ((title == null && show.getTitle() == null) || title.equals(show.getTitle())) &&
				((screening == null && show.getScreening() == null) || screening.equals(show.getScreening())) &&
				((startTime == null && show.getStartTime() == null) || startTime.equals(show.getStartTime())) &&
				((price == null && show.getPrice() == null) || price.equals(show.getPrice())) &&
				((seatNum == null && show.getSeatNum() == null) || seatNum.equals(show.getSeatNum())) &&
				((state == null && show.getState() == null) || state.equals(show.getState()));
	}
	
}
