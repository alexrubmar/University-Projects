package Domain.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Enumeration;

import Domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestDadesModificades_Algorisme {

    private final ArrayList<ArrayList<Item>> matriuTest = new ArrayList<>();
    private ArrayList<Item> filesTotals = new ArrayList<>();
    String rutaEntrada = CtrlDomain.loadFileTest() + "provaTest.csv";
    DadesModificades instanceDadesMod;
    Algorisme instanceAlgorisme;


    public TestDadesModificades_Algorisme() throws IOException {
    }

    @Before
    public void setUp() throws IOException {
        instanceDadesMod = new DadesModificades(null, null, null, null, matriuTest, new ArrayList<>(), null);
        TestDadesModificades();
        instanceDadesMod.runDadesModificades();
    }

    public void TestDadesModificades() throws IOException {
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
            String valor = "";
            StringTokenizer tokens = new StringTokenizer(filaIndividual, ",");
            while (tokens.hasMoreTokens()) {
                valor = tokens.nextToken();
                filesTotals.add(new Item("0", valor));
            }

            matriuTest.add(filesTotals);
            filesTotals = new ArrayList<>();
        }

    }

    //Domain.Test que s'assegura de que la divisió 80/20 que realitza la classe DadesModificades es realitza de forma correcta
    @Test
    public void DivisioMatriuCorrecte () throws IOException {
        int esperatMatriuValidacio = 30;
        int sortidaMatriuValidacio = instanceDadesMod.matriuValidacio.size();
        int esperatMatriuAlgorisme = 120;
        int sortidaMatriuAlgorisme = instanceDadesMod.matriuAlgorisme.size();
        assertEquals("No son iguals!",esperatMatriuValidacio, sortidaMatriuValidacio );
        assertEquals("No son iguals!",esperatMatriuAlgorisme, sortidaMatriuAlgorisme );

    }

    /* Domain.Test que comprova que les regles extretes per l'algorisme tinguin suport i confiança més gran que el demanat
     i que el total de regles sigui menor o igual al màxim de regles demanat per aquests valors (Suport = 0.95,
     Confiança = 0.3, maxRegles = 7)
     */
    @Test
    public void Algorisme_Suport_Confiança_MaxRegles_Correcte1() {
        double Suport = 0.95;
        double Confi = 0.3;
        int maxRegles = 7;
        instanceAlgorisme = new Algorisme(Suport, Confi, maxRegles, DadesModificades.matriuAlgorisme, new Hashtable<Integer, ArrayList<Item>>(), new Hashtable<Integer, ArrayList<Item>>(), new Hashtable<Integer, Double>(), new Hashtable<Integer, Double>());
        instanceAlgorisme.runAlgorisme("reglesTEST");
        Enumeration e = instanceAlgorisme.llistaSuport.keys();
        boolean b;
        int clau;
        double ConfiElement;
        while (e.hasMoreElements()) {
            clau = (int) e.nextElement();
            ConfiElement = instanceAlgorisme.llistaSuport.get(e);
            if (ConfiElement < Confi) b = false;
            else b = true;
            assertTrue("La confiança d'una regla és inferior a la confiança mínima", b );
        }
        e = instanceAlgorisme.llistaSuport.keys();
        double SuportElement;
        while (e.hasMoreElements()) {
            clau = (int) e.nextElement();
            SuportElement = instanceAlgorisme.llistaConfianca.get(e);
            if (SuportElement < Suport) b = false;
            else b = true;
            assertTrue("El suport d'una regla és inferior al suport mínim", b );
        }
        b = (maxRegles >= instanceAlgorisme.antecedent.size() && maxRegles >= instanceAlgorisme.consequent.size());
        assertTrue("Es treuen més regles del màxim demanat", b);
    }

    /* Domain.Test que comprova que les regles extretes per l'algorisme tinguin suport i confiança més gran que el demanat
     i que el total de regles sigui menor o igual al màxim de regles demanat per aquests valors (Suport = 0.5,
     Confiança = 0.2, maxRegles = 4)
     */
    @Test
    public void Algorisme_Suport_Confiança_MaxRegles_Correcte2() {
        double Suport = 0.5;
        double Confi = 0.2;
        int maxRegles = 4;
        instanceAlgorisme = new Algorisme(Suport, Confi, maxRegles, DadesModificades.matriuAlgorisme, new Hashtable<Integer, ArrayList<Item>>(), new Hashtable<Integer, ArrayList<Item>>(), new Hashtable<Integer, Double>(), new Hashtable<Integer, Double>());
        instanceAlgorisme.runAlgorisme("reglesTEST");
        Enumeration e = instanceAlgorisme.llistaSuport.keys();
        boolean b;
        int clau;
        double ConfiElement;
        while (e.hasMoreElements()) {
            clau = (int) e.nextElement();
            ConfiElement = instanceAlgorisme.llistaSuport.get(e);
            if (ConfiElement < Confi) b = false;
            else b = true;
            assertTrue("La confiança d'una regla és inferior a la confiança mínima", b );
        }
        e = instanceAlgorisme.llistaSuport.keys();
        double SuportElement;
        while (e.hasMoreElements()) {
            clau = (int) e.nextElement();
            SuportElement = instanceAlgorisme.llistaConfianca.get(e);
            if (SuportElement < Suport) b = false;
            else b = true;
            assertTrue("El suport d'una regla és inferior al suport mínim", b );
        }
        b = (maxRegles >= instanceAlgorisme.antecedent.size() && maxRegles >= instanceAlgorisme.consequent.size());
        assertTrue("Es treuen més regles del màxim demanat", b);
    }
    
    @Test
    public void HashTablesSincronitzats() {
        double Suport = 0.95;
        double Confi = 0.3;
        int maxRegles = 7;
        instanceAlgorisme = new Algorisme(Suport, Confi, maxRegles, DadesModificades.matriuAlgorisme, new Hashtable<Integer, ArrayList<Item>>(), new Hashtable<Integer, ArrayList<Item>>(), new Hashtable<Integer, Double>(), new Hashtable<Integer, Double>());
        instanceAlgorisme.runAlgorisme("reglesTEST");
        boolean b = false;
        if ((instanceAlgorisme.antecedent.size() == instanceAlgorisme.consequent.size())
            && (instanceAlgorisme.antecedent.size() == instanceAlgorisme.llistaSuport.size()) 
                && (instanceAlgorisme.antecedent.size() == instanceAlgorisme.llistaConfianca.size()))
            b = true;
        assertTrue("Els hashtables tenen diferents mides", b);
    }

}
