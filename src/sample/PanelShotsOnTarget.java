package sample;

import org.jfree.ui.tabbedui.VerticalLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelShotsOnTarget extends JPanel{
    //int WIDTH;
    //int DEFWIDTH = 1600;
    //double procWIDTH;
    //int HEIGHT;
    //int DEFHEIGHT = 1000;
    //int graphicWidth = 1250;
    //int graphicHeight = 410;
    int graphicHeight = 300;
    double procHEIGHT;
    String[] listOfTeams;
    JPanel tablePanelMatching;
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

    JComboBox<String> seasonCB;
    JComboBox<String> leagueChooser;

    public PanelShotsOnTarget(){
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
        JPanel panelChoosers = new JPanel(new GridLayout(1,0,5,5));

        ArrayList<String> listOfSeasons = Settings.getListOfSeasons();
        String[] seasonList = new String[listOfSeasons.size()];
        for (int i = 0; i < seasonList.length; i++)
            seasonList[i] = "Сезон " + listOfSeasons.get(i);
        seasonCB = new JComboBox<>(seasonList);
//        seasonCB.setPreferredSize(new Dimension((int) (120 * procWIDTH), 30));
        panelChoosers.add(seasonCB);

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
        listOfTeams = new String[]{"Команда"};
        if (!fileName.contains("Лига")) {
            listOfTeams = Main.readTxtFile(fileName);
        }
        final JComboBox<String> teamChooser = new JComboBox<>(listOfTeams);
//        teamChooser.setPreferredSize(new Dimension((int) (120 * procWIDTH), 30));
        teamChooser.setEnabled(false);
        panelChoosers.add(teamChooser);

        final String[] allOrHomeOrAway = {"Все матчи", "Дома", "На выезде"};
        final JComboBox<String> teamAllOrHomeOrAway = new JComboBox<>(allOrHomeOrAway);
//        teamAllOrHomeOrAway.setPreferredSize(new Dimension((int) (120 * procWIDTH), 30));
        panelChoosers.add(teamAllOrHomeOrAway);

        final String[] lastOrFullSeason = {"Весь сезон", "Последние 3", "Последние 4", "Последние 5", "Последние 6", "Последние 7", "Последние 8", "Последние 9", "Последние 10", "Последние 15", "Последние 20"};
        final JComboBox<String> teamLastOrFullSeason = new JComboBox<>(lastOrFullSeason);
//        teamLastOrFullSeason.setPreferredSize(new Dimension((int) (140 * procWIDTH), 30));
        panelChoosers.add(teamLastOrFullSeason);

        final JButton buttonShowInfo = new JButton("Отобразить!");
//        buttonShowInfo.setPreferredSize(new Dimension((int) (150 * procWIDTH), 30));
        Font fontForButton = new Font("", 0, 18);
        buttonShowInfo.setFont(fontForButton);
        panelChoosers.add(buttonShowInfo);

        this.add(panelChoosers, BorderLayout.NORTH);

        final JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder(""));

        scrollPane = new JScrollPane();
        infoPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(infoPanel);

        seasonCB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seasonCB.setFocusable(false);

                String team = String.valueOf(teamChooser.getSelectedItem());
                String league = String.valueOf(leagueChooser.getSelectedItem());

                String pathToLeaguesList = path + seasonCB.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/";
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

                String pathToTeamsList = path + seasonCB.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/" + leagueChooser.getSelectedItem() + ".txt";
                String[] list = {"Выберите команду"};
                if (!pathToTeamsList.contains("ыберите")) {
                    list = Main.readTxtFile(pathToTeamsList);
                }
                modelH = new DefaultComboBoxModel(list);
                teamChooser.setModel(modelH);
                teamChooser.setEnabled(false);

                pathToTeamsList = path + seasonCB.getSelectedItem().toString().replace("Сезон ", "") + "/" + league + "/Teams/";
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
                String pathToLeaguesList = path + seasonCB.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/";
                JFileChooser fileChooser = new JFileChooser(pathToLeaguesList);
                String[] directoryList = new String[fileChooser.getCurrentDirectory().list().length+1];
                directoryList[0] = "Лига";
                for (int i=1; i<directoryList.length; i++)
                    directoryList[i] = fileChooser.getCurrentDirectory().list()[i-1].replace(".txt", "");
                DefaultComboBoxModel modelH = new DefaultComboBoxModel(directoryList);
                leagueChooser.setModel(modelH);
                leagueChooser.setSelectedIndex(index);

                teamChooser.setEnabled(true);
                String pathToTeamsList = path + seasonCB.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/" + leagueChooser.getSelectedItem() + ".txt";
                listOfTeams = new String[]{"Команда"};
                if (!pathToTeamsList.contains("Лига")) {
                    listOfTeams = Main.readTxtFile(pathToTeamsList);
                }
                modelH = new DefaultComboBoxModel(listOfTeams);
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
                /*WindowCalculating window = new WindowCalculating("Подождите, идет сбор данных");
                window.jpb.setValue(0);
                window.setVisible(true);*/
                if (scrollPane != null) {
                    infoPanel.remove(scrollPane);
                }
                scrollPane = refreshData((String) leagueChooser.getSelectedItem(),
                        (String) teamChooser.getSelectedItem(),
                        (String) teamAllOrHomeOrAway.getSelectedItem(),
                        (String) seasonCB.getSelectedItem(),
                        (String) teamLastOrFullSeason.getSelectedItem()
                );
                infoPanel.add(scrollPane);
                infoPanel.revalidate();
                buttonShowInfo.setFocusable(false);

            }
        });
    }

    public JScrollPane refreshData(final String leagueName, final String teamName, final String allOrHomeOrAway, String season, String lastOrFullSeason){
        this.setCursor(Cursor.getPredefinedCursor (Cursor.WAIT_CURSOR));
        final JScrollPane scrollPane;
        int otstup = 5;
        season = season.replace("Сезон ", "");
        if ((!leagueName.contains("Лига"))&&(!teamName.contains("Команда"))){
            final Selector selector = new Selector();
            selector.getListOfMatches(leagueName, teamName, allOrHomeOrAway, season, lastOrFullSeason);
            selector.getPList(selector.listOfMatches, teamName, season);
            if (selector.listOfMatches.size()>0){
                //int numberOfStages = selector.listOfMatches.size() + 1;
                JPanel allInfoPanel = new JPanel(new BorderLayout());

                final JPanel container = new JPanel();
                container.setLayout(null);
                Font fontLabel = new Font("Arial", Font.BOLD, 15);
                int matches = selector.listOfMatches.size();

                String labelForTable = "";
                if (lastOrFullSeason.contains("Весь сезон")){
                    if (allOrHomeOrAway.contains("Все")){
                        labelForTable = "Бросковые показатели всех игр " + teamName + " в сезоне " + season + ":";
                    }
                    if (allOrHomeOrAway.contains("Дома")){
                        labelForTable = "Бросковые показатели всех домашних игр " + teamName + " в сезоне " + season + ":";
                    }
                    if (allOrHomeOrAway.contains("На выезде")){
                        labelForTable = "Бросковые показатели всех гостевых игр " + teamName + " в сезоне " + season + ":";
                    }
                } else {
                    if (allOrHomeOrAway.contains("Все")){
                        labelForTable = "Бросковые показатели последних " + matches + " игр " + teamName + " в сезоне " + season + ":";
                    }
                    if (allOrHomeOrAway.contains("Дома")){
                        labelForTable = "Бросковые показатели последних " + matches + " домашних игр " + teamName + " в сезоне " + season + ":";
                    }
                    if (allOrHomeOrAway.contains("На выезде")){
                        labelForTable = "Бросковые показатели последних " + matches + " гостевых игр " + teamName + " в сезоне " + season + ":";
                    }
                }

                final JLabel label = new JLabel(labelForTable);
                label.setLocation(5, otstup);
                label.setSize(new Dimension((int) (0.98 * WIDTH) - 30, 25));
                otstup += 25;
                label.setFont(fontLabel);
                container.add(label);

                String[] colHeads = {"Параметр" ,teamName, "Соперник"};
                String[] params = {"Бросков в створ всего", "Броски в створ (сред.)", "Бр.в ст. 1 пер.(сред.) и их доля в %",
                        "Бр.в ст. 2 пер.(сред.) и их доля в %", "Бр.в ст. 3 пер.(сред.) и их доля в %",
                        "Бросков в створ за матч, мин.", "Бросков в створ за матч, макс.", "Дисперсия выборки бросков в створ",
                        "Блокированные броски (сред.)", "Кол-во большинства (сред.)", "Время в атаке (сред.)"};

                double minShots = 1000;
                double maxShots = 0;
                double minShotsOp = 1000;
                double maxShotsOp = 0;
                double disp = 0;
                double dispOp = 0;
                double MOshots = Double.parseDouble(selector.pList.get(7).get(1));
                double MOshotsOp = Double.parseDouble(selector.pList.get(7).get(2));
                double shots1per = 0;
                double shots2per = 0;
                double shots3per = 0;
                double shotsOpp1per = 0;
                double shotsOpp2per = 0;
                double shotsOpp3per = 0;

                for (int i=0; i<selector.listOfMatches.size(); i++){

                    Match m = selector.listOfMatches.get(i);

                    if (m.homeTeam.equals(teamName)){
                        if (m.homeShotsOnTarget > maxShots)
                            maxShots = m.homeShotsOnTarget;
                        if (m.homeShotsOnTarget < minShots)
                            minShots = m.homeShotsOnTarget;
                        if (m.awayShotsOnTarget > maxShotsOp)
                            maxShotsOp = m.awayShotsOnTarget;
                        if (m.awayShotsOnTarget < minShotsOp)
                            minShotsOp = m.awayShotsOnTarget;
                        disp += Math.pow( (m.homeShotsOnTarget - MOshots) , 2);
                        dispOp += Math.pow( (m.awayShotsOnTarget - MOshotsOp) , 2);
                        shots1per += m.homeShotsOnTarget1stPeriod;
                        shots2per += m.homeShotsOnTarget2ndPeriod;
                        shots3per += m.homeShotsOnTarget3rdPeriod;
                        shotsOpp1per += m.awayShotsOnTarget1stPeriod;
                        shotsOpp2per += m.awayShotsOnTarget2ndPeriod;
                        shotsOpp3per += m.awayShotsOnTarget3rdPeriod;
                    } else {
                        if (m.awayShotsOnTarget > maxShots)
                            maxShots = m.awayShotsOnTarget;
                        if (m.awayShotsOnTarget < minShots)
                            minShots = m.awayShotsOnTarget;
                        if (m.homeShotsOnTarget > maxShotsOp)
                            maxShotsOp = m.homeShotsOnTarget;
                        if (m.homeShotsOnTarget < minShotsOp)
                            minShotsOp = m.homeShotsOnTarget;
                        disp += Math.pow( (m.awayShotsOnTarget - MOshots) , 2);
                        dispOp += Math.pow( (m.homeShotsOnTarget - MOshotsOp) , 2);
                        shots1per += m.awayShotsOnTarget1stPeriod;
                        shots2per += m.awayShotsOnTarget2ndPeriod;
                        shots3per += m.awayShotsOnTarget3rdPeriod;
                        shotsOpp1per += m.homeShotsOnTarget1stPeriod;
                        shotsOpp2per += m.homeShotsOnTarget2ndPeriod;
                        shotsOpp3per += m.homeShotsOnTarget3rdPeriod;
                    }
                    //window.setProgressBarValue((int) (100 * (i + 1) / (double) numberOfStages));
                }
                disp = disp / matches;
                dispOp = dispOp / matches;
                shots1per = MyMath.round(shots1per / matches, 2);
                shots2per = MyMath.round(shots2per / matches, 2);
                shots3per = MyMath.round(shots3per / matches, 2);
                shotsOpp1per = MyMath.round(shotsOpp1per / matches, 2);
                shotsOpp2per = MyMath.round(shotsOpp2per / matches, 2);
                shotsOpp3per = MyMath.round(shotsOpp3per / matches, 2);

                String timeAttack = String.valueOf(Settings.getTimeInMinutesAndSecondsFromSeconds(Double.parseDouble(selector.pList.get(20).get(1)) / matches));
                if (timeAttack.equals("0:00"))
                    timeAttack = "No info";
                String timeAttackOpp = String.valueOf(Settings.getTimeInMinutesAndSecondsFromSeconds(Double.parseDouble(selector.pList.get(20).get(2)) / matches));
                if (timeAttackOpp.equals("0:00"))
                    timeAttackOpp = "No info";

                Object[][] data = {
                        {" " + params[0], (int) (matches*Double.parseDouble(selector.pList.get(7).get(1))), (int) (matches*Double.parseDouble(selector.pList.get(7).get(2)))},
                        {" " + params[1], selector.pList.get(7).get(1), selector.pList.get(7).get(2)},
                        {" " + params[2], shots1per + " (" + MyMath.round( 100*shots1per / Double.parseDouble(selector.pList.get(7).get(1)) , 1) +  "%)", shotsOpp1per + " (" + MyMath.round( 100*shotsOpp1per / Double.parseDouble(selector.pList.get(7).get(2)) , 1) +  "%)"},
                        {" " + params[3], shots2per + " (" + MyMath.round( 100*shots2per / Double.parseDouble(selector.pList.get(7).get(1)) , 1) +  "%)", shotsOpp2per + " (" + MyMath.round( 100*shotsOpp2per / Double.parseDouble(selector.pList.get(7).get(2)) , 1) +  "%)"},
                        {" " + params[4], shots3per + " (" + MyMath.round( 100*shots3per / Double.parseDouble(selector.pList.get(7).get(1)) , 1) +  "%)", shotsOpp3per + " (" + MyMath.round( 100*shotsOpp3per / Double.parseDouble(selector.pList.get(7).get(2)) , 1) +  "%)"},
                        {" " + params[5], (int) minShots, (int) minShotsOp},
                        {" " + params[6], (int) maxShots, (int) maxShotsOp},
                        {" " + params[7], MyMath.round(disp, 3), MyMath.round(dispOp, 3)},
                        {" " + params[8], Double.parseDouble(selector.pList.get(12).get(1)), Double.parseDouble(selector.pList.get(12).get(2))},
                        {" " + params[9], MyMath.round(Double.parseDouble(selector.pList.get(8).get(1)) / matches, 3), MyMath.round(Double.parseDouble(selector.pList.get(8).get(2)) / matches, 3)},
                        {" " + params[10], timeAttack, timeAttackOpp}
                };

                JTable table = new JTable(data, colHeads);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.setEnabled(false);
                table.getTableHeader().setFont(fontLabel);
                table.setFont(fontLabel);
                table.setRowHeight(25);
                table.getColumnModel().getColumn(0).setPreferredWidth(297);
                table.getColumnModel().getColumn(1).setPreferredWidth(160);
                table.getColumnModel().getColumn(2).setPreferredWidth(160);
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
                table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
                JPanel tablePanel = new JPanel();
                tablePanel.setLayout(new BorderLayout());
                tablePanel.add(table);
                tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);

                tablePanel.setSize(620, (table.getRowCount() + 1) * 25 - 3);
                tablePanel.setLocation(5, otstup);
                otstup += (table.getRowCount() + 1) * 25;
                container.add(tablePanel);

                //window.setProgressBarValue(100);

                Settings settings = Settings.getSettingsFromFile();
                boolean flagTableSTOpponents = settings.showSoTOpponents;
                ArrayList<Selector> opponentsList = null;
                opponentsList = Selector.getOpponentsList(teamName, selector.listOfMatches, season, allOrHomeOrAway, lastOrFullSeason);
                if (flagTableSTOpponents){
                    String labelForTableOpponents = "";
                    if (lastOrFullSeason.contains("Весь сезон")){
                        if (allOrHomeOrAway.contains("Все")){
                            labelForTableOpponents = "Показатели по броскам в створ противников " + teamName + " в сезоне " + season + ":";
                        }
                        if (allOrHomeOrAway.contains("Дома")){
                            labelForTableOpponents = "Показатели по броскам в створ (на чужом поле) противников " + teamName + " в сезоне " + season + ":";
                        }
                        if (allOrHomeOrAway.contains("На выезде")){
                            labelForTableOpponents = "Показатели по броскам в створ (на своем поле) противников " + teamName + " в сезоне " + season + ":";
                        }
                    } else {
                        if (allOrHomeOrAway.contains("Все")){
                            labelForTableOpponents = "Показатели по броскам в створ последних " + matches + " игр противников " + teamName + " в сезоне " + season + ":";
                        }
                        if (allOrHomeOrAway.contains("Дома")){
                            labelForTableOpponents = "Показатели по броскам в створ последних " + matches + " гостевых игр противников " + teamName + " в сезоне " + season + ":";
                        }
                        if (allOrHomeOrAway.contains("На выезде")){
                            labelForTableOpponents = "Показатели по броскам в створ последних " + matches + " домашних игр противников " + teamName + " в сезоне " + season + ":";
                        }
                    }
                    final JLabel label2 = new JLabel(labelForTableOpponents);
                    label2.setLocation(5, otstup);
                    label2.setSize(new Dimension((int) (0.98 * WIDTH) - 30, 25));
                    otstup += 25;
                    label2.setFont(fontLabel);
                    container.add(label2);

                    JPanel tableOpponents = TableMaker.getTableSTOpponents(teamName, selector.listOfMatches, allOrHomeOrAway, opponentsList);
                    //tableOpponents.setSize(620, 275);
                    tableOpponents.setLocation(0, otstup);
                    tableOpponents.setBackground(new Color(238, 238, 238));
                    otstup += (opponentsList.size() + 1) * 25;
                    container.add(tableOpponents);
                }


                //Вставка просмотрщика параметров
                final JPanel paramsPanel = new JPanel(new VerticalLayout());
                paramsPanel.setBorder(BorderFactory.createTitledBorder(""));
                JPanel selectorsPanel = new JPanel(new GridLayout(1, 0, 5, 5));

                String[] paramsList = new String[]{"Броски в створ (осн.время)", "Броски в створ 1 пер.", "Броски в створ 2 пер.", "Броски в створ 3 пер."};
                final JComboBox<String> paramsChooser = new JComboBox<>(paramsList);
                paramsChooser.setFont(new Font("", Font.BOLD, 15));
                selectorsPanel.add(paramsChooser);

                String[] indexList = {"Выберите тип ставки"};
                final JComboBox<String> indexChooser = new JComboBox<>(indexList);
                indexChooser.setFont(new Font("", Font.BOLD, 15));
                selectorsPanel.add(indexChooser);
                paramsPanel.add(selectorsPanel);

                JPanel panelSlider = new JPanel(new BorderLayout());

                leftValue = new JLabel("");
                leftValue.setFont(new Font("", Font.BOLD, 15));
                leftValue.setPreferredSize(new Dimension(40, 40));
                leftValue.setHorizontalAlignment(SwingConstants.CENTER);
                panelSlider.add(leftValue, BorderLayout.WEST);
                rightValue = new JLabel("");
                rightValue.setFont(new Font("", Font.BOLD, 15));
                rightValue.setPreferredSize(new Dimension(40, 40));
                rightValue.setHorizontalAlignment(SwingConstants.CENTER);
                panelSlider.add(rightValue, BorderLayout.EAST);
                bottomValue = new JLabel("");
                bottomValue.setFont(new Font("", Font.BOLD, 15));
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

                paramsPanel.setSize(600, 170);
                paramsPanel.setLocation(640, 10);
                container.add(paramsPanel);
                container.setPreferredSize(new Dimension((int) (0.9 * WIDTH), otstup)); // 275 + (selector.listOfMatches.size()+1)*25)

                JLabel labelHeaderMatching = new JLabel("Таблица созданных и полученных бросков в створ (сред.)");
                labelHeaderMatching.setLocation(640, 190);
                labelHeaderMatching.setSize(500, 30);
                container.add(labelHeaderMatching);

                final String[] matchingHeaders = {"Лед", "Весь сезон", "Посл. 7", "Посл. 5", "Посл. 3"};
                final JPanel matchingShots = new JPanel(new BorderLayout());
                matchingShots.setBorder(BorderFactory.createTitledBorder(""));
                matchingShots.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.gray));
                final JPanel teamsMatching = new JPanel(new VerticalLayout());
                JLabel emptyLabel = new JLabel("Команда");
                emptyLabel.setPreferredSize(new Dimension(100, 23));
                emptyLabel.setBorder(BorderFactory.createTitledBorder(""));
                teamsMatching.add(emptyLabel);
                JLabel teamToMatch = new JLabel(teamName);
                teamToMatch.setBorder(BorderFactory.createTitledBorder(""));
                teamToMatch.setPreferredSize(new Dimension(100, 26));
                teamsMatching.add(teamToMatch);
                final JComboBox<String> teamToMatchCB = new JComboBox<>(listOfTeams);
                teamToMatchCB.setSize(100, 30);
                teamsMatching.add(teamToMatchCB);
                matchingShots.add(teamsMatching, BorderLayout.WEST);
                matchingShots.setSize(600, 75);
                matchingShots.setLocation(640, 220);

                Object[][] dataMatching = {
                        {"", "", "", "", "", ""},
                        {"", "", "", "", "", ""}
                };

                JTable tableMatching = new JTable(dataMatching, matchingHeaders);
                tableMatching.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                tableMatching.setEnabled(false);
                Font fontMatching = new Font("", 0 , 15);
                tableMatching.getTableHeader().setFont(fontMatching);
                tableMatching.setFont(fontMatching);
                tableMatching.setRowHeight(25);
                tableMatching.getColumnModel().getColumn(0).setPreferredWidth(95);
                tableMatching.getColumnModel().getColumn(1).setPreferredWidth(95);
                tableMatching.getColumnModel().getColumn(2).setPreferredWidth(95);
                tableMatching.getColumnModel().getColumn(3).setPreferredWidth(95);
                tableMatching.getColumnModel().getColumn(4).setPreferredWidth(95);
                centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                tableMatching.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
                tableMatching.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
                tableMatching.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
                tableMatching.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
                tableMatching.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
                tablePanelMatching = new JPanel();
                tablePanelMatching.setLayout(new BorderLayout());
                tablePanelMatching.add(tableMatching);
                tablePanelMatching.add(tableMatching.getTableHeader(), BorderLayout.NORTH);

                container.add(matchingShots);



                allInfoPanel.add(container, BorderLayout.NORTH);
                //////////////////////////////Конец вставки просмотрщика по параметрам

                ////////////////////////ГРАФИКИ И ТАБЛИЦЫ ВСТАВЛЯЮ ТУТ
                JPanel graphAndTables = new JPanel(new BorderLayout());

//                GraphicForOneTeam graphic = new GraphicForOneTeam();
                Graphic graphic = new Graphic(1, teamName);
                JPanel panelGraphics = graphic.getGraphicsForShotsOnTarget(teamName, allOrHomeOrAway, lastOrFullSeason, selector.listOfMatches, opponentsList, selector);
                graphAndTables.add(panelGraphics, BorderLayout.CENTER);
                allInfoPanel.add(graphAndTables, BorderLayout.WEST);



                ////////////////////////ГРАФИКИ И ТАБЛИЦЫ БОЛЬШЕ НЕ ВСТАВЛЯЮ
                scrollPane = new JScrollPane(allInfoPanel);
                scrollPane.setVerticalScrollBar( new JScrollBar() {
                    public int getUnitIncrement( int direction ) {
                        return 50;
                    }
                } );
                //window.setVisible(false);

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
//                      index2 = indexChooser.getSelectedIndex();
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
                        valueForSlider = minSliderValue + stepSlider * slider.getValue();
                        bottomValue.setText("Выбрано значение: " + String.valueOf(valueForSlider));
                        index3 = slider.getValue();
                        getParamsPanel(paramsPanel, teamName, selector, paramsChooser, indexChooser, valueForSlider);
                    }
                });

                final String finalSeason = season;
                teamToMatchCB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        scrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        scrollPane.revalidate();
                        if (tablePanelMatching != null){
                            matchingShots.remove(tablePanelMatching);
                        }
                        Selector selHT;
                        String htAll;
                        String ht3;
                        String ht5;
                        String ht7;
                        if (!allOrHomeOrAway.equals("Весь сезон")){
                            selHT = new Selector();
                            selHT.getListOfMatches(leagueName, teamName, allOrHomeOrAway, finalSeason, "Весь сезон");
                            selHT.getPList(selHT.listOfMatches, teamName, finalSeason);
                        } else {
                            selHT = selector;
                        }

                        htAll = selHT.pList.get(7).get(1) + " - " + selHT.pList.get(7).get(2);
                        while (selHT.listOfMatches.size() > 7)
                            selHT.listOfMatches.remove(0);
                        selHT.getPList(selHT.listOfMatches, teamName, finalSeason);
                        ht7 = selHT.pList.get(7).get(1) + " - " + selHT.pList.get(7).get(2);
                        while (selHT.listOfMatches.size() > 5)
                            selHT.listOfMatches.remove(0);
                        selHT.getPList(selHT.listOfMatches, teamName, finalSeason);
                        ht5 = selHT.pList.get(7).get(1) + " - " + selHT.pList.get(7).get(2);
                        while (selHT.listOfMatches.size() > 3)
                            selHT.listOfMatches.remove(0);
                        selHT.getPList(selHT.listOfMatches, teamName, finalSeason);
                        ht3 = selHT.pList.get(7).get(1) + " - " + selHT.pList.get(7).get(2);

                        Selector selAT = new Selector();
                        String atAll;
                        String at3;
                        String at5;
                        String at7;
                        String awayAllOrHomeOrAway = "Все матчи";
                        if (allOrHomeOrAway.equals("Дома"))
                            awayAllOrHomeOrAway = "На выезде";
                        if (allOrHomeOrAway.equals("На выезде"))
                            awayAllOrHomeOrAway = "Дома";
                        selAT.getListOfMatches(leagueName, teamToMatchCB.getSelectedItem().toString(), awayAllOrHomeOrAway, finalSeason, "Весь сезон");
                        selAT.getPList(selAT.listOfMatches, teamToMatchCB.getSelectedItem().toString(), finalSeason);
                        atAll = selAT.pList.get(7).get(1) + " - " + selAT.pList.get(7).get(2);
                        while (selAT.listOfMatches.size() > 7)
                            selAT.listOfMatches.remove(0);
                        selAT.getPList(selAT.listOfMatches, teamToMatchCB.getSelectedItem().toString(), finalSeason);
                        at7 = selAT.pList.get(7).get(1) + " - " + selAT.pList.get(7).get(2);
                        while (selAT.listOfMatches.size() > 5)
                            selAT.listOfMatches.remove(0);
                        selAT.getPList(selAT.listOfMatches, teamToMatchCB.getSelectedItem().toString(), finalSeason);
                        at5 = selAT.pList.get(7).get(1) + " - " + selAT.pList.get(7).get(2);
                        while (selAT.listOfMatches.size() > 3)
                            selAT.listOfMatches.remove(0);
                        selAT.getPList(selAT.listOfMatches, teamToMatchCB.getSelectedItem().toString(), finalSeason);
                        at3 = selAT.pList.get(7).get(1) + " - " + selAT.pList.get(7).get(2);


                        Object[][] dataMatching = {
                                {allOrHomeOrAway, htAll, ht7, ht5, ht3},
                                {awayAllOrHomeOrAway, atAll, at7, at5, at3}
                        };

                        JTable tableMatching = new JTable(dataMatching, matchingHeaders);
                        tableMatching.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                        tableMatching.setEnabled(false);
                        Font fontMatching = new Font("", 0 , 15);
                        tableMatching.getTableHeader().setFont(fontMatching);
                        tableMatching.setFont(fontMatching);
                        tableMatching.setRowHeight(25);
                        tableMatching.getColumnModel().getColumn(0).setPreferredWidth(95);
                        tableMatching.getColumnModel().getColumn(1).setPreferredWidth(95);
                        tableMatching.getColumnModel().getColumn(2).setPreferredWidth(95);
                        tableMatching.getColumnModel().getColumn(3).setPreferredWidth(95);
                        tableMatching.getColumnModel().getColumn(4).setPreferredWidth(95);
                        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                        tableMatching.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
                        tableMatching.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
                        tableMatching.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
                        tableMatching.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
                        tableMatching.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);


                        tablePanelMatching = new JPanel();
                        tablePanelMatching.setLayout(new BorderLayout());
                        tablePanelMatching.add(tableMatching);
                        tablePanelMatching.add(tableMatching.getTableHeader(), BorderLayout.NORTH);
                        matchingShots.add(tablePanelMatching);
                        matchingShots.revalidate();
                        container.revalidate();
                        scrollPane.setCursor(Cursor.getDefaultCursor());
                    }
                });

            } else {
                JPanel container = new JPanel(/*new GridLayout(3,1,0,0)*/);
                container.setLayout(null);
                container.setPreferredSize(new Dimension((int) (0.48 * WIDTH), 730));

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
                label.setSize(new Dimension((int) (0.995 * WIDTH) - 30, 25));
                Font fontLabel = new Font("Arial", Font.BOLD, 15);
                label.setFont(fontLabel);
                container.add(label);

                scrollPane = new JScrollPane(container);
                scrollPane.setPreferredSize(new Dimension((int) (0.995 * WIDTH), (int) (0.87 * HEIGHT)));
                scrollPane.setLocation(5, 50);
                scrollPane.setVerticalScrollBar( new JScrollBar() {
                    public int getUnitIncrement( int direction ) {
                        return 50;
                    }
                } );
            }
        } else {
            JPanel container = new JPanel(/*new GridLayout(3,1,0,0)*/);
            container.setLayout(null);
            container.setPreferredSize(new Dimension((int) (0.48 * WIDTH), 730));

            final JLabel label = new JLabel("Задайте все условия поиска. Лига и/или команда не заданы.");
            label.setLocation(10, 0);
            label.setSize(new Dimension((int) (0.995 * WIDTH) - 30, 25));
            Font fontLabel = new Font("Arial", Font.BOLD, 15);
            label.setFont(fontLabel);
            container.add(label);

            scrollPane = new JScrollPane(container);
            scrollPane.setPreferredSize(new Dimension((int) (0.995 * WIDTH), (int) (0.87 * HEIGHT)));
            scrollPane.setLocation(5, 50);
            scrollPane.setVerticalScrollBar(new JScrollBar() {
                public int getUnitIncrement(int direction) {
                    return 50;
                }
            });
        }
        this.setCursor(Cursor.getDefaultCursor());
        return scrollPane;
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
        Font fontLabel = new Font("Arial", Font.BOLD, 15);
        tableByParams.getTableHeader().setFont(fontLabel);
        tableByParams.setFont(fontLabel);
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
        seasonCB.setSelectedItem("Сезон " + season);
        leagueChooser.setSelectedItem(league);
    }

}