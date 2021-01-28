package sample;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.general.DefaultKeyedValues2DDataset;
import org.jfree.data.general.KeyedValues2DDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.jfree.ui.tabbedui.VerticalLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

public class WindowMatchStats extends JFrame{
    Settings settings = Settings.getSettingsFromFile();

    public WindowMatchStats(Match match){
        super(match.homeTeam + " - " + match.awayTeam + " (" + match.date + ")");
        //this.setResizable(false);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 550;
        int height = 800;
        if (screenSize.width < width)
            width = screenSize.width;
        if (screenSize.height < height)
            height = screenSize.height;

        setSize(width,height);
        setLocation(0, 0);
        this.setLayout(new BorderLayout());
        JPanel headPanel = new JPanel(new BorderLayout());

        KeyedValues2DDataset dataset;
        JFreeChart jfreechart;

        File fileHTI = new File("images/" + match.homeTeam + ".png");
        File fileATI = new File("images/" + match.awayTeam + ".png");
        BufferedImage bimg = null;
        try {
            bimg = ImageIO.read(fileHTI);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image scaled = bimg.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel homeTeamImage = new JLabel(new ImageIcon(scaled));

        try {
            bimg = ImageIO.read(fileATI);
        } catch (IOException e) {
            e.printStackTrace();
        }
        scaled = bimg.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel awayTeamImage = new JLabel(new ImageIcon(scaled));
        headPanel.add(homeTeamImage, BorderLayout.WEST);
        headPanel.add(awayTeamImage, BorderLayout.EAST);

        JPanel scorePanel = new JPanel(new BorderLayout());
        JLabel labelEnded = new JLabel("Завершен");
        labelEnded.setFont(new Font("", 1, 15));
        labelEnded.setHorizontalAlignment(SwingConstants.CENTER);
        scorePanel.add(labelEnded, BorderLayout.NORTH);

        String s = "";
        if (match.homeScore == match.awayScore){
            if (match.homeScoreOT == 1)
                s = String.valueOf(match.homeScore+1) + " : " + match.awayScore + " OT";
            if (match.homeScoreBullits == 1)
                s = String.valueOf(match.homeScore+1) + " : " + match.awayScore + " Б";
            if (match.awayScoreOT == 1)
                s = match.homeScore + " : " + String.valueOf(match.awayScore+1) + " OT";
            if (match.awayScoreBullits == 1)
                s = match.homeScore + " : " + String.valueOf(match.awayScore+1) + " Б";
        } else
            s = match.homeScore + " : " + match.awayScore;

        JLabel labelScore = new JLabel(s);
        labelScore.setFont(new Font("", 1, 70));
        labelScore.setHorizontalAlignment(SwingConstants.CENTER);
        scorePanel.add(labelScore);

        JLabel labelByPeriods = new JLabel(match.homeScore1stPeriod + " : " + match.awayScore1stPeriod + "   |   "
                + match.homeScore2ndPeriod + " : " + match.awayScore2ndPeriod + "   |   "
                + match.homeScore3rdPeriod + " : " + match.awayScore3rdPeriod);
        labelByPeriods.setFont(new Font("", 1, 20));
        labelByPeriods.setHorizontalAlignment(SwingConstants.CENTER);
        scorePanel.add(labelByPeriods, BorderLayout.SOUTH);

        headPanel.add(scorePanel);
        headPanel.setBorder(BorderFactory.createTitledBorder(""));
        this.add(headPanel, BorderLayout.NORTH);

//        JPanel panelStats = new JPanel(new VerticalLayout());

        JTabbedPane jtp = new JTabbedPane(JTabbedPane.TOP,
                JTabbedPane.SCROLL_TAB_LAYOUT);
        JPanel panelStatsAll = new JPanel(new VerticalLayout());

        double homeCorsi = MyMath.getCorsi(match.homeShotsOnTarget, match.homeMissedShots, match.awayBlockedShots);
        double awayCorsi = MyMath.getCorsi(match.awayShotsOnTarget, match.awayMissedShots, match.homeBlockedShots);
        double homeCorsiForPercent = MyMath.round( 100 * homeCorsi / (homeCorsi+awayCorsi), 2);
        double awayCorsiForPercent = MyMath.round( 100 * awayCorsi / (homeCorsi+awayCorsi), 2);

        double homeFenwick = MyMath.getFenwick(match.homeShotsOnTarget, match.homeMissedShots);
        double awayFenwick = MyMath.getFenwick(match.awayShotsOnTarget, match.awayMissedShots);
        double homeFenwickForPercent = MyMath.round( 100 * homeFenwick / (homeFenwick+awayFenwick), 2);
        double awayFenwickForPercent = MyMath.round( 100 * awayFenwick / (homeFenwick+awayFenwick), 2);

        double[] argsHT = {match.homeTimeInAttack, match.homeShotsOnTarget, MyMath.round(match.homeScore / (double) match.homeShotsOnTarget * 100, 2),
                match.homeMissedShots, match.homeGoalsInPP, MyMath.round(100 * match.homeGoalsInPP / (double) match.homeNumberOfPP, 2), match.homeNumberOfPP,
                match.homeShorthandedGoals, match.homeFaceoffsWon, match.homeBlockedShots, match.homeHits, match.home2MinPenalties, match.homePenaltyMinutes,
                homeCorsi, homeCorsiForPercent, homeFenwick, homeFenwickForPercent
        };
        double[] argsAT = {match.awayTimeInAttack, match.awayShotsOnTarget, MyMath.round(match.awayScore / (double) match.awayShotsOnTarget * 100, 2),
                match.awayMissedShots, match.awayGoalsInPP, MyMath.round(100 * match.awayGoalsInPP / (double) match.awayNumberOfPP, 2), match.awayNumberOfPP,
                match.awayShorthandedGoals, match.awayFaceoffsWon, match.awayBlockedShots, match.awayHits, match.away2MinPenalties, match.awayPenaltyMinutes,
                awayCorsi, awayCorsiForPercent, awayFenwick, awayFenwickForPercent
        };
        String[] labelTitles = {"Время в атаке", "Броски в створ", "Реализация бросков, %", "Броски мимо", "Голов в большинстве",
                "Реализация большинства, %", "Количество большинства", "Голы в меньшинстве", "Вбрасывания", "Блокировано бросков",
                "Силовые приемы", "2 мин. удаления", "Штрафное время", "Corsi", "CorsiFor%", "Fenwick", "FenwickFor%"};
        for (int i = 0; i<argsHT.length; i++){
            JPanel panelParameter = new JPanel(new BorderLayout());
            JLabel labelParameter = new JLabel(labelTitles[i]);
            labelParameter.setHorizontalAlignment(SwingConstants.CENTER);
            panelParameter.add(labelParameter, BorderLayout.NORTH);
            JLabel labelHT = new JLabel("");
            JLabel labelAT = new JLabel("");

            switch (i){
                case 0:{
                    labelHT.setText(String.valueOf((int) argsHT[i] / 60) + ":" + String.valueOf((int) argsHT[i] % 60));
                    labelAT.setText(String.valueOf((int) argsAT[i] / 60) + ":" + String.valueOf((int) argsAT[i] % 60));
                    break;
                }
                case 2:
                case 5:
                case 13:
                case 14:
                case 15:
                case 16:
                {
                    labelHT.setText(String.valueOf(argsHT[i]));
                    labelAT.setText(String.valueOf(argsAT[i]));
                    break;
                }
                default:{
                    labelHT.setText(String.valueOf((int) argsHT[i]));
                    labelAT.setText(String.valueOf((int) argsAT[i]));
                    break;
                }

            }

            labelHT.setPreferredSize(new Dimension(50, 15));
            labelHT.setHorizontalAlignment(SwingConstants.CENTER);
            panelParameter.add(labelHT, BorderLayout.WEST);
            labelAT.setPreferredSize(new Dimension(50, 15));
            labelAT.setHorizontalAlignment(SwingConstants.CENTER);
            panelParameter.add(labelAT, BorderLayout.EAST);

            dataset = createDataset(argsHT[i], argsAT[i]);
            jfreechart = ChartFactory.createStackedBarChart("", "", "", dataset, PlotOrientation.HORIZONTAL, false, false, false);
            jfreechart.setBackgroundPaint(new Color(238, 238, 238));
            CategoryPlot plot = (CategoryPlot) jfreechart.getPlot();
            plot.setBackgroundPaint(new Color(238, 238, 238));
            plot.setInsets(new RectangleInsets(0, 0, 0, 0));
            plot.setOutlineVisible(false);
            plot.getDomainAxis().setVisible(false);
            plot.getRangeAxis().setVisible(false);
            ValueAxis rangeAxis = plot.getRangeAxis();         //getDomainAxis();
            double range = argsHT[i] + argsAT[i];
            if (range == 0)
                range = 1;
            rangeAxis.setRange(-range, range);
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setBarPainter(new StandardBarPainter());
            renderer.setSeriesPaint(0, new Color(100, 100, 100));
            renderer.setSeriesPaint(1, new Color(200, 200, 200));
            renderer.setSeriesPaint(2, new Color(100, 100, 100));
            renderer.setSeriesPaint(3, new Color(200, 200, 200));

            if (argsHT[i] > argsAT[i])
                renderer.setSeriesPaint(0, Color.RED);
            if (argsHT[i] < argsAT[i])
                renderer.setSeriesPaint(2, Color.RED);
            Marker marker = new ValueMarker(0);
            marker.setPaint(Color.black);
            marker.setStroke(new BasicStroke(2));
            plot.addRangeMarker(marker);
            marker = new ValueMarker(0.01);
            marker.setPaint(Color.WHITE);
            marker.setStroke(new BasicStroke(1.5f));
            plot.addRangeMarker(marker);
            marker = new ValueMarker(-0.01);
            marker.setPaint(Color.WHITE);
            marker.setStroke(new BasicStroke(1.5f));
            plot.addRangeMarker(marker);

            ChartPanel chartpanel = new ChartPanel(jfreechart);
            chartpanel.setPreferredSize(new Dimension(400, 15));
            chartpanel.setRangeZoomable(false);
            panelParameter.add(chartpanel, BorderLayout.CENTER);
            panelStatsAll.add(panelParameter);
        }

        JScrollPane jsp = new JScrollPane(panelStatsAll);
        jtp.add("Весь матч", jsp);

        if (! (match.date.split("\\.")[2].contains("2017") || (match.date.split("\\.")[2].contains("2018") && Integer.parseInt(match.date.split("\\.")[1]) < 8))){
            JPanel panelStats1Per = new JPanel(new VerticalLayout());
            JPanel panelStats2Per = new JPanel(new VerticalLayout());
            JPanel panelStats3Per = new JPanel(new VerticalLayout());

            double[] argsHT1Per = {match.homeTimeInAttack1stPeriod, match.homeShotsOnTarget1stPeriod, MyMath.round(match.homeScore1stPeriod / (double) match.homeShotsOnTarget1stPeriod * 100, 2),
                    match.home2MinPenalties1stPeriod, match.homePenaltyMinutes1stPeriod
            };
            double[] argsAT1Per = {match.awayTimeInAttack1stPeriod, match.awayShotsOnTarget1stPeriod, MyMath.round(match.awayScore1stPeriod / (double) match.awayShotsOnTarget1stPeriod * 100, 2),
                    match.away2MinPenalties1stPeriod, match.awayPenaltyMinutes1stPeriod
            };
            String[] labelTitles1Per = {"Время в атаке", "Броски в створ", "Реализация бросков, %", "2 мин. удаления", "Штрафное время"};
            for (int i = 0; i<argsHT1Per.length; i++){
                JPanel panelParameter = new JPanel(new BorderLayout());
                JLabel labelParameter = new JLabel(labelTitles1Per[i]);
                labelParameter.setHorizontalAlignment(SwingConstants.CENTER);
                panelParameter.add(labelParameter, BorderLayout.NORTH);
                JLabel labelHT = new JLabel("");
                JLabel labelAT = new JLabel("");

                switch (i){
                    case 0:{
                        labelHT.setText(String.valueOf((int) argsHT1Per[i] / 60) + ":" + String.valueOf((int) argsHT1Per[i] % 60));
                        labelAT.setText(String.valueOf((int) argsAT1Per[i] / 60) + ":" + String.valueOf((int) argsAT1Per[i] % 60));
                        break;
                    }
                    case 2:{
                        labelHT.setText(String.valueOf(argsHT1Per[i]));
                        labelAT.setText(String.valueOf(argsAT1Per[i]));
                        break;
                    }
                    default:{
                        labelHT.setText(String.valueOf((int) argsHT1Per[i]));
                        labelAT.setText(String.valueOf((int) argsAT1Per[i]));
                        break;
                    }
                }

                labelHT.setPreferredSize(new Dimension(50, 15));
                labelHT.setHorizontalAlignment(SwingConstants.CENTER);
                panelParameter.add(labelHT, BorderLayout.WEST);
                labelAT.setPreferredSize(new Dimension(50, 15));
                labelAT.setHorizontalAlignment(SwingConstants.CENTER);
                panelParameter.add(labelAT, BorderLayout.EAST);

                dataset = createDataset(argsHT1Per[i], argsAT1Per[i]);
                jfreechart = ChartFactory.createStackedBarChart("", "", "", dataset, PlotOrientation.HORIZONTAL, false, false, false);
                jfreechart.setBackgroundPaint(new Color(238, 238, 238));
                CategoryPlot plot = (CategoryPlot) jfreechart.getPlot();
                plot.setBackgroundPaint(new Color(238, 238, 238));
                plot.setInsets(new RectangleInsets(0, 0, 0, 0));
                plot.setOutlineVisible(false);
                plot.getDomainAxis().setVisible(false);
                plot.getRangeAxis().setVisible(false);
                ValueAxis rangeAxis = plot.getRangeAxis();         //getDomainAxis();
                double range = argsHT1Per[i] + argsAT1Per[i];
                if (range == 0)
                    range = 1;
                rangeAxis.setRange(-range, range);
                BarRenderer renderer = (BarRenderer) plot.getRenderer();
                renderer.setBarPainter(new StandardBarPainter());
                renderer.setSeriesPaint(0, new Color(100, 100, 100));
                renderer.setSeriesPaint(1, new Color(200, 200, 200));
                renderer.setSeriesPaint(2, new Color(100, 100, 100));
                renderer.setSeriesPaint(3, new Color(200, 200, 200));

                if (argsHT1Per[i] > argsAT1Per[i])
                    renderer.setSeriesPaint(0, Color.RED);
                if (argsHT1Per[i] < argsAT1Per[i])
                    renderer.setSeriesPaint(2, Color.RED);
                Marker marker = new ValueMarker(0);
                marker.setPaint(Color.black);
                marker.setStroke(new BasicStroke(2));
                plot.addRangeMarker(marker);
                marker = new ValueMarker(0.01);
                marker.setPaint(Color.WHITE);
                marker.setStroke(new BasicStroke(1.5f));
                plot.addRangeMarker(marker);
                marker = new ValueMarker(-0.01);
                marker.setPaint(Color.WHITE);
                marker.setStroke(new BasicStroke(1.5f));
                plot.addRangeMarker(marker);

                ChartPanel chartpanel = new ChartPanel(jfreechart);
                chartpanel.setPreferredSize(new Dimension(400, 15));
                chartpanel.setRangeZoomable(false);
                panelParameter.add(chartpanel, BorderLayout.CENTER);
                panelStats1Per.add(panelParameter);
            }
            jsp = new JScrollPane(panelStats1Per);
            jtp.add("1 период", jsp);

            double[] argsHT2Per = {match.homeTimeInAttack2ndPeriod, match.homeShotsOnTarget2ndPeriod, MyMath.round(match.homeScore2ndPeriod / (double) match.homeShotsOnTarget2ndPeriod * 100, 2),
                    match.home2MinPenalties2ndPeriod, match.homePenaltyMinutes2ndPeriod
            };
            double[] argsAT2Per = {match.awayTimeInAttack2ndPeriod, match.awayShotsOnTarget2ndPeriod, MyMath.round(match.awayScore2ndPeriod / (double) match.awayShotsOnTarget2ndPeriod * 100, 2),
                    match.away2MinPenalties2ndPeriod, match.awayPenaltyMinutes2ndPeriod
            };
            String[] labelTitles2Per = {"Время в атаке", "Броски в створ", "Реализация бросков, %", "2 мин. удаления", "Штрафное время"};
            for (int i = 0; i<argsHT2Per.length; i++){
                JPanel panelParameter = new JPanel(new BorderLayout());
                JLabel labelParameter = new JLabel(labelTitles2Per[i]);
                labelParameter.setHorizontalAlignment(SwingConstants.CENTER);
                panelParameter.add(labelParameter, BorderLayout.NORTH);
                JLabel labelHT = new JLabel("");
                JLabel labelAT = new JLabel("");

                switch (i){
                    case 0:{
                        labelHT.setText(String.valueOf((int) argsHT2Per[i] / 60) + ":" + String.valueOf((int) argsHT2Per[i] % 60));
                        labelAT.setText(String.valueOf((int) argsAT2Per[i] / 60) + ":" + String.valueOf((int) argsAT2Per[i] % 60));
                        break;
                    }
                    case 2:{
                        labelHT.setText(String.valueOf(argsHT2Per[i]));
                        labelAT.setText(String.valueOf(argsAT2Per[i]));
                        break;
                    }
                    default:{
                        labelHT.setText(String.valueOf((int) argsHT2Per[i]));
                        labelAT.setText(String.valueOf((int) argsAT2Per[i]));
                        break;
                    }
                }

                labelHT.setPreferredSize(new Dimension(50, 15));
                labelHT.setHorizontalAlignment(SwingConstants.CENTER);
                panelParameter.add(labelHT, BorderLayout.WEST);
                labelAT.setPreferredSize(new Dimension(50, 15));
                labelAT.setHorizontalAlignment(SwingConstants.CENTER);
                panelParameter.add(labelAT, BorderLayout.EAST);

                dataset = createDataset(argsHT2Per[i], argsAT2Per[i]);
                jfreechart = ChartFactory.createStackedBarChart("", "", "", dataset, PlotOrientation.HORIZONTAL, false, false, false);
                jfreechart.setBackgroundPaint(new Color(238, 238, 238));
                CategoryPlot plot = (CategoryPlot) jfreechart.getPlot();
                plot.setBackgroundPaint(new Color(238, 238, 238));
                plot.setInsets(new RectangleInsets(0, 0, 0, 0));
                plot.setOutlineVisible(false);
                plot.getDomainAxis().setVisible(false);
                plot.getRangeAxis().setVisible(false);
                ValueAxis rangeAxis = plot.getRangeAxis();         //getDomainAxis();
                double range = argsHT2Per[i] + argsAT2Per[i];
                if (range == 0)
                    range = 1;
                rangeAxis.setRange(-range, range);
                BarRenderer renderer = (BarRenderer) plot.getRenderer();
                renderer.setBarPainter(new StandardBarPainter());
                renderer.setSeriesPaint(0, new Color(100, 100, 100));
                renderer.setSeriesPaint(1, new Color(200, 200, 200));
                renderer.setSeriesPaint(2, new Color(100, 100, 100));
                renderer.setSeriesPaint(3, new Color(200, 200, 200));

                if (argsHT2Per[i] > argsAT2Per[i])
                    renderer.setSeriesPaint(0, Color.RED);
                if (argsHT2Per[i] < argsAT2Per[i])
                    renderer.setSeriesPaint(2, Color.RED);
                Marker marker = new ValueMarker(0);
                marker.setPaint(Color.black);
                marker.setStroke(new BasicStroke(2));
                plot.addRangeMarker(marker);
                marker = new ValueMarker(0.01);
                marker.setPaint(Color.WHITE);
                marker.setStroke(new BasicStroke(1.5f));
                plot.addRangeMarker(marker);
                marker = new ValueMarker(-0.01);
                marker.setPaint(Color.WHITE);
                marker.setStroke(new BasicStroke(1.5f));
                plot.addRangeMarker(marker);

                ChartPanel chartpanel = new ChartPanel(jfreechart);
                chartpanel.setPreferredSize(new Dimension(400, 15));
                chartpanel.setRangeZoomable(false);
                panelParameter.add(chartpanel, BorderLayout.CENTER);
                panelStats2Per.add(panelParameter);
            }
            jsp = new JScrollPane(panelStats2Per);
            jtp.add("2 период", jsp);

            //////////////////////************************************************

            double[] argsHT3Per = {match.homeTimeInAttack3rdPeriod, match.homeShotsOnTarget3rdPeriod, MyMath.round(match.homeScore3rdPeriod / (double) match.homeShotsOnTarget3rdPeriod * 100, 2),
                    match.home2MinPenalties3rdPeriod, match.homePenaltyMinutes3rdPeriod
            };
            double[] argsAT3Per = {match.awayTimeInAttack3rdPeriod, match.awayShotsOnTarget3rdPeriod, MyMath.round(match.awayScore3rdPeriod / (double) match.awayShotsOnTarget3rdPeriod * 100, 2),
                    match.away2MinPenalties3rdPeriod, match.awayPenaltyMinutes3rdPeriod
            };
            String[] labelTitles3Per = {"Время в атаке", "Броски в створ", "Реализация бросков, %", "2 мин. удаления", "Штрафное время"};
            for (int i = 0; i<argsHT3Per.length; i++){
                JPanel panelParameter = new JPanel(new BorderLayout());
                JLabel labelParameter = new JLabel(labelTitles3Per[i]);
                labelParameter.setHorizontalAlignment(SwingConstants.CENTER);
                panelParameter.add(labelParameter, BorderLayout.NORTH);
                JLabel labelHT = new JLabel("");
                JLabel labelAT = new JLabel("");

                switch (i){
                    case 0:{
                        labelHT.setText(String.valueOf((int) argsHT3Per[i] / 60) + ":" + String.valueOf((int) argsHT3Per[i] % 60));
                        labelAT.setText(String.valueOf((int) argsAT3Per[i] / 60) + ":" + String.valueOf((int) argsAT3Per[i] % 60));
                        break;
                    }
                    case 2:{
                        labelHT.setText(String.valueOf(argsHT3Per[i]));
                        labelAT.setText(String.valueOf(argsAT3Per[i]));
                        break;
                    }
                    default:{
                        labelHT.setText(String.valueOf((int) argsHT3Per[i]));
                        labelAT.setText(String.valueOf((int) argsAT3Per[i]));
                        break;
                    }
                }
                labelHT.setPreferredSize(new Dimension(50, 15));
                labelHT.setHorizontalAlignment(SwingConstants.CENTER);
                panelParameter.add(labelHT, BorderLayout.WEST);
                labelAT.setPreferredSize(new Dimension(50, 15));
                labelAT.setHorizontalAlignment(SwingConstants.CENTER);
                panelParameter.add(labelAT, BorderLayout.EAST);

                dataset = createDataset(argsHT3Per[i], argsAT3Per[i]);
                jfreechart = ChartFactory.createStackedBarChart("", "", "", dataset, PlotOrientation.HORIZONTAL, false, false, false);
                jfreechart.setBackgroundPaint(new Color(238, 238, 238));
                CategoryPlot plot = (CategoryPlot) jfreechart.getPlot();
                plot.setBackgroundPaint(new Color(238, 238, 238));
                plot.setInsets(new RectangleInsets(0, 0, 0, 0));
                plot.setOutlineVisible(false);
                plot.getDomainAxis().setVisible(false);
                plot.getRangeAxis().setVisible(false);
                ValueAxis rangeAxis = plot.getRangeAxis();         //getDomainAxis();
                double range = argsHT3Per[i] + argsAT3Per[i];
                if (range == 0)
                    range = 1;
                rangeAxis.setRange(-range, range);
                BarRenderer renderer = (BarRenderer) plot.getRenderer();
                renderer.setBarPainter(new StandardBarPainter());
                renderer.setSeriesPaint(0, new Color(100, 100, 100));
                renderer.setSeriesPaint(1, new Color(200, 200, 200));
                renderer.setSeriesPaint(2, new Color(100, 100, 100));
                renderer.setSeriesPaint(3, new Color(200, 200, 200));

                if (argsHT3Per[i] > argsAT3Per[i])
                    renderer.setSeriesPaint(0, Color.RED);
                if (argsHT3Per[i] < argsAT3Per[i])
                    renderer.setSeriesPaint(2, Color.RED);
                Marker marker = new ValueMarker(0);
                marker.setPaint(Color.black);
                marker.setStroke(new BasicStroke(2));
                plot.addRangeMarker(marker);
                marker = new ValueMarker(0.01);
                marker.setPaint(Color.WHITE);
                marker.setStroke(new BasicStroke(1.5f));
                plot.addRangeMarker(marker);
                marker = new ValueMarker(-0.01);
                marker.setPaint(Color.WHITE);
                marker.setStroke(new BasicStroke(1.5f));
                plot.addRangeMarker(marker);

                ChartPanel chartpanel = new ChartPanel(jfreechart);
                chartpanel.setPreferredSize(new Dimension(400, 15));
                chartpanel.setRangeZoomable(false);
                panelParameter.add(chartpanel, BorderLayout.CENTER);
                panelStats3Per.add(panelParameter);
            }
            jsp = new JScrollPane(panelStats3Per);
            jtp.add("3 период", jsp);
        }



        //==========================================-------------------------------------------------------
        this.add(jtp);
        if (settings.windowsOnTop)
            setAlwaysOnTop(true);
    }

    private KeyedValues2DDataset createDataset(double leftValue, double rightValue) {
        DefaultKeyedValues2DDataset defaultkeyedvalues2ddataset = new DefaultKeyedValues2DDataset();

        if (leftValue + rightValue == 0){
            defaultkeyedvalues2ddataset.addValue(0, "Negative", "row 1");
            defaultkeyedvalues2ddataset.addValue(-1, "NegativeSumm", "row 1");
            defaultkeyedvalues2ddataset.addValue(0, "Positive", "row 1");
            defaultkeyedvalues2ddataset.addValue(1, "PositiveSumm", "row 1");
        } else {
            defaultkeyedvalues2ddataset.addValue(-leftValue, "Negative", "row 1");
            defaultkeyedvalues2ddataset.addValue(-rightValue, "NegativeSumm", "row 1");
            defaultkeyedvalues2ddataset.addValue(rightValue, "Positive", "row 1");
            defaultkeyedvalues2ddataset.addValue(leftValue, "PositiveSumm", "row 1");
        }

        return defaultkeyedvalues2ddataset;
    }
}
