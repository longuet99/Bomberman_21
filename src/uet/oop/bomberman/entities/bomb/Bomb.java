package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.level.Coordinates;
import uet.oop.bomberman.sound.effect.SoundEffect;

import java.util.List;

public class Bomb extends AnimatedEntity {

    protected double explodeTimer = 120; //2 seconds
    public int _timeAfter = 20;

    protected Board board;
    protected Flame[] flames = new Flame[4];
    protected boolean exploded = false;
    protected boolean allowedToPass = true;

    public Bomb(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
        sprite = Sprite.bomb;
    }

    @Override
    public void update() {
        if (explodeTimer > 0)
            explodeTimer--;
        else {
            if (!exploded)
                explode();
            else
                updateFlames();

            if (_timeAfter > 0)
                _timeAfter--;
            else
                remove();
        }

        animate();
    }

    @Override
    public void render(Screen screen) {
        if (exploded) {
            sprite = Sprite.bomb_exploded2;
            renderFlames(screen);
        } else
            sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 60);

        int xt = (int) x << 4;
        int yt = (int) y << 4;

        screen.renderEntity(xt, yt, this);
    }

    public void renderFlames(Screen screen) {
        for (Flame flame : flames) {
            flame.render(screen);
        }
    }

    public void updateFlames() {
        for (Flame flame : flames) {
            flame.update();
        }
    }

    /**
     * Xử lý Bomb nổ
     */
    protected void explode() {
        SoundEffect.EXPLODE.playExpLode();
        exploded = true;

        // TODO: xử lý khi Character đứng tại vị trí Bomb

        List<Character> characterList = board.characters;

        for (Character c : characterList) {
            if (c.getXTile() == x && c.getYTile() == y) {
                c.kill();
            }
        }
        // TODO: tạo các Flame


        for (int i = 0; i < 4; i++) {
            Flame upFlame = new Flame((int) x, (int) y, i, Game.getBombRadius(), this.board);
            flames[i] = upFlame;
        }


    }

    public FlamePatch flameAt(int x, int y) {
        if (!exploded) return null;

        for (Flame flame : flames) {
            if (flame == null) return null;
            FlamePatch e = flame.flameSegmentAt(x, y);
            if (e != null) return e;
        }

        return null;
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý khi Bomber đi ra sau khi vừa đặt bom (allowedToPass)
        // TODO: xử lý va chạm với Flame của Bomb khác

        if (e instanceof Bomber) {
            double diffX = e.getX() - Coordinates.tileToPixel(x);
            double diffY = e.getY() - Coordinates.tileToPixel(y);
            if (!(diffX >= -Game.getCharacterWidth() && diffX < Game.TILES_SIZE && diffY >= 1 && diffY <= Game.TILES_SIZE)) {
                allowedToPass = false;
            }

            return !allowedToPass;
        }


        if (e instanceof Flame) {
            this.explodeTimer = 0;
            return true;
        }
        return true;
    }
}
