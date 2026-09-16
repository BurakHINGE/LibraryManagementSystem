
import javafx.application.Application;
import javafx.stage.Stage;

public class MainLibrary extends Application {

    @Override 
    public void start(Stage primaryStage) {
        
        Library library = new Library();
        AuthenticationManager auth = new AuthenticationManager();
        UIManager interfaceManager = new UIManager(primaryStage, auth, library);

        primaryStage.setScene(interfaceManager.startApp());
		primaryStage.setTitle("Library & Bookshelf App");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}