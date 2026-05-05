public class Book{
    private int bookId;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(int bookId, String title, String author){
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public int getBookId(){
        return bookId;
    }

    public String getTitle(){
        return title;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    public void setAvailable(boolean available){
        isAvailable = available;
    }

    @Override
    public String toString(){
        return bookId + "/" + title + "/" + author + "/" + "/ 대출가능 : " + isAvailable;
    }

    //for test
    public static void main(String[] args){
        Book a = new Book(123, "죄와 벌", "도프스예프스키");
        a.toString();
    }
}