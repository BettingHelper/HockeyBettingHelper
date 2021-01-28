package sample;

import org.jfree.ui.tabbedui.VerticalLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class WindowWithID extends JFrame {

    public WindowWithID(String text) {
        super("Внимание!");
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        setSize(800, 500);
        setLocation(200, 200);

        Font font = new Font("", 0, 18);
        JPanel panel = new JPanel(new VerticalLayout());

        final JLabel labelText = new JLabel(text);
        labelText.setFont(font);
        labelText.setHorizontalAlignment(JLabel.CENTER);
        labelText.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(labelText);

        JLabel label = new JLabel("Ваш ID в программе HockeyBettingHelper:");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(font);
        panel.add(label);

        JTextField idEdit = null;
        try {
            idEdit = new JTextField(Settings.getSerialNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
        idEdit.setPreferredSize(new Dimension(600, 30));
        idEdit.setFont(font);
        idEdit.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(idEdit);

        JPanel panelButton = new JPanel();
        panelButton.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        JButton buttonHelp = new JButton("Справка!");
        buttonHelp.setFont(font);
        panelButton.add(buttonHelp);
        panel.add(panelButton);

        this.add(panel, BorderLayout.SOUTH);
        this.pack();


        final JTextField finalIdEdit = idEdit;
        idEdit.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                finalIdEdit.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        buttonHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame window = new JFrame("Справка!");
                window.setResizable(false);
                window.setLayout(new VerticalLayout());
                window.setBounds(100, 100, 800, 700);
                window.setVisible(true);
                window.setAlwaysOnTop(true);
                Font font = new Font("", 0, 15);

                JLabel label = new JLabel("<html>Если вы уверены, что зарегистрированы, а программа выдает вам окно с ID, <br>" +
                        " то проблема может быть вызвана одной из следующих причин:<br>" +
                        "<br>" +
                        "1) У вас отстутствует интернет-соединение. <br>" +
                        "<blockquote>Проверьте соединение и повторите попытку;</blockquote>" +
                        "2) Программу блокирует брандмауэр или Защитник Windows. <br>" +
                        "<blockquote>Его в таком случае стоит отключить; </blockquote>" +
                        "3) Программу блокирует антивирус. <br>" +
                        "<blockquote>Следует добавить программу в исключения антивируса;</blockquote>" +
                        "4) Ваш ID изменился. Эта ошибка возникает крайне редко при некоторых изменениях параметров Windows;<br>" +
                        "<blockquote>Обратитесь к администратору.</blockquote>" +
                        "5) Вы или какие-то системные службы внесли изменения в файлы программы и это привело к сбою.<br>" +
                        "<blockquote>Обратитесь к администратору.</blockquote>" +
                        "</html>");


                label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                label.setFont(font);
                window.add(label);

                JTextArea jtf = new JTextArea(
                        "Контакты:\n" +
                                "ВК: vk.com/id493869084\n" +
                                "Telegram | WhatsApp: 8-916-477-81-33\n" +
                                "\n" +
                                "Реквизиты для оплаты:\n" +
                                "1) Сбербанк: 4817 7600 0971 7228 (Михаил Романович Г.)\n" +
                                "2) Яндекс.Деньги: 410017025122373\n" +
                                "3) Киви: 8-916-477-81-33"
                );
                jtf.setBackground(new Color(238, 238, 238));
                jtf.setFont(font);
                jtf.setEditable(false);
                jtf.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                window.add(jtf);

                window.pack();
            }
        });
    }
}
