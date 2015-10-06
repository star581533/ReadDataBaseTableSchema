package com.iisi.www;

import java.awt.Component;
import java.sql.Connection;

import javax.swing.JOptionPane;

public class MainFormUtils {

	public static final int INT_20 = 20;
	
	public static final int INT_50 = 50;
	
	public static final int INT_70 = 70;
	
	public static final int INT_100 = 100;
	
	public static final int INT_150 = 150;
	
	public static final int INT_200 = 200;
	
	public static final int INT_300 = 300;

	public static final String RC = "RC";
	
	public static final String RR = "RR";
	
	public static final String RL = "RL";
	
	public static FileTypeFilter docFilter(){
		FileTypeFilter docFilter = new FileTypeFilter(".doc", "Microsoft Word Documents");

		return docFilter;
	}
	
	public static FileTypeFilter docxFilter(){
		FileTypeFilter docxFilter = new FileTypeFilter(".docx", "Microsoft Word Documents");
		return docxFilter;
	}
	
	public static FileTypeFilter xlsFilter(){
		FileTypeFilter xlsFilter = new FileTypeFilter(".xls", "Microsoft Excel Documents");
		return xlsFilter;
	}
	
	public static FileTypeFilter xlsxFilter(){
		FileTypeFilter xlsxFilter = new FileTypeFilter(".xlsx", "Microsoft Excel Documents");
		return xlsxFilter;
	}
	
	public static void verifyColumn(Component component, String column, String message){
		if(column == null || column.length() == 0){
			JOptionPane.showMessageDialog(component, message, "錯誤", JOptionPane.ERROR_MESSAGE);
			throw new RuntimeException(message);
		}
	}
	
	public static void verifyColumnCharArr(Component component, char[] column, String message){
		if(column == null || column.length == 0){
			JOptionPane.showMessageDialog(component, message, "錯誤", JOptionPane.ERROR_MESSAGE);
			throw new RuntimeException(message);
		}
	}
	
	public static void verifyConnection(Component component, Connection conn, String message){
		if(conn == null){
			setMessage(component, message, "錯誤", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private static void setMessage(Component component, String message, String title, int msgInt){
		JOptionPane.showMessageDialog(component, message, title, msgInt);
		throw new RuntimeException(message);
	}
	
}
