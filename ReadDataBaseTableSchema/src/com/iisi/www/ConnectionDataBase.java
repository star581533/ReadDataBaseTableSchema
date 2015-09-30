package com.iisi.www;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDataBase {

	/* mysql fileupload */
	private static final String USER_NAME = "root";	
	private static final String IP = "localhost";
	private static final String PORT = "3306";
	private static final String PASSWORD = "123456";	
	private static final String JDBC_NAME = "jdbc:mysql://";	
	private static final String DB_NAME = "fileupload";
	
	
		
	public static Connection connect(){
		Connection conn = null;
		try{
			//use Mysql
			String url = JDBC_NAME + IP + ":" + PORT + "/" + DB_NAME + "?user=" + USER_NAME + "&password=" + PASSWORD;
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(url);
		}catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}
}
