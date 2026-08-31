public class BookshelfMenu {

    private AuthenticationManager auth;
    private InputManager inputManager;

    public BookshelfMenu(AuthenticationManager auth) {
        this.auth = auth;
        inputManager = new InputManager();
    }

    public void showBookshelfMenu(User loggedInUser) {
        
        System.out.println("Kitaplığına hoş geldin " + loggedInUser.getUsername());

        while (true) {
            System.out.println("----- Kitaplık -----");
            System.out.print("1 - Kitap Ekle\n2 - Kitaplığını Gör\n3 - Ana Sayfaya Dön\n> ");
            int choice = inputManager.getInt(1, 3);
            System.out.println();

            switch (choice) {
                case 3: { // Exit bookshelf
                    System.out.println("Kitaplıktan çıkış yapılıyor...");
                    break;
                }
                case 2: { // List books
                    boolean empty = loggedInUser.getBooks().isEmpty();

                    if (empty) {
                        System.out.println("Kitaplığınıza henüz kitap eklemediniz.");
                    }
                    else {
                        System.out.println("----- Okuma Listeniz -----");
                        loggedInUser.listBooks();
                        System.out.println("\n");

                        System.out.print("Geri gelmek için \"1\" > ");
                        int exitList = inputManager.getInt();
                        System.out.println();

                        while (exitList != 1) {
                            System.out.print("Geri gelmek için sadece 1'i tuşlayabilirsiniz\n> ");
                            exitList = inputManager.getInt();
                            System.out.println();
                        }
                    }
                    break;
                }
                case 1: { // Add book
                    boolean added = false;

                    while(!added) {
                        System.out.print("Eklemek istediğiniz kitabın adını giriniz\n> ");
                        String bookName = inputManager.getString();
                        System.out.println();

                        System.out.print("Kitabınızın yazarının adını giriniz\n> ");
                        String authorName = inputManager.getString();
                        System.out.println();

                        String category = categoryMenu(); // Select category from category menu

                        Book book = new Book(bookName, authorName, category); // Create new book object for bookshelf
                        ReadingRecord record = new ReadingRecord(book, BookSource.PERSONAL); // Create reading record for new book to user

                        ReadingStatus status = readInfoMenu(); // Select reading statu from read info menu
                        record.setStatus(status);

                        System.out.print("Kitabınızı 10 üzerinden puanlayınız\n> ");
                        int puan = inputManager.getInt(0, 10);
                        System.out.println();

                        record.setRating(puan);
                        added = loggedInUser.addBooks(record);

                        if (!added) {
                            System.out.println("Bu kitap zaten kitaplığında bulunuyor.");
                        } 
                        else {
                            System.out.println("Kitabınız kitaplığınıza başarıyla eklendi.");
                            JsonManager.saveUsers(auth.getUsers());
                        }
                    }
                    break;
                }
            }

            if (choice == 3) {
                break;
            }
        }
    }

    private String categoryMenu() { // Category Menu

        System.out.println("1- Dünya Klasikleri\n2- Tarih\n3- Psikoloji\n4- Aşk\n5- Korku-Gerilim\n6- Bilim-Kurgu\n7- Polisiye\n8- Aksiyon-Macera\n9- Şiir\n10- Çocuk\n11- Felsefe\n12- Sosyoloji\n13- Biyografi\n14- Makale\n15- Deneme\n16- Bilim-Teknoloji");
        System.out.print("Kitabınızın kategorisini giriniz\n> ");
        int choose = inputManager.getInt(1, 16);
        System.out.println();

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

    private ReadingStatus readInfoMenu() { // Reading Info Menu

        System.out.print("1- Okudum\n2- Okuyorum\n3- Okuyacağım\nKitabı okuma durumunuzu belirtiniz\n> ");
        int choose = inputManager.getInt(1, 3);
        System.out.println();
    
        switch (choose) {
            case 1:
                return ReadingStatus.READ;
    
            case 2:
                return ReadingStatus.READING;
    
            case 3:
                return ReadingStatus.WANT_TO_READ;
    
            default:
                System.out.println("Geçersiz seçim!");
                return null;
        }
    }
}