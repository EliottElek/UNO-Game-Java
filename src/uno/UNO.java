package uno;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.ImageIcon;
//package com.zetcode;

import javax.swing.JFrame;

public class UNO implements constInterface {

    public static void main(String[] args) throws IOException {
        JFrame obj = new JFrame();
        ImageIcon icon;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        obj.setSize(2000,1000);
        obj.setTitle("UNO Game");
        obj.setResizable(true);
        //obj.setExtendedState(JFrame.MAXIMIZED_BOTH);
        obj.setUndecorated(true);
        obj.setVisible(true);
        icon = new ImageIcon("icon.png");
        obj.setIconImage(icon.getImage());
        //obj.setExtendedState(JFrame.MAXIMIZED_BOTH);
        obj.setVisible(true);
        Game Gameplay = new Game();
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(Gameplay);
        obj.setResizable(true);
        obj.setVisible(true);
        Gameplay.setinitialCardsToPlayers();
        Gameplay.showPlayers();
    }

}
