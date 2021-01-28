package sample;

import javax.swing.*;
import java.awt.*;

public class WindowMessage extends JFrame {

    public WindowMessage(String text){
        super("Внимание!");

        JPanel panelText = new JPanel(new BorderLayout());

        JLabel label = new JLabel(text);
        label.setFont(new Font("", Font.BOLD, 18));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelText.add(label);

        this.add(label);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        this.pack();

    }

}
