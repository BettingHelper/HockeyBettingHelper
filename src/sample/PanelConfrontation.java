package sample;

import org.jfree.ui.tabbedui.VerticalLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelConfrontation extends JPanel{
    //int WIDTH;
    //int DEFWIDTH = 1600;
    //double procWIDTH;
    //int HEIGHT;
    //int DEFHEIGHT = 1000;
    //int graphicWidth = 1250;
    //int graphicHeight = 410;
    //double procHEIGHT;

    JScrollPane scrollPane;
    JComboBox<String> seasonCB;
    JComboBox<String> leagueChooser;
    JComboBox<String> teamChooserHome;
    JComboBox<String> teamChooserAway;
    JComboBox<String> allOrHomeAwayComboBox;

    public PanelConfrontation(){
        this.setLayout(new BorderLayout());
        /*WIDTH = width;
        HEIGHT = height;
        procWIDTH = WIDTH / (double) DEFWIDTH;
        procHEIGHT = HEIGHT / (double) DEFHEIGHT;
        if (procWIDTH == 1)
            this.graphicWidth = 1300;*/
        String curSeason = Settings.getCurrentSeason();
        final String path = "database/";

        ////////////////////////////////////////////ПАНЕЛЬ
        JPanel panelChoosers = new JPanel(new GridLayout(1, 0, 5, 5));

        ArrayList<String> listOfSeasons = Settings.getListOfSeasons();
        String[] seasonList = new String[listOfSeasons.size()];
        for (int i = 0; i < seasonList.length; i++)
            seasonList[i] = "Сезон " + listOfSeasons.get(i);
        seasonCB = new JComboBox<>(seasonList);
        panelChoosers.add(seasonCB);

        JFileChooser fileChooser = new JFileChooser(path + curSeason + "/" + "leagues");
        String[] directoryList = fileChooser.getCurrentDirectory().list();
        ArrayList<String> leagueList = new ArrayList<>();
        leagueList.add("Выберите лигу");
        for (String aDirectoryList : directoryList) leagueList.add(aDirectoryList.replace(".txt", ""));
        String[] listOfLeagues = new String[leagueList.size()];
        for (int i = 0; i < listOfLeagues.length; i++)
            listOfLeagues[i] = leagueList.get(i);
        leagueChooser = new JComboBox<>(listOfLeagues);
//        leagueChooser.setPreferredSize(new Dimension((int) (150 * procWIDTH), 30));
        panelChoosers.add(leagueChooser);

        String fileNameHome = path + curSeason + "/" + "leagues/" + leagueChooser.getSelectedItem() + ".txt";
        String[] listHome = {"Хозяева"};
        if (!fileNameHome.contains("ыберите")) {
            listHome = Main.readTxtFile(fileNameHome);
        }
        teamChooserHome = new JComboBox<>(listHome);
//        teamChooserHome.setPreferredSize(new Dimension((int) (170 * procWIDTH), 30));
        teamChooserHome.setEnabled(false);
        panelChoosers.add(teamChooserHome);

        String fileNameAway = path + curSeason + "/" + "leagues/" + leagueChooser.getSelectedItem() + ".txt";
        String[] listAway = {"Гости"};
        if (!fileNameAway.contains("ыберите")) {
            listAway = Main.readTxtFile(fileNameAway);
        }
        teamChooserAway = new JComboBox<>(listAway);
//        teamChooserAway.setPreferredSize(new Dimension((int) (170 * procWIDTH), 30));
        teamChooserAway.setEnabled(false);
        panelChoosers.add(teamChooserAway);

        String[] allOrHomeAway = {"Все матчи", "На поле хозяев"};
        allOrHomeAwayComboBox = new JComboBox<>(allOrHomeAway);
        panelChoosers.add(allOrHomeAwayComboBox);

        final JButton buttonShow = new JButton("Отобразить!");
//        buttonShow.setPreferredSize(new Dimension((int) (200 * procWIDTH), 30));
        Font fontForButton = new Font("", 0, 18);
        buttonShow.setFont(fontForButton);
        panelChoosers.add(buttonShow);
        this.add(panelChoosers, BorderLayout.NORTH);

        final JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder(""));

        scrollPane = new JScrollPane();
        infoPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(infoPanel);

        seasonCB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seasonCB.setFocusable(false);

                String pathToLeaguesList = path + seasonCB.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/";
                JFileChooser fileChooser = new JFileChooser(pathToLeaguesList);
                String[] directoryList = new String[fileChooser.getCurrentDirectory().list().length+1];
                directoryList[0] = "Выберите лигу";
                for (int i=1; i<directoryList.length; i++)
                    directoryList[i] = fileChooser.getCurrentDirectory().list()[i-1].replace(".txt", "");
                DefaultComboBoxModel modelH = new DefaultComboBoxModel(directoryList);
                leagueChooser.setModel(modelH);

                String pathToTeamsList = path + seasonCB.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/" + leagueChooser.getSelectedItem() + ".txt";
                String[] listRight = {"Выберите команду"};
                if (!pathToTeamsList.contains("ыберите")) {
                    listRight = Main.readTxtFile(pathToTeamsList);
                }

                DefaultComboBoxModel modelH2;
                modelH = new DefaultComboBoxModel(listRight);
                modelH2 = new DefaultComboBoxModel(listRight);
                teamChooserHome.setModel(modelH);
                teamChooserAway.setModel(modelH2);

                leagueChooser.setFocusable(true);
            }
        });

        leagueChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = leagueChooser.getSelectedIndex();
                String pathToLeaguesList = path + seasonCB.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/";
                JFileChooser leftFileChooser = new JFileChooser(pathToLeaguesList);
                String[] leftDirectoryList = new String[leftFileChooser.getCurrentDirectory().list().length+1];
                leftDirectoryList[0] = "Выберите лигу";
                for (int i=1; i<leftDirectoryList.length; i++)
                    leftDirectoryList[i] = leftFileChooser.getCurrentDirectory().list()[i-1].replace(".txt", "");
                DefaultComboBoxModel modelH = new DefaultComboBoxModel(leftDirectoryList);
                DefaultComboBoxModel modelH2;
                leagueChooser.setModel(modelH);
                leagueChooser.setSelectedIndex(index);
                leagueChooser.setFocusable(false);

                teamChooserHome.setEnabled(true);
                teamChooserAway.setEnabled(true);
                String pathToTeamsList = path + seasonCB.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/" + leagueChooser.getSelectedItem() + ".txt";
                String[] listRight = {"Выберите команду"};
                if (!pathToTeamsList.contains("ыберите")) {
                    listRight = Main.readTxtFile(pathToTeamsList);
                }
                modelH = new DefaultComboBoxModel(listRight);
                modelH2 = new DefaultComboBoxModel(listRight);
                teamChooserHome.setModel(modelH);
                teamChooserAway.setModel(modelH2);

            }
        });

        teamChooserHome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teamChooserHome.setFocusable(false);
            }
        });

        teamChooserAway.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teamChooserAway.setFocusable(false);
            }
        });

        allOrHomeAwayComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allOrHomeAwayComboBox.setFocusable(false);
            }
        });

        buttonShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infoPanel.removeAll();
                JScrollPane scrollPane = refreshData((String) leagueChooser.getSelectedItem(),
                        (String) teamChooserHome.getSelectedItem(),
                        (String) teamChooserAway.getSelectedItem(),
                        (String) allOrHomeAwayComboBox.getSelectedItem())
                        ;
                infoPanel.add(scrollPane);
                infoPanel.revalidate();
                buttonShow.setFocusable(false);
            }
        });

    }

    public JScrollPane refreshData(String leagueName, final String homeTeamName, final String awayTeamName, final String allOrHomeAway){
        this.setCursor(Cursor.getPredefinedCursor (Cursor.WAIT_CURSOR));
        JScrollPane scrollPane;
        JPanel container = new JPanel(new VerticalLayout());
        if ((!leagueName.contains("Выберите"))&&(!homeTeamName.contains("Команда"))&&(!awayTeamName.contains("Команда"))){
            final Selector selector = new Selector();
            selector.getConfrontationList(leagueName, homeTeamName, awayTeamName, allOrHomeAway);

            if (selector.listOfMatches.size()>0){
                int matches = selector.listOfMatches.size();

                String teamStats = "Матчей: " + String.valueOf(matches);
                final JLabel label = new JLabel(teamStats);
                Font fontLabel = new Font("Arial", Font.BOLD, 15);
                label.setFont(fontLabel);
                label.setHorizontalAlignment(SwingConstants.LEFT);
                label.setBorder(new EmptyBorder(10, 10, 0, 0));
                container.add(label);

                String[] colHeads = {"№" , "Сезон", "Матч", "Счет", "Тотал ОВ", "Счет по периодам", "Бр. в ст.",
                        "Мин.штр", "2мин.уд.", "Вбрас.", "Бл. бр.", "Сил. пр."
                };
                Object[][] data = new Object[selector.listOfMatches.size()][colHeads.length];
                for (int i=0; i<selector.listOfMatches.size(); i++){
                    data[i][0] = String.valueOf(i+1);
                    data[i][1] = selector.listForConfrontation.get(i);
                    data[i][2] = selector.listOfMatches.get(i).homeTeam + " - " + selector.listOfMatches.get(i).awayTeam;
                    if (selector.listOfMatches.get(i).homeScore != selector.listOfMatches.get(i).awayScore)
                        data[i][3] = selector.listOfMatches.get(i).homeScore + " : " + selector.listOfMatches.get(i).awayScore;
                    else if (selector.listOfMatches.get(i).homeScoreOT + selector.listOfMatches.get(i).homeScoreBullits > selector.listOfMatches.get(i).awayScoreOT + selector.listOfMatches.get(i).awayScoreBullits)
                            if (selector.listOfMatches.get(i).homeScoreOT == 1)
                                data[i][3] = (selector.listOfMatches.get(i).homeScore+1) + " : " + selector.listOfMatches.get(i).awayScore + " ОТ";
                            else
                                data[i][3] = (selector.listOfMatches.get(i).homeScore+1) + " : " + selector.listOfMatches.get(i).awayScore + " Б";
                        else
                            if (selector.listOfMatches.get(i).awayScoreOT == 1)
                                data[i][3] = selector.listOfMatches.get(i).homeScore + " : " + (selector.listOfMatches.get(i).awayScore+1) + " ОТ";
                            else
                                data[i][3] = selector.listOfMatches.get(i).homeScore + " : " + (selector.listOfMatches.get(i).awayScore+1) + " Б";

                    data[i][4] = String.valueOf(selector.listOfMatches.get(i).homeScore + selector.listOfMatches.get(i).awayScore);
                    data[i][5] = selector.listOfMatches.get(i).homeScore1stPeriod + " : " + selector.listOfMatches.get(i).awayScore1stPeriod + "   |   "
                                    + selector.listOfMatches.get(i).homeScore2ndPeriod + " : " + selector.listOfMatches.get(i).awayScore2ndPeriod + "   |   "
                                    + selector.listOfMatches.get(i).homeScore3rdPeriod + " : " + selector.listOfMatches.get(i).awayScore3rdPeriod;
                    data[i][6] = selector.listOfMatches.get(i).homeShotsOnTarget + " : " + selector.listOfMatches.get(i).awayShotsOnTarget;
                    data[i][7] = selector.listOfMatches.get(i).homePenaltyMinutes + " : " + selector.listOfMatches.get(i).awayPenaltyMinutes;
                    data[i][8] = selector.listOfMatches.get(i).home2MinPenalties + " : " + selector.listOfMatches.get(i).away2MinPenalties;
                    data[i][9] = selector.listOfMatches.get(i).homeFaceoffsWon + " : " + selector.listOfMatches.get(i).awayFaceoffsWon;
                    data[i][10] = selector.listOfMatches.get(i).homeBlockedShots + " : " + selector.listOfMatches.get(i).awayBlockedShots;
                    data[i][11] = selector.listOfMatches.get(i).homeHits + " : " + selector.listOfMatches.get(i).awayHits;
                }

                JTable table = new JTable(data, colHeads);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.setEnabled(false);
                table.getTableHeader().setFont(fontLabel);
                table.setFont(fontLabel);
                table.setRowHeight(25);
                table.getColumnModel().getColumn(0).setPreferredWidth(30);
                table.getColumnModel().getColumn(1).setPreferredWidth(70);
                table.getColumnModel().getColumn(2).setPreferredWidth(180);
                table.getColumnModel().getColumn(3).setPreferredWidth(90);
                table.getColumnModel().getColumn(4).setPreferredWidth(90);
                table.getColumnModel().getColumn(5).setPreferredWidth(170);
                table.getColumnModel().getColumn(6).setPreferredWidth(90);
                table.getColumnModel().getColumn(7).setPreferredWidth(90);
                table.getColumnModel().getColumn(8).setPreferredWidth(90);
                table.getColumnModel().getColumn(9).setPreferredWidth(90);
                table.getColumnModel().getColumn(10).setPreferredWidth(90);
                table.getColumnModel().getColumn(11).setPreferredWidth(90);
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                for (int r=0; r<colHeads.length; r++)
                    table.getColumnModel().getColumn(r).setCellRenderer(centerRenderer);

                JPanel tablePanel = new JPanel();
                tablePanel.setLayout(new BorderLayout());
                tablePanel.add(table, BorderLayout.CENTER);
                tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);

                JButton[] arrOfButton = new JButton[selector.listOfMatches.size()];
                JPanel panelButtons = new JPanel(new VerticalLayout());
                for (int i=0; i<selector.listOfMatches.size(); i++){
                    arrOfButton[i] = new JButton("Окно матча");
                    arrOfButton[i].setPreferredSize(new Dimension(150, table.getRowHeight()));
                    panelButtons.add(arrOfButton[i]);

                    final int finalI = i;
                    arrOfButton[i].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            WindowMatchStats window = new WindowMatchStats(selector.listOfMatches.get(finalI));
                            window.setVisible(true);
                        }
                    });

                    tablePanel.add(panelButtons, BorderLayout.EAST);
                }

                container.add(tablePanel);

                String bets = "Варианты ставок на основное время матча и их проходимость в очных встречах команд:";
                final JLabel label2 = new JLabel(bets);
                label2.setFont(fontLabel);
                label2.setBorder(new EmptyBorder(10, 10, 0, 0));
                container.add(label2);

                String[] colHeads2 = {"Ставка" , "Победа " + homeTeamName, "X", "Победа " + awayTeamName, "П1", "П2", "Обе забьют", "Обе забьют > 1.5"};
                Object[][] data2 = new Object[1][colHeads2.length];
                int pHome = 0;
                int x = 0;
                int pAway = 0;
                int p1 = 0;
                int p2 = 0;
                int oz = 0;
                int oz15 = 0;
                int pHomeOTB = 0;
                int pAwayOTB = 0;
                int p1OTB = 0;
                int p2OTB = 0;
                for (int i=0; i<selector.listOfMatches.size(); i++){
                    if (selector.listOfMatches.get(i).homeScore == selector.listOfMatches.get(i).awayScore){
                        x++;
                        if (selector.listOfMatches.get(i).homeScoreOT ==1 || selector.listOfMatches.get(i).homeScoreBullits == 1){
                            p1OTB++;
                            if (selector.listOfMatches.get(i).homeTeam.equals(homeTeamName))
                                pHomeOTB++;
                            else
                                pAwayOTB++;
                        }
                        else{
                            p2OTB++;
                            if (selector.listOfMatches.get(i).awayTeam.equals(awayTeamName))
                                pAwayOTB++;
                            else
                                pHomeOTB++;
                        }

                    }
                    if (selector.listOfMatches.get(i).homeScore > selector.listOfMatches.get(i).awayScore){
                        p1++;
                        if (selector.listOfMatches.get(i).homeTeam.equals(homeTeamName))
                            pHome++;
                        else
                            pAway++;
                    }
                    if (selector.listOfMatches.get(i).homeScore < selector.listOfMatches.get(i).awayScore){
                        p2++;
                        if (selector.listOfMatches.get(i).homeTeam.equals(homeTeamName))
                            pAway++;
                        else
                            pHome++;
                    }
                    if (selector.listOfMatches.get(i).homeScore * selector.listOfMatches.get(i).awayScore > 0)
                        oz++;
                    if (selector.listOfMatches.get(i).homeScore > 1.5 && selector.listOfMatches.get(i).awayScore > 1.5)
                        oz15++;
                }
                data2[0][0] = "Заход и %";
                data2[0][1] = pHome + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) pHome/selector.listOfMatches.size()*100, 1) + "%";
                data2[0][2] = x + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) x/selector.listOfMatches.size()*100, 1) + "%";
                data2[0][3] = pAway + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) pAway/selector.listOfMatches.size()*100, 1) + "%";
                data2[0][4] = p1 + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) p1/selector.listOfMatches.size()*100, 1) + "%";
                data2[0][5] = p2 + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) p2/selector.listOfMatches.size()*100, 1) + "%";
                data2[0][6] = oz + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) oz/selector.listOfMatches.size()*100, 1) + "%";
                data2[0][7] = oz15 + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) oz15/selector.listOfMatches.size()*100, 1) + "%";

                JTable table2 = new JTable(data2, colHeads2);
                table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table2.setEnabled(false);
                table2.getTableHeader().setFont(fontLabel);
                table2.setFont(fontLabel);
                table2.setRowHeight(25);
                table2.getColumnModel().getColumn(0).setPreferredWidth(90);
                table2.getColumnModel().getColumn(1).setPreferredWidth(225);
                table2.getColumnModel().getColumn(2).setPreferredWidth(90);
                table2.getColumnModel().getColumn(3).setPreferredWidth(225);
                table2.getColumnModel().getColumn(4).setPreferredWidth(90);
                table2.getColumnModel().getColumn(5).setPreferredWidth(90);
                table2.getColumnModel().getColumn(6).setPreferredWidth(150);
                table2.getColumnModel().getColumn(7).setPreferredWidth(150);
                for (int r=0; r<colHeads2.length; r++)
                    table2.getColumnModel().getColumn(r).setCellRenderer(centerRenderer);
                JPanel tablePanel2 = new JPanel();
                tablePanel2.setLayout(new BorderLayout());
                tablePanel2.add(table2, BorderLayout.CENTER);
                tablePanel2.add(table2.getTableHeader(), BorderLayout.NORTH);

                container.add(tablePanel2);

                String params = "Варианты ставок на победу по итогам всего матча с учетом ОТ и буллитов:";
                final JLabel label3 = new JLabel(params);
                label3.setFont(fontLabel);
                label3.setBorder(new EmptyBorder(10, 10, 0, 0));
                container.add(label3);

                String[] colHeads3 = {"Ставка" , "Победа " + homeTeamName, "Победа " + awayTeamName, "П1", "П2"};
                Object[][] data3 = new Object[1][colHeads2.length];
                data3[0][0] = "Заход и %";
                data3[0][1] = (pHome + pHomeOTB) + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) (pHome + pHomeOTB)/selector.listOfMatches.size()*100, 1) + "%";
                data3[0][2] = (pAway + pAwayOTB) + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) (pAway + pAwayOTB)/selector.listOfMatches.size()*100, 1) + "%";
                data3[0][3] = (p1 + p1OTB) + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) (p1 + p1OTB)/selector.listOfMatches.size()*100, 1) + "%";
                data3[0][4] = (p2 + p2OTB) + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) (p2 + p2OTB)/selector.listOfMatches.size()*100, 1) + "%";

                JTable table3 = new JTable(data3, colHeads3);
                table3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table3.setEnabled(false);
                table3.getTableHeader().setFont(fontLabel);
                table3.setFont(fontLabel);
                table3.setRowHeight(25);
                table3.getColumnModel().getColumn(0).setPreferredWidth(90);
                table3.getColumnModel().getColumn(1).setPreferredWidth(260);
                table3.getColumnModel().getColumn(2).setPreferredWidth(260);
                table3.getColumnModel().getColumn(3).setPreferredWidth(250);
                table3.getColumnModel().getColumn(4).setPreferredWidth(250);
                for (int r=0; r<colHeads3.length; r++)
                    table3.getColumnModel().getColumn(r).setCellRenderer(centerRenderer);
                JPanel tablePanel3 = new JPanel();
                tablePanel3.setLayout(new BorderLayout());
                tablePanel3.add(table3, BorderLayout.CENTER);
                tablePanel3.add(table3.getTableHeader(), BorderLayout.NORTH);

                container.add(tablePanel3);


                String totalsText = "Варианты ставок на тоталы основного времени матча:";
                final JLabel label4 = new JLabel(totalsText);
                label4.setFont(fontLabel);
                label4.setBorder(new EmptyBorder(10, 10, 0, 0));
                container.add(label4);

                String[] colHeads4 = {"Тотал" , "3", "3.5", "4", "4.5", "5", "5.5", "6", "6.5"};
                Object[][] data4 = new Object[3][colHeads4.length];
                int[][] dataTotal = new int[3][colHeads4.length];
                data4[0][0] = "Тотал больше";
                data4[1][0] = "Тотал меньше";
                data4[2][0] = "Тотал ровно";

                for (int i=0; i<selector.listOfMatches.size(); i++){
                    int total = selector.listOfMatches.get(i).homeScore + selector.listOfMatches.get(i).awayScore;
                    int index = 1;
                    while (index < colHeads4.length){
                        if (total > Double.parseDouble(colHeads4[index]))
                            dataTotal[0][index] += 1;
                        if (total < Double.parseDouble(colHeads4[index]))
                            dataTotal[1][index] += 1;
                        if (total == Double.parseDouble(colHeads4[index]))
                            dataTotal[2][index] += 1;
                        index++;
                    }
                }

                for (int i=1; i<dataTotal[0].length; i++){
                    data4[0][i] = dataTotal[0][i] + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) dataTotal[0][i]/selector.listOfMatches.size()*100, 1) + "%";
                    data4[1][i] = dataTotal[1][i] + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) dataTotal[1][i]/selector.listOfMatches.size()*100, 1) + "%";
                    data4[2][i] = dataTotal[2][i] + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) dataTotal[2][i]/selector.listOfMatches.size()*100, 1) + "%";

                }

                JTable table4 = new JTable(data4, colHeads4);
                table4.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table4.setEnabled(false);
                table4.getTableHeader().setFont(fontLabel);
                table4.setFont(fontLabel);
                table4.setRowHeight(25);
                table4.getColumnModel().getColumn(0).setPreferredWidth(150);
                table4.getColumnModel().getColumn(1).setPreferredWidth(120);
                table4.getColumnModel().getColumn(2).setPreferredWidth(120);
                table4.getColumnModel().getColumn(3).setPreferredWidth(120);
                table4.getColumnModel().getColumn(4).setPreferredWidth(120);
                table4.getColumnModel().getColumn(5).setPreferredWidth(120);
                table4.getColumnModel().getColumn(6).setPreferredWidth(120);
                table4.getColumnModel().getColumn(7).setPreferredWidth(120);
                table4.getColumnModel().getColumn(8).setPreferredWidth(120);
                for (int r=0; r<colHeads4.length; r++)
                    table4.getColumnModel().getColumn(r).setCellRenderer(centerRenderer);
                JPanel tablePanel4 = new JPanel();
                tablePanel4.setLayout(new BorderLayout());
                tablePanel4.add(table4, BorderLayout.CENTER);
                tablePanel4.add(table4.getTableHeader(), BorderLayout.NORTH);

                container.add(tablePanel4);

                String period1Text = "Варианты ставок на периоды:";
                final JLabel label1period = new JLabel(period1Text);
                label1period.setFont(fontLabel);
                label1period.setBorder(new EmptyBorder(10, 10, 0, 0));
                container.add(label1period);

                String[] colHeads5 = {"", "Ставка" , "Победа " + homeTeamName, "X", "Победа " + awayTeamName, "П1", "П2", "ТБ(1.5)", "Обе забьют"};
                Object[][] data5 = new Object[3][colHeads5.length];
                pHome = 0;
                x = 0;
                pAway = 0;
                p1 = 0;
                p2 = 0;
                oz = 0;
                int tb15 = 0;
                for (int i=0; i<selector.listOfMatches.size(); i++){
                    if (selector.listOfMatches.get(i).homeScore1stPeriod == selector.listOfMatches.get(i).awayScore1stPeriod)
                        x++;
                    if (selector.listOfMatches.get(i).homeScore1stPeriod > selector.listOfMatches.get(i).awayScore1stPeriod){
                        p1++;
                        if (selector.listOfMatches.get(i).homeTeam.equals(homeTeamName))
                            pHome++;
                        else
                            pAway++;
                    }
                    if (selector.listOfMatches.get(i).homeScore1stPeriod < selector.listOfMatches.get(i).awayScore1stPeriod){
                        p2++;
                        if (selector.listOfMatches.get(i).homeTeam.equals(homeTeamName))
                            pAway++;
                        else
                            pHome++;
                    }
                    if (selector.listOfMatches.get(i).homeScore1stPeriod * selector.listOfMatches.get(i).awayScore1stPeriod > 0)
                        oz++;
                    if (selector.listOfMatches.get(i).homeScore1stPeriod + selector.listOfMatches.get(i).awayScore1stPeriod > 1.5)
                        tb15++;
                }
                data5[0][0] = "1 период";
                data5[0][1] = "Заход и %";
                data5[0][2] = pHome + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) pHome/selector.listOfMatches.size()*100, 1) + "%";
                data5[0][3] = x + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) x/selector.listOfMatches.size()*100, 1) + "%";
                data5[0][4] = pAway + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) pAway/selector.listOfMatches.size()*100, 1) + "%";
                data5[0][5] = p1 + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) p1/selector.listOfMatches.size()*100, 1) + "%";
                data5[0][6] = p2 + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) p2/selector.listOfMatches.size()*100, 1) + "%";
                data5[0][7] = tb15 + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) tb15/selector.listOfMatches.size()*100, 1) + "%";
                data5[0][8] = oz + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) oz/selector.listOfMatches.size()*100, 1) + "%";

                pHome = 0;
                x = 0;
                pAway = 0;
                p1 = 0;
                p2 = 0;
                oz = 0;
                tb15 = 0;
                for (int i=0; i<selector.listOfMatches.size(); i++){
                    if (selector.listOfMatches.get(i).homeScore2ndPeriod == selector.listOfMatches.get(i).awayScore2ndPeriod)
                        x++;
                    if (selector.listOfMatches.get(i).homeScore2ndPeriod > selector.listOfMatches.get(i).awayScore2ndPeriod){
                        p1++;
                        if (selector.listOfMatches.get(i).homeTeam.equals(homeTeamName))
                            pHome++;
                        else
                            pAway++;
                    }
                    if (selector.listOfMatches.get(i).homeScore2ndPeriod < selector.listOfMatches.get(i).awayScore2ndPeriod){
                        p2++;
                        if (selector.listOfMatches.get(i).homeTeam.equals(homeTeamName))
                            pAway++;
                        else
                            pHome++;
                    }
                    if (selector.listOfMatches.get(i).homeScore2ndPeriod * selector.listOfMatches.get(i).awayScore2ndPeriod > 0)
                        oz++;
                    if (selector.listOfMatches.get(i).homeScore2ndPeriod + selector.listOfMatches.get(i).awayScore2ndPeriod > 1.5)
                        tb15++;
                }
                data5[1][0] = "2 период";
                data5[1][1] = "Заход и %";
                data5[1][2] = pHome + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) pHome/selector.listOfMatches.size()*100, 1) + "%";
                data5[1][3] = x + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) x/selector.listOfMatches.size()*100, 1) + "%";
                data5[1][4] = pAway + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) pAway/selector.listOfMatches.size()*100, 1) + "%";
                data5[1][5] = p1 + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) p1/selector.listOfMatches.size()*100, 1) + "%";
                data5[1][6] = p2 + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) p2/selector.listOfMatches.size()*100, 1) + "%";
                data5[1][7] = tb15 + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) tb15/selector.listOfMatches.size()*100, 1) + "%";
                data5[1][8] = oz + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) oz/selector.listOfMatches.size()*100, 1) + "%";

                pHome = 0;
                x = 0;
                pAway = 0;
                p1 = 0;
                p2 = 0;
                oz = 0;
                tb15 = 0;
                for (int i=0; i<selector.listOfMatches.size(); i++){
                    if (selector.listOfMatches.get(i).homeScore3rdPeriod == selector.listOfMatches.get(i).awayScore3rdPeriod)
                        x++;
                    if (selector.listOfMatches.get(i).homeScore3rdPeriod > selector.listOfMatches.get(i).awayScore3rdPeriod){
                        p1++;
                        if (selector.listOfMatches.get(i).homeTeam.equals(homeTeamName))
                            pHome++;
                        else
                            pAway++;
                    }
                    if (selector.listOfMatches.get(i).homeScore3rdPeriod < selector.listOfMatches.get(i).awayScore3rdPeriod){
                        p2++;
                        if (selector.listOfMatches.get(i).homeTeam.equals(homeTeamName))
                            pAway++;
                        else
                            pHome++;
                    }
                    if (selector.listOfMatches.get(i).homeScore3rdPeriod * selector.listOfMatches.get(i).awayScore3rdPeriod > 0)
                        oz++;
                    if (selector.listOfMatches.get(i).homeScore3rdPeriod + selector.listOfMatches.get(i).awayScore3rdPeriod > 1.5)
                        tb15++;
                }
                data5[2][0] = "3 период";
                data5[2][1] = "Заход и %";
                data5[2][2] = pHome + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) pHome/selector.listOfMatches.size()*100, 1) + "%";
                data5[2][3] = x + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) x/selector.listOfMatches.size()*100, 1) + "%";
                data5[2][4] = pAway + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) pAway/selector.listOfMatches.size()*100, 1) + "%";
                data5[2][5] = p1 + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) p1/selector.listOfMatches.size()*100, 1) + "%";
                data5[2][6] = p2 + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) p2/selector.listOfMatches.size()*100, 1) + "%";
                data5[2][7] = tb15 + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) tb15/selector.listOfMatches.size()*100, 1) + "%";
                data5[2][8] = oz + "/" + selector.listOfMatches.size() + "  " + MyMath.round((double) oz/selector.listOfMatches.size()*100, 1) + "%";

                JTable table5 = new JTable(data5, colHeads5);
                table5.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table5.setEnabled(false);
                table5.getTableHeader().setFont(fontLabel);
                table5.setFont(fontLabel);
                table5.setRowHeight(25);
                table5.getColumnModel().getColumn(0).setPreferredWidth(90);
                table5.getColumnModel().getColumn(1).setPreferredWidth(100);
                table5.getColumnModel().getColumn(2).setPreferredWidth(150);
                table5.getColumnModel().getColumn(3).setPreferredWidth(120);
                table5.getColumnModel().getColumn(4).setPreferredWidth(150);
                table5.getColumnModel().getColumn(5).setPreferredWidth(100);
                table5.getColumnModel().getColumn(6).setPreferredWidth(100);
                table5.getColumnModel().getColumn(7).setPreferredWidth(150);
                table5.getColumnModel().getColumn(8).setPreferredWidth(150);
                for (int r=0; r<colHeads5.length; r++)
                    table5.getColumnModel().getColumn(r).setCellRenderer(centerRenderer);
                JPanel tablePanel5 = new JPanel();
                tablePanel5.setLayout(new BorderLayout());
                tablePanel5.add(table5, BorderLayout.CENTER);
                tablePanel5.add(table5.getTableHeader(), BorderLayout.NORTH);

                container.add(tablePanel5);

            } else {
                container.setLayout(null);
                container.setPreferredSize(new Dimension((int) (0.8 * WIDTH), 330));

                String labelText = "В базе нет данных о личных встречах данных команд.";
                final JLabel label = new JLabel(labelText);
                label.setLocation(10, 0);
                label.setSize(new Dimension((int) (0.995 * WIDTH) - 30, 25));
                Font fontLabel = new Font("Arial", Font.BOLD, 15);
                label.setFont(fontLabel);
                container.add(label);

            }
        } else {
            container.setLayout(null);
            container.setPreferredSize(new Dimension((int) (0.8 * WIDTH), 330));

            final JLabel label = new JLabel("Задайте все условия поиска. Лига и/или команда не заданы.");
            label.setLocation(10, 0);
            label.setSize(new Dimension((int) (0.995 * WIDTH) - 30, 25));
            Font fontLabel = new Font("Arial", Font.BOLD, 15);
            label.setFont(fontLabel);
            container.add(label);
        }
        scrollPane = new JScrollPane(container);
        scrollPane.setVerticalScrollBar( new JScrollBar() {
            public int getUnitIncrement( int direction ) {
                return 50;
            }
        } );
        this.setCursor(Cursor.getDefaultCursor());
        return scrollPane;
    }

    public void setFilters(String league, String homeTeam, String awayTeam){
        String season = Settings.getCurrentSeasonInLeague(league);
        seasonCB.setSelectedItem("Сезон " + season);
        leagueChooser.setSelectedItem(league);
        teamChooserHome.setSelectedItem(homeTeam);
        teamChooserAway.setSelectedItem(awayTeam);
    }

}