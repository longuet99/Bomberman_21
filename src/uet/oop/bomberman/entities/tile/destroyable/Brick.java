package uet.oop.bomberman.entities.tile.destroyable;


import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class Brick extends DestroyableTile {
	
	public Brick(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	@Override
	public void update() {
		super.update();
	}
	
	@Override
	public void render(Screen screen) {
		int x = Coordinates.tileToPixel(this.x);
		int y = Coordinates.tileToPixel(this.y);

		if (!destroyed) {
			screen.renderEntity( x, y, this);
		} else {
			sprite = movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2);

			screen.renderEwHiddenE(x, y, this, hiddenSprite);
		}
	}
	
}
