public class AdminMenu {
    
    private Library library;
    private InputManager inputManager;
    private AuthenticationManager auth;

    public AdminMenu(Library library, AuthenticationManager auth) {
        this.library = library;
        this.inputManager = new InputManager();
        this.auth = auth;
    }

    public void showAdminMenu(User loggedInUser) {

        System.out.println("Admin Paneline Hoş Geldin " + loggedInUser.getUsername());

        while (true) { 
            System.out.println("----- ADMIN PANEL -----");
            System.out.println("Yapmak istediğiniz işlemi seçini:\n1 - Kitapları Gör\n2 - Kitap Ara\n3 - Kitap Ekle\n4 - Kitap Sil\n"
            + "5 - Kitap Bilgilerini Düzenle\n6 - Kullanıcıları Gör\n7 - Kullanıcı Sil\n8 - Ödünç Alınan Kitapları Gör\n"
            + "9 - Ana Sayfaya Dön");
            int choice = inputManager.getInt(1, 9);
            System.out.println();

            switch (choice) {
                case 1: {
                    for (LibraryBook libraryBook : library.getBooks()) {

                        Book book = libraryBook.getBook();
                        System.out.println(book.getTitle() + " - " + book.getAuthorName() + " - " + book.getCategory()
                        + " | Müsait: " + libraryBook.getAvailableCopies() + "/" + libraryBook.getTotalCopies());
                    }
                    break;
                }
                case 2: {
                    System.out.print("Aramak istediğiniz kitabın adını giriniz > ");
                    String bookName = inputManager.getString().trim().toLowerCase();
                    boolean found = false;
                    System.out.println();

                    for (LibraryBook libraryBook : library.getBooks()) {
                        if (libraryBook.getBook().getTitle().toLowerCase().contains(bookName)) {
                            found = true;
                            System.out.println(libraryBook.getBook().getTitle() + " - " + libraryBook.getBook().getAuthorName() 
                            + " - " + libraryBook.getBook().getCategory() + " | Müsait: " + libraryBook.getAvailableCopies() + "/" 
                            + libraryBook.getTotalCopies());
                        }
                    }

                    if (!found) {
                        System.out.println("Aradığınız kitap bulunamadı.");
                    }

                    System.out.print("Geri gelmek için 0 giriniz\n> ");
                    int exitChoice = inputManager.getInt();
                    System.out.println();

                    while (exitChoice != 0){
                        System.out.print("Geri gelmek için sadece 0'ı tuşlayabilirsiniz > ");
                        exitChoice = inputManager.getInt();
                        System.out.println();
                    }
                    break;
                }
                case 3: {
                    System.out.println("----- KİTAP EKLE -----");
                    System.out.print("Kitap adı: ");
                    String title = inputManager.getString();

                    System.out.print("Yazar adı: ");
                    String authorName = inputManager.getString();

                    System.out.println("Kategori seçiniz:");
                    String category = categoryMenu();

                    System.out.print("Kopya sayısı: ");
                    int totalCopies = inputManager.getInt(1, 100);

                    Book book = new Book(title, authorName, category);
                    LibraryBook libraryBook = new LibraryBook(book, totalCopies);

                    if (library.addBook(libraryBook)) {
                        System.out.println("Kitap başarıyla kütüphaneye eklendi.");

                    } else {
                        System.out.println("Kitap kütüphaneye eklenemedi.");
                    }
                    break;
                }
                case 4: {
                    System.out.println("----- Kitap Sil -----");

                    for (LibraryBook libraryBook : library.getBooks()) {

                        Book book = libraryBook.getBook();

                        System.out.println("ID: " + book.getID() + "\n" + book.getTitle() + " - " + book.getAuthorName()
                        + "\nMüsait: " + libraryBook.getAvailableCopies() + "/" + libraryBook.getTotalCopies() + "\n");
                    }

                    System.out.print("Silmek istediğiniz kitabın ID'sini giriniz\nÇıkış için 0 giriniz\n> ");
                    System.out.println();
                    int idChoice = inputManager.getInt();

                    if (idChoice == 0) {
                        break;
                    }

                    LibraryBook selectedBook = library.findBook(idChoice);

                    if (selectedBook == null) {
                        System.out.println("Bu ID'ye sahip bir kitap bulunamadı.");
                        break;
                    }

                    int availableCopies = selectedBook.getAvailableCopies();

                    if (availableCopies == 0) {
                        System.out.println("Bu kitabın tüm kopyaları ödünç alınmış. Silinebilecek kopya bulunmuyor.");
                        break;
                    }

                    System.out.println("Bu kitaptan " + availableCopies + " adet kopya müsait.");
                    System.out.println("Kaç adet kopya silmek istiyorsunuz?");

                    int amount = inputManager.getInt(1, availableCopies);
                    boolean removed = library.removeCopies(idChoice, amount);

                    if (removed) {
                        System.out.println("Kitap kopyaları başarıyla silindi.");
                    }
                    break;
                }
                case 5: { // Edit book information

                    System.out.println("----- Kitap Bilgilerini Düzenle -----");
                
                    for (LibraryBook libraryBook : library.getBooks()) {
                
                        Book book = libraryBook.getBook();
                
                        System.out.println("ID: " + book.getID() + "\n" + book.getTitle() + " - " + book.getAuthorName()
                        + " - " + book.getCategory() + "\nMüsait: " + libraryBook.getAvailableCopies() + "/"
                        + libraryBook.getTotalCopies() + "\n");
                    }
                
                    System.out.print("Düzenlemek istediğiniz kitabın ID'sini giriniz\nÇıkış için 0 giriniz\n> ");
                    System.out.println();
                
                    int idChoice = inputManager.getInt();
                
                    if (idChoice == 0) {
                        break;
                    }
                
                    LibraryBook selectedBook = library.findBook(idChoice);
                
                    if (selectedBook == null) {
                        System.out.println("Bu ID'ye sahip bir kitap bulunamadı.");
                        break;
                    }
                
                    Book book = selectedBook.getBook();
                
                    System.out.println("\nMevcut bilgiler:");
                    System.out.println("Kitap Adı: " + book.getTitle());
                    System.out.println("Yazar: " + book.getAuthorName());
                    System.out.println("Kategori: " + book.getCategory());
                    System.out.println("Toplam Kopya: " + selectedBook.getTotalCopies()); 
                    System.out.println();
                
                    System.out.println("Yeni kitap adını giriniz:");
                    String title = inputManager.getString();
                
                    System.out.println("Yeni yazar adını giriniz:");
                    String authorName = inputManager.getString();
                
                    System.out.println("Yeni kategoriyi seçiniz:");
                    String category = categoryMenu();
                
                    int borrowedCopies = selectedBook.getTotalCopies() - selectedBook.getAvailableCopies();
                
                    System.out.println("Yeni toplam kopya sayısını giriniz.\nÖdünçte olan kopya sayısı: " + borrowedCopies);
                
                    int totalCopies = inputManager.getInt(borrowedCopies, 1000);
                    boolean updated = library.updateBook(idChoice, title, authorName, category, totalCopies);
                
                    if (updated) {
                        System.out.println("Kitap bilgileri başarıyla güncellendi.");
                    } 
                    else {
                        System.out.println("Kitap bilgileri güncellenemedi.");
                    }
                    break;
                }
                case 6: { // Show users

                    System.out.println("----- KULLANICILAR -----");
                
                    for (User user : auth.getUsers()) {
                        System.out.println("Kullanıcı Adı: " + user.getUsername() + " | Rol: " + user.getRole());
                    }
                
                    System.out.println();
                    System.out.print("Geri gelmek için 0 giriniz\n> ");
                    int exitChoice = inputManager.getInt();
                
                    while (exitChoice != 0) {
                        System.out.print("Geri gelmek için sadece 0'ı tuşlayabilirsiniz > ");
                        exitChoice = inputManager.getInt();
                    }
                
                    break;
                }
                case 7: { // Delete user

                    System.out.println("----- KULLANICI SİL -----");
                    System.out.println("Silmek istediğiniz kullanıcının kullanıcı adını giriniz:");
                    String username = inputManager.getString();
                
                    if (username.equals(loggedInUser.getUsername())) {
                        System.out.println("Kendi hesabınızı silemezsiniz.");
                        break;
                    }
                
                    User targetUser = null;
                
                    for (User user : auth.getUsers()) {
                        if (user.getUsername().equals(username)) {
                            targetUser = user;
                            break;
                        }
                    }
                
                    if (targetUser == null) {
                        System.out.println("Bu kullanıcı adına sahip bir kullanıcı bulunamadı.");
                        break;
                    }
                
                    boolean hasActiveLoan = false;
                
                    for (Loan loan : targetUser.getLoans()) {
                        if (!loan.isReturned()) {
                            hasActiveLoan = true;
                            break;
                        }
                    }
                
                    if (hasActiveLoan) {
                        System.out.println("Bu kullanıcı silinemez.");
                        System.out.println("Kullanıcının hâlâ kütüphaneden ödünç aldığı kitaplar bulunuyor.");
                        break;
                    }
                
                    boolean deleted = auth.removeUser(username);
                
                    if (deleted) {
                        System.out.println("Kullanıcı başarıyla silindi.");
                    }
                    else {
                        System.out.println("Kullanıcı silinirken bir hata oluştu.");
                    }
                
                    break;
                }
                case 8: { // See borrowed books

                    System.out.println("----- Ödünç Alınan Kitaplar -----");
                    boolean hasActiveLoan = false;
                
                    for (User user : auth.getUsers()) {
                        for (Loan loan : user.getLoans()) {
                
                            if (!loan.isReturned()) {
                                hasActiveLoan = true;
                
                                Book book = loan.getLibraryBook().getBook();
                
                                System.out.println(
                                    "Loan ID: " + loan.getLoanID() +
                                    "\nKullanıcı: " + user.getUsername() +
                                    "\nKitap: " + book.getTitle() +
                                    "\nYazar: " + book.getAuthorName() +
                                    "\nKategori: " + book.getCategory() +
                                    "\nAlınma Tarihi: " + loan.getBorrowDate() +
                                    "\n-----------------------------"
                                );
                            }
                        }
                    }
                
                    if (!hasActiveLoan) {
                        System.out.println("Şu anda ödünç alınmış kitap bulunmuyor.");
                    }
                
                    System.out.print("\nGeri gelmek için 0 giriniz\n> ");
                    int exitChoice = inputManager.getInt();
                    System.out.println();
                
                    while (exitChoice != 0) {
                        System.out.print("Geri gelmek için sadece 0'ı tuşlayabilirsiniz > ");
                        exitChoice = inputManager.getInt();
                        System.out.println();
                    }
                
                    break;
                }
            }

            if (choice == 9) {
                break;
            }
        }

        System.out.println("Ana Sayfaya Dönülüyor...");
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
}
