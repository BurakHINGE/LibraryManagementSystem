import java.util.Scanner;

public class UserMenu {

    private Scanner input;
    private AuthenticationManager auth;
    private MenuManager menuMan;

    public UserMenu(AuthenticationManager auth) {
        this.input = new Scanner(System.in);
        this.auth = auth;
        this.menuMan = menuMan;
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

                        String kategori = categoryMenu();

                        String okumaDurumu = readInfoMenu();

                        System.out.println("Kitabınızı 10 üzerinden puanlayınız: ");
                        int puan = input.nextInt();

                        added = loggedInUser.addBooks(kitapAdi, yazarAdi, kategori, okumaDurumu, puan);
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

    public String categoryMenu() {

        System.out.println("1- Dünya Klasikleri\n2- Tarih\n3- Psikoloji\n4- Aşk\n5- Korku-Gelirim\n6- Bilim-Kurgu\n7- Polisiye\n8- Aksiyon-Macera\n9- Şiir\n10- Çocuk\n11- Felsefe\n12- Sosyoloji\n13- Biyografi\n14- Makale\n15- Deneme\n16- Bilim-Teknoloji");
        System.out.println("Kitabınızın kategorisini giriniz: ");
        int choose = input.nextInt();

        switch (choose) {
            case 1: {
                return "Dünya Klasikleri";
            }
            case 2: {
                return "Tarih";
            }
            case 3: {
                return "Psikoloji";
            }
            case 4: {
                return "Aşk";
            }
            case 5: {
                return "Korku-Gerilim";
            }
            case 6: {
                return "Bilim-Kurgu";
            }
            case 7: {
                return "Polisiye";
            }
            case 8: {
                return "Aksiyon-Macera";
            }
            case 9: {
                return "Şiir";
            }
            case 10: {
                return "Çocuk";
            }
            case 11: {
                return "Felsefe";
            }
            case 12: {
                return "Sosyoloji";
            }
            case 13: {
                return "Biyografi";
            }
            case 14: {
                return "Makale";
            }
            case 15: {
                return "Deneme";
            }
            case 16: {
                return "Bilim-Teknoloji";
            }
        }
        return "";
    }

    public String readInfoMenu() {

        System.out.println("1- Okudum\n2- Okuyorum\n 3- Okuyacağım\nKitabı okuma durumunuzu belirtiniz: ");
        int choose = input.nextInt();

        switch (choose) {
            case 1: {
                return "Okudum";
            }
            case 2: {
                return "Okuyorum";
            }
            case 3: {
                return "Okuyacağım";
            }
        }
        return "";
    }
}