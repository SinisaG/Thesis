import java.net.*;
import java.io.*;
class SSocket implements Runnable {

	@Override
	public void run() {
		try {
			int rows=4;
			int colums=4;
			int chunks=rows*colums;
			byte[] data = null;
			//client
			InputStream in = Streznik.sockets.get(0).getInputStream();
			OutputStream out = Streznik.sockets.get(0).getOutputStream();
			DataInputStream employeeI = new DataInputStream(in);
			DataOutputStream employeeO = new DataOutputStream(out);
			//server
			InputStream inn = Streznik.sockets.get(1).getInputStream();
			OutputStream outt = Streznik.sockets.get(1).getOutputStream();
			DataInputStream employerI = new DataInputStream(inn);
			DataOutputStream employerO = new DataOutputStream(outt);
			
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
		 catch (Exception e) {}
		 
		
	}
}