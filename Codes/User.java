import java.util.ArrayList;

public class User {

    private String username;
    private String password;
    private ArrayList<ReadingRecord> bookshelf;
    private ArrayList<Loan> loans;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.bookshelf = new ArrayList<>();
        this.loans = new ArrayList<>();
    }

    public boolean addBooks(ReadingRecord record) { // Add book to bookshelf

        for (ReadingRecord book : bookshelf) {
            if (book.getBook().getID() == record.getBook().getID()) {
                return false; 
            }
        }

        bookshelf.add(record);
        return true; 
    }

    public boolean removeBooks(ReadingRecord record) { // Remove book from bookshelf

        for (ReadingRecord book : bookshelf) {
            if (record.getBook().getID() == book.getBook().getID()) {
                bookshelf.remove(record);
                return true;
            }
        }

        return false;
    }

    public void listBooks() { // List books in the bookshelf

        for (ReadingRecord book : bookshelf) {
            System.out.println(book.getBook().getTitle() + " - " + book.getBook().getAuthorName() + " - " + book.getBook().getCategory() + " - " + book.getStatus() + " - " + book.getRating());
        }
    }

    public void addLoan(Loan loan) { // Add loan book from library
        loans.add(loan);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<ReadingRecord> getBooks() {
        return bookshelf;
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}