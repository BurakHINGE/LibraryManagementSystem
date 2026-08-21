public class Book {
    
    private int id;
    private String title;
    private String authorName;
    private String category;
    private String readInfo;
    private int point;

    public Book(int id, String title, String authorName, String category) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.category = category;
    }

    public int getID() {
        return id;
    }
    
    public String getTitle() {
        return title;
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

    public void setID(int id) {
        this.id = id;
    }

    public void setTitle(String bookName) {
        this.title = bookName;
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