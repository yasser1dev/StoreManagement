package com.GestionPaiement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.DAO.AbstractDAO;
import com.DAO.DAO;
import com.GestionVente.VenteDAOIMPL;

public class IPaiEnLinge extends AbstractDAO implements DAO<PaiEnLigne>{
	private final String type="EnLigne";
	VenteDAOIMPL vDAO=new VenteDAOIMPL();
	
	@Override
	public PaiEnLigne find(long id) {
		String sql="SELECT * FROM `paiement` where id=? and type="+type+" ";
		PreparedStatement statement;
		ResultSet r;
		try {
			statement=dbAccess.getConnection().prepareStatement(sql);
			statement.setLong(1,id);
			r=statement.executeQuery();
			while(r.next()) {
				PaiEnLigne pEL=new PaiEnLigne(r.getLong("id"),r.getDate("dateEffet"),r.getString("numCard"),
						r.getDouble("montant"),r.getString("codeCard"),vDAO.find(r.getLong("idVente")));
				pEL.setVente(vDAO.find(r.getLong("idVente")));
				return pEL;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}

	@Override
	public void create(PaiEnLigne p) {
		String sql="insert into paiement(id,dateEffet,etat,type,idVente,montant,numCard,codeCard) values(?,?,?,?,?,?,?,?)";
		PreparedStatement statement;
		try {
			statement=dbAccess.getConnection().prepareStatement(sql);
			statement.setLong(1,p.getId());
			statement.setDate(2,p.getDateEffet());
			statement.setBoolean(3,true);
			statement.setString(4,type);
			statement.setLong(5,p.getVente().getId());
			statement.setDouble(6,p.getMontant());
			statement.setString(7,p.getNumCard());
			statement.setString(8,p.getCodeCard());


			statement.executeUpdate();
			System.out.println("paiement added");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}		
	

	@Override
	public void delete(long id) {
		String sql="delete from paiement where id='"+id+"' and type="+type+"";
		
		try {
			Statement statement=dbAccess.getConnection().createStatement();
			statement.executeUpdate(sql);
			System.out.println("query executed");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("query Failed");
			e.printStackTrace();
		}						
	}

	@Override
	public boolean update(PaiEnLigne p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<PaiEnLigne> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PaiEnLigne> finDAll(String Key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long maxId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
