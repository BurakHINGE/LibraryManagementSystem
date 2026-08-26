public class MenuManager {

    private AuthenticationManager auth;
    private UserMenu userMenu;
    private InputManager inputManager;

    public MenuManager(AuthenticationManager auth, UserMenu userMenu) {
        this.auth = auth;
        this.userMenu = userMenu;
        this.inputManager = new InputManager();
    }

    public void showMainMenu() { // Main Menu

        while (true) {
            System.out.println("Uygulamaya Hoş Geldiniz!");
            System.out.println("1-Giriş Yap\n2-Kayıt Ol\n3-Çıkış Yap");

            int choice = inputManager.getInt(1, 3);

            if (choice == 3) {
                break;
            }
            else if (choice == 2) {
                registerMenu();
            }
            else if (choice == 1) {
                loginMenu();
            }
        }

        auth.logout();
    }

    private void registerMenu() { // Register Menu

        System.out.println("----- Kayıt Ol -----");
        System.out.println("Kullanıcı Adı Seçiniz:");
        String username = inputManager.getString();

        while (!auth.isAllowedUsername(username)) {
            System.out.println("Bu isimde başka bir kullanıcı adı bulunuyor. Lütfen farklı bir kullanıcı adı seçiniz.");
            System.out.println("Kullanıcı Adı Seçiniz:");
            username = inputManager.getString();
        }

        System.out.println("Şifre Belirleyiniz:");
        String password = inputManager.getString();

        int passwordValidation = auth.isAllowedPassword(password);

        while (passwordValidation != AuthenticationManager.ALLOWED) { // Check password requirements
            switch (passwordValidation) {
                case AuthenticationManager.SHORT:
                    System.out.println(
                        "Şifreniz çok kısa! " +
                        "Lütfen en az 8 karakterli şifre oluşturun."
                    );
                    break;
                case AuthenticationManager.ALL_REQ_DENIED:
                    System.out.println(
                        "Şifrenizde en az birer adet küçük, " +
                        "büyük ve özel karakter bulunmalıdır!"
                    );
                    break;
                case AuthenticationManager.CAPITAL_LOWER:
                    System.out.println(
                        "Şifrenizde en az birer adet büyük ve küçük harf bulunmalıdır!"
                    );
                    break;
                case AuthenticationManager.CAPITAL_SPECIAL:
                    System.out.println(
                        "Şifrenizde en az birer adet büyük ve özel karakter bulunmalıdır!"
                    );
                    break;
                case AuthenticationManager.LOWER_SPECIAL:
                    System.out.println(
                        "Şifrenizde en az birer adet küçük ve özel karakter bulunmalıdır!"
                    );
                    break;
                case AuthenticationManager.SPECIAL:
                    System.out.println(
                        "Şifrenizde en az bir adet özel karakter bulunmalıdır!"
                    );
                    break;
                case AuthenticationManager.LOWER:
                    System.out.println(
                        "Şifrenizde en az bir adet küçük harf bulunmalıdır!"
                    );
                    break;
                case AuthenticationManager.CAPITAL:
                    System.out.println(
                        "Şifrenizde en az bir adet büyük harf bulunmalıdır!"
                    );
                    break;
            }

            System.out.println("Şifrenizi giriniz:");
            password = inputManager.getString();
            passwordValidation = auth.isAllowedPassword(password);
        }

        // Password confirmation
        while (true) {

            System.out.println("Aynı şifreyi tekrar giriniz:");
            String againPassword = inputManager.getString();

            if (auth.passwordsMatch(password, againPassword)) {
                break;
            }

            System.out.println("Farklı şifreler girdiniz, lütfen tekrar deneyin.");
            System.out.println("Şifrenizi tekrar giriniz:");
            password = inputManager.getString();
        }

        // Check register
        boolean isRegistered = auth.register(username, password);

        if (isRegistered) {
            System.out.println("Hesabınız oluşturuldu!");
            System.out.println(
                "Artık istediğiniz kitapları ve yazarlarını " +
                "kitaplığınıza ekleyebilirsiniz."
            );
            System.out.println("Tekrardan giriş yapmalısınız:");
        }
    }

    private void loginMenu() { // Login Menu

        System.out.println("Giriş yapmak için kullanıcı adınızı giriniz:");
        String username = inputManager.getString();

        System.out.println("Şifrenizi giriniz:");
        String password = inputManager.getString();

        User loggedInUser = auth.login(username, password);

        if (loggedInUser == null) {
            System.out.println("Hatalı kullanıcı adı veya şifre!");
            return;
        }

        userMenu.showUserMenu(loggedInUser);
    }
}