import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;

public class JsonManager {

    private static final String USER_FILE_NAME = "users.json";
    private static final String LIBRARY_FILE_NAME = "libraryBooks.json";

    public static void saveUsers(ArrayList<User> users) {

        try (FileWriter writer = new FileWriter(USER_FILE_NAME)) {

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            gson.toJson(users, writer);

        } catch (Exception e) {
            System.out.println("JSON kaydedilirken hata oluştu.");
            e.printStackTrace();
        }
    }

    public static ArrayList<User> loadUsers() {

        try (FileReader reader = new FileReader(USER_FILE_NAME)) {

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            Type userListType =
                    new TypeToken<ArrayList<User>>() {}.getType();

            ArrayList<User> users =
                    gson.fromJson(reader, userListType);

            if (users == null) {
                return new ArrayList<>();
            }

            return users;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void saveLibraryBooks(ArrayList<LibraryBook> books) {

        try (FileWriter writer = new FileWriter(LIBRARY_FILE_NAME)) {

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            gson.toJson(books, writer);

        } catch (Exception e) {
            System.out.println("Kütüphane kitapları JSON'a kaydedilirken hata oluştu.");
            e.printStackTrace();
        }
    }

    public static ArrayList<LibraryBook> loadLibraryBooks() {

        try (FileReader reader = new FileReader(LIBRARY_FILE_NAME)) {
    
            Gson gson = new GsonBuilder()
                    .create();
    
            Type libraryBookListType =
                    new TypeToken<ArrayList<LibraryBook>>() {}.getType();
    
            ArrayList<LibraryBook> books =
                    gson.fromJson(reader, libraryBookListType);
    
            if (books == null) {
                return new ArrayList<>();
            }
    
            for (LibraryBook libraryBook : books) {
                Book.updateNextID(libraryBook.getBook().getID());
            }
    
            return books;
    
        } catch (Exception e) {
            System.out.println("Kütüphane kitapları yüklenirken hata oluştu.");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}