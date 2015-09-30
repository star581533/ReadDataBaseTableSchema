package com.iisi.www;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 讀取資料庫中Table資訊
 * @author 1104611
 * 取得主鍵：http://www.cnblogs.com/lbangel/p/3487796.html
 */
public class ReadTableColumnSchema {
	
	private Connection conn;
		
	public static void main(String args[]){
		new ReadTableColumnSchema().start();
//		new ReadTableColumnSchema().read();
		new ReadTableColumnSchema().end();
	}
	
	
	public void start(){
		conn = ConnectionDataBase.connect();
		TableWork tableWork = new TableWork();
		tableWork.doWork(conn);			
	}
	
	
			
	public void end(){
		System.out.println("Close DataBase");
		try{
			if(conn != null){
				conn.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
