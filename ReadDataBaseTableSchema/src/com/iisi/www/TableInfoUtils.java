package com.iisi.www;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author 1104611
 * 取index：http://my.oschina.net/lison/blog/5434
 */
public class TableInfoUtils {

	/**
	 * 取得資料表內欄位名稱
	 * @param catalog
	 * @param dbma
	 * @param tableName
	 * @return Map<String, String>
	 */
	public static Map<String, String> getColumns(String catalog, DatabaseMetaData dbma, String tableName){
		Map<String, String> map = new HashMap<String, String>();
		try{			
			ResultSet columnRs = dbma.getColumns(catalog, null, tableName, null);
			while(columnRs.next()){
				//取得欄位名稱
				String columnName = columnRs.getString("COLUMN_NAME");
				//取得欄位型別
				String columnType = columnRs.getString("TYPE_NAME");
				//取得位長度
				String columnSize = columnRs.getString("COLUMN_SIZE");				
				String nullable = columnRs.getString("IS_NULLABLE").trim(); 
				String columnData = columnName + "," + columnType + "("+ columnSize + ")" + "," + checkIsNull(nullable);
//				System.out.println("[" + columnData + "]");
				map.put(columnName, columnData);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}	
		
		return map;
	}
	
	/**
	 * 檢查資料是否允許null
	 * @param nullable String
	 * @return N or " "
	 */
	private static String checkIsNull(String nullable){
		String rtn = " ";
		if(nullable != null && nullable.length() > 0){
			if(nullable.equals("NO")){
				rtn = "N";
			}
		}
		return rtn;
	}
	
	/**
	 * 取得資料表主鍵
	 * @param catalog
	 * @param dbma
	 * @param tableName
	 * @return Map<String, String>
	 */
	public static Map<String, String> getPKey(String catalog, DatabaseMetaData dbma, String tableName){
		Map<String, String> map = new HashMap<String, String>();
		try{
			ResultSet pkeyRs = dbma.getPrimaryKeys(catalog, null, tableName);
			while(pkeyRs.next()){
				String pkey = pkeyRs.getString("PK_NAME");
				String pKeyCol = pkeyRs.getString("COLUMN_NAME");
				String pkeyData = pkey + "," + pKeyCol;
				map.put(pkey, pkeyData);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 取得資料表外來鍵
	 * @param catalog
	 * @param dbma
	 * @param tableName
	 */
	public static void getForeignKey(String catalog, DatabaseMetaData dbma, String tableName){
		try{
//			ResultSet foreignKeyRs = dbma.getImportedKeys(catalog, null, tableName);
			ResultSet foreignKeyRs = dbma.getExportedKeys(catalog, null, tableName);
			while(foreignKeyRs.next()){
				String fkColumnName = foreignKeyRs.getString("FKCOLUMN_NAME");
				Short keySeq = foreignKeyRs.getShort("KEY_SEQ");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	/**
	 * 取得資料表索引值
	 * @param catalog
	 * @param dbma
	 * @param tableName
	 * @return
	 */
	public static IdentityHashMap<String, String> getTableIndex(String catalog, DatabaseMetaData dbma, String tableName){
		//利用IdentityHashMap可紀錄相同的Key但不同的Value
		IdentityHashMap<String, String> map = new IdentityHashMap<String, String>();
		try{
			ResultSet indexRs = dbma.getIndexInfo(catalog, null, tableName, true, true);
			while(indexRs.next()){
				String indexName = indexRs.getString("INDEX_NAME");
				String columnName = indexRs.getString("COLUMN_NAME");								
				map.put(columnName, indexName);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return map;
	}
	
	/**
	 * 將null取轉成""
	 * @param str
	 * @return
	 */
	public static String nullToSpace(String str){
		String rtn = str;
		if(str == null){
			rtn = "";
		}		
		return rtn;
	}
	
	/**
	 * 印出Map資料
	 * @param map
	 */
	public static void printMapData(Map<String, String> map){
		Iterator iter = map.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry entry = (Map.Entry)iter.next();
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			System.out.println("key = " + key + ", value = " + value);
		}
	}
	
	/**
	 * 將指定資料填入
	 * @param index
	 * @param value
	 * @param size
	 * @return
	 */
	public static String[] setArrayModel(String index, String value, int size){
		String[] rtn = new String[size];
		String[] valArr = value.split(",");
		for(int num=0; num<size; num++){
			switch(num){
				case 0:
					String str = TableInfoUtils.nullToSpace(index);
					if(str.indexOf("_") > -1){
						rtn[num] = splitKey(str);
					}else{
						rtn[num] = str;
					}
					break;
				case 1:
					rtn[num] = valArr[0];
					break;
				case 3:
					rtn[num] = valArr[1];
					break;
				case 5:
					rtn[num] = valArr[2];
					break;
				default:
					rtn[num] = "";
					break;
			}
		}
		
		return rtn;
	}
	
	/**
	 * 分開字串中多個PKey或Index
	 * @param value
	 * @return
	 */
	public static String splitKey(String value){
		String rtn = "";
		String[] arr = value.split(",");		
		
		for(int i=0;i<arr.length; i++){
			String str = arr[i];
			String key = str.substring(str.indexOf("_")+1, str.lastIndexOf("_"));
			if(i>0){
				rtn = rtn + keyUseName(key.toUpperCase());
			}else{
				rtn = keyUseName(key.toUpperCase());
			}			
			if(arr.length > 1){
				rtn = rtn + "\n";
			}
		}		
		return rtn;
	}
	
	/**
	 * 取得PKey或Index完整名稱
	 * @param name String
	 * @return String
	 */
	private static String keyUseName(String name){
		String rtn = "";
		if(name.toLowerCase().equals("p") || name.toUpperCase().equals("P")){
			rtn = "PK";
		}else if(name.indexOf("s") > -1 || name.indexOf("S") > -1){
			rtn = name.replace("S", "INDEX");
		}
		return rtn;
	}
}
