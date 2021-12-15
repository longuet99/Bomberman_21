package uet.oop.bomberman.entities;

import uet.oop.bomberman.graphics.IRender;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

/**
 * Lớp đại diện cho tất cả thực thể trong game (Bomber, Enemy, Wall, Brick,...)
 */
public abstract class Entity implements IRender {

	protected double x, y;
	protected boolean removed = false;
	protected Sprite sprite;

	/**
	 * Phương thức này được gọi liên tục trong vòng lặp game,
	 * mục đích để xử lý sự kiện và cập nhật trạng thái Entity
	 */
	@Override
	public abstract void update();

	/**
	 * Phương thức này được gọi liên tục trong vòng lặp game,
	 * mục đích để cập nhật hình ảnh của entity theo trạng thái
	 */
	@Override
	public abstract void render(Screen screen);

	/**
	 * Phương thức huỷ diệt thực thể
	 */
	public void remove() {
		removed = true;
	}

	/**
	 * Kiểm tra thực thể có bị huỷ chưa
	 * @return removed <boolean variable> </boolean>
	 */
	public boolean isRemoved() {
		return removed;
	}

	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * Phương thức này được gọi để xử lý khi hai entity va chạm vào nhau
	 * @param e
	 * @return true / false tuỳ vào loại thực thể đang xét và thực thể e
	 */
	public abstract boolean collide(Entity e);
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public int getXTile() {
		return Coordinates.pixelToTile(x + sprite.kichCo / 2);
	}
	
	public int getYTile() {
		return Coordinates.pixelToTile(y - sprite.kichCo / 2);
	}
}
