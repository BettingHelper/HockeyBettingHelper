package sample;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

// определяем корневой элемент
@XmlRootElement(name = "Settings")
// определяем последовательность тегов в XML
@XmlType(propOrder = {"showGoals", "showGoalsOT", "showRealization", "showPowerPlay", "showShortHanded", "showTimeInAttack",
        "showGoals1per", "showGoals2per", "showGoals3per", "showGoalsShortHanded", "showShotsOnTarget",
        "showShotsOnTarget1per", "showShotsOnTarget2per", "showShotsOnTarget3per", "showMissedShots",
        "showBlockedShots", "showFaceoffs", "showHits", "showPenMinutes", "showNumberOf2MinutesPen", "showTotal",
        "windowResolution", "showSoTOpponents", "showPenOpponents", "trendPercent", "form", "windowsOnTop", "showGraphics",
        "trendsHA", "ip", "localTime", "useColors"/*, "dispMinMax"*/
})

public class Settings {
    public final static String TMP_DIR = System.getProperty("java.io.tmpdir");
    public boolean showGoals;
    public boolean showGoalsOT;
    public boolean showRealization;
    public boolean showPowerPlay;
    public boolean showShortHanded;
    public boolean showTimeInAttack;
    public boolean showGoals1per;
    public boolean showGoals2per;
    public boolean showGoals3per;
    public boolean showGoalsShortHanded;
    public boolean showShotsOnTarget;
    public boolean showShotsOnTarget1per;
    public boolean showShotsOnTarget2per;
    public boolean showShotsOnTarget3per;
    public boolean showMissedShots;
    public boolean showBlockedShots;
    public boolean showFaceoffs;
    public boolean showHits;
    public boolean showPenMinutes;
    public boolean showNumberOf2MinutesPen;
    public boolean showTotal;
    public String windowResolution;
    public String form;
    public String ip;
    public boolean showSoTOpponents;
    public boolean showPenOpponents;
    public String trendPercent;
    public boolean windowsOnTop;
    public boolean showGraphics;
    public int localTime;
    public boolean trendsHA;
    public boolean useColors;
//    public boolean dispMinMax;

    public Settings(){
    }

    public Settings(ArrayList<Boolean> arrayList, int width, int height, int percent, boolean windowsOnTop, String form, String ip, int localTime) {
        this.showGoals = arrayList.get(0);
        this.showGoalsOT = arrayList.get(1);
        this.showRealization = arrayList.get(2);
        this.showPowerPlay = arrayList.get(3);
        this.showShortHanded = arrayList.get(4);
        this.showTimeInAttack = arrayList.get(5);
        this.showGoals1per = arrayList.get(6);
        this.showGoals2per = arrayList.get(7);
        this.showGoals3per = arrayList.get(8);
        this.showGoalsShortHanded = arrayList.get(9);
        this.showShotsOnTarget = arrayList.get(10);
        this.showShotsOnTarget1per = arrayList.get(11);
        this.showShotsOnTarget2per = arrayList.get(12);
        this.showShotsOnTarget3per = arrayList.get(13);
        this.showMissedShots = arrayList.get(14);
        this.showBlockedShots = arrayList.get(15);
        this.showFaceoffs = arrayList.get(16);
        this.showHits = arrayList.get(17);
        this.showPenMinutes = arrayList.get(18);
        this.showNumberOf2MinutesPen = arrayList.get(19);
        this.showTotal = arrayList.get(20);
        this.windowResolution = String.valueOf(width) + "x" + String.valueOf(height);
        this.showSoTOpponents = arrayList.get(21);
        this.showPenOpponents = arrayList.get(22);
        this.showGraphics = arrayList.get(23);
        this.trendsHA = arrayList.get(24);
        this.trendPercent = String.valueOf(percent);
        this.windowsOnTop = windowsOnTop;
        this.form = form;
        this.ip = ip;
        this.localTime = localTime;
        this.useColors = arrayList.get(25);
//        this.dispMinMax = arrayList.get(26);
    }

    public ArrayList<Boolean> getShowList(){
        ArrayList<Boolean> result = new ArrayList<>();
        result.add(this.showGoals);
        result.add(this.showGoalsOT);
        result.add(this.showRealization);
        result.add(this.showPowerPlay);
        result.add(this.showShortHanded);
        result.add(this.showTimeInAttack);
        result.add(this.showGoals1per);
        result.add(this.showGoals2per);
        result.add(this.showGoals3per);
        result.add(this.showGoalsShortHanded);
        result.add(this.showShotsOnTarget);
        result.add(this.showShotsOnTarget1per);
        result.add(this.showShotsOnTarget2per);
        result.add(this.showShotsOnTarget3per);
        result.add(this.showMissedShots);
        result.add(this.showBlockedShots);
        result.add(this.showFaceoffs);
        result.add(this.showHits);
        result.add(this.showPenMinutes);
        result.add(this.showNumberOf2MinutesPen);
        result.add(this.showTotal);
        result.add(this.showSoTOpponents);
        result.add(this.showPenOpponents);
        result.add(this.showGraphics);
        result.add(this.trendsHA);
        result.add(this.useColors);
        //result.add(this.dispMinMax);

        return result;
    }

    public static Settings getSettingsFromFile(){
        String fileName = "settings/graphicSettings.xml";
        try {
            // создаем объект JAXBContext - точку входа для JAXB
            JAXBContext jaxbContext = JAXBContext.newInstance(Settings.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();

            return (Settings) un.unmarshal(new File(fileName));
        } catch (JAXBException e) {
            e.printStackTrace();
            PopupWindow window = new PopupWindow("<html>   Ошибка при синхронизации базы. Код ошибки 24<br>" +
                    "Обратитесь к администратору</html>");
            window.setVisible(true);
            window.setAlwaysOnTop(true);
        }
        return null;
    }

    public void pushSettingsToFile(){
        String fileName = "settings/graphicSettings.xml";
        try {
            JAXBContext context = JAXBContext.newInstance(Settings.class);
            Marshaller marshaller = context.createMarshaller();
            // устанавливаем флаг для читабельного вывода XML в JAXB
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // маршаллинг объекта в файл
            marshaller.marshal(this, new File(fileName));
        } catch (JAXBException e) {
            e.printStackTrace();
            PopupWindow window = new PopupWindow("<html>   Ошибка при синхронизации базы. Код ошибки 25<br>" +
                    "Обратитесь к администратору</html>");
            window.setVisible(true);
            window.setAlwaysOnTop(true);
        }
    }

    public static String getCurrentSeason(){
        String result = "";
        try {
            File fileDir = new File("database/seasons.txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF-8"));
            result = in.readLine();
            in.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static String getDateString(String s){
        String result = s.substring(0,3);
        switch (s.substring(4,7)){
            case "Jan":{
                result += "01."; break;
            }
            case "Feb":{
                result += "02."; break;
            }
            case "Mar":{
                result += "03."; break;
            }
            case "Apr":{
                result += "04."; break;
            }
            case "May":{
                result += "05."; break;
            }
            case "Jun":{
                result += "06."; break;
            }
            case "Jul":{
                result += "07."; break;
            }
            case "Aug":{
                result += "08."; break;
            }
            case "Sep":{
                result += "09."; break;
            }
            case "Oct":{
                result += "10."; break;
            }
            case "Nov":{
                result += "11."; break;
            }
            case "Dec":{
                result += "12."; break;
            }
        }
        result += s.substring(8,12);
        return result;
    }

    public static ArrayList<String> getListOfSeasons(){
        ArrayList<String> result = new ArrayList<>();
        try {
            File fileDir = new File("database/seasons.txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF-8"));
            String str;
            while (((str = in.readLine()) != null)) {
                result.add(str);
            }
            in.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static String getSerialNumber() throws Exception{
        String line;
        StringBuilder serial = new StringBuilder("H");
        Process process = Runtime.getRuntime().exec("cmd /c wmic path Win32_PhysicalMedia where \"tag like '%Drive0%'\" Get SerialNumber");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(process.getInputStream()) );
        while ((line = in.readLine()) != null) {
            if ((!line.contains("erial"))&&(!line.equals("")))
                serial.append(line);
        }
        in.close();
        serial = new StringBuilder(serial.toString().replaceAll("\\s", ""));
        if (serial.toString().toUpperCase().contains("ERROR")){
            PopupWindow window = new PopupWindow("<html>   Ошибка при проверке ID. Код ошибки 1<br>" +
                    "Обратитесь к администратору." + ".</html>");
            window.setVisible(true);
            window.setAlwaysOnTop(true);
        }
        return serial.toString();
    }

    public static String getDateBySerialNumber(String serialNumber){
        String result = "";
        try {
            File fileDir = new File(Settings.TMP_DIR + "/usersHBH.txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF-8"));
            String str;
            while (((str = in.readLine()) != null)) {
                if (serialNumber.contains(str.split("=")[2])){
                    result = str.split("=")[3];
                    break;
                }
            }
            in.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static String getRefCodeBySerialNumber(String serialNumber){
        String result = "";
        try {
            File fileDir = new File(Settings.TMP_DIR + "/usersHBH.txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF-8"));
            String str;
            while (((str = in.readLine()) != null)) {
                if (serialNumber.contains(str.split("=")[2])){
                    result = str.split("=")[5];
                    break;
                }
            }
            in.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static void setLastUpdate(String path){
        File file = new File("settings/lastUpdate.txt");

        try(FileWriter writer = new FileWriter(file, false))
        {
            writer.write(path);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static boolean checkKey(String serialNumber){
        boolean flag = false;
        int numberOfAccount = Settings.getNumberOfAccount();
        try {
            FTPLoader.downloadFile(getLogin(numberOfAccount), getPassword(numberOfAccount), "/data/usersHBH.txt", Settings.TMP_DIR + "/usersHBH.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            PopupWindow window = new PopupWindow("<html>   Ошибка при проверке ID. Код ошибки 2<br>" +
                    "Обратитесь к администратору." + ".</html>");
            window.setVisible(true);
            window.setAlwaysOnTop(true);
        }

        String ID = getIDBySerialNumber(serialNumber);
        String user = getUserBySerialNumber(serialNumber);
        try {
            File fileDir = new File(Settings.TMP_DIR + "/usersHBH.txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF-8"));
            String str;
            while (((str = in.readLine()) != null)) {
                if (serialNumber.contains(str.split("=")[2]) && str.split("=")[1].toUpperCase().equals("YES")){
                    flag = true;
                    break;
                }
            }
            in.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
            PopupWindow window = new PopupWindow("<html>   Ошибка при синхронизации базы. Код ошибки 3<br>" +
                    "Обратитесь к администратору." + ".</html>");
            window.setVisible(true);
            window.setAlwaysOnTop(true);
        }

        if (flag){
            try {
                FTPLoader.downloadFile(getLogin(numberOfAccount), getPassword(numberOfAccount), "/data/visit_log_HBH.txt", Settings.TMP_DIR + "/visit_log_HBH.txt");
                File file = new File(Settings.TMP_DIR + "/visit_log_HBH.txt");
                FileWriter fr = null;
                BufferedWriter br = null;
                try {
                    //для обновления файла нужно инициализировать FileWriter с помощью этого конструктора
                    Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+3"));

                    String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
                    String month = String.valueOf(c.get(Calendar.MONTH)+1);
                    String year = String.valueOf(c.get(Calendar.YEAR));
                    String hour = String.valueOf(c.get(11));
                    String minute = String.valueOf(c.get(12));
                    String second = String.valueOf(c.get(13));

                    if (day.length()==1){
                        day = "0" + day;
                    }
                    if (month.length()==1){
                        month = "0" + month;
                    }
                    if (hour.length()==1){
                        hour = "0" + hour;
                    }
                    if (minute.length()==1){
                        minute = "0" + minute;
                    }
                    if (second.length()==1){
                        second = "0" + second;
                    }

                    fr = new FileWriter(file,true);
                    br = new BufferedWriter(fr);
                    br.newLine();
                    //теперь мы можем использовать метод write или метод append
                    try {
                        br.write("[" + day + "." + month + "." + year + "   " + hour + ":" + minute + ":" + second + "]   "  + "User " + user + " checked serial number. ID: " + ID + ".");
                    } catch (Exception e) {
                        e.printStackTrace();
                        PopupWindow window = new PopupWindow("<html>   Ошибка при синхронизации базы. Код ошибки 4<br>" +
                                "Обратитесь к администратору." + ".</html>");
                        window.setVisible(true);
                        window.setAlwaysOnTop(true);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally{
                    try {
                        br.close();
                        fr.close();
                        FTPLoader.uploadFile(getLogin(numberOfAccount), getPassword(numberOfAccount), "/data/visit_log_HBH.txt", Settings.TMP_DIR + "/visit_log_HBH.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                        PopupWindow window = new PopupWindow("<html>   Ошибка при синхронизации базы. Код ошибки 5<br>" +
                                "Обратитесь к администратору." + ".</html>");
                        window.setVisible(true);
                        window.setAlwaysOnTop(true);
                    }
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                PopupWindow window = new PopupWindow("<html>   Ошибка при синхронизации базы. Код ошибки 6<br>" +
                        "Обратитесь к администратору." + ".</html>");
                window.setVisible(true);
                window.setAlwaysOnTop(true);
            }
        }

        return flag;
    }

    public static boolean checkDate(){
        String user = "Alexander Bolgov";
        String ID = "AB____AB";
        boolean flag = false;
        int numberOfAccount = Settings.getNumberOfAccount();
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+3"));

        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String month = String.valueOf(c.get(Calendar.MONTH)+1);
        String year = String.valueOf(c.get(Calendar.YEAR));

        if ((year.equals("2020") && month.equals("2")) || month.equals("3") )
            flag = true;

        if (flag){
            try {
                FTPLoader.downloadFile(getLogin(numberOfAccount), getPassword(numberOfAccount), "/data/visit_log_HBH.txt", Settings.TMP_DIR + "/visit_log_HBH.txt");
                File file = new File(Settings.TMP_DIR + "/visit_log_HBH.txt");
                FileWriter fr = null;
                BufferedWriter br = null;
                try {
                    //для обновления файла нужно инициализировать FileWriter с помощью этого конструктора
                    String hour = String.valueOf(c.get(11));
                    String minute = String.valueOf(c.get(12));
                    String second = String.valueOf(c.get(13));

                    if (day.length()==1){
                        day = "0" + day;
                    }
                    if (month.length()==1){
                        month = "0" + month;
                    }
                    if (hour.length()==1){
                        hour = "0" + hour;
                    }
                    if (minute.length()==1){
                        minute = "0" + minute;
                    }
                    if (second.length()==1){
                        second = "0" + second;
                    }

                    fr = new FileWriter(file,true);
                    br = new BufferedWriter(fr);
                    br.newLine();
                    //теперь мы можем использовать метод write или метод append
                    try {
                        br.write("[" + day + "." + month + "." + year + "   " + hour + ":" + minute + ":" + second + "]   "  + "User " + user + " checked serial number. ID: " + ID + ".");                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        PopupWindow window = new PopupWindow("<html>   Ошибка при синхронизации базы. Код ошибки 4<br>" +
                                "Обратитесь к администратору." + ".</html>");
                        window.setVisible(true);
                        window.setAlwaysOnTop(true);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally{
                    try {
                        br.close();
                        fr.close();
                        FTPLoader.uploadFile(getLogin(numberOfAccount), getPassword(numberOfAccount), "/data/visit_log_HBH.txt", Settings.TMP_DIR + "/visit_log_HBH.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                        PopupWindow window = new PopupWindow("<html>   Ошибка при синхронизации базы. Код ошибки 5<br>" +
                                "Обратитесь к администратору." + ".</html>");
                        window.setVisible(true);
                        window.setAlwaysOnTop(true);
                    }
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                PopupWindow window = new PopupWindow("<html>   Ошибка при синхронизации базы. Код ошибки 6<br>" +
                        "Обратитесь к администратору." + ".</html>");
                window.setVisible(true);
                window.setAlwaysOnTop(true);
            }
        }

        return flag;
    }

    public static String getUserBySerialNumber(String serialNumber){
        String result = "UNKNOWN_USER";
        try {
            File fileDir = new File(Settings.TMP_DIR + "/usersHBH.txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF-8"));
            String str;
            while (((str = in.readLine()) != null)) {
                if (serialNumber.contains(str.split("=")[2])){
                    result = str.split("=")[0];
                    break;
                }
            }
            in.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
            PopupWindow window = new PopupWindow("<html>   Ошибка при синхронизации базы. Код ошибки 28<br>" +
                    "Обратитесь к администратору</html>");
            window.setVisible(true);
            window.setAlwaysOnTop(true);
        }

        return result;
    }

    public static String getIDBySerialNumber(String serialNumber){
        String result = serialNumber;
        try {
            File fileDir = new File(Settings.TMP_DIR + "/usersHBH.txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF-8"));
            String str;
            while (((str = in.readLine()) != null)) {
                if (serialNumber.contains(str.split("=")[2])){
                    result = str.split("=")[2];
                    break;
                }
            }
            in.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
            PopupWindow window = new PopupWindow("<html>   Ошибка при синхронизации базы. Код ошибки 2435<br>" +
                    "Обратитесь к администратору</html>");
            window.setVisible(true);
            window.setAlwaysOnTop(true);
        }

        return result;
    }

    public static String getLastUpdateName(){
        String result = "";
        try {
            File fileDir = new File("settings/lastUpdate.txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF-8"));
            String str;
            while (((str = in.readLine()) != null)) {
                result = result + str;
            }
            in.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public String getIp(){
        return ip;
    }

    public static String getLogin(int n){
        return "client" + String.valueOf(n);
    }

    public static String getPassword(int n){
        switch (n){
            case 0:{
                return "wfa92^@m<WOFphfWOQG*@5202nIG@(@4t3gg2";
            }
            case 1:{
                return "vXSdk07852!%d{FWjf23451";
            }
            case 2:{
                return "d&21?fegFF(2nFpgr%@741";
            }
            case 3:{
                return "*12dskDE@(JdvsE6EOx";
            }
            case 4:{
                return "dP@RTU#ffdjgf203R$KjHSfG;w[";
            }
            case 5:{
                return "kgnW439352R2@-)#@$JG<eaE[WFKsgkp2";
            }
            case 6:{
                return "GPdwn1086%$@$,mf@$)@";
            }
            case 7:{
                return "&249HF@ebww324%@((LdwmwfI#24";
            }
            case 8:{
                return "*2Hd$2mH&3D,p)842nfU3*egf73";
            }
            case 9:{
                return "bf(@#br3525BWEF^#@(*$f6e3tjg2g";
            }

        }
        return null;
    }

    public static int getNumberOfAccount(){
        double t = MyMath.round(Math.random(), 2);
        String r = String.valueOf(t).substring(2,3);
        return Integer.parseInt(r);
    }

    public static String toRefreshDatabase(WindowWithProgressBar windowWithProgressBar, int numberOfAccount){
        String resultOfRefreshing = "Fail";
        String lastUpdateName = getLastUpdateName();

        String fileName = "_listOfUpdates.txt";
        try {
            String resultOfDownload = FTPLoader.downloadFile(getLogin(numberOfAccount), getPassword(numberOfAccount), "/data/hockey/database/updates/" + fileName, Settings.TMP_DIR + "/" + fileName);
            if (resultOfDownload.equals("Success")){
                if (lastUpdateName.equals("")){
                    String listFile = Settings.TMP_DIR + "/_listOfUpdates.txt";
                    try {
                        File fileDir = new File(listFile);
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                        new FileInputStream(fileDir), "UTF-8"));
                        String str;
                        while (((str = in.readLine()) != null)) {
                            getUpdatesByName(str, windowWithProgressBar, numberOfAccount);
                            Settings.setLastUpdate(str);
                        }
                        in.close();
                        resultOfRefreshing = "Success";
                    } catch (IOException e)
                    {
                        System.out.println(e.getMessage());
                    }
                } else {
                    String listFile = Settings.TMP_DIR + "/_listOfUpdates.txt";
                    try {
                        File fileDir = new File(listFile);
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                        new FileInputStream(fileDir), "UTF-8"));
                        String str;
                        boolean flag = false;
                        while (((str = in.readLine()) != null)) {
                            if (str.contains(lastUpdateName) || flag){
                                flag = true;
                                if (flag){
                                    getUpdatesByName(str, windowWithProgressBar, numberOfAccount);
                                    Settings.setLastUpdate(str);
                                }
                            }

                        }
                        in.close();
                        resultOfRefreshing = "Success";
                    } catch (IOException e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
            } else resultOfRefreshing += ": " + resultOfDownload;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return  resultOfRefreshing;
    }

    public static void getUpdatesByName(String updateName, WindowWithProgressBar windowWithProgressBar, int numberOfAccount){
        try {
            FTPLoader.downloadFile(getLogin(numberOfAccount), getPassword(numberOfAccount), "/data/hockey/database/updates/" + updateName, Settings.TMP_DIR + "/" + updateName);
            String listFile = Settings.TMP_DIR + "/" + updateName;
            try {
                File fileDir = new File(listFile);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(fileDir), "UTF-8"));
                String str;
                while (((str = in.readLine()) != null)) {
                    getDataFromDatabase(str, windowWithProgressBar, numberOfAccount);
                    LogWriter.writeLog("Successful download of " + str.substring(str.lastIndexOf('/') + 1));
                }
                in.close();
            } catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void getDataFromDatabase(String dataName, WindowWithProgressBar windowWithProgressBar, int numberOfAccount){
        /*try {
            String resultOfDownload = FTPLoader.downloadFile(getIp(), getLogin(numberOfAccount), getPassword(numberOfAccount), "/data/hockey/" + dataName, dataName);
            windowWithProgressBar.downloadedFiles ++;
            windowWithProgressBar.setProgressBarValue((int) (100*windowWithProgressBar.downloadedFiles / (double) windowWithProgressBar.numberOfFiles));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        String command = "";
        String argument = "";
        if (dataName.split("=").length > 1){
            command = dataName.split("=")[0];
            argument = dataName.split("=")[1];
        }
        switch (command){
            case "download":{
                try {
                    String resultOfDownload = FTPLoader.downloadFile(getLogin(numberOfAccount), getPassword(numberOfAccount), "/data/hockey/" + argument, argument);
                    windowWithProgressBar.downloadedFiles ++;
                    windowWithProgressBar.setProgressBarValue((int) (100*windowWithProgressBar.downloadedFiles / (double) windowWithProgressBar.numberOfFiles));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    PopupWindow window = new PopupWindow("<html>   Ошибка при синхронизации базы. Код ошибки 27<br>" +
                            "Обратитесь к администратору</html>");
                    window.setVisible(true);
                    window.setAlwaysOnTop(true);
                }
                break;
            }
            case "createDirectory":{
                try{
                    String absPath = new File(".pathToProject").getAbsolutePath().replace(".pathToProject", "");
                    File file2 = new File(absPath + "/" + argument);
                    if (!file2.exists())
                        file2.mkdir();
                } catch (Exception e){
                    e.printStackTrace();
                }


                break;
            }
            case "clearDirectory":{
                try{
                    deleteAllFilesFolder(argument);
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
            case "deleteDirectory":{
                getDataFromDatabase("clearDirectory=" + argument, windowWithProgressBar, numberOfAccount);
                File directory = new File(argument);
                File[] children = directory.listFiles();
                for (File child : children) {
                    System.out.println(child.getAbsolutePath());
                }

                boolean result = directory.delete();
                if (result) {
                    System.out.printf("Directory " + argument + " is successfully deleted",
                            directory.getAbsolutePath());
                } else {
                    System.out.printf("Failed to delete directory " + argument,
                            directory.getAbsolutePath());
                }
                break;
            }
            case "deleteFile":{
                File file = new File(argument);
                if (argument.contains("graphicSettings.xml")){
                    String sn = null;
                    try {
                        sn = Settings.getSerialNumber();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //String ID = getIDBySerialNumber(sn);
                    String user = getUserBySerialNumber(sn);
                    if (user.contains("UNKNOWN_USER")){
                        file.delete();
                    }
                } else {
                    file.delete();
                }
                break;
            }
        }

    }

    public static int countLines(String filename) throws IOException {
        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            count ++;
            return (count == 0 && !empty) ? 1 : count;
        }
    }

    public static void deleteAllFilesFolder(String path) {
        for (File myFile : new File(path).listFiles())
            if (myFile.isFile()) myFile.delete();
    }

    public static int getSeconds(String time){
        return 60*Integer.parseInt(time.split(":")[0]) + Integer.parseInt(time.split(":")[1]);
    }

    public static String getTimeInMinutesAndSecondsFromSeconds(double time){
        int t = (int) time;
        String seconds = String.valueOf(t % 60);
        if (seconds.length()<2)
                seconds = "0" + seconds;
        return String.valueOf(t / 60) + ":" + seconds;
    }

    public static String getTimeInMinutesAndSecondsFromMinutes(double time){
        int totalSeconds = (int) (time*60);
        return getTimeInMinutesAndSecondsFromSeconds(totalSeconds);
    }

    public static String getDefaultSeason(){
        String result = "";
        try {
            File fileDir = new File("database/seasons.txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF-8"));
            result = in.readLine();
            in.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static ArrayList<String> getListForMatchCenter(String dayCode, int hourDifference){
        ArrayList<String> result = new ArrayList<>();
        String fullFileName;
        if (hourDifference < 0){
            fullFileName = Settings.TMP_DIR + "H_" + String.valueOf(getNextDayCode(Integer.parseInt(dayCode))) + ".txt";
            try {
                File fileDir = new File(fullFileName);
                if (!fileDir.exists()){
                    fileDir = downloadDayFile(Settings.getNumberOfAccount(), "H_" + fullFileName.split("H_")[1]);
                }
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(fileDir), "UTF-8"));
                String str;
                String league = "";
                while (((str = in.readLine()) != null)) {
                    if (str.split("\\*").length == 1){
                        league = str;
                    } else {
                        String hour = str.split("\\*")[0].split(":")[0];
                        if (hour.startsWith("0"))
                            hour = hour.replaceFirst("0", "");
                        int newHour = Integer.parseInt(hour) + hourDifference;
                        if (newHour < 0){
                            String newHourString = String.valueOf(newHour + 24);
                            if (newHourString.length() < 2)
                                newHourString = "0" + newHourString;
                            str = str.replaceFirst(str.split("\\*")[0].split(":")[0], newHourString);
                            if (!result.contains(league))
                                result.add(league);
                            result.add(str);

                        }
                    }
                }
                in.close();
            } catch (IOException e)
            {
                System.out.println(e.getMessage());
                MyException.sendException(e.getStackTrace(), "getListForMatchCenter1");
            }
        }
        try {
            fullFileName = Settings.TMP_DIR + "H_" + String.valueOf(dayCode) + ".txt";
            File fileDir = new File(fullFileName);
            if (!fileDir.exists()){
                fileDir = downloadDayFile(Settings.getNumberOfAccount(), "H_" + fullFileName.split("H_")[1]);
            }
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF-8"));
            String str;
            String league = "";
            while (((str = in.readLine()) != null)) {
                if (str.split("\\*").length == 1){
                    league = str;
                } else {
                    String hour = str.split("\\*")[0].split(":")[0];
                    if (hour.startsWith("0"))
                        hour = hour.replaceFirst("0", "");
                    int newHour = Integer.parseInt(hour) + hourDifference;
                    if (newHour < 24 && newHour >= 0){
                        String newHourString = String.valueOf(newHour);
                        if (newHourString.length() < 2)
                            newHourString = "0" + newHourString;
                        str = str.replaceFirst(str.split("\\*")[0].split(":")[0], newHourString);
                        if (!result.contains(league)){
                            result.add(league);
                            result.add(str);
                        } else {
                            int countOfAddedMatchesInLeague = 0;
                            int index = result.indexOf(league)+1;
                            while (index<result.size()){
                                if (result.get(index).split("\\*").length > 1 ){
                                    countOfAddedMatchesInLeague++;
                                    index++;
                                } else{
                                    break;
                                }
                            }
                            result.add(result.indexOf(league)+countOfAddedMatchesInLeague+1, str);
                        }
                    }
                }
            }
            in.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
            MyException.sendException(e.getStackTrace(), "getListForMatchCenter2");
        }
        if (hourDifference > 0){
            fullFileName = Settings.TMP_DIR + "H_" + String.valueOf(getPreviousDayCode(Integer.parseInt(dayCode))) + ".txt";
            try {
                File fileDir = new File(fullFileName);
                if (!fileDir.exists()){
                    fileDir = downloadDayFile(Settings.getNumberOfAccount(), "H_" + fullFileName.split("H_")[1]);
                }
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(fileDir), "UTF-8"));
                String str;
                String league = "";
                while (((str = in.readLine()) != null)) {
                    if (str.split("\\*").length == 1){
                        league = str;
                    } else {
                        String hour = str.split("\\*")[0].split(":")[0];
                        if (hour.startsWith("0"))
                            hour = hour.replaceFirst("0", "");
                        int newHour = Integer.parseInt(hour) + hourDifference;
                        if (newHour >= 24){
                            String newHourString = String.valueOf(newHour - 24);
                            if (newHourString.length() < 2)
                                newHourString = "0" + newHourString;
                            str = str.replaceFirst(str.split("\\*")[0].split(":")[0], newHourString);
                            if (!result.contains(league)){
                                result.add(league);
                                result.add(str);
                            } else {
                                int countOfAddedMatchesInLeague = 0;
                                int index = result.indexOf(league)+1;
                                while (index<result.size()){
                                    if (result.get(index).split("\\*").length > 1 ){
                                        countOfAddedMatchesInLeague++;
                                        index++;
                                    } else{
                                        break;
                                    }
                                }
                                result.add(result.indexOf(league)+countOfAddedMatchesInLeague+1, str);
                            }
                        }
                    }
                }
                in.close();
            } catch (IOException e)
            {
                MyException.sendException(e.getStackTrace(), "getListForMatchCenter3");
                System.out.println(e.getMessage());
            }
        }

        return result;
    }

    public static int getNextDayCode(int todayCode){
        int res = todayCode;
        int currentDay = Integer.parseInt(String.valueOf(todayCode).substring(6,8));
        int currentMonth = Integer.parseInt(String.valueOf(todayCode).substring(4,6));
        int currentYear = Integer.parseInt(String.valueOf(todayCode).substring(0,4));

        if (currentDay+1 <= getLastDayInMonth(currentMonth, currentYear))
            res++;
        else {
            currentDay = 1;
            if (currentMonth < 12)
                currentMonth++;
            else{
                currentMonth = 1;
                currentYear++;
            }
            res = currentYear*10000 + currentMonth*100 + currentDay;
        }
        return  res;
    }

    public static int getPreviousDayCode(int todayCode){
        int res = todayCode;
        int currentDay = Integer.parseInt(String.valueOf(todayCode).substring(6,8));
        int currentMonth = Integer.parseInt(String.valueOf(todayCode).substring(4,6));
        int currentYear = Integer.parseInt(String.valueOf(todayCode).substring(0,4));

        if (currentDay-1 > 0)
            res--;
        else {
            if (currentMonth-1 > 0)
                currentMonth--;
            else {
                currentMonth = 12;
                currentYear--;
            }
            res = currentYear*10000 + currentMonth*100 + currentDay;
        }
        return  res;
    }

    public static File downloadDayFile(int n, String nameOfFile){
        try {

            FTPLoader.downloadFile(Settings.getLogin(n), Settings.getPassword(n), "/data/hockey/matchCenter/" + nameOfFile, Settings.TMP_DIR + nameOfFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new File(Settings.TMP_DIR + nameOfFile);
    }

    public static int getLastDayInMonth(int month, int year){
        switch (month){
            case 1: {
                return 31;
            }
            case 2: {
                if (year % 4 == 0)
                    return 29;
                else
                    return 28;
            }
            case 3: {
                return 31;
            }
            case 4: {
                return 30;
            }
            case 5: {
                return 31;
            }
            case 6: {
                return 30;
            }
            case 7: {
                return 31;
            }
            case 8: {
                return 31;
            }
            case 9: {
                return 30;
            }
            case 10: {
                return 31;
            }
            case 11: {
                return 30;
            }
            case 12: {
                return 31;
            }

        }
        return 0;
    }

    public static String getCurrentSeasonInLeague(String leagueName){
        String result = "";
        try {
            File fileDir = new File("settings/allLeagues.txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF-8"));
            String str;
            while (((str = in.readLine()) != null)) {
                if (str.split("=")[0].equals(leagueName)){
                    return str.split("=")[2];
                }
            }
            in.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static int getNumberOfTeamsInLeague(String leagueName, String season){
        int result = 0;
        try {
            File fileDir = new File("database/" + season + "/leagues/" + leagueName + ".txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF-8"));
            while ((in.readLine() != null)) {
                result++;
            }
            in.close();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        return result;

    }

    public static void setWindowResolution(int width, int height){
        Settings settings = getSettingsFromFile();
        settings.windowResolution = String.valueOf(width) + "x" + String.valueOf(height);
        settings.pushSettingsToFile();
    }

    public String getWindowResolution(){
        return this.windowResolution;
    }

    public static void checkIntegrity(){
        String result = "";
        File fileHBH = new File("HockeyBettingHelper.exe");
        String pathToExe = fileHBH.getAbsolutePath();
        File directory = new File(pathToExe.split("HockeyBettingHelper.exe")[0]);
        String foldersList = "";
        for (String s : directory.list()){
            if (s.equals("database")){
                foldersList += "database*";
            }
            if (s.equals("images")){
                foldersList += "images*";
            }
            if (s.equals("leaguesInfo")){
                foldersList += "leaguesInfo*";
            }
            if (s.equals("logs")){
                foldersList += "logs*";
            }
            if (s.equals("settings")){
                foldersList += "settings*";
            }
        }

        if (foldersList.contains("database") || foldersList.contains("images") || foldersList.contains("leaguesInfo") || foldersList.contains("logs") || foldersList.contains("settings")){
            if (!foldersList.contains("database")){
                try{
                    String absPath = directory.getAbsolutePath();
                    File file2 = new File(absPath + "/database");
                    if (!file2.exists())
                        file2.mkdir();
                } catch (Exception e){
                    e.printStackTrace();
                }
                result += "Была создана директория database/ <br>";
            }
            if (!foldersList.contains("images")){
                try{
                    String absPath = directory.getAbsolutePath();
                    File file2 = new File(absPath + "/images");
                    if (!file2.exists()){
                        file2.mkdir();
                        FTPLoader.downloadFile(Settings.getLogin(0), Settings.getPassword(0), "/data/hockey/images/info.png", "images/info.png");
                        FTPLoader.downloadFile(Settings.getLogin(0), Settings.getPassword(0), "/data/hockey/images/instagram.png", "images/instagram.png");
                        FTPLoader.downloadFile(Settings.getLogin(0), Settings.getPassword(0), "/data/hockey/images/left-arrow.png", "images/left-arrow.png");
                        FTPLoader.downloadFile(Settings.getLogin(0), Settings.getPassword(0), "/data/hockey/images/legalbet.png", "images/legalbet.png");
                        FTPLoader.downloadFile(Settings.getLogin(0), Settings.getPassword(0), "/data/hockey/images/lose.png", "images/lose.png");
                        FTPLoader.downloadFile(Settings.getLogin(0), Settings.getPassword(0), "/data/hockey/images/loseOT.png", "images/loseOT.png");
                        FTPLoader.downloadFile(Settings.getLogin(0), Settings.getPassword(0), "/data/hockey/images/right-arrow.png", "images/right-arrow.png");
                        FTPLoader.downloadFile(Settings.getLogin(0), Settings.getPassword(0), "/data/hockey/images/twitter.png", "images/twitter.png");
                        FTPLoader.downloadFile(Settings.getLogin(0), Settings.getPassword(0), "/data/hockey/images/vk.png", "images/vk.png");
                        FTPLoader.downloadFile(Settings.getLogin(0), Settings.getPassword(0), "/data/hockey/images/win.png", "images/win.png");
                        FTPLoader.downloadFile(Settings.getLogin(0), Settings.getPassword(0), "/data/hockey/images/winOT.png", "images/winOT.png");
                        FTPLoader.downloadFile(Settings.getLogin(0), Settings.getPassword(0), "/data/hockey/images/www.png", "images/www.png");

                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
                result += "Была создана директория images/ <br>";
            }
            if (!foldersList.contains("leaguesInfo")){
                try{
                    String absPath = directory.getAbsolutePath();
                    File file2 = new File(absPath + "/leaguesInfo");
                    if (!file2.exists())
                        file2.mkdir();
                } catch (Exception e){
                    e.printStackTrace();
                }
                result += "Была создана директория leaguesInfo/ <br>";
            }
            if (!foldersList.contains("logs")){
                try{
                    String absPath = directory.getAbsolutePath();
                    File file2 = new File(absPath + "/logs");
                    if (!file2.exists())
                        file2.mkdir();
                } catch (Exception e){
                    e.printStackTrace();
                }
                result += "Была создана директория logs/ <br>";
            }
            if (!foldersList.contains("settings")){
                try{
                    String absPath = directory.getAbsolutePath();
                    File file2 = new File(absPath + "/settings");
                    if (!file2.exists()){
                        file2.mkdir();
                        FTPLoader.downloadFile(Settings.getLogin(0), Settings.getPassword(0), "/data/hockey/settings/graphicSettings.xml", "settings/graphicSettings.xml");
                        FTPLoader.downloadFile(Settings.getLogin(0), Settings.getPassword(0), "/data/hockey/settings/lastUpdate.txt", "settings/lastUpdate.txt");
                        FTPLoader.downloadFile(Settings.getLogin(0), Settings.getPassword(0), "/data/hockey/settings/allLeagues.txt", "settings/allLeagues.txt");

                    }

                } catch (Exception e){
                    e.printStackTrace();
                }
                result += "Была создана директория settings/ <br>";
            }

            if (!result.equals("")){
                String text = "<html>" + result + "</html>";
                WindowMessage wm = new WindowMessage(text);
                wm.setVisible(true);

            }

        } else {
            String text = "<html>Файл HockeyBettingHelper.exe <br>" +
                    "находится вне своей папки. Переместите его в родную папку. <br>" +
                    "Вероятнее всего, она называется HockeyBettingHelper и лежит на диске D.</html>";
            WindowMessage wm = new WindowMessage(text);
            wm.setVisible(true);
        }
    }
}


//**********************************************************************************************************************

/*public static void addDownload(Download download) {
        // Register to be notified when the download changes.
        download.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
            }
        });
        // Fire table row insertion notification to table.
    }*/


    /*public static void toRefreshDatabase2(){
        String lastUpdateName = getLastUpdateName();

        Socket s = null;
        try {
            s = new Socket(getIp(), 80);
            //s = new Socket("java-course.ru", 80);
            InputStream in = s.getInputStream();
            OutputStream out = s.getOutputStream();
            String str = "GET /data/hockey/database/updates/_listOfUpdates.txt HTTP/1.1\r\n" +
                    "Host:" + getIp() + "\r\n\r\n";
            *//*String str = "GET /network.txt HTTP/1.1\r\n" +
                    "Host:192.168.4.28\r\n\r\n";*//*
            byte buf[] = str.getBytes();
            out.write(buf);

            ArrayList<String> listOfUpdates = new ArrayList();
            String answer;

            byte buf_out[] = new byte[1024];
            int size = in.read(buf_out);

            *//*while ((size = in.read(buf_out)) != -1) {
                System.out.print(new String(buf_out, 0, size));
            }*//*

            System.out.print(new String(buf_out, 0, size));
            answer = new String(buf_out, 0, size);
            *//*System.out.print(new String(buf_out, 0, size));
            answer = new String(buf_out, 0, size); *//*//.split("Ranges: bytes\r\n\r\n")[1];

            //String[] list = answer.split("\r\n");
            listOfUpdates.add("");

            s.close();



        } catch (IOException e) {
            e.printStackTrace();
        }



        int i=0;

    }*/
