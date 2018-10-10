package dbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static final String DBDRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Shanghai";
	private static final String DBuser = "theatre_admin";
	private static final String DBPASSWORD = "1234";
	private Connection conn = null;
	
	public DatabaseConnection() throws Exception {
		try {
			Class.forName(DBDRIVER);
			conn = DriverManager.getConnection(DBURL, DBuser, DBPASSWORD);
		} catch(ClassNotFoundException e) {
			System.out.println("jdbc Driver cannot found.");
			e.printStackTrace();
			throw e;
		} catch(SQLException e) {
			System.out.println("DB connection failed.");
			e.printStackTrace();
			throw e;
		}
	}

    public Connection getConnection() {
        return this.conn;
    }

    public void close() throws Exception {
        if (this.conn != null) {
            this.conn.close();
        }
    }
}
