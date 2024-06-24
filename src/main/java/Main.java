import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/signin-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Image image = new Image("img/icon.png");
        stage.getIcons().add(image);
        stage.setTitle("Clothify");

        stage.setScene(scene);
        stage.show();
    }
}