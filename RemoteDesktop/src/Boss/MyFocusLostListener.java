package Boss;

import java.awt.event.*;

class MyFocusLostListener implements WindowListener {

	public static boolean aktivna=true;
	
	@Override
        public void windowActivated(WindowEvent e) {		
		aktivna=true;		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {		
		aktivna=false;		
	}
	@Override	
	public void windowClosed(WindowEvent e)  {	
		System.exit(0);
	}
	@Override
	public void windowClosing(WindowEvent e)  {	
		System.exit(0);
	}
	@Override
	public void windowDeiconified(WindowEvent e)  {	
		aktivna=true;
	}
	@Override
	public void windowIconified(WindowEvent e)  {	
		aktivna=false;
	}
	@Override
	public void windowOpened(WindowEvent e) {
                aktivna=true;
	}

}	