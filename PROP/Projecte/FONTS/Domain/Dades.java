package Domain;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Dades {
    /**
     * Attributes
     */
    public static String ruta;
    public static Object[][] matriuDades;
    public static Object[][] matriuDadesInvertides;
    public static ArrayList<ArrayList<Item>> matriuDiscretitzada;
    public static ArrayList<ArrayList<Item>> matriuAlgorisme;
    public static ArrayList tipusAtributs;
    public static ArrayList nomAtributs;




    /**
     * Constructor
     */
    public Dades (String ruta, Object[][] matriuDades, Object[][] matriuDadesInvertides , ArrayList tipusAtributs, ArrayList<ArrayList<Item>> matriuDiscretitzada, ArrayList<ArrayList<Item>> matriuAlgorisme, ArrayList nomAtributs) {
        Dades.ruta = ruta;
        Dades.matriuDades = matriuDades;
        Dades.tipusAtributs = tipusAtributs;
        Dades.matriuDadesInvertides = matriuDadesInvertides;
        Dades.matriuDiscretitzada = matriuDiscretitzada;
        Dades.matriuAlgorisme = matriuAlgorisme;
        Dades.nomAtributs = nomAtributs;
    }

}

