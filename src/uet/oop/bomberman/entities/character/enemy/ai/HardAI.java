package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

public class HardAI extends AI {
    protected Bomber bomb;
    protected Enemy ene;

    public HardAI(Bomber bomber, Enemy enemy) {
        this.bomb = bomber;
        this.ene = enemy;
    }

    @Override
    public int calculateDirection() {
        int bomX = bomb.getXTile();
        int bomY = bomb.getYTile();
        int eX = ene.getXTile();
        int eY = ene.getYTile();
        int diffX = bomX - eX;
        int diffY = bomY - eY;
        if (random.nextInt(2) != 0) {
            if (diffY < 0) {
                return 0;
            } else {
                if (diffY > 0) {
                    return 2;
                } else {
                    if (diffX < 0) {
                        return 1;
                    } else {
                        return 3;
                    }
                }
            }
        } else {
            if (diffX < 0) {
                return 3;
            } else {
                if (diffX > 0) {
                    return 1;
                } else {
                    if (diffY < 0) {
                        return 0;
                    } else {
                        return 2;
                    }
                }
            }
        }
    }

}
