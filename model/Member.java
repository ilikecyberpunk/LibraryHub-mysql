abstract class User {
}

public class Member extends User{
    private int memberId;
    private String name;
    private String phone;

    public Member(int memberId, String name, String phone){
        this.memberId = memberId;
        this.name = name;
        this.phone = phone;
    }

    public int getMemberId(){
        return memberId;
    }

    public String getName(){
        return name;
    }

    public String getPhone(){
        return phone;
    }

    //for test
    public static void main(String[] args){
        Member a = new Member(20240350, "taeho kim", "01012345678");

    }
}
