public class Book {
    
    private String bookName;
    private String authorName;
    private String category;
    private String readInfo;
    private int point;

    public Book(String bookName, String authorName, String category, String readInfo, int point) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.category = category;
        this.readInfo = readInfo;
        this.point = point;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getCategory() {
        return category;
    }

    public String getReadInfo() {
        return readInfo;
    }

    public int getPoint() {
        return point;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setReadInfo(String readInfo) {
        this.readInfo = readInfo;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}