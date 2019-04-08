package com.company;

import java.util.ArrayList;
import java.lang.Cloneable;
import java.util.HashMap;
import java.util.Map;

public class Noeud implements Cloneable{


        private int sommet;
        private Map<Noeud,Integer> successeurs = new HashMap<>();
        private ArrayList<Integer> predecesseurs = new ArrayList<>();



    public Noeud() {
    }

    public Noeud(int sommet)
    {
        this.sommet = sommet;
    }

    public void setSommet(int sommet) {
        this.sommet = sommet;
    }

    public int getSommet() {
        return sommet;
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

    public void addSuccesseurs(Noeud noeud,int valeur) {
        this.successeurs.put(noeud,valeur);
    }

    public void addPredecesseurs(int valeur) {
        this.predecesseurs.add(valeur);
    }

    public Map<Noeud, Integer> getSuccesseurs() {
        return successeurs;
    }
}
