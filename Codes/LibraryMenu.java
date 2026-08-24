
import java.util.Scanner;

public class LibraryMenu {

    private Scanner input;
    private Library library;
    private AuthenticationManager auth;
    
    public LibraryMenu(Library library, AuthenticationManager auth) {
        input = new Scanner(System.in);
        this.library = library;
        this.auth = auth;
    }

    public void showLibraryMenu(User loggedInUser) {

        System.out.println("Kütüphaneye Hoş Geldin " + loggedInUser.getUsername());

        while (true) { 
            
            System.out.println("----- Kütüphane -----");
            System.out.println("1 - Kitapları Gör\n2 - Kitap Ara\n3 - Kitap Ödünç Al\n4 - Kitap İade Et\n5 - Ödünç Aldıklarım\n6 - Ana Sayfaya Dön");
            int choice = input.nextInt();

            while (choice < 1 || choice > 6) {
                System.out.println("Lütfen geçerli bir işlem giriniz: ");
                System.out.println("1 - Kitapları Gör\\n2 - Kitap Ara\\n3 - Kitap Ödünç Al\\n4 - Kitap İade Et\\n5 - Ödünç Aldıklarım\\n6 - Ana Sayfaya Dön");
                choice = input.nextInt();
                input.nextLine();
            }

            switch (choice) {
                case 1: {
                    for (LibraryBook libraryBook : library.getBooks()) {

                        Book book = libraryBook.getBook();
                        System.out.println(book.getTitle() + " - " + book.getAuthorName() + " - " + book.getCategory()
                            + " | Müsait: " + libraryBook.getAvailableCopies() + "/" + libraryBook.getTotalCopies());
                    }
                }
                case 2: {

                }
                case 3: {
                    System.out.println("----- Kütüphanedeki Kitaplar -----");

                    for (LibraryBook libraryBook : library.getBooks()) {

                        Book book = libraryBook.getBook();
                        System.out.println("ID: " + book.getID() + "\n" + book.getTitle() + " - " + book.getAuthorName() 
                            + "\nMüsait: " + libraryBook.getAvailableCopies() + "/" + libraryBook.getTotalCopies());
                    }

                    System.out.println("Ödünç almak istediğiniz kitabın ID'sini giriniz\nÇıkış Yapmak için 0 Giriniz\n> ");
                    int idChoice = input.nextInt();

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
                        chosenLibraryBook.borrow(); //reduce available copies
                        library.borrowBookFromLibrary(idChoice, loggedInUser); //Create readind record for user
                        Loan createLoan = new Loan(idChoice, loggedInUser, chosenLibraryBook);
                    }
                    break;
                }
                case 4: {

                }
                case 5: {

                }
            }

            if (choice == 6) {
                break;
            }
        }

        System.out.println("Ana Sayfaya Dönülüyor...");
    }
}
