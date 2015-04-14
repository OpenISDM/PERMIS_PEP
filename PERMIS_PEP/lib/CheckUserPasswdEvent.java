import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckUserPasswdEvent  implements ActionListener{
	Nurse nursePersonalData = new Nurse();
	@Override
	public void actionPerformed(ActionEvent e) {
		if(checkTextIsNotEmpty()){
			Database nursedata = new Database();
			nursePersonalData = nursedata.selectNurseData(AuthenticationGUI.loginNameText.getText());
			if(checkUserAndPassed(nursePersonalData.passwd)){

				new MeMDAS().MeMDASGUI(nursePersonalData);
				AuthenticationGUI.fram.dispose();
			}
			else{
				AuthenticationGUI.messageLabel.setText("�b���αK�X���~");
			}
		}
		else{
			AuthenticationGUI.messageLabel.setText("�ж�J�b���K�X");
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
