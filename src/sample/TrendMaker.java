package sample;

import java.awt.*;
import java.util.ArrayList;

public class TrendMaker {
    Settings settings = Settings.getSettingsFromFile();
    int percentBorder = Integer.parseInt(settings.trendPercent);
    ArrayList<String> listOfTrends = new ArrayList<>();
    ArrayList<String> resultList = new ArrayList<>();
    double tmSOT = 65.5;
    double tbSOT = 55.5;
    double itmSOT = 32.5;
    double itbSOT = 27.5;

    double tmSOT1per = 20.5;
    double tbSOT1per = 20.5;
    double itmSOT1per = 10.5;
    double itbSOT1per = 10.5;

    double tmPenTime = 20.5;
    double tbPenTime = 12.5;
    double itmPenTime = 14.5;
    double itbPenTime = 6.5;

    public TrendMaker(String parameter, String league){
        switch (parameter){
            case "all":{
                this.listOfTrends.add("ТМ(5.5)");
                this.listOfTrends.add("ТМ(4.5)");
                this.listOfTrends.add("ТБ(4.5)");
                this.listOfTrends.add("ТБ(5.5)");
                this.listOfTrends.add("Обе забьют >1.5 - ДА");
                this.listOfTrends.add("Обе забьют >1.5 - НЕТ");
                this.listOfTrends.add("Гол в каждом периоде - ДА");
                this.listOfTrends.add("Гол в каждом периоде - НЕТ");
                this.listOfTrends.add("Заб. в 1 пер.");
                this.listOfTrends.add("Проп. в 1 пер.");
                this.listOfTrends.add("ТБ(1.5) в 1 пер.");
                this.listOfTrends.add("ТБ(2) в 1 пер.");
                this.listOfTrends.add("ТM(1.5) в 1 пер.");
                this.listOfTrends.add("ТM(2) в 1 пер.");
                this.listOfTrends.add("Обе забьют - ДА в 1 пер.");
                this.listOfTrends.add("Обе забьют - НЕТ в 1 пер.");
                this.listOfTrends.add("Заб. в 2 пер.");
                this.listOfTrends.add("Проп. в 2 пер.");
                this.listOfTrends.add("ТБ(1.5) в 2 пер.");
                this.listOfTrends.add("ТБ(2) в 2 пер.");
                this.listOfTrends.add("ТM(1.5) в 2 пер.");
                this.listOfTrends.add("ТМ(2) в 2 пер.");
                this.listOfTrends.add("Обе забьют - ДА в 2 пер.");
                this.listOfTrends.add("Обе забьют - НЕТ в 2 пер.");
                this.listOfTrends.add("Заб. в 3 пер.");
                this.listOfTrends.add("Проп. в 3 пер.");
                this.listOfTrends.add("ТБ(1.5) в 3 пер.");
                this.listOfTrends.add("ТБ(2) в 3 пер.");
                this.listOfTrends.add("ТM(1.5) в 3 пер.");
                this.listOfTrends.add("ТМ(2) в 3 пер.");
                this.listOfTrends.add("Обе забьют - ДА в 3 пер.");
                this.listOfTrends.add("Обе забьют - НЕТ в 3 пер.");
                this.listOfTrends.add("БрВС: ТМ");
                this.listOfTrends.add("БрВС: ТБ");
                this.listOfTrends.add("БрВС: Фора команды");
                this.listOfTrends.add("БрВС: Фора соперника");
                this.listOfTrends.add("БрВС: ИТБ команды");
                this.listOfTrends.add("БрВС: ИТМ команды");
                this.listOfTrends.add("БрВС: ИТБ соперника");
                this.listOfTrends.add("БрВС: ИТМ соперника");
                this.listOfTrends.add("БрВС 1пер: ТМ");
                this.listOfTrends.add("БрВС 1пер: ТБ");
                this.listOfTrends.add("БрВС 1пер: Фора команды");
                this.listOfTrends.add("БрВС 1пер: Фора соперника");
                this.listOfTrends.add("БрВС 1пер: ИТБ команды");
                this.listOfTrends.add("БрВС 1пер: ИТМ команды");
                this.listOfTrends.add("БрВС 1пер: ИТБ соперника");
                this.listOfTrends.add("БрВС 1пер: ИТМ соперника");
                this.listOfTrends.add("ШТР.ВР: ТМ");
                this.listOfTrends.add("ШТР.ВР: ТБ");
                this.listOfTrends.add("ШТР.ВР: Фора команды");
                this.listOfTrends.add("ШТР.ВР: Фора соперника");
                this.listOfTrends.add("ШТР.ВР: ИТБ команды");
                this.listOfTrends.add("ШТР.ВР: ИТМ команды");
                this.listOfTrends.add("ШТР.ВР: ИТБ соперника");
                this.listOfTrends.add("ШТР.ВР: ИТМ соперника");
                //this.listOfTrends.add("ШТР.ВР 1пер: Фора команды");
                //this.listOfTrends.add("ШТР.ВР 1пер: Фора соперника");
                break;
            }
            case "Голы":{
                this.listOfTrends.add("ТМ(5.5)");
                this.listOfTrends.add("ТМ(4.5)");
                this.listOfTrends.add("ТБ(4.5)");
                this.listOfTrends.add("ТБ(5.5)");
                this.listOfTrends.add("Обе забьют >1.5 - ДА");
                this.listOfTrends.add("Обе забьют >1.5 - НЕТ");
                this.listOfTrends.add("Гол в каждом периоде - ДА");
                this.listOfTrends.add("Гол в каждом периоде - НЕТ");
                this.listOfTrends.add("Заб. в 1 пер.");
                this.listOfTrends.add("Проп. в 1 пер.");
                this.listOfTrends.add("ТБ(1.5) в 1 пер.");
                this.listOfTrends.add("ТБ(2) в 1 пер.");
                this.listOfTrends.add("ТM(1.5) в 1 пер.");
                this.listOfTrends.add("ТM(2) в 1 пер.");
                this.listOfTrends.add("Обе забьют - ДА в 1 пер.");
                this.listOfTrends.add("Обе забьют - НЕТ в 1 пер.");
                this.listOfTrends.add("Заб. в 2 пер.");
                this.listOfTrends.add("Проп. в 2 пер.");
                this.listOfTrends.add("ТБ(1.5) в 2 пер.");
                this.listOfTrends.add("ТБ(2) в 2 пер.");
                this.listOfTrends.add("ТM(1.5) в 2 пер.");
                this.listOfTrends.add("ТМ(2) в 2 пер.");
                this.listOfTrends.add("Обе забьют - ДА в 2 пер.");
                this.listOfTrends.add("Обе забьют - НЕТ в 2 пер.");
                this.listOfTrends.add("Заб. в 3 пер.");
                this.listOfTrends.add("Проп. в 3 пер.");
                this.listOfTrends.add("ТБ(1.5) в 3 пер.");
                this.listOfTrends.add("ТБ(2) в 3 пер.");
                this.listOfTrends.add("ТM(1.5) в 3 пер.");
                this.listOfTrends.add("ТМ(2) в 3 пер.");
                this.listOfTrends.add("Обе забьют - ДА в 3 пер.");
                this.listOfTrends.add("Обе забьют - НЕТ в 3 пер.");
                break;
            }
            case "Броски в створ":{
                this.listOfTrends.add("БрВС: ТМ");
                this.listOfTrends.add("БрВС: ТБ");
                this.listOfTrends.add("БрВС: Фора команды");
                this.listOfTrends.add("БрВС: Фора соперника");
                this.listOfTrends.add("БрВС: ИТБ команды");
                this.listOfTrends.add("БрВС: ИТМ команды");
                this.listOfTrends.add("БрВС: ИТБ соперника");
                this.listOfTrends.add("БрВС: ИТМ соперника");
                this.listOfTrends.add("БрВС 1пер: ТМ");
                this.listOfTrends.add("БрВС 1пер: ТБ");
                this.listOfTrends.add("БрВС 1пер: Фора команды");
                this.listOfTrends.add("БрВС 1пер: Фора соперника");
                this.listOfTrends.add("БрВС 1пер: ИТБ команды");
                this.listOfTrends.add("БрВС 1пер: ИТМ команды");
                this.listOfTrends.add("БрВС 1пер: ИТБ соперника");
                this.listOfTrends.add("БрВС 1пер: ИТМ соперника");
                break;
            }
            case "Штрафное время":{
                this.listOfTrends.add("ШТР.ВР: ТМ");
                this.listOfTrends.add("ШТР.ВР: ТБ");
                this.listOfTrends.add("ШТР.ВР: Фора команды");
                this.listOfTrends.add("ШТР.ВР: Фора соперника");
                this.listOfTrends.add("ШТР.ВР: ИТБ команды");
                this.listOfTrends.add("ШТР.ВР: ИТМ команды");
                this.listOfTrends.add("ШТР.ВР: ИТБ соперника");
                this.listOfTrends.add("ШТР.ВР: ИТМ соперника");
                this.listOfTrends.add("ШТР.ВР 1пер: Фора команды");
                this.listOfTrends.add("ШТР.ВР 1пер: Фора соперника");
                break;
            }

        }
        switch (league){
            case "KHL":{
                tmSOT = 60.5;
                tbSOT = 55.5;
                itmSOT = 30.5;
                itbSOT = 27.5;
                tmSOT1per = 20.5;
                tbSOT1per = 20.5;
                itmSOT1per = 10.5;
                itbSOT1per = 10.5;
                tmPenTime = 14.5;
                tbPenTime = 12.5;
                itmPenTime = 8.5;
                itbPenTime = 6.5;

                // если в КХЛ мало голов будет постоянно, то убрать надо ТМ(2) по периодам
                //this.listOfTrends.remove("ТM(2) в 1 пер.");
                //this.listOfTrends.remove("ТM(2) в 2 пер.");
                //this.listOfTrends.remove("ТM(2) в 3 пер.");
                //this.listOfTrends.remove("ТБ(2) в 1 пер.");
                //this.listOfTrends.remove("ТБ(2) в 2 пер.");
                //this.listOfTrends.remove("ТБ(2) в 3 пер.");

                break;
            }
            case "NHL":{
                tmSOT = 62.5;
                tbSOT = 60.5;
                itmSOT = 32.5;
                itbSOT = 30.5;
                tmSOT1per = 20.5;
                tbSOT1per = 20.5;
                itmSOT1per = 10.5;
                itbSOT1per = 10.5;
                tmPenTime = 14.5;
                tbPenTime = 12.5;
                itmPenTime = 6.5;
                itbPenTime = 4.5;
                break;
            }
        }
    }

    public void analyzeTrends(String team, Selector selector){
        resultList = new ArrayList<>();
        for (int i=0; i<listOfTrends.size(); i++){
            int percent = 0;
            int success = 0;
            switch (listOfTrends.get(i)){
                case "ТМ(5.5)":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore + selector.listOfMatches.get(j).awayScore < 5.5)
                            success++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add("ТМ(5.5): " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }
                case "ТБ(5.5)":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore + selector.listOfMatches.get(j).awayScore > 5.5)
                            success++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add("ТБ(5.5): " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }
                case "ТБ(4.5)":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore + selector.listOfMatches.get(j).awayScore > 4.5)
                            success++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add("ТБ(4.5): " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }
                case "ТМ(4.5)":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore + selector.listOfMatches.get(j).awayScore < 4.5)
                            success++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add("ТМ(4.5): " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }
                case "Обе забьют >1.5 - ДА":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore > 1.5 && selector.listOfMatches.get(j).awayScore > 1.5)
                            success++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add("Обе забьют >1.5 - ДА: " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }
                case "Обе забьют >1.5 - НЕТ":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore < 1.5 || selector.listOfMatches.get(j).awayScore < 1.5)
                            success++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add("Обе забьют >1.5 - НЕТ: " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }
                case "Гол в каждом периоде - ДА":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if ( ( (selector.listOfMatches.get(j).homeScore1stPeriod + selector.listOfMatches.get(j).awayScore1stPeriod) *
                                (selector.listOfMatches.get(j).homeScore2ndPeriod + selector.listOfMatches.get(j).awayScore2ndPeriod) *
                                (selector.listOfMatches.get(j).homeScore3rdPeriod + selector.listOfMatches.get(j).awayScore3rdPeriod) ) > 0)
                            success++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add("Гол в каждом периоде - ДА: " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }
                case "Гол в каждом периоде - НЕТ":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if ( ( (selector.listOfMatches.get(j).homeScore1stPeriod + selector.listOfMatches.get(j).awayScore1stPeriod) *
                                (selector.listOfMatches.get(j).homeScore2ndPeriod + selector.listOfMatches.get(j).awayScore2ndPeriod) *
                                (selector.listOfMatches.get(j).homeScore3rdPeriod + selector.listOfMatches.get(j).awayScore3rdPeriod) ) == 0)
                            success++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add("Гол в каждом периоде - НЕТ: " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }

                case "Заб. в 1 пер.":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                            if (selector.listOfMatches.get(j).homeScore1stPeriod > 0)
                                success++;
                        } else{
                            if (selector.listOfMatches.get(j).awayScore1stPeriod > 0)
                                success++;
                        }
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add(team + " забивает в 1-ом периоде: " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }
                case "Проп. в 1 пер.":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                            if (selector.listOfMatches.get(j).awayScore1stPeriod > 0)
                                success++;
                        } else{
                            if (selector.listOfMatches.get(j).homeScore1stPeriod > 0)
                                success++;
                        }
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add(team + " пропускает в 1-ом периоде: " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }
                case "ТБ(1.5) в 1 пер.":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore1stPeriod + selector.listOfMatches.get(j).awayScore1stPeriod > 1.5)
                            success++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add("ТБ(1.5) в 1-ом периоде: " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }
                case "ТБ(2) в 1 пер.":{
                    int vozvrat = 0;
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore1stPeriod + selector.listOfMatches.get(j).awayScore1stPeriod > 2)
                            success++;
                        if (selector.listOfMatches.get(j).homeScore1stPeriod + selector.listOfMatches.get(j).awayScore1stPeriod == 2)
                            vozvrat++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / (selector.listOfMatches.size()-vozvrat) , 0);
                    if (percent > percentBorder){
                        resultList.add("ТБ(2) в 1-ом периоде: " + success + "+ " + vozvrat + "= " + (selector.listOfMatches.size()-success-vozvrat) + "- (" + percent + "%)");
                    }
                    break;
                }
                case "ТM(1.5) в 1 пер.":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore1stPeriod + selector.listOfMatches.get(j).awayScore1stPeriod < 1.5)
                            success++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add("ТM(1.5) в 1-ом периоде: " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }

                case "ТМ(2) в 1 пер.":{
                    int vozvrat = 0;
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore1stPeriod + selector.listOfMatches.get(j).awayScore1stPeriod < 2)
                            success++;
                        if (selector.listOfMatches.get(j).homeScore1stPeriod + selector.listOfMatches.get(j).awayScore1stPeriod == 2)
                            vozvrat++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / (selector.listOfMatches.size()-vozvrat) , 0);
                    if (percent > percentBorder){
                        resultList.add("ТМ(2) в 1-ом периоде: " + success + "+ " + vozvrat + "= " + (selector.listOfMatches.size()-success-vozvrat) + "- (" + percent + "%)");
                    }
                    break;
                }
                case "Обе забьют - ДА в 1 пер.":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore1stPeriod * selector.listOfMatches.get(j).awayScore1stPeriod > 0)
                            success++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add("Обе забьют - ДА в 1-ом периоде: " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }
                case "Обе забьют - НЕТ в 1 пер.":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore1stPeriod * selector.listOfMatches.get(j).awayScore1stPeriod == 0)
                            success++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add("Обе забьют - НЕТ в 1-ом периоде: " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }
                case "Заб. в 2 пер.":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                            if (selector.listOfMatches.get(j).homeScore2ndPeriod > 0)
                                success++;
                        } else{
                            if (selector.listOfMatches.get(j).awayScore2ndPeriod > 0)
                                success++;
                        }
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add(team + " забивает во 2-ом периоде: " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }
                case "Проп. в 2 пер.":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                            if (selector.listOfMatches.get(j).awayScore2ndPeriod > 0)
                                success++;
                        } else{
                            if (selector.listOfMatches.get(j).homeScore2ndPeriod > 0)
                                success++;
                        }
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add(team + " пропускает во 2-ом периоде: " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }
                case "ТБ(1.5) в 2 пер.":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore2ndPeriod + selector.listOfMatches.get(j).awayScore2ndPeriod > 1.5)
                            success++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add("ТБ(1.5) во 2-ом периоде: " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }
                case "ТБ(2) в 2 пер.":{
                    int vozvrat = 0;
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore2ndPeriod + selector.listOfMatches.get(j).awayScore2ndPeriod > 2)
                            success++;
                        if (selector.listOfMatches.get(j).homeScore2ndPeriod + selector.listOfMatches.get(j).awayScore2ndPeriod == 2)
                            vozvrat++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / (selector.listOfMatches.size()-vozvrat) , 0);
                    if (percent > percentBorder){
                        resultList.add("ТБ(2) во 2-ом периоде: " + success + "+ " + vozvrat + "= " + (selector.listOfMatches.size()-success-vozvrat) + "- (" + percent + "%)");
                    }
                    break;
                }
                case "ТM(1.5) в 2 пер.":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore2ndPeriod + selector.listOfMatches.get(j).awayScore2ndPeriod < 1.5)
                            success++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add("ТМ(1.5) во 2-ом периоде: " + + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }

                case "ТМ(2) в 2 пер.":{
                    int vozvrat = 0;
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore2ndPeriod + selector.listOfMatches.get(j).awayScore2ndPeriod < 2)
                            success++;
                        if (selector.listOfMatches.get(j).homeScore2ndPeriod + selector.listOfMatches.get(j).awayScore2ndPeriod == 2)
                            vozvrat++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / (selector.listOfMatches.size()-vozvrat) , 0);
                    if (percent > percentBorder){
                        resultList.add("ТМ(2) во 2-ом периоде: " + success + "+ " + vozvrat + "= " + (selector.listOfMatches.size()-success-vozvrat) + "- (" + percent + "%)");
                    }
                    break;
                }

                case "Обе забьют - ДА в 2 пер.":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore2ndPeriod * selector.listOfMatches.get(j).awayScore2ndPeriod > 0)
                            success++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add("Обе забьют - ДА во 2-ом периоде: " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }
                case "Обе забьют - НЕТ в 2 пер.":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore2ndPeriod * selector.listOfMatches.get(j).awayScore2ndPeriod == 0)
                            success++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add("Обе забьют - НЕТ во 2-ом периоде: " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }
                case "Заб. в 3 пер.":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                            if (selector.listOfMatches.get(j).homeScore3rdPeriod > 0)
                                success++;
                        } else{
                            if (selector.listOfMatches.get(j).awayScore3rdPeriod > 0)
                                success++;
                        }
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add(team + " забивает в 3-ем периоде: " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }
                case "Проп. в 3 пер.":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                            if (selector.listOfMatches.get(j).awayScore3rdPeriod > 0)
                                success++;
                        } else{
                            if (selector.listOfMatches.get(j).homeScore3rdPeriod > 0)
                                success++;
                        }
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add(team + " пропускает в 3-ем периоде: " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }
                case "ТБ(1.5) в 3 пер.":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore3rdPeriod + selector.listOfMatches.get(j).awayScore3rdPeriod > 1.5)
                            success++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add("ТБ(1.5) в 3-ем периоде: " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }
                case "ТБ(2) в 3 пер.":{
                    int vozvrat = 0;
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore3rdPeriod + selector.listOfMatches.get(j).awayScore3rdPeriod > 2)
                            success++;
                        if (selector.listOfMatches.get(j).homeScore3rdPeriod + selector.listOfMatches.get(j).awayScore3rdPeriod == 2)
                            vozvrat++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / (selector.listOfMatches.size()-vozvrat) , 0);
                    if (percent > percentBorder){
                        resultList.add("ТБ(2) в 3-ем периоде: " + success + "+ " + vozvrat + "= " + (selector.listOfMatches.size()-success-vozvrat) + "- (" + percent + "%)");
                    }
                    break;
                }
                case "ТM(1.5) в 3 пер.":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore3rdPeriod + selector.listOfMatches.get(j).awayScore3rdPeriod < 1.5)
                            success++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add("ТМ(1.5) в 3-ем периоде: " + + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }

                case "ТМ(2) в 3 пер.":{
                    int vozvrat = 0;
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore3rdPeriod + selector.listOfMatches.get(j).awayScore3rdPeriod < 2)
                            success++;
                        if (selector.listOfMatches.get(j).homeScore3rdPeriod + selector.listOfMatches.get(j).awayScore3rdPeriod == 2)
                            vozvrat++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / (selector.listOfMatches.size()-vozvrat) , 0);
                    if (percent > percentBorder){
                        resultList.add("ТМ(2) в 3-ем периоде: " + success + "+ " + vozvrat + "= " + (selector.listOfMatches.size()-success-vozvrat) + "- (" + percent + "%)");
                    }
                    break;
                }
                case "Обе забьют - ДА в 3 пер.":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore3rdPeriod * selector.listOfMatches.get(j).awayScore3rdPeriod > 0)
                            success++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add("Обе забьют - ДА в 3-ем периоде: " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }
                case "Обе забьют - НЕТ в 3 пер.":{
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeScore3rdPeriod * selector.listOfMatches.get(j).awayScore3rdPeriod == 0)
                            success++;
                    }
                    percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                    if (percent > percentBorder){
                        resultList.add("Обе забьют - НЕТ в 3-ем периоде: " + success + " матчей из " + selector.listOfMatches.size() + " (" + percent + "%)");
                    }
                    break;
                }

                case "БрВС: ТМ":{
                    boolean flag = false;
                    double resultTotal = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if (selector.listOfMatches.get(j).homeShotsOnTarget + selector.listOfMatches.get(j).awayShotsOnTarget < tmSOT)
                                success++;
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultTotal = tmSOT;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        tmSOT -= 1;
                    }
                    if (resultFlag){
                        resultList.add("БрВС: ТМ (" + resultTotal + ") " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }

                    break;
                }
                case "БрВС: ТБ":{
                    boolean flag = false;
                    double resultTotal = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if (selector.listOfMatches.get(j).homeShotsOnTarget + selector.listOfMatches.get(j).awayShotsOnTarget > tbSOT)
                                success++;
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultTotal = tbSOT;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        tbSOT += 1;
                    }
                    if (resultFlag){
                        resultList.add("БрВС: ТБ (" + resultTotal + ") " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }

                    break;
                }
                case "БрВС: Фора команды":{
                    double fora = 0.5;
                    boolean flag = false;
                    double resultFora = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                                if (selector.listOfMatches.get(j).homeShotsOnTarget - selector.listOfMatches.get(j).awayShotsOnTarget + fora > 0)
                                    success++;
                            } else{
                                if (selector.listOfMatches.get(j).awayShotsOnTarget - selector.listOfMatches.get(j).homeShotsOnTarget + fora > 0)
                                    success++;
                            }
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultFora = fora;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        fora -= 1;
                    }
                    if (resultFlag){
                        String str = String.valueOf(resultFora);
                        if (resultFora > 0)
                            str = "+" + str;
                        resultList.add("Фора (" + str + ") " + team + " по БрВС: " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }
                    break;
                }
                case "БрВС: Фора соперника":{
                    double fora = 0.5;
                    boolean flag = false;
                    double resultFora = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                                if (selector.listOfMatches.get(j).awayShotsOnTarget - selector.listOfMatches.get(j).homeShotsOnTarget + fora > 0)
                                    success++;
                            } else{
                                if (selector.listOfMatches.get(j).homeShotsOnTarget - selector.listOfMatches.get(j).awayShotsOnTarget + fora > 0)
                                    success++;
                            }
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultFora = fora;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        fora -= 1;
                    }
                    if (resultFlag){
                        String str = String.valueOf(resultFora);
                        if (resultFora > 0)
                            str = "+" + str;
                        resultList.add("Фора (" + str + ") соперника " + team + " по БрВС: " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }
                    break;
                }

                case "БрВС: ИТБ команды":{
                    boolean flag = false;
                    double resultTotal = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                                if (selector.listOfMatches.get(j).homeShotsOnTarget > itbSOT)
                                    success++;
                            } else{
                                if (selector.listOfMatches.get(j).awayShotsOnTarget > itbSOT)
                                    success++;
                            }
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultTotal = itbSOT;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        itbSOT += 1;
                    }
                    if (resultFlag){
                        resultList.add(team + ": ИТБ (" + resultTotal + ") по БрВС: " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }
                    break;
                }
                case "БрВС: ИТМ команды":{
                    boolean flag = false;
                    double resultTotal = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                                if (selector.listOfMatches.get(j).homeShotsOnTarget < itmSOT)
                                    success++;
                            } else{
                                if (selector.listOfMatches.get(j).awayShotsOnTarget < itmSOT)
                                    success++;
                            }
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultTotal = itmSOT;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        itmSOT -= 1;
                    }
                    if (resultFlag){
                        resultList.add(team + ": ИТМ (" + resultTotal + ") по БрВС: " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }
                    break;
                }
                case "БрВС: ИТБ соперника":{
                    boolean flag = false;
                    double resultTotal = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                                if (selector.listOfMatches.get(j).awayShotsOnTarget > itbSOT)
                                    success++;
                            } else{
                                if (selector.listOfMatches.get(j).homeShotsOnTarget > itbSOT)
                                    success++;
                            }
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultTotal = itbSOT;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        itbSOT += 1;
                    }
                    if (resultFlag){
                        resultList.add("Соперник " + team + ": ИТБ (" + resultTotal + ") по БрВС: " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }
                    break;
                }
                case "БрВС: ИТМ соперника":{
                    boolean flag = false;
                    double resultTotal = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                                if (selector.listOfMatches.get(j).awayShotsOnTarget < itmSOT)
                                    success++;
                            } else{
                                if (selector.listOfMatches.get(j).homeShotsOnTarget < itmSOT)
                                    success++;
                            }
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultTotal = itmSOT;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        itmSOT -= 1;
                    }
                    if (resultFlag){
                        resultList.add("Соперник " + team + ": ИТМ (" + resultTotal + ") по БрВС: " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }
                    break;
                }
                case "БрВС 1пер: ТМ":{
                    boolean flag = false;
                    double resultTotal = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if (selector.listOfMatches.get(j).homeShotsOnTarget1stPeriod + selector.listOfMatches.get(j).awayShotsOnTarget1stPeriod < tmSOT1per)
                                success++;
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultTotal = tmSOT1per;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        tmSOT1per -= 1;
                    }
                    if (resultFlag){
                        resultList.add("БрВС в 1-ОМ ПЕР: ТМ (" + resultTotal + ") " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }

                    break;
                }
                case "БрВС 1пер: ТБ":{
                    boolean flag = false;
                    double resultTotal = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if (selector.listOfMatches.get(j).homeShotsOnTarget1stPeriod + selector.listOfMatches.get(j).awayShotsOnTarget1stPeriod > tbSOT1per)
                                success++;
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultTotal = tbSOT1per;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        tbSOT1per+= 1;
                    }
                    if (resultFlag){
                        resultList.add("БрВС в 1-ОМ ПЕР: ТБ (" + resultTotal + ") " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }

                    break;
                }
                case "БрВС 1пер: Фора команды":{
                    double fora = 0.5;
                    boolean flag = false;
                    double resultFora = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                                if (selector.listOfMatches.get(j).homeShotsOnTarget1stPeriod - selector.listOfMatches.get(j).awayShotsOnTarget1stPeriod + fora > 0)
                                    success++;
                            } else{
                                if (selector.listOfMatches.get(j).awayShotsOnTarget1stPeriod - selector.listOfMatches.get(j).homeShotsOnTarget1stPeriod + fora > 0)
                                    success++;
                            }
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultFora = fora;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        fora -= 1;
                    }
                    if (resultFlag){
                        String str = String.valueOf(resultFora);
                        if (resultFora > 0)
                            str = "+" + str;
                        resultList.add("Фора (" + str + ") " + team + " по БрВС в 1-ОМ ПЕР: " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }
                    break;
                }
                case "БрВС 1пер: Фора соперника":{
                    double fora = 0.5;
                    boolean flag = false;
                    double resultFora = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                                if (selector.listOfMatches.get(j).awayShotsOnTarget1stPeriod - selector.listOfMatches.get(j).homeShotsOnTarget1stPeriod + fora > 0)
                                    success++;
                            } else{
                                if (selector.listOfMatches.get(j).homeShotsOnTarget1stPeriod - selector.listOfMatches.get(j).awayShotsOnTarget1stPeriod + fora > 0)
                                    success++;
                            }
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultFora = fora;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        fora -= 1;
                    }
                    if (resultFlag){
                        String str = String.valueOf(resultFora);
                        if (resultFora > 0)
                            str = "+" + str;
                        resultList.add("Фора (" + str + ") соперника " + team + " по БрВС в 1-ОМ ПЕР: " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }
                    break;
                }

                case "БрВС 1пер: ИТБ команды":{
                    boolean flag = false;
                    double resultTotal = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                                if (selector.listOfMatches.get(j).homeShotsOnTarget1stPeriod > itbSOT1per)
                                    success++;
                            } else{
                                if (selector.listOfMatches.get(j).awayShotsOnTarget1stPeriod > itbSOT1per)
                                    success++;
                            }
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultTotal = itbSOT1per;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        itbSOT1per += 1;
                    }
                    if (resultFlag){
                        resultList.add(team + ": ИТБ (" + resultTotal + ") по БрВС в 1-ОМ ПЕР: " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }
                    break;
                }
                case "БрВС 1пер: ИТМ команды":{
                    boolean flag = false;
                    double resultTotal = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                                if (selector.listOfMatches.get(j).homeShotsOnTarget1stPeriod < itmSOT1per)
                                    success++;
                            } else{
                                if (selector.listOfMatches.get(j).awayShotsOnTarget1stPeriod < itmSOT1per)
                                    success++;
                            }
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultTotal = itmSOT1per;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        itmSOT1per -= 1;
                    }
                    if (resultFlag){
                        resultList.add(team + ": ИТМ (" + resultTotal + ") по БрВС в 1-ОМ ПЕР: " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }
                    break;
                }
                case "БрВС 1пер: ИТБ соперника":{
                    boolean flag = false;
                    double resultTotal = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                                if (selector.listOfMatches.get(j).awayShotsOnTarget1stPeriod > itbSOT1per)
                                    success++;
                            } else{
                                if (selector.listOfMatches.get(j).homeShotsOnTarget1stPeriod > itbSOT1per)
                                    success++;
                            }
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultTotal = itbSOT1per;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        itbSOT1per += 1;
                    }
                    if (resultFlag){
                        resultList.add("Соперник " + team + ": ИТБ (" + resultTotal + ") по БрВС в 1-ОМ ПЕР: " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }
                    break;
                }
                case "БрВС 1пер: ИТМ соперника":{
                    boolean flag = false;
                    double resultTotal = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                                if (selector.listOfMatches.get(j).awayShotsOnTarget1stPeriod < itmSOT1per)
                                    success++;
                            } else{
                                if (selector.listOfMatches.get(j).homeShotsOnTarget1stPeriod < itmSOT1per)
                                    success++;
                            }
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultTotal = itmSOT1per;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        itmSOT1per-= 1;
                    }
                    if (resultFlag){
                        resultList.add("Соперник " + team + ": ИТМ (" + resultTotal + ") по БрВС в 1-ОМ ПЕР: " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }
                    break;
                }

                case "ШТР.ВР: ТМ":{
                    boolean flag = false;
                    double resultTotal = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if ((selector.listOfMatches.get(j).home2MinPenalties + selector.listOfMatches.get(j).away2MinPenalties)*2 < tmPenTime)
                                success++;
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultTotal = tmPenTime;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        tmPenTime -= 2;
                    }
                    if (resultFlag){
                        resultList.add("ШТР.ВР: ТМ (" + resultTotal + ") " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }

                    break;
                }
                case "ШТР.ВР: ТБ":{
                    boolean flag = false;
                    double resultTotal = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if ((selector.listOfMatches.get(j).home2MinPenalties + selector.listOfMatches.get(j).away2MinPenalties)*2 > tbPenTime)
                                success++;
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultTotal = tbPenTime;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        tbPenTime += 2;
                    }
                    if (resultFlag){
                        resultList.add("ШТР.ВР: ТБ (" + resultTotal + ") " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }

                    break;
                }
                case "ШТР.ВР: Фора команды":{
                    double fora = 0.5;
                    boolean flag = false;
                    double resultFora = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                                if ((selector.listOfMatches.get(j).home2MinPenalties - selector.listOfMatches.get(j).away2MinPenalties)*2 + fora > 0)
                                    success++;
                            } else{
                                if ((selector.listOfMatches.get(j).away2MinPenalties - selector.listOfMatches.get(j).home2MinPenalties)*2 + fora > 0)
                                    success++;
                            }
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultFora = fora;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        fora -= 1;
                    }
                    if (resultFlag){
                        String str = String.valueOf(resultFora);
                        if (resultFora > 0)
                            str = "+" + str;
                        resultList.add("Фора (" + str + ") " + team + " по ШТР.ВР: " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }
                    break;
                }
                case "ШТР.ВР: Фора соперника":{
                    double fora = 0.5;
                    boolean flag = false;
                    double resultFora = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                                if ((selector.listOfMatches.get(j).away2MinPenalties - selector.listOfMatches.get(j).home2MinPenalties)*2 + fora > 0)
                                    success++;
                            } else{
                                if ((selector.listOfMatches.get(j).home2MinPenalties - selector.listOfMatches.get(j).away2MinPenalties)*2 + fora > 0)
                                    success++;
                            }
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultFora = fora;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        fora -= 1;
                    }
                    if (resultFlag){
                        String str = String.valueOf(resultFora);
                        if (resultFora > 0)
                            str = "+" + str;
                        resultList.add("Фора (" + str + ") соперника " + team + " по ШТР.ВР: " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }
                    break;
                }

                case "ШТР.ВР: ИТБ команды":{
                    boolean flag = false;
                    double resultTotal = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                                if (selector.listOfMatches.get(j).home2MinPenalties*2 > itbPenTime)
                                    success++;
                            } else{
                                if (selector.listOfMatches.get(j).away2MinPenalties*2 > itbPenTime)
                                    success++;
                            }
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultTotal = itbPenTime;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        itbPenTime += 2;
                    }
                    if (resultFlag){
                        resultList.add(team + ": ИТБ (" + resultTotal + ") по ШТР.ВР: " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }
                    break;
                }
                case "ШТР.ВР: ИТМ команды":{
                    boolean flag = false;
                    double resultTotal = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                                if (selector.listOfMatches.get(j).home2MinPenalties * 2 < itmPenTime)
                                    success++;
                            } else{
                                if (selector.listOfMatches.get(j).away2MinPenalties * 2 < itmPenTime)
                                    success++;
                            }
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultTotal = itmPenTime;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        itmPenTime -= 2;
                    }
                    if (resultFlag){
                        resultList.add(team + ": ИТМ (" + resultTotal + ") по ШТР.ВР: " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }
                    break;
                }
                case "ШТР.ВР: ИТБ соперника":{
                    boolean flag = false;
                    double resultTotal = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                                if (selector.listOfMatches.get(j).away2MinPenalties * 2 > itbPenTime)
                                    success++;
                            } else{
                                if (selector.listOfMatches.get(j).home2MinPenalties * 2 > itbPenTime)
                                    success++;
                            }
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultTotal = itbPenTime;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        itbPenTime += 2;
                    }
                    if (resultFlag){
                        resultList.add("Соперник " + team + ": ИТБ (" + resultTotal + ") по ШТР.ВР: " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }
                    break;
                }
                case "ШТР.ВР: ИТМ соперника":{
                    boolean flag = false;
                    double resultTotal = 99;
                    int resultSuccess = 99;
                    int resultPercent = 200;
                    boolean resultFlag = false;

                    while (!flag){
                        success = 0;
                        for (int j=0; j<selector.listOfMatches.size(); j++){
                            if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                                if (selector.listOfMatches.get(j).away2MinPenalties * 2 < itmPenTime)
                                    success++;
                            } else{
                                if (selector.listOfMatches.get(j).home2MinPenalties * 2 < itmPenTime)
                                    success++;
                            }
                        }
                        percent = (int) MyMath.round(100 * (double) success / selector.listOfMatches.size() , 0);
                        if (percent < percentBorder){
                            flag = true;
                        } else {
                            resultFlag = true;
                            resultTotal = itmPenTime;
                            resultSuccess = success;
                            resultPercent = percent;
                        }
                        itmPenTime -= 2;
                    }
                    if (resultFlag){
                        resultList.add("Соперник " + team + ": ИТМ (" + resultTotal + ") по ШТР.ВР: " + resultSuccess + " матчей из " + selector.listOfMatches.size() + " (" + resultPercent + "%)");
                    }
                    break;
                }

                case "ШТР.ВР 1пер: Фора команды":{
                    boolean flag = false;
                    int vozvrat = 0;
                    success = 0;
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).homeTeam.equals(team)){
                            if ((selector.listOfMatches.get(j).home2MinPenalties1stPeriod - selector.listOfMatches.get(j).away2MinPenalties1stPeriod)*2 > 0)
                                success++;
                            if ((selector.listOfMatches.get(j).home2MinPenalties1stPeriod - selector.listOfMatches.get(j).away2MinPenalties1stPeriod)*2 == 0)
                                vozvrat++;
                        } else{
                            if ((selector.listOfMatches.get(j).away2MinPenalties1stPeriod - selector.listOfMatches.get(j).home2MinPenalties1stPeriod)*2 > 0)
                                success++;
                            if ((selector.listOfMatches.get(j).away2MinPenalties1stPeriod - selector.listOfMatches.get(j).home2MinPenalties1stPeriod)*2 == 0)
                                vozvrat++;
                        }
                    }
                    percent = (int) MyMath.round(100 * (double) success / (selector.listOfMatches.size()-vozvrat) , 0);
                    if (percent >= percentBorder)
                        flag = true;
                    if (flag){
                        resultList.add("Фора (0) " + team + " по ШТР.ВР в 1-ОМ ПЕР: " + success + "+ " + vozvrat + "= " + (selector.listOfMatches.size()-success-vozvrat) + "- (" + percent + "%)");
                    }
                    break;
                }
                case "ШТР.ВР 1пер: Фора соперника":{
                    boolean flag = false;
                    int vozvrat = 0;
                    success = 0;
                    for (int j=0; j<selector.listOfMatches.size(); j++){
                        if (selector.listOfMatches.get(j).awayTeam.equals(team)){
                            if ((selector.listOfMatches.get(j).home2MinPenalties1stPeriod - selector.listOfMatches.get(j).away2MinPenalties1stPeriod)*2 > 0)
                                success++;
                            if ((selector.listOfMatches.get(j).home2MinPenalties1stPeriod - selector.listOfMatches.get(j).away2MinPenalties1stPeriod)*2 == 0)
                                vozvrat++;
                        } else{
                            if ((selector.listOfMatches.get(j).away2MinPenalties1stPeriod - selector.listOfMatches.get(j).home2MinPenalties1stPeriod)*2 > 0)
                                success++;
                            if ((selector.listOfMatches.get(j).away2MinPenalties1stPeriod - selector.listOfMatches.get(j).home2MinPenalties1stPeriod)*2 == 0)
                                vozvrat++;
                        }
                    }
                    percent = (int) MyMath.round(100 * (double) success / (selector.listOfMatches.size()-vozvrat) , 0);
                    if (percent >= percentBorder)
                        flag = true;
                    if (flag){
                        resultList.add("Фора (0) соперника " + team + " по ШТР.ВР в 1-ОМ ПЕР: " + success + "+ " + vozvrat + "= " + (selector.listOfMatches.size()-success-vozvrat) + "- (" + percent + "%)");
                    }
                    break;
                }
            }
        }
    }

    public static Color chooseColorForLabel(String text){

        if (text.contains("1-ом")){
            return new Color(180, 255, 245);
        }
        if (text.contains("2-ом")){
            return new Color(255, 193, 122);
        }
        if (text.contains("3-ем")){
            return new Color(149, 255, 125);
        }
        if (text.contains("БрВС")){
            return new Color(244, 182, 255);
        }
        if (text.contains("ШТР")){
            return new Color(255, 124, 118);
        }

        return new Color(200, 200, 200);
    }
}
