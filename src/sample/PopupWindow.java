package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class PopupWindow extends JFrame{

    public PopupWindow(String text){
        super("Внимание!");
        this.setResizable(false);
        setSize(600,200);
        setLocation(200, 200);
        Font font = new Font("", 0, 20);
        final JLabel label = new JLabel(text);
        label.setFont(font);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(label, BorderLayout.CENTER);
        this.setVisible(true);
        this.pack();

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            // ...
            public void windowClosing(WindowEvent event) {

                if (label.getText().contains("не зарегистрированы") || label.getText().contains("ограничена")){
                    System.exit(0);
                }

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }
}
