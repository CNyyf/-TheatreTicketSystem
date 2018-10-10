package vo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dbc.DatabaseConnection;
import junit.framework.TestCase;

public class TicketTest extends TestCase {
	
	private DatabaseConnection dbc;
	private ResultSet rs;
	private Ticket ticket;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
    	dbc = new DatabaseConnection();
    	ticket = new Ticket();
    	Connection conn = dbc.getConnection();
    	System.out.println("conn success");
    	String sql = "SELECT * FROM theatre_ticket.ticket WHERE ticket_id = ?";
    	PreparedStatement pstmt = conn.prepareStatement(sql);
    	pstmt.setInt(1, new Integer(1));
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
    	ticket.setAll(rs);
		Ticket ticket_except = new Ticket();
		ticket_except.setTicketId(1l);
		ticket_except.setTitle("上海交大迎新晚会");
		ticket_except.setScreening("A0001");
		ticket_except.setSeat(1);
		ticket_except.setPrice(BigDecimal.valueOf(10000, 2));
		ticket_except.setMemberId(null);
		ticket_except.setState(1);
		assertEquals(ticket_except, ticket);
		//TO DO
	}

}
