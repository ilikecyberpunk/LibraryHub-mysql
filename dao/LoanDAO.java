interface LoanDAO {
    public void registerLoan(int bookId, int memberId);
    public void returnBook(int loanId, int bookId);
    public void getOverdueList();
}