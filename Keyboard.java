import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener,Input {

	//this number mught need to be increased.
	private boolean[] keys = new boolean[255];
	
	public boolean up, down, left, right, space, esc;
	
	public void getInput()
	{
		up = keys[KeyEvent.VK_UP] ||  keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] ||  keys[KeyEvent.VK_S];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		left = keys[KeyEvent.VK_LEFT] ||  keys[KeyEvent.VK_A];
		space = keys[KeyEvent.VK_SPACE];
		esc = keys[KeyEvent.VK_ESCAPE];
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		keys[e.getKeyCode()] = true;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

}