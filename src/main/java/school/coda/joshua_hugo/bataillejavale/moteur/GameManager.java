package school.coda.joshua_hugo.bataillejavale.moteur;

import school.coda.joshua_hugo.bataillejavale.map.Grille;
import school.coda.joshua_hugo.bataillejavale.navires.Navire;
import school.coda.joshua_hugo.bataillejavale.navires.TypeNavire;

public class GameManager {

    private Grille playerGrid;
    private Grille computerGrid;
    private Navire currentShip;

    public GameManager() {
        this.playerGrid = new Grille();
        this.computerGrid = new Grille();

        this.currentShip = new Navire(TypeNavire.PORTE_AVION);
    }
}