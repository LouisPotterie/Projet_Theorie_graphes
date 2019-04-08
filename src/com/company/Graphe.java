package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Graphe {

    private ArrayList<Noeud> noeuds;

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
        File fileMatrice = new File("L3-D4-1.txt");
        Scanner readMatrice = new Scanner(fileMatrice);


        int taille = readMatrice.nextInt();
        int succ;
        int pred;
        int longueur;

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

    void creationTableauAffichage()
    {
        int tableau[][] = new int [noeuds.size()][noeuds.size()];

        for (int i = 0;i < noeuds.size();i++)
        {
            Noeud noeud = noeuds.get(i);
            for (int j = 0; j < noeuds.size();j++)
            {
                if()

            }
        }


    }


}
