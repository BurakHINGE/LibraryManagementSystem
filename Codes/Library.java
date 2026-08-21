import java.util.ArrayList;

public class Library {

    private ArrayList<LibraryBook> books;
    
    public Library() {

    }

    public boolean addBook(LibraryBook book) {

        for (LibraryBook tempBook : books) {
            if (tempBook.getBook().getID() == book.getBook().getID()) {
                return false;
            }
        }

        books.add(book);
        return true;
    }

    public boolean removeBook(LibraryBook book) {

        for (LibraryBook tempBook : books) {
            if (tempBook.getBook().getID() == book.getBook().getID()) {
                books.remove(book);
                return true;
            }
        }

        return false;
    }

    public LibraryBook findBook(int id) {

        for (LibraryBook tempBook : books) {
            if (tempBook.getBook().getID() == id) {
                return tempBook;
            }
        }

        return books.get(id);
    }

    public boolean borrowBookFromLibrary(int bookID, User user) {

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

        return true;
    }

    public boolean returnBookToLibrary(int bookID, User user) {

        LibraryBook libraryBook = findBook(bookID);

        if (libraryBook == null) {
            return false;
        }

        ReadingRecord targetRecord = null;

        for (ReadingRecord record : user.getBooks()) {
            if (record.getBook().getID() == bookID && record.getSource() == BookSource.LIBRARY) {
                targetRecord = record;
                break;
            }
        }

        if (targetRecord == null) {
            return false;
        }

        if (!libraryBook.returnBook()) {
            return false;
        }

        user.removeBooks(targetRecord);
        return true;
    }
    
    public ArrayList<LibraryBook> getBooks() {
        return books;
    }

}
