import javax.imageio.ImageIO;
import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.*;


public class server{

	public static void main(String [] args){
		
		ServerSocket streznik=null;
		Socket klient = null;
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		DisplayImage kk = null;
		/*PointerInfo info;
		Point b;*/
		BufferedImage slika;
		Dimension ekran = Toolkit.getDefaultToolkit().getScreenSize();
		MyFocusLostListener focus = new MyFocusLostListener();
		MyMouseListener mouse = new MyMouseListener();
		MyMouseClickListener clickM = new MyMouseClickListener();
		MyKeyListener keyL = new MyKeyListener();
		try{
			streznik = new ServerSocket(80);
		}
		
		catch(Exception e){
			System.out.println("Pizdarija");
		}
		try{
			klient = streznik.accept();
			InputStream is = klient.getInputStream(); 
			DataInputStream dis = new DataInputStream( is );
			OutputStream os = klient.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			ByteArrayInputStream bais;
			int len;
			byte[] data;
			/*info=MouseInfo.getPointerInfo();
			b = info.getLocation();*/
			
			len = dis.readInt();
			data= new byte[len];
			dis.readFully(data);
			bais = new ByteArrayInputStream(data);
			slika=ImageIO.read(bais);
			kk=new DisplayImage(resize(slika));
			kk.setFocusable(true);
			kk.addMouseMotionListener(mouse);
			kk.addMouseListener(clickM);
			kk.addKeyListener(keyL);
			dos.writeInt(mouse.coordinataX);
			dos.writeInt(mouse.coordinataY);
			dos.writeBoolean(clickM.mousy);
			dos.writeBoolean(keyL.keyPress);
			frame.add(kk);
			frame.pack();
			frame.setVisible(true);
			frame.addWindowListener(focus);
			
			
			while(true){
				if(focus.aktivna==true){
					/*info=MouseInfo.getPointerInfo();
					b = info.getLocation();
					System.out.println(b);*/
					len = dis.readInt();
					data= new byte[len];
					dis.readFully(data);
					bais = new ByteArrayInputStream(data);
					kk.changeImage(resize(ImageIO.read(bais)));
					dos.writeInt(mouse.coordinataX);
					dos.writeInt(mouse.coordinataY);
					dos.writeBoolean(clickM.mousy);
					if(clickM.mousy==true){
						dos.writeInt(clickM.buttonC);
						clickM.mousy=false;
					}
					dos.writeBoolean(keyL.keyPress);
					if(keyL.keyPress==true){
						
						dos.writeInt(keyL.key);
						keyL.keyPress=false;
					}
				}
				if(focus.aktivna==false){
					Thread.sleep(500);
				}
			
			}
			
			
		}
		catch (Exception e){
			System.out.println(e);
		}
	}
	
	
	public static BufferedImage resize(BufferedImage img){
	
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		double iwidth=img.getWidth();
		double iheight=img.getHeight();
		double percentw;
		double percenth; 
		if(iwidth>width || iheight>height){
			
				BufferedImage dimg = dimg = new BufferedImage((int)width, (int)height , img.getType());  
				Graphics2D g = dimg.createGraphics();  
				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
				g.drawImage(img, 0, 0, (int)width, (int)height, 0, 0, (int)iwidth, (int)iheight, null);  
				g.dispose();  
				return dimg;  			
		}
		
		return img;
	}
}