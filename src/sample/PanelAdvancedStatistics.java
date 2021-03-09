package sample;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class PanelAdvancedStatistics extends JPanel{
    //int WIDTH;
    //int DEFWIDTH = 1600;
    //double procWIDTH;
    //int HEIGHT;
    //int DEFHEIGHT = 1000;
    int graphicHeight = 350;
    //double procHEIGHT;
    JComboBox<String> leftSeasonChooser;
    JComboBox<String> leftLeagueChooser;
    JComboBox<String> leftTeamChooser;
    JComboBox<String> leftTeamAllOrHomeOrAway;
    JComboBox<String> leftLastOrFullSeason;
    JPanel leftTeamPanel;
    JPanel leftInfoPanel;
    JButton leftButtonShowInfo;

    JComboBox<String> rightSeasonChooser;
    JComboBox<String> rightLeagueChooser;
    JComboBox<String> rightTeamChooser;
    JComboBox<String> rightTeamAllOrHomeOrAway;
    JComboBox<String> rightLastOrFullSeason;
    JPanel rightTeamPanel;
    JPanel rightInfoPanel;
    JButton rightButtonShowInfo;

    int heightOfTable = 0;
    final Font font15 = new Font("Arial", Font.BOLD, 15);
    final Font font16 = new Font("Arial", Font.BOLD, 16);
    final Font font18 = new Font("Arial", Font.BOLD, 18);
    final Font font20 = new Font("Arial", Font.BOLD, 20);


    public PanelAdvancedStatistics() {
        this.setLayout(new GridLayout(1,2));
        //WIDTH = width;
        //HEIGHT = height;
        //procWIDTH = WIDTH / (double) DEFWIDTH;
        //procHEIGHT = HEIGHT / (double) DEFHEIGHT;
        final String path = "database/";
        final String curSeason = Settings.getCurrentSeason();

        ////////////////////////////////////////////ЛЕВАЯ ПАНЕЛЬ
        leftTeamPanel = new JPanel(new BorderLayout());
        leftTeamPanel.setBorder(BorderFactory.createTitledBorder(""));

        JPanel leftPanelChoosers = new JPanel(new GridLayout(1, 4, 5, 5));
        ArrayList<String> listOfSeasons = Settings.getListOfSeasons();
        String[] seasonList = new String[listOfSeasons.size()];
        for (int i = 0; i < seasonList.length; i++)
            seasonList[i] = "Сезон " + listOfSeasons.get(i);
        leftSeasonChooser = new JComboBox<>(seasonList);
        leftPanelChoosers.add(leftSeasonChooser);

        JFileChooser leftFileChooser = new JFileChooser(path + curSeason + "/leagues");
        String[] leftDirectoryList = leftFileChooser.getCurrentDirectory().list();
        ArrayList<String> leftLeagueList = new ArrayList<>();
        leftLeagueList.add("Лига");
        for (String aDirectoryList : leftDirectoryList) leftLeagueList.add(aDirectoryList.replace(".txt", ""));
        String[] leftListOfLeagues = new String[leftLeagueList.size()];
        for (int i = 0; i < leftListOfLeagues.length; i++)
            leftListOfLeagues[i] = leftLeagueList.get(i);
        leftLeagueChooser = new JComboBox<>(leftListOfLeagues);
//        leftLeagueChooser.setPreferredSize(new Dimension((int) (90 * procWIDTH), 30));
        leftPanelChoosers.add(leftLeagueChooser);

        String fileNameLeft = path + curSeason + "/leagues/" + leftLeagueChooser.getSelectedItem() + ".txt";
        String[] listLeft = {"Команда"};
        if (!fileNameLeft.contains("Лига")) {
            listLeft = Main.readTxtFile(fileNameLeft);
        }
        leftTeamChooser = new JComboBox<>(listLeft);
//        leftTeamChooser.setPreferredSize(new Dimension((int) (120 * procWIDTH), 30));
        leftTeamChooser.setEnabled(false);
        leftPanelChoosers.add(leftTeamChooser);

        final String[] leftAllOrHomeOrAway = {"Все матчи", "Дома", "На выезде"};
        leftTeamAllOrHomeOrAway = new JComboBox<>(leftAllOrHomeOrAway);
//        leftTeamAllOrHomeOrAway.setPreferredSize(new Dimension((int) (120 * procWIDTH), 30));
        leftPanelChoosers.add(leftTeamAllOrHomeOrAway);

        final String[] leftLastOrFullSeasonS = {"Весь сезон", "Последние 3", "Последние 4", "Последние 5", "Последние 6", "Последние 7", "Последние 8", "Последние 9", "Последние 10", "Последние 15", "Последние 20"};
        leftLastOrFullSeason = new JComboBox<>(leftLastOrFullSeasonS);
//        leftTeamLastOrFullSeason.setPreferredSize(new Dimension((int) (140 * procWIDTH), 30));
        leftPanelChoosers.add(leftLastOrFullSeason);

        leftButtonShowInfo = new JButton("Отобразить!");
//        leftButtonShowInfo.setPreferredSize(new Dimension((int) (150 * procWIDTH), 30));
        Font fontForButton = new Font("", 0, 15);
        leftButtonShowInfo.setFont(fontForButton);
        leftButtonShowInfo.setMargin(new Insets(0, 0, 0, 0));
        leftPanelChoosers.add(leftButtonShowInfo);

        leftTeamPanel.add(leftPanelChoosers, BorderLayout.NORTH);

        leftInfoPanel = new JPanel();
        leftTeamPanel.add(leftInfoPanel, BorderLayout.CENTER);

        this.add(leftTeamPanel);

        leftSeasonChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                leftSeasonChooser.setFocusable(false);

                String team = String.valueOf(leftTeamChooser.getSelectedItem());
                String league = String.valueOf(leftLeagueChooser.getSelectedItem());

                String pathToLeaguesList = path + leftSeasonChooser.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/";
                JFileChooser leftFileChooser = new JFileChooser(pathToLeaguesList);
                String[] leftDirectoryList = new String[leftFileChooser.getCurrentDirectory().list().length + 1];
                leftDirectoryList[0] = "Выберите лигу";
                for (int i = 1; i < leftDirectoryList.length; i++)
                    leftDirectoryList[i] = leftFileChooser.getCurrentDirectory().list()[i - 1].replace(".txt", "");
                DefaultComboBoxModel modelH = new DefaultComboBoxModel(leftDirectoryList);
                leftLeagueChooser.setModel(modelH);

                for (int i = 0; i < leftDirectoryList.length; i++) {
                    if (leftDirectoryList[i].equals(league))
                        leftLeagueChooser.setSelectedItem(league);
                }

                String pathToTeamsList = path + leftSeasonChooser.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/" + leftLeagueChooser.getSelectedItem() + ".txt";
                String[] listLeft = {"Выберите команду"};
                if (!pathToTeamsList.contains("ыберите")) {
                    listLeft = Main.readTxtFile(pathToTeamsList);
                }
                modelH = new DefaultComboBoxModel(listLeft);
                leftTeamChooser.setModel(modelH);
                leftTeamChooser.setEnabled(false);

                pathToTeamsList = path + leftSeasonChooser.getSelectedItem().toString().replace("Сезон ", "") + "/" + league + "/Teams/";
                leftFileChooser = new JFileChooser(pathToTeamsList);
                leftDirectoryList = new String[leftFileChooser.getCurrentDirectory().list().length + 1];
                leftDirectoryList[0] = "Выберите команду";
                for (int i = 1; i < leftDirectoryList.length; i++)
                    leftDirectoryList[i] = leftFileChooser.getCurrentDirectory().list()[i - 1].replace(".xml", "");
                for (int i = 0; i < leftDirectoryList.length; i++) {
                    if (leftDirectoryList[i].equals(team)) {
                        leftTeamChooser.setSelectedItem(team);
                        leftTeamChooser.setEnabled(true);
                    }
                }
                leftLeagueChooser.setFocusable(true);
            }
        });

        leftLeagueChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = leftLeagueChooser.getSelectedIndex();
                String pathToLeaguesList = path + leftSeasonChooser.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/";
                JFileChooser leftFileChooser = new JFileChooser(pathToLeaguesList);
                String[] leftDirectoryList = new String[leftFileChooser.getCurrentDirectory().list().length+1];
                leftDirectoryList[0] = "Лига";
                for (int i=1; i<leftDirectoryList.length; i++)
                    leftDirectoryList[i] = leftFileChooser.getCurrentDirectory().list()[i-1].replace(".txt", "");
                DefaultComboBoxModel modelH = new DefaultComboBoxModel(leftDirectoryList);
                leftLeagueChooser.setModel(modelH);
                leftLeagueChooser.setSelectedIndex(index);

                leftTeamChooser.setEnabled(true);
                String pathToTeamsList = path + leftSeasonChooser.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/" + leftLeagueChooser.getSelectedItem() + ".txt";
                String[] listLeft = {"Команда"};
                if (!pathToTeamsList.contains("Лига")) {
                    listLeft = Main.readTxtFile(pathToTeamsList);
                }
                modelH = new DefaultComboBoxModel(listLeft);
                leftTeamChooser.setModel(modelH);
            }
        });

        leftTeamChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        leftTeamAllOrHomeOrAway.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        leftLastOrFullSeason.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        leftButtonShowInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (leftInfoPanel != null) {
                    leftTeamPanel.remove(leftInfoPanel);
                }
                leftInfoPanel = refreshData((String) leftLeagueChooser.getSelectedItem(),
                        (String) leftTeamChooser.getSelectedItem(),
                        (String) leftTeamAllOrHomeOrAway.getSelectedItem(),
                        (String) leftSeasonChooser.getSelectedItem(),
                        (String) leftLastOrFullSeason.getSelectedItem()
                );

                leftTeamPanel.add(leftInfoPanel);
                leftTeamPanel.revalidate();
                leftButtonShowInfo.setFocusable(false);
            }
        });

        ////////////////////////////////////////////ПРАВАЯ ПАНЕЛЬ
        rightTeamPanel = new JPanel(new BorderLayout());
        rightTeamPanel.setBorder(BorderFactory.createTitledBorder(""));

        JPanel rightPanelChoosers = new JPanel();
        rightPanelChoosers.setLayout(new GridLayout(1, 4, 5, 5));

        rightSeasonChooser = new JComboBox<>(seasonList);
//        rightSeasonChooser.setPreferredSize(new Dimension((int) (120 * procWIDTH), 30));
        rightPanelChoosers.add(rightSeasonChooser);

        JFileChooser rightFileChooser = new JFileChooser(path + curSeason + "/leagues");
        String[] rightDirectoryList = rightFileChooser.getCurrentDirectory().list();
        ArrayList<String> rightLeagueList = new ArrayList<>();
        rightLeagueList.add("Лига");
        for (String aDirectoryList : rightDirectoryList) rightLeagueList.add(aDirectoryList.replace(".txt", ""));
        String[] rightListOfLeagues = new String[rightLeagueList.size()];
        for (int i = 0; i < rightListOfLeagues.length; i++)
            rightListOfLeagues[i] = rightLeagueList.get(i);
        rightLeagueChooser = new JComboBox<>(rightListOfLeagues);
//        rightLeagueChooser.setPreferredSize(new Dimension((int) (90 * procWIDTH), 30));
        rightPanelChoosers.add(rightLeagueChooser);

        String fileNameRight = path + curSeason + "/leagues/" + rightLeagueChooser.getSelectedItem() + ".txt";
        String[] listRight = {"Команда"};
        if (!fileNameRight.contains("Лига")) {
            listRight = Main.readTxtFile(fileNameRight);
        }
        rightTeamChooser = new JComboBox<>(listRight);
//        rightTeamChooser.setPreferredSize(new Dimension((int) (120 * procWIDTH), 30));
        rightTeamChooser.setEnabled(false);
        rightPanelChoosers.add(rightTeamChooser);

        String[] rightAllOrHomeOrAway = {"Все матчи", "Дома", "На выезде"};
        rightTeamAllOrHomeOrAway = new JComboBox<>(rightAllOrHomeOrAway);
//        rightTeamAllOrHomeOrAway.setPreferredSize(new Dimension((int) (120 * procWIDTH), 30));
        rightPanelChoosers.add(rightTeamAllOrHomeOrAway);

        final String[] rightLastOrFullSeasonS = {"Весь сезон", "Последние 3", "Последние 4", "Последние 5", "Последние 6", "Последние 7", "Последние 8", "Последние 9", "Последние 10", "Последние 15", "Последние 20"};
        rightLastOrFullSeason = new JComboBox<>(rightLastOrFullSeasonS);
//        rightTeamLastOrFullSeason.setPreferredSize(new Dimension((int) (140 * procWIDTH), 30));
        rightPanelChoosers.add(rightLastOrFullSeason);

        rightButtonShowInfo = new JButton("Отобразить!");
//        rightButtonShowInfo.setPreferredSize(new Dimension((int) (150 * procWIDTH), 30));`
        rightButtonShowInfo.setMargin(new Insets(0, 0, 0, 0));
        rightButtonShowInfo.setFont(fontForButton);
        rightPanelChoosers.add(rightButtonShowInfo);

        rightTeamPanel.add(rightPanelChoosers, BorderLayout.NORTH);

        rightInfoPanel = new JPanel();
        rightTeamPanel.add(rightInfoPanel, BorderLayout.CENTER);

        this.add(rightTeamPanel);

        rightSeasonChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rightSeasonChooser.setFocusable(false);

                String team = String.valueOf(rightTeamChooser.getSelectedItem());
                String league = String.valueOf(rightLeagueChooser.getSelectedItem());

                String pathToLeaguesList = path + rightSeasonChooser.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/";
                JFileChooser rightFileChooser = new JFileChooser(pathToLeaguesList);
                String[] rightDirectoryList = new String[rightFileChooser.getCurrentDirectory().list().length + 1];
                rightDirectoryList[0] = "Выберите лигу";
                for (int i = 1; i < rightDirectoryList.length; i++)
                    rightDirectoryList[i] = rightFileChooser.getCurrentDirectory().list()[i - 1].replace(".txt", "");
                DefaultComboBoxModel modelH = new DefaultComboBoxModel(rightDirectoryList);
                rightLeagueChooser.setModel(modelH);

                for (int i = 0; i < rightDirectoryList.length; i++) {
                    if (rightDirectoryList[i].equals(league))
                        rightLeagueChooser.setSelectedItem(league);
                }

                String pathToTeamsList = path + rightSeasonChooser.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/" + rightLeagueChooser.getSelectedItem() + ".txt";
                String[] listRight = {"Выберите команду"};
                if (!pathToTeamsList.contains("ыберите")) {
                    listRight = Main.readTxtFile(pathToTeamsList);
                }
                modelH = new DefaultComboBoxModel(listRight);
                rightTeamChooser.setModel(modelH);
                rightTeamChooser.setEnabled(false);

                pathToTeamsList = path + rightSeasonChooser.getSelectedItem().toString().replace("Сезон ", "") + "/" + league + "/Teams/";
                rightFileChooser = new JFileChooser(pathToTeamsList);
                rightDirectoryList = new String[rightFileChooser.getCurrentDirectory().list().length + 1];
                rightDirectoryList[0] = "Выберите команду";
                for (int i = 1; i < rightDirectoryList.length; i++)
                    rightDirectoryList[i] = rightFileChooser.getCurrentDirectory().list()[i - 1].replace(".xml", "");
                for (int i = 0; i < rightDirectoryList.length; i++) {
                    if (rightDirectoryList[i].equals(team)) {
                        rightTeamChooser.setSelectedItem(team);
                        rightTeamChooser.setEnabled(true);
                    }
                }
                rightLeagueChooser.setFocusable(true);

            }
        });

        rightLeagueChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = rightLeagueChooser.getSelectedIndex();
                String pathToLeaguesList = path + rightSeasonChooser.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/";
                JFileChooser rightFileChooser = new JFileChooser(pathToLeaguesList);
                String[] rightDirectoryList = new String[rightFileChooser.getCurrentDirectory().list().length+1];
                rightDirectoryList[0] = "Лига";
                for (int i=1; i<rightDirectoryList.length; i++)
                    rightDirectoryList[i] = rightFileChooser.getCurrentDirectory().list()[i-1].replace(".txt", "");
                DefaultComboBoxModel modelH = new DefaultComboBoxModel(rightDirectoryList);
                rightLeagueChooser.setModel(modelH);
                rightLeagueChooser.setSelectedIndex(index);

                rightTeamChooser.setEnabled(true);
                String pathToTeamsList = path + rightSeasonChooser.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/" + rightLeagueChooser.getSelectedItem() + ".txt";
                String[] listRight = {"Команда"};
                if (!pathToTeamsList.contains("Лига")) {
                    listRight = Main.readTxtFile(pathToTeamsList);
                }
                modelH = new DefaultComboBoxModel(listRight);
                rightTeamChooser.setModel(modelH);

            }
        });

        rightTeamChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        rightTeamAllOrHomeOrAway.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        rightButtonShowInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rightInfoPanel != null) {
                    rightTeamPanel.remove(rightInfoPanel);
                }
                rightInfoPanel = refreshData((String) rightLeagueChooser.getSelectedItem(),
                        (String) rightTeamChooser.getSelectedItem(),
                        (String) rightTeamAllOrHomeOrAway.getSelectedItem(),
                        (String) rightSeasonChooser.getSelectedItem(),
                        (String) rightLastOrFullSeason.getSelectedItem()
                );

                rightTeamPanel.add(rightInfoPanel);
                rightTeamPanel.revalidate();
                rightButtonShowInfo.setFocusable(false);
            }
        });
    }

    public JPanel refreshData(String leagueName, final String teamName, String allOrHomeOrAway, String season, String lastOrFullSeason){
        this.setCursor(Cursor.getPredefinedCursor (Cursor.WAIT_CURSOR));
        JPanel result = new JPanel(new BorderLayout());
        final Settings settings = Settings.getSettingsFromFile();
        final JScrollPane scrollPane;
        season = season.replace("Сезон ", "");
        if ((!leagueName.contains("Лига"))&&(!teamName.contains("Команда"))){
            ArrayList<String> tournamentTable = League.getLeagueFromFile(leagueName, season).tournamentTable;
            final Selector selector = new Selector();
            selector.getListOfMatches(leagueName, teamName, allOrHomeOrAway, season, lastOrFullSeason);
            selector.getPList(selector.listOfMatches, teamName, season);

            if (selector.listOfMatches.size()>0){
                JPanel allInfoPanel = new JPanel(new BorderLayout());

                final JPanel container = new JPanel();
                container.setLayout(null);
                int otstup = 0;
//                container.setPreferredSize(new Dimension((int) (0.46 * WIDTH), 500));
                int matches = selector.listOfMatches.size();
                int wins = (int) Double.parseDouble(selector.pList.get(0).get(1));
                int winsOT = (int) Double.parseDouble(selector.pList.get(1).get(1));
                int loses = (int) Double.parseDouble(selector.pList.get(2).get(1));
                int losesOT = (int) Double.parseDouble(selector.pList.get(3).get(1));
                int points = (int) Double.parseDouble(selector.pList.get(4).get(1));
                String teamStats = "Матчей: " + String.valueOf(matches) + ";  Побед: " + String.valueOf(wins) + ";  Побед в ОТ: " + String.valueOf(winsOT) + ";   Поражений в ОТ: " + String.valueOf(losesOT) + ";  Поражений: " + String.valueOf(loses);
                final JLabel label = new JLabel(teamStats);
                label.setLocation(10, 0);
                label.setSize(new Dimension(600, 25));
                otstup += 25;
                Font fontLabel = new Font("Arial", Font.BOLD, 15);
                label.setFont(fontLabel);
                container.add(label);

                String teamPoints = "Набрано очков: " + String.valueOf(points);
                final JLabel label2 = new JLabel(teamPoints);
                label2.setLocation(10, 25);
                label2.setSize(new Dimension(600, 25));
                otstup += 25;
                label2.setFont(fontLabel);
                container.add(label2);

                int goalsS = (int) Math.round(Double.parseDouble(selector.pList.get(5).get(1)));
                int goalsC = (int) Math.round(Double.parseDouble(selector.pList.get(5).get(2)));
                int diff = goalsS - goalsC;
                String teamGoals = "Голы в основное время. Забито:   " + String.valueOf(goalsS) + ";  Пропущено:   " + String.valueOf(goalsC) +
                        ";  Разница:   " + String.valueOf(diff);
                final JLabel label3 = new JLabel(teamGoals);
                label3.setLocation(10, 50);
                label3.setSize(new Dimension(600, 25));
                otstup += 25;
                label3.setFont(fontLabel);
                container.add(label3);

                int goalsSOT = (int) Math.round(Double.parseDouble(selector.pList.get(6).get(1)));
                int goalsCOT = (int) Math.round(Double.parseDouble(selector.pList.get(6).get(2)));
                int diffOT = goalsSOT - goalsCOT;
                String teamGoalsOT = "Голы с учетом ОТ. Забито:   " + String.valueOf(goalsSOT) + ";  Пропущено:   " + String.valueOf(goalsCOT) +
                        ";  Разница:   " + String.valueOf(diffOT);
                final JLabel label4 = new JLabel(teamGoalsOT);
                label4.setLocation(10, 75);
                label4.setSize(new Dimension(600, 25));
                otstup += 25;
                label4.setFont(fontLabel);
                container.add(label4);

                String forma = selector.pList.get(16).get(1);
                final Dimension defFormaLocation = new Dimension(70,103);
                for (int i=0; i<forma.length(); i++){
                    JLabel imageLabel = null;
                    if (forma.substring(i, i+1).equals("W")){
                        imageLabel = new JLabel(new ImageIcon("images/win.png"));
                    }
                    if (forma.substring(i, i+1).equals("L")){
                        imageLabel = new JLabel(new ImageIcon("images/lose.png"));
                    }
                    if (forma.substring(i, i+1).equals("w")){
                        imageLabel = new JLabel(new ImageIcon("images/winOT.png"));
                    }
                    if (forma.substring(i, i+1).equals("l")){
                        imageLabel = new JLabel(new ImageIcon("images/loseOT.png"));
                    }
                    imageLabel.setLocation(defFormaLocation.width + 25*i, defFormaLocation.height);
                    imageLabel.setSize(20,20);
                    final JLabel finalImageLabel = imageLabel;
                    imageLabel.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            int numberOfMatch = (finalImageLabel.getX() - defFormaLocation.width)/25;
                            if (selector.listOfMatches.size() > 20)
                                numberOfMatch = numberOfMatch + selector.listOfMatches.size() - 20;
                            WindowMatchStats window = new WindowMatchStats(selector.listOfMatches.get(numberOfMatch));
                            window.setVisible(true);
                        }
                        @Override
                        public void mousePressed(MouseEvent e) {
                        }
                        @Override
                        public void mouseReleased(MouseEvent e) {
                        }
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            int numberOfMatch = (finalImageLabel.getX() - defFormaLocation.width)/25;
                            if (selector.listOfMatches.size() > 20)
                                numberOfMatch = numberOfMatch + selector.listOfMatches.size() - 20;
                            String s = "<html>   " + selector.listOfMatches.get(numberOfMatch).homeTeam + " - " + selector.listOfMatches.get(numberOfMatch).awayTeam + "<br>";
                            if (selector.listOfMatches.get(numberOfMatch).homeScore == selector.listOfMatches.get(numberOfMatch).awayScore){
                                if (selector.listOfMatches.get(numberOfMatch).homeScoreOT == 1)
                                    s = s + "   " + String.valueOf(selector.listOfMatches.get(numberOfMatch).homeScore+1) + ":" + selector.listOfMatches.get(numberOfMatch).awayScore + " OT" + "</html>";
                                if (selector.listOfMatches.get(numberOfMatch).homeScoreBullits == 1)
                                    s = s + "   " + String.valueOf(selector.listOfMatches.get(numberOfMatch).homeScore+1) + ":" + selector.listOfMatches.get(numberOfMatch).awayScore + " Б" + "</html>";
                                if (selector.listOfMatches.get(numberOfMatch).awayScoreOT == 1)
                                    s = s + "   " + selector.listOfMatches.get(numberOfMatch).homeScore + ":" + String.valueOf(selector.listOfMatches.get(numberOfMatch).awayScore+1) + " OT" + "</html>";
                                if (selector.listOfMatches.get(numberOfMatch).awayScoreBullits == 1)
                                    s = s + "   " + selector.listOfMatches.get(numberOfMatch).homeScore + ":" + String.valueOf(selector.listOfMatches.get(numberOfMatch).awayScore+1) + " Б" + "</html>";
                            } else
                                s = s + "   " + selector.listOfMatches.get(numberOfMatch).homeScore + ":" + selector.listOfMatches.get(numberOfMatch).awayScore + "</html>";
                            finalImageLabel.setToolTipText(s);
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {

                        }
                    });
                    container.add(imageLabel);
                }
                String teamForma = "Форма:";
                final JLabel label5 = new JLabel(teamForma);
                label5.setLocation(10, 100);
                label5.setSize(new Dimension(600, 25));
                otstup += 25;
                label5.setFont(fontLabel);
                container.add(label5);

                String sources = "Сайт клуба / Твиттер:";
                final JLabel labelSources = new JLabel(sources);
                labelSources.setLocation(10,135);
                labelSources.setSize(new Dimension(200, 25));
                labelSources.setFont(fontLabel);
                container.add(labelSources);

                final JButton buttonSite = new JButton(new ImageIcon("images/www.png"));
                if (Team.getWebsite(teamName).equals("website"))
                    buttonSite.setEnabled(false);
                buttonSite.setLocation(170, 132);
                buttonSite.setSize(32,32);
                otstup += 40;
                container.add(buttonSite);

                JButton buttonTwitter = new JButton(new ImageIcon("images/twitter.png"));
                if (Team.getTwitter(teamName).equals("twitter"))
                    buttonTwitter.setEnabled(false);
                buttonTwitter.setLocation(170 + 32 + 5, 132);
                buttonTwitter.setSize(32,32);
                container.add(buttonTwitter);

                String teamSrednie = "";
                if (lastOrFullSeason.contains("Весь сезон")){
                    if (allOrHomeOrAway.contains("Все")){
                        teamSrednie = "Продвинутая статистика всех игр " + teamName + " в сезоне " + season;
                    }
                    if (allOrHomeOrAway.contains("Дома")){
                        teamSrednie = "Продвинутая статистика всех домашних игр " + teamName + " в сезоне " + season;
                    }
                    if (allOrHomeOrAway.contains("На выезде")){
                        teamSrednie = "Продвинутая статистика всех гостевых игр " + teamName + " в сезоне " + season;
                    }
                } else {
                    if (allOrHomeOrAway.contains("Все")){
                        teamSrednie = "Продвинутая статистика последних " + matches + " игр " + teamName + " в сезоне " + season;
                    }
                    if (allOrHomeOrAway.contains("Дома")){
                        teamSrednie = "Продвинутая статистика последних " + matches + " домашних игр " + teamName + " в сезоне " + season;
                    }
                    if (allOrHomeOrAway.contains("На выезде")){
                        teamSrednie = "Продвинутая статистика последних " + matches + " гостевых игр " + teamName + " в сезоне " + season;
                    }
                }

                final JLabel label6 = new JLabel(teamSrednie);
                label6.setLocation(10, 170);
                label6.setSize(570, 25);
                otstup += 25;
                label6.setFont(fontLabel);
                container.add(label6);

                String[] colHeads = {"Параметр" ,teamName, "Соперник", "Разница", "Тотал"};
                String[] params = {"Голы (ср.)", "Бр.в ст. (ср.)", "Реал, %", "Бр.мимо (cр.)",
                        "Блок.бр.(ср.)", "Вр.в атаке (ср.)", "Corsi", "CorsiFor, %", "Fenwick", "FenwickFor, %", "PDO"};
                String timeAttack = String.valueOf(Settings.getTimeInMinutesAndSecondsFromSeconds(Double.parseDouble(selector.pList.get(20).get(1)) / matches));
                String timeAttackOpp = String.valueOf(Settings.getTimeInMinutesAndSecondsFromSeconds(Double.parseDouble(selector.pList.get(20).get(2)) / matches));
                String timeAttackDiff = String.valueOf(Settings.getTimeInMinutesAndSecondsFromSeconds((Double.parseDouble(selector.pList.get(20).get(1)) - Double.parseDouble(selector.pList.get(20).get(2))) / matches));
                String timeAttackTotal = String.valueOf(Settings.getTimeInMinutesAndSecondsFromSeconds((Double.parseDouble(selector.pList.get(20).get(1)) + Double.parseDouble(selector.pList.get(20).get(2))) / matches));
                if (timeAttack.equals("0:00")){
                    timeAttack = "No info";
                    timeAttackOpp = "No info";
                    timeAttackDiff = "No info";
                    timeAttackTotal = "No info";
                }

                double corsi = MyMath.getCorsi(Double.parseDouble(selector.pList.get(7).get(1)), Double.parseDouble(selector.pList.get(21).get(1)) , Double.parseDouble(selector.pList.get(12).get(2)));
                double corsiOpp = MyMath.getCorsi(Double.parseDouble(selector.pList.get(7).get(2)), Double.parseDouble(selector.pList.get(21).get(2)) , Double.parseDouble(selector.pList.get(12).get(1)));
                double corsiPercent = MyMath.round(100 * corsi / (corsi + corsiOpp), 2);
                double fenwick = MyMath.getFenwick(Double.parseDouble(selector.pList.get(7).get(1)), Double.parseDouble(selector.pList.get(21).get(1)));
                double fenwickOpp = MyMath.getFenwick(Double.parseDouble(selector.pList.get(7).get(2)), Double.parseDouble(selector.pList.get(21).get(2)));
                double fenwickPercent = MyMath.round(100 * fenwick / (fenwick + fenwickOpp), 2);
                double realization = MyMath.round(100 * Double.parseDouble(selector.pList.get(5).get(1))/selector.listOfMatches.size() / Double.parseDouble(selector.pList.get(7).get(1)),2);
                double realizationOpp = MyMath.round(100 * Double.parseDouble(selector.pList.get(5).get(2))/selector.listOfMatches.size() / Double.parseDouble(selector.pList.get(7).get(2)),2);
                Object[][] data = {
                        {" " + params[0],
                                MyMath.round(Double.parseDouble(selector.pList.get(5).get(1))/selector.listOfMatches.size(),2),
                                MyMath.round(Double.parseDouble(selector.pList.get(5).get(2))/selector.listOfMatches.size(),2),
                                MyMath.round((Double.parseDouble(selector.pList.get(5).get(1)) - Double.parseDouble(selector.pList.get(5).get(2)))/selector.listOfMatches.size(),2),
                                MyMath.round((Double.parseDouble(selector.pList.get(5).get(1)) + Double.parseDouble(selector.pList.get(5).get(2)))/selector.listOfMatches.size(),2),
                        },
                        {" " + params[1],
                                selector.pList.get(7).get(1),
                                selector.pList.get(7).get(2),
                                MyMath.round(Double.parseDouble(selector.pList.get(7).get(1)) - Double.parseDouble(selector.pList.get(7).get(2)),2),
                                MyMath.round(Double.parseDouble(selector.pList.get(7).get(1)) + Double.parseDouble(selector.pList.get(7).get(2)),2),
                        },
                        {" " + params[2],
                                realization,
                                realizationOpp,
                                "-",
                                "-"},
                        {" " + params[3],
                                selector.pList.get(21).get(1),
                                selector.pList.get(21).get(2),
                                MyMath.round(Double.parseDouble(selector.pList.get(21).get(1)) - Double.parseDouble(selector.pList.get(21).get(2)),2),
                                MyMath.round(Double.parseDouble(selector.pList.get(21).get(1)) + Double.parseDouble(selector.pList.get(21).get(2)),2),
                        },
                        {" " + params[4],
                                selector.pList.get(12).get(1),
                                selector.pList.get(12).get(2),
                                MyMath.round(Double.parseDouble(selector.pList.get(12).get(1)) - Double.parseDouble(selector.pList.get(12).get(2)),2),
                                MyMath.round(Double.parseDouble(selector.pList.get(12).get(1)) + Double.parseDouble(selector.pList.get(12).get(2)),2),
                        },
                        {" " + params[5],
                                timeAttack,
                                timeAttackOpp,
                                timeAttackDiff,
                                timeAttackTotal
                        },
                        {" " + params[6],
                                corsi,
                                corsiOpp,
                                MyMath.round(corsi - corsiOpp, 2),
                                MyMath.round(corsi + corsiOpp, 2)
                        },
                        {" " + params[7],
                                corsiPercent,
                                MyMath.round(100 - corsiPercent,2),
                                MyMath.round(corsiPercent - (100 - corsiPercent), 2),
                                100
                        },
                        {" " + params[8],
                                fenwick,
                                fenwickOpp,
                                MyMath.round(fenwick - fenwickOpp,2),
                                MyMath.round(fenwick + fenwickOpp,2)
                        },
                        {" " + params[9],
                                fenwickPercent,
                                MyMath.round(100 - fenwickPercent,2),
                                MyMath.round(fenwickPercent - (100 - fenwickPercent),2),
                                100
                        },
                        {" " + params[10],
                                MyMath.round(realization + 100 - realizationOpp, 2),
                                MyMath.round(realizationOpp + 100 - realization, 2),
                                "-",
                                "-"
                        }
                        };

                JTable table = new JTable(data, colHeads);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.setEnabled(false);
                table.getTableHeader().setFont(fontLabel);
                table.setFont(fontLabel);
                table.setRowHeight(25);
                //table.getColumnModel().getColumn(0).setPreferredWidth(297);
                //table.getColumnModel().getColumn(1).setPreferredWidth(150);
                //table.getColumnModel().getColumn(2).setPreferredWidth(150);
                //table.getColumnModel().getColumn(3).setPreferredWidth(150);
                //table.getColumnModel().getColumn(4).setPreferredWidth(150);
                Renderer renderer = new Renderer(0);
//                renderer.setHorizontalAlignment(JLabel.CENTER);

                for (int i=1; i<table.getColumnCount();i++){
                    table.getColumnModel().getColumn(i).setCellRenderer(renderer);
                }

                JPanel tablePanel = new JPanel();
                tablePanel.setLayout(new BorderLayout());
                tablePanel.add(table);
                tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);

                tablePanel.setSize(610, (params.length + 1) * table.getRowHeight() - 3);
                tablePanel.setLocation(10, otstup);
                otstup += (params.length + 1) * table.getRowHeight();
                container.add(tablePanel);

                JButton buttonAdvancedStatsInfo = new JButton();
                buttonAdvancedStatsInfo.setIcon(new ImageIcon("images/info.png"));
                buttonAdvancedStatsInfo.setLocation(600, otstup - (params.length + 1) * table.getRowHeight() - 20);
                buttonAdvancedStatsInfo.setSize(20, 20);

                container.setPreferredSize(new Dimension(600, otstup));
                container.add(buttonAdvancedStatsInfo);

                allInfoPanel.add(container,BorderLayout.NORTH);

                ////////////////////////ГРАФИКИ ВСТАВЛЯЮ ТУТ
                final Graphic graphic;
                final JPanel panelG;

                graphic = new Graphic(0, teamName);
                panelG = graphic.getGraphicsForAdvancedStats(teamName, allOrHomeOrAway, lastOrFullSeason, selector, tournamentTable);
                //panelG.setPreferredSize(new Dimension(300,1000));
                allInfoPanel.add(panelG);

                ////////////////////////ГРАФИКИ БОЛЬШЕ НЕ ВСТАВЛЯЮ
                scrollPane = new JScrollPane(allInfoPanel);
                scrollPane.setVerticalScrollBar( new JScrollBar() {
                    public int getUnitIncrement( int direction ) {
                        return 50;
                    }
                } );

                buttonSite.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String website = Team.getWebsite(teamName);

                        Desktop desktop = java.awt.Desktop.getDesktop();
                        URI uri = null;
                        try {
                            uri = new URI("http://www." + website);
                        } catch (URISyntaxException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            desktop.browse(uri);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });

                buttonTwitter.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String twitter = Team.getTwitter(teamName);

                        Desktop desktop = java.awt.Desktop.getDesktop();
                        URI uri = null;
                        try {
                            uri = new URI("https://www.twitter.com/" + twitter);
                        } catch (URISyntaxException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            desktop.browse(uri);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });

                buttonAdvancedStatsInfo.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String text = "<html>Самыми популярными показателями продвинутой статистики являются CORSI, FENWICK и PDO:<br>" +
                                "       <br>" +
                                "CORSI = кол-во бросков в створ ворот + кол-во бросков мимо ворот + заблокированные противником броски   <br>" +
                                "   Corsi существует двух видов:<br>" +
                                "   CF (Corsi For) - броски в створ и мимо, заблокированные противником броски;<br>" +
                                "   CA (Corsi Against) - броски противника в створ и мимо, собственные заблокированные броски;<br>" +
                                "   Также важным показателем является CF%. Он отражает стиль команд.<br>" +
                                "   CF% = 100 * CF / (CF + CA);<br>" +
                                "       <br>" +
                                "FENWICK = кол-во бросков в створ ворот + кол-во бросков мимо ворот<br>" +
                                "   Fenwick существует двух видов:<br>" +
                                "   FF (Fenwick For) - броски в створ и мимо;<br>" +
                                "   FA (Fenwick Against) - броски противника в створ и мимо;<br>" +
                                "   По аналогии с CF% вычисляют FF%.<br>" +
                                "   FF% = 100 * FF / (FF + FA);<br>" +
                                "       <br>" +
                                "PDO = процент реализации бросков + процент отраженных бросков;<br>" +
                                "В какой-то мере этот показатель отражает меру 'везения' команд;<br>" +
                                "Если PDO &#62; 100, то команда реализует броски лучше соперника, если &#60; 100 - хуже;<br>" +
                                "       <br>" +
                                "   Данная информация может помочь вам при анализе показателей команд.<br>" +
                                "</html>";


                        PopupWindow window = new PopupWindow(text);
                        window.setSize(930, 630);
                        window.setLocation(50, 120);
                        window.setVisible(true);
                    }
                });

                JPanel searchPanel = new JPanel(new GridLayout(1, 3, 5, 5));
                JButton buttonUp = new JButton("Вверх");
                buttonUp.setFont(font18);
                searchPanel.add(buttonUp);

                final JTextField searchTextField = new JTextField("Перейти к:");
                searchTextField.setFont(font18);
                searchPanel.add(searchTextField);

                JButton buttonDown = new JButton("Вниз");
                buttonDown.setFont(font18);
                searchPanel.add(buttonDown);

                searchPanel.setEnabled(false);
                result.add(searchPanel, BorderLayout.SOUTH);

                buttonUp.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
                    }
                });

                buttonDown.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
                    }
                });

                DocumentListener dl = new DocumentListener() {
                    public void insertUpdate(DocumentEvent e) {
                        String text = searchTextField.getText();

                        if (!text.equals("")){
                            int indexOfFoundGraphic = 0;
                            int containerHeight = container.getHeight();
                            for (String s : graphic.graphicTitles){
                                if (s.toUpperCase().contains(text.toUpperCase().trim())){
                                    indexOfFoundGraphic = graphic.graphicTitles.indexOf(s);
                                    break;
                                }
                            }
                            int resHeight;
                            if (settings.showGraphics){
                                resHeight = containerHeight + indexOfFoundGraphic*graphic.graphicHeight;
                            } else {
                                heightOfTable = panelG.getHeight() / graphic.graphicTitles.size();
                                resHeight = containerHeight + indexOfFoundGraphic*heightOfTable;
                            }

                            scrollPane.getVerticalScrollBar().setValue(resHeight);
                        }

                    }

                    public void removeUpdate(DocumentEvent e) {
                        insertUpdate(e);
                    }

                    public void changedUpdate(DocumentEvent e) {
                        System.out.println("Was change...");
                    }
                };

                searchTextField.getDocument().addDocumentListener(dl);

                searchTextField.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        searchTextField.selectAll();
                    }
                    @Override
                    public void focusLost(FocusEvent e) {

                    }
                });

            } else {
                JPanel container = new JPanel(new BorderLayout());

                String labelText = "";
                if (allOrHomeOrAway.contains("Все")){
                    labelText = "В сезоне " + season + " команда " + teamName + " не провела ни одного матча.";
                }
                if (allOrHomeOrAway.contains("Дома")){
                    labelText = "В сезоне " + season + " команда " + teamName + " не провела ни одного матча на своем поле.";
                }
                if (allOrHomeOrAway.contains("На выезде")){
                    labelText = "В сезоне " + season + " команда " + teamName + " не провела ни одного матча на выезде.";
                }
                final JLabel label = new JLabel(labelText);
                label.setLocation(10, 0);
//                label.setSize(new Dimension((int) (0.495 * WIDTH) - 30, 25));
                Font fontLabel = new Font("Arial", Font.BOLD, 15);
                label.setFont(fontLabel);
                container.add(label, BorderLayout.NORTH);

                scrollPane = new JScrollPane(container);
                scrollPane.setVerticalScrollBar( new JScrollBar() {
                    public int getUnitIncrement( int direction ) {
                        return 50;
                    }
                } );
            }
        } else {
            JPanel container = new JPanel(new BorderLayout());

            final JLabel label = new JLabel("Задайте все условия поиска. Лига и/или команда не заданы.");
            label.setLocation(10, 0);
//            label.setSize(new Dimension((int) (0.495 * WIDTH) - 30, 25));
            Font fontLabel = new Font("Arial", Font.BOLD, 15);
            label.setFont(fontLabel);
            container.add(label, BorderLayout.NORTH);

            scrollPane = new JScrollPane(container);
            scrollPane.setVerticalScrollBar( new JScrollBar() {
                public int getUnitIncrement( int direction ) {
                    return 50;
                }
            } );
        }
        this.setCursor(Cursor.getDefaultCursor());
        result.add(scrollPane);

        return result;
    }

    public void setFilters(String league, String homeTeam, String awayTeam){
        leftButtonShowInfo.setEnabled(false);
        rightButtonShowInfo.setEnabled(false);
        String season = Settings.getCurrentSeasonInLeague(league);
        leftSeasonChooser.setSelectedItem("Сезон " + season);
        rightSeasonChooser.setSelectedItem("Сезон " + season);
        leftLeagueChooser.setSelectedItem(league);
        rightLeagueChooser.setSelectedItem(league);
        leftTeamChooser.setSelectedItem(homeTeam);
        rightTeamChooser.setSelectedItem(awayTeam);
        leftTeamAllOrHomeOrAway.setSelectedItem("Дома");
        rightTeamAllOrHomeOrAway.setSelectedItem("На выезде");
        leftLastOrFullSeason.setSelectedItem("Весь сезон");
        rightLastOrFullSeason.setSelectedItem("Весь сезон");
        leftButtonShowInfo.setEnabled(true);
        rightButtonShowInfo.setEnabled(true);
    }
}