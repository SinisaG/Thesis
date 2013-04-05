package Streznik;

import java.io.DataInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.*;
import java.util.*;

public class Streznik {

    //public static ArrayList<Socket> sockets = new ArrayList<Socket>();
    public static Hashtable  connections = new Hashtable();    
    public static void main(String[] args) {
        
        int port=0;; //random port number
        InputStream is;
        DataInputStream dis = null;
        int id=0;
        boolean isBoss=false;
        
        
        try {
             
            Scanner scan = new Scanner(new File("Config/config.txt"));
            scan.useDelimiter("\n|;|:");          
            while (scan.hasNext()) {
               
                String temp = scan.next();                
                if("server port".equals(temp)){                
                    port=Integer.parseInt(scan.next());                  
                }             
            }
        } catch (Exception e) {
            System.out.println(e + "\nCan not find config file or it is not formated properly");
            System.exit(1);
        }
        
        try {

            ServerSocket ss = new ServerSocket(port);
            Socket client = null;
            while (true) {
                 client= ss.accept();
                //sockets.add(socket);
                System.out.println("Nekdo se je povezal na streznik");              
                dis=new DataInputStream(client.getInputStream());
                id=dis.readInt();
                isBoss=dis.readBoolean();               
                connections.put(id,client);  
                
                if(isBoss){
                    ConnectionListener l = new ConnectionListener(client, id);
                    Thread t = new Thread(l);
                    t.start();
                }       
                Set st = connections.keySet();    
                Iterator itr = st.iterator();
                while(itr.hasNext())
                   System.out.println(itr.next());

                }

        } catch (Exception e) {
          
            System.out.println("Server error : "  + e );
        }
    }
}