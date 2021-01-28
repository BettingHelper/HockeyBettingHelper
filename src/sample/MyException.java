package sample;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class MyException {

    public static void sendException(StackTraceElement[] stackTrace, String info){
        String sn = "";
        try {
            sn = Settings.getSerialNumber();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String userName = Settings.getUserBySerialNumber(sn);
        String userID = Settings.getIDBySerialNumber(sn);

        LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Europe/Moscow"));

        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        DateTimeFormatter formatter2  = DateTimeFormatter.ofPattern("dd.MM.yy_HH:mm_");

        String text = ldt.format(formatter) + "\n"
                + "UserName: " + userName + "\n"
                + "UserID: " + userID + "\n"
                + "Info: " + info + "\n"
                + "Problem: " + "\n"
                + Arrays.toString(stackTrace).replaceAll(",", ",\n");

        FileWriter fr = null;
        BufferedWriter br = null;
        File file = new File(Settings.TMP_DIR + "/" + ldt.format(formatter2) + "_" + userName.replaceAll(" ", "") + ".txt");

        try {
            //для обновления файла нужно инициализировать FileWriter с помощью этого конструктора
            fr = new FileWriter(file,true);
            br = new BufferedWriter(fr);
            //теперь мы можем использовать метод write или метод append
            br.write(text);


        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                br.close();
                fr.close();
                FTPLoader.uploadFile(Settings.getLogin(0), Settings.getPassword(0),
                        "/data/hockey/support/" + file.getName(), file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        JFrame frame = new JFrame("Внимание!");
        frame.setLayout(new BorderLayout());

        JLabel labelWithTextToUser = new JLabel("");
        labelWithTextToUser.setFont(new Font("", Font.BOLD, 20));
        labelWithTextToUser.setBackground(new Color(238, 238, 238));

        String textForUser = "<html>В работе программы произошла ошибка!<br>" +
                "Она уже отправлена администраторам.<br>" +
                "Они постараются устранить ошибку как можно скорее.<br>" +
                "Спасибо!<br></html>";

        labelWithTextToUser.setText(textForUser);
        frame.add(labelWithTextToUser);
        frame.setSize(1000, 600);
        frame.setLocation(100, 100);
        frame.setAlwaysOnTop(true);
        frame.pack();
        frame.setVisible(true);



    }
}
