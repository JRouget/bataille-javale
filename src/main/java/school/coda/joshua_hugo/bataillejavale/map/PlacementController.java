package school.coda.joshua_hugo.bataillejavale.map; // ou .ui

import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

// Une seule classe principale !
public class PlacementController {

    @FXML
    private GridPane grilleOcean;

    @FXML
    public void initialize() {
        construireGrilleVisuelle();
    }

    private void construireGrilleVisuelle() {
        int tailleGrille = 10;
        for (int ligne = 0; ligne < tailleGrille; ligne++) {
            for (int colonne = 0; colonne < tailleGrille; colonne++) {
                Button caseVisuelle = new Button();
                caseVisuelle.setPrefSize(40, 40);
                caseVisuelle.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #87CEEB;");

                final int x = colonne;
                final int y = ligne;
                caseVisuelle.setOnAction(event -> {
                    System.out.println("Clic sur la case : Colonne " + x + ", Ligne " + y);
                });
                grilleOcean.add(caseVisuelle, colonne, ligne);
            }
        }
    }
}
