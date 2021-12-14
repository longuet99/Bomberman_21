package uet.oop.bomberman.entities;

import uet.oop.bomberman.entities.tile.destroyable.DestroyableTile;
import uet.oop.bomberman.graphics.Screen;

import java.util.LinkedList;

/**
 * Chứa và quản lý nhiều Entity tại cùng một vị trí
 * Ví dụ: tại vị trí giấu Item, có 3 Entity [Grass, Item, Brick]
 */
public class LayeredEntity extends Entity {
	
	protected LinkedList<Entity> entities = new LinkedList<>();
	
	public LayeredEntity(int x, int y, Entity ... entities) {
		this.x = x;
		this.y = y;
		
		for (int i = 0; i < entities.length; i++) {
			this.entities.add(entities[i]);
			
			if(i > 1) {
				if(entities[i] instanceof DestroyableTile)
					((DestroyableTile)entities[i]).addHiddenSprite(entities[i-1].getSprite());
			}
		}
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
		
		if(top.isRemoved())  {
			entities.removeLast();
		}
	}

	@Override
	public boolean collide(Entity e) {
		return this.getTopEntity().collide(e);
	}

}
