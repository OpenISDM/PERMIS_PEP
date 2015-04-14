import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class Config {
	public static String standaloneServe = null;
	public static String sqlServer = null;
	public static	 String sqlAccount = null;
	public static String sqlPasswd = null; 
	
	@SuppressWarnings("resource")
	public Config(){
		
		try {
			File file = new File("config.txt");
			FileInputStream fis = new FileInputStream(file);
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader bis = new BufferedReader(new InputStreamReader(dis));
			
			String strLine;
			while((strLine = bis.readLine()) != null){
				String[] parameter = strLine.split("=");
				
				switch (parameter[0].trim()) {
				case "standaloneServer":
					standaloneServe = parameter[1].trim();
					break;
					
				case "sqlServer":
					sqlServer = parameter[1].trim();
					break;
					
				case "sqlAccount":
					sqlAccount = parameter[1].trim();
					break;
					
				case "sqlPasswd":
					sqlPasswd = parameter[1].trim();
					break;

				default:
					break;
				}//switch
			}//while
			
		} catch (FileNotFoundException e) {
			System.err.println("Read File Error");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error on read file");
			e.printStackTrace();
		}
	}

}
