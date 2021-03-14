package sample;

import org.jfree.ui.tabbedui.VerticalLayout;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class PanelSettings extends JPanel{
    Settings settings;
    JPanel container;
    String formS;
    boolean windowsOnTop;
    boolean flagGraphics;
    boolean flagTrendsHA;
    boolean flagBubbleChartsHA;
    final String path = "database/";

    public PanelSettings(){
        this.setLayout(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setLocation(0,0);
        mainPanel.setSize(700, 620);
        mainPanel.setBorder(BorderFactory.createTitledBorder(""));

        JPanel secondPanel = new JPanel(new BorderLayout());
        mainPanel.add(secondPanel);

        settings = Settings.getSettingsFromFile();

        JPanel selectGraphics = new JPanel();
        selectGraphics.setBorder(
                BorderFactory.createTitledBorder("Отображение графиков"));
        selectGraphics.setLocation(5, 5);
        selectGraphics.setSize(280, 560);
        selectGraphics.setLayout(null);
        int otstup = 20;

        Font font = new Font("Arial", Font.BOLD, 15);
        final JCheckBox goals  = new JCheckBox("Голы в основное время", settings.showGoals);
        goals.setLocation(20, otstup);
        otstup +=20;
        goals.setSize(200, 20);
        selectGraphics.add(goals);

        final JCheckBox goalsOT = new JCheckBox("Голы с учетом ОТ и буллитов", settings.showGoalsOT);
        goalsOT.setLocation(20, otstup);
        otstup +=20;
        goalsOT.setSize(200, 20);
        selectGraphics.add(goalsOT);

        final JCheckBox realization = new JCheckBox("Реализация бросков", settings.showRealization);
        realization.setLocation(20, otstup);
        otstup +=20;
        realization.setSize(200, 20);
        selectGraphics.add(realization);

        final JCheckBox powerPlay = new JCheckBox("Реализация большинства", settings.showPowerPlay);
        powerPlay.setLocation(20, otstup);
        otstup +=20;
        powerPlay.setSize(200, 20);
        selectGraphics.add(powerPlay);

        final JCheckBox shortHanded = new JCheckBox("Меньшинство", settings.showShortHanded);
        shortHanded.setLocation(20, otstup);
        otstup +=20;
        shortHanded.setSize(200, 20);
        selectGraphics.add(shortHanded);

        final JCheckBox timeInAttack  = new JCheckBox("Время в атаке", settings.showTimeInAttack);
        timeInAttack.setLocation(20, otstup);
        otstup +=20;
        timeInAttack.setSize(240, 20);
        selectGraphics.add(timeInAttack);

        final JCheckBox goals1per  = new JCheckBox("Голы в первом периоде", settings.showGoals1per);
        goals1per.setLocation(20, otstup);
        otstup +=20;
        goals1per.setSize(200, 20);
        selectGraphics.add(goals1per);

        final JCheckBox goals2per  = new JCheckBox("Голы во втором периоде", settings.showGoals2per);
        goals2per.setLocation(20, otstup);
        otstup +=20;
        goals2per.setSize(200, 20);
        selectGraphics.add(goals2per);

        final JCheckBox goals3per = new JCheckBox("Голы в третьем периоде", settings.showGoals3per);
        goals3per.setLocation(20, otstup);
        otstup +=20;
        goals3per.setSize(200, 20);
        selectGraphics.add(goals3per);

        final JCheckBox goalsShortHanded  = new JCheckBox("Забитые голы в меньшинстве", settings.showGoalsShortHanded);
        goalsShortHanded.setLocation(20, otstup);
        otstup +=20;
        goalsShortHanded.setSize(200, 20);
        selectGraphics.add(goalsShortHanded);

        final JCheckBox shotsOnTarget  = new JCheckBox("Броски в створ", settings.showShotsOnTarget);
        shotsOnTarget.setLocation(20, otstup);
        otstup +=20;
        shotsOnTarget.setSize(200, 20);
        selectGraphics.add(shotsOnTarget);

        final JCheckBox shotsOnTarget1per  = new JCheckBox("Броски в створ 1 пер.", settings.showShotsOnTarget1per);
        shotsOnTarget1per.setLocation(20, otstup);
        otstup +=20;
        shotsOnTarget1per.setSize(200, 20);
        selectGraphics.add(shotsOnTarget1per);

        final JCheckBox shotsOnTarget2per  = new JCheckBox("Броски в створ 2 пер.", settings.showShotsOnTarget2per);
        shotsOnTarget2per.setLocation(20, otstup);
        otstup +=20;
        shotsOnTarget2per.setSize(200, 20);
        selectGraphics.add(shotsOnTarget2per);

        final JCheckBox shotsOnTarget3per  = new JCheckBox("Броски в створ 3 пер.", settings.showShotsOnTarget3per);
        shotsOnTarget3per.setLocation(20, otstup);
        otstup +=20;
        shotsOnTarget3per.setSize(200, 20);
        selectGraphics.add(shotsOnTarget3per);

        final JCheckBox missedShots  = new JCheckBox("Броски мимо", settings.showMissedShots);
        missedShots.setLocation(20, otstup);
        otstup +=20;
        missedShots.setSize(200, 20);
        selectGraphics.add(missedShots);

        final JCheckBox blockedShots  = new JCheckBox("Заблокированные броски", settings.showBlockedShots);
        blockedShots.setLocation(20, otstup);
        otstup +=20;
        blockedShots.setSize(200, 20);
        selectGraphics.add(blockedShots);

        final JCheckBox faceoffs  = new JCheckBox("Вбрасывания", settings.showFaceoffs);
        faceoffs.setLocation(20, otstup);
        otstup +=20;
        faceoffs.setSize(200, 20);
        selectGraphics.add(faceoffs);

        final JCheckBox hits  = new JCheckBox("Силовые приемы", settings.showHits);
        hits.setLocation(20, otstup);
        otstup +=20;
        hits.setSize(200, 20);
        selectGraphics.add(hits);

        final JCheckBox penMinutes  = new JCheckBox("Минуты штрафа", settings.showPenMinutes);
        penMinutes.setLocation(20, otstup);
        otstup +=20;
        penMinutes.setSize(200, 20);
        selectGraphics.add(penMinutes);

        final JCheckBox numberOf2MinutesPen  = new JCheckBox("Кол-во двухминутных удалений", settings.showNumberOf2MinutesPen);
        numberOf2MinutesPen.setLocation(20, otstup);
        otstup +=20;
        numberOf2MinutesPen.setSize(240, 20);
        selectGraphics.add(numberOf2MinutesPen);

        otstup +=20;
        final JCheckBox showTotal  = new JCheckBox("Графики тоталов", settings.showTotal);
        showTotal.setLocation(20, otstup);
        otstup +=20;
        showTotal.setSize(200, 20);
        selectGraphics.add(showTotal);

        final JCheckBox showSoTOpponents  = new JCheckBox("Таблица по броскам соперников", settings.showSoTOpponents);
        showSoTOpponents.setLocation(20, otstup);
        otstup +=20;
        showSoTOpponents.setSize(240, 20);
        selectGraphics.add(showSoTOpponents);

        final JCheckBox showPenOpponents  = new JCheckBox("Таблица по штрафам соперников", settings.showPenOpponents);
        showPenOpponents.setLocation(20, otstup);
        otstup +=30;
        showPenOpponents.setSize(240, 20);
        selectGraphics.add(showPenOpponents);

        /*final JCheckBox dispMinMax  = new JCheckBox("Показывать дисперсии/MAX/MIN бросков и штрафов", settings.dispMinMax);
        dispMinMax.setLocation(20, otstup);
        otstup +=30;
        dispMinMax.setSize(240, 20);
        selectGraphics.add(dispMinMax);*/

        final JCheckBox useColors  = new JCheckBox("Цветовое оформление таблиц", settings.useColors);
        useColors.setLocation(20, otstup);
        otstup +=50;
        useColors.setSize(240, 20);
        selectGraphics.add(useColors);

        secondPanel.add(selectGraphics);
        //this.add(selectGraphics);

        JPanel otherSettings = new JPanel(new VerticalLayout());

        JPanel panelWithSlider = new JPanel();
        panelWithSlider.setBorder(BorderFactory.createTitledBorder("Граница проходимости тренда"));

        final JSlider slider = new JSlider(60, 100, Integer.parseInt(settings.trendPercent));
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(5);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);
        slider.setPreferredSize(new Dimension(280, 100));
        panelWithSlider.add(slider);
        otherSettings.add(panelWithSlider);


        JPanel rbPanel = new JPanel(new FlowLayout());
        rbPanel.setBorder(BorderFactory.createTitledBorder("Вывод матчей в строке 'Форма'"));
        JRadioButton leftToRight = new JRadioButton("Слева направо");
        JRadioButton rightToLeft = new JRadioButton("Справа налево");

        if (settings.form.equals("leftToRight")){
            leftToRight.setSelected(true);
            formS = "leftToRight";
        }
        else{
            rightToLeft.setSelected(true);
            formS = "rightToLeft";
        }

        final ButtonGroup group = new ButtonGroup();
        group.add(leftToRight);
        group.add(rightToLeft);
        rbPanel.add(leftToRight);
        rbPanel.add(rightToLeft);
        otherSettings.add(rbPanel);

        final JCheckBox windowsOnTop = new JCheckBox("Открывать дополнит. окна поверх всех", settings.windowsOnTop);
        otherSettings.add(windowsOnTop);

        String[] hoursList = new String[]{
                "GMT + 12. Анадырь",
                "GMT + 11. Магадан",
                "GMT + 10. Владивосток",
                "GMT + 9. Якутск",
                "GMT + 8. Иркутск",
                "GMT + 7. Красноярск",
                "GMT + 6. Омск",
                "GMT + 5. Екатеринбург",
                "GMT + 4. Самара",
                "GMT + 3. Москва",
                "GMT + 2. Калининград",
                "GMT + 1. Среднеевропейское время",
                "GMT + 0. Гринвичское время",
                "GMT - 1. Гренландия",
                "GMT - 2. Среднеатлантическое время",
                "GMT - 3. Аргентина",
                "GMT - 4. Атлантическое время",
                "GMT - 5. Нью-Йорк",
                "GMT - 6. Чикаго",
                "GMT - 7. Денвер",
                "GMT - 8. Лос-Анджелес",
                "GMT - 9. Аляска",
                "GMT - 10. Гавайское время",
                "GMT - 11. Американское Самоа",
                "GMT - 12. Ну вы серьезно до туда добрались?:) Ѳ",
        };
        JPanel timeChooserPanel = new JPanel(new BorderLayout());
        timeChooserPanel.setBorder(BorderFactory.createTitledBorder("Выберите ваш часовой пояс"));
        final JComboBox<String> timeChooser = new JComboBox<>(hoursList);
        timeChooser.setSelectedIndex(settings.localTime);
        timeChooserPanel.add(timeChooser);
        otherSettings.add(timeChooserPanel);

        JPanel panelGraphicOrTables = new JPanel();
        panelGraphicOrTables.setBorder(BorderFactory.createTitledBorder("Отображение статистики"));
        JRadioButton graphicRB = new JRadioButton("Графики");
        JRadioButton tablesRB = new JRadioButton("Таблицы");

        if (settings.showGraphics){
            graphicRB.setSelected(true);
            flagGraphics = true;
        }
        else{
            tablesRB.setSelected(true);
            flagGraphics = false;
        }

        final ButtonGroup groupGraphicRB = new ButtonGroup();
        groupGraphicRB.add(graphicRB);
        groupGraphicRB.add(tablesRB);
        panelGraphicOrTables.add(graphicRB);
        panelGraphicOrTables.add(tablesRB);
        otherSettings.add(panelGraphicOrTables);

        JPanel panelTrendsSettings = new JPanel();
        panelTrendsSettings.setBorder(BorderFactory.createTitledBorder("Какие тренды отображать?"));

        JRadioButton trendsHA = new JRadioButton("Дом - выезд");
        JRadioButton trendsAll = new JRadioButton("Все игры - все игры");

        if (settings.trendsHA){
            trendsHA.setSelected(true);
            flagTrendsHA = true;
        }
        else{
            trendsAll.setSelected(true);
            flagTrendsHA = false;
        }

        final ButtonGroup groupTrends = new ButtonGroup();
        groupTrends.add(trendsHA);
        groupTrends.add(trendsAll);
        panelTrendsSettings.add(trendsHA);
        panelTrendsSettings.add(trendsAll);
        otherSettings.add(panelTrendsSettings);

        JPanel panelBubblesChartSettings = new JPanel();
        panelBubblesChartSettings.setBorder(BorderFactory.createTitledBorder("Перекрестные графики"));

        JRadioButton bubbleChartsHA = new JRadioButton("Дом - выезд");
        JRadioButton bubbleChartsAll = new JRadioButton("Все игры - все игры");

        if (settings.bubbleChartsHA){
            bubbleChartsHA.setSelected(true);
            flagBubbleChartsHA = true;
        }
        else{
            bubbleChartsAll.setSelected(true);
            flagBubbleChartsHA = false;
        }

        final ButtonGroup groupBubbleCharts = new ButtonGroup();
        groupBubbleCharts.add(bubbleChartsHA);
        groupBubbleCharts.add(bubbleChartsAll);
        panelBubblesChartSettings.add(bubbleChartsHA);
        panelBubblesChartSettings.add(bubbleChartsAll);
        otherSettings.add(panelBubblesChartSettings);


        secondPanel.add(otherSettings, BorderLayout.EAST);

        JButton buttonApply = new JButton("Применить");
        buttonApply.setFont(font);
        buttonApply.setLocation(20, 320);
        buttonApply.setSize(240, 40);
        mainPanel.add(buttonApply, BorderLayout.SOUTH);

        this.add(mainPanel);


        Font fontForButton = new Font("", 0, 18);
        JPanel panelGetAllDB = new JPanel(new BorderLayout());
        panelGetAllDB.setBorder(BorderFactory.createTitledBorder("Принудительная синхронизация базы"));
        panelGetAllDB.setSize(300, 200);
        panelGetAllDB.setLocation(300, 5);
        String text = "<html>   Ее можно провести, чтобы база <br>" +
                "синхронизировалась целиком.<br>" +
                "Процесс может занять пару минут.<br>" +
                "По окончании синхронизации текст кнопки изменится.</html>";
        JLabel label = new JLabel(text);
        label.setFont(font);
        panelGetAllDB.add(label);

        final JButton buttonGetDB = new JButton("Синхронизировать!");
        buttonGetDB.setFont(fontForButton);
        panelGetAllDB.add(buttonGetDB, BorderLayout.SOUTH);
        //this.add(panelGetAllDB);

        leftToRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formS = "leftToRight";
            }
        });
        rightToLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formS = "rightToLeft";
            }
        });
        graphicRB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flagGraphics = true;
            }
        });
        tablesRB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flagGraphics = false;
            }
        });
        trendsHA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flagTrendsHA = true;
            }
        });
        trendsAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flagTrendsHA = false;
            }
        });

        bubbleChartsHA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flagBubbleChartsHA = true;
            }
        });
        bubbleChartsAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flagBubbleChartsHA = false;
            }
        });

        buttonApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Boolean> arrayList = new ArrayList<>();

                arrayList.add(goals.isSelected());
                arrayList.add(goalsOT.isSelected());
                arrayList.add(realization.isSelected());
                arrayList.add(powerPlay.isSelected());
                arrayList.add(shortHanded.isSelected());
                arrayList.add(timeInAttack.isSelected());
                arrayList.add(goals1per.isSelected());
                arrayList.add(goals2per.isSelected());
                arrayList.add(goals3per.isSelected());
                arrayList.add(goalsShortHanded.isSelected());
                arrayList.add(shotsOnTarget.isSelected());
                arrayList.add(shotsOnTarget1per.isSelected());
                arrayList.add(shotsOnTarget2per.isSelected());
                arrayList.add(shotsOnTarget3per.isSelected());
                arrayList.add(missedShots.isSelected());
                arrayList.add(blockedShots.isSelected());
                arrayList.add(faceoffs.isSelected());
                arrayList.add(hits.isSelected());
                arrayList.add(penMinutes.isSelected());
                arrayList.add(numberOf2MinutesPen.isSelected());
                arrayList.add(showTotal.isSelected());
                arrayList.add(showSoTOpponents.isSelected());
                arrayList.add(showPenOpponents.isSelected());
                arrayList.add(flagGraphics);
                arrayList.add(flagTrendsHA);
                arrayList.add(useColors.isSelected());
                arrayList.add(flagBubbleChartsHA);
                //arrayList.add(dispMinMax.isSelected());
                settings = new Settings(arrayList, Main.getFrames()[0].getWidth(), Main.getFrames()[0].getHeight(), slider.getValue(), windowsOnTop.isSelected(), formS, settings.getIp(), timeChooser.getSelectedIndex());
                settings.pushSettingsToFile();
            }
        });

                buttonGetDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numberOfAccount = Settings.getNumberOfAccount();
                try {
                    FTPLoader.downloadFile(Settings.getLogin(numberOfAccount), Settings.getPassword(numberOfAccount),
                            "/databaseCopies/databaseHBH.zip", Settings.TMP_DIR + "database.zip");
                    LogWriter.writeLog("Successful decompressing of database");
                    String user = "";
                    try {
                        user = Settings.getUserBySerialNumber(Settings.getSerialNumber());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    FTPLoader.downloadFile(Settings.getLogin(numberOfAccount), Settings.getPassword(numberOfAccount), "/data/visit_log_HBH.txt", Settings.TMP_DIR + "/visit_log_HBH.txt");
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
                            br.write("[" + day + "." + month + "." + year + "   " + hour + ":" + minute + ":" + second + "]   "  + "User " + user + " downloaded full database.");
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }

                    } catch (IOException e3) {
                        e3.printStackTrace();
                    } finally{
                        try {
                            br.close();
                            fr.close();
                            FTPLoader.uploadFile(Settings.getLogin(numberOfAccount), Settings.getPassword(numberOfAccount), "/data/visit_log_HBH.txt", Settings.TMP_DIR + "/visit_log_HBH.txt");
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }

                    buttonGetDB.setText("База синхронизирована!");
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}