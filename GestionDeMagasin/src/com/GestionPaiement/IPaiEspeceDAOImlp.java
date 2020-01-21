package com.GestionPaiement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.DAO.AbstractDAO;
import com.DAO.DAO;
import com.GestionClient.Client;
import com.GestionClient.Ville;
import com.GestionVente.VenteDAOIMPL;

public class IPaiEspeceDAOImlp extends AbstractDAO implements DAO<PaiEspece>{
	
	public final String type="espece";
	VenteDAOIMPL vDAO=new VenteDAOIMPL();
	@Override
	public PaiEspece find(long id) {
		String sql="SELECT (id,dateEffet,idVente,montant) FROM `paiement` where id=? and type="+type+" ";
		PreparedStatement statement;
		ResultSet r;
		try {
			statement=dbAccess.getConnection().prepareStatement(sql);
			statement.setLong(1,id);
			r=statement.executeQuery();
			while(r.next()) {
				PaiEspece pesp=new PaiEspece(r.getLong("id"),r.getDate("dateEffet"),r.getDouble("montant"),vDAO.find(r.getLong("idVente")));
				pesp.setVente(vDAO.find(r.getLong("idVente")));
				return pesp;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
	

	@Override
	public void create(PaiEspece p) {
		String sql="insert into paiement(id,dateEffet,etat,type,idVente,montant) values(?,?,?,?,?,?)";
		PreparedStatement statement;
		try {
			statement=dbAccess.getConnection().prepareStatement(sql);
			statement.setLong(1,p.getId());
			statement.setDate(2,p.getDateEffet());
			statement.setBoolean(3,true);
			statement.setString(4,type);
			statement.setLong(5,p.getVente().getId());
			statement.setDouble(6,p.getMontant());

			statement.executeUpdate();
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
	public boolean update(PaiEspece p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<PaiEspece> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PaiEspece> finDAll(String Key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long maxId() {
		String sql= "select `AUTO_INCREMENT` from information_schema.TABLES where TABLE_SCHEMA = 'magasinmanagement'" + 
				"and TABLE_NAME = 'paiement'";
		Statement statement=null;
		try {
			statement = dbAccess.getConnection().createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ResultSet r=statement.executeQuery(sql);
			while(r.next()) {
				return r.getLong("AUTO_INCREMENT");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

}
