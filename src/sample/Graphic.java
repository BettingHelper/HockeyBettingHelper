package sample;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.tabbedui.VerticalLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class Graphic {
    String[] titles = {"Голы в основное время", "Голы с учетом ОТ и буллитов", "Реализация бросков, %","Реализация большинства", "Меньшинство", "Время в атаке (минут)",
            "Голы в первом периоде", "Голы во втором периоде", "Голы в третьем периоде", "Забитые голы в меньшинстве",
            "Броски в створ", "Броски в створ 1 пер.", "Броски в створ 2 пер.", "Броски в створ 3 пер.", "Броски мимо",
            "Заблокированные броски", "Вбрасывания", "Силовые приемы", "Минуты штрафа", "Кол-во двухминутных удалений"};
    double MAX = 0;
    double MIN = 100;
    int graphicHeight = 300;
    int status;
    String teamName;
    String shortTeamName;
    Settings settings;
    String series1name;
    String series2name;
    String series3name;
    ArrayList<String> graphicTitles;
    ArrayList<Integer> heights = new ArrayList<>();

    public Graphic(int status, String teamName){
        this.status = status;
        this.teamName = teamName;
        this.shortTeamName = Team.getShortName(this.teamName);
        graphicTitles = new ArrayList<>();

    }

    public JPanel getGraphics(String teamName, String allOrHomeOrAway, String lastOrFullSeason, Selector selector, ArrayList<String> tournamentTable){  //(getChart("Y(i)", "Y", "Y", 1));
        int numberOfMatchesLimit = 8;
        //int diagramWidth = 290;
        ArrayList<Match> listOfMatches = selector.listOfMatches;
        this.settings = Settings.getSettingsFromFile();
        double[][] dataArrayThis = new double[titles.length][listOfMatches.size()];
        double[][] dataArrayOpponent = new double[titles.length][listOfMatches.size()];
        double[][] dataArrayTotal = new double[titles.length][listOfMatches.size()];
        String[] arrayOpponents = new String[listOfMatches.size()];
        selector.getArraysWithStats(teamName, dataArrayThis, dataArrayOpponent, dataArrayTotal, arrayOpponents, tournamentTable);
        int index;
        int numberOfGraphics = 0;
        JPanel result = new JPanel(new GridLayout(0, 1, 0, 0));


        ////////////////////////////ДО ЭТОГО МОМЕНТА ВСЕ ЗАПОЛНИЛОСЬ ХОРОШО, ТЕПЕРЬ ПОРА ДОБАВЛЯТЬ ГРАФИКИ.
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

            ValueAxis rangeAxis = plot.getRangeAxis();         //getDomainAxis();
            rangeAxis.setRange(MIN/1.05-0.2, MAX*1.05+0.2);
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

            String lastOrFull = "Последние " + listOfMatches.size() + " игр";
            if (lastOrFullSeason != null){
                if (lastOrFullSeason.contains("Весь сезон"))
                    lastOrFull = "Все игры";
            }  else {
                lastOrFull = "Все игры";
            }
            if (allOrHomeOrAway.equals("Все матчи")) lastOrFull = " (" + lastOrFull + ")";
            if (allOrHomeOrAway.equals("Дома")) lastOrFull = " (" + lastOrFull + " дома)";
            if (allOrHomeOrAway.equals("На выезде")) lastOrFull = " (" + lastOrFull + " на выезде)";
            String title = teamName+ ": " + titles[index] + lastOrFull;
            JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
            chart.setBackgroundPaint(Color.white);
            JPanel panelWithGraphicAndTable = new JPanel(new BorderLayout());
            ChartPanel chartPanel = new ChartPanel(chart);
            panelWithGraphicAndTable.add(chartPanel);
            chartPanel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
            //panel.setPreferredSize(new Dimension(diagramWidth, graphicHeight));
            //panel.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
            if (settings.getShowList().get(index)){
                result.add(panelWithGraphicAndTable);
                graphicTitles.add(title);
                heights.add(panelWithGraphicAndTable.getHeight());
                numberOfGraphics++;
            }
        }

        if (status == 1){
            addTablesToGraphics(result, teamName, selector);
        }

        result.setPreferredSize(new Dimension(300, numberOfGraphics*graphicHeight));
        return result;
    }

    public JPanel getGraphicsForAdvancedStats(String teamName, String allOrHomeOrAway, String lastOrFullSeason, Selector selector, ArrayList<String> tournamentTable){  //(getChart("Y(i)", "Y", "Y", 1));
        int diagramWidth = 290;
        ArrayList<ArrayList<Double>> listOfAdvancedStats = Selector.getAdvancedStatsList(teamName, selector.listOfMatches);
        titles = new String[]{"Corsi", "CorsiFor, %", "Fenwick", "FenwickFor, %", "PDO"};
        int numberOfMatchesLimit = 8;

        this.settings = Settings.getSettingsFromFile();
        this.teamName = teamName;
        double[][] dataArrayThis = new double[titles.length][listOfAdvancedStats.size()];
        double[][] dataArrayOpponent = new double[titles.length][listOfAdvancedStats.size()];
        String[] arrayOpponents = new String[listOfAdvancedStats.size()];
        selector.getArraysForAdvancedStats(teamName, /*dataArrayThis, dataArrayOpponent,*/ arrayOpponents, tournamentTable);
        for (int i=0; i<listOfAdvancedStats.size(); i++){
            dataArrayThis[0][i] = listOfAdvancedStats.get(i).get(0);
            dataArrayThis[1][i] = listOfAdvancedStats.get(i).get(2);
            dataArrayThis[2][i] = listOfAdvancedStats.get(i).get(4);
            dataArrayThis[3][i] = listOfAdvancedStats.get(i).get(6);
            dataArrayThis[4][i] = listOfAdvancedStats.get(i).get(8);

            dataArrayOpponent[0][i] = listOfAdvancedStats.get(i).get(1);
            dataArrayOpponent[1][i] = listOfAdvancedStats.get(i).get(3);
            dataArrayOpponent[2][i] = listOfAdvancedStats.get(i).get(5);
            dataArrayOpponent[3][i] = listOfAdvancedStats.get(i).get(7);
            dataArrayOpponent[4][i] = listOfAdvancedStats.get(i).get(9);

            if (teamName.equals(selector.listOfMatches.get(i).homeTeam))
                arrayOpponents[i] = Team.getShortName(selector.listOfMatches.get(i).awayTeam) + "(H)" + selector.listOfMatches.get(i).date.split("\\.")[0] + "." + selector.listOfMatches.get(i).date.split("\\.")[1];
            else
                arrayOpponents[i] = Team.getShortName(selector.listOfMatches.get(i).homeTeam) + "(A)" + selector.listOfMatches.get(i).date.split("\\.")[0] + "." + selector.listOfMatches.get(i).date.split("\\.")[1];
        }
        ////////////////////////////ДО ЭТОГО МОМЕНТА ВСЕ ЗАПОЛНИЛОСЬ ХОРОШО, ТЕПЕРЬ ПОРА ДОБАВЛЯТЬ ГРАФИКИ.
        int index;
        JPanel content = new JPanel(new VerticalLayout());

        for (index=0; index<dataArrayThis.length; index++){
            MAX = 0;
            MIN = 100;
            IntervalXYDataset dataSet;
            series1name = teamName;
            series2name = "Соперник";
            series3name = "Тотал";
            switch (index){
                case 0:{
                    series1name = teamName;
                    series2name = "Соперник";
                    break;
                }
                case 1:{
                    series1name = teamName + ": CorsiFor, %";
                    series2name = "Скользящее среднее CorsiFor";
                    break;
                }
                case 2:{
                    series1name = teamName;
                    series2name = "Соперник";
                    break;
                }
                case 3:{
                    series1name = teamName + ": FenwickFor, %";
                    series2name = "Скользящее среднее FenwickFor";
                    break;
                }
                case 4:{
                    series1name = teamName + ": PDO";
                    series2name = "Скользящее среднее PDO";
                    break;
                }
            }
            dataSet = createDoubleDataSet(dataArrayThis, dataArrayOpponent, index, series1name, series2name);
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

            if (selector.listOfMatches.size() > numberOfMatchesLimit){
                NumberAxis domain = (NumberAxis) plot.getDomainAxis();
                domain.setVerticalTickLabels(true);
            }

            ValueAxis rangeAxis = plot.getRangeAxis();         //getDomainAxis();
            rangeAxis.setRange(MIN/1.05-0.2, MAX*1.05+0.2);
            if (index==4)
                rangeAxis.setRange(MIN-0.2, MAX+0.2);
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

            String lastOrFull = "Последние " + selector.listOfMatches.size() + " игр";
            if (lastOrFullSeason.contains("Весь сезон"))
                lastOrFull = "Все игры";
            if (allOrHomeOrAway.equals("Все матчи")) lastOrFull = " (" + lastOrFull + ")";
            if (allOrHomeOrAway.equals("Дома")) lastOrFull = " (" + lastOrFull + " дома)";
            if (allOrHomeOrAway.equals("На выезде")) lastOrFull = " (" + lastOrFull + " на выезде)";
            String title = titles[index] + lastOrFull;
            JFreeChart chart = new JFreeChart(teamName+ ": " + title, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
            graphicTitles.add(title);
            chart.setBackgroundPaint(Color.white);
            ChartPanel panel = new ChartPanel(chart);
            panel.setPreferredSize(new Dimension(diagramWidth, graphicHeight));
            panel.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
            content.add(panel);
        }
        return content;
    }

    public void addTablesToGraphics(JPanel content, String teamName, Selector selector){
        int tableHeightLeft = 25 * (selector.listOfMatches.size()+1);
        int tableHeightRight;
        int index = 0;

        int numberOf2Graphics = 0;
        if (settings.showGoals)
            numberOf2Graphics ++;
        if (settings.showGoalsOT)
            numberOf2Graphics ++;

        if (settings.showGoals || settings.showGoalsOT){
            JPanel table = TableMaker.getTableGoals(selector.listOfMatches);
            table.setPreferredSize(new Dimension(245, graphicHeight));
            table.setBackground(new Color(238, 238, 238));
            //panelTables.add(table);

            tableHeightRight = ((JTable) table.getComponent(0)).getRowHeight() * (((JTable) table.getComponent(0)).getRowCount()+1);
            table.setPreferredSize(new Dimension(245, Math.max(tableHeightLeft, tableHeightRight)));
            if (!settings.showGraphics){
                if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                    heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
                }
            }
            table.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
            table.setBackground(new Color(238, 238, 238));
            ((JPanel) content.getComponents()[index]).add(table, BorderLayout.EAST);
            index++;
        }

        for (int i=0; i<numberOf2Graphics-1;i++){
            /*JPanel tablePos = new JPanel();
            tablePos.setPreferredSize(new Dimension(245, graphicHeight));
            tablePos.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
            tablePos.setBackground(new Color(238, 238, 238));
            panelTables.add(tablePos);*/
            JPanel table = new JPanel();
            tableHeightRight = 0;
            table.setPreferredSize(new Dimension(245, Math.max(tableHeightLeft, tableHeightRight)));
            if (!settings.showGraphics){
                if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                    heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
                }
            }
            table.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
            table.setBackground(new Color(238, 238, 238));
            ((JPanel) content.getComponents()[index]).add(table, BorderLayout.EAST);
            index++;
        }
        if (settings.showRealization){
            /*JPanel tablePos = new JPanel();
            tablePos.setPreferredSize(new Dimension(245, graphicHeight));
            tablePos.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
            tablePos.setBackground(new Color(238, 238, 238));
            panelTables.add(tablePos);*/
            JPanel table = new JPanel();
            tableHeightRight = 0;
            table.setPreferredSize(new Dimension(245, Math.max(tableHeightLeft, tableHeightRight)));
            if (!settings.showGraphics){
                if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                    heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
                }
            }
            table.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
            table.setBackground(new Color(238, 238, 238));
            ((JPanel) content.getComponents()[index]).add(table, BorderLayout.EAST);
            index++;
        }
        if (settings.showPowerPlay){
            /*JPanel tablePos = new JPanel();
            tablePos.setPreferredSize(new Dimension(245, graphicHeight));
            tablePos.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
            tablePos.setBackground(new Color(238, 238, 238));
            panelTables.add(tablePos);*/
            JPanel table = new JPanel();
            tableHeightRight = 0;
            table.setPreferredSize(new Dimension(245, Math.max(tableHeightLeft, tableHeightRight)));
            if (!settings.showGraphics){
                if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                    heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
                }
            }
            table.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
            table.setBackground(new Color(238, 238, 238));
            ((JPanel) content.getComponents()[index]).add(table, BorderLayout.EAST);
            index++;
        }
        if (settings.showShortHanded){
            /*JPanel tablePos = new JPanel();
            tablePos.setPreferredSize(new Dimension(245, graphicHeight));
            tablePos.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
            tablePos.setBackground(new Color(238, 238, 238));
            panelTables.add(tablePos);*/
            JPanel table = new JPanel();
            tableHeightRight = 0;
            table.setPreferredSize(new Dimension(245, Math.max(tableHeightLeft, tableHeightRight)));
            if (!settings.showGraphics){
                if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                    heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
                }
            }
            table.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
            table.setBackground(new Color(238, 238, 238));
            ((JPanel) content.getComponents()[index]).add(table, BorderLayout.EAST);
            index++;
        }
        if (settings.showTimeInAttack){
            /*JPanel tablePos = new JPanel();
            tablePos.setPreferredSize(new Dimension(245, graphicHeight));
            tablePos.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
            tablePos.setBackground(new Color(238, 238, 238));
            panelTables.add(tablePos);*/
            JPanel table = new JPanel();
            tableHeightRight = 0;
            table.setPreferredSize(new Dimension(245, Math.max(tableHeightLeft, tableHeightRight)));
            if (!settings.showGraphics){
                if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                    heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
                }
            }
            table.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
            table.setBackground(new Color(238, 238, 238));
            ((JPanel) content.getComponents()[index]).add(table, BorderLayout.EAST);
            index++;
        }
        if (settings.showGoals1per){
            // "Голы в первом периоде"
            JPanel table = TableMaker.getTable1stperiod(teamName, selector.listOfMatches);
            /*table1stperiod.setPreferredSize(new Dimension(245, graphicHeight));
            table1stperiod.setBackground(new Color(238, 238, 238));
            panelTables.add(table1stperiod);*/
            tableHeightRight = ((JTable) table.getComponent(0)).getRowHeight() * (((JTable) table.getComponent(0)).getRowCount()+1);
            table.setPreferredSize(new Dimension(245, Math.max(tableHeightLeft, tableHeightRight)));
            if (!settings.showGraphics){
                if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                    heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
                }
            }
            table.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
            table.setBackground(new Color(238, 238, 238));
            ((JPanel) content.getComponents()[index]).add(table, BorderLayout.EAST);
            index++;
        }

        if (settings.showGoals2per){
            // "Голы во втором периоде"
            JPanel table = TableMaker.getTable2ndperiod(teamName, selector.listOfMatches);
            /*table2ndperiod.setPreferredSize(new Dimension(245, graphicHeight));
            table2ndperiod.setBackground(new Color(238, 238, 238));
            panelTables.add(table2ndperiod);*/
            tableHeightRight = ((JTable) table.getComponent(0)).getRowHeight() * (((JTable) table.getComponent(0)).getRowCount()+1);
            table.setPreferredSize(new Dimension(245, Math.max(tableHeightLeft, tableHeightRight)));
            if (!settings.showGraphics){
                if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                    heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
                }
            }
            table.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
            table.setBackground(new Color(238, 238, 238));
            ((JPanel) content.getComponents()[index]).add(table, BorderLayout.EAST);
            index++;
        }

        if (settings.showGoals3per){
            // "Голы в третьем периоде"
            JPanel table = TableMaker.getTable3rdperiod(teamName, selector.listOfMatches);
            /*table3rdperiod.setPreferredSize(new Dimension(245, graphicHeight));
            table3rdperiod.setBackground(new Color(238, 238, 238));
            panelTables.add(table3rdperiod);*/
            tableHeightRight = ((JTable) table.getComponent(0)).getRowHeight() * (((JTable) table.getComponent(0)).getRowCount()+1);
            table.setPreferredSize(new Dimension(245, Math.max(tableHeightLeft, tableHeightRight)));
            if (!settings.showGraphics){
                if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                    heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
                }
            }
            table.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
            table.setBackground(new Color(238, 238, 238));
            ((JPanel) content.getComponents()[index]).add(table, BorderLayout.EAST);
            index++;
        }

        if (settings.showGoalsShortHanded){
            // "Забитые голы в меньшинстве"
            /*JPanel tablePos = new JPanel();
            tablePos.setPreferredSize(new Dimension(245, graphicHeight));
            tablePos.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
            tablePos.setBackground(new Color(238, 238, 238));
            panelTables.add(tablePos);*/
            JPanel table = new JPanel();
            tableHeightRight = 0;
            table.setPreferredSize(new Dimension(245, Math.max(tableHeightLeft, tableHeightRight)));
            if (!settings.showGraphics){
                if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                    heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
                }
            }
            table.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
            table.setBackground(new Color(238, 238, 238));
            ((JPanel) content.getComponents()[index]).add(table, BorderLayout.EAST);
            index++;
        }

        if (settings.showShotsOnTarget){
            //"Броски в створ"
            JPanel table = TableMaker.getTableShotsOnTarget(teamName, selector);
            /*tableShotsOnTarget.setPreferredSize(new Dimension(245, graphicHeight));
            tableShotsOnTarget.setBackground(new Color(238, 238, 238));
            panelTables.add(tableShotsOnTarget);*/
            tableHeightRight = ((JTable) table.getComponent(0)).getRowHeight() * (((JTable) table.getComponent(0)).getRowCount()+1);
            table.setPreferredSize(new Dimension(245, Math.max(tableHeightLeft, tableHeightRight)));
            if (!settings.showGraphics){
                if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                    heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
                }
            }
            table.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
            table.setBackground(new Color(238, 238, 238));
            ((JPanel) content.getComponents()[index]).add(table, BorderLayout.EAST);
            index++;
        }

        if (settings.showShotsOnTarget1per){
            //"Броски в створ 1 пер"
            JPanel table = TableMaker.getTableShotsOnTarget1per(teamName, selector);
            /*tableShotsOnTarget.setPreferredSize(new Dimension(245, graphicHeight));
            tableShotsOnTarget.setBackground(new Color(238, 238, 238));
            panelTables.add(tableShotsOnTarget);*/
            tableHeightRight = ((JTable) table.getComponent(0)).getRowHeight() * (((JTable) table.getComponent(0)).getRowCount()+1);
            table.setPreferredSize(new Dimension(245, Math.max(tableHeightLeft, tableHeightRight)));
            if (!settings.showGraphics){
                if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                    heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
                }
            }
            table.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
            table.setBackground(new Color(238, 238, 238));
            ((JPanel) content.getComponents()[index]).add(table, BorderLayout.EAST);
            index++;
        }

        if (settings.showShotsOnTarget2per){
            //"Броски в створ 2 пер"
            JPanel table = TableMaker.getTableShotsOnTarget2per(teamName, selector);
            /*tableShotsOnTarget.setPreferredSize(new Dimension(245, graphicHeight));
            tableShotsOnTarget.setBackground(new Color(238, 238, 238));
            panelTables.add(tableShotsOnTarget);*/
            tableHeightRight = ((JTable) table.getComponent(0)).getRowHeight() * (((JTable) table.getComponent(0)).getRowCount()+1);
            table.setPreferredSize(new Dimension(245, Math.max(tableHeightLeft, tableHeightRight)));
            if (!settings.showGraphics){
                if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                    heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
                }
            }
            table.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
            table.setBackground(new Color(238, 238, 238));
            ((JPanel) content.getComponents()[index]).add(table, BorderLayout.EAST);
            index++;
        }

        if (settings.showShotsOnTarget3per){
            //"Броски в створ 3 пер"
            JPanel table = TableMaker.getTableShotsOnTarget3per(teamName, selector);
            /*tableShotsOnTarget.setPreferredSize(new Dimension(245, graphicHeight));
            tableShotsOnTarget.setBackground(new Color(238, 238, 238));
            panelTables.add(tableShotsOnTarget);*/
            tableHeightRight = ((JTable) table.getComponent(0)).getRowHeight() * (((JTable) table.getComponent(0)).getRowCount()+1);
            table.setPreferredSize(new Dimension(245, Math.max(tableHeightLeft, tableHeightRight)));
            if (!settings.showGraphics){
                if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                    heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
                }
            }
            table.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
            table.setBackground(new Color(238, 238, 238));
            ((JPanel) content.getComponents()[index]).add(table, BorderLayout.EAST);
            index++;
        }

        if (settings.showMissedShots){
            /*JPanel tablePos = new JPanel();
            tablePos.setPreferredSize(new Dimension(245, graphicHeight));
            tablePos.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
            tablePos.setBackground(new Color(238, 238, 238));
            panelTables.add(tablePos);*/
            JPanel table = new JPanel();
            tableHeightRight = 0;
            table.setPreferredSize(new Dimension(245, Math.max(tableHeightLeft, tableHeightRight)));
            if (!settings.showGraphics){
                if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                    heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
                }
            }
            table.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
            table.setBackground(new Color(238, 238, 238));
            ((JPanel) content.getComponents()[index]).add(table, BorderLayout.EAST);
            index++;
        }

        if (settings.showBlockedShots){
            // "Заблокированные броски"
            JPanel table = TableMaker.getTableBlockedShots(teamName, selector);
            /*tableBlockedShots.setPreferredSize(new Dimension(245, graphicHeight));
            tableBlockedShots.setBackground(new Color(238, 238, 238));
            panelTables.add(tableBlockedShots);*/
            tableHeightRight = ((JTable) table.getComponent(0)).getRowHeight() * (((JTable) table.getComponent(0)).getRowCount()+1);
            table.setPreferredSize(new Dimension(245, Math.max(tableHeightLeft, tableHeightRight)));
            if (!settings.showGraphics){
                if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                    heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
                }
            }
            table.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
            table.setBackground(new Color(238, 238, 238));
            ((JPanel) content.getComponents()[index]).add(table, BorderLayout.EAST);
            index++;


        }

        if (settings.showFaceoffs){
            // Вбрасывания
            JPanel table = TableMaker.getTableFaceoffs(teamName, selector);
            /*tableFaceoffs.setPreferredSize(new Dimension(245, graphicHeight));
            tableFaceoffs.setBackground(new Color(238, 238, 238));
            panelTables.add(tableFaceoffs);*/
            tableHeightRight = ((JTable) table.getComponent(0)).getRowHeight() * (((JTable) table.getComponent(0)).getRowCount()+1);
            table.setPreferredSize(new Dimension(245, Math.max(tableHeightLeft, tableHeightRight)));
            if (!settings.showGraphics){
                if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                    heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
                }
            }
            table.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
            table.setBackground(new Color(238, 238, 238));
            ((JPanel) content.getComponents()[index]).add(table, BorderLayout.EAST);
            index++;
        }

        if (settings.showHits){
            // Силовые приемы
            JPanel table = TableMaker.getTableHits(teamName, selector);
            /*tableHits.setPreferredSize(new Dimension(245, graphicHeight));
            tableHits.setBackground(new Color(238, 238, 238));
            panelTables.add(tableHits);*/
            tableHeightRight = ((JTable) table.getComponent(0)).getRowHeight() * (((JTable) table.getComponent(0)).getRowCount()+1);
            table.setPreferredSize(new Dimension(245, Math.max(tableHeightLeft, tableHeightRight)));
            if (!settings.showGraphics){
                if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                    heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
                }
            }
            table.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
            table.setBackground(new Color(238, 238, 238));
            ((JPanel) content.getComponents()[index]).add(table, BorderLayout.EAST);
            index++;
        }

        if (settings.showPenMinutes){
            // Минуты штрафа
            JPanel table = TableMaker.getTablePenMinutes(teamName, selector);
            /*tablePenMinutes.setPreferredSize(new Dimension(245, graphicHeight));
            tablePenMinutes.setBackground(new Color(238, 238, 238));
            panelTables.add(tablePenMinutes);*/
            tableHeightRight = ((JTable) table.getComponent(0)).getRowHeight() * (((JTable) table.getComponent(0)).getRowCount()+1);
            table.setPreferredSize(new Dimension(245, Math.max(tableHeightLeft, tableHeightRight)));
            if (!settings.showGraphics){
                if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                    heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
                }
            }
            table.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
            table.setBackground(new Color(238, 238, 238));
            ((JPanel) content.getComponents()[index]).add(table, BorderLayout.EAST);
            index++;
        }

        if (settings.showNumberOf2MinutesPen){
            // 2мин удаления
            JPanel table = TableMaker.getTablePen2minutes(teamName, selector);
            /*tablePen2minutes.setPreferredSize(new Dimension(245, graphicHeight));
            tablePen2minutes.setBackground(new Color(238, 238, 238));
            panelTables.add(tablePen2minutes);*/
            tableHeightRight = ((JTable) table.getComponent(0)).getRowHeight() * (((JTable) table.getComponent(0)).getRowCount()+1);
            table.setPreferredSize(new Dimension(245, Math.max(tableHeightLeft, tableHeightRight)));
            if (!settings.showGraphics){
                if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                    heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
                }
            }
            table.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
            table.setBackground(new Color(238, 238, 238));
            ((JPanel) content.getComponents()[index]).add(table, BorderLayout.EAST);
            index++;
        }

        /*for (int i=0; i<5; i++){
            JPanel emptyPanel = new JPanel();
            emptyPanel.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
            emptyPanel.setPreferredSize(new Dimension(245, graphicHeight));
            emptyPanel.setBackground(new Color(238, 238, 238));
            ((JPanel) content.getComponents()[index]).add(emptyPanel, BorderLayout.EAST);
            index++;
        }*/

    }

    public void addTablesToShotsOnTargetGraphics(JPanel content, String teamName, Selector selector){
//        int tableHeightLeft = 25 * (selector.listOfMatches.size()+1);
//        int tableHeightRight;
        int index = 0;


        JPanel tableShotsOnTarget = TableMaker.getTableShotsOnTarget(teamName, selector);
        tableShotsOnTarget.setPreferredSize(new Dimension(245, graphicHeight));
        tableShotsOnTarget.setBackground(new Color(238, 238, 238));
        /*if (!settings.showGraphics){
            if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
            }
        }*/
        tableShotsOnTarget.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        ((JPanel) content.getComponents()[index]).add(tableShotsOnTarget, BorderLayout.EAST);
        index++;

        JPanel table1 = new JPanel();
        table1.setPreferredSize(new Dimension(245, graphicHeight));
        table1.setBackground(new Color(238, 238, 238));
        /*if (!settings.showGraphics){
            if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
            }
        }*/
        table1.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        ((JPanel) content.getComponents()[index]).add(table1, BorderLayout.EAST);
        index++;

        JPanel table0 = new JPanel();
        table0.setPreferredSize(new Dimension(245, graphicHeight));
        table0.setBackground(new Color(238, 238, 238));
        /*if (Team.getLeague(teamName).equals("KHL"))
            ((JPanel) content.getComponents()[index]).add(table0);*/
        //tableHeightRight = graphicHeight;
            /*if (!settings.showGraphics){
                if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                    heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
                }
            }*/
        table0.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        table0.setBackground(new Color(238, 238, 238));
        ((JPanel) content.getComponents()[index]).add(table0, BorderLayout.EAST);
        index++;

        JPanel table2 = new JPanel();
        table2.setPreferredSize(new Dimension(245, graphicHeight));
        table2.setBackground(new Color(238, 238, 238));
        /*if (!settings.showGraphics){
            if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
            }
        }*/
        table2.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        ((JPanel) content.getComponents()[index]).add(table2, BorderLayout.EAST);
        index++;

        JPanel tableShotsOnTarget1per = TableMaker.getTableShotsOnTarget1per(teamName, selector);
        tableShotsOnTarget1per.setPreferredSize(new Dimension(245, graphicHeight));
        tableShotsOnTarget1per.setBackground(new Color(238, 238, 238));
        /*if (!settings.showGraphics){
            if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
            }
        }*/
        tableShotsOnTarget1per.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        ((JPanel) content.getComponents()[index]).add(tableShotsOnTarget1per, BorderLayout.EAST);
        index++;


        JPanel tableShotsOnTarget2per = TableMaker.getTableShotsOnTarget2per(teamName, selector);
        tableShotsOnTarget2per.setPreferredSize(new Dimension(245, graphicHeight));
        tableShotsOnTarget2per.setBackground(new Color(238, 238, 238));
        /*if (!settings.showGraphics){
            if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
            }
        }*/
        tableShotsOnTarget2per.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        ((JPanel) content.getComponents()[index]).add(tableShotsOnTarget2per, BorderLayout.EAST);
        index++;

        JPanel tableShotsOnTarget3per = TableMaker.getTableShotsOnTarget2per(teamName, selector);
        tableShotsOnTarget3per.setPreferredSize(new Dimension(245, graphicHeight));
        tableShotsOnTarget3per.setBackground(new Color(238, 238, 238));
        /*if (!settings.showGraphics){
            if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
            }
        }*/
        tableShotsOnTarget3per.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        ((JPanel) content.getComponents()[index]).add(tableShotsOnTarget3per, BorderLayout.EAST);
        index++;


    }

    public void addTablesToPenalties(JPanel content, String teamName, Selector selector){
        //int tableHeightLeft = 25 * (selector.listOfMatches.size()+1);
        //int tableHeightRight;
        int index = 0;

        JPanel table0 = new JPanel();
        table0.setPreferredSize(new Dimension(245, graphicHeight));
        table0.setBackground(new Color(238, 238, 238));
        /*if (!settings.showGraphics){
            if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
            }
        }*/
        table0.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        ((JPanel) content.getComponents()[index]).add(table0, BorderLayout.EAST);
        index++;

        JPanel tablePenMinutes = TableMaker.getTablePenMinutes(teamName, selector);
        tablePenMinutes.setPreferredSize(new Dimension(245, graphicHeight));
        tablePenMinutes.setBackground(new Color(238, 238, 238));
        tablePenMinutes.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        ((JPanel) content.getComponents()[index]).add(tablePenMinutes, BorderLayout.EAST);
        index++;


        JPanel table1 = new JPanel();
        table1.setPreferredSize(new Dimension(245, graphicHeight));
        table1.setBackground(new Color(238, 238, 238));
        /*if (!settings.showGraphics){
            if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
            }
        }*/
        table1.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        ((JPanel) content.getComponents()[index]).add(table1, BorderLayout.EAST);
        index++;

        JPanel table2 = new JPanel();
        table2.setPreferredSize(new Dimension(245, graphicHeight));
        table2.setBackground(new Color(238, 238, 238));
        /*if (!settings.showGraphics){
            if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
            }
        }*/
        table2.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        ((JPanel) content.getComponents()[index]).add(table2, BorderLayout.EAST);
        index++;


        JPanel tablePenMinutes1per = TableMaker.getTablePenMinutes1per(teamName, selector);
        tablePenMinutes1per.setPreferredSize(new Dimension(245, graphicHeight));
        tablePenMinutes1per.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        tablePenMinutes1per.setBackground(new Color(238, 238, 238));
        ((JPanel) content.getComponents()[index]).add(tablePenMinutes1per, BorderLayout.EAST);
        index++;

        JPanel tablePenMinutes2per = TableMaker.getTablePenMinutes2per(teamName, selector);
        tablePenMinutes2per.setPreferredSize(new Dimension(245, graphicHeight));
        tablePenMinutes2per.setBackground(new Color(238, 238, 238));
        tablePenMinutes2per.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        ((JPanel) content.getComponents()[index]).add(tablePenMinutes2per, BorderLayout.EAST);
        index++;

        JPanel tablePenMinutes3per = TableMaker.getTablePenMinutes3per(teamName, selector);
        tablePenMinutes3per.setPreferredSize(new Dimension(245, graphicHeight));
        tablePenMinutes3per.setBackground(new Color(238, 238, 238));
        tablePenMinutes3per.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        ((JPanel) content.getComponents()[index]).add(tablePenMinutes3per, BorderLayout.EAST);
        index++;

        JPanel table2MinPenalties = TableMaker.getTablePen2minutes(teamName, selector);
        table2MinPenalties.setPreferredSize(new Dimension(245, graphicHeight));
        table2MinPenalties.setBackground(new Color(238, 238, 238));
        table2MinPenalties.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        ((JPanel) content.getComponents()[index]).add(table2MinPenalties, BorderLayout.EAST);
        index++;

        JPanel table3 = new JPanel();
        table3.setPreferredSize(new Dimension(245, graphicHeight));
        table3.setBackground(new Color(238, 238, 238));
        /*if (!settings.showGraphics){
            if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
            }
        }*/
        table3.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        ((JPanel) content.getComponents()[index]).add(table3, BorderLayout.EAST);
        index++;

        JPanel table4 = new JPanel();
        table4.setPreferredSize(new Dimension(245, graphicHeight));
        table4.setBackground(new Color(238, 238, 238));
        /*if (!settings.showGraphics){
            if (Math.max(tableHeightLeft, tableHeightRight) > heights.get(index)){
                heights.set(index, Math.max(tableHeightLeft, tableHeightRight));
            }
        }*/
        table4.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        ((JPanel) content.getComponents()[index]).add(table4, BorderLayout.EAST);
        index++;

        JPanel table2MinPenalties1per = TableMaker.getTablePen2minutes1per(teamName, selector);
        table2MinPenalties1per.setPreferredSize(new Dimension(245, graphicHeight));
        table2MinPenalties1per.setBackground(new Color(238, 238, 238));
        table2MinPenalties1per.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        ((JPanel) content.getComponents()[index]).add(table2MinPenalties1per, BorderLayout.EAST);
        index++;

        JPanel table2MinPenalties2per = TableMaker.getTablePen2minutes2per(teamName, selector);
        table2MinPenalties2per.setPreferredSize(new Dimension(245, graphicHeight));
        table2MinPenalties2per.setBackground(new Color(238, 238, 238));
        table2MinPenalties2per.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        ((JPanel) content.getComponents()[index]).add(table2MinPenalties2per, BorderLayout.EAST);
        index++;

        JPanel table2MinPenalties3per = TableMaker.getTablePen2minutes3per(teamName, selector);
        table2MinPenalties3per.setPreferredSize(new Dimension(245, graphicHeight));
        table2MinPenalties3per.setBackground(new Color(238, 238, 238));
        table2MinPenalties3per.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        ((JPanel) content.getComponents()[index]).add(table2MinPenalties3per, BorderLayout.EAST);
        index++;

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

    public JPanel getDiagrams(String teamName, String allOrHomeOrAway, String season, String lastOrFullSeason, Selector selector){
        JPanel result = new JPanel(new VerticalLayout());
        int diagramWidth = 290;
        String headerText = "";
        if (lastOrFullSeason.contains("Весь сезон")){
            if (allOrHomeOrAway.contains("Все")){
                headerText = "Все игры " + teamName + " в сезоне " + season;
            }
            if (allOrHomeOrAway.contains("Дома")){
                headerText = "Все домашние игры " + teamName + " в сезоне " + season;
            }
            if (allOrHomeOrAway.contains("На выезде")){
                headerText = "Все гостевые игры " + teamName + " в сезоне " + season;
            }
        } else {
            if (allOrHomeOrAway.contains("Все")){
                headerText = "Последние " + selector.listOfMatches.size() + " игр " + teamName + " в сезоне " + season;
            }
            if (allOrHomeOrAway.contains("Дома")){
                headerText = "Последние " + selector.listOfMatches.size() + " домашних игр " + teamName + " в сезоне " + season;
            }
            if (allOrHomeOrAway.contains("На выезде")){
                headerText = "Последние " + selector.listOfMatches.size() + " гостевых игр " + teamName + " в сезоне " + season;
            }
        }
        JLabel jtf = new JLabel(headerText);
        jtf.setFont(new Font("", 0, 18));
        jtf.setHorizontalAlignment(SwingConstants.CENTER);
        jtf.setBorder(new EmptyBorder(10,10,0,10));
        result.add(jtf);

        DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
        // row keys...
        String series1 = "1-ый период";
        String series2 = "2-ой период";
        String series3 = "3-ий период";
        // column keys...
        String category1 = "Забито" ;
        String category2 = "Пропущено" ;
        String category3 = "Разница" ;
        String category4 = "Тотал" ;

        categoryDataset.addValue(Double.parseDouble(selector.pList.get(17).get(1)), series1, category1);
        categoryDataset.addValue(Double.parseDouble(selector.pList.get(18).get(1)), series2, category1);
        categoryDataset.addValue(Double.parseDouble(selector.pList.get(19).get(1)), series3, category1);

        categoryDataset.addValue(Double.parseDouble(selector.pList.get(17).get(2)), series1, category2);
        categoryDataset.addValue(Double.parseDouble(selector.pList.get(18).get(2)), series2, category2);
        categoryDataset.addValue(Double.parseDouble(selector.pList.get(19).get(2)), series3, category2);

        categoryDataset.addValue(Double.parseDouble(selector.pList.get(17).get(1)) - Double.parseDouble(selector.pList.get(17).get(2)), series1, category3);
        categoryDataset.addValue(Double.parseDouble(selector.pList.get(18).get(1)) - Double.parseDouble(selector.pList.get(18).get(2)), series2, category3);
        categoryDataset.addValue(Double.parseDouble(selector.pList.get(19).get(1)) - Double.parseDouble(selector.pList.get(19).get(2)), series3, category3);

        categoryDataset.addValue(Double.parseDouble(selector.pList.get(17).get(1)) + Double.parseDouble(selector.pList.get(17).get(2)), series1, category4);
        categoryDataset.addValue(Double.parseDouble(selector.pList.get(18).get(1)) + Double.parseDouble(selector.pList.get(18).get(2)), series2, category4);
        categoryDataset.addValue(Double.parseDouble(selector.pList.get(19).get(1)) + Double.parseDouble(selector.pList.get(19).get(2)), series3, category4);

        JFreeChart chartByTimes = ChartFactory.createBarChart(
                "Голы по периодам", "", "", categoryDataset, PlotOrientation.VERTICAL, true, true, false);

        // Определение фона диаграммы
        chartByTimes.setBackgroundPaint(new Color(238, 238, 238));
        chartByTimes.getTitle().setFont(new Font("", 0, 18));
        chartByTimes.getTitle().setMargin(10,0,0,0);
        // Настройка plot'а
        CategoryPlot plotByTimes = chartByTimes.getCategoryPlot();
        plotByTimes.setBackgroundPaint(new Color(238, 238, 238));
        plotByTimes.getRenderer().setSeriesPaint(0, new Color(255, 40 , 40  ));
        plotByTimes.getRenderer().setSeriesPaint(1, new Color(40 , 40 , 255 ));
        plotByTimes.getRenderer().setSeriesPaint(2, new Color(40 , 255, 40  ));

        plotByTimes.setDomainGridlinePaint(Color.black);
        plotByTimes.setRangeGridlinePaint(Color.black);
        NumberAxis axis = (NumberAxis) plotByTimes.getRangeAxis();
        axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        BarRenderer renderer = (BarRenderer)plotByTimes.getRenderer();
        renderer.setItemMargin(0.02);
        SubCategoryAxis subCategoryAxis = new SubCategoryAxis("");
        subCategoryAxis.setCategoryMargin(0.15);

        ChartPanel cp = new ChartPanel(chartByTimes);
        cp.setPreferredSize(new Dimension(diagramWidth, graphicHeight));
        result.add(cp);

        category1 = "Собственные" ;
        category2 = "Противник" ;

        double shots1per = 0;
        double shots2per = 0;
        double shots3per = 0;
        double shotsOpp1per = 0;
        double shotsOpp2per = 0;
        double shotsOpp3per = 0;
        double penMinutes1per = 0;
        double penMinutes2per = 0;
        double penMinutes3per = 0;
        double penMinutesOpp1per = 0;
        double penMinutesOpp2per = 0;
        double penMinutesOpp3per = 0;
        double twoMinPen1per = 0;
        double twoMinPen2per = 0;
        double twoMinPen3per = 0;
        double twoMinPenOpp1per = 0;
        double twoMinPenOpp2per = 0;
        double twoMinPenOpp3per = 0;


        for (int i=0; i<selector.listOfMatches.size(); i++){

            Match m = selector.listOfMatches.get(i);

            if (m.homeTeam.equals(teamName)){
                shots1per += m.homeShotsOnTarget1stPeriod;
                shots2per += m.homeShotsOnTarget2ndPeriod;
                shots3per += m.homeShotsOnTarget3rdPeriod;
                shotsOpp1per += m.awayShotsOnTarget1stPeriod;
                shotsOpp2per += m.awayShotsOnTarget2ndPeriod;
                shotsOpp3per += m.awayShotsOnTarget3rdPeriod;
                penMinutes1per += m.homePenaltyMinutes1stPeriod;
                penMinutes2per += m.homePenaltyMinutes2ndPeriod;
                penMinutes3per += m.homePenaltyMinutes3rdPeriod;
                penMinutesOpp1per += m.awayPenaltyMinutes1stPeriod;
                penMinutesOpp2per += m.awayPenaltyMinutes2ndPeriod;
                penMinutesOpp3per += m.awayPenaltyMinutes3rdPeriod;
                twoMinPen1per += m.home2MinPenalties1stPeriod;
                twoMinPen2per += m.home2MinPenalties2ndPeriod;
                twoMinPen3per += m.home2MinPenalties3rdPeriod;
                twoMinPenOpp1per += m.away2MinPenalties1stPeriod;
                twoMinPenOpp2per += m.away2MinPenalties2ndPeriod;
                twoMinPenOpp3per += m.away2MinPenalties3rdPeriod;

            } else {
                shots1per += m.awayShotsOnTarget1stPeriod;
                shots2per += m.awayShotsOnTarget2ndPeriod;
                shots3per += m.awayShotsOnTarget3rdPeriod;
                shotsOpp1per += m.homeShotsOnTarget1stPeriod;
                shotsOpp2per += m.homeShotsOnTarget2ndPeriod;
                shotsOpp3per += m.homeShotsOnTarget3rdPeriod;
                penMinutes1per += m.awayPenaltyMinutes1stPeriod;
                penMinutes2per += m.awayPenaltyMinutes2ndPeriod;
                penMinutes3per += m.awayPenaltyMinutes3rdPeriod;
                penMinutesOpp1per += m.homePenaltyMinutes1stPeriod;
                penMinutesOpp2per += m.homePenaltyMinutes2ndPeriod;
                penMinutesOpp3per += m.homePenaltyMinutes3rdPeriod;
                twoMinPen1per += m.away2MinPenalties1stPeriod;
                twoMinPen2per += m.away2MinPenalties2ndPeriod;
                twoMinPen3per += m.away2MinPenalties3rdPeriod;
                twoMinPenOpp1per += m.home2MinPenalties1stPeriod;
                twoMinPenOpp2per += m.home2MinPenalties2ndPeriod;
                twoMinPenOpp3per += m.home2MinPenalties3rdPeriod;
            }
        }

        shots1per = shots1per / (double) selector.listOfMatches.size();
        shots2per = shots2per / (double) selector.listOfMatches.size();
        shots3per = shots3per / (double) selector.listOfMatches.size();
        shotsOpp1per = shotsOpp1per / (double) selector.listOfMatches.size();
        shotsOpp2per = shotsOpp2per / (double) selector.listOfMatches.size();
        shotsOpp3per = shotsOpp3per / (double) selector.listOfMatches.size();
        penMinutes1per = penMinutes1per / (double) selector.listOfMatches.size();
        penMinutes2per = penMinutes2per / (double) selector.listOfMatches.size();
        penMinutes3per = penMinutes3per / (double) selector.listOfMatches.size();
        penMinutesOpp1per = penMinutesOpp1per / (double) selector.listOfMatches.size();
        penMinutesOpp2per = penMinutesOpp2per / (double) selector.listOfMatches.size();
        penMinutesOpp3per = penMinutesOpp3per / (double) selector.listOfMatches.size();
        twoMinPen1per = twoMinPen1per / (double) selector.listOfMatches.size();
        twoMinPen2per = twoMinPen2per / (double) selector.listOfMatches.size();
        twoMinPen3per = twoMinPen3per / (double) selector.listOfMatches.size();
        twoMinPenOpp1per = twoMinPenOpp1per / (double) selector.listOfMatches.size();
        twoMinPenOpp2per = twoMinPenOpp2per / (double) selector.listOfMatches.size();
        twoMinPenOpp3per = twoMinPenOpp3per / (double) selector.listOfMatches.size();

        categoryDataset = new DefaultCategoryDataset();

        categoryDataset.addValue(shots1per, series1, category1);
        categoryDataset.addValue(shots2per, series2, category1);
        categoryDataset.addValue(shots3per, series3, category1);

        categoryDataset.addValue(shotsOpp1per, series1, category2);
        categoryDataset.addValue(shotsOpp2per, series2, category2);
        categoryDataset.addValue(shotsOpp3per, series3, category2);

        categoryDataset.addValue(shots1per - shotsOpp1per, series1, category3);
        categoryDataset.addValue(shots2per - shotsOpp2per, series2, category3);
        categoryDataset.addValue(shots3per - shotsOpp3per, series3, category3);

        categoryDataset.addValue(shots1per + shotsOpp1per, series1, category4);
        categoryDataset.addValue(shots2per + shotsOpp2per, series2, category4);
        categoryDataset.addValue(shots3per + shotsOpp3per, series3, category4);

        chartByTimes = ChartFactory.createBarChart(
                "Броски в створ по периодам (сред.)", "", "", categoryDataset, PlotOrientation.VERTICAL, true, true, false);

        // Определение фона диаграммы
        chartByTimes.setBackgroundPaint(new Color(238, 238, 238));
        chartByTimes.getTitle().setFont(new Font("", 0, 18));
        chartByTimes.getTitle().setMargin(10,0,0,0);
        // Настройка plot'а
        plotByTimes = chartByTimes.getCategoryPlot();
        plotByTimes.setBackgroundPaint(new Color(238, 238, 238));
        plotByTimes.getRenderer().setSeriesPaint(0, new Color(255, 40 , 40  ));
        plotByTimes.getRenderer().setSeriesPaint(1, new Color(40 , 40 , 255 ));
        plotByTimes.getRenderer().setSeriesPaint(2, new Color(40 , 255, 40  ));

        plotByTimes.setDomainGridlinePaint(Color.black);
        plotByTimes.setRangeGridlinePaint(Color.black);
        axis = (NumberAxis) plotByTimes.getRangeAxis();
        axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        renderer = (BarRenderer)plotByTimes.getRenderer();
        renderer.setItemMargin(0.02);
        subCategoryAxis = new SubCategoryAxis("");
        subCategoryAxis.setCategoryMargin(0.15);

        cp = new ChartPanel(chartByTimes);
        cp.setPreferredSize(new Dimension(diagramWidth, graphicHeight));
        result.add(cp);

        categoryDataset = new DefaultCategoryDataset();

        categoryDataset.addValue(penMinutes1per, series1, category1);
        categoryDataset.addValue(penMinutes2per, series2, category1);
        categoryDataset.addValue(penMinutes3per, series3, category1);

        categoryDataset.addValue(penMinutesOpp1per, series1, category2);
        categoryDataset.addValue(penMinutesOpp2per, series2, category2);
        categoryDataset.addValue(penMinutesOpp3per, series3, category2);

        categoryDataset.addValue(penMinutes1per - penMinutesOpp1per, series1, category3);
        categoryDataset.addValue(penMinutes2per - penMinutesOpp2per, series2, category3);
        categoryDataset.addValue(penMinutes3per - penMinutesOpp3per, series3, category3);

        categoryDataset.addValue(penMinutes1per + penMinutesOpp1per, series1, category4);
        categoryDataset.addValue(penMinutes2per + penMinutesOpp2per, series2, category4);
        categoryDataset.addValue(penMinutes3per + penMinutesOpp3per, series3, category4);

        chartByTimes = ChartFactory.createBarChart(
                "Минуты штрафа по периодам (сред.)", "", "", categoryDataset, PlotOrientation.VERTICAL, true, true, false);

        // Определение фона диаграммы
        chartByTimes.setBackgroundPaint(new Color(238, 238, 238));
        chartByTimes.getTitle().setFont(new Font("", 0, 18));
        chartByTimes.getTitle().setMargin(10,0,0,0);
        // Настройка plot'а
        plotByTimes = chartByTimes.getCategoryPlot();
        plotByTimes.setBackgroundPaint(new Color(238, 238, 238));
        plotByTimes.getRenderer().setSeriesPaint(0, new Color(255, 40 , 40  ));
        plotByTimes.getRenderer().setSeriesPaint(1, new Color(40 , 40 , 255 ));
        plotByTimes.getRenderer().setSeriesPaint(2, new Color(40 , 255, 40  ));

        plotByTimes.setDomainGridlinePaint(Color.black);
        plotByTimes.setRangeGridlinePaint(Color.black);
        axis = (NumberAxis) plotByTimes.getRangeAxis();
        axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        renderer = (BarRenderer)plotByTimes.getRenderer();
        renderer.setItemMargin(0.02);
        subCategoryAxis = new SubCategoryAxis("");
        subCategoryAxis.setCategoryMargin(0.15);

        cp = new ChartPanel(chartByTimes);
        cp.setPreferredSize(new Dimension(diagramWidth, graphicHeight));
        result.add(cp);

        categoryDataset = new DefaultCategoryDataset();

        categoryDataset.addValue(twoMinPen1per, series1, category1);
        categoryDataset.addValue(twoMinPen2per, series2, category1);
        categoryDataset.addValue(twoMinPen3per, series3, category1);

        categoryDataset.addValue(twoMinPenOpp1per, series1, category2);
        categoryDataset.addValue(twoMinPenOpp2per, series2, category2);
        categoryDataset.addValue(twoMinPenOpp3per, series3, category2);

        categoryDataset.addValue(twoMinPen1per - twoMinPenOpp1per, series1, category3);
        categoryDataset.addValue(twoMinPen2per - twoMinPenOpp2per, series2, category3);
        categoryDataset.addValue(twoMinPen3per - twoMinPenOpp3per, series3, category3);

        categoryDataset.addValue(twoMinPen1per + twoMinPenOpp1per, series1, category4);
        categoryDataset.addValue(twoMinPen2per + twoMinPenOpp2per, series2, category4);
        categoryDataset.addValue(twoMinPen3per + twoMinPenOpp3per, series3, category4);

        chartByTimes = ChartFactory.createBarChart(
                "Кол-во двухминутных удалений по периодам (сред.)", "", "", categoryDataset, PlotOrientation.VERTICAL, true, true, false);

        // Определение фона диаграммы
        chartByTimes.setBackgroundPaint(new Color(238, 238, 238));
        chartByTimes.getTitle().setFont(new Font("", 0, 18));
        chartByTimes.getTitle().setMargin(10,0,0,0);
        // Настройка plot'а
        plotByTimes = chartByTimes.getCategoryPlot();
        plotByTimes.setBackgroundPaint(new Color(238, 238, 238));
        plotByTimes.getRenderer().setSeriesPaint(0, new Color(255, 40 , 40  ));
        plotByTimes.getRenderer().setSeriesPaint(1, new Color(40 , 40 , 255 ));
        plotByTimes.getRenderer().setSeriesPaint(2, new Color(40 , 255, 40  ));

        plotByTimes.setDomainGridlinePaint(Color.black);
        plotByTimes.setRangeGridlinePaint(Color.black);
        axis = (NumberAxis) plotByTimes.getRangeAxis();
        axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        renderer = (BarRenderer)plotByTimes.getRenderer();
        renderer.setItemMargin(0.02);
        subCategoryAxis = new SubCategoryAxis("");
        subCategoryAxis.setCategoryMargin(0.15);

        cp = new ChartPanel(chartByTimes);
        cp.setPreferredSize(new Dimension(diagramWidth, graphicHeight));
        result.add(cp);

        return result;
    }

    public JPanel getTablesWithStats(String teamName, String allOrHomeOrAway, Selector selector, ArrayList<String> tournamentTable){
        JPanel result = new JPanel(new VerticalLayout());
        ArrayList<Match> listOfMatches = selector.listOfMatches;
        this.settings = Settings.getSettingsFromFile();
        this.teamName = teamName;
        double[][] dataArrayThis = new double[titles.length][listOfMatches.size()];
        double[][] dataArrayOpponent = new double[titles.length][listOfMatches.size()];
        double[][] dataArrayTotal = new double[titles.length][listOfMatches.size()];
        String[] arrayOpponents = new String[listOfMatches.size()];
        selector.getArraysWithStats(teamName ,dataArrayThis, dataArrayOpponent, dataArrayTotal, arrayOpponents, tournamentTable);

        for (int i=0; i<2; i++){
            if (settings.getShowList().get(i)){
                String whereIsMatch = " (Все игры)";
                if (allOrHomeOrAway.equals("Дома")) whereIsMatch = " (Игры дома)" ;
                if (allOrHomeOrAway.equals("На выезде")) whereIsMatch = " (Игры на выезде)" ;
                String title = "  " + teamName+ ": " + titles[i] + whereIsMatch;
                JPanel panelWithTable = getPanelWithTable(dataArrayThis, dataArrayOpponent, dataArrayTotal, arrayOpponents, i, title, tournamentTable);
                graphicTitles.add(title);
                heights.add(panelWithTable.getHeight());
                result.add(panelWithTable);
            }
        }

        for (int i=2; i<5; i++){
            switch (i){
                case 2:{
                    series1name = "Реализация " + teamName;
                    series2name = "Реализация соперника";
                    break;
                }
                case 3:{
                    series1name = "Забитые голы";
                    series2name = "Кол-во большинства";
                    break;
                }
                case 4:{
                    series1name = "Пропущенные голы";
                    series2name = "Кол-во меньшинства";
                    break;
                }
            }
            if (settings.getShowList().get(i)){
                String whereIsMatch = " (Все игры)";
                if (allOrHomeOrAway.equals("Дома")) whereIsMatch = " (Игры дома)" ;
                if (allOrHomeOrAway.equals("На выезде")) whereIsMatch = " (Игры на выезде)" ;
                String title = "  " + teamName+ ": " + series1name + " и " + series2name + whereIsMatch;
                JPanel panelWithTable = getPanelWithTable(dataArrayThis, dataArrayOpponent, dataArrayTotal, arrayOpponents, i, title, tournamentTable);
                graphicTitles.add(title);
                heights.add(panelWithTable.getHeight());
                result.add(panelWithTable);
            }
        }

        for (int i=5; i<titles.length; i++){
            if (settings.getShowList().get(i)){
                String whereIsMatch = " (Все игры)";
                if (allOrHomeOrAway.equals("Дома")) whereIsMatch = " (Игры дома)" ;
                if (allOrHomeOrAway.equals("На выезде")) whereIsMatch = " (Игры на выезде)" ;
                String title = "  " + teamName+ ": " + titles[i] + whereIsMatch;
                JPanel panelWithTable = getPanelWithTable(dataArrayThis, dataArrayOpponent, dataArrayTotal, arrayOpponents, i, title, tournamentTable);
                graphicTitles.add(title);
                heights.add(panelWithTable.getHeight());
                result.add(panelWithTable);
            }
        }

        if (status == 1){
            addTablesToGraphics(result, teamName, selector);
        }

        for (int i=0; i<heights.size(); i++){
            heights.set(i, heights.get(i) + 24);
        }

        return result;
    }

    public JPanel getPanelWithTable(double[][] dataArrayThis, double[][] dataArrayOpponent, double[][] dataArrayTotal,
                                    String[] arrayOpponents, int index, String title, ArrayList<String> tournamentTable){
        JPanel result = new JPanel(new BorderLayout());

        JLabel headerText = new JLabel(title);
        headerText.setFont(new Font("", 0, 18));
        result.add(headerText, BorderLayout.NORTH);

        Object[] colHeads;
        Object[][] data;

        switch (index){
            case 2:{
                colHeads = new Object[]{"Номер", "Хозяева", "% реал. бросков", "Гости"};
                data = new Object[arrayOpponents.length][colHeads.length];
                for (int i=0; i<arrayOpponents.length; i++){
                    if (arrayOpponents[i].contains("(H)")){
                        data[i] = new Object[]{
                                i+1,
                                shortTeamName + "(" + League.getPositionInLeague(teamName, tournamentTable) + ")",
                                String.valueOf(MyMath.round(100 * dataArrayThis[0][i] / dataArrayThis[10][i] , 2)) + " : " +String.valueOf(MyMath.round(100 * dataArrayOpponent[0][i] / dataArrayOpponent[10][i] , 2)),
                                arrayOpponents[i].substring(0,3) + arrayOpponents[i].substring(6,arrayOpponents[i].length())
                        };
                    } else {
                        data[i] = new Object[]{
                                i+1,
                                arrayOpponents[i].substring(0,3) + arrayOpponents[i].substring(6,arrayOpponents[i].length()),
                                String.valueOf(MyMath.round(100 * dataArrayOpponent[0][i] / dataArrayOpponent[10][i] , 2)) + " : " +String.valueOf(MyMath.round(100 * dataArrayThis[0][i] / dataArrayThis[10][i] , 2)),
                                shortTeamName + "(" + League.getPositionInLeague(teamName, tournamentTable) + ")"
                        };
                    }
                }
                break;
            }
            case 3:{
                colHeads = new Object[]{"Номер", "Хозяева", shortTeamName + ": Г.бол. / Кол.бол.", "Гости"};
                data = new Object[arrayOpponents.length][colHeads.length];
                for (int i=0; i<arrayOpponents.length; i++){
                    if (arrayOpponents[i].contains("(H)")){
                        data[i] = new Object[]{
                                i+1,
                                shortTeamName + "(" + League.getPositionInLeague(teamName, tournamentTable) + ")",
                                (int) dataArrayThis[3][i] + " / " + (int) dataArrayOpponent[3][i],
                                arrayOpponents[i].substring(0,3) + arrayOpponents[i].substring(6,arrayOpponents[i].length()),
                        };
                    } else {
                        data[i] = new Object[]{
                                i+1,
                                arrayOpponents[i].substring(0,3) + arrayOpponents[i].substring(6,arrayOpponents[i].length()),
                                (int) dataArrayThis[3][i] + " / " + (int) dataArrayOpponent[3][i],
                                shortTeamName + "(" + League.getPositionInLeague(teamName, tournamentTable) + ")"
                        };
                    }
                }
                break;
            }
            case 4:{
                colHeads = new Object[]{"Номер", "Хозяева", shortTeamName + ": Пр.Г.мен. / Кол.мен.", "Гости"};
                data = new Object[arrayOpponents.length][colHeads.length];
                for (int i=0; i<arrayOpponents.length; i++){
                    if (arrayOpponents[i].contains("(H)")){
                        data[i] = new Object[]{
                                i+1,
                                shortTeamName + "(" + League.getPositionInLeague(teamName, tournamentTable) + ")",
                                (int) dataArrayThis[4][i] + " / " + (int) dataArrayOpponent[4][i],
                                arrayOpponents[i].substring(0,3) + arrayOpponents[i].substring(6,arrayOpponents[i].length()),
                        };
                    } else {
                        data[i] = new Object[]{
                                i+1,
                                arrayOpponents[i].substring(0,3) + arrayOpponents[i].substring(6,arrayOpponents[i].length()),
                                (int) dataArrayThis[4][i] + " / " + (int) dataArrayOpponent[4][i],
                                shortTeamName + "(" + League.getPositionInLeague(teamName, tournamentTable) + ")"
                        };
                    }
                }
                break;
            }
            case 5:{
                colHeads = new Object[]{"Номер", "Хозяева", "Время в атаке", "Гости", "Тотал", "Разница"};
                data = new Object[arrayOpponents.length][colHeads.length];
                for (int i=0; i<arrayOpponents.length; i++){
                    if (arrayOpponents[i].contains("(H)")){
                        String timeAttackHT = Settings.getTimeInMinutesAndSecondsFromMinutes(dataArrayThis[5][i]);
                        String timeAttackAT = Settings.getTimeInMinutesAndSecondsFromMinutes(dataArrayOpponent[5][i]);
                        String timeAttackDiff;
                        if (dataArrayThis[5][i] >= dataArrayOpponent[5][i]){
                            timeAttackDiff = Settings.getTimeInMinutesAndSecondsFromMinutes(dataArrayThis[5][i] - dataArrayOpponent[5][i]);
                        } else {
                            timeAttackDiff =  "-" + Settings.getTimeInMinutesAndSecondsFromMinutes(dataArrayOpponent[5][i] - dataArrayThis[5][i]);
                        }
                        String timeAttackTotal = Settings.getTimeInMinutesAndSecondsFromMinutes(dataArrayThis[5][i] + dataArrayOpponent[5][i]);
                        data[i] = new Object[]{
                                i+1,
                                shortTeamName + "(" + League.getPositionInLeague(teamName, tournamentTable) + ")",
                                timeAttackHT + " : " + timeAttackAT,
                                arrayOpponents[i].substring(0,3) + arrayOpponents[i].substring(6,arrayOpponents[i].length()),
                                timeAttackTotal,
                                timeAttackDiff
                        };
                    } else {
                        String timeAttackHT = Settings.getTimeInMinutesAndSecondsFromMinutes(dataArrayOpponent[5][i]);
                        String timeAttackAT = Settings.getTimeInMinutesAndSecondsFromMinutes(dataArrayThis[5][i]);
                        String timeAttackDiff;
                        if (dataArrayThis[5][i] >= dataArrayOpponent[5][i]){
                            timeAttackDiff = Settings.getTimeInMinutesAndSecondsFromMinutes(dataArrayThis[5][i] - dataArrayOpponent[5][i]);
                        } else {
                            timeAttackDiff =  "-" + Settings.getTimeInMinutesAndSecondsFromMinutes(dataArrayOpponent[5][i] - dataArrayThis[5][i]);
                        }
                        String timeAttackTotal = Settings.getTimeInMinutesAndSecondsFromMinutes(dataArrayThis[5][i] + dataArrayOpponent[5][i]);
                        data[i] = new Object[]{
                                i+1,
                                arrayOpponents[i].substring(0,3) + arrayOpponents[i].substring(6,arrayOpponents[i].length()),
                                timeAttackHT + " : " + timeAttackAT,
                                shortTeamName + "(" + League.getPositionInLeague(teamName, tournamentTable) + ")",
                                timeAttackTotal,
                                timeAttackDiff
                        };
                    }
                }
                break;
            }
            default:{
                colHeads = new Object[]{"Номер", "Хозяева", "Счет", "Гости", "Тотал", "Разница"};
                data = new Object[arrayOpponents.length][colHeads.length];
                for (int i=0; i<arrayOpponents.length; i++){
                    if (arrayOpponents[i].contains("(H)")){
                        data[i] = new Object[]{
                                i+1,
                                shortTeamName + "(" + League.getPositionInLeague(teamName, tournamentTable) + ")",
                                String.valueOf((int) dataArrayThis[index][i]) + " : " + String.valueOf((int) dataArrayOpponent[index][i]),
                                arrayOpponents[i].substring(0,3) + arrayOpponents[i].substring(6,arrayOpponents[i].length()),
                                String.valueOf((int) dataArrayTotal[index][i]),
                                String.valueOf((int) dataArrayThis[index][i] - (int) dataArrayOpponent[index][i])
                        };
                    } else {
                        data[i] = new Object[]{
                                i+1,
                                arrayOpponents[i].substring(0,3) + arrayOpponents[i].substring(6,arrayOpponents[i].length()),
                                String.valueOf((int) dataArrayOpponent[index][i]) + " : " + String.valueOf((int) dataArrayThis[index][i]),
                                shortTeamName + "(" + League.getPositionInLeague(teamName, tournamentTable) + ")",
                                String.valueOf((int) dataArrayTotal[index][i]),
                                String.valueOf((int) dataArrayThis[index][i] - (int) dataArrayOpponent[index][i])
                        };
                    }
                }
            }
        }

        final Font fontLabel = new Font("Arial", Font.BOLD, 15);
        JTable table = new JTable(data, colHeads);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setEnabled(false);
        table.getTableHeader().setFont(fontLabel);
        table.setFont(fontLabel);
        table.setRowHeight(25);
        table.setBackground(new Color(238, 238, 238));
        table.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
        Renderer renderer = new Renderer(Team.getShortName(teamName), 4, index);
        for (int t=0; t<table.getColumnCount(); t++){
            table.getColumnModel().getColumn(t).setCellRenderer(renderer);
        }
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(table);
        tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
        result.add(tablePanel);

        return result;
    }

    public JPanel getPanelWithAdvancedTable(double[][] dataArrayThis, double[][] dataArrayOpponent, double[][] dataArrayTotal,
                                    String[] arrayOpponents, int index, String title, ArrayList<String> tournamentTable){
        JPanel result = new JPanel(new BorderLayout());

        JLabel headerText = new JLabel(title);
        headerText.setFont(new Font("", 0, 18));
        result.add(headerText, BorderLayout.NORTH);

        Object[] colHeads;
        Object[][] data;

        colHeads = new Object[]{"Номер", "Хозяева", "Счет", "Гости", "Тотал", "Разница"};
        data = new Object[arrayOpponents.length][colHeads.length];
        for (int i=0; i<arrayOpponents.length; i++){
            if (arrayOpponents[i].contains("(H)")){
                data[i] = new Object[]{
                        i+1,
                        shortTeamName + "(" + League.getPositionInLeague(teamName, tournamentTable) + ")",
                        String.valueOf((int) dataArrayThis[index][i]) + " : " + String.valueOf((int) dataArrayOpponent[index][i]),
                        arrayOpponents[i].substring(0,3) + arrayOpponents[i].substring(6,arrayOpponents[i].length()),
                        String.valueOf((int) dataArrayTotal[index][i]),
                        String.valueOf((int) dataArrayThis[index][i] - (int) dataArrayOpponent[index][i])
                };
            } else {
                data[i] = new Object[]{
                        i+1,
                        arrayOpponents[i].substring(0,3) + arrayOpponents[i].substring(6,arrayOpponents[i].length()),
                        String.valueOf((int) dataArrayOpponent[index][i]) + " : " + String.valueOf((int) dataArrayThis[index][i]),
                        shortTeamName + "(" + League.getPositionInLeague(teamName, tournamentTable) + ")",
                        String.valueOf((int) dataArrayTotal[index][i]),
                        String.valueOf((int) dataArrayThis[index][i] - (int) dataArrayOpponent[index][i])
                };
            }
        }

        final Font fontLabel = new Font("Arial", Font.BOLD, 15);
        JTable table = new JTable(data, colHeads);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setEnabled(false);
        table.getTableHeader().setFont(fontLabel);
        table.setFont(fontLabel);
        table.setRowHeight(25);
        table.setBackground(new Color(238, 238, 238));
        table.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
        Renderer renderer = new Renderer(Team.getShortName(teamName), 4, index);
        for (int t=0; t<table.getColumnCount(); t++){
            table.getColumnModel().getColumn(t).setCellRenderer(renderer);
        }
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(table);
        tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
        result.add(tablePanel);

        return result;
    }

    public JPanel getGraphicsForShotsOnTarget(String teamName, String allOrHomeOrAway, String lastOrFullSeason, ArrayList<Match> listOfMatches, ArrayList<Selector> opponentsList, Selector selector){
        int numberOfMatchesLimit = 12;

        this.settings = Settings.getSettingsFromFile();
        this.teamName = teamName;
        titles = new String[]{"Броски в створ",
                "Броски в створ и ср. кол-во полученных соперником бросков",
                "Броски противника и ср. кол-во нанесенных бросков противника.",
                "Тотал бросков и ср. тотал в матча противника.",
                "Броски в створ 1 пер.",
                "Броски в створ 2 пер.",
                "Броски в створ 3 пер."};
        double[][] dataArrayThis = new double[titles.length][listOfMatches.size()];
        double[][] dataArrayOpponent = new double[titles.length][listOfMatches.size()];
        double[][] dataArrayTotal = new double[titles.length][listOfMatches.size()];
        String[] arrayOpponents = new String[listOfMatches.size()];
        for (int i=0; i<listOfMatches.size(); i++){
            Match m = listOfMatches.get(i);
            if (teamName.equals(m.homeTeam)){
                dataArrayThis[0][i] = m.homeShotsOnTarget;
                dataArrayThis[1][i] = m.homeShotsOnTarget;
                dataArrayThis[2][i] = m.awayShotsOnTarget;
                dataArrayThis[3][i] = m.homeShotsOnTarget + m.awayShotsOnTarget;

                dataArrayThis[4][i] = m.homeShotsOnTarget1stPeriod;
                dataArrayThis[5][i] = m.homeShotsOnTarget2ndPeriod;
                dataArrayThis[6][i] = m.homeShotsOnTarget3rdPeriod;

                dataArrayOpponent[0][i] = m.awayShotsOnTarget;
                if (opponentsList != null){
                    dataArrayOpponent[1][i] = Double.parseDouble(opponentsList.get(i).pList.get(7).get(2));
                    dataArrayOpponent[2][i] = Double.parseDouble(opponentsList.get(i).pList.get(7).get(1));
                    dataArrayOpponent[3][i] = Double.parseDouble(opponentsList.get(i).pList.get(7).get(1)) + Double.parseDouble(opponentsList.get(i).pList.get(7).get(2));
                }
                dataArrayOpponent[4][i] = m.awayShotsOnTarget1stPeriod;
                dataArrayOpponent[5][i] = m.awayShotsOnTarget2ndPeriod;
                dataArrayOpponent[6][i] = m.awayShotsOnTarget3rdPeriod;

                dataArrayTotal[0][i] = m.homeShotsOnTarget + m.awayShotsOnTarget;
                dataArrayTotal[1][i] = 0;
                dataArrayTotal[2][i] = 0;
                dataArrayTotal[3][i] = 0;
                dataArrayTotal[4][i] = m.homeShotsOnTarget1stPeriod + m.awayShotsOnTarget1stPeriod;
                dataArrayTotal[5][i] = m.homeShotsOnTarget2ndPeriod + m.awayShotsOnTarget2ndPeriod;
                dataArrayTotal[6][i] = m.homeShotsOnTarget3rdPeriod + m.awayShotsOnTarget3rdPeriod;

                arrayOpponents[i] = Team.getShortName(m.awayTeam) + "(H)" + m.date.split("\\.")[0] + "." + m.date.split("\\.")[1];

            } else if(teamName.equals(m.awayTeam)) {
                dataArrayThis[0][i] = m.awayShotsOnTarget;
                dataArrayThis[1][i] = m.awayShotsOnTarget;
                dataArrayThis[2][i] = m.homeShotsOnTarget;
                dataArrayThis[3][i] = m.awayShotsOnTarget + m.homeShotsOnTarget;

                dataArrayThis[4][i] = m.awayShotsOnTarget1stPeriod;
                dataArrayThis[5][i] = m.awayShotsOnTarget2ndPeriod;
                dataArrayThis[6][i] = m.awayShotsOnTarget3rdPeriod;

                dataArrayOpponent[0][i] = m.homeShotsOnTarget;
                if (opponentsList != null){
                    dataArrayOpponent[1][i] = Double.parseDouble(opponentsList.get(i).pList.get(7).get(2));
                    dataArrayOpponent[2][i] = Double.parseDouble(opponentsList.get(i).pList.get(7).get(1));
                    dataArrayOpponent[3][i] = Double.parseDouble(opponentsList.get(i).pList.get(7).get(1)) + Double.parseDouble(opponentsList.get(i).pList.get(7).get(2));
                }
                dataArrayOpponent[4][i] = m.homeShotsOnTarget1stPeriod;
                dataArrayOpponent[5][i] = m.homeShotsOnTarget2ndPeriod;
                dataArrayOpponent[6][i] = m.homeShotsOnTarget3rdPeriod;

                dataArrayTotal[0][i] = m.homeShotsOnTarget + m.awayShotsOnTarget;
                dataArrayTotal[1][i] = 0;
                dataArrayTotal[2][i] = 0;
                dataArrayTotal[3][i] = 0;
                dataArrayTotal[4][i] = m.homeShotsOnTarget1stPeriod + m.awayShotsOnTarget1stPeriod;
                dataArrayTotal[5][i] = m.homeShotsOnTarget2ndPeriod + m.awayShotsOnTarget2ndPeriod;
                dataArrayTotal[6][i] = m.homeShotsOnTarget3rdPeriod + m.awayShotsOnTarget3rdPeriod;

                arrayOpponents[i] = Team.getShortName(m.homeTeam) + "(A)" + m.date.split("\\.")[0] + "." + m.date.split("\\.")[1];
            }
        }
        ////////////////////////////ДО ЭТОГО МОМЕНТА ВСЕ ЗАПОЛНИЛОСЬ ХОРОШО, ТЕПЕРЬ ПОРА ДОБАВЛЯТЬ ГРАФИКИ.
        int index;
        JPanel content = new JPanel(new GridLayout(0, 1, 0, 0));

        for (index=0; index<dataArrayThis.length; index++){
            MAX = 0;
            MIN = 100;
            IntervalXYDataset dataSet;
            series1name = teamName;
            series2name = "Соперник";
            series3name = "Тотал";

            if (!settings.showTotal || index==1 || index==2 || index==3){
                if (index==1){
                    series1name = "Броски " + teamName;
                    series2name = "Среднее кол-во полученных соперником бросков";
                }
                if (index==2){
                    series1name = "Броски противника";
                    series2name = "Среднее кол-во нанесенных бросков противника";
                }
                if (index==3){
                    series1name =  "Тотал бросков";
                    series2name = "Ср. тотал в матчах противника";
                }
                dataSet = createDoubleDataSet(dataArrayThis, dataArrayOpponent, index, series1name, series2name);
            }
            else
                dataSet = createTripleDataSet(dataArrayThis, dataArrayOpponent, dataArrayTotal, index);

            final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
            renderer.setSeriesStroke(0, new BasicStroke(3f));
            renderer.setSeriesStroke(1, new BasicStroke(3f));
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

            JPanel panelWithGraphicAndTable = new JPanel(new BorderLayout());
            ChartPanel panel = new ChartPanel(chart);
            panelWithGraphicAndTable.add(panel);
            panel.setPreferredSize(new Dimension(963, graphicHeight));
            panel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
            /*switch (index){
                case 0:{
                    if (dataArrayThis[index][0] > 0)

                    break;
                }
                case 2:{
                    if (opponentsList != null)
                        content.add(panelWithGraphicAndTable);
                    break;
                }
                case 3:{
                    if (opponentsList != null)
                        content.add(panelWithGraphicAndTable);
                    break;
                }
                default:{
                    content.add(panelWithGraphicAndTable);
                    break;
                }
            }*/
            content.add(panelWithGraphicAndTable);
        }

        addTablesToShotsOnTargetGraphics(content, teamName, selector);

        return content;
    }

    public JPanel getGraphicsForPenalties(String teamName, String allOrHomeOrAway, ArrayList<Match> listOfMatches, ArrayList<Selector> opponentsList, Selector selector){
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
                    dataArrayOpponent[2][i] = MyMath.round(Double.parseDouble(opponentsList.get(i).pList.get(14).get(2)) / opponentsList.get(i).matches, 2);
                    dataArrayOpponent[3][i] = MyMath.round(Double.parseDouble(opponentsList.get(i).pList.get(14).get(1)) / opponentsList.get(i).matches, 2);
                }
                dataArrayOpponent[4][i] = m.awayPenaltyMinutes1stPeriod;
                dataArrayOpponent[5][i] = m.awayPenaltyMinutes2ndPeriod;
                dataArrayOpponent[6][i] = m.awayPenaltyMinutes3rdPeriod;
                dataArrayOpponent[7][i] = m.away2MinPenalties;
                if (opponentsList != null){
                    dataArrayOpponent[8][i] = MyMath.round(Double.parseDouble(opponentsList.get(i).pList.get(15).get(2)) / opponentsList.get(i).matches, 2);
                    dataArrayOpponent[9][i] = MyMath.round(Double.parseDouble(opponentsList.get(i).pList.get(15).get(1)) / opponentsList.get(i).matches, 2);
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
                    dataArrayOpponent[2][i] = MyMath.round(Double.parseDouble(opponentsList.get(i).pList.get(14).get(2)) / opponentsList.get(i).matches, 2);
                    dataArrayOpponent[3][i] = MyMath.round(Double.parseDouble(opponentsList.get(i).pList.get(14).get(1)) / opponentsList.get(i).matches, 2);
                }
                dataArrayOpponent[4][i] = m.homePenaltyMinutes1stPeriod;
                dataArrayOpponent[5][i] = m.homePenaltyMinutes2ndPeriod;
                dataArrayOpponent[6][i] = m.homePenaltyMinutes3rdPeriod;
                dataArrayOpponent[7][i] = m.home2MinPenalties;
                if (opponentsList != null){
                    dataArrayOpponent[8][i] = MyMath.round(Double.parseDouble(opponentsList.get(i).pList.get(15).get(2)) / opponentsList.get(i).matches, 2);
                    dataArrayOpponent[9][i] = MyMath.round(Double.parseDouble(opponentsList.get(i).pList.get(15).get(1)) / opponentsList.get(i).matches, 2);
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
        JPanel content = new JPanel(new GridLayout(0, 1, 0, 0));

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

            JPanel panelWithGraphicAndTable = new JPanel(new BorderLayout());
            ChartPanel panel = new ChartPanel(chart);
            panelWithGraphicAndTable.add(panel);
            panel.setPreferredSize(new Dimension(963, graphicHeight));
            panel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));

            content.add(panelWithGraphicAndTable);
        }

        addTablesToPenalties(content, teamName, selector);

        return content;
    }

}