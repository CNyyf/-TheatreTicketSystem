package dao;

import java.sql.Connection;

import dbc.DatabaseConnection;

public class baseDAO {

	protected DatabaseConnection dbc = null;
	protected Connection conn = null;

	public baseDAO() {
		try {
			dbc = new DatabaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		conn = dbc.getConnection();
	}
	
	public void close() {
		try {
			if(dbc != null) {
				dbc.close();
				dbc = null;
			}
			conn = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void finalize() throws Throwable{
		this.close();
		super.finalize();
	}
	
}
