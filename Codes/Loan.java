
import java.time.LocalDate;

public class Loan {
    
    private int id;
    private User user;
    private LibraryBook libraryBook;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private boolean returned;

    public Loan(int id, User user, LibraryBook libraryBook) {
        this.id = id;
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

        if (returned) {
            return true;
        }
        else {
            return false;
        }
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
}