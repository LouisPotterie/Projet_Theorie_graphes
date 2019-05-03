package com.company;

import java.io.FileNotFoundException;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws FileNotFoundException
    {

        //initialisation d'un graph sous la forme d'un tableau de transition

        Graphe graphe = new Graphe();
        int nombreSommets= graphe.getNombreSommets();
        int tableau_transitions[][]= new int[graphe.getNombreTransitions()][3];
       int transition=0;
            for (Noeud noeud : graphe.getNoeuds())
            {
                for (Map.Entry<Noeud, Integer> entry : noeud.getSuccesseurs().entrySet())
                {
                    tableau_transitions[transition]= new int[]{noeud.getSommet(), entry.getKey().getSommet(), entry.getValue()};
                    transition++;
                }
            }
      

        // prec - succ - valeur de transition
        //int tableau_transitions[][] = { {0,1,5},{1,2,-10}, {2,0,3} };

        //le nombre  de sommet est à récuperer en première ligne du txt

     
        //le nombre de transition du graph
        int nombreTransitions;
        nombreTransitions = tableau_transitions.length;

        /*
            Tableau des k

            Taille :
            (en ligne) de k = 0 à k = nombreSommets - 1
            (en colonne) tous les sommets, donc nombreSommets
        */
        int tableau_de_k[][] = new int[nombreSommets][nombreSommets];

        /*
            Tableau des predecesseurs

            meme dimensions que le tableau des k
            il permet de recuperer les prédecesseurs permettant de dresser le chemin le plus cours
         */
        int tableau_de_predecesseur[][] = new int[nombreSommets][nombreSommets];

        // Le MAX_SIZE permet de remplacer le signe infini dans le tableau des k
         int MAX_SIZE = 999;

        // On initialise le tableau des k avec la variable MAX_SIZE (inifini)
        for (int i = 0; i < nombreSommets; i++){
            for (int n =0; n < nombreSommets; n++){
                tableau_de_k[i][n] = MAX_SIZE;
            }
        }

        //l'utilisateur choisit le sommet de départ
        int sommet_depart;
        sommet_depart = 0;

        //initialisation de la colonne du sommet de départ
        for(int j = 0; j < nombreSommets; j++)
        {
            tableau_de_k[j][sommet_depart]=0;
            tableau_de_predecesseur[j][sommet_depart]=sommet_depart;
        }

        //Savoir si le graph est absorbant ou pas ?
        int absorbant_ou_pas;
        int plus_petite_transition = 0;

        for (int recherche_absorbant_ou_pas = 0; recherche_absorbant_ou_pas < nombreTransitions; recherche_absorbant_ou_pas++){
            plus_petite_transition = Math.min(plus_petite_transition,tableau_transitions[recherche_absorbant_ou_pas][2]);
        }
        if (plus_petite_transition < 0)
            absorbant_ou_pas = 0;
        else
            absorbant_ou_pas = 1;

        /*
            initialisation des différentes variables
         */
        int prec = 0;
        int succ = 0;
        int prec_v2 = 0;
        int succ_v2 = 0;
        int value = 0;
        int v = 0;
        int suivant = 1;
        int compteur = 0;

        //Si le graph a un circuit absorbant
        if (absorbant_ou_pas == 0){
            for (int k = 1; k < nombreSommets; k++) {
                for (int o = 0; o < nombreTransitions; o++){

                    prec = tableau_transitions[o][0]; //on récup le prec grace au graph
                    succ = tableau_transitions[o][1];
                    value = tableau_transitions[o][2];

                    prec_v2 = tableau_de_k[k-1][prec]; //on recup le poids pour le prec dans le tableau k
                    succ_v2 = tableau_de_k[k-1][succ];

                    v = Math.min(succ_v2, (prec_v2 + value)); //on cherche le min

                    if ( v < tableau_de_k[k][succ]){
                        tableau_de_k[k][succ] = v; // on modifie la valeur dans le tableau k pour avoir le plus petit chemin
                        if (v != tableau_de_k[k-1][succ]) {
                            tableau_de_predecesseur[k][succ] = prec;
                        }
                    }
                }
            }
        }
        else { //si le graph n'a pas de circuit absorbant
            int k = 1;
            while (suivant == 1) {
                for (int o = 0; o < nombreTransitions; o++) {

                    prec = tableau_transitions[o][0]; //on récup le prec grace au graph
                    succ = tableau_transitions[o][1];
                    value = tableau_transitions[o][2];

                    prec_v2 = tableau_de_k[k - 1][prec]; //on recup le poids pour le prec dans le tableau k
                    succ_v2 = tableau_de_k[k - 1][succ];

                    v = Math.min(succ_v2, (prec_v2 + value)); //on cherche le min

                    if (v < tableau_de_k[k][succ]) {
                        tableau_de_k[k][succ] = v; // on modifie la valeur dans le tableau k pour avoir le plus petit chemin
                        if (v != tableau_de_k[k - 1][succ]) {
                            tableau_de_predecesseur[k][succ] = prec;
                        }
                    }
                }

                for (int m = 0; m < nombreSommets; m++){
                    if (tableau_de_k[k-1][m] == tableau_de_k[k][m]){
                        compteur ++;
                    }
                }
                if (compteur == nombreSommets)
                    suivant = 0;

                compteur = 0;
                k++;
            }
        }



        /*
        Affichage des deux tableaux combinés, tableau des k et prédécesseur
         */
        int a = 1;
        int b;

        while (a < nombreSommets)
        {
            b = 0;
            while(b < nombreSommets)
            {
                if (tableau_de_k[a][b] > 900) {
                    System.out.printf("%4s    |", "*");
                }
                else {
                    if (tableau_de_predecesseur[a][b] == 0){
                        tableau_de_predecesseur[a][b] = tableau_de_predecesseur[a-1][b];
                    }
                    System.out.printf("%3d (%d) |",tableau_de_k[a][b], tableau_de_predecesseur[a][b]);
                }
                b++;
            }
            System.out.println("");
            a++;
        }

    }
}
