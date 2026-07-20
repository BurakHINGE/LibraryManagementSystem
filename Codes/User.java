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

    public boolean addBooks(String bookName, String authorName) {

        capitalizeWords(bookName);
        capitalizeWords(authorName);

        for (Book book : bookList) {
    
            if (book.getBookName().equalsIgnoreCase(bookName) &&
                book.getAuthorName().equalsIgnoreCase(authorName)) {
    
                return false; 
            }
        }
    
        Book newBook = new Book(bookName, authorName);
        bookList.add(newBook);
    
        return true; 
    }

    public void listBooks() {

        for (Book book : bookList) {
            System.out.println(book.getBookName() + " - " + book.getAuthorName());
        }
    }

    private String capitalizeWords(String text) {

        String[] words = text.split(" ");

        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if(!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
            }
        }

        return result.toString().trim();
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