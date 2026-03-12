package school.coda.joshua_hugo.bataillejavale.map;

import school.coda.joshua_hugo.bataillejavale.navires.Navire;

public class Case {

    private Navire navireDessus;
    private boolean aEteTouchee;

    public Case() {
        this.navireDessus = null;
        this.aEteTouchee = false;
    }

    public boolean estVide() {
        return this.navireDessus == null;
    }

    public void placerNavire(Navire n) {
        this.navireDessus = n;
    }
}