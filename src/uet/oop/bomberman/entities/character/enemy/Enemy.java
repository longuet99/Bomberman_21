package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Message;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemy.ai.AI;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

import java.awt.*;

public abstract class Enemy extends Character {

    protected int points;

    protected double speed;
    protected AI ai;

    protected final double MAX_STEPS;
    protected final double rest;
    protected double steps;

    protected int finalAnimation = 30;
    protected Sprite deadSprite;

    public Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
        super(x, y, board);

        this.points = points;
        this.speed = speed;

        afterTimer = 20;
        deadSprite = dead;

        MAX_STEPS = Game.TILES_SIZE / this.speed;

        steps = MAX_STEPS;

        rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
        Game.addEnemyCount(1);
    }

    @Override
    public void update() {
        animate();

        if (!alive) {
            afterKill();
            return;
        }

        if (alive)
            calculateMove();
    }

    @Override
    public void render(Screen screen) {

        if (!alive) {
            if (afterTimer <= 0) {
                sprite = Sprite.animatedSprites(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 60);
            } else {
                sprite = deadSprite;
                animate = 0;
            }
        }
        else {
            chooseSprite();
        }
        screen.renderEntity((int) x, (int) y - sprite.kichCo, this);
    }

    @Override
    public void calculateMove() {
        // TODO: Tính toán hướng đi và di chuyển Enemy theo ai và cập nhật giá trị cho direction
        // TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không
        // TODO: sử dụng move() để di chuyển
        // TODO: nhớ cập nhật lại giá trị cờ moving khi thay đổi trạng thái di chuyển

        double x = this.x;
        double y = this.y;
        if (steps <= 0){
            steps = MAX_STEPS;
            this.direction = ai.calculateDirection();
        }
        steps--;

        if (this.direction == 1) {
            x += speed;
        } else if (this.direction == 0) {
            y -= speed;
        } else if (this.direction == 3) {
            x -= speed;
        } else if (this.direction == 2) {
            y += speed;
        }


        this.move(x, y);
    }

    @Override
    public void move(double xA, double yA) {
        if (alive && this.canMove(xA, yA)) {
            y = yA;
            x = xA;
        }
    }

    @Override
    public boolean canMove(double xA, double yA) {
        // TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không
        double dependedDirectionX1 = xA;
        double dependedDirectionY1 = yA;

        double dependedDirectionX2 = xA;
        double dependedDirectionY2 = yA;

        if (this.direction == 0) {
            dependedDirectionX1 += 1;
            dependedDirectionY1 -= Game.TILES_SIZE;
            dependedDirectionX2 += Game.TILES_SIZE - 1;
            dependedDirectionY2 -= Game.TILES_SIZE;
        } else if (this.direction == 1) {
            dependedDirectionX1 += Game.TILES_SIZE;
            dependedDirectionY1 -= Game.TILES_SIZE - 1;
            dependedDirectionX2 += Game.TILES_SIZE;
            dependedDirectionY2 -= 1;
        } else if (this.direction == 2) {
            dependedDirectionX1 += 1;
            dependedDirectionY1 -= 1;
            dependedDirectionX2 += Game.TILES_SIZE - 1;
            dependedDirectionY2 -= 1;
        } else if (this.direction == 3) {
            dependedDirectionY1 -= 1;
            dependedDirectionY2 -= Game.TILES_SIZE - 1;
        }

        Entity entity1 = board.getEntity(Coordinates.pixelToTile(dependedDirectionX1), Coordinates.pixelToTile(dependedDirectionY1), this);
        Entity entity2 = board.getEntity(Coordinates.pixelToTile(dependedDirectionX2), Coordinates.pixelToTile(dependedDirectionY2), this);

        return !(entity1.collide(this) || entity2.collide(this));
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý va chạm với Bomber

        if (e instanceof Bomber) {
            ((Bomber) e).kill();
            return true;
        }

        return true;
        // Long but easy to understand
    }

    @Override
    public void kill() {
        if (alive) return;
        alive = false;

        board.addPoints(points);

        Message msg = new Message("+" + points, getXMessage(), getYMessage(), 2, Color.white, 14);
        board.addMessage(msg);
        Game.addEnemyCount(-1);
    }


    @Override
    protected void afterKill() {
        if (afterTimer <= 0) {
            if (finalAnimation <= 0)  remove();
            else
                --finalAnimation;
        }
        else {
            --afterTimer;
        }
    }

    protected abstract void chooseSprite();
}
