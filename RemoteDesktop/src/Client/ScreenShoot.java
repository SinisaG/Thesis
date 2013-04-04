package Client;

import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;




public class ScreenShoot{

	public BufferedImage getScreen() throws Exception{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle screenRectangle = new Rectangle(screenSize);
		Robot robot = new Robot();
		BufferedImage image = robot.createScreenCapture(screenRectangle);
		return image;
	}
	
	
} 