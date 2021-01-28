package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.nio.file.*;

public class Main extends JFrame { //Наследуя от JFrame, мы получаем всю функциональность окна
    String currentVersion;
    String newestVersion;

    public Main() throws Exception{
        super("Hockey Betting Helper"); //Заголовок окна

        java.util.Locale.setDefault(new java.util.Locale("ru","RU"));
        getInputContext().selectInputMethod( java.util.Locale.getDefault());

        Settings.checkIntegrity();

        final Settings settings = Settings.getSettingsFromFile();

        String sn = null;
        try {
            sn = Settings.getSerialNumber();
            System.out.println("My ID is: " + sn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean flagPath = false;
        File file = new File("logs/log.txt");
        Path path = Paths.get(file.getAbsolutePath().split("HockeyBettingHelper")[0]);

        if(Files.isWritable(path)){
            flagPath = true;
        }

        PopupWindow window;
        WindowWithProgressBar windowWithProgressBar;
        int numberOfAccount = Settings.getNumberOfAccount();
        if (!flagPath){
            window = new PopupWindow("<html>   Папка " + path + " ограничена в правах!<br>" +
                    "Пути решения проблемы:<br>" +
                    "1. Перенесите программу в другой каталог<br>" +
                    "2. Раздайте права на эту папку<br>" +
                    "3. Запускайте программу от имени администратора.</html>");
            window.setVisible(true);
            window.setAlwaysOnTop(true);
        } else {
            if (Settings.checkKey(sn)){

                /*if(Files.isWritable(path)){
                    flagPath = true;
                }
                String ID = Settings.getIDBySerialNumber(sn);
                String user = Settings.getUserBySerialNumber(sn);
                if (user.contains("UNKNOWN_USER")){
                    Settings.deleteAllFilesFolder("settings/");
                }*/

                int width = Integer.parseInt(settings.getWindowResolution().split("x")[0]);
                int height = Integer.parseInt(settings.getWindowResolution().split("x")[1]);

                setBounds(0, 0, width, height); //отступы и размеры окна
                super.setMinimumSize(new Dimension(1280, 720));
                String tabs[] = {"О программе", "Сравнение команд", "Статистика команды", "Противостояние", "Броски в створ ворот",
                        "Штраф","Таблицы по лиге", "Продвинутая статистика", "Тренды", "Настройки", "Матч-центр", "До-после"};  /*Настройки и редактирование базы*/
                JTabbedPane jtp = new JTabbedPane(JTabbedPane.TOP,
                        JTabbedPane.SCROLL_TAB_LAYOUT);
                jtp.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
                    @Override
                    protected int calculateTabHeight(int tabPlacement, int tabIndex,
                                                     int fontHeight) {
                        return 40;
                    }

                    @Override
                    protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects,
                                            int tabIndex, Rectangle iconRect, Rectangle textRect) {
                        rects[tabIndex].height = 35;
                        super.paintTab(g, tabPlacement, rects, tabIndex, iconRect, textRect);
                    }
                });
                windowWithProgressBar = new WindowWithProgressBar("  Подождите, выполняется синхронизация базы данных");
                windowWithProgressBar.setVisible(true);

                String resultOfSync = Settings.toRefreshDatabase(windowWithProgressBar, numberOfAccount);

                if (resultOfSync.contains("Fail")){
                    windowWithProgressBar.setVisible(false);
                    windowWithProgressBar = new WindowWithProgressBar("<html>   База не была синхронизирована<br>" +
                            "Причина: " + resultOfSync.split(": ")[1] + ".</html>");
                    windowWithProgressBar.setVisible(true);
                } else if (resultOfSync.contains("Success")){
                    windowWithProgressBar.setVisible(false);
                    window = new PopupWindow("База была синхронизирована успешно!");
                    window.setVisible(true);
                }
                windowWithProgressBar.setAlwaysOnTop(true);

                PanelStart panelStart = new PanelStart();
                PanelSettings panelSettings = new PanelSettings();
                PanelMatching panelMatching = new PanelMatching();
                PanelConfrontation panelConfrontation = new PanelConfrontation();
                PanelShotsOnTarget panelShotsOnTarget = new PanelShotsOnTarget();
                PanelPenalties panelPenalties = new PanelPenalties();
                PanelOneTeamStats panelOneTeamStats = new PanelOneTeamStats();
                PanelTablesByLeague panelTablesByLeague = new PanelTablesByLeague();
                PanelAdvancedStatistics panelAdvancedStatistics = new PanelAdvancedStatistics();
                PanelTrends panelTrends = new PanelTrends();
                PanelBeforeAfter panelBeforeAfter = new PanelBeforeAfter();
                PanelMatchCenter panelMatchCenter = new PanelMatchCenter(panelMatching, panelOneTeamStats, panelConfrontation,
                        panelTablesByLeague, panelTrends, panelShotsOnTarget, panelPenalties, panelAdvancedStatistics);

                jtp.addTab(tabs[0], panelStart);
                add(jtp);
                jtp.addTab(tabs[10], panelMatchCenter);
                add(jtp);
                jtp.addTab(tabs[1], panelMatching);
                add(jtp);
                jtp.addTab(tabs[2], panelOneTeamStats);
                add(jtp);
                jtp.addTab(tabs[3], panelConfrontation);
                add(jtp);
                jtp.addTab(tabs[4], panelShotsOnTarget);
                add(jtp);
                jtp.addTab(tabs[5], panelPenalties);
                add(jtp);
                jtp.addTab(tabs[6], panelTablesByLeague);
                add(jtp);
                jtp.addTab(tabs[7], panelAdvancedStatistics);
                add(jtp);
                jtp.addTab(tabs[8], panelTrends);
                add(jtp);
                jtp.addTab(tabs[11], panelBeforeAfter);
                add(jtp);
                jtp.addTab(tabs[9], panelSettings);
                add(jtp);

                jtp.setSelectedIndex(0);
                currentVersion = panelStart.currentVersion;
                newestVersion = panelStart.newestVersion;

            } else {
                WindowWithID windowWithID = new WindowWithID("<html>   Вы не зарегистрированы или у вас кончилась подписка!<br>" +
                        "Обратитесь к администратору.</html>");
                windowWithID.setVisible(true);
                windowWithID.setAlwaysOnTop(true);
            }
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //это нужно для того, чтобы при закрытии окна закрывалась и программа, иначе она останется висеть в процессах
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            // ...
            public void windowClosing(WindowEvent event) {

                int h = Main.getFrames()[0].getHeight();
                int w = Main.getFrames()[0].getWidth();
                Settings.setWindowResolution(w, h);

                int n = Settings.getNumberOfAccount();
                if (!currentVersion.equals(newestVersion)){
                    try {
                        FTPLoader.downloadFile(Settings.getLogin(n), Settings.getPassword(n), "/versions/HockeyBettingHelper.exe", "HockeyBettingHelper.exe");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    public static void main(String[] args) throws Exception {
        Main app = new Main();
        app.setVisible(true);
    }

    public static double roundResult(double d, int precise) {
        precise = (int) Math.pow(10,precise);
        d = d*precise;
        int i = (int) Math.round(d);
        return (double) i/precise;
    }

    void writeFile() throws FileNotFoundException {
        try {
            // отрываем поток для записи
            FileOutputStream fos = new FileOutputStream("");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            // пишем данные
            for (int i = 0; i < 10; i++) {
                String str = String.valueOf(i);
                bw.write(str);
                bw.newLine();
            }
            // закрываем поток
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // Конец процедуры записи списка в файл

    public static String[] readTxtFile(String fileName){
        String resultS = "Команда\n";
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

}
