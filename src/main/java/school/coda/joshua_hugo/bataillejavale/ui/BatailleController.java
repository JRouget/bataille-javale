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

                Case casePlateau = gameManager.getPlayerGrid().getCase(ligne, colonne);

                if (!casePlateau.estVide()) {
                    caseJoueur.setStyle("-fx-background-color: #808080; -fx-border-color: #333333;");
                } else {
                    caseJoueur.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #87CEEB;");
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
                caseRadar.setStyle("-fx-background-color: #000080; -fx-border-color: #4169E1;");

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
            boutonClique.setStyle("-fx-background-color: #FF0000; -fx-border-color: #8B0000;");
            System.out.println("Vous avez touché un navire ennemi !");
        } else {
            boutonClique.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CCCCCC;");
            System.out.println("Vous avez tiré dans l'eau !");
        }

        if (gameManager.getComputerGrid().sontTousCoules()) {
            partieTerminee = true;
            afficherEcranFin("VICTOIRE !", "#228B22");
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
            boutonCible.setStyle("-fx-background-color: #FF8C00; -fx-border-color: #8B0000;");
            System.out.println("L'ordinateur a touché votre navire !");
        } else {
            boutonCible.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CCCCCC;");
            System.out.println("L'ordinateur a tiré dans l'eau !");
        }

        if (gameManager.getPlayerGrid().sontTousCoules()) {
            partieTerminee = true;
            afficherEcranFin("DÉFAITE...", "#8B0000");
        }
    }

    private void afficherEcranFin(String messageFin, String couleur) {
        HBox conteneurPrincipal = (HBox) grilleJoueur.getParent();
        conteneurPrincipal.getChildren().clear();

        Label labelFin = new Label(messageFin);
        labelFin.setStyle("-fx-font-size: 80px; -fx-font-weight: bold; -fx-text-fill: " + couleur + ";");

        conteneurPrincipal.getChildren().add(labelFin);
    }
}