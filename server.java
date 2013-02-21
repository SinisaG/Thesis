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

	public static BufferedImage [] pics = new BufferedImage[16];

	public static void main(String [] args){
		
		
		Socket klient = null;
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
			klient = new Socket("localhost", 666);	
		}
		
		catch(Exception e){
			System.out.println("Pizdarija");
		}
		try{
			
			InputStream is = klient.getInputStream(); 
			DataInputStream dis = new DataInputStream( is );
			OutputStream os = klient.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			ByteArrayInputStream bais;
			int len=0;
			byte[] data;
			/*info=MouseInfo.getPointerInfo();
			b = info.getLocation();*/
			
			len = dis.readInt();
			
			data= new byte[len];
			dis.readFully(data);
			bais = new ByteArrayInputStream(data);
			slika=ImageIO.read(bais);
			slice(slika);
			kk=new DisplayImage(resize(slika));
			kk.setFocusable(true);
			kk.addMouseMotionListener(mouse);
			kk.addMouseListener(clickM);
			kk.addKeyListener(keyL);
			dos.writeInt(mouse.coordinataX);
			dos.writeInt(mouse.coordinataY);
			dos.writeInt(clickM.mousy);
			
			dos.writeBoolean(keyL.keyPress);
			frame.add(kk);
			frame.pack();
			frame.setVisible(true);
			frame.addWindowListener(focus);
			
			int x=0;
			int y=0;
			int indeks=0;
			//System.out.println(slika);
			
			while(true){
				if(focus.aktivna==true){
					/*info=MouseInfo.getPointerInfo();
					b = info.getLocation();
					System.out.println(b);*/
					//long start=System.nanoTime();
					
					
					//System.out.println((stop-start)/1000000);
					kk.changeImage(resize(glu(dis, bais, data, len)));
					
					if(ekran.getWidth()<slika.getWidth()){	
							x=(int)(mouse.coordinataX*slika.getWidth()/ekran.getWidth());
							y=(int)(mouse.coordinataY*slika.getHeight()/(ekran.getHeight()-70));
					}
					
					else {
						x=mouse.coordinataX;
						y=mouse.coordinataY;
					
					}
					
					dos.writeInt(x);
					dos.writeInt(y);
					
					dos.writeInt(clickM.mousy);
					if(clickM.mousy==1){
						dos.writeInt(clickM.buttonC);
						clickM.mousy=0;
					}
					
					if(keyL.key.size() != indeks && keyL.key.size()!=0){
						dos.writeBoolean(true);
						dos.writeInt((int)keyL.key.get(indeks));
						indeks++;								
					}
					else{
						dos.writeBoolean(false);
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
				g.drawImage(img, 0, 0, (int)width, (int)height -70, 0, 0, (int)iwidth, (int)iheight, null);  
				g.dispose();  
				return dimg;  			
		}
		
		return img;
	}
	
	
	public static BufferedImage glu(DataInputStream dis, ByteArrayInputStream bais, byte [] data, int len) throws Exception{
	
		int rows=4;
		int colums=4;
		int chunks=rows*colums;
		int chunkWidth;
		int chunkHeight;
		int type;
		
		
		for(int i=0; i<chunks; i++){	
			if(dis.readBoolean()){				
				len = dis.readInt();
				data= new byte[len];
				dis.readFully(data);
				bais = new ByteArrayInputStream(data);
				pics[i]=ImageIO.read(bais);
			}
		}
		
		type = pics[0].getType();  
        chunkWidth = pics[0].getWidth();  
        chunkHeight = pics[0].getHeight();
		
		BufferedImage finalImg = new BufferedImage(chunkWidth*colums, chunkHeight*rows, type); 
	
		int num = 0;  
        for (int i = 0; i < rows; i++) {  
            for (int j = 0; j < colums; j++) {  
                finalImg.createGraphics().drawImage(pics[num], chunkWidth * j, chunkHeight * i, null);  
                num++;  
            }  
        }  
		return finalImg;
	}
	
	
	public static void slice (BufferedImage image){
	
		int rows = 4; //You should decide the values for rows and cols variables  
        int cols = 4;  
        int chunks = rows * cols;  
  
        int chunkWidth = image.getWidth() / cols; // determines the chunk width and height  
        int chunkHeight = image.getHeight() / rows;  
        int count = 0;  
        for (int x = 0; x < rows; x++) {  
            for (int y = 0; y < cols; y++) {  
                //Initialize the image array with image chunks  
                pics[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());  
  
                // draws the image chunk  
                Graphics2D gr = pics[count++].createGraphics();  
                gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);  
                gr.dispose();  
            }  
        }  
	}
	
}