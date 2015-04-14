/*
 * 此class用來做user的認證動作
*/
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckUserPasswdEvent  implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		Nurse nurse = new Nurse();
		
		if(checkTextIsNotEmpty()){
			//Database nursedata = new Database();
			//nursePersonalData = nursedata.getNurseData(AuthenticationGUI.loginNameText.getText());
			nurse.getNurseData(AuthenticationGUI.loginNameText.getText());
			if(checkUserAndPassed(nurse.passwd)){

				new MeMDAS().MeMDASGUI(nurse);
				AuthenticationGUI.fram.dispose();
			}
			else{
				AuthenticationGUI.messageLabel.setText("帳號或密碼錯誤");
			}
		}
		else{
			AuthenticationGUI.messageLabel.setText("請填入帳號密碼");
		}	
	}
	
	public boolean checkTextIsNotEmpty(){
		if(AuthenticationGUI.loginNameText.getText().isEmpty() || AuthenticationGUI.loginPasswdText.getText().isEmpty())
			return false;
		else
			return true;
	}
	
	public boolean checkUserAndPassed(String passwd){
		if(EncryptBean.encrypt(AuthenticationGUI.loginPasswdText.getText()).equals(passwd)){
			return true;
		}
		else{
			return false;
		}
	}

}
