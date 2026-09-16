import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Control;

public class UIManager {

    private Stage stage;
    private StackPane rootPane;
    private AuthenticationManager auth;
    private Library library;
    private User currentUser;

    public UIManager(Stage stage, AuthenticationManager auth, Library library) {

        this.stage = stage;
        this.rootPane = new StackPane();
        this.auth = auth;
        this.library = library;
    }

    // =========================================================
    // APPLICATION
    // =========================================================
    public Scene startApp() {

        rootPane.getChildren().setAll(showMainMenu());
        Scene mainScene = new Scene(rootPane);
        
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        return mainScene;
    }

    // =========================================================
    // MAIN MENU
    // =========================================================
    public StackPane showMainMenu() {

        StackPane baseLayout = new StackPane();
        
        VBox layout = new VBox(25);
        layout.setAlignment(Pos.CENTER);
        layout.setPickOnBounds(false);

        Label title = new Label("LIBRARY MANAGEMENT");
        title.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.06).asString(), "px; -fx-font-weight: bold;"));

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        Button exitButton = new Button("Exit");

        applyScaling(loginButton, 0.2, 0.08, 0.025);
        applyScaling(registerButton, 0.2, 0.08, 0.025);
        applyScaling(exitButton, 0.15, 0.06, 0.02);

        loginButton.setOnAction(e -> showLayer(showLoginMenu()));
        registerButton.setOnAction(e -> showLayer(showRegisterMenu()));
        exitButton.setOnAction(e -> {
            if (auth != null && auth.getUsers() != null) {
                JsonManager.saveUsers(auth.getUsers());
                System.out.println("DEBUG: Uygulama kapatiliyor, JSON kaydedildi.");
            }
            stage.close();
        });

        layout.getChildren().addAll(title, loginButton, registerButton);
        HBox bottomRightBox = createBottomRightBox(exitButton);

        baseLayout.getChildren().addAll(layout, bottomRightBox);
        return baseLayout;
    }

    // =========================================================
    // LOGIN
    // =========================================================
    public StackPane showLoginMenu() {

        StackPane baseLayout = new StackPane();
        
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPickOnBounds(false);

        Label title = new Label("Login");
        title.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.05).asString(), "px; -fx-font-weight: bold;"));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        Button backButton = new Button("Back");

        Label errorLabel = new Label();
        errorLabel.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.02).asString(), "px; -fx-text-fill: red;"));

        applyScaling(usernameField, 0.25, 0.06, 0.02);
        applyScaling(passwordField, 0.25, 0.06, 0.02);
        applyScaling(loginButton, 0.25, 0.06, 0.02);
        applyScaling(backButton, 0.15, 0.06, 0.02);

        loginButton.setOnAction(e -> {

            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isBlank()) {
                errorLabel.setText("Please enter your username.");
                return;
            }
            if (password.isBlank()) {
                errorLabel.setText("Please enter your password.");
                return;
            }
            if (!auth.usernameExists(username)) {
                errorLabel.setText("Username not found.");
                return;
            }

            User loggedInUser = auth.login(username, password);

            if (loggedInUser == null) {
                errorLabel.setText("Wrong password.");
                return;
            }

            currentUser = loggedInUser;
            
            if (currentUser.getRole() == Role.ADMIN) {
                showLayer(showAdminMenu());
            } else {
                showLayer(showUserMenu());
            }
        });

        backButton.setOnAction(e -> showLayer(showMainMenu()));
        
        layout.getChildren().addAll(title, usernameField, passwordField, loginButton, errorLabel);
        HBox bottomRightBox = createBottomRightBox(backButton);

        baseLayout.getChildren().addAll(layout, bottomRightBox);
        return baseLayout;
    }

    // =========================================================
    // REGISTER
    // =========================================================
    public StackPane showRegisterMenu() {

        StackPane baseLayout = new StackPane();
        
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPickOnBounds(false);

        Label title = new Label("Register");
        title.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.05).asString(), "px; -fx-font-weight: bold;"));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        PasswordField ctrlPasswordField = new PasswordField();
        ctrlPasswordField.setPromptText("Confirm Password");

        Button registerButton = new Button("Register");
        Button backButton = new Button("Back");

        Label errorLabel = new Label();
        errorLabel.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.015).asString(), "px; -fx-text-fill: red;"));

        applyScaling(usernameField, 0.25, 0.06, 0.02);
        applyScaling(passwordField, 0.25, 0.06, 0.02);
        applyScaling(ctrlPasswordField, 0.25, 0.06, 0.02);
        applyScaling(registerButton, 0.25, 0.06, 0.02);
        applyScaling(backButton, 0.15, 0.06, 0.02);

        registerButton.setOnAction(e -> {

            String username = usernameField.getText();
            String password = passwordField.getText();
            String ctrlPassword = ctrlPasswordField.getText();

            if (username.isBlank()) {
                errorLabel.setText("Please enter a username.");
                return;
            }
            if (password.isBlank()) {
                errorLabel.setText("Please enter a password.");
                return;
            }
            if (!auth.isAllowedUsername(username)) {
                errorLabel.setText("Username already exists.");
                return;
            }

            int passwordResult = auth.isAllowedPassword(password);
            
            if (passwordResult != AuthenticationManager.ALLOWED) {
                switch (passwordResult) {
                    case AuthenticationManager.SHORT:
                        errorLabel.setText("Password must be at least 8 characters.");
                        break;
                    case AuthenticationManager.ALL_REQ_DENIED:
                        errorLabel.setText("Password must contain uppercase, lowercase and special characters.");
                        break;
                    case AuthenticationManager.CAPITAL_LOWER:
                        errorLabel.setText("Password needs uppercase and lowercase letters.");
                        break;
                    case AuthenticationManager.CAPITAL_SPECIAL:
                        errorLabel.setText("Password needs uppercase letters and special characters.");
                        break;
                    case AuthenticationManager.LOWER_SPECIAL:
                        errorLabel.setText("Password needs lowercase letters and special characters.");
                        break;
                    case AuthenticationManager.SPECIAL:
                        errorLabel.setText("Password needs a special character.");
                        break;
                    case AuthenticationManager.LOWER:
                        errorLabel.setText("Password needs lowercase letters.");
                        break;
                    case AuthenticationManager.CAPITAL:
                        errorLabel.setText( "Password needs uppercase letters.");
                        break;
                }
                return;
            }

            if (!auth.passwordsMatch(password, ctrlPassword)) {
                errorLabel.setText("Passwords do not match.");
                return;
            }

            boolean registered = auth.register(username, password);
            
            if (registered) {
                errorLabel.setText("Registration successful!");
                errorLabel.setStyle("-fx-text-fill: green;");
            } else {
                errorLabel.setText("Registration failed.");
            }
        });

        backButton.setOnAction(e -> showLayer(showMainMenu()));
        
        layout.getChildren().addAll(title, usernameField, passwordField, ctrlPasswordField, registerButton, errorLabel);
        HBox bottomRightBox = createBottomRightBox(backButton);

        baseLayout.getChildren().addAll(layout, bottomRightBox);
        return baseLayout;
    }

    // =========================================================
    // USER MENU
    // =========================================================
    public StackPane showUserMenu() {

        StackPane baseLayout = new StackPane();
        BorderPane borderPane = new BorderPane(); 

        // --- SIDEBAR ---
        VBox sidebar = new VBox();
        sidebar.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-width: 0 1 0 0;");
        sidebar.prefWidthProperty().bind(rootPane.widthProperty().multiply(0.20));

        // Title
        Label menuTitle = new Label("User Panel");
        menuTitle.prefWidthProperty().bind(sidebar.prefWidthProperty());
        menuTitle.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.03).asString(), "px; -fx-font-weight: bold; -fx-padding: 20 0 20 20; -fx-border-color: darkgray; -fx-border-width: 0 0 3 0;"));

        // Content Area (Orta Kısım)
        VBox contentArea = new VBox(20);
        contentArea.setAlignment(Pos.CENTER);
        
        Label welcomeLabel = new Label("Welcome, " + currentUser.getUsername());
        welcomeLabel.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.04).asString(), "px;"));

        contentArea.getChildren().add(welcomeLabel);
        borderPane.setCenter(contentArea);

        // --- Library Section ---
        VBox libraryContainer = new VBox();
        Button libraryMainButton = createSidebarMainButton("Library ▼", sidebar);
        
        VBox librarySubMenu = new VBox();
        librarySubMenu.setVisible(false);
        librarySubMenu.setManaged(false);

        Button btnViewBooks = createSidebarSubButton("See Books", sidebar);
        btnViewBooks.setOnAction(e -> {
            contentArea.getChildren().clear();
            
            Label header = new Label("Library Books");
            header.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.04).asString(), "px; -fx-font-weight: bold;"));

            ScrollPane scroll = new ScrollPane();
            scroll.setFitToWidth(true);
            applyScaling(scroll, 0.6, 0.6, 0); 
            
            VBox listVBox = new VBox(10);
            listVBox.setAlignment(Pos.TOP_CENTER);
            listVBox.setPadding(new Insets(10));

            if (library.getBooks().isEmpty()) {
                Label emptyLbl = new Label("Library is currently empty.");
                emptyLbl.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.02).asString(), "px;"));
                listVBox.getChildren().add(emptyLbl);
            } else {
                for (LibraryBook libraryBook : library.getBooks()) {
                    Book b = libraryBook.getBook();
                    String info = b.getTitle() + " - " + b.getAuthorName() + " - " + b.getCategory() 
                            + "\nAvailable: " + libraryBook.getAvailableCopies() + "/" + libraryBook.getTotalCopies();
                    Label bookLbl = new Label(info);
                    bookLbl.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.02).asString(), "px; -fx-background-color: #f8f9fa; -fx-border-color: #cccccc; -fx-padding: 10;"));
                    bookLbl.prefWidthProperty().bind(scroll.widthProperty().subtract(40));
                    listVBox.getChildren().add(bookLbl);
                }
            }
            
            scroll.setContent(listVBox);
            contentArea.getChildren().addAll(header, scroll);
        });
        Button btnSearchBook = createSidebarSubButton("Search Book", sidebar);
        btnSearchBook.setOnAction(e -> {
            contentArea.getChildren().clear();
            
            Label header = new Label("Search Book");
            header.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.04).asString(), "px; -fx-font-weight: bold;"));

            HBox searchBox = new HBox(10);
            searchBox.setAlignment(Pos.CENTER);
            
            TextField searchField = new TextField();
            searchField.setPromptText("Enter book name...");
            applyScaling(searchField, 0.4, 0.05, 0.02);
            
            Button searchBtn = new Button("Search");
            applyScaling(searchBtn, 0.15, 0.05, 0.02);
            
            searchBox.getChildren().addAll(searchField, searchBtn);

            Label msgLabel = new Label();
            msgLabel.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.02).asString(), "px;"));

            ScrollPane scroll = new ScrollPane();
            scroll.setFitToWidth(true);
            applyScaling(scroll, 0.6, 0.5, 0); 
            
            VBox listVBox = new VBox(10);
            listVBox.setAlignment(Pos.TOP_CENTER);
            listVBox.setPadding(new Insets(10));
            scroll.setContent(listVBox);

            searchBtn.setOnAction(ev -> {
                listVBox.getChildren().clear();
                msgLabel.setText("");
                String query = searchField.getText().trim().toLowerCase();
                
                if (query.isEmpty()) {
                    msgLabel.setText("Please enter a book name.");
                    msgLabel.setStyle("-fx-text-fill: red;");
                    return;
                }

                boolean found = false;
                for (LibraryBook libraryBook : library.getBooks()) {
                    if (libraryBook.getBook().getTitle().toLowerCase().contains(query)) {
                        found = true;
                        Book b = libraryBook.getBook();
                        String info = b.getTitle() + " - " + b.getAuthorName() + " - " + b.getCategory() 
                                + "\nAvailable: " + libraryBook.getAvailableCopies() + "/" + libraryBook.getTotalCopies();
                        Label bookLbl = new Label(info);
                        bookLbl.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.02).asString(), "px; -fx-background-color: #f8f9fa; -fx-border-color: #cccccc; -fx-padding: 10;"));
                        bookLbl.prefWidthProperty().bind(scroll.widthProperty().subtract(40));
                        listVBox.getChildren().add(bookLbl);
                    }
                }

                if (!found) {
                    msgLabel.setText("Aradığınız kitap bulunamadı."); // Exact terminal wording
                    msgLabel.setStyle("-fx-text-fill: red;");
                }
            });

            contentArea.getChildren().addAll(header, searchBox, msgLabel, scroll);
        });
        Button btnBorrowBook = createSidebarSubButton("Borrow Book", sidebar);
        btnBorrowBook.setOnAction(e -> {
            contentArea.getChildren().clear();
            
            Label header = new Label("Borrow a Book");
            header.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.04).asString(), "px; -fx-font-weight: bold;"));

            ScrollPane scroll = new ScrollPane();
            scroll.setFitToWidth(true);
            applyScaling(scroll, 0.6, 0.5, 0); 
            
            VBox listVBox = new VBox(10);
            listVBox.setAlignment(Pos.TOP_CENTER);
            listVBox.setPadding(new Insets(10));
            scroll.setContent(listVBox);

            // Populate all library books with ID
            Runnable populateBooks = () -> {
                listVBox.getChildren().clear();
                for (LibraryBook libraryBook : library.getBooks()) {
                    Book b = libraryBook.getBook();
                    String info = "ID: " + b.getID() + "\n" + b.getTitle() + " - " + b.getAuthorName() 
                            + "\nAvailable: " + libraryBook.getAvailableCopies() + "/" + libraryBook.getTotalCopies();
                    Label bookLbl = new Label(info);
                    bookLbl.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.02).asString(), "px; -fx-background-color: #f8f9fa; -fx-border-color: #cccccc; -fx-padding: 10;"));
                    bookLbl.prefWidthProperty().bind(scroll.widthProperty().subtract(40));
                    listVBox.getChildren().add(bookLbl);
                }
            };
            populateBooks.run();

            HBox actionBox = new HBox(10);
            actionBox.setAlignment(Pos.CENTER);
            
            TextField idField = new TextField();
            idField.setPromptText("Enter Book ID...");
            applyScaling(idField, 0.4, 0.05, 0.02);
            
            Button borrowBtn = new Button("Borrow");
            applyScaling(borrowBtn, 0.15, 0.05, 0.02);
            
            actionBox.getChildren().addAll(idField, borrowBtn);

            Label msgLabel = new Label();
            msgLabel.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.02).asString(), "px;"));

            borrowBtn.setOnAction(ev -> {
                try {
                    int idChoice = Integer.parseInt(idField.getText().trim());
                    LibraryBook chosenLibraryBook = library.findBook(idChoice);

                    if (chosenLibraryBook == null) {
                        msgLabel.setText("Bu ID'ye sahip bir kitap bulunamadı.");
                        msgLabel.setStyle("-fx-text-fill: red;");
                    } else if (chosenLibraryBook.getAvailableCopies() <= 0) {
                        msgLabel.setText("Bu kitabın şu anda müsait kopyası bulunmuyor.");
                        msgLabel.setStyle("-fx-text-fill: red;");
                    } else {
                        boolean borrowed = library.borrowBookFromLibrary(idChoice, currentUser);
                        if (borrowed) {
                            JsonManager.saveUsers(auth.getUsers());
                            msgLabel.setText("Kitap başarıyla ödünç alındı.");
                            msgLabel.setStyle("-fx-text-fill: green;");
                            idField.clear();
                            populateBooks.run(); // Refresh list to update available copies
                        } else {
                            msgLabel.setText("Ödünç alma işlemi başarısız oldu.");
                            msgLabel.setStyle("-fx-text-fill: red;");
                        }
                    }
                } catch (NumberFormatException ex) {
                    msgLabel.setText("Geçerli bir ID giriniz.");
                    msgLabel.setStyle("-fx-text-fill: red;");
                }
            });

            contentArea.getChildren().addAll(header, scroll, actionBox, msgLabel);
        });
        Button btnReturnBook = createSidebarSubButton("Return Book", sidebar);
        btnReturnBook.setOnAction(e -> {
            contentArea.getChildren().clear();
            
            Label header = new Label("Return a Book");
            header.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.04).asString(), "px; -fx-font-weight: bold;"));

            ScrollPane scroll = new ScrollPane();
            scroll.setFitToWidth(true);
            applyScaling(scroll, 0.6, 0.5, 0); 
            
            VBox listVBox = new VBox(10);
            listVBox.setAlignment(Pos.TOP_CENTER);
            listVBox.setPadding(new Insets(10));
            scroll.setContent(listVBox);

            Label listMsgLabel = new Label();
            listMsgLabel.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.02).asString(), "px;"));

            Runnable populateLoans = () -> {
                listVBox.getChildren().clear();
                boolean hasActiveLoan = false;
                for (Loan loan : currentUser.getLoans()) {
                    if (!loan.isReturned()) {
                        hasActiveLoan = true;
                        Book book = loan.getLibraryBook().getBook();
                        String info = "Loan ID: " + loan.getLoanID() + "\n" + book.getTitle() + " - " + book.getAuthorName() 
                                + "\nBorrow Date: " + loan.getBorrowDate();
                        Label bookLbl = new Label(info);
                        bookLbl.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.02).asString(), "px; -fx-background-color: #f8f9fa; -fx-border-color: #cccccc; -fx-padding: 10;"));
                        bookLbl.prefWidthProperty().bind(scroll.widthProperty().subtract(40));
                        listVBox.getChildren().add(bookLbl);
                    }
                }
                
                if (!hasActiveLoan) {
                    listMsgLabel.setText("Ödünç alınan kitabınız bulunmuyor.");
                } else {
                    listMsgLabel.setText("");
                }
            };
            populateLoans.run();

            HBox actionBox = new HBox(10);
            actionBox.setAlignment(Pos.CENTER);
            
            TextField idField = new TextField();
            idField.setPromptText("Enter Loan ID...");
            applyScaling(idField, 0.4, 0.05, 0.02);
            
            Button returnBtn = new Button("Return");
            applyScaling(returnBtn, 0.15, 0.05, 0.02);
            
            actionBox.getChildren().addAll(idField, returnBtn);

            Label msgLabel = new Label();
            msgLabel.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.02).asString(), "px;"));

            returnBtn.setOnAction(ev -> {
                try {
                    int idChoice = Integer.parseInt(idField.getText().trim());
                    boolean returned = library.returnBookToLibrary(idChoice, currentUser);
                    
                    if (returned) {
                        JsonManager.saveUsers(auth.getUsers());
                        msgLabel.setText("Kitap başarıyla iade edildi.");
                        msgLabel.setStyle("-fx-text-fill: green;");
                        idField.clear();
                        populateLoans.run();
                    } else {
                        msgLabel.setText("Geçersiz Loan ID veya bu kitap zaten iade edilmiş.");
                        msgLabel.setStyle("-fx-text-fill: red;");
                    }
                } catch (NumberFormatException ex) {
                    msgLabel.setText("Geçerli bir Loan ID giriniz.");
                    msgLabel.setStyle("-fx-text-fill: red;");
                }
            });

            contentArea.getChildren().addAll(header, listMsgLabel, scroll, actionBox, msgLabel);
        });
        Button btnLoanedBooks = createSidebarSubButton("My Loaned Books", sidebar);
        btnLoanedBooks.setOnAction(e -> {
            contentArea.getChildren().clear();
            
            Label header = new Label("My Loaned Books");
            header.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.04).asString(), "px; -fx-font-weight: bold;"));

            ScrollPane scroll = new ScrollPane();
            scroll.setFitToWidth(true);
            applyScaling(scroll, 0.6, 0.6, 0); 
            
            VBox listVBox = new VBox(10);
            listVBox.setAlignment(Pos.TOP_CENTER);
            listVBox.setPadding(new Insets(10));

            boolean hasActiveLoan = false;
            for (Loan loan : currentUser.getLoans()) {
                if (!loan.isReturned()) {
                    hasActiveLoan = true;
                    Book book = loan.getLibraryBook().getBook();
                    String info = book.getTitle() + " - " + book.getAuthorName() + "\nBorrow Date: " + loan.getBorrowDate();
                    Label bookLbl = new Label(info);
                    bookLbl.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.02).asString(), "px; -fx-background-color: #f8f9fa; -fx-border-color: #cccccc; -fx-padding: 10;"));
                    bookLbl.prefWidthProperty().bind(scroll.widthProperty().subtract(40));
                    listVBox.getChildren().add(bookLbl);
                }
            }

            if (!hasActiveLoan) {
                Label emptyLbl = new Label("Ödünç alınan kitabınız bulunmuyor.");
                emptyLbl.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.02).asString(), "px;"));
                listVBox.getChildren().add(emptyLbl);
            }
            
            scroll.setContent(listVBox);
            contentArea.getChildren().addAll(header, scroll);
        });

        librarySubMenu.getChildren().addAll(btnViewBooks, btnSearchBook, btnBorrowBook, btnReturnBook, btnLoanedBooks);

        libraryMainButton.setOnAction(e -> {

            boolean isVisible = librarySubMenu.isVisible();
            librarySubMenu.setVisible(!isVisible);
            librarySubMenu.setManaged(!isVisible);
            libraryMainButton.setText(!isVisible ? "Library ▲" : "Library ▼");
        });

        libraryContainer.getChildren().addAll(libraryMainButton, librarySubMenu);

        // --- Bookshelf Section ---
        VBox bookshelfContainer = new VBox();
        Button bookshelfMainButton = createSidebarMainButton("Bookshelf ▼", sidebar);

        VBox bookshelfSubMenu = new VBox();
        bookshelfSubMenu.setVisible(false);
        bookshelfSubMenu.setManaged(false);

        Button btnAddBook = createSidebarSubButton("Add Book", sidebar);
        Button btnSeeBookshelf = createSidebarSubButton("See Bookshelf", sidebar);

        // Action: Add Book
        btnAddBook.setOnAction(e -> {

            contentArea.getChildren().clear();
            
            Label header = new Label("Add New Book");
            header.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.04).asString(), "px; -fx-font-weight: bold;"));

            TextField titleField = new TextField();
            titleField.setPromptText("Book Title");
            applyScaling(titleField, 0.4, 0.05, 0.02);

            TextField authorField = new TextField();
            authorField.setPromptText("Author Name");
            applyScaling(authorField, 0.4, 0.05, 0.02);

            ComboBox<String> categoryBox = new ComboBox<>();
            categoryBox.getItems().addAll("Dünya Klasikleri", "Tarih", "Psikoloji", "Aşk", "Korku-Gerilim", "Bilim-Kurgu", "Polisiye", "Aksiyon-Macera", "Şiir", "Çocuk", "Felsefe", "Sosyoloji", "Biyografi", "Makale", "Deneme", "Bilim-Teknoloji");
            categoryBox.setPromptText("Category");
            applyScaling(categoryBox, 0.4, 0.05, 0.02);

            ComboBox<ReadingStatus> statusBox = new ComboBox<>();
            statusBox.getItems().addAll(ReadingStatus.READ, ReadingStatus.READING, ReadingStatus.WANT_TO_READ);
            statusBox.setPromptText("Reading Status");
            applyScaling(statusBox, 0.4, 0.05, 0.02);

            TextField ratingField = new TextField();
            ratingField.setPromptText("Rating (0-10)");
            applyScaling(ratingField, 0.4, 0.05, 0.02);

            Button saveBtn = new Button("Save Book");
            applyScaling(saveBtn, 0.2, 0.05, 0.02);
            
            Label msgLabel = new Label();
            msgLabel.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.02).asString(), "px;"));

            saveBtn.setOnAction(ev -> {

                String bName = titleField.getText();
                String aName = authorField.getText();
                String cat = categoryBox.getValue();
                ReadingStatus stat = statusBox.getValue();
                String rateStr = ratingField.getText();

                if (bName.isBlank() || aName.isBlank() || cat == null || stat == null || rateStr.isBlank()) {
                    msgLabel.setText("Please fill all fields!");
                    msgLabel.setStyle("-fx-text-fill: red;");
                    return;
                }

                // Prevent adding duplicate book by title and author
                boolean alreadyExists = false;
                for (ReadingRecord existingRecord : currentUser.getBooks()) {
                    if (existingRecord.getBook().getTitle().equalsIgnoreCase(bName) && 
                        existingRecord.getBook().getAuthorName().equalsIgnoreCase(aName)) {
                        alreadyExists = true;
                        break;
                    }
                }
                
                if (alreadyExists) {
                    msgLabel.setText("This book is already in your bookshelf.");
                    msgLabel.setStyle("-fx-text-fill: red;");
                    return;
                }

                try {
                    int rate = Integer.parseInt(rateStr);
                    
                    if (rate < 0 || rate > 10) {
                        msgLabel.setText("Rating must be between 0-10.");
                        msgLabel.setStyle("-fx-text-fill: red;");
                        return;
                    }

                    Book book = new Book(bName, aName, cat);
                    ReadingRecord record = new ReadingRecord(book, BookSource.PERSONAL);
                    record.setStatus(stat);
                    record.setRating(rate);

                    boolean added = currentUser.addBooks(record);
                    
                    if (added) {
                        msgLabel.setText("Book added to bookshelf successfully!");
                        msgLabel.setStyle("-fx-text-fill: green;");
                        JsonManager.saveUsers(auth.getUsers()); 
                        System.out.println("DEBUG: Kitap eklendi, JSON'a yazma tetiklendi.");
                        
                        titleField.clear();
                        authorField.clear();
                        categoryBox.setValue(null);
                        statusBox.setValue(null);
                        ratingField.clear();
                    } else {
                        msgLabel.setText("This book is already in your bookshelf.");
                        msgLabel.setStyle("-fx-text-fill: red;");
                    }
                } catch (NumberFormatException ex) {
                    msgLabel.setText("Rating must be a valid number.");
                    msgLabel.setStyle("-fx-text-fill: red;");
                }
            });

            contentArea.getChildren().addAll(header, titleField, authorField, categoryBox, statusBox, ratingField, saveBtn, msgLabel);
        });

        // Action: See Bookshelf
        btnSeeBookshelf.setOnAction(e -> {

            contentArea.getChildren().clear();
            
            Label header = new Label("My Bookshelf");
            header.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.04).asString(), "px; -fx-font-weight: bold;"));

            ScrollPane scroll = new ScrollPane();
            scroll.setFitToWidth(true);
            applyScaling(scroll, 0.6, 0.6, 0); 
            
            VBox listVBox = new VBox(10);
            listVBox.setAlignment(Pos.TOP_CENTER);
            listVBox.setPadding(new Insets(10));

            if (currentUser.getBooks().isEmpty()) {
                Label emptyLbl = new Label("Your bookshelf is empty.");
                emptyLbl.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.02).asString(), "px;"));
                listVBox.getChildren().add(emptyLbl);
            } else {
                for (ReadingRecord record : currentUser.getBooks()) {
                    Book b = record.getBook();
                    String info = b.getTitle() + " - " + b.getAuthorName() + "\nCategory: " + b.getCategory() + " | Status: " + record.getStatus() + " | Rating: " + record.getRating() + "/10";
                    Label bookLbl = new Label(info);
                    bookLbl.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.02).asString(), "px; -fx-background-color: #f8f9fa; -fx-border-color: #cccccc; -fx-padding: 10;"));
                    bookLbl.prefWidthProperty().bind(scroll.widthProperty().subtract(40));
                    listVBox.getChildren().add(bookLbl);
                }
            }
            
            scroll.setContent(listVBox);
            contentArea.getChildren().addAll(header, scroll);
        });

        bookshelfSubMenu.getChildren().addAll(btnAddBook, btnSeeBookshelf);

        bookshelfMainButton.setOnAction(e -> {

            boolean isVisible = bookshelfSubMenu.isVisible();
            bookshelfSubMenu.setVisible(!isVisible);
            bookshelfSubMenu.setManaged(!isVisible);
            bookshelfMainButton.setText(!isVisible ? "Bookshelf ▲" : "Bookshelf ▼");
        });

        bookshelfContainer.getChildren().addAll(bookshelfMainButton, bookshelfSubMenu);

        sidebar.getChildren().addAll(menuTitle, libraryContainer, bookshelfContainer);
        borderPane.setLeft(sidebar);

        // --- LOGOUT BUTTON ---
        Button logoutButton = new Button("Logout");
        applyScaling(logoutButton, 0.15, 0.06, 0.02);
        logoutButton.setOnAction(e -> logout());
        HBox bottomRightBox = createBottomRightBox(logoutButton);

        baseLayout.getChildren().addAll(borderPane, bottomRightBox);
        return baseLayout;
    }

    // =========================================================
    // ADMIN MENU
    // =========================================================
    public StackPane showAdminMenu() {

        StackPane baseLayout = new StackPane();
        BorderPane borderPane = new BorderPane(); 

        // --- SIDEBAR ---
        VBox sidebar = new VBox();
        sidebar.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-width: 0 1 0 0;");
        sidebar.prefWidthProperty().bind(rootPane.widthProperty().multiply(0.20));

        // Title
        Label menuTitle = new Label("Admin Panel");
        menuTitle.prefWidthProperty().bind(sidebar.prefWidthProperty());
        menuTitle.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.03).asString(), "px; -fx-font-weight: bold; -fx-padding: 20 0 20 20; -fx-border-color: darkgray; -fx-border-width: 0 0 3 0;"));

        // Manage Section
        VBox manageContainer = new VBox();
        Button manageMainButton = createSidebarMainButton("Management ▼", sidebar);
        
        VBox manageSubMenu = new VBox();
        manageSubMenu.setVisible(false);
        manageSubMenu.setManaged(false);

        Button btnManageBooks = createSidebarSubButton("Manage Books", sidebar);
        Button btnManageUsers = createSidebarSubButton("Manage Users", sidebar);
        Button btnViewLoans = createSidebarSubButton("View Loans", sidebar);

        manageSubMenu.getChildren().addAll(btnManageBooks, btnManageUsers, btnViewLoans);

        manageMainButton.setOnAction(e -> {

            boolean isVisible = manageSubMenu.isVisible();
            manageSubMenu.setVisible(!isVisible);
            manageSubMenu.setManaged(!isVisible);
            manageMainButton.setText(!isVisible ? "Management ▲" : "Management ▼");
        });

        manageContainer.getChildren().addAll(manageMainButton, manageSubMenu);

        sidebar.getChildren().addAll(menuTitle, manageContainer);
        borderPane.setLeft(sidebar);

        // --- CONTENT AREA ---
        VBox contentArea = new VBox(20);
        contentArea.setAlignment(Pos.CENTER);
        
        Label welcomeLabel = new Label("Welcome, " + currentUser.getUsername());
        welcomeLabel.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(0.04).asString(), "px;"));

        contentArea.getChildren().add(welcomeLabel);
        borderPane.setCenter(contentArea);

        // --- LOGOUT BUTTON ---
        Button logoutButton = new Button("Logout");
        applyScaling(logoutButton, 0.15, 0.06, 0.02);
        logoutButton.setOnAction(e -> logout());
        HBox bottomRightBox = createBottomRightBox(logoutButton);

        baseLayout.getChildren().addAll(borderPane, bottomRightBox);
        return baseLayout;
    }

    // =========================================================
    // LOGOUT
    // =========================================================
    private void logout() {
        
        if (auth != null && auth.getUsers() != null) {
            JsonManager.saveUsers(auth.getUsers());
            System.out.println("DEBUG: Cikis yapiliyor, JSON kaydedildi.");
        }
        
        currentUser = null;
        auth.logout();
        showLayer(showMainMenu());
    }

    // =========================================================
    // SCREEN NAVIGATION
    // =========================================================
    private void showLayer(StackPane newLayer) {

        rootPane.getChildren().setAll(newLayer);
    }

    // =========================================================
    // HELPER METHODS
    // =========================================================
    
    // Scale properties based on screen size
    private void applyScaling(Control control, double widthFactor, double heightFactor, double fontFactor) {

        control.prefWidthProperty().bind(rootPane.widthProperty().multiply(widthFactor));
        control.maxWidthProperty().bind(control.prefWidthProperty());
        
        control.prefHeightProperty().bind(rootPane.heightProperty().multiply(heightFactor));
        control.maxHeightProperty().bind(control.prefHeightProperty());
        
        if (fontFactor > 0) {
            control.styleProperty().bind(Bindings.concat("-fx-font-size: ", rootPane.heightProperty().multiply(fontFactor).asString(), "px;"));
        }
    }

    // Wrap buttons in an HBox for bottom-right corner placement
    private HBox createBottomRightBox(Button... buttons) {

        HBox box = new HBox(15);
        box.setAlignment(Pos.BOTTOM_RIGHT);
        box.setPadding(new Insets(30)); 
        box.setPickOnBounds(false); 
        box.getChildren().addAll(buttons);
        
        return box;
    }

    // Create a main category button for the sidebar
    private Button createSidebarMainButton(String text, VBox sidebarBox) {

        Button btn = new Button(text);
        
        btn.prefWidthProperty().bind(sidebarBox.prefWidthProperty());
        btn.prefHeightProperty().bind(rootPane.heightProperty().multiply(0.08));
        
        btn.styleProperty().bind(Bindings.concat(
            "-fx-font-size: ", rootPane.heightProperty().multiply(0.02).asString(), "px;",
            "-fx-background-color: transparent; -fx-border-color: lightgray; -fx-border-width: 0 0 1 0; -fx-alignment: center-left; -fx-padding: 0 0 0 20; -fx-font-weight: bold;"
        ));
        
        return btn;
    }

    // Create a sub-menu button for the sidebar (grayish style)
    private Button createSidebarSubButton(String text, VBox sidebarBox) {

        Button btn = new Button(text);
        
        btn.prefWidthProperty().bind(sidebarBox.prefWidthProperty());
        btn.prefHeightProperty().bind(rootPane.heightProperty().multiply(0.06));
        
        btn.styleProperty().bind(Bindings.concat(
            "-fx-font-size: ", rootPane.heightProperty().multiply(0.015).asString(), "px;",
            "-fx-background-color: #f0f0f0; -fx-border-color: lightgray; -fx-border-width: 0 0 1 0; -fx-alignment: center-left; -fx-padding: 0 0 0 40; -fx-text-fill: #555555;"
        ));
        
        return btn;
    }
}