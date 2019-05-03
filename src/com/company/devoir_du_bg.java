package com.company;

import java.util.Scanner;

public class devoir_du_bg {

    /**
     * Methode qui demande à la personne de choisir quelle graphe elle souhaite ouvrir parmi les 10 disponibles
     * avec une saisie sécurisée contre les caracteres
     * @return le fichier que l'on souhaite ouvrir
     */
    public String choixFichier()
    {
        int saisie;
        System.out.println("Choisissez un graphe entre 1 et 10 :");
        do {
            saisie = inputWithOnlyInt();
        }while (saisie<1 || saisie>10);

        return "L3-D4-"+saisie+".txt";
    }


    /**
     * Methode permettant le choix du sommet de départ pour les algorithmes de dijkstra et/ou Bellman
     * @return le sommet de départ
     */
    public int choixSommet()
    {
        int saisie;
        System.out.println("Choisissez le sommet de départ entre 0 et "+ graphe.getNombreSommets()+" :");
        do {
            saisie = inputWithOnlyInt();
        }while (saisie<0 || saisie>graphe.getNombreSommets());

        return saisie;
    }

    /**
     * Methode qui s'assure que la saisie est bien un entier
     * @return un entier saisie
     */
    public int inputWithOnlyInt()
    {
        Scanner kb = new Scanner(System.in);

        System.out.print("-> ");
        while (!kb.hasNextInt())  //si la selection est différente d'un entier on coninue de demander une saisie
        {
            System.out.print("Make a correct selection please\n-> ");
            kb.next();
        }

        return kb.nextInt();
    }
}
