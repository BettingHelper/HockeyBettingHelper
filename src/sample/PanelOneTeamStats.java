package sample;

import org.jfree.ui.tabbedui.VerticalLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class PanelOneTeamStats extends JPanel{
    //int WIDTH;
    //int DEFWIDTH = 1600;
    //double procWIDTH;
    //int HEIGHT;
    //int DEFHEIGHT = 1000;
    //int graphicWidth = 1250;
    int graphicHeight = 410;
    double procHEIGHT;
    int index1 = 0;
    int index2 = 0;
    int index3 = 0;
    double valueForSlider = 0;
    double minSliderValue = 0;
    double maxSliderValue = 0;
    double stepSlider = 0.5;
    JLabel leftValue;
    JLabel rightValue;
    JLabel bottomValue;
    JScrollPane scrollPane;
    JSlider slider = new JSlider(0,10,0);

    JComboBox<String> seasonChooser;
    JComboBox<String> leagueChooser;
    JComboBox<String> teamChooser;
    JComboBox<String> teamAllOrHomeOrAway;
    JComboBox<String> lastOrFullSeason;
    JPanel teamPanel;
    JPanel infoPanel;

    final Font font15 = new Font("Arial", Font.BOLD, 15);
    final Font font16 = new Font("Arial", Font.BOLD, 16);
    final Font font18 = new Font("Arial", Font.BOLD, 18);
    final Font font20 = new Font("Arial", Font.BOLD, 20);
    int heightOfTable = 0;

    public PanelOneTeamStats(){
        this.setLayout(new BorderLayout());
        //WIDTH = width;
        //HEIGHT = height;
        //procWIDTH = WIDTH / (double) DEFWIDTH;
        //procHEIGHT = HEIGHT / (double) DEFHEIGHT;
        //if (procWIDTH == 1)
        //    this.graphicWidth = 1300;
        String curSeason = Settings.getCurrentSeason();
        final String path = "database/";

        ////////////////////////////////////////////ПАНЕЛЬ
        teamPanel = new JPanel(new BorderLayout());
        teamPanel.setBorder(BorderFactory.createTitledBorder(""));

        JPanel panelChoosers = new JPanel(new GridLayout(1, 0, 5, 5));

        ArrayList<String> listOfSeasons = Settings.getListOfSeasons();
        String[] seasonList = new String[listOfSeasons.size()];
        for (int i = 0; i < seasonList.length; i++)
            seasonList[i] = "Сезон " + listOfSeasons.get(i);
        seasonChooser = new JComboBox<>(seasonList);
//        seasonChooser.setPreferredSize(new Dimension((int) (120 * procWIDTH), 30));
        panelChoosers.add(seasonChooser);

        JFileChooser fileChooser = new JFileChooser(path + curSeason + "/leagues");
        String[] directoryList = fileChooser.getCurrentDirectory().list();
        ArrayList<String> leagueList = new ArrayList<>();
        leagueList.add("Лига");
        for (String aDirectoryList : directoryList) leagueList.add(aDirectoryList.replace(".txt", ""));
        String[] listOfLeagues = new String[leagueList.size()];
        for (int i = 0; i < listOfLeagues.length; i++)
            listOfLeagues[i] = leagueList.get(i);
        leagueChooser = new JComboBox<>(listOfLeagues);
//        leagueChooser.setPreferredSize(new Dimension((int) (100 * procWIDTH), 30));
        panelChoosers.add(leagueChooser);

        String fileName = path + curSeason + "/leagues/" + leagueChooser.getSelectedItem() + ".txt";
        String[] list = {"Команда"};
        if (!fileName.contains("Лига")) {
            list = Main.readTxtFile(fileName);
        }
        teamChooser = new JComboBox<>(list);
//        teamChooser.setPreferredSize(new Dimension((int) (120 * procWIDTH), 30));
        teamChooser.setEnabled(false);
        panelChoosers.add(teamChooser);

        final String[] allOrHomeOrAway = {"Все матчи", "Дома", "На выезде"};
        teamAllOrHomeOrAway = new JComboBox<>(allOrHomeOrAway);
//        teamAllOrHomeOrAway.setPreferredSize(new Dimension((int) (120 * procWIDTH), 30));
        panelChoosers.add(teamAllOrHomeOrAway);

        final String[] lastOrFullSeasonS = {"Весь сезон", "Последние 3", "Последние 4", "Последние 5", "Последние 6", "Последние 7", "Последние 8", "Последние 9", "Последние 10", "Последние 15", "Последние 20"};
        lastOrFullSeason = new JComboBox<>(lastOrFullSeasonS);
//        lastOrFullSeason.setPreferredSize(new Dimension((int) (140 * procWIDTH), 30));
        panelChoosers.add(lastOrFullSeason);

        final JButton buttonShowInfo = new JButton("Отобразить!");
//        buttonShowInfo.setPreferredSize(new Dimension((int) (150 * procWIDTH), 30));
//        Font fontForButton = new Font("", 0, (int) (20 * procWIDTH * 0.9));
        buttonShowInfo.setFont(font18);
        panelChoosers.add(buttonShowInfo);

        teamPanel.add(panelChoosers, BorderLayout.NORTH);

        infoPanel = new JPanel();
        teamPanel.add(infoPanel, BorderLayout.CENTER);

        this.add(teamPanel);

        seasonChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seasonChooser.setFocusable(false);

                String team = String.valueOf(teamChooser.getSelectedItem());
                String league = String.valueOf(leagueChooser.getSelectedItem());

                String pathToLeaguesList = path + seasonChooser.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/";
                JFileChooser fileChooser = new JFileChooser(pathToLeaguesList);
                String[] directoryList = new String[fileChooser.getCurrentDirectory().list().length+1];
                directoryList[0] = "Выберите лигу";
                for (int i=1; i<directoryList.length; i++)
                    directoryList[i] = fileChooser.getCurrentDirectory().list()[i-1].replace(".txt", "");
                DefaultComboBoxModel modelH = new DefaultComboBoxModel(directoryList);
                leagueChooser.setModel(modelH);

                for (int i=0; i<directoryList.length; i++){
                    if (directoryList[i].equals(league))
                        leagueChooser.setSelectedItem(league);
                }

                String pathToTeamsList = path + seasonChooser.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/" + leagueChooser.getSelectedItem() + ".txt";
                String[] list = {"Выберите команду"};
                if (!pathToTeamsList.contains("ыберите")) {
                    list = Main.readTxtFile(pathToTeamsList);
                }
                modelH = new DefaultComboBoxModel(list);
                teamChooser.setModel(modelH);
                teamChooser.setEnabled(false);

                pathToTeamsList = path + seasonChooser.getSelectedItem().toString().replace("Сезон ", "") + "/" + league + "/Teams/";
                fileChooser = new JFileChooser(pathToTeamsList);
                directoryList = new String[fileChooser.getCurrentDirectory().list().length+1];
                directoryList[0] = "Выберите команду";
                for (int i=1; i<directoryList.length; i++)
                    directoryList[i] = fileChooser.getCurrentDirectory().list()[i-1].replace(".xml", "");
                for (int i=0; i<directoryList.length; i++){
                    if (directoryList[i].equals(team)){
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
                directoryList[0] = "Лига";
                for (int i=1; i<directoryList.length; i++)
                    directoryList[i] = fileChooser.getCurrentDirectory().list()[i-1].replace(".txt", "");
                DefaultComboBoxModel modelH = new DefaultComboBoxModel(directoryList);
                leagueChooser.setModel(modelH);
                leagueChooser.setSelectedIndex(index);

                teamChooser.setEnabled(true);
                String pathToTeamsList = path + seasonChooser.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/" + leagueChooser.getSelectedItem() + ".txt";
                String[] listRight = {"Команда"};
                if (!pathToTeamsList.contains("Лига")) {
                    listRight = Main.readTxtFile(pathToTeamsList);
                }
                modelH = new DefaultComboBoxModel(listRight);
                teamChooser.setModel(modelH);

            }
        });

        teamChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        teamAllOrHomeOrAway.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        buttonShowInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (infoPanel != null) {
                    teamPanel.remove(infoPanel);
                }
                infoPanel = refreshData((String) leagueChooser.getSelectedItem(),
                        (String) teamChooser.getSelectedItem(),
                        (String) teamAllOrHomeOrAway.getSelectedItem(),
                        (String) seasonChooser.getSelectedItem(),
                        (String) lastOrFullSeason.getSelectedItem()
                );

                teamPanel.add(infoPanel);
                teamPanel.revalidate();
                buttonShowInfo.setFocusable(false);

            }
        });

    }

    public JPanel refreshData(String leagueName, final String teamName, final String allOrHomeOrAway, String season, final String lastOrFullSeason){
        this.setCursor(Cursor.getPredefinedCursor (Cursor.WAIT_CURSOR));
        final Settings settings = Settings.getSettingsFromFile();
        JPanel result = new JPanel(new BorderLayout());
        final JScrollPane scrollPane;
        season = season.replace("Сезон ", "");
        if ((!leagueName.contains("Лига"))&&(!teamName.contains("Команда"))){
            final Selector selector = new Selector();
            ArrayList<String> tournamentTable = League.getLeagueFromFile(leagueName, season).tournamentTable;
            selector.getListOfMatches(leagueName, teamName, allOrHomeOrAway, season, lastOrFullSeason);
            selector.getPList(selector.listOfMatches, teamName, season);

            if (selector.listOfMatches.size()>0){
                final JPanel container = new JPanel();
                JPanel allInfoPanel = new JPanel(new BorderLayout());

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
                label.setLocation(5, otstup);
                otstup += 25;
                label.setSize(new Dimension(600, 25));
                label.setFont(font15);
                container.add(label);

                String teamPoints = "Набрано очков: " + String.valueOf(points);
                final JLabel label2 = new JLabel(teamPoints);
                label2.setLocation(5, otstup);
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
                label3.setLocation(5, otstup);
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
                label4.setLocation(5, otstup);
                otstup += 25;
                label4.setSize(new Dimension(600, 25));
                label4.setFont(font15);
                container.add(label4);

                String forma = selector.pList.get(16).get(1);
                final Dimension defFormaLocation = new Dimension(65,otstup + 3);
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
                label5.setLocation(5, otstup);
                otstup += 35;
                label5.setSize(new Dimension(600, 25));
                label5.setFont(font15);
                container.add(label5);

                String sources = "Сайт клуба / Твиттер:";
                final JLabel labelSources = new JLabel(sources);
                labelSources.setLocation(10,otstup);
                otstup += 35;
                labelSources.setSize(new Dimension(200, 25));
                labelSources.setFont(font15);
                container.add(labelSources);

                final JButton buttonSite = new JButton(new ImageIcon("images/www.png"));
                if (Team.getWebsite(teamName).equals("site"))
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
                if (lastOrFullSeason.contains("Весь сезон")){
                    if (allOrHomeOrAway.contains("Все")){
                        teamSrednie = "Показатели всех игр " + teamName + " в сезоне " + season + " (осн. время):";
                    }
                    if (allOrHomeOrAway.contains("Дома")){
                        teamSrednie = "Показатели всех домашних игр " + teamName + " в сезоне " + season + " (осн. время):";
                    }
                    if (allOrHomeOrAway.contains("На выезде")){
                        teamSrednie = "Показатели всех гостевых игр " + teamName + " в сезоне " + season + " (осн. время):";
                    }
                } else {
                    if (allOrHomeOrAway.contains("Все")){
                        teamSrednie = "Показатели последних " + matches + " игр " + teamName + " в сезоне " + season + " (осн. время):";
                    }
                    if (allOrHomeOrAway.contains("Дома")){
                        teamSrednie = "Показатели последних " + matches + " домашних игр " + teamName + " в сезоне " + season + " (осн. время):";
                    }
                    if (allOrHomeOrAway.contains("На выезде")){
                        teamSrednie = "Показатели последних " + matches + " гостевых игр " + teamName + " в сезоне " + season + " (осн. время):";
                    }
                }

                final JLabel label6 = new JLabel(teamSrednie);
                label6.setLocation(5, otstup);
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
                String timeAttack = String.valueOf(Settings.getTimeInMinutesAndSecondsFromSeconds(Double.parseDouble(selector.pList.get(20).get(1)) / matches));
                String timeAttackOpp = String.valueOf(Settings.getTimeInMinutesAndSecondsFromSeconds(Double.parseDouble(selector.pList.get(20).get(2)) / matches));
                String timeAttackDiff = String.valueOf(Settings.getTimeInMinutesAndSecondsFromSeconds((Double.parseDouble(selector.pList.get(20).get(1)) - Double.parseDouble(selector.pList.get(20).get(2))) / matches));
                String timeAttackTotal = String.valueOf(Settings.getTimeInMinutesAndSecondsFromSeconds((Double.parseDouble(selector.pList.get(20).get(1)) + Double.parseDouble(selector.pList.get(20).get(2))) / matches));
                double corsi = MyMath.getCorsi(Double.parseDouble(selector.pList.get(7).get(1)), Double.parseDouble(selector.pList.get(21).get(1)) , Double.parseDouble(selector.pList.get(12).get(2)));
                double corsiOpp = MyMath.getCorsi(Double.parseDouble(selector.pList.get(7).get(2)), Double.parseDouble(selector.pList.get(21).get(2)) , Double.parseDouble(selector.pList.get(12).get(1)));
                double corsiPercent = MyMath.round(100 * corsi / (corsi + corsiOpp), 2);
                double fenwick = MyMath.getFenwick(Double.parseDouble(selector.pList.get(7).get(1)), Double.parseDouble(selector.pList.get(21).get(1)));
                double fenwickOpp = MyMath.getFenwick(Double.parseDouble(selector.pList.get(7).get(2)), Double.parseDouble(selector.pList.get(21).get(2)));
                double fenwickPercent = MyMath.round(100 * fenwick / (fenwick + fenwickOpp), 2);
                double realization = MyMath.round(100 * Double.parseDouble(selector.pList.get(5).get(1))/selector.listOfMatches.size() / Double.parseDouble(selector.pList.get(7).get(1)),2);
                double realizationOpp = MyMath.round(100 * Double.parseDouble(selector.pList.get(5).get(2))/selector.listOfMatches.size() / Double.parseDouble(selector.pList.get(7).get(2)),2);


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

                // Вставка таблицы корреляционных показателей
                String corrLabelText = "Таблица корреляций статистических показателей " + teamName;
                final JLabel labelCorr = new JLabel(corrLabelText);
                labelCorr.setLocation(610, 125);
                labelCorr.setSize(650, 25);
                labelCorr.setFont(font15);
//                container.add(labelCorr);

                JButton buttonCorrInfo = new JButton();
                buttonCorrInfo.setIcon(new ImageIcon("images/info.png"));
                buttonCorrInfo.setLocation(610 + 573, 130);
                buttonCorrInfo.setSize(20, 20);
//                container.add(buttonCorrInfo);

                JScrollPane panelCorrSP = new JScrollPane();
                panelCorrSP.setLayout(null);
                panelCorrSP.setSize(594, 300);
                panelCorrSP.setLocation(610, 150);
                JPanel panelCorr = TableMaker.getTableCorrelation(teamName, selector.listOfMatches, selector.pList);
                panelCorr.setSize(650, 300);
                panelCorr.setLocation(0, 0);

                panelCorrSP.add(panelCorr);
//                container.add(panelCorrSP);

                //Вставка просмотрщика параметров
                final JPanel paramsPanel = new JPanel(new VerticalLayout());
                paramsPanel.setBorder(BorderFactory.createTitledBorder(""));
                JPanel selectorsPanel = new JPanel(new GridLayout(1, 0, 5, 5));

                String[] paramsList = Parameters.getParamsList(season);
                final JComboBox<String> paramsChooser = new JComboBox<>(paramsList);
                paramsChooser.setFont(font15);
                selectorsPanel.add(paramsChooser);

                String[] indexList = {"Выберите тип ставки"};
                final JComboBox<String> indexChooser = new JComboBox<>(indexList);
                indexChooser.setFont(font15);
                selectorsPanel.add(indexChooser);
                paramsPanel.add(selectorsPanel);

                JPanel panelSlider = new JPanel(new BorderLayout());

                leftValue = new JLabel("");
                leftValue.setFont(font15);
                leftValue.setPreferredSize(new Dimension(40, 40));
                leftValue.setHorizontalAlignment(SwingConstants.CENTER);
                panelSlider.add(leftValue, BorderLayout.WEST);
                rightValue = new JLabel("");
                rightValue.setFont(font15);
                rightValue.setPreferredSize(new Dimension(40, 40));
                rightValue.setHorizontalAlignment(SwingConstants.CENTER);
                panelSlider.add(rightValue, BorderLayout.EAST);
                bottomValue = new JLabel("");
                bottomValue.setFont(font15);
                bottomValue.setPreferredSize(new Dimension(40, 40));
                bottomValue.setHorizontalAlignment(SwingConstants.CENTER);
                panelSlider.add(bottomValue, BorderLayout.SOUTH);


                slider.setPaintLabels(false);
                slider.setMajorTickSpacing(1);
                slider.setPaintTicks(true);
                slider.setEnabled(false);
                panelSlider.add(slider);
                panelSlider.setBorder(new EmptyBorder(10, 0, 0, 0));
                paramsPanel.add(panelSlider);

                paramsPanel.setSize(620, 170);
                paramsPanel.setLocation(610, 10);
                container.add(paramsPanel);
                container.setPreferredSize(new Dimension(600, otstup));

                allInfoPanel.add(container, BorderLayout.NORTH);

                //////////////////////////////Конец вставки просмотрщика по параметрам

                ////////////////////////ГРАФИКИ И ТАБЛИЦЫ ВСТАВЛЯЮ ТУТ
                //JPanel graphAndTables = new JPanel(new BorderLayout());


//                GraphicForOneTeam graphic = new GraphicForOneTeam();
                //final Graphic2 graphic = new Graphic2(1, teamName);
//                final JPanel panelGraphics = graphic.getGraphics(teamName, allOrHomeOrAway, lastOrFullSeason, selector, tournamentTable);

                //graphAndTables.add(panelGraphics, BorderLayout.CENTER);
                //graphAndTables.add(panelTables, BorderLayout.EAST);

//                allInfoPanel.add(panelGraphics, BorderLayout.CENTER);
                //allInfoPanel.add(graphAndTables, BorderLayout.CENTER);

                final Graphic graphic;
                final JPanel panelG;
                if (settings.showGraphics){
                    graphic = new Graphic(1, teamName);
                    panelG = graphic.getGraphics(teamName, allOrHomeOrAway, lastOrFullSeason, selector, tournamentTable);
                    //panelG.setPreferredSize(new Dimension(300,1000));
                    allInfoPanel.add(panelG);
                } else {
                    graphic = new Graphic(1, teamName);
                    panelG = graphic.getTablesWithStats(teamName, allOrHomeOrAway, selector, tournamentTable);
                    allInfoPanel.add(panelG);
                }


                paramsChooser.setSelectedIndex(index1);
                String[] list = Parameters.getIndex();
                DefaultComboBoxModel modelH = new DefaultComboBoxModel(list);
                indexChooser.setModel(modelH);
                indexChooser.setSelectedIndex(index2);
                slider.setValue(index3);
                final double[] sliderParams = Parameters.getValues((String) paramsChooser.getSelectedItem(), (String) indexChooser.getSelectedItem());
                stepSlider = sliderParams[2];

                if (sliderParams[2] > 0){
                    minSliderValue = sliderParams[0];
                    stepSlider = sliderParams[2];
                    leftValue.setText(String.valueOf(sliderParams[0]));
                    rightValue.setText(String.valueOf(sliderParams[1]));
                    bottomValue.setText("Выбрано значение: " + String.valueOf(valueForSlider));
                    getParamsPanel(paramsPanel, teamName, selector, paramsChooser, indexChooser, valueForSlider);
                    slider.setMajorTickSpacing(1);
                    slider.setPaintTicks(true);
                    slider.setEnabled(true);
                    int numberOfVariants = (int) ((sliderParams[1] - sliderParams[0]) / sliderParams[2] + 1);
                    slider.setMaximum(numberOfVariants-1);
                }

                ////////////////////////ГРАФИКИ И ТАБЛИЦЫ БОЛЬШЕ НЕ ВСТАВЛЯЮ
                scrollPane = new JScrollPane(allInfoPanel);
                scrollPane.setVerticalScrollBar( new JScrollBar() {
                    public int getUnitIncrement( int direction ) {
                        return 50;
                    }
                } );


                paramsChooser.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        index1 = paramsChooser.getSelectedIndex();
                        String[] list = Parameters.getIndex();
                        DefaultComboBoxModel modelH = new DefaultComboBoxModel(list);
                        indexChooser.setModel(modelH);
                        index2 = indexChooser.getSelectedIndex();
                        slider.setEnabled(false);
                        slider.setValue(0);
                        index3 = 0;
                        bottomValue.setText("");
                        leftValue.setText("");
                        rightValue.setText("");

                        paramsChooser.setFocusable(false);
                    }
                });

                indexChooser.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        index2 = indexChooser.getSelectedIndex();
                        double[] sliderParams = Parameters.getValues((String) paramsChooser.getSelectedItem(), (String) indexChooser.getSelectedItem());
                        minSliderValue = sliderParams[0];
                        valueForSlider = sliderParams[0];
                        stepSlider = sliderParams[2];
                        slider.setEnabled(true);
                        slider.setValue(0);
                        index3 = 0;
                        int numberOfVariants = (int) ((sliderParams[1] - sliderParams[0]) / sliderParams[2] + 1);
                        slider.setMaximum(numberOfVariants-1);
                        leftValue.setText(String.valueOf(sliderParams[0]));
                        rightValue.setText(String.valueOf(sliderParams[1]));
                        bottomValue.setText("Выбрано значение: " + String.valueOf(valueForSlider));

                        getParamsPanel(paramsPanel, teamName, selector, paramsChooser, indexChooser, valueForSlider);
                        indexChooser.setFocusable(false);
                    }
                });

                slider.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        valueForSlider = minSliderValue + stepSlider*slider.getValue();
                        bottomValue.setText("Выбрано значение: " + String.valueOf(valueForSlider));
                        index3 = slider.getValue();
                        getParamsPanel(paramsPanel, teamName, selector, paramsChooser, indexChooser, valueForSlider);
                    }
                });

                buttonCorrInfo.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String textCorr = "<html>    В таблице выводятся коэффициенты корреляции Пирсона (линейные коэффициенты корреляции). <br>" +
                                "   Они лежат в пределах [-1; 1] и показывают статистическую зависимость одной величины от другой,<br>" +
                                "   то есть, проще говоря, насколько один параметр зависит от другого.<br>" +
                                "<br>" +
                                "   Например: Если значение коэффициента корреляции между владением и угловыми высоко,<br>" +
                                "   то при невысоком показателе владения команда подает меньше угловых, а при высоком - больше.<br>" +
                                "   Если же значение коэффициента отрицательное, то при невысоком показателе владения<br>" +
                                "   команда подает больше угловых.<br>" +
                                "<br>" +
                                "   Данная информация может помочь вам при анализе показателей команд.<br>" +
                                "</html>";


                        PopupWindow windowCorr = new PopupWindow(textCorr);
                        windowCorr.setSize(900, 300);
                        windowCorr.setLocation(500, 120);
                        windowCorr.setVisible(true);
                    }
                });

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
                final JPanel container = new JPanel();
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
                label.setSize(new Dimension(600, 25));
                label.setFont(font15);
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
            label.setSize(new Dimension(600, 25));
            label.setFont(font15);
            container.add(label, BorderLayout.NORTH);

            scrollPane = new JScrollPane(container);
            scrollPane.setVerticalScrollBar(new JScrollBar() {
                public int getUnitIncrement(int direction) {
                    return 50;
                }
            });

        }
        this.setCursor(Cursor.getDefaultCursor());
        result.add(scrollPane);

        return result;
    }

    private void getParamsPanel(JPanel paramsPanel, String teamName, Selector selector, JComboBox paramsChooser, JComboBox indexChooser, double valueOfSlider){
        if (paramsPanel.getComponentCount()>2){
            paramsPanel.remove(2);
        }
        final JTable tableByParams = Parameters.getTableByParams(teamName, selector.listOfMatches, (String) (paramsChooser.getSelectedItem()), (String) (indexChooser.getSelectedItem()), valueOfSlider);
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(null);
        tablePanel.setSize(600,44);
        tablePanel.setLocation(0,40);

        tableByParams.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableByParams.setEnabled(false);
        tableByParams.getTableHeader().setFont(font15);
        tableByParams.setFont(font15);
        tableByParams.setRowHeight(25);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableByParams.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableByParams.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableByParams.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableByParams.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tableByParams.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tableByParams.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(new JScrollPane(tableByParams), BorderLayout.CENTER);
        tablePanel.add(tableByParams.getTableHeader(), BorderLayout.NORTH);

        tableByParams.setSize(200, 180);
        tableByParams.setLocation(5, 5);

        tablePanel.add(tableByParams);
        paramsPanel.add(tablePanel);
        paramsPanel.revalidate();
    }

    public void setFilters(String league){
        String season = Settings.getCurrentSeasonInLeague(league);
        seasonChooser.setSelectedItem("Сезон " + season);
        leagueChooser.setSelectedItem(league);
    }
}