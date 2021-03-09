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

public class PanelPenalties extends JPanel{
    int graphicHeight = 410;
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
    JButton buttonShowInfo;

    public PanelPenalties(){
        this.setLayout(new BorderLayout());
        String curSeason = Settings.getCurrentSeason();
        final String path = "database/";

        ////////////////////////////////////////////ПАНЕЛЬ
        JPanel panelChoosers = new JPanel(new GridLayout(1,0,5,5));

        ArrayList<String> listOfSeasons = Settings.getListOfSeasons();
        String[] seasonList = new String[listOfSeasons.size()];
        for (int i = 0; i < seasonList.length; i++)
            seasonList[i] = "Сезон " + listOfSeasons.get(i);
        seasonCB = new JComboBox<>(seasonList);
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
        panelChoosers.add(leagueChooser);

        String fileName = path + curSeason + "/leagues/" + leagueChooser.getSelectedItem() + ".txt";
        String[] list = {"Команда"};
        if (!fileName.contains("Лига")) {
            list = Main.readTxtFile(fileName);
        }
        final JComboBox<String> teamChooser = new JComboBox<>(list);
        teamChooser.setEnabled(false);
        panelChoosers.add(teamChooser);

        final String[] allOrHomeOrAway = {"Все матчи", "Дома", "На выезде"};
        final JComboBox<String> teamAllOrHomeOrAway = new JComboBox<>(allOrHomeOrAway);
        panelChoosers.add(teamAllOrHomeOrAway);

        final String[] lastOrFullSeason = {"Весь сезон", "Последние 3", "Последние 4", "Последние 5", "Последние 6", "Последние 7", "Последние 8", "Последние 9", "Последние 10", "Последние 15", "Последние 20"};
        final JComboBox<String> teamLastOrFullSeason = new JComboBox<>(lastOrFullSeason);
        panelChoosers.add(teamLastOrFullSeason);

        buttonShowInfo = new JButton("Отобразить!");
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

    public JScrollPane refreshData(String leagueName, final String teamName, String allOrHomeOrAway, String season, String lastOrFullSeason){
        this.setCursor(Cursor.getPredefinedCursor (Cursor.WAIT_CURSOR));
        JScrollPane scrollPane;
        season = season.replace("Сезон ", "");
        int otstup = 5;
        if ((!leagueName.contains("Лига"))&&(!teamName.contains("Команда"))){
            final Selector selector = new Selector();
            selector.getListOfMatches(leagueName, teamName, allOrHomeOrAway, season, lastOrFullSeason);
            selector.getPList(selector.listOfMatches, teamName, season);

            if (selector.listOfMatches.size()>0){
                JPanel allInfoPanel = new JPanel(new BorderLayout());

                JPanel container = new JPanel();
                container.setLayout(null);
                Font fontLabel = new Font("Arial", Font.BOLD, 15);
                int matches = selector.listOfMatches.size();

                String labelForTable = "";
                if (lastOrFullSeason.contains("Весь сезон")){
                    if (allOrHomeOrAway.contains("Все")){
                        labelForTable = "Показатели штрафа всех игр " + teamName + " в сезоне " + season + ":";
                    }
                    if (allOrHomeOrAway.contains("Дома")){
                        labelForTable = "Показатели штрафа всех домашних игр " + teamName + " в сезоне " + season + ":";
                    }
                    if (allOrHomeOrAway.contains("На выезде")){
                        labelForTable = "Показатели штрафа всех гостевых игр " + teamName + " в сезоне " + season + ":";
                    }
                } else {
                    if (allOrHomeOrAway.contains("Все")){
                        labelForTable = "Показатели штрафа последних " + matches + " игр " + teamName + " в сезоне " + season + ":";
                    }
                    if (allOrHomeOrAway.contains("Дома")){
                        labelForTable = "Показатели штрафа последних " + matches + " домашних игр " + teamName + " в сезоне " + season + ":";
                    }
                    if (allOrHomeOrAway.contains("На выезде")){
                        labelForTable = "Показатели штрафа последних " + matches + " гостевых игр " + teamName + " в сезоне " + season + ":";
                    }
                }

                final JLabel label = new JLabel(labelForTable);
                label.setLocation(5, otstup);
                label.setSize(new Dimension(600, 25));
                otstup += 25;
                label.setFont(fontLabel);
                container.add(label);

                String[] colHeads = {"Параметр" ,teamName, "Соперник"};
                String[] params = {"Минут штрафа всего", "Минут штрафа (сред.)", "2 мин. удалений всего", "2 мин. удалений (сред.)",
                        "Мин. кол-во штрафных минут", "Макс. кол-во штрафных минут",
                        "Мин. кол-во 2 мин. удалений", "Макс. кол-во 2 мин. удалений",
                        "'Дисциплинарных' мин. штр. (сред.)"};

                double min2minPen = 1000;
                double max2minPen = 0;
                double min2minPenOp = 1000;
                double max2minPenOp = 0;
                double minPenMinutes = 1000;
                double maxPenMinutes = 0;
                double minPenMinutesOp = 1000;
                double maxPenMinutesOp = 0;

                for (int i=0; i<selector.listOfMatches.size(); i++){
                    Match m = selector.listOfMatches.get(i);

                    if (m.homeTeam.equals(teamName)){
                        if (m.home2MinPenalties > max2minPen)
                            max2minPen = m.home2MinPenalties;
                        if (m.home2MinPenalties < min2minPen)
                            min2minPen = m.home2MinPenalties;
                        if (m.away2MinPenalties > max2minPenOp)
                            max2minPenOp = m.away2MinPenalties;
                        if (m.away2MinPenalties < min2minPenOp)
                            min2minPenOp = m.away2MinPenalties;
                        if (m.homePenaltyMinutes > maxPenMinutes)
                            maxPenMinutes = m.homePenaltyMinutes;
                        if (m.homePenaltyMinutes < minPenMinutes)
                            minPenMinutes = m.homePenaltyMinutes;
                        if (m.awayPenaltyMinutes > maxPenMinutesOp)
                            maxPenMinutesOp = m.awayPenaltyMinutes;
                        if (m.awayPenaltyMinutes < minPenMinutesOp)
                            minPenMinutesOp = m.awayPenaltyMinutes;
                    } else {
                        if (m.away2MinPenalties > max2minPen)
                            max2minPen = m.away2MinPenalties;
                        if (m.away2MinPenalties < min2minPen)
                            min2minPen = m.away2MinPenalties;
                        if (m.home2MinPenalties > max2minPenOp)
                            max2minPenOp = m.home2MinPenalties;
                        if (m.home2MinPenalties < min2minPenOp)
                            min2minPenOp = m.home2MinPenalties;
                        if (m.awayPenaltyMinutes > maxPenMinutes)
                            maxPenMinutes = m.awayPenaltyMinutes;
                        if (m.awayPenaltyMinutes < minPenMinutes)
                            minPenMinutes = m.awayPenaltyMinutes;
                        if (m.homePenaltyMinutes > maxPenMinutesOp)
                            maxPenMinutesOp = m.homePenaltyMinutes;
                        if (m.homePenaltyMinutes < minPenMinutesOp)
                            minPenMinutesOp = m.homePenaltyMinutes;
                    }
                }

                Object[][] data = {
                        {" " + params[0], (int) (Double.parseDouble(selector.pList.get(14).get(1))), (int) (Double.parseDouble(selector.pList.get(14).get(2)))},
                        {" " + params[1], MyMath.round(Double.parseDouble(selector.pList.get(14).get(1))/matches, 2), MyMath.round(Double.parseDouble(selector.pList.get(14).get(2))/matches,2)},
                        {" " + params[2], (int) (Double.parseDouble(selector.pList.get(15).get(1))), (int) (Double.parseDouble(selector.pList.get(15).get(2)))},
                        {" " + params[3], MyMath.round(Double.parseDouble(selector.pList.get(15).get(1))/matches, 2), MyMath.round(Double.parseDouble(selector.pList.get(15).get(2))/matches,2)},
                        {" " + params[4], (int) minPenMinutes, (int) minPenMinutesOp},
                        {" " + params[5], (int) maxPenMinutes, (int) maxPenMinutesOp},
                        {" " + params[6], (int) min2minPen, (int) min2minPenOp},
                        {" " + params[7], (int) max2minPen, (int) max2minPenOp},
                        {" " + params[8], MyMath.round((Double.parseDouble(selector.pList.get(14).get(1))-2*Double.parseDouble(selector.pList.get(15).get(1))) / matches, 3), MyMath.round((Double.parseDouble(selector.pList.get(14).get(2))-2*Double.parseDouble(selector.pList.get(15).get(2))) / matches, 3)}
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

                tablePanel.setSize(620, (table.getRowCount()+1)*table.getRowHeight() - 3);
                tablePanel.setLocation(5, otstup);
                otstup += (table.getRowCount()+1)*table.getRowHeight();
                container.add(tablePanel);

                Settings settings = Settings.getSettingsFromFile();
                boolean flagTablePenOpponents = settings.showPenOpponents;
                ArrayList<Selector> opponentsList = null;
                opponentsList = Selector.getOpponentsList(teamName, selector.listOfMatches, season, allOrHomeOrAway, lastOrFullSeason);
                if (flagTablePenOpponents){
                    String labelForTableOpponents = "";
                    if (lastOrFullSeason.contains("Весь сезон")){
                        if (allOrHomeOrAway.contains("Все")){
                            labelForTableOpponents = "Штрафные показатели противников " + teamName + " в сезоне " + season + ":";
                        }
                        if (allOrHomeOrAway.contains("Дома")){
                            labelForTableOpponents = "Штрафные показатели (на чужом поле) противников " + teamName + " в сезоне " + season + ":";
                        }
                        if (allOrHomeOrAway.contains("На выезде")){
                            labelForTableOpponents = "Штрафные показатели (на своем поле) противников " + teamName + " в сезоне " + season + ":";
                        }
                    } else {
                        if (allOrHomeOrAway.contains("Все")){
                            labelForTableOpponents = "Штрафные показатели последних " + matches + " игр противников " + teamName + " в сезоне " + season + ":";
                        }
                        if (allOrHomeOrAway.contains("Дома")){
                            labelForTableOpponents = "Штрафные показатели последних " + matches + " гостевых игр противников " + teamName + " в сезоне " + season + ":";
                        }
                        if (allOrHomeOrAway.contains("На выезде")){
                            labelForTableOpponents = "Штрафные показатели последних " + matches + " домашних игр противников " + teamName + " в сезоне " + season + ":";
                        }
                    }
                    final JLabel label2 = new JLabel(labelForTableOpponents);
                    label2.setLocation(5, otstup);
                    label2.setSize(new Dimension(600, 25));
                    otstup += 25;
                    label2.setFont(fontLabel);
                    container.add(label2);


                    JPanel tableOpponents = TableMaker.getTablePenaltiesOpponents(teamName, selector.listOfMatches, allOrHomeOrAway, opponentsList);
                    //tableOpponents.setSize(620, 275);
                    tableOpponents.setLocation(0, otstup);
                    otstup += (opponentsList.size() + 1) * 25;
                    tableOpponents.setBackground(new Color(238, 238, 238));
                    container.add(tableOpponents);
                }



                //Вставка просмотрщика параметров
                final JPanel paramsPanel = new JPanel(new VerticalLayout());
                paramsPanel.setBorder(BorderFactory.createTitledBorder(""));
                JPanel selectorsPanel = new JPanel(new GridLayout(1, 0, 5, 5));

                String[] paramsList = new String[]{"Штр. время", "Штр. время 1 пер.", "Штр. время 2 пер.", "Штр. время 3 пер.",
                        "Кол-во двухмин. уд.", "Кол-во двухмин. уд. 1 пер.",
                        "Кол-во двухмин. уд. 2 пер.", "Кол-во двухмин. уд. 3 пер."};
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
                container.setPreferredSize(new Dimension(600, otstup));

                allInfoPanel.add(container, BorderLayout.NORTH);
                //////////////////////////////Конец вставки просмотрщика по параметрам

                ////////////////////////ГРАФИКИ И ТАБЛИЦЫ ВСТАВЛЯЮ ТУТ
                JPanel graphAndTables = new JPanel(new BorderLayout());

//                GraphicForOneTeam graphic = new GraphicForOneTeam();
                Graphic graphic = new Graphic(1, teamName);
                JPanel panelGraphics = graphic.getGraphicsForPenalties(teamName, allOrHomeOrAway, selector.listOfMatches, opponentsList, selector);
                graphAndTables.add(panelGraphics, BorderLayout.CENTER);
                allInfoPanel.add(graphAndTables, BorderLayout.WEST);

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


                /*paramsChooser.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String[] list = Parameters.getIndex((String) paramsChooser.getSelectedItem());
                        String[] list2 = Parameters.getValues((String) paramsChooser.getSelectedItem(), "Выберите тип ставки"); //(String) indexChooser.getSelectedItem()
                        DefaultComboBoxModel modelH = new DefaultComboBoxModel(list);
                        DefaultComboBoxModel modelH2 = new DefaultComboBoxModel(list2);
                        indexChooser.setModel(modelH);
                        valueChooser.setModel(modelH2);
                        paramsChooser.setFocusable(false);
                    }
                });*/

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
                        valueForSlider = minSliderValue + stepSlider * slider.getValue();
                        bottomValue.setText("Выбрано значение: " + String.valueOf(valueForSlider));
                        index3 = slider.getValue();
                        getParamsPanel(paramsPanel, teamName, selector, paramsChooser, indexChooser, valueForSlider);
                    }
                });

            } else {
                JPanel container = new JPanel(/*new GridLayout(3,1,0,0)*/);
                container.setLayout(null);
                container.setPreferredSize(new Dimension(600, 730));

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
                Font fontLabel = new Font("Arial", Font.BOLD, 15);
                label.setFont(fontLabel);
                container.add(label);

                scrollPane = new JScrollPane(container);
                scrollPane.setPreferredSize(new Dimension(600, (int) (0.87 * HEIGHT)));
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
            container.setPreferredSize(new Dimension(600, 730));

            final JLabel label = new JLabel("Задайте все условия поиска. Лига и/или команда не заданы.");
            label.setLocation(10, 0);
            label.setSize(new Dimension(600, 25));
            Font fontLabel = new Font("Arial", Font.BOLD, 15);
            label.setFont(fontLabel);
            container.add(label);

            scrollPane = new JScrollPane(container);
            scrollPane.setPreferredSize(new Dimension(600, (int) (0.87 * HEIGHT)));
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
        buttonShowInfo.setEnabled(false);
        String season = Settings.getCurrentSeasonInLeague(league);
        seasonCB.setSelectedItem("Сезон " + season);
        leagueChooser.setSelectedItem(league);
        buttonShowInfo.setEnabled(true);
    }

}