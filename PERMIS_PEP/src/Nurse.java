import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Å@¤h¸ê®Æ
 */
public class Nurse{
	String age = new String();
	String name = new String();
	String passwd = new String();
	String sex = new String();
	String status = new String();
	String position = new String();
	String duty = new String();
	String[] role;
	
	public Nurse(){}
	
	public Nurse(String nurseName) {
		getNurseData(nurseName);
	}
	
	public void getNurseData(String nurseName){
		Database data = new Database();
		Nurse nurse = data.getNurseData(nurseName);
		
		this.name = nurse.name;
		this.age = nurse.age;
		this.passwd = nurse.passwd;
		this.sex = nurse.sex;
		this.position = nurse.position;
		this.status = nurse.status;
		this.duty = nurse.duty;
		this.role = nurse.role;
	}
	
	public void getNurseList(){
		
	}
	
	public void modifyNurseData(){
		Database data = new Database();
		data.modifyNurseData(this);
	}

}
