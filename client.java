import java.net.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.*;
import java.awt.event.*;

public class client {

	public static void main(String [] args) throws Exception {
	
		Socket stranka=null;
		int portNumber = 80;
		ScreenShoot screen = new ScreenShoot();
		Robot mouse = new Robot();
		boolean clickM=false;
		boolean keyPress=false;
		try{
			stranka=new Socket("192.168.1.4", portNumber);	
			OutputStream os = stranka.getOutputStream(); 
			DataOutputStream dos = new DataOutputStream( os );
			DataInputStream dis = new DataInputStream(stranka.getInputStream());
			BufferedImage image;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] imageInByte;
			while(true){
				image=screen.getScreen();
				ImageIO.write( image, "png", baos );
				baos.flush();
				imageInByte= baos.toByteArray();
				baos.close(); 
				dos.writeInt(imageInByte.length);
				dos.write(imageInByte,0,imageInByte.length);
				dos.flush();  
				baos.reset();
				mouse.mouseMove(dis.readInt(), dis.readInt());
				clickM=dis.readBoolean();
				if(clickM==true) {
					int i = dis.readInt();
					if(i==1) {
						mouse.mousePress(InputEvent.BUTTON1_MASK);
						mouse.mouseRelease(InputEvent.BUTTON1_MASK);
					}
					
					if(i==2) {
						mouse.mousePress(InputEvent.BUTTON2_MASK);
						mouse.mouseRelease(InputEvent.BUTTON2_MASK);
					}
					
					if(i==3) {
						mouse.mousePress(InputEvent.BUTTON3_MASK);
						mouse.mouseRelease(InputEvent.BUTTON3_MASK);
					}				
					
				}
				keyPress=dis.readBoolean();				
				if(keyPress==true){
				
					System.out.println(dis.readInt());;
				}
				
			}
		}
		
		catch (Exception e){
			
			System.out.println(e);
		}
	}


}