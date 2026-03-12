package school.coda.joshua_hugo.bataillejavale.moteur;

import java.util.ArrayList;
import java.util.List;

import school.coda.joshua_hugo.bataillejavale.map.Grille;
import school.coda.joshua_hugo.bataillejavale.navires.Navire;
import school.coda.joshua_hugo.bataillejavale.navires.TypeNavire;

public class GameManager {

    private Grille playerGrid;
    private Grille computerGrid;
    private List<TypeNavire> attentePlacement;
    private Navire bateauActuel;


    public GameManager() {
        this.playerGrid = new Grille();
        this.computerGrid = new Grille();

        // crée la liste
        this.attentePlacement = new ArrayList<>();

        // 5 types de bateau
        this.attentePlacement.add(TypeNavire.PORTE_AVION);
        this.attentePlacement.add(TypeNavire.CUIRASSE);
        this.attentePlacement.add(TypeNavire.DESTROYER);
        this.attentePlacement.add(TypeNavire.SOUS_MARIN);
        this.attentePlacement.add(TypeNavire.PATROUILLEUR);

        // Premier bateau
        preparerProchainBateau();
    }


    public void preparerProchainBateau() {

        //On vérifie si la liste est vide
        if(!attentePlacement.isEmpty()){

            TypeNavire prochainType = attentePlacement.get(0); // On le met à l'index 0
            this.bateauActuel = new Navire(prochainType); // On le cré
            attentePlacement.remove(0); //On enleve de la liste

            System.out.println("Prochain bateau à placer : " + prochainType.getNom());

        } else{
            this.bateauActuel = null;
            System.out.println("Il n'y a plus de bateau à placer.");
        }

    }

    public void bateauOrdinateur(){

        TypeNavire[] tousLesTypes = TypeNavire.values();//Liste de tout les bateau et type

        for (TypeNavire typeChoisi : tousLesTypes){

            boolean pose = false;

            while(pose == false){

                //On choisis les coordonnées
                int ligne = (int) (Math.random() * 10);
                int colonne = (int) (Math.random() * 10);

                //On choisis son orientation
                int chance = (int) (Math.random() * 100);

                boolean horizontal;

                if(chance > 50){
                    horizontal = true;
                } else{
                    horizontal = false;
                }

                //On crée le bateau
                Navire nouveauBateau = new Navire(typeChoisi);

                //On le met sur la grille
                boolean resultat = computerGrid.placerNavire(nouveauBateau, ligne, colonne, horizontal);

                if(resultat == true){
                    pose = true;
                    System.out.println("J'ai poser le " + typeChoisi.getNom() + ". " +
                            "\n Aux coordonnées " + "[ ligne : " + ligne  + " / colonne :" +colonne  + "]");
                }

            }

        }

    }

    public void tirOrdinateur() {

        boolean tirValide = false;
        int ligne = 0;
        int colonne = 0;

        // On boucle tant que le tir est pas valide
        while (tirValide == false) {

            // coordonnées de tir
            ligne = (int) (Math.random() * 10);
            colonne = (int) (Math.random() * 10);

            // Si c'est déjà touché
            if (playerGrid.estDejaTouchee(ligne, colonne) == false) {

                // Le tir à fonctionné
                tirValide = true;

            } else {
                //Echec, la boucle reboucle
                System.out.println("Déjà tiré en " + ligne + "," + colonne + ". Je re-tente...");
            }
        }

        System.out.println("L'ordinateur tire enfin en : " + ligne + " " + colonne);
    }

    public Grille getPlayerGrid() {
        return this.playerGrid;
    }
    public Grille getComputerGrid() {
        return this.computerGrid;
    }
    public Navire getBateauActuel() {
        return this.bateauActuel;
    }

}