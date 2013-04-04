package Boss;

import java.awt.event.*;

public class MyMouseClickListener implements MouseListener{

	int buttonC;
	int mousy = 0;
	
@Override
public void mouseClicked(MouseEvent e) {
	mousy=1;
	buttonC=e.getButton();
}
@Override
public void mouseEntered(MouseEvent e) {}
@Override
public void mouseExited(MouseEvent e) {}
@Override
public void mousePressed(MouseEvent e) {}
@Override
public void mouseReleased(MouseEvent e) {}

}