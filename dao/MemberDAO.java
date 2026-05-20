package dao;
import java.util.*;

import model.Member;

public interface MemberDAO {
    void addMember(Member member);
    Member findById(int memberId);
    ArrayList<Member>findAll();
    public void deleteMember(int memberId);

    
}
