package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class PanelTrends extends JPanel{
    double procHEIGHT;
    String lastTrendsHA;
    JScrollPane leftScrollPane;
    JScrollPane rightScrollPane;
    JPanel infoPanel;
    final String path = "database/";
    String lastCalculatedLeague = "";
    String lastCalculatedSeason = "";
    String lastCalculatedNumberOfMatches = "";
    String parameter = "";
    String leagueName = "";
    String season = "";
    String lastMatches = "";
    ArrayList<Selector> arrayList = new ArrayList<>();
    ArrayList<String> resultList = new ArrayList<>();
    final JButton buttonShowInfo;
    final JButton buttonToText;
    TrendsThread trendsThread;
    JComboBox<String> seasonChooser;
    JComboBox<String> leagueChooser;

    public PanelTrends(){
        this.setLayout(new BorderLayout());
        final String curSeason = Settings.getDefaultSeason();

        ////////////////////////////////////////////ПАНЕЛЬ
        final JPanel headersPanel = new JPanel(new GridLayout(1, 0, 5, 5));

        ArrayList<String> listOfSeasons = Settings.getListOfSeasons();
        String[] seasonList = new String[listOfSeasons.size()];
        for (int i = 0; i < seasonList.length; i++)
            seasonList[i] = "Сезон " + listOfSeasons.get(i);
        seasonChooser = new JComboBox<>(seasonList);
        headersPanel.add(seasonChooser);
        season = listOfSeasons.get(0);

        JFileChooser fileChooser = new JFileChooser(path + curSeason + "/leagues");
        String[] directoryList = fileChooser.getCurrentDirectory().list();
        ArrayList<String> leagueList = new ArrayList<>();
        leagueList.add("Выберите лигу");
        for (String aDirectoryList : directoryList) leagueList.add(aDirectoryList.replace(".txt", ""));
        String[] listOfLeagues = new String[leagueList.size()];
        for (int i = 0; i < listOfLeagues.length; i++)
            listOfLeagues[i] = leagueList.get(i);
        leagueChooser = new JComboBox<>(listOfLeagues);
        headersPanel.add(leagueChooser);
        leagueName = listOfLeagues[0];


        String[] params = {"Голы", "Броски в створ", "Штрафное время", "Вбрасывания", "Блокированные броски", "Силовые приемы"};
        final JComboBox<String> paramChooser = new JComboBox<>(params);
        headersPanel.add(paramChooser);
        parameter = params[0];

        final String[] lastOrFullSeason = {"Весь сезон", "Последние 3", "Последние 4", "Последние 5", "Последние 6", "Последние 7", "Последние 8", "Последние 9", "Последние 10", "Последние 15", "Последние 20"};
        final JComboBox<String> lastOrFullSeasonChooser = new JComboBox<>(lastOrFullSeason);
        headersPanel.add(lastOrFullSeasonChooser);
        lastMatches = lastOrFullSeason[0];

        buttonShowInfo = new JButton("Отобразить!");
        Font fontForButton = new Font("", 0, 18);
        buttonShowInfo.setFont(fontForButton);
        headersPanel.add(buttonShowInfo);

        this.add(headersPanel, BorderLayout.NORTH);

        infoPanel = new JPanel(new GridLayout(1, 0, 0, 0));
        infoPanel.setBorder(BorderFactory.createTitledBorder(""));

        this.add(infoPanel);

        buttonToText = new JButton("Экспорт трендов в текстовый вид.");
        buttonToText.setFont(fontForButton);
        buttonToText.setEnabled(false);
        this.add(buttonToText, BorderLayout.SOUTH);

        leagueChooser.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                leagueName = (String) leagueChooser.getSelectedItem();
                int index = leagueChooser.getSelectedIndex();
                String pathToLeaguesList = path + seasonChooser.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/";
                JFileChooser rightFileChooser = new JFileChooser(pathToLeaguesList);
                String[] rightDirectoryList = new String[rightFileChooser.getCurrentDirectory().list().length+1];
                rightDirectoryList[0] = "Выберите лигу";
                for (int i=1; i<rightDirectoryList.length; i++)
                    rightDirectoryList[i] = rightFileChooser.getCurrentDirectory().list()[i-1].replace(".txt", "");
                DefaultComboBoxModel modelH = new DefaultComboBoxModel(rightDirectoryList);
                leagueChooser.setModel(modelH);
                leagueChooser.setSelectedIndex(index);
                leagueChooser.setFocusable(false);

            }
        });

        seasonChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seasonChooser.setFocusable(false);
                season = seasonChooser.getSelectedItem().toString().replace("Сезон ", "");

                String league = String.valueOf(leagueChooser.getSelectedItem());

                String pathToLeaguesList = path + season + "/leagues/";
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
            }
        });

        paramChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parameter = (String) paramChooser.getSelectedItem();
            }
        });

        lastOrFullSeasonChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lastMatches = (String) lastOrFullSeasonChooser.getSelectedItem();
            }
        });

        buttonShowInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!leagueName.contains("Выберите")){
//                    trendsThread = new TrendsThread(leagueName, parameter, season, lastMatches, (PanelTrends) buttonShowInfo.getParent().getParent());
//                    trendsThread = new TrendsThread2(leagueName, parameter, season, (PanelTrends) buttonShowInfo.getParent().getParent(), res);
//                    trendsThread.start();
                    Settings settings = Settings.getSettingsFromFile();
                    resultList.clear();
                    trendsThread = new TrendsThread(leagueName, parameter, season, lastMatches, (PanelTrends) buttonShowInfo.getParent().getParent(), resultList, settings.trendsHA);
                    trendsThread.start();
                } else {
                    JFrame window = new JFrame("Внимание!");
                    window.setResizable(false);
                    window.setLayout(new BorderLayout());
                    window.setSize(300, 100);
                    window.setLocation(200, 200);

                    Font font = new Font("", 0, 18);

                    final JLabel labelText = new JLabel("Не выбрана лига!");
                    labelText.setFont(font);
                    labelText.setHorizontalAlignment(JLabel.CENTER);
                    labelText.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                    window.add(labelText);

                    window.setVisible(true);

                }


            }
        });

        buttonToText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File resFile = new File(System.getProperty("user.home") + "/Desktop/" + leagueName + "_" + parameter + "_trends.txt");
                try {
                    // отрываем поток для записи
                    FileOutputStream fos = new FileOutputStream(resFile);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                    // пишем данные
                    for (String s : resultList) {
                        bw.write(s);
                        bw.newLine();
                    }
                    // закрываем поток
                    bw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }

    public void setFilters(String league){
        buttonShowInfo.setEnabled(false);
        String season = Settings.getCurrentSeasonInLeague(league);
        seasonChooser.setSelectedItem("Сезон " + season);
        leagueChooser.setSelectedItem(league);
        buttonShowInfo.setEnabled(true);

    }

}