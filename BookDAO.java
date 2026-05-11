import java.util.ArrayList;


public interface BookDAO {
    void addBook(Book book);
    Book findById(int bookId);
    ArrayList<Book> findAll();
    void updateBook(Book book);
    void deleteBook(int bookId);
}
