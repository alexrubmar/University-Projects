package Presentation;

import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class viewMenuPrincipal extends JFrame{

    private JPanel menuPanel;
    private JButton startButton;
    private JLabel titleLabel;
    private JButton exitButton;
    private JButton showRulesButton;
    private CtrlPresentation ctrlPresentation;
    private int xMouse;
    private int yMouse;

    public viewMenuPrincipal(CtrlPresentation cP) {
        ctrlPresentation = cP;
        initComponents();
    }

    public void makeVisible() {
        this.setSize(new Dimension(600,400));
        setLocationRelativeTo(null);
        menuPanel.setEnabled(true);
        startButton.setEnabled(true);
        titleLabel.setEnabled(true);
        exitButton.setEnabled(true);
        showRulesButton.setEnabled(true);
    }

    public void makeNotVisible() {
        menuPanel.setEnabled(false);
        startButton.setEnabled(false);
        titleLabel.setEnabled(false);
        exitButton.setEnabled(false);
        showRulesButton.setEnabled(false);
    }

    private void initComponents() {
        setContentPane(menuPanel);

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                exitButtonmouseClicked(e);
            }
        });

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
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ctrlPresentation.switchFromMPtoMF();
            }
        });

        showRulesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ctrlPresentation.switchFromMPtoMSR();
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

    private void exitButtonmouseClicked(MouseEvent e) {
        ctrlPresentation.exit();
    }
}
