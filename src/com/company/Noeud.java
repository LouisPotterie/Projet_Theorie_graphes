package com.company;

import java.util.ArrayList;

public class Noeud {


        private int sommet;
        private ArrayList<Integer> longueur = new ArrayList<>();
        private ArrayList<Integer> successeur = new ArrayList<>();
        private ArrayList<Integer> predecesseur = new ArrayList<>();

    public int getSommet() {
        return sommet;
    }

    public void setSommet(int sommet) {
        this.sommet = sommet;
    }

    public Noeud() {
    }

    public Noeud (int nSommet, int nLongueur, int nSuccesseur, int nPredecesseur) {
            this.sommet = nSommet;
            this.longueur.add(nLongueur);
            this.successeur.add(nSuccesseur);
            this.predecesseur.add(nPredecesseur);

        }

}
