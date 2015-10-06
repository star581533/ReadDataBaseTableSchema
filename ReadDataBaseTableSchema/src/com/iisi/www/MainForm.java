package com.iisi.www;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class MainForm extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel ipLab = new JLabel("IP：");
	
	private JLabel portLab = new JLabel("PORT：");
	
	private JLabel userIdLab = new JLabel("USER ID：");
	
	private JLabel passwordLab = new JLabel("PASSWORD：");
	
	private JLabel dbLab = new JLabel("DB Name:");
	
	private JLabel srcLab = new JLabel("樣板來源：");
	
	private JLabel outputLab = new JLabel("產出路徑：");
	
	private JLabel xlsxLab = new JLabel("Excel對應資料表名稱：");
	
	private JFileChooser sourceChooser = new JFileChooser();
	
	private JFileChooser outputChooser = new JFileChooser();
	
	private JFileChooser xlsxChooser = new JFileChooser();
	
	private JButton sourceBtn = new JButton("選擇");
	
	private JButton outputBtn = new JButton("選擇");
	
	private JButton xlsxBtn = new JButton("選擇Excel");
	
	private JButton printBtn = new JButton("製產Word");
	
	private JTextField ipText = new JTextField(50);
	
	private JTextField portText = new JTextField(10);
	
	private JTextField userIdText = new JTextField(100);
	
	private JTextField srcText = new JTextField(100);
	
	private JTextField outputText = new JTextField(100);
	
	private JTextField xlsxText = new JTextField(100);
	
	private JPasswordField passwordText = new JPasswordField(100);	
	
	private JComboBox db = new JComboBox();
	
	private static final Dimension DR_SIZE = new Dimension(700, 540);

	
	public MainForm(){
		this.createPanel();		
		sourceBtn.addActionListener(this);
		outputBtn.addActionListener(this);
		xlsxBtn.addActionListener(this);
		printBtn.addActionListener(this);
	}
	
	
	public static void main(String args[]){
		EventQueue.invokeLater(new Runnable() {			
			public void run() {
				(new MainForm()).setVisible(true);;
			}
		});
	}
	
	private JPanel createPanel(){
		JPanel setPanel = new JPanel();
		setPanel.setLayout(null);
		
		ipLab.setBounds(20, 20, MainFormUtils.INT_70, MainFormUtils.INT_20);
		setPanel.add(ipLab);
		
		ipText.setBounds(100, 20, MainFormUtils.INT_100, MainFormUtils.INT_20);
		setPanel.add(ipText);
		
		portLab.setBounds(210, 20, MainFormUtils.INT_50, MainFormUtils.INT_20);
		setPanel.add(portLab);
		
		portText.setBounds(310, 20, MainFormUtils.INT_70, MainFormUtils.INT_20);
		setPanel.add(portText);
		
		dbLab.setBounds(400, 20, MainFormUtils.INT_70, MainFormUtils.INT_20);
		setPanel.add(dbLab);
		
		db.setBounds(480, 20, MainFormUtils.INT_100, MainFormUtils.INT_20);
		setDbBox(db);
		setPanel.add(db);
		
		userIdLab.setBounds(20, 50, MainFormUtils.INT_70, MainFormUtils.INT_20);
		setPanel.add(userIdLab);
		
		userIdText.setBounds(100, 50, MainFormUtils.INT_100, MainFormUtils.INT_20);
		setPanel.add(userIdText);
		
		passwordLab.setBounds(210, 50, MainFormUtils.INT_100, MainFormUtils.INT_20);
		setPanel.add(passwordLab);
		
		passwordText.setBounds(310, 50, MainFormUtils.INT_70, MainFormUtils.INT_20);
		setPanel.add(passwordText);	
		
		srcLab.setBounds(20, 80, MainFormUtils.INT_70, MainFormUtils.INT_20);
		setPanel.add(srcLab);
		
		srcText.setBounds(100, 80, MainFormUtils.INT_300, MainFormUtils.INT_20);
		setPanel.add(srcText);
		
		sourceBtn.setBounds(400, 80, MainFormUtils.INT_70, MainFormUtils.INT_20);
		setPanel.add(sourceBtn);
		
		outputLab.setBounds(20, 110, MainFormUtils.INT_70, MainFormUtils.INT_20);
		setPanel.add(outputLab);
		
		outputText.setBounds(100, 110, MainFormUtils.INT_300, MainFormUtils.INT_20);
		setPanel.add(outputText);
		
		outputBtn.setBounds(400, 110, MainFormUtils.INT_70, MainFormUtils.INT_20);
		setPanel.add(outputBtn);
		
		xlsxLab.setBounds(20, 140, MainFormUtils.INT_150, MainFormUtils.INT_20);
		setPanel.add(xlsxLab);
		
		xlsxText.setBounds(160, 140, MainFormUtils.INT_300, MainFormUtils.INT_20);
		setPanel.add(xlsxText);
		
		xlsxBtn.setBounds(460, 140, MainFormUtils.INT_70, MainFormUtils.INT_20);
		setPanel.add(xlsxBtn);
		
		printBtn.setBounds(300, 170, MainFormUtils.INT_100, MainFormUtils.INT_20);
		setPanel.add(printBtn);	
		
		setSize(DR_SIZE);
		setTitle("Create Doc Table");
		
		//將Panel加入Frame中
		add(setPanel);
		
		return setPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		
		if(btn.equals(sourceBtn)){
			sourceChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			sourceChooser.addChoosableFileFilter(MainFormUtils.docFilter());
			//將檔案類型，所有檔案選項取消
			sourceChooser.setAcceptAllFileFilterUsed(false);
			int show = sourceChooser.showOpenDialog(this);		
			if(show == JFileChooser.APPROVE_OPTION){
				srcText.setText(sourceChooser.getSelectedFile().getPath());	
			}			 			
		}else if(btn.equals(outputBtn)){
			//只選擇資料夾
			outputChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int show = outputChooser.showOpenDialog(this);
			if(show == JFileChooser.APPROVE_OPTION){
				outputText.setText(outputChooser.getSelectedFile().getPath()); 
			}
		}else if(btn.equals(xlsxBtn)){
			xlsxChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			//只選Excel檔
			xlsxChooser.addChoosableFileFilter(MainFormUtils.xlsFilter());
			xlsxChooser.addChoosableFileFilter(MainFormUtils.xlsxFilter());
			xlsxChooser.setAcceptAllFileFilterUsed(false);
			
			int show = xlsxChooser.showOpenDialog(this);
			if(show == JFileChooser.APPROVE_OPTION){
				xlsxText.setText(xlsxChooser.getSelectedFile().getPath()); 
			}
		}else if(btn.equals(printBtn)){			
			MustInputModel model = new MustInputModel();
			model.setIp(ipText.getText());
			model.setPort(portText.getText());
			model.setUserId(userIdText.getText());
			model.setPassword(passwordText.getPassword());
			model.setSrc(srcText.getText());
			model.setOutput(outputText.getText());
			model.setXlsx(xlsxText.getText());
			model.setDbName(String.valueOf(db.getItemAt(db.getSelectedIndex())));
			model.setLevel(getLevel(model.getDbName()));
						
			MainFormUtils.verifyColumn(this, model.getIp(), "請輸入IP");
			MainFormUtils.verifyColumn(this, model.getPort(), "請輸入PORT");
			MainFormUtils.verifyColumn(this, model.getDbName(), "請輸入DB Name");
			MainFormUtils.verifyColumn(this, model.getUserId(), "請輸入USER ID");
			MainFormUtils.verifyColumnCharArr(this, model.getPassword(), "請輸入PASSWORD");
			MainFormUtils.verifyColumn(this, model.getSrc(), "請輸入樣板來源");
			MainFormUtils.verifyColumn(this, model.getOutput(), "請輸入產出路徑");
			MainFormUtils.verifyColumn(this, model.getXlsx(), "請輸入Excel對應資料表名稱");
			
			Connection conn = ConnectionDataBase.connect(model);
			MainFormUtils.verifyConnection(this, conn, "資料庫連線有誤");
			
			System.out.println("開始製產Word");
			ReadTableColumnSchema tableColSchema = new ReadTableColumnSchema();
			tableColSchema.start(conn, model);
			System.out.println("結束製產Word");
		}		
	}
	
	private void setDbBox(JComboBox box){
		box.addItem("chun0000");
		box.addItem("teun0000");
		box.addItem("teun0020");
	}
	
	private String getLevel(String str){
		String rtn = "";
		if(str.equals("chun0000")){
			rtn = MainFormUtils.RC;
		}else if(str.equals("teun0000")){
			rtn = MainFormUtils.RR;
		}else if(str.equals("teun0020")){
			rtn = MainFormUtils.RL;
		}
		return rtn;
	}
}
