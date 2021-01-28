package sample;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class TableMaker {
    public TableMaker(){
    }

    public static JPanel getTableGoals(ArrayList<Match> list){
        String[] colHeads = {"Ставка", "Заход и %"};
        double oz = 0.0;
        double oz15 = 0.0;
        double tb45 = 0.0;
        double tb55 = 0.0;
        double everyPeriodGoals = 0.0;

        for (int i=0; i<list.size(); i++){
            if (list.get(i).homeScore + list.get(i).awayScore > 4.5)
                tb45++;
            if (list.get(i).homeScore + list.get(i).awayScore > 5.5)
                tb55++;
            if ((list.get(i).homeScore>0) && (list.get(i).awayScore > 0))
                oz++;
            if ((list.get(i).homeScore>1.5) && (list.get(i).awayScore > 1.5))
                oz15++;
            if ((list.get(i).homeScore1stPeriod + list.get(i).awayScore1stPeriod > 0) && (list.get(i).homeScore2ndPeriod + list.get(i).awayScore2ndPeriod > 0) && (list.get(i).homeScore3rdPeriod + list.get(i).awayScore3rdPeriod > 0))
                everyPeriodGoals++;
        }
        Object[][] data = {
                {"Обе забьют" , String.valueOf((int) (oz)) + "/" + list.size() + " (" + MyMath.round(oz/list.size()*100, 1) + ")"},
                {"Обе забьют - нет" , String.valueOf((int) (list.size() - oz)) + "/" + list.size() + " (" + MyMath.round((list.size() - oz)/list.size()*100, 1) + ")"},
                {"Обе забьют > 1.5" , String.valueOf((int) (oz15)) + "/" + list.size() + " (" + MyMath.round(oz15/list.size()*100, 1) + ")"},
                {"Обе забьют > 1.5 - нет" , String.valueOf((int) (list.size() - oz15)) + "/" + list.size() + " (" + MyMath.round((list.size() - oz15)/list.size()*100, 1) + ")"},
                {"ТБ(4.5)" , String.valueOf((int) (tb45)) + "/" + list.size() + " (" + MyMath.round(tb45/list.size()*100, 1) + ")"},
                {"ТМ(4.5)" , String.valueOf((int) (list.size() - tb45)) + "/" + list.size() + " (" + MyMath.round((list.size() - tb45)/list.size()*100, 1) + ")"},
                {"ТБ(5.5)" , String.valueOf((int) (tb55)) + "/" + list.size() + " (" + MyMath.round(tb55/list.size()*100, 1) + ")"},
                {"ТМ(5.5)" , String.valueOf((int) (list.size() - tb55)) + "/" + list.size() + " (" + MyMath.round((list.size() - tb55)/list.size()*100, 1) + ")"},
                {"Гол в каждом периоде" , String.valueOf((int) (everyPeriodGoals)) + "/" + list.size() + " (" + MyMath.round(everyPeriodGoals/list.size()*100, 1) + ")"},
        };


        JTable tableGoals = new JTable(data, colHeads);
        Font font = new Font("Arial", Font.BOLD, 12);
        tableGoals.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableGoals.setEnabled(false);
        tableGoals.getTableHeader().setFont(font);
        tableGoals.setFont(font);
        tableGoals.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableGoals.setRowHeight(25);
        tableGoals.getColumnModel().getColumn(1).setPreferredWidth(92);
        DefaultTableCellRenderer centerRenderer2 = new DefaultTableCellRenderer();
        centerRenderer2.setHorizontalAlignment(JLabel.CENTER);
        tableGoals.getColumnModel().getColumn(0).setCellRenderer(centerRenderer2);
        tableGoals.getColumnModel().getColumn(1).setCellRenderer(centerRenderer2);
        tableGoals.setBackground(new Color(238, 238, 238));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
        tablePanel.add(tableGoals, BorderLayout.CENTER);
        tablePanel.add(tableGoals.getTableHeader(), BorderLayout.NORTH);

        return tablePanel;
    }

    public static JPanel getTable1stperiod(String teamName, ArrayList<Match> list){
        double win = 0;
        double winX = 0;
        double totalAverageGoals = 1.5;
        double selfAverageGoals = 1.5;
        double opAverageGoals = 1.5;

        if (MyMath.round(totalAverageGoals,0) > totalAverageGoals)
            totalAverageGoals = MyMath.round(totalAverageGoals,0) - 0.5;
        else
            totalAverageGoals = MyMath.round(totalAverageGoals,0) + 0.5;
        if (MyMath.round(selfAverageGoals,0) > selfAverageGoals)
            selfAverageGoals = MyMath.round(selfAverageGoals,0) - 0.5;
        else
            selfAverageGoals = MyMath.round(selfAverageGoals,0) + 0.5;
        if (MyMath.round(opAverageGoals,0) > opAverageGoals)
            opAverageGoals = MyMath.round(opAverageGoals,0) - 0.5;
        else
            opAverageGoals = MyMath.round(opAverageGoals,0) + 0.5;
        double totalSred = 0;
        double totalPlus1 = 0;
        double totalMinus1 = 0;
        double selfSred = 0;
        double selfPlus1 = 0;
        double selfMinus1 = 0;
        double opSred = 0;
        double opPlus1 = 0;
        double opMinus1 = 0;

        for (int i=0; i<list.size(); i++){
            if (list.get(i).homeScore1stPeriod + list.get(i).awayScore1stPeriod > (totalAverageGoals-1))
                totalMinus1++;
            if (list.get(i).homeScore1stPeriod + list.get(i).awayScore1stPeriod > totalAverageGoals)
                totalSred++;
            if (list.get(i).homeScore1stPeriod + list.get(i).awayScore1stPeriod > (totalAverageGoals+1))
                totalPlus1++;

            if ((list.get(i).homeScore1stPeriod > (selfAverageGoals+1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayScore1stPeriod > (selfAverageGoals+1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfPlus1++;
            if ((list.get(i).homeScore1stPeriod > (selfAverageGoals)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayScore1stPeriod > (selfAverageGoals)))&&(teamName.equals(list.get(i).awayTeam))))
                selfSred++;
            if ((list.get(i).homeScore1stPeriod > (selfAverageGoals-1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayScore1stPeriod > (selfAverageGoals-1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfMinus1++;

            if ((list.get(i).homeScore1stPeriod > (opAverageGoals+1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayScore1stPeriod > (opAverageGoals+1)))&&(teamName.equals(list.get(i).homeTeam))))
                opPlus1++;
            if ((list.get(i).homeScore1stPeriod > (opAverageGoals)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayScore1stPeriod > (opAverageGoals)))&&(teamName.equals(list.get(i).homeTeam))))
                opSred++;
            if ((list.get(i).homeScore1stPeriod > (opAverageGoals-1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayScore1stPeriod > (opAverageGoals-1)))&&(teamName.equals(list.get(i).homeTeam))))
                opMinus1++;
            if ((list.get(i).homeScore1stPeriod > list.get(i).awayScore1stPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayScore1stPeriod > list.get(i).homeScore1stPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                win++;
            if ((list.get(i).homeScore1stPeriod >= list.get(i).awayScore1stPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayScore1stPeriod >= list.get(i).homeScore1stPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                winX++;
        }

        String t85 = "ТБ(" + String.valueOf(totalAverageGoals-1) + ")";
        String t85s = String.valueOf((int) (totalMinus1)) + "/" + list.size() + " (" + MyMath.round(totalMinus1/list.size()*100, 1) + ")";
        if (totalMinus1/list.size() < 0.5){
            t85 = "ТM(" + String.valueOf(totalAverageGoals-1) + ")";
            t85s = String.valueOf((int) (list.size() - totalMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalMinus1)/list.size()*100, 1) + ")";
        }
        String t95 = "ТБ(" + String.valueOf(totalAverageGoals) + ")";
        String t95s = String.valueOf((int) (totalSred)) + "/" + list.size() + " (" + MyMath.round(totalSred/list.size()*100, 1) + ")";
        if (totalSred/list.size() < 0.5){
            t95 = "ТM(" + String.valueOf(totalAverageGoals) + ")";
            t95s = String.valueOf((int) (list.size() - totalSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalSred)/list.size()*100, 1) + ")";
        }

        String t105 = "ТБ(" + String.valueOf(totalAverageGoals+1) + ")";
        String t105s = String.valueOf((int) (totalPlus1)) + "/" + list.size() + " (" + MyMath.round(totalPlus1/list.size()*100, 1) + ")";
        if (totalPlus1/list.size() < 0.5){
            t105 = "ТM(" + String.valueOf(totalAverageGoals+1) + ")";
            t105s = String.valueOf((int) (list.size() - totalPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalPlus1)/list.size()*100, 1) + ")";
        }

        double total = selfAverageGoals + 1;
        String itbPlus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBPlus1 = String.valueOf((int) (selfPlus1)) + "/" + list.size() + " (" + MyMath.round(selfPlus1/list.size()*100, 1) + ")";
        if (selfPlus1/list.size() < 0.5){
            itbPlus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBPlus1 = String.valueOf((int) (list.size() - selfPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfPlus1)/list.size()*100, 1) + ")";
        }

        total = selfAverageGoals;
        String itbSred = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBSred = String.valueOf((int) (selfSred)) + "/" + list.size() + " (" + MyMath.round(selfSred/list.size()*100, 1) + ")";
        if (selfSred/list.size() < 0.5){
            itbSred = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBSred = String.valueOf((int) (list.size() - selfSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfSred)/list.size()*100, 1) + ")";
        }

        total = selfAverageGoals - 1;
        String itbMinus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBMinus1 = String.valueOf((int) (selfMinus1)) + "/" + list.size() + " (" + MyMath.round(selfMinus1/list.size()*100, 1) + ")";
        if (selfMinus1/list.size() < 0.5){
            itbMinus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBMinus1 = String.valueOf((int) (list.size() - selfMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfMinus1)/list.size()*100, 1) + ")";
        }

        total = opAverageGoals + 1;
        String optbPlus1 = "Opp: ТБ(" + total + ")";
        String opTBPlus1 = String.valueOf((int) (opPlus1)) + "/" + list.size() + " (" + MyMath.round(opPlus1/list.size()*100, 1) + ")";
        if (opPlus1/list.size() < 0.5){
            optbPlus1 = "Opp: ТM(" + total + ")";
            opTBPlus1 = String.valueOf((int) (list.size() - opPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opPlus1)/list.size()*100, 1) + ")";
        }

        total = opAverageGoals;
        String optbSred = "Opp: ТБ(" + total + ")";
        String opTBSred = String.valueOf((int) (opSred)) + "/" + list.size() + " (" + MyMath.round(opSred/list.size()*100, 1) + ")";
        if (opSred/list.size() < 0.5){
            optbSred = "Opp: ТM(" + total + ")";
            opTBSred = String.valueOf((int) (list.size() - opSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - opSred)/list.size()*100, 1) + ")";
        }

        total = opAverageGoals - 1;
        String optbMinus1 = "Opp: ТБ(" + total + ")";
        String opTBMinus1 = String.valueOf((int) (opMinus1)) + "/" + list.size() + " (" + MyMath.round(opMinus1/list.size()*100, 1) + ")";
        if (opMinus1/list.size() < 0.5){
            optbMinus1 = "Opp: ТM(" + total + ")";
            opTBMinus1 = String.valueOf((int) (list.size() - opMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opMinus1)/list.size()*100, 1) + ")";
        }

        String winS = Team.getShortName(teamName) + ": Ф(-0.5)";
        String winSS = String.valueOf((int) win) + "/" + list.size() + " (" + MyMath.round(win/list.size()*100, 1) + ")";

        String winXS = Team.getShortName(teamName) + ": Ф(+0.5)";
        String winXSS = String.valueOf((int) winX) + "/" + list.size() + " (" + MyMath.round(winX/list.size()*100, 1) + ")";

        String[] colHeads = {"Ставка", "Заход и %"};
        Object[][] data = {
                {t85 , t85s},
                {t95 , t95s},
                {t105 , t105s},
                {itbMinus1 , selfTBMinus1},
                {itbSred , selfTBSred},
                {itbPlus1 , selfTBPlus1},
                {optbMinus1 , opTBMinus1},
                {optbSred , opTBSred},
                {optbPlus1 , opTBPlus1},
                {winS , winSS},
                {winXS , winXSS},
        };

        Font font = new Font("Arial", Font.BOLD, 12);
        JTable tableUSV = new JTable(data, colHeads);
        tableUSV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUSV.setEnabled(false);
        tableUSV.getTableHeader().setFont(font);
        tableUSV.setFont(font);
        tableUSV.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableUSV.setRowHeight(25);
        tableUSV.getColumnModel().getColumn(1).setPreferredWidth(92);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableUSV.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableUSV.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableUSV.setBackground(new Color(238, 238, 238));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
        tablePanel.add(tableUSV, BorderLayout.CENTER);
        tablePanel.add(tableUSV.getTableHeader(), BorderLayout.NORTH);

        return tablePanel;
    }

    public static JPanel getTable2ndperiod(String teamName, ArrayList<Match> list){
        double win = 0;
        double winX = 0;
        double totalAverageGoals = 1.5;
        double selfAverageGoals = 1.5;
        double opAverageGoals = 1.5;

        if (MyMath.round(totalAverageGoals,0) > totalAverageGoals)
            totalAverageGoals = MyMath.round(totalAverageGoals,0) - 0.5;
        else
            totalAverageGoals = MyMath.round(totalAverageGoals,0) + 0.5;
        if (MyMath.round(selfAverageGoals,0) > selfAverageGoals)
            selfAverageGoals = MyMath.round(selfAverageGoals,0) - 0.5;
        else
            selfAverageGoals = MyMath.round(selfAverageGoals,0) + 0.5;
        if (MyMath.round(opAverageGoals,0) > opAverageGoals)
            opAverageGoals = MyMath.round(opAverageGoals,0) - 0.5;
        else
            opAverageGoals = MyMath.round(opAverageGoals,0) + 0.5;
        double totalSred = 0;
        double totalPlus1 = 0;
        double totalMinus1 = 0;
        double selfSred = 0;
        double selfPlus1 = 0;
        double selfMinus1 = 0;
        double opSred = 0;
        double opPlus1 = 0;
        double opMinus1 = 0;

        for (int i=0; i<list.size(); i++){
            if (list.get(i).homeScore2ndPeriod + list.get(i).awayScore2ndPeriod > (totalAverageGoals-1))
                totalMinus1++;
            if (list.get(i).homeScore2ndPeriod + list.get(i).awayScore2ndPeriod > totalAverageGoals)
                totalSred++;
            if (list.get(i).homeScore2ndPeriod + list.get(i).awayScore2ndPeriod > (totalAverageGoals+1))
                totalPlus1++;

            if ((list.get(i).homeScore2ndPeriod > (selfAverageGoals+1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayScore2ndPeriod > (selfAverageGoals+1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfPlus1++;
            if ((list.get(i).homeScore2ndPeriod > (selfAverageGoals)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayScore2ndPeriod > (selfAverageGoals)))&&(teamName.equals(list.get(i).awayTeam))))
                selfSred++;
            if ((list.get(i).homeScore2ndPeriod > (selfAverageGoals-1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayScore2ndPeriod > (selfAverageGoals-1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfMinus1++;

            if ((list.get(i).homeScore2ndPeriod > (opAverageGoals+1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayScore2ndPeriod > (opAverageGoals+1)))&&(teamName.equals(list.get(i).homeTeam))))
                opPlus1++;
            if ((list.get(i).homeScore2ndPeriod > (opAverageGoals)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayScore2ndPeriod > (opAverageGoals)))&&(teamName.equals(list.get(i).homeTeam))))
                opSred++;
            if ((list.get(i).homeScore2ndPeriod > (opAverageGoals-1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayScore2ndPeriod > (opAverageGoals-1)))&&(teamName.equals(list.get(i).homeTeam))))
                opMinus1++;
            if ((list.get(i).homeScore2ndPeriod > list.get(i).awayScore2ndPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayScore2ndPeriod > list.get(i).homeScore2ndPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                win++;
            if ((list.get(i).homeScore2ndPeriod >= list.get(i).awayScore2ndPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayScore2ndPeriod >= list.get(i).homeScore2ndPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                winX++;
        }

        String t85 = "ТБ(" + String.valueOf(totalAverageGoals-1) + ")";
        String t85s = String.valueOf((int) (totalMinus1)) + "/" + list.size() + " (" + MyMath.round(totalMinus1/list.size()*100, 1) + ")";
        if (totalMinus1/list.size() < 0.5){
            t85 = "ТM(" + String.valueOf(totalAverageGoals-1) + ")";
            t85s = String.valueOf((int) (list.size() - totalMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalMinus1)/list.size()*100, 1) + ")";
        }
        String t95 = "ТБ(" + String.valueOf(totalAverageGoals) + ")";
        String t95s = String.valueOf((int) (totalSred)) + "/" + list.size() + " (" + MyMath.round(totalSred/list.size()*100, 1) + ")";
        if (totalSred/list.size() < 0.5){
            t95 = "ТM(" + String.valueOf(totalAverageGoals) + ")";
            t95s = String.valueOf((int) (list.size() - totalSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalSred)/list.size()*100, 1) + ")";
        }

        String t105 = "ТБ(" + String.valueOf(totalAverageGoals+1) + ")";
        String t105s = String.valueOf((int) (totalPlus1)) + "/" + list.size() + " (" + MyMath.round(totalPlus1/list.size()*100, 1) + ")";
        if (totalPlus1/list.size() < 0.5){
            t105 = "ТM(" + String.valueOf(totalAverageGoals+1) + ")";
            t105s = String.valueOf((int) (list.size() - totalPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalPlus1)/list.size()*100, 1) + ")";
        }

        double total = selfAverageGoals + 1;
        String itbPlus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBPlus1 = String.valueOf((int) (selfPlus1)) + "/" + list.size() + " (" + MyMath.round(selfPlus1/list.size()*100, 1) + ")";
        if (selfPlus1/list.size() < 0.5){
            itbPlus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBPlus1 = String.valueOf((int) (list.size() - selfPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfPlus1)/list.size()*100, 1) + ")";
        }

        total = selfAverageGoals;
        String itbSred = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBSred = String.valueOf((int) (selfSred)) + "/" + list.size() + " (" + MyMath.round(selfSred/list.size()*100, 1) + ")";
        if (selfSred/list.size() < 0.5){
            itbSred = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBSred = String.valueOf((int) (list.size() - selfSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfSred)/list.size()*100, 1) + ")";
        }

        total = selfAverageGoals - 1;
        String itbMinus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBMinus1 = String.valueOf((int) (selfMinus1)) + "/" + list.size() + " (" + MyMath.round(selfMinus1/list.size()*100, 1) + ")";
        if (selfMinus1/list.size() < 0.5){
            itbMinus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBMinus1 = String.valueOf((int) (list.size() - selfMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfMinus1)/list.size()*100, 1) + ")";
        }

        total = opAverageGoals + 1;
        String optbPlus1 = "Opp: ТБ(" + total + ")";
        String opTBPlus1 = String.valueOf((int) (opPlus1)) + "/" + list.size() + " (" + MyMath.round(opPlus1/list.size()*100, 1) + ")";
        if (opPlus1/list.size() < 0.5){
            optbPlus1 = "Opp: ТM(" + total + ")";
            opTBPlus1 = String.valueOf((int) (list.size() - opPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opPlus1)/list.size()*100, 1) + ")";
        }

        total = opAverageGoals;
        String optbSred = "Opp: ТБ(" + total + ")";
        String opTBSred = String.valueOf((int) (opSred)) + "/" + list.size() + " (" + MyMath.round(opSred/list.size()*100, 1) + ")";
        if (opSred/list.size() < 0.5){
            optbSred = "Opp: ТM(" + total + ")";
            opTBSred = String.valueOf((int) (list.size() - opSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - opSred)/list.size()*100, 1) + ")";
        }

        total = opAverageGoals - 1;
        String optbMinus1 = "Opp: ТБ(" + total + ")";
        String opTBMinus1 = String.valueOf((int) (opMinus1)) + "/" + list.size() + " (" + MyMath.round(opMinus1/list.size()*100, 1) + ")";
        if (opMinus1/list.size() < 0.5){
            optbMinus1 = "Opp: ТM(" + total + ")";
            opTBMinus1 = String.valueOf((int) (list.size() - opMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opMinus1)/list.size()*100, 1) + ")";
        }

        String winS = Team.getShortName(teamName) + ": Ф(-0.5)";
        String winSS = String.valueOf((int) win) + "/" + list.size() + " (" + MyMath.round(win/list.size()*100, 1) + ")";

        String winXS = Team.getShortName(teamName) + ": Ф(+0.5)";
        String winXSS = String.valueOf((int) winX) + "/" + list.size() + " (" + MyMath.round(winX/list.size()*100, 1) + ")";

        String[] colHeads = {"Ставка", "Заход и %"};
        Object[][] data = {
                {t85 , t85s},
                {t95 , t95s},
                {t105 , t105s},
                {itbMinus1 , selfTBMinus1},
                {itbSred , selfTBSred},
                {itbPlus1 , selfTBPlus1},
                {optbMinus1 , opTBMinus1},
                {optbSred , opTBSred},
                {optbPlus1 , opTBPlus1},
                {winS , winSS},
                {winXS , winXSS},
        };

        Font font = new Font("Arial", Font.BOLD, 12);
        JTable tableUSV = new JTable(data, colHeads);
        tableUSV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUSV.setEnabled(false);
        tableUSV.getTableHeader().setFont(font);
        tableUSV.setFont(font);
        tableUSV.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableUSV.setRowHeight(25);
        tableUSV.getColumnModel().getColumn(1).setPreferredWidth(92);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableUSV.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableUSV.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableUSV.setBackground(new Color(238, 238, 238));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
        tablePanel.add(tableUSV, BorderLayout.CENTER);
        tablePanel.add(tableUSV.getTableHeader(), BorderLayout.NORTH);

        return tablePanel;
    }

    public static JPanel getTable3rdperiod(String teamName, ArrayList<Match> list){
        double win = 0;
        double winX = 0;
        double totalAverageGoals = 1.5;
        double selfAverageGoals = 1.5;
        double opAverageGoals = 1.5;

        if (MyMath.round(totalAverageGoals,0) > totalAverageGoals)
            totalAverageGoals = MyMath.round(totalAverageGoals,0) - 0.5;
        else
            totalAverageGoals = MyMath.round(totalAverageGoals,0) + 0.5;
        if (MyMath.round(selfAverageGoals,0) > selfAverageGoals)
            selfAverageGoals = MyMath.round(selfAverageGoals,0) - 0.5;
        else
            selfAverageGoals = MyMath.round(selfAverageGoals,0) + 0.5;
        if (MyMath.round(opAverageGoals,0) > opAverageGoals)
            opAverageGoals = MyMath.round(opAverageGoals,0) - 0.5;
        else
            opAverageGoals = MyMath.round(opAverageGoals,0) + 0.5;
        double totalSred = 0;
        double totalPlus1 = 0;
        double totalMinus1 = 0;
        double selfSred = 0;
        double selfPlus1 = 0;
        double selfMinus1 = 0;
        double opSred = 0;
        double opPlus1 = 0;
        double opMinus1 = 0;

        for (int i=0; i<list.size(); i++){
            if (list.get(i).homeScore3rdPeriod + list.get(i).awayScore3rdPeriod > (totalAverageGoals-1))
                totalMinus1++;
            if (list.get(i).homeScore3rdPeriod + list.get(i).awayScore3rdPeriod > totalAverageGoals)
                totalSred++;
            if (list.get(i).homeScore3rdPeriod + list.get(i).awayScore3rdPeriod > (totalAverageGoals+1))
                totalPlus1++;

            if ((list.get(i).homeScore3rdPeriod > (selfAverageGoals+1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayScore3rdPeriod > (selfAverageGoals+1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfPlus1++;
            if ((list.get(i).homeScore3rdPeriod > (selfAverageGoals)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayScore3rdPeriod > (selfAverageGoals)))&&(teamName.equals(list.get(i).awayTeam))))
                selfSred++;
            if ((list.get(i).homeScore3rdPeriod > (selfAverageGoals-1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayScore3rdPeriod > (selfAverageGoals-1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfMinus1++;

            if ((list.get(i).homeScore3rdPeriod > (opAverageGoals+1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayScore3rdPeriod > (opAverageGoals+1)))&&(teamName.equals(list.get(i).homeTeam))))
                opPlus1++;
            if ((list.get(i).homeScore3rdPeriod > (opAverageGoals)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayScore3rdPeriod > (opAverageGoals)))&&(teamName.equals(list.get(i).homeTeam))))
                opSred++;
            if ((list.get(i).homeScore3rdPeriod > (opAverageGoals-1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayScore3rdPeriod > (opAverageGoals-1)))&&(teamName.equals(list.get(i).homeTeam))))
                opMinus1++;
            if ((list.get(i).homeScore3rdPeriod > list.get(i).awayScore3rdPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayScore3rdPeriod > list.get(i).homeScore3rdPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                win++;
            if ((list.get(i).homeScore3rdPeriod >= list.get(i).awayScore3rdPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayScore3rdPeriod >= list.get(i).homeScore3rdPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                winX++;
        }

        String t85 = "ТБ(" + String.valueOf(totalAverageGoals-1) + ")";
        String t85s = String.valueOf((int) (totalMinus1)) + "/" + list.size() + " (" + MyMath.round(totalMinus1/list.size()*100, 1) + ")";
        if (totalMinus1/list.size() < 0.5){
            t85 = "ТM(" + String.valueOf(totalAverageGoals-1) + ")";
            t85s = String.valueOf((int) (list.size() - totalMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalMinus1)/list.size()*100, 1) + ")";
        }
        String t95 = "ТБ(" + String.valueOf(totalAverageGoals) + ")";
        String t95s = String.valueOf((int) (totalSred)) + "/" + list.size() + " (" + MyMath.round(totalSred/list.size()*100, 1) + ")";
        if (totalSred/list.size() < 0.5){
            t95 = "ТM(" + String.valueOf(totalAverageGoals) + ")";
            t95s = String.valueOf((int) (list.size() - totalSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalSred)/list.size()*100, 1) + ")";
        }

        String t105 = "ТБ(" + String.valueOf(totalAverageGoals+1) + ")";
        String t105s = String.valueOf((int) (totalPlus1)) + "/" + list.size() + " (" + MyMath.round(totalPlus1/list.size()*100, 1) + ")";
        if (totalPlus1/list.size() < 0.5){
            t105 = "ТM(" + String.valueOf(totalAverageGoals+1) + ")";
            t105s = String.valueOf((int) (list.size() - totalPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalPlus1)/list.size()*100, 1) + ")";
        }

        double total = selfAverageGoals + 1;
        String itbPlus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBPlus1 = String.valueOf((int) (selfPlus1)) + "/" + list.size() + " (" + MyMath.round(selfPlus1/list.size()*100, 1) + ")";
        if (selfPlus1/list.size() < 0.5){
            itbPlus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBPlus1 = String.valueOf((int) (list.size() - selfPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfPlus1)/list.size()*100, 1) + ")";
        }

        total = selfAverageGoals;
        String itbSred = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBSred = String.valueOf((int) (selfSred)) + "/" + list.size() + " (" + MyMath.round(selfSred/list.size()*100, 1) + ")";
        if (selfSred/list.size() < 0.5){
            itbSred = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBSred = String.valueOf((int) (list.size() - selfSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfSred)/list.size()*100, 1) + ")";
        }

        total = selfAverageGoals - 1;
        String itbMinus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBMinus1 = String.valueOf((int) (selfMinus1)) + "/" + list.size() + " (" + MyMath.round(selfMinus1/list.size()*100, 1) + ")";
        if (selfMinus1/list.size() < 0.5){
            itbMinus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBMinus1 = String.valueOf((int) (list.size() - selfMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfMinus1)/list.size()*100, 1) + ")";
        }

        total = opAverageGoals + 1;
        String optbPlus1 = "Opp: ТБ(" + total + ")";
        String opTBPlus1 = String.valueOf((int) (opPlus1)) + "/" + list.size() + " (" + MyMath.round(opPlus1/list.size()*100, 1) + ")";
        if (opPlus1/list.size() < 0.5){
            optbPlus1 = "Opp: ТM(" + total + ")";
            opTBPlus1 = String.valueOf((int) (list.size() - opPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opPlus1)/list.size()*100, 1) + ")";
        }

        total = opAverageGoals;
        String optbSred = "Opp: ТБ(" + total + ")";
        String opTBSred = String.valueOf((int) (opSred)) + "/" + list.size() + " (" + MyMath.round(opSred/list.size()*100, 1) + ")";
        if (opSred/list.size() < 0.5){
            optbSred = "Opp: ТM(" + total + ")";
            opTBSred = String.valueOf((int) (list.size() - opSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - opSred)/list.size()*100, 1) + ")";
        }

        total = opAverageGoals - 1;
        String optbMinus1 = "Opp: ТБ(" + total + ")";
        String opTBMinus1 = String.valueOf((int) (opMinus1)) + "/" + list.size() + " (" + MyMath.round(opMinus1/list.size()*100, 1) + ")";
        if (opMinus1/list.size() < 0.5){
            optbMinus1 = "Opp: ТM(" + total + ")";
            opTBMinus1 = String.valueOf((int) (list.size() - opMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opMinus1)/list.size()*100, 1) + ")";
        }

        String winS = Team.getShortName(teamName) + ": Ф(-0.5)";
        String winSS = String.valueOf((int) win) + "/" + list.size() + " (" + MyMath.round(win/list.size()*100, 1) + ")";

        String winXS = Team.getShortName(teamName) + ": Ф(+0.5)";
        String winXSS = String.valueOf((int) winX) + "/" + list.size() + " (" + MyMath.round(winX/list.size()*100, 1) + ")";

        String[] colHeads = {"Ставка", "Заход и %"};
        Object[][] data = {
                {t85 , t85s},
                {t95 , t95s},
                {t105 , t105s},
                {itbMinus1 , selfTBMinus1},
                {itbSred , selfTBSred},
                {itbPlus1 , selfTBPlus1},
                {optbMinus1 , opTBMinus1},
                {optbSred , opTBSred},
                {optbPlus1 , opTBPlus1},
                {winS , winSS},
                {winXS , winXSS},
        };

        Font font = new Font("Arial", Font.BOLD, 12);
        JTable tableUSV = new JTable(data, colHeads);
        tableUSV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUSV.setEnabled(false);
        tableUSV.getTableHeader().setFont(font);
        tableUSV.setFont(font);
        tableUSV.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableUSV.setRowHeight(25);
        tableUSV.getColumnModel().getColumn(1).setPreferredWidth(92);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableUSV.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableUSV.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableUSV.setBackground(new Color(238, 238, 238));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
        tablePanel.add(tableUSV, BorderLayout.CENTER);
        tablePanel.add(tableUSV.getTableHeader(), BorderLayout.NORTH);

        return tablePanel;
    }

    public static JPanel getTableShotsOnTarget(String teamName, /*ArrayList<Match> list*/ Selector selector){
        double win = 0;
        double winX = 0;

        double shotsOnTarget = Double.parseDouble(selector.pList.get(7).get(1));
        double shotsOnTargetOpponent = Double.parseDouble(selector.pList.get(7).get(2));

        double totalAverageShots = shotsOnTarget + shotsOnTargetOpponent;
        double selfAverageShots =  shotsOnTarget;
        double opAverageShots =  shotsOnTargetOpponent;

        if (MyMath.round(totalAverageShots,0) > totalAverageShots)
            totalAverageShots = MyMath.round(totalAverageShots,0) - 0.5;
        else
            totalAverageShots = MyMath.round(totalAverageShots,0) + 0.5;
        if (MyMath.round(selfAverageShots,0) > selfAverageShots)
            selfAverageShots = MyMath.round(selfAverageShots,0) - 0.5;
        else
            selfAverageShots = MyMath.round(selfAverageShots,0) + 0.5;
        if (MyMath.round(opAverageShots,0) > opAverageShots)
            opAverageShots = MyMath.round(opAverageShots,0) - 0.5;
        else
            opAverageShots = MyMath.round(opAverageShots,0) + 0.5;
        double totalSred = 0;
        double totalPlus1 = 0;
        double totalMinus1 = 0;
        double selfSred = 0;
        double selfPlus1 = 0;
        double selfMinus1 = 0;
        double opSred = 0;
        double opPlus1 = 0;
        double opMinus1 = 0;

        ArrayList<Match> list = selector.listOfMatches;

        for (int i=0; i<list.size(); i++){
            if (list.get(i).homeShotsOnTarget + list.get(i).awayShotsOnTarget > (totalAverageShots-1))
                totalMinus1++;
            if (list.get(i).homeShotsOnTarget + list.get(i).awayShotsOnTarget > totalAverageShots)
                totalSred++;
            if (list.get(i).homeShotsOnTarget + list.get(i).awayShotsOnTarget > (totalAverageShots+1))
                totalPlus1++;

            if ((list.get(i).homeShotsOnTarget > (selfAverageShots+1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayShotsOnTarget > (selfAverageShots+1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfPlus1++;
            if ((list.get(i).homeShotsOnTarget > (selfAverageShots)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayShotsOnTarget > (selfAverageShots)))&&(teamName.equals(list.get(i).awayTeam))))
                selfSred++;
            if ((list.get(i).homeShotsOnTarget > (selfAverageShots-1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayShotsOnTarget > (selfAverageShots-1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfMinus1++;

            if ((list.get(i).homeShotsOnTarget > (opAverageShots+1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayShotsOnTarget > (opAverageShots+1)))&&(teamName.equals(list.get(i).homeTeam))))
                opPlus1++;
            if ((list.get(i).homeShotsOnTarget > (opAverageShots)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayShotsOnTarget > (opAverageShots)))&&(teamName.equals(list.get(i).homeTeam))))
                opSred++;
            if ((list.get(i).homeShotsOnTarget > (opAverageShots-1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayShotsOnTarget > (opAverageShots-1)))&&(teamName.equals(list.get(i).homeTeam))))
                opMinus1++;
            if ((list.get(i).homeShotsOnTarget > list.get(i).awayShotsOnTarget&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayShotsOnTarget > list.get(i).homeShotsOnTarget))&&(teamName.equals(list.get(i).awayTeam))))
                win++;
            if ((list.get(i).homeShotsOnTarget >= list.get(i).awayShotsOnTarget&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayShotsOnTarget >= list.get(i).homeShotsOnTarget))&&(teamName.equals(list.get(i).awayTeam))))
                winX++;
        }

        String t85 = "ТБ(" + String.valueOf(totalAverageShots-1) + ")";
        String t85s = String.valueOf((int) (totalMinus1)) + "/" + list.size() + " (" + MyMath.round(totalMinus1/list.size()*100, 1) + ")";
        if (totalMinus1/list.size() < 0.5){
            t85 = "ТM(" + String.valueOf(totalAverageShots-1) + ")";
            t85s = String.valueOf((int) (list.size() - totalMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalMinus1)/list.size()*100, 1) + ")";
        }
        String t95 = "ТБ(" + String.valueOf(totalAverageShots) + ")";
        String t95s = String.valueOf((int) (totalSred)) + "/" + list.size() + " (" + MyMath.round(totalSred/list.size()*100, 1) + ")";
        if (totalSred/list.size() < 0.5){
            t95 = "ТM(" + String.valueOf(totalAverageShots) + ")";
            t95s = String.valueOf((int) (list.size() - totalSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalSred)/list.size()*100, 1) + ")";
        }

        String t105 = "ТБ(" + String.valueOf(totalAverageShots+1) + ")";
        String t105s = String.valueOf((int) (totalPlus1)) + "/" + list.size() + " (" + MyMath.round(totalPlus1/list.size()*100, 1) + ")";
        if (totalPlus1/list.size() < 0.5){
            t105 = "ТM(" + String.valueOf(totalAverageShots+1) + ")";
            t105s = String.valueOf((int) (list.size() - totalPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalPlus1)/list.size()*100, 1) + ")";
        }

        double total = selfAverageShots + 1;
        String itbPlus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBPlus1 = String.valueOf((int) (selfPlus1)) + "/" + list.size() + " (" + MyMath.round(selfPlus1/list.size()*100, 1) + ")";
        if (selfPlus1/list.size() < 0.5){
            itbPlus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBPlus1 = String.valueOf((int) (list.size() - selfPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfPlus1)/list.size()*100, 1) + ")";
        }

        total = selfAverageShots;
        String itbSred = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBSred = String.valueOf((int) (selfSred)) + "/" + list.size() + " (" + MyMath.round(selfSred/list.size()*100, 1) + ")";
        if (selfSred/list.size() < 0.5){
            itbSred = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBSred = String.valueOf((int) (list.size() - selfSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfSred)/list.size()*100, 1) + ")";
        }

        total = selfAverageShots - 1;
        String itbMinus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBMinus1 = String.valueOf((int) (selfMinus1)) + "/" + list.size() + " (" + MyMath.round(selfMinus1/list.size()*100, 1) + ")";
        if (selfMinus1/list.size() < 0.5){
            itbMinus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBMinus1 = String.valueOf((int) (list.size() - selfMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfMinus1)/list.size()*100, 1) + ")";
        }

        total = opAverageShots + 1;
        String optbPlus1 = "Opp: ТБ(" + total + ")";
        String opTBPlus1 = String.valueOf((int) (opPlus1)) + "/" + list.size() + " (" + MyMath.round(opPlus1/list.size()*100, 1) + ")";
        if (opPlus1/list.size() < 0.5){
            optbPlus1 = "Opp: ТM(" + total + ")";
            opTBPlus1 = String.valueOf((int) (list.size() - opPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opPlus1)/list.size()*100, 1) + ")";
        }

        total = opAverageShots;
        String optbSred = "Opp: ТБ(" + total + ")";
        String opTBSred = String.valueOf((int) (opSred)) + "/" + list.size() + " (" + MyMath.round(opSred/list.size()*100, 1) + ")";
        if (opSred/list.size() < 0.5){
            optbSred = "Opp: ТM(" + total + ")";
            opTBSred = String.valueOf((int) (list.size() - opSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - opSred)/list.size()*100, 1) + ")";
        }

        total = opAverageShots - 1;
        String optbMinus1 = "Opp: ТБ(" + total + ")";
        String opTBMinus1 = String.valueOf((int) (opMinus1)) + "/" + list.size() + " (" + MyMath.round(opMinus1/list.size()*100, 1) + ")";
        if (opMinus1/list.size() < 0.5){
            optbMinus1 = "Opp: ТM(" + total + ")";
            opTBMinus1 = String.valueOf((int) (list.size() - opMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opMinus1)/list.size()*100, 1) + ")";
        }

        String winS = Team.getShortName(teamName) + ": Ф(-0.5)";
        String winSS = String.valueOf((int) win) + "/" + list.size() + " (" + MyMath.round(win/list.size()*100, 1) + ")";

        String winXS = Team.getShortName(teamName) + ": Ф(+0.5)";
        String winXSS = String.valueOf((int) winX) + "/" + list.size() + " (" + MyMath.round(winX/list.size()*100, 1) + ")";

        String[] colHeads = {"Ставка", "Заход и %"};
        Object[][] data = {
                {t85 , t85s},
                {t95 , t95s},
                {t105 , t105s},
                {itbMinus1 , selfTBMinus1},
                {itbSred , selfTBSred},
                {itbPlus1 , selfTBPlus1},
                {optbMinus1 , opTBMinus1},
                {optbSred , opTBSred},
                {optbPlus1 , opTBPlus1},
                {winS , winSS},
                {winXS , winXSS},
        };

        Font font = new Font("Arial", Font.BOLD, 12);
        JTable tableUSV = new JTable(data, colHeads);
        tableUSV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUSV.setEnabled(false);
        tableUSV.getTableHeader().setFont(font);
        tableUSV.setFont(font);
        tableUSV.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableUSV.setRowHeight(25);
        tableUSV.getColumnModel().getColumn(1).setPreferredWidth(92);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableUSV.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableUSV.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableUSV.setBackground(new Color(238, 238, 238));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
        tablePanel.add(tableUSV, BorderLayout.CENTER);
        tablePanel.add(tableUSV.getTableHeader(), BorderLayout.NORTH);

        return tablePanel;
    }

    public static JPanel getTableShotsOnTarget1per(String teamName, /*ArrayList<Match> list*/ Selector selector){
        double win = 0;
        double winX = 0;

        double parameter = Double.parseDouble(selector.pList.get(22).get(1)) / (double) selector.listOfMatches.size();
        double parameterOpponent = Double.parseDouble(selector.pList.get(22).get(2)) / (double) selector.listOfMatches.size();

        double totalAverageShots = (parameter + parameterOpponent) ;
        double selfAverageShots =  parameter;
        double opAverageShots = parameterOpponent;

        if (MyMath.round(totalAverageShots,0) > totalAverageShots)
            totalAverageShots = MyMath.round(totalAverageShots,0) - 0.5;
        else
            totalAverageShots = MyMath.round(totalAverageShots,0) + 0.5;
        if (MyMath.round(selfAverageShots,0) > selfAverageShots)
            selfAverageShots = MyMath.round(selfAverageShots,0) - 0.5;
        else
            selfAverageShots = MyMath.round(selfAverageShots,0) + 0.5;
        if (MyMath.round(opAverageShots,0) > opAverageShots)
            opAverageShots = MyMath.round(opAverageShots,0) - 0.5;
        else
            opAverageShots = MyMath.round(opAverageShots,0) + 0.5;
        double totalSred = 0;
        double totalPlus1 = 0;
        double totalMinus1 = 0;
        double selfSred = 0;
        double selfPlus1 = 0;
        double selfMinus1 = 0;
        double opSred = 0;
        double opPlus1 = 0;
        double opMinus1 = 0;

        ArrayList<Match> list = selector.listOfMatches;

        for (int i=0; i<list.size(); i++){
            if (list.get(i).homeShotsOnTarget1stPeriod + list.get(i).awayShotsOnTarget1stPeriod > (totalAverageShots-1))
                totalMinus1++;
            if (list.get(i).homeShotsOnTarget1stPeriod + list.get(i).awayShotsOnTarget1stPeriod > totalAverageShots)
                totalSred++;
            if (list.get(i).homeShotsOnTarget1stPeriod + list.get(i).awayShotsOnTarget1stPeriod > (totalAverageShots+1))
                totalPlus1++;

            if ((list.get(i).homeShotsOnTarget1stPeriod > (selfAverageShots+1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayShotsOnTarget1stPeriod > (selfAverageShots+1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfPlus1++;
            if ((list.get(i).homeShotsOnTarget1stPeriod > (selfAverageShots)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayShotsOnTarget1stPeriod > (selfAverageShots)))&&(teamName.equals(list.get(i).awayTeam))))
                selfSred++;
            if ((list.get(i).homeShotsOnTarget1stPeriod > (selfAverageShots-1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayShotsOnTarget1stPeriod > (selfAverageShots-1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfMinus1++;

            if ((list.get(i).homeShotsOnTarget1stPeriod > (opAverageShots+1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayShotsOnTarget1stPeriod > (opAverageShots+1)))&&(teamName.equals(list.get(i).homeTeam))))
                opPlus1++;
            if ((list.get(i).homeShotsOnTarget1stPeriod > (opAverageShots)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayShotsOnTarget1stPeriod > (opAverageShots)))&&(teamName.equals(list.get(i).homeTeam))))
                opSred++;
            if ((list.get(i).homeShotsOnTarget1stPeriod > (opAverageShots-1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayShotsOnTarget1stPeriod > (opAverageShots-1)))&&(teamName.equals(list.get(i).homeTeam))))
                opMinus1++;
            if ((list.get(i).homeShotsOnTarget1stPeriod > list.get(i).awayShotsOnTarget1stPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayShotsOnTarget1stPeriod > list.get(i).homeShotsOnTarget1stPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                win++;
            if ((list.get(i).homeShotsOnTarget1stPeriod >= list.get(i).awayShotsOnTarget1stPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayShotsOnTarget1stPeriod >= list.get(i).homeShotsOnTarget1stPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                winX++;
        }

        String t85 = "ТБ(" + String.valueOf(totalAverageShots-1) + ")";
        String t85s = String.valueOf((int) (totalMinus1)) + "/" + list.size() + " (" + MyMath.round(totalMinus1/list.size()*100, 1) + ")";
        if (totalMinus1/list.size() < 0.5){
            t85 = "ТM(" + String.valueOf(totalAverageShots-1) + ")";
            t85s = String.valueOf((int) (list.size() - totalMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalMinus1)/list.size()*100, 1) + ")";
        }
        String t95 = "ТБ(" + String.valueOf(totalAverageShots) + ")";
        String t95s = String.valueOf((int) (totalSred)) + "/" + list.size() + " (" + MyMath.round(totalSred/list.size()*100, 1) + ")";
        if (totalSred/list.size() < 0.5){
            t95 = "ТM(" + String.valueOf(totalAverageShots) + ")";
            t95s = String.valueOf((int) (list.size() - totalSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalSred)/list.size()*100, 1) + ")";
        }

        String t105 = "ТБ(" + String.valueOf(totalAverageShots+1) + ")";
        String t105s = String.valueOf((int) (totalPlus1)) + "/" + list.size() + " (" + MyMath.round(totalPlus1/list.size()*100, 1) + ")";
        if (totalPlus1/list.size() < 0.5){
            t105 = "ТM(" + String.valueOf(totalAverageShots+1) + ")";
            t105s = String.valueOf((int) (list.size() - totalPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalPlus1)/list.size()*100, 1) + ")";
        }

        double total = selfAverageShots + 1;
        String itbPlus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBPlus1 = String.valueOf((int) (selfPlus1)) + "/" + list.size() + " (" + MyMath.round(selfPlus1/list.size()*100, 1) + ")";
        if (selfPlus1/list.size() < 0.5){
            itbPlus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBPlus1 = String.valueOf((int) (list.size() - selfPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfPlus1)/list.size()*100, 1) + ")";
        }

        total = selfAverageShots;
        String itbSred = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBSred = String.valueOf((int) (selfSred)) + "/" + list.size() + " (" + MyMath.round(selfSred/list.size()*100, 1) + ")";
        if (selfSred/list.size() < 0.5){
            itbSred = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBSred = String.valueOf((int) (list.size() - selfSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfSred)/list.size()*100, 1) + ")";
        }

        total = selfAverageShots - 1;
        String itbMinus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBMinus1 = String.valueOf((int) (selfMinus1)) + "/" + list.size() + " (" + MyMath.round(selfMinus1/list.size()*100, 1) + ")";
        if (selfMinus1/list.size() < 0.5){
            itbMinus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBMinus1 = String.valueOf((int) (list.size() - selfMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfMinus1)/list.size()*100, 1) + ")";
        }

        total = opAverageShots + 1;
        String optbPlus1 = "Opp: ТБ(" + total + ")";
        String opTBPlus1 = String.valueOf((int) (opPlus1)) + "/" + list.size() + " (" + MyMath.round(opPlus1/list.size()*100, 1) + ")";
        if (opPlus1/list.size() < 0.5){
            optbPlus1 = "Opp: ТM(" + total + ")";
            opTBPlus1 = String.valueOf((int) (list.size() - opPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opPlus1)/list.size()*100, 1) + ")";
        }

        total = opAverageShots;
        String optbSred = "Opp: ТБ(" + total + ")";
        String opTBSred = String.valueOf((int) (opSred)) + "/" + list.size() + " (" + MyMath.round(opSred/list.size()*100, 1) + ")";
        if (opSred/list.size() < 0.5){
            optbSred = "Opp: ТM(" + total + ")";
            opTBSred = String.valueOf((int) (list.size() - opSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - opSred)/list.size()*100, 1) + ")";
        }

        total = opAverageShots - 1;
        String optbMinus1 = "Opp: ТБ(" + total + ")";
        String opTBMinus1 = String.valueOf((int) (opMinus1)) + "/" + list.size() + " (" + MyMath.round(opMinus1/list.size()*100, 1) + ")";
        if (opMinus1/list.size() < 0.5){
            optbMinus1 = "Opp: ТM(" + total + ")";
            opTBMinus1 = String.valueOf((int) (list.size() - opMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opMinus1)/list.size()*100, 1) + ")";
        }

        String winS = Team.getShortName(teamName) + ": Ф(-0.5)";
        String winSS = String.valueOf((int) win) + "/" + list.size() + " (" + MyMath.round(win/list.size()*100, 1) + ")";

        String winXS = Team.getShortName(teamName) + ": Ф(+0.5)";
        String winXSS = String.valueOf((int) winX) + "/" + list.size() + " (" + MyMath.round(winX/list.size()*100, 1) + ")";

        String[] colHeads = {"Ставка", "Заход и %"};
        Object[][] data = {
                {t85 , t85s},
                {t95 , t95s},
                {t105 , t105s},
                {itbMinus1 , selfTBMinus1},
                {itbSred , selfTBSred},
                {itbPlus1 , selfTBPlus1},
                {optbMinus1 , opTBMinus1},
                {optbSred , opTBSred},
                {optbPlus1 , opTBPlus1},
                {winS , winSS},
                {winXS , winXSS},
        };

        Font font = new Font("Arial", Font.BOLD, 12);
        JTable tableUSV = new JTable(data, colHeads);
        tableUSV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUSV.setEnabled(false);
        tableUSV.getTableHeader().setFont(font);
        tableUSV.setFont(font);
        tableUSV.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableUSV.setRowHeight(25);
        tableUSV.getColumnModel().getColumn(1).setPreferredWidth(92);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableUSV.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableUSV.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableUSV.setBackground(new Color(238, 238, 238));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
        tablePanel.add(tableUSV, BorderLayout.CENTER);
        tablePanel.add(tableUSV.getTableHeader(), BorderLayout.NORTH);

        return tablePanel;
    }

    public static JPanel getTableShotsOnTarget2per(String teamName, /*ArrayList<Match> list*/ Selector selector){
        double win = 0;
        double winX = 0;

        double parameter = Double.parseDouble(selector.pList.get(23).get(1)) / (double) selector.listOfMatches.size();
        double parameterOpponent = Double.parseDouble(selector.pList.get(23).get(2)) / (double) selector.listOfMatches.size();

        double totalAverageShots = (parameter + parameterOpponent);
        double selfAverageShots = parameter;
        double opAverageShots = parameterOpponent;

        if (MyMath.round(totalAverageShots,0) > totalAverageShots)
            totalAverageShots = MyMath.round(totalAverageShots,0) - 0.5;
        else
            totalAverageShots = MyMath.round(totalAverageShots,0) + 0.5;
        if (MyMath.round(selfAverageShots,0) > selfAverageShots)
            selfAverageShots = MyMath.round(selfAverageShots,0) - 0.5;
        else
            selfAverageShots = MyMath.round(selfAverageShots,0) + 0.5;
        if (MyMath.round(opAverageShots,0) > opAverageShots)
            opAverageShots = MyMath.round(opAverageShots,0) - 0.5;
        else
            opAverageShots = MyMath.round(opAverageShots,0) + 0.5;
        double totalSred = 0;
        double totalPlus1 = 0;
        double totalMinus1 = 0;
        double selfSred = 0;
        double selfPlus1 = 0;
        double selfMinus1 = 0;
        double opSred = 0;
        double opPlus1 = 0;
        double opMinus1 = 0;

        ArrayList<Match> list = selector.listOfMatches;

        for (int i=0; i<list.size(); i++){
            if (list.get(i).homeShotsOnTarget2ndPeriod + list.get(i).awayShotsOnTarget2ndPeriod > (totalAverageShots-1))
                totalMinus1++;
            if (list.get(i).homeShotsOnTarget2ndPeriod + list.get(i).awayShotsOnTarget2ndPeriod > totalAverageShots)
                totalSred++;
            if (list.get(i).homeShotsOnTarget2ndPeriod + list.get(i).awayShotsOnTarget2ndPeriod > (totalAverageShots+1))
                totalPlus1++;

            if ((list.get(i).homeShotsOnTarget2ndPeriod > (selfAverageShots+1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayShotsOnTarget2ndPeriod > (selfAverageShots+1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfPlus1++;
            if ((list.get(i).homeShotsOnTarget2ndPeriod > (selfAverageShots)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayShotsOnTarget2ndPeriod > (selfAverageShots)))&&(teamName.equals(list.get(i).awayTeam))))
                selfSred++;
            if ((list.get(i).homeShotsOnTarget2ndPeriod > (selfAverageShots-1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayShotsOnTarget2ndPeriod > (selfAverageShots-1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfMinus1++;

            if ((list.get(i).homeShotsOnTarget2ndPeriod > (opAverageShots+1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayShotsOnTarget2ndPeriod > (opAverageShots+1)))&&(teamName.equals(list.get(i).homeTeam))))
                opPlus1++;
            if ((list.get(i).homeShotsOnTarget2ndPeriod > (opAverageShots)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayShotsOnTarget2ndPeriod > (opAverageShots)))&&(teamName.equals(list.get(i).homeTeam))))
                opSred++;
            if ((list.get(i).homeShotsOnTarget2ndPeriod > (opAverageShots-1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayShotsOnTarget2ndPeriod > (opAverageShots-1)))&&(teamName.equals(list.get(i).homeTeam))))
                opMinus1++;
            if ((list.get(i).homeShotsOnTarget2ndPeriod > list.get(i).awayShotsOnTarget2ndPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayShotsOnTarget2ndPeriod > list.get(i).homeShotsOnTarget2ndPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                win++;
            if ((list.get(i).homeShotsOnTarget2ndPeriod >= list.get(i).awayShotsOnTarget2ndPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayShotsOnTarget2ndPeriod >= list.get(i).homeShotsOnTarget2ndPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                winX++;
        }

        String t85 = "ТБ(" + String.valueOf(totalAverageShots-1) + ")";
        String t85s = String.valueOf((int) (totalMinus1)) + "/" + list.size() + " (" + MyMath.round(totalMinus1/list.size()*100, 1) + ")";
        if (totalMinus1/list.size() < 0.5){
            t85 = "ТM(" + String.valueOf(totalAverageShots-1) + ")";
            t85s = String.valueOf((int) (list.size() - totalMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalMinus1)/list.size()*100, 1) + ")";
        }
        String t95 = "ТБ(" + String.valueOf(totalAverageShots) + ")";
        String t95s = String.valueOf((int) (totalSred)) + "/" + list.size() + " (" + MyMath.round(totalSred/list.size()*100, 1) + ")";
        if (totalSred/list.size() < 0.5){
            t95 = "ТM(" + String.valueOf(totalAverageShots) + ")";
            t95s = String.valueOf((int) (list.size() - totalSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalSred)/list.size()*100, 1) + ")";
        }

        String t105 = "ТБ(" + String.valueOf(totalAverageShots+1) + ")";
        String t105s = String.valueOf((int) (totalPlus1)) + "/" + list.size() + " (" + MyMath.round(totalPlus1/list.size()*100, 1) + ")";
        if (totalPlus1/list.size() < 0.5){
            t105 = "ТM(" + String.valueOf(totalAverageShots+1) + ")";
            t105s = String.valueOf((int) (list.size() - totalPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalPlus1)/list.size()*100, 1) + ")";
        }

        double total = selfAverageShots + 1;
        String itbPlus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBPlus1 = String.valueOf((int) (selfPlus1)) + "/" + list.size() + " (" + MyMath.round(selfPlus1/list.size()*100, 1) + ")";
        if (selfPlus1/list.size() < 0.5){
            itbPlus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBPlus1 = String.valueOf((int) (list.size() - selfPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfPlus1)/list.size()*100, 1) + ")";
        }

        total = selfAverageShots;
        String itbSred = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBSred = String.valueOf((int) (selfSred)) + "/" + list.size() + " (" + MyMath.round(selfSred/list.size()*100, 1) + ")";
        if (selfSred/list.size() < 0.5){
            itbSred = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBSred = String.valueOf((int) (list.size() - selfSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfSred)/list.size()*100, 1) + ")";
        }

        total = selfAverageShots - 1;
        String itbMinus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBMinus1 = String.valueOf((int) (selfMinus1)) + "/" + list.size() + " (" + MyMath.round(selfMinus1/list.size()*100, 1) + ")";
        if (selfMinus1/list.size() < 0.5){
            itbMinus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBMinus1 = String.valueOf((int) (list.size() - selfMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfMinus1)/list.size()*100, 1) + ")";
        }

        total = opAverageShots + 1;
        String optbPlus1 = "Opp: ТБ(" + total + ")";
        String opTBPlus1 = String.valueOf((int) (opPlus1)) + "/" + list.size() + " (" + MyMath.round(opPlus1/list.size()*100, 1) + ")";
        if (opPlus1/list.size() < 0.5){
            optbPlus1 = "Opp: ТM(" + total + ")";
            opTBPlus1 = String.valueOf((int) (list.size() - opPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opPlus1)/list.size()*100, 1) + ")";
        }

        total = opAverageShots;
        String optbSred = "Opp: ТБ(" + total + ")";
        String opTBSred = String.valueOf((int) (opSred)) + "/" + list.size() + " (" + MyMath.round(opSred/list.size()*100, 1) + ")";
        if (opSred/list.size() < 0.5){
            optbSred = "Opp: ТM(" + total + ")";
            opTBSred = String.valueOf((int) (list.size() - opSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - opSred)/list.size()*100, 1) + ")";
        }

        total = opAverageShots - 1;
        String optbMinus1 = "Opp: ТБ(" + total + ")";
        String opTBMinus1 = String.valueOf((int) (opMinus1)) + "/" + list.size() + " (" + MyMath.round(opMinus1/list.size()*100, 1) + ")";
        if (opMinus1/list.size() < 0.5){
            optbMinus1 = "Opp: ТM(" + total + ")";
            opTBMinus1 = String.valueOf((int) (list.size() - opMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opMinus1)/list.size()*100, 1) + ")";
        }

        String winS = Team.getShortName(teamName) + ": Ф(-0.5)";
        String winSS = String.valueOf((int) win) + "/" + list.size() + " (" + MyMath.round(win/list.size()*100, 1) + ")";

        String winXS = Team.getShortName(teamName) + ": Ф(+0.5)";
        String winXSS = String.valueOf((int) winX) + "/" + list.size() + " (" + MyMath.round(winX/list.size()*100, 1) + ")";

        String[] colHeads = {"Ставка", "Заход и %"};
        Object[][] data = {
                {t85 , t85s},
                {t95 , t95s},
                {t105 , t105s},
                {itbMinus1 , selfTBMinus1},
                {itbSred , selfTBSred},
                {itbPlus1 , selfTBPlus1},
                {optbMinus1 , opTBMinus1},
                {optbSred , opTBSred},
                {optbPlus1 , opTBPlus1},
                {winS , winSS},
                {winXS , winXSS},
        };

        Font font = new Font("Arial", Font.BOLD, 12);
        JTable tableUSV = new JTable(data, colHeads);
        tableUSV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUSV.setEnabled(false);
        tableUSV.getTableHeader().setFont(font);
        tableUSV.setFont(font);
        tableUSV.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableUSV.setRowHeight(25);
        tableUSV.getColumnModel().getColumn(1).setPreferredWidth(92);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableUSV.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableUSV.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableUSV.setBackground(new Color(238, 238, 238));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
        tablePanel.add(tableUSV, BorderLayout.CENTER);
        tablePanel.add(tableUSV.getTableHeader(), BorderLayout.NORTH);

        return tablePanel;
    }

    public static JPanel getTableShotsOnTarget3per(String teamName, /*ArrayList<Match> list*/ Selector selector){
        double win = 0;
        double winX = 0;

        double parameter = Double.parseDouble(selector.pList.get(24).get(1)) / (double) selector.listOfMatches.size();
        double parameterOpponent = Double.parseDouble(selector.pList.get(24).get(2)) / (double) selector.listOfMatches.size();

        double totalAverageShots = (parameter + parameterOpponent);
        double selfAverageShots = parameter;
        double opAverageShots = parameterOpponent;

        if (MyMath.round(totalAverageShots,0) > totalAverageShots)
            totalAverageShots = MyMath.round(totalAverageShots,0) - 0.5;
        else
            totalAverageShots = MyMath.round(totalAverageShots,0) + 0.5;
        if (MyMath.round(selfAverageShots,0) > selfAverageShots)
            selfAverageShots = MyMath.round(selfAverageShots,0) - 0.5;
        else
            selfAverageShots = MyMath.round(selfAverageShots,0) + 0.5;
        if (MyMath.round(opAverageShots,0) > opAverageShots)
            opAverageShots = MyMath.round(opAverageShots,0) - 0.5;
        else
            opAverageShots = MyMath.round(opAverageShots,0) + 0.5;
        double totalSred = 0;
        double totalPlus1 = 0;
        double totalMinus1 = 0;
        double selfSred = 0;
        double selfPlus1 = 0;
        double selfMinus1 = 0;
        double opSred = 0;
        double opPlus1 = 0;
        double opMinus1 = 0;

        ArrayList<Match> list = selector.listOfMatches;

        for (int i=0; i<list.size(); i++){
            if (list.get(i).homeShotsOnTarget3rdPeriod + list.get(i).awayShotsOnTarget3rdPeriod > (totalAverageShots-1))
                totalMinus1++;
            if (list.get(i).homeShotsOnTarget3rdPeriod + list.get(i).awayShotsOnTarget3rdPeriod > totalAverageShots)
                totalSred++;
            if (list.get(i).homeShotsOnTarget3rdPeriod + list.get(i).awayShotsOnTarget3rdPeriod > (totalAverageShots+1))
                totalPlus1++;

            if ((list.get(i).homeShotsOnTarget3rdPeriod > (selfAverageShots+1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayShotsOnTarget3rdPeriod > (selfAverageShots+1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfPlus1++;
            if ((list.get(i).homeShotsOnTarget3rdPeriod > (selfAverageShots)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayShotsOnTarget3rdPeriod > (selfAverageShots)))&&(teamName.equals(list.get(i).awayTeam))))
                selfSred++;
            if ((list.get(i).homeShotsOnTarget3rdPeriod > (selfAverageShots-1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayShotsOnTarget3rdPeriod > (selfAverageShots-1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfMinus1++;

            if ((list.get(i).homeShotsOnTarget3rdPeriod > (opAverageShots+1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayShotsOnTarget3rdPeriod > (opAverageShots+1)))&&(teamName.equals(list.get(i).homeTeam))))
                opPlus1++;
            if ((list.get(i).homeShotsOnTarget3rdPeriod > (opAverageShots)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayShotsOnTarget3rdPeriod > (opAverageShots)))&&(teamName.equals(list.get(i).homeTeam))))
                opSred++;
            if ((list.get(i).homeShotsOnTarget3rdPeriod > (opAverageShots-1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayShotsOnTarget3rdPeriod > (opAverageShots-1)))&&(teamName.equals(list.get(i).homeTeam))))
                opMinus1++;
            if ((list.get(i).homeShotsOnTarget3rdPeriod > list.get(i).awayShotsOnTarget3rdPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayShotsOnTarget3rdPeriod > list.get(i).homeShotsOnTarget3rdPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                win++;
            if ((list.get(i).homeShotsOnTarget3rdPeriod >= list.get(i).awayShotsOnTarget3rdPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayShotsOnTarget3rdPeriod >= list.get(i).homeShotsOnTarget3rdPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                winX++;
        }

        String t85 = "ТБ(" + String.valueOf(totalAverageShots-1) + ")";
        String t85s = String.valueOf((int) (totalMinus1)) + "/" + list.size() + " (" + MyMath.round(totalMinus1/list.size()*100, 1) + ")";
        if (totalMinus1/list.size() < 0.5){
            t85 = "ТM(" + String.valueOf(totalAverageShots-1) + ")";
            t85s = String.valueOf((int) (list.size() - totalMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalMinus1)/list.size()*100, 1) + ")";
        }
        String t95 = "ТБ(" + String.valueOf(totalAverageShots) + ")";
        String t95s = String.valueOf((int) (totalSred)) + "/" + list.size() + " (" + MyMath.round(totalSred/list.size()*100, 1) + ")";
        if (totalSred/list.size() < 0.5){
            t95 = "ТM(" + String.valueOf(totalAverageShots) + ")";
            t95s = String.valueOf((int) (list.size() - totalSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalSred)/list.size()*100, 1) + ")";
        }

        String t105 = "ТБ(" + String.valueOf(totalAverageShots+1) + ")";
        String t105s = String.valueOf((int) (totalPlus1)) + "/" + list.size() + " (" + MyMath.round(totalPlus1/list.size()*100, 1) + ")";
        if (totalPlus1/list.size() < 0.5){
            t105 = "ТM(" + String.valueOf(totalAverageShots+1) + ")";
            t105s = String.valueOf((int) (list.size() - totalPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalPlus1)/list.size()*100, 1) + ")";
        }

        double total = selfAverageShots + 1;
        String itbPlus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBPlus1 = String.valueOf((int) (selfPlus1)) + "/" + list.size() + " (" + MyMath.round(selfPlus1/list.size()*100, 1) + ")";
        if (selfPlus1/list.size() < 0.5){
            itbPlus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBPlus1 = String.valueOf((int) (list.size() - selfPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfPlus1)/list.size()*100, 1) + ")";
        }

        total = selfAverageShots;
        String itbSred = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBSred = String.valueOf((int) (selfSred)) + "/" + list.size() + " (" + MyMath.round(selfSred/list.size()*100, 1) + ")";
        if (selfSred/list.size() < 0.5){
            itbSred = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBSred = String.valueOf((int) (list.size() - selfSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfSred)/list.size()*100, 1) + ")";
        }

        total = selfAverageShots - 1;
        String itbMinus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBMinus1 = String.valueOf((int) (selfMinus1)) + "/" + list.size() + " (" + MyMath.round(selfMinus1/list.size()*100, 1) + ")";
        if (selfMinus1/list.size() < 0.5){
            itbMinus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBMinus1 = String.valueOf((int) (list.size() - selfMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfMinus1)/list.size()*100, 1) + ")";
        }

        total = opAverageShots + 1;
        String optbPlus1 = "Opp: ТБ(" + total + ")";
        String opTBPlus1 = String.valueOf((int) (opPlus1)) + "/" + list.size() + " (" + MyMath.round(opPlus1/list.size()*100, 1) + ")";
        if (opPlus1/list.size() < 0.5){
            optbPlus1 = "Opp: ТM(" + total + ")";
            opTBPlus1 = String.valueOf((int) (list.size() - opPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opPlus1)/list.size()*100, 1) + ")";
        }

        total = opAverageShots;
        String optbSred = "Opp: ТБ(" + total + ")";
        String opTBSred = String.valueOf((int) (opSred)) + "/" + list.size() + " (" + MyMath.round(opSred/list.size()*100, 1) + ")";
        if (opSred/list.size() < 0.5){
            optbSred = "Opp: ТM(" + total + ")";
            opTBSred = String.valueOf((int) (list.size() - opSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - opSred)/list.size()*100, 1) + ")";
        }

        total = opAverageShots - 1;
        String optbMinus1 = "Opp: ТБ(" + total + ")";
        String opTBMinus1 = String.valueOf((int) (opMinus1)) + "/" + list.size() + " (" + MyMath.round(opMinus1/list.size()*100, 1) + ")";
        if (opMinus1/list.size() < 0.5){
            optbMinus1 = "Opp: ТM(" + total + ")";
            opTBMinus1 = String.valueOf((int) (list.size() - opMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opMinus1)/list.size()*100, 1) + ")";
        }

        String winS = Team.getShortName(teamName) + ": Ф(-0.5)";
        String winSS = String.valueOf((int) win) + "/" + list.size() + " (" + MyMath.round(win/list.size()*100, 1) + ")";

        String winXS = Team.getShortName(teamName) + ": Ф(+0.5)";
        String winXSS = String.valueOf((int) winX) + "/" + list.size() + " (" + MyMath.round(winX/list.size()*100, 1) + ")";

        String[] colHeads = {"Ставка", "Заход и %"};
        Object[][] data = {
                {t85 , t85s},
                {t95 , t95s},
                {t105 , t105s},
                {itbMinus1 , selfTBMinus1},
                {itbSred , selfTBSred},
                {itbPlus1 , selfTBPlus1},
                {optbMinus1 , opTBMinus1},
                {optbSred , opTBSred},
                {optbPlus1 , opTBPlus1},
                {winS , winSS},
                {winXS , winXSS},
        };

        Font font = new Font("Arial", Font.BOLD, 12);
        JTable tableUSV = new JTable(data, colHeads);
        tableUSV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUSV.setEnabled(false);
        tableUSV.getTableHeader().setFont(font);
        tableUSV.setFont(font);
        tableUSV.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableUSV.setRowHeight(25);
        tableUSV.getColumnModel().getColumn(1).setPreferredWidth(92);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableUSV.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableUSV.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableUSV.setBackground(new Color(238, 238, 238));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
        tablePanel.add(tableUSV, BorderLayout.CENTER);
        tablePanel.add(tableUSV.getTableHeader(), BorderLayout.NORTH);

        return tablePanel;
    }

    public static JPanel getTableBlockedShots(String teamName, /*ArrayList<Match> list*/ Selector selector){
        double win = 0;
        double winX = 0;

        double blockedShots = Double.parseDouble(selector.pList.get(12).get(1));
        double blockedShotsOpponent = Double.parseDouble(selector.pList.get(12).get(2));

        double totalAverageBlockedShots = blockedShots + blockedShotsOpponent;
        double selfAverageBlockedShots =  blockedShots;
        double opAverageBlockedShots =  blockedShotsOpponent;

        if (MyMath.round(totalAverageBlockedShots,0) > totalAverageBlockedShots)
            totalAverageBlockedShots = MyMath.round(totalAverageBlockedShots,0) - 0.5;
        else
            totalAverageBlockedShots = MyMath.round(totalAverageBlockedShots,0) + 0.5;
        if (MyMath.round(selfAverageBlockedShots,0) > selfAverageBlockedShots)
            selfAverageBlockedShots = MyMath.round(selfAverageBlockedShots,0) - 0.5;
        else
            selfAverageBlockedShots = MyMath.round(selfAverageBlockedShots,0) + 0.5;
        if (MyMath.round(opAverageBlockedShots,0) > opAverageBlockedShots)
            opAverageBlockedShots = MyMath.round(opAverageBlockedShots,0) - 0.5;
        else
            opAverageBlockedShots = MyMath.round(opAverageBlockedShots,0) + 0.5;
        double totalSred = 0;
        double totalPlus1 = 0;
        double totalMinus1 = 0;
        double selfSred = 0;
        double selfPlus1 = 0;
        double selfMinus1 = 0;
        double opSred = 0;
        double opPlus1 = 0;
        double opMinus1 = 0;

        ArrayList<Match> list = selector.listOfMatches;

        for (int i=0; i<list.size(); i++){
            if (list.get(i).homeBlockedShots + list.get(i).awayBlockedShots > (totalAverageBlockedShots-1))
                totalMinus1++;
            if (list.get(i).homeBlockedShots + list.get(i).awayBlockedShots > totalAverageBlockedShots)
                totalSred++;
            if (list.get(i).homeBlockedShots + list.get(i).awayBlockedShots > (totalAverageBlockedShots+1))
                totalPlus1++;

            if ((list.get(i).homeBlockedShots > (selfAverageBlockedShots+1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayBlockedShots > (selfAverageBlockedShots+1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfPlus1++;
            if ((list.get(i).homeBlockedShots > (selfAverageBlockedShots)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayBlockedShots > (selfAverageBlockedShots)))&&(teamName.equals(list.get(i).awayTeam))))
                selfSred++;
            if ((list.get(i).homeBlockedShots > (selfAverageBlockedShots-1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayBlockedShots > (selfAverageBlockedShots-1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfMinus1++;

            if ((list.get(i).homeBlockedShots > (opAverageBlockedShots+1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayBlockedShots > (opAverageBlockedShots+1)))&&(teamName.equals(list.get(i).homeTeam))))
                opPlus1++;
            if ((list.get(i).homeBlockedShots > (opAverageBlockedShots)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayBlockedShots > (opAverageBlockedShots)))&&(teamName.equals(list.get(i).homeTeam))))
                opSred++;
            if ((list.get(i).homeBlockedShots > (opAverageBlockedShots-1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayBlockedShots > (opAverageBlockedShots-1)))&&(teamName.equals(list.get(i).homeTeam))))
                opMinus1++;
            if ((list.get(i).homeBlockedShots > list.get(i).awayBlockedShots&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayBlockedShots > list.get(i).homeBlockedShots))&&(teamName.equals(list.get(i).awayTeam))))
                win++;
            if ((list.get(i).homeBlockedShots >= list.get(i).awayBlockedShots&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayBlockedShots >= list.get(i).homeBlockedShots))&&(teamName.equals(list.get(i).awayTeam))))
                winX++;
        }

        String t85 = "ТБ(" + String.valueOf(totalAverageBlockedShots-1) + ")";
        String t85s = String.valueOf((int) (totalMinus1)) + "/" + list.size() + " (" + MyMath.round(totalMinus1/list.size()*100, 1) + ")";
        if (totalMinus1/list.size() < 0.5){
            t85 = "ТM(" + String.valueOf(totalAverageBlockedShots-1) + ")";
            t85s = String.valueOf((int) (list.size() - totalMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalMinus1)/list.size()*100, 1) + ")";
        }
        String t95 = "ТБ(" + String.valueOf(totalAverageBlockedShots) + ")";
        String t95s = String.valueOf((int) (totalSred)) + "/" + list.size() + " (" + MyMath.round(totalSred/list.size()*100, 1) + ")";
        if (totalSred/list.size() < 0.5){
            t95 = "ТM(" + String.valueOf(totalAverageBlockedShots) + ")";
            t95s = String.valueOf((int) (list.size() - totalSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalSred)/list.size()*100, 1) + ")";
        }

        String t105 = "ТБ(" + String.valueOf(totalAverageBlockedShots+1) + ")";
        String t105s = String.valueOf((int) (totalPlus1)) + "/" + list.size() + " (" + MyMath.round(totalPlus1/list.size()*100, 1) + ")";
        if (totalPlus1/list.size() < 0.5){
            t105 = "ТM(" + String.valueOf(totalAverageBlockedShots+1) + ")";
            t105s = String.valueOf((int) (list.size() - totalPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalPlus1)/list.size()*100, 1) + ")";
        }

        double total = selfAverageBlockedShots + 1;
        String itbPlus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBPlus1 = String.valueOf((int) (selfPlus1)) + "/" + list.size() + " (" + MyMath.round(selfPlus1/list.size()*100, 1) + ")";
        if (selfPlus1/list.size() < 0.5){
            itbPlus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBPlus1 = String.valueOf((int) (list.size() - selfPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfPlus1)/list.size()*100, 1) + ")";
        }

        total = selfAverageBlockedShots;
        String itbSred = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBSred = String.valueOf((int) (selfSred)) + "/" + list.size() + " (" + MyMath.round(selfSred/list.size()*100, 1) + ")";
        if (selfSred/list.size() < 0.5){
            itbSred = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBSred = String.valueOf((int) (list.size() - selfSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfSred)/list.size()*100, 1) + ")";
        }

        total = selfAverageBlockedShots - 1;
        String itbMinus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBMinus1 = String.valueOf((int) (selfMinus1)) + "/" + list.size() + " (" + MyMath.round(selfMinus1/list.size()*100, 1) + ")";
        if (selfMinus1/list.size() < 0.5){
            itbMinus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBMinus1 = String.valueOf((int) (list.size() - selfMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfMinus1)/list.size()*100, 1) + ")";
        }

        total = opAverageBlockedShots + 1;
        String optbPlus1 = "Opp: ТБ(" + total + ")";
        String opTBPlus1 = String.valueOf((int) (opPlus1)) + "/" + list.size() + " (" + MyMath.round(opPlus1/list.size()*100, 1) + ")";
        if (opPlus1/list.size() < 0.5){
            optbPlus1 = "Opp: ТM(" + total + ")";
            opTBPlus1 = String.valueOf((int) (list.size() - opPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opPlus1)/list.size()*100, 1) + ")";
        }

        total = opAverageBlockedShots;
        String optbSred = "Opp: ТБ(" + total + ")";
        String opTBSred = String.valueOf((int) (opSred)) + "/" + list.size() + " (" + MyMath.round(opSred/list.size()*100, 1) + ")";
        if (opSred/list.size() < 0.5){
            optbSred = "Opp: ТM(" + total + ")";
            opTBSred = String.valueOf((int) (list.size() - opSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - opSred)/list.size()*100, 1) + ")";
        }

        total = opAverageBlockedShots - 1;
        String optbMinus1 = "Opp: ТБ(" + total + ")";
        String opTBMinus1 = String.valueOf((int) (opMinus1)) + "/" + list.size() + " (" + MyMath.round(opMinus1/list.size()*100, 1) + ")";
        if (opMinus1/list.size() < 0.5){
            optbMinus1 = "Opp: ТM(" + total + ")";
            opTBMinus1 = String.valueOf((int) (list.size() - opMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opMinus1)/list.size()*100, 1) + ")";
        }

        String winS = Team.getShortName(teamName) + ": Ф(-0.5)";
        String winSS = String.valueOf((int) win) + "/" + list.size() + " (" + MyMath.round(win/list.size()*100, 1) + ")";

        String winXS = Team.getShortName(teamName) + ": Ф(+0.5)";
        String winXSS = String.valueOf((int) winX) + "/" + list.size() + " (" + MyMath.round(winX/list.size()*100, 1) + ")";

        String[] colHeads = {"Ставка", "Заход и %"};
        Object[][] data = {
                {t85 , t85s},
                {t95 , t95s},
                {t105 , t105s},
                {itbMinus1 , selfTBMinus1},
                {itbSred , selfTBSred},
                {itbPlus1 , selfTBPlus1},
                {optbMinus1 , opTBMinus1},
                {optbSred , opTBSred},
                {optbPlus1 , opTBPlus1},
                {winS , winSS},
                {winXS , winXSS},
        };

        Font font = new Font("Arial", Font.BOLD, 12);
        JTable tableUSV = new JTable(data, colHeads);
        tableUSV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUSV.setEnabled(false);
        tableUSV.getTableHeader().setFont(font);
        tableUSV.setFont(font);
        tableUSV.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableUSV.setRowHeight(25);
        tableUSV.getColumnModel().getColumn(1).setPreferredWidth(92);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableUSV.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableUSV.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableUSV.setBackground(new Color(238, 238, 238));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
        tablePanel.add(tableUSV, BorderLayout.CENTER);
        tablePanel.add(tableUSV.getTableHeader(), BorderLayout.NORTH);

        return tablePanel;
    }

    public static JPanel getTableFaceoffs(String teamName, /*ArrayList<Match> list*/ Selector selector){
        double win = 0;
        double winX = 0;

        double faceoffs = Double.parseDouble(selector.pList.get(11).get(1));
        double faceoffsOpponent = Double.parseDouble(selector.pList.get(11).get(2));

        double totalAverageFaceoffs = faceoffs + faceoffsOpponent;
        double selfAverageFaceoffs =  faceoffs;
        double opAverageFaceoffs =  faceoffsOpponent;

        if (MyMath.round(totalAverageFaceoffs,0) > totalAverageFaceoffs)
            totalAverageFaceoffs = MyMath.round(totalAverageFaceoffs,0) - 0.5;
        else
            totalAverageFaceoffs = MyMath.round(totalAverageFaceoffs,0) + 0.5;
        if (MyMath.round(selfAverageFaceoffs,0) > selfAverageFaceoffs)
            selfAverageFaceoffs = MyMath.round(selfAverageFaceoffs,0) - 0.5;
        else
            selfAverageFaceoffs = MyMath.round(selfAverageFaceoffs,0) + 0.5;
        if (MyMath.round(opAverageFaceoffs,0) > opAverageFaceoffs)
            opAverageFaceoffs = MyMath.round(opAverageFaceoffs,0) - 0.5;
        else
            opAverageFaceoffs = MyMath.round(opAverageFaceoffs,0) + 0.5;
        double totalSred = 0;
        double totalPlus1 = 0;
        double totalMinus1 = 0;
        double selfSred = 0;
        double selfPlus1 = 0;
        double selfMinus1 = 0;
        double opSred = 0;
        double opPlus1 = 0;
        double opMinus1 = 0;

        ArrayList<Match> list = selector.listOfMatches;

        for (int i=0; i<list.size(); i++){
            if (list.get(i).homeFaceoffsWon + list.get(i).awayFaceoffsWon > (totalAverageFaceoffs-1))
                totalMinus1++;
            if (list.get(i).homeFaceoffsWon + list.get(i).awayFaceoffsWon > totalAverageFaceoffs)
                totalSred++;
            if (list.get(i).homeFaceoffsWon + list.get(i).awayFaceoffsWon > (totalAverageFaceoffs+1))
                totalPlus1++;

            if ((list.get(i).homeFaceoffsWon > (selfAverageFaceoffs+1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayFaceoffsWon > (selfAverageFaceoffs+1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfPlus1++;
            if ((list.get(i).homeFaceoffsWon > (selfAverageFaceoffs)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayFaceoffsWon > (selfAverageFaceoffs)))&&(teamName.equals(list.get(i).awayTeam))))
                selfSred++;
            if ((list.get(i).homeFaceoffsWon > (selfAverageFaceoffs-1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayFaceoffsWon > (selfAverageFaceoffs-1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfMinus1++;

            if ((list.get(i).homeFaceoffsWon > (opAverageFaceoffs+1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayFaceoffsWon > (opAverageFaceoffs+1)))&&(teamName.equals(list.get(i).homeTeam))))
                opPlus1++;
            if ((list.get(i).homeFaceoffsWon > (opAverageFaceoffs)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayFaceoffsWon > (opAverageFaceoffs)))&&(teamName.equals(list.get(i).homeTeam))))
                opSred++;
            if ((list.get(i).homeFaceoffsWon > (opAverageFaceoffs-1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayFaceoffsWon > (opAverageFaceoffs-1)))&&(teamName.equals(list.get(i).homeTeam))))
                opMinus1++;
            if ((list.get(i).homeFaceoffsWon > list.get(i).awayFaceoffsWon&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayFaceoffsWon > list.get(i).homeFaceoffsWon))&&(teamName.equals(list.get(i).awayTeam))))
                win++;
            if ((list.get(i).homeFaceoffsWon >= list.get(i).awayFaceoffsWon&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayFaceoffsWon >= list.get(i).homeFaceoffsWon))&&(teamName.equals(list.get(i).awayTeam))))
                winX++;
        }

        String t85 = "ТБ(" + String.valueOf(totalAverageFaceoffs-1) + ")";
        String t85s = String.valueOf((int) (totalMinus1)) + "/" + list.size() + " (" + MyMath.round(totalMinus1/list.size()*100, 1) + ")";
        if (totalMinus1/list.size() < 0.5){
            t85 = "ТM(" + String.valueOf(totalAverageFaceoffs-1) + ")";
            t85s = String.valueOf((int) (list.size() - totalMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalMinus1)/list.size()*100, 1) + ")";
        }
        String t95 = "ТБ(" + String.valueOf(totalAverageFaceoffs) + ")";
        String t95s = String.valueOf((int) (totalSred)) + "/" + list.size() + " (" + MyMath.round(totalSred/list.size()*100, 1) + ")";
        if (totalSred/list.size() < 0.5){
            t95 = "ТM(" + String.valueOf(totalAverageFaceoffs) + ")";
            t95s = String.valueOf((int) (list.size() - totalSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalSred)/list.size()*100, 1) + ")";
        }

        String t105 = "ТБ(" + String.valueOf(totalAverageFaceoffs+1) + ")";
        String t105s = String.valueOf((int) (totalPlus1)) + "/" + list.size() + " (" + MyMath.round(totalPlus1/list.size()*100, 1) + ")";
        if (totalPlus1/list.size() < 0.5){
            t105 = "ТM(" + String.valueOf(totalAverageFaceoffs+1) + ")";
            t105s = String.valueOf((int) (list.size() - totalPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalPlus1)/list.size()*100, 1) + ")";
        }

        double total = selfAverageFaceoffs + 1;
        String itbPlus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBPlus1 = String.valueOf((int) (selfPlus1)) + "/" + list.size() + " (" + MyMath.round(selfPlus1/list.size()*100, 1) + ")";
        if (selfPlus1/list.size() < 0.5){
            itbPlus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBPlus1 = String.valueOf((int) (list.size() - selfPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfPlus1)/list.size()*100, 1) + ")";
        }

        total = selfAverageFaceoffs;
        String itbSred = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBSred = String.valueOf((int) (selfSred)) + "/" + list.size() + " (" + MyMath.round(selfSred/list.size()*100, 1) + ")";
        if (selfSred/list.size() < 0.5){
            itbSred = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBSred = String.valueOf((int) (list.size() - selfSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfSred)/list.size()*100, 1) + ")";
        }

        total = selfAverageFaceoffs - 1;
        String itbMinus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBMinus1 = String.valueOf((int) (selfMinus1)) + "/" + list.size() + " (" + MyMath.round(selfMinus1/list.size()*100, 1) + ")";
        if (selfMinus1/list.size() < 0.5){
            itbMinus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBMinus1 = String.valueOf((int) (list.size() - selfMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfMinus1)/list.size()*100, 1) + ")";
        }

        total = opAverageFaceoffs + 1;
        String optbPlus1 = "Opp: ТБ(" + total + ")";
        String opTBPlus1 = String.valueOf((int) (opPlus1)) + "/" + list.size() + " (" + MyMath.round(opPlus1/list.size()*100, 1) + ")";
        if (opPlus1/list.size() < 0.5){
            optbPlus1 = "Opp: ТM(" + total + ")";
            opTBPlus1 = String.valueOf((int) (list.size() - opPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opPlus1)/list.size()*100, 1) + ")";
        }

        total = opAverageFaceoffs;
        String optbSred = "Opp: ТБ(" + total + ")";
        String opTBSred = String.valueOf((int) (opSred)) + "/" + list.size() + " (" + MyMath.round(opSred/list.size()*100, 1) + ")";
        if (opSred/list.size() < 0.5){
            optbSred = "Opp: ТM(" + total + ")";
            opTBSred = String.valueOf((int) (list.size() - opSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - opSred)/list.size()*100, 1) + ")";
        }

        total = opAverageFaceoffs - 1;
        String optbMinus1 = "Opp: ТБ(" + total + ")";
        String opTBMinus1 = String.valueOf((int) (opMinus1)) + "/" + list.size() + " (" + MyMath.round(opMinus1/list.size()*100, 1) + ")";
        if (opMinus1/list.size() < 0.5){
            optbMinus1 = "Opp: ТM(" + total + ")";
            opTBMinus1 = String.valueOf((int) (list.size() - opMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opMinus1)/list.size()*100, 1) + ")";
        }

        String winS = Team.getShortName(teamName) + ": Ф(-0.5)";
        String winSS = String.valueOf((int) win) + "/" + list.size() + " (" + MyMath.round(win/list.size()*100, 1) + ")";

        String winXS = Team.getShortName(teamName) + ": Ф(+0.5)";
        String winXSS = String.valueOf((int) winX) + "/" + list.size() + " (" + MyMath.round(winX/list.size()*100, 1) + ")";

        String[] colHeads = {"Ставка", "Заход и %"};
        Object[][] data = {
                {t85 , t85s},
                {t95 , t95s},
                {t105 , t105s},
                {itbMinus1 , selfTBMinus1},
                {itbSred , selfTBSred},
                {itbPlus1 , selfTBPlus1},
                {optbMinus1 , opTBMinus1},
                {optbSred , opTBSred},
                {optbPlus1 , opTBPlus1},
                {winS , winSS},
                {winXS , winXSS},
        };

        Font font = new Font("Arial", Font.BOLD, 12);
        JTable tableUSV = new JTable(data, colHeads);
        tableUSV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUSV.setEnabled(false);
        tableUSV.getTableHeader().setFont(font);
        tableUSV.setFont(font);
        tableUSV.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableUSV.setRowHeight(25);
        tableUSV.getColumnModel().getColumn(1).setPreferredWidth(92);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableUSV.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableUSV.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableUSV.setBackground(new Color(238, 238, 238));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
        tablePanel.add(tableUSV, BorderLayout.CENTER);
        tablePanel.add(tableUSV.getTableHeader(), BorderLayout.NORTH);

        return tablePanel;
    }

    public static JPanel getTableHits(String teamName, /*ArrayList<Match> list*/ Selector selector){
        double win = 0;
        double winX = 0;

        double hits = Double.parseDouble(selector.pList.get(13).get(1));
        double hitsOpponent = Double.parseDouble(selector.pList.get(13).get(2));

        double totalAverageHits = hits + hitsOpponent;
        double selfAverageHits =  hits;
        double opAverageHits =  hitsOpponent;

        if (MyMath.round(totalAverageHits,0) > totalAverageHits)
            totalAverageHits = MyMath.round(totalAverageHits,0) - 0.5;
        else
            totalAverageHits = MyMath.round(totalAverageHits,0) + 0.5;
        if (MyMath.round(selfAverageHits,0) > selfAverageHits)
            selfAverageHits = MyMath.round(selfAverageHits,0) - 0.5;
        else
            selfAverageHits = MyMath.round(selfAverageHits,0) + 0.5;
        if (MyMath.round(opAverageHits,0) > opAverageHits)
            opAverageHits = MyMath.round(opAverageHits,0) - 0.5;
        else
            opAverageHits = MyMath.round(opAverageHits,0) + 0.5;
        double totalSred = 0;
        double totalPlus1 = 0;
        double totalMinus1 = 0;
        double selfSred = 0;
        double selfPlus1 = 0;
        double selfMinus1 = 0;
        double opSred = 0;
        double opPlus1 = 0;
        double opMinus1 = 0;

        ArrayList<Match> list = selector.listOfMatches;

        for (int i=0; i<list.size(); i++){
            if (list.get(i).homeHits + list.get(i).awayHits > (totalAverageHits-1))
                totalMinus1++;
            if (list.get(i).homeHits + list.get(i).awayHits > totalAverageHits)
                totalSred++;
            if (list.get(i).homeHits + list.get(i).awayHits > (totalAverageHits+1))
                totalPlus1++;

            if ((list.get(i).homeHits > (selfAverageHits+1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayHits > (selfAverageHits+1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfPlus1++;
            if ((list.get(i).homeHits > (selfAverageHits)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayHits > (selfAverageHits)))&&(teamName.equals(list.get(i).awayTeam))))
                selfSred++;
            if ((list.get(i).homeHits > (selfAverageHits-1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayHits > (selfAverageHits-1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfMinus1++;

            if ((list.get(i).homeHits > (opAverageHits+1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayHits > (opAverageHits+1)))&&(teamName.equals(list.get(i).homeTeam))))
                opPlus1++;
            if ((list.get(i).homeHits > (opAverageHits)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayHits > (opAverageHits)))&&(teamName.equals(list.get(i).homeTeam))))
                opSred++;
            if ((list.get(i).homeHits > (opAverageHits-1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayHits > (opAverageHits-1)))&&(teamName.equals(list.get(i).homeTeam))))
                opMinus1++;
            if ((list.get(i).homeHits > list.get(i).awayHits&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayHits > list.get(i).homeHits))&&(teamName.equals(list.get(i).awayTeam))))
                win++;
            if ((list.get(i).homeHits >= list.get(i).awayHits&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayHits >= list.get(i).homeHits))&&(teamName.equals(list.get(i).awayTeam))))
                winX++;
        }

        String t85 = "ТБ(" + String.valueOf(totalAverageHits-1) + ")";
        String t85s = String.valueOf((int) (totalMinus1)) + "/" + list.size() + " (" + MyMath.round(totalMinus1/list.size()*100, 1) + ")";
        if (totalMinus1/list.size() < 0.5){
            t85 = "ТM(" + String.valueOf(totalAverageHits-1) + ")";
            t85s = String.valueOf((int) (list.size() - totalMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalMinus1)/list.size()*100, 1) + ")";
        }
        String t95 = "ТБ(" + String.valueOf(totalAverageHits) + ")";
        String t95s = String.valueOf((int) (totalSred)) + "/" + list.size() + " (" + MyMath.round(totalSred/list.size()*100, 1) + ")";
        if (totalSred/list.size() < 0.5){
            t95 = "ТM(" + String.valueOf(totalAverageHits) + ")";
            t95s = String.valueOf((int) (list.size() - totalSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalSred)/list.size()*100, 1) + ")";
        }

        String t105 = "ТБ(" + String.valueOf(totalAverageHits+1) + ")";
        String t105s = String.valueOf((int) (totalPlus1)) + "/" + list.size() + " (" + MyMath.round(totalPlus1/list.size()*100, 1) + ")";
        if (totalPlus1/list.size() < 0.5){
            t105 = "ТM(" + String.valueOf(totalAverageHits+1) + ")";
            t105s = String.valueOf((int) (list.size() - totalPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalPlus1)/list.size()*100, 1) + ")";
        }

        double total = selfAverageHits + 1;
        String itbPlus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBPlus1 = String.valueOf((int) (selfPlus1)) + "/" + list.size() + " (" + MyMath.round(selfPlus1/list.size()*100, 1) + ")";
        if (selfPlus1/list.size() < 0.5){
            itbPlus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBPlus1 = String.valueOf((int) (list.size() - selfPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfPlus1)/list.size()*100, 1) + ")";
        }

        total = selfAverageHits;
        String itbSred = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBSred = String.valueOf((int) (selfSred)) + "/" + list.size() + " (" + MyMath.round(selfSred/list.size()*100, 1) + ")";
        if (selfSred/list.size() < 0.5){
            itbSred = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBSred = String.valueOf((int) (list.size() - selfSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfSred)/list.size()*100, 1) + ")";
        }

        total = selfAverageHits - 1;
        String itbMinus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBMinus1 = String.valueOf((int) (selfMinus1)) + "/" + list.size() + " (" + MyMath.round(selfMinus1/list.size()*100, 1) + ")";
        if (selfMinus1/list.size() < 0.5){
            itbMinus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBMinus1 = String.valueOf((int) (list.size() - selfMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfMinus1)/list.size()*100, 1) + ")";
        }

        total = opAverageHits + 1;
        String optbPlus1 = "Opp: ТБ(" + total + ")";
        String opTBPlus1 = String.valueOf((int) (opPlus1)) + "/" + list.size() + " (" + MyMath.round(opPlus1/list.size()*100, 1) + ")";
        if (opPlus1/list.size() < 0.5){
            optbPlus1 = "Opp: ТM(" + total + ")";
            opTBPlus1 = String.valueOf((int) (list.size() - opPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opPlus1)/list.size()*100, 1) + ")";
        }

        total = opAverageHits;
        String optbSred = "Opp: ТБ(" + total + ")";
        String opTBSred = String.valueOf((int) (opSred)) + "/" + list.size() + " (" + MyMath.round(opSred/list.size()*100, 1) + ")";
        if (opSred/list.size() < 0.5){
            optbSred = "Opp: ТM(" + total + ")";
            opTBSred = String.valueOf((int) (list.size() - opSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - opSred)/list.size()*100, 1) + ")";
        }

        total = opAverageHits - 1;
        String optbMinus1 = "Opp: ТБ(" + total + ")";
        String opTBMinus1 = String.valueOf((int) (opMinus1)) + "/" + list.size() + " (" + MyMath.round(opMinus1/list.size()*100, 1) + ")";
        if (opMinus1/list.size() < 0.5){
            optbMinus1 = "Opp: ТM(" + total + ")";
            opTBMinus1 = String.valueOf((int) (list.size() - opMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opMinus1)/list.size()*100, 1) + ")";
        }

        String winS = Team.getShortName(teamName) + ": Ф(-0.5)";
        String winSS = String.valueOf((int) win) + "/" + list.size() + " (" + MyMath.round(win/list.size()*100, 1) + ")";

        String winXS = Team.getShortName(teamName) + ": Ф(+0.5)";
        String winXSS = String.valueOf((int) winX) + "/" + list.size() + " (" + MyMath.round(winX/list.size()*100, 1) + ")";

        String[] colHeads = {"Ставка", "Заход и %"};
        Object[][] data = {
                {t85 , t85s},
                {t95 , t95s},
                {t105 , t105s},
                {itbMinus1 , selfTBMinus1},
                {itbSred , selfTBSred},
                {itbPlus1 , selfTBPlus1},
                {optbMinus1 , opTBMinus1},
                {optbSred , opTBSred},
                {optbPlus1 , opTBPlus1},
                {winS , winSS},
                {winXS , winXSS},
        };

        Font font = new Font("Arial", Font.BOLD, 12);
        JTable tableUSV = new JTable(data, colHeads);
        tableUSV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUSV.setEnabled(false);
        tableUSV.getTableHeader().setFont(font);
        tableUSV.setFont(font);
        tableUSV.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableUSV.setRowHeight(25);
        tableUSV.getColumnModel().getColumn(1).setPreferredWidth(92);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableUSV.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableUSV.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableUSV.setBackground(new Color(238, 238, 238));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
        tablePanel.add(tableUSV, BorderLayout.CENTER);
        tablePanel.add(tableUSV.getTableHeader(), BorderLayout.NORTH);

        return tablePanel;
    }

    public static JPanel getTablePenMinutes(String teamName, /*ArrayList<Match> list*/ Selector selector){
        double win = 0;
        double winX = 0;

        double parameter = Double.parseDouble(selector.pList.get(14).get(1)) / (double) selector.listOfMatches.size();
        double parameterOpponent = Double.parseDouble(selector.pList.get(14).get(2)) / (double) selector.listOfMatches.size();

        double totalAveragePenMin = (parameter + parameterOpponent);
        double selfAveragePenMin = parameter;
        double opAveragePenMin = parameterOpponent;


        if (MyMath.round(totalAveragePenMin,0) > totalAveragePenMin)
            totalAveragePenMin = MyMath.round(totalAveragePenMin,0) - 0.5;
        else
            totalAveragePenMin = MyMath.round(totalAveragePenMin,0) + 0.5;
        if (MyMath.round(selfAveragePenMin,0) > selfAveragePenMin)
            selfAveragePenMin = MyMath.round(selfAveragePenMin,0) - 0.5;
        else
            selfAveragePenMin = MyMath.round(selfAveragePenMin,0) + 0.5;
        if (MyMath.round(opAveragePenMin,0) > opAveragePenMin)
            opAveragePenMin = MyMath.round(opAveragePenMin,0) - 0.5;
        else
            opAveragePenMin = MyMath.round(opAveragePenMin,0) + 0.5;
        double totalSred = 0;
        double totalPlus1 = 0;
        double totalMinus1 = 0;
        double selfSred = 0;
        double selfPlus1 = 0;
        double selfMinus1 = 0;
        double opSred = 0;
        double opPlus1 = 0;
        double opMinus1 = 0;

        ArrayList<Match> list = selector.listOfMatches;

        for (int i=0; i<list.size(); i++){
            if (list.get(i).homePenaltyMinutes + list.get(i).awayPenaltyMinutes > (totalAveragePenMin-1))
                totalMinus1++;
            if (list.get(i).homePenaltyMinutes + list.get(i).awayPenaltyMinutes > totalAveragePenMin)
                totalSred++;
            if (list.get(i).homePenaltyMinutes + list.get(i).awayPenaltyMinutes > (totalAveragePenMin+1))
                totalPlus1++;

            if ((list.get(i).homePenaltyMinutes > (selfAveragePenMin+1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayPenaltyMinutes > (selfAveragePenMin+1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfPlus1++;
            if ((list.get(i).homePenaltyMinutes > (selfAveragePenMin)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayPenaltyMinutes > (selfAveragePenMin)))&&(teamName.equals(list.get(i).awayTeam))))
                selfSred++;
            if ((list.get(i).homePenaltyMinutes > (selfAveragePenMin-1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayPenaltyMinutes > (selfAveragePenMin-1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfMinus1++;

            if ((list.get(i).homePenaltyMinutes > (opAveragePenMin+1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayPenaltyMinutes > (opAveragePenMin+1)))&&(teamName.equals(list.get(i).homeTeam))))
                opPlus1++;
            if ((list.get(i).homePenaltyMinutes > (opAveragePenMin)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayPenaltyMinutes > (opAveragePenMin)))&&(teamName.equals(list.get(i).homeTeam))))
                opSred++;
            if ((list.get(i).homePenaltyMinutes > (opAveragePenMin-1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayPenaltyMinutes > (opAveragePenMin-1)))&&(teamName.equals(list.get(i).homeTeam))))
                opMinus1++;
            if ((list.get(i).homePenaltyMinutes > list.get(i).awayPenaltyMinutes&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayPenaltyMinutes > list.get(i).homePenaltyMinutes))&&(teamName.equals(list.get(i).awayTeam))))
                win++;
            if ((list.get(i).homePenaltyMinutes >= list.get(i).awayPenaltyMinutes&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayPenaltyMinutes >= list.get(i).homePenaltyMinutes))&&(teamName.equals(list.get(i).awayTeam))))
                winX++;
        }

        String t85 = "ТБ(" + String.valueOf(totalAveragePenMin-1) + ")";
        String t85s = String.valueOf((int) (totalMinus1)) + "/" + list.size() + " (" + MyMath.round(totalMinus1/list.size()*100, 1) + ")";
        if (totalMinus1/list.size() < 0.5){
            t85 = "ТM(" + String.valueOf(totalAveragePenMin-1) + ")";
            t85s = String.valueOf((int) (list.size() - totalMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalMinus1)/list.size()*100, 1) + ")";
        }
        String t95 = "ТБ(" + String.valueOf(totalAveragePenMin) + ")";
        String t95s = String.valueOf((int) (totalSred)) + "/" + list.size() + " (" + MyMath.round(totalSred/list.size()*100, 1) + ")";
        if (totalSred/list.size() < 0.5){
            t95 = "ТM(" + String.valueOf(totalAveragePenMin) + ")";
            t95s = String.valueOf((int) (list.size() - totalSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalSred)/list.size()*100, 1) + ")";
        }

        String t105 = "ТБ(" + String.valueOf(totalAveragePenMin+1) + ")";
        String t105s = String.valueOf((int) (totalPlus1)) + "/" + list.size() + " (" + MyMath.round(totalPlus1/list.size()*100, 1) + ")";
        if (totalPlus1/list.size() < 0.5){
            t105 = "ТM(" + String.valueOf(totalAveragePenMin+1) + ")";
            t105s = String.valueOf((int) (list.size() - totalPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalPlus1)/list.size()*100, 1) + ")";
        }

        double total = selfAveragePenMin + 1;
        String itbPlus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBPlus1 = String.valueOf((int) (selfPlus1)) + "/" + list.size() + " (" + MyMath.round(selfPlus1/list.size()*100, 1) + ")";
        if (selfPlus1/list.size() < 0.5){
            itbPlus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBPlus1 = String.valueOf((int) (list.size() - selfPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfPlus1)/list.size()*100, 1) + ")";
        }

        total = selfAveragePenMin;
        String itbSred = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBSred = String.valueOf((int) (selfSred)) + "/" + list.size() + " (" + MyMath.round(selfSred/list.size()*100, 1) + ")";
        if (selfSred/list.size() < 0.5){
            itbSred = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBSred = String.valueOf((int) (list.size() - selfSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfSred)/list.size()*100, 1) + ")";
        }

        total = selfAveragePenMin - 1;
        String itbMinus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBMinus1 = String.valueOf((int) (selfMinus1)) + "/" + list.size() + " (" + MyMath.round(selfMinus1/list.size()*100, 1) + ")";
        if (selfMinus1/list.size() < 0.5){
            itbMinus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBMinus1 = String.valueOf((int) (list.size() - selfMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfMinus1)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin + 1;
        String optbPlus1 = "Opp: ТБ(" + total + ")";
        String opTBPlus1 = String.valueOf((int) (opPlus1)) + "/" + list.size() + " (" + MyMath.round(opPlus1/list.size()*100, 1) + ")";
        if (opPlus1/list.size() < 0.5){
            optbPlus1 = "Opp: ТM(" + total + ")";
            opTBPlus1 = String.valueOf((int) (list.size() - opPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opPlus1)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin;
        String optbSred = "Opp: ТБ(" + total + ")";
        String opTBSred = String.valueOf((int) (opSred)) + "/" + list.size() + " (" + MyMath.round(opSred/list.size()*100, 1) + ")";
        if (opSred/list.size() < 0.5){
            optbSred = "Opp: ТM(" + total + ")";
            opTBSred = String.valueOf((int) (list.size() - opSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - opSred)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin - 1;
        String optbMinus1 = "Opp: ТБ(" + total + ")";
        String opTBMinus1 = String.valueOf((int) (opMinus1)) + "/" + list.size() + " (" + MyMath.round(opMinus1/list.size()*100, 1) + ")";
        if (opMinus1/list.size() < 0.5){
            optbMinus1 = "Opp: ТM(" + total + ")";
            opTBMinus1 = String.valueOf((int) (list.size() - opMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opMinus1)/list.size()*100, 1) + ")";
        }

        String winS = Team.getShortName(teamName) + ": Ф(-0.5)";
        String winSS = String.valueOf((int) win) + "/" + list.size() + " (" + MyMath.round(win/list.size()*100, 1) + ")";

        String winXS = Team.getShortName(teamName) + ": Ф(+0.5)";
        String winXSS = String.valueOf((int) winX) + "/" + list.size() + " (" + MyMath.round(winX/list.size()*100, 1) + ")";

        String[] colHeads = {"Ставка", "Заход и %"};
        Object[][] data = {
                {t85 , t85s},
                {t95 , t95s},
                {t105 , t105s},
                {itbMinus1 , selfTBMinus1},
                {itbSred , selfTBSred},
                {itbPlus1 , selfTBPlus1},
                {optbMinus1 , opTBMinus1},
                {optbSred , opTBSred},
                {optbPlus1 , opTBPlus1},
                {winS , winSS},
                {winXS , winXSS},
        };

        Font font = new Font("Arial", Font.BOLD, 12);
        JTable tableUSV = new JTable(data, colHeads);
        tableUSV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUSV.setEnabled(false);
        tableUSV.getTableHeader().setFont(font);
        tableUSV.setFont(font);
        tableUSV.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableUSV.setRowHeight(25);
        tableUSV.getColumnModel().getColumn(1).setPreferredWidth(92);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableUSV.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableUSV.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableUSV.setBackground(new Color(238, 238, 238));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
        tablePanel.add(tableUSV, BorderLayout.CENTER);
        tablePanel.add(tableUSV.getTableHeader(), BorderLayout.NORTH);

        return tablePanel;
    }

    public static JPanel getTablePenMinutes1per(String teamName, /*ArrayList<Match> list*/ Selector selector){
        double win = 0;
        double winX = 0;

        double parameter = Double.parseDouble(selector.pList.get(25).get(1)) / (double) selector.listOfMatches.size();
        double parameterOpponent = Double.parseDouble(selector.pList.get(25).get(2)) / (double) selector.listOfMatches.size();

        double totalAveragePenMin = (parameter + parameterOpponent);
        double selfAveragePenMin = parameter;
        double opAveragePenMin = parameterOpponent;

        if (MyMath.round(totalAveragePenMin,0) > totalAveragePenMin)
            totalAveragePenMin = MyMath.round(totalAveragePenMin,0) - 0.5;
        else
            totalAveragePenMin = MyMath.round(totalAveragePenMin,0) + 0.5;
        if (MyMath.round(selfAveragePenMin,0) > selfAveragePenMin)
            selfAveragePenMin = MyMath.round(selfAveragePenMin,0) - 0.5;
        else
            selfAveragePenMin = MyMath.round(selfAveragePenMin,0) + 0.5;
        if (MyMath.round(opAveragePenMin,0) > opAveragePenMin)
            opAveragePenMin = MyMath.round(opAveragePenMin,0) - 0.5;
        else
            opAveragePenMin = MyMath.round(opAveragePenMin,0) + 0.5;
        double totalSred = 0;
        double totalPlus1 = 0;
        double totalMinus1 = 0;
        double selfSred = 0;
        double selfPlus1 = 0;
        double selfMinus1 = 0;
        double opSred = 0;
        double opPlus1 = 0;
        double opMinus1 = 0;

        ArrayList<Match> list = selector.listOfMatches;

        for (int i=0; i<list.size(); i++){
            if (list.get(i).homePenaltyMinutes1stPeriod + list.get(i).awayPenaltyMinutes1stPeriod > (totalAveragePenMin-1))
                totalMinus1++;
            if (list.get(i).homePenaltyMinutes1stPeriod + list.get(i).awayPenaltyMinutes1stPeriod > totalAveragePenMin)
                totalSred++;
            if (list.get(i).homePenaltyMinutes1stPeriod + list.get(i).awayPenaltyMinutes1stPeriod > (totalAveragePenMin+1))
                totalPlus1++;

            if ((list.get(i).homePenaltyMinutes1stPeriod > (selfAveragePenMin+1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayPenaltyMinutes1stPeriod > (selfAveragePenMin+1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfPlus1++;
            if ((list.get(i).homePenaltyMinutes1stPeriod > (selfAveragePenMin)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayPenaltyMinutes1stPeriod > (selfAveragePenMin)))&&(teamName.equals(list.get(i).awayTeam))))
                selfSred++;
            if ((list.get(i).homePenaltyMinutes1stPeriod > (selfAveragePenMin-1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayPenaltyMinutes1stPeriod > (selfAveragePenMin-1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfMinus1++;

            if ((list.get(i).homePenaltyMinutes1stPeriod > (opAveragePenMin+1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayPenaltyMinutes1stPeriod > (opAveragePenMin+1)))&&(teamName.equals(list.get(i).homeTeam))))
                opPlus1++;
            if ((list.get(i).homePenaltyMinutes1stPeriod > (opAveragePenMin)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayPenaltyMinutes1stPeriod > (opAveragePenMin)))&&(teamName.equals(list.get(i).homeTeam))))
                opSred++;
            if ((list.get(i).homePenaltyMinutes1stPeriod > (opAveragePenMin-1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayPenaltyMinutes1stPeriod > (opAveragePenMin-1)))&&(teamName.equals(list.get(i).homeTeam))))
                opMinus1++;
            if ((list.get(i).homePenaltyMinutes1stPeriod > list.get(i).awayPenaltyMinutes1stPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayPenaltyMinutes1stPeriod > list.get(i).homePenaltyMinutes1stPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                win++;
            if ((list.get(i).homePenaltyMinutes1stPeriod >= list.get(i).awayPenaltyMinutes1stPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayPenaltyMinutes1stPeriod >= list.get(i).homePenaltyMinutes1stPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                winX++;
        }

        String t85 = "ТБ(" + String.valueOf(totalAveragePenMin-1) + ")";
        String t85s = String.valueOf((int) (totalMinus1)) + "/" + list.size() + " (" + MyMath.round(totalMinus1/list.size()*100, 1) + ")";
        if (totalMinus1/list.size() < 0.5){
            t85 = "ТM(" + String.valueOf(totalAveragePenMin-1) + ")";
            t85s = String.valueOf((int) (list.size() - totalMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalMinus1)/list.size()*100, 1) + ")";
        }
        String t95 = "ТБ(" + String.valueOf(totalAveragePenMin) + ")";
        String t95s = String.valueOf((int) (totalSred)) + "/" + list.size() + " (" + MyMath.round(totalSred/list.size()*100, 1) + ")";
        if (totalSred/list.size() < 0.5){
            t95 = "ТM(" + String.valueOf(totalAveragePenMin) + ")";
            t95s = String.valueOf((int) (list.size() - totalSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalSred)/list.size()*100, 1) + ")";
        }

        String t105 = "ТБ(" + String.valueOf(totalAveragePenMin+1) + ")";
        String t105s = String.valueOf((int) (totalPlus1)) + "/" + list.size() + " (" + MyMath.round(totalPlus1/list.size()*100, 1) + ")";
        if (totalPlus1/list.size() < 0.5){
            t105 = "ТM(" + String.valueOf(totalAveragePenMin+1) + ")";
            t105s = String.valueOf((int) (list.size() - totalPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalPlus1)/list.size()*100, 1) + ")";
        }

        double total = selfAveragePenMin + 1;
        String itbPlus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBPlus1 = String.valueOf((int) (selfPlus1)) + "/" + list.size() + " (" + MyMath.round(selfPlus1/list.size()*100, 1) + ")";
        if (selfPlus1/list.size() < 0.5){
            itbPlus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBPlus1 = String.valueOf((int) (list.size() - selfPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfPlus1)/list.size()*100, 1) + ")";
        }

        total = selfAveragePenMin;
        String itbSred = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBSred = String.valueOf((int) (selfSred)) + "/" + list.size() + " (" + MyMath.round(selfSred/list.size()*100, 1) + ")";
        if (selfSred/list.size() < 0.5){
            itbSred = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBSred = String.valueOf((int) (list.size() - selfSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfSred)/list.size()*100, 1) + ")";
        }

        total = selfAveragePenMin - 1;
        String itbMinus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBMinus1 = String.valueOf((int) (selfMinus1)) + "/" + list.size() + " (" + MyMath.round(selfMinus1/list.size()*100, 1) + ")";
        if (selfMinus1/list.size() < 0.5){
            itbMinus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBMinus1 = String.valueOf((int) (list.size() - selfMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfMinus1)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin + 1;
        String optbPlus1 = "Opp: ТБ(" + total + ")";
        String opTBPlus1 = String.valueOf((int) (opPlus1)) + "/" + list.size() + " (" + MyMath.round(opPlus1/list.size()*100, 1) + ")";
        if (opPlus1/list.size() < 0.5){
            optbPlus1 = "Opp: ТM(" + total + ")";
            opTBPlus1 = String.valueOf((int) (list.size() - opPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opPlus1)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin;
        String optbSred = "Opp: ТБ(" + total + ")";
        String opTBSred = String.valueOf((int) (opSred)) + "/" + list.size() + " (" + MyMath.round(opSred/list.size()*100, 1) + ")";
        if (opSred/list.size() < 0.5){
            optbSred = "Opp: ТM(" + total + ")";
            opTBSred = String.valueOf((int) (list.size() - opSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - opSred)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin - 1;
        String optbMinus1 = "Opp: ТБ(" + total + ")";
        String opTBMinus1 = String.valueOf((int) (opMinus1)) + "/" + list.size() + " (" + MyMath.round(opMinus1/list.size()*100, 1) + ")";
        if (opMinus1/list.size() < 0.5){
            optbMinus1 = "Opp: ТM(" + total + ")";
            opTBMinus1 = String.valueOf((int) (list.size() - opMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opMinus1)/list.size()*100, 1) + ")";
        }

        String winS = Team.getShortName(teamName) + ": Ф(-0.5)";
        String winSS = String.valueOf((int) win) + "/" + list.size() + " (" + MyMath.round(win/list.size()*100, 1) + ")";

        String winXS = Team.getShortName(teamName) + ": Ф(+0.5)";
        String winXSS = String.valueOf((int) winX) + "/" + list.size() + " (" + MyMath.round(winX/list.size()*100, 1) + ")";

        String[] colHeads = {"Ставка", "Заход и %"};
        Object[][] data = {
                {t85 , t85s},
                {t95 , t95s},
                {t105 , t105s},
                {itbMinus1 , selfTBMinus1},
                {itbSred , selfTBSred},
                {itbPlus1 , selfTBPlus1},
                {optbMinus1 , opTBMinus1},
                {optbSred , opTBSred},
                {optbPlus1 , opTBPlus1},
                {winS , winSS},
                {winXS , winXSS},
        };

        Font font = new Font("Arial", Font.BOLD, 12);
        JTable tableUSV = new JTable(data, colHeads);
        tableUSV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUSV.setEnabled(false);
        tableUSV.getTableHeader().setFont(font);
        tableUSV.setFont(font);
        tableUSV.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableUSV.setRowHeight(25);
        tableUSV.getColumnModel().getColumn(1).setPreferredWidth(92);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableUSV.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableUSV.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableUSV.setBackground(new Color(238, 238, 238));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
        tablePanel.add(tableUSV, BorderLayout.CENTER);
        tablePanel.add(tableUSV.getTableHeader(), BorderLayout.NORTH);

        return tablePanel;
    }

    public static JPanel getTablePenMinutes2per(String teamName, /*ArrayList<Match> list*/ Selector selector){
        double win = 0;
        double winX = 0;

        double parameter = Double.parseDouble(selector.pList.get(26).get(1)) / (double) selector.listOfMatches.size();
        double parameterOpponent = Double.parseDouble(selector.pList.get(26).get(2)) / (double) selector.listOfMatches.size();

        double totalAveragePenMin = (parameter + parameterOpponent);
        double selfAveragePenMin = parameter;
        double opAveragePenMin = parameterOpponent;

        if (MyMath.round(totalAveragePenMin,0) > totalAveragePenMin)
            totalAveragePenMin = MyMath.round(totalAveragePenMin,0) - 0.5;
        else
            totalAveragePenMin = MyMath.round(totalAveragePenMin,0) + 0.5;
        if (MyMath.round(selfAveragePenMin,0) > selfAveragePenMin)
            selfAveragePenMin = MyMath.round(selfAveragePenMin,0) - 0.5;
        else
            selfAveragePenMin = MyMath.round(selfAveragePenMin,0) + 0.5;
        if (MyMath.round(opAveragePenMin,0) > opAveragePenMin)
            opAveragePenMin = MyMath.round(opAveragePenMin,0) - 0.5;
        else
            opAveragePenMin = MyMath.round(opAveragePenMin,0) + 0.5;
        double totalSred = 0;
        double totalPlus1 = 0;
        double totalMinus1 = 0;
        double selfSred = 0;
        double selfPlus1 = 0;
        double selfMinus1 = 0;
        double opSred = 0;
        double opPlus1 = 0;
        double opMinus1 = 0;

        ArrayList<Match> list = selector.listOfMatches;

        for (int i=0; i<list.size(); i++){
            if (list.get(i).homePenaltyMinutes2ndPeriod + list.get(i).awayPenaltyMinutes2ndPeriod > (totalAveragePenMin-1))
                totalMinus1++;
            if (list.get(i).homePenaltyMinutes2ndPeriod + list.get(i).awayPenaltyMinutes2ndPeriod > totalAveragePenMin)
                totalSred++;
            if (list.get(i).homePenaltyMinutes2ndPeriod + list.get(i).awayPenaltyMinutes2ndPeriod > (totalAveragePenMin+1))
                totalPlus1++;

            if ((list.get(i).homePenaltyMinutes2ndPeriod > (selfAveragePenMin+1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayPenaltyMinutes2ndPeriod > (selfAveragePenMin+1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfPlus1++;
            if ((list.get(i).homePenaltyMinutes2ndPeriod > (selfAveragePenMin)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayPenaltyMinutes2ndPeriod > (selfAveragePenMin)))&&(teamName.equals(list.get(i).awayTeam))))
                selfSred++;
            if ((list.get(i).homePenaltyMinutes2ndPeriod > (selfAveragePenMin-1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayPenaltyMinutes2ndPeriod > (selfAveragePenMin-1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfMinus1++;

            if ((list.get(i).homePenaltyMinutes2ndPeriod > (opAveragePenMin+1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayPenaltyMinutes2ndPeriod > (opAveragePenMin+1)))&&(teamName.equals(list.get(i).homeTeam))))
                opPlus1++;
            if ((list.get(i).homePenaltyMinutes2ndPeriod > (opAveragePenMin)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayPenaltyMinutes2ndPeriod > (opAveragePenMin)))&&(teamName.equals(list.get(i).homeTeam))))
                opSred++;
            if ((list.get(i).homePenaltyMinutes2ndPeriod > (opAveragePenMin-1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayPenaltyMinutes2ndPeriod > (opAveragePenMin-1)))&&(teamName.equals(list.get(i).homeTeam))))
                opMinus1++;
            if ((list.get(i).homePenaltyMinutes2ndPeriod > list.get(i).awayPenaltyMinutes2ndPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayPenaltyMinutes2ndPeriod > list.get(i).homePenaltyMinutes2ndPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                win++;
            if ((list.get(i).homePenaltyMinutes2ndPeriod >= list.get(i).awayPenaltyMinutes2ndPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayPenaltyMinutes2ndPeriod >= list.get(i).homePenaltyMinutes2ndPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                winX++;
        }

        String t85 = "ТБ(" + String.valueOf(totalAveragePenMin-1) + ")";
        String t85s = String.valueOf((int) (totalMinus1)) + "/" + list.size() + " (" + MyMath.round(totalMinus1/list.size()*100, 1) + ")";
        if (totalMinus1/list.size() < 0.5){
            t85 = "ТM(" + String.valueOf(totalAveragePenMin-1) + ")";
            t85s = String.valueOf((int) (list.size() - totalMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalMinus1)/list.size()*100, 1) + ")";
        }
        String t95 = "ТБ(" + String.valueOf(totalAveragePenMin) + ")";
        String t95s = String.valueOf((int) (totalSred)) + "/" + list.size() + " (" + MyMath.round(totalSred/list.size()*100, 1) + ")";
        if (totalSred/list.size() < 0.5){
            t95 = "ТM(" + String.valueOf(totalAveragePenMin) + ")";
            t95s = String.valueOf((int) (list.size() - totalSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalSred)/list.size()*100, 1) + ")";
        }

        String t105 = "ТБ(" + String.valueOf(totalAveragePenMin+1) + ")";
        String t105s = String.valueOf((int) (totalPlus1)) + "/" + list.size() + " (" + MyMath.round(totalPlus1/list.size()*100, 1) + ")";
        if (totalPlus1/list.size() < 0.5){
            t105 = "ТM(" + String.valueOf(totalAveragePenMin+1) + ")";
            t105s = String.valueOf((int) (list.size() - totalPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalPlus1)/list.size()*100, 1) + ")";
        }

        double total = selfAveragePenMin + 1;
        String itbPlus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBPlus1 = String.valueOf((int) (selfPlus1)) + "/" + list.size() + " (" + MyMath.round(selfPlus1/list.size()*100, 1) + ")";
        if (selfPlus1/list.size() < 0.5){
            itbPlus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBPlus1 = String.valueOf((int) (list.size() - selfPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfPlus1)/list.size()*100, 1) + ")";
        }

        total = selfAveragePenMin;
        String itbSred = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBSred = String.valueOf((int) (selfSred)) + "/" + list.size() + " (" + MyMath.round(selfSred/list.size()*100, 1) + ")";
        if (selfSred/list.size() < 0.5){
            itbSred = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBSred = String.valueOf((int) (list.size() - selfSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfSred)/list.size()*100, 1) + ")";
        }

        total = selfAveragePenMin - 1;
        String itbMinus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBMinus1 = String.valueOf((int) (selfMinus1)) + "/" + list.size() + " (" + MyMath.round(selfMinus1/list.size()*100, 1) + ")";
        if (selfMinus1/list.size() < 0.5){
            itbMinus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBMinus1 = String.valueOf((int) (list.size() - selfMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfMinus1)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin + 1;
        String optbPlus1 = "Opp: ТБ(" + total + ")";
        String opTBPlus1 = String.valueOf((int) (opPlus1)) + "/" + list.size() + " (" + MyMath.round(opPlus1/list.size()*100, 1) + ")";
        if (opPlus1/list.size() < 0.5){
            optbPlus1 = "Opp: ТM(" + total + ")";
            opTBPlus1 = String.valueOf((int) (list.size() - opPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opPlus1)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin;
        String optbSred = "Opp: ТБ(" + total + ")";
        String opTBSred = String.valueOf((int) (opSred)) + "/" + list.size() + " (" + MyMath.round(opSred/list.size()*100, 1) + ")";
        if (opSred/list.size() < 0.5){
            optbSred = "Opp: ТM(" + total + ")";
            opTBSred = String.valueOf((int) (list.size() - opSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - opSred)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin - 1;
        String optbMinus1 = "Opp: ТБ(" + total + ")";
        String opTBMinus1 = String.valueOf((int) (opMinus1)) + "/" + list.size() + " (" + MyMath.round(opMinus1/list.size()*100, 1) + ")";
        if (opMinus1/list.size() < 0.5){
            optbMinus1 = "Opp: ТM(" + total + ")";
            opTBMinus1 = String.valueOf((int) (list.size() - opMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opMinus1)/list.size()*100, 1) + ")";
        }

        String winS = Team.getShortName(teamName) + ": Ф(-0.5)";
        String winSS = String.valueOf((int) win) + "/" + list.size() + " (" + MyMath.round(win/list.size()*100, 1) + ")";

        String winXS = Team.getShortName(teamName) + ": Ф(+0.5)";
        String winXSS = String.valueOf((int) winX) + "/" + list.size() + " (" + MyMath.round(winX/list.size()*100, 1) + ")";

        String[] colHeads = {"Ставка", "Заход и %"};
        Object[][] data = {
                {t85 , t85s},
                {t95 , t95s},
                {t105 , t105s},
                {itbMinus1 , selfTBMinus1},
                {itbSred , selfTBSred},
                {itbPlus1 , selfTBPlus1},
                {optbMinus1 , opTBMinus1},
                {optbSred , opTBSred},
                {optbPlus1 , opTBPlus1},
                {winS , winSS},
                {winXS , winXSS},
        };

        Font font = new Font("Arial", Font.BOLD, 12);
        JTable tableUSV = new JTable(data, colHeads);
        tableUSV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUSV.setEnabled(false);
        tableUSV.getTableHeader().setFont(font);
        tableUSV.setFont(font);
        tableUSV.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableUSV.setRowHeight(25);
        tableUSV.getColumnModel().getColumn(1).setPreferredWidth(92);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableUSV.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableUSV.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableUSV.setBackground(new Color(238, 238, 238));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
        tablePanel.add(tableUSV, BorderLayout.CENTER);
        tablePanel.add(tableUSV.getTableHeader(), BorderLayout.NORTH);

        return tablePanel;
    }

    public static JPanel getTablePenMinutes3per(String teamName, /*ArrayList<Match> list*/ Selector selector){
        double win = 0;
        double winX = 0;

        double parameter = Double.parseDouble(selector.pList.get(27).get(1)) / (double) selector.listOfMatches.size();
        double parameterOpponent = Double.parseDouble(selector.pList.get(27).get(2)) / (double) selector.listOfMatches.size();

        double totalAveragePenMin = (parameter + parameterOpponent);
        double selfAveragePenMin = parameter;
        double opAveragePenMin = parameterOpponent;

        if (MyMath.round(totalAveragePenMin,0) > totalAveragePenMin)
            totalAveragePenMin = MyMath.round(totalAveragePenMin,0) - 0.5;
        else
            totalAveragePenMin = MyMath.round(totalAveragePenMin,0) + 0.5;
        if (MyMath.round(selfAveragePenMin,0) > selfAveragePenMin)
            selfAveragePenMin = MyMath.round(selfAveragePenMin,0) - 0.5;
        else
            selfAveragePenMin = MyMath.round(selfAveragePenMin,0) + 0.5;
        if (MyMath.round(opAveragePenMin,0) > opAveragePenMin)
            opAveragePenMin = MyMath.round(opAveragePenMin,0) - 0.5;
        else
            opAveragePenMin = MyMath.round(opAveragePenMin,0) + 0.5;
        double totalSred = 0;
        double totalPlus1 = 0;
        double totalMinus1 = 0;
        double selfSred = 0;
        double selfPlus1 = 0;
        double selfMinus1 = 0;
        double opSred = 0;
        double opPlus1 = 0;
        double opMinus1 = 0;

        ArrayList<Match> list = selector.listOfMatches;

        for (int i=0; i<list.size(); i++){
            if (list.get(i).homePenaltyMinutes3rdPeriod + list.get(i).awayPenaltyMinutes3rdPeriod > (totalAveragePenMin-1))
                totalMinus1++;
            if (list.get(i).homePenaltyMinutes3rdPeriod + list.get(i).awayPenaltyMinutes3rdPeriod > totalAveragePenMin)
                totalSred++;
            if (list.get(i).homePenaltyMinutes3rdPeriod + list.get(i).awayPenaltyMinutes3rdPeriod > (totalAveragePenMin+1))
                totalPlus1++;

            if ((list.get(i).homePenaltyMinutes3rdPeriod > (selfAveragePenMin+1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayPenaltyMinutes3rdPeriod > (selfAveragePenMin+1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfPlus1++;
            if ((list.get(i).homePenaltyMinutes3rdPeriod > (selfAveragePenMin)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayPenaltyMinutes3rdPeriod > (selfAveragePenMin)))&&(teamName.equals(list.get(i).awayTeam))))
                selfSred++;
            if ((list.get(i).homePenaltyMinutes3rdPeriod > (selfAveragePenMin-1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayPenaltyMinutes3rdPeriod > (selfAveragePenMin-1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfMinus1++;

            if ((list.get(i).homePenaltyMinutes3rdPeriod > (opAveragePenMin+1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayPenaltyMinutes3rdPeriod > (opAveragePenMin+1)))&&(teamName.equals(list.get(i).homeTeam))))
                opPlus1++;
            if ((list.get(i).homePenaltyMinutes3rdPeriod > (opAveragePenMin)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayPenaltyMinutes3rdPeriod > (opAveragePenMin)))&&(teamName.equals(list.get(i).homeTeam))))
                opSred++;
            if ((list.get(i).homePenaltyMinutes3rdPeriod > (opAveragePenMin-1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).awayPenaltyMinutes3rdPeriod > (opAveragePenMin-1)))&&(teamName.equals(list.get(i).homeTeam))))
                opMinus1++;
            if ((list.get(i).homePenaltyMinutes3rdPeriod > list.get(i).awayPenaltyMinutes3rdPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayPenaltyMinutes3rdPeriod > list.get(i).homePenaltyMinutes3rdPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                win++;
            if ((list.get(i).homePenaltyMinutes3rdPeriod >= list.get(i).awayPenaltyMinutes3rdPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).awayPenaltyMinutes3rdPeriod >= list.get(i).homePenaltyMinutes3rdPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                winX++;
        }

        String t85 = "ТБ(" + String.valueOf(totalAveragePenMin-1) + ")";
        String t85s = String.valueOf((int) (totalMinus1)) + "/" + list.size() + " (" + MyMath.round(totalMinus1/list.size()*100, 1) + ")";
        if (totalMinus1/list.size() < 0.5){
            t85 = "ТM(" + String.valueOf(totalAveragePenMin-1) + ")";
            t85s = String.valueOf((int) (list.size() - totalMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalMinus1)/list.size()*100, 1) + ")";
        }
        String t95 = "ТБ(" + String.valueOf(totalAveragePenMin) + ")";
        String t95s = String.valueOf((int) (totalSred)) + "/" + list.size() + " (" + MyMath.round(totalSred/list.size()*100, 1) + ")";
        if (totalSred/list.size() < 0.5){
            t95 = "ТM(" + String.valueOf(totalAveragePenMin) + ")";
            t95s = String.valueOf((int) (list.size() - totalSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalSred)/list.size()*100, 1) + ")";
        }

        String t105 = "ТБ(" + String.valueOf(totalAveragePenMin+1) + ")";
        String t105s = String.valueOf((int) (totalPlus1)) + "/" + list.size() + " (" + MyMath.round(totalPlus1/list.size()*100, 1) + ")";
        if (totalPlus1/list.size() < 0.5){
            t105 = "ТM(" + String.valueOf(totalAveragePenMin+1) + ")";
            t105s = String.valueOf((int) (list.size() - totalPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalPlus1)/list.size()*100, 1) + ")";
        }

        double total = selfAveragePenMin + 1;
        String itbPlus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBPlus1 = String.valueOf((int) (selfPlus1)) + "/" + list.size() + " (" + MyMath.round(selfPlus1/list.size()*100, 1) + ")";
        if (selfPlus1/list.size() < 0.5){
            itbPlus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBPlus1 = String.valueOf((int) (list.size() - selfPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfPlus1)/list.size()*100, 1) + ")";
        }

        total = selfAveragePenMin;
        String itbSred = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBSred = String.valueOf((int) (selfSred)) + "/" + list.size() + " (" + MyMath.round(selfSred/list.size()*100, 1) + ")";
        if (selfSred/list.size() < 0.5){
            itbSred = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBSred = String.valueOf((int) (list.size() - selfSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfSred)/list.size()*100, 1) + ")";
        }

        total = selfAveragePenMin - 1;
        String itbMinus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBMinus1 = String.valueOf((int) (selfMinus1)) + "/" + list.size() + " (" + MyMath.round(selfMinus1/list.size()*100, 1) + ")";
        if (selfMinus1/list.size() < 0.5){
            itbMinus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBMinus1 = String.valueOf((int) (list.size() - selfMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfMinus1)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin + 1;
        String optbPlus1 = "Opp: ТБ(" + total + ")";
        String opTBPlus1 = String.valueOf((int) (opPlus1)) + "/" + list.size() + " (" + MyMath.round(opPlus1/list.size()*100, 1) + ")";
        if (opPlus1/list.size() < 0.5){
            optbPlus1 = "Opp: ТM(" + total + ")";
            opTBPlus1 = String.valueOf((int) (list.size() - opPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opPlus1)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin;
        String optbSred = "Opp: ТБ(" + total + ")";
        String opTBSred = String.valueOf((int) (opSred)) + "/" + list.size() + " (" + MyMath.round(opSred/list.size()*100, 1) + ")";
        if (opSred/list.size() < 0.5){
            optbSred = "Opp: ТM(" + total + ")";
            opTBSred = String.valueOf((int) (list.size() - opSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - opSred)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin - 1;
        String optbMinus1 = "Opp: ТБ(" + total + ")";
        String opTBMinus1 = String.valueOf((int) (opMinus1)) + "/" + list.size() + " (" + MyMath.round(opMinus1/list.size()*100, 1) + ")";
        if (opMinus1/list.size() < 0.5){
            optbMinus1 = "Opp: ТM(" + total + ")";
            opTBMinus1 = String.valueOf((int) (list.size() - opMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opMinus1)/list.size()*100, 1) + ")";
        }

        String winS = Team.getShortName(teamName) + ": Ф(-0.5)";
        String winSS = String.valueOf((int) win) + "/" + list.size() + " (" + MyMath.round(win/list.size()*100, 1) + ")";

        String winXS = Team.getShortName(teamName) + ": Ф(+0.5)";
        String winXSS = String.valueOf((int) winX) + "/" + list.size() + " (" + MyMath.round(winX/list.size()*100, 1) + ")";

        String[] colHeads = {"Ставка", "Заход и %"};
        Object[][] data = {
                {t85 , t85s},
                {t95 , t95s},
                {t105 , t105s},
                {itbMinus1 , selfTBMinus1},
                {itbSred , selfTBSred},
                {itbPlus1 , selfTBPlus1},
                {optbMinus1 , opTBMinus1},
                {optbSred , opTBSred},
                {optbPlus1 , opTBPlus1},
                {winS , winSS},
                {winXS , winXSS},
        };

        Font font = new Font("Arial", Font.BOLD, 12);
        JTable tableUSV = new JTable(data, colHeads);
        tableUSV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUSV.setEnabled(false);
        tableUSV.getTableHeader().setFont(font);
        tableUSV.setFont(font);
        tableUSV.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableUSV.setRowHeight(25);
        tableUSV.getColumnModel().getColumn(1).setPreferredWidth(92);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableUSV.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableUSV.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableUSV.setBackground(new Color(238, 238, 238));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
        tablePanel.add(tableUSV, BorderLayout.CENTER);
        tablePanel.add(tableUSV.getTableHeader(), BorderLayout.NORTH);

        return tablePanel;
    }

    public static JPanel getTablePen2minutes(String teamName,/*ArrayList<Match> list*/ Selector selector){
        double win = 0;
        double winX = 0;

        double parameter = Double.parseDouble(selector.pList.get(15).get(1)) / (double) selector.listOfMatches.size();
        double parameterOpponent = Double.parseDouble(selector.pList.get(15).get(2)) / (double) selector.listOfMatches.size();

        double totalAveragePenMin = (parameter + parameterOpponent);
        double selfAveragePenMin = parameter;
        double opAveragePenMin = parameterOpponent;

        if (MyMath.round(totalAveragePenMin,0) > totalAveragePenMin)
            totalAveragePenMin = MyMath.round(totalAveragePenMin,0) - 0.5;
        else
            totalAveragePenMin = MyMath.round(totalAveragePenMin,0) + 0.5;
        if (MyMath.round(selfAveragePenMin,0) > selfAveragePenMin)
            selfAveragePenMin = MyMath.round(selfAveragePenMin,0) - 0.5;
        else
            selfAveragePenMin = MyMath.round(selfAveragePenMin,0) + 0.5;
        if (MyMath.round(opAveragePenMin,0) > opAveragePenMin)
            opAveragePenMin = MyMath.round(opAveragePenMin,0) - 0.5;
        else
            opAveragePenMin = MyMath.round(opAveragePenMin,0) + 0.5;
        double totalSred = 0;
        double totalPlus1 = 0;
        double totalMinus1 = 0;
        double selfSred = 0;
        double selfPlus1 = 0;
        double selfMinus1 = 0;
        double opSred = 0;
        double opPlus1 = 0;
        double opMinus1 = 0;

        ArrayList<Match> list = selector.listOfMatches;

        for (int i=0; i<list.size(); i++){
            if (list.get(i).home2MinPenalties + list.get(i).away2MinPenalties > (totalAveragePenMin-1))
                totalMinus1++;
            if (list.get(i).home2MinPenalties + list.get(i).away2MinPenalties > totalAveragePenMin)
                totalSred++;
            if (list.get(i).home2MinPenalties + list.get(i).away2MinPenalties > (totalAveragePenMin+1))
                totalPlus1++;

            if ((list.get(i).home2MinPenalties > (selfAveragePenMin+1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).away2MinPenalties > (selfAveragePenMin+1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfPlus1++;
            if ((list.get(i).home2MinPenalties > (selfAveragePenMin)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).away2MinPenalties > (selfAveragePenMin)))&&(teamName.equals(list.get(i).awayTeam))))
                selfSred++;
            if ((list.get(i).home2MinPenalties > (selfAveragePenMin-1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).away2MinPenalties > (selfAveragePenMin-1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfMinus1++;

            if ((list.get(i).home2MinPenalties > (opAveragePenMin+1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).away2MinPenalties > (opAveragePenMin+1)))&&(teamName.equals(list.get(i).homeTeam))))
                opPlus1++;
            if ((list.get(i).home2MinPenalties > (opAveragePenMin)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).away2MinPenalties > (opAveragePenMin)))&&(teamName.equals(list.get(i).homeTeam))))
                opSred++;
            if ((list.get(i).home2MinPenalties > (opAveragePenMin-1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).away2MinPenalties > (opAveragePenMin-1)))&&(teamName.equals(list.get(i).homeTeam))))
                opMinus1++;
            if ((list.get(i).home2MinPenalties > list.get(i).away2MinPenalties&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).away2MinPenalties > list.get(i).home2MinPenalties))&&(teamName.equals(list.get(i).awayTeam))))
                win++;
            if ((list.get(i).home2MinPenalties >= list.get(i).away2MinPenalties&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).away2MinPenalties >= list.get(i).home2MinPenalties))&&(teamName.equals(list.get(i).awayTeam))))
                winX++;
        }

        String t85 = "ТБ(" + String.valueOf(totalAveragePenMin-1) + ")";
        String t85s = String.valueOf((int) (totalMinus1)) + "/" + list.size() + " (" + MyMath.round(totalMinus1/list.size()*100, 1) + ")";
        if (totalMinus1/list.size() < 0.5){
            t85 = "ТM(" + String.valueOf(totalAveragePenMin-1) + ")";
            t85s = String.valueOf((int) (list.size() - totalMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalMinus1)/list.size()*100, 1) + ")";
        }
        String t95 = "ТБ(" + String.valueOf(totalAveragePenMin) + ")";
        String t95s = String.valueOf((int) (totalSred)) + "/" + list.size() + " (" + MyMath.round(totalSred/list.size()*100, 1) + ")";
        if (totalSred/list.size() < 0.5){
            t95 = "ТM(" + String.valueOf(totalAveragePenMin) + ")";
            t95s = String.valueOf((int) (list.size() - totalSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalSred)/list.size()*100, 1) + ")";
        }

        String t105 = "ТБ(" + String.valueOf(totalAveragePenMin+1) + ")";
        String t105s = String.valueOf((int) (totalPlus1)) + "/" + list.size() + " (" + MyMath.round(totalPlus1/list.size()*100, 1) + ")";
        if (totalPlus1/list.size() < 0.5){
            t105 = "ТM(" + String.valueOf(totalAveragePenMin+1) + ")";
            t105s = String.valueOf((int) (list.size() - totalPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalPlus1)/list.size()*100, 1) + ")";
        }

        double total = selfAveragePenMin + 1;
        String itbPlus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBPlus1 = String.valueOf((int) (selfPlus1)) + "/" + list.size() + " (" + MyMath.round(selfPlus1/list.size()*100, 1) + ")";
        if (selfPlus1/list.size() < 0.5){
            itbPlus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBPlus1 = String.valueOf((int) (list.size() - selfPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfPlus1)/list.size()*100, 1) + ")";
        }

        total = selfAveragePenMin;
        String itbSred = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBSred = String.valueOf((int) (selfSred)) + "/" + list.size() + " (" + MyMath.round(selfSred/list.size()*100, 1) + ")";
        if (selfSred/list.size() < 0.5){
            itbSred = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBSred = String.valueOf((int) (list.size() - selfSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfSred)/list.size()*100, 1) + ")";
        }

        total = selfAveragePenMin - 1;
        String itbMinus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBMinus1 = String.valueOf((int) (selfMinus1)) + "/" + list.size() + " (" + MyMath.round(selfMinus1/list.size()*100, 1) + ")";
        if (selfMinus1/list.size() < 0.5){
            itbMinus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBMinus1 = String.valueOf((int) (list.size() - selfMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfMinus1)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin + 1;
        String optbPlus1 = "Opp: ТБ(" + total + ")";
        String opTBPlus1 = String.valueOf((int) (opPlus1)) + "/" + list.size() + " (" + MyMath.round(opPlus1/list.size()*100, 1) + ")";
        if (opPlus1/list.size() < 0.5){
            optbPlus1 = "Opp: ТM(" + total + ")";
            opTBPlus1 = String.valueOf((int) (list.size() - opPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opPlus1)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin;
        String optbSred = "Opp: ТБ(" + total + ")";
        String opTBSred = String.valueOf((int) (opSred)) + "/" + list.size() + " (" + MyMath.round(opSred/list.size()*100, 1) + ")";
        if (opSred/list.size() < 0.5){
            optbSred = "Opp: ТM(" + total + ")";
            opTBSred = String.valueOf((int) (list.size() - opSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - opSred)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin - 1;
        String optbMinus1 = "Opp: ТБ(" + total + ")";
        String opTBMinus1 = String.valueOf((int) (opMinus1)) + "/" + list.size() + " (" + MyMath.round(opMinus1/list.size()*100, 1) + ")";
        if (opMinus1/list.size() < 0.5){
            optbMinus1 = "Opp: ТM(" + total + ")";
            opTBMinus1 = String.valueOf((int) (list.size() - opMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opMinus1)/list.size()*100, 1) + ")";
        }

        String winS = Team.getShortName(teamName) + ": Ф(-0.5)";
        String winSS = String.valueOf((int) win) + "/" + list.size() + " (" + MyMath.round(win/list.size()*100, 1) + ")";

        String winXS = Team.getShortName(teamName) + ": Ф(+0.5)";
        String winXSS = String.valueOf((int) winX) + "/" + list.size() + " (" + MyMath.round(winX/list.size()*100, 1) + ")";

        String[] colHeads = {"Ставка", "Заход и %"};
        Object[][] data = {
                {t85 , t85s},
                {t95 , t95s},
                {t105 , t105s},
                {itbMinus1 , selfTBMinus1},
                {itbSred , selfTBSred},
                {itbPlus1 , selfTBPlus1},
                {optbMinus1 , opTBMinus1},
                {optbSred , opTBSred},
                {optbPlus1 , opTBPlus1},
                {winS , winSS},
                {winXS , winXSS},
        };

        Font font = new Font("Arial", Font.BOLD, 12);
        JTable tableUSV = new JTable(data, colHeads);
        tableUSV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUSV.setEnabled(false);
        tableUSV.getTableHeader().setFont(font);
        tableUSV.setFont(font);
        tableUSV.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableUSV.setRowHeight(25);
        tableUSV.getColumnModel().getColumn(1).setPreferredWidth(92);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableUSV.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableUSV.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableUSV.setBackground(new Color(238, 238, 238));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
        tablePanel.add(tableUSV, BorderLayout.CENTER);
        tablePanel.add(tableUSV.getTableHeader(), BorderLayout.NORTH);

        return tablePanel;
    }

    public static JPanel getTablePen2minutes1per(String teamName, /*ArrayList<Match> list*/ Selector selector){
        double win = 0;
        double winX = 0;

        double parameter = Double.parseDouble(selector.pList.get(28).get(1)) / (double) selector.listOfMatches.size();
        double parameterOpponent = Double.parseDouble(selector.pList.get(28).get(2)) / (double) selector.listOfMatches.size();

        double totalAveragePenMin = (parameter + parameterOpponent);
        double selfAveragePenMin = parameter;
        double opAveragePenMin = parameterOpponent;

        if (MyMath.round(totalAveragePenMin,0) > totalAveragePenMin)
            totalAveragePenMin = MyMath.round(totalAveragePenMin,0) - 0.5;
        else
            totalAveragePenMin = MyMath.round(totalAveragePenMin,0) + 0.5;
        if (MyMath.round(selfAveragePenMin,0) > selfAveragePenMin)
            selfAveragePenMin = MyMath.round(selfAveragePenMin,0) - 0.5;
        else
            selfAveragePenMin = MyMath.round(selfAveragePenMin,0) + 0.5;
        if (MyMath.round(opAveragePenMin,0) > opAveragePenMin)
            opAveragePenMin = MyMath.round(opAveragePenMin,0) - 0.5;
        else
            opAveragePenMin = MyMath.round(opAveragePenMin,0) + 0.5;
        double totalSred = 0;
        double totalPlus1 = 0;
        double totalMinus1 = 0;
        double selfSred = 0;
        double selfPlus1 = 0;
        double selfMinus1 = 0;
        double opSred = 0;
        double opPlus1 = 0;
        double opMinus1 = 0;

        ArrayList<Match> list = selector.listOfMatches;

        for (int i=0; i<list.size(); i++){
            if (list.get(i).home2MinPenalties1stPeriod + list.get(i).away2MinPenalties1stPeriod > (totalAveragePenMin-1))
                totalMinus1++;
            if (list.get(i).home2MinPenalties1stPeriod + list.get(i).away2MinPenalties1stPeriod > totalAveragePenMin)
                totalSred++;
            if (list.get(i).home2MinPenalties1stPeriod + list.get(i).away2MinPenalties1stPeriod > (totalAveragePenMin+1))
                totalPlus1++;

            if ((list.get(i).home2MinPenalties1stPeriod > (selfAveragePenMin+1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).away2MinPenalties1stPeriod > (selfAveragePenMin+1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfPlus1++;
            if ((list.get(i).home2MinPenalties1stPeriod > (selfAveragePenMin)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).away2MinPenalties1stPeriod > (selfAveragePenMin)))&&(teamName.equals(list.get(i).awayTeam))))
                selfSred++;
            if ((list.get(i).home2MinPenalties1stPeriod > (selfAveragePenMin-1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).away2MinPenalties1stPeriod > (selfAveragePenMin-1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfMinus1++;

            if ((list.get(i).home2MinPenalties1stPeriod > (opAveragePenMin+1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).away2MinPenalties1stPeriod > (opAveragePenMin+1)))&&(teamName.equals(list.get(i).homeTeam))))
                opPlus1++;
            if ((list.get(i).home2MinPenalties1stPeriod > (opAveragePenMin)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).away2MinPenalties1stPeriod > (opAveragePenMin)))&&(teamName.equals(list.get(i).homeTeam))))
                opSred++;
            if ((list.get(i).home2MinPenalties1stPeriod > (opAveragePenMin-1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).away2MinPenalties1stPeriod > (opAveragePenMin-1)))&&(teamName.equals(list.get(i).homeTeam))))
                opMinus1++;
            if ((list.get(i).home2MinPenalties1stPeriod > list.get(i).away2MinPenalties1stPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).away2MinPenalties1stPeriod > list.get(i).home2MinPenalties1stPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                win++;
            if ((list.get(i).home2MinPenalties1stPeriod >= list.get(i).away2MinPenalties1stPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).away2MinPenalties1stPeriod >= list.get(i).home2MinPenalties1stPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                winX++;
        }

        String t85 = "ТБ(" + String.valueOf(totalAveragePenMin-1) + ")";
        String t85s = String.valueOf((int) (totalMinus1)) + "/" + list.size() + " (" + MyMath.round(totalMinus1/list.size()*100, 1) + ")";
        if (totalMinus1/list.size() < 0.5){
            t85 = "ТM(" + String.valueOf(totalAveragePenMin-1) + ")";
            t85s = String.valueOf((int) (list.size() - totalMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalMinus1)/list.size()*100, 1) + ")";
        }
        String t95 = "ТБ(" + String.valueOf(totalAveragePenMin) + ")";
        String t95s = String.valueOf((int) (totalSred)) + "/" + list.size() + " (" + MyMath.round(totalSred/list.size()*100, 1) + ")";
        if (totalSred/list.size() < 0.5){
            t95 = "ТM(" + String.valueOf(totalAveragePenMin) + ")";
            t95s = String.valueOf((int) (list.size() - totalSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalSred)/list.size()*100, 1) + ")";
        }

        String t105 = "ТБ(" + String.valueOf(totalAveragePenMin+1) + ")";
        String t105s = String.valueOf((int) (totalPlus1)) + "/" + list.size() + " (" + MyMath.round(totalPlus1/list.size()*100, 1) + ")";
        if (totalPlus1/list.size() < 0.5){
            t105 = "ТM(" + String.valueOf(totalAveragePenMin+1) + ")";
            t105s = String.valueOf((int) (list.size() - totalPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalPlus1)/list.size()*100, 1) + ")";
        }

        double total = selfAveragePenMin + 1;
        String itbPlus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBPlus1 = String.valueOf((int) (selfPlus1)) + "/" + list.size() + " (" + MyMath.round(selfPlus1/list.size()*100, 1) + ")";
        if (selfPlus1/list.size() < 0.5){
            itbPlus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBPlus1 = String.valueOf((int) (list.size() - selfPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfPlus1)/list.size()*100, 1) + ")";
        }

        total = selfAveragePenMin;
        String itbSred = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBSred = String.valueOf((int) (selfSred)) + "/" + list.size() + " (" + MyMath.round(selfSred/list.size()*100, 1) + ")";
        if (selfSred/list.size() < 0.5){
            itbSred = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBSred = String.valueOf((int) (list.size() - selfSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfSred)/list.size()*100, 1) + ")";
        }

        total = selfAveragePenMin - 1;
        String itbMinus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBMinus1 = String.valueOf((int) (selfMinus1)) + "/" + list.size() + " (" + MyMath.round(selfMinus1/list.size()*100, 1) + ")";
        if (selfMinus1/list.size() < 0.5){
            itbMinus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBMinus1 = String.valueOf((int) (list.size() - selfMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfMinus1)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin + 1;
        String optbPlus1 = "Opp: ТБ(" + total + ")";
        String opTBPlus1 = String.valueOf((int) (opPlus1)) + "/" + list.size() + " (" + MyMath.round(opPlus1/list.size()*100, 1) + ")";
        if (opPlus1/list.size() < 0.5){
            optbPlus1 = "Opp: ТM(" + total + ")";
            opTBPlus1 = String.valueOf((int) (list.size() - opPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opPlus1)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin;
        String optbSred = "Opp: ТБ(" + total + ")";
        String opTBSred = String.valueOf((int) (opSred)) + "/" + list.size() + " (" + MyMath.round(opSred/list.size()*100, 1) + ")";
        if (opSred/list.size() < 0.5){
            optbSred = "Opp: ТM(" + total + ")";
            opTBSred = String.valueOf((int) (list.size() - opSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - opSred)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin - 1;
        String optbMinus1 = "Opp: ТБ(" + total + ")";
        String opTBMinus1 = String.valueOf((int) (opMinus1)) + "/" + list.size() + " (" + MyMath.round(opMinus1/list.size()*100, 1) + ")";
        if (opMinus1/list.size() < 0.5){
            optbMinus1 = "Opp: ТM(" + total + ")";
            opTBMinus1 = String.valueOf((int) (list.size() - opMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opMinus1)/list.size()*100, 1) + ")";
        }

        String winS = Team.getShortName(teamName) + ": Ф(-0.5)";
        String winSS = String.valueOf((int) win) + "/" + list.size() + " (" + MyMath.round(win/list.size()*100, 1) + ")";

        String winXS = Team.getShortName(teamName) + ": Ф(+0.5)";
        String winXSS = String.valueOf((int) winX) + "/" + list.size() + " (" + MyMath.round(winX/list.size()*100, 1) + ")";

        String[] colHeads = {"Ставка", "Заход и %"};
        Object[][] data = {
                {t85 , t85s},
                {t95 , t95s},
                {t105 , t105s},
                {itbMinus1 , selfTBMinus1},
                {itbSred , selfTBSred},
                {itbPlus1 , selfTBPlus1},
                {optbMinus1 , opTBMinus1},
                {optbSred , opTBSred},
                {optbPlus1 , opTBPlus1},
                {winS , winSS},
                {winXS , winXSS},
        };

        Font font = new Font("Arial", Font.BOLD, 12);
        JTable tableUSV = new JTable(data, colHeads);
        tableUSV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUSV.setEnabled(false);
        tableUSV.getTableHeader().setFont(font);
        tableUSV.setFont(font);
        tableUSV.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableUSV.setRowHeight(25);
        tableUSV.getColumnModel().getColumn(1).setPreferredWidth(92);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableUSV.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableUSV.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableUSV.setBackground(new Color(238, 238, 238));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
        tablePanel.add(tableUSV, BorderLayout.CENTER);
        tablePanel.add(tableUSV.getTableHeader(), BorderLayout.NORTH);

        return tablePanel;
    }

    public static JPanel getTablePen2minutes2per(String teamName, /*ArrayList<Match> list*/ Selector selector){
        double win = 0;
        double winX = 0;

        double parameter = Double.parseDouble(selector.pList.get(29).get(1))/ (double) selector.listOfMatches.size();
        double parameterOpponent = Double.parseDouble(selector.pList.get(29).get(2))/ (double) selector.listOfMatches.size();

        double totalAveragePenMin = (parameter + parameterOpponent);
        double selfAveragePenMin = parameter;
        double opAveragePenMin = parameterOpponent;

        if (MyMath.round(totalAveragePenMin,0) > totalAveragePenMin)
            totalAveragePenMin = MyMath.round(totalAveragePenMin,0) - 0.5;
        else
            totalAveragePenMin = MyMath.round(totalAveragePenMin,0) + 0.5;
        if (MyMath.round(selfAveragePenMin,0) > selfAveragePenMin)
            selfAveragePenMin = MyMath.round(selfAveragePenMin,0) - 0.5;
        else
            selfAveragePenMin = MyMath.round(selfAveragePenMin,0) + 0.5;
        if (MyMath.round(opAveragePenMin,0) > opAveragePenMin)
            opAveragePenMin = MyMath.round(opAveragePenMin,0) - 0.5;
        else
            opAveragePenMin = MyMath.round(opAveragePenMin,0) + 0.5;
        double totalSred = 0;
        double totalPlus1 = 0;
        double totalMinus1 = 0;
        double selfSred = 0;
        double selfPlus1 = 0;
        double selfMinus1 = 0;
        double opSred = 0;
        double opPlus1 = 0;
        double opMinus1 = 0;

        ArrayList<Match> list = selector.listOfMatches;

        for (int i=0; i<list.size(); i++){
            if (list.get(i).home2MinPenalties2ndPeriod + list.get(i).away2MinPenalties2ndPeriod > (totalAveragePenMin-1))
                totalMinus1++;
            if (list.get(i).home2MinPenalties2ndPeriod + list.get(i).away2MinPenalties2ndPeriod > totalAveragePenMin)
                totalSred++;
            if (list.get(i).home2MinPenalties2ndPeriod + list.get(i).away2MinPenalties2ndPeriod > (totalAveragePenMin+1))
                totalPlus1++;

            if ((list.get(i).home2MinPenalties2ndPeriod > (selfAveragePenMin+1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).away2MinPenalties2ndPeriod > (selfAveragePenMin+1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfPlus1++;
            if ((list.get(i).home2MinPenalties2ndPeriod > (selfAveragePenMin)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).away2MinPenalties2ndPeriod > (selfAveragePenMin)))&&(teamName.equals(list.get(i).awayTeam))))
                selfSred++;
            if ((list.get(i).home2MinPenalties2ndPeriod > (selfAveragePenMin-1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).away2MinPenalties2ndPeriod > (selfAveragePenMin-1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfMinus1++;

            if ((list.get(i).home2MinPenalties2ndPeriod > (opAveragePenMin+1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).away2MinPenalties2ndPeriod > (opAveragePenMin+1)))&&(teamName.equals(list.get(i).homeTeam))))
                opPlus1++;
            if ((list.get(i).home2MinPenalties2ndPeriod > (opAveragePenMin)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).away2MinPenalties2ndPeriod > (opAveragePenMin)))&&(teamName.equals(list.get(i).homeTeam))))
                opSred++;
            if ((list.get(i).home2MinPenalties2ndPeriod > (opAveragePenMin-1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).away2MinPenalties2ndPeriod > (opAveragePenMin-1)))&&(teamName.equals(list.get(i).homeTeam))))
                opMinus1++;
            if ((list.get(i).home2MinPenalties2ndPeriod > list.get(i).away2MinPenalties2ndPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).away2MinPenalties2ndPeriod > list.get(i).home2MinPenalties2ndPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                win++;
            if ((list.get(i).home2MinPenalties2ndPeriod >= list.get(i).away2MinPenalties2ndPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).away2MinPenalties2ndPeriod >= list.get(i).home2MinPenalties2ndPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                winX++;
        }

        String t85 = "ТБ(" + String.valueOf(totalAveragePenMin-1) + ")";
        String t85s = String.valueOf((int) (totalMinus1)) + "/" + list.size() + " (" + MyMath.round(totalMinus1/list.size()*100, 1) + ")";
        if (totalMinus1/list.size() < 0.5){
            t85 = "ТM(" + String.valueOf(totalAveragePenMin-1) + ")";
            t85s = String.valueOf((int) (list.size() - totalMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalMinus1)/list.size()*100, 1) + ")";
        }
        String t95 = "ТБ(" + String.valueOf(totalAveragePenMin) + ")";
        String t95s = String.valueOf((int) (totalSred)) + "/" + list.size() + " (" + MyMath.round(totalSred/list.size()*100, 1) + ")";
        if (totalSred/list.size() < 0.5){
            t95 = "ТM(" + String.valueOf(totalAveragePenMin) + ")";
            t95s = String.valueOf((int) (list.size() - totalSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalSred)/list.size()*100, 1) + ")";
        }

        String t105 = "ТБ(" + String.valueOf(totalAveragePenMin+1) + ")";
        String t105s = String.valueOf((int) (totalPlus1)) + "/" + list.size() + " (" + MyMath.round(totalPlus1/list.size()*100, 1) + ")";
        if (totalPlus1/list.size() < 0.5){
            t105 = "ТM(" + String.valueOf(totalAveragePenMin+1) + ")";
            t105s = String.valueOf((int) (list.size() - totalPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalPlus1)/list.size()*100, 1) + ")";
        }

        double total = selfAveragePenMin + 1;
        String itbPlus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBPlus1 = String.valueOf((int) (selfPlus1)) + "/" + list.size() + " (" + MyMath.round(selfPlus1/list.size()*100, 1) + ")";
        if (selfPlus1/list.size() < 0.5){
            itbPlus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBPlus1 = String.valueOf((int) (list.size() - selfPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfPlus1)/list.size()*100, 1) + ")";
        }

        total = selfAveragePenMin;
        String itbSred = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBSred = String.valueOf((int) (selfSred)) + "/" + list.size() + " (" + MyMath.round(selfSred/list.size()*100, 1) + ")";
        if (selfSred/list.size() < 0.5){
            itbSred = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBSred = String.valueOf((int) (list.size() - selfSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfSred)/list.size()*100, 1) + ")";
        }

        total = selfAveragePenMin - 1;
        String itbMinus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBMinus1 = String.valueOf((int) (selfMinus1)) + "/" + list.size() + " (" + MyMath.round(selfMinus1/list.size()*100, 1) + ")";
        if (selfMinus1/list.size() < 0.5){
            itbMinus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBMinus1 = String.valueOf((int) (list.size() - selfMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfMinus1)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin + 1;
        String optbPlus1 = "Opp: ТБ(" + total + ")";
        String opTBPlus1 = String.valueOf((int) (opPlus1)) + "/" + list.size() + " (" + MyMath.round(opPlus1/list.size()*100, 1) + ")";
        if (opPlus1/list.size() < 0.5){
            optbPlus1 = "Opp: ТM(" + total + ")";
            opTBPlus1 = String.valueOf((int) (list.size() - opPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opPlus1)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin;
        String optbSred = "Opp: ТБ(" + total + ")";
        String opTBSred = String.valueOf((int) (opSred)) + "/" + list.size() + " (" + MyMath.round(opSred/list.size()*100, 1) + ")";
        if (opSred/list.size() < 0.5){
            optbSred = "Opp: ТM(" + total + ")";
            opTBSred = String.valueOf((int) (list.size() - opSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - opSred)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin - 1;
        String optbMinus1 = "Opp: ТБ(" + total + ")";
        String opTBMinus1 = String.valueOf((int) (opMinus1)) + "/" + list.size() + " (" + MyMath.round(opMinus1/list.size()*100, 1) + ")";
        if (opMinus1/list.size() < 0.5){
            optbMinus1 = "Opp: ТM(" + total + ")";
            opTBMinus1 = String.valueOf((int) (list.size() - opMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opMinus1)/list.size()*100, 1) + ")";
        }

        String winS = Team.getShortName(teamName) + ": Ф(-0.5)";
        String winSS = String.valueOf((int) win) + "/" + list.size() + " (" + MyMath.round(win/list.size()*100, 1) + ")";

        String winXS = Team.getShortName(teamName) + ": Ф(+0.5)";
        String winXSS = String.valueOf((int) winX) + "/" + list.size() + " (" + MyMath.round(winX/list.size()*100, 1) + ")";

        String[] colHeads = {"Ставка", "Заход и %"};
        Object[][] data = {
                {t85 , t85s},
                {t95 , t95s},
                {t105 , t105s},
                {itbMinus1 , selfTBMinus1},
                {itbSred , selfTBSred},
                {itbPlus1 , selfTBPlus1},
                {optbMinus1 , opTBMinus1},
                {optbSred , opTBSred},
                {optbPlus1 , opTBPlus1},
                {winS , winSS},
                {winXS , winXSS},
        };

        Font font = new Font("Arial", Font.BOLD, 12);
        JTable tableUSV = new JTable(data, colHeads);
        tableUSV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUSV.setEnabled(false);
        tableUSV.getTableHeader().setFont(font);
        tableUSV.setFont(font);
        tableUSV.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableUSV.setRowHeight(25);
        tableUSV.getColumnModel().getColumn(1).setPreferredWidth(92);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableUSV.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableUSV.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableUSV.setBackground(new Color(238, 238, 238));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
        tablePanel.add(tableUSV, BorderLayout.CENTER);
        tablePanel.add(tableUSV.getTableHeader(), BorderLayout.NORTH);

        return tablePanel;
    }

    public static JPanel getTablePen2minutes3per(String teamName, /*ArrayList<Match> list*/ Selector selector){
        double win = 0;
        double winX = 0;

        double parameter = Double.parseDouble(selector.pList.get(30).get(1))/ (double) selector.listOfMatches.size();
        double parameterOpponent = Double.parseDouble(selector.pList.get(30).get(2))/ (double) selector.listOfMatches.size();

        double totalAveragePenMin = (parameter + parameterOpponent);
        double selfAveragePenMin = parameter;
        double opAveragePenMin = parameterOpponent;

        if (MyMath.round(totalAveragePenMin,0) > totalAveragePenMin)
            totalAveragePenMin = MyMath.round(totalAveragePenMin,0) - 0.5;
        else
            totalAveragePenMin = MyMath.round(totalAveragePenMin,0) + 0.5;
        if (MyMath.round(selfAveragePenMin,0) > selfAveragePenMin)
            selfAveragePenMin = MyMath.round(selfAveragePenMin,0) - 0.5;
        else
            selfAveragePenMin = MyMath.round(selfAveragePenMin,0) + 0.5;
        if (MyMath.round(opAveragePenMin,0) > opAveragePenMin)
            opAveragePenMin = MyMath.round(opAveragePenMin,0) - 0.5;
        else
            opAveragePenMin = MyMath.round(opAveragePenMin,0) + 0.5;
        double totalSred = 0;
        double totalPlus1 = 0;
        double totalMinus1 = 0;
        double selfSred = 0;
        double selfPlus1 = 0;
        double selfMinus1 = 0;
        double opSred = 0;
        double opPlus1 = 0;
        double opMinus1 = 0;

        ArrayList<Match> list = selector.listOfMatches;

        for (int i=0; i<list.size(); i++){
            if (list.get(i).home2MinPenalties3rdPeriod + list.get(i).away2MinPenalties3rdPeriod > (totalAveragePenMin-1))
                totalMinus1++;
            if (list.get(i).home2MinPenalties3rdPeriod + list.get(i).away2MinPenalties3rdPeriod > totalAveragePenMin)
                totalSred++;
            if (list.get(i).home2MinPenalties3rdPeriod + list.get(i).away2MinPenalties3rdPeriod > (totalAveragePenMin+1))
                totalPlus1++;

            if ((list.get(i).home2MinPenalties3rdPeriod > (selfAveragePenMin+1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).away2MinPenalties3rdPeriod > (selfAveragePenMin+1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfPlus1++;
            if ((list.get(i).home2MinPenalties3rdPeriod > (selfAveragePenMin)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).away2MinPenalties3rdPeriod > (selfAveragePenMin)))&&(teamName.equals(list.get(i).awayTeam))))
                selfSred++;
            if ((list.get(i).home2MinPenalties3rdPeriod > (selfAveragePenMin-1)&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).away2MinPenalties3rdPeriod > (selfAveragePenMin-1)))&&(teamName.equals(list.get(i).awayTeam))))
                selfMinus1++;

            if ((list.get(i).home2MinPenalties3rdPeriod > (opAveragePenMin+1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).away2MinPenalties3rdPeriod > (opAveragePenMin+1)))&&(teamName.equals(list.get(i).homeTeam))))
                opPlus1++;
            if ((list.get(i).home2MinPenalties3rdPeriod > (opAveragePenMin)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).away2MinPenalties3rdPeriod > (opAveragePenMin)))&&(teamName.equals(list.get(i).homeTeam))))
                opSred++;
            if ((list.get(i).home2MinPenalties3rdPeriod > (opAveragePenMin-1)&&(teamName.equals(list.get(i).awayTeam))||((list.get(i).away2MinPenalties3rdPeriod > (opAveragePenMin-1)))&&(teamName.equals(list.get(i).homeTeam))))
                opMinus1++;
            if ((list.get(i).home2MinPenalties3rdPeriod > list.get(i).away2MinPenalties3rdPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).away2MinPenalties3rdPeriod > list.get(i).home2MinPenalties3rdPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                win++;
            if ((list.get(i).home2MinPenalties3rdPeriod >= list.get(i).away2MinPenalties3rdPeriod&&(teamName.equals(list.get(i).homeTeam))||((list.get(i).away2MinPenalties3rdPeriod >= list.get(i).home2MinPenalties3rdPeriod))&&(teamName.equals(list.get(i).awayTeam))))
                winX++;
        }

        String t85 = "ТБ(" + String.valueOf(totalAveragePenMin-1) + ")";
        String t85s = String.valueOf((int) (totalMinus1)) + "/" + list.size() + " (" + MyMath.round(totalMinus1/list.size()*100, 1) + ")";
        if (totalMinus1/list.size() < 0.5){
            t85 = "ТM(" + String.valueOf(totalAveragePenMin-1) + ")";
            t85s = String.valueOf((int) (list.size() - totalMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalMinus1)/list.size()*100, 1) + ")";
        }
        String t95 = "ТБ(" + String.valueOf(totalAveragePenMin) + ")";
        String t95s = String.valueOf((int) (totalSred)) + "/" + list.size() + " (" + MyMath.round(totalSred/list.size()*100, 1) + ")";
        if (totalSred/list.size() < 0.5){
            t95 = "ТM(" + String.valueOf(totalAveragePenMin) + ")";
            t95s = String.valueOf((int) (list.size() - totalSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalSred)/list.size()*100, 1) + ")";
        }

        String t105 = "ТБ(" + String.valueOf(totalAveragePenMin+1) + ")";
        String t105s = String.valueOf((int) (totalPlus1)) + "/" + list.size() + " (" + MyMath.round(totalPlus1/list.size()*100, 1) + ")";
        if (totalPlus1/list.size() < 0.5){
            t105 = "ТM(" + String.valueOf(totalAveragePenMin+1) + ")";
            t105s = String.valueOf((int) (list.size() - totalPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - totalPlus1)/list.size()*100, 1) + ")";
        }

        double total = selfAveragePenMin + 1;
        String itbPlus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBPlus1 = String.valueOf((int) (selfPlus1)) + "/" + list.size() + " (" + MyMath.round(selfPlus1/list.size()*100, 1) + ")";
        if (selfPlus1/list.size() < 0.5){
            itbPlus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBPlus1 = String.valueOf((int) (list.size() - selfPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfPlus1)/list.size()*100, 1) + ")";
        }

        total = selfAveragePenMin;
        String itbSred = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBSred = String.valueOf((int) (selfSred)) + "/" + list.size() + " (" + MyMath.round(selfSred/list.size()*100, 1) + ")";
        if (selfSred/list.size() < 0.5){
            itbSred = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBSred = String.valueOf((int) (list.size() - selfSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfSred)/list.size()*100, 1) + ")";
        }

        total = selfAveragePenMin - 1;
        String itbMinus1 = Team.getShortName(teamName) + ": ТБ(" + total + ")";
        String selfTBMinus1 = String.valueOf((int) (selfMinus1)) + "/" + list.size() + " (" + MyMath.round(selfMinus1/list.size()*100, 1) + ")";
        if (selfMinus1/list.size() < 0.5){
            itbMinus1 = Team.getShortName(teamName) + ": ТM(" + total + ")";
            selfTBMinus1 = String.valueOf((int) (list.size() - selfMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - selfMinus1)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin + 1;
        String optbPlus1 = "Opp: ТБ(" + total + ")";
        String opTBPlus1 = String.valueOf((int) (opPlus1)) + "/" + list.size() + " (" + MyMath.round(opPlus1/list.size()*100, 1) + ")";
        if (opPlus1/list.size() < 0.5){
            optbPlus1 = "Opp: ТM(" + total + ")";
            opTBPlus1 = String.valueOf((int) (list.size() - opPlus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opPlus1)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin;
        String optbSred = "Opp: ТБ(" + total + ")";
        String opTBSred = String.valueOf((int) (opSred)) + "/" + list.size() + " (" + MyMath.round(opSred/list.size()*100, 1) + ")";
        if (opSred/list.size() < 0.5){
            optbSred = "Opp: ТM(" + total + ")";
            opTBSred = String.valueOf((int) (list.size() - opSred)) + "/" + list.size() + " (" + MyMath.round((list.size() - opSred)/list.size()*100, 1) + ")";
        }

        total = opAveragePenMin - 1;
        String optbMinus1 = "Opp: ТБ(" + total + ")";
        String opTBMinus1 = String.valueOf((int) (opMinus1)) + "/" + list.size() + " (" + MyMath.round(opMinus1/list.size()*100, 1) + ")";
        if (opMinus1/list.size() < 0.5){
            optbMinus1 = "Opp: ТM(" + total + ")";
            opTBMinus1 = String.valueOf((int) (list.size() - opMinus1)) + "/" + list.size() + " (" + MyMath.round((list.size() - opMinus1)/list.size()*100, 1) + ")";
        }

        String winS = Team.getShortName(teamName) + ": Ф(-0.5)";
        String winSS = String.valueOf((int) win) + "/" + list.size() + " (" + MyMath.round(win/list.size()*100, 1) + ")";

        String winXS = Team.getShortName(teamName) + ": Ф(+0.5)";
        String winXSS = String.valueOf((int) winX) + "/" + list.size() + " (" + MyMath.round(winX/list.size()*100, 1) + ")";

        String[] colHeads = {"Ставка", "Заход и %"};
        Object[][] data = {
                {t85 , t85s},
                {t95 , t95s},
                {t105 , t105s},
                {itbMinus1 , selfTBMinus1},
                {itbSred , selfTBSred},
                {itbPlus1 , selfTBPlus1},
                {optbMinus1 , opTBMinus1},
                {optbSred , opTBSred},
                {optbPlus1 , opTBPlus1},
                {winS , winSS},
                {winXS , winXSS},
        };

        Font font = new Font("Arial", Font.BOLD, 12);
        JTable tableUSV = new JTable(data, colHeads);
        tableUSV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUSV.setEnabled(false);
        tableUSV.getTableHeader().setFont(font);
        tableUSV.setFont(font);
        tableUSV.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableUSV.setRowHeight(25);
        tableUSV.getColumnModel().getColumn(1).setPreferredWidth(92);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableUSV.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableUSV.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableUSV.setBackground(new Color(238, 238, 238));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
        tablePanel.add(tableUSV, BorderLayout.CENTER);
        tablePanel.add(tableUSV.getTableHeader(), BorderLayout.NORTH);

        return tablePanel;
    }

    public static JPanel getTableCorrelation(String teamName, ArrayList<Match> list, ArrayList<ArrayList<String>> pList){
        String[] headsCorr = {"Корреляции " + teamName , "Собственные", "Противник", "Фора" };

        double corGoalsRealPP = Correlator.getCorrelationOfParams(teamName, "ГолыОВ", "Реал.больш.", list, pList);
        double corGoalsRealPPOpponent = Correlator.getCorrelationOfParams(teamName, "ГолыОВПротивника", "Реал.больш.Противника", list, pList);
        double corGoalsRealPPHandicap = Correlator.getCorrelationOfParams(teamName, "ГолыОВФора", "Реал.больш.", list, pList);
        double corGoalsShorthanded = Correlator.getCorrelationOfParams(teamName, "ГолыОВ", "Реал.меньш.", list, pList);
        double corGoalsShorthandedOpponent = Correlator.getCorrelationOfParams(teamName, "ГолыОВПротивника", "Реал.меньш.Противника", list, pList);
        double corGoalsShorthandedHandicap = Correlator.getCorrelationOfParams(teamName, "ГолыОВФора", "Реал.меньш.", list, pList);
        double corGoals_1per = Correlator.getCorrelationOfParams(teamName, "ГолыОВ", "Фора1пер", list, pList);
        double corGoals_1perOpponent = Correlator.getCorrelationOfParams(teamName, "ГолыОВПротивника", "Фора1перПротивника", list, pList);
        double corGoals_1perHandicap = Correlator.getCorrelationOfParams(teamName, "ГолыОВФора", "Фора1перФора", list, pList);



        double corPossOffsides = Correlator.getCorrelationOfParams(teamName, "Владение", "Офсайды", list, pList);
        double corPossOffsidesOpponent = Correlator.getCorrelationOfParams(teamName, "ВладениеПротивника", "ОфсайдыПротивника", list, pList);
        double corPossOffsidesHandicap = Correlator.getCorrelationOfParams(teamName, "Владение", "ОфсайдыФора", list, pList);
        double corPossYC = Correlator.getCorrelationOfParams(teamName, "Владение", "ЖК", list, pList);
        double corPossYCOpponent = Correlator.getCorrelationOfParams(teamName, "ВладениеПротивника", "ЖКПротивника", list, pList);
        double corPossYCHandicap = Correlator.getCorrelationOfParams(teamName, "Владение", "ЖКФора", list, pList);
        double corShotsUSV = Correlator.getCorrelationOfParams(teamName, "Удары", "УдарыВСтвор", list, pList);
        double corShotsUSVOpponent = Correlator.getCorrelationOfParams(teamName, "УдарыПротивника", "УдарыВСтворПротивника", list, pList);
        double corShotsUSVHandicap = Correlator.getCorrelationOfParams(teamName, "УдарыФора", "УдарыВСтворФора", list, pList);
        double corShotsCorners = Correlator.getCorrelationOfParams(teamName, "Удары", "Угловые", list, pList);
        double corShotsCornersOpponent = Correlator.getCorrelationOfParams(teamName, "УдарыПротивника", "УгловыеПротивника", list, pList);
        double corShotsCornersHandicap = Correlator.getCorrelationOfParams(teamName, "УдарыФора", "УгловыеФора", list, pList);
        double corFoulsYC = Correlator.getCorrelationOfParams(teamName, "Фолы", "ЖК", list, pList);
        double corFoulsYCOpponent = Correlator.getCorrelationOfParams(teamName, "ФолыПротивника", "ЖКПротивника", list, pList);
        double corFoulsYCHandicap = Correlator.getCorrelationOfParams(teamName, "ФолыФора", "ЖКФора", list, pList);
        double corPossShots = Correlator.getCorrelationOfParams(teamName, "Владение", "Удары", list, pList);
        double corPossShotsOpponent = Correlator.getCorrelationOfParams(teamName, "ВладениеПротивника", "УдарыПротивника", list, pList);
        double corPossShotsHandicap = Correlator.getCorrelationOfParams(teamName, "Владение", "УдарыФора", list, pList);
        double corUVSCorners = Correlator.getCorrelationOfParams(teamName, "УдарыВСтвор", "Угловые", list, pList);
        double corUVSCornersOpponent = Correlator.getCorrelationOfParams(teamName, "УдарыВСтворПротивника", "УгловыеПротивника", list, pList);
        double corUVSCornersHandicap = Correlator.getCorrelationOfParams(teamName, "УдарыВСтворФора", "УгловыеФора", list, pList);
        double corCornersYC = Correlator.getCorrelationOfParams(teamName, "Угловые", "ЖК", list, pList);
        double corCornersYCOpponent = Correlator.getCorrelationOfParams(teamName, "УгловыеПротивника", "ЖКПротивника", list, pList);
        double corCornersYCHandicap = Correlator.getCorrelationOfParams(teamName, "УгловыеФора", "ЖКФора", list, pList);

        Object[][] dataCorr = {
                {"Голы и реал.больш.", String.valueOf(MyMath.round(corGoalsRealPP, 3)),
                        String.valueOf(MyMath.round(corGoalsRealPPOpponent, 3)),
                        String.valueOf(MyMath.round(corGoalsRealPPHandicap, 3))},
                {"Голы и реал.меньш.", String.valueOf(MyMath.round(corGoalsShorthanded, 3)),
                        String.valueOf(MyMath.round(corGoalsShorthandedOpponent, 3)),
                        String.valueOf(MyMath.round(corGoalsShorthandedHandicap, 3))},
                {"Голы и фора 1 периода", String.valueOf(MyMath.round(corGoals_1per, 3)),
                        String.valueOf(MyMath.round(corGoals_1perOpponent, 3)),
                        String.valueOf(MyMath.round(corGoals_1perHandicap, 3))},


                {"Владение и удары", String.valueOf(MyMath.round(corPossShots, 3)),
                        String.valueOf(MyMath.round(corPossShotsOpponent, 3)),
                        String.valueOf(MyMath.round(corPossShotsHandicap, 3))},
                {"Владение и офсайды", String.valueOf(MyMath.round(corPossOffsides, 3)),
                        String.valueOf(MyMath.round(corPossOffsidesOpponent, 3)),
                        String.valueOf(MyMath.round(corPossOffsidesHandicap, 3))},
                {"Владение и ЖК", String.valueOf(MyMath.round(corPossYC, 3)),
                        String.valueOf(MyMath.round(corPossYCOpponent, 3)),
                        String.valueOf(MyMath.round(corPossYCHandicap, 3))},
                {"Удары и УВС", String.valueOf(MyMath.round(corShotsUSV, 3)),
                        String.valueOf(MyMath.round(corShotsUSVOpponent, 3)),
                        String.valueOf(MyMath.round(corShotsUSVHandicap, 3))},
                {"Удары и угловые", String.valueOf(MyMath.round(corShotsCorners, 3)),
                        String.valueOf(MyMath.round(corShotsCornersOpponent, 3)),
                        String.valueOf(MyMath.round(corShotsCornersHandicap, 3))},
                {"Удары в створ и угловые", String.valueOf(MyMath.round(corUVSCorners, 3)),
                        String.valueOf(MyMath.round(corUVSCornersOpponent, 3)),
                        String.valueOf(MyMath.round(corUVSCornersHandicap, 3))},
                {"Угловые и ЖК", String.valueOf(MyMath.round(corCornersYC, 3)),
                        String.valueOf(MyMath.round(corCornersYCOpponent, 3)),
                        String.valueOf(MyMath.round(corCornersYCHandicap, 3))},
                {"Фолы и ЖК", String.valueOf(MyMath.round(corFoulsYC, 3)),
                        String.valueOf(MyMath.round(corFoulsYCOpponent, 3)),
                        String.valueOf(MyMath.round(corFoulsYCHandicap, 3))}
        };

        Font font = new Font("Arial", Font.BOLD, 15);
        JTable tableCorr = new JTable(dataCorr, headsCorr);
        tableCorr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableCorr.setEnabled(false);
        tableCorr.getTableHeader().setFont(font);
        tableCorr.setFont(font);
        tableCorr.getColumnModel().getColumn(0).setPreferredWidth(233);
        tableCorr.setRowHeight(25);
        tableCorr.getColumnModel().getColumn(1).setPreferredWidth(120);
        tableCorr.getColumnModel().getColumn(2).setPreferredWidth(120);
        tableCorr.getColumnModel().getColumn(3).setPreferredWidth(120);

        Renderer renderer = new Renderer(2);
        renderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i=0; i<tableCorr.getColumnCount();i++){
            tableCorr.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(tableCorr, BorderLayout.CENTER);
        tablePanel.add(tableCorr.getTableHeader(), BorderLayout.NORTH);

        return tablePanel;
    }

    public static JPanel getTableSTOpponents(String teamName, ArrayList<Match> list, String allOrHomeOrAway, ArrayList<Selector> opponentsList){
        int rowCount = list.size();

        String[] colHeads = {"Соперник", "Счет матча", "Бр. в створ", "Нанесено (сред.)", "Получено (сред.)", "Тотал (сред.)"
                /*"Выб. дисперсия", "MIN/MAX нанесено", "MIN/MAX получено"*/};
        Object[][] data = new Object[rowCount][colHeads.length];

        for (int i=0; i<rowCount; i++){
            String opponent = "";
            switch (allOrHomeOrAway) {
                case "Все матчи":
                    if (teamName.equals(list.get(i).homeTeam))
                        opponent = list.get(i).awayTeam;
                    else
                        opponent = list.get(i).homeTeam;
                    break;
                case "Дома":
                    opponent = list.get(i).awayTeam;
                    break;
                case "На выезде":
                    opponent = list.get(i).homeTeam;
                    break;
            }

            /*int matches = opponentsList.get(i).listOfMatches.size();
            int minShots = 1000;
            int maxShots = 0;
            int minShotsOp = 1000;
            int maxShotsOp = 0;
            double disp = 0;
            double MOshots = Double.parseDouble(opponentsList.get(i).pList.get(7).get(1));

            for (int k=0; k<opponentsList.get(i).listOfMatches.size(); k++){
                Match m = opponentsList.get(i).listOfMatches.get(k);

                if (m.homeTeam.equals(opponent)){
                    if (m.homeShotsOnTarget > maxShots)
                        maxShots = m.homeShotsOnTarget;
                    if (m.homeShotsOnTarget < minShots)
                        minShots = m.homeShotsOnTarget;
                    if (m.awayShotsOnTarget > maxShotsOp)
                        maxShotsOp = m.awayShotsOnTarget;
                    if (m.awayShotsOnTarget < minShotsOp)
                        minShotsOp = m.awayShotsOnTarget;
                    disp += Math.pow( (m.homeShotsOnTarget - MOshots) , 2);
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
                }
            }
            disp = disp / matches;*/

            String s = "";
            if (list.get(i).homeScore == list.get(i).awayScore){
                if (list.get(i).homeScoreOT == 1)
                    s = s + String.valueOf(list.get(i).homeScore+1) + ":" + list.get(i).awayScore + "OT";
                if (list.get(i).homeScoreBullits == 1)
                    s = s + String.valueOf(list.get(i).homeScore+1) + ":" + list.get(i).awayScore + "Б" ;
                if (list.get(i).awayScoreOT == 1)
                    s = s + list.get(i).homeScore + ":" + String.valueOf(list.get(i).awayScore+1) + "OT";
                if (list.get(i).awayScoreBullits == 1)
                    s = s + list.get(i).homeScore + ":" + String.valueOf(list.get(i).awayScore+1) + "Б";
            } else
                s = s + list.get(i).homeScore + ":" + list.get(i).awayScore;


            data[i] = new String[]{opponent,
                    Team.getShortName(list.get(i).homeTeam) + "  " + s + "  " + Team.getShortName(list.get(i).awayTeam),
                    Team.getShortName(list.get(i).homeTeam) + "  " + list.get(i).homeShotsOnTarget + ":" + list.get(i).awayShotsOnTarget + "  " + Team.getShortName(list.get(i).awayTeam),
                    opponentsList.get(i).pList.get(7).get(1),
                    opponentsList.get(i).pList.get(7).get(2),
                    String.valueOf(MyMath.round(Double.parseDouble(opponentsList.get(i).pList.get(7).get(1)) + Double.parseDouble(opponentsList.get(i).pList.get(7).get(2)), 2)),
                    //String.valueOf(MyMath.round(disp, 3)),
                    //String.valueOf(minShots) + " / " + String.valueOf(maxShots),
                    //String.valueOf(minShotsOp) + " / " + String.valueOf(maxShotsOp)
            };

        }

        Font font = new Font("Arial", Font.BOLD, 12);
        JTable tableUSV = new JTable(data, colHeads);
        tableUSV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUSV.setEnabled(false);
        tableUSV.getTableHeader().setFont(font);
        tableUSV.setFont(font);
        tableUSV.setRowHeight(25);
        //tableUSV.getColumnModel().getColumn(0).setPreferredWidth(120);
        //tableUSV.getColumnModel().getColumn(1).setPreferredWidth(120);
        //tableUSV.getColumnModel().getColumn(2).setPreferredWidth(120);
        //tableUSV.getColumnModel().getColumn(3).setPreferredWidth(120);
        //tableUSV.getColumnModel().getColumn(4).setPreferredWidth(120);
        //tableUSV.getColumnModel().getColumn(5).setPreferredWidth(120);

        Renderer renderer = new Renderer(Team.getShortName(teamName), 5, 0);

        for (int r=0; r<colHeads.length; r++)
            tableUSV.getColumnModel().getColumn(r).setCellRenderer(renderer);

        tableUSV.setBackground(new Color(238, 238, 238));


        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(tableUSV);
        tablePanel.add(tableUSV.getTableHeader(), BorderLayout.NORTH);
        tablePanel.setSize(1190, (rowCount + 1) * 25 - 6);

        return tablePanel;
    }

    public static JPanel getTablePenaltiesOpponents(String teamName, ArrayList<Match> list, String allOrHomeOrAway, ArrayList<Selector> opponentsList){
        int rowCount = list.size();

        String[] colHeads = {"Соперник", "Счет матча", "Штрафное время", "Штр. время (сред.)", "Штр. время прот.(сред.)",
                "Кол-во 2мин. уд. (сред.)", "Кол-во 2мин. уд. прот.(сред.)"};
        Object[][] data = new Object[rowCount][colHeads.length];

        for (int i=0; i<rowCount; i++){
            String opponent = "";
            switch (allOrHomeOrAway) {
                case "Все матчи":
                    if (teamName.equals(list.get(i).homeTeam))
                        opponent = list.get(i).awayTeam;
                    else
                        opponent = list.get(i).homeTeam;
                    break;
                case "Дома":
                    opponent = list.get(i).awayTeam;
                    break;
                case "На выезде":
                    opponent = list.get(i).homeTeam;
                    break;
            }


            int matches = opponentsList.get(i).listOfMatches.size();

            String s = "";
            if (list.get(i).homeScore == list.get(i).awayScore){
                if (list.get(i).homeScoreOT == 1)
                    s = s + String.valueOf(list.get(i).homeScore+1) + ":" + list.get(i).awayScore + "OT";
                if (list.get(i).homeScoreBullits == 1)
                    s = s + String.valueOf(list.get(i).homeScore+1) + ":" + list.get(i).awayScore + "Б" ;
                if (list.get(i).awayScoreOT == 1)
                    s = s + list.get(i).homeScore + ":" + String.valueOf(list.get(i).awayScore+1) + "OT";
                if (list.get(i).awayScoreBullits == 1)
                    s = s + list.get(i).homeScore + ":" + String.valueOf(list.get(i).awayScore+1) + "Б";
            } else
                s = s + list.get(i).homeScore + ":" + list.get(i).awayScore;

            data[i] = new String[]{
                    opponent,
                    Team.getShortName(list.get(i).homeTeam) + "  " + s + "  " + Team.getShortName(list.get(i).awayTeam),
                    Team.getShortName(list.get(i).homeTeam) + "  " + list.get(i).homePenaltyMinutes + ":" + list.get(i).awayPenaltyMinutes + "  " + Team.getShortName(list.get(i).awayTeam),
                    String.valueOf(MyMath.round(Double.parseDouble(opponentsList.get(i).pList.get(14).get(1)) / matches, 2)),
                    String.valueOf(MyMath.round(Double.parseDouble(opponentsList.get(i).pList.get(14).get(2)) / matches, 2)),
                    String.valueOf(MyMath.round(Double.parseDouble(opponentsList.get(i).pList.get(15).get(1)) / matches, 2)),
                    String.valueOf(MyMath.round(Double.parseDouble(opponentsList.get(i).pList.get(15).get(2)) / matches, 2))};

        }

        Font font = new Font("Arial", Font.BOLD, 12);
        JTable tableUSV = new JTable(data, colHeads);
        tableUSV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableUSV.setEnabled(false);
        tableUSV.getTableHeader().setFont(font);
        tableUSV.setFont(font);
        tableUSV.setRowHeight(25);
//        tableUSV.getColumnModel().getColumn(0).setPreferredWidth(180);
//        tableUSV.getColumnModel().getColumn(1).setPreferredWidth(180);
//        tableUSV.getColumnModel().getColumn(2).setPreferredWidth(180);
//        tableUSV.getColumnModel().getColumn(3).setPreferredWidth(180);
//        tableUSV.getColumnModel().getColumn(4).setPreferredWidth(180);

        Renderer renderer = new Renderer(Team.getShortName(teamName), 5, 0);

        for (int r=0; r<colHeads.length; r++)
            tableUSV.getColumnModel().getColumn(r).setCellRenderer(renderer);


        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(tableUSV);
        tablePanel.add(tableUSV.getTableHeader(), BorderLayout.NORTH);
        tablePanel.setSize(1190, (rowCount + 1) * 25 - 6);

        return tablePanel;
    }


    /*for (int k=0; k<selector.listOfMatches.size(); k++){
                *//*int matches = selector.listOfMatches.size();
                double howManyOT = 0;
                double minShots = 1000;
                double maxShots = 0;
                double minShotsOp = 1000;
                double maxShotsOp = 0;
                double disp = 0;
                double dispOp = 0;
                double MOshots = Double.parseDouble(selector.pList.get(7).get(1));
                double MOshotsOp = Double.parseDouble(selector.pList.get(7).get(2));

                for (int j=0; j<selector.listOfMatches.size(); j++){
                    Match m = selector.listOfMatches.get(j);
                    if (m.homeScoreOT==1 || m.awayScoreOT==1)
                        howManyOT += 0.5;
                    else if (m.homeScoreBullits==1 || m.awayScoreBullits==1)
                        howManyOT += 1;

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
                    }
                }
                disp = disp / matches;
                dispOp = dispOp / matches;

                double minutes = 60 + howManyOT*5/matches;*//*
    }*/

    /*public static JPanel getTableCorrelation(String teamName, ArrayList<Match> list, ArrayList<ArrayList<String>> pList){
        String[] headsCorr = {"Корреляции " + teamName , "Собственные", "Противник", "Фора" };

        double corPossCorners = CapperRobot.getCorrelationOfParams(teamName, "Владение", "Угловые", list, pList);
        double corPossCornersOpponent = CapperRobot.getCorrelationOfParams(teamName, "ВладениеПротивника", "УгловыеПротивника", list, pList);
        double corPossCornersHandicap = CapperRobot.getCorrelationOfParams(teamName, "Владение", "УгловыеФора", list, pList);
        double corPossFouls = CapperRobot.getCorrelationOfParams(teamName, "Владение", "ФолыПротивника", list, pList);
        double corPossFoulsOpponent = CapperRobot.getCorrelationOfParams(teamName, "ВладениеПротивника", "Фолы", list, pList);
        double corPossFoulsHandicap = CapperRobot.getCorrelationOfParams(teamName, "ВладениеПротивника", "ФолыФора", list, pList);
        double corPossUSV = CapperRobot.getCorrelationOfParams(teamName, "Владение", "УдарыВСтвор", list, pList);
        double corPossUSVOpponent = CapperRobot.getCorrelationOfParams(teamName, "ВладениеПротивника", "УдарыВСтворПротивника", list, pList);
        double corPossUSVHandicap = CapperRobot.getCorrelationOfParams(teamName, "Владение", "УдарыВСтворФора", list, pList);
        double corPossOffsides = CapperRobot.getCorrelationOfParams(teamName, "Владение", "Офсайды", list, pList);
        double corPossOffsidesOpponent = CapperRobot.getCorrelationOfParams(teamName, "ВладениеПротивника", "ОфсайдыПротивника", list, pList);
        double corPossOffsidesHandicap = CapperRobot.getCorrelationOfParams(teamName, "Владение", "ОфсайдыФора", list, pList);
        double corPossYC = CapperRobot.getCorrelationOfParams(teamName, "Владение", "ЖКПротивника", list, pList);
        double corPossYCOpponent = CapperRobot.getCorrelationOfParams(teamName, "ВладениеПротивника", "ЖК", list, pList);
        double corPossYCHandicap = CapperRobot.getCorrelationOfParams(teamName, "ВладениеПротивника", "ЖКФора", list, pList);
        double corShotsUSV = CapperRobot.getCorrelationOfParams(teamName, "Удары", "УдарыВСтвор", list, pList);
        double corShotsUSVOpponent = CapperRobot.getCorrelationOfParams(teamName, "УдарыПротивника", "УдарыВСтворПротивника", list, pList);
        double corShotsUSVHandicap = CapperRobot.getCorrelationOfParams(teamName, "УдарыФора", "УдарыВСтворФора", list, pList);
        double corShotsCorners = CapperRobot.getCorrelationOfParams(teamName, "Удары", "Угловые", list, pList);
        double corShotsCornersOpponent = CapperRobot.getCorrelationOfParams(teamName, "УдарыПротивника", "УгловыеПротивника", list, pList);
        double corShotsCornersHandicap = CapperRobot.getCorrelationOfParams(teamName, "УдарыФора", "УгловыеФора", list, pList);
        double corFoulsYC = CapperRobot.getCorrelationOfParams(teamName, "Фолы", "ЖК", list, pList);
        double corFoulsYCOpponent = CapperRobot.getCorrelationOfParams(teamName, "ФолыПротивника", "ЖКПротивника", list, pList);
        double corFoulsYCHandicap = CapperRobot.getCorrelationOfParams(teamName, "ФолыФора", "ЖКФора", list, pList);

        Object[][] dataCorr = {
                {"Владение и угловые", String.valueOf(MyMath.round(corPossCorners, 3)),
                        String.valueOf(MyMath.round(corPossCornersOpponent, 3)),
                        String.valueOf(MyMath.round(corPossCornersHandicap, 3))},
                {"Владение и фолы", String.valueOf(MyMath.round(corPossFouls, 3)),
                        String.valueOf(MyMath.round(corPossFoulsOpponent, 3)),
                        String.valueOf(MyMath.round(corPossFoulsHandicap, 3))},
                {"Владение и удары в створ", String.valueOf(MyMath.round(corPossUSV, 3)),
                        String.valueOf(MyMath.round(corPossUSVOpponent, 3)),
                        String.valueOf(MyMath.round(corPossUSVHandicap, 3))},
                {"Владение и офсайды", String.valueOf(MyMath.round(corPossOffsides, 3)),
                        String.valueOf(MyMath.round(corPossOffsidesOpponent, 3)),
                        String.valueOf(MyMath.round(corPossOffsidesHandicap, 3))},
                {"Владение и ЖК", String.valueOf(MyMath.round(corPossYC, 3)),
                        String.valueOf(MyMath.round(corPossYCOpponent, 3)),
                        String.valueOf(MyMath.round(corPossYCHandicap, 3))},
                {"Удары и УВС", String.valueOf(MyMath.round(corShotsUSV, 3)),
                        String.valueOf(MyMath.round(corShotsUSVOpponent, 3)),
                        String.valueOf(MyMath.round(corShotsUSVHandicap, 3))},
                {"Удары и угловые", String.valueOf(MyMath.round(corShotsCorners, 3)),
                        String.valueOf(MyMath.round(corShotsCornersOpponent, 3)),
                        String.valueOf(MyMath.round(corShotsCornersHandicap, 3))},
                {"Фолы и ЖК", String.valueOf(MyMath.round(corFoulsYC, 3)),
                        String.valueOf(MyMath.round(corFoulsYCOpponent, 3)),
                        String.valueOf(MyMath.round(corFoulsYCHandicap, 3))}
        };

        Font font = new Font("Arial", Font.BOLD, 12);
        JTable tableCorr = new JTable(dataCorr, headsCorr);
        tableCorr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableCorr.setEnabled(false);
        tableCorr.getTableHeader().setFont(font);
        tableCorr.setFont(font);
        tableCorr.getColumnModel().getColumn(0).setPreferredWidth(273);
        tableCorr.setRowHeight(25);
        tableCorr.getColumnModel().getColumn(1).setPreferredWidth(140);
        tableCorr.getColumnModel().getColumn(2).setPreferredWidth(140);
        tableCorr.getColumnModel().getColumn(3).setPreferredWidth(140);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableCorr.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableCorr.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableCorr.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableCorr.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(new JScrollPane(tableCorr), BorderLayout.CENTER);
        tablePanel.add(tableCorr.getTableHeader(), BorderLayout.NORTH);

        return tablePanel;
    }*/

}


