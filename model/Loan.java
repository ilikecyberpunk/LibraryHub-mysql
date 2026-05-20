import java.util.Date;

public class Loan {
    private int loanId;
    private int bookId;
    private int memberId;
    private Date loanDate;
    private Date returnDate;

    public Loan(int loanId, int bookId, int memberId, Date loanDate){
        this.loanId = loanId;
        this.bookId = bookId;
        this.memberId = memberId;
        this.loanDate = loanDate;
        this.returnDate = null; // before return the book
    }

    public int getLoanId(){
        return loanId;
    }

    public int getBookId(){
        return bookId;
    }

    public int getMemberId(){
        return memberId;
    }

    public Date getLoanDate(){
        return loanDate;
    }

    public Date getReturnDate(){
        return returnDate;
    }

    public void setReturnDate(Date returnDate){
        this.returnDate = returnDate;
    }

}
