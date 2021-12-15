package uet.oop.bomberman.level;


import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.*;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import java.io.*;

public class FileLevelLoader extends LevelLoader {

    /**
     * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
     * từ ma trận bản đồ trong tệp cấu hình
     */
    private static char[][] _map;

    public FileLevelLoader(Board board, int level) throws LoadLevelException {
        super(board, level);
    }

    @Override
    public void taiManChoi(int manChoi) throws LoadLevelException {

        String levelFilePathName = "/levels/Level" + manChoi + ".txt";
        InputStream in = FileLevelLoader.class.getResourceAsStream(levelFilePathName);
        if (in == null) {
            System.out.println(levelFilePathName); // for checking purposes
            throw new LoadLevelException();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String firstLine;
        String[] thongTinManChoi;
        try {
            firstLine = reader.readLine();
            thongTinManChoi = firstLine.split(" ");
            if (thongTinManChoi.length == 3) {
                this.manChoi = Integer.parseInt(thongTinManChoi[0]);
                this.rong = Integer.parseInt(thongTinManChoi[1]);
                this.dai = Integer.parseInt(thongTinManChoi[2]);
            } else {
                throw new LoadLevelException("Level bi loi, vui long thu lai");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        _map = new char[this.rong][this.dai];

        try {
            for (int i = 0; i < this.rong; i++) {
                String str = reader.readLine();
                //System.out.println(str); // test purposes
                for (int j = 0; j < this.dai; j++) {
                    _map[i][j] = str.charAt(j);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new LoadLevelException("Level bi loi, vui long thu lai");
        }


        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void taoVatThe() {


        for (int y = 0; y < this.rong; y++) {
            for (int x = 0; x < this.dai; x++) {
                int position = y * dai + x;
                if (_map[y][x] == '*') {
                    // Brick tile
                    board.themVatThe(position,
                            new LayeredEntity(x, y,
                                    new Grass(x, y, Sprite.grass),
                                    new Brick(x, y, Sprite.brick))
                    );
                } else if (_map[y][x] == '#') {
                    // Wall tile
                    board.themVatThe(position, new Wall(x, y, Sprite.wall));
                } else if (_map[y][x] == ' ') {
                    // Grass tile
                    board.themVatThe(position, new Grass(x, y, Sprite.grass));
                } else if (_map[y][x] == '1') {
                    // Balloon boy
                    board.addCharacter(new Balloon(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
                    board.themVatThe(position, new Grass(x, y, Sprite.grass));
                } else if (_map[y][x] == '2') {
                    // Oneal
                    board.addCharacter(new Oneal(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
                    board.themVatThe(position, new Grass(x, y, Sprite.grass));
                } else if (_map[y][x] == '3') {
                    // Dahl
                    board.addCharacter(new Dahl(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
                    board.themVatThe(position, new Grass(x, y, Sprite.grass));
                } else if (_map[y][x] == '4') {
                    // Minvo
                    board.addCharacter(new Minvo(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
                    board.themVatThe(position, new Grass(x, y, Sprite.grass));
                } else if (_map[y][x] == '5') {
                    // Doria
                    board.addCharacter(new Doria(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
                    board.themVatThe(position, new Grass(x, y, Sprite.grass));
                } else if (_map[y][x] == 'b') {
                    // More boom, more fun
                    board.themVatThe(position,
                            new LayeredEntity(
                                    x, y,
                                    new Grass(x, y, Sprite.grass),
                                    new BombItem(x, y, Sprite.powerup_bombs),
                                    new Brick(x, y, Sprite.brick)
                            ));
                } else if (_map[y][x] == 'f') {
                    // Flames patches
                    board.themVatThe(position,
                            new LayeredEntity(
                                    x, y,
                                    new Grass(x, y, Sprite.grass),
                                    new FlameItem(x, y, Sprite.powerup_flames),
                                    new Brick(x, y, Sprite.brick)
                            ));
                } else if (_map[y][x] == 'p') {
                    // Bomber
                    board.addCharacter(new Bomber(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
                    Screen.datOffset(0, 0);
                    board.themVatThe(position, new Grass(x, y, Sprite.grass));
                } else if (_map[y][x] == 's') {
                    // Speed item
                    board.themVatThe(position,
                            new LayeredEntity(
                                    x, y,
                                    new Grass(x, y, Sprite.grass),
                                    new SpeedItem(x, y, Sprite.powerup_speed),
                                    new Brick(x, y, Sprite.brick)
                            ));
                } else if (_map[y][x] == 'x') {
                    // Portal tile
                    board.themVatThe(position, new LayeredEntity(
                            x, y,
                            new Portal(x, y, Sprite.portal, board),
                            new Brick(x, y, Sprite.brick)
                    ));
                } else {
                    // Load grass default
                    board.themVatThe(position, new Brick(x, y, Sprite.brick));
                }

            }
        }
    }
}