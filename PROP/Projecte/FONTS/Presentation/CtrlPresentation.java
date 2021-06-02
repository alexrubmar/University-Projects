package Presentation;

import Domain.CtrlDomain;

import java.io.IOException;
import java.util.ArrayList;

public class CtrlPresentation {
    /**
     * Attributes
     */
    private final CtrlDomain ctrlDomain;

    private final viewMenuPrincipal viewMP;
    private final viewMenuFitxers viewMF;
    private final viewMenuMostrarRegles viewMSR;
    private final viewMenuFitxersSensePre viewMFSP;
    private final viewMenuFitxersPre viewMFP;
    private final viewValidacioRegles viewVR;
    private final viewAlgorisme viewA;
    private final viewImprimirReglesValidades viewIRV;
    private ArrayList<String> tipusatributs = new ArrayList<String>();

    /**
     * Constructor
     * */

    public static CtrlPresentation getInstance() {
        return CtrlPresentationHolder.INSTANCE;
    }


    private static class CtrlPresentationHolder {

        private static final CtrlPresentation INSTANCE = new CtrlPresentation();
    }

    public CtrlPresentation() {
        ctrlDomain = CtrlDomain.getInstance();

        viewMP = new viewMenuPrincipal(this);
        viewMF = new viewMenuFitxers(this);
        viewMSR = new viewMenuMostrarRegles(this);
        viewMFSP = new viewMenuFitxersSensePre(this);
        viewMFP = new viewMenuFitxersPre(this);
        viewVR = new viewValidacioRegles(this);
        viewA = new viewAlgorisme(this);
        viewIRV = new viewImprimirReglesValidades(this);
    }

    public void inicialitzaPresentation() {
        viewMP.setVisible(true);
        viewMP.makeVisible();
    }


    public void switchFromMPtoMF(){
        viewMP.setVisible(false);
        viewMP.makeNotVisible();

        viewMF.setVisible(true);
        viewMF.makeVisible();
    }

    public void switchFromMPtoMSR() {
        viewMP.setVisible(false);
        viewMP.makeNotVisible();

        viewMSR.setVisible(true);
        viewMSR.makeVisible();
    }

    public void switchFromMFtoMFP() {
        viewMF.setVisible(false);
        viewMF.makeNotVisible();

        viewMFP.setVisible(true);
        viewMFP.makeVisible();
    }

    public void switchFromMFtoMFSP() {
        viewMF.setVisible(false);
        viewMF.makeNotVisible();

        viewMFSP.setVisible(true);
        viewMFSP.makeVisible();
    }

    public void switchFromMFtoMP() {
        viewMF.setVisible(false);
        viewMF.makeNotVisible();

        viewMP.setVisible(true);
        viewMP.makeVisible();
    }

    public void switchFromMSRtoMP() {
        viewMSR.setVisible(false);
        viewMSR.makeNotVisible();

        viewMP.setVisible(true);
        viewMP.makeVisible();
    }

    public void switchFromMFPtoMF() {
        viewMFP.setVisible(false);
        viewMFP.makeNotVisible();

        viewMF.setVisible(true);
        viewMF.makeVisible();
    }

    public void switchFromMFSPtoMF() {
        viewMFSP.setVisible(false);
        viewMFSP.makeNotVisible();

        viewMF.setVisible(true);
        viewMF.makeVisible();
    }

    public void switchFromMFSPtoA(String path) {
        viewMFSP.setVisible(false);
        viewMFSP.makeNotVisible();

        viewA.setVisible(true);
        viewA.makeVisible();
        viewA.path = path;
    }

    public void switchFromMFPtoA(String Path) {
        viewMFP.setVisible(false);
        viewMFP.makeNotVisible();

        viewA.setVisible(true);
        viewA.makeVisible();
        viewA.path = Path;
    }

    public void switchFromAtoMF() {
        viewA.setVisible(false);
        viewA.makeNotVisible();

        viewMF.setVisible(true);
        viewMF.makeVisible();
    }

    public void switchFromAtoVR() {
        viewA.setVisible(false);
        viewA.makeNotVisible();

        viewVR.setVisible(true);
        viewVR.makeVisible();
    }

    public void switchFromAtoMP() {
        viewA.setVisible(false);
        viewA.makeNotVisible();

        viewMP.setVisible(true);
        viewMP.makeVisible();
    }

    public void switchFromVRtoA() {
        viewVR.setVisible(false);
        viewVR.makeNotVisible();

        viewA.setVisible(true);
        viewA.makeVisible();
    }

    public void switchFromVRtoIRV(String path) {
        viewVR.setVisible(false);
        viewVR.makeNotVisible();

        viewIRV.path = path;
        viewIRV.setVisible(true);
        viewIRV.makeVisible();
    }

    public void switchFromIRVtoMP() {
        viewIRV.setVisible(false);
        viewIRV.makeNotVisible();

        viewMP.setVisible(true);
        viewMP.makeVisible();
    }

    public void Preprocessar(String pathSortida) throws IOException {
        ctrlDomain.PreprocessarDades(pathSortida);
    }

    public void AssignarTipusAtributs(String path) throws IOException {
        ArrayList<String> nomsAtributs = ctrlDomain.AgafarAtributs(path);
        viewMenuAsignarTipusAtributs viewMATA = new viewMenuAsignarTipusAtributs(this);
        for (int i = 0; i < nomsAtributs.size() ; ++i){
            viewMATA.getatribut(nomsAtributs.get(i));
            viewMATA.setVisible(true);
        }
    }

    public void AfegirTipusAtribut(String atribut) {
        tipusatributs.add(atribut);
    }

    public void passarTipusAtributs() {
        ctrlDomain.passarTipusAtributs(tipusatributs);
    }

    public void JaPreprocessat(String pathEntrada) throws IOException {
        ctrlDomain.JaPreprocessat(pathEntrada);
    }

    public String AgafarPathFitxerPre () throws IOException {
        return ctrlDomain.loadFilePre();
    }

    public void ModificarDades(String path) throws IOException {
        ctrlDomain.JaPreprocessat(path);
    }

    public void Algorisme80_20(String path, double sup, double conf, int maxRegles) throws IOException {
        ctrlDomain.AplicarAlgorisme80_20(sup, conf, maxRegles, path);
    }

    public void Algorisme100(String path, double sup, double conf, int maxRegles) throws IOException {
        ctrlDomain.AplicarAlgorisme100(sup, conf, maxRegles, path);
    }

    public String getPathV() throws IOException {
        return ctrlDomain.loadFileV();
    }

    public void validarRegles(double MargeSup, double MargeConfi, String ruta) throws IOException {
        ctrlDomain.ValidarRegles(MargeSup, MargeConfi, ruta);
    }


    /**
     * View's synchronization methods
     */
    public void exit() {
        System.exit(0);
    }

}
