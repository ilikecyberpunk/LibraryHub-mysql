import java.sql.*;

public class JDBCTest{
	public static void main(String[] args){
		String url = "jdbc:oracle:thin:@localhost:1521/xe";
		String sql = "SELECT * FROM BOOK";
		
		try{

			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "c##kimtaeho1", "8165");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			rs.next();

			int bookid = rs.getInt("BOOK_ID");
			String title = rs.getString("TITLE");
			String author   = rs.getString("AUTHOR");
    		String isAvail  = rs.getString("IS_AVAILABLE");

			System.out.println("BOOK ID : "+bookid);
			System.out.println("TITLE : " + title);
			System.out.println("AUTHOR : " + author);
    		System.out.println("IS_AVAILABLE : " + isAvail);
			

			rs.close();
			st.close();
			con.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}