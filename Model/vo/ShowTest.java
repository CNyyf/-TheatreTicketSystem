package vo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dbc.DatabaseConnection;
import junit.framework.TestCase;

public class ShowTest extends TestCase {
	
	private DatabaseConnection dbc;
	private ResultSet rs;
	private Show show;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
    	dbc = new DatabaseConnection();
    	show = new Show();
    	Connection conn = dbc.getConnection();
    	System.out.println("conn success");
    	String sql = "SELECT * FROM theatre_ticket.show WHERE title = ? AND screening = ?";
    	PreparedStatement pstmt = conn.prepareStatement(sql);
    	pstmt.setString(1, "上海交大迎新晚会");
    	pstmt.setString(2, "A0001");
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
    	show.setAll(rs);
    	Show show_except = new Show();
		show_except.setTitle("上海交大迎新晚会");
		show_except.setScreening("A0001");
		show_except.setStartTime(Timestamp.valueOf("2018-10-10 18:00:00"));
		show_except.setPrice(BigDecimal.valueOf(10000, 2));
		show_except.setSeatNum(BigDecimal.valueOf(10000));;
		show_except.setState(1);
		assertEquals(show_except, show);
		assertEquals("上海交大迎新晚会", rs.getString("title"));
		assertEquals("A0001", rs.getString("screening"));
		assertEquals(Timestamp.valueOf("2018-10-10 18:00:00"), rs.getTimestamp("start_time"));
		assertEquals(BigDecimal.valueOf(10000, 2), rs.getBigDecimal("price"));
		assertEquals(BigDecimal.valueOf(10000), rs.getBigDecimal("seat_num"));
		assertEquals(1, rs.getInt("state"));
	}

}
