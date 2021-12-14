package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

public class HardAI extends AI {
    protected Bomber bomber;
    protected Enemy enemy;

    public HardAI(Bomber bomber, Enemy enemy) {
        this.bomber = bomber;
        this.enemy = enemy;
    }

    @Override
    public int calculateDirection() {
        int bomberX = bomber.getXTile();
        int bomberY = bomber.getYTile();
        int enemyX = enemy.getXTile();
        int enemyY = enemy.getYTile();
        int diffX = bomberX - enemyX;
        int diffY = bomberY - enemyY;
        if (random.nextInt(2) == 0) {
            if (diffX> 0) {
                return 1;
            } else if (diffX < 0) {
                return 3;
            } else {
                if (diffY > 0) {
                    return 2;
                } else {
                    return 0;
                }
            }
        } else {
            if (diffY > 0) {
                return 2;
            } else if (diffY < 0) {
                return 0;
            } else {
                if (diffX > 0) {
                    return 3;
                } else {
                    return 1;
                }
            }
        }
    }

}
