import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

public class UsersWindow {
    public static void show() {
        Stage stage = new Stage();
        stage.setTitle("Registered Accounts");
        stage.setResizable(false);

        Database db = new Database();
        List<User> list = db.getUsersList();

        Label title = new Label("👤 Registered Accounts");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; " + ThemeStyles.TEXT_WHITE);

        ListView<String> listView = new ListView<>();
        listView.setStyle(ThemeStyles.LISTVIEW_STYLE);
        listView.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle(ThemeStyles.LIST_CELL_STYLE);
                    } else {
                        setText(item);
                        setStyle(ThemeStyles.LIST_CELL_STYLE + " -fx-font-size: 14px; -fx-padding: 8;");
                    }
                }
            };
            return cell;
        });

        if (list.isEmpty()) {
            listView.getItems().add("No registered accounts yet...");
        } else {
            for (User u : list) {
                listView.getItems().add(
                        "🆔 ID: " + u.getId()
                                + "  |  👤 Username: " + u.getUsername()
                );
            }
        }

        VBox root = new VBox(15, title, listView);
        root.setStyle(ThemeStyles.BACKGROUND + "-fx-padding: 20;");
        root.setPrefSize(600, 700);

        Scene scene = new Scene(root, 600, 700);
        stage.setScene(scene);
        stage.show();
    }
}