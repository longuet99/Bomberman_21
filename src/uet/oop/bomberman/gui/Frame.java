package uet.oop.bomberman.gui;

import uet.oop.bomberman.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Swing Frame chứa toàn bộ các component
 */
public class Frame extends JFrame {

    public GamePanel gamePanel;
    private JPanel containerPanel;
    private InfoPanel infoPanel;

    private Game game;

    public Frame() {

        containerPanel = new JPanel(new BorderLayout());
        gamePanel = new GamePanel(this);
        infoPanel = new InfoPanel(gamePanel.getGame());

        containerPanel.add(infoPanel, BorderLayout.PAGE_START);
        containerPanel.add(gamePanel, BorderLayout.PAGE_END);

        game = gamePanel.getGame();

        add(containerPanel);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        game.start();
    }

    public void setTime(int time) {
        infoPanel.setTime(time);
    }

    public void setPoints(int points) {
        infoPanel.setPoints(points);
    }

}
