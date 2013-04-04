package Boss;

import java.awt.event.*;
import java.util.*;

public class MyKeyListener implements KeyListener{

	//int [] temp = new int [2];
	boolean keyPress=false;
	//public static int i=0;
	public static ArrayList key = new ArrayList();
	
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

