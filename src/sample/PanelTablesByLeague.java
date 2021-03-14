package sample;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SubCategoryAxis;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.tabbedui.VerticalLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.*;

public class PanelTablesByLeague extends JPanel{
    JScrollPane scrollPane;
    final String path = "database/";

    ArrayList<Selector> arrayList = new ArrayList<>();
    //Renderer renderer = new Renderer();
    TablesThread tablesThread;
    String leagueName;
    String season;
    String parameter;
    String lastOrFull;
    String lastCalculatedLeague = "";
    String lastCalculatedSeason = "";
    String lastCalculatedLastOrFull = "";
    final JButton buttonShowInfo;
    JPanel panelWithTablesByLeague;
    JComboBox<String> seasonCB;
    JComboBox<String> leagueChooser;
    League league;
    Settings settings;

    public PanelTablesByLeague(){
        this.setLayout(new BorderLayout());
        final String curSeason = Settings.getDefaultSeason();

        ////////////////////////////////////////////ПАНЕЛЬ
        final JPanel headersPanel = new JPanel(new GridLayout(1, 0, 5, 5));

        ArrayList<String> listOfSeasons = Settings.getListOfSeasons();
        String[] seasonList = new String[listOfSeasons.size()];
        for (int i = 0; i < seasonList.length; i++)
            seasonList[i] = "Сезон " + listOfSeasons.get(i);
        seasonCB = new JComboBox<>(seasonList);
        headersPanel.add(seasonCB);

        JFileChooser fileChooser = new JFileChooser(path + curSeason + "/leagues");
        String[] directoryList = fileChooser.getCurrentDirectory().list();
        final ArrayList<String> leagueList = new ArrayList<>();
        leagueList.add("Выберите лигу");
        for (String aDirectoryList : directoryList) leagueList.add(aDirectoryList.replace(".txt", ""));
        String[] listOfLeagues = new String[leagueList.size()];
        for (int i = 0; i < listOfLeagues.length; i++)
            listOfLeagues[i] = leagueList.get(i);
        leagueChooser = new JComboBox<>(listOfLeagues);
        headersPanel.add(leagueChooser);

        buttonShowInfo = new JButton("Отобразить!");
        Font fontForButton = new Font("", 0, 20);
        buttonShowInfo.setFont(fontForButton);
        headersPanel.add(buttonShowInfo);

        this.add(headersPanel, BorderLayout.NORTH);

        final JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder(""));

        scrollPane = new JScrollPane();
        infoPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(infoPanel);

        leagueChooser.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                int index = leagueChooser.getSelectedIndex();
                String pathToLeaguesList = path + seasonCB.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/";
                JFileChooser fileChooser = new JFileChooser(pathToLeaguesList);
                String[] rightDirectoryList = new String[fileChooser.getCurrentDirectory().list().length+1];
                rightDirectoryList[0] = "Выберите лигу";
                for (int i=1; i<rightDirectoryList.length; i++)
                    rightDirectoryList[i] = fileChooser.getCurrentDirectory().list()[i-1].replace(".txt", "");
                DefaultComboBoxModel modelH = new DefaultComboBoxModel(rightDirectoryList);
                leagueChooser.setModel(modelH);
                leagueChooser.setSelectedIndex(index);
                leagueChooser.setFocusable(false);
                leagueName = leagueChooser.getSelectedItem().toString();

            }
        });

        seasonCB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seasonCB.setFocusable(false);
                season = seasonCB.getSelectedItem().toString();

                String league = String.valueOf(leagueChooser.getSelectedItem());

                String pathToLeaguesList = path + seasonCB.getSelectedItem().toString().replace("Сезон ", "") + "/leagues/";
                JFileChooser fileChooser = new JFileChooser(pathToLeaguesList);
                String[] directoryList = new String[fileChooser.getCurrentDirectory().list().length+1];
                directoryList[0] = "Выберите лигу";
                for (int i=1; i<directoryList.length; i++)
                    directoryList[i] = fileChooser.getCurrentDirectory().list()[i-1].replace(".txt", "");
                DefaultComboBoxModel modelH = new DefaultComboBoxModel(directoryList);
                leagueChooser.setModel(modelH);

                for (int i=0; i<directoryList.length; i++){
                    if (directoryList[i].equals(league))
                        leagueChooser.setSelectedItem(league);
                }
            }
        });

        buttonShowInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infoPanel.removeAll();

                if (!((String) leagueChooser.getSelectedItem()).contains("Выберите")){
                    JScrollPane panel = refreshLeagueData((String) leagueChooser.getSelectedItem(),
                            (String) seasonCB.getSelectedItem()
                    );
                    infoPanel.add(panel);
                    infoPanel.revalidate();
                } else {
                    infoPanel.add(new JLabel("  Не выбрана лига"), BorderLayout.NORTH);
                    infoPanel.revalidate();
                }
                buttonShowInfo.setFocusable(false);

            }
        });

    }

    public JScrollPane refreshLeagueData(final String leagueName, String seasonString){
        JScrollPane scrollPane = null;
//        JScrollPane scrollPane = new JScrollPane();
        this.setCursor(Cursor.getPredefinedCursor (Cursor.WAIT_CURSOR));
        settings = Settings.getSettingsFromFile();

        JPanel container = new JPanel(new VerticalLayout());
        season = seasonString.replace("Сезон ", "");
        this.leagueName = leagueName;

        league = League.getLeagueFromFile(leagueName, season);

        panelWithTablesByLeague = new JPanel(new BorderLayout());
        panelWithTablesByLeague.setBorder(BorderFactory.createTitledBorder("Таблица статистических показателей по командам"));
        String[] paramsForTables;
        if (leagueName.equals("VHL")){
            paramsForTables = new String[]{"Выберите показатель",
                    "Голы", "Голы с учетом ОТиБ", "Голы 1 пер", "Голы 2 пер", "Голы 3 пер",
                    "Броски в створ", "Броски в створ 1 пер", "Броски в створ 2 пер", "Броски в створ 3 пер",
                    "Реализация большинства, %", "Вбрасывания", "Минуты штрафа", "2мин удаления"
            };
        } else {
            paramsForTables = new String[]{"Выберите показатель",
                    "Голы", "Голы с учетом ОТиБ", "Реализация бросков, %", "Голы 1 пер", "Голы 2 пер", "Голы 3 пер",
                    "Броски в створ", "Броски в створ 1 пер", "Броски в створ 2 пер", "Броски в створ 3 пер",
                    "Реализация большинства, %", "Вбрасывания", "Время в атаке, сек.", "Блок.броски", "Сил приемы",
                    "Минуты штрафа", "2мин удаления", "Бр. мимо", "Corsi", "CorsiFor, %", "Fenwick", "FenwickFor, %",
                    "PDO",
            };
        }



        JPanel panelParameter = new JPanel(new GridLayout(1, 3, 10, 10));
        panelParameter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelForTable = new JLabel("Выберите показатели для построения таблицы:  ");
        labelForTable.setFont(new Font("", Font.BOLD, 15));
        panelParameter.add(labelForTable);

        String[] lastOrFullArr = new String[]{"Весь сезон", "Последние 3", "Последние 4", "Последние 5", "Последние 6", "Последние 7", "Последние 8", "Последние 9", "Последние 10", "Последние 15", "Последние 20"};
        final JComboBox<String> lastOrFullChooser = new JComboBox<>(lastOrFullArr);
        lastOrFull = lastOrFullArr[0];
        panelParameter.add(lastOrFullChooser);

        final JComboBox<String> paramChooser = new JComboBox<>(paramsForTables);
        panelParameter.add(paramChooser);

        panelWithTablesByLeague.add(panelParameter, BorderLayout.NORTH);
        container.add(panelWithTablesByLeague);

        JLabel label = new JLabel("Матчей сыграно: " + league.matchesPlayed);
        Font fontLabel = new Font("Arial", Font.BOLD, 15);
        label.setFont(fontLabel);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setBorder(new EmptyBorder(10, 10, 0, 0));
        container.add(label);

        JLabel jtf = new JLabel("Таблица голов в " + leagueName);
        jtf.setFont(new Font("", 0, 18));
        jtf.setHorizontalAlignment(SwingConstants.CENTER);
        jtf.setBorder(new EmptyBorder(10,10,0,10));
        container.add(jtf);

        String[] colHeads = {"Показатель", "Общие", "Хозяева", "Гости", "Разница"};
        String[] params;

        params = new String[]{"  Голы в ОснВр", "  Голы в ОснВр (сред.)",
                "  Голы в 1-ом периоде", "  Голы во 2-ом периоде", "  Голы в 3-ом периоде",
                "  Голы в 1-ом периоде (сред.)", "  Голы во 2-ом периоде (сред.)", "  Голы в 3-ом периоде (сред.)",
                "  Броски все (сред.)", "  Броски в створ (сред.)", "  Броски мимо (сред.)", "  Заблокированные броски (сред.)",
                "  Броски в створ в 1-ом периоде (сред.)", "  Броски в створ во 2-ом периоде (сред.)", "  Броски в створ в 3-ом периоде (сред.)",
                "  Реализация большинства, %", "  Вбрасываний выиграно (сред.) и %",
                "  Время в атаке (сред.)", "  Силовых приемов (сред.)",
                "  Минуты штрафа (сред.)", "  Кол-во двухминутных удалений (сред.)",
                };

        double matches = (double) league.matchesPlayed;

        Object[][] data = new Object[params.length][colHeads.length];
        data[0][0] = params[0];
        data[0][1] = String.valueOf(league.homeGoals + league.awayGoals);
        data[0][2] = String.valueOf(league.homeGoals);
        data[0][3] = String.valueOf(league.awayGoals);
        data[0][4] = String.valueOf(league.homeGoals - league.awayGoals);
        data[1][0] = params[1];
        data[1][1] = String.valueOf(MyMath.round((league.homeGoals + league.awayGoals) / matches , 2));
        data[1][2] = String.valueOf(MyMath.round((league.homeGoals / matches) , 2));
        data[1][3] = String.valueOf(MyMath.round((league.awayGoals / matches) , 2));
        data[1][4] = String.valueOf(MyMath.round((league.homeGoals - league.awayGoals) / matches , 2));
        data[2][0] = params[2];
        data[2][1] = String.valueOf(league.goalsScored1per);
        data[2][2] = String.valueOf(league.homeGoals1per);
        data[2][3] = String.valueOf(league.awayGoals1per);
        data[2][4] = String.valueOf(league.homeGoals1per - league.awayGoals1per);
        data[3][0] = params[3];
        data[3][1] = String.valueOf(league.goalsScored2per);
        data[3][2] = String.valueOf(league.homeGoals2per);
        data[3][3] = String.valueOf(league.awayGoals2per);
        data[3][4] = String.valueOf(league.homeGoals2per - league.awayGoals2per);
        data[4][0] = params[4];
        data[4][1] = String.valueOf(league.goalsScored3per);
        data[4][2] = String.valueOf(league.homeGoals3per);
        data[4][3] = String.valueOf(league.awayGoals3per);
        data[4][4] = String.valueOf(league.homeGoals3per - league.awayGoals3per);
        data[5][0] = params[5];
        data[5][1] = String.valueOf(MyMath.round(league.goalsScored1per / matches , 2));
        data[5][2] = String.valueOf(MyMath.round(league.homeGoals1per / matches , 2));
        data[5][3] = String.valueOf(MyMath.round(league.awayGoals1per / matches , 2));
        data[5][4] = String.valueOf(MyMath.round((league.homeGoals1per - league.awayGoals1per) / matches , 2));
        data[6][0] = params[6];
        data[6][1] = String.valueOf(MyMath.round(league.goalsScored2per / matches , 2));
        data[6][2] = String.valueOf(MyMath.round(league.homeGoals2per / matches , 2));
        data[6][3] = String.valueOf(MyMath.round(league.awayGoals2per / matches , 2));
        data[6][4] = String.valueOf(MyMath.round((league.homeGoals2per - league.awayGoals2per) / matches , 2));
        data[7][0] = params[7];
        data[7][1] = String.valueOf(MyMath.round(league.goalsScored3per / matches , 2));
        data[7][2] = String.valueOf(MyMath.round(league.homeGoals3per / matches , 2));
        data[7][3] = String.valueOf(MyMath.round(league.awayGoals3per / matches , 2));
        data[7][4] = String.valueOf(MyMath.round((league.homeGoals3per - league.awayGoals3per) / matches , 2));
        data[8][0] = params[8];
        data[8][1] = String.valueOf(MyMath.round((league.homeShots + league.awayShots) / matches , 2));
        data[8][2] = String.valueOf(MyMath.round(league.homeShots / matches , 2));
        data[8][3] = String.valueOf(MyMath.round(league.awayShots / matches , 2));
        data[8][4] = String.valueOf(MyMath.round((league.homeShots - league.awayShots) / matches , 2));
        data[9][0] = params[9];
        data[9][1] = String.valueOf(MyMath.round((league.homeShotsOnTarget + league.awayShotsOnTarget) / matches , 2));
        data[9][2] = String.valueOf(MyMath.round(league.homeShotsOnTarget / matches , 2));
        data[9][3] = String.valueOf(MyMath.round(league.awayShotsOnTarget / matches , 2));
        data[9][4] = String.valueOf(MyMath.round((league.homeShotsOnTarget - league.awayShotsOnTarget) / matches , 2));
        data[10][0] = params[10];
        data[10][1] = String.valueOf(MyMath.round((league.homeMissedShots + league.awayMissedShots) / matches , 2));
        data[10][2] = String.valueOf(MyMath.round(league.homeMissedShots / matches , 2));
        data[10][3] = String.valueOf(MyMath.round(league.awayMissedShots / matches , 2));
        data[10][4] = String.valueOf(MyMath.round((league.homeMissedShots - league.awayMissedShots) / matches , 2));
        data[11][0] = params[11];
        data[11][1] = String.valueOf(MyMath.round((league.homeBlockedShots + league.awayBlockedShots) / matches , 2));
        data[11][2] = String.valueOf(MyMath.round(league.homeBlockedShots / matches , 2));
        data[11][3] = String.valueOf(MyMath.round(league.awayBlockedShots / matches , 2));
        data[11][4] = String.valueOf(MyMath.round((league.homeBlockedShots - league.awayBlockedShots) / matches , 2));
        data[12][0] = params[12];
        data[12][1] = String.valueOf(MyMath.round((league.homeShotsOnTarget1per + league.awayShotsOnTarget1per) / matches , 2));
        data[12][2] = String.valueOf(MyMath.round(league.homeShotsOnTarget1per / matches , 2));
        data[12][3] = String.valueOf(MyMath.round(league.awayShotsOnTarget1per / matches , 2));
        data[12][4] = String.valueOf(MyMath.round((league.homeShotsOnTarget1per - league.awayShotsOnTarget1per) / matches , 2));
        data[13][0] = params[13];
        data[13][1] = String.valueOf(MyMath.round((league.homeShotsOnTarget2per + league.awayShotsOnTarget2per) / matches , 2));
        data[13][2] = String.valueOf(MyMath.round(league.homeShotsOnTarget2per / matches , 2));
        data[13][3] = String.valueOf(MyMath.round(league.awayShotsOnTarget2per / matches , 2));
        data[13][4] = String.valueOf(MyMath.round((league.homeShotsOnTarget2per - league.awayShotsOnTarget2per) / matches , 2));
        data[14][0] = params[14];
        data[14][1] = String.valueOf(MyMath.round((league.homeShotsOnTarget3per + league.awayShotsOnTarget3per) / matches , 2));
        data[14][2] = String.valueOf(MyMath.round(league.homeShotsOnTarget3per / matches , 2));
        data[14][3] = String.valueOf(MyMath.round(league.awayShotsOnTarget3per / matches , 2));
        data[14][4] = String.valueOf(MyMath.round((league.homeShotsOnTarget3per - league.awayShotsOnTarget3per) / matches , 2));
        data[15][0] = params[15];
        data[15][1] = MyMath.round(100 * (league.homeGoalsInPP + league.awayGoalsInPP) / (double) (league.homePowerPlays + league.awayPowerPlays) , 2) + "%";
        data[15][2] = MyMath.round(100 * league.homeGoalsInPP / (double) league.homePowerPlays , 2) + "%";
        data[15][3] = MyMath.round(100 * league.awayGoalsInPP / (double) league.awayPowerPlays , 2) + "%";
        data[15][4] = "-";
        data[16][0] = params[16];
        data[16][1] = MyMath.round((league.homeFaceoffsWon + league.awayFaceoffsWon) / matches , 2);
        data[16][2] = MyMath.round(league.homeFaceoffsWon / matches, 2) + " (" + MyMath.round(100 * league.homeFaceoffsWon / (double) (league.homeFaceoffsWon + league.awayFaceoffsWon), 2) + "%)";
        data[16][3] = MyMath.round(league.awayFaceoffsWon / matches, 2) + " (" + MyMath.round(100 * league.awayFaceoffsWon / (double) (league.homeFaceoffsWon + league.awayFaceoffsWon), 2) + "%)";
        data[16][4] = "-";
        data[17][0] = params[17];
        data[17][1] = Settings.getTimeInMinutesAndSecondsFromSeconds( (int) ((league.homeTimeInAttack + league.awayTimeInAttack) / matches ) );
        data[17][2] = Settings.getTimeInMinutesAndSecondsFromSeconds( (int) (league.homeTimeInAttack / matches ) );
        data[17][3] = Settings.getTimeInMinutesAndSecondsFromSeconds( (int) (league.awayTimeInAttack / matches ) );
        data[17][4] = Settings.getTimeInMinutesAndSecondsFromSeconds( (int) ((league.homeTimeInAttack - league.awayTimeInAttack) / matches ) );
        data[18][0] = params[18];
        data[18][1] = String.valueOf(MyMath.round((league.homeHits + league.awayHits) / matches , 2));
        data[18][2] = String.valueOf(MyMath.round(league.homeHits / matches , 2));
        data[18][3] = String.valueOf(MyMath.round(league.awayHits / matches , 2));
        data[18][4] = String.valueOf(MyMath.round((league.homeHits - league.awayHits) / matches , 2));
        data[19][0] = params[19];
        data[19][1] = String.valueOf(MyMath.round((league.homePenMinutes + league.awayPenMinutes) / matches , 2));
        data[19][2] = String.valueOf(MyMath.round(league.homePenMinutes / matches , 2));
        data[19][3] = String.valueOf(MyMath.round(league.awayPenMinutes / matches , 2));
        data[19][4] = String.valueOf(MyMath.round((league.homePenMinutes - league.awayPenMinutes) / matches , 2));
        data[20][0] = params[20];
        data[20][1] = String.valueOf(MyMath.round((league.home2MinPenalties + league.away2MinPenalties) / matches , 2));
        data[20][2] = String.valueOf(MyMath.round(league.home2MinPenalties / matches , 2));
        data[20][3] = String.valueOf(MyMath.round(league.away2MinPenalties / matches , 2));
        data[20][4] = String.valueOf(MyMath.round((league.home2MinPenalties - league.away2MinPenalties) / matches , 2));

        JTable table = new JTable(data, colHeads);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setEnabled(false);
        table.getTableHeader().setFont(fontLabel);
        table.setFont(fontLabel);
        table.setRowHeight(25);
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
//        renderer.setHorizontalAlignment(JLabel.CENTER);
        for (int r=1; r<colHeads.length; r++)
            table.getColumnModel().getColumn(r).setCellRenderer(centerRenderer);
//            table.getColumnModel().getColumn(r).setCellRenderer(renderer);


        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(table, BorderLayout.CENTER);
        tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);

        container.add(tablePanel);

        JPanel panelButton = new JPanel(new BorderLayout());
        JButton buttonShowBubble = new JButton("Отобразить графики перекрестных показателей");
        buttonShowBubble.setFont(fontLabel);
        panelButton.setBorder(BorderFactory.createEmptyBorder(5, 300, 5, 300));

        if (leagueName.equals("VHL")){
            buttonShowBubble.setEnabled(false);
        }
        panelButton.add(buttonShowBubble);

        container.add(panelButton);

        JPanel bubbleChartsPanel = new JPanel(new BorderLayout());
        container.add(bubbleChartsPanel);


        jtf = new JLabel("Диаграммы голов в основное время в " + leagueName);
        jtf.setFont(new Font("", Font.BOLD, 18));
        jtf.setHorizontalAlignment(SwingConstants.CENTER);
        jtf.setBorder(new EmptyBorder(10,10,0,10));
        container.add(jtf);

        DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
        // row keys...
        String series1 = "Победа хозяев";
        String series2 = "Ничья";
        String series3 = "Победа гостей";
        // column keys...
        String category1 = "Матч" ;
        String category2 = "1-ый период" ;
        String category3 = "2-ой период" ;
        String category4 = "3-ий период" ;

        categoryDataset.addValue(Double.parseDouble(league.g_homeWin_draw_awayWin.split("\\*")[0]), series1, category1);
        categoryDataset.addValue(Double.parseDouble(league.g_homeWin_draw_awayWin.split("\\*")[1]), series2, category1);
        categoryDataset.addValue(Double.parseDouble(league.g_homeWin_draw_awayWin.split("\\*")[2]), series3, category1);

        categoryDataset.addValue(Double.parseDouble(league.g_homeWin_draw_awayWin_1per.split("\\*")[0]), series1, category2);
        categoryDataset.addValue(Double.parseDouble(league.g_homeWin_draw_awayWin_1per.split("\\*")[1]), series2, category2);
        categoryDataset.addValue(Double.parseDouble(league.g_homeWin_draw_awayWin_1per.split("\\*")[2]), series3, category2);

        categoryDataset.addValue(Double.parseDouble(league.g_homeWin_draw_awayWin_2per.split("\\*")[0]), series1, category3);
        categoryDataset.addValue(Double.parseDouble(league.g_homeWin_draw_awayWin_2per.split("\\*")[1]), series2, category3);
        categoryDataset.addValue(Double.parseDouble(league.g_homeWin_draw_awayWin_2per.split("\\*")[2]), series3, category3);

        categoryDataset.addValue(Double.parseDouble(league.g_homeWin_draw_awayWin_3per.split("\\*")[0]), series1, category4);
        categoryDataset.addValue(Double.parseDouble(league.g_homeWin_draw_awayWin_3per.split("\\*")[1]), series2, category4);
        categoryDataset.addValue(Double.parseDouble(league.g_homeWin_draw_awayWin_3per.split("\\*")[2]), series3, category4);

        JFreeChart chartWinDrawLose = ChartFactory.createBarChart(
                "Исходы матчей и таймов", "", "", categoryDataset, PlotOrientation.VERTICAL, true, true, false);

        // Определение фона диаграммы
        chartWinDrawLose.setBackgroundPaint(new Color(238, 238, 238));
        chartWinDrawLose.getTitle().setFont(new Font("", Font.BOLD, 18));
        chartWinDrawLose.getTitle().setMargin(10,0,0,0);
        // Настройка plot'а
        CategoryPlot plotWinDrawLose = chartWinDrawLose.getCategoryPlot();
        plotWinDrawLose.setBackgroundPaint(new Color(238, 238, 238));
        plotWinDrawLose.getRenderer().setSeriesPaint(0, new Color(255, 40, 40 ));
        plotWinDrawLose.getRenderer().setSeriesPaint(1, new Color(255, 220, 60 ));
        plotWinDrawLose.getRenderer().setSeriesPaint(2, new Color(40, 40, 255 ));

        plotWinDrawLose.setDomainGridlinePaint(Color.black);
        plotWinDrawLose.setRangeGridlinePaint(Color.black);
        NumberAxis axis = (NumberAxis) plotWinDrawLose.getRangeAxis();
        axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        BarRenderer renderer = (BarRenderer)plotWinDrawLose.getRenderer();
        renderer.setItemMargin(0.02);
        SubCategoryAxis subCategoryAxis = new SubCategoryAxis("");
        subCategoryAxis.setCategoryMargin(0.15);

        ChartPanel cp = new ChartPanel(chartWinDrawLose);
//        cp.setPreferredSize(new Dimension(500, graphicHeight));
        container.add(cp);

        jtf = new JLabel("Тоталы голов в " + leagueName);
        jtf.setFont(new Font("", Font.BOLD, 18));
        jtf.setHorizontalAlignment(SwingConstants.CENTER);
        jtf.setBorder(new EmptyBorder(10,10,0,10));
        container.add(jtf);

        categoryDataset = new DefaultCategoryDataset();
        // row keys...
        String series0 = "0";
        series1 = "1";
        series2 = "2";
        series3 = "3";
        String series4 = "4";
        String series5 = "5";
        String series6 = "6";
        String series7 = "7";
        String series8 = "8";
        String series9 = "9";
        String series10 = "10+";

        // column keys...
        category1 = "Матч" ;
        category2 = "1-ый период" ;
        category3 = "2-ой период" ;
        category4 = "3-ий период" ;

        categoryDataset.addValue(Double.parseDouble(league.g_totals.split("\\*")[0]), series0, category1);
        categoryDataset.addValue(Double.parseDouble(league.g_totals.split("\\*")[1]), series1, category1);
        categoryDataset.addValue(Double.parseDouble(league.g_totals.split("\\*")[2]), series2, category1);
        categoryDataset.addValue(Double.parseDouble(league.g_totals.split("\\*")[3]), series3, category1);
        categoryDataset.addValue(Double.parseDouble(league.g_totals.split("\\*")[4]), series4, category1);
        categoryDataset.addValue(Double.parseDouble(league.g_totals.split("\\*")[5]), series5, category1);
        categoryDataset.addValue(Double.parseDouble(league.g_totals.split("\\*")[6]), series6, category1);
        categoryDataset.addValue(Double.parseDouble(league.g_totals.split("\\*")[7]), series7, category1);
        categoryDataset.addValue(Double.parseDouble(league.g_totals.split("\\*")[8]), series8, category1);
        categoryDataset.addValue(Double.parseDouble(league.g_totals.split("\\*")[9]), series9, category1);
        categoryDataset.addValue(Double.parseDouble(league.g_totals.split("\\*")[10]), series10, category1);

        categoryDataset.addValue(Double.parseDouble(league.g_totals_1per.split("\\*")[0]), series0, category2);
        categoryDataset.addValue(Double.parseDouble(league.g_totals_1per.split("\\*")[1]), series1, category2);
        categoryDataset.addValue(Double.parseDouble(league.g_totals_1per.split("\\*")[2]), series2, category2);
        categoryDataset.addValue(Double.parseDouble(league.g_totals_1per.split("\\*")[3]), series3, category2);
        categoryDataset.addValue(Double.parseDouble(league.g_totals_1per.split("\\*")[4]), series4, category2);
        categoryDataset.addValue(Double.parseDouble(league.g_totals_1per.split("\\*")[5]), series5, category2);
        categoryDataset.addValue(Double.parseDouble(league.g_totals_1per.split("\\*")[6]), series6, category2);

        categoryDataset.addValue(Double.parseDouble(league.g_totals_2per.split("\\*")[0]), series0, category3);
        categoryDataset.addValue(Double.parseDouble(league.g_totals_2per.split("\\*")[1]), series1, category3);
        categoryDataset.addValue(Double.parseDouble(league.g_totals_2per.split("\\*")[2]), series2, category3);
        categoryDataset.addValue(Double.parseDouble(league.g_totals_2per.split("\\*")[3]), series3, category3);
        categoryDataset.addValue(Double.parseDouble(league.g_totals_2per.split("\\*")[4]), series4, category3);
        categoryDataset.addValue(Double.parseDouble(league.g_totals_2per.split("\\*")[5]), series5, category3);
        categoryDataset.addValue(Double.parseDouble(league.g_totals_2per.split("\\*")[6]), series6, category3);

        categoryDataset.addValue(Double.parseDouble(league.g_totals_3per.split("\\*")[0]), series0, category4);
        categoryDataset.addValue(Double.parseDouble(league.g_totals_3per.split("\\*")[1]), series1, category4);
        categoryDataset.addValue(Double.parseDouble(league.g_totals_3per.split("\\*")[2]), series2, category4);
        categoryDataset.addValue(Double.parseDouble(league.g_totals_3per.split("\\*")[3]), series3, category4);
        categoryDataset.addValue(Double.parseDouble(league.g_totals_3per.split("\\*")[4]), series4, category4);
        categoryDataset.addValue(Double.parseDouble(league.g_totals_3per.split("\\*")[5]), series5, category4);
        categoryDataset.addValue(Double.parseDouble(league.g_totals_3per.split("\\*")[6]), series6, category4);

        JFreeChart chartTotals = ChartFactory.createBarChart(
                "", "", "", categoryDataset, PlotOrientation.VERTICAL, true, true, false);

        // Определение фона диаграммы
        chartTotals.setBackgroundPaint(new Color(238, 238, 238));
        chartTotals.getTitle().setFont(new Font("", Font.BOLD, 18));
        chartTotals.getTitle().setMargin(10,0,0,0);
        // Настройка plot'а
        CategoryPlot plotTotals = chartTotals.getCategoryPlot();
        plotTotals.setBackgroundPaint(new Color(238, 238, 238));
        plotTotals.getRenderer().setSeriesPaint(0, new Color(255, 40, 40));
        plotTotals.getRenderer().setSeriesPaint(1, new Color(40, 40, 255));
        plotTotals.getRenderer().setSeriesPaint(2, new Color(0, 140, 20));
        plotTotals.getRenderer().setSeriesPaint(3, new Color(242, 120, 21));
        plotTotals.getRenderer().setSeriesPaint(4, new Color(187,  46, 230));
        plotTotals.getRenderer().setSeriesPaint(5, new Color(4, 175, 230));
        plotTotals.getRenderer().setSeriesPaint(6, new Color(255, 220, 60));
        plotTotals.getRenderer().setSeriesPaint(7, new Color(255, 144, 246));
        plotTotals.getRenderer().setSeriesPaint(8, new Color(255, 180, 74));
        plotTotals.getRenderer().setSeriesPaint(9, new Color(71, 255, 235));
        plotTotals.getRenderer().setSeriesPaint(10, new Color(51, 255, 11));

        plotTotals.setDomainGridlinePaint(Color.black);
        plotTotals.setRangeGridlinePaint(Color.black);
        axis = (NumberAxis) plotTotals.getRangeAxis();
        axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        renderer = (BarRenderer)plotTotals.getRenderer();
        renderer.setItemMargin(0.02);
        subCategoryAxis = new SubCategoryAxis("");
        subCategoryAxis.setCategoryMargin(0.15);

        cp = new ChartPanel(chartTotals);
//        cp.setPreferredSize(new Dimension(500, graphicHeight));
        container.add(cp);

        JPanel panelPieDiagram = new JPanel(new GridLayout(1, 0, 0, 0));
        DefaultPieDataset dataset = new DefaultPieDataset( );
        dataset.setValue("ДА",  league.g_OZ15 );
        dataset.setValue("НЕТ", league.matchesPlayed - league.g_OZ15 );

        JFreeChart chart = ChartFactory.createPieChart( "Обе забьют > 1.5 гола в " + leagueName, dataset, true, true, false);
        chart.setBackgroundPaint(new Color(238, 238, 238));
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1} \n {2}"));
        plot.setBackgroundPaint(new Color(238, 238, 238));
        plot.setSectionPaint("ДА", new Color(0, 240, 20 ));
        plot.setSectionPaint("НЕТ", new Color(255, 40, 40 ));

        cp = new ChartPanel(chart);
        panelPieDiagram.add(cp);

        dataset = new DefaultPieDataset( );
        dataset.setValue("ДА", league.g_goalsInAllPeriods );
        dataset.setValue("НЕТ", league.matchesPlayed - league.g_goalsInAllPeriods );

        chart = ChartFactory.createPieChart( "Голы в каждом периоде", dataset, true, true, false);
        chart.setBackgroundPaint(new Color(238, 238, 238));
        plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1} \n {2}"));
        plot.setBackgroundPaint(new Color(238, 238, 238));
        plot.setSectionPaint("ДА", new Color(0, 240, 20 ));
        plot.setSectionPaint("НЕТ", new Color(255, 40, 40 ));

        cp = new ChartPanel(chart);
        panelPieDiagram.add(cp);

        dataset = new DefaultPieDataset( );
        double under55 = 0;
        for (int i=0; i< 6; i++){
            under55 += Double.parseDouble(league.g_totals.split("\\*")[i]);
        }
        dataset.setValue("ТБ(5.5)", league.matchesPlayed - under55);
        dataset.setValue("ТМ(5.5)", under55);

        chart = ChartFactory.createPieChart( "Тотал 5.5 в матчах " + leagueName, dataset, true, true, false);
        chart.setBackgroundPaint(new Color(238, 238, 238));
        plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1} \n {2}"));
        plot.setBackgroundPaint(new Color(238, 238, 238));
        plot.setSectionPaint("ТБ(5.5)", new Color(0, 240, 20 ));
        plot.setSectionPaint("ТМ(5.5)", new Color(255, 40, 40 ));

        cp = new ChartPanel(chart);
        panelPieDiagram.add(cp);
        panelPieDiagram.setPreferredSize(new Dimension(this.getWidth()-20, 300));

        container.add(panelPieDiagram);


        lastOrFullChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lastOrFull = String.valueOf(lastOrFullChooser.getSelectedItem());
            }
        });

        paramChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parameter = paramChooser.getSelectedItem().toString();
                tablesThread = new TablesThread(leagueName, parameter, season, lastOrFull, (PanelTablesByLeague) buttonShowInfo.getParent().getParent());
                tablesThread.start();
            }
        });

        buttonShowBubble.addActionListener(e -> {
            if ( bubbleChartsPanel.getComponentCount() == 0){
                bubbleChartsPanel.add(getBubbleCharts());
                buttonShowBubble.setText("Скрыть графики перекрестных показателей");
            } else {
                bubbleChartsPanel.removeAll();
                buttonShowBubble.setText("Отобразить графики перекрестных показателей");
            }

        });

        scrollPane = new JScrollPane(container);
        scrollPane.setVerticalScrollBar( new JScrollBar() {
            public int getUnitIncrement( int direction ) {
                return 50;
            }
        } );
        this.setCursor(Cursor.getDefaultCursor());
        return scrollPane;
    }

    public void setFilters(String league){
        buttonShowInfo.setEnabled(false);
        String season = Settings.getCurrentSeasonInLeague(league);
        seasonCB.setSelectedItem("Сезон " + season);
        leagueChooser.setSelectedItem(league);
        buttonShowInfo.setEnabled(true);
    }

    public JPanel getBubbleCharts(){
        JPanel result = new JPanel(new GridLayout(0, 2, 0, 0));

        ArrayList<String> table1; // основная / домашняя таблица
        ArrayList<String> table2; // основная / выездная таблица
        int numberOfCharts = 5;
        int totalCharts = 5;

        ArrayList<String> graphicTitles = new ArrayList<>();

        if (settings.bubbleChartsHA){
            table1 = league.homeStatsTable;
            table2 = league.awayStatsTable;
            numberOfCharts *= 2;

            graphicTitles.add("Дома: Голы в ОВ + % реал. бросков");
            graphicTitles.add("На выезде: Голы в ОВ + % реал. бросков");
            graphicTitles.add("Дома: Проп.голы в ОВ + % реал. бросков противника");
            graphicTitles.add("На выезде: Проп.голы в ОВ + % реал. бросков противника");
            graphicTitles.add("Дома: % реал. большинства + % 'убийства' меньшинства");
            graphicTitles.add("На выезде: % реал. большинства + % 'убийства' меньшинства");
            graphicTitles.add("Дома: Броски в створ + Броски мимо");
            graphicTitles.add("На выезде: Броски в створ + Броски мимо");
            graphicTitles.add("Дома: Силовые приемы + 2 минутные удаления");
            graphicTitles.add("На выезде: Силовые приемы + 2 минутные удаления");

        } else {
            table1 = league.overallStatsTable;
            table2 = league.overallStatsTable;

            graphicTitles.add("Весь сезон: Голы в ОВ + % реал. бросков");
            graphicTitles.add("Весь сезон: Проп.голы в ОВ + % реал. бросков противника");
            graphicTitles.add("Весь сезон: % реал. большинства + % 'убийства' меньшинства");
            graphicTitles.add("Весь сезон: Броски в створ + Броски мимо");
            graphicTitles.add("Весь сезон: Силовые приемы + 2 минутные удаления");
        }

        int numberOfTeams = league.overallStatsTable.size();
        ArrayList<String> teamsList = new ArrayList<>();
        double [][][] data = new double[numberOfCharts][numberOfTeams][2];

        for (int i=0; i<numberOfTeams; i++){
            int index = 0;
            int matches1 = Integer.parseInt(table1.get(i).split("\\*")[1]);
            int matches2 = Integer.parseInt(table2.get(i).split("\\*")[1]);
            teamsList.add(table1.get(i).split("\\*")[0]);

            for(int j=0; j<totalCharts; j++){
                double par1_Team1 = 0;
                double par2_Team1 = 0;

                double par1_Team2 = 0;
                double par2_Team2 = 0;

                switch (j){
                    case 0:{ // Голы в ОВ + % реал. бросков
                        par1_Team1 = MyMath.round(Double.parseDouble(table1.get(i).split("\\*")[2].split("_")[0]) / matches1,4);
                        par2_Team1 = MyMath.round(100 * Double.parseDouble(table1.get(i).split("\\*")[2].split("_")[0]) / Double.parseDouble(table1.get(i).split("\\*")[7].split("_")[0]),4);

                        par1_Team2 = MyMath.round(Double.parseDouble(table2.get(i).split("\\*")[2].split("_")[0]) / matches2,4);
                        par2_Team2 = MyMath.round(100 * Double.parseDouble(table2.get(i).split("\\*")[2].split("_")[0]) / Double.parseDouble(table2.get(i).split("\\*")[7].split("_")[0]),4);
                        break;
                    }
                    case 1:{// Проп.голы в ОВ + % реал. бросков противника
                        par1_Team1 = MyMath.round(Double.parseDouble(table1.get(i).split("\\*")[2].split("_")[1]) / matches1,4);
                        par2_Team1 = MyMath.round(100 * Double.parseDouble(table1.get(i).split("\\*")[2].split("_")[1]) / Double.parseDouble(table1.get(i).split("\\*")[7].split("_")[1]),4);

                        par1_Team2 = MyMath.round(Double.parseDouble(table2.get(i).split("\\*")[2].split("_")[1])  / matches2,4);
                        par2_Team2 = MyMath.round(100 * Double.parseDouble(table2.get(i).split("\\*")[2].split("_")[1]) / Double.parseDouble(table2.get(i).split("\\*")[7].split("_")[1]),4);
                        break;
                    }
                    case 2:{// % реал. большинства + % 'убийства' меньшинства
                        par1_Team1 = MyMath.round(100 * Double.parseDouble(table1.get(i).split("\\*")[19].split("_")[0]) / Double.parseDouble(table1.get(i).split("\\*")[20].split("_")[0]),4);
                        par2_Team1 = MyMath.round(100 * Double.parseDouble(table1.get(i).split("\\*")[19].split("_")[1]) / Double.parseDouble(table1.get(i).split("\\*")[20].split("_")[1]),4);

                        par1_Team2 = MyMath.round(100 * Double.parseDouble(table2.get(i).split("\\*")[19].split("_")[0]) / Double.parseDouble(table2.get(i).split("\\*")[20].split("_")[0]),4);
                        par2_Team2 = MyMath.round(100 * Double.parseDouble(table2.get(i).split("\\*")[19].split("_")[1]) / Double.parseDouble(table2.get(i).split("\\*")[20].split("_")[1]),4);
                        break;
                    }
                    case 3:{ // Броски в створ + Броски мимо
                        par1_Team1 = MyMath.round(Double.parseDouble(table1.get(i).split("\\*")[7].split("_")[0]) / matches1,4);
                        par2_Team1 = MyMath.round(Double.parseDouble(table1.get(i).split("\\*")[18].split("_")[0]) / matches1,4);

                        par1_Team2 = MyMath.round(Double.parseDouble(table2.get(i).split("\\*")[7].split("_")[0]) / matches2,4);
                        par2_Team2 = MyMath.round(Double.parseDouble(table2.get(i).split("\\*")[18].split("_")[0]) / matches2,4);

                        break;
                    }
                    case 4:{ // Силовые приемы + 2 минутные удаления
                        par1_Team1 = MyMath.round(Double.parseDouble(table1.get(i).split("\\*")[15].split("_")[0]) / matches1,4);
                        par2_Team1 = MyMath.round(Double.parseDouble(table1.get(i).split("\\*")[17].split("_")[0]) / matches1,4);

                        par1_Team2 = MyMath.round(Double.parseDouble(table2.get(i).split("\\*")[15].split("_")[0]) / matches2,4);
                        par2_Team2 = MyMath.round(Double.parseDouble(table2.get(i).split("\\*")[17].split("_")[0]) / matches2,4);

                        break;
                    }
                    /*case 4:{

                        break;
                    }
                    case 5:{

                        break;
                    }
                    case 6:{

                        break;
                    }
                    case 7:{

                        break;
                    }
                    case 8:{

                        break;
                    }
                    case 9:{

                        break;
                    }*/
                }


                if (settings.bubbleChartsHA){
                    data[index][i][0] = par1_Team1;
                    data[index][i][1] = par2_Team1;

                    data[index+1][i][0] = par1_Team2;
                    data[index+1][i][1] = par2_Team2;

                    index += 2;
                } else {
                    data[index][i][0] = par1_Team1;
                    data[index][i][1] = par2_Team1;

                    index ++;
                }

            }

        }

        for (int k=0; k<data.length; k++){
            XYDataset dataset = createDataset(k, data, teamsList);
            JFreeChart chart = ChartFactory.createScatterPlot(
                    graphicTitles.get(k),
                    graphicTitles.get(k).split(":")[1].split("\\+")[0].trim(),
                    graphicTitles.get(k).split(":")[1].split("\\+")[1].trim(),
                    dataset);

            XYPlot plot = (XYPlot)chart.getPlot();
            plot.setBackgroundPaint(new Color(230, 230, 230));
            XYItemRenderer renderer;
            renderer = plot.getRenderer();

            for (int i=0; i<numberOfTeams; i++){
                renderer.setSeriesShape(i, new Ellipse2D.Double(-5, -5, 10, 10));
            }

            XYItemLabelGenerator generator =
                    new StandardXYItemLabelGenerator("{0}");
            renderer.setBaseItemLabelGenerator(generator);
            renderer.setBaseItemLabelsVisible(true);

            chart.setBackgroundPaint(Color.white);
            chart.getLegend().setVisible(false);
            ChartPanel panel = new ChartPanel(chart);
            panel.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 1));
            result.add(panel);
        }



        return result;
    }

    private XYDataset createDataset(int index, double[][][] data, ArrayList<String> teamsList) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        for (int i=0; i<teamsList.size(); i++){
            XYSeries series = new XYSeries(teamsList.get(i));
            series.add(data[index][i][0], data[index][i][1]);
            dataset.addSeries(series);
        }

        return dataset;
    }

}

