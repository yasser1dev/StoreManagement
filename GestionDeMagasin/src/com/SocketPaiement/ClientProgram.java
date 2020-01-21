package com.SocketPaiement;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;





public class ClientProgram {
	
	static int port=9999;
	Socket socket;
	InputStream in;
	OutputStream out;
	ObjectInputStream inObj;
	ObjectOutputStream outObj;
	PaiementData p;
	public ClientProgram(PaiementData p) throws UnknownHostException, IOException {
		socket=new Socket("127.0.0.1",port);
		System.out.println("connection accepted");
		in=socket.getInputStream();
		out=socket.getOutputStream();
		this.p=p;
	}
	public void sendData() throws IOException {
		outObj=new ObjectOutputStream(out);
		outObj.writeObject(p);
	}
	public Boolean getData() throws IOException, ClassNotFoundException {
		inObj=new ObjectInputStream(in);
		Boolean x=(Boolean)inObj.readObject();
		return x;
	}
	
	
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		//ClientProgram c=new ClientProgram();
		
		
		
	}
}
