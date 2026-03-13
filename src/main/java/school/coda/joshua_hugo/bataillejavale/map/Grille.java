package school.coda.joshua_hugo.bataillejavale.map;

import school.coda.joshua_hugo.bataillejavale.navires.Navire;

public class Grille {

    private static final int TAILLE = 10;
    private Case[][] plateau;

    public Grille() {
        plateau = new Case[TAILLE][TAILLE];

        for (int ligne = 0; ligne < TAILLE; ligne++) {
            for (int colonne = 0; colonne < TAILLE; colonne++) {
                plateau[ligne][colonne] = new Case();
            }
        }

    }

    public boolean estDejaTouchee(int ligne, int colonne) {
        // trouve quelle case
        Case laCase = plateau[ligne][colonne];

        // Si elle a été touché
        return laCase.isAEteTouchee();
    }

    public boolean placerNavire(Navire navire, int ligneDepart, int colDepart, boolean estHorizontal) {

        int taille = navire.getType().getTaille();

        if (estHorizontal) {
            if (colDepart + taille > TAILLE) return false;
        } else {
            if (ligneDepart + taille > TAILLE) return false;
        }

        for (int i = 0; i < taille; i++) {
            int ligneAInspecter = estHorizontal ? ligneDepart : ligneDepart + i;
            int colAInspecter = estHorizontal ? colDepart + i : colDepart;

            if (!plateau[ligneAInspecter][colAInspecter].estVide()) {
                return false;
            }
        }

        for (int i = 0; i < taille; i++) {
            int ligneAPlacer = estHorizontal ? ligneDepart : ligneDepart + i;
            int colAPlacer = estHorizontal ? colDepart + i : colDepart;

            plateau[ligneAPlacer][colAPlacer].placerNavire(navire);
        }

        return true;
    }

    public boolean recevoirTir(int ligne, int colonne) {
        Case caseCible = plateau[ligne][colonne];

        caseCible.setAEteTouchee(true);

        if (!caseCible.estVide()) {
            return true;
        } else {
            return false;
        }
    }

    public Case getCase(int ligne, int colonne) {
        return plateau[ligne][colonne];
    }
}
