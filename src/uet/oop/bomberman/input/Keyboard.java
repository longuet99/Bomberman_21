package uet.oop.bomberman.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Tiếp nhận và xử lý các sự kiện nhập từ bàn phím
 */
public class Keyboard implements KeyListener {
	
	private boolean[] phimBam = new boolean[120]; //120 là đủ cho trò chơi này
	public boolean len, xuong, trai, phai, datBom;
	
	public void update() {
		datBom = phimBam[KeyEvent.VK_SPACE] || phimBam[KeyEvent.VK_X];
		len = phimBam[KeyEvent.VK_UP] || phimBam[KeyEvent.VK_W];
		phai = phimBam[KeyEvent.VK_RIGHT] || phimBam[KeyEvent.VK_D];
		xuong = phimBam[KeyEvent.VK_S] || phimBam[KeyEvent.VK_DOWN];
		trai = phimBam[KeyEvent.VK_A] || phimBam[KeyEvent.VK_LEFT];
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		phimBam[e.getKeyCode()] = true;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		phimBam[e.getKeyCode()] = false;
		
	}

}
