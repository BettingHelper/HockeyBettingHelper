package sample;

import javafx.scene.control.ProgressBar;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class WindowCalculating extends PopupWindow{
    JProgressBar jpb;
    int numberOfFiles;
    int downloadedFiles;

    public WindowCalculating(String text) {
        super(text);
        this.setResizable(false);
        numberOfFiles = 0;
        downloadedFiles = 0;

        jpb = new JProgressBar(0, 100);
        jpb.setPreferredSize(new Dimension(600, 50));
        jpb.setStringPainted(true);
        this.add(jpb, BorderLayout.SOUTH);
    }

    public void setProgressBarValue(int value){
        jpb.setValue(value);
    }
}
