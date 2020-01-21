package com.SocketPaiement;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;


public class ServerProgram {
	static int port=9999;
	ServerSocket serverSocket;
	Socket socket;
	InputStream in;
	OutputStream out;
	ObjectInputStream inObj;
	ObjectOutputStream outObj;
	
	public ServerProgram() throws IOException {
		serverSocket=new ServerSocket(port);
		System.out.println("Server started");
	}
	public void accept() throws IOException, SQLException {
		socket=serverSocket.accept();
		Thread srvT=new ServerThread(socket);
		srvT.start();
		//broadCast();
		
	}
	
	public void broadCast() throws IOException {
		out=socket.getOutputStream();
		DataOutputStream dataOut=new DataOutputStream(out);
		String msg="thank's for using our service";
		dataOut.writeUTF(msg);
	}
	public static void main(String[] args) throws IOException, SQLException {
		ServerProgram srvP=new ServerProgram();
		while(true) srvP.accept();
	}

}
