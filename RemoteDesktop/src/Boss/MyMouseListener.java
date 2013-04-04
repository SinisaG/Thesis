package Boss;

import java.awt.event.*;


public class MyMouseListener implements MouseMotionListener{

	public static int coordinataX;
	public static int coordinataY;
	
	public void mouseDragged(MouseEvent e) {}
	
	public void mouseMoved(MouseEvent e) {
		coordinataX=e.getX();
		coordinataY=e.getY();
	}
	

}