package vo;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Member {
	
	private String memberId;
	private BigDecimal account;
	private BigDecimal consumption;
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public BigDecimal getAccount() {
		return account;
	}
	public void setAccount(BigDecimal account) {
		this.account = account;
	}
	public BigDecimal getConsumption() {
		return consumption;
	}
	public void setConsumption(BigDecimal consumption) {
		this.consumption = consumption;
	}

	public void setAll(ResultSet rs) throws SQLException{
		try {
			this.setMemberId(rs.getString("member_id"));
			this.setAccount(rs.getBigDecimal("account"));
			this.setConsumption(rs.getBigDecimal("consumption"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(this.getClass() != obj.getClass()) return false;
		Member member = (Member) obj;
		return ((memberId == null && member.getMemberId() == null) || memberId.equals(member.getMemberId())) &&
				((account == null && member.getAccount() == null) || account.equals(member.getAccount())) &&
				((consumption == null && member.getConsumption() == null) || consumption.equals(member.getConsumption()));
	}
	
}
