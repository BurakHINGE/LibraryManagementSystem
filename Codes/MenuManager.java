import java.util.Scanner;

public class MenuManager {
    
    private Scanner input;
    private AuthenticationManager auth;
    private UserMenu userMenu;

    public MenuManager(AuthenticationManager auth, UserMenu userMenu) {
        this.input = new Scanner(System.in);
        this.auth = auth;
        this.userMenu = userMenu;
    }

    public void showMainMenu() {

        while (true) {
            System.out.println("Kütüphaneye Hoş Geldiniz!");
            System.out.println("1-Giriş Yap\n2-Kayıt Ol\n3-Çıkış Yap");
            int secenek = input.nextInt();
            input.nextLine();

            while (secenek < 1 || secenek > 3) {
                System.out.println("Lütfen geçerli bir işlem giriniz!");
                System.out.println("1-Giriş Yap\n2-Kayıt Ol\n3-Çıkış Yap");
                secenek = input.nextInt();
                input.nextLine();
            }

            if (secenek == 3) { //exit program
                break;
            }
            else if (secenek == 2) { //register
                registerMenu();
            }
            else if(secenek == 1) { //login
                loginMenu();
            }
        }
        auth.logout();
    }

    public void registerMenu() {

        System.out.println("-----Kayıt Ol-----");
        System.out.println("Kullanıcı Adı Seçiniz: ");
        String kullaniciAdi = input.nextLine();

        boolean isCorrectUsername = auth.isAllowedUsername(kullaniciAdi);

        while (!isCorrectUsername) {
            System.out.println("Bu isimde başka bir kullanıcı adı bulunuyor lütfen farklı bir kullanıcı adı seçiniz.");
            System.out.println("-----Kayıt Ol-----");
            System.out.println("Kullanıcı Adı Seçiniz: ");
            kullaniciAdi = input.nextLine();

            isCorrectUsername = auth.isAllowedUsername(kullaniciAdi);
       }
                
        System.out.println("Şifre Belirleyiniz: ");
        String sifre = input.nextLine();

        int isCorrectPassword = auth.isAllowedPassword(sifre);

        while (!(isCorrectPassword == 8)) {

            switch (isCorrectPassword) {
                case 0: {
                    System.out.println("Şifreniz çok kısa lütfen en az 8 karakterli şifre oluşturun!");
                    System.out.println("Şifrenizi giriniz: ");
                    sifre = input.nextLine();
                    break;
                }
                case 1: {
                    System.out.println("Şifrenizde en az birer adet küçük, büyük ve özel karakter bulunmalıdır!");
                    System.out.println("Şifrenizi giriniz: ");
                    sifre = input.nextLine();
                    break;
                }
                case 2: {
                    System.out.println("Şifrenizde en az birer adet büyük ve küçük harf bulunmalıdır!");
                    System.out.println("Şifrenizi giriniz: ");
                    sifre = input.nextLine();
                    break;
                }
                case 3: {
                    System.out.println("Şifrenizde en az birer adet büyük ve özel karakter bulunmalıdır!");
                    System.out.println("Şifrenizi giriniz: ");
                    sifre = input.nextLine();
                    break;
                }
                case 4: {
                    System.out.println("Şifrenizde en az birer adet küçük ve özel karakter bulunmalıdır!");
                    System.out.println("Şifrenizi giriniz: ");
                    sifre = input.nextLine();
                    break;
                }
                case 5: {
                    System.out.println("Şifrenizde en az bir adet özel karakter bulunmalıdır!");
                    System.out.println("Şifrenizi giriniz: ");
                    sifre = input.nextLine();
                    break;
                }
                case 6: {
                    System.out.println("Şifrenizde en az bir adet küçük harf bulunmalıdır!");
                    System.out.println("Şifrenizi giriniz: ");
                    sifre = input.nextLine();
                    break;
                }
                case 7: {
                    System.out.println("Şifrenizde en az bir adet büyük harf bulunmalıdır!");
                    System.out.println("Şifrenizi giriniz: ");
                    sifre = input.nextLine();
                    break;
                }
            }

            isCorrectPassword = auth.isAllowedPassword(sifre);
        }

        System.out.println("Aynı şifreyi tekrar giriniz: ");
        String tekrarSifre = input.nextLine();

        sifre = auth.testSifre(sifre, tekrarSifre, input);

        boolean isCorrect = auth.register(kullaniciAdi, sifre);

        while (!isCorrect) {
            System.out.println("Bu isimde başka bir kullanıcı adı bulunuyor lütfen farklı bir kullanıcı adı seçiniz.");

            System.out.println("-----Kayıt Ol-----");
            System.out.println("Kullanıcı Adı Seçiniz: ");
            kullaniciAdi = input.nextLine();
                    
            System.out.println("Şifre Belirleyiniz: ");
            sifre = input.nextLine();

            System.out.println("Aynı şifreyi tekrar giriniz: ");
            tekrarSifre = input.nextLine();

            sifre = auth.testSifre(sifre, tekrarSifre, input);

            isCorrect = auth.register(kullaniciAdi, sifre);
       }

        System.out.println("Hesabınız oluşturuldu!");
        System.out.println("Artık istediğiniz kitapları ve yazarlarını kitaplığınıza ekleyebilirsiniz.");
        System.out.println("Tekrardan giriş yapmalısınız:");
    }

    public void loginMenu() {

        System.out.println("Giriş yapmak için kullanıcı adınızı giriniz: ");
        String kullaniciAdi = input.nextLine();

        System.out.println("Şifrenizi giriniz: ");
        String sifre = input.nextLine();

        User loggedInUser = auth.login(kullaniciAdi, sifre);

        if (loggedInUser == null) {
            System.out.println("Hatalı kullanıcı adı veya şifre!");
            return;
        }

        userMenu.showUserMenu(loggedInUser);
    }
}