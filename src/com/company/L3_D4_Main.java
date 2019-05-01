package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class L3_D4_Main {

    //Leo
    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("Hello World!");

        int[][] matrice = creationMatrice();

        System.out.println("\n\n");

        int[][] matriceValeur = creationMatriceValeur();

        enregistrementMatrice(matrice);

        djistra();

        //test();

    }
//
    public static void djistra() throws FileNotFoundException
    {
        ArrayList<Noeud> cc = new ArrayList<>();
        Graphe graphe = new Graphe(); // A initialiser avec tous les sommets

        try {
            graphe.initialisationMatriceValeur();
            graphe.affichageValeurs();
            ArrayList<Noeud> m = graphe.getNoeuds();
            int nombre_sommet = graphe.getNoeuds().size();
            int pi_etoile[] = new int[nombre_sommet]; // tableau des valeurs de Pi étoiles de taille nombre de sommets
            int pi[] = new int[nombre_sommet]; // tableau des valeurs de Pi  de taille nombre de sommets
            int tab_matrice[] = new int[nombre_sommet];

            Scanner kb = new Scanner(System.in);
            System.out.println("Quel est le sommet de départ ?"); // choix du sommet de départ

            int initiale = kb.nextInt();

            System.out.println("Le sommet choisi est :" + initiale);

            for (Noeud i : m)  // parcourt de l'ensemble M
                if (initiale == i.getSommet()) {  // on cherche le sommet que l'utilisateur a choisi
                    cc.add(i); // Ajout du Noeud dans CC
                    m.remove(i); // On retire le noeud de CC
                    pi_etoile[initiale] = 0; // on initialise son pi_etoile à 0
                    System.out.println("IN");

                    System.out.println(cc.get(0).getSuccesseurs());
                    if (i.aSuccesseurs()) {


                        for(Map.Entry<Noeud,Integer> entry : cc.get(0).getSuccesseurs().entrySet())
                        {
                            Noeud cle = entry.getKey();
                            cle.setDistance(entry.getValue());

                        }


                    } else i.setDistance(1000);


                    //si matrice_adjacence = 1


            /*if (matriceAdjacence[initiale][i.getSommet()]== 1)
            {
                pi[i.getSommet()] = matriceValeur[initiale][i.getSommet()]; // On initialise tous les autres PI
            }

            else
            {
                pi[i.getSommet()] = 100000;
            }
                    */


                    int tampon_valeur = 1000; //initialisation arbitraire
                    //Noeud tampon_sommet = new Noeud();

                    int tampon_nom = -1;

                    int sommet=initiale;
                    while (m.size() != 0) {

                        Noeud tampon_sommet = distanceLaPlusCourte(m, graphe.getMatriceAdjacence(),sommet);
                        sommet=tampon_sommet.getSommet();
                        System.out.println("TEST : " + tampon_sommet.getSommet());
                        m.remove(tampon_sommet);


                        cc.add(tampon_sommet);

                        pi_etoile[tampon_sommet.getSommet()] = pi[tampon_sommet.getSommet()];

                        System.out.println("Taille de m" + m.size());


                        if (m.size() != 0) {
                            for (Noeud c : m)  // parcourt de l'ensemble M
                                if (tampon_sommet.estPredecesseur(c) && graphe.getMatriceAdjacence()[c.getSommet()][tampon_sommet.getSommet()]==1) {

                                    c.setDistance(Math.min(c.getDistance(), tampon_sommet.getDistance() + tampon_sommet.getSuccesseurs().get(c)));


                                    //pi[i.getSommet()] = Math.min(pi[i.getSommet()],pi[tampon_sommet.getSommet()] + matriceValeur[tampon_sommet.getSommet()][i.getSommet()]);
                                    //tab_matrice[i.getSommet()] = tampon_sommet.getSommet();
                                    c.setSommetChemin(tampon_sommet.getSommet());
                                }
                        }
                    }

                    System.out.println("Choisir un sommet : \n");

                    int choix = kb.nextInt();

                    System.out.println("Vous avez choisi le sommet : " + choix + "\n");

                    Noeud sommet_choisi = new Noeud();

                    for (Noeud n : cc) {
                        if (choix == n.getSommet()) {
                            sommet_choisi = n;
                        }
                    }

                    cheminLePlusCourt(initiale, sommet_choisi, cc);

                }


            // tant que tab[sommet choisi cc]  =! tab [sommet initiale] return i = tab


        } catch (FileNotFoundException e) {
            System.out.println("Aucun fichier trouvé");
        }
    }


    public static void test() throws FileNotFoundException
    {
        Graphe test = new Graphe();

        try {
            test.initialisationMatriceValeur();
            test.initialisationMatriceAdjacence();

            for (Noeud n : test.getNoeuds())
            {
                System.out.println("Nom noeud : "+ n.getSommet()+ " Sucesseurs " + n.getSuccesseurs().get(n) + "Predecesseur" + n.getSommetChemin() +"\n");
            }

        }
        catch(FileNotFoundException e)
        {
            System.out.println("Erreur");
        }
    }

    public static Noeud distanceLaPlusCourte(ArrayList<Noeud> m, int[][] matriceAdjacence, int initiale)
    {
        Noeud tampon_sommet = null;
        int tampon_valeur = Integer.MAX_VALUE;

        for (Noeud n: m) {
            if (matriceAdjacence[initiale][n.getSommet()]==1)
            {
                int noeudDistance = n.getDistance();
                if (noeudDistance < tampon_valeur) {
                    tampon_valeur = noeudDistance;
                    tampon_sommet = n;
                }
            }

        }
        return tampon_sommet;
    }

    public static void  cheminLePlusCourt(int initiale, Noeud sommet_choisi, ArrayList<Noeud> cc)
    {
        if (initiale == sommet_choisi.getSommet())
        {
            System.out.println("Le sommet choisi est le sommet initiale");
        }

        Noeud a = null;

        a = sommet_choisi;

        System.out.println(a.getSommet());

        if (a.getSommetChemin() == initiale)
        {
            System.out.println("Le chemin le plus court est " + sommet_choisi +","+ initiale );
        }

        while (a.getSommet()!= initiale)
        {
            System.out.println("NEW : " + a.getSommetChemin());
            a = predecesseur(cc, a.getSommetChemin());
            System.out.println("New s : " + a.getSommetChemin());
        }

        if(a.getSommet() == initiale)
        {
            System.out.println(initiale);
        }

    }

    public static Noeud predecesseur(ArrayList<Noeud> cc, int pred)
    {
        Noeud tampon = null;
        for (Noeud n : cc)
        {
           if (pred == n.getSommet())
           {
               tampon = n;
           }
        }

        return tampon;
    }


    static int[][] creationMatrice() throws FileNotFoundException {
        File fileMatrice = new File("L3-D4-1.txt");
        Scanner readMatrice = new Scanner(fileMatrice);

        int nombreSommets = readMatrice.nextInt();
        int[][] matrice = new int[nombreSommets][nombreSommets];
        int succ;
        int prec;
        int valeur;

        while (readMatrice.hasNext()) {
            succ = readMatrice.nextInt();
            valeur = readMatrice.nextInt();
            prec = readMatrice.nextInt();
            matrice[succ][prec] = 1;
        }

        affichage(matrice);

        readMatrice.close();
        return matrice;
    }

    static void affichage(int[][] matrice) {
        //affichage n°ligne
        System.out.print("suc ");
        for (int cmpt1 = 0; cmpt1 < matrice.length; cmpt1++) {
            System.out.print(cmpt1 + " ");
            if (cmpt1 < 10) {
                System.out.print(" ");
            }
        }
        System.out.println();
        System.out.print("pre");
        for (int cmpt2 = 0; cmpt2 < matrice.length; cmpt2++) {
            System.out.print("__");
            if (cmpt2 < 10) {
                System.out.print("_");
            }
        }
        System.out.println();


        //affichage matrice
        for (int i = 0; i < matrice.length; i++) {
            //affichage n°colonne
            System.out.print(i + " | ");


            for (int j = 0; j < matrice.length; j++) {

                System.out.print(matrice[i][j] + " ");
                if (matrice[i][j] < 10) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    static void enregistrementMatrice(int[][] matrice) throws FileNotFoundException {
        final int NUMERO_GRAPHE = 1;
        final int SOMMET_DEPART = 1;

        int taille = matrice.length - 1;

        PrintWriter enregistrement = new PrintWriter("L3-D4-trace" + NUMERO_GRAPHE + "_" + SOMMET_DEPART + ".txt");

        //affichage n°ligne
        enregistrement.print("suc ");
        for (int cmpt1 = 0; cmpt1 < matrice.length; cmpt1++) {
            enregistrement.print(cmpt1 + " ");
            if (cmpt1 < 10) {
                enregistrement.print(" ");
            }
        }
        enregistrement.println();
        enregistrement.print("pre");
        for (int cmpt2 = 0; cmpt2 < matrice.length; cmpt2++) {
            enregistrement.print("__");
            if (cmpt2 < 10) {
                enregistrement.print("_");
            }
        }
        enregistrement.println();


        //affichage matrice
        for (int i = 0; i < matrice[taille].length; i++) {
            //affichage n°colonne
            enregistrement.print(i + " | ");


            for (int j = 0; j < matrice.length; j++) {
                enregistrement.print(matrice[i][j] + " ");
                if (matrice[i][j] < 10) {
                    enregistrement.print(" ");
                }
            }
            enregistrement.println();


        }
        enregistrement.close();
    }

    static int[][] creationMatriceValeur() throws FileNotFoundException {
        File fileMatrice = new File("L3-D4-1.txt");
        Scanner readMatrice = new Scanner(fileMatrice);

        int nombreSommets = readMatrice.nextInt();
        int[][] matrice = new int[nombreSommets][nombreSommets];
        int succ;
        int prec;
        int valeur;

        while (readMatrice.hasNext()) {
            succ = readMatrice.nextInt();
            valeur = readMatrice.nextInt();
            prec = readMatrice.nextInt();
            matrice[succ][prec] = valeur;
        }

        affichage(matrice);

        readMatrice.close();
        return matrice;
    }



}

