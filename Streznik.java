import java.net.*;
import java.io.*;
import java.util.*;

public class Streznik {

public static ArrayList<Socket> sockets = new ArrayList<Socket>();
	public static void main(String[] args) {
		int port = 666; //random port number
		try {

			ServerSocket ss = new ServerSocket(port);
			//prvi se poveze nadzorovani(client), nato nadzornik(server)
			
			while(true){
				
				Socket socket = ss.accept();
				sockets.add(socket);
				System.out.println("Nekdo se je povzedal an streznik");
				if(sockets.size()==2){
					SSocket sSocket = new SSocket();
					Thread t = new Thread(sSocket);
					t.start();	
				}
			}

		} catch (Exception e) {
		}
	}
}