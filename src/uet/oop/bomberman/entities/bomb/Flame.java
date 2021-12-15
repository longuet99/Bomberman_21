package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Screen;

public class Flame extends Entity {

    protected Board board;
    protected int direction;
    protected int radius;
    protected int xOrigin, yOrigin;
    protected FlamePatch[] flamePatches;

    /**
     * @param x         hoành độ bắt đầu của Flame
     * @param y         tung độ bắt đầu của Flame
     * @param direction là hướng của Flame
     * @param radius    độ dài cực đại của Flame
     */
    public Flame(int x, int y, int direction, int radius, Board board) {
        xOrigin = x;
        yOrigin = y;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.radius = radius;
        this.board = board;
        createFlameSegments();
    }

    /**
     * Tạo các FlamePatch, mỗi segment ứng một đơn vị độ dài
     */
    private void createFlameSegments() {
        /**
         * tính toán độ dài Flame, tương ứng với số lượng segment
         */
        int flameSegmentsLength = calculatePermittedDistance();
        flamePatches = new FlamePatch[flameSegmentsLength];

        /**
         * biến last dùng để đánh dấu cho segment cuối cùng
         */
        boolean last;

        // TODO: tạo các segment dưới đây

        int calculatedX = (int) x;
        int calculatedY = (int) y;
        for (int i = 0; i < flameSegmentsLength; i++) {
            if (direction == 0) {
                calculatedY--;
            } else if (direction == 1) {
                calculatedX++;
            } else if (direction == 2) {
                calculatedY++;
            } else if (direction == 3) {
                calculatedX--;
            }
            flamePatches[i] = new FlamePatch(calculatedX, calculatedY, direction, false);
        }

        if (flameSegmentsLength <= 0) {
            return;
        }
        flamePatches[flameSegmentsLength - 1].setLast(true);
    }

    /**
     * Tính toán độ dài của Flame, nếu gặp vật cản là Brick/Wall, độ dài sẽ bị cắt ngắn
     *
     * @return độ dài ngọn lửa chuẩn
     */
    private int calculatePermittedDistance() {
        // TODO: thực hiện tính toán độ dài của Flame
        int flameLength = 0;
        int cvtXtoInt = (int) x;
        int cvtYtoInt = (int) y;
        while (flameLength <= radius) {
            if (direction == 0) {
                cvtYtoInt--;
            } else if (direction == 1) {
                cvtXtoInt++;
            } else if (direction == 2) {
                cvtYtoInt++;
            } else if (direction == 3) {
                cvtXtoInt--;
            }

            Entity entityInFlame = board.getEntity(cvtXtoInt, cvtYtoInt, null);
            if (entityInFlame.collide(this)) {
                break;
            }
            flameLength++;
        }
        return flameLength;
    }

    public FlamePatch flameSegmentAt(int x, int y) {
        for (FlamePatch flamePatch : flamePatches) {
            if (flamePatch.getY() == y && flamePatch.getX() == x)
                return flamePatch;
        }
        return null;
    }

    @Override
    public void render(Screen screen) {
        for (FlamePatch flamePatch : flamePatches) {
            flamePatch.render(screen);
        }
    }

    @Override
    public void update() {
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý va chạm với Bomber, Enemy. Chú ý đối tượng này có vị trí chính là vị trí của Bomb đã nổ

        return true;
    }
}
