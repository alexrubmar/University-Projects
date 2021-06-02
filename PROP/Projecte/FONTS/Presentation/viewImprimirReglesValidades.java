package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class viewImprimirReglesValidades extends JFrame{
    private CtrlPresentation ctrlPresentation;
    private JPanel menuPanel;
    private JButton exitButton;
    private JButton menuButton;
    private JTextArea textArea;
    private int xMouse;
    private int yMouse;
    String path;

    public viewImprimirReglesValidades(CtrlPresentation cP) {
        ctrlPresentation = cP;
        initComponents();
    }

    public void makeVisible() {
        this.setSize(new Dimension(1000,700));
        setLocationRelativeTo(null);
        menuPanel.setEnabled(true);
        exitButton.setEnabled(true);
        menuButton.setEnabled(true);
        textArea.setEnabled(true);
        textArea.setText("");

        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            textArea.read(br, null);
            br.close();
            textArea.requestFocus();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void makeNotVisible() {
        menuPanel.setEnabled(false);
        exitButton.setEnabled(false);
        menuButton.setEnabled(false);
        textArea.setEnabled(false);
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

        menuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                menuMouseClicked(e);
            }
        });

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                exitButtonmouseClicked(e);
            }
        });

    }

    private void menuMouseClicked(MouseEvent e) {
        ctrlPresentation.switchFromIRVtoMP();
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

    private void exitButtonmouseClicked(MouseEvent e) {
        ctrlPresentation.exit();
    }
}
