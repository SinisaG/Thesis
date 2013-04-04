package Streznik;

import java.io.*;
import java.net.Socket;
class SSocket implements Runnable {
        
        Socket boss=null;
        Socket client=null;
        int bossID=0;
        int clientID=0;
    
        public SSocket(Socket _boss, Socket _client, int a, int b) {

            this.boss=_boss;
            this.client=_client;
            bossID=a;
            clientID=b;          
        }
    
        
	@Override
	public void run() {
		try {
                        System.out.println("Remote desktop running");
                        int rows=4;
			int colums=4;
			int chunks=rows*colums;
			byte[] data = null;
			//client
			InputStream in = client.getInputStream();
			OutputStream out = client.getOutputStream();
			DataInputStream employeeI = new DataInputStream(in);
			DataOutputStream employeeO = new DataOutputStream(out);
			//server
			InputStream inn = boss.getInputStream();
			OutputStream outt = boss.getOutputStream();
			DataInputStream employerI = new DataInputStream(inn);
			DataOutputStream employerO = new DataOutputStream(outt);
			
                        employeeO.writeInt(employerI.readInt());
			int len=employeeI.readInt();
			employerO.writeInt(len);
			data=new byte [len];
			employeeI.readFully(data);
			employerO.write(data,0,data.length);
			employerO.flush();
			
			employeeO.writeInt(employerI.readInt());
			employeeO.writeInt(employerI.readInt());
			employeeO.writeInt(employerI.readInt());
			employeeO.writeBoolean(employerI.readBoolean());
			
			
			
		   while (true) {
				

			boolean sprememba;
			
			for(int i=0; i<chunks; i++){
				sprememba=employeeI.readBoolean();
				employerO.writeBoolean(sprememba);
				if(sprememba){			
				int b=employeeI.readInt();
				employerO.writeInt(b);
				data=new byte[b];
				employeeI.readFully(data);
				employerO.write(data,0,data.length);
				employerO.flush();
				}
			}
				
				//koordinate miske
				employeeO.writeInt(employerI.readInt());
				employeeO.writeInt(employerI.readInt());
				
				//miskin klik
				int a=employerI.readInt();
				employeeO.writeInt(a);
				if(a==1){	
					employeeO.writeInt(employerI.readInt());
				}
				
				//tipkovnica en boolean se zihr prense!
				boolean tipka=employerI.readBoolean();
				employeeO.writeBoolean(tipka);
				if(tipka==true){
					employeeO.writeInt(employerI.readInt());
				}
			}
				
		 }
		 catch (Exception e) {               
                     Streznik.connections.remove(bossID);
                     Streznik.connections.remove(clientID);
                 }
		 
		
	}
}