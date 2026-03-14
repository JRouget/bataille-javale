package school.coda.joshua_hugo.bataillejavale.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import school.coda.joshua_hugo.bataillejavale.map.Case;
import school.coda.joshua_hugo.bataillejavale.moteur.GameManager;

public class BatailleController {

    @FXML
    private GridPane grilleJoueur;

    @FXML
    private GridPane grilleRadar;

    private GameManager gameManager;
    private Button[][] boutonsJoueur = new Button[10][10];
    private boolean partieTerminee = false;

    public void setGameManager(GameManager gm) {
        this.gameManager = gm;
        dessinerGrilleJoueur();
        dessinerGrilleRadar();
    }

    private void dessinerGrilleJoueur() {
        int tailleGrille = 10;
        for (int ligne = 0; ligne < tailleGrille; ligne++) {
            for (int colonne = 0; colonne < tailleGrille; colonne++) {

                Button caseJoueur = new Button();
                caseJoueur.setPrefSize(40, 40);
                caseJoueur.setDisable(true);

                caseJoueur.getStyleClass().add("case-ocean");

                Case casePlateau = gameManager.getPlayerGrid().getCase(ligne, colonne);

                if (!casePlateau.estVide()) {
                    caseJoueur.getStyleClass().add("case-navire");
                }

                boutonsJoueur[ligne][colonne] = caseJoueur;
                grilleJoueur.add(caseJoueur, colonne, ligne);
            }
        }
    }

    private void dessinerGrilleRadar() {
        int tailleGrille = 10;
        for (int ligne = 0; ligne < tailleGrille; ligne++) {
            for (int colonne = 0; colonne < tailleGrille; colonne++) {

                Button caseRadar = new Button();
                caseRadar.setPrefSize(40, 40);

                // Style radar
                caseRadar.getStyleClass().add("case-ocean");

                final int x = colonne;
                final int y = ligne;

                caseRadar.setOnAction(event -> {
                    if (!partieTerminee) {
                        tenterTir(x, y, caseRadar);
                    }
                });

                grilleRadar.add(caseRadar, colonne, ligne);
            }
        }
    }

    private void tenterTir(int x, int y, Button boutonClique) {
        boutonClique.setDisable(true);
        boolean estTouche = gameManager.getComputerGrid().recevoirTir(y, x);

        if (estTouche) {
            boutonClique.getStyleClass().add("case-touche");
            System.out.println("Vous avez touché un navire ennemi !");
        } else {
            boutonClique.getStyleClass().add("case-eau");
            System.out.println("Vous avez tiré dans l'eau !");
        }

        if (gameManager.getComputerGrid().sontTousCoules()) {
            partieTerminee = true;
            afficherEcranFin("VICTOIRE !", "#00FF41"); // Vert Néon
            return;
        }

        tourDeLOrdinateur();
    }

    private void tourDeLOrdinateur() {
        int ligneTir = (int) (Math.random() * 10);
        int colonneTir = (int) (Math.random() * 10);

        boolean botTouche = gameManager.getPlayerGrid().recevoirTir(ligneTir, colonneTir);
        Button boutonCible = boutonsJoueur[ligneTir][colonneTir];

        if (botTouche) {
            boutonCible.getStyleClass().add("bot-touche");
            System.out.println("L'ordinateur a touché votre navire !");
        } else {
            boutonCible.getStyleClass().add("case-eau");
            System.out.println("L'ordinateur a tiré dans l'eau !");
        }

        if (gameManager.getPlayerGrid().sontTousCoules()) {
            partieTerminee = true;
            afficherEcranFin("DÉFAITE...", "#E63946"); // Rouge Alerte
        }
    }

    private void afficherEcranFin(String messageFin, String couleur) {
        HBox conteneurPrincipal = (HBox) grilleJoueur.getParent();
        conteneurPrincipal.getChildren().clear();

        Label labelFin = new Label(messageFin);
        labelFin.getStyleClass().add("label-fin");
        labelFin.setStyle("-fx-text-fill: " + couleur + ";");

        conteneurPrincipal.getChildren().add(labelFin);
    }
}