package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Tile {
	Board board;

	public Portal(int x, int y, Sprite sprite, Board board) {
		super(x, y, sprite);
		this.board = board;
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý khi Bomber đi vào
		// Lên level (phải diệt sạch quái trước)
		if (e instanceof Bomber && Game.getNumberOfEnemy() == 0 ){
			board.nextLevel();
		}
		return false;
	}

}
