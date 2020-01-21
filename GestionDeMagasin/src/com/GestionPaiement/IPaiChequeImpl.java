package com.GestionPaiement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.DAO.AbstractDAO;
import com.DAO.DAO;
import com.GestionClient.IClientDAOIMPL;
import com.GestionVente.VenteDAOIMPL;

public class IPaiChequeImpl extends AbstractDAO implements DAO<PaiCheque>{
	VenteDAOIMPL vDAO=new VenteDAOIMPL();
	IClientDAOIMPL cDAO=new IClientDAOIMPL();
	IChequeDAOIMPL chDAO;
	public final String type="cheque";
	@Override
	public PaiCheque find(long id) {
		String sql="SELECT * FROM `paiement` where paiement.id=? and paiement.type=?";
		PreparedStatement statement;
		ResultSet r;
		try {
			statement=dbAccess.getConnection().prepareStatement(sql);
			statement.setLong(1,id);
			statement.setString(2,type);
			r=statement.executeQuery();
			while(r.next()) {
				PaiCheque pCheque=new PaiCheque(r.getLong("id"),r.getDate("dateEffet"),null,vDAO.find(r.getLong("idVente")));
				chDAO=new IChequeDAOIMPL();
				Cheque cheque=chDAO.find(r.getLong("idCheque"));
				pCheque.setCheque(cheque);
				pCheque.setVente(vDAO.find(r.getLong("idVente")));
				return pCheque;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}

	@Override
	public void create(PaiCheque p) {
		// TODO Auto-generated method stub
		//penser à créer paimentDAOimpl
		String sql="insert into paiement(id,dateEffet,etat,type,idVente,montant,idCheque) values(?,?,?,?,?,?,?)";
		chDAO=new IChequeDAOIMPL();
		chDAO.create(p.getCheque());
		PreparedStatement statement;
		try {
			statement=dbAccess.getConnection().prepareStatement(sql);
			statement.setLong(1,p.getId());
			statement.setDate(2,p.getDateEffet());
			statement.setBoolean(3,true);
			statement.setString(4,type);
			statement.setLong(5,p.getVente().getId());
			statement.setDouble(6,p.getCheque().getMontant());
			statement.setLong(7,p.getCheque().getId());


			statement.executeUpdate();
			System.out.println("paiement added");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
		
		
		
		
		
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		String sql="delete from paiement where id='"+id+"' and type="+type+"";
		
		try {
			Statement statement=dbAccess.getConnection().createStatement();
			statement.executeUpdate(sql);
			IChequeDAOIMPL chDAO=new IChequeDAOIMPL();
			chDAO.delete(id);
			System.out.println("query executed");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("query Failed");
			e.printStackTrace();
		}				
		
	}
		
	

	@Override
	public boolean update(PaiCheque p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<PaiCheque> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PaiCheque> finDAll(String Key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long maxId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
