package sample;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.tabbedui.VerticalLayout;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GraphicForOneTeam {
    String[] titles = {"Голы в основное время", "Голы с учетом ОТ и буллитов", "Реализация бросков, %","Реализация большинства", "Меньшинство", "Время в атаке (минут)",
            "Голы в первом периоде", "Голы во втором периоде", "Голы в третьем периоде", "Забитые голы в меньшинстве",
            "Броски в створ", "Броски в створ 1 пер.", "Броски в створ 2 пер.", "Броски в створ 3 пер.", "Броски мимо",
            "Заблокированные броски", "Вбрасывания", "Силовые приемы", "Минуты штрафа", "Кол-во двухминутных удалений"};
    double MAX = 0;
    double MIN = 100;
    int graphicHeight = 300;
    //double procWIDTH;
    //double procHEIGHT;
    //int graphicWidth = 1150;
    //int graphicHeight; // = 410;
    String teamName;
    Settings settings;
    String series1name;
    String series2name;
    String series3name;

    public static Dimension DEFAULT_CONTENT_SIZE;

    public GraphicForOneTeam(){
        //this.procWIDTH = procWIDTH;
        //this.procHEIGHT = procHEIGHT;
        //this.graphicHeight = graphicHeight;
        //if (procWIDTH == 1)
        //    this.graphicWidth = 1300;
    }

    public JPanel getGraphics(String teamName, String allOrHomeOrAway, String lastOrFullSeason, ArrayList<Match> listOfMatches){  //(getChart("Y(i)", "Y", "Y", 1));

        int numberOfMatchesLimit = 12;
        int diagramWidth = 290;

        this.settings = Settings.getSettingsFromFile();
        this.teamName = teamName;
        double[][] dataArrayThis = new double[titles.length][listOfMatches.size()];
        double[][] dataArrayOpponent = new double[titles.length][listOfMatches.size()];
        double[][] dataArrayTotal = new double[titles.length][listOfMatches.size()];
        String[] arrayOpponents = new String[listOfMatches.size()];
        for (int i=0; i<listOfMatches.size(); i++){
            Match m = listOfMatches.get(i);
            if (teamName.equals(m.homeTeam)){
                dataArrayThis[0][i] = m.homeScore;
                dataArrayThis[1][i] = m.homeScore + m.homeScoreOT + m.homeScoreBullits;
                dataArrayThis[2][i] = MyMath.round((double) 100*m.homeScore/m.homeShotsOnTarget, 2);
                dataArrayThis[3][i] = m.homeGoalsInPP;
                dataArrayThis[4][i] = m.awayGoalsInPP;
                dataArrayThis[5][i] = m.homeTimeInAttack/60 + m.homeTimeInAttack%60/60.0;
                dataArrayThis[6][i] = m.homeScore1stPeriod;
                dataArrayThis[7][i] = m.homeScore2ndPeriod;
                dataArrayThis[8][i] = m.homeScore3rdPeriod;
                dataArrayThis[9][i] = m.homeShorthandedGoals;
                dataArrayThis[10][i] = m.homeShotsOnTarget;
                dataArrayThis[11][i] = m.homeShotsOnTarget1stPeriod;
                dataArrayThis[12][i] = m.homeShotsOnTarget2ndPeriod;
                dataArrayThis[13][i] = m.homeShotsOnTarget3rdPeriod;
                dataArrayThis[14][i] = m.homeMissedShots;
                dataArrayThis[15][i] = m.homeBlockedShots;
                dataArrayThis[16][i] = m.homeFaceoffsWon;
                dataArrayThis[17][i] = m.homeHits;
                dataArrayThis[18][i] = m.homePenaltyMinutes;
                dataArrayThis[19][i] = m.home2MinPenalties;

                dataArrayOpponent[0][i] = m.awayScore;
                dataArrayOpponent[1][i] = m.awayScore + m.awayScoreOT + m.awayScoreBullits;
                dataArrayOpponent[2][i] = MyMath.round((double) 100*m.awayScore/m.awayShotsOnTarget, 2);
                dataArrayOpponent[3][i] = m.homeNumberOfPP;
                dataArrayOpponent[4][i] = m.awayNumberOfPP;
                dataArrayOpponent[5][i] = m.awayTimeInAttack/60 + m.awayTimeInAttack%60/60.0;
                dataArrayOpponent[6][i] = m.awayScore1stPeriod;
                dataArrayOpponent[7][i] = m.awayScore2ndPeriod;
                dataArrayOpponent[8][i] = m.awayScore3rdPeriod;
                dataArrayOpponent[9][i] = m.awayShorthandedGoals;
                dataArrayOpponent[10][i] = m.awayShotsOnTarget;
                dataArrayOpponent[11][i] = m.awayShotsOnTarget1stPeriod;
                dataArrayOpponent[12][i] = m.awayShotsOnTarget2ndPeriod;
                dataArrayOpponent[13][i] = m.awayShotsOnTarget3rdPeriod;
                dataArrayOpponent[14][i] = m.awayMissedShots;
                dataArrayOpponent[15][i] = m.awayBlockedShots;
                dataArrayOpponent[16][i] = m.awayFaceoffsWon;
                dataArrayOpponent[17][i] = m.awayHits;
                dataArrayOpponent[18][i] = m.awayPenaltyMinutes;
                dataArrayOpponent[19][i] = m.away2MinPenalties;

                dataArrayTotal[0][i] = m.homeScore + m.awayScore;
                dataArrayTotal[1][i] = m.homeScore + m.homeScoreOT + m.homeScoreBullits + m.awayScore + m.awayScoreOT + m.awayScoreBullits;
                dataArrayTotal[2][i] = 0;
                dataArrayTotal[3][i] = 0;
                dataArrayTotal[4][i] = 0;
                dataArrayTotal[5][i] = (m.homeTimeInAttack + m.awayTimeInAttack)/60 + (m.homeTimeInAttack + m.awayTimeInAttack)%60/60.0;
                dataArrayTotal[6][i] = m.homeScore1stPeriod + m.awayScore1stPeriod;
                dataArrayTotal[7][i] = m.homeScore2ndPeriod + m.awayScore2ndPeriod;
                dataArrayTotal[8][i] = m.homeScore3rdPeriod + m.awayScore3rdPeriod;
                dataArrayTotal[9][i] = m.homeShorthandedGoals + m.awayShorthandedGoals;
                dataArrayTotal[10][i] = m.homeShotsOnTarget + m.awayShotsOnTarget;
                dataArrayTotal[11][i] = m.homeShotsOnTarget1stPeriod + m.awayShotsOnTarget1stPeriod;
                dataArrayTotal[12][i] = m.homeShotsOnTarget2ndPeriod + m.awayShotsOnTarget2ndPeriod;
                dataArrayTotal[13][i] = m.homeShotsOnTarget3rdPeriod + m.awayShotsOnTarget3rdPeriod;
                dataArrayTotal[14][i] = m.homeMissedShots + m.awayMissedShots;
                dataArrayTotal[15][i] = m.homeBlockedShots + m.awayBlockedShots;
                dataArrayTotal[16][i] = m.homeFaceoffsWon + m.awayFaceoffsWon;
                dataArrayTotal[17][i] = m.homeHits + m.awayHits;
                dataArrayTotal[18][i] = m.homePenaltyMinutes + m.awayPenaltyMinutes;
                dataArrayTotal[19][i] = m.home2MinPenalties + m.away2MinPenalties;

                arrayOpponents[i] = Team.getShortName(m.awayTeam) + "(H)" + m.date.split("\\.")[0] + "." + m.date.split("\\.")[1] ;

            } else if(teamName.equals(m.awayTeam)) {
                dataArrayThis[0][i] = m.awayScore;
                dataArrayThis[1][i] = m.awayScore + m.awayScoreOT + m.awayScoreBullits;
                dataArrayThis[2][i] = MyMath.round((double) 100*m.awayScore/m.awayShotsOnTarget, 2);
                dataArrayThis[3][i] = m.awayGoalsInPP;
                dataArrayThis[4][i] = m.homeGoalsInPP;
                dataArrayThis[5][i] = m.awayTimeInAttack/60 + m.awayTimeInAttack%60/60.0;
                dataArrayThis[6][i] = m.awayScore1stPeriod;
                dataArrayThis[7][i] = m.awayScore2ndPeriod;
                dataArrayThis[8][i] = m.awayScore3rdPeriod;
                dataArrayThis[9][i] = m.awayShorthandedGoals;
                dataArrayThis[10][i] = m.awayShotsOnTarget;
                dataArrayThis[11][i] = m.awayShotsOnTarget1stPeriod;
                dataArrayThis[12][i] = m.awayShotsOnTarget2ndPeriod;
                dataArrayThis[13][i] = m.awayShotsOnTarget3rdPeriod;
                dataArrayThis[14][i] = m.awayMissedShots;
                dataArrayThis[15][i] = m.awayBlockedShots;
                dataArrayThis[16][i] = m.awayFaceoffsWon;
                dataArrayThis[17][i] = m.awayHits;
                dataArrayThis[18][i] = m.awayPenaltyMinutes;
                dataArrayThis[19][i] = m.away2MinPenalties;

                dataArrayOpponent[0][i] = m.homeScore;
                dataArrayOpponent[1][i] = m.homeScore + m.homeScoreOT + m.homeScoreBullits;
                dataArrayOpponent[2][i] = MyMath.round((double) 100*m.homeScore/m.homeShotsOnTarget, 2);
                dataArrayOpponent[3][i] = m.awayNumberOfPP;
                dataArrayOpponent[4][i] = m.homeNumberOfPP;
                dataArrayOpponent[5][i] = m.homeTimeInAttack/60 + m.homeTimeInAttack%60/60.0;
                dataArrayOpponent[6][i] = m.homeScore1stPeriod;
                dataArrayOpponent[7][i] = m.homeScore2ndPeriod;
                dataArrayOpponent[8][i] = m.homeScore3rdPeriod;
                dataArrayOpponent[9][i] = m.homeShorthandedGoals;
                dataArrayOpponent[10][i] = m.homeShotsOnTarget;
                dataArrayOpponent[11][i] = m.homeShotsOnTarget1stPeriod;
                dataArrayOpponent[12][i] = m.homeShotsOnTarget2ndPeriod;
                dataArrayOpponent[13][i] = m.homeShotsOnTarget3rdPeriod;
                dataArrayOpponent[14][i] = m.homeMissedShots;
                dataArrayOpponent[15][i] = m.homeBlockedShots;
                dataArrayOpponent[16][i] = m.homeFaceoffsWon;
                dataArrayOpponent[17][i] = m.homeHits;
                dataArrayOpponent[18][i] = m.homePenaltyMinutes;
                dataArrayOpponent[19][i] = m.home2MinPenalties;

                dataArrayTotal[0][i] = m.homeScore + m.awayScore;
                dataArrayTotal[1][i] = m.homeScore + m.homeScoreOT + m.homeScoreBullits + m.awayScore + m.awayScoreOT + m.awayScoreBullits;
                dataArrayTotal[2][i] = 0;
                dataArrayTotal[3][i] = 0;
                dataArrayTotal[4][i] = 0;
                dataArrayTotal[5][i] = (m.homeTimeInAttack + m.awayTimeInAttack)/60 + (m.homeTimeInAttack + m.awayTimeInAttack)%60/60.0;
                dataArrayTotal[6][i] = m.homeScore1stPeriod + m.awayScore1stPeriod;
                dataArrayTotal[7][i] = m.homeScore2ndPeriod + m.awayScore2ndPeriod;
                dataArrayTotal[8][i] = m.homeScore3rdPeriod + m.awayScore3rdPeriod;
                dataArrayTotal[9][i] = m.homeShorthandedGoals + m.awayShorthandedGoals;
                dataArrayTotal[10][i] = m.homeShotsOnTarget + m.awayShotsOnTarget;
                dataArrayTotal[11][i] = m.homeShotsOnTarget1stPeriod + m.awayShotsOnTarget1stPeriod;
                dataArrayTotal[12][i] = m.homeShotsOnTarget2ndPeriod + m.awayShotsOnTarget2ndPeriod;
                dataArrayTotal[13][i] = m.homeShotsOnTarget3rdPeriod + m.awayShotsOnTarget3rdPeriod;
                dataArrayTotal[14][i] = m.homeMissedShots + m.awayMissedShots;
                dataArrayTotal[15][i] = m.homeBlockedShots + m.awayBlockedShots;
                dataArrayTotal[16][i] = m.homeFaceoffsWon + m.awayFaceoffsWon;
                dataArrayTotal[17][i] = m.homeHits + m.awayHits;
                dataArrayTotal[18][i] = m.homePenaltyMinutes + m.awayPenaltyMinutes;
                dataArrayTotal[19][i] = m.home2MinPenalties + m.away2MinPenalties;

                arrayOpponents[i] = Team.getShortName(m.homeTeam) + "(A)" + m.date.split("\\.")[0] + "." + m.date.split("\\.")[1];
            }
        }
        ////////////////////////////ДО ЭТОГО МОМЕНТА ВСЕ ЗАПОЛНИЛОСЬ ХОРОШО, ТЕПЕРЬ ПОРА ДОБАВЛЯТЬ ГРАФИКИ.
        int index;
        JPanel content = new JPanel(new VerticalLayout());

        int place = 0;
        for (index=0; index<dataArrayThis.length; index++){
            MAX = 0;
            MIN = 100;
            IntervalXYDataset dataSet;
            series1name = teamName;
            series2name = "Соперник";
            series3name = "Тотал";
            if ((index==2)||(index==3)||(index==4)||(!settings.showTotal)){
                if (index==2){
                    series1name = "Реализация " + teamName;
                    series2name = "Реализация соперника";
                }
                if (index==3){
                    series1name = "Забитые голы";
                    series2name = "Кол-во большинства";
                }
                if (index==4){
                    series1name = "Пропущенные голы";
                    series2name = "Кол-во меньшинства";
                }
                dataSet = createDoubleDataSet(dataArrayThis, dataArrayOpponent, index, series1name, series2name);
            }
            else
                dataSet = createTripleDataSet(dataArrayThis, dataArrayOpponent, dataArrayTotal, index);

            final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
            renderer.setSeriesStroke(0, new BasicStroke(3f));
            renderer.setSeriesStroke(1, new BasicStroke(3f));
            if (settings.showTotal)
                renderer.setSeriesStroke(2, new BasicStroke(3f));

            XYPlot plot = new XYPlot(dataSet, new SymbolAxis("", arrayOpponents), new NumberAxis(), renderer);
            plot.setBackgroundPaint(Color.white);
            plot.setDomainGridlinePaint(Color.lightGray);
            plot.setRangeGridlinePaint(Color.lightGray);
            plot.setRangeCrosshairVisible(true);
            XYItemRenderer plotRenderer = plot.getRenderer();
            plotRenderer.setSeriesPaint(2, new Color(0, 228, 46));

            if (listOfMatches.size() > numberOfMatchesLimit){
                NumberAxis domain = (NumberAxis) plot.getDomainAxis();
                domain.setVerticalTickLabels(true);
            }

            plot.setBackgroundPaint(Color.white);

            ValueAxis rangeAxis = plot.getRangeAxis();         //getDomainAxis();
            rangeAxis.setRange(MIN/1.1-0.2, MAX*1.1+0.2);
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

            String lastOrFull = "Последние " + listOfMatches.size() + " игр";
            if (lastOrFullSeason.contains("Весь сезон"))
                lastOrFull = "Все игры";
            if (allOrHomeOrAway.equals("Все матчи")) lastOrFull = " (" + lastOrFull + ")";
            if (allOrHomeOrAway.equals("Дома")) lastOrFull = " (" + lastOrFull + " дома)";
            if (allOrHomeOrAway.equals("На выезде")) lastOrFull = " (" + lastOrFull + " на выезде)";
            JFreeChart chart = new JFreeChart(teamName+ ": " + titles[index] + lastOrFull, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
            chart.setBackgroundPaint(Color.white);

            ChartPanel panel = new ChartPanel(chart);
            panel.setPreferredSize(new Dimension(diagramWidth, graphicHeight));
            panel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
            //panel.setLocation(0, (int) (place*(graphicHeight+5)*procHEIGHT));
            if (settings.getShowList().get(index)){
                content.add(panel);
                place ++;
            }
        }
        return content;
    }

    public JPanel getGraphicsForShotsOnTarget(String teamName, String allOrHomeOrAway, String lastOrFullSeason, ArrayList<Match> listOfMatches, ArrayList<Selector> opponentsList){
        int numberOfMatchesLimit = 12;

        this.settings = Settings.getSettingsFromFile();
        this.teamName = teamName;
        titles = new String[]{"Время в атаке (минут)", "Броски в створ", "Броски в створ и ср. кол-во полученных соперником бросков",
                "Броски противника и ср. кол-во нанесенных бросков противника.", "Броски в створ 1 пер.", "Броски в створ 2 пер.", "Броски в створ 3 пер."};
        double[][] dataArrayThis = new double[titles.length][listOfMatches.size()];
        double[][] dataArrayOpponent = new double[titles.length][listOfMatches.size()];
        double[][] dataArrayTotal = new double[titles.length][listOfMatches.size()];
        String[] arrayOpponents = new String[listOfMatches.size()];
        for (int i=0; i<listOfMatches.size(); i++){
            Match m = listOfMatches.get(i);
            if (teamName.equals(m.homeTeam)){
                dataArrayThis[0][i] = m.homeTimeInAttack/60 + m.homeTimeInAttack%60/60.0;
                dataArrayThis[1][i] = m.homeShotsOnTarget;
                dataArrayThis[2][i] = m.homeShotsOnTarget;
                dataArrayThis[3][i] = m.awayShotsOnTarget;
                dataArrayThis[4][i] = m.homeShotsOnTarget1stPeriod;
                dataArrayThis[5][i] = m.homeShotsOnTarget2ndPeriod;
                dataArrayThis[6][i] = m.homeShotsOnTarget3rdPeriod;

                dataArrayOpponent[0][i] = m.awayTimeInAttack/60 + m.awayTimeInAttack%60/60.0;
                dataArrayOpponent[1][i] = m.awayShotsOnTarget;
                if (opponentsList != null){
                    dataArrayOpponent[2][i] = Double.parseDouble(opponentsList.get(i).pList.get(7).get(2));
                    dataArrayOpponent[3][i] = Double.parseDouble(opponentsList.get(i).pList.get(7).get(1));
                }
                dataArrayOpponent[4][i] = m.awayShotsOnTarget1stPeriod;
                dataArrayOpponent[5][i] = m.awayShotsOnTarget2ndPeriod;
                dataArrayOpponent[6][i] = m.awayShotsOnTarget3rdPeriod;

                dataArrayTotal[0][i] = 0;
                dataArrayTotal[1][i] = m.homeShotsOnTarget + m.awayShotsOnTarget;
                dataArrayTotal[2][i] = m.homeShotsOnTarget + m.awayShotsOnTarget;
                dataArrayTotal[3][i] = m.homeShotsOnTarget + m.awayShotsOnTarget;
                dataArrayTotal[4][i] = m.homeShotsOnTarget1stPeriod + m.awayShotsOnTarget1stPeriod;
                dataArrayTotal[5][i] = m.homeShotsOnTarget2ndPeriod + m.awayShotsOnTarget2ndPeriod;
                dataArrayTotal[6][i] = m.homeShotsOnTarget3rdPeriod + m.awayShotsOnTarget3rdPeriod;

                arrayOpponents[i] = Team.getShortName(m.awayTeam) + "(H)" + m.date.split("\\.")[0] + "." + m.date.split("\\.")[1];

            } else if(teamName.equals(m.awayTeam)) {
                dataArrayThis[0][i] = m.awayTimeInAttack/60 + m.awayTimeInAttack%60/60.0;
                dataArrayThis[1][i] = m.awayShotsOnTarget;
                dataArrayThis[2][i] = m.awayShotsOnTarget;
                dataArrayThis[3][i] = m.homeShotsOnTarget;
                dataArrayThis[4][i] = m.awayShotsOnTarget1stPeriod;
                dataArrayThis[5][i] = m.awayShotsOnTarget2ndPeriod;
                dataArrayThis[6][i] = m.awayShotsOnTarget3rdPeriod;

                dataArrayOpponent[0][i] = m.homeTimeInAttack/60 + m.homeTimeInAttack%60/60.0;
                dataArrayOpponent[1][i] = m.homeShotsOnTarget;
                if (opponentsList != null){
                    dataArrayOpponent[2][i] = Double.parseDouble(opponentsList.get(i).pList.get(7).get(2));
                    dataArrayOpponent[3][i] = Double.parseDouble(opponentsList.get(i).pList.get(7).get(1));
                }
                dataArrayOpponent[4][i] = m.homeShotsOnTarget1stPeriod;
                dataArrayOpponent[5][i] = m.homeShotsOnTarget2ndPeriod;
                dataArrayOpponent[6][i] = m.homeShotsOnTarget3rdPeriod;

                dataArrayTotal[0][i] = 0;
                dataArrayTotal[1][i] = m.homeShotsOnTarget + m.awayShotsOnTarget;
                dataArrayTotal[2][i] = m.homeShotsOnTarget + m.awayShotsOnTarget;
                dataArrayTotal[3][i] = m.homeShotsOnTarget + m.awayShotsOnTarget;
                dataArrayTotal[4][i] = m.homeShotsOnTarget1stPeriod + m.awayShotsOnTarget1stPeriod;
                dataArrayTotal[5][i] = m.homeShotsOnTarget2ndPeriod + m.awayShotsOnTarget2ndPeriod;
                dataArrayTotal[6][i] = m.homeShotsOnTarget3rdPeriod + m.awayShotsOnTarget3rdPeriod;

                arrayOpponents[i] = Team.getShortName(m.homeTeam) + "(A)" + m.date.split("\\.")[0] + "." + m.date.split("\\.")[1];
            }
        }
        ////////////////////////////ДО ЭТОГО МОМЕНТА ВСЕ ЗАПОЛНИЛОСЬ ХОРОШО, ТЕПЕРЬ ПОРА ДОБАВЛЯТЬ ГРАФИКИ.
        int index;
        JPanel content = new JPanel(new VerticalLayout());

        int place = 0;
        for (index=0; index<dataArrayThis.length; index++){
            MAX = 0;
            MIN = 100;
            IntervalXYDataset dataSet;
            series1name = teamName;
            series2name = "Соперник";
            series3name = "Тотал";
            if (!settings.showTotal || index==0 || index==2 || index==3){
                if (index==2){
                    series1name = "Броски " + teamName;
                    series2name = "Среднее кол-во полученных соперником бросков";
                }
                if (index==3){
                    series1name =  "Броски соперника";
                    series2name = "Среднее кол-во нанесенных противником бросков";
                }
                dataSet = createDoubleDataSet(dataArrayThis, dataArrayOpponent, index, series1name, series2name);
            }
            else
                dataSet = createTripleDataSet(dataArrayThis, dataArrayOpponent, dataArrayTotal, index);

            final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
            renderer.setSeriesStroke(0, new BasicStroke(3f));
            renderer.setSeriesStroke(1, new BasicStroke(3f));
            if (settings.showTotal && index!=0)
                renderer.setSeriesStroke(2, new BasicStroke(3f));

            XYPlot plot = new XYPlot(dataSet, new SymbolAxis("", arrayOpponents), new NumberAxis(), renderer);
            plot.setBackgroundPaint(Color.white);
            plot.setDomainGridlinePaint(Color.lightGray);
            plot.setRangeGridlinePaint(Color.lightGray);
            plot.setRangeCrosshairVisible(true);
            XYItemRenderer plotRenderer = plot.getRenderer();
            plotRenderer.setSeriesPaint(2, new Color(0, 228, 46));

            if (listOfMatches.size() > numberOfMatchesLimit){
                NumberAxis domain = (NumberAxis) plot.getDomainAxis();
                domain.setVerticalTickLabels(true);
            }

            plot.setBackgroundPaint(Color.white);

            ValueAxis rangeAxis = plot.getRangeAxis();         //getDomainAxis();
            rangeAxis.setRange(MIN/1.1-0.2, MAX*1.1+0.2);
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

            String lastOrFull = "Последние " + listOfMatches.size() + " игр";
            if (lastOrFullSeason.contains("Весь сезон"))
                lastOrFull = "Все игры";
            if (allOrHomeOrAway.equals("Все матчи")) lastOrFull = " (" + lastOrFull + ")";
            if (allOrHomeOrAway.equals("Дома")) lastOrFull = " (" + lastOrFull + " дома)";
            if (allOrHomeOrAway.equals("На выезде")) lastOrFull = " (" + lastOrFull + " на выезде)";
            JFreeChart chart = new JFreeChart(teamName+ ": " + titles[index] + lastOrFull, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
            chart.setBackgroundPaint(Color.white);
            ChartPanel panel = new ChartPanel(chart);
            panel.setPreferredSize(new Dimension(963, graphicHeight));
            panel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
            //panel.setLocation(0, (int) (place*(graphicHeight+5)*procHEIGHT));
            switch (index){
                case 0:{
                    if (dataArrayThis[index][0] > 0)
                        content.add(panel);
                    break;
                }
                case 2:{
                    if (opponentsList != null)
                        content.add(panel);
                    break;
                }
                case 3:{
                    if (opponentsList != null)
                        content.add(panel);
                    break;
                }
                default:{
                    content.add(panel);
                    break;
                }
            }
            place ++;
        }
        return content;
    }

    public JPanel getGraphicsForPenalties(String teamName, String allOrHomeOrAway, ArrayList<Match> listOfMatches, ArrayList<Selector> opponentsList){
        int numberOfMatchesLimit = 12;

        this.settings = Settings.getSettingsFromFile();
        this.teamName = teamName;

        titles = new String[]{"Время в атаке (минут)", "Штр. время", "Штр.время и ср. кол-во штрафа оппонентов противника",
                "Штр.время противника и ср. штр.время противника.",
                "Штр. время 1 пер.", "Штр. время 2 пер.", "Штр. время 3 пер.",
                "Кол-во двухмин. уд.", "Кол-во двухмин. уд. и ср. кол-во 2мин.уд. оппонентов противника",
                "Кол-во двухмин. уд. противника и ср. кол-во 2мин.уд. противника.", "Кол-во двухмин. уд. 1 пер.",
                "Кол-во двухмин. уд. 2 пер.", "Кол-во двухмин. уд. 3 пер"};
        double[][] dataArrayThis = new double[titles.length][listOfMatches.size()];
        double[][] dataArrayOpponent = new double[titles.length][listOfMatches.size()];
        double[][] dataArrayTotal = new double[titles.length][listOfMatches.size()];
        String[] arrayOpponents = new String[listOfMatches.size()];
        for (int i=0; i<listOfMatches.size(); i++){
            Match m = listOfMatches.get(i);
            if (teamName.equals(m.homeTeam)){
                dataArrayThis[0][i] = m.homeTimeInAttack/60 + m.homeTimeInAttack%60/60.0;
                dataArrayThis[1][i] = m.homePenaltyMinutes;
                dataArrayThis[2][i] = m.homePenaltyMinutes;
                dataArrayThis[3][i] = m.awayPenaltyMinutes;
                dataArrayThis[4][i] = m.homePenaltyMinutes1stPeriod;
                dataArrayThis[5][i] = m.homePenaltyMinutes2ndPeriod;
                dataArrayThis[6][i] = m.homePenaltyMinutes3rdPeriod;
                dataArrayThis[7][i] = m.home2MinPenalties;
                dataArrayThis[8][i] = m.home2MinPenalties;
                dataArrayThis[9][i] = m.away2MinPenalties;
                dataArrayThis[10][i] = m.home2MinPenalties1stPeriod;
                dataArrayThis[11][i] = m.home2MinPenalties2ndPeriod;
                dataArrayThis[12][i] = m.home2MinPenalties3rdPeriod;

                dataArrayOpponent[0][i] = m.awayTimeInAttack/60 + m.awayTimeInAttack%60/60.0;
                dataArrayOpponent[1][i] = m.awayPenaltyMinutes;
                if (opponentsList != null){
                    dataArrayOpponent[2][i] = MyMath.round(Double.parseDouble(opponentsList.get(i).pList.get(14).get(2)) / opponentsList.get(i).listOfMatches.size(), 2);
                    dataArrayOpponent[3][i] = MyMath.round(Double.parseDouble(opponentsList.get(i).pList.get(14).get(1)) / opponentsList.get(i).listOfMatches.size(), 2);
                }
                dataArrayOpponent[4][i] = m.awayPenaltyMinutes1stPeriod;
                dataArrayOpponent[5][i] = m.awayPenaltyMinutes2ndPeriod;
                dataArrayOpponent[6][i] = m.awayPenaltyMinutes3rdPeriod;
                dataArrayOpponent[7][i] = m.away2MinPenalties;
                if (opponentsList != null){
                    dataArrayOpponent[8][i] = MyMath.round(Double.parseDouble(opponentsList.get(i).pList.get(15).get(2)) / opponentsList.get(i).listOfMatches.size(), 2);
                    dataArrayOpponent[9][i] = MyMath.round(Double.parseDouble(opponentsList.get(i).pList.get(15).get(1)) / opponentsList.get(i).listOfMatches.size(), 2);
                }
                dataArrayOpponent[10][i] = m.away2MinPenalties1stPeriod;
                dataArrayOpponent[11][i] = m.away2MinPenalties2ndPeriod;
                dataArrayOpponent[12][i] = m.away2MinPenalties3rdPeriod;



                dataArrayTotal[0][i] = 0;
                dataArrayTotal[1][i] = m.homePenaltyMinutes + m.awayPenaltyMinutes;
                dataArrayTotal[2][i] = 0;
                dataArrayTotal[3][i] = 0;
                dataArrayTotal[4][i] = m.homePenaltyMinutes1stPeriod + m.awayPenaltyMinutes1stPeriod;
                dataArrayTotal[5][i] = m.homePenaltyMinutes2ndPeriod + m.awayPenaltyMinutes2ndPeriod;
                dataArrayTotal[6][i] = m.homePenaltyMinutes3rdPeriod + m.awayPenaltyMinutes3rdPeriod;
                dataArrayTotal[7][i] = m.home2MinPenalties + m.away2MinPenalties;
                dataArrayTotal[8][i] = 0;
                dataArrayTotal[9][i] = 0;
                dataArrayTotal[10][i] = m.home2MinPenalties1stPeriod + m.away2MinPenalties1stPeriod;
                dataArrayTotal[11][i] = m.home2MinPenalties2ndPeriod + m.away2MinPenalties2ndPeriod;
                dataArrayTotal[12][i] = m.home2MinPenalties3rdPeriod + m.away2MinPenalties3rdPeriod;

                arrayOpponents[i] = Team.getShortName(m.awayTeam) + "(H)" + m.date.split("\\.")[0] + "." + m.date.split("\\.")[1];

            } else if(teamName.equals(m.awayTeam)) {
                dataArrayThis[0][i] = m.awayTimeInAttack/60 + m.awayTimeInAttack%60/60.0;
                dataArrayThis[1][i] = m.awayPenaltyMinutes;
                dataArrayThis[2][i] = m.awayPenaltyMinutes;
                dataArrayThis[3][i] = m.homePenaltyMinutes;
                dataArrayThis[4][i] = m.awayPenaltyMinutes1stPeriod;
                dataArrayThis[5][i] = m.awayPenaltyMinutes2ndPeriod;
                dataArrayThis[6][i] = m.awayPenaltyMinutes3rdPeriod;
                dataArrayThis[7][i] = m.away2MinPenalties;
                dataArrayThis[8][i] = m.away2MinPenalties;
                dataArrayThis[9][i] = m.home2MinPenalties;
                dataArrayThis[10][i] = m.away2MinPenalties1stPeriod;
                dataArrayThis[11][i] = m.away2MinPenalties2ndPeriod;
                dataArrayThis[12][i] = m.away2MinPenalties3rdPeriod;

                dataArrayOpponent[0][i] = m.homeTimeInAttack/60 + m.homeTimeInAttack%60/60.0;
                dataArrayOpponent[1][i] = m.homePenaltyMinutes;
                if (opponentsList != null){
                    dataArrayOpponent[2][i] = MyMath.round(Double.parseDouble(opponentsList.get(i).pList.get(14).get(2)) / opponentsList.get(i).listOfMatches.size(), 2);
                    dataArrayOpponent[3][i] = MyMath.round(Double.parseDouble(opponentsList.get(i).pList.get(14).get(1)) / opponentsList.get(i).listOfMatches.size(), 2);
                }
                dataArrayOpponent[4][i] = m.homePenaltyMinutes1stPeriod;
                dataArrayOpponent[5][i] = m.homePenaltyMinutes2ndPeriod;
                dataArrayOpponent[6][i] = m.homePenaltyMinutes3rdPeriod;
                dataArrayOpponent[7][i] = m.home2MinPenalties;
                if (opponentsList != null){
                    dataArrayOpponent[8][i] = MyMath.round(Double.parseDouble(opponentsList.get(i).pList.get(15).get(2)) / opponentsList.get(i).listOfMatches.size(), 2);
                    dataArrayOpponent[9][i] = MyMath.round(Double.parseDouble(opponentsList.get(i).pList.get(15).get(1)) / opponentsList.get(i).listOfMatches.size(), 2);
                }
                dataArrayOpponent[10][i] = m.home2MinPenalties1stPeriod;
                dataArrayOpponent[11][i] = m.home2MinPenalties2ndPeriod;
                dataArrayOpponent[12][i] = m.home2MinPenalties3rdPeriod;



                dataArrayTotal[0][i] = 0;
                dataArrayTotal[1][i] = m.homePenaltyMinutes + m.awayPenaltyMinutes;
                dataArrayTotal[2][i] = 0;
                dataArrayTotal[3][i] = 0;
                dataArrayTotal[4][i] = m.homePenaltyMinutes1stPeriod + m.awayPenaltyMinutes1stPeriod;
                dataArrayTotal[5][i] = m.homePenaltyMinutes2ndPeriod + m.awayPenaltyMinutes2ndPeriod;
                dataArrayTotal[6][i] = m.homePenaltyMinutes3rdPeriod + m.awayPenaltyMinutes3rdPeriod;
                dataArrayTotal[7][i] = m.home2MinPenalties + m.away2MinPenalties;
                dataArrayTotal[8][i] = 0;
                dataArrayTotal[9][i] = 0;
                dataArrayTotal[10][i] = m.home2MinPenalties1stPeriod + m.away2MinPenalties1stPeriod;
                dataArrayTotal[11][i] = m.home2MinPenalties2ndPeriod + m.away2MinPenalties2ndPeriod;
                dataArrayTotal[12][i] = m.home2MinPenalties3rdPeriod + m.away2MinPenalties3rdPeriod;

                arrayOpponents[i] = Team.getShortName(m.homeTeam) + "(A)" + m.date.split("\\.")[0] + "." + m.date.split("\\.")[1];
            }
        }
        ////////////////////////////ДО ЭТОГО МОМЕНТА ВСЕ ЗАПОЛНИЛОСЬ ХОРОШО, ТЕПЕРЬ ПОРА ДОБАВЛЯТЬ ГРАФИКИ.
        int index;
        JPanel content = new JPanel(new VerticalLayout());

        int place = 0;
        for (index=0; index<dataArrayThis.length; index++){
            MAX = 0;
            MIN = 100;
            IntervalXYDataset dataSet;
            series1name = teamName;
            series2name = "Соперник";
            series3name = "Тотал";
            if (!settings.showTotal || index==0 || index==2 || index==3 || index==8 || index==9){
                if (index==2){
                    series1name = "Штр.время " + teamName;
                    series2name = "Среднее кол-во штр.врем. оппонентов противника";
                }
                if (index==3){
                    series1name =  "Штр.время соперника";
                    series2name = "Среднее кол-во штр.врем. противника";
                }
                if (index==8){
                    series1name = "Кол-во 2мин.уд. " + teamName;
                    series2name = "Среднее кол-во 2мин.уд. оппонентов противника";
                }
                if (index==9){
                    series1name = "Кол-во 2мин.уд. соперника";
                    series2name = "Среднее кол-во 2мин.уд. противника";
                }
                dataSet = createDoubleDataSet(dataArrayThis, dataArrayOpponent, index, series1name, series2name);
            }
            else
                dataSet = createTripleDataSet(dataArrayThis, dataArrayOpponent, dataArrayTotal, index);

            final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
            renderer.setSeriesStroke(0, new BasicStroke(3f));
            renderer.setSeriesStroke(1, new BasicStroke(3f));
            if (settings.showTotal)
                renderer.setSeriesStroke(2, new BasicStroke(3f));

            XYPlot plot = new XYPlot(dataSet, new SymbolAxis("", arrayOpponents), new NumberAxis(), renderer);
            plot.setBackgroundPaint(Color.white);
            plot.setDomainGridlinePaint(Color.lightGray);
            plot.setRangeGridlinePaint(Color.lightGray);
            plot.setRangeCrosshairVisible(true);
            XYItemRenderer plotRenderer = plot.getRenderer();
            plotRenderer.setSeriesPaint(2, new Color(0, 228, 46));

            if (listOfMatches.size() > numberOfMatchesLimit){
                NumberAxis domain = (NumberAxis) plot.getDomainAxis();
                domain.setVerticalTickLabels(true);
            }

            plot.setBackgroundPaint(Color.white);

            ValueAxis rangeAxis = plot.getRangeAxis();         //getDomainAxis();
            rangeAxis.setRange(MIN/1.1-0.2, MAX*1.1+0.2);
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

            String lastOrFull = "Последние " + listOfMatches.size() + " игр";
            if (lastOrFull.contains("Весь сезон"))
                lastOrFull = "Все игры";
            if (allOrHomeOrAway.equals("Все матчи")) lastOrFull = " (" + lastOrFull + ")";
            if (allOrHomeOrAway.equals("Дома")) lastOrFull = " (" + lastOrFull + " дома)";
            if (allOrHomeOrAway.equals("На выезде")) lastOrFull = " (" + lastOrFull + " на выезде)";
            JFreeChart chart = new JFreeChart(teamName+ ": " + titles[index] + lastOrFull, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
            chart.setBackgroundPaint(Color.white);
            ChartPanel panel = new ChartPanel(chart);
            panel.setPreferredSize(new Dimension(963, graphicHeight));
            panel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
//            panel.setLocation(0, (int) (place*(graphicHeight+5)*procHEIGHT));
            switch (index){
                case 0:{
                    if (dataArrayThis[index][0] > 0)
                        content.add(panel);
                    break;
                }
                case 2:{
                    if (opponentsList != null)
                        content.add(panel);
                    break;
                }
                case 3:{
                    if (opponentsList != null)
                        content.add(panel);
                    break;
                }
                case 8:{
                    if (opponentsList != null)
                        content.add(panel);
                    break;
                }
                case 9:{
                    if (opponentsList != null)
                        content.add(panel);
                    break;
                }
                default:{
                    content.add(panel);
                    break;
                }
            }
            place ++;
        }
        return content;
    }

    public IntervalXYDataset createDoubleDataSet(double[][] dataArrayThis, double[][] dataArrayOp, int index, String series1name, String series2name) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries(series1name);
        for (int i = 0 ; i < dataArrayThis[0].length ; i++){
            series1.add(i, dataArrayThis[index][i]);
            if (dataArrayThis[index][i] > MAX){
                MAX = dataArrayThis[index][i];
            }
            if (dataArrayThis[index][i] < MIN){
                MIN = dataArrayThis[index][i];
            }
        }
        dataset.addSeries(series1);
        XYSeries series2 = new XYSeries(series2name);
        for (int i = 0 ; i < dataArrayOp[0].length ; i++){
            series2.add(i, dataArrayOp[index][i]);
            if (dataArrayOp[index][i] > MAX){
                MAX = dataArrayOp[index][i];
            }
            if (dataArrayOp[index][i] < MIN){
                MIN = dataArrayOp[index][i];
            }
        }
        dataset.addSeries(series2);
        return dataset;
    }

    public IntervalXYDataset createTripleDataSet(double[][] dataArrayThis, double[][] dataArrayOp, double[][] dataArrayTotal, int index) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries(teamName);
        for (int i = 0 ; i < dataArrayThis[0].length ; i++){
            series1.add(i, dataArrayThis[index][i]);
            if (dataArrayThis[index][i] > MAX){
                MAX = dataArrayThis[index][i];
            }
            if (dataArrayThis[index][i] < MIN){
                MIN = dataArrayThis[index][i];
            }
        }
        dataset.addSeries(series1);
        XYSeries series2 = new XYSeries("Соперник");
        for (int i = 0 ; i < dataArrayOp[0].length ; i++){
            series2.add(i, dataArrayOp[index][i]);
            if (dataArrayOp[index][i] > MAX){
                MAX = dataArrayOp[index][i];
            }
            if (dataArrayOp[index][i] < MIN){
                MIN = dataArrayOp[index][i];
            }
        }
        dataset.addSeries(series2);

        XYSeries series3 = new XYSeries("Тотал");
        for (int i = 0 ; i < dataArrayTotal[0].length ; i++){
            series3.add(i, dataArrayTotal[index][i]);
            if (dataArrayTotal[index][i] > MAX){
                MAX = dataArrayTotal[index][i];
            }
            if (dataArrayOp[index][i] < MIN){
                MIN = dataArrayTotal[index][i];
            }
        }
        dataset.addSeries(series3);
        return dataset;
    }

    public IntervalXYDataset createDataSet(double[][] dataArray, int index) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries(titles[index]);
        for (int i = 0 ; i < dataArray[0].length ; i++){
            series1.add(i, dataArray[index][i]);
            if (dataArray[index][i] > MAX){
                MAX = dataArray[index][i];
            }
            if (dataArray[index][i] < MIN){
                MIN = dataArray[index][i];
            }
        }
        dataset.addSeries(series1);
        XYSeries series2 = new XYSeries(titles[index+1]);
        for (int i = 0 ; i < dataArray[0].length ; i++){
            series2.add(i, dataArray[index+1][i]);
            if (dataArray[index+1][i] > MAX){
                MAX = dataArray[index+1][i];
            }
            if (dataArray[index+1][i] < MIN){
                MIN = dataArray[index+1][i];
            }
        }
        dataset.addSeries(series2);
        return dataset;
    }
}