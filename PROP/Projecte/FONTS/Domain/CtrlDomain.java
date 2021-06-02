package Domain;

import Presentation.CtrlPresentation;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;

public class CtrlDomain {
    /**
     * Attributes
     */
    private final Dades Dades = new Dades(null, null, null, new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>());
    private final DadesBrut DadesBrut = new DadesBrut(null, null, null, new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>());
    private DadesPreprocessades DadesPreprocessades = null;
    private DadesModificades DadesModificades = null;
    private Algorisme Algorisme = null;
    private Validacio Validacio = null;
    private static String text;

    /**
     * Constructor
     */


    public static CtrlDomain getInstance() {
        return CtrlDomainHolder.INSTANCE;
    }


    private static class CtrlDomainHolder {

        private static final CtrlDomain INSTANCE = new CtrlDomain();
    }

    /**
     * Methods called by presentation controller
     */

    public ArrayList<String> AgafarAtributs(String path) {
        DadesBrut.AgafarAtributs(path);
        return DadesBrut.nomAtributs;
    }

    public void passarTipusAtributs(ArrayList<String> tipusAtributs) {
        DadesBrut.tipusAtributs = tipusAtributs;
        DadesBrut.runDadesBrut();
    }

    public void PreprocessarDades (String rutaSortida) throws IOException{
        String rutaPre = loadFilePre() + rutaSortida + ".csv";
        DadesPreprocessades = new DadesPreprocessades(rutaPre, DadesBrut.matriuDades, DadesBrut.matriuDadesInvertides, DadesBrut.tipusAtributs, null, null, DadesBrut.nomAtributs);
        DadesPreprocessades.runPreprocessar();
        DadesModificades = new DadesModificades(null, DadesBrut.matriuDades, DadesBrut.matriuDadesInvertides, DadesBrut.tipusAtributs, DadesPreprocessades.matriuDiscretitzada, new ArrayList<ArrayList<Item>>(), DadesBrut.nomAtributs);
        DadesModificades.runDadesModificades();
    }

    public void JaPreprocessat(String rutaEntrada) throws IOException {
        String rutaCompleta = rutaEntrada;
        DadesPreprocessades = new DadesPreprocessades(rutaCompleta, null, null, null, null, null, new ArrayList<String>());
        DadesPreprocessades.runJaPreprocessat();

    }

    public void AplicarAlgorisme80_20(double suport, double confianca, int maxRegles, String rutaEntrada) throws IOException {
        String rutaCompleta = loadFileR() + rutaEntrada + ".csv";
        DadesModificades = new DadesModificades(null, DadesPreprocessades.matriuDades, null, null, DadesPreprocessades.matriuDiscretitzada, new ArrayList<ArrayList<Item>>(), DadesPreprocessades.nomAtributs);
        DadesModificades.runDadesModificades();
        Algorisme = new Algorisme(suport, confianca, maxRegles,DadesModificades.matriuAlgorisme, new Hashtable<Integer, ArrayList<Item>>(), new Hashtable<Integer, ArrayList<Item>>(), new Hashtable<Integer, Double>(), new Hashtable<Integer, Double>());
        Algorisme.runAlgorisme(rutaCompleta);
    }

    public void AplicarAlgorisme100(double suport, double confianca, int maxRegles, String rutaEntrada) throws IOException {
        String rutaCompleta = loadFileR() + rutaEntrada + ".csv";
        Algorisme = new Algorisme(suport, confianca, maxRegles,DadesPreprocessades.matriuDiscretitzada, new Hashtable<Integer, ArrayList<Item>>(), new Hashtable<Integer, ArrayList<Item>>(), new Hashtable<Integer, Double>(), new Hashtable<Integer, Double>());
        Algorisme.runAlgorisme(rutaCompleta);
    }

    public void ValidarRegles(double MargeSup, double MargeConfi, String ruta) throws IOException {
        Validacio = new Validacio(MargeSup, MargeConfi);
        Validacio.runValidacio(loadFileV() + ruta + ".csv");
    }



    public static void setTextFitxers(String s) {
        text += "\n" + s;
    }


    public static String getText() {
        return text;
    }


    public String loadFileV () throws IOException {
            return Data.CtrlDatabase.getInstance().readV();
    }

    public String loadFilePre() throws IOException {
        return Data.CtrlDatabase.getInstance().readPre();
    }

    private String loadFileR () throws IOException {
        return Data.CtrlDatabase.getInstance().readR();
    }

    public static String loadFileTest() throws IOException {
        return Data.CtrlDatabase.getInstance().readTest();
    }



}
