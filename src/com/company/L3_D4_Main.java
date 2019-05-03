package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class L3_D4_Main
{

    //Leo
    public static void main(String[] args) throws FileNotFoundException
    {


//        int[][] matrice = creationMatrice();

  //      enregistrementMatrice(matrice);

        dijkstra();

        //test();

    }

    //
    public static void dijkstra() throws FileNotFoundException
    {
        ArrayList<Integer>  cc = new ArrayList<>();
        Map<Noeud, Noeud> predecesseurCheminCourt = new HashMap<>();
        Graphe graphe = new Graphe(); // A initialiser avec tous les sommets
        int nombreSommets = graphe.getNombreSommets();
        Integer[][] dijkstra = new Integer[nombreSommets][nombreSommets];
        try
        {
            graphe.affichageAdjascence();
            graphe.affichageValeurs();
            ArrayList<Noeud> m = graphe.getNoeuds();

            Scanner kb = new Scanner(System.in);
            System.out.println("Quel est le sommet de départ ?"); // choix du sommet de départ

            int initiale = kb.nextInt();

            System.out.println("Le sommet choisi est :" + initiale);

            Noeud i = m.get(initiale);
            cc.add(initiale); // Ajout du Noeud dans CC
            m.remove(i); // On retire le noeud de CC
            System.out.println("IN");

            //tout initialiser à l'infini
            for (int ligne = 0; ligne < nombreSommets; ligne++)
            {
                for (int colonne = 0; colonne < nombreSommets; colonne++)
                {
                    dijkstra[ligne][colonne] = Integer.MAX_VALUE;
                }

            }


            for (Map.Entry<Noeud, Integer> entry : i.getSuccesseurs().entrySet())
            {
                Noeud cle = entry.getKey();
                cle.setDistance(entry.getValue());
                dijkstra[0][cle.getSommet()] = (entry.getValue());
            }

            dijkstra[0][initiale] = 0;


            for (int etape = 1; etape < nombreSommets; etape++)
            {
                dijkstra[etape] = dijkstra[etape - 1].clone();
                for (int ligne = 0; ligne < nombreSommets; ligne++)
                {

                    System.out.println( Arrays.toString(dijkstra[ligne]));
                }

                System.out.println("--");

                Noeud sommetProche= distanceLaPlusCourte(m, dijkstra, etape-1);
                if (sommetProche!=null)
                {
                    m.remove(sommetProche);
                    cc.add(sommetProche.getSommet());
                }
                else
                {
                    continue;
                }

//                sommetProche.setDistance(sommetProche.getDistance());

                for (Noeud noeud : m)  // parcourt de l'ensemble M
                    if (sommetProche.estPredecesseur(noeud) && graphe.getMatriceAdjacence()[sommetProche.getSommet()][noeud.getSommet()] == 1)
                    {
                        int distance_sommetproche_vers_noeud = sommetProche.getSuccesseurs().get(noeud);
                        int nouvelleDistance;
                        if (dijkstra[etape][sommetProche.getSommet()]!=Integer.MAX_VALUE)
                        {
                                nouvelleDistance= dijkstra[etape][sommetProche.getSommet()]+distance_sommetproche_vers_noeud;
                        }

                        else {
                            nouvelleDistance = Integer.MAX_VALUE;
                        }
                        //int nouvelleDistance = sommetProche.getDistance() + distance_sommetproche_vers_noeud;
                        if (nouvelleDistance <dijkstra[etape][noeud.getSommet()])
                        {
                            dijkstra[etape][noeud.getSommet()] = nouvelleDistance;
                            predecesseurCheminCourt.put(noeud,sommetProche);
                        }
                    }



            }

            for (int ligne = 0 ; ligne < nombreSommets; ligne++)
            {
                for(int sommet = 0; sommet < nombreSommets;sommet++)
                {
                    if (cc.contains(sommet)&& ligne>= cc.indexOf(sommet)&& ligne !=0 && ligne !=nombreSommets-1 )
                    {
                        System.out.print(". ,");
                    }
                    else {
                        System.out.print(dijkstra[ligne][sommet] +" ,");
                    }
                }
                System.out.println();
            }

        }
        catch (FileNotFoundException e)
        {
            System.out.println("Aucun fichier trouvé");
        }
    }




    public static void test() throws FileNotFoundException
    {
        try
        {
            Graphe test = new Graphe();

            for (Noeud n : test.getNoeuds())
            {
                System.out.println("Nom noeud : " + n.getSommet() + " Sucesseurs " + n.getSuccesseurs().get(n) + "Predecesseur" + n.getSommetChemin() + "\n");
            }

        }
        catch (FileNotFoundException e)
        {
            System.out.println("Erreur");
        }
    }

    public static void test2() throws FileNotFoundException
    {
        int nom_1 = 1;
        int nom_2 = 2;
        int nom_3 = 3;
        int nom_4 = 4;
        Noeud premier = new Noeud(nom_1);
        Noeud deux = new Noeud(nom_2);
        Noeud trois = new Noeud(nom_3);
        Noeud quatre = new Noeud(nom_4);

        quatre.setSommetChemin(nom_3);
        trois.setSommetChemin(nom_2);
        deux.setSommetChemin(nom_1);

        Map<Integer, Noeud> cc = new HashMap<>();

        cc.put(premier.getSommet(), premier);
        cc.put(deux.getSommet(), deux);
        cc.put(trois.getSommet(), trois);
        cc.put(quatre.getSommet(), quatre);



      //  cheminLePlusCourt(premier.getSommet(),quatre,cc);
    }


    public static Noeud distanceLaPlusCourte(ArrayList<Noeud> m, Integer[][] dijkstra, int etape)
    {
        Noeud tampon_sommet = null;
        int tampon_valeur = Integer.MAX_VALUE;

        for (Noeud n : m)
        {
                if ( dijkstra[etape][n.getSommet()]<tampon_valeur)
                {
                    tampon_valeur = dijkstra[etape][n.getSommet()];
                    tampon_sommet = n;
                }


        }
        return tampon_sommet;
    }


    public static Noeud predecesseur(Map<Integer, Noeud> cc, int pred)
    {
        return cc.get(pred);
    }


    static int[][] creationMatrice() throws FileNotFoundException
    {
        File fileMatrice = new File("L3-D4-1.txt");
        Scanner readMatrice = new Scanner(fileMatrice);

        int nombreSommets = readMatrice.nextInt();
        int[][] matrice = new int[nombreSommets][nombreSommets];
        int succ;
        int prec;
        int valeur;

        while (readMatrice.hasNext())
        {
            succ = readMatrice.nextInt();
            valeur = readMatrice.nextInt();
            prec = readMatrice.nextInt();
            matrice[succ][prec] = 1;
        }

        affichage(matrice);

        readMatrice.close();
        return matrice;
    }

    static void affichage(int[][] matrice)
    {
        //affichage n°ligne
        System.out.print("suc ");
        for (int cmpt1 = 0; cmpt1 < matrice.length; cmpt1++)
        {
            System.out.print(cmpt1 + " ");
            if (cmpt1 < 10)
            {
                System.out.print(" ");
            }
        }
        System.out.println();
        System.out.print("pre");
        for (int cmpt2 = 0; cmpt2 < matrice.length; cmpt2++)
        {
            System.out.print("__");
            if (cmpt2 < 10)
            {
                System.out.print("_");
            }
        }
        System.out.println();


        //affichage matrice
        for (int i = 0; i < matrice.length; i++)
        {
            //affichage n°colonne
            System.out.print(i + " | ");


            for (int j = 0; j < matrice.length; j++)
            {

                System.out.print(matrice[i][j] + " ");
                if (matrice[i][j] < 10)
                {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    static void enregistrementMatrice(int[][] matrice) throws FileNotFoundException
    {
        final int NUMERO_GRAPHE = 1;
        final int SOMMET_DEPART = 1;

        int taille = matrice.length - 1;

        PrintWriter enregistrement = new PrintWriter("L3-D4-trace" + NUMERO_GRAPHE + "_" + SOMMET_DEPART + ".txt");

        //affichage n°ligne
        enregistrement.print("suc ");
        for (int cmpt1 = 0; cmpt1 < matrice.length; cmpt1++)
        {
            enregistrement.print(cmpt1 + " ");
            if (cmpt1 < 10)
            {
                enregistrement.print(" ");
            }
        }
        enregistrement.println();
        enregistrement.print("pre");
        for (int cmpt2 = 0; cmpt2 < matrice.length; cmpt2++)
        {
            enregistrement.print("__");
            if (cmpt2 < 10)
            {
                enregistrement.print("_");
            }
        }
        enregistrement.println();


        //affichage matrice
        for (int i = 0; i < matrice[taille].length; i++)
        {
            //affichage n°colonne
            enregistrement.print(i + " | ");


            for (int j = 0; j < matrice.length; j++)
            {
                enregistrement.print(matrice[i][j] + " ");
                if (matrice[i][j] < 10)
                {
                    enregistrement.print(" ");
                }
            }
            enregistrement.println();


        }
        enregistrement.close();
    }

    static int[][] creationMatriceValeur() throws FileNotFoundException
    {
        File fileMatrice = new File("L3-D4-1.txt");
        Scanner readMatrice = new Scanner(fileMatrice);

        int nombreSommets = readMatrice.nextInt();
        int[][] matrice = new int[nombreSommets][nombreSommets];
        int succ;
        int prec;
        int valeur;

        while (readMatrice.hasNext())
        {
            succ = readMatrice.nextInt();
            valeur = readMatrice.nextInt();
            prec = readMatrice.nextInt();
            matrice[succ][prec] = valeur;
        }

        affichage(matrice);

        readMatrice.close();
        return matrice;
    }


}

