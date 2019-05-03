package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Graphe {

    private Map<Noeud, Noeud> chemin= new HashMap<>();
    private ArrayList<Noeud> noeuds;

    public int[][] getMatriceAdjacence()
    {
        return matriceAdjacence;
    }

    private int[][] matriceAdjacence;
    private ArrayList<Noeud> matriceValeurs;

    public Graphe() throws FileNotFoundException
    {
        noeuds = new ArrayList<>();
        initialisationMatriceAdjacence();
        matriceAdjacence=creationTableauAffichage();

    }

    public ArrayList<Noeud> getNoeuds() {
        return noeuds;
    }

    public void setNoeuds(ArrayList<Noeud> noeuds) {
        this.noeuds = noeuds;
    }

    void initialisationMatriceValeur() throws FileNotFoundException {
        File fileMatrice = new File("L3-D4-1.txt");
        Scanner readMatrice = new Scanner(fileMatrice);


        int taille = readMatrice.nextInt();
        int succ;
        int pred;
        int longueur;

        noeuds.clear();

        for (int i = 0; i < taille; i++) {
            this.noeuds.add(new Noeud(i));
        }

        while (readMatrice.hasNext()) {
            pred = readMatrice.nextInt();
            longueur = readMatrice.nextInt();
            succ = readMatrice.nextInt();

            this.noeuds.get(succ).addPredecesseurs(pred);//ajout du predecesseur
            this.noeuds.get(pred).getSuccesseurs().put(noeuds.get(succ),longueur);// ajout du successeur et de la longueur de la liaison
        }
        readMatrice.close();
    }

    void initialisationMatriceAdjacence() throws FileNotFoundException {
        File fileMatrice = new File("L3-D4-1.txt");
        Scanner readMatrice = new Scanner(fileMatrice);


        int taille = readMatrice.nextInt();
        int succ;
        int pred;
        int longueur;

        noeuds.clear();

        for (int i = 0; i < taille; i++) {
            this.noeuds.add(new Noeud(i));
        }

        while (readMatrice.hasNext()) {
            pred = readMatrice.nextInt();
            longueur = readMatrice.nextInt();// meme si la longeur n'est pas utilisée, il est préférable de la stocker pour éviter tout désagrément
            succ = readMatrice.nextInt();

            this.noeuds.get(succ).addPredecesseurs(pred);//ajout du predecesseur
            this.noeuds.get(pred).getSuccesseurs().put(noeuds.get(succ),1);// ajout du successeur et dde la valeur 1
        }
        readMatrice.close();
    }

    int[][] creationTableauAffichage()
    {
        int tableau[][] = new int [noeuds.size()][noeuds.size()];

        for (int i = 0;i < noeuds.size();i++)
        {
            for (int j = 0; j < noeuds.size();j++)
            {
                if(noeuds.get(i).getSuccesseurs().get(noeuds.get(j))!= null)// à vérifier
                {
                    tableau[i][j] = noeuds.get(i).getSuccesseurs().get(noeuds.get(j));
                }
            }
        }
        return tableau;
    }

    void affichage() throws FileNotFoundException
    {

        int matrice[][] = creationTableauAffichage();


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

    public void affichageValeurs() throws FileNotFoundException
    {
        initialisationMatriceAdjacence();
        int[][] matriceAdjacence=creationTableauAffichage();
        initialisationMatriceValeur();
        int matrice[][] = creationTableauAffichage();
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
                if (matriceAdjacence[i][j]==1)
                {
                    System.out.print(matrice[i][j] + " ");

                }
                else
                {
                    System.out.print("-" + " ");
                }
                if (matrice[i][j] < 10) {
                    System.out.print(" ");
                }

            }
            System.out.println();
        }

    }

}
