import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Log{
	
	public void showLog(String title, String message){
		Date date = new Date();
		DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
		String strTime = "[" + timeFormat.format(date) + "]";
		
		MeMDAS.controlText.append(strTime + title + "\n" + message + "\n");
		MeMDAS.controlText.setCaretPosition(MeMDAS.controlText.getText().length());
		
	}
}
