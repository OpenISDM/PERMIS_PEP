
public class MainFlow {
	public static void main(String[] args){
		Database nursedata = new Database();
		Nurse nurse = new Nurse();
		nurse = nursedata.selectNurseData("bob");
		new MeMDAS().MeMDASGUI(nurse);
		//new DutyAssignment();
		//new AuthenticationGUI();
	}
}
