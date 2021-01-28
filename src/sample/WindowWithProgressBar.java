package sample;

import javafx.scene.control.ProgressBar;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class WindowWithProgressBar extends JFrame{
    JProgressBar jpb;
    int numberOfFiles;
    int downloadedFiles;

    public WindowWithProgressBar(String text) {
        super("Внимание!");
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setSize(500, 200);
        this.setLocation(200, 200);
        numberOfFiles = 0;
        downloadedFiles = 0;

        JLabel label = new JLabel(text);
        label.setFont(new Font("", Font.BOLD, 20));
        this.add(label, BorderLayout.NORTH);

        jpb = new JProgressBar(0, 100);
        jpb.setPreferredSize(new Dimension(600, 50));
        jpb.setStringPainted(true);
        this.add(jpb, BorderLayout.SOUTH);

        String resultOfRefreshing = "Fail";
        String lastUpdateName = Settings.getLastUpdateName();
        String fileName = "_listOfUpdates.txt";
        int n = Settings.getNumberOfAccount();
        try{
            String resultOfDownload = FTPLoader.downloadFile(Settings.getLogin(n), Settings.getPassword(n), "/data/hockey/database/updates/" + fileName, Settings.TMP_DIR + "/" + fileName);
            if (resultOfDownload.equals("Success")){
                String listFile = Settings.TMP_DIR + "/_listOfUpdates.txt";
                try {
                    File fileDir = new File(listFile);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(fileDir), "UTF-8"));
                    String str;
                    while (((str = in.readLine()) != null)) {
                        if (Integer.parseInt(str.split("_")[0]) >= Integer.parseInt(lastUpdateName.split("_")[0])){
                            resultOfDownload = FTPLoader.downloadFile(Settings.getLogin(n), Settings.getPassword(n), "/data/hockey/database/updates/" + str, Settings.TMP_DIR + "/" + str);
                            numberOfFiles += Settings.countLines(Settings.TMP_DIR + "/" + str);
                        }
                    }
                    in.close();
                    resultOfRefreshing = "Success";
                } catch (IOException e)
                {
                    System.out.println(e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            PopupWindow window = new PopupWindow("<html>   Ошибка при синхронизации базы. Код ошибки 8<br>" +
                    "Обратитесь к администратору." + ".</html>");
            window.setVisible(true);
            window.setAlwaysOnTop(true);
            resultOfRefreshing = "Crash: " + e.toString();
        }

        this.pack();
    }

    public void setProgressBarValue(int value){
        jpb.setValue(value);
    }
}
