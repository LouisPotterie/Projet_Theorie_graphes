package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class L3_D4_Graphe
{

    private Map<L3_D4_Noeud,ArrayList> toutLesChemins = new HashMap<>(); // map qui va stocker en key des sommets et en value les des arraylists qui represente les chemins du sommet initiale vers le sommet en KEY



    public Map<L3_D4_Noeud, ArrayList> getToutLesChemins() {
        return toutLesChemins;
    } //getter de la map


    private boolean arcsPositifs = true; // boolean pour savoir si le graphe possede que des arcs positifs

    public boolean isArcsPositifs() {
        return arcsPositifs;
    } // getter du boolean

    public void setArcsPositifs(boolean arcsPositifs) {
        this.arcsPositifs = arcsPositifs;
    } // setter du boolean



    private ArrayList<L3_D4_Noeud> noeuds; // arrayList contenant tous les noeuds

    public int getNombreTransitions()
    {
        return nombreTransitions;
    } // Getter de l'attribut nombre de transition

    private int nombreTransitions; // le nombre de transition du graphe

    public int[][] getMatriceAdjacence()
    {
        return matriceAdjacence;
    } // getter matrice adjacence

    public int getNombreSommets()
    {
        return noeuds.size();
    } // getter du nombres de sommets par l'intermediaire du la taille de l'arraylist

    private int[][] matriceAdjacence; // tab 2d representant la matrice d adjacence

    public int[][] getMatriceValeurs()
    {
        return matriceValeurs;
    }   // getter de la matrice de valeur

    private int[][] matriceValeurs; // tab 2d representant la matrice des valeurs

    /**
     *
     *  Constructeur de la classe
     * @param numFichier on lui passe le numero du fichier
     * @throws FileNotFoundException
     */

    public L3_D4_Graphe(int numFichier) throws FileNotFoundException
    {
        noeuds = new ArrayList<>();
        initialisationMatrices(numFichier); //initialisation des matrices de valeurs et d adjacence

    }

    public ArrayList<L3_D4_Noeud> getNoeuds() { //getter de l'ArrayList noeuds
        return noeuds;
    }

    public void setNoeuds(ArrayList<L3_D4_Noeud> noeuds) {
        this.noeuds = noeuds;
    }


    public void affichage_toutLesChemins() // methode permettant d'afficher la Map toutLesChemins si nescessaire
    {
        for (Map.Entry<L3_D4_Noeud, ArrayList> entry : toutLesChemins.entrySet()) // Parcourt de la map
        {
            ArrayList<L3_D4_Noeud> cle = entry.getValue();

            System.out.println("Le chemin pour aller du sommet initiale à " + entry.getKey().getSommet() + " est" );

            for (L3_D4_Noeud n : cle) // parcourt de l'ArrayList
            {
                System.out.print(n.getSommet()); // affichage des noeuds présent dans l'Arraylist
            }

            System.out.println("\n");
        }
    }


     void initialisationMatrices(int numFichier) throws FileNotFoundException {


        File fileMatrice = new File("L3_D4_" + numFichier + ".txt"); // récupération du txt selon la valeur choisi par l'utilisateur
        Scanner readMatrice = new Scanner(fileMatrice); // scanner permettant la lecture du txt

        ArrayList<L3_D4_Noeud> noeudsAdjascence = new ArrayList<>();

        int taille = readMatrice.nextInt(); //recupere le nombre de sommet écrit 1ere ligne du txt
        int succ; //le nom du noeud du départ de l'arc
        int pred; // le nom du noeud d'arrivée de l'arc
        int longueur; //recupere la longueur de l'arc

        noeuds.clear(); // clear pour être sûr que l'ArrayList est bien vide avant de la remplir

        for (int i = 0; i < taille; i++) {
            this.noeuds.add(new L3_D4_Noeud(i));
            noeudsAdjascence.add(new L3_D4_Noeud(i));
        }
        nombreTransitions=0;
        while (readMatrice.hasNext()) { // tant qu'il y a des données disponibles
            pred = readMatrice.nextInt(); // 1ere colonne on recupere le nom du noeud du départ de l'arc
            longueur = readMatrice.nextInt();// meme si la longeur n'est pas utilisée, il est préférable de la stocker pour éviter tout désagrément
            if (longueur<0) // si une longueur est inférieur à 0 alors on set le boolean arcsPositifs à faux
            {
                arcsPositifs = false;
            }
            succ = readMatrice.nextInt(); // 3eme colonne on recupere le nom du noeud  d'arrivée de l'arc
            nombreTransitions++; // on incrémente le nombre de transitions de 1
            noeudsAdjascence.get(succ).addPredecesseurs(pred);//ajout du predecesseur
            noeudsAdjascence.get(pred).addSuccesseurs(noeudsAdjascence.get(succ),1);// ajout du successeur et dde la valeur 1
            this.noeuds.get(succ).addPredecesseurs(pred);//ajout du predecesseur
            this.noeuds.get(pred).addSuccesseurs(noeuds.get(succ),longueur);// ajout du successeur et dde la valeur 1
        }

        matriceValeurs = creationTableauAffichage(noeuds);
        matriceAdjacence = creationTableauAffichage(noeudsAdjascence);
        readMatrice.close(); // fermeture du fichier de lecture
    }


    /**
     *  Methode permettant de créer la structure d'un tableau d'affichage
     * @param noeuds2
     * @return tableau
     */

    int[][] creationTableauAffichage(ArrayList<L3_D4_Noeud> noeuds2)
    {
        int tableau[][] = new int [noeuds2.size()][noeuds2.size()];

        for (int i = 0;i < noeuds2.size();i++)
        {
            for (int j = 0; j < noeuds2.size();j++)
            {
                if(noeuds2.get(i).getSuccesseurs().get(noeuds2.get(j))!= null)
                {
                    tableau[i][j] = noeuds2.get(i).getSuccesseurs().get(noeuds2.get(j)); // permettant d'afficher les sommets
                }
            }
        }
        return tableau;
    }

    /**
     *  Méthode permettant l'affichage de la matrice d'adjacence
     * @param enregistrement
     * @throws FileNotFoundException
     */

    public void affichageAdjacence(PrintWriter enregistrement) throws FileNotFoundException
    {
        //affichage n°ligne
        System.out.print("suc ");
        enregistrement.print("suc ");
        for (int cmpt1 = 0; cmpt1 < matriceAdjacence.length; cmpt1++) {
            enregistrement.print(cmpt1 + " ");
            System.out.print(cmpt1 + " ");
            if (cmpt1 < 10) {
                System.out.print(" ");
                enregistrement.print(" ");
            }
        }
        System.out.println();
        enregistrement.println();
        System.out.print("pre");
        enregistrement.print("pre");
        for (int cmpt2 = 0; cmpt2 < matriceAdjacence.length; cmpt2++) {
            enregistrement.print("__");
            System.out.print("__");
            if (cmpt2 < 10) {
                System.out.print("_");
                enregistrement.print("_");
            }
        }
        System.out.println();
        enregistrement.println();

        //affichage matrice
        for (int i = 0; i < matriceAdjacence.length; i++) {
            //affichage n°colonne
            System.out.print(i + " | ");
            enregistrement.print(i + " | ");


            for (int j = 0; j < matriceAdjacence.length; j++) {

                if((matriceAdjacence[i][j]!=0))
                {
                    System.out.print(matriceAdjacence[i][j] + " ");
                    enregistrement.print(matriceAdjacence[i][j] + " ");
                }
                else
                {
                    System.out.print( "- ");
                    enregistrement.print( "- ");
                }


                if (matriceAdjacence[i][j] < 10) {
                    System.out.print(" ");
                    enregistrement.print(" ");
                }

            }
            System.out.println();
            enregistrement.println("\n");
        }

    }

    /**
     * Méthode permettant l'affichage de la matrice des valeurs
     * @param enregistrement
     * @throws FileNotFoundException
     */

    public void affichageValeurs(PrintWriter enregistrement) throws FileNotFoundException
    {
        //affichage n°ligne
        System.out.print("suc ");
        enregistrement.print("suc ");
        for (int cmpt1 = 0; cmpt1 < matriceValeurs.length; cmpt1++) {
            System.out.print(cmpt1 + " ");
            enregistrement.print(cmpt1 + " ");
            if (cmpt1 < 10) {
                System.out.print(" ");
                enregistrement.print(" ");
            }
        }
        System.out.println();
        enregistrement.println();
        System.out.print("pre");
        enregistrement.print("pre");
        for (int cmpt2 = 0; cmpt2 < matriceValeurs.length; cmpt2++) {
            System.out.print("__");
            enregistrement.print("__");
            if (cmpt2 < 10) {
                System.out.print("_");
                enregistrement.print("_");
            }
        }
        System.out.println();
        enregistrement.println();

        //affichage matrice
        for (int i = 0; i < matriceValeurs.length; i++) {
            //affichage n°colonne
            System.out.print(i + " | ");
            enregistrement.print(i + " | ");


            for (int j = 0; j < matriceValeurs.length; j++) {
                if (matriceAdjacence[i][j]==1)
                {
                    System.out.print(matriceValeurs[i][j] + " ");
                    enregistrement.print(matriceValeurs[i][j] + " ");

                }
                else
                {
                    System.out.print("-" + " ");
                    enregistrement.print("-" + " ");
                }
                if (matriceValeurs[i][j] < 10) {
                    System.out.print(" ");
                    enregistrement.print(" ");
                }

            }
            System.out.println();
            enregistrement.println("\n");
        }

    }

}
