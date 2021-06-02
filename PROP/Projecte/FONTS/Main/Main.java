package Main;

import Presentation.CtrlPresentation;

public class Main {

    public static void main(String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CtrlPresentation ctrlPresentation;
                    ctrlPresentation = new CtrlPresentation();
                    ctrlPresentation.inicialitzaPresentation();

            };
        });
    }
}
