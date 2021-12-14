package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.enemy.ai.EasyAI;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Enemy {
	
	
	public Balloon(int x, int y, Board board) {
		super(x, y, board, Sprite.balloom_dead, Game.getBomberSpeed() / 2, 1000);
		
		sprite = Sprite.balloom_left1;
		
		ai = new EasyAI();
		direction = ai.calculateDirection();
	}

	@Override
	protected void chooseSprite() {
		switch(direction) {
			case 0:
			case 1:
					sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animate, 60);
				break;
			case 2:
			case 3:
					sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animate, 60);
				break;
		}
	}
}