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

    }
//
    public static void djistra() {
        ArrayList<Noeud> cc = new ArrayList<>();
        Graphe graphe = new Graphe(); // A initialiser avec tous les sommets
        ArrayList<Noeud> m =graphe.getNoeuds();

        int nombre_sommet = graphe.getNoeuds().size();
        int pi_etoile[] = new int[nombre_sommet]; // tableau des valeurs de Pi étoiles de taille nombre de sommets
        int pi[] = new int[nombre_sommet]; // tableau des valeurs de Pi  de taille nombre de sommets
        int tab_matrice[] = new int [nombre_sommet];

        Scanner kb = new Scanner(System.in);
        System.out.println("Quel est le sommet de départ ?"); // choix du sommet de départ

        int initiale = kb.nextInt();

        System.out.println("Le sommet choisi est :" + initiale);

        for (Noeud i : m)  // parcourt de l'ensemble M
        {

            if (initiale == i.getSommet()) {  // on cherche le sommet que l'utilisateur a choisi
                cc.add(i); // Ajout du Noeud dans CC
                m.remove(i); // On retire le noeud de CC
                pi_etoile[initiale] = 0; // on initialise son pi_etoile à 0
            }
            //si matrice_adjacence = 1
            if (i.aSuccesseurs())
            {
                                  i.setDistance(m.get(initiale).getSuccesseurs().get(i));
                 }
                 else i.setDistance(1000);

            /*if (matriceAdjacence[initiale][i.getSommet()]== 1)
            {
                pi[i.getSommet()] = matriceValeur[initiale][i.getSommet()]; // On initialise tous les autres PI
            }

            else
            {
                pi[i.getSommet()] = 100000;
            }
                    */

        }

        int tampon_valeur = 1000; //initialisation arbitraire
        Noeud tampon_sommet = new Noeud();



        while (m.size() != 0) {
            for (Noeud i : m)  // parcourt de l'ensemble M
            {
                if (i.getDistance() < tampon_valeur) {
                    tampon_valeur = i.getDistance();

                    tampon_sommet = i; // objet = objet
                }
            }

            cc.add(tampon_sommet);
            m.remove(tampon_sommet);
            pi_etoile[tampon_sommet.getSommet()] = pi[tampon_sommet.getSommet()];


            if (m.size() != 0) {
                for (Noeud i : m)  // parcourt de l'ensemble M
                {
                    if (i.estPredecesseur(tampon_sommet) && m.contains(i)) {
                        i.setDistance(Math.min(i.getDistance(), tampon_sommet.getDistance() + tampon_sommet.getSuccesseurs().get(i)));
                        //pi[i.getSommet()] = Math.min(pi[i.getSommet()],pi[tampon_sommet.getSommet()] + matriceValeur[tampon_sommet.getSommet()][i.getSommet()]);
                        //tab_matrice[i.getSommet()] = tampon_sommet.getSommet();
                        i.setSommetChemin(tampon_sommet.getSommet());
                    }

                }
            }
        }

        System.out.println("Choisir un sommet : \n");

        int choix = kb.nextInt();

        System.out.println("Vous avez choisi le sommet : " + choix + "\n");

        Noeud sommet_choisi = new Noeud();

        for (Noeud n : cc)
        {
            if (choix == n.getSommet())
            {
                sommet_choisi = n;
            }
        }

        cheminLePlusCourt(initiale,sommet_choisi);
        

        // tant que tab[sommet choisi cc]  =! tab [sommet initiale] return i = tab



       
    }

    public static void  cheminLePlusCourt(int initiale, Noeud sommet_choisi)
    {
        if (initiale == sommet_choisi.getSommet())
        {
            System.out.println("Le sommet choisi est le sommet initiale");
        }

        Noeud a = new Noeud();

        a = sommet_choisi;

        if (a.getSommetChemin() == initiale)
        {
            System.out.println("Le chemin le plus court est " + sommet_choisi +","+ initiale );
        }

        while (a.getSommet()!= initiale)
        {
            System.out.println(a.getSommetChemin()+",");
            a.setSommet(a.getSommetChemin());
        }

        if(a.getSommet() == initiale)
        {
            System.out.println(initiale);
        }

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

