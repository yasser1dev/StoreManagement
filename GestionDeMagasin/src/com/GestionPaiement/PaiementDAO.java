package com.GestionPaiement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.DAO.AbstractDAO;
import com.DAO.DAO;
import com.GestionVente.VenteDAOIMPL;

public class PaiementDAO extends AbstractDAO implements DAO<Paiement>{
	VenteDAOIMPL vDAO=new VenteDAOIMPL();
	IChequeDAOIMPL chDAO=new IChequeDAOIMPL();
	@Override
	public Paiement find(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	public Paiement findByVente(long id) {
		String sql="SELECT * FROM `paiement` where idVente=?";
		PreparedStatement statement;
		ResultSet r;
		try {
			statement=dbAccess.getConnection().prepareStatement(sql);
			statement.setLong(1,id);
			r=statement.executeQuery();
			while(r.next()) {
				switch (r.getString("type")) {
				case "espece":
					Paiement pesp=new PaiEspece(r.getLong("id"),r.getDate("dateEffet"),r.getDouble("montant"),vDAO.find(r.getLong("idVente")));
					((Paiement)pesp).setEtat(r.getBoolean("etat"));
					return pesp;
				case "EnLigne":
					Paiement pEL=new PaiEnLigne(r.getLong("id"),r.getDate("dateEffet"),r.getString("numCard"),
							r.getDouble("montant"),r.getString("codeCard"),vDAO.find(r.getLong("idVente")));
					pEL.setVente(vDAO.find(r.getLong("idVente")));
					((Paiement)pEL).setEtat(r.getBoolean("etat"));

					return pEL;
				case "cheque":
					Paiement pCheque=new PaiCheque(r.getLong("id"),r.getDate("dateEffet"),chDAO.find(r.getLong("idCheque")),vDAO.find(r.getLong("idVente")));
					pCheque.setVente(vDAO.find(r.getLong("idVente")));
					((Paiement)pCheque).setEtat(r.getBoolean("etat"));
					return pCheque;
				case "traite":
					Paiement pTraite=new PaiTraite(r.getLong("id"), r.getDate("dateEffet"),null, vDAO.find(r.getLong("idVente")));
					ITraiteImpl tDAO=new ITraiteImpl();
					List<Traite> listTraite=tDAO.findByPaiement(r.getLong("id"));
					((PaiTraite)pTraite).setListeTraite(listTraite);
					((Paiement)pTraite).setEtat(r.getBoolean("etat"));

					return pTraite;
				default:
					break;
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}

	@Override
	public void create(Paiement p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean update(Paiement p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Paiement> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Paiement> finDAll(String Key) {
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
