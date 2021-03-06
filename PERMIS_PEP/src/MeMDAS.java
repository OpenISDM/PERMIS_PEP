/*
 * 主畫面和相關的操作
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MeMDAS  implements ListSelectionListener, ActionListener{

	public static JTextArea recordText = new JTextArea();
	public static JTextArea controlText = new JTextArea();
	
	JLabel imagelJLabel;
	JLabel nurseNameLabel = new JLabel("Name: ");
	JLabel nurseSexLabel = new JLabel("Sex: ");
	JLabel nursePositionLabel = new JLabel("position:: ");
	JLabel nurseDutyLabel = new JLabel("Duty: ");
	JLabel nurseStatusLabel = new JLabel("Status: ");
	JButton handoverButton = new JButton("交班");
	JButton changeDutyButton = new JButton("指定責任病房");
	JButton modifyPatientRecordButton = new JButton("修改病歷資料");
	JFrame frame = new JFrame("MeMDAS");
	
	DefaultListModel<String> patientModel = new DefaultListModel<String>();
	JList<String> patientList = new JList<String>(patientModel);
	Nurse nurse;// = new Nurse();
	
	public void MeMDASGUI(final Nurse nurse){
		this.nurse = nurse;
		
		NurseRoleLog();
		//宣告 Layout
		SpringLayout mainLayout = new SpringLayout();
		SpringLayout recordLayout = new SpringLayout();
		
		frame.setSize(1000, 800);
		frame.setLayout(mainLayout);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//宣告主要的fram、pannel
		JPanel recordPannel = new JPanel(recordLayout);
		JPanel nursePannel = new JPanel(new GridLayout(2, 3, 20, 20));
		JPanel dutyJPanel = new JPanel(new GridLayout(3, 1, 10, 10));
		JScrollPane controlTextScroll = new JScrollPane(controlText);
		JScrollPane recordTextScroll = new JScrollPane(recordText);
		JScrollPane patientListScroll = new JScrollPane(patientList);
		
		//設定背景色、邊框和字型
		nursePannel.setBorder(BorderFactory.createLineBorder(Color.decode("#2C6791")));
		patientList.setBorder(BorderFactory.createLineBorder(Color.decode("#2C6791")));
		patientList.setBackground(Color.lightGray);
		recordText.setBackground(Color.white);
		recordText.setEditable(false);				//初始變數宣告

		//加入護士表單欄位
		nursePannel.add(nurseNameLabel);
		nursePannel.add(nurseSexLabel);
		nursePannel.add(nursePositionLabel);
		nursePannel.add(nurseDutyLabel);
		nursePannel.add(nurseStatusLabel);
		
		//加入Duty Button
		dutyJPanel.add(handoverButton);
		dutyJPanel.add(changeDutyButton);

		//寫入護士個人資料
		showNurseInfo();

		//寫入病人名單
		showPatientList();
		
		//選單的Event和Action
		patientList.addListSelectionListener(this);
		
		//送出病歷資料
		modifyPatientRecordButton.addActionListener(this);
		handoverButton.addActionListener(this);
		changeDutyButton.addActionListener(this);
		
		//右邊主要 panel 排版設定
		frame.add(nursePannel);
		mainLayout.putConstraint(SpringLayout.EAST, nursePannel, -200, SpringLayout.EAST, frame.getContentPane());
		mainLayout.putConstraint(SpringLayout.WEST, nursePannel, 10, SpringLayout.EAST, imagelJLabel);
		mainLayout.putConstraint(SpringLayout.SOUTH, nursePannel, 150, SpringLayout.NORTH, frame.getContentPane());
		mainLayout.putConstraint(SpringLayout.NORTH, nursePannel, 15, SpringLayout.NORTH, frame.getContentPane());	//最北邊基準線
		frame.add(dutyJPanel);
		mainLayout.putConstraint(SpringLayout.EAST, dutyJPanel, 0, SpringLayout.EAST, recordPannel);
		mainLayout.putConstraint(SpringLayout.WEST, dutyJPanel, 10, SpringLayout.EAST, nursePannel);
		mainLayout.putConstraint(SpringLayout.SOUTH, dutyJPanel, 0, SpringLayout.SOUTH, nursePannel);
		mainLayout.putConstraint(SpringLayout.NORTH, dutyJPanel, 0, SpringLayout.NORTH, nursePannel);
		frame.add(recordPannel);
		mainLayout.putConstraint(SpringLayout.EAST, recordPannel, -15, SpringLayout.EAST, frame.getContentPane());	//最東邊基準線
		mainLayout.putConstraint(SpringLayout.WEST, recordPannel, 0, SpringLayout.WEST, nursePannel);
		mainLayout.putConstraint(SpringLayout.SOUTH, recordPannel, -15, SpringLayout.SOUTH, frame.getContentPane());	//最南邊基準線
		mainLayout.putConstraint(SpringLayout.NORTH, recordPannel, 10, SpringLayout.SOUTH, nursePannel);
		recordPannel.add(controlTextScroll);
		recordLayout.putConstraint(SpringLayout.EAST, controlTextScroll, 0, SpringLayout.EAST, recordPannel);
		recordLayout.putConstraint(SpringLayout.WEST, controlTextScroll, 0, SpringLayout.WEST, recordPannel);
		recordLayout.putConstraint(SpringLayout.SOUTH, controlTextScroll, 150, SpringLayout.NORTH, recordPannel);
		recordLayout.putConstraint(SpringLayout.NORTH, controlTextScroll, 0, SpringLayout.NORTH, recordPannel);
		recordPannel.add(recordTextScroll);
		recordLayout.putConstraint(SpringLayout.EAST, recordTextScroll, 0, SpringLayout.EAST, recordPannel);
		recordLayout.putConstraint(SpringLayout.WEST, recordTextScroll, 0, SpringLayout.WEST, recordPannel);
		recordLayout.putConstraint(SpringLayout.SOUTH, recordTextScroll, -70, SpringLayout.SOUTH, recordPannel);
		recordLayout.putConstraint(SpringLayout.NORTH, recordTextScroll, 5, SpringLayout.SOUTH, controlTextScroll);
		recordPannel.add(modifyPatientRecordButton);
		recordLayout.putConstraint(SpringLayout.EAST, modifyPatientRecordButton, 0, SpringLayout.EAST, recordPannel);
		recordLayout.putConstraint(SpringLayout.WEST, modifyPatientRecordButton, 0, SpringLayout.WEST, recordPannel);
		recordLayout.putConstraint(SpringLayout.SOUTH, modifyPatientRecordButton, 0, SpringLayout.SOUTH, recordPannel);
		recordLayout.putConstraint(SpringLayout.NORTH, modifyPatientRecordButton, 10, SpringLayout.SOUTH, recordTextScroll);
		frame.add(imagelJLabel);
		mainLayout.putConstraint(SpringLayout.EAST, imagelJLabel, 160, SpringLayout.WEST, frame.getContentPane());
		mainLayout.putConstraint(SpringLayout.WEST, imagelJLabel, 15, SpringLayout.WEST, frame.getContentPane());	//最西邊基準線
		mainLayout.putConstraint(SpringLayout.SOUTH, imagelJLabel, 0, SpringLayout.SOUTH, nursePannel);
		mainLayout.putConstraint(SpringLayout.NORTH, imagelJLabel, 0, SpringLayout.NORTH, nursePannel);
		frame.add(patientListScroll);
		mainLayout.putConstraint(SpringLayout.EAST, patientListScroll, 0, SpringLayout.EAST, imagelJLabel);
		mainLayout.putConstraint(SpringLayout.WEST, patientListScroll, 0, SpringLayout.WEST, imagelJLabel);
		mainLayout.putConstraint(SpringLayout.SOUTH, patientListScroll, 0, SpringLayout.SOUTH, recordPannel);
		mainLayout.putConstraint(SpringLayout.NORTH, patientListScroll, 80, SpringLayout.NORTH, recordPannel);

		frame.setVisible(true);
	}

	//設定護士基本資料
	public void showNurseInfo(){
    	nurseNameLabel.setText("Name: " + nurse.name);
    	nurseSexLabel.setText("Sex: " + nurse.sex);
    	nursePositionLabel.setText("Position: " + nurse.position);
    	nurseDutyLabel.setText("Duty: " + nurse.duty);
    	nurseStatusLabel.setText("Status: " + nurse.status);
    	
    	//設定護士大頭貼
		BufferedImage photo;
		try {
	    	File source = new File("photo/" + nurse.name + ".jpg");
			photo = ImageIO.read(source);
			imagelJLabel = new JLabel(new ImageIcon(photo));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.repaint();
		frame.revalidate();
	}
	
	//設定病人列表
	public void showPatientList(){
		String[] patients;
		
		Database data = new Database();	
		patients = data.getPatientList();
		
		for(int i =0; i<patients.length; i++)
			patientModel.add(i, patients[i]);
	}

	//改變選單所會觸發的項目
	@Override
	public void valueChanged(ListSelectionEvent e) {
		String readRecordPermition;
		String writeRecordPermition;
		String target;
		
		if(e.getValueIsAdjusting() == false){
			recordText.setText("");
			
			//取出病人資訊
			Patient patient = new Patient(patientList.getSelectedValue());
			target = "cn=" + patient.ward + ", ou=wards, o=AAA, c=gb";		//設定target
			
			//發出request
			writeRecordPermition = sendReuest("Write", target);
			//Permit
			if(writeRecordPermition.equals("Permit")){
				modifyPatientRecordButton.setEnabled(true);
			}
			else if (writeRecordPermition.equals("Deny")) {
				modifyPatientRecordButton.setEnabled(false);
			}
			else {
				recordText.append("Write Permition Error: " + writeRecordPermition);
			}
			
			//發出request
			readRecordPermition = sendReuest("Read", target);
			//Permit
			if(readRecordPermition.equals("Permit")){
				/*recordText.append("Name: " + patient.name + "\r\n");
				recordText.append("Sex: " + patient.sex + "\r\n");
				recordText.append("Age: " + patient.age + "\r\n");
				recordText.append("Ward: " + patient.ward + "\r\n");
				recordText.append("Record: " + patient.record + "\r\n");*/
				recordText.append(patient.record + "\r\n");
				
				recordText.setEditable(true);
				
			}
			else if (readRecordPermition.equals("Deny")) {
				recordText.append("拒絕存取");
			}
			else {
				recordText.append("Read Permition Error: " + readRecordPermition);
			}			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == modifyPatientRecordButton)					// "修改病歷" 按鈕，執行修改病歷	
			modifyPatientRecord();
		else if (e.getSource() == changeDutyButton)						//改變護士Duty 
			changeDuty();
		else if (e.getSource() == handoverButton)							//護士交接
			changeNurseStatus();
	}
	
	public void modifyPatientRecord(){
		String writeRecordPermition;
		//取出病人資訊
		Patient patient = new Patient(patientList.getSelectedValue());
		//Target
		String target = "cn=" + patient.ward + ", ou=wards, o=AAA, c=gb";		

		//發出request		
		writeRecordPermition = sendReuest("Write", target);
		if(writeRecordPermition.equals("Permit")){
			patient.record  = recordText.getText();
			patient.modifyPatientData();
			errorMessage("修改成功");
		}
		else if (writeRecordPermition.equals("Deny"))
			errorMessage("您沒有修改病歷的權限");
		else
			recordText.append("Write Permition Error: " + writeRecordPermition);
	}
	
	private void changeDuty(){
		String writeDutyPermition;
		
		writeDutyPermition = sendReuest("Write", "cn=nurseDuty, ou=target, o=AAA, c=gb");
		if(writeDutyPermition.equals("Permit")){
			new DutyAssignment(this);
		//frame.setEnabled(false);
		}
		else if (writeDutyPermition.equals("Deny"))
			errorMessage("您沒有修改Duty的權限");
	}
	
	private void changeNurseStatus(){
		switch(nurse.status){
		case "Observer":
			nurse.status = "OnService";
			break;
		case "OnService":
			nurse.status = "InCharge";
			break;
		case "InCharge":
			nurse.status = "Observer";
			break;
		}
		
		nurse.modifyNurseData();
		flush();
		
	}
	
	public String sendReuest(String action, String target){
		Query query = new Query();
		//設定Nurse的Attribute
		query.subjectAttribute = new String[nurse.role.length];
		for(int i=0; i<nurse.role.length; i++)
			query.subjectAttribute[i] = nurse.role[i];
		
		query.actionAttribute = action;
		query.targetAttribute = target;
		return query.queryToPermisPDP();
	}
	
	
	public void flush(){
		Nurse newNurse = new Nurse(nurse.name);
		nurse = newNurse;
		System.out.println(nurse.role[1]);
		showNurseInfo();
	}
	
	private void NurseRoleLog() {
		String type = "Role Change";
		String message = "";	
		for(int i=0; i<nurse.role.length; i++)
			message += nurse.role[i] + "\n";
		
		printControlLog(type, message);
	}
	
	public void printControlLog(String type, String message){
		Log log = new Log();
		log.showLog(type, message);
	}
	
	public void errorMessage(String message){
		JFrame fram = new JFrame("提示訊息");
		fram.getRootPane().setWindowDecorationStyle(JRootPane.WARNING_DIALOG); 
		
		JLabel lbl = new JLabel(message);
		lbl.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lbl.setVerticalAlignment(SwingConstants.CENTER);
		fram.getContentPane().add(lbl);
		fram.setUndecorated(true);
		fram.setSize(300,150);
		fram.setVisible(true);
	}
}