import java.util.Scanner;

public class UserMenu {

    private Scanner input;
    private AuthenticationManager auth;

    public UserMenu(AuthenticationManager auth) {
        this.input = new Scanner(System.in);
        this.auth = auth;
    }

    public void showUserMenu(User loggedInUser) {

        System.out.println("Kitaplığına hoş geldin " + loggedInUser.getUsername());

        while (true) {
            System.out.println("-----İşlem Seçiniz-----");
            System.out.println("1-Kitap Ekle\n2-Kitaplığını Gör\n3-Çıkış");
            int secim = input.nextInt();
            input.nextLine();

            while (secim < 1 || secim > 3) {
                System.out.println("Lütfen geçerli bir işlem giriniz: ");
                System.out.println("1-Kitap Ekle\n2-Kitaplığını Gör\n3-Çıkış Yap");
                secim = input.nextInt();
                input.nextLine();
            }

            switch (secim) {
                case 3: { //exit bookshelf
                    System.out.println("Kitaplıktan çıkış yapılıyor...");
                    break;
                }
                case 2: { //list books
                    boolean empty = loggedInUser.getBooks().isEmpty();

                    if (empty) {
                        System.out.println("Kitaplığınıza henüz kitap eklemediniz.");
                    }
                    else {
                        System.out.println("-----Okuma Listeniz-----");
                        loggedInUser.listBooks();
                        System.out.println("\n");

                        System.out.println("Geri gelmek için \"1\": ");
                        int exitList = input.nextInt();

                        while (exitList != 1) {
                            System.out.println("Geri gelmek için sadece 1'i tuşlayabilirsiniz: ");
                            exitList = input.nextInt();
                        }
                    }
                    break;
                }
                case 1: { //add book
                    boolean added = false;

                    while(!added) {
                        System.out.println("Eklemek istediğiniz kitabın adını giriniz: ");
                        String kitapAdi = input.nextLine();

                        System.out.println("Kitabınızın yazarının adını giriniz: ");
                        String yazarAdi = input.nextLine();

                        added = loggedInUser.addBooks(kitapAdi, yazarAdi);
                        JsonManager.saveUsers(auth.getUsers());

                        if (!added) {
                            System.out.println("Bu kitap zaten kitaplığında bulunuyor.");
                        }
                        else {
                            System.out.println("Kitabınız kitaplığınıza başarıyla eklendi.");
                        } 
                    }
                    break;
                }
            }

            if (secim == 3) {
                break;
            }
        }
    }
}