package uet.oop.bomberman.entities.character.enemy;


import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.enemy.ai.HardAI;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {
	
	public Oneal(int x, int y, Board board) {
		super(x, y, board, Sprite.oneal_dead, Game.getBomberSpeed() * 0.65, 2000);
		
		sprite = Sprite.oneal_left1;
		
		ai = new HardAI(this.board.getBomber(), this);
		direction = ai.calculateDirection();
	}
	
	@Override
	protected void chooseSprite() {
		if (direction == 0 || direction == 1) {
			if (moving)
				sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animate, 60);
			else
				sprite = Sprite.oneal_left1;
		} else if (direction == 2 || direction == 3) {
			if (moving)
				sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate, 60);
			else
				sprite = Sprite.oneal_left1;
		}
	}

	@Override
	public boolean collide(Entity e) {
		if (e instanceof Flame) {
			this.kill();
		}
		return true;
	}
}
