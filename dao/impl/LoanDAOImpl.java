package dao.impl;
import java.sql.*;

import dao.LoanDAO;
import util.DBConnection;

public class LoanDAOImpl implements LoanDAO {

    public void registerLoan(int bookId, int memberId) {
        Connection con = DBConnection.getConnection();
        PreparedStatement st = null;

       
        try {
            PreparedStatement check = con.prepareStatement("SELECT IS_AVAILABLE FROM BOOK WHERE BOOK_ID = ?");
            check.setInt(1, bookId);
            ResultSet rs = check.executeQuery();
            if (rs.next() && "N".equals(rs.getString("IS_AVAILABLE"))) {
                System.out.println("이미 대출중인 도서입니다.");
                check.close(); rs.close(); con.close();
                return;
            }
            check.close(); rs.close();
        } catch (Exception e) { System.out.println(e); }

        String sql = "INSERT INTO LOAN(BOOK_ID, MEMBER_ID) VALUES(?, ?)";
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, bookId);
            st.setInt(2, memberId);
            st.executeUpdate();

            PreparedStatement st2 = con.prepareStatement("UPDATE BOOK SET IS_AVAILABLE = 'N' WHERE BOOK_ID = ?");
            st2.setInt(1, bookId);
            st2.executeUpdate();
            st2.close();
            System.out.println("멤버 id=" + memberId + ", 책 id=" + bookId + " 대출등록 성공");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBConnection.close(con, st, null);
        }
    }

    public void returnBook(int loanId, int bookId) {
        Connection con = DBConnection.getConnection();
        PreparedStatement st = null;

   
        String sql = "UPDATE LOAN SET RETURN_DATE = NOW() WHERE LOAN_ID = ?";
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, loanId);
            st.executeUpdate();

            
            PreparedStatement st2 = con.prepareStatement("UPDATE BOOK SET IS_AVAILABLE = 'Y' WHERE BOOK_ID = ?");
            st2.setInt(1, bookId);
            st2.executeUpdate();
            st2.close();
            System.out.println("반납완료 (loanId=" + loanId + ", bookId=" + bookId + ")");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBConnection.close(con, st, null);
        }
    }

    public void getOverdueList() {
        Connection con = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT MEMBER.NAME, BOOK.TITLE, LOAN.LOAN_DATE, "
                   + "DATEDIFF(NOW(), LOAN.LOAN_DATE) AS OVERDUE_DAYS "
                   + "FROM LOAN "
                   + "JOIN BOOK ON LOAN.BOOK_ID = BOOK.BOOK_ID "
                   + "JOIN MEMBER ON LOAN.MEMBER_ID = MEMBER.MEMBER_ID "
                   + "WHERE LOAN.RETURN_DATE IS NULL "
                   + "AND DATEDIFF(NOW(), LOAN.LOAN_DATE) > 14 "
                   + "ORDER BY OVERDUE_DAYS DESC";
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(
                    rs.getString("NAME") + " / " +
                    rs.getString("TITLE") + " / " +
                    rs.getDate("LOAN_DATE") + " / " +
                    rs.getInt("OVERDUE_DAYS") + "일 연체"
                );
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBConnection.close(con, pstmt, rs);
        }
    }
}