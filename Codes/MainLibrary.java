import java.util.Scanner;

public class MainLibrary {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Library library = new Library();
        AuthenticationManager auth = new AuthenticationManager();
        UserMenu userMenu = new UserMenu(library, auth);
        AdminMenu adminMenu = new AdminMenu(library, auth);
        MenuManager mainMenu = new MenuManager(auth, userMenu, adminMenu);

        mainMenu.showMainMenu();
    }
}