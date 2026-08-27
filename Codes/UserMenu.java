

public class UserMenu {

    private Library library;
    private AuthenticationManager auth;
    private MenuManager menuMan;
    private BookshelfMenu bookshelfMenu;
    private LibraryMenu libraryMenu;
    private InputManager inputManager;

    public UserMenu(Library library, AuthenticationManager auth) {
        this.auth = auth;
        this.menuMan = menuMan;
        bookshelfMenu = new BookshelfMenu(auth);
        libraryMenu = new LibraryMenu(library, auth);
        this.inputManager = new InputManager();
    }

    public void showUserMenu(User loggedInUser) { // User Menu

        System.out.println("Ana Sayfaya Hoş Geldin " + loggedInUser.getUsername());

        while (true) {
            System.out.println("----- İşlem Seçiniz -----");
            System.out.print("1 - Kitaplığınıza Girin\n2 - Kütüphaneye Girin\n3 - Çıkış\n> ");
            int choice = inputManager.getInt(1, 3);
            System.out.println();

            switch (choice) {
                case 1: { // Bookshelf Menu
                    bookshelfMenu.showBookshelfMenu(loggedInUser);
                    break;
                }
                case 2: { // Library Menu
                    libraryMenu.showLibraryMenu(loggedInUser);
                    break;
                }
            }

            if (choice == 3) {
                break;
            }
        }

        System.out.println("Hesaptan çıkış yapılıyor...");
    }
}