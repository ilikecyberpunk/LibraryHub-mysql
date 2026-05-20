package dao.impl;
import java.util.*;

import dao.MemberDAO;
import model.Member;
import util.DBConnection;

import java.sql.*;

public class MemberDAOImpl implements MemberDAO {

    public void addMember(Member member){
        Connection con = DBConnection.getConnection();
        PreparedStatement st = null;

        String sql = "INSERT INTO MEMBER( NAME, PHONE) VALUES( ?, ?)";
        try{
            st = con.prepareStatement(sql);
            st.setString(1, member.getName());
            st.setString(2, member.getPhone());
            st.executeUpdate();
            System.out.println("멤버 추가 완료 : " + member.getName());
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally{
            DBConnection.close(con, st, null);
        }
    }

    

    public Member findById(int memberId){
        Connection con = DBConnection.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM MEMBER WHERE MEMBER_ID = ?";

        try{
            st = con.prepareStatement(sql);
            st.setInt(1, memberId);
            rs = st.executeQuery(); //커서가 기본적으로 데이터 행 위에 있기 때문에 rs.next() 해야함
            
            //next함수랑 조건문 둘다 실행됨
            if(rs.next()){
                return new Member(rs.getInt("MEMBER_ID"), rs.getString("NAME"), rs.getString("PHONE"));
            }
            
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally{
            DBConnection.close(con, st, rs);
        }
        System.out.println("ID : " + memberId+", 불러오기 성공");
        return null;
    }

    public ArrayList<Member> findAll(){
        Connection con = DBConnection.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM MEMBER";

        ArrayList<Member> m = new ArrayList<>();

        try{
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            if(rs.next()){
                m.add(new Member(rs.getInt("MEMBER_ID"), rs.getString("NAME"), rs.getString("PHONE")));
                return m;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        finally{
            DBConnection.close(con, st, rs);
        }
        System.out.println("찾기 성공");
        return null;
    }

    public void deleteMember(int memberId){
        Connection con = DBConnection.getConnection();
        PreparedStatement st = null;

        String sql = "DELETE FROM MEMBER WHERE MEMBER_ID = ? ";
        
        try{
            st = con.prepareStatement(sql);
            st.setInt(1, memberId);
            st.executeUpdate();
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally{
            DBConnection.close(con, st, null);
        }
        System.out.println("삭제완료");
    }

    public static void main(String[] args){
        //테스트
        MemberDAOImpl a = new MemberDAOImpl();
        // a.addMember(new Member(231,"닝겐", "01012345678"));
        a.findById(1);
        // a.deleteMember(2);
    }
}
