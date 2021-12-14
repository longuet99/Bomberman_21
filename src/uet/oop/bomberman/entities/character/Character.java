package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.graphics.Screen;

/**
 * Bao gồm Bomber và Enemy
 */
public abstract class Character extends AnimatedEntity {
	
	protected Board board;
	protected int direction = -1;
	protected boolean alive = true;
	protected boolean moving = false;
	public int afterTimer = 40;
	
	public Character(int x, int y, Board board) {
		this.x = x;
		this.y = y;
		this.board = board;
	}
	
	@Override
	public abstract void update();
	
	@Override
	public abstract void render(Screen screen);

	/**
	 * Tính toán hướng đi
	 */
	protected abstract void calculateMove();
	
	protected abstract void move(double x, double y);

	/**
	 * Được gọi khi đối tượng bị tiêu diệt
	 */
	public abstract void kill();

	/**
	 * Xử lý hiệu ứng bị tiêu diệt
	 */
	protected abstract void afterKill();

	/**
	 * Kiểm tra xem đối tượng có di chuyển tới vị trí đã tính toán hay không
	 * @param x
	 * @param y
	 * @return
	 */
	protected abstract boolean canMove(double x, double y);

	protected double getXMessage() {
		return (x * Game.SCALE) + (sprite.SIZE / 2 * Game.SCALE);
	}
	
	protected double getYMessage() {
		return (y * Game.SCALE) - (sprite.SIZE / 2 * Game.SCALE);
	}
	
}
