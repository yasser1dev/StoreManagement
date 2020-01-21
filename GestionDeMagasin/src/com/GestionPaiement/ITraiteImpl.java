package com.GestionPaiement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.DAO.AbstractDAO;
import com.DAO.DAO;
import com.GestionClient.Ville;

public class ITraiteImpl extends AbstractDAO{

	

	public void createTraite(Traite p,String type,Long idPaiement) {
		String sqlCheque="insert into traite(datePrevu,dateEffet,type,montantApayer,idPaiement,idCheque,etat) values(?,?,?,?,?,?,?)";
		
		PreparedStatement statement;
		try {
			statement=dbAccess.getConnection().prepareStatement(sqlCheque);
			statement.setDate(1,p.getDatePrevu());
			statement.setDate(2,p.getDateEffet());
			statement.setString(3,type);
			statement.setDouble(4,p.getMontantAPayer());
			statement.setLong(5,idPaiement);
			if(type=="cheque") {
				IChequeDAOIMPL chDAO=new IChequeDAOIMPL();
				if(chDAO.findByPaiement(idPaiement)!=null) {
					statement.setLong(6,chDAO.findByPaiement(idPaiement).getId());
					statement.setBoolean(7,p.isEtat());
				}
				else {
					statement.setString(6,null);
					statement.setBoolean(7,p.isEtat());
				}

			}
			statement.setString(6,null);

			statement.setBoolean(7,p.isEtat());

			statement.executeUpdate();
			System.out.println("Traite added");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
	}
	
	public List<Traite> findByPaiement(Long id){
		List<Traite> l=new ArrayList();
		String sql="Select * from traite where idPaiement=?";
		PreparedStatement statement=null;
		try {
			statement=dbAccess.getConnection().prepareStatement(sql);
			statement.setLong(1,id);
		}catch (Exception e) {
			// TODO: handle exception
		}
		try {
			IChequeDAOIMPL chDAO=new IChequeDAOIMPL();
			ResultSet rslt=statement.executeQuery();
			while(rslt.next()) {
				Traite t=new Traite(rslt.getLong("id"),rslt.getDate("datePrevu"), rslt.getDouble("montantAPayer"));
				t.setDateEffet(rslt.getDate("dateEffet"));
				t.setEtat(rslt.getBoolean("etat"));
				if((String.valueOf(rslt.getLong("idCheque"))!=null)) t.setCheque(chDAO.find(rslt.getLong("idCheque")));
				l.add(t);
			}
			if(rslt!=null) return l;
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public boolean update(Traite t) {
		int etatInt=t.isEtat() ? 1:0;
		String sql="Update traite set dateEffet='"+t.getDateEffet()+"',idCheque='"+t.getCheque().getId()+"',etat='"+etatInt+
				"' where id='"+t.getId()+"'";
		
		try {
			Statement statement=dbAccess.getConnection().createStatement();
			statement.executeUpdate(sql);
			System.out.println("Traite updated");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Traite update Failed");
			e.printStackTrace();
			return false;

		}
	}
	
	public long maxId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
