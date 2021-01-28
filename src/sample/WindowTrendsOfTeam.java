package sample;

import org.jfree.ui.tabbedui.VerticalLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WindowTrendsOfTeam extends JFrame{
    Settings settings = Settings.getSettingsFromFile();

    public WindowTrendsOfTeam(String team,  String allOrHomeOrAway, String season, String lastOrFullSeason, Selector selector){
        super(team + " .Тренды");
        TrendMaker tm = new TrendMaker("all", Team.getLeague(team));
        tm.analyzeTrends(team, selector);

        this.setLayout(new BorderLayout());
        this.setLocation(10, 10);
        this.setMinimumSize(new Dimension(400, 400));

        File fileTI = new File("images/" + team + ".png");
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

        String headerText = "";
        if (lastOrFullSeason.contains("Весь сезон")){
            if (allOrHomeOrAway.contains("Все")){
                headerText = "Тренды за все игры " + team + " в сезоне " + season;
            }
            if (allOrHomeOrAway.contains("Дома")){
                headerText = "Тренды за все домашние игры " + team + " в сезоне " + season;
            }
            if (allOrHomeOrAway.contains("На выезде")){
                headerText = "Тренды за все гостевые игры " + team + " в сезоне " + season;
            }
        } else {
            if (allOrHomeOrAway.contains("Все")){
                headerText = "Тренды последних " + selector.listOfMatches.size() + " игр " + team + " в сезоне " + season;
            }
            if (allOrHomeOrAway.contains("Дома")){
                headerText = "Тренды последних " + selector.listOfMatches.size() + " домашних игр " + team + " в сезоне " + season;
            }
            if (allOrHomeOrAway.contains("На выезде")){
                headerText = "Тренды последних " + selector.listOfMatches.size() + " гостевых игр " + team + " в сезоне " + season;
            }
        }

        JScrollPane jsp;
        JPanel trendContainer = new JPanel(new VerticalLayout());

        JLabel jtf = new JLabel(headerText);
        jtf.setFont(new Font("", 0, 18));
        jtf.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        jtf.setBorder(new EmptyBorder(10,10,0,10));
        trendContainer.add(jtf);
        trendContainer.setBorder(new EmptyBorder(10,10,0,10));



        for (int i=0; i<tm.resultList.size(); i++){
            Color color = TrendMaker.chooseColorForLabel(tm.resultList.get(i));
            JLabel trends = new JLabel();
            trends.setFont(new Font("", 0, 18));
            trends.setText(tm.resultList.get(i));
            trends.setBackground(color);
            trends.setOpaque(true);
            trendContainer.add(trends);
        }

        if (tm.resultList.size() == 0)
            trendContainer.add(new JLabel("<html>Трендов с проходимостью >" + tm.percentBorder + "%" + "<br>в отобранных матчах " + team + " не найдено.</html>\""));



        /*JLabel trends = new JLabel();
        trends.setFont(new Font("", 0, 18));

        String textTrends = "<html>";
        for (int i=1; i<=tm.resultList.size(); i++){
            textTrends = textTrends + i + ". " + tm.resultList.get(i-1) + "<br>";
        }
        textTrends = textTrends + "</html>";

        if (tm.resultList.size() == 0)
            textTrends = "<html>Трендов с проходимостью >" + tm.percentBorder + "%" + "<br>в отобранных матчах " + team + " не найдено.</html>\"";

        trends.setText(textTrends);
        trends.setBorder(new EmptyBorder(10,10,10,10));
        trends.setBackground(new Color(50, 205, 50));
        trends.setOpaque(true);
        trendContainer.add(trends);*/

        jsp = new JScrollPane(trendContainer);
        jsp.setVerticalScrollBar( new JScrollBar() {
            public int getUnitIncrement( int direction ) {
                return 50;
            }
        } );

        this.add(jsp);
        if (settings.windowsOnTop)
            setAlwaysOnTop(true);

        this.setSize(700,700);
        this.pack();
    }
}
