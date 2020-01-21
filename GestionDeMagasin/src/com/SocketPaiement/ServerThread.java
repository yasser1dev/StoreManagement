package com.SocketPaiement;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.DAO.DataConnection;
import com.GestionClient.IClientDAOIMPL;
import com.GestionPaiement.Bank;
import com.GestionPaiement.Cheque;
import com.GestionPaiement.IPaiChequeImpl;
import java.sql.PreparedStatement;

public class ServerThread extends Thread{
	Socket socket;
	InputStream in;
	OutputStream out;
	ObjectInputStream inObj;
	ObjectOutputStream outObj;
	DataConnection dbAccess;
	
	
	public ServerThread(Socket socket) throws SQLException {
		this.socket=socket;
		dbAccess=DataConnection.getInstance();
		try {
			in=socket.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out=socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void checkSolde(PaiementData p) throws IOException {
		String sql="select * from compte where idClient=? and numCard=? and codeCard=?";
		PreparedStatement statement;
		ResultSet r;
		try {
			statement=dbAccess.getConnection().prepareStatement(sql);
			statement.setLong(1,p.c.getId());
			statement.setString(2,p.numCard);
			statement.setString(3,p.codeCard);

			r=statement.executeQuery();
			Double solde=0.0;
			while(r.next()) {
				solde=r.getDouble("solde");
			}
			if(solde>p.montant) {
				sendData(true);
			}
			else sendData(false);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void sendData(Boolean x) throws IOException {
		outObj=new ObjectOutputStream(out);
		outObj.writeObject(x);
	}
	public void getData() throws IOException, ClassNotFoundException {
		inObj=new ObjectInputStream(in);
		PaiementData p=(PaiementData)inObj.readObject();
		checkSolde(p);
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			getData();
			socket.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
}
