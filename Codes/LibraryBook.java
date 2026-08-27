public class LibraryBook implements Borrowable {
    
    private Book book;
    private int totalCopies;
    private int availableCopies;

    public LibraryBook(Book book, int totalCopies) {
        this.book = book;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    @Override
    public boolean borrow() { // Check available copies and decrease 1
        
        if (availableCopies <= 0) {
            return false;
        }

        availableCopies--;
        return true;
    }

    @Override
    public boolean returnBook() { // Check total copies and increase 1
        
        if (availableCopies >= totalCopies) {
            return false;
        }

        availableCopies++;
        return true;
    }

    public Book getBook() {
        return book;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }
}