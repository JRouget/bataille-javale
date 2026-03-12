package school.coda.joshua_hugo.bataillejavale.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import school.coda.joshua_hugo.bataillejavale.map.Grille;
import school.coda.joshua_hugo.bataillejavale.moteur.GameManager;
import school.coda.joshua_hugo.bataillejavale.navires.Navire;

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
            System.out.println("Tous les bateaux sont déjà placés !");
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

        } else {
            System.out.println("Placement impossible ici !");
        }
    }
}