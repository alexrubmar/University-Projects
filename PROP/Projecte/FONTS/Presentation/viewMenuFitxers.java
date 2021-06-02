package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class viewMenuFitxers extends JFrame{
    private final CtrlPresentation ctrlPresentation;
    private JButton exitButton;
    private JButton filePreButton;
    private JButton fileNonPreButton;
    private JPanel menuPanel;
    private JButton backButton;
    private int xMouse;
    private int yMouse;

    public viewMenuFitxers(CtrlPresentation cP) {
        ctrlPresentation = cP;
        initComponents();
    }

    public void makeVisible() {
        this.setSize(new Dimension(600,400));
        setLocationRelativeTo(null);
        menuPanel.setEnabled(true);
        exitButton.setEnabled(true);
        backButton.setEnabled(true);
        filePreButton.setEnabled(true);
        fileNonPreButton.setEnabled(true);
    }

    public void makeNotVisible() {
        menuPanel.setEnabled(false);
        exitButton.setEnabled(false);
        backButton.setEnabled(false);
        filePreButton.setEnabled(false);
        fileNonPreButton.setEnabled(false);
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

        fileNonPreButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fileNonPreMouseClicked(e);
            }
        });

        filePreButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                filePreMouseClicked(e);
            }
        });

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BackMouseClicked(e);
            }
        });

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                exitButtonMouseClicked(e);
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

    private void BackMouseClicked(MouseEvent e) {
        ctrlPresentation.switchFromMFtoMP();
    }

    private void filePreMouseClicked(MouseEvent e) {
        ctrlPresentation.switchFromMFtoMFP();
    }

    private void fileNonPreMouseClicked(MouseEvent e) {
        ctrlPresentation.switchFromMFtoMFSP();
    }

    private void exitButtonMouseClicked(MouseEvent e) {
        ctrlPresentation.exit();
    }
}
