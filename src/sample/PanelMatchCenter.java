package sample;

import org.jfree.ui.tabbedui.VerticalLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

public class PanelMatchCenter extends JPanel{
    JScrollPane scrollPane;
    int dayCode;
    JPanel panelHeader;
    JPanel panelWithJSP;
    JButton buttonLeft;
    JButton buttonRight;
    JLabel labelDate;
    JScrollPane jsp;
    PanelMatching panelMatching;
    PanelOneTeamStats panelOneTeamStats;
    PanelConfrontation panelConfrontation;
    PanelTablesByLeague panelTablesByLeague;
    PanelTrends panelTrends;
    PanelShotsOnTarget panelShotsOnTarget;
    PanelPenalties panelPenalties;
    PanelAdvancedStatistics panelAdvancedStatistics;
    PanelCalculator panelCalculator;

    public PanelMatchCenter(PanelMatching panelMatching, PanelOneTeamStats panelOneTeamStats, PanelConfrontation panelConfrontation, PanelTablesByLeague panelTablesByLeague,
                            PanelTrends panelTrends, PanelShotsOnTarget panelShotsOnTarget, PanelPenalties panelPenalties, PanelAdvancedStatistics panelAdvancedStatistics,
                            PanelCalculator panelCalculator) {
        this.setLayout(new BorderLayout());

        this.panelMatching = panelMatching;
        this.panelOneTeamStats = panelOneTeamStats;
        this.panelConfrontation = panelConfrontation;
        this.panelTablesByLeague = panelTablesByLeague;
        this.panelTrends = panelTrends;
        this.panelShotsOnTarget = panelShotsOnTarget;
        this.panelPenalties = panelPenalties;
        this.panelAdvancedStatistics = panelAdvancedStatistics;
        this.panelCalculator = panelCalculator;

        Settings settings = Settings.getSettingsFromFile();

        ////////////////////////////////////////////ПАНЕЛЬ
        panelHeader = new JPanel(new BorderLayout());

        buttonLeft = new JButton(new ImageIcon("images/left-arrow.png"));
//        buttonLeft.setEnabled(false);
        panelHeader.add(buttonLeft, BorderLayout.WEST);

        LocalDateTime ldt = LocalDateTime.now(ZoneId.of("GMT"));
        ldt = ldt.plusHours(12 - settings.localTime); // 12 - индекс гринвича в комбобоксе
        final int hourDifference = 11 - settings.localTime; // 11 - индекс в комбобоксе среднеевропейского времени, от которого считаает WS
        String day = String.valueOf(ldt.getDayOfMonth());
        String month = String.valueOf(ldt.getMonthValue());
        String year = String.valueOf(ldt.getYear());
        if (day.length()<2)
            day = "0" + day;
        if (month.length()<2)
            month = "0" + month;
        String dateS = day + "." + month + "." + year;
        dayCode = Integer.parseInt(year)*10000 + Integer.parseInt(month)*100 + Integer.parseInt(day);

        Font font = new Font("", Font.BOLD, 20);
        labelDate = new JLabel(dateS);
        labelDate.setFont(font);
        labelDate.setHorizontalAlignment(SwingConstants.CENTER);
        panelHeader.add(labelDate);

        buttonRight = new JButton(new ImageIcon("images/right-arrow.png"));

        int n = Settings.getNumberOfAccount();
        int nextDay = Settings.getNextDayCode(dayCode);
        String nameOfFile = "H_" + String.valueOf(nextDay) + ".txt";
        File file = Settings.downloadDayFile(n, nameOfFile);

        if (!file.exists() || file.length()==0)
            buttonRight.setEnabled(false);
        else
            buttonRight.setEnabled(true);

        panelHeader.add(buttonRight, BorderLayout.EAST);
        this.add(panelHeader, BorderLayout.NORTH);

        panelWithJSP = new JPanel(new BorderLayout());
        fillData(dayCode, hourDifference);
        this.add(panelWithJSP);



        buttonLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newDayCode = Settings.getPreviousDayCode(dayCode);
                String day = String.valueOf(newDayCode).substring(6,8);
                String month = String.valueOf(newDayCode).substring(4,6);
                String year = String.valueOf(newDayCode).substring(0,4);

                File file = Settings.downloadDayFile(Settings.getNumberOfAccount(), "H_" + Settings.getPreviousDayCode(newDayCode) + ".txt");
                buttonLeft.setEnabled(file.exists() && file.length() != 0);
                labelDate.setText(day + "." + month + "." + year);
                dayCode = Integer.parseInt(year)*10000 + Integer.parseInt(month)*100 + Integer.parseInt(day);

                file = Settings.downloadDayFile(Settings.getNumberOfAccount(), "H_" + Settings.getNextDayCode(newDayCode) + ".txt");
                buttonRight.setEnabled(file.exists() && file.length() != 0);

                fillData(dayCode, hourDifference);

            }
        });

        buttonRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newDayCode = Settings.getNextDayCode(dayCode);
                String day = String.valueOf(newDayCode).substring(6, 8);
                String month = String.valueOf(newDayCode).substring(4,6);
                String year = String.valueOf(newDayCode).substring(0,4);
                File file = Settings.downloadDayFile(Settings.getNumberOfAccount(), "H_" + Settings.getNextDayCode(newDayCode) + ".txt");
                buttonRight.setEnabled(file.exists() && file.length() != 0);
                labelDate.setText(day + "." + month + "." + year);
                dayCode = Integer.parseInt(year)*10000 + Integer.parseInt(month)*100 + Integer.parseInt(day);
                file = Settings.downloadDayFile(Settings.getNumberOfAccount(), "H_" + Settings.getPreviousDayCode(newDayCode) + ".txt");
                buttonLeft.setEnabled(file.exists() && file.length() != 0);
                fillData(dayCode, hourDifference);

            }
        });

    }

    private void fillData(int dayCode, int hourDifference){
        JPanel panelWithInfo = new JPanel(new VerticalLayout());
        Font font = new Font("", Font.BOLD, 20);
        ArrayList<String> list = Settings.getListForMatchCenter(String.valueOf(dayCode), hourDifference);
        int index = 0;

        while (index < list.size()){
            if (list.get(index).split("\\*").length > 1){
                final JPanel panelMatch = new JPanel(new BorderLayout());
                JLabel labelTime = new JLabel(list.get(index).split("\\*")[0]);

                final String homeTeam = list.get(index).split("\\*")[1];
                final String awayTeam = list.get(index).split("\\*")[2];

                labelTime.setFont(font);
                labelTime.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
                labelTime.setHorizontalAlignment(SwingConstants.CENTER);
                panelMatch.add(labelTime, BorderLayout.WEST);

                JLabel labelMatch = new JLabel(homeTeam + "  vs  " + awayTeam);
                labelMatch.setFont(font);
                labelMatch.setHorizontalAlignment(SwingConstants.CENTER);
                panelMatch.add(labelMatch);

                JPanel panelButtons = new JPanel(new GridLayout(1, 0, 5, 5));
                JButton buttonStats = new JButton("Задать значения фильтрам");
                buttonStats.setFont(font);
                JButton buttonTrends = new JButton("Тренды");
                buttonTrends.setFont(font);
                panelButtons.add(buttonStats);
                panelButtons.add(buttonTrends);
                panelButtons.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 3));
                panelMatch.add(panelButtons, BorderLayout.EAST);

                buttonStats.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });

                final String currentLeague = Team.getLeague(homeTeam);
                buttonTrends.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Settings settings = Settings.getSettingsFromFile();

                        Selector selectorHT = new Selector();
                        Selector selectorAT = new Selector();
                        String allOrHA = "Все матчи";

                        if (settings.trendsHA){
                            selectorHT.getListOfMatches(currentLeague, homeTeam, "Дома", Settings.getCurrentSeasonInLeague(currentLeague), "Весь сезон");
                            selectorAT.getListOfMatches(currentLeague, awayTeam, "На выезде", Settings.getCurrentSeasonInLeague(currentLeague), "Весь сезон");
                            allOrHA = "Дом - выезд";
                        } else {
                            selectorHT.getListOfMatches(currentLeague, homeTeam, "Все матчи", Settings.getCurrentSeasonInLeague(currentLeague), "Весь сезон");
                            selectorAT.getListOfMatches(currentLeague, awayTeam, "Все матчи", Settings.getCurrentSeasonInLeague(currentLeague), "Весь сезон");
                        }

                        selectorHT.getPList(selectorHT.listOfMatches, homeTeam, Settings.getCurrentSeasonInLeague(currentLeague));
                        selectorAT.getPList(selectorAT.listOfMatches, awayTeam, Settings.getCurrentSeasonInLeague(currentLeague));

                        WindowTrendsForTwoTeams wttt = new WindowTrendsForTwoTeams(homeTeam, awayTeam, Settings.getCurrentSeasonInLeague(currentLeague),
                                allOrHA, "Весь сезон", selectorHT, selectorAT);
                        wttt.setVisible(true);
                    }
                });

                buttonStats.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        FiltersThread filtersThread = new FiltersThread(panelMatching, panelOneTeamStats, panelConfrontation, panelTablesByLeague,
                                panelTrends, panelShotsOnTarget, panelPenalties, panelAdvancedStatistics, panelCalculator, currentLeague, homeTeam, awayTeam);
                        filtersThread.start();

                    }
                });

                panelMatch.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

                JPanel outerPanel = new JPanel(new BorderLayout());
                outerPanel.add(panelMatch);
                outerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
                panelWithInfo.add(outerPanel);
                index++;
            } else{
//                currentLeague = list.get(index);
                JLabel labelTime = new JLabel(list.get(index));
                labelTime.setFont(font);
                labelTime.setHorizontalAlignment(SwingConstants.CENTER);
                labelTime.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
                panelWithInfo.add(labelTime);
                index++;
            }
        }
        if (panelWithJSP != null)
            panelWithJSP.removeAll();
        jsp = new JScrollPane(panelWithInfo);
        jsp.setVerticalScrollBar( new JScrollBar() {
            public int getUnitIncrement( int direction ) {
                return 50;
            }
        } );
        panelWithJSP.add(jsp);
        panelWithJSP.revalidate();
    }

}