package me.NoahCagle.yur.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class InputListener implements KeyListener, MouseMotionListener {
	
	public int mouseX, mouseY;
	
	public boolean[] keys = new boolean[65536];
	
	public boolean w, a, s, d, left, right, up, down;
	
	public void tick() {
		w = keys[KeyEvent.VK_W];
		a = keys[KeyEvent.VK_A];
		s = keys[KeyEvent.VK_S];
		d = keys[KeyEvent.VK_D];
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];
		up = keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_DOWN];
	}

	public void mouseDragged(MouseEvent e) {
		
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

}
