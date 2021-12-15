package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.Coordinates;
import uet.oop.bomberman.sound.effect.SoundEffect;

import java.util.Iterator;
import java.util.List;

public class Bomber extends Character {

    private List<Bomb> bombs;
    protected Keyboard input;

    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb tiếp theo,
     * cứ mỗi lần đặt 1 Bomb mới, giá trị này sẽ được reset về 0 và giảm dần trong mỗi lần update()
     */
    protected int timeBetweenTwoBombs = 0;

    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        bombs = this.board.getBombs();
        input = this.board.getInput();
        sprite = Sprite.player_right;
    }

    @Override
    public void update() {
        clearBombs();
        if (!alive) {
            afterKill();
            return;
        }

        if (timeBetweenTwoBombs < -7500) timeBetweenTwoBombs = 0;
        else timeBetweenTwoBombs--;

        animate();

        calculateMove();

        detectBombPlacement();
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();

        if (alive) {
            chooseSprite();
        } else {
            sprite = Sprite.player_dead1;
        }

        screen.renderEntity((int) x, (int) y - sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(board, this);
        Screen.setOffset(xScroll, 0);
    }

    /**
     * Kiểm tra xem có đặt được bom hay không? nếu có thì đặt bom tại vị trí hiện tại của Bomber
     */
    private void detectBombPlacement() {
        // TODO: kiểm tra xem phím điều khiển đặt bom có được gõ và giá trị timeBetweenTwoBombs, Game.getBombRate() có thỏa mãn hay không
        // TODO:  Game.getBombRate() sẽ trả về số lượng bom có thể đặt liên tiếp tại thời điểm hiện tại
        // TODO: timeBetweenTwoBombs dùng để ngăn chặn Bomber đặt 2 Bomb cùng tại 1 vị trí trong 1 khoảng thời gian quá ngắn
        // TODO: nếu 3 điều kiện trên thỏa mãn thì thực hiện đặt bom bằng placeBomb()
        // TODO: sau khi đặt, nhớ giảm số lượng Bomb Rate và reset timeBetweenTwoBombs về 0
        int bombRate = Game.getBombRate();
        if (!input.space || bombRate <= 0 || this.timeBetweenTwoBombs >= 0) {
            return;
        }
        Game.addBombRate(-1);
        this.timeBetweenTwoBombs = Game.TIME_BETWEEN_PLACE_BOMB;
        this.placeBomb(Coordinates.pixelToTile(this.x + Game.TILES_SIZE / 2), Coordinates.pixelToTile(this.y - Game.TILES_SIZE / 2));

    }

    protected void placeBomb(int x, int y) {
        // TODO: thực hiện tạo đối tượng bom, đặt vào vị trí (x, y)
        Bomb bomb = new Bomb(x, y, board);
        this.board.addBomb(bomb);


    }

    private void clearBombs() {
        Iterator<Bomb> bs = bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }

    }

    @Override
    public void kill() {
        if (!alive) {
            return;
        }
        SoundEffect.GHOST.stop();
        alive = false;
    }

    @Override
    protected void afterKill() {
        if (afterTimer > 0) --afterTimer;
        else {
            board.endGame();
        }
    }

    @Override
    protected void calculateMove() {

        double x = this.x;
        double y = this.y;

        if (!input.up) {
        } else {
            y -= Game.getBomberSpeed();
            this.direction = 0;

        }

        if (!input.right) {
        } else {
            x += Game.getBomberSpeed();
            this.direction = 1;
        }

        if (!input.down) {
        } else {
            y += Game.getBomberSpeed();
            this.direction = 2;
        }

        if (!input.left) {
        } else {
            x -= Game.getBomberSpeed();
            this.direction = 3;
        }

        this.moving = input.down ||input.left || input.up || input.right ;

        this.move(x, y);

    }

    @Override
    public boolean canMove(double x, double y) {
        // TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không

        // I created 2 point to determine which block ahead bomber man, in case of bomber man in the middle of 2 block
        // The block ahead bomber man depend on the direction


        double coordinatesX1 = x;
        double coordinatesY1 = y;

        double coordinatesX2 = x;
        double coordinatesY2 = y;

        switch (this.direction) {

            case 0: {
                coordinatesX1 += 1;
                coordinatesY1 -= Game.getCharacterWidth();
                coordinatesX2 += Game.getCharacterWidth() - 1;
                coordinatesY2 -= Game.getCharacterHeight();
                break;
            }

            case 1: {
                coordinatesX1 += Game.getCharacterWidth();
                coordinatesY1 -= Game.getCharacterHeight() - 1;
                coordinatesX2 += Game.getCharacterWidth();
                coordinatesY2 -= 1;
                break;
            }

            case 2: {
                coordinatesX1 += 1;
                coordinatesY1 -= 1;
                coordinatesX2 += Game.getCharacterWidth() - 1;
                coordinatesY2 -= 1;
                break;
            }

            case 3: {
                coordinatesY1 -= 1;
                coordinatesY2 -= Game.getCharacterHeight() - 1;
                break;
            }

            default: {
                break;
            }
        }


        Entity entity1 = board.getEntity(Coordinates.pixelToTile(coordinatesX1), Coordinates.pixelToTile(coordinatesY1), this);
        Entity entity2 = board.getEntity(Coordinates.pixelToTile(coordinatesX2), Coordinates.pixelToTile(coordinatesY2), this);
        Entity currentEntity = board.getBombAt(Coordinates.pixelToTile(this.x), Coordinates.pixelToTile(this.y - 1));

        return currentEntity == null ? !(entity1.collide(this) || entity2.collide(this)) : !(currentEntity.collide(this) || entity1.collide(this) || entity2.collide(this));
    }

    @Override
    public void move(double x, double y) {

        if (!this.canMove(x, y) || !alive) {
            for (int i = 0; i < Game.getBomberSpeed(); i++) {
                switch (this.direction) {
                    case 0: {
                        y++;
                        break;
                    }
                    case 1: {
                        x--;
                        break;
                    }

                    case 2: {
                        y--;
                        break;
                    }
                    case 3: {
                        x++;
                        break;
                    }
                }
                if (!canMove(x, y)) {
                    continue;
                }
                this.x = x;
                this.y = y;
                break;
            }

        } else {
            this.x = x;
            this.y = y;
        }

    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý va chạm với Flame
        // TODO: xử lý va chạm với Enemy

        if (e instanceof Enemy) {
            this.kill();
            SoundEffect.DEAD.playDead();
            return true;
        }

        if (e instanceof Flame) {
            this.kill();
            SoundEffect.DEAD.playDead();
            return false;
        }

        return false;
    }

    private void chooseSprite() {
        switch (direction) {
            case 0:
                sprite = Sprite.player_up;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate, 20);
                }
                break;
            case 2:
                sprite = Sprite.player_down;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animate, 20);
                }
                break;
            case 3:
                sprite = Sprite.player_left;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, 20);
                }
                break;
            case 1:
            default:
                sprite = Sprite.player_right;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 20);
                }
                break;
        }
    }
}
