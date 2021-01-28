package sample;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.*;
import java.util.ArrayList;

// определяем корневой элемент
    @XmlRootElement(name = "Team")
    // определяем последовательность тегов в XML
    @XmlType(propOrder = {"teamName", "matches", "wins", "winsOT", "loses", "losesOT",
            /*"goalsScored", "goalsConcedered", "goalsDifference",
            "goalsScoredOTandBullits", "goalsConcederedOTandBullits", "goalsOTandBullitsDifference", "points",
            "shotsOnTarget", "shotsOnTargetOpponent", "numberOfPP", "numberOfPPopponent", "goalsInPP", "goalsInPPopponent",
            "shorthandedGoals", "shorthandedGoalsOpponent", "numberOffFaceoffs", "faceoffsWon", "blockedShots", "blockedShotsOpponent",
            "hits", "hitsOpponent", "penaltyMinutes", "penaltyMinutesOpponent", "numberOf2MinPenalties", "numberOf2MinPenaltiesOpponent",
            "shotsOnTarget1stPeriod", "shotsOnTarget2ndPeriod", "shotsOnTarget3rdPeriod",
            "shotsOnTarget1stPeriodOpp", "shotsOnTarget2ndPeriodOpp", "shotsOnTarget3rdPeriodOpp",
            "penaltyMinutes1stPeriod", "penaltyMinutes2ndPeriod", "penaltyMinutes3rdPeriod",
            "penaltyMinutes1stPeriodOpp", "penaltyMinutes2ndPeriodOpp", "penaltyMinutes3rdPeriodOpp",
            "twoMinPenalties1stPeriod", "twoMinPenalties2ndPeriod", "twoMinPenalties3rdPeriod",
            "twoMinPenalties1stPeriodOpp", "twoMinPenalties2ndPeriodOpp", "twoMinPenalties3rdPeriodOpp",
            "timeInAttack", "timeInAttackOpp", "timeInAttack1stPeriod", "timeInAttack1stPeriodOpp", "timeInAttack2ndPeriod",
            "timeInAttack2ndPeriodOpp", "timeInAttack3rdPeriod", "timeInAttack3rdPeriodOpp", "missedShots", "missedShotsOpponent",*/
            "matchList"})

    public class Team {

        public String teamName;
        //======================= Основные параметры команды
        public int matches;
        public int wins;
        public int winsOT;
        public int loses;
        public int losesOT;
        /*public int goalsScored;
        public int goalsConcedered;
        public int goalsDifference;
        public int goalsScoredOTandBullits;
        public int goalsConcederedOTandBullits;
        public int goalsOTandBullitsDifference;
        public int points;
        //======================= Статистические параметры команды за весь турнир и за матч (Заполняемые)
        public int shotsOnTarget;
        public int shotsOnTargetOpponent;
        public int numberOfPP;
        public int numberOfPPopponent;
        public int goalsInPP;
        public int goalsInPPopponent;
        public int shorthandedGoals;
        public int shorthandedGoalsOpponent;
        public int numberOffFaceoffs;
        public int faceoffsWon;
        public int blockedShots;
        public int blockedShotsOpponent;
        public int hits;
        public int hitsOpponent;
        public int penaltyMinutes;
        public int penaltyMinutesOpponent;
        public int numberOf2MinPenalties;
        public int numberOf2MinPenaltiesOpponent;
        public int shotsOnTarget1stPeriod;
        public int shotsOnTarget2ndPeriod;
        public int shotsOnTarget3rdPeriod;
        public int shotsOnTarget1stPeriodOpp;
        public int shotsOnTarget2ndPeriodOpp;
        public int shotsOnTarget3rdPeriodOpp;
        public int penaltyMinutes1stPeriod;
        public int penaltyMinutes2ndPeriod;
        public int penaltyMinutes3rdPeriod;
        public int penaltyMinutes1stPeriodOpp;
        public int penaltyMinutes2ndPeriodOpp;
        public int penaltyMinutes3rdPeriodOpp;
        public int twoMinPenalties1stPeriod;
        public int twoMinPenalties2ndPeriod;
        public int twoMinPenalties3rdPeriod;
        public int twoMinPenalties1stPeriodOpp;
        public int twoMinPenalties2ndPeriodOpp;
        public int twoMinPenalties3rdPeriodOpp;
        public int timeInAttack;
        public int timeInAttackOpp;
        public int timeInAttack1stPeriod;
        public int timeInAttack1stPeriodOpp;
        public int timeInAttack2ndPeriod;
        public int timeInAttack2ndPeriodOpp;
        public int timeInAttack3rdPeriod;
        public int timeInAttack3rdPeriodOpp;
        public int missedShots;
        public int missedShotsOpponent;*/

        //======================= Список матчей
        public ArrayList<String> matchList;

        public Team() {
            this.matchList = new ArrayList<>();
        }

        public Team(String name) {

            this.teamName = name;
            this.matches= 0;
            this.wins = 0;
            this.winsOT = 0;
            this.loses = 0;
            this.losesOT = 0;
            /*this.goalsScored = 0;
            this.goalsConcedered = 0;
            this.goalsDifference = 0;
            this.goalsScoredOTandBullits = 0;
            this.goalsConcederedOTandBullits = 0;
            this.goalsOTandBullitsDifference = 0;
            this.points = 0;
            this.shotsOnTarget = 0;
            this.shotsOnTargetOpponent = 0;
            this.numberOfPP = 0;
            this.numberOfPPopponent = 0;
            this.goalsInPP = 0;
            this.goalsInPPopponent = 0;
            this.shorthandedGoals = 0;
            this.shorthandedGoalsOpponent = 0;
            this.numberOffFaceoffs = 0;
            this.faceoffsWon = 0;
            this.blockedShots = 0;
            this.blockedShotsOpponent = 0;
            this.hits = 0;
            this.hitsOpponent = 0;
            this.penaltyMinutes = 0;
            this.penaltyMinutesOpponent = 0;
            this.numberOf2MinPenalties = 0;
            this.numberOf2MinPenaltiesOpponent = 0;
            this.shotsOnTarget1stPeriod = 0;
            this.shotsOnTarget2ndPeriod = 0;
            this.shotsOnTarget3rdPeriod = 0;
            this.shotsOnTarget1stPeriodOpp = 0;
            this.shotsOnTarget2ndPeriodOpp = 0;
            this.shotsOnTarget3rdPeriodOpp = 0;
            this.penaltyMinutes1stPeriod = 0;
            this.penaltyMinutes2ndPeriod = 0;
            this.penaltyMinutes3rdPeriod = 0;
            this.penaltyMinutes1stPeriodOpp = 0;
            this.penaltyMinutes2ndPeriodOpp = 0;
            this.penaltyMinutes3rdPeriodOpp = 0;
            this.twoMinPenalties1stPeriod = 0;
            this.twoMinPenalties2ndPeriod = 0;
            this.twoMinPenalties3rdPeriod = 0;
            this.twoMinPenalties1stPeriodOpp = 0;
            this.twoMinPenalties2ndPeriodOpp = 0;
            this.twoMinPenalties3rdPeriodOpp = 0;
            this.timeInAttack = 0;
            this.timeInAttackOpp = 0;
            this.timeInAttack1stPeriod = 0;
            this.timeInAttack1stPeriodOpp = 0;
            this.timeInAttack2ndPeriod = 0;
            this.timeInAttack2ndPeriodOpp = 0;
            this.timeInAttack3rdPeriod = 0;
            this.timeInAttack3rdPeriodOpp = 0;
            this.missedShots = 0;
        this.missedShotsOpponent = 0;*/

        this.matchList = new ArrayList<>();

    }

    public static Team getTeamFromFileWithSeason(String teamName, String season, String leagueName){
        String path = "database/" + season + "/" + leagueName + "/Teams/" + teamName+".xml";
        Team team = null;
        int n = Settings.getNumberOfAccount();
        try {
            FTPLoader.downloadFile(Settings.getLogin(n), Settings.getPassword(n), "/data/hockey/" + path, path);
            LogWriter.writeLog("Successful download of " + teamName + ".xml");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Team.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();
            team = (Team) un.unmarshal(new File(path));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return team;
    }

    public static double roundResult(double d, int precise) {
        precise = (int) Math.pow(10,precise);
        d = d*precise;
        int i = (int) Math.round(d);
        return (double) i/precise;
    }

    public static String getShortName(String teamName){
        String result = "";
        try {
            File fileDir = new File("database/Teams.txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF-8"));
            String str;
            while (((str = in.readLine()) != null)) {
                if (str.split("=")[0].equals(teamName)){
                    result = str.split("=")[5];
                }
            }
            in.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static String getNameFromFullName(String fullName){
        String result = "";
        try {
            File fileDir = new File("database/Teams.txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF-8"));
            String str;
            while (((str = in.readLine()) != null)) {
                if (str.split("=")[1].equals(fullName)){
                    result = str.split("=")[0];
                }
            }
            in.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static String getFullName(String name){
        String result = "";
        try {
            File fileDir = new File("database/Teams.txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF-8"));
            String str;
            while (((str = in.readLine()) != null)) {
                if (str.split("=")[0].equals(name)){
                    result = str.split("=")[1];
                }
            }
            in.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static String getLeague(String teamName){
        String result = "";
        try {
            File fileDir = new File("database/Teams.txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF-8"));
            String str;
            while (((str = in.readLine()) != null)) {
                if (str.split("=")[0].equals(teamName)){
                    result = str.split("=")[2];
                }
            }
            in.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static String getWebsite(String teamName){
        String result = "";
        try {
            File fileDir = new File("database/Teams.txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF-8"));
            String str;
            while (((str = in.readLine()) != null)) {
                if (str.split("=")[0].equals(teamName)){
                    result = str.split("=")[6];
                }
            }
            in.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static String getTwitter(String teamName){
        String result = "";
        try {
            File fileDir = new File("database/Teams.txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF-8"));
            String str;
            while (((str = in.readLine()) != null)) {
                if (str.split("=")[0].equals(teamName)){
                    result = str.split("=")[7];
                }
            }
            in.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static boolean isTeamFoundInSeason(String teamName, String season){
        boolean flagResult = false;
        try {
            File fileDir = new File("database/" + season + "/leagues/" + Team.getLeague(teamName) + ".txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF-8"));
            String str;
            while (((str = in.readLine()) != null) && !flagResult) {
                if (str.equals(teamName)){
                    flagResult = true;
                }
            }
            in.close();
        } catch (IOException e){
            //System.out.println(e.getMessage());
        }
        return flagResult;
    }
}