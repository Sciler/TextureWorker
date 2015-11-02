package de.sciler.textureworker.desktop;


import javax.swing.*;

public class DesktopLauncher {
    public static void main(String[] args) {
        JFrame frame = new JFrame("TextureWorker 0.1 ALPHA");
        desktopPacker dp;
        dp = new desktopPacker();
        frame.setContentPane(dp.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setVisible(true);
    }
}
