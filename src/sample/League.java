package sample;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.*;
import java.util.ArrayList;

// определяем корневой элемент
@XmlRootElement(name = "League")
// определяем последовательность тегов в XML
@XmlType(propOrder = { "leagueName", "season", "fileName", "matchesPlayed", "matchesToAddingInStat",
        "goalsScored", "goalsScored1per", "goalsScored2per", "goalsScored3per", "goalsScoredOT",
        "homeGoals", "awayGoals",
        "homeGoals1per", "awayGoals1per", "homeGoals2per", "awayGoals2per", "homeGoals3per", "awayGoals3per",
        "homeShots", "awayShots", "homeShotsOnTarget", "awayShotsOnTarget",
        "homeShotsOnTarget1per", "awayShotsOnTarget1per", "homeShotsOnTarget2per", "awayShotsOnTarget2per", "homeShotsOnTarget3per", "awayShotsOnTarget3per",
        "homeBlockedShots", "awayBlockedShots",
        "homeMissedShots", "awayMissedShots",
        "homePowerPlays", "awayPowerPlays",
        "homeGoalsInPP", "awayGoalsInPP",
        "homeFaceoffsWon", "awayFaceoffsWon",
        "homeTimeInAttack", "awayTimeInAttack",
        "homeHits", "awayHits",
        "homePenMinutes", "awayPenMinutes",
        "home2MinPenalties", "away2MinPenalties",
        "g_homeWin_draw_awayWin", "g_homeWin_draw_awayWinOT", "g_homeWin_draw_awayWin_1per", "g_homeWin_draw_awayWin_2per", "g_homeWin_draw_awayWin_3per",
        "g_OZ15", "g_totals", "g_totals_1per", "g_totals_2per", "g_totals_3per", "g_goalsInAllPeriods",
        "tournamentTable", "overallStatsTable", "homeStatsTable", "awayStatsTable"
})

public class League {
    public String leagueName;
    public String season;
    public String fileName;
    public int matchesPlayed;
    public ArrayList<String> matchesToAddingInStat;
    public int goalsScored;
    public int goalsScoredOT;
    public int goalsScored1per;
    public int goalsScored2per;
    public int goalsScored3per;
    public int homeGoals;
    public int awayGoals;
    public int homeGoals1per;
    public int awayGoals1per;
    public int homeGoals2per;
    public int awayGoals2per;
    public int homeGoals3per;
    public int awayGoals3per;
    public int homeShots;
    public int awayShots;
    public int homeShotsOnTarget;
    public int awayShotsOnTarget;
    public int homeShotsOnTarget1per;
    public int awayShotsOnTarget1per;
    public int homeShotsOnTarget2per;
    public int awayShotsOnTarget2per;
    public int homeShotsOnTarget3per;
    public int awayShotsOnTarget3per;
    public int homeBlockedShots;
    public int awayBlockedShots;
    public int homeMissedShots;
    public int awayMissedShots;
    public int homePowerPlays;
    public int awayPowerPlays;
    public int homeGoalsInPP;
    public int awayGoalsInPP;
    public int homeFaceoffsWon;
    public int awayFaceoffsWon;
    public int homeTimeInAttack;
    public int awayTimeInAttack;
    public int homeHits;
    public int awayHits;
    public int homePenMinutes;
    public int awayPenMinutes;
    public int home2MinPenalties;
    public int away2MinPenalties;

    public String g_homeWin_draw_awayWin;
    public String g_homeWin_draw_awayWinOT;
    public String g_homeWin_draw_awayWin_1per;
    public String g_homeWin_draw_awayWin_2per;
    public String g_homeWin_draw_awayWin_3per;

    public int g_OZ15;
    public String g_totals;
    public String g_totals_1per;
    public String g_totals_2per;
    public String g_totals_3per;
    public int g_goalsInAllPeriods;

    public ArrayList<String> tournamentTable;
    public ArrayList<String> overallStatsTable;
    public ArrayList<String> homeStatsTable;
    public ArrayList<String> awayStatsTable;


    public static League getLeagueFromFile(String leagueName, String season){
        String fileName = "leaguesInfo/" + leagueName + "_" + season + ".xml";
        int n = Settings.getNumberOfAccount();
        try {
            FTPLoader.downloadFile(Settings.getLogin(n), Settings.getPassword(n), "/data/hockey/" + fileName, fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            MyException.sendException(e.getStackTrace(), "Проблема при загрузке " + fileName);
        }
        try {
            // создаем объект JAXBContext - точку входа для JAXB
            JAXBContext jaxbContext = JAXBContext.newInstance(League.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();

            return (League) un.unmarshal(new File(fileName));
        } catch (JAXBException e) {
            e.printStackTrace();
            MyException.sendException(e.getStackTrace(), fileName + " содержит ошибку!");
        }
        return null;
    }

    public static String[] getListOfTeams(String season, String leagueName){
        String resultS = "";
        String fileName = "database/" + season + "/leagues/" + leagueName + ".txt";
        try {
            File fileDir = new File(fileName);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF-8"));
            String str;
            while (((str = in.readLine()) != null)) {
                resultS += str + "\n";
            }
            in.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        return resultS.split("\n");
    }

    public static String getPositionInLeague(String teamName, ArrayList<String> tournamentTable){
        String result;
        int localIndex = 0;
        int upperPlace;
        int downPlace;
        int localPoints = 0;

        for (int i=0; i<tournamentTable.size(); i++){
            if (tournamentTable.get(i).startsWith(teamName)){
                localIndex = i;
                localPoints = Integer.parseInt(tournamentTable.get(localIndex).split("\\*")[5]);
            }
        }

        upperPlace = localIndex;
        downPlace = localIndex;
        for (int i=0; i<localIndex; i++){
            if (tournamentTable.get(i).split("\\*")[5].equals(String.valueOf(localPoints))){
                upperPlace = i;
                break;
            }
        }
        for (int i=localIndex; i<tournamentTable.size(); i++){
            if (tournamentTable.get(i).split("\\*")[5].equals(String.valueOf(localPoints))){
                downPlace = i;
            }
        }

        if (upperPlace == localIndex && localIndex == downPlace){
            result = String.valueOf(localIndex+1);
        } else {
            result = String.valueOf(1 + Math.min(upperPlace, localIndex)) + "-" + String.valueOf(1 + Math.max(localIndex, downPlace));
        }

        return result;
    }

    public double getParameterValue(String teamName, String allHomeAway, int indexOfParameter, int selfOrOpp){
        double result = 0;


        if (allHomeAway.contains("Общее")){
            for (int i=0; i<overallStatsTable.size(); i++){
                String teamNameLocal = Team.getShortName(overallStatsTable.get(i).split("\\*")[0]);
                if (teamName.contains(teamNameLocal)){
                    return (Double.parseDouble(overallStatsTable.get(i).split("\\*")[indexOfParameter].split("_")[selfOrOpp])
                            / Double.parseDouble(overallStatsTable.get(i).split("\\*")[1]) );
                }
            }
        }
        if (allHomeAway.contains("Дом")){
            for (int i=0; i<homeStatsTable.size(); i++){
                String teamNameLocal = Team.getShortName(homeStatsTable.get(i).split("\\*")[0]);
                if (teamName.contains(teamNameLocal)){
                    return (Double.parseDouble(homeStatsTable.get(i).split("\\*")[indexOfParameter].split("_")[selfOrOpp])
                            / Double.parseDouble(homeStatsTable.get(i).split("\\*")[1]) );
                }
            }
        }
        if (allHomeAway.contains("Выезд")){
            for (int i=0; i<awayStatsTable.size(); i++){
                String teamNameLocal = Team.getShortName(awayStatsTable.get(i).split("\\*")[0]);
                if (teamName.contains(teamNameLocal)){
                    return (Double.parseDouble(awayStatsTable.get(i).split("\\*")[indexOfParameter].split("_")[selfOrOpp])
                            / Double.parseDouble(awayStatsTable.get(i).split("\\*")[1]) );
                }
            }
        }

        return result;
    }

    public void resetTables(String season, String leagueName, String lastOrFull, JProgressBar jpb){
        this.overallStatsTable = new ArrayList<>();
        this.homeStatsTable = new ArrayList<>();
        this.awayStatsTable = new ArrayList<>();
        jpb.setValue(0);

        String[] listOfTeams = League.getListOfTeams(season, leagueName);

        for (int i=0; i<listOfTeams.length; i++){
            String teamName = listOfTeams[i];

            Selector totalSelector = new Selector();
            totalSelector.getListOfMatches(leagueName, teamName, "Все матчи", season, lastOrFull);
            totalSelector.getPList(totalSelector.listOfMatches, teamName, season);
            double matchesTotal = totalSelector.listOfMatches.size();

            Selector homeSelector = new Selector();
            homeSelector.getListOfMatches(leagueName, teamName, "Дома", season, lastOrFull);
            homeSelector.getPList(homeSelector.listOfMatches, teamName, season);
            double homeMatches = homeSelector.listOfMatches.size();

            Selector awaySelector = new Selector();
            awaySelector.getListOfMatches(leagueName, teamName, "На выезде", season, lastOrFull);
            awaySelector.getPList(awaySelector.listOfMatches, teamName, season);
            double awayMatches = awaySelector.listOfMatches.size();

            double real_Overall     = Team.roundResult(100 * (Double.parseDouble(totalSelector.pList.get(9).get(1)) / Double.parseDouble(totalSelector.pList.get(8).get(1))) , 2);
            double real_Overall_Opp = Team.roundResult(100 * (Double.parseDouble(totalSelector.pList.get(9).get(2)) / Double.parseDouble(totalSelector.pList.get(8).get(2))) , 2);

            double realHT     = Team.roundResult(100 * (Double.parseDouble(homeSelector.pList.get(9).get(1)) / Double.parseDouble(homeSelector.pList.get(8).get(1))) , 2);
            double realHT_Opp = Team.roundResult(100 * (Double.parseDouble(homeSelector.pList.get(9).get(2)) / Double.parseDouble(homeSelector.pList.get(8).get(2))) , 2);
            double realAT     = Team.roundResult(100 * (Double.parseDouble(awaySelector.pList.get(9).get(1)) / Double.parseDouble(awaySelector.pList.get(8).get(1))) , 2);
            double realAT_Opp = Team.roundResult(100 * (Double.parseDouble(awaySelector.pList.get(9).get(2)) / Double.parseDouble(awaySelector.pList.get(8).get(2))) , 2);

            String recordTotal = teamName + "*" + (int) matchesTotal + "*"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(5).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(5).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(5).get(1)) - Double.parseDouble(totalSelector.pList.get(5).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(5).get(1)) + Double.parseDouble(totalSelector.pList.get(5).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(6).get(1)) , 2) + "_"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(6).get(2)) , 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(6).get(1)) - Double.parseDouble(totalSelector.pList.get(6).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(6).get(1)) + Double.parseDouble(totalSelector.pList.get(6).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(17).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(17).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(17).get(1)) - Double.parseDouble(totalSelector.pList.get(17).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(17).get(1)) + Double.parseDouble(totalSelector.pList.get(17).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(18).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(18).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(18).get(1)) - Double.parseDouble(totalSelector.pList.get(18).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(18).get(1)) + Double.parseDouble(totalSelector.pList.get(18).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(19).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(19).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(19).get(1)) - Double.parseDouble(totalSelector.pList.get(19).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(19).get(1)) + Double.parseDouble(totalSelector.pList.get(19).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(7).get(1)) * matchesTotal, 2) + "_"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(7).get(2)) * matchesTotal, 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(7).get(1)) - Double.parseDouble(totalSelector.pList.get(7).get(2))) * matchesTotal, 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(7).get(1)) + Double.parseDouble(totalSelector.pList.get(7).get(2))) * matchesTotal, 2) + "*"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(22).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(22).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(22).get(1)) - Double.parseDouble(totalSelector.pList.get(22).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(22).get(1)) + Double.parseDouble(totalSelector.pList.get(22).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(23).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(23).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(23).get(1)) - Double.parseDouble(totalSelector.pList.get(23).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(23).get(1)) + Double.parseDouble(totalSelector.pList.get(23).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(24).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(24).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(24).get(1)) - Double.parseDouble(totalSelector.pList.get(24).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(24).get(1)) + Double.parseDouble(totalSelector.pList.get(24).get(2))), 2) + "*"
                    + String.valueOf(real_Overall) + "_"
                    + String.valueOf(real_Overall_Opp) + "_"
                    + String.valueOf(0) + "_"
                    + String.valueOf(0) + "*"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(11).get(1)) * matchesTotal, 2) + "_"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(11).get(2)) * matchesTotal, 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(11).get(1)) - Double.parseDouble(totalSelector.pList.get(11).get(2))) * matchesTotal, 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(11).get(1)) + Double.parseDouble(totalSelector.pList.get(11).get(2))) * matchesTotal, 2) + "*"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(20).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(20).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(20).get(1)) - Double.parseDouble(totalSelector.pList.get(20).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(20).get(1)) + Double.parseDouble(totalSelector.pList.get(20).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(12).get(1)) * matchesTotal, 2) + "_"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(12).get(2)) * matchesTotal, 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(12).get(1)) - Double.parseDouble(totalSelector.pList.get(12).get(2))) * matchesTotal, 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(12).get(1)) + Double.parseDouble(totalSelector.pList.get(12).get(2))) * matchesTotal, 2) + "*"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(13).get(1)) * matchesTotal, 2) + "_"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(13).get(2)) * matchesTotal, 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(13).get(1)) - Double.parseDouble(totalSelector.pList.get(13).get(2))) * matchesTotal, 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(13).get(1)) + Double.parseDouble(totalSelector.pList.get(13).get(2))) * matchesTotal, 2) + "*"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(14).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(14).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(14).get(1)) - Double.parseDouble(totalSelector.pList.get(14).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(14).get(1)) + Double.parseDouble(totalSelector.pList.get(14).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(15).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(15).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(15).get(1)) - Double.parseDouble(totalSelector.pList.get(15).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(15).get(1)) + Double.parseDouble(totalSelector.pList.get(15).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(21).get(1)) * matchesTotal, 2) + "_"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(21).get(2)) * matchesTotal, 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(21).get(1)) - Double.parseDouble(totalSelector.pList.get(21).get(2))) * matchesTotal, 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(21).get(1)) + Double.parseDouble(totalSelector.pList.get(21).get(2))) * matchesTotal, 2) + "*"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(9).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(9).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(9).get(1)) - Double.parseDouble(totalSelector.pList.get(9).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(9).get(1)) + Double.parseDouble(totalSelector.pList.get(9).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(10).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(totalSelector.pList.get(10).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(10).get(1)) - Double.parseDouble(totalSelector.pList.get(10).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(totalSelector.pList.get(10).get(1)) + Double.parseDouble(totalSelector.pList.get(10).get(2))), 2);


            String recordHome = teamName + "*" + (int) homeMatches + "*"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(5).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(5).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(5).get(1)) - Double.parseDouble(homeSelector.pList.get(5).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(5).get(1)) + Double.parseDouble(homeSelector.pList.get(5).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(6).get(1)) , 2) + "_"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(6).get(2)) , 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(6).get(1)) - Double.parseDouble(homeSelector.pList.get(6).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(6).get(1)) + Double.parseDouble(homeSelector.pList.get(6).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(17).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(17).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(17).get(1)) - Double.parseDouble(homeSelector.pList.get(17).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(17).get(1)) + Double.parseDouble(homeSelector.pList.get(17).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(18).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(18).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(18).get(1)) - Double.parseDouble(homeSelector.pList.get(18).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(18).get(1)) + Double.parseDouble(homeSelector.pList.get(18).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(19).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(19).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(19).get(1)) - Double.parseDouble(homeSelector.pList.get(19).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(19).get(1)) + Double.parseDouble(homeSelector.pList.get(19).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(7).get(1)) * homeMatches, 2) + "_"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(7).get(2)) * homeMatches, 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(7).get(1)) - Double.parseDouble(homeSelector.pList.get(7).get(2))) * homeMatches, 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(7).get(1)) + Double.parseDouble(homeSelector.pList.get(7).get(2))) * homeMatches, 2) + "*"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(22).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(22).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(22).get(1)) - Double.parseDouble(homeSelector.pList.get(22).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(22).get(1)) + Double.parseDouble(homeSelector.pList.get(22).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(23).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(23).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(23).get(1)) - Double.parseDouble(homeSelector.pList.get(23).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(23).get(1)) + Double.parseDouble(homeSelector.pList.get(23).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(24).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(24).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(24).get(1)) - Double.parseDouble(homeSelector.pList.get(24).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(24).get(1)) + Double.parseDouble(homeSelector.pList.get(24).get(2))), 2) + "*"
                    + String.valueOf(realHT) + "_"
                    + String.valueOf(realHT_Opp) + "_"
                    + String.valueOf(0) + "_"
                    + String.valueOf(0) + "*"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(11).get(1)) * homeMatches, 2) + "_"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(11).get(2)) * homeMatches, 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(11).get(1)) - Double.parseDouble(homeSelector.pList.get(11).get(2))) * homeMatches, 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(11).get(1)) + Double.parseDouble(homeSelector.pList.get(11).get(2))) * homeMatches, 2) + "*"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(20).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(20).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(20).get(1)) - Double.parseDouble(homeSelector.pList.get(20).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(20).get(1)) + Double.parseDouble(homeSelector.pList.get(20).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(12).get(1)) * homeMatches, 2) + "_"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(12).get(2)) * homeMatches, 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(12).get(1)) - Double.parseDouble(homeSelector.pList.get(12).get(2))) * homeMatches, 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(12).get(1)) + Double.parseDouble(homeSelector.pList.get(12).get(2))) * homeMatches, 2) + "*"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(13).get(1)) * homeMatches, 2) + "_"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(13).get(2)) * homeMatches, 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(13).get(1)) - Double.parseDouble(homeSelector.pList.get(13).get(2))) * homeMatches, 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(13).get(1)) + Double.parseDouble(homeSelector.pList.get(13).get(2))) * homeMatches, 2) + "*"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(14).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(14).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(14).get(1)) - Double.parseDouble(homeSelector.pList.get(14).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(14).get(1)) + Double.parseDouble(homeSelector.pList.get(14).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(15).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(15).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(15).get(1)) - Double.parseDouble(homeSelector.pList.get(15).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(15).get(1)) + Double.parseDouble(homeSelector.pList.get(15).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(21).get(1)) * homeMatches, 2) + "_"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(21).get(2)) * homeMatches, 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(21).get(1)) - Double.parseDouble(homeSelector.pList.get(21).get(2))) * homeMatches, 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(21).get(1)) + Double.parseDouble(homeSelector.pList.get(21).get(2))) * homeMatches, 2) + "*"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(9).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(9).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(9).get(1)) - Double.parseDouble(homeSelector.pList.get(9).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(9).get(1)) + Double.parseDouble(homeSelector.pList.get(9).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(10).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(homeSelector.pList.get(10).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(10).get(1)) - Double.parseDouble(homeSelector.pList.get(10).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(homeSelector.pList.get(10).get(1)) + Double.parseDouble(homeSelector.pList.get(10).get(2))), 2);

            String recordAway = teamName + "*" + (int) awayMatches + "*"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(5).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(5).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(5).get(1)) - Double.parseDouble(awaySelector.pList.get(5).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(5).get(1)) + Double.parseDouble(awaySelector.pList.get(5).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(6).get(1)) , 2) + "_"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(6).get(2)) , 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(6).get(1)) - Double.parseDouble(awaySelector.pList.get(6).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(6).get(1)) + Double.parseDouble(awaySelector.pList.get(6).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(17).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(17).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(17).get(1)) - Double.parseDouble(awaySelector.pList.get(17).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(17).get(1)) + Double.parseDouble(awaySelector.pList.get(17).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(18).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(18).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(18).get(1)) - Double.parseDouble(awaySelector.pList.get(18).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(18).get(1)) + Double.parseDouble(awaySelector.pList.get(18).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(19).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(19).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(19).get(1)) - Double.parseDouble(awaySelector.pList.get(19).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(19).get(1)) + Double.parseDouble(awaySelector.pList.get(19).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(7).get(1)) * awayMatches, 2) + "_"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(7).get(2)) * awayMatches, 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(7).get(1)) - Double.parseDouble(awaySelector.pList.get(7).get(2))) * awayMatches, 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(7).get(1)) + Double.parseDouble(awaySelector.pList.get(7).get(2))) * awayMatches, 2) + "*"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(22).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(22).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(22).get(1)) - Double.parseDouble(awaySelector.pList.get(22).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(22).get(1)) + Double.parseDouble(awaySelector.pList.get(22).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(23).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(23).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(23).get(1)) - Double.parseDouble(awaySelector.pList.get(23).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(23).get(1)) + Double.parseDouble(awaySelector.pList.get(23).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(24).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(24).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(24).get(1)) - Double.parseDouble(awaySelector.pList.get(24).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(24).get(1)) + Double.parseDouble(awaySelector.pList.get(24).get(2))), 2) + "*"
                    + String.valueOf(realAT) + "_"
                    + String.valueOf(realAT_Opp) + "_"
                    + String.valueOf(0) + "_"
                    + String.valueOf(0) + "*"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(11).get(1)) * awayMatches, 2) + "_"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(11).get(2)) * awayMatches, 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(11).get(1)) - Double.parseDouble(awaySelector.pList.get(11).get(2))) * awayMatches, 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(11).get(1)) + Double.parseDouble(awaySelector.pList.get(11).get(2))) * awayMatches, 2) + "*"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(20).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(20).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(20).get(1)) - Double.parseDouble(awaySelector.pList.get(20).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(20).get(1)) + Double.parseDouble(awaySelector.pList.get(20).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(12).get(1)) * awayMatches, 2) + "_"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(12).get(2)) * awayMatches, 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(12).get(1)) - Double.parseDouble(awaySelector.pList.get(12).get(2))) * awayMatches, 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(12).get(1)) + Double.parseDouble(awaySelector.pList.get(12).get(2))) * awayMatches, 2) + "*"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(13).get(1)) * awayMatches, 2) + "_"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(13).get(2)) * awayMatches, 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(13).get(1)) - Double.parseDouble(awaySelector.pList.get(13).get(2))) * awayMatches, 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(13).get(1)) + Double.parseDouble(awaySelector.pList.get(13).get(2))) * awayMatches, 2) + "*"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(14).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(14).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(14).get(1)) - Double.parseDouble(awaySelector.pList.get(14).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(14).get(1)) + Double.parseDouble(awaySelector.pList.get(14).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(15).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(15).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(15).get(1)) - Double.parseDouble(awaySelector.pList.get(15).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(15).get(1)) + Double.parseDouble(awaySelector.pList.get(15).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(21).get(1)) * awayMatches, 2) + "_"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(21).get(2)) * awayMatches, 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(21).get(1)) - Double.parseDouble(awaySelector.pList.get(21).get(2))) * awayMatches, 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(21).get(1)) + Double.parseDouble(awaySelector.pList.get(21).get(2))) * awayMatches, 2) + "*"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(9).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(9).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(9).get(1)) - Double.parseDouble(awaySelector.pList.get(9).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(9).get(1)) + Double.parseDouble(awaySelector.pList.get(9).get(2))), 2) + "*"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(10).get(1)), 2) + "_"
                    + MyMath.round(Double.parseDouble(awaySelector.pList.get(10).get(2)), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(10).get(1)) - Double.parseDouble(awaySelector.pList.get(10).get(2))), 2) + "_"
                    + MyMath.round((Double.parseDouble(awaySelector.pList.get(10).get(1)) + Double.parseDouble(awaySelector.pList.get(10).get(2))), 2);

            this.overallStatsTable.add(recordTotal);
            this.homeStatsTable.add(recordHome);
            this.awayStatsTable.add(recordAway);

            jpb.setValue((int) ((i+1) / (double) listOfTeams.length * 100));
        }
    }
}
