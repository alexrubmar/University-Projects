package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class viewMenuFitxersPre extends JFrame{
    private final CtrlPresentation ctrlPresentation;
    private JButton exitButton;
    private JButton AlgButton;
    private JButton getFileButton;
    private JPanel menuPanel;
    private JButton backButton;
    private JTextArea textArea;
    private int xMouse;
    private int yMouse;
    private final JFileChooser agafaFitxer;
    private String path;

    public viewMenuFitxersPre(CtrlPresentation cP) {
        ctrlPresentation = cP;
        agafaFitxer = new JFileChooser();
        initComponents();
    }

    public void makeVisible() {
        this.setSize(new Dimension(1000,700));
        setLocationRelativeTo(null);
        menuPanel.setEnabled(true);
        exitButton.setEnabled(true);
        getFileButton.setEnabled(true);
        AlgButton.setEnabled(true);
        textArea.setEnabled(true);
        backButton.setEnabled(true);
    }

    public void makeNotVisible() {
        menuPanel.setEnabled(false);
        exitButton.setEnabled(false);
        getFileButton.setEnabled(false);
        AlgButton.setEnabled(false);
        textArea.setEnabled(false);
        backButton.setEnabled(false);
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

        getFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int valorRetorn = agafaFitxer.showOpenDialog(null);

                if (valorRetorn == JFileChooser.APPROVE_OPTION){
                    path = (agafaFitxer.getSelectedFile().getAbsolutePath());
                    try {
                        FileReader fr = new FileReader(path);
                        BufferedReader br = new BufferedReader(fr);
                        textArea.read(br, null);
                        br.close();
                        textArea.requestFocus();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else {
                }
            }
        });

        AlgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ctrlPresentation.JaPreprocessat(path);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                ctrlPresentation.switchFromMFPtoA(path);
            }
        });

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                backMouseClicked(e);
            }
        });

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                exitButtonmouseClicked(e);
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

    private void backMouseClicked(MouseEvent e) {
        ctrlPresentation.switchFromMFPtoMF();
    }

    private void exitButtonmouseClicked(MouseEvent e) {
        ctrlPresentation.exit();
    }
}
