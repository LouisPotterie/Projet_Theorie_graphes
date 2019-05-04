package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Graphe {

    private ArrayList<Noeud> noeuds;

    private ArrayList<Noeud> bellmanNoeuds;
    private ArrayList<Integer> bellmanLongueur;
    private ArrayList<Integer> bellmanNombreIteration;

    public Graphe()
    {
        noeuds = new ArrayList<>();
    }

    public ArrayList<Noeud> getNoeuds() {
        return noeuds;
    }

    public void setNoeuds(ArrayList<Noeud> noeuds) {
        this.noeuds = noeuds;
    }

    void initialisationMatriceValeur() throws FileNotFoundException {
        File fileMatrice = new File("L3-D4-2.txt");
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
            this.noeuds.get(pred).getSuccesseurs().put(noeuds.get(succ),1);// ajout du successeur et de la valeur 1
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
                if(noeuds.get(i).getSuccesseurs().get(noeuds.get(j))!= null)
                {
                    tableau[i][j] = noeuds.get(i).getSuccesseurs().get(noeuds.get(j));
                }
            }
        }
        return tableau;
    }

    void affichage() {

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


    public void bellman()
    {
        //ArrayList <HashMap> parcoursTotal = new ArrayList<>();

        ArrayList<ArrayList<Noeud>> parcoursNoeuds = new ArrayList<>();
        ArrayList<ArrayList<Integer>> parcoursLongueur = new ArrayList<>();
        ArrayList<Noeud> Predecesseur = new ArrayList<>();

        final int NOEUD_DEPART = 0; // a supprimer
        int nombreIteration = 2;

        for (int i = 0; i<nombreIteration; i++)
        {
            parcoursNoeuds.add(new ArrayList<>());
            parcoursLongueur.add(new ArrayList<>());

            parcoursNoeuds.get(i).add(noeuds.get(0));
            parcoursLongueur.get(i).add(0);


            for (int j = 0; j< noeuds.size();j++)
            {

                if (noeuds.get(i).getSuccesseurs().containsKey(noeuds.get(j)))
                {
                    if (i == 0)
                    {
                        parcoursNoeuds.get(i).add(noeuds.get(i));
                        parcoursLongueur.get(i).add(noeuds.get(i).getSuccesseurs().get(noeuds.get(j)));
                    }
                    else
                    {
                        parcoursNoeuds.get(i).add(noeuds.get(i));
                        parcoursLongueur.get(i).add(noeuds.get(i).getSuccesseurs().get(noeuds.get(j)) /*+Predecesseur*/);
                    }
                }
            }
        }
        System.out.println("parcoursNoeuds : "+parcoursNoeuds);
        System.out.println("parcoursLongueur : "+parcoursLongueur);
    }

    public void bellman2()
    {
        bellmanLongueur = new ArrayList<>();
        bellmanNoeuds = new ArrayList<>();
        bellmanNombreIteration = new ArrayList<>();

        bellmanLongueur.add(0);
        bellmanNoeuds.add(noeuds.get(0));
        bellmanNombreIteration.add(1);

        bellmanNombreIteration = new ArrayList<>();

        bellmanIteration(0,noeuds.get(0),5 );

        System.out.println(bellmanLongueur.size()+" "+bellmanNoeuds.size()+" "+bellmanNombreIteration.size());
        for(int i = 0; i<bellmanNoeuds.size();i++)
        {

            System.out.println("noeuds : "+bellmanNoeuds.get(i)+ "   longueur : "+ bellmanLongueur.get(i)+"     nombre itération"+bellmanNombreIteration.get(i));

        }

        for (int i = 0; i<bellmanNombreIteration.size(); i++)
        {
            System.out.println("iteration : " + bellmanNombreIteration.get(i));

            for (int j = 0; j<bellmanNoeuds.size(); j++)
            {
                System.out.println("noeuds  : " + bellmanNoeuds.get(j));


            }
        }
    }
    public void bellmanIteration(int longueur, Noeud noeud, int nombreIteration)
    {
        if(nombreIteration <= 0)//condition si nbr iteration == 0
        {
            return;
        }

        for (int i = 0; i<noeuds.size(); i++)
        {
            if(noeud.getSuccesseurs().containsKey(noeuds.get(i)))
            {
                bellmanLongueur.add(longueur + noeud.getSuccesseurs().get(noeuds.get(i)));
                bellmanNoeuds.add(noeuds.get(i));
                bellmanNombreIteration.add(nombreIteration);

                bellmanIteration(longueur + noeud.getSuccesseurs().get(noeuds.get(i)),noeuds.get(i),nombreIteration-1);

            }
        }

        if(false)//condition si 2 lignes identiques
        {
            return;
        }
    }



}
