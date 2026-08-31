public class AdminMenu {
    
    private Library library;
    private InputManager inputManager;

    public AdminMenu(Library library) {
        this.library = library;
        this.inputManager = new InputManager();
    }

    public void showAdminMenu(User loggedInUser) {

        System.out.println("Admin Paneline Hoş Geldin " + loggedInUser.getUsername());

        while (true) { 
            System.out.println("----- ADMIN PANEL -----");
            System.out.println("Yapmak istediğiniz işlemi seçini:\n1 - Kitapları Gör\n2 - Kitap Ekle\n3 - Kitap Sil\n"
            + "4 - Kitap Bilgilerini Düzenle\n5 - Kullanıcıları Gör\n6 - Kullanıcı Sil\n7 - Ödünç Alınan Kitapları Gör"
            + "8 - Ana Sayfaya Dön");
            int choice = inputManager.getInt(1, 6);
            System.out.println();

            switch (choice) {
                case 1: {

                }
                case 2: {

                }
                case 3: {

                }
                case 4: {

                }
                case 5: {

                }
                case 6: {

                }
                case 7: {

                }
            }
            
            if (choice == 6) {
                break;
            }
        }

        System.out.println("Ana Sayfaya Dönülüyor...");
    }
}
