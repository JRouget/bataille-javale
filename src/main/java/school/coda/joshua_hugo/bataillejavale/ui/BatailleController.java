package school.coda.joshua_hugo.bataillejavale.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import school.coda.joshua_hugo.bataillejavale.moteur.GameManager;

public class BatailleController {

    @FXML
    private GridPane grilleJoueur;

    @FXML
    private GridPane grilleRadar;

    private GameManager gameManager;

    public void setGameManager(GameManager gm) {
        this.gameManager = gm;
    }

    @FXML
    public void initialize() {

    }
}