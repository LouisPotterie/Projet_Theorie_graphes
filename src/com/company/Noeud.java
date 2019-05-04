package com.company;

import java.util.ArrayList;
import java.lang.Cloneable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Noeud implements Cloneable {


    private int sommet;
    private Map<Noeud, Integer> successeurs = new HashMap<>();
    private ArrayList<Integer> predecesseurs = new ArrayList<>();

    public int getSommetChemin() {
        return sommetChemin;
    }

    public void setSommetChemin(int sommetChemin) {
        this.sommetChemin = sommetChemin;
    }

    private int sommetChemin;
    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Integer getDistance() {
        return distance;
    }

    private Integer distance;
    private int distancePlusCourt;


    public Noeud() {
    }

    public Noeud(int sommet) {
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
        } catch (CloneNotSupportedException cnse) {

            cnse.printStackTrace(System.err);
        }

        return o;
    }

    public void addSuccesseurs(Noeud noeud, int valeur) {
        this.successeurs.put(noeud, valeur);
    }

    public boolean aSuccesseurs() {
        if (successeurs.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean estPredecesseur(Noeud noeud)
    {
        if(successeurs.get(noeud)== null)
        {
            return false;
        }
        return true;
    }

    public void addPredecesseurs(int valeur) {
        this.predecesseurs.add(valeur);
    }

    public Map<Noeud, Integer> getSuccesseurs() {
        return successeurs;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Noeud)) return false;
        Noeud noeud = (Noeud) o;
        return getSommet() == noeud.getSommet();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getSommet());
    }
}
