import java.awt.event.*;

public class MyKeyListener implements KeyListener{

	public static int key;
	boolean keyPress=false;
	@Override
	public void keyPressed(KeyEvent e){
		key=e.getKeyCode();
		keyPress=true;
	}
	@Override
	public void keyReleased(KeyEvent e){}
	@Override
	public void keyTyped(KeyEvent e){
		key=e.getKeyCode();
		keyPress=true;
		
	}


}