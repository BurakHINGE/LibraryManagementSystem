import java.util.Scanner;

public class MainLibrary {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        AuthenticationManager auth = new AuthenticationManager();
        UserMenu userMenu = new UserMenu(auth);
        MainMenu mainMenu = new MainMenu(auth, userMenu);

        mainMenu.showMainMenu();
    }
}