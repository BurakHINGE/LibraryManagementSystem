import java.util.Scanner;

public class UserMenu {

    private Scanner input;
    private Library library;
    private AuthenticationManager auth;
    private MenuManager menuMan;
    private BookshelfMenu bookshelfMenu;
    private LibraryMenu libraryMenu;

    public UserMenu(Library library, AuthenticationManager auth) {
        this.input = new Scanner(System.in);
        this.auth = auth;
        this.menuMan = menuMan;
        bookshelfMenu = new BookshelfMenu(auth);
        libraryMenu = new LibraryMenu(library, auth);
    }

    public void showUserMenu(User loggedInUser) {

        System.out.println("Ana Sayfaya Hoş Geldin " + loggedInUser.getUsername());

        while (true) {
            System.out.println("-----İşlem Seçiniz-----");
            System.out.println("1-Kitaplığınıza Girin\n2-Kütüphaneye Girin\n3-Çıkış\n: ");
            int choice = input.nextInt();

            switch (choice) {
                case 1: {
                    bookshelfMenu.showBookshelfMenu(loggedInUser);
                    break;
                }
                case 2: {
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