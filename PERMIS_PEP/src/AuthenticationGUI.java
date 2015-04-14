/*
 * 此class用來做authentication的GUI
*/
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;


public class AuthenticationGUI {
	public static final JFrame fram = new JFrame("Authentication");
	public static final JTextField loginNameText = new JTextField();
	public static final JTextField loginPasswdText = new JPasswordField();
	public static final JLabel messageLabel = new JLabel();
	JButton submitButton = new JButton("Submit");
	
	
	public AuthenticationGUI(){
		//Declare Layout & Panel
		SpringLayout mainLayout = new SpringLayout();
		JLabel loginNameJLabel = new JLabel("Name:");
		JLabel loginPasswdLabel = new JLabel("Password:");
		JLabel title = new JLabel("MeMDAS Authentication System", JLabel.CENTER);
		
		//Configuration
		fram.setSize(600, 400);
		fram.setLayout(mainLayout);
		fram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		title.setFont(new Font("標楷體", Font.BOLD, 32));
		loginNameJLabel.setFont(new Font("標楷體", Font.BOLD, 30));
		loginPasswdLabel.setFont(new Font("標楷體", Font.BOLD, 30));
		
		//object posttion configuration
		fram.add(title);
		mainLayout.putConstraint(SpringLayout.EAST, title, -20, SpringLayout.EAST, fram.getContentPane());
		mainLayout.putConstraint(SpringLayout.WEST, title, 20, SpringLayout.WEST, fram.getContentPane());
		mainLayout.putConstraint(SpringLayout.SOUTH, title, 100, SpringLayout.NORTH, fram.getContentPane());
		mainLayout.putConstraint(SpringLayout.NORTH, title, 60, SpringLayout.NORTH, fram.getContentPane());
		fram.add(loginNameJLabel);
		mainLayout.putConstraint(SpringLayout.WEST, loginNameJLabel, 100, SpringLayout.WEST, fram.getContentPane());
		mainLayout.putConstraint(SpringLayout.NORTH, loginNameJLabel, 100, SpringLayout.NORTH, title);
		fram.add(loginPasswdLabel);
		mainLayout.putConstraint(SpringLayout.EAST, loginPasswdLabel, 0, SpringLayout.EAST, loginNameJLabel);
		mainLayout.putConstraint(SpringLayout.NORTH, loginPasswdLabel, 50, SpringLayout.NORTH, loginNameJLabel);
		fram.add(loginNameText);
		mainLayout.putConstraint(SpringLayout.EAST, loginNameText, 260, SpringLayout.EAST, loginNameJLabel);
		mainLayout.putConstraint(SpringLayout.WEST, loginNameText, 10, SpringLayout.EAST, loginNameJLabel);
		mainLayout.putConstraint(SpringLayout.SOUTH, loginNameText, -5, SpringLayout.SOUTH, loginNameJLabel);
		fram.add(loginPasswdText);
		mainLayout.putConstraint(SpringLayout.EAST, loginPasswdText, 260, SpringLayout.EAST, loginPasswdLabel);
		mainLayout.putConstraint(SpringLayout.WEST, loginPasswdText, 10, SpringLayout.EAST, loginPasswdLabel);
		mainLayout.putConstraint(SpringLayout.SOUTH, loginPasswdText, -5, SpringLayout.SOUTH, loginPasswdLabel);
		fram.add(submitButton);
		mainLayout.putConstraint(SpringLayout.EAST, submitButton, 120, SpringLayout.EAST, loginPasswdLabel);
		mainLayout.putConstraint(SpringLayout.NORTH, submitButton, 50, SpringLayout.NORTH, loginPasswdLabel);
		fram.add(messageLabel);
		mainLayout.putConstraint(SpringLayout.EAST, messageLabel, 120, SpringLayout.EAST, loginPasswdLabel);
		mainLayout.putConstraint(SpringLayout.NORTH, messageLabel, 50, SpringLayout.NORTH, title);
		
		//Event and Action
		CheckUserPasswdEvent event = new CheckUserPasswdEvent();
		submitButton.addActionListener(event);
		loginNameText.addActionListener(event);
		loginPasswdText.addActionListener(event);
		
		fram.setVisible(true);
	}	
}
