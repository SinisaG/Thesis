package Client;

import GUI.GUIClient;
import java.net.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class client implements Runnable {

    public static int rows = 4;
    public static int colums = 4;
    public static int chunks = rows * colums;
    public static BufferedImage imgs[] = new BufferedImage[chunks];
    public static ByteArrayOutputStream baos = new ByteArrayOutputStream();
    public static byte[] imageInByte;
    public static boolean prvic = true;
    public static int methodNumber;
    public static boolean allow = false;
    public static int bossId=0;
    
    @Override
    public void run() {   

       int id =(int)(Math.random() * 1000000000);
       ArrayList key = new ArrayList();
       int portNumber=0;
       String host="";
        
       try {            
            Scanner scan = new Scanner(new File("src/Config/config.txt"));
            scan.useDelimiter("\n|;|:");
           
            while (scan.hasNext()) {
                
                String temp = scan.next();              
                if("port number".equals(temp)){
                                     
                    portNumber=Integer.parseInt(scan.next());
                    
                }
                if("host".equals(temp)){
                    host = scan.next();
                }             
            }
        
        } catch (Exception e) {
            System.out.println(e + "\nCan not find config file or it is not formated properly");
            System.exit(1);
        }

        Socket stranka = null;
        ScreenShoot screen = new ScreenShoot();
         Robot mouse = null;
        try{
            mouse = new Robot();
        }
        catch (Exception e){
        
            System.out.println("Robot can't be created!");
        }
        int clickM = 0;
        boolean keyPress = false;
        int mouseX;
        int mouseY;
        int mouseXP = 0;
        int mouseYP = 0;
   
        methodNumber=1;
        try {
            stranka = new Socket(host, portNumber);            
            OutputStream os = stranka.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            DataInputStream dis = new DataInputStream(stranka.getInputStream());
            BufferedImage image;
            dos.writeInt(id);
            dos.writeBoolean(false);
            
            GUIClient gui = new GUIClient();
            gui.setSize(450, 350);
            gui.jTextField1.setText(Integer.toString(id));
            gui.jTextArea1.setText("Connected to the server. \nPlease confirm that you allow connections on your computer.\n");
            gui.setVisible(true);
            
            while (true) {           
                Thread.sleep(5);
                
                if(allow){
                    bossId=dis.readInt();
                    gui.jTextArea1.append(bossId + " has connected to your maschine." );
                    while(true){
                        image = screen.getScreen();
                        if (prvic == true) {
                            ImageIO.write(image, "jpg", baos);
                            baos.flush();
                            imageInByte = baos.toByteArray();
                            baos.close();
                            dos.writeInt(imageInByte.length);
                            dos.write(imageInByte, 0, imageInByte.length);
                            dos.flush();
                            baos.reset();
                            prvic = false;
                        }

                        sliceAndSend(image, dos);
                        mouseX = dis.readInt();
                        mouseY = dis.readInt();


                        if (mouseX != mouseXP || mouseY != mouseYP) {
                            mouse.mouseMove(mouseX, mouseY);
                            mouseXP = mouseX;
                            mouseYP = mouseY;
                        }

                        clickM = dis.readInt();


                        if (clickM == 1) {
                            int i = dis.readInt();
                            if (i == 1) {
                                mouse.mousePress(InputEvent.BUTTON1_MASK);
                                mouse.mouseRelease(InputEvent.BUTTON1_MASK);
                            }

                            if (i == 2) {
                                mouse.mousePress(InputEvent.BUTTON2_MASK);
                                mouse.mouseRelease(InputEvent.BUTTON2_MASK);
                            }

                            if (i == 3) {
                                mouse.mousePress(InputEvent.BUTTON3_MASK);
                                mouse.mouseRelease(InputEvent.BUTTON3_MASK);
                            }

                        }
                        keyPress = dis.readBoolean();

                        if (keyPress == true) {
                           key.add(dis.readInt());
                           if(key.size()>1){
                           
                               if((key.get(key.size() - 2) == KeyEvent.VK_ALT) || (key.get(key.size() - 2) == KeyEvent.VK_SHIFT)){
                               
                                    mouse.keyPress(key.size() - 1);
                                    mouse.keyPress(key.size() - 2);
                                    mouse.keyPress(key.size() - 1);
                                    mouse.keyRelease(key.size() - 2);
                               }
                               
                               else{
                               
                                   mouse.keyPress(key.size() - 1);
                                    mouse.keyRelease(key.size() - 1);
                               }                          
                           }                          
                           else {
                           
                                     mouse.keyPress(key.size() - 1);
                                    mouse.keyRelease(key.size() - 1);
                           }
                                 
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void sliceAndSend(BufferedImage i, DataOutputStream dos) throws Exception {

        int chunkWidth = i.getWidth() / rows;
        int chunkHeight = i.getHeight() / colums;

        int count = 0;

        BufferedImage temp = new BufferedImage(chunkWidth, chunkHeight, i.getType());
        int pixelTemp;
        int pixel;
        int randomx;
        int randomy;
        boolean diff = true;
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < colums; y++) {
                //Initialize the image array with image chunks  
                if (imgs[count] != null) {

                    temp = new BufferedImage(chunkWidth, chunkHeight, i.getType());
 
                    Graphics2D gr = temp.createGraphics();
                    gr.drawImage(i, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                    gr.dispose();
                    
                    if(methodNumber==1){
                        for (int m = 0; m < 500; m++) {
                            randomx = (int) (Math.random() * chunkWidth);
                            randomy = (int) (Math.random() * chunkHeight);
                            pixelTemp = temp.getRGB(randomx, randomy);
                            pixel = imgs[count].getRGB(randomx, randomy);

                            if (pixelTemp != pixel) {
                                diff = true;
                                break;
                            }
                        }

                        if (diff == true) {
                            imgs[count] = temp;
                            diff = false;
                            dos.writeBoolean(true);
                            ImageIO.write(imgs[count], "jpg", baos);
                            baos.flush();
                            imageInByte = baos.toByteArray();
                            baos.close();
                            dos.writeInt(imageInByte.length);
                            dos.write(imageInByte, 0, imageInByte.length);
                            dos.flush();
                            baos.reset();

                        } else {
                            
                            dos.writeBoolean(false);
                        }
                    }  
                    
                    
                    if(methodNumber==2){
                        //md5 method 
                       ImageIO.write(imgs[count], "jpg", baos);
                       baos.flush();
                       imageInByte = baos.toByteArray();
                       baos.close();
                       
                       ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                       ImageIO.write(temp, "jpg", outputStream);
                       byte[] data = outputStream.toByteArray();
                       outputStream.close();
                       
                      
                       if(!(Arrays.equals(imageInByte, data))){
                           imgs[count] = temp;
                           dos.writeBoolean(true);
                            dos.writeInt(data.length);
                            dos.write(data, 0, data.length);
                            dos.flush();
                            baos.reset();
                            outputStream.reset(); 
                       }
                       
                       else{
                       
                            dos.writeBoolean(false);
                            baos.reset();
                            outputStream.reset();
                       }
                    
                    
                    }
                    count++;

                } else {
                    imgs[count] = new BufferedImage(chunkWidth, chunkHeight, i.getType());

                    // draws the image chunk  
                    Graphics2D gr = imgs[count++].createGraphics();
                    gr.drawImage(i, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                    gr.dispose();

                }
            }

        }

    }
}