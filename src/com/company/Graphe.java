package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Graphe {

    private Map<Noeud,ArrayList> toutLesChemins = new HashMap<>();

    public Map<Noeud, ArrayList> getToutLesChemins() {
        return toutLesChemins;
    }

    public void setToutLesChemins(Map<Noeud, ArrayList> toutLesChemins) {
        this.toutLesChemins = toutLesChemins;
    }


    private boolean arcsPositifs = true;

    public boolean isArcsPositifs() {
        return arcsPositifs;
    }

    public void setArcsPositifs(boolean arcsPositifs) {
        this.arcsPositifs = arcsPositifs;
    }



    private ArrayList<Noeud> noeuds;

    public int getNombreTransitions()
    {
        return nombreTransitions;
    }

    private int nombreTransitions;

    public int[][] getMatriceAdjacence()
    {
        return matriceAdjacence;
    }

    public int getNombreSommets()
    {
        return noeuds.size();
    }

    private int[][] matriceAdjacence;
    private int[][] matriceValeurs;

    public Graphe(String fichier) throws FileNotFoundException
    {
        noeuds = new ArrayList<>();
        initialisationMatrices(fichier);
        //initialisationMatrices();
    }

    public ArrayList<Noeud> getNoeuds() {
        return noeuds;
    }

    public void setNoeuds(ArrayList<Noeud> noeuds) {
        this.noeuds = noeuds;
    }

     void initialisationMatrices(String fichier) throws FileNotFoundException {
        //File fileMatrice = new File("L3-D4-1.txt");

        File fileMatrice = new File(fichier);
        Scanner readMatrice = new Scanner(fileMatrice);

        ArrayList<Noeud> noeudsAdjascence = new ArrayList<>();

        int taille = readMatrice.nextInt();
        int succ;
        int pred;
        int longueur;

        noeuds.clear();

        for (int i = 0; i < taille; i++) {
            this.noeuds.add(new Noeud(i));
            noeudsAdjascence.add(new Noeud(i));
        }
        nombreTransitions=0;
        while (readMatrice.hasNext()) {
            pred = readMatrice.nextInt();
            longueur = readMatrice.nextInt();// meme si la longeur n'est pas utilisée, il est préférable de la stocker pour éviter tout désagrément
            if (longueur<0)
            {
                arcsPositifs = false;
            }
            succ = readMatrice.nextInt();
            nombreTransitions++;
            noeudsAdjascence.get(succ).addPredecesseurs(pred);//ajout du predecesseur
            noeudsAdjascence.get(pred).getSuccesseurs().put(noeudsAdjascence.get(succ),1);// ajout du successeur et dde la valeur 1
            this.noeuds.get(succ).addPredecesseurs(pred);//ajout du predecesseur
            this.noeuds.get(pred).getSuccesseurs().put(noeuds.get(succ),longueur);// ajout du successeur et dde la valeur 1
        }

        matriceValeurs = creationTableauAffichage(noeuds);
        matriceAdjacence = creationTableauAffichage(noeudsAdjascence);
        readMatrice.close();
    }

    int[][] creationTableauAffichage(ArrayList<Noeud> noeuds2)
    {
        int tableau[][] = new int [noeuds2.size()][noeuds2.size()];

        for (int i = 0;i < noeuds2.size();i++)
        {
            for (int j = 0; j < noeuds2.size();j++)
            {
                if(noeuds2.get(i).getSuccesseurs().get(noeuds2.get(j))!= null)// à vérifier
                {
                    tableau[i][j] = noeuds2.get(i).getSuccesseurs().get(noeuds2.get(j));
                }
            }
        }
        return tableau;
    }

    public void affichageAdjascence() throws FileNotFoundException
    {
        //affichage n°ligne
        System.out.print("suc ");
        for (int cmpt1 = 0; cmpt1 < matriceAdjacence.length; cmpt1++) {
            System.out.print(cmpt1 + " ");
            if (cmpt1 < 10) {
                System.out.print(" ");
            }
        }
        System.out.println();
        System.out.print("pre");
        for (int cmpt2 = 0; cmpt2 < matriceAdjacence.length; cmpt2++) {
            System.out.print("__");
            if (cmpt2 < 10) {
                System.out.print("_");
            }
        }
        System.out.println();

        //affichage matrice
        for (int i = 0; i < matriceAdjacence.length; i++) {
            //affichage n°colonne
            System.out.print(i + " | ");


            for (int j = 0; j < matriceAdjacence.length; j++) {

                    System.out.print(matriceAdjacence[i][j] + " ");

                if (matriceAdjacence[i][j] < 10) {
                    System.out.print(" ");
                }

            }
            System.out.println();
        }

    }

    public void affichageValeurs() throws FileNotFoundException
    {
        //affichage n°ligne
        System.out.print("suc ");
        for (int cmpt1 = 0; cmpt1 < matriceValeurs.length; cmpt1++) {
            System.out.print(cmpt1 + " ");
            if (cmpt1 < 10) {
                System.out.print(" ");
            }
        }
        System.out.println();
        System.out.print("pre");
        for (int cmpt2 = 0; cmpt2 < matriceValeurs.length; cmpt2++) {
            System.out.print("__");
            if (cmpt2 < 10) {
                System.out.print("_");
            }
        }
        System.out.println();

        //affichage matrice
        for (int i = 0; i < matriceValeurs.length; i++) {
            //affichage n°colonne
            System.out.print(i + " | ");


            for (int j = 0; j < matriceValeurs.length; j++) {
                if (matriceAdjacence[i][j]==1)
                {
                    System.out.print(matriceValeurs[i][j] + " ");

                }
                else
                {
                    System.out.print("-" + " ");
                }
                if (matriceValeurs[i][j] < 10) {
                    System.out.print(" ");
                }

            }
            System.out.println();
        }

    }

}
