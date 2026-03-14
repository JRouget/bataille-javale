package school.coda.joshua_hugo.bataillejavale.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import school.coda.joshua_hugo.bataillejavale.BatailleJavaleApp;
import school.coda.joshua_hugo.bataillejavale.map.Grille;
import school.coda.joshua_hugo.bataillejavale.moteur.GameManager;
import school.coda.joshua_hugo.bataillejavale.navires.Navire;

import java.io.IOException;

public class PlacementController {

    @FXML
    private GridPane grilleOcean;

    @FXML
    private Button boutonRotation;

    private GameManager gameManager = new GameManager();

    private Button[][] boutonsGrille = new Button[10][10];

    private boolean estHorizontal = true;

    @FXML
    public void initialize() {
        construireGrilleVisuelle();
    }

    @FXML
    protected void onBoutonRotationClick() {
        estHorizontal = !estHorizontal;

        if (estHorizontal) {
            boutonRotation.setText("Sens : Horizontal ➡️");
        } else {
            boutonRotation.setText("Sens : Vertical ⬇️");
        }
    }

    private void construireGrilleVisuelle() {
        int tailleGrille = 10;

        for (int ligne = 0; ligne < tailleGrille; ligne++) {
            for (int colonne = 0; colonne < tailleGrille; colonne++) {

                Button caseVisuelle = new Button();
                caseVisuelle.setPrefSize(40, 40);
                caseVisuelle.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #87CEEB;");

                boutonsGrille[colonne][ligne] = caseVisuelle;

                final int x = colonne;
                final int y = ligne;

                caseVisuelle.setOnAction(event -> {
                    tenterPlacementBateau(x, y);
                });

                grilleOcean.add(caseVisuelle, colonne, ligne);
            }
        }
    }

    private void tenterPlacementBateau(int x, int y) {
        Navire navireAposer = gameManager.getBateauActuel();

        if (navireAposer == null) {
            return;
        }

        Grille grilleDuJoueur = gameManager.getPlayerGrid();
        boolean autorisation = grilleDuJoueur.placerNavire(navireAposer, y, x, estHorizontal);

        if (autorisation) {
            int taille = navireAposer.getType().getTaille();
            for (int i = 0; i < taille; i++) {
                int caseX = estHorizontal ? x + i : x;
                int caseY = estHorizontal ? y : y + i;
                boutonsGrille[caseX][caseY].setStyle("-fx-background-color: #808080; -fx-border-color: #333333;");
            }

            gameManager.preparerProchainBateau();

            if (gameManager.getBateauActuel() == null) {
                lancerBataille();
            }
        }
    }

    private void lancerBataille() {
        gameManager.bateauOrdinateur();

        try {
            FXMLLoader loader = new FXMLLoader(BatailleJavaleApp.class.getResource("bataille.fxml"));
            Parent root = loader.load();

            BatailleController battleController = loader.getController();
            battleController.setGameManager(this.gameManager);

            Scene sceneBataille = new Scene(root, 1000, 600);
            Stage stage = (Stage) grilleOcean.getScene().getWindow();
            stage.setScene(sceneBataille);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}