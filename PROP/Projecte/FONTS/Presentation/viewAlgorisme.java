package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class viewAlgorisme extends JFrame{
    private JButton exitButton;
    private JButton AlgButton;
    private JPanel menuPanel;
    private JTextField SuptextField;
    private JTextField ConftextField;
    private JTextField NumRtextField;
    private JTextField FitxerDtextField;
    private JLabel NumRLabel;
    private JLabel ConfLabel;
    private JLabel SupLabel;
    private JLabel FitxerDLabel;
    private JRadioButton NoValidacioRadioButton;
    private JRadioButton ValidacioRadioButton;
    private final CtrlPresentation ctrlPresentation;
    private int xMouse;
    private int yMouse;
    public String path;

    public viewAlgorisme(CtrlPresentation cP) {
        ctrlPresentation = cP;
        initComponents();
    }

    public void makeVisible() {
        this.setSize(new Dimension(800, 400));
        setLocationRelativeTo(null);
        menuPanel.setEnabled(true);
        exitButton.setEnabled(true);
        AlgButton.setEnabled(true);
        SupLabel.setEnabled(true);
        SuptextField.setEnabled(true);
        ConfLabel.setEnabled(true);
        ConftextField.setEnabled(true);
        NumRLabel.setEnabled(true);
        NumRtextField.setEnabled(true);
        FitxerDLabel.setEnabled(true);
        FitxerDtextField.setEnabled(true);
    }

    public void makeNotVisible() {
        menuPanel.setEnabled(false);
        exitButton.setEnabled(false);
        AlgButton.setEnabled(false);
        SupLabel.setEnabled(false);
        SuptextField.setEnabled(false);
        ConfLabel.setEnabled(false);
        ConftextField.setEnabled(false);
        NumRLabel.setEnabled(false);
        NumRtextField.setEnabled(false);
        FitxerDLabel.setEnabled(false);
        FitxerDtextField.setEnabled(false);

    }

    private void initComponents() {
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

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                exitButtonmouseClicked(e);
            }
        });

        AlgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    if (ValidacioRadioButton.isSelected()) {
                        ctrlPresentation.ModificarDades(path);
                        ctrlPresentation.Algorisme80_20(FitxerDtextField.getText() ,Double.parseDouble(SuptextField.getText()), Double.parseDouble(ConftextField.getText()), Integer.parseInt(NumRtextField.getText()));
                        ctrlPresentation.switchFromAtoVR();
                    }
                    else {
                        ctrlPresentation.Algorisme100(FitxerDtextField.getText() ,Double.parseDouble(SuptextField.getText()), Double.parseDouble(ConftextField.getText()), Integer.parseInt(NumRtextField.getText()));
                        ctrlPresentation.switchFromAtoMP();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    private void menuPanelmousePressed(MouseEvent e) {
        xMouse = e.getX();
        yMouse = e.getY();
    }

    private void menuPanelmouseDragged(MouseEvent e) {
        int x = e.getXOnScreen();
        int y = e.getYOnScreen();

        this.setLocation(x - xMouse, y - yMouse);
    }

    private void exitButtonmouseClicked(MouseEvent e) {
        ctrlPresentation.exit();
    }
}
