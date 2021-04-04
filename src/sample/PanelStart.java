package sample;

import org.jfree.ui.tabbedui.VerticalLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class PanelStart extends JPanel {
    Settings settings;
    JPanel container;
    final String path = "database/";
    String currentVersion = "5.0.1";
    String newestVersion;

    public PanelStart() {
        this.setLayout(new BorderLayout());

        String userName = "";
        String date = "";
        String refCode = "";
        try {
            String sn = Settings.getSerialNumber();
            userName = Settings.getUserBySerialNumber(sn);
            date = Settings.getDateBySerialNumber(sn);
            refCode = Settings.getRefCodeBySerialNumber(sn);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        JPanel topPanel = new JPanel(new VerticalLayout());

        String textForUser = "Пользователь: " + userName + ".   " + "Ваша подписка действует до " + date + ".   Ваш реф. код: " + refCode;
        JTextField userTextField = new JTextField(textForUser);
        userTextField.setFont(new Font("", Font.BOLD, 20));
        userTextField.setBackground(new Color(238, 238, 238));
        topPanel.add(userTextField);

        Font fontHeader = new Font("Arial", Font.BOLD, 40);
        JLabel label1 = new JLabel("Добро пожаловать в Hockey Betting Helper!");
        label1.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setFont(fontHeader);
        topPanel.add(label1);

        this.add(topPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 0, 0));

        JPanel panelAboutProgram = new JPanel(new VerticalLayout());
        panelAboutProgram.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        Font fontText = new Font("Arial", Font.BOLD, 25);
        Font fontTezis = new Font("Arial", Font.BOLD, 20);
        String text = "<html><br><blockquote>Программа 'Hockey Betting Helper' - это:</blockquote>";

        JLabel label2 = new JLabel(text);
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setFont(fontText);
        panelAboutProgram.add(label2);

        String tezis = "<html><blockquote>- актуальная статистика топовых хоккейных лиг;</blockquote>" +
                "<blockquote>- простота и удобство интерфейса;</blockquote>" +
                "<blockquote>- разнообразные таблицы и графики;</blockquote>" +
                "<blockquote>- подробная статистика бросков в створ ворот и других показателей;</blockquote>" +
                "<blockquote>- автоматическое обновление данных;</blockquote>" +
                "<blockquote>- экономия времени при анализе матчей и т.д.</html>";
        JLabel label3 = new JLabel(tezis);
        label3.setHorizontalAlignment(JLabel.CENTER);
        label3.setFont(fontTezis);
        panelAboutProgram.add(label3);

        mainPanel.add(panelAboutProgram);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JPanel panelAd = new JPanel(new BorderLayout());
        JLabel labelHeaderAd = new JLabel("<html><br>Объявления:</html>");
        labelHeaderAd.setHorizontalAlignment(SwingConstants.CENTER);
        labelHeaderAd.setFont(fontText);
        panelAd.add(labelHeaderAd, BorderLayout.NORTH);

        String textNotification = "";
        try {
            int n = Settings.getNumberOfAccount();
            String resultOfDownload = FTPLoader.downloadFile(Settings.getLogin(n), Settings.getPassword(n),
                    "/data/hockey/notifications/notifications.txt", Settings.TMP_DIR + "/notifications.txt");
            File fileDir = new File(Settings.TMP_DIR + "/notifications.txt");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF-8"));
            String str;
            while (((str = in.readLine()) != null)) {
                textNotification += str + "\n";
            }
            in.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        JTextArea jtf = new JTextArea(textNotification);
        jtf.setBackground(new Color(238, 238, 238));
        jtf.setLineWrap(true);
        jtf.setWrapStyleWord(true);
        jtf.setFont(fontTezis);
        jtf.setEditable(false);
        jtf.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane sp = new JScrollPane(jtf, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelAd.add(sp);

        rightPanel.add(panelAd);

        JPanel panelResources = new JPanel(new VerticalLayout());
        JLabel resLabel = new JLabel("Наши ссылки:");
        resLabel.setFont(fontText);
        resLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelResources.add(resLabel);

        JPanel vkPanel = new JPanel(new BorderLayout());
        File fileVK = new File("images/vk.png");
        BufferedImage bimg = null;
        try {
            bimg = ImageIO.read(fileVK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image scaled = bimg.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        JLabel vkImage = new JLabel(new ImageIcon(scaled));
        vkPanel.add(vkImage, BorderLayout.WEST);
        String vk = "<html> (<a href=\"vk.com/betting_helper\">vk.com/betting_helper</a>) </html>";

        JLabel vkLabel = new JLabel(vk);
        vkLabel.setFont(fontTezis);
        vkLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Desktop desktop = java.awt.Desktop.getDesktop();
                URI uri = null;
                try {
                    uri = new URI("https://vk.com/betting_helper");
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
                try {
                    desktop.browse(uri);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        vkPanel.add(vkLabel);
        panelResources.add(vkPanel);

        JPanel sitePanel = new JPanel(new BorderLayout());
        File fileSite = new File("images/www.png");
        bimg = null;
        try {
            bimg = ImageIO.read(fileSite);
        } catch (IOException e) {
            e.printStackTrace();
        }
        scaled = bimg.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        JLabel siteImage = new JLabel(new ImageIcon(scaled));
        sitePanel.add(siteImage, BorderLayout.WEST);
        String site = "<html> (<a href=\"bettinghelper.ru\">bettinghelper.ru</a>) </html>";

        JLabel siteLabel = new JLabel(site);
        siteLabel.setFont(fontTezis);
        siteLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Desktop desktop = java.awt.Desktop.getDesktop();
                URI uri = null;
                try {
                    uri = new URI("https://bettinghelper.ru");
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
                try {
                    desktop.browse(uri);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        sitePanel.add(siteLabel);
        panelResources.add(sitePanel);

        JPanel telegramPanel = new JPanel(new BorderLayout());
        File fileTelegram = new File("images/telegram.png");
        bimg = null;
        try {
            bimg = ImageIO.read(fileTelegram);
        } catch (IOException e) {
            e.printStackTrace();
        }
        scaled = bimg.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        JLabel telegramImage = new JLabel(new ImageIcon(scaled));
        telegramPanel.add(telegramImage, BorderLayout.WEST);
        String telegram = "<html> (<a href=\"t.me/HockeyBettingHelper\">https://t.me/HockeyBettingHelper</a>) </html>";

        JLabel telegramLabel = new JLabel(telegram);
        telegramLabel.setFont(fontTezis);
        telegramLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Desktop desktop = java.awt.Desktop.getDesktop();
                URI uri = null;
                try {
                    uri = new URI("https://t.me/HockeyBettingHelper");
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
                try {
                    desktop.browse(uri);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        telegramPanel.add(telegramLabel);
        panelResources.add(telegramPanel);

        JPanel legalPanel = new JPanel(new BorderLayout());
        File fileLegal = new File("images/legalbet.png");
        bimg = null;
        try {
            bimg = ImageIO.read(fileLegal);
        } catch (IOException e) {
            e.printStackTrace();
        }
        scaled = bimg.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        JLabel legalImage = new JLabel(new ImageIcon(scaled));
        legalPanel.add(legalImage, BorderLayout.WEST);
        String legal = "<html> (<a href=\"legalbet.ru/blogs/hockey-betting-helper\">legalbet.ru/blogs/hockey-betting-helper</a>) </html>";

        JLabel legalLabel = new JLabel(legal);
        legalLabel.setFont(fontTezis);
        legalLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Desktop desktop = java.awt.Desktop.getDesktop();
                URI uri = null;
                try {
                    uri = new URI("https://legalbet.ru/blogs/hockey-betting-helper");
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
                try {
                    desktop.browse(uri);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        legalPanel.add(legalLabel);
        panelResources.add(legalPanel);


        rightPanel.add(panelResources, BorderLayout.SOUTH);

        mainPanel.add(rightPanel);
        this.add(mainPanel);

        String newestVersionFile = Settings.TMP_DIR + "/newestVersionHBH.txt";
        int n = Settings.getNumberOfAccount();
        try {
            String resultOfDownload = FTPLoader.downloadFile(Settings.getLogin(n), Settings.getPassword(n),
                    "/versions/newestVersionHBH.txt", newestVersionFile);
            try {
                File fileDir = new File(newestVersionFile);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(fileDir), "UTF-8"));
                String str;
                while (((str = in.readLine()) != null)) {
                    newestVersion = str;
                }
                in.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println(resultOfDownload);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String version = "Текущая версия - HBH_" + currentVersion;
        if (!currentVersion.equals(newestVersion)){
            version += ". ДОСТУПНА ВЕРСИЯ - HBH_" + newestVersion + ". Для ее получения перезапустите программу.";
            /*try {
                FTPLoader.downloadFile(Settings.getLogin(n), Settings.getPassword(n), "/versions/HockeyBettingHelper.exe", "HockeyBettingHelper.exe");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/
        }


        JLabel label5 = new JLabel(version);
//        label5.setPreferredSize(new Dimension(width, 70));
        label5.setHorizontalAlignment(JLabel.CENTER);
        label5.setFont(fontTezis);
        this.add(label5, BorderLayout.SOUTH);

        this.add(mainPanel, BorderLayout.CENTER);
    }
}
