package sample;

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

}
