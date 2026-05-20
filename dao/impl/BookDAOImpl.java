package dao.impl;
import java.util.ArrayList;

import dao.BookDAO;
import model.Book;
import util.DBConnection;

import java.sql.*;

public class BookDAOImpl implements BookDAO {

    
    @Override
    public void addBook(Book book){
        Connection con = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO BOOK(TITLE, AUTHOR, IS_AVAILABLE) VALUES( ?, ?, ?)";

        try{
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.isAvailable() ? "Y": "N");
            pstmt.executeUpdate();
            System.out.printf("책 추가됨 : %s\n", book.getTitle());
        }

        catch(Exception e){
            System.out.println(e);
        }
        finally{
            DBConnection.close(con, pstmt, null);
        }
    }

    @Override
    public Book findById(int bookId){
        Connection con = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM BOOK WHERE BOOK_ID = ?";

        try{
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, bookId);
            rs = pstmt.executeQuery();

            //다음행이 있다면
            if(rs.next()){
                return new Book(rs.getInt("book_id"),rs.getString("title"),rs.getString("author"),"Y".equals(rs.getString("IS_AVAILABLE")));
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally{
            DBConnection.close(con, pstmt, rs);
        }
        return null;
    }

    @Override
    public ArrayList<Book> findAll(){
        Connection con = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        ArrayList<Book> list = new ArrayList<>();

        String sql = "SELECT * FROM BOOK";

        try{
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                list.add(new Book(rs.getInt("BOOK_ID"), rs.getString("TITLE"), rs.getString("AUTHOR"), "Y".equals(rs.getString("IS_AVAILABLE"))));

            }

        }catch(Exception e){
            System.out.println(e);
        }
        finally{
            DBConnection.close(con, pstmt, rs);
        }
        return list;
    }

    @Override
    public void updateBook(Book book){
        Connection con = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        String sql = "UPDATE BOOK SET TITLE = ?, AUTHOR = ?, IS_AVAILABLE = ? WHERE BOOK_ID = ?";

        try{
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.isAvailable() ? "Y" : "N");
            pstmt.setInt(4, book.getBookId());
            pstmt.executeUpdate();
            System.out.println("책 정보 수정완료");
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally{
            DBConnection.close(con, pstmt, null);
        }
    }

    @Override
    public void deleteBook(int BookId){
        Connection con = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM BOOK WHERE BOOK_ID = ?";
        try{
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, BookId);
            pstmt.executeUpdate();
            System.out.println("책 삭제완료");
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally{
            DBConnection.close(con, pstmt, null);
        }
    }

    public static void main(String[] args){
        BookDAOImpl a = new BookDAOImpl();
        // a.addBook(new Book(3, "창세기", "모세", true));
        a.deleteBook(4);

    }


}
