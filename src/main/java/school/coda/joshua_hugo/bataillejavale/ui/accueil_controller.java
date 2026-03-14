package school.coda.joshua_hugo.bataillejavale.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import school.coda.joshua_hugo.bataillejavale.BatailleJavaleApp;

import java.io.IOException;

public class accueil_controller {

    @FXML
    protected void onNouvellePartieClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BatailleJavaleApp.class.getResource("grid.fxml"));
        Scene sceneDePlacement = new Scene(fxmlLoader.load(), 800, 600);

        Stage fenetre = (Stage) ((Node) event.getSource()).getScene().getWindow();
        fenetre.setScene(sceneDePlacement);
        fenetre.show();
    }
}
