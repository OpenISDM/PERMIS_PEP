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
		      con = DriverManager.getConnection("jdbc:mysql://140.113.216.109/memdas", "root","123456"); 

		      //jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=Big5
		      //localhost�O�D���W,test�Odatabase�W
		      //useUnicode=true&characterEncoding=Big5�ϥΪ��s�X 
		      
		}catch(ClassNotFoundException | SQLException e){
			 System.out.println("connect ERROR : "+e.toString()); 
		}
	}
	
	public String[] fetchNurseName(){
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
	
	public String fetchNurseDuty(String name){
		String data = null;
		
		query = "select duty from nurse where name = '"+ name + "'";
		try{
			connect();
			stat = con.createStatement();
			rs = stat.executeQuery(query);
			rs.next();		//ResultSet �n���X(�Ĥ@��)��ƫe,�@�w�n�����@�� resultSet.next() �A�]�� default ���ЬO���V�Ĥ@�����e�� null 
			data =  rs.getString("duty");
		}catch(SQLException e){
			data =  "No This nurse duty Error";
		}finally{ 
		      Close(); 
		}
		return data;
	}
	
	  //���X�n�J�@�h���
	public Nurse selectNurseData(String name){
		Nurse nurse = new Nurse();
		query = "select *  from nurse where name = '"+ name + "'";
		
	    try{
	    	connect();
	    	stat = con.createStatement();
	    	rs = stat.executeQuery(query);
	    	rs.next();		//ResultSet �n���X(�Ĥ@��)��ƫe,�@�w�n�����@�� resultSet.next() �A�]�� default ���ЬO���V�Ĥ@�����e�� null
	    	
	    	nurse.name = rs.getString("name");
	    	nurse.passwd = rs.getString("passwd");
	    	nurse.sex = rs.getString("sex");
	    	nurse.age = rs.getInt("age");
	    	nurse.office = rs.getString("office");
	    	nurse.duty = rs.getString("duty").split(" ");
	    	nurse.role = new String[nurse.duty.length];
	    	for(int i=0; i<nurse.duty.length; i++)
	    		nurse.role[i] = nurse.office + "_OnService_On" + nurse.duty[i];

	    }catch(SQLException e){
	    	System.out.println("DropDB Exception :" + e.toString()); 
	    }finally{ 
	      Close(); 
	    }
	    return nurse;
	}
	
	  public void modifyNurseDuty(String name, String duty){
		  query =  "UPDATE nurse set duty= ? where name = ?";
		  
		  try{
			  connect();
			  pst = con.prepareStatement(query);
			  pst.setString(1, duty); 
			  pst.setString(2, name);
			  pst.executeUpdate(); 
		  }catch(SQLException e){ 
			  System.out.println("InsertDB Exception :" + e.toString()); 
	    }finally{ 
	      Close(); 
	    }
	  }
	
	public String fetchPatientData(String name, String type){
		String data = null;
		
		query = "select *  from patient where name = '"+ name + "'";
		try{
			connect();
			stat = con.createStatement();
			rs = stat.executeQuery(query);
			rs.next();		//ResultSet �n���X(�Ĥ@��)��ƫe,�@�w�n�����@�� resultSet.next() �A�]�� default ���ЬO���V�Ĥ@�����e�� null 
			if(type.equals("record")){
				data = "name: " + name + "\tsex: " + rs.getString("sex") + "\nage: " + rs.getString("age") + "\t\tward: " + rs.getString("ward") + "\n\nrecord: \n" + rs.getString("record");
			}
			else if (type.equals("ward")) {
				data =  rs.getString("ward");
			}
		}catch(SQLException e){
			data =  "No This Patient Error";
		}finally{ 
		      Close(); 
		}
		return data;
	}
	
	public String[] fetchPatientName(){
		int maxPatientNum = 1000;
		String[] PatientName = new String[maxPatientNum];
		int i = 0;
		
		query = "select name  from patient";
		try{
			connect();
			stat = con.createStatement();
			rs = stat.executeQuery(query);
		      while(rs.next()) 
		      {
		    	  PatientName[i++] = rs.getString("name");
		      }
		}catch(SQLException e){
			System.out.println("select patient error");
		}finally{ 
		      Close(); 
		}
		return PatientName;
	}
	
	  public void modifyPatientRecord(Patient p){
		  query =  "UPDATE patient set record= ? where name = ?";
		  
		  try{
			  connect();
			  //p.record = "123";
			  pst = con.prepareStatement(query);
			  pst.setString(1, p.record); 
			  pst.setString(2, p.name);
			  pst.executeUpdate(); 
		  }catch(SQLException e){ 
			  System.out.println("InsertDB Exception :" + e.toString()); 
	    }finally{ 
	      Close(); 
	    }
	  }
	
	  //����ϥΧ���Ʈw��,�O�o�n�����Ҧ�Object 
	  //�_�h�b����Timeout��,�i��|��Connection poor�����p 
	private void Close(){
		 
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
	}
}
