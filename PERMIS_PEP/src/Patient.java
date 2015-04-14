/*
 * 病人資料
 */
public class Patient {
	public String age = new String();
	public String sex = new String();
	public String ward = new String();
	public String name = new String();
	public String record = new String();
	public String birthday = new String();
	public String bedNuml = new String();
	
	public Patient(){}
	
	public Patient(String patientName){
		getPatientData(patientName);
	}
	
	public void getPatientData(String patientName){
		Database data = new Database();
		Patient patient = data.getPatientData(patientName);
		
		this.age = patient.age;
		this.sex = patient.sex;
		this.ward = patient.ward;
		this.name = patient.name;
		this.record = patient.record;
		this.birthday = patient.birthday;
		this.bedNuml = patient.bedNuml;
	}
	
	public void modifyPatientData(){
		Database data = new Database();
		data.modifyPatientData(this);
	}
}
