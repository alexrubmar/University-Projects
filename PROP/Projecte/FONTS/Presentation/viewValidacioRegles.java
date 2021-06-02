package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

public class viewValidacioRegles extends JFrame{
    private JPanel menuPanel;
    private JButton exitButton;
    private JButton backButton;
    private JTextField textFieldConfi;
    private JTextField textFieldSup;
    private JButton VALIDARButton;
    private JLabel labelSuport;
    private JLabel labelConfi;
    private JTextField textField1;
    private CtrlPresentation ctrlPresentation;
    private int xMouse;
    private int yMouse;
    String path;

    public viewValidacioRegles(CtrlPresentation cP) {
        ctrlPresentation = cP;
        initComponents();
    }

    public void makeVisible() {
        this.setSize(new Dimension(800,300));
        setLocationRelativeTo(null);
        menuPanel.setEnabled(true);
        exitButton.setEnabled(true);
        backButton.setEnabled(true);
        textFieldSup.setEnabled(true);
        labelSuport.setEnabled(true);
        textFieldConfi.setEnabled(true);
        labelConfi.setEnabled(true);
        VALIDARButton.setEnabled(true);
        textField1.setEnabled(true);
    }

    public void makeNotVisible() {
        menuPanel.setEnabled(false);
        exitButton.setEnabled(false);
        backButton.setEnabled(false);
        textFieldSup.setEnabled(false);
        labelSuport.setEnabled(false);
        textFieldConfi.setEnabled(false);
        labelConfi.setEnabled(false);
        VALIDARButton.setEnabled(false);
        textField1.setEnabled(false);
    }



    void initComponents(){
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

        VALIDARButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    VALIDARButtonmouseClicked(e);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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

    private void backMouseClicked(MouseEvent e) {
        ctrlPresentation.switchFromVRtoA();
    }

    private void exitButtonmouseClicked(MouseEvent e) {
        ctrlPresentation.exit();
    }

    private void VALIDARButtonmouseClicked(MouseEvent e) throws IOException {
        path = ctrlPresentation.getPathV() + textField1.getText() + ".csv";
        ctrlPresentation.validarRegles(Double.parseDouble(textFieldSup.getText()) ,Double.parseDouble(textFieldConfi.getText()), textField1.getText());
        ctrlPresentation.switchFromVRtoIRV(path);

    }


}
