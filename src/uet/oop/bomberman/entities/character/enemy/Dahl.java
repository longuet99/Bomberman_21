package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.enemy.ai.HardAI;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Dahl extends Enemy {

    public Dahl (int x, int y, Board board){
        super(x, y, board, Sprite.doll_dead, Game.getBomberSpeed() / 2, 2000);

        sprite = Sprite.doll_left1;

        ai = new HardAI(board.getBomber(), this);
        direction = ai.calculateDirection();
    }
    @Override
    protected void chooseSprite() {
        switch(direction) {
            case 0:
            case 1:
                sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, animate, 60);
                break;
            case 2:
            case 3:
                sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, animate, 60);
                break;
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
