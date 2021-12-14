package uet.oop.bomberman.gui;

import uet.oop.bomberman.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Swing Panel chứa cảnh game
 */
public class GamePanel extends JPanel {

	private Game game;
	
	public GamePanel(Frame frame) {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE));

		game = new Game(frame);

		add(game);

		game.setVisible(true);

		setVisible(true);
		setFocusable(true);
		
	}

	public Game getGame() {
		return game;
	}
	
}
