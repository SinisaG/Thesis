import java.awt.event.*;
import java.awt.KeyEventDispatcher;
import java.util.*;

public class MyKeyListener implements KeyEventDispatcher{

	//int [] temp = new int [2];
	boolean keyPress=false;
	//public static int i=0;
	public static boolean enkrat=false;
	public static int key;
	
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
	
	public boolean dispatchKeyEvent(KeyEvent e) {
		
		if(enkrat==false){
			key=e.getKeyCode();
			//i++;
			keyPress=true;
			//key=temp[0];
			enkrat=true;
		}
			return true;
	}


}