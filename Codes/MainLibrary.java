import java.util.Scanner;

public class MainLibrary {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        AuthenticationManager auth = new AuthenticationManager();
        UserMenu userMenu = new UserMenu(auth);
        MenuManager mainMenu = new MenuManager(auth, userMenu);

        mainMenu.showMainMenu();
    }
}