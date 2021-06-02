package Domain;


import java.io.*;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.StringTokenizer;

public class DadesPreprocessades extends Dades {
    /**
     * Atributs
     */

    public static File arxiu = null;
    public static FileReader fitxerL = null;
    public static BufferedReader buffer = null;
    public static ArrayList filesTotals = new ArrayList<>();
    public static FileWriter fitxerE = null;
    public static PrintWriter escriptor = null;
    public static String[][] matriuJaPreprocessada = null;



    /**
     * Constructora
     */
    public DadesPreprocessades(String ruta, Object[][] matriuDades, Object[][] matriuDadesInvertides, ArrayList tipusAtributs,
                               ArrayList<ArrayList<Item>>matriuDiscretitzada, ArrayList<ArrayList<Item>> matriuADiscretitzar, ArrayList nomAtributs) {
        super(ruta, matriuDades, matriuDadesInvertides, tipusAtributs, matriuDiscretitzada, matriuADiscretitzar, nomAtributs);
    }

    /**
     * Metodes privats
     */

    public void discretization() {
        double[] max = new double[matriuDadesInvertides.length];
        double[] min = new double[matriuDadesInvertides.length];
        double[] equalWidth = new double[matriuDadesInvertides.length];
        String[][] matriuDadesPreprocessades = new String[matriuDades.length][matriuDades[0].length];
        for (int i = 0; i < matriuDadesInvertides.length; ++i) {
            if (matriuDadesInvertides[i][0] != "null") {
                for (int j = 0; j < matriuDadesInvertides[i].length - 1; ++j) {
                    if (j == 0) {
                        max[i] = Double.valueOf(String.valueOf(matriuDadesInvertides[i][j]));
                        min[i] = Double.valueOf(String.valueOf(matriuDadesInvertides[i][j]));
                    } else {

                        if (Double.valueOf(String.valueOf(matriuDadesInvertides[i][j])) < min[i]) min[i] = Double.valueOf(String.valueOf(matriuDadesInvertides[i][j]));
                        if (Double.valueOf(String.valueOf(matriuDadesInvertides[i][j])) > max[i]) max[i] = Double.valueOf(String.valueOf(matriuDadesInvertides[i][j]));
                    }
                }
                equalWidth[i] = (max[i] - min[i]) / 3;

                for (int j = 0; j < matriuDadesInvertides[i].length - 1; ++j) {
                    if (Double.valueOf(String.valueOf(matriuDadesInvertides[i][j])) >= min[i] && Double.valueOf(String.valueOf(matriuDadesInvertides[i][j])) <= (min[i]+equalWidth[i])) {
                        BigDecimal valorArrodonit = BigDecimal.valueOf(min[i] + equalWidth[i]);
                        valorArrodonit = valorArrodonit.setScale(2, RoundingMode.HALF_UP);
                        matriuDadesPreprocessades[j][i] = String.valueOf(valorArrodonit);

                    }
                    else if (Double.valueOf(String.valueOf(matriuDadesInvertides[i][j])) > (min[i]+equalWidth[i]) && Double.valueOf(String.valueOf(matriuDadesInvertides[i][j])) <= (min[i]+(2*equalWidth[i]))) {
                        BigDecimal valorArrodonit = BigDecimal.valueOf(min[i] + (2 * equalWidth[i]));
                        valorArrodonit = valorArrodonit.setScale(2, RoundingMode.HALF_UP);
                        matriuDadesPreprocessades[j][i] = String.valueOf(valorArrodonit);

                    }
                    else if (Double.valueOf(String.valueOf(matriuDadesInvertides[i][j])) > (min[i]+(2*equalWidth[i])) && Double.valueOf(String.valueOf(matriuDadesInvertides[i][j])) <= (min[i]+(3*equalWidth[i]))) {
                        BigDecimal valorArrodonit = BigDecimal.valueOf(min[i] + (3 * equalWidth[i]));
                        valorArrodonit = valorArrodonit.setScale(2, RoundingMode.HALF_UP);
                        matriuDadesPreprocessades[j][i] = String.valueOf(valorArrodonit);
                    }
                }
            }
        }
        afegeixStringsBools(matriuDadesPreprocessades);
        guardarFitxerPreprocessat(matriuDadesPreprocessades);
        matriuDiscretitzada = transformaMatriuAArrayListItem(matriuDadesPreprocessades);
    }

    private void guardarFitxerPreprocessat(String[][] matriu) {
        try {
            fitxerE = new FileWriter(ruta);
        } catch (IOException e) {
            e.printStackTrace();
        }
        escriptor = new PrintWriter(fitxerE);
        int c = 0;
        for (Object atr : nomAtributs) {
            if (c == nomAtributs.size()-1) escriptor.print(atr);
            else escriptor.print(atr + ",");
            ++c;
        }
            escriptor.print("\n");
        for (int i = 1; i < matriu.length - 1; ++i) {
            for (int j = 0; j < matriu[i].length; ++j) {
                if (j == matriu[i].length -1) escriptor.print(matriu[i][j]);
                else escriptor.print(matriu[i][j] + ",");
            }
            escriptor.print("\n");
        }
        if (null != fitxerE) {
            try {
                fitxerE.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void afegeixStringsBools (String[][] m) {
        int it = 0;
        for (Object s : tipusAtributs) {
            if (s.equals("bool") || s.equals("String")) {
                for (int j = 0; j < m.length; ++j) {
                    if (s.equals("bool")) m[j][it] = Boolean.toString(Boolean.parseBoolean((String.valueOf(matriuDades[j][it]))));
                    else m[j][it] = (String) matriuDades[j][it];
                }
            }
            it++;
        }
    }


    private ArrayList<ArrayList<Item>> transformaMatriuAArrayListItem(String[][] m) {
        ArrayList<ArrayList<Item>> ret = new ArrayList<>();
        String[] vec = new String[nomAtributs.size()];
        int it = 0;
        for (Object s : nomAtributs) {
            vec[it] = (String) s;
            ++it;
        }
        for (int i = 0; i < m.length - 1; ++i) {
            ArrayList<Item> aux = new ArrayList<>();
            for (int j = 0; j < m[0].length; ++j) {
                Item ii = new Item(vec[j],m[i][j] );
                aux.add(ii);
            }
            ret.add(aux);

        }
        return ret;
    }

    private void InicialitzarFitxer() {
        try {
            arxiu = new File(ruta);
            fitxerL = new FileReader(arxiu);
            buffer = new BufferedReader(fitxerL);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int LlegirFitxerPreprocessat() {
        String filaIndividual = null;
        boolean isPrimeraFila = false;
        String primeraFila = "";
        int numColumnes = 0;
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
        matriuJaPreprocessada = new String[filesTotals.size()][c];
        int itFila;

        for (int i = 1; i < filesTotals.size(); ++i) {
            String lineaa = (String) filesTotals.get(i);    // Guardamos la linea en un String
            //Tratamiento de la matriz
            itFila = 0;
            StringTokenizer tokensAtribut = new StringTokenizer(lineaa, ",");
            while (tokensAtribut.hasMoreTokens()) {
                    String valorString = tokensAtribut.nextToken();
                    matriuJaPreprocessada[i-1][itFila] = valorString;
                    itFila++;



            }
        }
    }

    private void TancarFitxer() {
        try {
            if (null != fitxerL) {
                fitxerL.close();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void runPreprocessar() {
        discretization();
    }

    public void runJaPreprocessat() {
        InicialitzarFitxer();
        int columnes = LlegirFitxerPreprocessat();
        EscriureMatrius(columnes);
        TancarFitxer();
        matriuDiscretitzada = transformaMatriuAArrayListItem(matriuJaPreprocessada);
    }

}
