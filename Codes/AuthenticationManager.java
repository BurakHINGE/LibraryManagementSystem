import java.util.ArrayList;
import java.util.Scanner;

public class AuthenticationManager {

    private Scanner input;
    public static final int SHORT = 0;
    public static final int ALL_REQ_DENIED = 1;
    public static final int CAPITAL_LOWER = 2;
    public static final int CAPITAL_SPECIAL = 3;
    public static final int LOWER_SPECIAL = 4;
    public static final int SPECIAL = 5;
    public static final int LOWER = 6;
    public static final int CAPITAL = 7;
    public static final int ALLOWED = 8;
    private ArrayList<User> users;
    
    public AuthenticationManager() {
        this.input = new Scanner(System.in);
        users = JsonManager.loadUsers();
    }

    public User login(String username, String password) {

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {

                return user;
            }
        }

        return null;
    }

    public boolean register(String username, String password) {

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }

        User newUser = new User(username, password);
        users.add(newUser);
        JsonManager.saveUsers(users);
        return true;
    }

    public int logout() {
        System.out.println("Kütüphaneden çıkış yapılıyor...");
        return 0;
    }

    public boolean isAllowedUsername(String username) {

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }

        return true;
    }

    public int isAllowedPassword(String password) {

        int capitalLetter = 0;
        int lowerCaseLetter = 0;
        int specialCharacter = 0;

        if (password.length() < 8) {
            return SHORT;
        }    

        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                capitalLetter++;
            }
            else if (Character.isLowerCase(password.charAt(i))) {
                lowerCaseLetter++;
            }
            else if (!Character.isLetterOrDigit(password.charAt(i))) {
                    specialCharacter++;
            }
        }
            
        if (capitalLetter < 1 && lowerCaseLetter < 1 && specialCharacter < 1) {
            return ALL_REQ_DENIED;
        }
        else if (capitalLetter < 1 && lowerCaseLetter < 1 && specialCharacter > 0) {
            return CAPITAL_LOWER;
        }
        else if (capitalLetter < 1 && lowerCaseLetter > 0 && specialCharacter < 1) {
            return CAPITAL_SPECIAL;
        }
        else if (capitalLetter > 0 && lowerCaseLetter < 1 && specialCharacter < 1) {
            return LOWER_SPECIAL;
        }
        else if (capitalLetter > 0 && lowerCaseLetter > 0 && specialCharacter < 1) {
            return SPECIAL;
        }
        else if (capitalLetter > 0 && lowerCaseLetter < 1 && specialCharacter > 0) {
            return LOWER;
        }
        else if (capitalLetter < 1 && lowerCaseLetter > 0 && specialCharacter > 0) {
            return CAPITAL;
        }
        else {
            return ALLOWED;
        } 
    }

    public String testSifre(String pass, String testPass, Scanner input) {

        while (!pass.equals(testPass)) {
            System.out.println("Farklı şifreler girdiniz, lütfen tekrar deneyin.");
            System.out.println("Şifrenizi giriniz: ");
            pass = input.nextLine();
            System.out.println("Aynı şifreyi tekrar giriniz: ");
            testPass = input.nextLine();
        }
        return pass;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}