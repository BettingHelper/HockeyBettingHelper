package sample;

import java.util.ArrayList;

public class MyMath {
    public static double round(double d, int precise) {
        precise = (int) Math.pow(10,precise);
        d = d*precise;
        int i = (int) Math.round(d);
        return (double) i/precise;
    }

    public static double getCorsi(double shotsOnTarget, double missedShots, double blockedShots){
        return MyMath.round(shotsOnTarget +missedShots + blockedShots,2);
    }

    public static double getFenwick(double shotsOnTarget, double missedShots){
        return MyMath.round(shotsOnTarget +missedShots,2);
    }

    public static ArrayList<Double> getWeights(int n, double w){
        ArrayList<Double> resultList = new ArrayList<>();

        for (int i=0; i<n; i++){
//           resultList.add((2*(i+1) - 1)/(double) n);
            resultList.add((i - (n-1) / 2.0)*2*w/n + 1);
        }
        return resultList;
    }

    public static double getValueBetweenAB(double a, double b, double weight){
        return MyMath.round( a+(b-a)*weight , 2);
    }

}
