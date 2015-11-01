package de.sciler.textureworker.desktop;


import javax.swing.*;

public class DesktopLauncher {
    public static void main(String[] args) {
        JFrame frame = new JFrame("TextureWorker 0.1 ALPHA");
        frame.setContentPane(new desktopPacker().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setVisible(true);
    }
}
