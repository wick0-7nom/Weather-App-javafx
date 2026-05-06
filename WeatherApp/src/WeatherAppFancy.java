import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WeatherAppFancy extends Application {

    private final int SCENE_WIDTH = 600;
    private final int SCENE_HEIGHT = 700;
    private Database db = new Database();
    private Stage stage;
    private String loggedInUser;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("Weather App");
        stage.setResizable(false);

        Scene welcomeScene = createWelcomeScene();
        stage.setScene(welcomeScene);
        stage.show();
    }

    // ==================== WELCOME SCENE ====================
    private Scene createWelcomeScene() {
        Label welcomeText = new Label("Welcome to Weather App ☁");
        welcomeText.setFont(Font.font(36));
        welcomeText.setStyle(ThemeStyles.TEXT_WHITE);

        Button startBtn = new Button("Let's Begin");
        startBtn.setFont(Font.font(20));
        startBtn.setPadding(new Insets(15, 30, 15, 30));
        startBtn.setStyle(ThemeStyles.BUTTON_GOLD);

        VBox welcomeRoot = new VBox(40, welcomeText, startBtn);
        welcomeRoot.setAlignment(Pos.CENTER);
        welcomeRoot.setStyle(ThemeStyles.BACKGROUND);

        Scene welcomeScene = new Scene(welcomeRoot, SCENE_WIDTH, SCENE_HEIGHT);

        startBtn.setOnAction(e -> stage.setScene(createAuthScene()));

        return welcomeScene;
    }

    // ==================== AUTH SCENE (Login/Register Choice) ====================
    private Scene createAuthScene() {
        Label cloudIcon = new Label("☁");
        cloudIcon.setFont(Font.font(60));
        cloudIcon.setStyle(ThemeStyles.TEXT_WHITE);

        Label title = new Label("Weather App");
        title.setFont(Font.font(30));
        title.setStyle(ThemeStyles.TEXT_WHITE);

        Label subtitle = new Label("Please Login or Register to continue");
        subtitle.setFont(Font.font(16));
        subtitle.setStyle(ThemeStyles.TEXT_LIGHT_BLUE);

        Button loginBtn = new Button("🔐  Login");
        loginBtn.setFont(Font.font(20));
        loginBtn.setPrefWidth(220);
        loginBtn.setPadding(new Insets(15));
        loginBtn.setStyle(ThemeStyles.BUTTON_GOLD);

        Button registerBtn = new Button("📝  Register");
        registerBtn.setFont(Font.font(20));
        registerBtn.setPrefWidth(220);
        registerBtn.setPadding(new Insets(15));
        registerBtn.setStyle(ThemeStyles.BUTTON_GOLD);

        VBox authRoot = new VBox(25, cloudIcon, title, subtitle, loginBtn, registerBtn);
        authRoot.setAlignment(Pos.CENTER);
        authRoot.setStyle(ThemeStyles.BACKGROUND);

        Scene authScene = new Scene(authRoot, SCENE_WIDTH, SCENE_HEIGHT);

        loginBtn.setOnAction(e -> stage.setScene(createLoginScene()));
        registerBtn.setOnAction(e -> stage.setScene(createRegisterScene()));

        return authScene;
    }

    // ==================== LOGIN SCENE ====================
    private Scene createLoginScene() {
        Label title = new Label("🔐 Login");
        title.setFont(Font.font(30));
        title.setStyle(ThemeStyles.TEXT_WHITE);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setFont(Font.font(18));
        usernameField.setPrefWidth(320);
        usernameField.setMaxWidth(320);
        usernameField.setStyle(ThemeStyles.TEXTFIELD_STYLE);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setFont(Font.font(18));
        passwordField.setPrefWidth(320);
        passwordField.setMaxWidth(320);
        passwordField.setStyle(ThemeStyles.TEXTFIELD_STYLE);

        Button loginBtn = new Button("Login");
        loginBtn.setFont(Font.font(20));
        loginBtn.setPrefWidth(200);
        loginBtn.setPadding(new Insets(12));
        loginBtn.setStyle(ThemeStyles.BUTTON_GOLD);

        Button backBtn = new Button("← Back");
        backBtn.setFont(Font.font(16));
        backBtn.setStyle(ThemeStyles.BUTTON_GOLD);

        Label messageLabel = new Label("");
        messageLabel.setStyle(ThemeStyles.TEXT_ERROR + " -fx-font-size: 14px;");

        VBox loginRoot = new VBox(18, title, usernameField, passwordField, loginBtn, backBtn, messageLabel);
        loginRoot.setAlignment(Pos.CENTER);
        loginRoot.setStyle(ThemeStyles.BACKGROUND);

        Scene loginScene = new Scene(loginRoot, SCENE_WIDTH, SCENE_HEIGHT);

        loginBtn.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("⚠ Please fill in all fields");
                messageLabel.setStyle(ThemeStyles.TEXT_ERROR + " -fx-font-size: 14px;");
                return;
            }

            User user = db.getUser(username, password);
            if (user != null) {
                loggedInUser = username;
                stage.setScene(createMainScene());
            } else {
                messageLabel.setText("⚠ Invalid username or password");
                messageLabel.setStyle(ThemeStyles.TEXT_ERROR + " -fx-font-size: 14px;");
            }
        });

        backBtn.setOnAction(e -> stage.setScene(createAuthScene()));

        return loginScene;
    }

    // ==================== REGISTER SCENE ====================
    private Scene createRegisterScene() {
        Label title = new Label("📝 Register");
        title.setFont(Font.font(30));
        title.setStyle(ThemeStyles.TEXT_WHITE);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setFont(Font.font(18));
        usernameField.setPrefWidth(320);
        usernameField.setMaxWidth(320);
        usernameField.setStyle(ThemeStyles.TEXTFIELD_STYLE);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setFont(Font.font(18));
        passwordField.setPrefWidth(320);
        passwordField.setMaxWidth(320);
        passwordField.setStyle(ThemeStyles.TEXTFIELD_STYLE);

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        confirmPasswordField.setFont(Font.font(18));
        confirmPasswordField.setPrefWidth(320);
        confirmPasswordField.setMaxWidth(320);
        confirmPasswordField.setStyle(ThemeStyles.TEXTFIELD_STYLE);

        Button registerBtn = new Button("Register");
        registerBtn.setFont(Font.font(20));
        registerBtn.setPrefWidth(200);
        registerBtn.setPadding(new Insets(12));
        registerBtn.setStyle(ThemeStyles.BUTTON_GOLD);

        Button backBtn = new Button("← Back");
        backBtn.setFont(Font.font(16));
        backBtn.setStyle(ThemeStyles.BUTTON_GOLD);

        Label messageLabel = new Label("");
        messageLabel.setStyle(ThemeStyles.TEXT_ERROR + " -fx-font-size: 14px;");

        VBox registerRoot = new VBox(14, title, usernameField, passwordField,
                confirmPasswordField, registerBtn, backBtn, messageLabel);
        registerRoot.setAlignment(Pos.CENTER);
        registerRoot.setStyle(ThemeStyles.BACKGROUND);

        Scene registerScene = new Scene(registerRoot, SCENE_WIDTH, SCENE_HEIGHT);

        registerBtn.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String confirmPassword = confirmPasswordField.getText().trim();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                messageLabel.setText("⚠ Please fill in all fields");
                messageLabel.setStyle(ThemeStyles.TEXT_ERROR + " -fx-font-size: 14px;");
                return;
            }

            if (!password.equals(confirmPassword)) {
                messageLabel.setText("⚠ Passwords do not match");
                messageLabel.setStyle(ThemeStyles.TEXT_ERROR + " -fx-font-size: 14px;");
                return;
            }

            if (db.usernameExists(username)) {
                messageLabel.setText("⚠ Username already exists");
                messageLabel.setStyle(ThemeStyles.TEXT_ERROR + " -fx-font-size: 14px;");
                return;
            }

            db.insertUser(username, password);
            messageLabel.setText("✅ Registration successful! Redirecting to login...");
            messageLabel.setStyle(ThemeStyles.TEXT_SUCCESS + " -fx-font-size: 14px;");

            // Redirect to login after delay
            new Thread(() -> {
                try {
                    Thread.sleep(1500);
                    javafx.application.Platform.runLater(() -> stage.setScene(createLoginScene()));
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();
        });

        backBtn.setOnAction(e -> stage.setScene(createAuthScene()));

        return registerScene;
    }

    // ==================== MAIN WEATHER SCENE ====================
    private Scene createMainScene() {
        // --- TOP: Welcome + Logout ---
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15, 20, 0, 20));
        topBar.setAlignment(Pos.CENTER);

        Label userLabel = new Label("Welcome, " + loggedInUser + " ☁");
        userLabel.setFont(Font.font(18));
        userLabel.setStyle(ThemeStyles.TEXT_WHITE);

        HBox.setHgrow(userLabel, Priority.ALWAYS);
        userLabel.setAlignment(Pos.CENTER_LEFT);

        Button logoutBtn = new Button("Logout");
        logoutBtn.setFont(Font.font(14));
        logoutBtn.setPadding(new Insets(8, 15, 8, 15));
        logoutBtn.setStyle(ThemeStyles.BUTTON_LOGOUT);

        topBar.getChildren().addAll(userLabel, logoutBtn);

        // --- SEARCH BAR ---
        TextField cityField = new TextField();
        cityField.setPromptText("Enter city name...");
        cityField.setFont(Font.font(18));
        cityField.setPrefWidth(250);
        cityField.setStyle(ThemeStyles.TEXTFIELD_STYLE);

        Button fetchButton = new Button("🔍 Get Weather");
        fetchButton.setFont(Font.font(16));
        fetchButton.setPadding(new Insets(10, 15, 10, 15));
        fetchButton.setStyle(ThemeStyles.BUTTON_GOLD);

        HBox searchBox = new HBox(10, cityField, fetchButton);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setPadding(new Insets(10));

        // --- BUTTONS BELOW SEARCH BAR ---
        Button historyButton = new Button("📋 Search History");
        historyButton.setFont(Font.font(15));
        historyButton.setPadding(new Insets(10, 20, 10, 20));
        historyButton.setStyle(ThemeStyles.BUTTON_GOLD);

        Button usersButton = new Button("👤 Registered Accounts");
        usersButton.setFont(Font.font(15));
        usersButton.setPadding(new Insets(10, 20, 10, 20));
        usersButton.setStyle(ThemeStyles.BUTTON_GOLD);

        HBox buttonsBox = new HBox(15, historyButton, usersButton);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(5));

        // --- WEATHER DISPLAY ---
        ImageView weatherIcon = new ImageView();
        weatherIcon.setFitWidth(150);
        weatherIcon.setFitHeight(150);

        Label tempLabel = new Label("--°C");
        tempLabel.setFont(Font.font("Arial", 64));
        tempLabel.setStyle(ThemeStyles.TEXT_WHITE);

        Label conditionLabel = new Label("Condition");
        conditionLabel.setFont(Font.font(26));
        conditionLabel.setStyle(ThemeStyles.TEXT_GOLD);

        VBox weatherDisplay = new VBox(15, weatherIcon, tempLabel, conditionLabel);
        weatherDisplay.setAlignment(Pos.CENTER);

        // --- BOTTOM: Humidity & Wind ---
        Label humidityLabel = new Label("Humidity: --%");
        Label windLabel = new Label("Wind: -- km/h");
        humidityLabel.setFont(Font.font(18));
        windLabel.setFont(Font.font(18));
        humidityLabel.setStyle(ThemeStyles.TEXT_WHITE);
        windLabel.setStyle(ThemeStyles.TEXT_WHITE);

        HBox bottomBox = new HBox(40, humidityLabel, windLabel);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(15));

        // --- MAIN LAYOUT ---
        VBox mainContent = new VBox(10, searchBox, buttonsBox, weatherDisplay, bottomBox);
        mainContent.setAlignment(Pos.CENTER);
        mainContent.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(mainContent);
        root.setStyle(ThemeStyles.BACKGROUND);

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        // ===== FETCH WEATHER ACTION =====
        fetchButton.setOnAction(e -> {
            try {
                String city = cityField.getText().trim();
                if (city.isEmpty()) {
                    conditionLabel.setText("Please enter a city");
                    conditionLabel.setStyle(ThemeStyles.TEXT_ERROR + " -fx-font-size: 26px;");
                    return;
                }

                WeatherService service = new WeatherService();
                String data = service.getWeather(city);

                if (data.startsWith("Error")) {
                    conditionLabel.setText("City not found");
                    conditionLabel.setStyle(ThemeStyles.TEXT_ERROR + " -fx-font-size: 26px;");
                    return;
                }

                String[] lines = data.split("\n");
                String temp = lines[0].replace("Temperature: ", "");
                String humidity = lines[1];
                String windRaw = lines[2].replace("Wind Speed: ", "").replace(" m/s", "");
                String condition = lines[3].replace("Condition: ", "");

                double windKmh = Double.parseDouble(windRaw) * 3.6;

                tempLabel.setText(temp);
                tempLabel.setStyle(ThemeStyles.TEXT_WHITE);
                humidityLabel.setText(humidity);
                windLabel.setText("Wind: " + String.format("%.1f", windKmh) + " km/h");
                conditionLabel.setText(condition);
                conditionLabel.setStyle(ThemeStyles.TEXT_GOLD + " -fx-font-size: 26px;");

                // Save to database
                db.insertWeather(city, temp, humidity,
                        String.format("%.1f km/h", windKmh), condition);

            } catch (Exception ex) {
                conditionLabel.setText("Error fetching data");
                conditionLabel.setStyle(ThemeStyles.TEXT_ERROR + " -fx-font-size: 26px;");
            }
        });

        // ===== HISTORY BUTTON ACTION =====
        historyButton.setOnAction(e -> HistoryWindow.show());

        // ===== USERS BUTTON ACTION =====
        usersButton.setOnAction(e -> UsersWindow.show());

        // ===== LOGOUT ACTION =====
        logoutBtn.setOnAction(e -> {
            loggedInUser = null;
            stage.setScene(createWelcomeScene());
        });

        return scene;
    }

    public static void main(String[] args) {
        launch();
    }
}