import java.util.Scanner;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainLibrary extends Application {

    @Override 
    public void start(Stage primaryStage) {
        
        Scanner input = new Scanner(System.in);
        Library library = new Library();
        AuthenticationManager auth = new AuthenticationManager();
        UserMenu userMenu = new UserMenu(library, auth);
        AdminMenu adminMenu = new AdminMenu(library, auth);
        MenuManager mainMenu = new MenuManager(auth, userMenu, adminMenu);
        UIManager interfaceManager = new UIManager(primaryStage, auth, library);

        primaryStage.setScene(interfaceManager.startApp());
		primaryStage.setTitle("Library & Bookshelf App");
        primaryStage.show();

        // Test aşamasında arka plandaki konsolu devre dışı bırakıyorum ki UI test ederken karışmasın.
        /* 
        Thread consoleThread = new Thread(() -> {
            mainMenu.showMainMenu();
            javafx.application.Platform.exit();
            System.exit(0);
        });
        consoleThread.setDaemon(true);
        consoleThread.start();
        */
    }
    public static void main(String[] args) {
        launch(args);
    }
}