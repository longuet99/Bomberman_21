package uet.oop.bomberman.graphics;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;

import java.awt.*;
import java.util.Arrays;

/**
 * Xử lý kết xuất cho tất cả các Đối tượng và một số màn hình phụ đối với Bảng điều khiển trò chơi
 */
public class Screen {
	protected int dai, rong;
	public int[] pixels;
	private int transparentColor = 0xffff00ff; //  mã màu vàng


	public static int xOffset = 0, yOffset = 0; // Trục XY bắt đầu từ 0
	
	public Screen(int dai, int rong) {
		this.dai = dai;
		this.rong = rong;
		
		pixels = new int[dai * rong];
		
	}
	
	public void clear() {
		Arrays.fill(pixels, 0);
	}
	
	public void renderEntity(int xp, int yp, Entity entity) { //lưu pixel thực thể
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; !(y >= entity.getSprite().layKichCo()); y++) {
			int ya; //thêm bù đắp
			ya = y + yp;
			for (int x = 0; entity.getSprite().layKichCo() > x; x++) {
				int xa = xp + x; //thêm bù đắp
				if (xa >= -entity.getSprite().layKichCo() && xa < dai && ya >= 0 && ya < rong) {
					if (0 > xa) xa = 0; //bắt đầu từ 0 từ trái
					int color;
					color = entity.getSprite().layPixel(x + y * entity.getSprite().layKichCo());
					if (transparentColor == color) {
						continue;
					}
					pixels[xa + ya * dai] = color;
				} else {
					break; //sửa lề đen
				}
			}
		}
	}
	
	public void renderEwHiddenE(int xp, int yp, Entity entity, Sprite below) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; entity.getSprite().layKichCo() > y; y++) {
			int ya;
			ya = y + yp;
			for (int x = 0; !(x >= entity.getSprite().layKichCo()); x++) {
				int xa;
				xa = xp + x;
				if(!(xa >= -entity.getSprite().layKichCo() && xa < dai && ya >= 0 && ya < rong)) break; //sửa lề đen
				if (xa >= 0) {
				} else {
					xa = 0;
				}
				int color = entity.getSprite().layPixel(x + y * entity.getSprite().layKichCo());
				if(!(color == transparentColor))
					pixels[xa + ya * dai] = color;
				else
					pixels[xa + ya * dai] = below.layPixel(x + y * below.layKichCo());
			}
		}
	}
	
	public static void datOffset(int xO, int yO) {
		yOffset = yO;
		xOffset = xO;
	}
	
	public static int tinhXOffset(Board board, Bomber bomber) {
		if(!(bomber != null)) return 0;
		int temp = xOffset;
		
		double BomberX = bomber.getX() / 16;
		double complement = 0.5;
		int firstBreakpoint = board.getWidth() / 4;
		int lastBreakpoint = board.getWidth() - firstBreakpoint;

		if(!(BomberX <= firstBreakpoint + complement) && BomberX < lastBreakpoint - complement) {
			temp = (int)bomber.getX()  - (Game.chieuDai / 2);
		}

		return temp;
	}

	public void drawPaused(Graphics g) {
		Font font = new Font("Arial", Font.PLAIN, 20 * Game.tiLe);
		g.setFont(font);
		g.setColor(Color.white);
		drawCenteredString("PAUSED", getRealWidth(), getRealHeight(), g);

	}

	public void drawChangeLevel(Graphics g, int level) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getRealWidth(), getRealHeight());

		Font font = new Font("Arial", Font.PLAIN, 20 * Game.tiLe);
		g.setFont(font);
		g.setColor(Color.white);
		drawCenteredString("LEVEL " + level, getRealWidth(), getRealHeight(), g);

	}
	
	public void drawEndGame(Graphics g, int points) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getRealWidth(), getRealHeight());
		
		Font font = new Font("Arial", Font.PLAIN, 20 * Game.tiLe);
		g.setFont(font);
		g.setColor(Color.white);
		drawCenteredString("GAME OVER", getRealWidth(), getRealHeight(), g);
		
		font = new Font("Arial", Font.PLAIN, 10 * Game.tiLe);
		g.setFont(font);
		g.setColor(Color.yellow);
		drawCenteredString("POINTS: " + points, getRealWidth(), getRealHeight() + (Game.TILES_SIZE * 2) * Game.tiLe, g);
	}

	public void drawCenteredString(String s, int w, int h, Graphics g) {
	    FontMetrics fm = g.getFontMetrics();
	    int x;
		x = (w - fm.stringWidth(s)) / 2;
		int y;
		y = ((h - (fm.getAscent() + fm.getDescent())) / 2 + fm.getAscent());
		g.drawString(s, x, y);
	 }

	public int getRong() {
		return rong;
	}
	
	public int getDai() {
		return dai;
	}

	public int getRealHeight() {
		return rong * Game.tiLe;
	}
	
	public int getRealWidth() {
		return dai * Game.tiLe;
	}

}
