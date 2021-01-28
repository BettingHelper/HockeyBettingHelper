package sample;

import javax.swing.*;
import java.util.ArrayList;

public class Parameters {

    public Parameters() {
    }

    public static String[] getParamsList(String season){
        if (season.contains("17-18")){
            return new String[]{"Выберите показатель", "Голы в основное время", "Голы с учетом ОТ и буллитов", "Голы в первом периоде",
                    "Голы во втором периоде", "Голы в третьем периоде", "Броски в створ (осн.время)", "Кол-во двухмин. уд.", "Штр. время", "Силовые приемы"};
        } else {
            return new String[]{"Выберите показатель", "Голы в основное время", "Голы с учетом ОТ и буллитов", "Голы в первом периоде",
                    "Голы во втором периоде", "Голы в третьем периоде", "Броски в створ (осн.время)", "Броски в створ 1 пер.",
                    "Броски в створ 2 пер.", "Броски в створ 3 пер.", "Кол-во двухмин. уд.", "Кол-во двухмин. уд. 1 пер.", "Кол-во двухмин. уд. 2 пер.",
                    "Кол-во двухмин. уд. 3 пер.", "Штр. время", "Штр. время 1 пер.", "Штр. время 2 пер.",
                    "Штр. время 3 пер.", "Силовые приемы", "Вбрасывания", "Блокированные броски"};
        }

    }

    public static String[] getIndex(){
        return new String[]{"Выберите тип ставки", "Общий тотал", "Инд.тотал команды", "Инд.тотал соперника", "Фора команды"};
    }

    public static double[] getValues(String parameter, String index){
        double[] result = new double[3];

        if ((index.equals("Выберите тип ставки"))||(parameter.equals("Выберите показатель"))){
            return new double[]{0,0,0};
        }
        if (parameter.contains("Голы")&&index.equals("Общий тотал")){
            return new double[]{0.5,7.5,0.5};
        }
        if (parameter.contains("Голы")&&index.equals("Инд.тотал команды")){
            return new double[]{0.5,7.5,0.5};
        }
        if (parameter.contains("Голы")&&index.equals("Инд.тотал соперника")){
            return new double[]{0.5,7.5,0.5};
        }
        if (parameter.contains("Голы")&&index.equals("Фора команды")){
            return new double[]{-3.5,3.5,0.5};
        }

        if (parameter.contains("Броски в створ (осн.время)")&&index.equals("Общий тотал")){
            return new double[]{45,75,0.5};
        }
        if (parameter.contains("Броски в створ (осн.время)")&&index.equals("Инд.тотал команды")){
            return new double[]{15,47,0.5};
        }
        if (parameter.contains("Броски в створ (осн.время)")&&index.equals("Инд.тотал соперника")){
            return new double[]{15,47,0.5};
        }
        if (parameter.contains("Броски в створ (осн.время)")&&index.equals("Фора команды")){
            return new double[]{-15.5,15.5,0.5};
        }

        if (parameter.contains("Броски в створ")&&parameter.contains("пер.")&&index.equals("Общий тотал")){
            return new double[]{0,26,0.5};
        }
        if (parameter.contains("Броски в створ")&&parameter.contains("пер.")&&index.contains("Инд.тотал")){
            return new double[]{0,26,0.5};
        }

        if (parameter.contains("Броски в створ")&&parameter.contains("пер.")&&index.equals("Фора команды")){
            return new double[]{-10.5,10.5,0.5};
        }
        if (parameter.contains("Кол-во двухмин. уд.")&&index.equals("Общий тотал")){
            return new double[]{0,26,0.5};
        }
        if (parameter.contains("Кол-во двухмин. уд.")&&index.equals("Инд.тотал команды")){
            return new double[]{0,26,0.5};
        }
        if (parameter.contains("Кол-во двухмин. уд.")&&index.equals("Инд.тотал соперника")){
            return new double[]{0,26,0.5};
        }
        if (parameter.contains("Кол-во двухмин. уд.")&&index.equals("Фора команды")){
            return new double[]{-10.5,10.5,0.5};
        }

        if (parameter.contains("Штр. время")&&index.equals("Общий тотал")){
            return new double[]{0.5,30.5,1};
        }
        if (parameter.contains("Штр. время")&&index.equals("Инд.тотал команды")){
            return new double[]{0.5,30.5,1};
        }
        if (parameter.contains("Штр. время")&&index.equals("Инд.тотал соперника")){
            return new double[]{0.5,30.5,1};
        }
        if (parameter.contains("Штр. время")&&index.equals("Фора команды")){
            return new double[]{-10.5,10.5,0.5};
        }

        if (parameter.contains("Силовые")&&index.equals("Общий тотал")){
            return new double[]{10.5,60.5,1};
        }
        if (parameter.contains("Силовые")&&index.equals("Инд.тотал команды")){
            return new double[]{0.5,40.5,1};
        }
        if (parameter.contains("Силовые")&&index.equals("Инд.тотал соперника")){
            return new double[]{0.5,40.5,1};
        }
        if (parameter.contains("Силовые")&&index.equals("Фора команды")){
            return new double[]{-10.5,10.5,0.5};
        }

        if (parameter.contains("Вбрасывания")&&index.equals("Общий тотал")){
            return new double[]{40.5,75.5,1};
        }
        if (parameter.contains("Вбрасывания")&&index.equals("Инд.тотал команды")){
            return new double[]{10.5,45.5,1};
        }
        if (parameter.contains("Вбрасывания")&&index.equals("Инд.тотал соперника")){
            return new double[]{10.5,45.5,1};
        }
        if (parameter.contains("Вбрасывания")&&index.equals("Фора команды")){
            return new double[]{-15.5,15.5,0.5};
        }

        if (parameter.contains("Блокированные")&&index.equals("Общий тотал")){
            return new double[]{15.5,50.5,1};
        }
        if (parameter.contains("Блокированные")&&index.equals("Инд.тотал команды")){
            return new double[]{5.5,40.5,1};
        }
        if (parameter.contains("Блокированные")&&index.equals("Инд.тотал соперника")){
            return new double[]{5.5,40.5,1};
        }
        if (parameter.contains("Блокированные")&&index.equals("Фора команды")){
            return new double[]{-15.5,15.5,0.5};
        }



        return result;
    }

    public static JTable getTableByParams(String teamName, ArrayList<Match> matchList, String parameter, String index, Double value){

        int typeOfTable = 1;

        if ((index == null)||(value == null)){
            index = "Выберите тип ставки";
        }
        int numberOfMatches = matchList.size();
        int numberPlus = 0;
        int numberEqual = 0;
        int numberMinus = 0;

        double morePercent;
        double lessPercent;
        double equalPercent;

        if ((parameter.equals("Голы в основное время"))&&(index.equals("Общий тотал"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeScore + matchList.get(i).awayScore > value)
                    numberPlus++;
                if (matchList.get(i).homeScore + matchList.get(i).awayScore < value)
                    numberMinus++;
                if (matchList.get(i).homeScore + matchList.get(i).awayScore == value)
                    numberEqual++;
            }
        }

        if ((parameter.equals("Голы в основное время"))&&(index.equals("Инд.тотал команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeScore > value)
                        numberPlus++;
                    if (matchList.get(i).homeScore < value)
                        numberMinus++;
                    if (matchList.get(i).homeScore == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayScore > value)
                        numberPlus++;
                    if (matchList.get(i).awayScore < value)
                        numberMinus++;
                    if (matchList.get(i).awayScore == value)
                        numberEqual++;
                }
            }
        }

        if ((parameter.equals("Голы в основное время"))&&(index.equals("Инд.тотал соперника"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).awayTeam.equals(teamName)){
                    if (matchList.get(i).homeScore > value)
                        numberPlus++;
                    if (matchList.get(i).homeScore < value)
                        numberMinus++;
                    if (matchList.get(i).homeScore == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayScore > value)
                        numberPlus++;
                    if (matchList.get(i).awayScore < value)
                        numberMinus++;
                    if (matchList.get(i).awayScore == value)
                        numberEqual++;
                }
            }
        }

        if ((parameter.equals("Голы в основное время"))&&(index.equals("Фора команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeScore - matchList.get(i).awayScore + value > 0)
                        numberPlus++;
                    if (matchList.get(i).homeScore - matchList.get(i).awayScore + value < 0)
                        numberMinus++;
                    if (matchList.get(i).homeScore - matchList.get(i).awayScore + value == 0)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayScore - matchList.get(i).homeScore + value > 0)
                        numberPlus++;
                    if (matchList.get(i).awayScore - matchList.get(i).homeScore + value < 0)
                        numberMinus++;
                    if (matchList.get(i).awayScore - matchList.get(i).homeScore + value == 0)
                        numberEqual++;
                }
            }
            typeOfTable = 2;
        }

        if ((parameter.equals("Голы с учетом ОТ и буллитов"))&&(index.equals("Общий тотал"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeScore + matchList.get(i).homeScoreOT + matchList.get(i).homeScoreBullits + matchList.get(i).awayScore + matchList.get(i).awayScoreOT + matchList.get(i).awayScoreBullits > value)
                    numberPlus++;
                if (matchList.get(i).homeScore + matchList.get(i).homeScoreOT + matchList.get(i).homeScoreBullits + matchList.get(i).awayScore + matchList.get(i).awayScoreOT + matchList.get(i).awayScoreBullits < value)
                    numberMinus++;
                if (matchList.get(i).homeScore + matchList.get(i).homeScoreOT + matchList.get(i).homeScoreBullits + matchList.get(i).awayScore + matchList.get(i).awayScoreOT + matchList.get(i).awayScoreBullits == value)
                    numberEqual++;
            }
        }

        if ((parameter.equals("Голы с учетом ОТ и буллитов"))&&(index.equals("Инд.тотал команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeScore + matchList.get(i).homeScoreOT + matchList.get(i).homeScoreBullits > value)
                        numberPlus++;
                    if (matchList.get(i).homeScore + matchList.get(i).homeScoreOT + matchList.get(i).homeScoreBullits < value)
                        numberMinus++;
                    if (matchList.get(i).homeScore + matchList.get(i).homeScoreOT + matchList.get(i).homeScoreBullits == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayScore + matchList.get(i).awayScoreOT + matchList.get(i).awayScoreBullits > value)
                        numberPlus++;
                    if (matchList.get(i).awayScore + matchList.get(i).awayScoreOT + matchList.get(i).awayScoreBullits < value)
                        numberMinus++;
                    if (matchList.get(i).awayScore + matchList.get(i).awayScoreOT + matchList.get(i).awayScoreBullits == value)
                        numberEqual++;
                }
            }
        }

        if ((parameter.equals("Голы с учетом ОТ и буллитов"))&&(index.equals("Инд.тотал соперника"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).awayTeam.equals(teamName)){
                    if (matchList.get(i).homeScore + matchList.get(i).homeScoreOT + matchList.get(i).homeScoreBullits > value)
                        numberPlus++;
                    if (matchList.get(i).homeScore + matchList.get(i).homeScoreOT + matchList.get(i).homeScoreBullits < value)
                        numberMinus++;
                    if (matchList.get(i).homeScore + matchList.get(i).homeScoreOT + matchList.get(i).homeScoreBullits == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayScore + matchList.get(i).awayScoreOT + matchList.get(i).awayScoreBullits > value)
                        numberPlus++;
                    if (matchList.get(i).awayScore + matchList.get(i).awayScoreOT + matchList.get(i).awayScoreBullits < value)
                        numberMinus++;
                    if (matchList.get(i).awayScore + matchList.get(i).awayScoreOT + matchList.get(i).awayScoreBullits == value)
                        numberEqual++;
                }
            }
        }

        if ((parameter.equals("Голы с учетом ОТ и буллитов"))&&(index.equals("Фора команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeScore + matchList.get(i).homeScoreOT + matchList.get(i).homeScoreBullits - (matchList.get(i).awayScore + matchList.get(i).awayScoreOT + matchList.get(i).awayScoreBullits) + value > 0)
                        numberPlus++;
                    if (matchList.get(i).homeScore + matchList.get(i).homeScoreOT + matchList.get(i).homeScoreBullits - (matchList.get(i).awayScore + matchList.get(i).awayScoreOT + matchList.get(i).awayScoreBullits) + value < 0)
                        numberMinus++;
                    if (matchList.get(i).homeScore + matchList.get(i).homeScoreOT + matchList.get(i).homeScoreBullits - (matchList.get(i).awayScore + matchList.get(i).awayScoreOT + matchList.get(i).awayScoreBullits) + value == 0)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayScore + matchList.get(i).awayScoreOT + matchList.get(i).awayScoreBullits - (matchList.get(i).homeScore + matchList.get(i).homeScoreOT + matchList.get(i).homeScoreBullits) + value > 0)
                        numberPlus++;
                    if (matchList.get(i).awayScore + matchList.get(i).awayScoreOT + matchList.get(i).awayScoreBullits - (matchList.get(i).homeScore + matchList.get(i).homeScoreOT + matchList.get(i).homeScoreBullits) + value < 0)
                        numberMinus++;
                    if (matchList.get(i).awayScore + matchList.get(i).awayScoreOT + matchList.get(i).awayScoreBullits - (matchList.get(i).homeScore + matchList.get(i).homeScoreOT + matchList.get(i).homeScoreBullits) + value == 0)
                        numberEqual++;
                }
            }
            typeOfTable = 2;
        }

        if ((parameter.equals("Голы в первом периоде"))&&(index.equals("Общий тотал"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeScore1stPeriod + matchList.get(i).awayScore1stPeriod > value)
                    numberPlus++;
                if (matchList.get(i).homeScore1stPeriod + matchList.get(i).awayScore1stPeriod < value)
                    numberMinus++;
                if (matchList.get(i).homeScore1stPeriod + matchList.get(i).awayScore1stPeriod == value)
                    numberEqual++;
            }
        }

        if ((parameter.equals("Голы в первом периоде"))&&(index.equals("Инд.тотал команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeScore1stPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).homeScore1stPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).homeScore1stPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayScore1stPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).awayScore1stPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).awayScore1stPeriod == value)
                        numberEqual++;
                }
            }
        }

        if ((parameter.equals("Голы в первом периоде"))&&(index.equals("Инд.тотал соперника"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).awayTeam.equals(teamName)){
                    if (matchList.get(i).homeScore1stPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).homeScore1stPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).homeScore1stPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayScore1stPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).awayScore1stPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).awayScore1stPeriod == value)
                        numberEqual++;
                }
            }
        }

        if ((parameter.equals("Голы в первом периоде"))&&(index.equals("Фора команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeScore1stPeriod - matchList.get(i).awayScore1stPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).homeScore1stPeriod - matchList.get(i).awayScore1stPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).homeScore1stPeriod - matchList.get(i).awayScore1stPeriod + value == 0)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayScore1stPeriod - matchList.get(i).homeScore1stPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).awayScore1stPeriod - matchList.get(i).homeScore1stPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).awayScore1stPeriod - matchList.get(i).homeScore1stPeriod + value == 0)
                        numberEqual++;
                }
            }
            typeOfTable = 2;
        }

        if ((parameter.equals("Голы во втором периоде"))&&(index.equals("Общий тотал"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeScore2ndPeriod + matchList.get(i).awayScore2ndPeriod > value)
                    numberPlus++;
                if (matchList.get(i).homeScore2ndPeriod + matchList.get(i).awayScore2ndPeriod < value)
                    numberMinus++;
                if (matchList.get(i).homeScore2ndPeriod + matchList.get(i).awayScore2ndPeriod == value)
                    numberEqual++;
            }
        }

        if ((parameter.equals("Голы во втором периоде"))&&(index.equals("Инд.тотал команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeScore2ndPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).homeScore2ndPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).homeScore2ndPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayScore2ndPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).awayScore2ndPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).awayScore2ndPeriod == value)
                        numberEqual++;
                }
            }
        }

        if ((parameter.equals("Голы во втором периоде"))&&(index.equals("Инд.тотал соперника"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).awayTeam.equals(teamName)){
                    if (matchList.get(i).homeScore2ndPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).homeScore2ndPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).homeScore2ndPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayScore2ndPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).awayScore2ndPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).awayScore2ndPeriod == value)
                        numberEqual++;
                }
            }
        }

        if ((parameter.equals("Голы во втором периоде"))&&(index.equals("Фора команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeScore2ndPeriod - matchList.get(i).awayScore2ndPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).homeScore2ndPeriod - matchList.get(i).awayScore2ndPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).homeScore2ndPeriod - matchList.get(i).awayScore2ndPeriod + value == 0)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayScore2ndPeriod - matchList.get(i).homeScore2ndPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).awayScore2ndPeriod - matchList.get(i).homeScore2ndPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).awayScore2ndPeriod - matchList.get(i).homeScore2ndPeriod + value == 0)
                        numberEqual++;
                }
            }
            typeOfTable = 2;
        }

        if ((parameter.equals("Голы в третьем периоде"))&&(index.equals("Общий тотал"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeScore3rdPeriod + matchList.get(i).awayScore3rdPeriod > value)
                    numberPlus++;
                if (matchList.get(i).homeScore3rdPeriod + matchList.get(i).awayScore3rdPeriod < value)
                    numberMinus++;
                if (matchList.get(i).homeScore3rdPeriod + matchList.get(i).awayScore3rdPeriod == value)
                    numberEqual++;
            }
        }

        if ((parameter.equals("Голы в третьем периоде"))&&(index.equals("Инд.тотал команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeScore3rdPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).homeScore3rdPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).homeScore3rdPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayScore3rdPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).awayScore3rdPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).awayScore3rdPeriod == value)
                        numberEqual++;
                }
            }
        }

        if ((parameter.equals("Голы в третьем периоде"))&&(index.equals("Инд.тотал соперника"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).awayTeam.equals(teamName)){
                    if (matchList.get(i).homeScore3rdPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).homeScore3rdPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).homeScore3rdPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayScore3rdPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).awayScore3rdPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).awayScore3rdPeriod == value)
                        numberEqual++;
                }
            }
        }

        if ((parameter.equals("Голы в третьем периоде"))&&(index.equals("Фора команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeScore3rdPeriod - matchList.get(i).awayScore3rdPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).homeScore3rdPeriod - matchList.get(i).awayScore3rdPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).homeScore3rdPeriod - matchList.get(i).awayScore3rdPeriod + value == 0)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayScore3rdPeriod - matchList.get(i).homeScore3rdPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).awayScore3rdPeriod - matchList.get(i).homeScore3rdPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).awayScore3rdPeriod - matchList.get(i).homeScore3rdPeriod + value == 0)
                        numberEqual++;
                }
            }
            typeOfTable = 2;
        }

        if ((parameter.equals("Броски в створ (осн.время)"))&&(index.equals("Общий тотал"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeShotsOnTarget + matchList.get(i).awayShotsOnTarget > value)
                    numberPlus++;
                if (matchList.get(i).homeShotsOnTarget + matchList.get(i).awayShotsOnTarget < value)
                    numberMinus++;
                if (matchList.get(i).homeShotsOnTarget + matchList.get(i).awayShotsOnTarget == value)
                    numberEqual++;
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Броски в створ (осн.время)"))&&(index.equals("Инд.тотал команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeShotsOnTarget > value)
                        numberPlus++;
                    if (matchList.get(i).homeShotsOnTarget < value)
                        numberMinus++;
                    if (matchList.get(i).homeShotsOnTarget == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayShotsOnTarget > value)
                        numberPlus++;
                    if (matchList.get(i).awayShotsOnTarget < value)
                        numberMinus++;
                    if (matchList.get(i).awayShotsOnTarget == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Броски в створ (осн.время)"))&&(index.equals("Инд.тотал соперника"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).awayTeam.equals(teamName)){
                    if (matchList.get(i).homeShotsOnTarget > value)
                        numberPlus++;
                    if (matchList.get(i).homeShotsOnTarget < value)
                        numberMinus++;
                    if (matchList.get(i).homeShotsOnTarget == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayShotsOnTarget > value)
                        numberPlus++;
                    if (matchList.get(i).awayShotsOnTarget < value)
                        numberMinus++;
                    if (matchList.get(i).awayShotsOnTarget == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Броски в створ (осн.время)"))&&(index.equals("Фора команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeShotsOnTarget - matchList.get(i).awayShotsOnTarget + value > 0)
                        numberPlus++;
                    if (matchList.get(i).homeShotsOnTarget - matchList.get(i).awayShotsOnTarget + value < 0)
                        numberMinus++;
                    if (matchList.get(i).homeShotsOnTarget - matchList.get(i).awayShotsOnTarget + value == 0)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayShotsOnTarget - matchList.get(i).homeShotsOnTarget + value > 0)
                        numberPlus++;
                    if (matchList.get(i).awayShotsOnTarget - matchList.get(i).homeShotsOnTarget + value < 0)
                        numberMinus++;
                    if (matchList.get(i).awayShotsOnTarget - matchList.get(i).homeShotsOnTarget + value == 0)
                        numberEqual++;
                }
            }
            typeOfTable = 2;
        }

        if ((parameter.equals("Броски в створ 1 пер."))&&(index.equals("Общий тотал"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeShotsOnTarget1stPeriod + matchList.get(i).awayShotsOnTarget1stPeriod > value)
                    numberPlus++;
                if (matchList.get(i).homeShotsOnTarget1stPeriod + matchList.get(i).awayShotsOnTarget1stPeriod < value)
                    numberMinus++;
                if (matchList.get(i).homeShotsOnTarget1stPeriod + matchList.get(i).awayShotsOnTarget1stPeriod == value)
                    numberEqual++;
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Броски в створ 1 пер."))&&(index.equals("Инд.тотал команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeShotsOnTarget1stPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).homeShotsOnTarget1stPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).homeShotsOnTarget1stPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayShotsOnTarget1stPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).awayShotsOnTarget1stPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).awayShotsOnTarget1stPeriod == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Броски в створ 1 пер."))&&(index.equals("Инд.тотал соперника"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).awayTeam.equals(teamName)){
                    if (matchList.get(i).homeShotsOnTarget1stPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).homeShotsOnTarget1stPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).homeShotsOnTarget1stPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayShotsOnTarget1stPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).awayShotsOnTarget1stPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).awayShotsOnTarget1stPeriod == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Броски в створ 1 пер."))&&(index.equals("Фора команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeShotsOnTarget1stPeriod - matchList.get(i).awayShotsOnTarget1stPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).homeShotsOnTarget1stPeriod - matchList.get(i).awayShotsOnTarget1stPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).homeShotsOnTarget1stPeriod - matchList.get(i).awayShotsOnTarget1stPeriod + value == 0)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayShotsOnTarget1stPeriod - matchList.get(i).homeShotsOnTarget1stPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).awayShotsOnTarget1stPeriod - matchList.get(i).homeShotsOnTarget1stPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).awayShotsOnTarget1stPeriod - matchList.get(i).homeShotsOnTarget1stPeriod + value == 0)
                        numberEqual++;
                }
            }
            typeOfTable = 2;
        }

        if ((parameter.equals("Броски в створ 2 пер."))&&(index.equals("Общий тотал"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeShotsOnTarget2ndPeriod + matchList.get(i).awayShotsOnTarget2ndPeriod > value)
                    numberPlus++;
                if (matchList.get(i).homeShotsOnTarget2ndPeriod + matchList.get(i).awayShotsOnTarget2ndPeriod < value)
                    numberMinus++;
                if (matchList.get(i).homeShotsOnTarget2ndPeriod + matchList.get(i).awayShotsOnTarget2ndPeriod == value)
                    numberEqual++;
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Броски в створ 2 пер."))&&(index.equals("Инд.тотал команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeShotsOnTarget2ndPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).homeShotsOnTarget2ndPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).homeShotsOnTarget2ndPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayShotsOnTarget2ndPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).awayShotsOnTarget2ndPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).awayShotsOnTarget2ndPeriod == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Броски в створ 2 пер."))&&(index.equals("Инд.тотал соперника"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).awayTeam.equals(teamName)){
                    if (matchList.get(i).homeShotsOnTarget2ndPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).homeShotsOnTarget2ndPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).homeShotsOnTarget2ndPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayShotsOnTarget2ndPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).awayShotsOnTarget2ndPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).awayShotsOnTarget2ndPeriod == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Броски в створ 2 пер."))&&(index.equals("Фора команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeShotsOnTarget2ndPeriod - matchList.get(i).awayShotsOnTarget2ndPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).homeShotsOnTarget2ndPeriod - matchList.get(i).awayShotsOnTarget2ndPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).homeShotsOnTarget2ndPeriod - matchList.get(i).awayShotsOnTarget2ndPeriod + value == 0)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayShotsOnTarget2ndPeriod - matchList.get(i).homeShotsOnTarget2ndPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).awayShotsOnTarget2ndPeriod - matchList.get(i).homeShotsOnTarget2ndPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).awayShotsOnTarget2ndPeriod - matchList.get(i).homeShotsOnTarget2ndPeriod + value == 0)
                        numberEqual++;
                }
            }
            typeOfTable = 2;
        }

        if ((parameter.equals("Броски в створ 3 пер."))&&(index.equals("Общий тотал"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeShotsOnTarget3rdPeriod + matchList.get(i).awayShotsOnTarget3rdPeriod > value)
                    numberPlus++;
                if (matchList.get(i).homeShotsOnTarget3rdPeriod + matchList.get(i).awayShotsOnTarget3rdPeriod < value)
                    numberMinus++;
                if (matchList.get(i).homeShotsOnTarget3rdPeriod + matchList.get(i).awayShotsOnTarget3rdPeriod == value)
                    numberEqual++;
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Броски в створ 3 пер."))&&(index.equals("Инд.тотал команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeShotsOnTarget3rdPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).homeShotsOnTarget3rdPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).homeShotsOnTarget3rdPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayShotsOnTarget3rdPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).awayShotsOnTarget3rdPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).awayShotsOnTarget3rdPeriod == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Броски в створ 3 пер."))&&(index.equals("Инд.тотал соперника"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).awayTeam.equals(teamName)){
                    if (matchList.get(i).homeShotsOnTarget3rdPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).homeShotsOnTarget3rdPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).homeShotsOnTarget3rdPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayShotsOnTarget3rdPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).awayShotsOnTarget3rdPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).awayShotsOnTarget3rdPeriod == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Броски в створ 3 пер."))&&(index.equals("Фора команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeShotsOnTarget3rdPeriod - matchList.get(i).awayShotsOnTarget3rdPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).homeShotsOnTarget3rdPeriod - matchList.get(i).awayShotsOnTarget3rdPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).homeShotsOnTarget3rdPeriod - matchList.get(i).awayShotsOnTarget3rdPeriod + value == 0)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayShotsOnTarget3rdPeriod - matchList.get(i).homeShotsOnTarget3rdPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).awayShotsOnTarget3rdPeriod - matchList.get(i).homeShotsOnTarget3rdPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).awayShotsOnTarget3rdPeriod - matchList.get(i).homeShotsOnTarget3rdPeriod + value == 0)
                        numberEqual++;
                }
            }
            typeOfTable = 2;
        }

        if ((parameter.equals("Кол-во двухмин. уд."))&&(index.equals("Общий тотал"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).home2MinPenalties + matchList.get(i).away2MinPenalties > value)
                    numberPlus++;
                if (matchList.get(i).home2MinPenalties + matchList.get(i).away2MinPenalties < value)
                    numberMinus++;
                if (matchList.get(i).home2MinPenalties + matchList.get(i).away2MinPenalties == value)
                    numberEqual++;
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Кол-во двухмин. уд."))&&(index.equals("Инд.тотал команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).home2MinPenalties > value)
                        numberPlus++;
                    if (matchList.get(i).home2MinPenalties < value)
                        numberMinus++;
                    if (matchList.get(i).home2MinPenalties == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).away2MinPenalties > value)
                        numberPlus++;
                    if (matchList.get(i).away2MinPenalties < value)
                        numberMinus++;
                    if (matchList.get(i).away2MinPenalties == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Кол-во двухмин. уд."))&&(index.equals("Инд.тотал соперника"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).awayTeam.equals(teamName)){
                    if (matchList.get(i).home2MinPenalties > value)
                        numberPlus++;
                    if (matchList.get(i).home2MinPenalties < value)
                        numberMinus++;
                    if (matchList.get(i).home2MinPenalties == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).away2MinPenalties > value)
                        numberPlus++;
                    if (matchList.get(i).away2MinPenalties < value)
                        numberMinus++;
                    if (matchList.get(i).away2MinPenalties == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Кол-во двухмин. уд."))&&(index.equals("Фора команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).home2MinPenalties - matchList.get(i).away2MinPenalties + value > 0)
                        numberPlus++;
                    if (matchList.get(i).home2MinPenalties - matchList.get(i).away2MinPenalties + value < 0)
                        numberMinus++;
                    if (matchList.get(i).home2MinPenalties - matchList.get(i).away2MinPenalties + value == 0)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).away2MinPenalties - matchList.get(i).home2MinPenalties + value > 0)
                        numberPlus++;
                    if (matchList.get(i).away2MinPenalties - matchList.get(i).home2MinPenalties + value < 0)
                        numberMinus++;
                    if (matchList.get(i).away2MinPenalties - matchList.get(i).home2MinPenalties + value == 0)
                        numberEqual++;
                }
            }
            typeOfTable = 2;
        }

        if ((parameter.equals("Кол-во двухмин. уд. 1 пер."))&&(index.equals("Общий тотал"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).home2MinPenalties1stPeriod + matchList.get(i).away2MinPenalties1stPeriod > value)
                    numberPlus++;
                if (matchList.get(i).home2MinPenalties1stPeriod + matchList.get(i).away2MinPenalties1stPeriod < value)
                    numberMinus++;
                if (matchList.get(i).home2MinPenalties1stPeriod + matchList.get(i).away2MinPenalties1stPeriod == value)
                    numberEqual++;
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Кол-во двухмин. уд. 1 пер."))&&(index.equals("Инд.тотал команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).home2MinPenalties1stPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).home2MinPenalties1stPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).home2MinPenalties1stPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).away2MinPenalties1stPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).away2MinPenalties1stPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).away2MinPenalties1stPeriod == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Кол-во двухмин. уд. 1 пер."))&&(index.equals("Инд.тотал соперника"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).awayTeam.equals(teamName)){
                    if (matchList.get(i).home2MinPenalties1stPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).home2MinPenalties1stPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).home2MinPenalties1stPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).away2MinPenalties1stPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).away2MinPenalties1stPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).away2MinPenalties1stPeriod == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Кол-во двухмин. уд. 1 пер."))&&(index.equals("Фора команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).home2MinPenalties1stPeriod - matchList.get(i).away2MinPenalties1stPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).home2MinPenalties1stPeriod - matchList.get(i).away2MinPenalties1stPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).home2MinPenalties1stPeriod - matchList.get(i).away2MinPenalties1stPeriod + value == 0)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).away2MinPenalties1stPeriod - matchList.get(i).home2MinPenalties1stPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).away2MinPenalties1stPeriod - matchList.get(i).home2MinPenalties1stPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).away2MinPenalties1stPeriod - matchList.get(i).home2MinPenalties1stPeriod + value == 0)
                        numberEqual++;
                }
            }
            typeOfTable = 2;
        }

        if ((parameter.equals("Кол-во двухмин. уд. 2 пер."))&&(index.equals("Общий тотал"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).home2MinPenalties2ndPeriod + matchList.get(i).away2MinPenalties2ndPeriod > value)
                    numberPlus++;
                if (matchList.get(i).home2MinPenalties2ndPeriod + matchList.get(i).away2MinPenalties2ndPeriod < value)
                    numberMinus++;
                if (matchList.get(i).home2MinPenalties2ndPeriod + matchList.get(i).away2MinPenalties2ndPeriod == value)
                    numberEqual++;
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Кол-во двухмин. уд. 2 пер."))&&(index.equals("Инд.тотал команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).home2MinPenalties2ndPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).home2MinPenalties2ndPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).home2MinPenalties2ndPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).away2MinPenalties2ndPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).away2MinPenalties2ndPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).away2MinPenalties2ndPeriod == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Кол-во двухмин. уд. 2 пер."))&&(index.equals("Инд.тотал соперника"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).awayTeam.equals(teamName)){
                    if (matchList.get(i).home2MinPenalties2ndPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).home2MinPenalties2ndPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).home2MinPenalties2ndPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).away2MinPenalties2ndPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).away2MinPenalties2ndPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).away2MinPenalties2ndPeriod == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Кол-во двухмин. уд. 2 пер."))&&(index.equals("Фора команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).home2MinPenalties2ndPeriod - matchList.get(i).away2MinPenalties2ndPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).home2MinPenalties2ndPeriod - matchList.get(i).away2MinPenalties2ndPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).home2MinPenalties2ndPeriod - matchList.get(i).away2MinPenalties2ndPeriod + value == 0)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).away2MinPenalties2ndPeriod - matchList.get(i).home2MinPenalties2ndPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).away2MinPenalties2ndPeriod - matchList.get(i).home2MinPenalties2ndPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).away2MinPenalties2ndPeriod - matchList.get(i).home2MinPenalties2ndPeriod + value == 0)
                        numberEqual++;
                }
            }
            typeOfTable = 2;
        }

        if ((parameter.equals("Кол-во двухмин. уд. 3 пер."))&&(index.equals("Общий тотал"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).home2MinPenalties3rdPeriod + matchList.get(i).away2MinPenalties3rdPeriod > value)
                    numberPlus++;
                if (matchList.get(i).home2MinPenalties3rdPeriod + matchList.get(i).away2MinPenalties3rdPeriod < value)
                    numberMinus++;
                if (matchList.get(i).home2MinPenalties3rdPeriod + matchList.get(i).away2MinPenalties3rdPeriod == value)
                    numberEqual++;
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Кол-во двухмин. уд. 3 пер."))&&(index.equals("Инд.тотал команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).home2MinPenalties3rdPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).home2MinPenalties3rdPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).home2MinPenalties3rdPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).away2MinPenalties3rdPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).away2MinPenalties3rdPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).away2MinPenalties3rdPeriod == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Кол-во двухмин. уд. 3 пер."))&&(index.equals("Инд.тотал соперника"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).awayTeam.equals(teamName)){
                    if (matchList.get(i).home2MinPenalties3rdPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).home2MinPenalties3rdPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).home2MinPenalties3rdPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).away2MinPenalties3rdPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).away2MinPenalties3rdPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).away2MinPenalties3rdPeriod == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Кол-во двухмин. уд. 3 пер."))&&(index.equals("Фора команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).home2MinPenalties3rdPeriod - matchList.get(i).away2MinPenalties3rdPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).home2MinPenalties3rdPeriod - matchList.get(i).away2MinPenalties3rdPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).home2MinPenalties3rdPeriod - matchList.get(i).away2MinPenalties3rdPeriod + value == 0)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).away2MinPenalties3rdPeriod - matchList.get(i).home2MinPenalties3rdPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).away2MinPenalties3rdPeriod - matchList.get(i).home2MinPenalties3rdPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).away2MinPenalties3rdPeriod - matchList.get(i).home2MinPenalties3rdPeriod + value == 0)
                        numberEqual++;
                }
            }
            typeOfTable = 2;
        }

        if ((parameter.equals("Штр. время"))&&(index.equals("Общий тотал"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homePenaltyMinutes + matchList.get(i).awayPenaltyMinutes > value)
                    numberPlus++;
                if (matchList.get(i).homePenaltyMinutes + matchList.get(i).awayPenaltyMinutes < value)
                    numberMinus++;
                if (matchList.get(i).homePenaltyMinutes + matchList.get(i).awayPenaltyMinutes == value)
                    numberEqual++;
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Штр. время"))&&(index.equals("Инд.тотал команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homePenaltyMinutes > value)
                        numberPlus++;
                    if (matchList.get(i).homePenaltyMinutes < value)
                        numberMinus++;
                    if (matchList.get(i).homePenaltyMinutes == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayPenaltyMinutes > value)
                        numberPlus++;
                    if (matchList.get(i).awayPenaltyMinutes < value)
                        numberMinus++;
                    if (matchList.get(i).awayPenaltyMinutes == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Штр. время"))&&(index.equals("Инд.тотал соперника"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).awayTeam.equals(teamName)){
                    if (matchList.get(i).homePenaltyMinutes > value)
                        numberPlus++;
                    if (matchList.get(i).homePenaltyMinutes < value)
                        numberMinus++;
                    if (matchList.get(i).homePenaltyMinutes == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayPenaltyMinutes > value)
                        numberPlus++;
                    if (matchList.get(i).awayPenaltyMinutes < value)
                        numberMinus++;
                    if (matchList.get(i).awayPenaltyMinutes == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Штр. время"))&&(index.equals("Фора команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homePenaltyMinutes - matchList.get(i).awayPenaltyMinutes + value > 0)
                        numberPlus++;
                    if (matchList.get(i).homePenaltyMinutes - matchList.get(i).awayPenaltyMinutes + value < 0)
                        numberMinus++;
                    if (matchList.get(i).homePenaltyMinutes - matchList.get(i).awayPenaltyMinutes + value == 0)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayPenaltyMinutes - matchList.get(i).homePenaltyMinutes + value > 0)
                        numberPlus++;
                    if (matchList.get(i).awayPenaltyMinutes - matchList.get(i).homePenaltyMinutes + value < 0)
                        numberMinus++;
                    if (matchList.get(i).awayPenaltyMinutes - matchList.get(i).homePenaltyMinutes + value == 0)
                        numberEqual++;
                }
            }
            typeOfTable = 2;
        }

        if ((parameter.equals("Штр. время 1 пер."))&&(index.equals("Общий тотал"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homePenaltyMinutes1stPeriod + matchList.get(i).awayPenaltyMinutes1stPeriod > value)
                    numberPlus++;
                if (matchList.get(i).homePenaltyMinutes1stPeriod + matchList.get(i).awayPenaltyMinutes1stPeriod < value)
                    numberMinus++;
                if (matchList.get(i).homePenaltyMinutes1stPeriod + matchList.get(i).awayPenaltyMinutes1stPeriod == value)
                    numberEqual++;
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Штр. время 1 пер."))&&(index.equals("Инд.тотал команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homePenaltyMinutes1stPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).homePenaltyMinutes1stPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).homePenaltyMinutes1stPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayPenaltyMinutes1stPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).awayPenaltyMinutes1stPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).awayPenaltyMinutes1stPeriod == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Штр. время 1 пер."))&&(index.equals("Инд.тотал соперника"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).awayTeam.equals(teamName)){
                    if (matchList.get(i).homePenaltyMinutes1stPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).homePenaltyMinutes1stPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).homePenaltyMinutes1stPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayPenaltyMinutes1stPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).awayPenaltyMinutes1stPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).awayPenaltyMinutes1stPeriod == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Штр. время 1 пер."))&&(index.equals("Фора команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homePenaltyMinutes1stPeriod - matchList.get(i).awayPenaltyMinutes1stPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).homePenaltyMinutes1stPeriod - matchList.get(i).awayPenaltyMinutes1stPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).homePenaltyMinutes1stPeriod - matchList.get(i).awayPenaltyMinutes1stPeriod + value == 0)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayPenaltyMinutes1stPeriod - matchList.get(i).homePenaltyMinutes1stPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).awayPenaltyMinutes1stPeriod - matchList.get(i).homePenaltyMinutes1stPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).awayPenaltyMinutes1stPeriod - matchList.get(i).homePenaltyMinutes1stPeriod + value == 0)
                        numberEqual++;
                }
            }
            typeOfTable = 2;
        }

        if ((parameter.equals("Штр. время 2 пер."))&&(index.equals("Общий тотал"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homePenaltyMinutes2ndPeriod + matchList.get(i).awayPenaltyMinutes2ndPeriod > value)
                    numberPlus++;
                if (matchList.get(i).homePenaltyMinutes2ndPeriod + matchList.get(i).awayPenaltyMinutes2ndPeriod < value)
                    numberMinus++;
                if (matchList.get(i).homePenaltyMinutes2ndPeriod + matchList.get(i).awayPenaltyMinutes2ndPeriod == value)
                    numberEqual++;
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Штр. время 2 пер."))&&(index.equals("Инд.тотал команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homePenaltyMinutes2ndPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).homePenaltyMinutes2ndPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).homePenaltyMinutes2ndPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayPenaltyMinutes2ndPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).awayPenaltyMinutes2ndPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).awayPenaltyMinutes2ndPeriod == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Штр. время 2 пер."))&&(index.equals("Инд.тотал соперника"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).awayTeam.equals(teamName)){
                    if (matchList.get(i).homePenaltyMinutes2ndPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).homePenaltyMinutes2ndPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).homePenaltyMinutes2ndPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayPenaltyMinutes2ndPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).awayPenaltyMinutes2ndPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).awayPenaltyMinutes2ndPeriod == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Штр. время 2 пер."))&&(index.equals("Фора команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homePenaltyMinutes2ndPeriod - matchList.get(i).awayPenaltyMinutes2ndPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).homePenaltyMinutes2ndPeriod - matchList.get(i).awayPenaltyMinutes2ndPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).homePenaltyMinutes2ndPeriod - matchList.get(i).awayPenaltyMinutes2ndPeriod + value == 0)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayPenaltyMinutes2ndPeriod - matchList.get(i).homePenaltyMinutes2ndPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).awayPenaltyMinutes2ndPeriod - matchList.get(i).homePenaltyMinutes2ndPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).awayPenaltyMinutes2ndPeriod - matchList.get(i).homePenaltyMinutes2ndPeriod + value == 0)
                        numberEqual++;
                }
            }
            typeOfTable = 2;
        }

        if ((parameter.equals("Штр. время 3 пер."))&&(index.equals("Общий тотал"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homePenaltyMinutes3rdPeriod + matchList.get(i).awayPenaltyMinutes3rdPeriod > value)
                    numberPlus++;
                if (matchList.get(i).homePenaltyMinutes3rdPeriod + matchList.get(i).awayPenaltyMinutes3rdPeriod < value)
                    numberMinus++;
                if (matchList.get(i).homePenaltyMinutes3rdPeriod + matchList.get(i).awayPenaltyMinutes3rdPeriod == value)
                    numberEqual++;
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Штр. время 3 пер."))&&(index.equals("Инд.тотал команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homePenaltyMinutes3rdPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).homePenaltyMinutes3rdPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).homePenaltyMinutes3rdPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayPenaltyMinutes3rdPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).awayPenaltyMinutes3rdPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).awayPenaltyMinutes3rdPeriod == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Штр. время 3 пер."))&&(index.equals("Инд.тотал соперника"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).awayTeam.equals(teamName)){
                    if (matchList.get(i).homePenaltyMinutes3rdPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).homePenaltyMinutes3rdPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).homePenaltyMinutes3rdPeriod == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayPenaltyMinutes3rdPeriod > value)
                        numberPlus++;
                    if (matchList.get(i).awayPenaltyMinutes3rdPeriod < value)
                        numberMinus++;
                    if (matchList.get(i).awayPenaltyMinutes3rdPeriod == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Штр. время 3 пер."))&&(index.equals("Фора команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homePenaltyMinutes3rdPeriod - matchList.get(i).awayPenaltyMinutes3rdPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).homePenaltyMinutes3rdPeriod - matchList.get(i).awayPenaltyMinutes3rdPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).homePenaltyMinutes3rdPeriod - matchList.get(i).awayPenaltyMinutes3rdPeriod + value == 0)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayPenaltyMinutes3rdPeriod - matchList.get(i).homePenaltyMinutes3rdPeriod + value > 0)
                        numberPlus++;
                    if (matchList.get(i).awayPenaltyMinutes3rdPeriod - matchList.get(i).homePenaltyMinutes3rdPeriod + value < 0)
                        numberMinus++;
                    if (matchList.get(i).awayPenaltyMinutes3rdPeriod - matchList.get(i).homePenaltyMinutes3rdPeriod + value == 0)
                        numberEqual++;
                }
            }
            typeOfTable = 2;
        }

        if ((parameter.equals("Силовые приемы"))&&(index.equals("Общий тотал"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeHits + matchList.get(i).awayHits > value)
                    numberPlus++;
                if (matchList.get(i).homeHits + matchList.get(i).awayHits < value)
                    numberMinus++;
                if (matchList.get(i).homeHits + matchList.get(i).awayHits == value)
                    numberEqual++;
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Силовые приемы"))&&(index.equals("Инд.тотал команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeHits > value)
                        numberPlus++;
                    if (matchList.get(i).homeHits < value)
                        numberMinus++;
                    if (matchList.get(i).homeHits == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayHits > value)
                        numberPlus++;
                    if (matchList.get(i).awayHits < value)
                        numberMinus++;
                    if (matchList.get(i).awayHits == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Силовые приемы"))&&(index.equals("Инд.тотал соперника"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).awayTeam.equals(teamName)){
                    if (matchList.get(i).homeHits > value)
                        numberPlus++;
                    if (matchList.get(i).homeHits < value)
                        numberMinus++;
                    if (matchList.get(i).homeHits == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayHits > value)
                        numberPlus++;
                    if (matchList.get(i).awayHits < value)
                        numberMinus++;
                    if (matchList.get(i).awayHits == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Силовые приемы"))&&(index.equals("Фора команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeHits - matchList.get(i).awayHits + value > 0)
                        numberPlus++;
                    if (matchList.get(i).homeHits - matchList.get(i).awayHits + value < 0)
                        numberMinus++;
                    if (matchList.get(i).homeHits - matchList.get(i).awayHits + value == 0)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayHits - matchList.get(i).homeHits + value > 0)
                        numberPlus++;
                    if (matchList.get(i).awayHits - matchList.get(i).homeHits + value < 0)
                        numberMinus++;
                    if (matchList.get(i).awayHits - matchList.get(i).homeHits + value == 0)
                        numberEqual++;
                }
            }
            typeOfTable = 2;
        }

        if ((parameter.equals("Вбрасывания"))&&(index.equals("Общий тотал"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeFaceoffsWon + matchList.get(i).awayFaceoffsWon > value)
                    numberPlus++;
                if (matchList.get(i).homeFaceoffsWon + matchList.get(i).awayFaceoffsWon < value)
                    numberMinus++;
                if (matchList.get(i).homeFaceoffsWon + matchList.get(i).awayFaceoffsWon == value)
                    numberEqual++;
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Вбрасывания"))&&(index.equals("Инд.тотал команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeFaceoffsWon > value)
                        numberPlus++;
                    if (matchList.get(i).homeFaceoffsWon < value)
                        numberMinus++;
                    if (matchList.get(i).homeFaceoffsWon == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayFaceoffsWon > value)
                        numberPlus++;
                    if (matchList.get(i).awayFaceoffsWon < value)
                        numberMinus++;
                    if (matchList.get(i).awayFaceoffsWon == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Вбрасывания"))&&(index.equals("Инд.тотал соперника"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).awayTeam.equals(teamName)){
                    if (matchList.get(i).homeFaceoffsWon > value)
                        numberPlus++;
                    if (matchList.get(i).homeFaceoffsWon < value)
                        numberMinus++;
                    if (matchList.get(i).homeFaceoffsWon == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayFaceoffsWon > value)
                        numberPlus++;
                    if (matchList.get(i).awayFaceoffsWon < value)
                        numberMinus++;
                    if (matchList.get(i).awayFaceoffsWon == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Вбрасывания"))&&(index.equals("Фора команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeFaceoffsWon - matchList.get(i).awayFaceoffsWon + value > 0)
                        numberPlus++;
                    if (matchList.get(i).homeFaceoffsWon - matchList.get(i).awayFaceoffsWon + value < 0)
                        numberMinus++;
                    if (matchList.get(i).homeFaceoffsWon - matchList.get(i).awayFaceoffsWon + value == 0)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayFaceoffsWon - matchList.get(i).homeFaceoffsWon + value > 0)
                        numberPlus++;
                    if (matchList.get(i).awayFaceoffsWon - matchList.get(i).homeFaceoffsWon + value < 0)
                        numberMinus++;
                    if (matchList.get(i).awayFaceoffsWon - matchList.get(i).homeFaceoffsWon + value == 0)
                        numberEqual++;
                }
            }
            typeOfTable = 2;
        }

        if ((parameter.equals("Блокированные броски"))&&(index.equals("Общий тотал"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeBlockedShots + matchList.get(i).awayBlockedShots > value)
                    numberPlus++;
                if (matchList.get(i).homeBlockedShots + matchList.get(i).awayBlockedShots < value)
                    numberMinus++;
                if (matchList.get(i).homeBlockedShots + matchList.get(i).awayBlockedShots == value)
                    numberEqual++;
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Блокированные броски"))&&(index.equals("Инд.тотал команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeBlockedShots > value)
                        numberPlus++;
                    if (matchList.get(i).homeBlockedShots < value)
                        numberMinus++;
                    if (matchList.get(i).homeBlockedShots == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayBlockedShots > value)
                        numberPlus++;
                    if (matchList.get(i).awayBlockedShots < value)
                        numberMinus++;
                    if (matchList.get(i).awayBlockedShots == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Блокированные броски"))&&(index.equals("Инд.тотал соперника"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).awayTeam.equals(teamName)){
                    if (matchList.get(i).homeBlockedShots > value)
                        numberPlus++;
                    if (matchList.get(i).homeBlockedShots < value)
                        numberMinus++;
                    if (matchList.get(i).homeBlockedShots == value)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayBlockedShots > value)
                        numberPlus++;
                    if (matchList.get(i).awayBlockedShots < value)
                        numberMinus++;
                    if (matchList.get(i).awayBlockedShots == value)
                        numberEqual++;
                }
            }
            typeOfTable = 1;
        }

        if ((parameter.equals("Блокированные броски"))&&(index.equals("Фора команды"))){
            for (int i=0; i<numberOfMatches; i++){
                if (matchList.get(i).homeTeam.equals(teamName)){
                    if (matchList.get(i).homeBlockedShots - matchList.get(i).awayBlockedShots + value > 0)
                        numberPlus++;
                    if (matchList.get(i).homeBlockedShots - matchList.get(i).awayBlockedShots + value < 0)
                        numberMinus++;
                    if (matchList.get(i).homeBlockedShots - matchList.get(i).awayBlockedShots + value == 0)
                        numberEqual++;
                }
                else {
                    if (matchList.get(i).awayBlockedShots - matchList.get(i).homeBlockedShots + value > 0)
                        numberPlus++;
                    if (matchList.get(i).awayBlockedShots - matchList.get(i).homeBlockedShots + value < 0)
                        numberMinus++;
                    if (matchList.get(i).awayBlockedShots - matchList.get(i).homeBlockedShots + value == 0)
                        numberEqual++;
                }
            }
            typeOfTable = 2;
        }


        morePercent =  MyMath.round(100 * (double) numberPlus/numberOfMatches, 2);
        lessPercent =  MyMath.round(100 * (double) numberMinus/numberOfMatches, 2);
        equalPercent = MyMath.round(100 * (double) numberEqual/numberOfMatches, 2);




        String[] heads = new String[0];
        Object[][] data = new Object[0][];
        if (typeOfTable == 1){
            heads = new String[]{"Больше", "Меньше", "Ровно", "% больше", "% меньше", "% ровно"};
            data = new Object[][]{
                    {String.valueOf(numberPlus), String.valueOf(numberMinus), String.valueOf(numberEqual), String.valueOf(morePercent), String.valueOf(lessPercent), String.valueOf(equalPercent)}
            };
        }
        if (typeOfTable == 2){
            heads = new String[]{"Прошла", "Не прошла", "Возврат", "% прохода", "% непрохода", "% возврата"};
            data = new Object[][]{
                    {String.valueOf(numberPlus), String.valueOf(numberMinus), String.valueOf(numberEqual), String.valueOf(morePercent), String.valueOf(lessPercent), String.valueOf(equalPercent)}
            };
        }
        return new JTable(data, heads);
    }
}
