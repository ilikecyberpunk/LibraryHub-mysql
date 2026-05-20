import java.util.*;

public interface MemberDAO {
    void addMember(Member member);
    Member findById(int memberId);
    ArrayList<Member>findAll();
    public void deleteMember(int memberId);

    
}
