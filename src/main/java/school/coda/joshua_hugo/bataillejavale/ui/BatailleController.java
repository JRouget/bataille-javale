package school.coda.joshua_hugo.bataillejavale.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import school.coda.joshua_hugo.bataillejavale.BatailleJavaleApp;
import school.coda.joshua_hugo.bataillejavale.map.Case;
import school.coda.joshua_hugo.bataillejavale.moteur.GameManager;

import java.io.IOException;

public class BatailleController {

    @FXML
    private GridPane grilleJoueur;

    @FXML
    private GridPane grilleRadar;

    @FXML
    private Label labelHistorique;

    private GameManager gameManager;
    private Button[][] boutonsJoueur = new Button[10][10];
    private boolean partieTerminee = false;

    public void setGameManager(GameManager gm) {
        this.gameManager = gm;
        // On force le texte en blanc pour qu'il soit lisible sur le thème sombre de ton binôme !
        labelHistorique.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        dessinerGrilleJoueur();
        dessinerGrilleRadar();
    }

    private void dessinerGrilleJoueur() {
        int tailleGrille = 10;
        for (int ligne = 0; ligne < tailleGrille; ligne++) {
            for (int colonne = 0; colonne < tailleGrille; colonne++) {

                Button caseJoueur = new Button();
                caseJoueur.setMinSize(40, 40);
                caseJoueur.setMaxSize(40, 40);
                caseJoueur.setDisable(true);

                Case casePlateau = gameManager.getPlayerGrid().getCase(ligne, colonne);

                if (!casePlateau.estVide()) {
                    caseJoueur.setStyle("-fx-background-color: #808080; -fx-border-color: #333333; -fx-opacity: 1;");
                } else {
                    caseJoueur.setStyle("-fx-background-color: #ADD8E6; -fx-border-color: #87CEEB; -fx-opacity: 1;");
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
                caseRadar.setMinSize(40, 40);
                caseRadar.setMaxSize(40, 40);
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
        String historiqueJoueur;

        if (estTouche) {
            boutonClique.setStyle("-fx-background-color: #FF0000; -fx-border-color: #8B0000; -fx-opacity: 1;");
            historiqueJoueur = "VOUS : Touché !";
        } else {
            boutonClique.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CCCCCC; -fx-opacity: 1;");
            historiqueJoueur = "VOUS : À l'eau.";
        }

        if (gameManager.getComputerGrid().sontTousCoules()) {
            partieTerminee = true;
            labelHistorique.setText(historiqueJoueur);
            afficherEcranFin("VICTOIRE !", "#4CAF50");
            return;
        }

        tourDeLOrdinateur(historiqueJoueur);
    }

    private void tourDeLOrdinateur(String historiqueJoueur) {
        int ligneTir;
        int colonneTir;

        do {
            ligneTir = (int) (Math.random() * 10);
            colonneTir = (int) (Math.random() * 10);
        } while (gameManager.getPlayerGrid().estDejaTouchee(ligneTir, colonneTir));

        boolean botTouche = gameManager.getPlayerGrid().recevoirTir(ligneTir, colonneTir);
        Button boutonCible = boutonsJoueur[ligneTir][colonneTir];
        String historiqueOrdi;

        if (botTouche) {
            boutonCible.setStyle("-fx-background-color: #FF8C00; -fx-border-color: #8B0000; -fx-opacity: 1;");
            historiqueOrdi = "ORDI : Votre navire est touché !";
        } else {
            boutonCible.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CCCCCC; -fx-opacity: 1;");
            historiqueOrdi = "ORDI : À l'eau.";
        }

        labelHistorique.setText(historiqueJoueur + "   |   " + historiqueOrdi);

        if (gameManager.getPlayerGrid().sontTousCoules()) {
            partieTerminee = true;
            afficherEcranFin("DÉFAITE...", "#FF5252");
        }
    }

    private void afficherEcranFin(String messageFin, String couleur) {
        VBox conteneurPrincipal = (VBox) labelHistorique.getParent();
        conteneurPrincipal.getChildren().clear();

        VBox conteneurCentral = new VBox(40);
        conteneurCentral.setAlignment(Pos.CENTER);

        Label labelFin = new Label(messageFin);
        labelFin.setStyle("-fx-font-size: 80px; -fx-font-weight: bold; -fx-text-fill: " + couleur + ";");

        Button boutonRejouer = new Button("Rejouer");
        boutonRejouer.setStyle("-fx-font-size: 24px; -fx-padding: 10 30 10 30; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");

        boutonRejouer.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(BatailleJavaleApp.class.getResource("accueil.fxml"));
                Scene sceneAccueil = new Scene(loader.load(), 800, 600);
                Stage stage = (Stage) conteneurPrincipal.getScene().getWindow();
                stage.setScene(sceneAccueil);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        conteneurCentral.getChildren().addAll(labelFin, boutonRejouer);
        conteneurPrincipal.getChildren().add(conteneurCentral);
    }
}