package uet.oop.bomberman.entities;

import uet.oop.bomberman.entities.tile.destroyable.DestroyableTile;
import uet.oop.bomberman.graphics.Screen;

import java.util.LinkedList;
import java.util.stream.IntStream;

/**
 * Chứa và quản lý nhiều Entity tại cùng một vị trí
 * Ví dụ: tại vị trí giấu Item, có 3 Entity [Grass, Item, Brick]
 */
public class LayeredEntity extends Entity {
	
	protected LinkedList<Entity> entities = new LinkedList<>();
	
	public LayeredEntity(int x, int y, Entity ... entities) {
		this.x = x;
		this.y = y;

		IntStream.range(0, entities.length).forEach(i -> {
			this.entities.add(entities[i]);
			if (i > 1) if (!(entities[i] instanceof DestroyableTile)) {
				return;
			} else {
				((DestroyableTile) entities[i]).addHiddenSprite(entities[i - 1].getSprite());
			}
			else {
				return;
			}
		});
	}
	
	@Override
	public void update() {
		clearRemoved();
		getTopEntity().update();
	}
	
	@Override
	public void render(Screen screen) {
		getTopEntity().render(screen);
	}
	
	public Entity getTopEntity() {
		
		return entities.getLast();
	}
	
	private void clearRemoved() {
		Entity top  = getTopEntity();

		if (!top.isRemoved()) {
			return;
		}
		entities.removeLast();
	}

	@Override
	public boolean collide(Entity e) {
		boolean collide = this.getTopEntity().collide(e);
		return collide;
	}

}
