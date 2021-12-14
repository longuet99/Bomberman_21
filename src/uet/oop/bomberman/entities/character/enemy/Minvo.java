package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.enemy.ai.HardAI;
import uet.oop.bomberman.graphics.Sprite;

public class Minvo extends Enemy {

    public Minvo (int x, int y, Board board){
        super(x, y, board, Sprite.minvo_dead, Game.getBomberSpeed() * 0.81, 3000);
        sprite = Sprite.minvo_left1;

        ai = new HardAI(board.getBomber(), this);
        direction = ai.calculateDirection();
    }
    @Override
    protected void chooseSprite() {
        switch(direction) {
            case 0:
            case 1:
                sprite = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, animate, 60);
                break;
            case 2:
            case 3:
                sprite = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, animate, 60);
                break;
        }
    }
}
