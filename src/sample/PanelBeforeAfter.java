package sample;

import org.jfree.ui.tabbedui.VerticalLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Hashtable;

public class PanelBeforeAfter extends JPanel{
    final Font font15 = new Font("Arial", Font.BOLD, 15);
    final Font font18 = new Font("Arial", Font.BOLD, 18);
    final Font font20 = new Font("Arial", Font.BOLD, 20);
    int heightOfTable = 0;

    //JScrollPane leftScrollPane;
    //JScrollPane rightScrollPane;
    JComboBox<String> seasonChooser;
    JComboBox<String> leagueChooser;
    JComboBox<String> teamChooser;
    JComboBox<String> teamAllOrHomeOrAway;
    JComboBox<String> teamLastOrFullSeason;
    JButton buttonShowInfo;
    JPanel leftTeamPanel;
    JPanel rightTeamPanel;
    JPanel leftInfoPanel;
    JPanel rightInfoPanel;

    JPanel sliderPanel;
    String leagueName;
    String teamName;
    String allOrHomeOrAway;
    String lastOrFullSeason = "До-после";
    String season;

    JSlider slider = new JSlider(0,10,0);
    Hashtable<Integer, JLabel> labels;
    JPanel panelTableWithBound = new JPanel();
    JButton buttonShowBeforeAfterStats;
    final JPanel panelScrolls;

    public PanelBeforeAfter() {
//        this.setLayout(new VerticalLayout());
        this.setLayout(new BorderLayout());

        final JPanel allInfoPanel = new JPanel(new BorderLayout());
        panelScrolls = new JPanel(new GridLayout(1, 0 , 0, 0));

        final String path = "database/";
        String curSeason = Settings.getDefaultSeason();

        JPanel panelChoosers = new JPanel(new GridLayout(1, 0, 5, 5));
        ArrayList<String> listOfSeasons = Settings.getListOfSeasons();
        String[] seasonList = new String[listOfSeasons.size()];
        for (int i = 0; i < seasonList.length; i++)
            seasonList[i] = "Сезон " + listOfSeasons.get(i);
        seasonChooser = new JComboBox<>(seasonList);
        //seasonChooser.setPreferredSize(new Dimension((int) (130 * procWIDTH), 30));
        panelChoosers.add(seasonChooser);

        JFileChooser fileChooser = new JFileChooser(path + curSeason + "/leagues");
        String[] directoryList = fileChooser.getCurrentDirectory().list();
        ArrayList<String> leagueList = new ArrayList<>();
        leagueList.add("Выберите лигу");
        for (String aDirectoryList : directoryList) leagueList.add(aDirectoryList.replace(".txt", ""));
        String[] listOfLeagues = new String[leagueList.size()];
        for (int i = 0; i < listOfLeagues.length; i++)
            listOfLeagues[i] = leagueList.get(i);
        leagueChooser = new JComboBox<>(listOfLeagues);
        panelChoosers.add(leagueChooser);

        String fileName = path + curSeason + "/leagues/" + leagueChooser.getSelectedItem() + ".txt";
        String[] listOfTeams = {"Выберите команду"};
        if (!fileName.contains("ыберите")) {
            listOfTeams = Main.readTxtFile(fileName);
        }
        teamChooser = new JComboBox<>(listOfTeams);
        teamChooser.setEnabled(false);
        panelChoosers.add(teamChooser);

        String[] allOrHomeOrAwayList = {"Все матчи", "Дома", "На выезде"};
        teamAllOrHomeOrAway = new JComboBox<>(allOrHomeOrAwayList);
        panelChoosers.add(teamAllOrHomeOrAway);

        Font fontForButton = new Font("", 0, 16);
        buttonShowInfo = new JButton("Отобразить!");
        buttonShowInfo.setFont(fontForButton);
        buttonShowInfo.setMargin(new Insets(0, 0, 0, 0));
        panelChoosers.add(buttonShowInfo);

        this.add(panelChoosers, BorderLayout.NORTH);

        final JPanel panelWithSlider = new JPanel(new BorderLayout());
        sliderPanel = new JPanel();
        panelWithSlider.add(sliderPanel);
        allInfoPanel.add(panelWithSlider, BorderLayout.NORTH);

        leftTeamPanel = new JPanel(new BorderLayout());
        leftTeamPanel.setBorder(BorderFactory.createTitledBorder(""));
        leftInfoPanel = new JPanel();
        leftTeamPanel.add(leftInfoPanel, BorderLayout.CENTER);

        rightTeamPanel = new JPanel(new BorderLayout());
        rightTeamPanel.setBorder(BorderFactory.createTitledBorder(""));
        rightInfoPanel = new JPanel();
        rightTeamPanel.add(rightInfoPanel, BorderLayout.CENTER);

        panelScrolls.add(leftTeamPanel);
        panelScrolls.add(rightTeamPanel);

        allInfoPanel.add(panelScrolls);
        this.add(allInfoPanel);

        seasonChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seasonChooser.setFocusable(false);

                String team = String.valueOf(teamChooser.getSelectedItem());
                String league = String.valueOf(leagueChooser.getSelectedItem());

                String pathToLeaguesList = path + seasonChooser.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/";
                JFileChooser fileChooser = new JFileChooser(pathToLeaguesList);
                String[] directoryList = new String[fileChooser.getCurrentDirectory().list().length + 1];
                directoryList[0] = "Выберите лигу";
                for (int i = 1; i < directoryList.length; i++)
                    directoryList[i] = fileChooser.getCurrentDirectory().list()[i - 1].replace(".txt", "");
                DefaultComboBoxModel modelH = new DefaultComboBoxModel(directoryList);
                leagueChooser.setModel(modelH);

                for (int i = 0; i < directoryList.length; i++) {
                    if (directoryList[i].equals(league))
                        leagueChooser.setSelectedItem(league);
                }

                String pathToTeamsList = path + seasonChooser.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/" + leagueChooser.getSelectedItem() + ".txt";
                String[] listOfTeams = {"Выберите команду"};
                if (!pathToTeamsList.contains("ыберите")) {
                    listOfTeams = Main.readTxtFile(pathToTeamsList);
                }
                modelH = new DefaultComboBoxModel(listOfTeams);
                teamChooser.setModel(modelH);
                teamChooser.setEnabled(false);

                pathToTeamsList = path + seasonChooser.getSelectedItem().toString().replace("Сезон ", "") + "/" + league + "/Teams/";
                fileChooser = new JFileChooser(pathToTeamsList);
                directoryList = new String[fileChooser.getCurrentDirectory().list().length + 1];
                directoryList[0] = "Выберите команду";
                for (int i = 1; i < directoryList.length; i++)
                    directoryList[i] = fileChooser.getCurrentDirectory().list()[i - 1].replace(".xml", "");
                for (int i = 0; i < directoryList.length; i++) {
                    if (directoryList[i].equals(team)) {
                        teamChooser.setSelectedItem(team);
                        teamChooser.setEnabled(true);
                    }
                }
                leagueChooser.setFocusable(true);
            }
        });

        leagueChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = leagueChooser.getSelectedIndex();
                String pathToLeaguesList = path + seasonChooser.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/";
                JFileChooser fileChooser = new JFileChooser(pathToLeaguesList);
                String[] directoryList = new String[fileChooser.getCurrentDirectory().list().length+1];
                directoryList[0] = "Выберите лигу";
                for (int i=1; i<directoryList.length; i++)
                    directoryList[i] = fileChooser.getCurrentDirectory().list()[i-1].replace(".txt", "");
                DefaultComboBoxModel modelH = new DefaultComboBoxModel(directoryList);
                leagueChooser.setModel(modelH);
                leagueChooser.setSelectedIndex(index);
                leagueChooser.setFocusable(false);

                teamChooser.setEnabled(true);
                String pathToTeamsList = path + seasonChooser.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/" + leagueChooser.getSelectedItem() + ".txt";
                String[] listOfTeams = {"Выберите команду"};
                if (!pathToTeamsList.contains("ыберите")) {
                    listOfTeams = Main.readTxtFile(pathToTeamsList);
                }
                modelH = new DefaultComboBoxModel(listOfTeams);
                teamChooser.setModel(modelH);
                teamAllOrHomeOrAway.setFocusable(false);
            }
        });

        teamChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teamChooser.setFocusable(false);
                teamAllOrHomeOrAway.setFocusable(true);
            }
        });

        teamAllOrHomeOrAway.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teamAllOrHomeOrAway.setFocusable(false);
            }
        });

        buttonShowInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sliderPanel != null) {
                    panelWithSlider.removeAll();
                }

                leagueName = (String) leagueChooser.getSelectedItem();
                teamName = (String) teamChooser.getSelectedItem();
                allOrHomeOrAway = (String) teamAllOrHomeOrAway.getSelectedItem();
                season = (String) seasonChooser.getSelectedItem();

                sliderPanel = getSliderPanel();

                panelWithSlider.add(sliderPanel);
                panelWithSlider.revalidate();
                allInfoPanel.revalidate();
                buttonShowInfo.setFocusable(false);
            }
        });

    }

    public JPanel getSliderPanel(){
        JPanel result = new JPanel(new VerticalLayout());
        this.setCursor(Cursor.getPredefinedCursor (Cursor.WAIT_CURSOR));
        season = season.replace("Сезон ", "");

        if ((!leagueName.contains("Выберите"))&&(!teamName.contains("Выберите"))){
            Selector selector = new Selector();
            selector.getListOfMatches(leagueName, teamName, allOrHomeOrAway, season, "Весь сезон");
            //selector.getPList(selector.listOfMatches, teamName);

            if (selector.listOfMatches.size()>0){
                int matches = selector.listOfMatches.size();

                String whereWereMatches = allOrHomeOrAway;
                if (allOrHomeOrAway.contains("Все"))
                    whereWereMatches = "";
                String textLabel = "В сезоне " + season + " команда " + teamName + " провела " + whereWereMatches + " " + matches + " игр.";
                JLabel labelHeader = new JLabel(textLabel);
                labelHeader.setFont(font18);
                labelHeader.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
                labelHeader.setHorizontalAlignment(SwingConstants.CENTER);
                result.add(labelHeader);

                JLabel labelSetBound = new JLabel("Задайте границу для отображения статистики «До–после»");
                labelSetBound.setFont(font18);
                labelSetBound.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
                labelSetBound.setHorizontalAlignment(SwingConstants.CENTER);
                result.add(labelSetBound);

                slider = new JSlider(0, matches-1);
                slider.setBorder(BorderFactory.createEmptyBorder(10, 50, 0, 50));

                labels = new Hashtable<>();
                for (int i=0; i<selector.listOfMatches.size(); i++){
                    if (selector.listOfMatches.get(i).homeTeam.equals(teamName)) {
                        labels.put(i, new JLabel(Team.getShortName(selector.listOfMatches.get(i).awayTeam)));
                    }
                    else{
                        labels.put(i, new JLabel(Team.getShortName(selector.listOfMatches.get(i).homeTeam)));
                    }
                }
                slider.setLabelTable(labels);
                slider.setPaintLabels(true);
                slider.setMajorTickSpacing(1);
                slider.setPaintTicks(true);
                slider.setEnabled(true);
                result.add(slider);

                result.add(panelTableWithBound);

                buttonShowBeforeAfterStats = new JButton("Отобразить!");
                buttonShowBeforeAfterStats.setFont(font18);

                result.add(buttonShowBeforeAfterStats);

                setTableWithBound();

                slider.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        setTableWithBound();
                    }
                });

                buttonShowBeforeAfterStats.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        if (leftTeamPanel != null) {
//                            leftTeamPanel.remove(leftInfoPanel);
                            panelScrolls.remove(leftTeamPanel);
                        }
                        leftTeamPanel = showBeforeAfterStats("0-" + String.valueOf(slider.getValue()));
                        panelScrolls.add(leftTeamPanel);
                        panelScrolls.revalidate();

                        if (rightTeamPanel != null) {
//                            rightTeamPanel.remove(rightInfoPanel);
                            panelScrolls.remove(rightTeamPanel);
                        }
                        rightTeamPanel = showBeforeAfterStats(String.valueOf(slider.getValue()+1) + "-" + String.valueOf(slider.getMaximum()));
                        panelScrolls.add(rightTeamPanel);
                        panelScrolls.revalidate();
                        setCursor(Cursor.getDefaultCursor());

                    }
                });

            } else {
                JPanel container = new JPanel(new BorderLayout());

                String labelText = "";
                if (allOrHomeOrAway.contains("Все")){
                    labelText = "   В сезоне " + season + " команда " + teamName + " не провела ни одного матча.";
                }
                if (allOrHomeOrAway.contains("Дома")){
                    labelText = "   В сезоне " + season + " команда " + teamName + " не провела ни одного матча на своем поле.";
                }
                if (allOrHomeOrAway.contains("На выезде")){
                    labelText = "   В сезоне " + season + " команда " + teamName + " не провела ни одного матча на выезде.";
                }
                final JLabel label = new JLabel(labelText);
                label.setLocation(10, 0);
                label.setPreferredSize(new Dimension(500, 25));
                label.setFont(font18);
                container.add(label, BorderLayout.NORTH);

            }
        } else {
            JPanel container = new JPanel(new BorderLayout());

            final JLabel label = new JLabel("   Задайте все условия поиска. Лига и/или команда не заданы.");
            label.setPreferredSize(new Dimension(500, 25));
            label.setFont(font18);
            container.add(label, BorderLayout.NORTH);

        }
        this.setCursor(Cursor.getDefaultCursor());

        return result;

    }

    public void setTableWithBound(){
        panelTableWithBound.setLayout(new GridLayout(1, 0, 5, 5));
        panelTableWithBound.removeAll();

        int totalMatches = slider.getMaximum()+1;
        int bound = slider.getValue();

        String leftText = "";
        String rightText = "";
        if (bound == slider.getMaximum()){
            leftText = "Справа от границы нет матчей";
            buttonShowBeforeAfterStats.setEnabled(false);
        } else {
            leftText = (bound+1) + " матчей. От " + labels.get(0).getText() + " до " + labels.get(bound).getText();
            rightText = (totalMatches - (bound+1)) + " матчей. От " + labels.get(bound+1).getText() + " до " + labels.get(totalMatches-1).getText();
            buttonShowBeforeAfterStats.setEnabled(true);
        }

        JLabel labelBefore = new JLabel(leftText);
        labelBefore.setFont(font18);
        labelBefore.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        labelBefore.setHorizontalAlignment(SwingConstants.CENTER);
        panelTableWithBound.add(labelBefore);

        JLabel labelAfter = new JLabel(rightText);
        labelAfter.setFont(font18);
        labelAfter.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        labelAfter.setHorizontalAlignment(SwingConstants.CENTER);
        panelTableWithBound.add(labelAfter);

        panelTableWithBound.revalidate();

    }

    public JPanel showBeforeAfterStats(String range){
        final Selector selector = new Selector();
        JPanel result = new JPanel(new BorderLayout());
        selector.getListOfMatches(leagueName, teamName, allOrHomeOrAway, season, range);
        ArrayList<String> tournamentTable = League.getLeagueFromFile(leagueName, season).tournamentTable;
        final JScrollPane scrollPane;
        final Settings settings = Settings.getSettingsFromFile();

        if (selector.listOfMatches.size()>0){
            final Font fontLabel = new Font("Arial", Font.BOLD, 15);
            JPanel leftInfo = new JPanel(new VerticalLayout());
            selector.getPList(selector.listOfMatches, teamName, season);

            //JPanel allInfoPanel = new JPanel(new BorderLayout());

            final JPanel container = new JPanel();
            container.setLayout(null);
            int otstup = 0;
            int matches = selector.listOfMatches.size();
            int wins = (int) Double.parseDouble(selector.pList.get(0).get(1));
            int winsOT = (int) Double.parseDouble(selector.pList.get(1).get(1));
            int loses = (int) Double.parseDouble(selector.pList.get(2).get(1));
            int losesOT = (int) Double.parseDouble(selector.pList.get(3).get(1));
            int points = (int) Double.parseDouble(selector.pList.get(4).get(1));
            String teamStats = "Матчей: " + String.valueOf(matches) + ";  Побед: " + String.valueOf(wins) + ";  Побед в ОТ: " + String.valueOf(winsOT) + ";   Поражений в ОТ: " + String.valueOf(losesOT) + ";  Поражений: " + String.valueOf(loses);
            final JLabel label = new JLabel(teamStats);
            label.setBackground(new Color(238, 238, 238));
            label.setLocation(10, otstup);
            otstup += 25;
            label.setSize(new Dimension(600, 25));
            label.setFont(font15);
            container.add(label);

            String teamPoints = "Набрано очков: " + String.valueOf(points);
            final JLabel label2 = new JLabel(teamPoints);
            label2.setLocation(10, otstup);
            otstup += 25;
            label2.setSize(new Dimension(600, 25));
            label2.setFont(font15);
            container.add(label2);

            int goalsS = (int) Math.round(Double.parseDouble(selector.pList.get(5).get(1)));
            int goalsC = (int) Math.round(Double.parseDouble(selector.pList.get(5).get(2)));
            int diff = goalsS - goalsC;
            String teamGoals = "Голы в основное время. Забито:   " + String.valueOf(goalsS) + ";  Пропущено:   " + String.valueOf(goalsC) +
                    ";  Разница:   " + String.valueOf(diff);
            final JLabel label3 = new JLabel(teamGoals);
            label3.setLocation(10, otstup);
            otstup += 25;
            label3.setSize(new Dimension(600, 25));
            label3.setFont(font15);
            container.add(label3);

            int goalsSOT = (int) Math.round(Double.parseDouble(selector.pList.get(6).get(1)));
            int goalsCOT = (int) Math.round(Double.parseDouble(selector.pList.get(6).get(2)));
            int diffOT = goalsSOT - goalsCOT;
            String teamGoalsOT = "Голы с учетом ОТ. Забито:   " + String.valueOf(goalsSOT) + ";  Пропущено:   " + String.valueOf(goalsCOT) +
                    ";  Разница:   " + String.valueOf(diffOT);
            final JLabel label4 = new JLabel(teamGoalsOT);
            label4.setLocation(10, otstup);
            otstup += 25;
            label4.setSize(new Dimension(600, 25));
            label4.setFont(font15);
            container.add(label4);

            String forma = selector.pList.get(16).get(1);
            final Dimension defFormaLocation = new Dimension(70,otstup + 3);
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
            label5.setLocation(10, otstup);
            otstup += 35;
            label5.setSize(new Dimension(370, 25));
            label5.setFont(font15);
            container.add(label5);

            String sources = "Сайт клуба / Твиттер:";
            final JLabel labelSources = new JLabel(sources);
            labelSources.setLocation(10, otstup);
            otstup += 35;
            labelSources.setSize(new Dimension(200, 25));
            labelSources.setFont(font15);
            container.add(labelSources);

            final JButton buttonSite = new JButton(new ImageIcon("images/www.png"));
            if (Team.getWebsite(teamName).equals("website"))
                buttonSite.setEnabled(false);
            buttonSite.setLocation(170, 132);
            buttonSite.setSize(32,32);
            container.add(buttonSite);

            JButton buttonTwitter = new JButton(new ImageIcon("images/twitter.png"));
            if (Team.getTwitter(teamName).equals("twitter"))
                buttonTwitter.setEnabled(false);
            buttonTwitter.setLocation(170 + 32 + 5, 132);
            buttonTwitter.setSize(32,32);
            container.add(buttonTwitter);

            JButton buttonTrends = new JButton("Тренды");
            buttonTrends.setLocation(10, otstup);
            buttonTrends.setFont(font15);
            buttonTrends.setSize(100, 30);
            container.add(buttonTrends);

            final JButton buttonDiagrams = new JButton("Диаграммы показателей");
            buttonDiagrams.setLocation(130, otstup);
            buttonDiagrams.setFont(font15);
            buttonDiagrams.setSize(220, 30);
            otstup += 30;
            container.add(buttonDiagrams);

            String teamSrednie = "";
            if (allOrHomeOrAway.contains("Все")){
                teamSrednie = "Показатели игр " + teamName + " в сезоне " + season + ". ДО разделения";
            }
            if (allOrHomeOrAway.contains("Дома")){
                teamSrednie = "Показатели домашних игр " + teamName + " в сезоне " + season + ". ДО разделения";
            }
            if (allOrHomeOrAway.contains("На выезде")){
                teamSrednie = "Показатели гостевых игр " + teamName + " в сезоне " + season + ". ПОСЛЕ разделения";
            }


            final JLabel label6 = new JLabel(teamSrednie);
            label6.setLocation(10, otstup);
            otstup += 25;
            label6.setSize(new Dimension(600, 25));
            label6.setFont(font15);
            container.add(label6);

            String[] colHeads = {"Параметр" ,teamName, "Соперник", "Разница", "Тотал"};
                /*String[] params = {"Голы (сред.)", "Броски в створ (сред.)", "Реализация, %", "Голы в первом пер. и их доля в %",
                                    "Голы во втором пер. и их доля в %", "Голы в третьем пер. и их доля в %",
                                    "Голы в большинстве", "Успешная игра в большинстве, %", "Пропущенные голы в меньшинстве",
                                    "Успешная игра в меньшинстве, %", "Время в атаке",
                                    "Забитые голы в меньшинстве", "Выигранные вбрасывания (сред.) и %", "Броски мимо (сред.)",
                                    "Блокированные броски (сред.)", "Силовые приемы (сред.)", "Минуты штрафа (сред.)",
                                    "Кол-во двухминутных удалений (сред.)"};*/
            String[] params = {"Голы (ср.)", "Бр.Ств (ср.)", "Реал., %", "Голы в 1П и %",
                    "Голы во 2П и %", "Голы в 3П и %",
                    "Голы в бол.", "Реал. бол, %", "Проп.Г в меньш.",
                    "Успеш. мен, %", "Время в атаке",
                    "Голы в меньш.", "Выиг.вбр(ср.) и %", "Бр. мимо (ср.)",
                    "Блок. бр. (ср.)", "Сил. пр. (ср.)", "Мин. шт. (ср.)",
                    "Кол 2мин.уд. (ср.)", "Corsi", "CorsiFor, %", "Fenwick", "FenwickFor, %", "PDO"};
            String bol = String.valueOf(MyMath.round(100 * Double.parseDouble(selector.pList.get(9).get(1))/Double.parseDouble(selector.pList.get(8).get(1)), 2)) + "%  (" + String.valueOf((int) Double.parseDouble(selector.pList.get(9).get(1))) + "/" + String.valueOf((int) Double.parseDouble(selector.pList.get(8).get(1))) + ")";
            String bolOp = String.valueOf(MyMath.round(100 * Double.parseDouble(selector.pList.get(9).get(2))/Double.parseDouble(selector.pList.get(8).get(2)), 2)) + "%  (" + String.valueOf((int) Double.parseDouble(selector.pList.get(9).get(2))) + "/" + String.valueOf((int) Double.parseDouble(selector.pList.get(8).get(2))) + ")";
            String men = String.valueOf(MyMath.round(100 - 100 * Double.parseDouble(selector.pList.get(9).get(2))/Double.parseDouble(selector.pList.get(8).get(2)), 2)) + "%  (" + String.valueOf((int) (Double.parseDouble(selector.pList.get(8).get(2)) - Double.parseDouble(selector.pList.get(9).get(2)))) + "/" + String.valueOf((int) Double.parseDouble(selector.pList.get(8).get(2))) + ")";
            String menOp = String.valueOf(MyMath.round(100 - 100 * Double.parseDouble(selector.pList.get(9).get(1))/Double.parseDouble(selector.pList.get(8).get(1)), 2)) + "%  (" + String.valueOf((int) (Double.parseDouble(selector.pList.get(8).get(1)) - Double.parseDouble(selector.pList.get(9).get(1)))) + "/" + String.valueOf((int) Double.parseDouble(selector.pList.get(8).get(1))) + ")";
            String timeAttack = Settings.getTimeInMinutesAndSecondsFromSeconds(Double.parseDouble(selector.pList.get(20).get(1)) / matches);
            String timeAttackOpp = Settings.getTimeInMinutesAndSecondsFromSeconds(Double.parseDouble(selector.pList.get(20).get(2)) / matches);
            String timeAttackDiff;
            double corsi = MyMath.getCorsi(Double.parseDouble(selector.pList.get(7).get(1)), Double.parseDouble(selector.pList.get(21).get(1)) , Double.parseDouble(selector.pList.get(12).get(2)));
            double corsiOpp = MyMath.getCorsi(Double.parseDouble(selector.pList.get(7).get(2)), Double.parseDouble(selector.pList.get(21).get(2)) , Double.parseDouble(selector.pList.get(12).get(1)));
            double corsiPercent = MyMath.round(100 * corsi / (corsi + corsiOpp), 2);
            double fenwick = MyMath.getFenwick(Double.parseDouble(selector.pList.get(7).get(1)), Double.parseDouble(selector.pList.get(21).get(1)));
            double fenwickOpp = MyMath.getFenwick(Double.parseDouble(selector.pList.get(7).get(2)), Double.parseDouble(selector.pList.get(21).get(2)));
            double fenwickPercent = MyMath.round(100 * fenwick / (fenwick + fenwickOpp), 2);
            double realization = MyMath.round(100 * Double.parseDouble(selector.pList.get(5).get(1))/selector.listOfMatches.size() / Double.parseDouble(selector.pList.get(7).get(1)),2);
            double realizationOpp = MyMath.round(100 * Double.parseDouble(selector.pList.get(5).get(2))/selector.listOfMatches.size() / Double.parseDouble(selector.pList.get(7).get(2)),2);

            if (Double.parseDouble(selector.pList.get(20).get(1)) >= Double.parseDouble(selector.pList.get(20).get(2))){
                timeAttackDiff = Settings.getTimeInMinutesAndSecondsFromSeconds((Double.parseDouble(selector.pList.get(20).get(1)) - Double.parseDouble(selector.pList.get(20).get(2))) / matches);
            } else {
                timeAttackDiff =  "-" + Settings.getTimeInMinutesAndSecondsFromSeconds((Double.parseDouble(selector.pList.get(20).get(2)) - Double.parseDouble(selector.pList.get(20).get(1))) / matches);
            }

            String timeAttackTotal = Settings.getTimeInMinutesAndSecondsFromSeconds((Double.parseDouble(selector.pList.get(20).get(1)) + Double.parseDouble(selector.pList.get(20).get(2))) / matches);

            if (timeAttack.equals("0:00")){
                timeAttack = "No info";
                timeAttackOpp = "No info";
                timeAttackDiff = "No info";
                timeAttackTotal = "No info";
            }

            double faceoffsFor = MyMath.round(Double.parseDouble(selector.pList.get(11).get(1)),2);
            double faceoffsOpp = MyMath.round(Double.parseDouble(selector.pList.get(11).get(2)),2);
            double faceoffsPercent = MyMath.round(faceoffsFor / (faceoffsFor + faceoffsOpp) * 100, 2);
            double faceoffsOppPercent = MyMath.round(faceoffsOpp / (faceoffsFor + faceoffsOpp) * 100, 2);
            Object[][] data = {
                    {" " + params[0],
                            MyMath.round(Double.parseDouble(selector.pList.get(5).get(1))/selector.listOfMatches.size(),2),
                            MyMath.round(Double.parseDouble(selector.pList.get(5).get(2))/selector.listOfMatches.size(),2),
                            MyMath.round((Double.parseDouble(selector.pList.get(5).get(1)) - Double.parseDouble(selector.pList.get(5).get(2)))/selector.listOfMatches.size(),2),
                            MyMath.round((Double.parseDouble(selector.pList.get(5).get(1)) + Double.parseDouble(selector.pList.get(5).get(2)))/selector.listOfMatches.size(),2)},
                    {" " + params[1],
                            selector.pList.get(7).get(1),
                            selector.pList.get(7).get(2),
                            MyMath.round(Double.parseDouble(selector.pList.get(7).get(1)) - Double.parseDouble(selector.pList.get(7).get(2)) , 2),
                            MyMath.round(Double.parseDouble(selector.pList.get(7).get(1)) + Double.parseDouble(selector.pList.get(7).get(2)) , 2),},
                    {" " + params[2],
                            MyMath.round(100 * Double.parseDouble(selector.pList.get(5).get(1))/selector.listOfMatches.size() / Double.parseDouble(selector.pList.get(7).get(1)),2),
                            MyMath.round(100 * Double.parseDouble(selector.pList.get(5).get(2))/selector.listOfMatches.size() / Double.parseDouble(selector.pList.get(7).get(2)),2),
                            "-",
                            "-"},
                    {" " + params[3],
                            (int) Double.parseDouble(selector.pList.get(17).get(1)) + " (" + MyMath.round( 100*Double.parseDouble(selector.pList.get(17).get(1)) / Double.parseDouble(selector.pList.get(5).get(1)) , 1) +  "%)",
                            (int) Double.parseDouble(selector.pList.get(17).get(2)) + " (" + MyMath.round( 100*Double.parseDouble(selector.pList.get(17).get(2)) / Double.parseDouble(selector.pList.get(5).get(2)) , 1) +  "%)",
                            (int) (Double.parseDouble(selector.pList.get(17).get(1)) - Double.parseDouble(selector.pList.get(17).get(2))),
                            (int) (Double.parseDouble(selector.pList.get(17).get(1)) + Double.parseDouble(selector.pList.get(17).get(2)))},
                    {" " + params[4],
                            (int) Double.parseDouble(selector.pList.get(18).get(1)) + " (" + MyMath.round( 100*Double.parseDouble(selector.pList.get(18).get(1)) / Double.parseDouble(selector.pList.get(5).get(1)) , 1) +  "%)",
                            (int) Double.parseDouble(selector.pList.get(18).get(2)) + " (" + MyMath.round( 100*Double.parseDouble(selector.pList.get(18).get(2)) / Double.parseDouble(selector.pList.get(5).get(2)) , 1) +  "%)",
                            (int) (Double.parseDouble(selector.pList.get(18).get(1)) - Double.parseDouble(selector.pList.get(18).get(2))),
                            (int) (Double.parseDouble(selector.pList.get(18).get(1)) + Double.parseDouble(selector.pList.get(18).get(2)))},
                    {" " + params[5],
                            (int) Double.parseDouble(selector.pList.get(19).get(1)) + " (" + MyMath.round( 100*Double.parseDouble(selector.pList.get(19).get(1)) / Double.parseDouble(selector.pList.get(5).get(1)) , 1) +  "%)",
                            (int) Double.parseDouble(selector.pList.get(19).get(2)) + " (" + MyMath.round( 100*Double.parseDouble(selector.pList.get(19).get(2)) / Double.parseDouble(selector.pList.get(5).get(2)) , 1) +  "%)",
                            (int) (Double.parseDouble(selector.pList.get(19).get(1)) - Double.parseDouble(selector.pList.get(19).get(2))),
                            (int) (Double.parseDouble(selector.pList.get(19).get(1)) + Double.parseDouble(selector.pList.get(19).get(2)))},
                    {" " + params[6],
                            (int) Double.parseDouble(selector.pList.get(9).get(1)),
                            (int) Double.parseDouble(selector.pList.get(9).get(2)),
                            (int) (Double.parseDouble(selector.pList.get(9).get(1)) - Double.parseDouble(selector.pList.get(9).get(2))),
                            (int) (Double.parseDouble(selector.pList.get(9).get(1)) + Double.parseDouble(selector.pList.get(9).get(2))),},
                    {" " + params[7],
                            bol,
                            bolOp,
                            "-",
                            "-"},
                    {" " + params[8],
                            (int) Double.parseDouble(selector.pList.get(9).get(2)),
                            (int) Double.parseDouble(selector.pList.get(9).get(1)),
                            (int) (Double.parseDouble(selector.pList.get(9).get(2)) - Double.parseDouble(selector.pList.get(9).get(1))),
                            (int) (Double.parseDouble(selector.pList.get(9).get(2)) + Double.parseDouble(selector.pList.get(9).get(1))),},
                    {" " + params[9],
                            men,
                            menOp,
                            "-",
                            "-"},
                    {" " + params[10],
                            timeAttack,
                            timeAttackOpp,
                            timeAttackDiff,
                            timeAttackTotal,},
                    {" " + params[11],
                            (int) Double.parseDouble(selector.pList.get(10).get(1)),
                            (int) Double.parseDouble(selector.pList.get(10).get(2)),
                            (int) (Double.parseDouble(selector.pList.get(10).get(1)) - Double.parseDouble(selector.pList.get(10).get(2))),
                            (int) (Double.parseDouble(selector.pList.get(10).get(1)) + Double.parseDouble(selector.pList.get(10).get(2))),},
                    {" " + params[12],
                            faceoffsFor + " (" + faceoffsPercent + "%)",
                            faceoffsOpp + " (" + faceoffsOppPercent + "%)",
                            MyMath.round(faceoffsFor - faceoffsOpp, 2),
                            MyMath.round(faceoffsFor + faceoffsOpp, 2)},
                    {" " + params[13],
                            Double.parseDouble(selector.pList.get(21).get(1)),
                            Double.parseDouble(selector.pList.get(21).get(2)),
                            MyMath.round(Double.parseDouble(selector.pList.get(21).get(1)) - Double.parseDouble(selector.pList.get(21).get(2)) , 2),
                            MyMath.round(Double.parseDouble(selector.pList.get(21).get(1)) + Double.parseDouble(selector.pList.get(21).get(2)) , 2),},
                    {" " + params[14],
                            Double.parseDouble(selector.pList.get(12).get(1)),
                            Double.parseDouble(selector.pList.get(12).get(2)),
                            MyMath.round(Double.parseDouble(selector.pList.get(12).get(1)) - Double.parseDouble(selector.pList.get(12).get(2)) , 2),
                            MyMath.round(Double.parseDouble(selector.pList.get(12).get(1)) + Double.parseDouble(selector.pList.get(12).get(2)) , 2),},
                    {" " + params[15],
                            Double.parseDouble(selector.pList.get(13).get(1)),
                            Double.parseDouble(selector.pList.get(13).get(2)),
                            MyMath.round(Double.parseDouble(selector.pList.get(13).get(1)) - Double.parseDouble(selector.pList.get(13).get(2)) , 2),
                            MyMath.round(Double.parseDouble(selector.pList.get(13).get(1)) + Double.parseDouble(selector.pList.get(13).get(2)) , 2),},
                    {" " + params[16],
                            MyMath.round(Double.parseDouble(selector.pList.get(14).get(1))/selector.listOfMatches.size(),2),
                            MyMath.round(Double.parseDouble(selector.pList.get(14).get(2))/selector.listOfMatches.size(),2),
                            MyMath.round((Double.parseDouble(selector.pList.get(14).get(1)) - Double.parseDouble(selector.pList.get(14).get(2)))/ selector.listOfMatches.size() , 2),
                            MyMath.round((Double.parseDouble(selector.pList.get(14).get(1)) + Double.parseDouble(selector.pList.get(14).get(2)))/ selector.listOfMatches.size() , 2),},
                    {" " + params[17],
                            MyMath.round(Double.parseDouble(selector.pList.get(15).get(1))/selector.listOfMatches.size(),2),
                            MyMath.round(Double.parseDouble(selector.pList.get(15).get(2))/selector.listOfMatches.size(),2),
                            MyMath.round((Double.parseDouble(selector.pList.get(15).get(1)) - Double.parseDouble(selector.pList.get(15).get(2)))/ selector.listOfMatches.size() , 2),
                            MyMath.round((Double.parseDouble(selector.pList.get(15).get(1)) + Double.parseDouble(selector.pList.get(15).get(2)))/ selector.listOfMatches.size() , 2)},
                    {" " + params[18],
                            corsi,
                            corsiOpp,
                            MyMath.round(corsi - corsiOpp, 2),
                            MyMath.round(corsi + corsiOpp, 2)
                    },
                    {" " + params[19],
                            corsiPercent,
                            MyMath.round(100 - corsiPercent,2),
                            MyMath.round(corsiPercent - (100 - corsiPercent), 2),
                            100
                    },
                    {" " + params[20],
                            fenwick,
                            fenwickOpp,
                            MyMath.round(fenwick - fenwickOpp,2),
                            MyMath.round(fenwick + fenwickOpp,2)
                    },
                    {" " + params[21],
                            fenwickPercent,
                            MyMath.round(100 - fenwickPercent,2),
                            MyMath.round(fenwickPercent - (100 - fenwickPercent),2),
                            100
                    },
                    {" " + params[22],
                            MyMath.round(realization + 100 - realizationOpp, 2),
                            MyMath.round(realizationOpp + 100 - realization, 2),
                            "-",
                            "-"
                    }
            };

            JTable table = new JTable(data, colHeads);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.setEnabled(false);
            table.getTableHeader().setFont(font15);
            table.setFont(font15);
            table.setRowHeight(25);
            //table.getColumnModel().getColumn(0).setPreferredWidth(297);

            Renderer renderer = new Renderer(0);
//                renderer.setHorizontalAlignment(JLabel.CENTER);

            for (int i=1; i<table.getColumnCount();i++){
                table.getColumnModel().getColumn(i).setCellRenderer(renderer);
            }

            table.getColumnModel().getColumn(0).setPreferredWidth(180);

            JPanel tablePanel = new JPanel();
            tablePanel.setLayout(new BorderLayout());
            tablePanel.add(table);
            tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);

            tablePanel.setSize(650, (params.length + 1) *table.getRowHeight() - 3);
            tablePanel.setLocation(10, otstup);
            otstup += (params.length + 1) *table.getRowHeight() - 3;
            container.setPreferredSize(new Dimension(600, otstup));
            container.add(tablePanel);

//            allInfoPanel.add(container,BorderLayout.NORTH);
            leftInfo.add(container,BorderLayout.NORTH);

            ////////////////////////ГРАФИКИ ВСТАВЛЯЮ ТУТ
            final Graphic graphic;
            final JPanel panelG;
            if (settings.showGraphics){
                graphic = new Graphic(0, teamName);
                panelG = graphic.getGraphics(teamName, allOrHomeOrAway, lastOrFullSeason, selector, tournamentTable);
                //panelG.setPreferredSize(new Dimension(300,1000));
                leftInfo.add(panelG);
            } else {
                graphic = new Graphic(0, teamName);
                panelG = graphic.getTablesWithStats(teamName, allOrHomeOrAway, selector, tournamentTable);
                leftInfo.add(panelG);
            }

            ////////////////////////ГРАФИКИ БОЛЬШЕ НЕ ВСТАВЛЯЮ
            scrollPane = new JScrollPane(leftInfo);
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

            final String finalSeason = season;
            buttonTrends.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    WindowTrendsOfTeam wtt = new WindowTrendsOfTeam(teamName, allOrHomeOrAway, finalSeason, lastOrFullSeason, selector);
                    wtt.setVisible(true);
                }
            });

            buttonDiagrams.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    WindowWithDiagrams wwd = new WindowWithDiagrams(teamName, allOrHomeOrAway, finalSeason, lastOrFullSeason, selector);
                    wwd.setVisible(true);
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
        } else {
            JPanel container = new JPanel(new BorderLayout());

            String labelText = "";
            if (allOrHomeOrAway.contains("Все")){
                labelText = "   В сезоне " + season + " команда " + teamName + " не провела ни одного матча.";
            }
            if (allOrHomeOrAway.contains("Дома")){
                labelText = "   В сезоне " + season + " команда " + teamName + " не провела ни одного матча на своем поле.";
            }
            if (allOrHomeOrAway.contains("На выезде")){
                labelText = "   В сезоне " + season + " команда " + teamName + " не провела ни одного матча на выезде.";
            }
            JLabel label = new JLabel(labelText);
            label.setLocation(10, 0);
            label.setPreferredSize(new Dimension(500, 25));
            label.setFont(font15);
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
        String season = Settings.getCurrentSeasonInLeague(league);
        seasonChooser.setSelectedItem("Сезон " + season);
        leagueChooser.setSelectedItem(league);
        teamChooser.setSelectedItem(homeTeam);
        teamAllOrHomeOrAway.setSelectedItem("Дома");
        teamLastOrFullSeason.setSelectedItem("Весь сезон");
    }
}