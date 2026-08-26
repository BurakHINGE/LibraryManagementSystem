public class LibraryMenu {

    private Library library;
    private AuthenticationManager auth;
    private InputManager inputManager;
    
    public LibraryMenu(Library library, AuthenticationManager auth) {
        this.library = library;
        this.auth = auth;
        this.inputManager = new InputManager();
    }


    public void showLibraryMenu(User loggedInUser) {

        System.out.println("Kütüphaneye Hoş Geldin " + loggedInUser.getUsername());

        while (true) {
            
            System.out.println("----- Kütüphane -----");
            System.out.println("1 - Kitapları Gör\n2 - Kitap Ara\n3 - Kitap Ödünç Al\n4 - Kitap İade Et\n5 - Ödünç Aldıklarım\n6 - Ana Sayfaya Dön");
            int choice = inputManager.getInt(1, 6);

            switch (choice) {
                case 1: { // See books in the library
                    for (LibraryBook libraryBook : library.getBooks()) {

                        Book book = libraryBook.getBook();
                        System.out.println(book.getTitle() + " - " + book.getAuthorName() + " - " + book.getCategory()
                        + " | Müsait: " + libraryBook.getAvailableCopies() + "/" + libraryBook.getTotalCopies());
                    }
                    break;
                }
                case 2: { // Search books in the library
                    System.out.println("Aramak istediğiniz kitabın adını giriniz > ");
                    String bookName = inputManager.getString().trim().toLowerCase();
                    boolean found = false;

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

                    System.out.println("Geri gelmek için 0 giriniz\n> ");
                    int exitChoice = inputManager.getInt();

                    while (exitChoice != 0){
                        System.out.println("Geri gelmek için sadece 1'i tuşlayabilirsiniz: ");
                        exitChoice = inputManager.getInt();
                    }
                    break;
                }
                case 3: { // Borrow book from library
                    System.out.println("----- Kütüphanedeki Kitaplar -----");

                    for (LibraryBook libraryBook : library.getBooks()) {

                        Book book = libraryBook.getBook();
                        System.out.println("ID: " + book.getID() + "\n" + book.getTitle() + " - " + book.getAuthorName() 
                            + "\nMüsait: " + libraryBook.getAvailableCopies() + "/" + libraryBook.getTotalCopies());
                    }

                    System.out.println("Ödünç almak istediğiniz kitabın ID'sini giriniz\nÇıkış Yapmak için 0 Giriniz\n> ");
                    int idChoice = inputManager.getInt();

                    if (idChoice == 0) {
                        break;
                    }

                    LibraryBook chosenLibraryBook = library.findBook(idChoice);

                    if (chosenLibraryBook == null) {
                        System.out.println("Bu ID'ye sahip bir kitap bulunamadı.");
                    }
                    else if (chosenLibraryBook.getAvailableCopies() <= 0) {
                        System.out.println("Bu kitabın şu anda müsait kopyası bulunmuyor.");
                    }
                    else {
                        boolean borrowed = library.borrowBookFromLibrary(idChoice, loggedInUser); //Create reading record for user
                        
                        if (borrowed) {
                            System.out.println("Kitap başarıyla ödünç alındı.");
                        }
                    }
                    break;
                }
                case 4: { // Return book to library
                    System.out.println("----- Ödünç Alınan Kitaplar -----");

                    boolean hasActiveLoan = false;

                    for (Loan loan : loggedInUser.getLoans()) {
                        if (!loan.isReturned()) {
                            hasActiveLoan = true;

                            Book book = loan.getLibraryBook().getBook();

                            System.out.println("Loan ID: " + loan.getLoanID() +"\n" + book.getTitle() + " - " + book.getAuthorName()
                            + "\nAlınma Tarihi: " + loan.getBorrowDate());
                        }
                    }

                    if (hasActiveLoan) { // Print if there is no any loan
                        System.out.println("Ödünç alınan kitabınız bulunmuyor.");
                        break;
                    }

                    System.out.println("\nİade etmek istediğiniz kitabın Loan ID'sini giriniz\nÇıkış için 0 giriniz\n> ");
                    int idChoice = inputManager.getInt();

                    if (idChoice == 0) {
                        break;
                    }

                    boolean returned = library.returnBookToLibrary(idChoice, loggedInUser);

                    if (returned) {
                        System.out.println("Kitap başarıyla iade edildi.");
                    }
                    else {
                        System.out.println("Geçersiz Loan ID veya bu kitap zaten iade edilmiş.");
                    }
                    break;
                }
                case 5: { // Show loaned book list
                    System.out.println("----- Ödünç Alınan Kitaplarınız -----");

                    for (Loan loan : loggedInUser.getLoans()) {
                        if (!loan.isReturned()) {
                            Book book = loan.getLibraryBook().getBook();

                            System.out.println(book.getTitle() + " - " + book.getAuthorName() + "\nAlınma Tarihi: "
                            + loan.getBorrowDate());
                        }
                    }

                    System.out.println("Geri gelmek için 0 giriniz\n> ");
                    int exitChoice = inputManager.getInt();

                    while (exitChoice != 0) {
                        System.out.println("Geri gelmek için sadece 1'i tuşlayabilirsiniz: ");
                        exitChoice = inputManager.getInt();
                    }
                    break;
                }
            }

            if (choice == 6) {
                break;
            }
        }

        System.out.println("Ana Sayfaya Dönülüyor...");
    }
}