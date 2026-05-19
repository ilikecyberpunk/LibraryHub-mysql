import java.sql.*;

public class LoanDAOImpl implements LoanDAO {
    public void registerLoan(int bookId, int memberId){
        Connection con = DBConnection.getConnection();
        PreparedStatement st = null;
        
        String sql = "INSERT INTO LOAN(LOAN_ID, BOOK_ID, MEMBER_ID, LOAN_DATE) VALUES(LOAN_SEQ.NEXTVAL, ?, ?, SYSDATE)";
        try{
            st = con.prepareStatement(sql);
            st.setInt(1,bookId);
            st.setInt(2, memberId);
            st.executeUpdate(); 
            //executeQuery는 select 할떄
            //executeUpdate는 ddl할때 ex) insert delelte update ..

            //대출화면
            PreparedStatement st2 = con.prepareStatement("UPDATE BOOK SET IS_AVAILABLE = 'N' WHERE BOOK_ID = ?");
            st2.setInt(1, bookId);
            st2.executeUpdate();
            st2.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally{
            DBConnection.close(con, st, null);
        }
        System.out.println("멤버 id = "+memberId+", 책 id = "+bookId+"대출등록 성공");
    }
    public void returnBook(int loanId, int bookId){
        Connection con = DBConnection.getConnection();
        PreparedStatement st = null;
        String sql = "UPDATE LOAN SET RETURN_DATE WHERE LOAN_ID = ?";
        try{
            st = con.prepareStatement(sql);
            st.setInt(1, loanId);
            st.executeUpdate();

            PreparedStatement st2 = con.prepareStatement("UPDATE BOOK SET IS_AVAILABLE = Y WHERE BOOK_ID = ?");
            st2.setInt(1, bookId);
            st2.executeUpdate();
            st2.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally{
            DBConnection.close(con, st, null);
        }
        System.out.println("반납완료 (loan ID : " + loanId + "Book ID : " + bookId);
    }

    //연체조회
    public void getOverdueList(){
        
    }
}
