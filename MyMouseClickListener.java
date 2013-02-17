import java.awt.event.*;

public class MyMouseClickListener implements MouseListener{

	int buttonC;
	boolean mousy = false;

@Override
public void mouseClicked(MouseEvent e) {
	mousy=true;
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