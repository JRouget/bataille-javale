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

    @FXML private GridPane grilleOcean;
    @FXML private Button boutonRotation;

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
        boutonRotation.setText("ORIENTATION : " + (estHorizontal ? "HORIZONTAL" : "VERTICAL"));
    }

    private void construireGrilleVisuelle() {
        grilleOcean.getChildren().clear();
        for (int ligne = 0; ligne < 10; ligne++) {
            for (int col = 0; col < 10; col++) {
                Button caseVisuelle = new Button();
                caseVisuelle.getStyleClass().add("case-ocean");

                boutonsGrille[col][ligne] = caseVisuelle;
                final int x = col;
                final int y = ligne;

                caseVisuelle.setOnAction(event -> tenterPlacementBateau(x, y));
                caseVisuelle.setOnMouseEntered(e -> montrerPreview(x, y, true));
                caseVisuelle.setOnMouseExited(e -> montrerPreview(x, y, false));

                grilleOcean.add(caseVisuelle, col, ligne);
            }
        }
    }

    private void montrerPreview(int x, int y, boolean active) {
        Navire navire = gameManager.getBateauActuel();
        if (navire == null) return;

        int taille = navire.getType().getTaille();
        for (int i = 0; i < taille; i++) {
            int targetX = estHorizontal ? x + i : x;
            int targetY = estHorizontal ? y : y + i;

            if (targetX >= 0 && targetX < 10 && targetY >= 0 && targetY < 10) {
                Button btn = boutonsGrille[targetX][targetY];
                if (active) {
                    btn.getStyleClass().add("case-preview");
                } else {
                    btn.getStyleClass().remove("case-preview");
                }
            }
        }
    }

    private void tenterPlacementBateau(int x, int y) {
        Navire navireAposer = gameManager.getBateauActuel();
        if (navireAposer == null) return;

        Grille grilleDuJoueur = gameManager.getPlayerGrid();
        boolean autorisation = grilleDuJoueur.placerNavire(navireAposer, y, x, estHorizontal);

        if (autorisation) {
            int taille = navireAposer.getType().getTaille();
            for (int i = 0; i < taille; i++) {
                int caseX = estHorizontal ? x + i : x;
                int caseY = estHorizontal ? y : y + i;

                Button btn = boutonsGrille[caseX][caseY];
                btn.getStyleClass().remove("case-preview");
                btn.getStyleClass().add("case-navire");

                if (i == 0) btn.getStyleClass().add(estHorizontal ? "proue-h" : "proue-v");
                else if (i == taille - 1) btn.getStyleClass().add(estHorizontal ? "poupe-h" : "poupe-v");
                else btn.getStyleClass().add("corps");
            }

            gameManager.preparerProchainBateau();
            if (gameManager.getBateauActuel() == null) lancerBataille();
        }
    }

    private void lancerBataille() {
        gameManager.bateauOrdinateur();
        try {
            FXMLLoader loader = new FXMLLoader(BatailleJavaleApp.class.getResource("bataille.fxml"));
            Parent root = loader.load();
            BatailleController battleController = loader.getController();
            battleController.setGameManager(this.gameManager);
            Scene sceneBataille = new Scene(root, 1000, 1000);
            Stage stage = (Stage) grilleOcean.getScene().getWindow();
            stage.setScene(sceneBataille);
            stage.setMaximized(true);
        } catch (IOException e) { e.printStackTrace(); }
    }
}