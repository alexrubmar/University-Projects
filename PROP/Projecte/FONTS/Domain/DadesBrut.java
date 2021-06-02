package Domain;

import java.io.*;
import java.util.*;

public class DadesBrut extends Dades{

    /**
     * Atributs
     */
    public static File arxiu = null;
    public static FileReader fitxer = null;
    public static BufferedReader buffer = null;
    public static ArrayList filesTotals;
    private int columnes;
    ArrayList<String> tipusAtributs;


    /**
     * Constructora
     */
    public DadesBrut(String ruta, Object[][] matriuDades, Object[][] matriuDadesInvertides, ArrayList tipusAtributs,
                     ArrayList<ArrayList<Item>>matriuDiscretitzada, ArrayList<ArrayList<Item>> matriuADiscretitzar, ArrayList nomAtributs) {
        super(ruta, matriuDades,matriuDadesInvertides , tipusAtributs, matriuDiscretitzada, matriuADiscretitzar, nomAtributs);
        tipusAtributs = new ArrayList<String>();
    }


    /**
     * Metodes privats
     */
    private void InicialitzarFitxer(String rutaEntrada) {
        try {
            arxiu = new File(rutaEntrada);
            fitxer = new FileReader(arxiu);
            buffer = new BufferedReader(fitxer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int LlegirFitxerBrut() {
        String filaIndividual = null;
        boolean isPrimeraFila = false;
        String primeraFila = "";
        int numColumnes = 0;
        nomAtributs = new ArrayList<>();
        filesTotals = new ArrayList<>();
        while (true) {
            try {
                if ((filaIndividual = buffer.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!isPrimeraFila) {
                filesTotals.add(filaIndividual);
                primeraFila = filaIndividual;
                isPrimeraFila = true;
            } else {
                filesTotals.add(filaIndividual);
            }
        }
        String atribut = "";
        StringTokenizer tokens = new StringTokenizer(primeraFila, ",");
        while (tokens.hasMoreTokens()) {//Vamos contando cuantos atributos tiene la primeraFila para construir correctamente la matriz
            ++numColumnes;
            atribut = tokens.nextToken();
            nomAtributs.add(atribut);
        }
        return numColumnes;
    }

    private void EscriureMatrius(int c) {
        matriuDades = new Object[filesTotals.size()][c];
        matriuDadesInvertides = new Object[c][filesTotals.size()];
        int itFila;

        for (int i = 1; i < filesTotals.size(); ++i) {
            String lineaa = (String) filesTotals.get(i);
            itFila = 0;
            StringTokenizer tokensAtribut = new StringTokenizer(lineaa, ",");
            while (tokensAtribut.hasMoreTokens()) {
                String atribut = (String) tipusAtributs.get(itFila);

                if (atribut.equals("int")) {
                    int valorInt = Integer.parseInt(tokensAtribut.nextToken());
                    matriuDades[i-1][itFila] = valorInt;
                    matriuDadesInvertides[itFila][i-1] = valorInt;
                    itFila++;

                } else if (atribut.equals("bool")) {
                    boolean valorBool = Boolean.parseBoolean(tokensAtribut.nextToken());
                    matriuDades[i-1][itFila] = valorBool;
                    matriuDadesInvertides[itFila][i-1] = "null";
                    itFila++;

                } else if (atribut.equals("String")) {
                    String valorString = tokensAtribut.nextToken();
                    matriuDades[i-1][itFila] = valorString;
                    matriuDadesInvertides[itFila][i-1] = "null";
                    itFila++;

                } else if (atribut.equals("double")) {
                    double valorDouble = Double.parseDouble(tokensAtribut.nextToken());
                    matriuDades[i-1][itFila] = valorDouble;
                    matriuDadesInvertides[itFila][i-1] = valorDouble;
                    itFila++;
                }

            }
        }
    }



    private void TancarFitxer() {
        try {
            if (null != fitxer) {
                fitxer.close();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /**
     * Metodes publics
     */

    public void MostrarFitxer(String rutaEntrada) {
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
        }
    }

    public void runDadesBrut() {
        EscriureMatrius(columnes);
        TancarFitxer();
    }

    public void AgafarAtributs(String rutaEntrada) {
        InicialitzarFitxer(rutaEntrada);
        columnes = LlegirFitxerBrut();
    }

}
