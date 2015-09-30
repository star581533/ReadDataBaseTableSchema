package com.iisi.www;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class DocInfoUtils {
	
	public static void close(OutputStream os){
		if(os != null){
			try{
				os.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public static void close(InputStream is){
		if(is != null){
			try{
				is.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public static void replaceInTable(XWPFDocument doc, Map<String, Object> params){
		Iterator<XWPFTable> iterator = doc.getTablesIterator();
		XWPFTable table;
		List<XWPFTableRow> rows;
		List<XWPFTableCell> cells;
		List<XWPFParagraph> paras;
		while(iterator.hasNext()){
			table = iterator.next();
			rows = table.getRows();
			for(XWPFTableRow row : rows){
				cells = row.getTableCells();
				for(XWPFTableCell cell : cells){
					paras = cell.getParagraphs();
					for(XWPFParagraph para : paras){
						replaceInParams(para, params);
					}
				}
			}
		}
	}
	
	public static void replaceInParams(XWPFParagraph param, Map<String, Object> params){
		List<XWPFRun> runs;
		Matcher matcher;
		if(matcher(param.getParagraphText()).find()){
			runs = param.getRuns();
			for(int i=0;i <runs.size();i++){
				XWPFRun run = runs.get(i);
				String runText = run.toString();
				System.out.println("runText = " + runText + ", i = " + i);
				matcher = matcher(runText);
				if(matcher.find()){
					while((matcher = matcher(runText)).find()){
						runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))));
					}
					
					param.removeRun(i);
					param.insertNewRun(i).setText(runText);
				}
			}
		}
	}
	
	public static void replaceInParams(XWPFDocument doc, Map<String, Object> params){
		Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
		XWPFParagraph param;
		while(iterator.hasNext()){
			param = iterator.next();
			replaceInParams(param, params);
		}
	}
	
	private static Matcher matcher(String str){
		Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		return matcher;
	}
	
	public static String getTitle(String titleCode, Map<String, String> titleMap){
		String rtn = "";		
		try{
			String titleName = String.valueOf(titleMap.get(titleCode.toUpperCase()));
			if(titleName != null && titleName.length() > 0){
				rtn = titleName;
			}
		}catch(Exception e){
			return rtn;
		}			
		return rtn;
	}
}
