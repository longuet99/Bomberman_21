package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.enemy.ai.HardAI;
import uet.oop.bomberman.graphics.Sprite;

public class Doria extends Enemy {
    public Doria (int x, int y , Board board ){
        super(x, y, board,Sprite.kondoria_dead, Game.getBomberSpeed()*0.8, 4000);
        sprite = Sprite.kondoria_left1;

        ai = new HardAI(board.getBomber(), this);
        direction = ai.calculateDirection();
    }

    @Override
    protected void chooseSprite() {
        switch(direction) {
            case 0:
            case 1:
                sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, animate, 60);
                break;
            case 2:
            case 3:
                sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, animate, 60);
                break;
        }
    }
}
