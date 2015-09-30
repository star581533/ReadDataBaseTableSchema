package com.iisi.www;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TableWork {
	
	private Statement stmt;
	
	//資料庫整體綜合資訊
	private DatabaseMetaData dbma;
	
	private String catalog = null;
	
	private ResultSet tableRs;
	
	private DocTableCreate createTableDoc;
	
	public void doWork(Connection conn){
		try{			
			stmt = conn.createStatement();
			dbma = conn.getMetaData();
			
			createTableDoc = new DocTableCreate();
			
//			System.out.println("Connected to URL : " + dbma.getURL());
			System.out.println("Driver is : " + dbma.getDriverName());
			
			System.out.println("===================================");
			
			Map<String, String> titleMap = getXlsxTitleMap("D:\\docTest\\TableList.xlsx", "RC");
			
			//%是取所有資料表
			tableRs = dbma.getTables(catalog, null, "%", null);
			
			catalog = conn.getCatalog();
			while(tableRs.next()){
				//取得資料表名稱
				String tableName = tableRs.getString("TABLE_NAME");

					System.out.println("Table Name : " + tableName);
					System.out.println("-----Column Names of table [stkid]------");
					//記錄欄位資訊
					Map<String, String> colMap =TableInfoUtils.getColumns(catalog, dbma, tableName);
					//記錄主鍵資訊
					Map<String, String> pkeyMap = TableInfoUtils.getPKey(catalog, dbma, tableName);
					//記錄索引資訊
					IdentityHashMap<String, String> indexMap = TableInfoUtils.getTableIndex(catalog, dbma, tableName);
					
					Iterator indexIter = indexMap.entrySet().iterator();
					//記錄組合索引資訊
					Map<String, String> indexTemp = new HashMap<String, String>();
					
					//組合索引
					while(indexIter.hasNext()){
						Map.Entry entry = (Map.Entry) indexIter.next();
						String indexCol = entry.getKey().toString();
						String indexName = entry.getValue().toString();						
						String tempIdxName = indexTemp.get(indexCol);						
						
						if(tempIdxName == null){
							indexTemp.put(indexCol, indexName);	
						}else{
							tempIdxName = tempIdxName + "," + indexName;
							indexTemp.put(indexCol, tempIdxName);
						}						
					}
					
//					List<DocOutputModel> models = new ArrayList<DocOutputModel>();
					
					List<String[]> arrays = new ArrayList<String[]>();
					
					Iterator colIter = colMap.entrySet().iterator();
					//索引、欄位組合
					while(colIter.hasNext()){
						Map.Entry entry = (Map.Entry) colIter.next();						
						String key = entry.getKey().toString();
						String value = entry.getValue().toString();
						
						String index = indexTemp.get(key);
						
						DocOutputModel model = setModel(index, value);						
						System.out.println(TableInfoUtils.nullToSpace(index) + "\t" + value);
						
						String[] array = TableInfoUtils.setArrayModel(index, value, DocOutputModel.class.getDeclaredFields().length);
						arrays.add(array);
//						models.add(model);
					}
					
					String docFile = "D:\\docTest" + File.separator + tableName.toUpperCase() + ".doc";
					
					createTableDoc.createDoc("D:\\docTest\\temp.doc", docFile , arrays, titleMap, tableName);
					
										
				}	
//			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private DocOutputModel setModel(String index, String value){
		String[] array = value.split(",");
		DocOutputModel model = new DocOutputModel();
		model.setKey(TableInfoUtils.nullToSpace(index));
		model.setColEnglish(array[0]);	
		model.setFormat(array[1]);
		model.setAllowNull(array[2]);		
		model.setColChinese("");
		model.setDefaultVal("");
		model.setRemark("");		
		return model;
	}
	
	private Map<String, String> getXlsxTitleMap(String fileName, String level){
		return XlsxRead.getXlsxData(fileName, level);
	}
	

}
