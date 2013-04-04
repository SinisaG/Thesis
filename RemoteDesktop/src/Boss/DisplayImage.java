package Boss;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class DisplayImage extends JPanel {

   BufferedImage image;
   Dimension size;
 
    public DisplayImage(BufferedImage i) {
        size = new Dimension();
        image = i;
        size.width = image.getWidth(null);
        size.height = image.getHeight(null);
        setPreferredSize(size);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, null);
    }
	
	public void changeImage(BufferedImage j){
		image=j;
		this.repaint();
	}
    
}