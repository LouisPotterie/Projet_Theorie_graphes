package com.company;

import java.util.ArrayList;

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
}
