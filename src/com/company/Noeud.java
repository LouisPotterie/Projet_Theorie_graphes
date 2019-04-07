package com.company;

import java.util.ArrayList;
import java.lang.Cloneable;

public class Noeud implements Cloneable{


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


    public Noeud clone() {
        Noeud o = null;
        try {
            // On récupère l'instance à renvoyer par l'appel de la
            // méthode super.clone()
            o = (Noeud) super.clone();
        } catch(CloneNotSupportedException cnse) {

            cnse.printStackTrace(System.err);
        }

        return o;
    }

}
