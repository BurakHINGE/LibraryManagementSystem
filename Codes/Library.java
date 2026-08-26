import java.util.ArrayList;

public class Library {

    private ArrayList<LibraryBook> books;
    private int nextLoanID;
    
    public Library() {
        books = new ArrayList<>();
        nextLoanID = 1;
    }

    public boolean addBook(LibraryBook book) { // Add book to library

        for (LibraryBook tempBook : books) {
            if (tempBook.getBook().getID() == book.getBook().getID()) {
                return false;
            }
        }

        books.add(book);
        return true;
    }

    public boolean removeBook(LibraryBook book) { // Remove book from library

        for (LibraryBook tempBook : books) {
            if (tempBook.getBook().getID() == book.getBook().getID()) {
                books.remove(book);
                return true;
            }
        }

        return false;
    }

    public LibraryBook findBook(int id) { // Find book in library

        for (LibraryBook tempBook : books) {
            if (tempBook.getBook().getID() == id) {
                return tempBook;
            }
        }

        return null;
    }

    public boolean borrowBookFromLibrary(int bookID, User user) { // Borrow book from library

        LibraryBook libraryBook = findBook(bookID);

        if (libraryBook == null) {
            return false;
        }

        if (!libraryBook.borrow()) {
            return false;
        }

        ReadingRecord record = new ReadingRecord(libraryBook.getBook(), BookSource.LIBRARY);
        record.setStatus(ReadingStatus.READING);

        if (!user.addBooks(record)) {
            libraryBook.returnBook();
            return false;
        }

        Loan createLoan = new Loan(nextLoanID, libraryBook);
        nextLoanID++;
        createLoan.borrow();
        user.addLoan(createLoan);
        return true;
    }

    public boolean returnBookToLibrary(int loanID, User user) { // Return book to library

        Loan targetLoan = null;
    
        for (Loan loan : user.getLoans()) {
            if (loan.getLoanID() == loanID && !loan.isReturned()) {
                targetLoan = loan;
                break;
            }
        }
    
        if (targetLoan == null) {
            return false;
        }
    
        LibraryBook libraryBook = targetLoan.getLibraryBook();
    
        if (!libraryBook.returnBook()) {
            return false;
        }
    
        targetLoan.returnBook();
        return true;
    }
    
    public ArrayList<LibraryBook> getBooks() {
        return books;
    }
}