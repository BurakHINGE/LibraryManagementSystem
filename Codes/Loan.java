
import java.time.LocalDate;

public class Loan {
    
    private int loanID;
    private LibraryBook libraryBook;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private boolean returned;

    public Loan(int loanID, LibraryBook libraryBook) {
        this.loanID = loanID;
        this.libraryBook = libraryBook;
        this.borrowDate = null;
        this.returnDate = null;
        this.returned = false;
    }

    public void borrow() { // Create borrow date

        if (borrowDate != null) {
            return;
        }

        borrowDate = LocalDate.now();
        returned = false;
    }

    public void returnBook() { // Create return date

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