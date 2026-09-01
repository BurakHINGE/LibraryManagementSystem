import java.util.ArrayList;

public class Library {

    private ArrayList<LibraryBook> books;
    private int nextLoanID;
    
    public Library() {
        books = JsonManager.loadLibraryBooks();
        nextLoanID = 1;
    }

    public boolean addBook(LibraryBook book) { // Add book to library

        for (LibraryBook tempBook : books) {
            if (tempBook.getBook().getID() == book.getBook().getID()) {
                return false;
            }
        }

        books.add(book);
        JsonManager.saveLibraryBooks(books);
        return true;
    }

    public boolean removeCopies(int bookID, int amount) {

        LibraryBook libraryBook = findBook(bookID);
    
        if (libraryBook == null) {
            return false;
        }
    
        if (amount <= 0) {
            return false;
        }
    
        if (amount > libraryBook.getAvailableCopies()) {
            return false;
        }
    
        int newTotalCopies = libraryBook.getTotalCopies() - amount;
        int newAvailableCopies = libraryBook.getAvailableCopies() - amount;
    
        libraryBook.setTotalCopies(newTotalCopies);
        libraryBook.setAvailableCopies(newAvailableCopies);
    
        if (newTotalCopies == 0) {
            books.remove(libraryBook);
        }
    
        JsonManager.saveLibraryBooks(books);
        return true;
    }

    public boolean removeBook(LibraryBook book) { // Remove book from library

        for (LibraryBook tempBook : books) {
            if (tempBook.getBook().getID() == book.getBook().getID()) {
                books.remove(book);
                JsonManager.saveLibraryBooks(books);
                return true;
            }
        }

        return false;
    }

    public boolean updateBook(int bookID, String title, String authorName, String category, int totalCopies) {

        LibraryBook libraryBook = findBook(bookID);
    
        if (libraryBook == null) {
            return false;
        }

        int borrowedCopies = libraryBook.getTotalCopies() - libraryBook.getAvailableCopies();

        if (totalCopies < borrowedCopies) {
            return false;
        }
    
        int availableCopies = totalCopies - borrowedCopies;
    
        Book book = libraryBook.getBook();
    
        book.setTitle(title);
        book.setAuthorName(authorName);
        book.setCategory(category);
    
        libraryBook.setTotalCopies(totalCopies);
        libraryBook.setAvailableCopies(availableCopies);
    
        JsonManager.saveLibraryBooks(books);
    
        return true;
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
        JsonManager.saveLibraryBooks(books);
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
        JsonManager.saveLibraryBooks(books);
        return true;
    }
    
    public ArrayList<LibraryBook> getBooks() {
        return books;
    }
}