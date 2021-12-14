package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.effect.SoundEffect;


public class FlamePatch extends Entity {

    protected boolean lastFire;
    protected int direction;

    /**
     * @param x
     * @param y
     * @param direction
     * @param last      cho biết segment này là cuối cùng của Flame hay không,
     *                  segment cuối có sprite khác so với các segment còn lại
     */
    public FlamePatch(int x, int y, int direction, boolean last) {
        this.x = x;
        this.y = y;
        lastFire = last;
        this.direction = direction;

        switch (direction) {
            case 0: {
                sprite = Sprite.explosion_vertical2;
                break;
            }

            case 1: {
                sprite = Sprite.explosion_horizontal2;
                break;
            }

            case 2: {
                sprite = Sprite.explosion_vertical2;
                break;
            }

            case 3: {
                sprite = Sprite.explosion_horizontal2;
                break;
            }
        }
    }

    public void setLast(boolean last) {
        this.lastFire = last;
        switch (direction) {
            case 0: {
                sprite = Sprite.explosion_vertical_top_last2;
                break;
            }

            case 1: {
                sprite = Sprite.explosion_horizontal_right_last2;
                break;
            }

            case 2: {
                sprite = Sprite.explosion_vertical_down_last2;
                break;
            }

            case 3: {
                sprite = Sprite.explosion_horizontal_left_last2;
                break;
            }

            default: {
                sprite = Sprite.explosion_vertical_top_last2;
                break;
            }
        }
    }

    @Override
    public void render(Screen screen) {
        int xt = (int) x << 4;
        int yt = (int) y << 4;

        screen.renderEntity(xt, yt, this);
    }

    @Override
    public void update() {
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý khi FlamePatch va chạm với Character
        if (e instanceof Character) {
            ((Character) e).kill();
            SoundEffect.KILL.playKill();
            return false;
        }
        return true;
    }
}
