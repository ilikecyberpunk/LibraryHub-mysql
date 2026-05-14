import java.sql.*;

public class DBConnection {
	private static String url = "jdbc:oracle:thin:@localhost:1521/xe";
	private static String UserId = "c##kimtaeho1";
	private static String pwd = "8165";

	public static Connection getConnection(){
		try{
			return DriverManager.getConnection(url, UserId, pwd);
		}
		catch(Exception e){
			System.out.println(e);
			return null;
		}
	}

	public static void close(Connection con, Statement st, ResultSet rs){
		try{
			if(rs != null){
				rs.close();
			}
			if(st != null){
				st.close();
			}
			if(con != null){
				con.close();
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	public static void main(String[] args){
		DBConnection a = new DBConnection();
		a.getConnection();

		if(a != null){
			System.out.println("db연결성공");
		}
		else{
			System.out.println("db연결실패");
		}

	}


}
