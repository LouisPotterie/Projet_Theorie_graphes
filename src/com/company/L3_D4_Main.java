package com.company;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;


public class L3_D4_Main
{

    public static void main(String[] args)
    {
        // démarrage du programme avec son menu
        menu();
    }

    /**
     * Algorithme de Dijkstra, chemin le plus court d'un sommet de départ choisi vers tous les autres sommets du graphe
     * @param numFichier Numéro du graphe choisi parmi les 10 graphes de test
     * @param graphe L3_D4_Graphe avec toutes ses informations
     * @param sommetDepart Sommet de départ choisi
     * @throws FileNotFoundException exception si aucun fichier n'est trouvé
     */
    public static void dijkstra(int numFichier, L3_D4_Graphe graphe, int sommetDepart) throws FileNotFoundException
    {
        //Unicode pour l'affichage des éléments du tableau de dijkstra
        final String UNICODE_POINT = "\u2022";
        final String UNICODE_INFINITY = "\u221E";
        //Liste cc, correspondant à l'ensemble CC vu en cours
        ArrayList<Integer> cc = new ArrayList<>();
        //predecesseurCheminCourt est une map ; la clé est un noeud du graphe, la valeur est le prédecesseur dans le chemin le plus court du sommet de départ au noeud.
        Map<L3_D4_Noeud, L3_D4_Noeud> predecesseurCheminCourt = new HashMap<>();
        int nombreSommets = graphe.getNombreSommets();
        //tableau de Dijkstra, chaque ligne correspondant à une étape de l'algorithme de Dijkstra
        Integer[][] dijkstra = new Integer[nombreSommets][nombreSommets];
        //variables necessaires pour un affichage propre
        int espaceOccupe;
        int espacement;
        String filename = "L3-D4-trace" + numFichier + "_" + sommetDepart + "_dijkstra.txt";
        PrintWriter enregistrement = new PrintWriter(filename);
        try
        {
            enregistrement.println(filename);
            enregistrementEntete(enregistrement, graphe);
            ArrayList<L3_D4_Noeud> m = cloneList(graphe.getNoeuds());
            enregistrement.println("Le sommet choisi est :" + sommetDepart);
            L3_D4_Noeud sommetChoisi = m.get(sommetDepart);
            cc.add(sommetDepart); // Ajout du L3_D4_Noeud dans CC
            m.remove(sommetChoisi); // On retire le noeud de M
            //tout initialiser à l'infini
            for (int ligne = 0; ligne < nombreSommets; ligne++)
            {
                for (int colonne = 0; colonne < nombreSommets; colonne++)
                {
                    dijkstra[ligne][colonne] = Integer.MAX_VALUE;
                }
            }
            //On récupère les distances du sommet choisi vers tous ses successeurs dans la première ligne du tableau de DIjkstra
            for (Map.Entry<L3_D4_Noeud, Integer> entry : sommetChoisi.getSuccesseurs().entrySet())
            {
                L3_D4_Noeud cle = entry.getKey();
                dijkstra[0][cle.getSommet()] = (entry.getValue());
                //initialement, tous les successeurs du sommet de départ ont comme prédecesseur dans le chemin le plus court le sommet de départ
                predecesseurCheminCourt.put(cle, sommetChoisi);
            }

            dijkstra[0][sommetDepart] = 0;
            for (int etape = 1; etape < nombreSommets; etape++)
            {
                dijkstra[etape] = dijkstra[etape - 1].clone();
                //on prend le sommet du pi le plus court (dans M)
                L3_D4_Noeud sommetProche = distanceLaPlusCourte(m, dijkstra, etape - 1);
                if (sommetProche != null)
                {
                    m.remove(sommetProche);
                    cc.add(sommetProche.getSommet());
                } else
                {
                    continue;
                }

                for (L3_D4_Noeud noeud : m)  // parcourt de l'ensemble M
                    if (sommetProche.estPredecesseur(noeud) && graphe.getMatriceAdjacence()[sommetProche.getSommet()][noeud.getSommet()] == 1)
                    {
                        // distance entre le le sommet avec le pi minimum et les noeuds de M
                        int distanceSommetProcheVersNoeud = sommetProche.getSuccesseurs().get(noeud);
                        int nouvelleDistance;
                        //Vérifier qu'à la ligne d'avant, le pi[sommetProche] n'est pas à égal à l'infini, sinon overflow et nouvelleDistance deviendrait négatif
                        if (dijkstra[etape][sommetProche.getSommet()] != Integer.MAX_VALUE)
                        {
                            nouvelleDistance = dijkstra[etape][sommetProche.getSommet()] + distanceSommetProcheVersNoeud;
                        } else
                        {
                            nouvelleDistance = Integer.MAX_VALUE;
                        }
                        /*si changement de pi, c'est à dire que la nouvelle distance calculée est inférieure à son ancien pi,
                        on modifie le pi, et le prédecesseur "optimal" dans le chemin le plus du L3_D4_Noeud noeud
                         */
                        if (nouvelleDistance < dijkstra[etape][noeud.getSommet()])
                        {
                            dijkstra[etape][noeud.getSommet()] = nouvelleDistance;
                            predecesseurCheminCourt.put(noeud, sommetProche);
                        }
                    }
            }
            //affichage du tableau de Dijkstra
            System.out.println("Algorithme de Dijkstra:");
            enregistrement.println("Algorithme de Dijkstra:");
            for (int compteur = 0; compteur < nombreSommets; compteur++)
            {
                System.out.print("_");
                enregistrement.print("_");
            }
            for (int compteur = 0; compteur < nombreSommets; compteur++)
            {
                System.out.print("_____");
                enregistrement.print("_____");
            }
            enregistrement.println();
            System.out.print("\nsommets ");
            enregistrement.print("\nsommets ");

            for (int compteur = 8; compteur < nombreSommets; compteur++)
            {
                System.out.print(" ");
                enregistrement.print(" ");
            }
            for (int compteur = 0; compteur < nombreSommets; compteur++)
            {
                System.out.print("|    ");
                enregistrement.print("|    ");
            }
            System.out.println("|");
            enregistrement.println("|");
            System.out.print("cc");
            enregistrement.print("cc");
            for (int compteur = 2; compteur < nombreSommets; compteur++)
            {
                System.out.print(" ");
                enregistrement.print(" ");
            }
            System.out.print("|");
            enregistrement.print("|");
            for (int compteur = 0; compteur < nombreSommets; compteur++)
            {
                System.out.print(" " + compteur + "  |");
                enregistrement.print(" " + compteur + "  |");
            }
            System.out.println();
            enregistrement.println();
            for (int compteur = 0; compteur < nombreSommets; compteur++)
            {
                System.out.print("_");
                enregistrement.print("_");
            }
            for (int compteur = 0; compteur < nombreSommets; compteur++)
            {
                System.out.print("|____");
                enregistrement.print("|____");
            }
            System.out.println("|");
            enregistrement.println("|");

            for (int ligne = 0; ligne < nombreSommets; ligne++)
            {
                espaceOccupe = 0;
                espacement = 0;
                for (int compteur = 0; compteur < Math.min(ligne + 1, cc.size()); compteur++)
                {
                    System.out.print(cc.get(compteur));
                    enregistrement.print(cc.get(compteur));
                    espaceOccupe++;
                }
                while (espaceOccupe + espacement < nombreSommets)
                {
                    System.out.print(" ");
                    enregistrement.print(" ");
                    espacement++;
                }

                System.out.print("| ");
                enregistrement.print("| ");
                for (int sommet = 0; sommet < nombreSommets; sommet++)
                {
                    if (cc.contains(sommet) && ligne >= cc.indexOf(sommet) && ligne != 0 && ligne != nombreSommets - 1)
                    {
                        System.out.print(".  | ");
                        enregistrement.print(".  | ");
                    } else if (dijkstra[ligne][sommet] == Integer.MAX_VALUE)
                    {
                        System.out.print(UNICODE_INFINITY + "  | ");
                        enregistrement.print(UNICODE_INFINITY + "  | ");
                    } else
                    {
                        if (dijkstra[ligne][sommet] >= 10)
                        {
                            System.out.print(dijkstra[ligne][sommet] + " | ");
                            enregistrement.print(dijkstra[ligne][sommet] + " | ");
                        } else
                        {
                            System.out.print(dijkstra[ligne][sommet] + "  | ");
                            enregistrement.print(dijkstra[ligne][sommet] + "  | ");
                        }
                    }
                }
                System.out.println();
                enregistrement.println();
            }
            for (int compteur = 0; compteur < nombreSommets; compteur++)
            {
                System.out.print("_");
                enregistrement.print("_");
            }
            for (int compteur = 0; compteur < nombreSommets; compteur++)
            {
                System.out.print("|____");
                enregistrement.print("|____");
            }
            System.out.println("|\n");
            enregistrement.println("|\n");
            L3_D4_Noeud sommet_choisi = new L3_D4_Noeud();
            for (L3_D4_Noeud j : graphe.getNoeuds())
            {
                sommet_choisi = j.clone();
                cheminLePlusCourt(sommetDepart, sommet_choisi, cc, predecesseurCheminCourt, graphe);
            }

            for (L3_D4_Noeud sommetArrivee : graphe.getNoeuds())
            {
                ArrayList<L3_D4_Noeud> chemin = graphe.getToutLesChemins().get(sommetArrivee);
                if (chemin != null)
                {
                    System.out.print("Le chemin le plus court du sommet " + sommetDepart + " à " + sommetArrivee.getSommet() + ", de longueur " +dijkstra[nombreSommets - 1][sommetArrivee.getSommet()]+ " est ");
                    enregistrement.print("Le chemin le plus court du sommet " + sommetDepart + " à " + sommetArrivee.getSommet() + ", de longueur " +dijkstra[nombreSommets - 1][sommetArrivee.getSommet()]+ " est ");
                    for (L3_D4_Noeud n : chemin)
                    {
                        System.out.print(n.getSommet());
                        enregistrement.print(n.getSommet());
                    }
                    System.out.println(".");
                    enregistrement.println(".");
                } else
                {
                    System.out.println("Le chemin pour aller du sommet " + sommetDepart + " à " + sommetArrivee.getSommet() + " est impossible.");
                    enregistrement.println("Le chemin pour aller du sommet " + sommetDepart + " à " + sommetArrivee.getSommet() + " est impossible.");
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Aucun fichier trouvé");
        }
        finally
        {
            enregistrement.close();
        }

    }

    /**
     * Méthode permettant de renvoyer une ArrayList venant du clonage d'une ArrayList passée en param
     * @param noeuds ArrayList à cloner
     * @return l'ArrayList clone
     */
    public static ArrayList<L3_D4_Noeud> cloneList(ArrayList<L3_D4_Noeud> noeuds)
    {
        ArrayList<L3_D4_Noeud> clone = new ArrayList<>(noeuds.size());
        for (L3_D4_Noeud item : noeuds) clone.add(item.clone());
        return clone;
    }

    /**
     * Trouver le sommet le plus proche du sommet de départ (pi minimum) parmi l'ensemble M
     * @param m Liste des noeud de M
     * @param dijkstra tableau de l'algorithme de Dijkstra
     * @param etape ligne ou étape de l'algo de Dijkstra
     * @return L3_D4_Noeud qui a le pi minimum à l'étape actuelle
     */
    public static L3_D4_Noeud distanceLaPlusCourte(ArrayList<L3_D4_Noeud> m, Integer[][] dijkstra, int etape)
    {
        L3_D4_Noeud tampon_sommet = null;
        int tampon_valeur = Integer.MAX_VALUE;

        for (L3_D4_Noeud n : m)
        {
            if (dijkstra[etape][n.getSommet()] < tampon_valeur)
            {
                tampon_valeur = dijkstra[etape][n.getSommet()];
                tampon_sommet = n;
            }
        }
        return tampon_sommet;
    }

    /**
     *  Méthode permettant de trouver le chemin le plus court entre un sommet et le sommet initial du L3_D4_Graphe
     * @param initiale nom du noeud initiale
     * @param sommetArrivee L3_D4_Noeud vers lequel on cherche le chemin le plus court
     * @param cc ArrayList cc ensemble des noeuds placés suite à l'algo de dijkstra
     * @param predecesseurCheminCourt Map <L3_D4_Noeud,L3_D4_Noeud> qui contient en Key un noeud et en value son predecesseur avec le moins de distance
     * @param graphe Instance de la classe L3_D4_Graphe qui ici nous est utile pour sa map ToutLesChemins<L3_D4_Noeud,ArrayList>
     */

    public static void cheminLePlusCourt(int initiale, L3_D4_Noeud sommetArrivee, ArrayList<Integer> cc, Map<L3_D4_Noeud, L3_D4_Noeud> predecesseurCheminCourt, L3_D4_Graphe graphe)
    {
        ArrayList<L3_D4_Noeud> chemin = new ArrayList<>(); // On va placer les noeuds du chemin le plus court dans cette Arraylist

        L3_D4_Noeud a = null; // L3_D4_Noeud tampon
        L3_D4_Noeud b = null; // L3_D4_Noeud tampon
        a = sommetArrivee.clone(); // On clone dans a le sommet cible
        // Un sommet isolé ne possède pas de prédécesseurs, et n'est donc pas contenu dans les clés de la map
        // Cela est aussi vrai pour le sommet initial
        if (predecesseurCheminCourt.containsKey(a) || a.getSommet() == initiale) // Si la map des predecesseurs contient le sommet tampon a en Key ou que a est le sommet initiale du graphe
        {
            while (a.getSommet() != initiale) // tant que le numero du noeud tampon a est different que le numéro du noeud initial
            {
                //chemin.put(a, a);
                chemin.add(a); // ajoute le noeud tampon a
                a = predecesseurCheminCourt.get(a).clone(); // on clone dans a le predecesseur du sommet a courant
            }
            for (L3_D4_Noeud j : graphe.getNoeuds())
            {
                if (j.getSommet() == cc.get(0)) // si le noeud courant j est égale au noeud intiale
                {
                    b = j.clone();
                }
            }
            chemin.add(b); // Ajout du sommet initiale
            Collections.reverse(chemin); // On inverse l'ordre pour avoir un chemin du sommet initiale vers le sommet d'arrivée
            graphe.getToutLesChemins().put(sommetArrivee, chemin); // On stock l'arrayList dans une map avec comme Key le sommet finale (le sommet initiale est connus puisque choisi)
        }
    }

    /**
     * Algorithme de Bellman
     * @param numFichier Numéro du graphe choisi parmi les 10 graphes de test
     * @param graphe L3_D4_Graphe avec toutes ses informations
     * @param sommet_depart Sommet de départ choisi
     * @throws FileNotFoundException exception si aucun fichier n'est trouvé
     */
    public static void bellman(int numFichier, L3_D4_Graphe graphe, int sommet_depart) throws FileNotFoundException
    {
        //initialisation d'un graph sous la forme d'un tableau de transition
        String filename = "L3-D4-trace" + numFichier + "_" + sommet_depart + "_bellman.txt";
        PrintWriter enregistrement = new PrintWriter(filename);
        enregistrement.println(filename);
        enregistrementEntete(enregistrement, graphe);
        enregistrement.println("\nLe sommet choisi est: " + sommet_depart);
        enregistrement.println("Algorithme de Bellman");
        System.out.println("Algorithme de Bellman");
        int nombreSommets = graphe.getNombreSommets();
        int tableau_transitions[][] = new int[graphe.getNombreTransitions()][3];
        int transition = 0;
        for (L3_D4_Noeud noeud : graphe.getNoeuds())
        {
            for (Map.Entry<L3_D4_Noeud, Integer> entry : noeud.getSuccesseurs().entrySet())
            {// prec - succ - valeur de transition
                tableau_transitions[transition] = new int[]{noeud.getSommet(), entry.getKey().getSommet(), entry.getValue()};
                transition++;
            }
        }

        //le nombre de transition du graph
        int nombreTransitions= tableau_transitions.length;
        int tableau_de_k[][] = new int[nombreSommets + 1][nombreSommets];

        /*
            Tableau des predecesseurs

            meme dimensions que le tableau des k
            il permet de recuperer les prédecesseurs permettant de dresser le chemin le plus cours
         */
        int tableau_de_predecesseur[][] = new int[nombreSommets + 1][nombreSommets];

        // Le MAX_SIZE permet de remplacer le signe infini dans le tableau des k
        int MAX_SIZE = Integer.MAX_VALUE;

        // On initialise le tableau des k avec la variable MAX_SIZE (inifini)
        for (int i = 0; i < nombreSommets + 1; i++)
        {
            for (int n = 0; n < nombreSommets; n++)
            {
                tableau_de_k[i][n] = MAX_SIZE;
            }
        }

        //initialisation de la colonne du sommet de départ
        for (int j = 0; j < nombreSommets + 1; j++)
        {
            tableau_de_k[j][sommet_depart] = 0;
            tableau_de_predecesseur[j][sommet_depart] = sommet_depart;
        }

        //initialisation des différentes variables
        int prec = 0;
        int succ = 0;
        int prec_v2 = 0;
        int succ_v2 = 0;
        int value = 0;
        int v = 0;
        int compteur = 0;
        int new_k = MAX_SIZE;

        for (int k = 1; k < nombreSommets + 1; k++)
        {
            for (int o = 0; o < nombreTransitions; o++)
            {
                prec = tableau_transitions[o][0]; //on récup le prec grace au graph
                succ = tableau_transitions[o][1];
                value = tableau_transitions[o][2];
                prec_v2 = tableau_de_k[k - 1][prec]; //on recup le poids pour le prec dans le tableau k
                succ_v2 = tableau_de_k[k - 1][succ];

                if (prec_v2 == MAX_SIZE)
                    v = succ_v2;
                else
                    v = Math.min(succ_v2, (prec_v2 + value)); //on cherche le min

                if (v < tableau_de_k[k][succ])
                {
                    tableau_de_k[k][succ] = v; // on modifie la valeur dans le tableau k pour avoir le plus petit chemin
                    if (v != tableau_de_k[k - 1][succ])
                    {
                        tableau_de_predecesseur[k][succ] = prec;
                    }
                }
            }

            for (int m = 0; m < nombreSommets; m++)
            {
                if (tableau_de_k[k - 1][m] == tableau_de_k[k][m])
                {
                    compteur++;
                }
            }
            if (compteur == nombreSommets)
            {
                new_k = Math.min(new_k,k);
            }
            compteur = 0;
        }

        if (new_k == MAX_SIZE){
            new_k = nombreSommets;
        }

        int absorbant = 0;
        int check = 0;

        for (int i = 0; i < nombreTransitions; i++)
        {
            check = Math.min(check, tableau_transitions[i][2]);
        }
        if (check < 0)
        {
            for (int m = 0; m < nombreSommets; m++)
            {
                if (tableau_de_k[nombreSommets - 1][m] == tableau_de_k[nombreSommets][m])
                {
                    compteur++;
                }
            }
            if (compteur == nombreSommets)
            {
                enregistrement.println("Ce graphe ne contient pas de circuit absorbant");
                System.out.println("Ce graphe ne contient pas de circuit absorbant");
            } else
            {
                enregistrement.println("Ce graphe contient un circuit absorbant");
                System.out.println("Ce graphe contient un circuit absorbant");
                absorbant = 1;

            }
        }

        //Affichage des deux tableaux combinés, tableau des k et prédécesseur
        int a = 1;
        int b;
        int nombreIteration = 1;

        System.out.print("__________");
        enregistrement.print("__________");
        for(int i = 0; i< nombreSommets;i++)
        {
            System.out.print("_________");
            enregistrement.print("_________");
        }
        System.out.println();
        enregistrement.println();
        System.out.print(" Sommets |");
        enregistrement.print(" Sommets |");
        for(int i = 0; i< nombreSommets;i++)
        {
            System.out.print("   "+i+"    |");
            enregistrement.print("   "+i+"    |");
        }
        System.out.println();
        enregistrement.println();
        System.out.print("   k     |");
        enregistrement.print("   k     |");
        for(int i = 0; i< nombreSommets;i++)
        {
            System.out.print("        |");
            enregistrement.print("        |");
        }
        System.out.println();
        enregistrement.println();
        System.out.print("---------|");
        enregistrement.print("---------|");
        for(int i = 0; i< nombreSommets;i++)
        {
            System.out.print("--------|");
            enregistrement.print("--------|");
        }
        System.out.println();
        enregistrement.println();

        while (a < nombreSommets +1){
            b=0;
            while (b < nombreSommets){
                if (tableau_de_k[a][b] < 900)
                {
                    if (tableau_de_predecesseur[a][b] == 0)
                    {
                        tableau_de_predecesseur[a][b] = tableau_de_predecesseur[a - 1][b];
                    }
                }
                b++;
            }
            a++;
        }

        a = 1;
        while (a < new_k + 1)
        {
            b = 0;
            System.out.print("   "+nombreIteration + "     |");
            enregistrement.print("   "+nombreIteration+ "     |");
            nombreIteration++;
            while (b < nombreSommets)
            {

                if (tableau_de_k[a][b] > 900)
                {
                    enregistrement.printf("%4s    |", "*");
                    System.out.printf("%4s    |", "*");
                } else
                {
                    if (tableau_de_predecesseur[a][b] == 0)
                    {
                        tableau_de_predecesseur[a][b] = tableau_de_predecesseur[a - 1][b];
                    }
                    enregistrement.printf("%3d (%d) |", tableau_de_k[a][b], tableau_de_predecesseur[a][b]);
                    System.out.printf("%3d (%d) |", tableau_de_k[a][b], tableau_de_predecesseur[a][b]);
                }
                b++;
            }
            enregistrement.println();
            System.out.println("");
            a++;
        }
        System.out.print("_________|");
        enregistrement.print("_________|");
        for(int i = 0; i< nombreSommets;i++)
        {
            System.out.print("________|");
            enregistrement.print("________|");
        }
        System.out.println("\n");
        enregistrement.println("\n");

        ArrayList<Integer> chemin = new ArrayList<>();

        if (absorbant == 0) {
            int w = MAX_SIZE;
            int p = MAX_SIZE;
            int longueur = 0;
            for (int ii = 0; ii < nombreSommets; ii++){
                p = ii;

                w = MAX_SIZE;
                while (w != sommet_depart){
                    if (tableau_de_predecesseur[nombreSommets-1][p] == 0)
                        break;
                    p = tableau_de_predecesseur[nombreSommets-1][p];
                    longueur += tableau_de_k[nombreSommets-1][p];
                    if (p != tableau_de_predecesseur[nombreSommets-2][p]) {
                        chemin.add(p);
                    }
                    //enregistrement.print(p);
                    //System.out.print(p);
                    w = p;
                }

                longueur = tableau_de_k[nombreSommets-1][ii];
                if (longueur == MAX_SIZE){

                    enregistrement.print("Le chemin pour aller du sommet " + sommet_depart + " à " + ii + " est impossible");
                    System.out.print("Le chemin pour aller du sommet " + sommet_depart + " à " + ii + " est impossible");
                }
                else {

                    enregistrement.print("Le chemin le plus court du sommet " + sommet_depart + " à " + ii + ", de longueur " + longueur +" est : ");
                    System.out.print("Le chemin le plus court du sommet " + sommet_depart + " à " + ii + ", de longueur " + longueur +" est : ");

                    if (sommet_depart == ii) {
                        System.out.print(ii);
                        enregistrement.print(ii);
                    }
                    else {
                        System.out.print(sommet_depart);
                        Collections.reverse(chemin);
                        for (Integer j : chemin)
                        {
                            enregistrement.print(j);
                            System.out.print(j);
                        }
                        chemin.clear();
                        System.out.print(ii);
                        enregistrement.print(ii);
                    }

                }

                longueur = 0;

                System.out.println(".");
                enregistrement.println(".");
            }
        }
        enregistrement.close();
    }

    /**
     * Enregistrement et affichage des informations du graphe (nombre de sommets, nombre de transitions, matrice d'adjacence et des valeurs.
     * @param enregistrement
     * @param graphe
     * @throws FileNotFoundException
     */
    static void enregistrementEntete(PrintWriter enregistrement, L3_D4_Graphe graphe) throws FileNotFoundException
    {
        System.out.println("Nombre de sommets: " + graphe.getNombreSommets());
        enregistrement.println("Nombre de sommets: " + graphe.getNombreSommets());
        System.out.println("Nombre de transitions: " + graphe.getNombreTransitions());
        enregistrement.println("Nombre de transitions: " + graphe.getNombreTransitions());
        enregistrement.println();
        enregistrement.println("Matrice d'adjacence:");
        System.out.println("\nMatrice d'adjacence:");
        graphe.affichageAdjacence(enregistrement);
        enregistrement.println("\n\nMatrice des valeurs:");
        enregistrement.println();
        System.out.println("\nMatrice des valeurs:");
        graphe.affichageValeurs(enregistrement);

    }

    public static void menu()
    {
        Scanner kb = new Scanner(System.in);

        int rep = 0; // variable permettant de sortir de la boucle selon sa valeur

        do
        {
            System.out.println("Projet Théorie des Graphes L3 Groupe D equipe 4 \n");


            try // try catch pour FileNotFoundException
            {
                int numFichier = choixFichier(); // stock le numéro du graphe voulu retourné par la fonction choixFichier()
                L3_D4_Graphe graphe = new L3_D4_Graphe(numFichier); // Instanciation du graphe
                if (graphe.isArcsPositifs() == false) // Vérification de la présence d'arc négatif, si oui appel automatique de Bellman
                {
                    int initiale = choixSommet(graphe.getNombreSommets()); // stockage du numéro du sommet initiale
                    bellman(numFichier, graphe, initiale); //appel de l'algorithm de Bellman
                } else // si il n'y a pas d'arc négatif alors l'utilisateur peut soit choisir l'algo de Dijkstra soit celui de Bellman
                {
                    System.out.println("Voulez-vous utiliser l'algorithme de Dijkstra (1) ou de Bellman (2) ?");
                    System.out.print("-> ");
                    int choix = 0;
                    choix = kb.nextInt(); // Input utilisateur du choix

                    while (choix < 1 || choix > 2) // sécurité pour l'input
                    {
                        System.out.println("Veuillez refaire votre choix (1 ou 2) : ");
                        System.out.print("-> ");
                        choix = kb.nextInt();
                    }
                    int initiale = choixSommet(graphe.getNombreSommets());// stockage du numéro du sommet initiale
                    if (choix == 1) // si choix 1 alors appel de l'algorithme de Dijkstra
                    {
                        dijkstra(numFichier, graphe, initiale);
                    }

                    if (choix == 2) // si choix 2 alors appel de l'algorithme de Dijkstra
                    {
                        bellman(numFichier, graphe, initiale);
                    }
                }
            }
            catch (FileNotFoundException e) // si le fichier n'existe pas
            {
                System.out.println("Aucun fichier trouvé::" + e.getMessage());
            }

            System.out.println("Voulez vous continuer (1) ou quitter ? (2)");
            rep = inputWithOnlyInt();
        } while (rep != 2);
    }

    /**
     * Methode permettant le choix du sommet de départ pour les algorithmes de dijkstra et/ou Bellman
     * @return le sommet de départ
     */
    public static int choixSommet(int nombreSommets)
    {
        Scanner kb = new Scanner(System.in);
        System.out.println("Quel est le sommet de départ ?"); // choix du sommet de départ
        int initiale;
        do
        {
            initiale = inputWithOnlyInt();
        } while (initiale < 0 || initiale > nombreSommets - 1);

        System.out.println("Le sommet choisi est: " + initiale);
        return initiale;
    }

    /**
     * Methode qui demande à la personne de choisir quelle graphe elle souhaite ouvrir parmi les 10 disponibles
     * avec une saisie sécurisée contre les caracteres
     *
     * @return le fichier que l'on souhaite ouvrir
     */
    public static int choixFichier()
    {
        int saisie;
        System.out.println("Choisissez un graphe entre 1 et 10 :");
        do
        {
            saisie = inputWithOnlyInt();
        } while (saisie < 1 || saisie > 10);

        return saisie;
    }

    /**
     * Methode qui s'assure que la saisie est bien un entier
     * @return un entier saisie
     */
    public static int inputWithOnlyInt()
    {
        Scanner kb = new Scanner(System.in);

        System.out.print("-> ");
        while (!kb.hasNextInt())  //si la selection est différente d'un entier on coninue de demander une saisie
        {
            System.out.print("Veuillez choisir un nombre valide\n-> ");
            kb.next();
        }
        return kb.nextInt();
    }

}

