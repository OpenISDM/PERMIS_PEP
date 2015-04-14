/*
 * 此class用來做與指定責任病房相關的所有動作
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class DutyAssignment implements ListSelectionListener, ActionListener{
	DefaultListModel<String> nurseListModel = new DefaultListModel<String>();
	JList<String> nurseList = new JList<String>(nurseListModel);
	JTextArea dutyText = new JTextArea();
	JButton modifyDutyButton = new JButton("ModifyDuty");
	JFrame frame = new JFrame();
	MeMDAS mFrame = new MeMDAS();

	public DutyAssignment(MeMDAS mFrame){
		this.mFrame = mFrame;
		DutyAssignmentGUI();
	}

	public void DutyAssignmentGUI(){
		SpringLayout mainLayout = new SpringLayout();
		
		frame.setLayout(mainLayout);
		frame.setSize(600, 250);
		JScrollPane nurseListScroll = new JScrollPane(nurseList);		
		JScrollPane dutyTextScroll = new JScrollPane(dutyText);
		
		//設定病人列表
		setNurseList();
		nurseList.addListSelectionListener(this);
		modifyDutyButton.addActionListener(this);
		
		frame.add(nurseListScroll);
		mainLayout.putConstraint(SpringLayout.EAST, nurseListScroll, 200, SpringLayout.WEST, frame.getContentPane());
		mainLayout.putConstraint(SpringLayout.WEST, nurseListScroll, 10, SpringLayout.WEST, frame.getContentPane());
		mainLayout.putConstraint(SpringLayout.SOUTH, nurseListScroll, 100, SpringLayout.NORTH, frame.getContentPane());
		mainLayout.putConstraint(SpringLayout.NORTH, nurseListScroll, 10, SpringLayout.NORTH, frame.getContentPane());
		frame.add(dutyTextScroll);
		mainLayout.putConstraint(SpringLayout.EAST, dutyTextScroll, -10, SpringLayout.EAST, frame.getContentPane());
		mainLayout.putConstraint(SpringLayout.WEST, dutyTextScroll, 10, SpringLayout.EAST, nurseListScroll);
		mainLayout.putConstraint(SpringLayout.SOUTH, dutyTextScroll, 100, SpringLayout.NORTH, frame.getContentPane());
		mainLayout.putConstraint(SpringLayout.NORTH, dutyTextScroll, 10, SpringLayout.NORTH, frame.getContentPane());
		frame.add(modifyDutyButton);
		mainLayout.putConstraint(SpringLayout.EAST, modifyDutyButton, -10, SpringLayout.EAST, frame.getContentPane());
		mainLayout.putConstraint(SpringLayout.WEST, modifyDutyButton, -120, SpringLayout.EAST, frame.getContentPane());
		mainLayout.putConstraint(SpringLayout.SOUTH, modifyDutyButton, 60, SpringLayout.SOUTH, dutyTextScroll);
		mainLayout.putConstraint(SpringLayout.NORTH, modifyDutyButton, 10, SpringLayout.SOUTH, dutyTextScroll);
		 
		frame.setVisible(true);
	}
	
	//設定病人列表
	public void setNurseList(){
		String[] nurseList;
		
		Database data = new Database();	
		nurseList = data.getNurseList();
		for(int i =0; i<nurseList.length; i++)
			nurseListModel.add(i, nurseList[i]);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		Nurse nurse = new Nurse(nurseList.getSelectedValue());
		dutyText.setText(nurse.duty);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Nurse nurse = new Nurse(nurseList.getSelectedValue());
		nurse.duty = dutyText.getText();
		nurse.modifyNurseData();
		mFrame.flush();
		mFrame.frame.setEnabled(true);
		frame.dispose();
	}
}
