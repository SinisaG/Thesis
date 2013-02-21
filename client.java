import java.net.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
public class client {

	public static int rows=4;
	public 	static int colums=4;
	public	static int chunks=rows*colums;
	public static BufferedImage imgs[] = new BufferedImage[chunks]; 
	public static ByteArrayOutputStream baos = new ByteArrayOutputStream();
	public static byte[] imageInByte;
	public static boolean prvic = true;

	public static void main(String [] args) throws Exception {
	
		Socket stranka=null;
		int portNumber = 666;
		ScreenShoot screen = new ScreenShoot();
		
		
		Robot mouse = new Robot();
		int clickM=0;
		boolean keyPress=false;
		int mouseX;
		int mouseY;
		int mouseXP=0;
		int mouseYP=0;
		long start;
		long stop;
		
		try{
			stranka=new Socket("192.168.1.3", portNumber);	
			OutputStream os = stranka.getOutputStream(); 
			DataOutputStream dos = new DataOutputStream( os );
			DataInputStream dis = new DataInputStream(stranka.getInputStream());
			BufferedImage image;
			
			while(true){
				start=System.nanoTime();
				image=screen.getScreen();
				if(prvic == true){
					ImageIO.write( image, "jpg", baos );
					baos.flush();
					imageInByte= baos.toByteArray();
					baos.close();
					dos.writeInt(imageInByte.length);
					dos.write(imageInByte,0,imageInByte.length);
					dos.flush();  
					baos.reset();
					prvic=false;
				}
				
				sliceAndSend(image, dos);			
				mouseX=dis.readInt();
				mouseY=dis.readInt();
				
				
				if(mouseX!=mouseXP || mouseY!=mouseYP){
					mouse.mouseMove(mouseX, mouseY);
					mouseXP=mouseX;
					mouseYP=mouseY;
				}
				
				clickM=dis.readInt();
				
				
				if(clickM==1) {
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
					int a=dis.readInt();
					mouse.keyPress(a);
					mouse.keyRelease(a);
					//System.out.println(a);
				} 
				stop=System.nanoTime();
				System.out.println((stop-start)/1000000);
				
			}
				
		}
		catch (Exception e){ 
			System.out.println(e);
		}
	}
	
	public static void sliceAndSend (BufferedImage i, DataOutputStream dos) throws Exception{

		int chunkWidth = i.getWidth()/rows;
		int chunkHeight = i.getHeight()/colums;
		
		int count=0;

		BufferedImage temp = new BufferedImage(chunkWidth, chunkHeight, i.getType()); 
		int pixelTemp;
		int pixel;
		int randomx;
		int randomy;
		boolean diff=true;
		for (int x = 0; x < rows; x++) {  
            for (int y = 0; y < colums; y++) {  
                //Initialize the image array with image chunks  
				if(imgs[count]!=null){
					
					temp= new BufferedImage(chunkWidth, chunkHeight, i.getType());  
				
					// draws the image chunk  
					//Graphics2D gr = imgs[count++].createGraphics();  
					Graphics2D gr = temp.createGraphics();
					gr.drawImage(i, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);  
					gr.dispose();  
					//System.out.println(temp);
					//System.out.println(imgs[count]);
					//ImageIO.write(temp, "jpg", new File("img.jpg")); 
					
					for(int m=0; m<500; m++){
						randomx=(int)(Math.random()*chunkWidth);
						randomy=(int)(Math.random()*chunkHeight);
						pixelTemp=temp.getRGB(randomx, randomy);
						pixel=imgs[count].getRGB(randomx, randomy);
					
						if(pixelTemp!=pixel){
							diff=true;
							break;
						}
					}
				
					if(diff==true){
						imgs[count]=temp;
						diff=false;
						dos.writeBoolean(true);
						ImageIO.write( imgs[count], "jpg", baos );
						baos.flush();
						imageInByte= baos.toByteArray();
						baos.close();
						dos.writeInt(imageInByte.length);
						dos.write(imageInByte,0,imageInByte.length);
						dos.flush();  
						baos.reset();
						
					}
					
					else{
					
						dos.writeBoolean(false);
					}
					
					count++;
					
				}
				
				else{
					imgs[count]= new BufferedImage(chunkWidth, chunkHeight, i.getType());  
	  
					// draws the image chunk  
					Graphics2D gr = imgs[count++].createGraphics();  
					gr.drawImage(i, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);  
					gr.dispose();  
						
				}
            }  
			
        }
					
	}
	
	

}