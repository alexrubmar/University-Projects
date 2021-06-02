package Domain;

import java.util.ArrayList;
import java.util.Random;


public class DadesModificades extends Dades {
    /**
     * Atributs
    */
    public static ArrayList<ArrayList<Item>> matriuValidacio = new ArrayList<ArrayList<Item>>();

    /**
    * Constructora
    */
    public DadesModificades(String ruta, Object[][] matriuDades, Object[][] matriuDadesInvertides, ArrayList tipusAtributs,
                        ArrayList<ArrayList<Item>>matriuDiscretitzada, ArrayList<ArrayList<Item>> matriuAlgorisme, ArrayList nomAtributs) {
        super(ruta, matriuDades,matriuDadesInvertides , tipusAtributs, matriuDiscretitzada, matriuAlgorisme, nomAtributs);
    }

    /**
    * Metodes privats
    */
    private static void elaboraMatriuValidacioIAlgorisme() {
        Random rand = new Random();
        DadesModificades.matriuAlgorisme = matriuDiscretitzada;
        int n = (int) (0.2 * matriuAlgorisme.size());
        for (int i = 0; i < n; i++) {
            int randomIndex = rand.nextInt(matriuAlgorisme.size());
            // add element in temporary list
            matriuValidacio.add(matriuAlgorisme.get(randomIndex));
            // Remove selected element from orginal list
            matriuAlgorisme.remove(randomIndex);
        }
    }

    /**
     * Metodes publics
     */

    public void runDadesModificades() {
        elaboraMatriuValidacioIAlgorisme();
    }
}

