package sample;

import org.jfree.ui.tabbedui.VerticalLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;

public class TablesThread extends Thread{
    String path;
    String leagueName;
    String parameter;
    String seasonString;
    String lastOrFull;
    JFrame tw;
    PanelTablesByLeague pt;
    JProgressBar jpb;
    int numberOfTeams;

    public TablesThread(String leagueName, final String parameter, String seasonString, String lastOrFull, PanelTablesByLeague pt){
        path = "database/";
        this.leagueName = leagueName;
        this.parameter = parameter;
        this.seasonString = seasonString;
        this.lastOrFull = lastOrFull;
        this.pt = pt;
        numberOfTeams = Settings.getNumberOfTeamsInLeague(leagueName, seasonString);

        tw = new JFrame("Внимание!");
        tw.setResizable(false);
        tw.setLayout(new BorderLayout());
        tw.setSize(500, 200);
        tw.setLocation(200, 200);

        JLabel label = new JLabel("Подождите, идет расчет таблицы");
        label.setFont(new Font("", Font.BOLD, 20));
        tw.add(label, BorderLayout.NORTH);

        jpb = new JProgressBar(0, 100);
        jpb = new JProgressBar(0, 100);
        jpb.setPreferredSize(new Dimension(600, 50));
        jpb.setStringPainted(true);
        tw.add(jpb, BorderLayout.SOUTH);

        tw.setVisible(true);
    }

    @Override
    public void run(){
        JPanel arr = createTable(pt.leagueName, pt.parameter, pt.season, pt.lastOrFull);
        if (pt.panelWithTablesByLeague.getComponentCount() > 1) {
            pt.panelWithTablesByLeague.remove(1);
        }

        pt.panelWithTablesByLeague.add(arr);
        pt.panelWithTablesByLeague.revalidate();
        pt.revalidate();
        tw.setVisible(false);

    }

    public JPanel createTable(String leagueName, final String parameter, String season, String lastOrFull){
        JPanel result = new JPanel(new BorderLayout());

        if (parameter.contains("Выберите")){
            JPanel r = new JPanel();
            JLabel l = new JLabel("Не выбран показатель");
            l.setFont(new Font("", Font.BOLD,15));
            r.setAlignmentX(Component.CENTER_ALIGNMENT);
            r.add(l);
            return r;
        }

        String[] directoryList = new JFileChooser(path + season + "/leagues").getCurrentDirectory().list();

        if (!leagueName.contains("Выберите") && directoryList.length > 0){
            if (!lastOrFull.contains("Весь")){
                if (!(pt.lastCalculatedLeague.equals(leagueName) && pt.lastCalculatedSeason.equals(season) && pt.lastCalculatedLastOrFull.equals(lastOrFull))){
                    pt.league.resetTables(season, leagueName, lastOrFull, jpb);
                }
            }

            int indexOfParameter = 0;
            switch (parameter){
                case "Голы":{
                    indexOfParameter = 2;
                    break;
                }
                case "Голы с учетом ОТиБ":{
                    indexOfParameter = 3;
                    break;
                }
                case "Голы 1 пер":{
                    indexOfParameter = 4;
                    break;
                }
                case "Голы 2 пер":{
                    indexOfParameter = 5;
                    break;
                }
                case "Голы 3 пер":{
                    indexOfParameter = 6;
                    break;
                }
                case "Броски в створ":{
                    indexOfParameter = 7;
                    break;
                }
                case "Броски в створ 1 пер":{
                    indexOfParameter = 8;
                    break;
                }
                case "Броски в створ 2 пер":{
                    indexOfParameter = 9;
                    break;
                }
                case "Броски в створ 3 пер":{
                    indexOfParameter = 10;
                    break;
                }
                case "Реализация большинства, %":{
                    indexOfParameter = 11;
                    break;
                }
                case "Вбрасывания":{
                    indexOfParameter = 12;
                    break;
                }
                case "Время в атаке, сек.":{
                    indexOfParameter = 13;
                    break;
                }
                case "Блок.броски":{
                    indexOfParameter = 14;
                    break;
                }
                case "Сил приемы":{
                    indexOfParameter = 15;
                    break;
                }
                case "Минуты штрафа":{
                    indexOfParameter = 16;
                    break;
                }
                case "2мин удаления":{
                    indexOfParameter = 17;
                    break;
                }
                case "Бр. мимо":{
                    indexOfParameter = 18;
                    break;
                }
                default:{
                    // ветка доп.показателей, которые не храню в таблице по лиге
                    indexOfParameter = 20;
                }
            }

            ArrayList<ArrayList<Double>> data = new ArrayList<>();
            ArrayList<String> listOfTeams = new ArrayList<>();

            if (indexOfParameter <= 18){
                for (int i=0; i<pt.league.overallStatsTable.size(); i++){
                    listOfTeams.add(pt.league.overallStatsTable.get(i).split("\\*")[0]);
                    int matchesAll = Integer.parseInt(pt.league.overallStatsTable.get(i).split("\\*")[1]);
                    int matchesHome = Integer.parseInt(pt.league.homeStatsTable.get(i).split("\\*")[1]);
                    int matchesAway = Integer.parseInt(pt.league.awayStatsTable.get(i).split("\\*")[1]);

                    if (indexOfParameter == 11){ // реализация и так усреднена, ее не надо делить на кол-во матчей, поэтому ставим по 1 этим параметрам
                        matchesAll = 1;
                        matchesHome = 1;
                        matchesAway = 1;
                    }

                    ArrayList<Double> record = new ArrayList<>();

                    if (matchesAll > 0){
                        record.add(MyMath.round(Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[indexOfParameter].split("_")[0]) / (double) matchesAll,2));
                        record.add(MyMath.round(Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[indexOfParameter].split("_")[1]) / (double) matchesAll,2));
                        record.add(MyMath.round(Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[indexOfParameter].split("_")[2]) / (double) matchesAll,2));
                        record.add(MyMath.round(Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[indexOfParameter].split("_")[3]) / (double) matchesAll,2));
                    } else {
                        record.add(0.0);
                        record.add(0.0);
                        record.add(0.0);
                        record.add(0.0);
                    }
                    if (matchesHome > 0){
                        record.add(MyMath.round(Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[indexOfParameter].split("_")[0]) / (double) matchesHome,2));
                        record.add(MyMath.round(Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[indexOfParameter].split("_")[1]) / (double) matchesHome,2));
                        record.add(MyMath.round(Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[indexOfParameter].split("_")[2]) / (double) matchesHome,2));
                        record.add(MyMath.round(Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[indexOfParameter].split("_")[3]) / (double) matchesHome,2));
                    } else {
                        record.add(0.0);
                        record.add(0.0);
                        record.add(0.0);
                        record.add(0.0);
                    }
                    if (matchesAway > 0){
                        record.add(MyMath.round(Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[indexOfParameter].split("_")[0]) / (double) matchesAway,2));
                        record.add(MyMath.round(Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[indexOfParameter].split("_")[1]) / (double) matchesAway,2));
                        record.add(MyMath.round(Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[indexOfParameter].split("_")[2]) / (double) matchesAway,2));
                        record.add(MyMath.round(Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[indexOfParameter].split("_")[3]) / (double) matchesAway,2));
                    } else {
                        record.add(0.0);
                        record.add(0.0);
                        record.add(0.0);
                        record.add(0.0);
                    }
                    data.add(record);

                }
            } else {
                // ветка доп.показателей, которые не храню в таблице по лиге
                for (int i=0; i<pt.league.overallStatsTable.size(); i++){
                    listOfTeams.add(pt.league.overallStatsTable.get(i).split("\\*")[0]);
                    int matchesAll = Integer.parseInt(pt.league.overallStatsTable.get(i).split("\\*")[1]);
                    int matchesHome = Integer.parseInt(pt.league.homeStatsTable.get(i).split("\\*")[1]);
                    int matchesAway = Integer.parseInt(pt.league.awayStatsTable.get(i).split("\\*")[1]);

                    ArrayList<Double> record = new ArrayList<>();

                    double all1 = 0;
                    double all2 = 0;
                    double all3 = 0;
                    double all4 = 0;
                    double home1 = 0;
                    double home2 = 0;
                    double home3 = 0;
                    double home4 = 0;
                    double away1 = 0;
                    double away2 = 0;
                    double away3 = 0;
                    double away4 = 0;


                    switch (parameter){
                        case "Corsi":{
                            if (matchesAll > 0){
                                all1 = MyMath.round((Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[7].split("_")[0]) + Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[18].split("_")[0]) + Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[14].split("_")[1]))/ (double) matchesAll,2);
                                all2 = MyMath.round((Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[7].split("_")[1]) + Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[18].split("_")[1]) + Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[14].split("_")[0]))/ (double) matchesAll,2);
                                all3 = MyMath.round((Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[7].split("_")[2]) + Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[18].split("_")[2]) - Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[14].split("_")[2]))/ (double) matchesAll,2);
                                all4 = MyMath.round((Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[7].split("_")[3]) + Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[18].split("_")[3]) + Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[14].split("_")[3]))/ (double) matchesAll,2);
                            }

                            if (matchesHome > 0){
                                home1 = MyMath.round((Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[7].split("_")[0]) + Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[18].split("_")[0]) + Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[14].split("_")[1]))/ (double) matchesHome,2);
                                home2 = MyMath.round((Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[7].split("_")[1]) + Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[18].split("_")[1]) + Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[14].split("_")[0]))/ (double) matchesHome,2);
                                home3 = MyMath.round((Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[7].split("_")[2]) + Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[18].split("_")[2]) - Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[14].split("_")[2]))/ (double) matchesHome,2);
                                home4 = MyMath.round((Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[7].split("_")[3]) + Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[18].split("_")[3]) + Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[14].split("_")[3]))/ (double) matchesHome,2);
                            }
                            if (matchesAway > 0){
                                away1 = MyMath.round((Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[7].split("_")[0]) + Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[18].split("_")[0]) + Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[14].split("_")[1]))/ (double) matchesAway,2);
                                away2 = MyMath.round((Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[7].split("_")[1]) + Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[18].split("_")[1]) + Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[14].split("_")[0]))/ (double) matchesAway,2);
                                away3 = MyMath.round((Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[7].split("_")[2]) + Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[18].split("_")[2]) - Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[14].split("_")[2]))/ (double) matchesAway,2);
                                away4 = MyMath.round((Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[7].split("_")[3]) + Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[18].split("_")[3]) + Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[14].split("_")[3]))/ (double) matchesAway,2);
                            }
                            break;
                        }
                        case "CorsiFor, %":{
                            if (matchesAll > 0){
                                double cFor = MyMath.round((Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[7].split("_")[0]) + Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[18].split("_")[0]) + Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[14].split("_")[1]))/ (double) matchesAll,2);
                                double cAg  = MyMath.round((Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[7].split("_")[1]) + Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[18].split("_")[1]) + Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[14].split("_")[0]))/ (double) matchesAll,2);

                                double cForPercent = MyMath.round( 100 * cFor / (cFor + cAg) , 2);
                                double cAgPercent = MyMath.round( 100 * cAg / (cFor + cAg) , 2);

                                all1 = cForPercent;
                                all2 = cAgPercent;
                                all3 = MyMath.round(cForPercent - cAgPercent, 2);
                                all4 = 100;
                            }

                            if (matchesHome > 0){
                                double cFor = MyMath.round((Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[7].split("_")[0]) + Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[18].split("_")[0]) + Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[14].split("_")[1]))/ (double) matchesHome,2);
                                double cAg  = MyMath.round((Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[7].split("_")[1]) + Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[18].split("_")[1]) + Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[14].split("_")[0]))/ (double) matchesHome,2);

                                double cForPercent = MyMath.round( 100 * cFor / (cFor + cAg) , 2);
                                double cAgPercent = MyMath.round( 100 * cAg / (cFor + cAg) , 2);

                                home1 = cForPercent;
                                home2 = cAgPercent;
                                home3 = MyMath.round(cForPercent - cAgPercent, 2);
                                home4 = 100;

                            }
                            if (matchesAway > 0){
                                double cFor = MyMath.round((Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[7].split("_")[0]) + Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[18].split("_")[0]) + Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[14].split("_")[1]))/ (double) matchesAway,2);
                                double cAg  = MyMath.round((Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[7].split("_")[1]) + Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[18].split("_")[1]) + Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[14].split("_")[0]))/ (double) matchesAway,2);

                                double cForPercent = MyMath.round( 100 * cFor / (cFor + cAg) , 2);
                                double cAgPercent = MyMath.round( 100 * cAg / (cFor + cAg) , 2);

                                away1 = cForPercent;
                                away2 = cAgPercent;
                                away3 = MyMath.round(cForPercent - cAgPercent , 2);
                                away4 = 100;

                            }
                            break;
                        }
                        case "Fenwick":{
                            if (matchesAll > 0){
                                all1 = MyMath.round((Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[7].split("_")[0]) + Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[18].split("_")[0]) )/ (double) matchesAll,2);
                                all2 = MyMath.round((Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[7].split("_")[1]) + Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[18].split("_")[1]) )/ (double) matchesAll,2);
                                all3 = MyMath.round((Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[7].split("_")[2]) + Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[18].split("_")[2]) )/ (double) matchesAll,2);
                                all4 = MyMath.round((Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[7].split("_")[3]) + Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[18].split("_")[3]) )/ (double) matchesAll,2);
                            }

                            if (matchesHome > 0){
                                home1 = MyMath.round((Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[7].split("_")[0]) + Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[18].split("_")[0]) )/ (double) matchesHome,2);
                                home2 = MyMath.round((Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[7].split("_")[1]) + Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[18].split("_")[1]) )/ (double) matchesHome,2);
                                home3 = MyMath.round((Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[7].split("_")[2]) + Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[18].split("_")[2]) )/ (double) matchesHome,2);
                                home4 = MyMath.round((Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[7].split("_")[3]) + Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[18].split("_")[3]) )/ (double) matchesHome,2);
                            }
                            if (matchesAway > 0){
                                away1 = MyMath.round((Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[7].split("_")[0]) + Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[18].split("_")[0]) )/ (double) matchesAway,2);
                                away2 = MyMath.round((Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[7].split("_")[1]) + Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[18].split("_")[1]) )/ (double) matchesAway,2);
                                away3 = MyMath.round((Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[7].split("_")[2]) + Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[18].split("_")[2]) )/ (double) matchesAway,2);
                                away4 = MyMath.round((Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[7].split("_")[3]) + Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[18].split("_")[3]) )/ (double) matchesAway,2);
                            }
                            break;
                        }
                        case "FenwickFor, %":{
                            if (matchesAll > 0){
                                double cFor = MyMath.round((Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[7].split("_")[0]) + Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[18].split("_")[0]) )/ (double) matchesAll,2);
                                double cAg  = MyMath.round((Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[7].split("_")[1]) + Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[18].split("_")[1]) )/ (double) matchesAll,2);

                                double cForPercent = MyMath.round( 100 * cFor / (cFor + cAg) , 2);
                                double cAgPercent = MyMath.round( 100 * cAg / (cFor + cAg) , 2);

                                all1 = cForPercent;
                                all2 = cAgPercent;
                                all3 = MyMath.round(cForPercent - cAgPercent, 2);
                                all4 = 100;
                            }

                            if (matchesHome > 0){
                                double cFor = MyMath.round((Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[7].split("_")[0]) + Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[18].split("_")[0]) )/ (double) matchesHome,2);
                                double cAg  = MyMath.round((Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[7].split("_")[1]) + Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[18].split("_")[1]) )/ (double) matchesHome,2);

                                double cForPercent = MyMath.round( 100 * cFor / (cFor + cAg) , 2);
                                double cAgPercent = MyMath.round( 100 * cAg / (cFor + cAg) , 2);

                                home1 = cForPercent;
                                home2 = cAgPercent;
                                home3 = MyMath.round(cForPercent - cAgPercent, 2);
                                home4 = 100;

                            }
                            if (matchesAway > 0){
                                double cFor = MyMath.round((Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[7].split("_")[0]) + Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[18].split("_")[0]) )/ (double) matchesAway,2);
                                double cAg  = MyMath.round((Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[7].split("_")[1]) + Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[18].split("_")[1]) )/ (double) matchesAway,2);

                                double cForPercent = MyMath.round( 100 * cFor / (cFor + cAg) , 2);
                                double cAgPercent = MyMath.round( 100 * cAg / (cFor + cAg) , 2);

                                away1 = cForPercent;
                                away2 = cAgPercent;
                                away3 = MyMath.round(cForPercent - cAgPercent, 2);
                                away4 = 100;

                            }
                            break;
                        }
                        case "PDO":{
                            if (matchesAll > 0){
                                double selfReal = MyMath.round(100 * Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[2].split("_")[0])/ Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[7].split("_")[0]),2);
                                double oppReal  = MyMath.round(100 * Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[2].split("_")[1])/ Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[7].split("_")[1]),2);

                                double PDO_Self = MyMath.round( 100 + selfReal - oppReal , 2);
                                double PDO_Opp = MyMath.round( 100 - selfReal + oppReal , 2);

                                all1 = PDO_Self;
                                all2 = PDO_Opp;
                                all3 = MyMath.round(PDO_Self - PDO_Opp, 2);
                                all4 = 100;
                            }

                            if (matchesHome > 0){
                                double selfReal = MyMath.round(100 * Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[2].split("_")[0])/ Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[7].split("_")[0]),2);
                                double oppReal  = MyMath.round(100 * Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[2].split("_")[1])/ Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[7].split("_")[1]),2);

                                double PDO_Self = MyMath.round( 100 + selfReal - oppReal , 2);
                                double PDO_Opp = MyMath.round( 100 - selfReal + oppReal , 2);

                                home1 = PDO_Self;
                                home2 = PDO_Opp;
                                home3 = MyMath.round(PDO_Self - PDO_Opp, 2);
                                home4 = 100;
                            }
                            if (matchesAway > 0){
                                double selfReal = MyMath.round(100 * Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[2].split("_")[0])/ Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[7].split("_")[0]),2);
                                double oppReal  = MyMath.round(100 * Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[2].split("_")[1])/ Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[7].split("_")[1]),2);

                                double PDO_Self = MyMath.round( 100 + selfReal - oppReal , 2);
                                double PDO_Opp = MyMath.round( 100 - selfReal + oppReal , 2);

                                away1 = PDO_Self;
                                away2 = PDO_Opp;
                                away3 = MyMath.round(PDO_Self - PDO_Opp, 2);
                                away4 = 100;

                            }
                            break;
                        }
                        case "Реализация бросков, %":{
                            if (matchesAll > 0){
                                all1 = MyMath.round(100 * (Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[2].split("_")[0]) / Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[7].split("_")[0])),2);
                                all2 = MyMath.round(100 * (Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[2].split("_")[1]) / Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[7].split("_")[1])),2);
                                //all3 = MyMath.round(100 * (Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[2].split("_")[2]) / Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[7].split("_")[2])),2);
                                all3 = 0;
                                all4 = MyMath.round(100 * (Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[2].split("_")[3]) / Double.parseDouble(pt.league.overallStatsTable.get(i).split("\\*")[7].split("_")[3])),2);
                            }

                            if (matchesHome > 0){
                                home1 = MyMath.round(100 * (Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[2].split("_")[0]) / Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[7].split("_")[0])),2);
                                home2 = MyMath.round(100 * (Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[2].split("_")[1]) / Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[7].split("_")[1])),2);
//                                home3 = MyMath.round(100 * (Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[2].split("_")[2]) / Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[7].split("_")[2])),2);
                                home3 = 0;
                                home4 = MyMath.round(100 * (Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[2].split("_")[3]) / Double.parseDouble(pt.league.homeStatsTable.get(i).split("\\*")[7].split("_")[3])),2);
                            }
                            if (matchesAway > 0){
                                away1 = MyMath.round(100 * (Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[2].split("_")[0]) / Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[7].split("_")[0])),2);
                                away2 = MyMath.round(100 * (Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[2].split("_")[1]) / Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[7].split("_")[1])),2);
//                                away3 = MyMath.round(100 * (Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[2].split("_")[2]) / Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[7].split("_")[2])),2);
                                away3 = 0;
                                away4 = MyMath.round(100 * (Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[2].split("_")[3]) / Double.parseDouble(pt.league.awayStatsTable.get(i).split("\\*")[7].split("_")[3])),2);
                            }
                            break;
                        }

                    }

                    record.add(all1);
                    record.add(all2);
                    record.add(all3);
                    record.add(all4);
                    record.add(home1);
                    record.add(home2);
                    record.add(home3);
                    record.add(home4);
                    record.add(away1);
                    record.add(away2);
                    record.add(away3);
                    record.add(away4);

                    data.add(record);

                }
            }



            Font font = new Font("Arial", Font.BOLD, 15);
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

            String[] colHeaders = {"", "Общая" , "Дома", "На выезде"};
            Object[][] emptyData = new Object[0][0];
            JTable tableHeaders = new JTable(emptyData, colHeaders);
            tableHeaders.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            tableHeaders.setEnabled(false);
            tableHeaders.getTableHeader().setFont(font);
            tableHeaders.setFont(font);
            tableHeaders.setRowHeight(25);
            for (int r=0; r<colHeaders.length; r++){
                tableHeaders.getColumnModel().getColumn(r).setCellRenderer(centerRenderer);
                tableHeaders.getColumnModel().getColumn(r).setPreferredWidth(360);
            }
            tableHeaders.getColumnModel().getColumn(0).setPreferredWidth(185);

            JPanel tablePanelHeaders = new JPanel();
            tablePanelHeaders.setLayout(new BorderLayout());
            tablePanelHeaders.add(tableHeaders, BorderLayout.CENTER);
            tablePanelHeaders.add(tableHeaders.getTableHeader(), BorderLayout.NORTH);
            result.add(tablePanelHeaders, BorderLayout.NORTH);

            String[] rankHeader = {"  "};
            Object[][] ranks = new Object[numberOfTeams][1];
            for (int i=0; i<numberOfTeams;i++)
                ranks[i][0] = i+1;

            JTable tableRanks = new JTable(ranks, rankHeader);
            tableRanks.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            tableRanks.setEnabled(false);
            tableRanks.getTableHeader().setFont(font);
            tableRanks.setFont(font);
            tableRanks.getColumnModel().getColumn(0).setPreferredWidth(35);
            tableRanks.setRowHeight(25);
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            tableRanks.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            JPanel tablePanelRanks = new JPanel();
            tablePanelRanks.setLayout(new BorderLayout());
            tablePanelRanks.setLayout(new BorderLayout());
            tablePanelRanks.add(tableRanks, BorderLayout.CENTER);
            tablePanelRanks.add(tableRanks.getTableHeader(), BorderLayout.NORTH);

            result.add(tablePanelRanks, BorderLayout.WEST);

            String[] colHeads = {"Команда" , "Свои", "Чужие", "Разница", "Тотал", "Свои", "Чужие", "Разница", "Тотал", "Свои", "Чужие", "Разница", "Тотал"};
            Object[][] dataForTable = new Object[numberOfTeams][colHeads.length];
            double[][] minMaxArray = new double[2][colHeads.length];
            for (int i=0; i<numberOfTeams; i++){
                dataForTable[i][0] = listOfTeams.get(i);
                for (int j=1; j<colHeads.length; j++)
                    dataForTable[i][j] = data.get(i).get(j-1);
            }

            for (int j=1; j<colHeads.length; j++){
                for (int i=0; i<numberOfTeams; i++){
                    minMaxArray[0][j] = 1000000;
                    minMaxArray[1][j] = -1000000;
                }
            }

            for (int j=1; j<colHeads.length; j++){
                for (int i=0; i<numberOfTeams; i++){
                    if ((double) dataForTable[i][j] <= minMaxArray[0][j])
                        minMaxArray[0][j] = (double) dataForTable[i][j];
                    if ((double) dataForTable[i][j] >= minMaxArray[1][j])
                        minMaxArray[1][j] = (double) dataForTable[i][j];
                }
            }

            TableModel model = new DefaultTableModel(dataForTable, colHeads) {
                public Class getColumnClass(int column) {
                    Class returnValue;
                    if ((column >= 0) && (column < getColumnCount())) {
                        returnValue = getValueAt(0, column).getClass();
                    }  else {
                        returnValue = Object.class;
                    }
                    return returnValue;
                }
            };
            JTable table = new JTable(model);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.setEnabled(false);
            table.getTableHeader().setFont(font);
            table.setFont(font);
            table.setRowHeight(25);
            Renderer renderer = new Renderer(minMaxArray, 3);
            for (int r=0; r<colHeads.length; r++){
                table.getColumnModel().getColumn(r).setCellRenderer(renderer);
                table.getColumnModel().getColumn(r).setPreferredWidth(90);
            }
            table.getColumnModel().getColumn(0).setPreferredWidth(150);
            RowSorter<TableModel> sorter = new TableRowSorter<>(model);
            table.setRowSorter(sorter);

            JPanel tablePanel = new JPanel();
            tablePanel.setLayout(new BorderLayout());
            tablePanel.add(table, BorderLayout.CENTER);
            tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);


            result.add(tablePanel);

            pt.lastCalculatedLeague = leagueName;
            pt.lastCalculatedSeason = season;
            pt.lastCalculatedLastOrFull = lastOrFull;

        } else {
            result.setLayout(new VerticalLayout());

            final JLabel label = new JLabel("Не выбран чемпионат.");
            if (directoryList.length == 0){
                label.setText("В сезоне " + season + " команды из " + leagueName + " не провели ни одной игры.");
            }

            label.setLocation(10, 0);
            Font fontLabel = new Font("Arial", Font.BOLD, 15);
            label.setFont(fontLabel);
            result.add(label);

        }
        return result;
    }
}

