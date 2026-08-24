
import java.time.LocalDate;

public class Loan {
    
    private int loanID;
    private User user;
    private LibraryBook libraryBook;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private boolean returned;

    public Loan(int loanID, User user, LibraryBook libraryBook) {
        this.loanID = loanID;
        this.user = user;
        this.libraryBook = libraryBook;
        this.borrowDate = null;
        this.returnDate = null;
        this.returned = false;
    }

    public void borrow() {

        if (borrowDate != null) {
            return;
        }

        borrowDate = LocalDate.now();
        returned = false;
    }

    public void returnBook() {

        if (returned) {
            return;
        }

        returned = true;
        returnDate = LocalDate.now();
    }

    public boolean isReturned() {
        return returned;
    }

    public int getLoanID() {
        return loanID;
    }

    public User getUser() {
        return user;
    }

    public LibraryBook getLibraryBook() {
        return libraryBook;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
}