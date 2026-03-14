package school.coda.joshua_hugo.bataillejavale;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BatailleJavaleApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BatailleJavaleApp.class.getResource("accueil.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Bataille Javale");
        stage.setScene(scene);
        stage.show();
    }
}
