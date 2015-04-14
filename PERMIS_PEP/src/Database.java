/*
 * 此class用來作與database相關的所有動作
*/
import java.sql.*;


public class Database {
	
	private Connection con = null;
	//連接object
	private Statement stat  = null;
	//執行,傳入之sql為完整字串 
	private ResultSet rs = null; 
	//結果集
	private PreparedStatement pst = null;
	//執行，傳入之sql為預儲存之字串，需要傳入變數之位置
	//先利用?來做標示
	
	private String query; 
	
	//private String selectSQL ;//= "select * from patient ";
	
	public void connect(){
		
		try {
		      //註冊driver 
		      Class.forName("com.mysql.jdbc.Driver"); 
		      //取得connection
		      //con = DriverManager.getConnection("jdbc:mysql://140.113.216.109/memdas", "permis_pep","123456"); 
		      con = DriverManager.getConnection("jdbc:mysql://" + Config.sqlServer, Config.sqlAccount, Config.sqlPasswd);

		      //jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=Big5
		      //localhost是主機名,test是database名
		      //useUnicode=true&characterEncoding=Big5使用的編碼 
		      
		}catch(ClassNotFoundException | SQLException e){
			 System.out.println("connect ERROR : "+e.toString()); 
		}
	}
	
	  //完整使用完資料庫後,記得要關閉所有Object 
	  //否則在等待Timeout時,可能會有Connection poor的狀況 
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
	
	//Nurse資料
	//取得Nurse列表
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

	
	  //取得登入護士資料
	public Nurse getNurseData(String name){
		Nurse nurse = new Nurse();
		query = "select *  from nurse where name = '"+ name + "'";
		
	    try{
	    	connect();
	    	stat = con.createStatement();
	    	rs = stat.executeQuery(query);
	    	//ResultSet 要取出(第一筆)資料前,一定要先做一次 resultSet.next() ，因為 default 指標是指向第一筆之前的 null
	    	rs.next();
	    	
	    	nurse.name = rs.getString("name");
	    	nurse.passwd = rs.getString("passwd");
	    	nurse.sex = rs.getString("sex");
	    	nurse.age = rs.getString("age");
	    	nurse.position = rs.getString("position");
	    	nurse.status = rs.getString("status");
	    	nurse.duty = rs.getString("duty");
	    	
	    	//放入護士所擁有的角色
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
	
	//修改Nurse責任區
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
	  //取得Patient列表
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
	
	  //取得patient資料
	public Patient getPatientData(String name){
		Patient patient = new Patient();		
		query = "select *  from patient where name = '"+ name + "'";
		
		try{
			connect();
			stat = con.createStatement();
			rs = stat.executeQuery(query);
			rs.next();		//ResultSet 要取出(第一筆)資料前,一定要先做一次 resultSet.next() ，因為 default 指標是指向第一筆之前的 null 
			
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
	
	//修改patient資料
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
