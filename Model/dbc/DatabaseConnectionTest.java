package dbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class DatabaseConnectionTest extends TestCase {

	private DatabaseConnection dbc;
	private ResultSet rs;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
    	dbc = new DatabaseConnection();
    	Connection conn = dbc.getConnection();
    	System.out.println("conn success");
    	String sql = "SELECT * FROM theatre_ticket.member WHERE member_id = ?";
    	PreparedStatement pstmt = conn.prepareStatement(sql);
    	pstmt.setString(1, "A0001");
    	rs = pstmt.executeQuery();
    	rs.next();
	}

	@After
	public void tearDown() throws Exception {
		rs.close();
		dbc.close();
		super.tearDown();
	}

	@Test
	public void test() throws SQLException {
		assertEquals("A0001", rs.getString("member_id"));
		assertEquals(BigDecimal.valueOf(100000, 2), rs.getBigDecimal("account"));
		assertEquals(BigDecimal.valueOf(500000, 2), rs.getBigDecimal("consumption"));
	}

}
