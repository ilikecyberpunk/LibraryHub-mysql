import java.sql.*;

public class DBConnection {
	private static String url = "jdbc:mysql://127.0.0.1:3306/BOOK_DB?serverTimezone=UTC&useSSL=false";
	private static String UserId = "root";
	private static String pwd = "a93686351@";

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

// Connection (통로): "데이터베이스라는 성(Castle)으로 들어가는 다리를 놓는다."

// Statement (전령): "종이에 SQL이라는 명령어를 적어서 성 안으로 보낸다."

// ResultSet (전리품): "성에서 가져온 데이터 주머니에서 하나씩 꺼내 확인한다."