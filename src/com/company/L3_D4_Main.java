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

        System.out.println("Hello World!");

        //int[][] matrice = creationMatrice();

        //System.out.println("\n\n");

        //int[][] matriceValeur = creationMatriceValeur();

        //enregistrementMatrice(matrice);

        //djistra();

        test2();

    }

    //
    public static void djistra() throws FileNotFoundException
    {
        Map<Integer, Noeud> cc = new HashMap<>();
        Graphe graphe = new Graphe(); // A initialiser avec tous les sommets

        try
        {
            graphe.initialisationMatriceValeur();
            graphe.affichageValeurs();
            ArrayList<Noeud> m = graphe.getNoeuds();
            int nombre_sommet = graphe.getNoeuds().size();
            int pi_etoile[] = new int[nombre_sommet]; // tableau des valeurs de Pi étoiles de taille nombre de sommets
            int pi[] = new int[nombre_sommet]; // tableau des valeurs de Pi  de taille nombre de sommets
            int tab_matrice[] = new int[nombre_sommet];

            Scanner kb = new Scanner(System.in);
            System.out.println("Quel est le sommet de départ ?"); // choix du sommet de départ

            int initiale = kb.nextInt();

            System.out.println("Le sommet choisi est :" + initiale);

            Noeud i = m.get(initiale);
            // on cherche le sommet que l'utilisateur a choisi
            cc.put(i.getSommet(), i); // Ajout du Noeud dans CC
            m.remove(i); // On retire le noeud de CC
            pi_etoile[initiale] = 0; // on initialise son pi_etoile à 0
            System.out.println("IN");

            System.out.println(cc.get(0).getSuccesseurs());
            if (i.aSuccesseurs())
            {


                for (Map.Entry<Noeud, Integer> entry : cc.get(0).getSuccesseurs().entrySet())
                {
                    Noeud cle = entry.getKey();
                    cle.setDistance(entry.getValue());

                }


            } else i.setDistance(1000);


            //si matrice_adjacence = 1


            /*if (matriceAdjacence[initiale][i.getSommet()]== 1)
            {
                pi[i.getSommet()] = matriceValeur[initiale][i.getSommet()]; // On initialise tous les autres PI
            }

            else
            {
                pi[i.getSommet()] = 100000;
            }
                    */


            int tampon_valeur = 1000; //initialisation arbitraire
            //Noeud tampon_sommet = new Noeud();

            int tampon_nom = -1;

            int sommet = initiale;
            for (int etape = 0; etape < graphe.getNoeuds().size(); etape++)
            {
                // Sommet le plus proche du nouveau sommet ajouté
                Noeud sommet_proche = distanceLaPlusCourte(m, graphe.getMatriceAdjacence(), sommet);
                Integer nouvelle_distance = null;
                if (sommet_proche != null)
                {
                    nouvelle_distance = cc.get(sommet).getSuccesseurs().get(sommet_proche) + cc.get(sommet).getDistance();
                }
                for (Noeud noeud : m)
                {
                    if (nouvelle_distance == null || noeud.getDistance() < nouvelle_distance)
                    {
                        sommet_proche = noeud;
                        nouvelle_distance = sommet_proche.getDistance();
                    }
                }

                Noeud tampon_sommet = sommet_proche;

                sommet = tampon_sommet.getSommet();
                System.out.println("TEST : " + tampon_sommet.getSommet());
                m.remove(tampon_sommet);


                cc.put(tampon_sommet.getSommet(), tampon_sommet);
                pi_etoile[tampon_sommet.getSommet()] = pi[tampon_sommet.getSommet()];

                System.out.println("Taille de m" + m.size());


                for (Noeud c : m)  // parcourt de l'ensemble M
                    if (tampon_sommet.estPredecesseur(c) && graphe.getMatriceAdjacence()[c.getSommet()][tampon_sommet.getSommet()] == 1)
                    {

                        c.setDistance(nouvelle_distance);
                        c.setSommetChemin(tampon_sommet.getSommet());
                    }
            }

            System.out.println("Choisir un sommet : \n");

            int choix = kb.nextInt();

            System.out.println("Vous avez choisi le sommet : " + choix + "\n");

            Noeud sommet_choisi = cc.get(choix);

            cheminLePlusCourt(initiale, sommet_choisi, cc);


            // tant que tab[sommet choisi cc]  =! tab [sommet initiale] return i = tab


        }
        catch (FileNotFoundException e)
        {
            System.out.println("Aucun fichier trouvé");
        }
    }


    public static void test() throws FileNotFoundException
    {
        Graphe test = new Graphe();

        try
        {
            test.initialisationMatriceValeur();
            test.initialisationMatriceAdjacence();

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



        cheminLePlusCourt(premier.getSommet(),quatre,cc);
    }


    public static Noeud distanceLaPlusCourte(ArrayList<Noeud> m, int[][] matriceAdjacence, int sommet)
    {
        Noeud tampon_sommet = null;
        int tampon_valeur = Integer.MAX_VALUE;

        for (Noeud n : m)
        {
            if (matriceAdjacence[sommet][n.getSommet()] == 1)
            {
                int noeudDistance = n.getDistance();
                if (noeudDistance < tampon_valeur)
                {
                    tampon_valeur = noeudDistance;
                    tampon_sommet = n;
                }
            }

        }
        return tampon_sommet;
    }

    public static void cheminLePlusCourt(int initiale, Noeud sommet_choisi, Map<Integer, Noeud> cc) // faudra passer le graph aussi
    {
        //Map<Noeud, Noeud> chemin= new HashMap<>();

        ArrayList<Noeud> chemin = new ArrayList<>();
        Map<Noeud,ArrayList> toutLesChemins = new HashMap<>();
        if (initiale == sommet_choisi.getSommet())
        {
            System.out.println("Le sommet choisi est le sommet initiale \n");
            //chemin.put(sommet_choisi,sommet_choisi);
            chemin.add(sommet_choisi);
            System.out.println("Le chemin est : ");

            /*
            for (Map.Entry<Noeud, Noeud> entry : chemin.entrySet())
            {
                Noeud cle = entry.getKey();
                System.out.print(cle.getSommet());

            }
            */

            for (Noeud affichage : chemin)
            {
                System.out.print(affichage.getSommet());
            }


        }

        Noeud a = null;

        a = sommet_choisi.clone();

        System.out.println("Le sommet clone a pour nom : " + a.getSommet());


        if (a.getSommetChemin() == initiale)
        {
            //System.out.println("Le chemin le plus court est " + sommet_choisi + "," + initiale);
            //chemin.put(sommet_choisi,sommet_choisi);
            //chemin.put(cc.get(initiale),cc.get(initiale));


            chemin.add(sommet_choisi);
            chemin.add(cc.get(initiale));
            System.out.println("Le chemin est : ");

            /*
            for (Map.Entry<Noeud, Noeud> entry : chemin.entrySet())
            {
                Noeud cle = entry.getKey();
                System.out.print(cle.getSommet());

            */

            for (Noeud affichage : chemin)
            {
                System.out.print(affichage.getSommet());
            }



        }

        if (a.getSommet() != initiale) {
            while (a.getSommet() != initiale) {
                System.out.println("Valeur mise dans la map de a :  " + a.getSommet());
                //chemin.put(a, a);
                chemin.add(a);
                a = cc.get(a.getSommetChemin()).clone();
                System.out.println("Nouvelle valeur de a : " + a.getSommet());


            }

            //chemin.put(cc.get(initiale), cc.get(initiale));

            chemin.add(cc.get(initiale));

            /*
            for (Map.Entry<Noeud, Noeud> entry : chemin.entrySet())
            {
                Noeud cle = entry.getKey();
                System.out.print(cle.getSommet());
            }
            */
            System.out.println("Le chemin est : ");
            for (Noeud affichage : chemin)
            {
                System.out.print(affichage.getSommet());
            }

            toutLesChemins.put(sommet_choisi,chemin);


        }
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

