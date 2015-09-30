package com.iisi.www;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class XlsxRead {

	public static void main(String args[]){
		new XlsxRead().read("D:\\docTest\\TableList.xlsx");
	}
	
	private void read(String fileName){
//		 Workbook book = getExcelWorkbook(fileName);
//		 getAppointSheetName(book, "RC");
		 Map map = readXlsx(fileName, "RC");
		 TableInfoUtils.printMapData(map);
	}
	
	public static Map<String, String> getXlsxData(String fileName, String level){
		 Map<String, String> map = readXlsx(fileName, "RC");
		 return map;
	}
	
	
	private static Sheet getSheetByNum(Workbook book, int number){
		Sheet sheet = null;
		try{
			sheet = book.getSheetAt(number);
		}catch(Exception e){
			e.printStackTrace();
		}
		return sheet;
	}
	
	private String getSheetByName(Workbook book, int number){
		String sheetName = "";
		try{
			sheetName = book.getSheetName(number);
		}catch(Exception e){
			e.printStackTrace();
		}
		return sheetName;
	}
	
	private static int getAppointSheetNum(Workbook book, String level){
		int rtn = 0;
		try{
			for(int num = 0; num < book.getNumberOfSheets() ; num++){	
				String sheetName = book.getSheetName(num);
				if(sheetName.indexOf(level) > -1){
					System.out.println("sheetName = " + sheetName + ", num = " + num);
					rtn = num;
				}				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return rtn;
	}
	
	private static Workbook getExcelWorkbook(String filePath){
		Workbook book = null;
		File file = null;
		FileInputStream fis = null;
		
		try{
			file = new File(filePath);
			if(!file.exists()){
				throw new RuntimeException("文件不存在");
			}else{
				fis = new FileInputStream(file);
				book = WorkbookFactory.create(fis);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return book;
	}
	
	private static Map<String,String> readXlsx(String fileName, String level){
		Map<String,String> map = new HashMap<String,String>();
		Workbook book = null;
		book = getExcelWorkbook(fileName);
		int num = getAppointSheetNum(book, level);
		Sheet sheet = getSheetByNum(book, num);
				
		int lastRowNum = sheet.getLastRowNum();
				
		for(int i=0;i <= lastRowNum; i++){
			Row row = null;
			row = sheet.getRow(i);
			if(row != null){
				int lastCellNum = row.getLastCellNum();
				Cell cell = null;
				String[] arr = new String[2];
				for(int j=0; j<=lastCellNum; j++){
					cell = row.getCell(j);
					if(cell != null && cell.getStringCellValue().length() > 0){
						String cellValue = cell.getStringCellValue();
						switch(j){
							case 2:
								arr[0] = cellValue;
								break;
							case 3:
								arr[1] = cellValue;
								break;
							default: 
								break;
						}
					}
				}
				String key = arr[0];
				String value = arr[1];
				if((key != null && key.length() > 0) && (value != null && value.length() > 0)){
					map.put(key, value);	
				}				
			}		
		}
		
		return map;
	}
	
}
