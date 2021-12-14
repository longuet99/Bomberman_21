package uet.oop.bomberman;

import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.gui.Frame;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.sound.effect.SoundEffect;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Tạo vòng lặp cho game, lưu trữ một vài tham số cấu hình toàn cục,
 * Gọi phương thức render(), update() cho tất cả các entity
 */
public class Game extends Canvas {

    public static final int TILES_SIZE = 16,
            WIDTH = TILES_SIZE * (31 / 2),
            HEIGHT = 13 * TILES_SIZE,
            MAXIMUM_JUMP_DISTANCE = 4,
            CHARACTER_WIDTH = 11,
            CHARACTER_HEIGHT = TILES_SIZE;


    public static int SCALE = 3;


    public static final String TITLE = "BombermanGame";

    public static final int TIME_BETWEEN_PLACE_BOMB = 10;

    private static int BOMBRATE = 1;
    private static final int BOMBRADIUS = 1;
    private static final double BOMBERSPEED = 1.0;
    private static final double ENEMY_SPEED = 0.5;


    public static final int TIME = 200;
    public static final int POINTS = 0;

    protected static int SCREENDELAY = 3;


    private static int numberOfEnemy = 0;
    protected static int bombRate = BOMBRATE;
    protected static int bombRadius = BOMBRADIUS;
    protected static int characterWidth = CHARACTER_WIDTH;
    protected static int characterHeight = CHARACTER_HEIGHT;
    protected static double bomberSpeed = BOMBERSPEED;
    protected static double enemySpeed = ENEMY_SPEED;


    protected int _screenDelay = SCREENDELAY;

    private Keyboard input;
    private boolean running = false;
    private boolean paused = true;

    private Board board;
    private Screen screen;
    private Frame frame;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    public Game(Frame frame) {
        this.frame = frame;
        this.frame.setTitle(TITLE);

        screen = new Screen(WIDTH, HEIGHT);
        input = new Keyboard();

        board = new Board(this, input, screen);
        addKeyListener(input);
    }


    private void renderGame() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.clear();

        board.render(screen);

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();

        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        board.renderMessages(g);

        g.dispose();
        bs.show();
    }

    private void renderScreen() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.clear();

        Graphics g = bs.getDrawGraphics();

        board.drawScreen(g);

        g.dispose();
        bs.show();
    }

    private void update() {
        input.update();
        board.update();
    }

    public void start() {
        running = true;

        SoundEffect.init();

        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0; //nanosecond, 60 frames per second
        double delta = 0;
        int frames = 0;
        int updates = 0;
        requestFocus();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }

            if (paused) {
                if (_screenDelay <= 0) {
                    board.setShow(-1);
                    paused = false;
                }

                renderScreen();
            } else {
                SoundEffect.GHOST.loopInf();
                renderGame();
            }


            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                frame.setTime(board.subtractTime());
                frame.setPoints(board.getPoints());
                timer += 1000;
                frame.setTitle(TITLE + " | " + updates + " rate, " + frames + " fps");
                updates = 0;
                frames = 0;

                if (board.getShow() == 2)
                    --_screenDelay;
            }
        }
    }

    public static int getNumberOfEnemy() {
        return numberOfEnemy;
    }

    public static void addEnemyCount(int i) {
        numberOfEnemy += i;
    }

    public static double getBomberSpeed() {
        return bomberSpeed;
    }


    public static int getCharacterWidth() {
        return characterWidth;
    }

    public static int getCharacterHeight() {
        return characterHeight;
    }

    public static int getBombRate() {
        return bombRate;
    }

    public static int getBombRadius() {
        return bombRadius;
    }


    public static void addBomberSpeed(double i) {
        bomberSpeed += i;
    }

    public static void addBombRadius(int i) {
        bombRadius += i;
    }

    public static void addBombRate(int i) {
        bombRate += i;
    }

    public void resetScreenDelay() {
        _screenDelay = SCREENDELAY;
    }

    public void resetBombRate() {
        bombRate = BOMBRATE;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isPaused() {
        return paused;
    }

    public void pause() {
        paused = true;
    }

}
