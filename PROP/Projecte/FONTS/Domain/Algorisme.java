package Domain;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class Algorisme {
    /**
     * Atributs
     */
    public static Hashtable<Integer, ArrayList<Item>> antecedent;
    public static Hashtable<Integer, ArrayList<Item>> consequent;
    public static Hashtable<Integer, Double> llistaSuport;
    public static Hashtable<Integer, Double> llistaConfianca;
    public static double suport;
    public static double confiancaMinima;
    public static int maxRegles;
    public static ArrayList<ArrayList<Item>> dadesModificades;
    private Dades dades = new Dades(null, null, null, null, null, Dades.matriuAlgorisme, null);

    /**
     * Constructora
     */

    public Algorisme(double suport, double confiancaMinima, int maxRegles, ArrayList<ArrayList<Item>> dadesModificades, Hashtable<Integer, ArrayList<Item>> antecedent,
                     Hashtable<Integer, ArrayList<Item>> consequent, Hashtable<Integer, Double> llistaSuport, Hashtable<Integer, Double> llistaConfianca) {
        Algorisme.suport = suport;
        Algorisme.confiancaMinima = confiancaMinima;
        Algorisme.maxRegles = maxRegles;
        Algorisme.dadesModificades = dadesModificades;
        Algorisme.antecedent = antecedent;
        Algorisme.consequent = consequent;
        Algorisme.llistaConfianca = llistaConfianca;
        Algorisme.llistaSuport = llistaSuport;
    }

    /**
     * Metodes privats
     */
    
    private static ArrayList<ItemSet> Apriori() {

        ArrayList<ItemSet> PrevItemSetsAmbSuportMinim = new ArrayList<>();

        ItemSet items = ItemsUnics1(dadesModificades);

        int nombreItems = 0; //Nombre d'items per cada itemset de la iteració a la que ens trobem
        while (true) {

            // Ampliem nombre items
            nombreItems++;


            // Agafa itemsets de x items
            ArrayList<ItemSet> itemSets = getItemSets(items, nombreItems);

            ArrayList<ItemSet> itemSetsAmbSuportMinim = new ArrayList<>();

            for (ItemSet conjunt : itemSets) {
                double aparicions = 0;
                for (ArrayList<Item> transaccio : dadesModificades) {
                    if (existsInTransaction(conjunt.set, transaccio)) aparicions++;
                }
                double supSet = aparicions/dadesModificades.size();
                if (supSet >= suport) {
                    conjunt.suport = supSet;
                    itemSetsAmbSuportMinim.add(conjunt);
                }
            }

            if (itemSetsAmbSuportMinim.size() == 0) {
                return PrevItemSetsAmbSuportMinim;
            }

            items = ItemsUnics2(itemSetsAmbSuportMinim);

            PrevItemSetsAmbSuportMinim = itemSetsAmbSuportMinim;
        }
    }

    private static ItemSet ItemsUnics1(ArrayList<ArrayList<Item>> DadesAUsar) {
        ItemSet toReturn = new ItemSet();

        for (ArrayList<Item> transaccio : DadesAUsar) {
            for (Item item : transaccio) {
                if (!containsItem(toReturn.set, item))
                    toReturn.set.add(item);
            }
        }

        return toReturn;
    }
    private static ItemSet ItemsUnics2(ArrayList<ItemSet> DadesAUsar) {
        ItemSet toReturn = new ItemSet();

        for (ItemSet transaccio : DadesAUsar) {
            for (Item item : transaccio.set) {
                if (!containsItem(toReturn.set, item)) toReturn.set.add(item);
            }
        }

        return toReturn;
    }

    //Retorna totes les possibles permutacions de items amb nombreItems determinat
    private static ArrayList<ItemSet> getItemSets(ItemSet items, int nombreItems) {
        if (nombreItems == 1) {

            // Retornem ArrayList de ItemSet (de un item)
            ArrayList<ItemSet> toReturn = new ArrayList<>();
            for (Item item : items.set) {
                ItemSet aList = new ItemSet();
                aList.set.add(item);
                toReturn.add(aList);
            }
            return toReturn;

        } else {
            int size = items.set.size();

            ArrayList<ItemSet> toReturn = new ArrayList<>();

            for (int i = 0; i < size; i++) {

                // Copiem items a itemsAux
                ItemSet itemsAux = new ItemSet();
                itemsAux.set.addAll(items.set);
                // Agafem l'item de la posició i
                Item thisItem = items.set.get(i);

                // Borrar items fins la posició i, inclosa
                itemsAux.set.subList(0, i + 1).clear();

                // Agafar les permutacions dels items restants
                ArrayList<ItemSet> permutationsBelow = getItemSets(itemsAux, nombreItems - 1);

                //Afegir thisItem a cada permutació i afegir la permutació a toReturn
                for (ItemSet aList : permutationsBelow) {
                    aList.set.add(thisItem);
                    toReturn.add(aList);
                }

            }

            return toReturn;

        }
    }


    private static boolean existsInTransaction(ArrayList<Item> itemSet, ArrayList<Item> transaccio) {
        for (Item item : itemSet) {
            if (!containsItem(transaccio, item)) return false;
        }
        return true;
    }

    private static boolean containsItem(ArrayList<Item> conjunt, Item a) {
        for (Item b : conjunt) {
            if (a.equals(b)) return true;
        }
        return false;
    }

    private static int regles_associacio(ItemSet conj, int it, Hashtable<Integer, ArrayList<Item>> _antecedent, Hashtable<Integer, ArrayList<Item>> _consequent, Hashtable<Integer, Double> _llistaSuport, Hashtable<Integer, Double> _llistaConfianca) {
        int numElem = 1;
        while (numElem < conj.set.size()) {
            ArrayList<ItemSet> Auxantecedents;
            Auxantecedents = getItemSets(conj, numElem);
            for (ItemSet perm: Auxantecedents) {
                double aparicions = 0;
                for (ArrayList<Item> transaccio : dadesModificades) {
                    if (existsInTransaction(perm.set, transaccio)) aparicions++;
                }
                double supantecedent = aparicions/dadesModificades.size();
                double confantecedent = conj.suport/supantecedent;
                if (confantecedent >= confiancaMinima) {
                    _antecedent.put(it, perm.set);
                    _llistaSuport.put(it, conj.suport);
                    _llistaConfianca.put(it, confantecedent);
                    ArrayList<Item> conseqAux = new ArrayList<>();
                    for (Item elem : conj.set) {
                        if (!containsItem(perm.set, elem)) conseqAux.add(elem);
                    }
                    _consequent.put(it, conseqAux);
                    ++it;
                }
            }
            ++numElem;
        }
        return it;
    }

    private void reduir_regles(Hashtable<Integer, ArrayList<Item>> antecedentAux, Hashtable<Integer, ArrayList<Item>> consequentAux, Hashtable<Integer, Double> llistaSuportAux, Hashtable<Integer, Double> llistaConfiancaAux) {
        int sizeOriginal = llistaConfiancaAux.size();
        for (int i = 0; i < maxRegles; ++i) {
            double maxConf = 0;
            int pos = 0;
            for (int j = 0; j < sizeOriginal; ++j) {
                if (llistaConfiancaAux.containsKey(j) && llistaConfiancaAux.get(j) > maxConf) {
                    maxConf = llistaConfiancaAux.get(j);
                    pos = j;
                }
            }
            antecedent.put(i, antecedentAux.get(pos));
            consequent.put(i, consequentAux.get(pos));
            llistaConfianca.put(i, llistaConfiancaAux.get(pos));
            llistaSuport.put(i, llistaSuportAux.get(pos));
            llistaConfiancaAux.remove(pos);
        }
    }

    /**
     * Metodes publics
     */
    
    public void runAlgorisme(String rutaEntrada) {
        ArrayList<ItemSet> ConjuntsFrequents = Apriori();
        Hashtable<Integer, ArrayList<Item>> antecedentAux = new Hashtable<Integer, ArrayList<Item>>();
        Hashtable<Integer, ArrayList<Item>> consequentAux = new Hashtable<Integer, ArrayList<Item>>();
        Hashtable<Integer, Double> llistaSuportAux = new Hashtable<Integer, Double>();
        Hashtable<Integer, Double> llistaConfiancaAux = new Hashtable<Integer, Double>();
        int it = 0;
        for (ItemSet Conjunt : ConjuntsFrequents) {
            it = regles_associacio(Conjunt, it, antecedentAux, consequentAux, llistaSuportAux, llistaConfiancaAux);
        }
        if (antecedentAux.size() > maxRegles) reduir_regles(antecedentAux, consequentAux, llistaSuportAux, llistaConfiancaAux);
        else {
            antecedent = antecedentAux;
            consequent = consequentAux;
            llistaSuport = llistaSuportAux;
            llistaConfianca = llistaConfiancaAux;
        }

        Enumeration claus = antecedent.keys();
        FileWriter fitxer = null;
        try {
            fitxer = new FileWriter(rutaEntrada);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter escriptor = new PrintWriter(fitxer);
        while (claus.hasMoreElements()) {
            Object clau = claus.nextElement();
            escriptor.print("Regla d'associacio" + ": ");
            for (Item elem : antecedent.get(clau)) {
                escriptor.print(elem.id + " [" + elem.valor + "] ");
            }
            escriptor.print("-> ");
            for (Item elem : consequent.get(clau)) {
                escriptor.print(elem.id + " [" + elem.valor + "] ");
            }

            escriptor.print("\n");
            escriptor.print("Suport: " + llistaSuport.get(clau));
            escriptor.print("\n");
            escriptor.print("Confianca: " + llistaConfianca.get(clau));
            escriptor.print("\n");
            escriptor.print("\n");
        }

            if (null != fitxer) {
                try {
                    fitxer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    public void LlegirRegles(String rutaEntrada) {
        BufferedReader buffer = null;
        try {
            File arxiu = new File(rutaEntrada);
            FileReader fitxer = new FileReader(arxiu);
            buffer = new BufferedReader(fitxer);

        } catch (IOException e) {
            e.printStackTrace();
        }
        String filaIndividual = null;
        while (true) {
            try {
                if ((filaIndividual = buffer.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            CtrlDomain.setTextFitxers(filaIndividual);
        }
    }
}
