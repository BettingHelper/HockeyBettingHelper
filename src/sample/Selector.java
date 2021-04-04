package sample;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Selector {
    public String teamName;
    public double matches;
    public ArrayList<Match> listOfMatches;
    public ArrayList<ArrayList<String>> pList;
    public ArrayList<String> listForConfrontation;
    public String[] params = {"Победы", "Победы в ОТ/Бул.", "Поражения", "Поражения в ОТ/Бул.", "Очки", "Голы в основное время",
            "Голы с учетом ОТ(Буллитов)", "Броски в створ","Кол-во большинства", "Голы в большинстве", "Голы в меньшинстве",
            "Вбрасывания", "Блокированные броски", "Силовые приемы", "Минуты штрафа", "Кол-во двухминутных удалений", "Форма",
            "Голы в первом периоде", "Голы во втором периоде", "Голы в третьем периоде", "Время в атаке", "Броски мимо",
            "Броски в створ 1 пер", "Броски в створ 2 пер", "Броски в створ 3 пер",
            "Минуты штрафа 1 пер", "Минуты штрафа 2 пер", "Минуты штрафа 3 пер",
            "Кол-во двухминутных удалений 1 пер", "Кол-во двухминутных удалений 2 пер", "Кол-во двухминутных удалений 3 пер",

    };
    public ArrayList<String> seasonsList = new ArrayList<>();

    public Selector(){
        pList = new ArrayList<>();
        listOfMatches = new ArrayList<>();
        listForConfrontation = new ArrayList<>();
    }

    public void getListOfMatches(String leagueName, String teamName, String allOrHomeOrAway, String season, String lastOrFullSeason){
        this.teamName = teamName;
        if ((!leagueName.contains("Лига"))&&(!teamName.contains("Команда"))){

            String path = "database/" + season + "/" + leagueName + "/Matches/";
            Team team = Team.getTeamFromFileWithSeason(teamName, season, leagueName);

            if (lastOrFullSeason.contains("Последние")){
                int count = Integer.parseInt(lastOrFullSeason.split(" ")[1]);
                int index = team.matchList.size()-1;
                while (count>0&&index>=0){
                    if (allOrHomeOrAway.contains("Все")){
                        listOfMatches.add(0, Match.getMatchFromFileByName(path + team.matchList.get(index)+ ".xml"));
                        count--;
                    }
                    if (allOrHomeOrAway.contains("Дома") && team.matchList.get(index).substring(0,3).equals(Team.getShortName(teamName))){
                        listOfMatches.add(0, Match.getMatchFromFileByName(path + team.matchList.get(index)+ ".xml"));
                        count--;
                    }
                    if (allOrHomeOrAway.contains("На выезде") && team.matchList.get(index).substring(3,6).equals(Team.getShortName(teamName))){
                        listOfMatches.add(0, Match.getMatchFromFileByName(path + team.matchList.get(index)+ ".xml"));
                        count--;
                    }
                    index --;
                }
            } else if (lastOrFullSeason.contains("Весь сезон")) {
                if (allOrHomeOrAway.contains("Все")){
                    for (int index=0; index<team.matchList.size(); index++){
                        listOfMatches.add(Match.getMatchFromFileByName(path + team.matchList.get(index)+ ".xml"));
                    }
                }
                if (allOrHomeOrAway.contains("Дома")){
                    for (int index=0; index<team.matchList.size(); index++){
                        if (team.matchList.get(index).substring(0,3).equals(Team.getShortName(teamName))){
                            listOfMatches.add(Match.getMatchFromFileByName(path + team.matchList.get(index)+ ".xml"));
                        }
                    }
                }
                if (allOrHomeOrAway.contains("На выезде")){
                    for (int index=0; index<team.matchList.size(); index++){
                        if (team.matchList.get(index).substring(3,6).equals(Team.getShortName(teamName))){
                            listOfMatches.add(Match.getMatchFromFileByName(path + team.matchList.get(index)+ ".xml"));
                        }
                    }
                }
            } else {
                // ветка для "ДО-ПОСЛЕ"
                int beginIndex = Integer.parseInt(lastOrFullSeason.split("-")[0]);
                int endIndex = Integer.parseInt(lastOrFullSeason.split("-")[1]);

                if (allOrHomeOrAway.contains("Все")){
                    for (int index=0; index<team.matchList.size(); index++){
                        listOfMatches.add(Match.getMatchFromFileByName(path + team.matchList.get(index)+ ".xml"));
                    }
                }
                if (allOrHomeOrAway.contains("Дома")){
                    for (int index=0; index<team.matchList.size(); index++){
                        if (team.matchList.get(index).substring(0,3).equals(Team.getShortName(teamName))){
                            listOfMatches.add(Match.getMatchFromFileByName(path + team.matchList.get(index)+ ".xml"));
                        }
                    }
                }
                if (allOrHomeOrAway.contains("На выезде")){
                    for (int index=0; index<team.matchList.size(); index++){
                        if (team.matchList.get(index).substring(3,6).equals(Team.getShortName(teamName))){
                            listOfMatches.add(Match.getMatchFromFileByName(path + team.matchList.get(index)+ ".xml"));
                        }
                    }
                }

                if (beginIndex == 0){
                    int size = listOfMatches.size();
                    for (int i=endIndex+1; i<size; i++){
                        listOfMatches.remove(listOfMatches.size()-1);
                    }
                } else{
                    for (int i=0; i<beginIndex; i++){
                        listOfMatches.remove(0);
                    }
                }
            }
            matches = listOfMatches.size();
        }
    }

    public void getArraysWithStats(String teamName, double[][] dataArrayThis, double[][] dataArrayOpponent, double[][] dataArrayTotal,
                                   String[] arrayOpponents, ArrayList<String> tournamentTable){
        this.teamName = teamName;
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

//                arrayOpponents[i] = Team.getShortName(m.awayTeam) + "(H)" + m.date.split(".20")[0];   //+"(H)"
                arrayOpponents[i] = Team.getShortName(m.awayTeam)+"(H)(" + League.getPositionInLeague(m.awayTeam, tournamentTable) + ")";


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

//                arrayOpponents[i] = Team.getShortName(m.homeTeam) + "(A)" + m.date.split(".20")[0];   //+"(A)"
                arrayOpponents[i] = Team.getShortName(m.homeTeam)+"(A)(" + League.getPositionInLeague(m.homeTeam, tournamentTable) + ")";

            }
        }
    }

    public void getArraysForAdvancedStats(String teamName, /*double[][] dataArrayThis, double[][] dataArrayOpponent,*/ String[] arrayOpponents, ArrayList<String> tournamentTable){
        this.teamName = teamName;
        for (int i=0; i<listOfMatches.size(); i++){
            Match m = listOfMatches.get(i);
            if (teamName.equals(m.homeTeam)){
                //dataArrayThis[0][i] = m.homeShotsOnTarget + m.homeMissedShots - m.awayBlockedShots;
                //dataArrayThis[1][i] = MyMath.round(100 * (m.homeShotsOnTarget + m.homeMissedShots + m.awayBlockedShots) / (m.homeShotsOnTarget + m.homeMissedShots + m.awayBlockedShots + m.awayShotsOnTarget + m.awayMissedShots + m.homeBlockedShots) , 2);
                //dataArrayThis[2][i] = m.homeShotsOnTarget + m.homeMissedShots;
                //dataArrayThis[3][i] = MyMath.round(100 * (m.homeShotsOnTarget + m.homeMissedShots) / (m.homeShotsOnTarget + m.homeMissedShots + m.awayShotsOnTarget + m.awayMissedShots) , 2);
                //dataArrayThis[4][i] = MyMath.round(100 + (100 * m.homeScore / m.homeShotsOnTarget) - (100 * m.awayScore / m.awayShotsOnTarget), 2);
//
                //dataArrayOpponent[0][i] = m.awayShotsOnTarget + m.awayMissedShots - m.homeBlockedShots;
                //dataArrayOpponent[1][i] = MyMath.round(100 * (m.awayShotsOnTarget + m.awayMissedShots + m.awayBlockedShots) / (m.homeShotsOnTarget + m.homeMissedShots + m.awayBlockedShots + m.awayShotsOnTarget + m.awayMissedShots + m.homeBlockedShots) , 2);
                //dataArrayOpponent[2][i] = m.awayShotsOnTarget + m.awayMissedShots;
                //dataArrayOpponent[3][i] = MyMath.round(100 * (m.awayShotsOnTarget + m.awayMissedShots) / (m.homeShotsOnTarget + m.homeMissedShots + m.awayShotsOnTarget + m.awayMissedShots) , 2);
                //dataArrayOpponent[4][i] = MyMath.round(100 + (100 * m.awayScore / m.awayShotsOnTarget) - (100 * m.homeScore / m.homeShotsOnTarget), 2);

                //                arrayOpponents[i] = Team.getShortName(m.awayTeam) + "(H)" + m.date.split(".20")[0];   //+"(H)"
                arrayOpponents[i] = Team.getShortName(m.awayTeam)+"(H)(" + League.getPositionInLeague(m.awayTeam, tournamentTable) + ")";

            } else if(teamName.equals(m.awayTeam)) {
                //dataArrayOpponent[0][i] = m.homeShotsOnTarget + m.homeMissedShots - m.awayBlockedShots;
                //dataArrayOpponent[1][i] = MyMath.round(100 * (m.homeShotsOnTarget + m.homeMissedShots + m.awayBlockedShots) / (m.homeShotsOnTarget + m.homeMissedShots + m.awayBlockedShots + m.awayShotsOnTarget + m.awayMissedShots + m.homeBlockedShots) , 2);
                //dataArrayOpponent[2][i] = m.homeShotsOnTarget + m.homeMissedShots;
                //dataArrayOpponent[3][i] = MyMath.round(100 * (m.homeShotsOnTarget + m.homeMissedShots) / (m.homeShotsOnTarget + m.homeMissedShots + m.awayShotsOnTarget + m.awayMissedShots) , 2);
                //dataArrayOpponent[4][i] = MyMath.round(100 + (100 * m.homeScore / m.homeShotsOnTarget) - (100 * m.awayScore / m.awayShotsOnTarget), 2);
//
                //dataArrayThis[0][i] = m.awayShotsOnTarget + m.awayMissedShots - m.homeBlockedShots;
                //dataArrayThis[1][i] = MyMath.round(100 * (m.awayShotsOnTarget + m.awayMissedShots + m.awayBlockedShots) / (m.homeShotsOnTarget + m.homeMissedShots + m.awayBlockedShots + m.awayShotsOnTarget + m.awayMissedShots + m.homeBlockedShots) , 2);
                //dataArrayThis[2][i] = m.awayShotsOnTarget + m.awayMissedShots;
                //dataArrayThis[3][i] = MyMath.round(100 * (m.awayShotsOnTarget + m.awayMissedShots) / (m.homeShotsOnTarget + m.homeMissedShots + m.awayShotsOnTarget + m.awayMissedShots) , 2);
                //dataArrayThis[4][i] = MyMath.round(100 + (100 * m.awayScore / m.awayShotsOnTarget) - (100 * m.homeScore / m.homeShotsOnTarget), 2);

//                arrayOpponents[i] = Team.getShortName(m.homeTeam) + "(A)" + m.date.split(".20")[0];   //+"(A)"
                arrayOpponents[i] = Team.getShortName(m.homeTeam)+"(A)(" + League.getPositionInLeague(m.homeTeam, tournamentTable) + ")";

            }
        }
    }

    public void getPList(ArrayList<Match> listOfMatches, String teamName, String season){
        pList.clear();
        if (listOfMatches.size()>0){
            double[][] paramsD = new double[params.length][2];
            String forma = "";
            for (int i=0; i<listOfMatches.size(); i++){
                Match m = listOfMatches.get(i);
                String league = Team.getLeague(teamName);
                if (teamName.equals(m.homeTeam)){
                    if (m.homeScore == m.awayScore){
                        if (m.homeScoreOT>m.awayScoreOT||m.homeScoreBullits>m.awayScoreBullits){
                            paramsD[1][0] += 1;
                            paramsD[3][1] += 1;
                            paramsD[4][0] += 2;
                            forma = forma + "w";
                        } else {
                            paramsD[1][1] += 1;
                            paramsD[3][0] += 1;
                            paramsD[4][0] += 1;
                            forma = forma + "l";
                        }
                    }
                    else if (m.homeScore > m.awayScore){
                        paramsD[0][0] += 1;
                        paramsD[2][1] += 1;
                        if (league.equals("KHL")&&season.contains("17-18")){
                            paramsD[4][0] += 3;
                        } else
                            paramsD[4][0] += 2;
                        forma = forma + "W";
                    }
                    else {
                        paramsD[0][1] += 1;
                        paramsD[2][0] += 1;
                        forma = forma + "L";
                    }

                    paramsD[5][0] += m.homeScore;
                    paramsD[5][1] += m.awayScore;
                    paramsD[6][0] += m.homeScore + m.homeScoreOT + m.homeScoreBullits;
                    paramsD[6][1] += m.awayScore + m.awayScoreOT + m.awayScoreBullits;
                    paramsD[7][0] += m.homeShotsOnTarget;
                    paramsD[7][1] += m.awayShotsOnTarget;
                    paramsD[8][0] += m.homeNumberOfPP;
                    paramsD[8][1] += m.awayNumberOfPP;
                    paramsD[9][0] += m.homeGoalsInPP;
                    paramsD[9][1] += m.awayGoalsInPP;
                    paramsD[10][0] += m.homeShorthandedGoals;
                    paramsD[10][1] += m.awayShorthandedGoals;
                    paramsD[11][0] += m.homeFaceoffsWon;
                    paramsD[11][1] += m.awayFaceoffsWon;
                    paramsD[12][0] += m.homeBlockedShots;
                    paramsD[12][1] += m.awayBlockedShots;
                    paramsD[13][0] += m.homeHits;
                    paramsD[13][1] += m.awayHits;
                    paramsD[14][0] += m.homePenaltyMinutes;
                    paramsD[14][1] += m.awayPenaltyMinutes;
                    paramsD[15][0] += m.home2MinPenalties;
                    paramsD[15][1] += m.away2MinPenalties;
                    paramsD[17][0] += m.homeScore1stPeriod;
                    paramsD[17][1] += m.awayScore1stPeriod;
                    paramsD[18][0] += m.homeScore2ndPeriod;
                    paramsD[18][1] += m.awayScore2ndPeriod;
                    paramsD[19][0] += m.homeScore3rdPeriod;
                    paramsD[19][1] += m.awayScore3rdPeriod;
                    paramsD[20][0] += m.homeTimeInAttack;
                    paramsD[20][1] += m.awayTimeInAttack;
                    paramsD[21][0] += m.homeMissedShots;
                    paramsD[21][1] += m.awayMissedShots;
                    paramsD[22][0] += m.homeShotsOnTarget1stPeriod;
                    paramsD[22][1] += m.awayShotsOnTarget1stPeriod;
                    paramsD[23][0] += m.homeShotsOnTarget2ndPeriod;
                    paramsD[23][1] += m.awayShotsOnTarget2ndPeriod;
                    paramsD[24][0] += m.homeShotsOnTarget3rdPeriod;
                    paramsD[24][1] += m.awayShotsOnTarget3rdPeriod;
                    paramsD[25][0] += m.homePenaltyMinutes1stPeriod;
                    paramsD[25][1] += m.awayPenaltyMinutes1stPeriod;
                    paramsD[26][0] += m.homePenaltyMinutes2ndPeriod;
                    paramsD[26][1] += m.awayPenaltyMinutes2ndPeriod;
                    paramsD[27][0] += m.homePenaltyMinutes3rdPeriod;
                    paramsD[27][1] += m.awayPenaltyMinutes3rdPeriod;
                    paramsD[28][0] += m.home2MinPenalties1stPeriod;
                    paramsD[28][1] += m.away2MinPenalties1stPeriod;
                    paramsD[29][0] += m.home2MinPenalties2ndPeriod;
                    paramsD[29][1] += m.away2MinPenalties2ndPeriod;
                    paramsD[30][0] += m.home2MinPenalties3rdPeriod;
                    paramsD[30][1] += m.away2MinPenalties3rdPeriod;


                } else if (teamName.equals(m.awayTeam)){
                    if (m.homeScore == m.awayScore){
                        if (m.awayScoreOT>m.homeScoreOT||m.awayScoreBullits>m.homeScoreBullits){
                            paramsD[1][0] += 1;
                            paramsD[3][1] += 1;
                            paramsD[4][0] += 2;
                            forma = forma + "w";
                        } else {
                            paramsD[1][1] += 1;
                            paramsD[3][0] += 1;
                            paramsD[4][0] += 1;
                            forma = forma + "l";
                        }
                    }
                    else if (m.awayScore > m.homeScore){
                        paramsD[0][0] += 1;
                        paramsD[2][1] += 1;
                        if (league.equals("KHL")&&season.contains("17-18")){
                            paramsD[4][0] += 3;
                        } else
                            paramsD[4][0] += 2;
                        forma = forma + "W";
                    }
                    else {
                        paramsD[0][1] += 1;
                        paramsD[2][0] += 1;
                        forma = forma + "L";
                    }

                    paramsD[5][0] += m.awayScore;
                    paramsD[5][1] += m.homeScore;
                    paramsD[6][0] += m.awayScore + m.awayScoreOT + m.awayScoreBullits;
                    paramsD[6][1] += m.homeScore + m.homeScoreOT + m.homeScoreBullits;
                    paramsD[7][0] += m.awayShotsOnTarget;
                    paramsD[7][1] += m.homeShotsOnTarget;
                    paramsD[8][0] += m.awayNumberOfPP;
                    paramsD[8][1] += m.homeNumberOfPP;
                    paramsD[9][0] += m.awayGoalsInPP;
                    paramsD[9][1] += m.homeGoalsInPP;
                    paramsD[10][0] += m.awayShorthandedGoals;
                    paramsD[10][1] += m.homeShorthandedGoals;
                    paramsD[11][0] += m.awayFaceoffsWon;
                    paramsD[11][1] += m.homeFaceoffsWon;
                    paramsD[12][0] += m.awayBlockedShots;
                    paramsD[12][1] += m.homeBlockedShots;
                    paramsD[13][0] += m.awayHits;
                    paramsD[13][1] += m.homeHits;
                    paramsD[14][0] += m.awayPenaltyMinutes;
                    paramsD[14][1] += m.homePenaltyMinutes;
                    paramsD[15][0] += m.away2MinPenalties;
                    paramsD[15][1] += m.home2MinPenalties;
                    paramsD[17][0] += m.awayScore1stPeriod;
                    paramsD[17][1] += m.homeScore1stPeriod;
                    paramsD[18][0] += m.awayScore2ndPeriod;
                    paramsD[18][1] += m.homeScore2ndPeriod;
                    paramsD[19][0] += m.awayScore3rdPeriod;
                    paramsD[19][1] += m.homeScore3rdPeriod;
                    paramsD[20][0] += m.awayTimeInAttack;
                    paramsD[20][1] += m.homeTimeInAttack;
                    paramsD[21][0] += m.awayMissedShots;
                    paramsD[21][1] += m.homeMissedShots;
                    paramsD[22][0] += m.awayShotsOnTarget1stPeriod;
                    paramsD[22][1] += m.homeShotsOnTarget1stPeriod;
                    paramsD[23][0] += m.awayShotsOnTarget2ndPeriod;
                    paramsD[23][1] += m.homeShotsOnTarget2ndPeriod;
                    paramsD[24][0] += m.awayShotsOnTarget3rdPeriod;
                    paramsD[24][1] += m.homeShotsOnTarget3rdPeriod;
                    paramsD[25][0] += m.awayPenaltyMinutes1stPeriod;
                    paramsD[25][1] += m.homePenaltyMinutes1stPeriod;
                    paramsD[26][0] += m.awayPenaltyMinutes2ndPeriod;
                    paramsD[26][1] += m.homePenaltyMinutes2ndPeriod;
                    paramsD[27][0] += m.awayPenaltyMinutes3rdPeriod;
                    paramsD[27][1] += m.homePenaltyMinutes3rdPeriod;
                    paramsD[28][0] += m.away2MinPenalties1stPeriod;
                    paramsD[28][1] += m.home2MinPenalties1stPeriod;
                    paramsD[29][0] += m.away2MinPenalties2ndPeriod;
                    paramsD[29][1] += m.home2MinPenalties2ndPeriod;
                    paramsD[30][0] += m.away2MinPenalties3rdPeriod;
                    paramsD[30][1] += m.home2MinPenalties3rdPeriod;

                }
            }
            for (int i=0; i<paramsD.length; i++){
                ArrayList<String> parameter = new ArrayList<>();
                if (i==7||i==11||i==12||i==13||i==21) {
                    paramsD[i][0] = MyMath.round(paramsD[i][0] / listOfMatches.size(), 2);
                    paramsD[i][1] = MyMath.round(paramsD[i][1] / listOfMatches.size(), 2);
                }

                parameter.add(params[i]);
                parameter.add(String.valueOf(MyMath.round(paramsD[i][0], 2)));
                parameter.add(String.valueOf(MyMath.round(paramsD[i][1], 2)));
                pList.add(parameter);
            }
            ArrayList<String> f = new ArrayList<>();
            if (forma.length()>20){
                forma = forma.substring(forma.length()-20,forma.length());
            }
            f.add("Форма");
            f.add(forma);
            f.add(forma);
            pList.set(16,f);
        }
    }

    public void fillPListForOpponents(String leagueName, String teamName, String allOrHomeOrAway, String season){
        pList.clear();

        this.teamName = teamName;

        League league = League.getLeagueFromFile(leagueName, season);
        double[][] paramsD = new double[params.length][2];
        for (int i=0; i<paramsD.length; i++){
            ArrayList<String> parameter = new ArrayList<>();
            parameter.add(params[i]);
            parameter.add("");
            parameter.add("");
            pList.add(parameter);
        }

        ArrayList<String> localList7 = new ArrayList<>();
        ArrayList<String> localList14 = new ArrayList<>();
        ArrayList<String> localList15 = new ArrayList<>();
        switch (allOrHomeOrAway){
            case "Все матчи":{
                int index = -1;
                boolean flagFound = false;
                while (!flagFound){
                    index++;
                    if (league.overallStatsTable.get(index).contains(teamName)){
                        flagFound = true;
                    }
                }
                matches = Double.parseDouble(league.overallStatsTable.get(index).split("\\*")[1]);

                localList7.add(teamName);
                localList7.add(String.valueOf(MyMath.round(Double.parseDouble(league.overallStatsTable.get(index).split("\\*")[7].split("_")[0]) / matches, 2)));
                localList7.add(String.valueOf(MyMath.round(Double.parseDouble(league.overallStatsTable.get(index).split("\\*")[7].split("_")[1]) / matches, 2)));
                localList14.add(teamName);
                localList14.add(String.valueOf(MyMath.round(Double.parseDouble(league.overallStatsTable.get(index).split("\\*")[16].split("_")[0]), 2)));
                localList14.add(String.valueOf(MyMath.round(Double.parseDouble(league.overallStatsTable.get(index).split("\\*")[16].split("_")[1]), 2)));
                localList15.add(teamName);
                localList15.add(String.valueOf(MyMath.round(Double.parseDouble(league.overallStatsTable.get(index).split("\\*")[17].split("_")[0]), 2)));
                localList15.add(String.valueOf(MyMath.round(Double.parseDouble(league.overallStatsTable.get(index).split("\\*")[17].split("_")[1]), 2)));
                break;
            }
            case "Дома":{
                int index = -1;
                boolean flagFound = false;
                while (!flagFound){
                    index++;
                    if (league.homeStatsTable.get(index).contains(teamName)){
                        flagFound = true;
                    }
                }
                matches = Double.parseDouble(league.homeStatsTable.get(index).split("\\*")[1]);

                localList7.add(teamName);
                localList7.add(String.valueOf(MyMath.round(Double.parseDouble(league.homeStatsTable.get(index).split("\\*")[7].split("_")[0]) / matches, 2)));
                localList7.add(String.valueOf(MyMath.round(Double.parseDouble(league.homeStatsTable.get(index).split("\\*")[7].split("_")[1]) / matches, 2)));
                localList14.add(teamName);
                localList14.add(String.valueOf(MyMath.round(Double.parseDouble(league.homeStatsTable.get(index).split("\\*")[16].split("_")[0]), 2)));
                localList14.add(String.valueOf(MyMath.round(Double.parseDouble(league.homeStatsTable.get(index).split("\\*")[16].split("_")[1]), 2)));
                localList15.add(teamName);
                localList15.add(String.valueOf(MyMath.round(Double.parseDouble(league.homeStatsTable.get(index).split("\\*")[17].split("_")[0]), 2)));
                localList15.add(String.valueOf(MyMath.round(Double.parseDouble(league.homeStatsTable.get(index).split("\\*")[17].split("_")[1]), 2)));
                break;
            }
            case "На выезде":{
                int index = -1;
                boolean flagFound = false;
                while (!flagFound){
                    index++;
                    if (league.awayStatsTable.get(index).contains(teamName)){
                        flagFound = true;
                    }
                }

                matches = Double.parseDouble(league.awayStatsTable.get(index).split("\\*")[1]);

                localList7.add(teamName);
                localList7.add(String.valueOf(MyMath.round(Double.parseDouble(league.awayStatsTable.get(index).split("\\*")[7].split("_")[0]) / matches, 2)));
                localList7.add(String.valueOf(MyMath.round(Double.parseDouble(league.awayStatsTable.get(index).split("\\*")[7].split("_")[1]) / matches, 2)));
                localList14.add(teamName);
                localList14.add(String.valueOf(MyMath.round(Double.parseDouble(league.awayStatsTable.get(index).split("\\*")[16].split("_")[0]), 2)));
                localList14.add(String.valueOf(MyMath.round(Double.parseDouble(league.awayStatsTable.get(index).split("\\*")[16].split("_")[1]), 2)));
                localList15.add(teamName);
                localList15.add(String.valueOf(MyMath.round(Double.parseDouble(league.awayStatsTable.get(index).split("\\*")[17].split("_")[0]), 2)));
                localList15.add(String.valueOf(MyMath.round(Double.parseDouble(league.awayStatsTable.get(index).split("\\*")[17].split("_")[1]), 2)));
                break;
            }

        }
        pList.set(7, localList7);
        pList.set(14, localList14);
        pList.set(15, localList15);

    }

    public void getConfrontationList(String leagueName, String homeTeamName, String awayTeamName, String allOrHomeAway){
        if ((!leagueName.contains("Выберите"))&&(!homeTeamName.contains("Выберите"))&&(!awayTeamName.contains("Выберите"))){
            ArrayList<String> listOfSeasons = Settings.getListOfSeasons();
            for (int i=listOfSeasons.size()-1; i>=0; i--){
                boolean flagTeamFound = Team.isTeamFoundInSeason(homeTeamName, listOfSeasons.get(i));
                Team ht = null;
                if (flagTeamFound)
                    ht = Team.getTeamFromFileWithSeason(homeTeamName, listOfSeasons.get(i), leagueName);
                if (ht != null){
                    String matchTitle1 = Team.getShortName(homeTeamName) + Team.getShortName(awayTeamName);
                    String matchTitle2 = Team.getShortName(awayTeamName) + Team.getShortName(homeTeamName);
                    String path = "database/" + listOfSeasons.get(i) + "/" + leagueName + "/Matches/";
                    for (int k=0; k<ht.matchList.size(); k++){
                        if (ht.matchList.get(k).contains(matchTitle1)){
                            listOfMatches.add(Match.getMatchFromFileByName(path + ht.matchList.get(k) + ".xml"));
                            listForConfrontation.add(listOfSeasons.get(i));
                            seasonsList.add(listOfSeasons.get(i));
                        }
                        if (ht.matchList.get(k).contains(matchTitle2) && allOrHomeAway.contains("Все матчи")){
                            listOfMatches.add(Match.getMatchFromFileByName(path + ht.matchList.get(k) + ".xml"));
                            listForConfrontation.add(listOfSeasons.get(i));
                            seasonsList.add(listOfSeasons.get(i));
                        }
                    }
                }


            }
        }
    }

    public static ArrayList<Selector> getOpponentsList(String teamName, ArrayList<Match> list, String season, String allOrHomeOrAway, String lastOrFullSeason){
        Selector selector;
        ArrayList<Selector> listResult = new ArrayList<>();
        int count = list.size();

        for (int i=0; i<count; i++){
            selector = new Selector();
            String opponent = "";
            String opAllOrHomeOrAway = "";

            switch (allOrHomeOrAway) {
                case "Все матчи":
                    if (teamName.equals(list.get(i).homeTeam))
                        opponent = list.get(i).awayTeam;
                    else
                        opponent = list.get(i).homeTeam;
                    opAllOrHomeOrAway = "Все матчи";
                    break;
                case "Дома":
                    opponent = list.get(i).awayTeam;
                    opAllOrHomeOrAway = "На выезде";
                    break;
                case "На выезде":
                    opponent = list.get(i).homeTeam;
                    opAllOrHomeOrAway = "Дома";
                    break;
            }

            if (lastOrFullSeason.contains("Последние")){
                selector.getListOfMatches(Team.getLeague(opponent), opponent, opAllOrHomeOrAway, season, lastOrFullSeason);
                selector.getPList(selector.listOfMatches, opponent, season);
            } else {
                selector.fillPListForOpponents(Team.getLeague(opponent), opponent, opAllOrHomeOrAway, season);
            }
            //selector.getListOfMatches(Team.getLeague(opponent), opponent, opAllOrHomeOrAway, season, lastOrFullSeason);
            //selector.getPList(selector.listOfMatches, opponent, season);
            listResult.add(selector);
        }

        return listResult;
    }

    public static ArrayList<ArrayList<Double>> getAdvancedStatsList(String teamName, ArrayList<Match> listOfMatches){
        ArrayList<ArrayList<Double>> result = new ArrayList<>();
        double corsiPercentMovingAverage = 0;
        double fenwickPercentMovingAverage = 0;
        double PDOMovingAverage = 0;

        for (int i=0; i<listOfMatches.size(); i++){
            ArrayList<Double> record = new ArrayList<>();
            if (teamName.equals(listOfMatches.get(i).homeTeam)){
                double corsi = MyMath.getCorsi((double) listOfMatches.get(i).homeShotsOnTarget, (double) listOfMatches.get(i).awayBlockedShots, (double) listOfMatches.get(i).homeMissedShots);
                double corsiOpp = MyMath.getCorsi((double) listOfMatches.get(i).awayShotsOnTarget,(double) listOfMatches.get(i).homeBlockedShots, (double) listOfMatches.get(i).awayMissedShots);
                double corsiPercent = MyMath.round(100 * corsi / (corsi + corsiOpp), 2);
                corsiPercentMovingAverage = MyMath.round((corsiPercentMovingAverage*i + corsiPercent) / (i+1), 2);

                record.add(corsi);
                record.add(corsiOpp);
                record.add(corsiPercent);
                record.add(corsiPercentMovingAverage);

                double fenwick = MyMath.getFenwick((double) listOfMatches.get(i).homeShotsOnTarget, (double) listOfMatches.get(i).homeMissedShots);
                double fenwickOpp = MyMath.getFenwick((double) listOfMatches.get(i).awayShotsOnTarget, (double) listOfMatches.get(i).awayMissedShots);
                double fenwickPercent = MyMath.round(100 * fenwick / (fenwick + fenwickOpp), 2);
                fenwickPercentMovingAverage = MyMath.round((fenwickPercentMovingAverage*i + fenwickPercent) / (i+1), 2);

                record.add(fenwick);
                record.add(fenwickOpp);
                record.add(fenwickPercent);
                record.add(fenwickPercentMovingAverage);

                double PDO = 100 + MyMath.round(100 * ( listOfMatches.get(i).homeScore/(double)listOfMatches.get(i).homeShotsOnTarget - listOfMatches.get(i).awayScore/ (double)listOfMatches.get(i).awayShotsOnTarget), 2);
                PDOMovingAverage = MyMath.round((PDOMovingAverage*i + PDO) / (i+1), 2);

                record.add(PDO);
                record.add(PDOMovingAverage);

                result.add(record);
            } else{
                double corsi = MyMath.getCorsi((double) listOfMatches.get(i).awayShotsOnTarget, (double) listOfMatches.get(i).homeBlockedShots, (double) listOfMatches.get(i).awayMissedShots);
                double corsiOpp = MyMath.getCorsi( (double) listOfMatches.get(i).homeShotsOnTarget, (double) listOfMatches.get(i).awayBlockedShots,  (double) listOfMatches.get(i).homeMissedShots);
                double corsiPercent = MyMath.round(100 * corsi / (corsi + corsiOpp), 2);
                corsiPercentMovingAverage = MyMath.round((corsiPercentMovingAverage*i + corsiPercent) / (i+1), 2);

                record.add(corsi);
                record.add(corsiOpp);
                record.add(corsiPercent);
                record.add(corsiPercentMovingAverage);

                double fenwick = (double) listOfMatches.get(i).awayShotsOnTarget + (double) listOfMatches.get(i).awayMissedShots;
                double fenwickOpp = (double) listOfMatches.get(i).homeShotsOnTarget + (double) listOfMatches.get(i).homeMissedShots;
                double fenwickPercent = MyMath.round(100 * fenwick / (fenwick + fenwickOpp), 2);
                fenwickPercentMovingAverage = MyMath.round((fenwickPercentMovingAverage*i + fenwickPercent) / (i+1), 2);

                record.add(fenwick);
                record.add(fenwickOpp);
                record.add(fenwickPercent);
                record.add(fenwickPercentMovingAverage);

                double PDO = 100 + MyMath.round(100 * ( listOfMatches.get(i).awayScore/(double)listOfMatches.get(i).awayShotsOnTarget - listOfMatches.get(i).homeScore/ (double)listOfMatches.get(i).homeShotsOnTarget), 2);
                PDOMovingAverage = MyMath.round((PDOMovingAverage*i + PDO) / (i+1), 2);

                record.add(PDO);
                record.add(PDOMovingAverage);

                result.add(record);
            }
        }
        return result;
    }
}


