import java.util.ArrayList;

public class practice {
    public static void main(String[] args){
        ArrayList<Book> booklist = new ArrayList<>();
        booklist.add(new Book(1234, "죄와 벌", "키예슬로프스키"));
        booklist.add(new Book(1235, "변신", "프란츠 카프카"));

        for(Book b : booklist){
            System.out.println(b);
        }

        for(int i = 0; i < booklist.size(); i++){
            System.out.println(i + ": "+ booklist.get(i).getTitle());
        }

    }
}
