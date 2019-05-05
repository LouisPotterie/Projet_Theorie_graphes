package com.company;

import java.util.ArrayList;
import java.lang.Cloneable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class L3_D4_Noeud implements Cloneable {


    private int sommet;
    private Map<L3_D4_Noeud, Integer> successeurs = new HashMap<>();
    private ArrayList<Integer> predecesseurs = new ArrayList<>();
    public L3_D4_Noeud() { }
    public L3_D4_Noeud(int sommet) {
        this.sommet = sommet;
    }
    public int getSommet() {
        return sommet;
    }

    /**
     * Clonage d'un noeud
     * @return noeud cloné
     */
    public L3_D4_Noeud clone() {
        L3_D4_Noeud o = null;
        try {
            // On récupère l'instance à renvoyer par l'appel de la
            // méthode super.clone()
            o = (L3_D4_Noeud) super.clone();
        } catch (CloneNotSupportedException cnse) {

            cnse.printStackTrace(System.err);
        }
        return o;
    }

    /**
     * Rajout d'un successeur dans le noeud courant
     * @param noeud successeur à rajouter
     * @param valeur longueur du noeud courant à son successeur
     */
    public void addSuccesseurs(L3_D4_Noeud noeud, int valeur) {
        this.successeurs.put(noeud, valeur);
    }

    /**
     * Vérifie si le noeud en paramètre est un précedesseur de l'object noeud
     * @param noeud
     * @return un boolean, vrai si le noeud en paramètre est prédecesseur, faux sinon
     */
    public boolean estPredecesseur(L3_D4_Noeud noeud)
    {
        if(successeurs.get(noeud)== null)
        {
            return false;
        }
        return true;
    }

    /**
     * Rajout d'un predecesseur dans le noeud object (this)
     * @param valeur sommet à rajouter
     */
    public void addPredecesseurs(int valeur) {
        this.predecesseurs.add(valeur);
    }

    /**
     * Retourne la map des successeurs (clé est le successeur, la valeur est la longueur entre le noeud et le successeur
     * @return
     */
    public Map<L3_D4_Noeud, Integer> getSuccesseurs() {
        return successeurs;
    }

    /**
     * Deux noeuds sont considérés les mêmes s'ils ont le même nom du sommet (utilisation lors des clonages)
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof L3_D4_Noeud)) return false;
        L3_D4_Noeud noeud = (L3_D4_Noeud) o;
        return getSommet() == noeud.getSommet();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getSommet());
    }
}
