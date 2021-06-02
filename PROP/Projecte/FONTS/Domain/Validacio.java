package Domain;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

public class Validacio {
    /**
     * Attributes
     */
    public static ArrayList<ArrayList<Item>> matriuValidacio;
    public static Hashtable<Integer, ArrayList<Item>> antecedent;
    public static Hashtable<Integer, ArrayList<Item>> consequent;
    public static Hashtable<Integer, Double> suport;
    public static Hashtable<Integer, Double> confianca;
    private static double MargeSup;
    private static double MargeConfi;
    private static Algorisme algo = new Algorisme(Algorisme.suport, Algorisme.confiancaMinima, 0,null, Algorisme.antecedent, Algorisme.consequent, Algorisme.llistaSuport, Algorisme.llistaConfianca);
    private static DadesModificades DadesMod = new DadesModificades(null, DadesModificades.matriuDades, null , null, null, null, null);

    /**
     * Constructor
     */
    public Validacio(double MargeSup, double MargeConfi) {
        Validacio.matriuValidacio = DadesMod.matriuValidacio;
        Validacio.antecedent = Algorisme.antecedent;
        Validacio.consequent = Algorisme.consequent;
        Validacio.suport = Algorisme.llistaSuport;
        Validacio.confianca = Algorisme.llistaConfianca;
        Validacio.MargeSup = MargeSup;
        Validacio.MargeConfi = MargeConfi;
    }

    private static void validar() {
        Enumeration e = antecedent.keys();
        Object clau;
        ArrayList<Item> itemsA;
        ArrayList<Item> itemsC;
        while (e.hasMoreElements()) {
            clau = e.nextElement();
            itemsA = antecedent.get(clau);
            itemsC = consequent.get(clau);
            double supAmbMarge = algo.suport - (algo.suport*MargeSup);
            double confAmbMarge = algo.confiancaMinima - (algo.confiancaMinima*MargeConfi);
            double aparicionsA = 0;
            double aparicionsT = 0;
            double SA = 0;
            double ST = 0;
            double C = 0;
            for (ArrayList<Item> Items : matriuValidacio) {
                if (AtributsPresents(Items, itemsA)) {
                    ++aparicionsA;
                    if (AtributsPresents(Items, itemsC)) ++aparicionsT;
                }
            }

            SA = aparicionsA / matriuValidacio.size();
            ST = aparicionsT / matriuValidacio.size();
            C = ST / SA;

            if (ST < supAmbMarge || C < confAmbMarge) {
                antecedent.remove(clau);
                consequent.remove(clau);
                suport.remove(clau);
                confianca.remove(clau);
            }
        }

    }

    private static boolean AtributsPresents(ArrayList<Item> Total, ArrayList<Item> Parcial) {
        boolean b;
        for (Item p : Parcial) {
            b = false;
            for (Item t : Total) {
                if (t.equals(p)) b = true;
            }
            if (!b) return false;
        }
        return true;
    }


    public void runValidacio(String rutaEntrada) {
        validar();
        Enumeration claus = antecedent.keys();
        FileWriter fitxer = null;
        try {
            fitxer = new FileWriter(rutaEntrada);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter escriptor = new PrintWriter(fitxer);


        while (claus.hasMoreElements()){
            Object clau = claus.nextElement();
            escriptor.print("Regla d'associacio valida " + ": ");
            for (Item elem : antecedent.get(clau)) {
                escriptor.print(elem.id + " [" + elem.valor + "] ");
            }
            escriptor.print("-> ");
            for (Item elem : consequent.get(clau)) {
                escriptor.print(elem.id + " [" + elem.valor + "] ");
            }
            escriptor.print("\n");
            escriptor.print("Suport: " + suport.get(clau));
            escriptor.print("\n");
            escriptor.print("Confianca: " + confianca.get(clau));
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
}
