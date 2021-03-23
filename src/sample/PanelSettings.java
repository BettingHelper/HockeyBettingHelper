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
        this.setLayout(new BorderLayout());

        JScrollPane sp;
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder(""));

        JPanel secondPanel = new JPanel(new BorderLayout());
        mainPanel.add(secondPanel);

        settings = Settings.getSettingsFromFile();

        JPanel selectGraphics = new JPanel();
        selectGraphics.setBorder(
                BorderFactory.createTitledBorder("Отображение графиков"));
        selectGraphics.setLayout(new BorderLayout());

        JPanel allMatchPanel = new JPanel(new VerticalLayout());

        Font font = new Font("Arial", Font.BOLD, 15);
        final JCheckBox goals  = new JCheckBox("Голы в основное время", settings.showGoals);
        allMatchPanel.add(goals);

        final JCheckBox goalsOT = new JCheckBox("Голы с учетом ОТ и буллитов", settings.showGoalsOT);
        allMatchPanel.add(goalsOT);

        final JCheckBox realization = new JCheckBox("Реализация бросков", settings.showRealization);
        allMatchPanel.add(realization);

        final JCheckBox powerPlay = new JCheckBox("Реализация большинства", settings.showPowerPlay);
        allMatchPanel.add(powerPlay);

        final JCheckBox shortHanded = new JCheckBox("Меньшинство", settings.showShortHanded);
        allMatchPanel.add(shortHanded);

        final JCheckBox timeInAttack  = new JCheckBox("Время в атаке", settings.showTimeInAttack);
        allMatchPanel.add(timeInAttack);

        final JCheckBox goals1per  = new JCheckBox("Голы в первом периоде", settings.showGoals1per);
        allMatchPanel.add(goals1per);

        final JCheckBox goals2per  = new JCheckBox("Голы во втором периоде", settings.showGoals2per);
        allMatchPanel.add(goals2per);

        final JCheckBox goals3per = new JCheckBox("Голы в третьем периоде", settings.showGoals3per);
        allMatchPanel.add(goals3per);

        final JCheckBox goalsShortHanded  = new JCheckBox("Забитые голы в меньшинстве", settings.showGoalsShortHanded);
        allMatchPanel.add(goalsShortHanded);

        final JCheckBox shotsOnTarget  = new JCheckBox("Броски в створ", settings.showShotsOnTarget);
        allMatchPanel.add(shotsOnTarget);

        final JCheckBox shotsOnTarget1per  = new JCheckBox("Броски в створ 1 пер.", settings.showShotsOnTarget1per);
        allMatchPanel.add(shotsOnTarget1per);

        final JCheckBox shotsOnTarget2per  = new JCheckBox("Броски в створ 2 пер.", settings.showShotsOnTarget2per);
        allMatchPanel.add(shotsOnTarget2per);

        final JCheckBox shotsOnTarget3per  = new JCheckBox("Броски в створ 3 пер.", settings.showShotsOnTarget3per);
        allMatchPanel.add(shotsOnTarget3per);

        final JCheckBox missedShots  = new JCheckBox("Броски мимо", settings.showMissedShots);
        allMatchPanel.add(missedShots);

        final JCheckBox blockedShots  = new JCheckBox("Заблокированные броски", settings.showBlockedShots);
        allMatchPanel.add(blockedShots);

        final JCheckBox faceoffs  = new JCheckBox("Вбрасывания", settings.showFaceoffs);
        allMatchPanel.add(faceoffs);

        final JCheckBox hits  = new JCheckBox("Силовые приемы", settings.showHits);
        allMatchPanel.add(hits);

        final JCheckBox penMinutes  = new JCheckBox("Минуты штрафа", settings.showPenMinutes);
        allMatchPanel.add(penMinutes);

        final JCheckBox numberOf2MinutesPen  = new JCheckBox("Кол-во двухминутных удалений", settings.showNumberOf2MinutesPen);
        allMatchPanel.add(numberOf2MinutesPen);

        final JCheckBox showTotal  = new JCheckBox("Графики тоталов", settings.showTotal);
        allMatchPanel.add(showTotal);

        final JCheckBox showSoTOpponents  = new JCheckBox("Таблица по броскам соперников", settings.showSoTOpponents);
        allMatchPanel.add(showSoTOpponents);

        final JCheckBox showPenOpponents  = new JCheckBox("Таблица по штрафам соперников", settings.showPenOpponents);
        allMatchPanel.add(showPenOpponents);

        /*final JCheckBox dispMinMax  = new JCheckBox("Показывать дисперсии/MAX/MIN бросков и штрафов", settings.dispMinMax);
        dispMinMax.setLocation(20, otstup);
        otstup +=30;
        dispMinMax.setSize(240, 20);
        selectGraphics.add(dispMinMax);*/

        final JCheckBox useColors  = new JCheckBox("Цветовое оформление таблиц", settings.useColors);
        allMatchPanel.add(useColors);

        selectGraphics.add(allMatchPanel, BorderLayout.WEST);
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

        sp = new JScrollPane(mainPanel);
        sp.setVerticalScrollBar( new JScrollBar() {
            public int getUnitIncrement( int direction ) {
                return 50;
            }
        } );
        this.add(sp, BorderLayout.WEST);

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

        JPanel panelReloadDatabase = new JPanel(new VerticalLayout());
        panelReloadDatabase.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton buttonReloadDatabase = new JButton("Перезагрузка БД");
        buttonReloadDatabase.setFont(font);
        buttonReloadDatabase.setPreferredSize(new Dimension(200, 30));
        panelReloadDatabase.add(buttonReloadDatabase);

        this.add(panelReloadDatabase, BorderLayout.EAST);

        buttonReloadDatabase.addActionListener(e -> {
            Settings.setLastUpdate("323_14.02.2020.txt");
            buttonReloadDatabase.setText("Перезапустите программу!");
            buttonReloadDatabase.setEnabled(false);
        });
    }
}