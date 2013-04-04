/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Streznik;

import java.io.*;
import java.net.Socket;

/**
 *
 * @author Siniša
 */
public class ConnectionListener implements Runnable{
    
    Socket a=null;
    int con=0;
    int id = 0;
    public ConnectionListener(Socket a, int _id){  
        this.a=a;   
        this.id =_id;
    }
       
    @Override
    public void run() {    
        try{
            System.out.println("Connection listener for " + id + " has started!");
            InputStream in = a.getInputStream();        
            DataInputStream dataIn = new DataInputStream(in);            
            con=dataIn.readInt();
            
            //wait for two minutes, if there is still no target, remove the connection
            for(int i=0; i<120; i++){           
                if(Streznik.connections.containsKey(con)){               
                    SSocket s = new SSocket(a, (Socket) Streznik.connections.get(con), id, con);
                    Thread t = new Thread(s);
                    t.start();
                    break;
                }          
                Thread.sleep(1000);
            }
            
           if(!Streznik.connections.containsKey(con)) Streznik.connections.remove(id);
        }
        catch (Exception e){
        
            System.out.println("Something went wrong.... " + e );
            Streznik.connections.remove(id);
        } 
    }
    
}
