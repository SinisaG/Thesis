import java.awt.event.*;
import java.awt.KeyEventDispatcher;
import java.util.*;

public class MyKeyListener implements KeyListener{

	//int [] temp = new int [2];
	boolean keyPress=false;
	//public static int i=0;
	public static ArrayList key = new ArrayList();
	/*@Override
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
		c=e.getKeyChar();
		if(c==VK_A) key=65;
		System.out.println(key);
		
	}*/
	
	public void keyPressed(KeyEvent e) {
        //  Invoked when a key has been pressed.
			//System.out.println("p");
			key.add(e.getKeyCode());
		}
	public void	keyReleased(KeyEvent e) {
          //Invoked when a key has been released.
			//System.out.println("r");
		  }
	public void	keyTyped(KeyEvent e) {
        //  Invoked when a key has been typed.
			
		}
	
}

