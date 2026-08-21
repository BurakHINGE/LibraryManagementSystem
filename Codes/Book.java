public class Book {
    
    private static int nextID;
    private String title;
    private String authorName;
    private String category;
    private String readInfo;
    private int point;

    public Book(String title, String authorName, String category) {
        this.nextID = nextID++;
        this.title = title;
        this.authorName = authorName;
        this.category = category;
    }

    public int getID() {
        return nextID;
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