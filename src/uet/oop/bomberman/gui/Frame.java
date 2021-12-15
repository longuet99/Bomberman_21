package uet.oop.bomberman.gui;

import uet.oop.bomberman.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Swing Frame chứa các component để render màn chơi
 */
public class Frame extends JFrame {

    public GamePanel panelGame;
    private JPanel panelContainer;
    private InfoPanel panelThongTin;

    private Game game;

    public Frame() {

        panelContainer = new JPanel(new BorderLayout());
        panelGame = new GamePanel(this);
        panelThongTin = new InfoPanel(panelGame.getGame());

        panelContainer.add(panelThongTin, BorderLayout.PAGE_START);
        panelContainer.add(panelGame, BorderLayout.PAGE_END);

        game = panelGame.getGame();

        add(panelContainer);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        game.start();
    }

    public void setTime(int time) {
        panelThongTin.setTime(time);
    }

    public void setPoints(int points) {
        panelThongTin.setPoints(points);
    }

}
