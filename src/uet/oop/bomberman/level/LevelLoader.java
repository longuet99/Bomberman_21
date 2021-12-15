package uet.oop.bomberman.level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.exceptions.LoadLevelException;

/**
 * Load và lưu trữ thông tin bản đồ các màn chơi
 */
public abstract class LevelLoader {

	protected int dai = 20, rong = 20; // default values just for testing
	protected int manChoi;
	protected Board board;

	public LevelLoader(Board board, int manChoi) throws LoadLevelException {
		this.board = board;
		taiManChoi(manChoi);
	}

	public abstract void taiManChoi(int manChoi) throws LoadLevelException;

	public abstract void taoVatThe();

	public int getDai() {
		return dai;
	}

	public int getRong() {
		return rong;
	}

	public int getManChoi() {
		return manChoi;
	}

}
