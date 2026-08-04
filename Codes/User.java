import java.util.ArrayList;

public class User {

    private String username;
    private String password;
    private ArrayList<Book> bookList;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.bookList = new ArrayList<>();
    }

    public boolean addBooks(String bookName, String authorName, String category, String readInfo, int point) {

        bookName = StringUtils.capitalizeWords(bookName);
        authorName = StringUtils.capitalizeWords(authorName);

        for (Book book : bookList) {
    
            if (book.getBookName().equalsIgnoreCase(bookName) &&
                book.getAuthorName().equalsIgnoreCase(authorName)) {
    
                return false; 
            }
        }
    
        Book newBook = new Book(bookName, authorName, category, readInfo, point);
        bookList.add(newBook);
    
        return true; 
    }

    public void listBooks() {

        for (Book book : bookList) {
            System.out.println(book.getBookName() + " - " + book.getAuthorName() + " - " + book.getCategory() + " - " + book.getReadInfo() + " - " + book.getPoint());
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Book> getBooks() {
        return bookList;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}