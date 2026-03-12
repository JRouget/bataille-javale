package school.coda.joshua_hugo.bataillejavale.navires;

public class Navire {
    private TypeNavire type; // Le lien vers le catalogue
    private int pointsDeVie;

    public Navire(TypeNavire type) {
        this.type = type;
        this.pointsDeVie = type.getTaille();
    }

    public TypeNavire getType() {
        return type;
    }
}