package sample;

import org.jfree.ui.tabbedui.VerticalLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WindowWithDiagrams extends JFrame{

    public WindowWithDiagrams(String teamName, String allOrHomeOrAway, String season, String lastOrFullSeason, Selector selector){
        super(teamName + ". Диаграммы");
        this.setLayout(new BorderLayout());
        this.setLocation(10, 10);
        this.setMinimumSize(new Dimension(400, 400));

        File fileTI = new File("images/" + teamName + ".png");
        BufferedImage bimg = null;
        try {
            bimg = ImageIO.read(fileTI);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image scaled = bimg.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel teamImage = new JLabel(new ImageIcon(scaled));
        teamImage.setBorder(new EmptyBorder(5,0,0,0));
        this.add(teamImage, BorderLayout.NORTH);

        JScrollPane jsp;
        Graphic graphic = new Graphic(0, teamName);
        JPanel diagramContainer = graphic.getDiagrams(teamName, allOrHomeOrAway, season, lastOrFullSeason, selector);

        jsp = new JScrollPane(diagramContainer);
        jsp.setVerticalScrollBar( new JScrollBar() {
            public int getUnitIncrement( int direction ) {
                return 50;
            }
        } );

        this.add(jsp);

        this.setSize(650,700);
        Settings settings = Settings.getSettingsFromFile();
        if (settings.windowsOnTop)
            this.setAlwaysOnTop(true);
    }
}
