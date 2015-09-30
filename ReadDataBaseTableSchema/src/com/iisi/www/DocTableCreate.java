package com.iisi.www;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class DocTableCreate {
	
	

	
	public void createDoc(String template, String output, List<String[]> columns, Map<String, String> titleMap, String tableName){
		try{
			File templateFile =  new File(template);
			InputStream isTemplate = new FileInputStream(templateFile);
			XWPFDocument docTemplate = new XWPFDocument(isTemplate);			
			setTableRow(docTemplate.getTables(), columns);	
			
//			Map<String, Object> params = setParams(tableName, titleMap);			
//			DocInfoUtils.replaceInParams(docTemplate, params);
//			DocInfoUtils.replaceInTable(docTemplate, params);			
			
			OutputStream os = new FileOutputStream(output);
			docTemplate.write(os);
			DocInfoUtils.close(os);
			DocInfoUtils.close(isTemplate);			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private Map<String, Object> setParams(String tableName, Map<String, String> titleMap){
		Map<String, Object> params = new HashMap<String, Object>();
		String title = DocInfoUtils.getTitle(tableName, titleMap);
		System.out.println("title = " + title);
		params.put("title", title);
		params.put("table", title);
		params.put("tableName", title+tableName);
		return params;
	}
	
	private void setTableRow(List<XWPFTable> tables, List<String[]> columns){
		for(XWPFTable table : tables){				
			for(String[] arr : columns){
				if(arr != null && arr.length > 0){
					//row
					XWPFTableRow tableRow = table.createRow();
		   			for(int i=0; i<arr.length;i++){
		   				String text = arr[i].trim();
	   					XWPFTableCell cell = tableRow.getCell(i);
	   					//欄位不存在時，就新增
	   					if(cell != null){
	   						tableRow.getCell(i).setText(text);	
	   					}else{
	   						tableRow.createCell().setText(text);	
	   					}
		   				tableRow.setHeight(500);   				
		   			}
				}													
	   		}
		}
	}
}
