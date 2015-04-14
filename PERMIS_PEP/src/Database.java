/*
 * ��class�Ψӧ@�Pdatabase�������Ҧ��ʧ@
*/
import java.sql.*;


public class Database {
	
	private Connection con = null;
	//�s��object
	private Statement stat  = null;
	//����,�ǤJ��sql������r�� 
	private ResultSet rs = null; 
	//���G��
	private PreparedStatement pst = null;
	//����A�ǤJ��sql���w�x�s���r��A�ݭn�ǤJ�ܼƤ���m
	//���Q��?�Ӱ��Х�
	
	private String query; 
	
	//private String selectSQL ;//= "select * from patient ";
	
	public void connect(){
		
		try {
		      //���Udriver 
		      Class.forName("com.mysql.jdbc.Driver"); 
		      //���oconnection
		      //con = DriverManager.getConnection("jdbc:mysql://140.113.216.109/memdas", "permis_pep","123456"); 
		      con = DriverManager.getConnection("jdbc:mysql://" + Config.sqlServer, Config.sqlAccount, Config.sqlPasswd);

		      //jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=Big5
		      //localhost�O�D���W,test�Odatabase�W
		      //useUnicode=true&characterEncoding=Big5�ϥΪ��s�X 
		      
		}catch(ClassNotFoundException | SQLException e){
			 System.out.println("connect ERROR : "+e.toString()); 
		}
	}
	
	  //����ϥΧ���Ʈw��,�O�o�n�����Ҧ�Object 
	  //�_�h�b����Timeout��,�i��|��Connection poor�����p 
	public void Close(){
		try{ 
			if(rs!=null){ 
					rs.close(); 
					rs = null; 
	    	} 
	    	if(stat!=null){ 
	    			stat.close(); 
	    			stat = null; 
	    	} 
	      if(pst!=null){ 
	    	  		pst.close(); 
	    	  		pst = null; 
	      } 
	    }catch(SQLException e){ 
	    	System.out.println("Close Exception :" + e.toString()); 
	    } 
	}//close
	
	//Nurse���
	//���oNurse�C��
	public String[] getNurseList(){
		int maxPatientNum = 1000;
		String[] PatientName = new String[maxPatientNum];
		int i = 0;
		
		query = "select name  from nurse";
		try{
			connect();
			stat = con.createStatement();
			rs = stat.executeQuery(query);
		      while(rs.next()) 
		      {
		    	  PatientName[i++] = rs.getString("name");
		      }
		}catch(SQLException e){
			System.out.println("select nurse error");
		}finally{ 
		      Close(); 
		}
		return PatientName;
	}

	
	  //���o�n�J�@�h���
	public Nurse getNurseData(String name){
		Nurse nurse = new Nurse();
		query = "select *  from nurse where name = '"+ name + "'";
		
	    try{
	    	connect();
	    	stat = con.createStatement();
	    	rs = stat.executeQuery(query);
	    	//ResultSet �n���X(�Ĥ@��)��ƫe,�@�w�n�����@�� resultSet.next() �A�]�� default ���ЬO���V�Ĥ@�����e�� null
	    	rs.next();
	    	
	    	nurse.name = rs.getString("name");
	    	nurse.passwd = rs.getString("passwd");
	    	nurse.sex = rs.getString("sex");
	    	nurse.age = rs.getString("age");
	    	nurse.position = rs.getString("position");
	    	nurse.status = rs.getString("status");
	    	nurse.duty = rs.getString("duty");
	    	
	    	//��J�@�h�Ҿ֦�������
	    	String[] dutySplit = nurse.duty.split(" ");
	    	nurse.role = new String[dutySplit.length+2];
	    	nurse.role[0] = nurse.position;
	    	nurse.role[1] = nurse.status;
	    	for(int i=0; i<dutySplit.length; i++)
	    		nurse.role[i+2] = "DutyOn" + dutySplit[i];

	    }catch(SQLException e){
	    	System.out.println("Database Exception :" + e.toString()); 
	    }finally{
	      Close();
	    }
	    return nurse;
	}
	
	//�ק�Nurse�d����
	  public void modifyNurseData(Nurse n){
		  query =  "UPDATE nurse SET sex=?, age=?, passwd=?, duty=?, position=?, status=? where name=?";
		  
		  try{
			  connect();
			  pst = con.prepareStatement(query);
			  pst.setString(1, n.sex);
			  pst.setString(2, n.age);
			  pst.setString(3, n.passwd);
			  pst.setString(4, n.duty);
			  pst.setString(5, n.position);
			  pst.setString(6, n.status);
			  pst.setString(7, n.name);
			  pst.executeUpdate(); 
		  }catch(SQLException e){ 
			  System.out.println("InsertDB Exception :" + e.toString()); 
	    }finally{ 
	      Close(); 
	    }
	  }
	  
	  //Patient
	  //���oPatient�C��
	  public String[] getPatientList(){
			int maxPatientNum = 1000;
			String[] PatientName = new String[maxPatientNum];
			int i = 0;
			
			query = "select *  from patient";
			try{
				connect();
				stat = con.createStatement();
				rs = stat.executeQuery(query);
			    while(rs.next())
			    	PatientName[i++] = rs.getString("name");// + " (" + rs.getString("ward") + ")";

			}catch(SQLException e){
				System.out.println("select patient error");
			}finally{ 
			      Close(); 
			}
			return PatientName;
	}
	
	  //���opatient���
	public Patient getPatientData(String name){
		Patient patient = new Patient();		
		query = "select *  from patient where name = '"+ name + "'";
		
		try{
			connect();
			stat = con.createStatement();
			rs = stat.executeQuery(query);
			rs.next();		//ResultSet �n���X(�Ĥ@��)��ƫe,�@�w�n�����@�� resultSet.next() �A�]�� default ���ЬO���V�Ĥ@�����e�� null 
			
			patient.name = name;
			patient.sex = rs.getString("sex");
			patient.age = rs.getString("age"); 
			patient.ward =  rs.getString("ward");
			patient.record = rs.getString("record");
				
		}catch(SQLException e){
			System.out.println("Database Exception :" + e.toString()); 
		}finally{ 
		      Close(); 
		}
		return patient;
	}
	
	//�ק�patient���
	  public void modifyPatientData(Patient p){
		  query =  "UPDATE patient SET sex= ?, age=?, ward=?, record= ? where name=?";
		  
		  try{
			  connect();
			  pst = con.prepareStatement(query);
			  pst.setString(1, p.sex); 
			  pst.setString(2, p.age);
			  pst.setString(3, p.ward);
			  pst.setString(4, p.record);
			  pst.setString(5, p.name);
			  pst.executeUpdate(); 
		  }catch(SQLException e){ 
			  System.out.println("InsertDB Exception :" + e.toString()); 
	    }finally{ 
	      Close(); 
	    }
	  }
}
