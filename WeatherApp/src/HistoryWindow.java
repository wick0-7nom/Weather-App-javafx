import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

public class HistoryWindow {
    public static void show() {
        Stage stage = new Stage();
        stage.setTitle("Search History");
        stage.setResizable(false);

        Database db = new Database();
        List<WeatherHistory> list = db.getHistoryList();

        Label title = new Label("☁ Search History");
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
                        setStyle(ThemeStyles.LIST_CELL_STYLE + " -fx-font-size: 13px; -fx-padding: 8;");
                    }
                }
            };
            return cell;
        });

        if (list.isEmpty()) {
            listView.getItems().add("No search history yet...");
        } else {
            for (WeatherHistory h : list) {
                listView.getItems().add(
                        "📍 " + h.getCity() + "  |  🌡 " + h.getTemperature()
                                + "  |  💧 " + h.getHumidity()
                                + "  |  💨 " + h.getWind()
                                + "  |  " + h.getCondition()
                                + "\n📅 " + h.getTime()
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