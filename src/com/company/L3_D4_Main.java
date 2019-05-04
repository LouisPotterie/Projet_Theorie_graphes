package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.*;

public class L3_D4_Main
{

    //Leo
    public static void main(String[] args) throws FileNotFoundException
    {


//        int[][] matrice = creationMatrice();

        //      enregistrementMatrice(matrice);

        //dijkstra();

        menu();

        //test();

    }

    //
    public static void dijkstra(Graphe graphe) throws FileNotFoundException
    {
        final String UNICODE_POINT = "\u2022";
        final String UNICODE_INFINITY= "\u221E";
        ArrayList<Integer> cc = new ArrayList<>();
        Map<Noeud, Noeud> predecesseurCheminCourt = new HashMap<>();
        //Graphe graphe = new Graphe();
        int nombreSommets = graphe.getNombreSommets();
        Integer[][] dijkstra = new Integer[nombreSommets][nombreSommets];
        try
        {
            graphe.affichageAdjascence();
            graphe.affichageValeurs();
            ArrayList<Noeud> m = cloneList(graphe.getNoeuds());


            Scanner kb = new Scanner(System.in);
            System.out.println("Quel est le sommet de départ ?"); // choix du sommet de départ

            int initiale = kb.nextInt();

            System.out.println("Le sommet choisi est :" + initiale);

            Noeud i = m.get(initiale);
            cc.add(initiale); // Ajout du Noeud dans CC
            m.remove(i); // On retire le noeud de CC
            System.out.println("IN");

            //tout initialiser à l'infini
            for (int ligne = 0; ligne < nombreSommets; ligne++)
            {
                for (int colonne = 0; colonne < nombreSommets; colonne++)
                {
                    dijkstra[ligne][colonne] = Integer.MAX_VALUE;

                }

            }


            for (Map.Entry<Noeud, Integer> entry : i.getSuccesseurs().entrySet())
            {
                Noeud cle = entry.getKey();
                cle.setDistance(entry.getValue());
                dijkstra[0][cle.getSommet()] = (entry.getValue());
                predecesseurCheminCourt.put(i, cle);
            }

            dijkstra[0][initiale] = 0;


            for (int etape = 1; etape < nombreSommets; etape++)
            {
               dijkstra[etape] = dijkstra[etape - 1].clone();
               /* for (int ligne = 0; ligne < nombreSommets; ligne++)
                {

                    System.out.println(Arrays.toString(dijkstra[ligne]));
                }

                System.out.println("--");*/

                Noeud sommetProche = distanceLaPlusCourte(m, dijkstra, etape - 1);
                if (sommetProche != null)
                {
                    m.remove(sommetProche);
                    cc.add(sommetProche.getSommet());
                } else
                {
                    continue;
                }

//                sommetProche.setDistance(sommetProche.getDistance());

                for (Noeud noeud : m)  // parcourt de l'ensemble M
                    if (sommetProche.estPredecesseur(noeud) && graphe.getMatriceAdjacence()[sommetProche.getSommet()][noeud.getSommet()] == 1)
                    {
                        int distance_sommetproche_vers_noeud = sommetProche.getSuccesseurs().get(noeud);
                        int nouvelleDistance;
                        //Vérifier qu'à la ligne d'avant, le pi[sommetProche] n'est pas à égal à l'infini, sinon overflow et nouvelleDistance deviendrait négatif
                        if (dijkstra[etape][sommetProche.getSommet()] != Integer.MAX_VALUE)
                        {
                            nouvelleDistance = dijkstra[etape][sommetProche.getSommet()] + distance_sommetproche_vers_noeud;
                        } else
                        {
                            nouvelleDistance = Integer.MAX_VALUE;
                        }
                        if (nouvelleDistance < dijkstra[etape][noeud.getSommet()])
                        {
                            dijkstra[etape][noeud.getSommet()] = nouvelleDistance;
                            predecesseurCheminCourt.put(noeud, sommetProche);

                        }
                    }


            }

            for (int ligne = 0; ligne < nombreSommets; ligne++)
            {
                for (int sommet = 0; sommet < nombreSommets; sommet++)
                {
                    if (cc.contains(sommet) && ligne >= cc.indexOf(sommet) && ligne != 0 && ligne != nombreSommets - 1)
                    {
                        System.out.print(UNICODE_POINT+", ");
                    }
                    else if (dijkstra[ligne][sommet]== Integer.MAX_VALUE)
                    {
                        System.out.print(UNICODE_INFINITY+", ");
                    }
                    else
                    {
                        System.out.print(dijkstra[ligne][sommet] + ", ");
                    }
                }
                System.out.println();
            }


            System.out.println("Choisir un sommet : ");
            int choix = kb.nextInt();

            Noeud sommet_choisi = new Noeud();

            for (Noeud j : graphe.getNoeuds())
            {
                if (j.getSommet() == choix)
                {
                    sommet_choisi = j.clone();
                }
            }

            System.out.println("Taille Map Predecesseur :" + predecesseurCheminCourt.size());

            //cheminLePlusCourt(initiale, sommet_choisi, cc, predecesseurCheminCourt,graphe);


        }
        catch (FileNotFoundException e)
        {
            System.out.println("Aucun fichier trouvé");
        }
    }

    public static ArrayList<Noeud> cloneList(ArrayList<Noeud> noeuds)
    {
        ArrayList<Noeud> clone = new ArrayList<>(noeuds.size());
        for (Noeud item : noeuds) clone.add(item.clone());
        return clone;
    }

    /*

    public static void test() throws FileNotFoundException
    {
        try
        {
            Graphe test = new Graphe();

            for (Noeud n : test.getNoeuds())
            {
                System.out.println("Nom noeud : " + n.getSommet() + " Sucesseurs " + n.getSuccesseurs().get(n) + "Predecesseur" + n.getSommetChemin() + "\n");
            }

        }
        catch (FileNotFoundException e)
        {
            System.out.println("Erreur");
        }
    }
    */

    public static void test2() throws FileNotFoundException
    {
        int nom_1 = 1;
        int nom_2 = 2;
        int nom_3 = 3;
        int nom_4 = 4;
        Noeud premier = new Noeud(nom_1);
        Noeud deux = new Noeud(nom_2);
        Noeud trois = new Noeud(nom_3);
        Noeud quatre = new Noeud(nom_4);

        quatre.setSommetChemin(nom_3);
        trois.setSommetChemin(nom_2);
        deux.setSommetChemin(nom_1);

        Map<Integer, Noeud> cc = new HashMap<>();

        cc.put(premier.getSommet(), premier);
        cc.put(deux.getSommet(), deux);
        cc.put(trois.getSommet(), trois);
        cc.put(quatre.getSommet(), quatre);


        //  cheminLePlusCourt(premier.getSommet(),quatre,cc);
    }


    public static Noeud distanceLaPlusCourte(ArrayList<Noeud> m, Integer[][] dijkstra, int etape)
    {
        Noeud tampon_sommet = null;
        int tampon_valeur = Integer.MAX_VALUE;

        for (Noeud n : m)
        {
            if (dijkstra[etape][n.getSommet()] < tampon_valeur)
            {
                tampon_valeur = dijkstra[etape][n.getSommet()];
                tampon_sommet = n;
            }
        }
        return tampon_sommet;
    }

    public static void cheminLePlusCourt(int initiale, Noeud sommet_choisi, ArrayList<Integer> cc, Map<Noeud, Noeud> predecesseurCheminCourt, Graphe graphe)
    {
        //Map<Noeud, Noeud> chemin= new HashMap<>();

        ArrayList<Noeud> chemin = new ArrayList<>();

        if (initiale == sommet_choisi.getSommet())
        {
            System.out.println("Le sommet choisi est le sommet initiale \n");
            //chemin.put(sommet_choisi,sommet_choisi);
            chemin.add(sommet_choisi);
            System.out.println("Le chemin est : ");


            for (Noeud affichage : chemin)
            {
                System.out.print(affichage.getSommet());
            }


        }

        Noeud a = null;
        Noeud b = null;

        a = sommet_choisi.clone();

        System.out.println("Le sommet clone a pour nom : " + a.getSommet());


        if (predecesseurCheminCourt.get(a).getSommet() == initiale)
        {
            System.out.println("Le chemin le plus court est " + sommet_choisi.getDistance() + "," + initiale);

            //System.out.println("Le chemin le plus court est " + sommet_choisi + "," + initiale);
            //chemin.put(sommet_choisi,sommet_choisi);
            //chemin.put(cc.get(initiale),cc.get(initiale));


            for (Noeud j : graphe.getNoeuds())
            {
                if (j.getSommet() == cc.get(0))
                {
                    b = j.clone();
                }
            }

            chemin.add(sommet_choisi);
            chemin.add(b);

            System.out.println("Le chemin est : ");


            for (Noeud affichage : chemin)
            {
                System.out.print(affichage.getSommet());
            }
        }


        if (a.getSommet() != initiale)
        {
            while (a.getSommet() != initiale)
            {
                System.out.println("Valeur mise dans la map de a :  " + a.getSommet());
                //chemin.put(a, a);
                chemin.add(a);
                a = predecesseurCheminCourt.get(a).clone();
                System.out.println("Nouvelle valeur de a : " + a.getSommet());


            }

            //chemin.put(cc.get(initiale), cc.get(initiale));

            chemin.add(b);


            Collections.reverse(chemin);
            System.out.println("Le chemin est : ");
            for (Noeud affichage : chemin)
            {
                System.out.print(affichage.getSommet());
            }

            graphe.getToutLesChemins().put(sommet_choisi, chemin);


        }
    }

    public static Noeud predecesseur(Map<Integer, Noeud> cc, int pred)
    {
        return cc.get(pred);
    }


    public static void bellman(Graphe graphe)
    {
        //initialisation d'un graph sous la forme d'un tableau de transition


        int nombreSommets = graphe.getNombreSommets();
        int tableau_transitions[][] = new int[graphe.getNombreTransitions()][3];
        int transition = 0;
        for (Noeud noeud : graphe.getNoeuds())
        {
            for (Map.Entry<Noeud, Integer> entry : noeud.getSuccesseurs().entrySet())
            {
                tableau_transitions[transition] = new int[]{noeud.getSommet(), entry.getKey().getSommet(), entry.getValue()};
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
        for (int i = 0; i < nombreSommets; i++)
        {
            for (int n = 0; n < nombreSommets; n++)
            {
                tableau_de_k[i][n] = MAX_SIZE;
            }
        }

        //l'utilisateur choisit le sommet de départ
        int sommet_depart;
        sommet_depart = 0;

        //initialisation de la colonne du sommet de départ
        for (int j = 0; j < nombreSommets; j++)
        {
            tableau_de_k[j][sommet_depart] = 0;
            tableau_de_predecesseur[j][sommet_depart] = sommet_depart;
        }

        //Savoir si le graph est absorbant ou pas ?
        int absorbant_ou_pas;
        int plus_petite_transition = 0;

        for (int recherche_absorbant_ou_pas = 0; recherche_absorbant_ou_pas < nombreTransitions; recherche_absorbant_ou_pas++)
        {
            plus_petite_transition = Math.min(plus_petite_transition, tableau_transitions[recherche_absorbant_ou_pas][2]);
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
        if (absorbant_ou_pas == 0)
        {
            for (int k = 1; k < nombreSommets; k++)
            {
                for (int o = 0; o < nombreTransitions; o++)
                {

                    prec = tableau_transitions[o][0]; //on récup le prec grace au graph
                    succ = tableau_transitions[o][1];
                    value = tableau_transitions[o][2];

                    prec_v2 = tableau_de_k[k - 1][prec]; //on recup le poids pour le prec dans le tableau k
                    succ_v2 = tableau_de_k[k - 1][succ];

                    v = Math.min(succ_v2, (prec_v2 + value)); //on cherche le min

                    if (v < tableau_de_k[k][succ])
                    {
                        tableau_de_k[k][succ] = v; // on modifie la valeur dans le tableau k pour avoir le plus petit chemin
                        if (v != tableau_de_k[k - 1][succ])
                        {
                            tableau_de_predecesseur[k][succ] = prec;
                        }
                    }
                }
            }
        } else
        { //si le graph n'a pas de circuit absorbant
            int k = 1;
            while (suivant == 1)
            {
                for (int o = 0; o < nombreTransitions; o++)
                {

                    prec = tableau_transitions[o][0]; //on récup le prec grace au graph
                    succ = tableau_transitions[o][1];
                    value = tableau_transitions[o][2];

                    prec_v2 = tableau_de_k[k - 1][prec]; //on recup le poids pour le prec dans le tableau k
                    succ_v2 = tableau_de_k[k - 1][succ];

                    v = Math.min(succ_v2, (prec_v2 + value)); //on cherche le min

                    if (v < tableau_de_k[k][succ])
                    {
                        tableau_de_k[k][succ] = v; // on modifie la valeur dans le tableau k pour avoir le plus petit chemin
                        if (v != tableau_de_k[k - 1][succ])
                        {
                            tableau_de_predecesseur[k][succ] = prec;
                        }
                    }
                }

                for (int m = 0; m < nombreSommets; m++)
                {
                    if (tableau_de_k[k - 1][m] == tableau_de_k[k][m])
                    {
                        compteur++;
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
            while (b < nombreSommets)
            {
                if (tableau_de_k[a][b] > 900)
                {
                    System.out.printf("%4s    |", "*");
                } else
                {
                    if (tableau_de_predecesseur[a][b] == 0)
                    {
                        tableau_de_predecesseur[a][b] = tableau_de_predecesseur[a - 1][b];
                    }
                    System.out.printf("%3d (%d) |", tableau_de_k[a][b], tableau_de_predecesseur[a][b]);
                }
                b++;
            }
            System.out.println("");
            a++;
        }

    }


    static void enregistrementMatrice(int[][] matrice) throws FileNotFoundException
    {
        final int NUMERO_GRAPHE = 1;
        final int SOMMET_DEPART = 1;

        int taille = matrice.length - 1;

        PrintWriter enregistrement = new PrintWriter("L3-D4-trace" + NUMERO_GRAPHE + "_" + SOMMET_DEPART + ".txt");

        //affichage n°ligne
        enregistrement.print("suc ");
        for (int cmpt1 = 0; cmpt1 < matrice.length; cmpt1++)
        {
            enregistrement.print(cmpt1 + " ");
            if (cmpt1 < 10)
            {
                enregistrement.print(" ");
            }
        }
        enregistrement.println();
        enregistrement.print("pre");
        for (int cmpt2 = 0; cmpt2 < matrice.length; cmpt2++)
        {
            enregistrement.print("__");
            if (cmpt2 < 10)
            {
                enregistrement.print("_");
            }
        }
        enregistrement.println();


        //affichage matrice
        for (int i = 0; i < matrice[taille].length; i++)
        {
            //affichage n°colonne
            enregistrement.print(i + " | ");


            for (int j = 0; j < matrice.length; j++)
            {
                enregistrement.print(matrice[i][j] + " ");
                if (matrice[i][j] < 10)
                {
                    enregistrement.print(" ");
                }
            }
            enregistrement.println();


        }
        enregistrement.close();
    }


    public static void menu()
    {
        Scanner kb = new Scanner(System.in);

        int rep = 0;

        do
        {
            System.out.println("Projet Théorie des Graphes L3 Groupe D equipe 4 \n");


            try
            {
                String fichier = choixFichier();
                Graphe graphe = new Graphe(fichier);
                if (graphe.isArcsPositifs() == false)
                {
                    //appel Bellman
                    bellman(graphe);
                } else
                {
                    System.out.println("Voulez-vous utiliser l'algorithme de Dijkstra (1) ou de Bellman (2) ? \n");
                    int choix = 0;
                    choix = kb.nextInt();

                    while (choix < 1 || choix > 2)
                    {
                        System.out.println("Veuillez refaire votre choix (1 ou 2) : ");
                        choix = kb.nextInt();
                    }

                    if (choix == 1)
                    {
                        dijkstra(graphe);
                    }

                    if (choix == 2)
                    {
                        bellman(graphe);
                    }

                }
            }
            catch (FileNotFoundException e)
            {
                System.out.println("Aucun fichier trouvé");
            }


            System.out.println("Voulez vous continuer (1) ou quitter ? (2)");

            rep = inputWithOnlyInt();


        } while (rep != 2);


    }

    /**
     * Methode qui demande à la personne de choisir quelle graphe elle souhaite ouvrir parmi les 10 disponibles
     * avec une saisie sécurisée contre les caracteres
     *
     * @return le fichier que l'on souhaite ouvrir
     */
    public static String choixFichier()
    {
        int saisie;
        System.out.println("Choisissez un graphe entre 1 et 10 :");
        do
        {
            saisie = inputWithOnlyInt();
        } while (saisie < 1 || saisie > 10);

        return "L3-D4-" + saisie + ".txt";
    }

    public static int inputWithOnlyInt()
    {
        Scanner kb = new Scanner(System.in);

        System.out.print("-> ");
        while (!kb.hasNextInt())  //si la selection est différente d'un entier on coninue de demander une saisie
        {
            System.out.print("Make a correct selection please\n-> ");
            kb.next();
        }

        return kb.nextInt();
    }


}

