package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class viewMenuAsignarTipusAtributs extends JDialog {
    private CtrlPresentation ctrlPresentation;
    private JPanel menuPanel;
    private JButton OKButton;
    private JComboBox<String> comboBox;
    JLabel text;
    private int xMouse;
    private int yMouse;
    public String atribut;
    public String path;

    public viewMenuAsignarTipusAtributs(CtrlPresentation cP) {
        ctrlPresentation = cP;
        initComponents();
        setModal(true);
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setLocationRelativeTo(null);
        setContentPane(menuPanel);

        menuPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                menuPanelmouseDragged(e);
            }
        });

        menuPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                menuPanelmousePressed(e);
            }
        });

        OKButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OKButtonmouseClicked(e);
            }
        });

    }

    private void menuPanelmouseDragged(MouseEvent e) {
        int x = e.getXOnScreen();
        int y = e.getYOnScreen();

        this.setLocation(x - xMouse, y - yMouse);
    }

    private void menuPanelmousePressed(MouseEvent e) {
        xMouse = e.getX();
        yMouse = e.getY();
    }

    private void OKButtonmouseClicked(MouseEvent e) {
        atribut = ((String) comboBox.getSelectedItem());
        onCancel();
        ctrlPresentation.AfegirTipusAtribut(atribut);
    }

    public void getatribut(String nomAtribut) {
        text.setText("De quin tipus es aquest atribut? " + nomAtribut);
    }

    private void onCancel() {
        dispose();
    }
}
