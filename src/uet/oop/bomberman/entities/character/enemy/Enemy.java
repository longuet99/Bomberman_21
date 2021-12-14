package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Message;
import uet.oop.bomberman.entities.bomb.Flame;
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

        MAX_STEPS = Game.TILES_SIZE / this.speed;
        rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
        steps = MAX_STEPS;

        afterTimer = 20;
        deadSprite = dead;
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

        if (alive)
            chooseSprite();
        else {
            if (afterTimer > 0) {
                sprite = deadSprite;
                animate = 0;
            } else {
                sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 60);
            }

        }

        screen.renderEntity((int) x, (int) y - sprite.SIZE, this);
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

        switch (this.direction) {
            case 0: {
                y -= speed;
                break;
            }
            case 1: {
                x += speed;
                break;
            }
            case 2: {
                y += speed;
                break;
            }
            case 3: {
                x -= speed;
                break;
            }
            default: {
                break;
            }
        }


        this.move(x, y);
    }

    @Override
    public void move(double xa, double ya) {
        if (alive && this.canMove(xa, ya)) {
            y = ya;
            x = xa;
        }
    }

    @Override
    public boolean canMove(double x, double y) {
        // TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không
        double dependedDirectionX1 = x;
        double dependedDirectionY1 = y;

        double dependedDirectionX2 = x;
        double dependedDirectionY2 = y;

        switch (this.direction) {

            case 0: {
                dependedDirectionX1 += 1;
                dependedDirectionY1 -= Game.TILES_SIZE;
                dependedDirectionX2 += Game.TILES_SIZE - 1;
                dependedDirectionY2 -= Game.TILES_SIZE;
                break;
            }

            case 1: {
                dependedDirectionX1 += Game.TILES_SIZE;
                dependedDirectionY1 -= Game.TILES_SIZE - 1;
                dependedDirectionX2 += Game.TILES_SIZE;
                dependedDirectionY2 -= 1;
                break;
            }

            case 2: {
                dependedDirectionX1 += 1;
                dependedDirectionY1 -= 1;
                dependedDirectionX2 += Game.TILES_SIZE - 1;
                dependedDirectionY2 -= 1;
                break;
            }

            case 3: {
                dependedDirectionY1 -= 1;
                dependedDirectionY2 -= Game.TILES_SIZE - 1;
                break;
            }

            default: {
                break;
            }
        }


        Entity entity1 = board.getEntity(Coordinates.pixelToTile(dependedDirectionX1), Coordinates.pixelToTile(dependedDirectionY1), this);
        Entity entity2 = board.getEntity(Coordinates.pixelToTile(dependedDirectionX2), Coordinates.pixelToTile(dependedDirectionY2), this);

        return !(entity1.collide(this) || entity2.collide(this));
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý va chạm với Flame
        // TODO: xử lý va chạm với Bomber

        if (e instanceof Flame) {
            this.kill();
            return true;
        }

        if (e instanceof Bomber) {
            ((Bomber) e).kill();
            return true;
        }

        return true;
        // Long but easy to understand
    }

    @Override
    public void kill() {
        if (!alive) return;
        alive = false;

        board.addPoints(points);

        Message msg = new Message("+" + points, getXMessage(), getYMessage(), 2, Color.white, 14);
        board.addMessage(msg);
        Game.addEnemyCount(-1);
    }


    @Override
    protected void afterKill() {
        if (afterTimer > 0) --afterTimer;
        else {
            if (finalAnimation > 0) --finalAnimation;
            else
                remove();
        }
    }

    protected abstract void chooseSprite();
}
