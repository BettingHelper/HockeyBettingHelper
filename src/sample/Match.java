package sample;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.*;

// определяем корневой элемент
@XmlRootElement(name = "Match")
// определяем последовательность тегов в XML
@XmlType(propOrder = {"homeTeam", "awayTeam", "homeScore", "awayScore", "homeScoreOT", "homeScoreBullits",
        "awayScoreOT", "awayScoreBullits", "homeShotsOnTarget", "awayShotsOnTarget",
        "homeNumberOfPP", "awayNumberOfPP", "homeGoalsInPP", "awayGoalsInPP", "homeShorthandedGoals",
        "awayShorthandedGoals", "numberOffFaceoffs", "homeFaceoffsWon", "awayFaceoffsWon", "homeBlockedShots",
        "awayBlockedShots", "homeHits", "awayHits", "homePenaltyMinutes", "awayPenaltyMinutes",
        "home2MinPenalties", "away2MinPenalties",
        "homeScore1stPeriod", "homeScore2ndPeriod", "homeScore3rdPeriod",
        "awayScore1stPeriod", "awayScore2ndPeriod", "awayScore3rdPeriod",
        "homeShotsOnTarget1stPeriod", "homeShotsOnTarget2ndPeriod", "homeShotsOnTarget3rdPeriod",
        "awayShotsOnTarget1stPeriod", "awayShotsOnTarget2ndPeriod", "awayShotsOnTarget3rdPeriod",
        "homePenaltyMinutes1stPeriod", "homePenaltyMinutes2ndPeriod", "homePenaltyMinutes3rdPeriod",
        "awayPenaltyMinutes1stPeriod", "awayPenaltyMinutes2ndPeriod", "awayPenaltyMinutes3rdPeriod",
        "home2MinPenalties1stPeriod", "home2MinPenalties2ndPeriod", "home2MinPenalties3rdPeriod",
        "away2MinPenalties1stPeriod", "away2MinPenalties2ndPeriod", "away2MinPenalties3rdPeriod",
        "homeTimeInAttack", "awayTimeInAttack", "homeTimeInAttack1stPeriod", "awayTimeInAttack1stPeriod",
        "homeTimeInAttack2ndPeriod", "awayTimeInAttack2ndPeriod", "homeTimeInAttack3rdPeriod",
        "awayTimeInAttack3rdPeriod", "homeMissedShots", "awayMissedShots", "date"})

public class Match {
    public String homeTeam;
    public String awayTeam;
    public int homeScore;
    public int awayScore;
    public int homeScoreOT;
    public int homeScoreBullits;
    public int awayScoreOT;
    public int awayScoreBullits;
    public int homeShotsOnTarget;
    public int awayShotsOnTarget;
    public int homeNumberOfPP;
    public int awayNumberOfPP;
    public int homeGoalsInPP;
    public int awayGoalsInPP;
    public int homeShorthandedGoals;
    public int awayShorthandedGoals;
    public int numberOffFaceoffs;
    public int homeFaceoffsWon;
    public int awayFaceoffsWon;
    public int homeBlockedShots;
    public int awayBlockedShots;
    public int homeHits;
    public int awayHits;
    public int homePenaltyMinutes;
    public int awayPenaltyMinutes;
    public int home2MinPenalties;
    public int away2MinPenalties;
    public int homeScore1stPeriod;
    public int homeScore2ndPeriod;
    public int homeScore3rdPeriod;
    public int awayScore1stPeriod;
    public int awayScore2ndPeriod;
    public int awayScore3rdPeriod;
    public int homeShotsOnTarget1stPeriod;
    public int awayShotsOnTarget1stPeriod;
    public int homePenaltyMinutes1stPeriod;
    public int awayPenaltyMinutes1stPeriod;
    public int home2MinPenalties1stPeriod;
    public int away2MinPenalties1stPeriod;
    public int homeShotsOnTarget2ndPeriod;
    public int awayShotsOnTarget2ndPeriod;
    public int homePenaltyMinutes2ndPeriod;
    public int awayPenaltyMinutes2ndPeriod;
    public int home2MinPenalties2ndPeriod;
    public int away2MinPenalties2ndPeriod;
    public int homeShotsOnTarget3rdPeriod;
    public int awayShotsOnTarget3rdPeriod;
    public int homePenaltyMinutes3rdPeriod;
    public int awayPenaltyMinutes3rdPeriod;
    public int home2MinPenalties3rdPeriod;
    public int away2MinPenalties3rdPeriod;
    public int homeTimeInAttack;
    public int awayTimeInAttack;
    public int homeTimeInAttack1stPeriod;
    public int awayTimeInAttack1stPeriod;
    public int homeTimeInAttack2ndPeriod;
    public int awayTimeInAttack2ndPeriod;
    public int homeTimeInAttack3rdPeriod;
    public int awayTimeInAttack3rdPeriod;
    public int homeMissedShots;
    public int awayMissedShots;

    public String date;

    public Match() {
    }

    public Match(String homeTeam, String awayTeam, String[][] stats, String date){
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = Integer.parseInt(stats[0][1]);
        this.awayScore = Integer.parseInt(stats[0][2]);
        this.homeScoreOT = Integer.parseInt(stats[1][1]);
        this.homeScoreBullits = Integer.parseInt(stats[2][1]);
        this.awayScoreOT = Integer.parseInt(stats[1][2]);
        this.awayScoreBullits = Integer.parseInt(stats[2][2]);
        this.homeShotsOnTarget = Integer.parseInt(stats[3][1]);
        this.awayShotsOnTarget = Integer.parseInt(stats[3][2]);
        this.homeNumberOfPP = Integer.parseInt(stats[4][1]);
        this.awayNumberOfPP = Integer.parseInt(stats[4][2]);
        this.homeGoalsInPP = Integer.parseInt(stats[5][1]);
        this.awayGoalsInPP = Integer.parseInt(stats[5][2]);
        this.homeShorthandedGoals = Integer.parseInt(stats[6][1]);
        this.awayShorthandedGoals = Integer.parseInt(stats[6][2]);
        this.homeFaceoffsWon = Integer.parseInt(stats[7][1]);
        this.awayFaceoffsWon = Integer.parseInt(stats[7][2]);
        this.numberOffFaceoffs = this.homeFaceoffsWon + this.awayFaceoffsWon;
        this.homeBlockedShots = Integer.parseInt(stats[8][1]);
        this.awayBlockedShots = Integer.parseInt(stats[8][2]);
        this.homeHits = Integer.parseInt(stats[9][1]);
        this.awayHits = Integer.parseInt(stats[9][2]);
        this.home2MinPenalties = Integer.parseInt(stats[10][1]);
        this.away2MinPenalties = Integer.parseInt(stats[10][2]);
        this.homeScore1stPeriod = Integer.parseInt(stats[11][1]);
        this.homeScore2ndPeriod = Integer.parseInt(stats[12][1]);
        this.homeScore3rdPeriod = Integer.parseInt(stats[13][1]);
        this.awayScore1stPeriod = Integer.parseInt(stats[11][2]);
        this.awayScore2ndPeriod = Integer.parseInt(stats[12][2]);
        this.awayScore3rdPeriod = Integer.parseInt(stats[13][2]);
        this.homePenaltyMinutes = Integer.parseInt(stats[14][1]);
        this.awayPenaltyMinutes = Integer.parseInt(stats[14][2]);
        this.homeShotsOnTarget1stPeriod = Integer.parseInt(stats[15][1]);
        this.awayShotsOnTarget1stPeriod = Integer.parseInt(stats[15][2]);
        this.homeShotsOnTarget2ndPeriod = Integer.parseInt(stats[16][2]);
        this.awayShotsOnTarget2ndPeriod = Integer.parseInt(stats[16][2]);
        this.homeShotsOnTarget3rdPeriod = Integer.parseInt(stats[17][1]);
        this.awayShotsOnTarget3rdPeriod = Integer.parseInt(stats[17][2]);
        this.homePenaltyMinutes1stPeriod = Integer.parseInt(stats[18][1]);
        this.awayPenaltyMinutes1stPeriod = Integer.parseInt(stats[18][2]);
        this.homePenaltyMinutes2ndPeriod = Integer.parseInt(stats[19][1]);
        this.awayPenaltyMinutes2ndPeriod = Integer.parseInt(stats[19][2]);
        this.homePenaltyMinutes3rdPeriod = Integer.parseInt(stats[20][1]);
        this.awayPenaltyMinutes3rdPeriod = Integer.parseInt(stats[20][2]);
        this.home2MinPenalties1stPeriod = Integer.parseInt(stats[21][1]);
        this.away2MinPenalties1stPeriod = Integer.parseInt(stats[21][2]);
        this.home2MinPenalties2ndPeriod = Integer.parseInt(stats[22][1]);
        this.away2MinPenalties2ndPeriod = Integer.parseInt(stats[22][2]);
        this.home2MinPenalties3rdPeriod = Integer.parseInt(stats[23][1]);
        this.away2MinPenalties3rdPeriod = Integer.parseInt(stats[23][2]);
        this.homeTimeInAttack = Integer.parseInt(stats[24][1]);
        this.awayTimeInAttack = Integer.parseInt(stats[24][2]);
        this.homeTimeInAttack1stPeriod = Integer.parseInt(stats[25][1]);
        this.awayTimeInAttack1stPeriod = Integer.parseInt(stats[25][2]);
        this.homeTimeInAttack2ndPeriod = Integer.parseInt(stats[26][1]);
        this.awayTimeInAttack2ndPeriod = Integer.parseInt(stats[26][2]);
        this.homeTimeInAttack3rdPeriod = Integer.parseInt(stats[27][1]);
        this.awayTimeInAttack3rdPeriod = Integer.parseInt(stats[27][2]);
        this.homeMissedShots = Integer.parseInt(stats[28][1]);
        this.awayMissedShots = Integer.parseInt(stats[28][2]);
        this.date = date;
    }

    public static Match getMatchFromFileByName(String matchName){
        Match match = null;
        try {
            // создаем объект JAXBContext - точку входа для JAXB
            JAXBContext jaxbContext = JAXBContext.newInstance(Match.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();
            match = (Match) un.unmarshal(new File(matchName));
        } catch (JAXBException e) {
            int n = Settings.getNumberOfAccount();
            try {
                FTPLoader.downloadFile(Settings.getLogin(n), Settings.getPassword(n), "/data/hockey/" + matchName, matchName);
                LogWriter.writeLog("Successful download of " + matchName);
                match = getMatchFromFileByNameWithoutDownload(matchName);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
                MyException.sendException(e.getStackTrace(), matchName + " не найден!");
            }
        }
        return match;
    }

    public static Match getMatchFromFileByNameWithoutDownload(String matchName){
        Match match = null;
        try {
            // создаем объект JAXBContext - точку входа для JAXB
            JAXBContext jaxbContext = JAXBContext.newInstance(Match.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();
            match = (Match) un.unmarshal(new File(matchName));
        } catch (JAXBException e) {
            MyException.sendException(e.getStackTrace(), matchName+ " содержит ошибку!");
        }
        return match;
    }

}
