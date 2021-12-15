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
            switch (direction) {
                case 0: {
                    calculatedY--;
                    break;
                }
                case 1: {
                    calculatedX++;
                    break;
                }
                case 2: {
                    calculatedY++;
                    break;
                }
                case 3: {
                    calculatedX--;
                    break;
                }
                default: {
                    break;
                }
            }
            flamePatches[i] = new FlamePatch(calculatedX, calculatedY, direction, false);
        }

        if (flameSegmentsLength > 0) {
            flamePatches[flameSegmentsLength - 1].setLast(true);
        }
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
        for (; flameLength <= radius; flameLength++) {
            switch (direction) {
                case 0: {
                    cvtYtoInt--;
                    break;
                }
                case 1: {
                    cvtXtoInt++;
                    break;
                }
                case 2: {
                    cvtYtoInt++;
                    break;
                }
                case 3: {
                    cvtXtoInt--;
                    break;
                }
                default: {
                    break;
                }
            }

            Entity entityInFlame = board.getEntity(cvtXtoInt, cvtYtoInt, null);
            if (entityInFlame.collide(this)) {
                break;
            }
        }
        return flameLength;
    }

    public FlamePatch flameSegmentAt(int x, int y) {
        for (int i = 0; i < flamePatches.length; i++) {
            if ( flamePatches[i].getY() == y && flamePatches[i].getX() == x)
                return flamePatches[i];
        }
        return null;
    }

    @Override
    public void render(Screen screen) {
        for (int i = 0; i < flamePatches.length; i++) {
            flamePatches[i].render(screen);
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
