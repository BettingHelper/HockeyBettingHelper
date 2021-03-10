package sample;

import org.jfree.ui.tabbedui.VerticalLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class TrendsThread extends Thread{
    final String path;
    String leagueName;
    String parameter;
    String seasonString;
    String lastOrFull;
    JFrame tw;
    PanelTrends panelTrends;
    JProgressBar jpb;
    int numberOfTeams;
    boolean trendsHA;
    ArrayList<String> resultList;

    public TrendsThread(String leagueName, final String parameter, String seasonString, String lastOrFull, PanelTrends pt, ArrayList<String> resultList, boolean trendsHA){
        path = "database/";
        this.leagueName = leagueName;
        this.parameter = parameter;
        this.seasonString = seasonString;
        this.lastOrFull = lastOrFull;
        this.panelTrends = pt;
        this.resultList = resultList;
        this.trendsHA = trendsHA;
        numberOfTeams = Settings.getNumberOfTeamsInLeague(leagueName, seasonString);

        tw = new JFrame("Внимание!");
        tw.setResizable(false);
        tw.setLayout(new BorderLayout());
        tw.setSize(500, 200);
        tw.setLocation(200, 200);

        JLabel label = new JLabel("Подождите, идет расчет трендов");
        label.setFont(new Font("", Font.BOLD, 20));
        tw.add(label, BorderLayout.NORTH);

        jpb = new JProgressBar(0, 100);
        jpb = new JProgressBar(0, 100);
        jpb.setPreferredSize(new Dimension(600, 50));
        jpb.setStringPainted(true);
        tw.add(jpb, BorderLayout.SOUTH);

        tw.setVisible(true);
    }

    @Override
    public void run(){
        //tw.setVisible(true);
        //jpb.setValue(0);

        if (trendsHA) {
            JPanel[] arr = refreshDataForHomeAway(panelTrends.leagueName, panelTrends.parameter, lastOrFull, panelTrends.season, resultList);
            if (panelTrends.infoPanel.getComponentCount() > 0) {
                panelTrends.infoPanel.removeAll();
            }

            panelTrends.infoPanel.add(arr[0]);
            panelTrends.infoPanel.add(arr[1]);
            panelTrends.infoPanel.revalidate();
            panelTrends.revalidate();
        } else {
            JPanel panelData = refreshDataForAll(panelTrends.leagueName, panelTrends.parameter, lastOrFull, panelTrends.season, resultList);
            if (panelTrends.infoPanel.getComponentCount() > 0) {
                panelTrends.infoPanel.removeAll();
            }

            panelTrends.infoPanel.add(panelData);
            panelTrends.infoPanel.revalidate();
            panelTrends.revalidate();
        }

        //panelTrends.buttonShowInfo.setFocusable(false);
        tw.setVisible(false);

    }

    public JPanel refreshDataForAll(String leagueName, final String parameter, String lastOrFull, String seasonString, ArrayList<String> resultList){
        JScrollPane scrollPane;
        JPanel result = new JPanel();
        int sizeOfPicture = 60;
        TrendMaker trendMaker = null;
        resultList.add("Тренды за сезон");
        String homeAwayOrAllAll = "All-all";

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(""));
        JLabel header = new JLabel("Тренды по всем играм в " + leagueName);
        header.setFont(new Font("", Font.BOLD, 15));
        header.setBorder(new EmptyBorder(10, 0, 0, 0));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(header, BorderLayout.NORTH);
        JPanel leftDataPanel = new JPanel(new VerticalLayout());

        //this.setCursor(Cursor.getPredefinedCursor (Cursor.WAIT_CURSOR));
        String season = seasonString.replace("Сезон ", "");
        String dir = path + season + "/" + leagueName + "/Matches";
        JFileChooser fileChooser = new JFileChooser(dir);
        String[] directoryList = fileChooser.getCurrentDirectory().list();

        if (!leagueName.contains("Выберите") && directoryList.length > 0){
            Font font = new Font("Arial", Font.BOLD, 15);
            File fileTeams = new File(path + season + "/leagues/" + leagueName + ".txt");

            if (!panelTrends.lastCalculatedLeague.equals(leagueName) || !panelTrends.lastCalculatedNumberOfMatches.equals(lastOrFull) || !panelTrends.lastCalculatedSeason.equals(season) || !panelTrends.lastTrendsHA.equals(homeAwayOrAllAll)){
                panelTrends.arrayList.clear();
                try {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(fileTeams), "UTF-8"));
                    String str;
                    int index = 0;
                    while (((str = in.readLine()) != null)) {
                        boolean hasTrends = false;
                        JPanel teamPanel = new JPanel(new BorderLayout());
                        panelTrends.lastCalculatedLeague = leagueName;
                        panelTrends.lastCalculatedSeason = season;
                        panelTrends.lastCalculatedNumberOfMatches = lastOrFull;
                        panelTrends.lastTrendsHA = "All-all";

                        String text = "<html>";
                        Selector selector = new Selector();
                        selector.getListOfMatches(leagueName, str, "Все матчи", season, lastOrFull);
                        selector.getPList(selector.listOfMatches, str, season);
                        panelTrends.arrayList.add(selector);

                        trendMaker = new TrendMaker(parameter, leagueName);
                        trendMaker.analyzeTrends(str, selector);
                        if (trendMaker.resultList.size()>0){
                            resultList.add(str + ":");
                            text =  text + str + ":<br>";
                            hasTrends = true;
                            for (int i=0; i<trendMaker.resultList.size(); i++){
                                text =  text + (i+1) + ") " + trendMaker.resultList.get(i) + "<br>";
                                resultList.add((i+1) + ") " + trendMaker.resultList.get(i));
                            }
                        }

                        text = text + "</html>";

                        if (hasTrends){
                            JLabel l = new JLabel(text);
                            l.setFont(font);
                            l.setBorder(new EmptyBorder(10,10,10,10));
                            teamPanel.add(l, BorderLayout.CENTER);
                            File fileI = new File("images/" + str + ".png");
                            BufferedImage bimg = null;
                            try {
                                bimg = ImageIO.read(fileI);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Image scaled = bimg.getScaledInstance(sizeOfPicture, sizeOfPicture, Image.SCALE_SMOOTH);
                            JLabel teamImage = new JLabel(new ImageIcon(scaled));
                            teamImage.setBorder(new EmptyBorder(10, 0, 10, 0));
                            teamPanel.add(teamImage, BorderLayout.WEST);
                            leftDataPanel.add(teamPanel);
                        }

                        index++;
                        jpb.setValue((int) (index * 100 / (double) numberOfTeams));

                    }
                    in.close();
                } catch (IOException e)
                {
                    System.out.println(e.getMessage());
                }
            } else{
                for (int k=0; k<panelTrends.arrayList.size(); k++){
                    String teamName = panelTrends.arrayList.get(k).teamName;
                    boolean hasTrends = false;
                    JPanel teamPanel = new JPanel(new BorderLayout());
                    panelTrends.lastCalculatedLeague = leagueName;
                    panelTrends.lastCalculatedSeason = season;
                    panelTrends.lastCalculatedNumberOfMatches = lastOrFull;
                    panelTrends.lastTrendsHA = "All-all";
                    String text = "<html>";

                    trendMaker = new TrendMaker(parameter, leagueName);
                    trendMaker.analyzeTrends(teamName, panelTrends.arrayList.get(k));
                    if (trendMaker.resultList.size()>0){
                        resultList.add(teamName + ":");
                        text =  text + teamName + ":<br>";
                        hasTrends = true;
                        for (int i=0; i<trendMaker.resultList.size(); i++){
                            text =  text + (i+1) + ") " + trendMaker.resultList.get(i) + "<br>";
                            resultList.add((i+1) + ") " + trendMaker.resultList.get(i));
                        }
                    }

                    text = text + "</html>";
                    if (hasTrends){
                        JLabel l = new JLabel(text);
                        l.setFont(font);
                        l.setBorder(new EmptyBorder(10,10,10,10));
                        teamPanel.add(l, BorderLayout.CENTER);
                        File fileI = new File("images/" + teamName + ".png");
                        BufferedImage bimg = null;
                        try {
                            bimg = ImageIO.read(fileI);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Image scaled = bimg.getScaledInstance(sizeOfPicture, sizeOfPicture, Image.SCALE_SMOOTH);
                        JLabel teamImage = new JLabel(new ImageIcon(scaled));
                        teamImage.setBorder(new EmptyBorder(10, 0, 10, 0));
                        teamPanel.add(teamImage, BorderLayout.WEST);
                        leftDataPanel.add(teamPanel);
                    }

                    jpb.setValue((int) ((k+1) * 100 / (double) panelTrends.arrayList.size()));

                }
            }

            //panelTrends.buttonToText.setEnabled(true);

            scrollPane = new JScrollPane(leftDataPanel);
            scrollPane.setVerticalScrollBar( new JScrollBar() {
                public int getUnitIncrement( int direction ) {
                    return 50;
                }
            } );
            panel.add(scrollPane);
            result = panel;

        } else {
            JPanel container = new JPanel();
            container.setLayout(null);
            container.setPreferredSize(new Dimension((int) (0.48 * 500), 730));  // 500 - WIDTH

            final JLabel label = new JLabel("Не выбран чемпионат.");
            if (directoryList.length == 0){
                label.setText("В сезоне " + season + " команды из " + leagueName + " не провели ни одной игры.");
            }

            label.setLocation(10, 0);
            label.setSize(new Dimension((int) (0.995 * 500) - 30, 25));   // 500 - WIDTH
            Font fontLabel = new Font("Arial", Font.BOLD, 15);
            label.setFont(fontLabel);
            container.add(label);

            panelTrends.buttonToText.setEnabled(true);

            scrollPane = new JScrollPane(container);
            scrollPane.setPreferredSize(new Dimension((int) (0.995 * 500), (int) (0.87 * 400)));   // 500 - WIDTH  400 - HEIGHT
            scrollPane.setLocation(5, 50);
            scrollPane.setVerticalScrollBar( new JScrollBar() {
                public int getUnitIncrement( int direction ) {
                    return 50;
                }
            } );
        }
        //this.setCursor(Cursor.getDefaultCursor());

        return result;
    }

    public JPanel[] refreshDataForHomeAway(String leagueName, final String parameter, String lastOrFull, String seasonString, ArrayList<String> resultList){
        JScrollPane scrollPane;
        JPanel[] result = new JPanel[2];
        int sizeOfPicture = 60;
        TrendMaker trendMaker = null;
        String homeAwayOrAllAll = "Home-away";
        //resultList = new ArrayList();
        resultList.add("Домашние тренды");
        resultList.add("Гостевые тренды");

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder(""));
        JLabel leftHeader = new JLabel("Домашние матчи");
        leftHeader.setFont(new Font("", Font.BOLD, 15));
        leftHeader.setBorder(new EmptyBorder(10,0,0,0));
        leftHeader.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(leftHeader, BorderLayout.NORTH);
        JPanel leftDataPanel = new JPanel(new VerticalLayout());

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder(""));
        JLabel rightHeader = new JLabel("Выездные матчи");
        rightHeader.setFont(new Font("", Font.BOLD, 15));
        rightHeader.setBorder(new EmptyBorder(10,0,0,0));
        rightHeader.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(rightHeader, BorderLayout.NORTH);
        JPanel rightDataPanel = new JPanel(new VerticalLayout());

        //this.setCursor(Cursor.getPredefinedCursor (Cursor.WAIT_CURSOR));
        String season = seasonString.replace("Сезон ", "");
        String dir = path + season + "/" + leagueName + "/Matches";
        JFileChooser fileChooser = new JFileChooser(dir);
        String[] directoryList = fileChooser.getCurrentDirectory().list();

        if (!leagueName.contains("Выберите") && directoryList.length > 0){
            Font font = new Font("Arial", Font.BOLD, 15);
            File fileTeams = new File(path + season + "/leagues/" + leagueName + ".txt");

            if (!panelTrends.lastCalculatedLeague.equals(leagueName) || !panelTrends.lastCalculatedNumberOfMatches.equals(lastOrFull) || !panelTrends.lastCalculatedSeason.equals(season) || !panelTrends.lastTrendsHA.equals(homeAwayOrAllAll)){
                panelTrends.arrayList.clear();
                try {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(fileTeams), "UTF-8"));
                    String str;
                    int index = 0;
                    while (((str = in.readLine()) != null)) {
                        boolean hasTrends = false;
                        JPanel teamPanel = new JPanel(new BorderLayout());
                        panelTrends.lastCalculatedLeague = leagueName;
                        panelTrends.lastCalculatedSeason = season;
                        panelTrends.lastCalculatedNumberOfMatches = lastOrFull;
                        panelTrends.lastTrendsHA = "Home-away";
                        String text = "<html>";
                        Selector selectorHT = new Selector();
                        selectorHT.getListOfMatches(leagueName, str, "Дома", season, lastOrFull);
                        selectorHT.getPList(selectorHT.listOfMatches, str, season);
                        panelTrends.arrayList.add(selectorHT);

                        Selector selectorAT = new Selector();
                        selectorAT.getListOfMatches(leagueName, str, "На выезде", season, lastOrFull);
                        selectorAT.getPList(selectorAT.listOfMatches, str, season);
                        panelTrends.arrayList.add(selectorAT);

                        trendMaker = new TrendMaker(parameter, leagueName);
                        trendMaker.analyzeTrends(str, selectorHT);
                        if (trendMaker.resultList.size()>0){
                            int indexToInsert = resultList.indexOf("Гостевые тренды");
                            resultList.add(indexToInsert, str + ":");
                            text =  text + str + ":<br>";
                            hasTrends = true;
                            for (int i=0; i<trendMaker.resultList.size(); i++){
                                text =  text + (i+1) + ") " + trendMaker.resultList.get(i) + "<br>";
                                resultList.add(indexToInsert+1, (i+1) + ") " + trendMaker.resultList.get(i));
                                indexToInsert++;
                            }
                        }

                        text = text + "</html>";
                        if (hasTrends){
                            JLabel l = new JLabel(text);
                            l.setFont(font);
                            l.setBorder(new EmptyBorder(10,10,10,10));
                            teamPanel.add(l, BorderLayout.CENTER);
                            File fileI = new File("images/" + str + ".png");
                            BufferedImage bimg = null;
                            try {
                                bimg = ImageIO.read(fileI);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Image scaled = bimg.getScaledInstance(sizeOfPicture, sizeOfPicture, Image.SCALE_SMOOTH);
                            JLabel teamImage = new JLabel(new ImageIcon(scaled));
                            teamImage.setBorder(new EmptyBorder(10, 0, 10, 0));
                            teamPanel.add(teamImage, BorderLayout.WEST);
                            leftDataPanel.add(teamPanel);
                        }

                        text = "<html>";
                        hasTrends = false;
                        teamPanel = new JPanel(new BorderLayout());
                        trendMaker = new TrendMaker(parameter, leagueName);
                        trendMaker.analyzeTrends(str, selectorAT);
                        if (trendMaker.resultList.size()>0){
                            resultList.add(str + ":");
                            text =  text + str + ":<br>";
                            hasTrends = true;
                            for (int i=0; i<trendMaker.resultList.size(); i++){
                                text = text + (i+1) + ") " + trendMaker.resultList.get(i) + "<br>";
                                resultList.add((i+1) + ") " + trendMaker.resultList.get(i));
                            }
                        }
                        text = text + "</html>";
                        if (hasTrends){
                            JLabel l = new JLabel(text);
                            l.setFont(font);
                            l.setBorder(new EmptyBorder(10,10,10,10));
                            teamPanel.add(l, BorderLayout.CENTER);
                            File fileI = new File("images/" + str + ".png");
                            BufferedImage bimg = null;
                            try {
                                bimg = ImageIO.read(fileI);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Image scaled = bimg.getScaledInstance(sizeOfPicture, sizeOfPicture, Image.SCALE_SMOOTH);
                            JLabel teamImage = new JLabel(new ImageIcon(scaled));
                            teamImage.setBorder(new EmptyBorder(10, 0, 10, 0));
                            teamPanel.add(teamImage, BorderLayout.WEST);
                            rightDataPanel.add(teamPanel);
                        }
                        index++;
                        jpb.setValue((int) (index * 100 / (double) numberOfTeams));

                    }
                    in.close();
                } catch (IOException e)
                {
                    System.out.println(e.getMessage());
                }
            } else{
                for (int k=0; k<panelTrends.arrayList.size(); k=k+2){
                    String teamName = panelTrends.arrayList.get(k).teamName;
                    boolean hasTrends = false;
                    JPanel teamPanel = new JPanel(new BorderLayout());
                    panelTrends.lastCalculatedLeague = leagueName;
                    panelTrends.lastCalculatedSeason = season;
                    panelTrends.lastCalculatedNumberOfMatches = lastOrFull;
                    panelTrends.lastTrendsHA = "Home-away";
                    String text = "<html>";

                    trendMaker = new TrendMaker(parameter, leagueName);
                    trendMaker.analyzeTrends(teamName, panelTrends.arrayList.get(k));
                    if (trendMaker.resultList.size()>0){
                        int indexToInsert = resultList.indexOf("Гостевые тренды");
                        resultList.add(indexToInsert, teamName + ":");
                        text =  text + teamName + ":<br>";
                        hasTrends = true;
                        for (int i=0; i<trendMaker.resultList.size(); i++){
                            text =  text + (i+1) + ") " + trendMaker.resultList.get(i) + "<br>";
                            resultList.add(indexToInsert+1, (i+1) + ") " + trendMaker.resultList.get(i));
                            indexToInsert++;
                        }
                    }

                    text = text + "</html>";
                    if (hasTrends){
                        JLabel l = new JLabel(text);
                        l.setFont(font);
                        l.setBorder(new EmptyBorder(10,10,10,10));
                        teamPanel.add(l, BorderLayout.CENTER);
                        File fileI = new File("images/" + teamName + ".png");
                        BufferedImage bimg = null;
                        try {
                            bimg = ImageIO.read(fileI);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Image scaled = bimg.getScaledInstance(sizeOfPicture, sizeOfPicture, Image.SCALE_SMOOTH);
                        JLabel teamImage = new JLabel(new ImageIcon(scaled));
                        teamImage.setBorder(new EmptyBorder(10, 0, 10, 0));
                        teamPanel.add(teamImage, BorderLayout.WEST);
                        leftDataPanel.add(teamPanel);
                    }

                    text = "<html>";
                    hasTrends = false;
                    teamPanel = new JPanel(new BorderLayout());
                    trendMaker = new TrendMaker(parameter, leagueName);
                    trendMaker.analyzeTrends(teamName, panelTrends.arrayList.get(k+1));
                    if (trendMaker.resultList.size()>0){
                        resultList.add(teamName + ":");
                        text =  text + teamName + ":<br>";
                        hasTrends = true;
                        for (int i=0; i<trendMaker.resultList.size(); i++){
                            text = text + (i+1) + ") " + trendMaker.resultList.get(i) + "<br>";
                            resultList.add((i+1) + ") " + trendMaker.resultList.get(i));
                        }
                    }
                    text = text + "</html>";
                    if (hasTrends){
                        JLabel l = new JLabel(text);
                        l.setFont(font);
                        l.setBorder(new EmptyBorder(10,10,10,10));
                        teamPanel.add(l, BorderLayout.CENTER);
                        File fileI = new File("images/" + teamName + ".png");
                        BufferedImage bimg = null;
                        try {
                            bimg = ImageIO.read(fileI);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Image scaled = bimg.getScaledInstance(sizeOfPicture, sizeOfPicture, Image.SCALE_SMOOTH);
                        JLabel teamImage = new JLabel(new ImageIcon(scaled));
                        teamImage.setBorder(new EmptyBorder(10, 0, 10, 0));
                        teamPanel.add(teamImage, BorderLayout.WEST);
                        rightDataPanel.add(teamPanel);
                    }

                    jpb.setValue((int) ((k+1) * 100 / (double) panelTrends.arrayList.size()));

                }
            }

            panelTrends.buttonToText.setEnabled(true);

            scrollPane = new JScrollPane(leftDataPanel);
            scrollPane.setVerticalScrollBar( new JScrollBar() {
                public int getUnitIncrement( int direction ) {
                    return 50;
                }
            } );
            leftPanel.add(scrollPane);
            result[0] = leftPanel;

            scrollPane = new JScrollPane(rightDataPanel);
            scrollPane.setVerticalScrollBar( new JScrollBar() {
                public int getUnitIncrement( int direction ) {
                    return 50;
                }
            } );
            rightPanel.add(scrollPane);
            result[1] = rightPanel;

        } else {
            JPanel container = new JPanel();
            container.setLayout(null);
            container.setPreferredSize(new Dimension((int) (0.48 * 500), 730));  // 500 - WIDTH

            final JLabel label = new JLabel("Не выбран чемпионат.");
            if (directoryList.length == 0){
                label.setText("В сезоне " + season + " команды из " + leagueName + " не провели ни одной игры.");
            }

            label.setLocation(10, 0);
            label.setSize(new Dimension((int) (0.995 * 500) - 30, 25));   // 500 - WIDTH
            Font fontLabel = new Font("Arial", Font.BOLD, 15);
            label.setFont(fontLabel);
            container.add(label);

            scrollPane = new JScrollPane(container);
            scrollPane.setPreferredSize(new Dimension((int) (0.995 * 500), (int) (0.87 * 400)));   // 500 - WIDTH  400 - HEIGHT
            scrollPane.setLocation(5, 50);
            scrollPane.setVerticalScrollBar( new JScrollBar() {
                public int getUnitIncrement( int direction ) {
                    return 50;
                }
            } );
        }
        //this.setCursor(Cursor.getDefaultCursor());

        return result;
    }


}
