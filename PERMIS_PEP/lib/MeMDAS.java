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
	JLabel nurseOfficeLabel = new JLabel("Office:: ");
	JLabel nurseDutyLabel = new JLabel("Duty: ");
	JButton handoverButton = new JButton("��Z");
	JButton changeDutyButton = new JButton("���w�d���f��");
	JButton modifyPatientRecordButton = new JButton("�ק�f�����");
	JFrame frame = new JFrame("MeMDAS");
	
	DefaultListModel<String> patientModel = new DefaultListModel<String>();
	JList<String> patientList = new JList<String>(patientModel);
	Nurse nurse = new Nurse();
	
	public void MeMDASGUI(final Nurse nurse){
		this.nurse = nurse;
		
		NurseRoleLog();
		//�ŧi Layout
		SpringLayout mainLayout = new SpringLayout();
		SpringLayout recordLayout = new SpringLayout();
		
		//�ŧi�D�n��fram�å[�J�ӤH�Ƴ]�w
		
		frame.setSize(1000, 800);
		frame.setLayout(mainLayout);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//�ŧi�D�n��fram�Bpannel
		JPanel recordPannel = new JPanel(recordLayout);
		JPanel nursePannel = new JPanel(new GridLayout(2, 3, 20, 20));
		JPanel dutyJPanel = new JPanel(new GridLayout(3, 1, 10, 10));
		JScrollPane controlTextScroll = new JScrollPane(controlText);
		JScrollPane recordTextScroll = new JScrollPane(recordText);
		JScrollPane patientListScroll = new JScrollPane(patientList);
		
		//�]�w�I����B��ةM�r��
		nursePannel.setBorder(BorderFactory.createLineBorder(Color.decode("#2C6791")));
		patientList.setBorder(BorderFactory.createLineBorder(Color.decode("#2C6791")));
		patientList.setBackground(Color.lightGray);
		recordText.setBackground(Color.white);
		recordText.setFont(new Font("�з���", Font.BOLD, 24));
		controlText.setFont(new Font("�з���", Font.BOLD, 16));
		nurseNameLabel.setFont(new Font("�з���", Font.BOLD, 28));
		nurseSexLabel.setFont(new Font("�з���", Font.BOLD, 28));
		nurseOfficeLabel.setFont(new Font("�з���", Font.BOLD, 28));
		nurseDutyLabel.setFont(new Font("�з���", Font.BOLD, 28));
		
		//��l�ܼƫŧi
		recordText.setEditable(false);

		//�[�J�@�h�������
		nursePannel.add(nurseNameLabel);
		nursePannel.add(nurseSexLabel);
		nursePannel.add(nurseOfficeLabel);
		nursePannel.add(nurseDutyLabel);
		
		//�[�JDuty Button
		dutyJPanel.add(handoverButton);
		dutyJPanel.add(changeDutyButton);

		//�g�J�@�h�ӤH���
		showNurseInfo();

		//�g�J�f�H�W��
		showPatientList();
		
		//��檺Event�MAction
		patientList.addListSelectionListener(this);
		
		//�e�X�f�����
		modifyPatientRecordButton.addActionListener(this);
		handoverButton.addActionListener(this);
		changeDutyButton.addActionListener(this);
		
		//�k��D�n panel �ƪ��]�w
		frame.add(nursePannel);
		mainLayout.putConstraint(SpringLayout.EAST, nursePannel, -200, SpringLayout.EAST, frame.getContentPane());
		mainLayout.putConstraint(SpringLayout.WEST, nursePannel, 10, SpringLayout.EAST, imagelJLabel);
		mainLayout.putConstraint(SpringLayout.SOUTH, nursePannel, 150, SpringLayout.NORTH, frame.getContentPane());
		mainLayout.putConstraint(SpringLayout.NORTH, nursePannel, 15, SpringLayout.NORTH, frame.getContentPane());	//�̥_���ǽu
		frame.add(dutyJPanel);
		mainLayout.putConstraint(SpringLayout.EAST, dutyJPanel, 0, SpringLayout.EAST, recordPannel);
		mainLayout.putConstraint(SpringLayout.WEST, dutyJPanel, 10, SpringLayout.EAST, nursePannel);
		mainLayout.putConstraint(SpringLayout.SOUTH, dutyJPanel, 0, SpringLayout.SOUTH, nursePannel);
		mainLayout.putConstraint(SpringLayout.NORTH, dutyJPanel, 0, SpringLayout.NORTH, nursePannel);
		frame.add(recordPannel);
		mainLayout.putConstraint(SpringLayout.EAST, recordPannel, -15, SpringLayout.EAST, frame.getContentPane());	//�̪F���ǽu
		mainLayout.putConstraint(SpringLayout.WEST, recordPannel, 0, SpringLayout.WEST, nursePannel);
		mainLayout.putConstraint(SpringLayout.SOUTH, recordPannel, -15, SpringLayout.SOUTH, frame.getContentPane());	//�̫n���ǽu
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
		mainLayout.putConstraint(SpringLayout.WEST, imagelJLabel, 15, SpringLayout.WEST, frame.getContentPane());	//�̦����ǽu
		mainLayout.putConstraint(SpringLayout.SOUTH, imagelJLabel, 0, SpringLayout.SOUTH, nursePannel);
		mainLayout.putConstraint(SpringLayout.NORTH, imagelJLabel, 0, SpringLayout.NORTH, nursePannel);
		frame.add(patientListScroll);
		mainLayout.putConstraint(SpringLayout.EAST, patientListScroll, 0, SpringLayout.EAST, imagelJLabel);
		mainLayout.putConstraint(SpringLayout.WEST, patientListScroll, 0, SpringLayout.WEST, imagelJLabel);
		mainLayout.putConstraint(SpringLayout.SOUTH, patientListScroll, 0, SpringLayout.SOUTH, recordPannel);
		mainLayout.putConstraint(SpringLayout.NORTH, patientListScroll, 80, SpringLayout.NORTH, recordPannel);

		//��ܵe��
		frame.setVisible(true);
	}

	//�]�w�@�h�򥻸��
	public void showNurseInfo(){
		String nurseDutyString = "";
		
    	for(int i = 0; i  < nurse.duty.length; i++)
    		nurseDutyString += (nurse.duty[i] + " ");	
    	nurseNameLabel.setText("Name: " + nurse.name);
    	nurseSexLabel.setText("Sex: " + nurse.sex);
    	nurseOfficeLabel.setText("Office: " + nurse.office);
    	nurseDutyLabel.setText("Duty: " + nurseDutyString);
    	
    	//�]�w�@�h�j�Y�K
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
	
	//�]�w�f�H�C��
	public void showPatientList(){
		String[] patientList;
		
		Database data = new Database();	
		patientList = data.fetchPatientName();
		
		for(int i =0; i<patientList.length; i++)
			patientModel.add(i, patientList[i]);
	}

	//���ܿ��ҷ|Ĳ�o������
	@Override
	public void valueChanged(ListSelectionEvent e) {
		String readRecordPermition;
		String writeRecordPermition;
		String target;

		if(e.getValueIsAdjusting() == false){
			recordText.setText("");																																								//�M��Text
			
			Database data = new Database();																																				//�s��Database�A���X�@�h������
			target = "cn=PatientRecordOn" + data.fetchPatientData(patientList.getSelectedValue(), "ward")+", ou=Resouce, o=AAA,c=gb";		//�]�wtarget
			
			writeRecordPermition = sendReuest("Write", target);																													//���XŪ��(read)�f�H�f���� request
			if(writeRecordPermition.equals("Permit")){																																	//���s���f�����v��
				modifyPatientRecordButton.setEnabled(true);
			}
			else if (writeRecordPermition.equals("Deny")) {
				modifyPatientRecordButton.setEnabled(false);
			}
			else {
				recordText.append("Write Permition Error: " + writeRecordPermition);
			}
			
			readRecordPermition = sendReuest("Read", target);																													//���XŪ��(read)�f�H�f���� request
			if(readRecordPermition.equals("Permit")){																																	//��Ū���f�����v��
				recordText.append(data.fetchPatientData(patientList.getSelectedValue(), "record"));
				recordText.setEditable(true);
			}
			else if (readRecordPermition.equals("Deny")) {
				recordText.append("�ڵ��s��");
			}
			else {
				recordText.append("Read Permition Error: " + readRecordPermition);
			}			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == modifyPatientRecordButton)					// "�ק�f��" ���s�A����ק�f��	
			modifyPatientRecord();
		else if (e.getSource() == changeDutyButton)						//�����@�hDuty 
			changeDuty();
		else if (e.getSource() == handoverButton)							//�@�h�汵
			changeRoleFromOnServiceToObserver();
	}
	
	public void modifyPatientRecord(){
		String writeRecordPermition;
		String target;
		String[] record;
		
		//�s��Database�A���X�@�h������
		Database data = new Database();
		//�]�wtarget
		target = "cn=PatientRecordOn" + data.fetchPatientData(patientList.getSelectedValue(), "ward")+", ou=Resouce, o=AAA,c=gb";		
		//���XŪ��(read)�f�H�f���� request
		
		writeRecordPermition = sendReuest("Write", target);
		if(writeRecordPermition.equals("Permit")){
			Patient patient = new Patient();
			record =recordText.getText().split("record:");
			patient.name = patientList.getSelectedValue();
			patient.record = record[1];
			data.modifyPatientRecord(patient);
			errorMessage("�ק令�\");
		}
		else if (writeRecordPermition.equals("Deny"))
			errorMessage("�z�S���ק�f�����v��");
		else
			recordText.append("Write Permition Error: " + writeRecordPermition);
	}
	
	public String sendReuest(String action, String target){
		Subject_Action_Target attributs = new Subject_Action_Target();
		attributs.subjectAttribute = new String[nurse.role.length];		//�]�w�@�h������
		for(int i=0; i<nurse.role.length; i++)
			attributs.subjectAttribute[i] = nurse.role[i];
		
		attributs.actionAttribute = action;										//�]�wAction��read
		attributs.targetAttribute = target;											//�]�wtarget
		Query query = new Query();
		
		return query.queryToPermisPDP(attributs);
	}
	
	private void changeRoleFromOnServiceToObserver(){
		for(int i=0; i<nurse.role.length; i++){
			if(nurse.role[i].contains("OnService")){
				nurse.role[i] = nurse.role[i].replace("OnService", "Observer");
			}
		}
		NurseRoleLog();
	}
	
	private void changeDuty(){
		new DutyAssignment(this);
		frame.setEnabled(false);
	}
	
	public void flush(){
		Database data = new Database();
		nurse = data.selectNurseData(nurse.name);
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
		JFrame fram = new JFrame("���ܰT��");
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