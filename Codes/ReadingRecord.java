public class ReadingRecord {
    
    private Book book;
    private ReadingStatus status;
    private int rating;
    private BookSource source;

    public ReadingRecord(Book book, BookSource source) {
        this.book = book;
        this.source = source;
    }

    public Book getBook() {
        return book;
    }

    public ReadingStatus getStatus() {
        return status;
    }

    public int getRating() {
        return rating;
    }

    public BookSource getSource() {
        return source;
    }

    public void setStatus(ReadingStatus status) {
        this.status = status;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}