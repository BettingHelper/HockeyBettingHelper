package sample;

import java.util.ArrayList;

public class Correlator {

    public Correlator(){
    }

    public static double getCorrelationOfParams(String teamName, String param1, String param2, ArrayList<Match> listOfMatches, ArrayList<ArrayList<String>> paramsList){
        double res = 0;
        int matches = listOfMatches.size();

        String[] headsCorr2 = {"ГолыОВ", "Реал.больш.", "Реал.меньш.", "1пер", "2пер", "3пер", "БроскиВСтвор", "Блок.броски", "Вбрасывания", "Силовые приемы", "Кол2минУд"};

        if ( (param1.equals("ГолыОВ")) && (param2.equals("Реал.больш.")) ){
            double MOGoals = Double.parseDouble(paramsList.get(5).get(1))/matches;
            double MOReal = Double.parseDouble(paramsList.get(9).get(1))/Double.parseDouble(paramsList.get(8).get(1));

            double verhSumm = 0;
            double nizSumm1 = 0;
            double nizSumm2 = 0;

            for (int i=0; i<listOfMatches.size(); i++){
                if (teamName.equals(listOfMatches.get(i).homeTeam)){
                    double real = 0;
                    if (listOfMatches.get(i).homeNumberOfPP > 0)
                        real = listOfMatches.get(i).homeGoalsInPP / (double) listOfMatches.get(i).homeNumberOfPP;

                    verhSumm += (listOfMatches.get(i).homeScore - MOGoals)*(real - MOReal);
                    nizSumm1 += Math.pow( (listOfMatches.get(i).homeScore - MOGoals) , 2);
                    nizSumm2 += Math.pow( (real - MOReal) , 2);
                } else{
                    double real = 0;
                    if (listOfMatches.get(i).awayNumberOfPP > 0)
                        real = listOfMatches.get(i).awayGoalsInPP / (double) listOfMatches.get(i).awayNumberOfPP;
                    verhSumm += (listOfMatches.get(i).awayScore - MOGoals)*(real - MOReal);
                    nizSumm1 += Math.pow( (listOfMatches.get(i).awayScore - MOGoals) , 2);
                    nizSumm2 += Math.pow( (real - MOReal) , 2);
                }
            }
            res = verhSumm / Math.sqrt(nizSumm1*nizSumm2);
        }

        if ( (param1.equals("ГолыОВПротивника")) && (param2.equals("Реал.больш.Противника")) ){
            double MOGoals = Double.parseDouble(paramsList.get(5).get(2))/matches;
            double MOReal = Double.parseDouble(paramsList.get(9).get(2))/Double.parseDouble(paramsList.get(8).get(2));

            double verhSumm = 0;
            double nizSumm1 = 0;
            double nizSumm2 = 0;

            for (int i=0; i<listOfMatches.size(); i++){
                if (teamName.equals(listOfMatches.get(i).awayTeam)){
                    double real = 0;
                    if (listOfMatches.get(i).homeNumberOfPP > 0)
                        real = listOfMatches.get(i).homeGoalsInPP / (double) listOfMatches.get(i).homeNumberOfPP;

                    verhSumm += (listOfMatches.get(i).homeScore - MOGoals)*(real - MOReal);
                    nizSumm1 += Math.pow( (listOfMatches.get(i).homeScore - MOGoals) , 2);
                    nizSumm2 += Math.pow( (real - MOReal) , 2);
                } else{
                    double real = 0;
                    if (listOfMatches.get(i).awayNumberOfPP > 0)
                        real = listOfMatches.get(i).awayGoalsInPP / (double) listOfMatches.get(i).awayNumberOfPP;
                    verhSumm += (listOfMatches.get(i).awayScore - MOGoals)*(real - MOReal);
                    nizSumm1 += Math.pow( (listOfMatches.get(i).awayScore - MOGoals) , 2);
                    nizSumm2 += Math.pow( (real - MOReal) , 2);
                }
            }
            res = verhSumm / Math.sqrt(nizSumm1*nizSumm2);
        }

        if ( (param1.equals("ГолыОВФора")) && (param2.equals("Реал.больш.")) ){
            double MOGoals = (Double.parseDouble(paramsList.get(5).get(1)) - Double.parseDouble(paramsList.get(5).get(2)))/matches;
            double MOReal = Double.parseDouble(paramsList.get(9).get(1))/Double.parseDouble(paramsList.get(8).get(1));

            double verhSumm = 0;
            double nizSumm1 = 0;
            double nizSumm2 = 0;

            for (int i=0; i<listOfMatches.size(); i++){
                if (teamName.equals(listOfMatches.get(i).homeTeam)){
                    double real = 0;
                    if (listOfMatches.get(i).homeNumberOfPP > 0)
                        real = listOfMatches.get(i).homeGoalsInPP / (double) listOfMatches.get(i).homeNumberOfPP;

                    verhSumm += (listOfMatches.get(i).homeScore - listOfMatches.get(i).awayScore - MOGoals)*(real - MOReal);
                    nizSumm1 += Math.pow( (listOfMatches.get(i).homeScore - MOGoals) , 2);
                    nizSumm2 += Math.pow( (real - MOReal) , 2);
                } else{
                    double real = 0;
                    if (listOfMatches.get(i).awayNumberOfPP > 0)
                        real = listOfMatches.get(i).awayGoalsInPP / (double) listOfMatches.get(i).awayNumberOfPP;
                    verhSumm += (listOfMatches.get(i).awayScore - listOfMatches.get(i).homeScore - MOGoals)*(real - MOReal);
                    nizSumm1 += Math.pow( (listOfMatches.get(i).awayScore - MOGoals) , 2);
                    nizSumm2 += Math.pow( (real - MOReal) , 2);
                }
            }
            res = verhSumm / Math.sqrt(nizSumm1*nizSumm2);
        }

        if ( (param1.equals("ГолыОВ")) && (param2.equals("Реал.меньш.")) ){
            double MOGoals = Double.parseDouble(paramsList.get(5).get(1))/matches;
            double MOReal = 1 - Double.parseDouble(paramsList.get(9).get(2))/Double.parseDouble(paramsList.get(8).get(2));

            double verhSumm = 0;
            double nizSumm1 = 0;
            double nizSumm2 = 0;

            for (int i=0; i<listOfMatches.size(); i++){
                if (teamName.equals(listOfMatches.get(i).homeTeam)){
                    double real = 0;
                    if (listOfMatches.get(i).homeNumberOfPP > 0)
                        real = 1 - listOfMatches.get(i).awayGoalsInPP / (double) listOfMatches.get(i).awayNumberOfPP;

                    verhSumm += (listOfMatches.get(i).homeScore - MOGoals)*(real - MOReal);
                    nizSumm1 += Math.pow( (listOfMatches.get(i).homeScore - MOGoals) , 2);
                    nizSumm2 += Math.pow( (real - MOReal) , 2);
                } else{
                    double real = 0;
                    if (listOfMatches.get(i).awayNumberOfPP > 0)
                        real = 1 - listOfMatches.get(i).homeGoalsInPP / (double) listOfMatches.get(i).homeNumberOfPP;
                    verhSumm += (listOfMatches.get(i).awayScore - MOGoals)*(real - MOReal);
                    nizSumm1 += Math.pow( (listOfMatches.get(i).awayScore - MOGoals) , 2);
                    nizSumm2 += Math.pow( (real - MOReal) , 2);
                }
            }
            res = verhSumm / Math.sqrt(nizSumm1*nizSumm2);
        }

        if ( (param1.equals("ГолыОВПротивника")) && (param2.equals("Реал.меньш.Противника")) ){
            double MOGoals = Double.parseDouble(paramsList.get(5).get(2))/matches;
            double MOReal = 1 - Double.parseDouble(paramsList.get(9).get(1))/Double.parseDouble(paramsList.get(8).get(1));

            double verhSumm = 0;
            double nizSumm1 = 0;
            double nizSumm2 = 0;

            for (int i=0; i<listOfMatches.size(); i++){
                if (teamName.equals(listOfMatches.get(i).awayTeam)){
                    double real = 0;
                    if (listOfMatches.get(i).homeNumberOfPP > 0)
                        real = 1 - listOfMatches.get(i).homeGoalsInPP / (double) listOfMatches.get(i).homeNumberOfPP;

                    verhSumm += (listOfMatches.get(i).homeScore - MOGoals)*(real - MOReal);
                    nizSumm1 += Math.pow( (listOfMatches.get(i).homeScore - MOGoals) , 2);
                    nizSumm2 += Math.pow( (real - MOReal) , 2);
                } else{
                    double real = 0;
                    if (listOfMatches.get(i).awayNumberOfPP > 0)
                        real = 1 - listOfMatches.get(i).awayGoalsInPP / (double) listOfMatches.get(i).awayNumberOfPP;
                    verhSumm += (listOfMatches.get(i).awayScore - MOGoals)*(real - MOReal);
                    nizSumm1 += Math.pow( (listOfMatches.get(i).awayScore - MOGoals) , 2);
                    nizSumm2 += Math.pow( (real - MOReal) , 2);
                }
            }
            res = verhSumm / Math.sqrt(nizSumm1*nizSumm2);
        }

        if ( (param1.equals("ГолыОВФора")) && (param2.equals("Реал.меньш.")) ){
            double MOGoals = (Double.parseDouble(paramsList.get(5).get(1)) - Double.parseDouble(paramsList.get(5).get(2)))/matches;
            double MOReal = 1 - Double.parseDouble(paramsList.get(9).get(2))/Double.parseDouble(paramsList.get(8).get(2));

            double verhSumm = 0;
            double nizSumm1 = 0;
            double nizSumm2 = 0;

            for (int i=0; i<listOfMatches.size(); i++){
                if (teamName.equals(listOfMatches.get(i).homeTeam)){
                    double real = 0;
                    if (listOfMatches.get(i).homeNumberOfPP > 0)
                        real = 1 - listOfMatches.get(i).awayGoalsInPP / (double) listOfMatches.get(i).awayNumberOfPP;

                    verhSumm += (listOfMatches.get(i).homeScore - listOfMatches.get(i).awayScore - MOGoals)*(real - MOReal);
                    nizSumm1 += Math.pow( (listOfMatches.get(i).homeScore - MOGoals) , 2);
                    nizSumm2 += Math.pow( (real - MOReal) , 2);
                } else{
                    double real = 0;
                    if (listOfMatches.get(i).awayNumberOfPP > 0)
                        real = 1 - listOfMatches.get(i).homeGoalsInPP / (double) listOfMatches.get(i).homeNumberOfPP;
                    verhSumm += (listOfMatches.get(i).awayScore - listOfMatches.get(i).homeScore - MOGoals)*(real - MOReal);
                    nizSumm1 += Math.pow( (listOfMatches.get(i).awayScore - MOGoals) , 2);
                    nizSumm2 += Math.pow( (real - MOReal) , 2);
                }
            }
            res = verhSumm / Math.sqrt(nizSumm1*nizSumm2);
        }

        //"ГолыОВ", "Фора1пер"

        if ( (param1.equals("ГолыОВ")) && (param2.equals("Фора1пер")) ){
            double MOGoals = Double.parseDouble(paramsList.get(5).get(1))/matches;
            double MOHand1per = Double.parseDouble(paramsList.get(17).get(1))/matches;

            double verhSumm = 0;
            double nizSumm1 = 0;
            double nizSumm2 = 0;

            for (int i=0; i<listOfMatches.size(); i++){
                if (teamName.equals(listOfMatches.get(i).homeTeam)){
                    double real = 0;
                    if (listOfMatches.get(i).homeNumberOfPP > 0)
                        real = 1 - listOfMatches.get(i).awayGoalsInPP / (double) listOfMatches.get(i).awayNumberOfPP;

                    verhSumm += (listOfMatches.get(i).homeScore - MOGoals)*(real - MOHand1per);
                    nizSumm1 += Math.pow( (listOfMatches.get(i).homeScore - MOGoals) , 2);
                    nizSumm2 += Math.pow( (real - MOHand1per) , 2);
                } else{
                    double real = 0;
                    if (listOfMatches.get(i).awayNumberOfPP > 0)
                        real = 1 - listOfMatches.get(i).homeGoalsInPP / (double) listOfMatches.get(i).homeNumberOfPP;
                    verhSumm += (listOfMatches.get(i).awayScore - MOGoals)*(real - MOHand1per);
                    nizSumm1 += Math.pow( (listOfMatches.get(i).awayScore - MOGoals) , 2);
                    nizSumm2 += Math.pow( (real - MOHand1per) , 2);
                }
            }
            res = verhSumm / Math.sqrt(nizSumm1*nizSumm2);
        }

        if ( (param1.equals("ГолыОВПротивника")) && (param2.equals("Фора1перПротивника")) ){
            double MOGoals = Double.parseDouble(paramsList.get(5).get(2))/matches;
            double MOHand1per = Double.parseDouble(paramsList.get(17).get(2))/matches;

            double verhSumm = 0;
            double nizSumm1 = 0;
            double nizSumm2 = 0;

            for (int i=0; i<listOfMatches.size(); i++){
                if (teamName.equals(listOfMatches.get(i).awayTeam)){
                    double real = 0;
                    if (listOfMatches.get(i).homeNumberOfPP > 0)
                        real = 1 - listOfMatches.get(i).homeGoalsInPP / (double) listOfMatches.get(i).homeNumberOfPP;

                    verhSumm += (listOfMatches.get(i).homeScore - MOGoals)*(real - MOHand1per);
                    nizSumm1 += Math.pow( (listOfMatches.get(i).homeScore - MOGoals) , 2);
                    nizSumm2 += Math.pow( (real - MOHand1per) , 2);
                } else{
                    double real = 0;
                    if (listOfMatches.get(i).awayNumberOfPP > 0)
                        real = 1 - listOfMatches.get(i).awayGoalsInPP / (double) listOfMatches.get(i).awayNumberOfPP;
                    verhSumm += (listOfMatches.get(i).awayScore - MOGoals)*(real - MOHand1per);
                    nizSumm1 += Math.pow( (listOfMatches.get(i).awayScore - MOGoals) , 2);
                    nizSumm2 += Math.pow( (real - MOHand1per) , 2);
                }
            }
            res = verhSumm / Math.sqrt(nizSumm1*nizSumm2);
        }

        if ( (param1.equals("ГолыОВФора")) && (param2.equals("Фора1перФора")) ){
            double MOGoals = (Double.parseDouble(paramsList.get(5).get(1)) - Double.parseDouble(paramsList.get(5).get(2)))/matches;
            double MOHand1per = (Double.parseDouble(paramsList.get(17).get(1)) - Double.parseDouble(paramsList.get(17).get(2)))/matches;

            double verhSumm = 0;
            double nizSumm1 = 0;
            double nizSumm2 = 0;

            for (int i=0; i<listOfMatches.size(); i++){
                if (teamName.equals(listOfMatches.get(i).homeTeam)){
                    double real = 0;
                    if (listOfMatches.get(i).homeNumberOfPP > 0)
                        real = 1 - listOfMatches.get(i).awayGoalsInPP / (double) listOfMatches.get(i).awayNumberOfPP;

                    verhSumm += (listOfMatches.get(i).homeScore - listOfMatches.get(i).awayScore - MOGoals)*(real - MOHand1per);
                    nizSumm1 += Math.pow( (listOfMatches.get(i).homeScore - MOGoals) , 2);
                    nizSumm2 += Math.pow( (real - MOHand1per) , 2);
                } else{
                    double real = 0;
                    if (listOfMatches.get(i).awayNumberOfPP > 0)
                        real = 1 - listOfMatches.get(i).homeGoalsInPP / (double) listOfMatches.get(i).homeNumberOfPP;
                    verhSumm += (listOfMatches.get(i).awayScore - listOfMatches.get(i).homeScore - MOGoals)*(real - MOHand1per);
                    nizSumm1 += Math.pow( (listOfMatches.get(i).awayScore - MOGoals) , 2);
                    nizSumm2 += Math.pow( (real - MOHand1per) , 2);
                }
            }
            res = verhSumm / Math.sqrt(nizSumm1*nizSumm2);
        }


        /*if ( (param1.equals("ВладениеПротивника")) && (param2.equals("УгловыеПротивника")) ){
            double MOposs = Double.parseDouble(paramsList.get(6).get(2));
            double MOcorners = Double.parseDouble(paramsList.get(10).get(2));

            double verhSumm = 0;
            double nizSumm1 = 0;
            double nizSumm2 = 0;

            for (int i=0; i<listOfMatches.size(); i++){
                if (teamName.equals(listOfMatches.get(i).homeTeam)){
                    verhSumm += (listOfMatches.get(i).awayBallPossetion - MOposs)*(listOfMatches.get(i).awayCorners - MOcorners);
                    nizSumm1 += Math.pow( (listOfMatches.get(i).awayBallPossetion - MOposs) , 2);
                    nizSumm2 += Math.pow( (listOfMatches.get(i).awayCorners - MOcorners) , 2);
                } else{
                    verhSumm += (listOfMatches.get(i).homeBallPossetion - MOposs)*(listOfMatches.get(i).homeCorners - MOcorners);
                    nizSumm1 += Math.pow( (listOfMatches.get(i).homeBallPossetion - MOposs) , 2);
                    nizSumm2 += Math.pow( (listOfMatches.get(i).homeCorners - MOcorners) , 2);
                }
            }
            res = verhSumm / Math.sqrt(nizSumm1*nizSumm2);
        }

        if ( (param1.equals("Владение")) && (param2.equals("УгловыеФора")) ){
            double MOposs = Double.parseDouble(paramsList.get(6).get(1));
            double MOcorners = Double.parseDouble(paramsList.get(10).get(1)) - Double.parseDouble(paramsList.get(10).get(2));

            double verhSumm = 0;
            double nizSumm1 = 0;
            double nizSumm2 = 0;

            for (int i=0; i<listOfMatches.size(); i++){
                if (teamName.equals(listOfMatches.get(i).homeTeam)){
                    verhSumm += (listOfMatches.get(i).homeBallPossetion - MOposs)*(listOfMatches.get(i).homeCorners - listOfMatches.get(i).awayCorners - MOcorners);
                    nizSumm1 += Math.pow( (listOfMatches.get(i).homeBallPossetion - MOposs) , 2);
                    nizSumm2 += Math.pow( (listOfMatches.get(i).homeCorners - listOfMatches.get(i).awayCorners - MOcorners) , 2);
                } else{
                    verhSumm += (listOfMatches.get(i).awayBallPossetion - MOposs)*(listOfMatches.get(i).awayCorners - listOfMatches.get(i).homeCorners - MOcorners);
                    nizSumm1 += Math.pow( (listOfMatches.get(i).awayBallPossetion - MOposs) , 2);
                    nizSumm2 += Math.pow( (listOfMatches.get(i).awayCorners - listOfMatches.get(i).homeCorners - MOcorners) , 2);
                }
            }
            res = verhSumm / Math.sqrt(nizSumm1*nizSumm2);
        }*/


        return res;
    }
}
