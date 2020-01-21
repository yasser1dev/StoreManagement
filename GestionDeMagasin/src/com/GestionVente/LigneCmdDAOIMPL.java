package com.GestionVente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.DAO.AbstractDAO;
import com.DAO.DAO;
import com.GestionClient.IClientDAOIMPL;
import com.GestionProduit.Categorie;
import com.GestionProduit.Produit;
import com.GestionProduit.ProduitDAOIMPL;

public class LigneCmdDAOIMPL extends AbstractDAO implements DAO<LigneCommande>{
	
	VenteDAOIMPL vDAO;
	ProduitDAOIMPL pDAO;

	@Override
	public LigneCommande find(long id) {
		vDAO=new VenteDAOIMPL();
		pDAO=new ProduitDAOIMPL();

		String sql="SELECT * FROM `lignecommande` INNER join vente WHERE lignecommande.idVente=vente.id and lignecommande.id=?";
		PreparedStatement statement;
		ResultSet r;
		try {
			statement=dbAccess.getConnection().prepareStatement(sql);
			statement.setLong(1,id);
			r=statement.executeQuery();
			while(r.next()) return new LigneCommande(r.getLong("id"),r.getInt("qte"),pDAO.find(r.getLong("idProduit")),
					vDAO.find(r.getLong("idVente")));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public Set<LigneCommande> findByVente(Vente v) {
		String sql="SELECT * FROM `lignecommande` WHERE idVente=?";
		PreparedStatement statement;
		ResultSet r;
		Set<LigneCommande> listLC=new HashSet<LigneCommande>();
		try {
			statement=dbAccess.getConnection().prepareStatement(sql);
			statement.setLong(1,v.getId());
			r=statement.executeQuery();
			while(r.next()) {
				vDAO=new VenteDAOIMPL();
				pDAO=new ProduitDAOIMPL();
				Produit p=pDAO.find(r.getLong("idProduit"));
				listLC.add(new LigneCommande(r.getLong("id"),r.getInt("qte"),p,v));
			}
			return listLC;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void create(LigneCommande lc) {
		String sql="insert into lignecommande(id,qte,sousTotale,idVente,idProduit) values(?,?,?,?,?)";
		PreparedStatement statement;
		try {
			statement=dbAccess.getConnection().prepareStatement(sql);
			statement.setLong(1,lc.getId());
			statement.setInt(2,lc.getQte());
			statement.setDouble(3, lc.getSousTotale());
			statement.setLong(4, lc.getVente().getId());
			statement.setLong(5, lc.getProduit().getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void delete(long id) {
	String sql="delete from lignecommande where id='"+id+"'";
		
		try {
			Statement statement=dbAccess.getConnection().createStatement();
			statement.executeUpdate(sql);
			System.out.println("item  deleted");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("query Failed");
			e.printStackTrace();
		}		
	}
	
	public void deleteByVente(long id) {
		String sql="delete from lignecommande where idVente='"+id+"'";
			
			try {
				Statement statement=dbAccess.getConnection().createStatement();
				statement.executeUpdate(sql);
				System.out.println("item  deleted");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("query Failed");
				e.printStackTrace();
			}		
		}

	@Override
	public boolean update(LigneCommande lc) {
		String sql="Update lignecommande set qte='"+lc.getQte()+"',sousTotale='"+lc.getSousTotale()+"',idProduit='"+lc.getProduit().getId()+"',idVente='"+lc.getVente().getId()+
				"' where id='"+lc.getId()+"'";
		
		try {
			Statement statement=dbAccess.getConnection().createStatement();
			statement.executeUpdate(sql);
			System.out.println("item updated");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("query Failed");
			e.printStackTrace();
			return false;

		}
		
	}

	@Override
	public List<LigneCommande> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LigneCommande> finDAll(String Key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long maxId() {
		String sql="select `AUTO_INCREMENT` from information_schema.TABLES where TABLE_SCHEMA = 'magasinmanagement'" + 
				"and TABLE_NAME = 'lignecommande'";
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
