package sample;

import org.jfree.ui.tabbedui.VerticalLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WindowTrendsForTwoTeams extends JFrame{
    public WindowTrendsForTwoTeams(String homeTeam, String awayTeam, String season, String allOrHA, String lastOrFullSeason, Selector selectorHT, Selector selectorAT){
        super("Тренды команд " + homeTeam + " и " + awayTeam);
        JPanel mainPanel = new JPanel(new VerticalLayout());

        TrendMaker tm = new TrendMaker("all", Team.getLeague(homeTeam));
        tm.analyzeTrends(homeTeam, selectorHT);

        this.setLayout(new BorderLayout());
        this.setLocation(0, 0);

        JPanel homeTeamPanel = new JPanel(new BorderLayout());
        File fileTI = new File("images/" + homeTeam + ".png");
        BufferedImage bimg = null;
        try {
            bimg = ImageIO.read(fileTI);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image scaled = bimg.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel teamImage = new JLabel(new ImageIcon(scaled));
        teamImage.setBorder(new EmptyBorder(5,0,5,0));
        homeTeamPanel.add(teamImage, BorderLayout.NORTH);

        String headerText = "";
        if (allOrHA.contains("Все матчи")){
            headerText = "Тренды за все игры " + homeTeam + " в сезоне " + season;
        } else {
            headerText = "Тренды за все домашние игры " + homeTeam + " в сезоне " + season;
        }

        JPanel trendContainerHT = new JPanel(new VerticalLayout());

        JLabel jtf = new JLabel(headerText);
        jtf.setFont(new Font("", 0, 18));
        jtf.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        trendContainerHT.add(jtf);
        trendContainerHT.setBorder(new EmptyBorder(5,10,0,10));

        for (int i=0; i<tm.resultList.size(); i++){
            Color color = TrendMaker.chooseColorForLabel(tm.resultList.get(i));
            JLabel trends = new JLabel();
            trends.setFont(new Font("", 0, 18));
            trends.setText(tm.resultList.get(i));
            trends.setBackground(color);
            trends.setOpaque(true);
            trendContainerHT.add(trends);
        }

        if (tm.resultList.size() == 0){
            JLabel trends = new JLabel();
            trends.setFont(new Font("", 0, 18));
            trends.setText("<html>Трендов с проходимостью >" + tm.percentBorder + "%" + "<br>в отобранных матчах " + homeTeam + " не найдено.</html>\"");
            trendContainerHT.add(trends);
        }

        homeTeamPanel.add(trendContainerHT);

        tm = new TrendMaker("all", Team.getLeague(awayTeam));
        tm.analyzeTrends(awayTeam, selectorAT);

        mainPanel.add(homeTeamPanel);

        JPanel awayTeamPanel = new JPanel(new BorderLayout());
        fileTI = new File("images/" + awayTeam + ".png");
        bimg = null;
        try {
            bimg = ImageIO.read(fileTI);
        } catch (IOException e) {
            e.printStackTrace();
        }
        scaled = bimg.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        teamImage = new JLabel(new ImageIcon(scaled));
        teamImage.setBorder(new EmptyBorder(5,0,5,0));
        awayTeamPanel.add(teamImage, BorderLayout.NORTH);

        headerText = "";
        if (allOrHA.contains("Все матчи")){
            headerText = "Тренды за все игры " + awayTeam + " в сезоне " + season;
        } else {
            headerText = "Тренды за все гостевые игры " + awayTeam + " в сезоне " + season;
        }

        JPanel trendContainerAT = new JPanel(new VerticalLayout());

        jtf = new JLabel(headerText);
        jtf.setFont(new Font("", 0, 18));
        jtf.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        trendContainerAT.add(jtf);
        trendContainerAT.setBorder(new EmptyBorder(5,10,0,10));

        for (int i=0; i<tm.resultList.size(); i++){
            Color color = TrendMaker.chooseColorForLabel(tm.resultList.get(i));
            JLabel trends = new JLabel();
            trends.setFont(new Font("", 0, 18));
            trends.setText(tm.resultList.get(i));
            trends.setBackground(color);
            trends.setOpaque(true);
            trendContainerAT.add(trends);
        }

        if (tm.resultList.size() == 0){
            JLabel trends = new JLabel();
            trends.setFont(new Font("", 0, 18));
            trends.setText("<html>Трендов с проходимостью >" + tm.percentBorder + "%" + "<br>в отобранных матчах " + awayTeam + " не найдено.</html>\"");
            trendContainerAT.add(trends);
        }

        awayTeamPanel.add(trendContainerAT);
        mainPanel.add(awayTeamPanel);

        this.add(new JScrollPane(mainPanel));

        Settings settings = Settings.getSettingsFromFile();
        this.setSize(Integer.parseInt(settings.getWindowResolution().split("x")[0]),
                     Integer.parseInt(settings.getWindowResolution().split("x")[1]));
        this.pack();

        if (settings.windowsOnTop)
            this.setAlwaysOnTop(true);
    }
}
