package sample;

import org.jfree.ui.tabbedui.VerticalLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CalculatorThread extends Thread{
    final String path;
    String leagueName;
    String homeTeam;
    String awayTeam;
    String season;
    double slider_HT_1_Value;
    double slider_HT_2_Value;
    double slider_HT_3_Value;
    double slider_HT_4_Value;
    double slider_AT_1_Value;
    double slider_AT_2_Value;
    double slider_AT_3_Value;
    double slider_AT_4_Value;

    ArrayList<Double> weightsHT;
    ArrayList<Double> weightsAT;
    ArrayList<Double> weightsConfAll;
    ArrayList<Double> weightsConfHomeField;

    String[][] data;

    JFrame tw;
    PanelCalculator pCalc;
    JProgressBar jpb;

    public CalculatorThread(String season,  String leagueName, String homeTeam, String awayTeam, PanelCalculator pCalc,
                            double slider_HT_1_Value, double slider_HT_2_Value, double slider_HT_3_Value, double slider_HT_4_Value,
                            double slider_AT_1_Value, double slider_AT_2_Value, double slider_AT_3_Value, double slider_AT_4_Value
    ){
        path = "database/";
        this.leagueName = leagueName;
        this.season = season;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.slider_HT_1_Value = slider_HT_1_Value;
        this.slider_HT_2_Value = slider_HT_2_Value;
        this.slider_HT_3_Value = slider_HT_3_Value;
        this.slider_HT_4_Value = slider_HT_4_Value;
        this.slider_AT_1_Value = slider_AT_1_Value;
        this.slider_AT_2_Value = slider_AT_2_Value;
        this.slider_AT_3_Value = slider_AT_3_Value;
        this.slider_AT_4_Value = slider_AT_4_Value;
        this.pCalc = pCalc;

        tw = new JFrame("Внимание!");
        tw.setResizable(false);
        tw.setLayout(new BorderLayout());
        tw.setSize(500, 200);
        tw.setLocation(200, 200);

        JLabel label = new JLabel("Подождите, идет расчет");
        label.setFont(new Font("", Font.BOLD, 20));
        tw.add(label, BorderLayout.NORTH);

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

        String[] titles = {"Голы в основное время", "Голы с учетом ОТ и буллитов", "Реализация бросков, %",
                "Реализация большинства", "Меньшинство", "Время в атаке (минут)",
                "Голы в первом периоде", "Голы во втором периоде", "Голы в третьем периоде", "Забитые голы в меньшинстве",
                "Броски в створ", "Броски в створ 1 пер.", "Броски в створ 2 пер.", "Броски в створ 3 пер.", "Броски мимо",
                "Заблокированные броски", "Вбрасывания", "Силовые приемы", "Минуты штрафа", "Кол-во двухминутных удалений"};

        int numberOfStages = 15;
        int currentStage = 0;

        if (!pCalc.lastCalculatedLeague.equals(leagueName) || !pCalc.lastCalculatedSeason.equals(season)
                || !pCalc.lastCalculatedHomeTeam.equals(homeTeam) || !pCalc.lastCalculatedAwayTeam.equals(awayTeam)){
            pCalc.currentLeague = League.getLeagueFromFile(leagueName, season);
            ArrayList<String> tournamentTable = League.getLeagueFromFile(leagueName, season).tournamentTable;
            currentStage++;
            jpb.setValue((int) (currentStage / (double) numberOfStages * 100));

            pCalc.selectorHT_All = new Selector();
            pCalc.selectorHT_All.getListOfMatches(leagueName, homeTeam, "Все матчи", season, "Весь сезон");
            pCalc.selectorHT_All.getPList(pCalc.selectorHT_All.listOfMatches, homeTeam, season);
            pCalc.dataArrayThis_HT_All = new double[titles.length][pCalc.selectorHT_All.listOfMatches.size()];
            pCalc.dataArrayOpponent_HT_All = new double[titles.length][pCalc.selectorHT_All.listOfMatches.size()];
            pCalc.dataArrayTotal_HT_All = new double[titles.length][pCalc.selectorHT_All.listOfMatches.size()];
            pCalc.arrayOpponents_HT_All = new String[pCalc.selectorHT_All.listOfMatches.size()];
            pCalc.selectorHT_All.getArraysWithStats(homeTeam, pCalc.dataArrayThis_HT_All, pCalc.dataArrayOpponent_HT_All,
                    pCalc.dataArrayTotal_HT_All, pCalc.arrayOpponents_HT_All, tournamentTable);
            currentStage++;
            jpb.setValue((int) (currentStage / (double) numberOfStages * 100));

            pCalc.selectorAT_All = new Selector();
            pCalc.selectorAT_All.getListOfMatches(leagueName, awayTeam, "Все матчи", season, "Весь сезон");
            pCalc.selectorAT_All.getPList(pCalc.selectorAT_All.listOfMatches, awayTeam, season);
            pCalc.dataArrayThis_AT_All = new double[titles.length][pCalc.selectorAT_All.listOfMatches.size()];
            pCalc.dataArrayOpponent_AT_All = new double[titles.length][pCalc.selectorAT_All.listOfMatches.size()];
            pCalc.dataArrayTotal_AT_All = new double[titles.length][pCalc.selectorAT_All.listOfMatches.size()];
            pCalc.arrayOpponents_AT_All = new String[pCalc.selectorAT_All.listOfMatches.size()];
            pCalc.selectorAT_All.getArraysWithStats(awayTeam, pCalc.dataArrayThis_AT_All, pCalc.dataArrayOpponent_AT_All,
                    pCalc.dataArrayTotal_AT_All, pCalc.arrayOpponents_AT_All, tournamentTable);
            currentStage++;
            jpb.setValue((int) (currentStage / (double) numberOfStages * 100));

            pCalc.selectorHT_Home = new Selector();
            pCalc.selectorHT_Home.getListOfMatches(leagueName, homeTeam, "Дома", season, "Весь сезон");
            pCalc.selectorHT_Home.getPList(pCalc.selectorHT_Home.listOfMatches, homeTeam, season);
            pCalc.dataArrayThis_HT_Home = new double[titles.length][pCalc.selectorHT_Home.listOfMatches.size()];
            pCalc.dataArrayOpponent_HT_Home = new double[titles.length][pCalc.selectorHT_Home.listOfMatches.size()];
            pCalc.dataArrayTotal_HT_Home = new double[titles.length][pCalc.selectorHT_Home.listOfMatches.size()];
            pCalc.arrayOpponents_HT_Home = new String[pCalc.selectorHT_Home.listOfMatches.size()];
            pCalc.selectorHT_Home.getArraysWithStats(homeTeam, pCalc.dataArrayThis_HT_Home, pCalc.dataArrayOpponent_HT_Home,
                    pCalc.dataArrayTotal_HT_Home, pCalc.arrayOpponents_HT_Home, tournamentTable);
            currentStage++;
            jpb.setValue((int) (currentStage / (double) numberOfStages * 100));

            pCalc.selectorAT_Away = new Selector();
            pCalc.selectorAT_Away.getListOfMatches(leagueName, awayTeam, "На выезде", season, "Весь сезон");
            pCalc.selectorAT_Away.getPList(pCalc.selectorAT_Away.listOfMatches, awayTeam, season);
            pCalc.dataArrayThis_AT_Away = new double[titles.length][pCalc.selectorAT_Away.listOfMatches.size()];
            pCalc.dataArrayOpponent_AT_Away = new double[titles.length][pCalc.selectorAT_Away.listOfMatches.size()];
            pCalc.dataArrayTotal_AT_Away = new double[titles.length][pCalc.selectorAT_Away.listOfMatches.size()];
            pCalc.arrayOpponents_AT_Away = new String[pCalc.selectorAT_Away.listOfMatches.size()];
            pCalc.selectorAT_Away.getArraysWithStats(awayTeam, pCalc.dataArrayThis_AT_Away, pCalc.dataArrayOpponent_AT_Away,
                    pCalc.dataArrayTotal_AT_Away, pCalc.arrayOpponents_AT_Away, tournamentTable);
            currentStage++;
            jpb.setValue((int) (currentStage / (double) numberOfStages * 100));

            pCalc.selectorConfAll_HT = new Selector();
            pCalc.selectorConfAll_HT.getConfrontationList(leagueName, homeTeam, awayTeam, "Все матчи");
            pCalc.dataArrayThis_ConfAll_HT = new double[titles.length][pCalc.selectorConfAll_HT.listOfMatches.size()];
            pCalc.dataArrayOpponent_ConfAll_HT = new double[titles.length][pCalc.selectorConfAll_HT.listOfMatches.size()];
            pCalc.dataArrayTotal_ConfAll_HT = new double[titles.length][pCalc.selectorConfAll_HT.listOfMatches.size()];
            pCalc.arrayOpponents_ConfAll_HT = new String[pCalc.selectorConfAll_HT.listOfMatches.size()];
            pCalc.selectorConfAll_HT.getArraysWithStats(homeTeam, pCalc.dataArrayThis_ConfAll_HT, pCalc.dataArrayOpponent_ConfAll_HT,
                    pCalc.dataArrayTotal_ConfAll_HT, pCalc.arrayOpponents_ConfAll_HT, tournamentTable);
            pCalc.arrayLeaguesAll = new ArrayList<>();
            for (int i=0; i<pCalc.selectorConfAll_HT.listOfMatches.size(); i++){
                String localSeason = pCalc.selectorConfAll_HT.seasonsList.get(i);
                League localLeague = League.getLeagueFromFile(leagueName, localSeason);
                pCalc.arrayLeaguesAll.add(localLeague);
            }
            currentStage++;
            jpb.setValue((int) (currentStage / (double) numberOfStages * 100));

            pCalc.selectorConfHomeField_HT = new Selector();
            pCalc.selectorConfHomeField_HT.getConfrontationList(leagueName, homeTeam, awayTeam, "На поле хозяев");
            pCalc.dataArrayThis_ConfHomeField_HT = new double[titles.length][pCalc.selectorConfHomeField_HT.listOfMatches.size()];
            pCalc.dataArrayOpponent_ConfHomeField_HT = new double[titles.length][pCalc.selectorConfHomeField_HT.listOfMatches.size()];
            pCalc.dataArrayTotal_ConfHomeField_HT = new double[titles.length][pCalc.selectorConfHomeField_HT.listOfMatches.size()];
            pCalc.arrayOpponents_ConfHomeField_HT = new String[pCalc.selectorConfHomeField_HT.listOfMatches.size()];
            pCalc.selectorConfHomeField_HT.getArraysWithStats(homeTeam, pCalc.dataArrayThis_ConfHomeField_HT, pCalc.dataArrayOpponent_ConfHomeField_HT,
                    pCalc.dataArrayTotal_ConfHomeField_HT, pCalc.arrayOpponents_ConfHomeField_HT, tournamentTable);
            ArrayList<League> arrayLeaguesHomeField_HT = new ArrayList<>();
            for (int i=0; i<pCalc.selectorConfHomeField_HT.listOfMatches.size(); i++){
                String localSeason = pCalc.selectorConfAll_HT.seasonsList.get(i);
                League localLeague = League.getLeagueFromFile(leagueName, localSeason);
                arrayLeaguesHomeField_HT.add(localLeague);
            }
            currentStage++;
            jpb.setValue((int) (currentStage / (double) numberOfStages * 100));

            pCalc.selectorConfAll_AT = new Selector();
            pCalc.selectorConfAll_AT.getConfrontationList(leagueName, homeTeam, awayTeam, "Все матчи");
            pCalc.dataArrayThis_ConfAll_AT = new double[titles.length][pCalc.selectorConfAll_AT.listOfMatches.size()];
            pCalc.dataArrayOpponent_ConfAll_AT = new double[titles.length][pCalc.selectorConfAll_AT.listOfMatches.size()];
            pCalc.dataArrayTotal_ConfAll_AT = new double[titles.length][pCalc.selectorConfAll_AT.listOfMatches.size()];
            pCalc.arrayOpponents_ConfAll_AT = new String[pCalc.selectorConfAll_AT.listOfMatches.size()];
            pCalc.selectorConfAll_AT.getArraysWithStats(awayTeam, pCalc.dataArrayThis_ConfAll_AT, pCalc.dataArrayOpponent_ConfAll_AT,
                    pCalc.dataArrayTotal_ConfAll_AT, pCalc.arrayOpponents_ConfAll_AT, tournamentTable);
            currentStage++;
            jpb.setValue((int) (currentStage / (double) numberOfStages * 100));

            pCalc.selectorConfHomeField_AT = new Selector();
            pCalc.selectorConfHomeField_AT.getConfrontationList(leagueName, homeTeam, awayTeam, "На поле хозяев");
            pCalc.dataArrayThis_ConfHomeField_AT = new double[titles.length][pCalc.selectorConfHomeField_AT.listOfMatches.size()];
            pCalc.dataArrayOpponent_ConfHomeField_AT = new double[titles.length][pCalc.selectorConfHomeField_AT.listOfMatches.size()];
            pCalc.dataArrayTotal_ConfHomeField_AT = new double[titles.length][pCalc.selectorConfHomeField_AT.listOfMatches.size()];
            pCalc.arrayOpponents_ConfHomeField_AT = new String[pCalc.selectorConfHomeField_AT.listOfMatches.size()];
            pCalc.selectorConfHomeField_AT.getArraysWithStats(awayTeam, pCalc.dataArrayThis_ConfHomeField_AT, pCalc.dataArrayOpponent_ConfHomeField_AT,
                    pCalc.dataArrayTotal_ConfHomeField_AT, pCalc.arrayOpponents_ConfHomeField_AT, tournamentTable);
            currentStage++;
            jpb.setValue((int) (currentStage / (double) numberOfStages * 100));

            pCalc.lastCalculatedSeason = season;
            pCalc.lastCalculatedLeague = leagueName;
            pCalc.lastCalculatedHomeTeam = homeTeam;
            pCalc.lastCalculatedAwayTeam = awayTeam;
        } else {
            currentStage += 9;
        }

        /////////////////// *************** Слайдер 1 - В зависимости от его значения по-разному заполняются ВЕСА
        weightsHT = MyMath.getWeights(pCalc.selectorHT_All.listOfMatches.size(), slider_HT_1_Value / pCalc.maxSliderValue);
        weightsAT = MyMath.getWeights(pCalc.selectorAT_All.listOfMatches.size(), slider_AT_1_Value / pCalc.maxSliderValue);
        weightsConfAll = MyMath.getWeights(pCalc.selectorConfAll_HT.listOfMatches.size(), (slider_HT_1_Value + slider_AT_1_Value) / 2.0 / pCalc.maxSliderValue);
        weightsConfHomeField = MyMath.getWeights(pCalc.selectorConfHomeField_HT.listOfMatches.size(), (slider_HT_1_Value + slider_AT_1_Value) / 2.0 / pCalc.maxSliderValue);
        /////////////////// &&&&&&&&&&&&&&&&&&&& Слайдер 1
        currentStage++;
        jpb.setValue((int) (currentStage / (double) numberOfStages * 100));

        ArrayList<Double> calculatedParams = new ArrayList();
        data = new String[5][3];

        // "Броски в створ", "Блокированные броски", "Вбрасывания", "Силовые приемы"
        // Броски за матч. 1 - getArraysWithStats; 2 - в Админке
        calculatedParams = calcParams(10, 7);
        data[0][0] = calculatedParams.get(0) + " : " + calculatedParams.get(1);
        data[0][1] = String.valueOf(MyMath.round(calculatedParams.get(0) + calculatedParams.get(1),2));
        data[0][2] = String.valueOf(MyMath.round(calculatedParams.get(0) - calculatedParams.get(1),2));

        currentStage++;
        jpb.setValue((int) (currentStage / (double) numberOfStages * 100));

        // Блокированные броски за матч
        calculatedParams = calcParams(15, 14);
        data[1][0] = calculatedParams.get(0) + " : " + calculatedParams.get(1);
        data[1][1] = String.valueOf(MyMath.round(calculatedParams.get(0) + calculatedParams.get(1),2));
        data[1][2] = String.valueOf(MyMath.round(calculatedParams.get(0) - calculatedParams.get(1),2));

        currentStage++;
        jpb.setValue((int) (currentStage / (double) numberOfStages * 100));

        // Вбрасывания за матч
        calculatedParams = calcParams(16, 12);
        data[2][0] = calculatedParams.get(0) + " : " + calculatedParams.get(1);
        data[2][1] = String.valueOf(MyMath.round(calculatedParams.get(0) + calculatedParams.get(1),2));
        data[2][2] = String.valueOf(MyMath.round(calculatedParams.get(0) - calculatedParams.get(1),2));

        currentStage++;
        jpb.setValue((int) (currentStage / (double) numberOfStages * 100));

        // Силовые приемы за матч
        calculatedParams = calcParams(17, 15);
        data[3][0] = calculatedParams.get(0) + " : " + calculatedParams.get(1);
        data[3][1] = String.valueOf(MyMath.round(calculatedParams.get(0) + calculatedParams.get(1),2));
        data[3][2] = String.valueOf(MyMath.round(calculatedParams.get(0) - calculatedParams.get(1),2));

        currentStage++;
        jpb.setValue((int) (currentStage / (double) numberOfStages * 100));

        // Двухминутные удаления за матч
        calculatedParams = calcParams(19, 17);
        data[4][0] = calculatedParams.get(0) + " : " + calculatedParams.get(1);
        data[4][1] = String.valueOf(MyMath.round(calculatedParams.get(0) + calculatedParams.get(1),2));
        data[4][2] = String.valueOf(MyMath.round(calculatedParams.get(0) - calculatedParams.get(1),2));

        currentStage++;
        jpb.setValue((int) (currentStage / (double) numberOfStages * 100));

        pCalc.dataPanel.removeAll();
        pCalc.dataPanel.add(getResultPanel(), BorderLayout.CENTER);
        pCalc.dataPanel.revalidate();
        pCalc.revalidate();

        tw.setVisible(false);

    }

    public ArrayList<Double> calcParams(int indexOfPar, int indexInTable){
        ArrayList<Double> result = new ArrayList<>();

        double[][] resultHT = new double[2][2];
        double[][] resultAT = new double[2][2];


        /////////////////// *************** Считаем статистику сезона (ВСЕ МАТЧИ)
        double sumHT = 0;
        double sumHT_Opp = 0;
        double sumAT = 0;
        double sumAT_Opp = 0;

        for (int i=0; i<pCalc.selectorHT_All.listOfMatches.size(); i++){
            double average_HT_OppConceded = pCalc.currentLeague.getParameterValue(pCalc.arrayOpponents_HT_All[i], "Общее", indexInTable, 1);
            sumHT += (pCalc.dataArrayThis_HT_All[indexOfPar][i] - average_HT_OppConceded)*weightsHT.get(i);
            double average_HT_OppScored = pCalc.currentLeague.getParameterValue(pCalc.arrayOpponents_HT_All[i], "Общее", indexInTable, 0);
            sumHT_Opp += (pCalc.dataArrayOpponent_HT_All[indexOfPar][i] - average_HT_OppScored)*weightsHT.get(i);
        }

        for (int i=0; i<pCalc.selectorAT_All.listOfMatches.size(); i++){
            double average_AT_OppCornersConceded = pCalc.currentLeague.getParameterValue(pCalc.arrayOpponents_AT_All[i], "Общее", indexInTable, 1);
            sumAT += (pCalc.dataArrayThis_AT_All[indexOfPar][i] - average_AT_OppCornersConceded)*weightsAT.get(i);

            double average_AT_OppCornersScored = pCalc.currentLeague.getParameterValue(pCalc.arrayOpponents_AT_All[i], "Общее", indexInTable, 0);
            sumAT_Opp += (pCalc.dataArrayOpponent_AT_All[indexOfPar][i] - average_AT_OppCornersScored)*weightsAT.get(i);
        }


        sumHT = sumHT / (double) pCalc.selectorHT_All.listOfMatches.size();
        sumHT_Opp = sumHT_Opp / (double) pCalc.selectorHT_All.listOfMatches.size();
        sumAT = sumAT / (double) pCalc.selectorAT_All.listOfMatches.size();
        sumAT_Opp = sumAT_Opp / (double) pCalc.selectorAT_All.listOfMatches.size();

        double homeCornersSelf = pCalc.currentLeague.getParameterValue(Team.getShortName(awayTeam), "Общее", indexInTable, 1) + sumHT;
        double awayCornersOpp = pCalc.currentLeague.getParameterValue(Team.getShortName(homeTeam), "Общее", indexInTable, 0) + sumAT_Opp;
        double awayCornersSelf = pCalc.currentLeague.getParameterValue(Team.getShortName(homeTeam), "Общее", indexInTable, 1) + sumAT;
        double homeCornersSOpp = pCalc.currentLeague.getParameterValue(Team.getShortName(awayTeam), "Общее", indexInTable, 0) + sumHT_Opp;

        System.out.println("Текущий сезон:");
        System.out.println("Все игры");
        System.out.println("Промежуточные результаты хозяев: " + MyMath.round(homeCornersSelf , 2) + " и " + MyMath.round(awayCornersOpp , 2));
        System.out.println("Промежуточные результаты гостей : " + MyMath.round(awayCornersSelf , 2) + " и " + MyMath.round(homeCornersSOpp , 2));

        resultHT[0][0] = MyMath.getValueBetweenAB(homeCornersSelf, awayCornersOpp,  slider_HT_4_Value/ pCalc.maxSliderValue);
        resultAT[0][0] = MyMath.getValueBetweenAB(awayCornersSelf, homeCornersSOpp, slider_AT_4_Value/ pCalc.maxSliderValue);

        System.out.println("Хозяева : " + MyMath.round(resultHT[0][0] , 2));
        System.out.println("Гости : " + MyMath.round(resultAT[0][0] , 2));
        /////////////////// &&&&&&&&&&&&&&&&&&&& Считаем статистику сезона (ВСЕ МАТЧИ)

        /////////////////// *************** Считаем статистику сезона (Дом/выезд)
        sumHT = 0;
        sumHT_Opp = 0;
        sumAT = 0;
        sumAT_Opp = 0;
        for (int i=0; i<pCalc.selectorHT_Home.listOfMatches.size(); i++){
            double average_HT_OppCornersConceded = pCalc.currentLeague.getParameterValue(pCalc.arrayOpponents_HT_Home[i], "Выезд", indexInTable, 1);
            sumHT += (pCalc.dataArrayThis_HT_Home[indexOfPar][i] - average_HT_OppCornersConceded)*weightsHT.get(i);

            double average_HT_OppCornersScored = pCalc.currentLeague.getParameterValue(pCalc.arrayOpponents_HT_Home[i], "Выезд", indexInTable, 0);
            sumHT_Opp += (pCalc.dataArrayOpponent_HT_Home[indexOfPar][i] - average_HT_OppCornersScored)*weightsHT.get(i);
        }

        for (int i=0; i<pCalc.selectorAT_Away.listOfMatches.size(); i++){
            double average_AT_OppCornersConceded = pCalc.currentLeague.getParameterValue(pCalc.arrayOpponents_AT_Away[i], "Дом", indexInTable, 1);
            sumAT += (pCalc.dataArrayThis_AT_Away[indexOfPar][i] - average_AT_OppCornersConceded)*weightsAT.get(i);

            double average_AT_OppCornersScored = pCalc.currentLeague.getParameterValue(pCalc.arrayOpponents_AT_Away[i], "Дом", indexInTable, 0);
            sumAT_Opp += (pCalc.dataArrayOpponent_AT_Away[indexOfPar][i] - average_AT_OppCornersScored)*weightsAT.get(i);
        }


        sumHT = sumHT / (double) pCalc.selectorHT_Home.listOfMatches.size();
        sumHT_Opp = sumHT_Opp / (double) pCalc.selectorHT_Home.listOfMatches.size();
        sumAT = sumAT / (double) pCalc.selectorAT_Away.listOfMatches.size();
        sumAT_Opp = sumAT_Opp / (double) pCalc.selectorAT_Away.listOfMatches.size();

        homeCornersSelf = pCalc.currentLeague.getParameterValue(Team.getShortName(awayTeam), "Выезд", indexInTable, 1) + sumHT;
        awayCornersOpp = pCalc.currentLeague.getParameterValue(Team.getShortName(homeTeam), "Дом", indexInTable, 0) + sumAT_Opp;
        awayCornersSelf = pCalc.currentLeague.getParameterValue(Team.getShortName(homeTeam), "Дом", indexInTable, 1) + sumAT;
        homeCornersSOpp = pCalc.currentLeague.getParameterValue(Team.getShortName(awayTeam), "Выезд", indexInTable, 0) + sumHT_Opp;


        System.out.println("Текущий сезон:");
        System.out.println("Дом/выезд");
        System.out.println("Промежуточные результаты хозяев: " + MyMath.round(homeCornersSelf , 2) + " и " + MyMath.round(awayCornersOpp , 2));
        System.out.println("Промежуточные результаты гостей : " + MyMath.round(awayCornersSelf , 2) + " и " + MyMath.round(homeCornersSOpp , 2));

        resultHT[0][1] = MyMath.getValueBetweenAB(homeCornersSelf, awayCornersOpp,  slider_HT_4_Value/ pCalc.maxSliderValue);
        resultAT[0][1] = MyMath.getValueBetweenAB(awayCornersSelf, homeCornersSOpp, slider_AT_4_Value/ pCalc.maxSliderValue);

        System.out.println("Хозяева : " + MyMath.round(resultHT[0][1] , 2));
        System.out.println("Гости : " + MyMath.round(resultAT[0][1] , 2));
        /////////////////// &&&&&&&&&&&&&&&&&&&& Считаем статистику сезона (Дом/выезд)

        /////////////////// ******************** Слайдер 2 - В зависимости от его значения посчитанные ниже значения будут разными
        double currentSeason_HT = MyMath.getValueBetweenAB(resultHT[0][0], resultHT[0][1], slider_HT_2_Value/ pCalc.maxSliderValue);
        double currentSeason_AT = MyMath.getValueBetweenAB(resultAT[0][0], resultAT[0][1], slider_AT_2_Value/ pCalc.maxSliderValue);
        /////////////////// &&&&&&&&&&&&&&&&&&&& Слайдер 2 - В зависимости от его значения посчитанные ниже значения будут разными



        /////////////////// ******************** Считаем статистику личных встреч (Все матчи)
        sumHT = 0;
        sumHT_Opp = 0;
        sumAT = 0;
        sumAT_Opp = 0;
        for (int i=0; i<pCalc.selectorConfAll_HT.listOfMatches.size(); i++){
            double average_HT_OppCornersConceded = pCalc.arrayLeaguesAll.get(i).getParameterValue(pCalc.arrayOpponents_ConfAll_HT[i], "Общее", indexInTable, 1);
            sumHT += (pCalc.dataArrayThis_ConfAll_HT[indexOfPar][i] - average_HT_OppCornersConceded)*weightsConfAll.get(i);

            double average_HT_OppCornersScored = pCalc.arrayLeaguesAll.get(i).getParameterValue(pCalc.arrayOpponents_ConfAll_HT[i], "Общее", indexInTable, 0);
            sumHT_Opp += (pCalc.dataArrayOpponent_ConfAll_HT[indexOfPar][i] - average_HT_OppCornersScored)*weightsConfAll.get(i);
        }

        for (int i=0; i<pCalc.selectorConfAll_AT.listOfMatches.size(); i++){
            double average_AT_OppCornersConceded = pCalc.arrayLeaguesAll.get(i).getParameterValue(pCalc.arrayOpponents_ConfAll_AT[i], "Общее", indexInTable, 1);
            sumAT += (pCalc.dataArrayThis_ConfAll_AT[indexOfPar][i] - average_AT_OppCornersConceded)*weightsConfAll.get(i);

            double average_AT_OppCornersScored = pCalc.arrayLeaguesAll.get(i).getParameterValue(pCalc.arrayOpponents_ConfAll_AT[i], "Общее", indexInTable, 0);
            sumAT_Opp += (pCalc.dataArrayOpponent_ConfAll_AT[indexOfPar][i] - average_AT_OppCornersScored)*weightsConfAll.get(i);
        }


        sumHT = sumHT / (double) pCalc.selectorConfAll_HT.listOfMatches.size();
        sumHT_Opp = sumHT_Opp / (double) pCalc.selectorConfAll_HT.listOfMatches.size();
        sumAT = sumAT / (double) pCalc.selectorConfAll_AT.listOfMatches.size();
        sumAT_Opp = sumAT_Opp / (double) pCalc.selectorConfAll_AT.listOfMatches.size();

        homeCornersSelf = pCalc.currentLeague.getParameterValue(Team.getShortName(awayTeam), "Общее", indexInTable, 1) + sumHT;
        awayCornersOpp  = pCalc.currentLeague.getParameterValue(Team.getShortName(homeTeam), "Общее", indexInTable, 0) + sumAT_Opp;
        awayCornersSelf = pCalc.currentLeague.getParameterValue(Team.getShortName(homeTeam), "Общее", indexInTable, 1) + sumAT;
        homeCornersSOpp = pCalc.currentLeague.getParameterValue(Team.getShortName(awayTeam), "Общее", indexInTable, 0) + sumHT_Opp;

        System.out.println("Личные встречи:");
        System.out.println("Все игры");
        System.out.println("Промежуточные результаты хозяев: " + MyMath.round(homeCornersSelf , 2) + " и " + MyMath.round(awayCornersOpp , 2));
        System.out.println("Промежуточные результаты гостей : " + MyMath.round(awayCornersSelf , 2) + " и " + MyMath.round(homeCornersSOpp , 2));

        resultHT[1][0] = MyMath.getValueBetweenAB(homeCornersSelf, awayCornersOpp,   slider_HT_4_Value/ pCalc.maxSliderValue);
        resultAT[1][0] = MyMath.getValueBetweenAB(awayCornersSelf, homeCornersSOpp,  slider_AT_4_Value/ pCalc.maxSliderValue);

        System.out.println("Хозяева : " + MyMath.round(resultHT[1][0] , 2));
        System.out.println("Гости : " + MyMath.round(resultAT[1][0] , 2));
        /////////////////// ******************** Считаем статистику личных встреч (Все матчи)

        /////////////////// ******************** Считаем статистику личных встреч (Только хозяева)
        sumHT = 0;
        sumHT_Opp = 0;
        sumAT = 0;
        sumAT_Opp = 0;
        for (int i=0; i<pCalc.selectorConfHomeField_HT.listOfMatches.size(); i++){
            double average_HT_OppCornersConceded = pCalc.arrayLeaguesAll.get(i).getParameterValue(pCalc.arrayOpponents_ConfHomeField_HT[i], "Выезд", indexInTable, 1);
            sumHT += (pCalc.dataArrayThis_ConfHomeField_HT[indexOfPar][i] - average_HT_OppCornersConceded)*weightsConfHomeField.get(i);

            double average_HT_OppCornersScored = pCalc.arrayLeaguesAll.get(i).getParameterValue(pCalc.arrayOpponents_ConfHomeField_HT[i], "Выезд", indexInTable, 0);
            sumHT_Opp += (pCalc.dataArrayOpponent_ConfHomeField_HT[indexOfPar][i] - average_HT_OppCornersScored)*weightsConfHomeField.get(i);
        }

        for (int i=0; i<pCalc.selectorConfHomeField_AT.listOfMatches.size(); i++){
            double average_AT_OppCornersConceded = pCalc.arrayLeaguesAll.get(i).getParameterValue(pCalc.arrayOpponents_ConfHomeField_AT[i], "Дом", indexInTable, 1);
            sumAT += (pCalc.dataArrayThis_ConfHomeField_AT[indexOfPar][i] - average_AT_OppCornersConceded)*weightsConfHomeField.get(i);

            double average_AT_OppCornersScored = pCalc.arrayLeaguesAll.get(i).getParameterValue(pCalc.arrayOpponents_ConfHomeField_AT[i], "Дом", indexInTable, 0);
            sumAT_Opp += (pCalc.dataArrayOpponent_ConfHomeField_AT[indexOfPar][i] - average_AT_OppCornersScored)*weightsConfHomeField.get(i);
        }


        sumHT = sumHT / (double) pCalc.selectorConfHomeField_HT.listOfMatches.size();
        sumHT_Opp = sumHT_Opp / (double) pCalc.selectorConfHomeField_HT.listOfMatches.size();
        sumAT = sumAT / (double) pCalc.selectorConfHomeField_AT.listOfMatches.size();
        sumAT_Opp = sumAT_Opp / (double) pCalc.selectorConfHomeField_AT.listOfMatches.size();

        homeCornersSelf = pCalc.currentLeague.getParameterValue(Team.getShortName(awayTeam), "Выезд", indexInTable, 1) + sumHT;
        awayCornersOpp = pCalc.currentLeague.getParameterValue(Team.getShortName(homeTeam), "Дом", indexInTable, 0) + sumAT_Opp;
        awayCornersSelf = pCalc.currentLeague.getParameterValue(Team.getShortName(homeTeam), "Дом", indexInTable, 1) + sumAT;
        homeCornersSOpp = pCalc.currentLeague.getParameterValue(Team.getShortName(awayTeam), "Выезд", indexInTable, 0) + sumHT_Opp;

        System.out.println("Личные встречи:");
        System.out.println("На поле хозяев");
        System.out.println("Промежуточные результаты хозяев: " + MyMath.round(homeCornersSelf , 2) + " и " + MyMath.round(awayCornersOpp , 2));
        System.out.println("Промежуточные результаты гостей : " + MyMath.round(awayCornersSelf , 2) + " и " + MyMath.round(homeCornersSOpp , 2));

        resultHT[1][1] = MyMath.getValueBetweenAB(homeCornersSelf, awayCornersOpp,   slider_HT_4_Value/ pCalc.maxSliderValue);
        resultAT[1][1] = MyMath.getValueBetweenAB(awayCornersSelf, homeCornersSOpp,  slider_AT_4_Value/ pCalc.maxSliderValue);

        System.out.println("Хозяева : " + MyMath.round(resultHT[1][1] , 2));
        System.out.println("Гости : " + MyMath.round(resultAT[1][1] , 2));
        /////////////////// ******************** Считаем статистику личных встреч (Только хозяева)

        /////////////////// ******************** Слайдер 2 - В зависимости от его значения посчитанные ниже значения будут разными
        double headToHeadMatches_HT = MyMath.getValueBetweenAB(resultHT[1][0], resultHT[1][1], slider_HT_2_Value/ pCalc.maxSliderValue);
        double headToHeadMatches_AT = MyMath.getValueBetweenAB(resultAT[1][0], resultAT[1][1], slider_AT_2_Value/ pCalc.maxSliderValue);
        /////////////////// &&&&&&&&&&&&&&&&&&&& Слайдер 2 - В зависимости от его значения посчитанные ниже значения будут разными

        /////////////////// ******************** Слайдер 3 - В зависимости от его значения посчитанные ниже значения будут разными
        double resultHTValue = MyMath.getValueBetweenAB(currentSeason_HT, headToHeadMatches_HT, slider_HT_3_Value/ pCalc.maxSliderValue);
        double resultATValue = MyMath.getValueBetweenAB(currentSeason_AT, headToHeadMatches_AT, slider_AT_3_Value/ pCalc.maxSliderValue);
        /////////////////// &&&&&&&&&&&&&&&&&&&& Слайдер 3 - В зависимости от его значения посчитанные ниже значения будут разными

        System.out.println("Итоговый результат:");
        System.out.println(homeTeam + "  " + resultHTValue + " : " + resultATValue + "  " + awayTeam);
        System.out.println("***********************************");

        result.add(resultHTValue);
        result.add(resultATValue);

        return result;
    }

    public JPanel getResultPanel(){
        JPanel result = new JPanel(new VerticalLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 300, 0, 300));

        Font font = new Font("", Font.BOLD, 30);
        JLabel labelTop = new JLabel(homeTeam + " : " + awayTeam);
        labelTop.setFont(font);
        labelTop.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(labelTop);

        File fileHT = new File("images/" + homeTeam + ".png");
        BufferedImage bimg = null;
        try {
            bimg = ImageIO.read(fileHT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image scaled = bimg.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel teamImage = new JLabel(new ImageIcon(scaled));
        teamImage.setBorder(new EmptyBorder(5,0,0,0));
        headerPanel.add(teamImage, BorderLayout.WEST);

        File fileAT = new File("images/" + awayTeam + ".png");
        bimg = null;
        try {
            bimg = ImageIO.read(fileAT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        scaled = bimg.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        teamImage = new JLabel(new ImageIcon(scaled));
        teamImage.setBorder(new EmptyBorder(5,0,0,0));
        headerPanel.add(teamImage, BorderLayout.EAST);

        result.add(headerPanel);

        String[] colHeads = {"Показатель", "Расч. счет матча", "Расч. тотал матча", "Расч. фора хозяев"
        };
        String[] params = {"Броски в створ", "Блокированные броски", "Вбрасывания", "Силовые приемы", "Двухминутные удаления"};
        Object[][] dataForTable = new Object[params.length][colHeads.length];

        for (int i=0; i<data.length; i++){
            dataForTable[i] = new Object[]{
                    params[i],
                    data[i][0],  data[i][1],  data[i][2]
            };
        }


        JTable tableInfo = new JTable(dataForTable, colHeads);
        Font font15 = new Font("Arial", Font.BOLD, 15);
        tableInfo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableInfo.setEnabled(false);
        tableInfo.getTableHeader().setFont(font15);
        tableInfo.setFont(font15);
        tableInfo.setRowHeight(25);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i=0; i<colHeads.length; i++){
            tableInfo.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        tableInfo.setBackground(new Color(238, 238, 238));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
        tablePanel.add(tableInfo, BorderLayout.CENTER);
        tablePanel.add(tableInfo.getTableHeader(), BorderLayout.NORTH);

        result.add(tablePanel);

        return result;
    }

}
