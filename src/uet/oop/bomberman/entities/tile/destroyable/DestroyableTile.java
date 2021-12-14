package uet.oop.bomberman.entities.tile.destroyable;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.enemy.Doria;
import uet.oop.bomberman.entities.tile.Tile;
import uet.oop.bomberman.graphics.Sprite;

/**
 * Đối tượng cố định có thể bị phá hủy
 */
public class DestroyableTile extends Tile {

	private final int MAX_ANIMATE = 7500;
	private int animate = 0;
	protected boolean destroyed = false;
	protected int timeToBanishment = 20;
	protected Sprite hiddenSprite = Sprite.grass;
	
	public DestroyableTile(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	@Override
	public void update() {
		if(destroyed) {
			if(animate < MAX_ANIMATE) animate++; else animate = 0;
			if(timeToBanishment > 0) 
				timeToBanishment--;
			else
				remove();
		}
	}

	public void destroy() {
		destroyed = true;
	}
	
	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý khi va chạm với Flame va Doria
		// Gặp lửa thì huỷ
		boolean check = true;
		if (e instanceof Flame){
			this.destroy();
			return true;
		}

		if (e instanceof Doria) {
			return false;
		}
		return true;
	}
	
	public void addHiddenSprite(Sprite sprite) {
		hiddenSprite = sprite;
	}
	
	protected Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2) {
		int calc = animate % 30;
		
		if(calc < 10) {
			return normal;
		}
			
		if(calc < 20) {
			return x1;
		}
			
		return x2;
	}
}
