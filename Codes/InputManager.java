
import java.util.Scanner;

public class InputManager {
    
    private final Scanner scanner;

    public InputManager() {
        scanner = new Scanner(System.in);
    }

    public int getInt() {
        while (true) { 
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Lütfen geçerli bir sayı giriniz\n> ");
            }
        }
    }

    public int getInt(int min, int max) {
        while (true) {
            int value = getInt();
    
            if (value >= min && value <= max) {
                return value;
            }
    
            System.out.println("Lütfen " + min + " ile " + max + " arasında bir sayı girin.");
        }
    }

    public String getString() {
        return scanner.nextLine();
    }
}
