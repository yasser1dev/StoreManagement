package com.GestionVente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.DAO.AbstractDAO;
import com.GestionClient.Client;
import com.GestionClient.IClientDAOIMPL;
import com.GestionProduit.Categorie;
import com.GestionProduit.Produit;

public class VenteDAOIMPL extends AbstractDAO implements VenteDAO{
	IClientDAOIMPL cDAO;
	LigneCmdDAOIMPL lcDAO;

	@Override
	public Vente find(long id) {
		
		String sql="SELECT * FROM `vente` where id=?";
		PreparedStatement statement;
		ResultSet r;
		try {
			statement=dbAccess.getConnection().prepareStatement(sql);
			statement.setLong(1,id);
			r=statement.executeQuery();
			
			while(r.next()) {
				lcDAO=new LigneCmdDAOIMPL();
				cDAO=new IClientDAOIMPL();
				Client c=cDAO.find(r.getLong("idClient"));
				Vente v=new Vente(r.getLong("id"),r.getDate("date"),r.getDouble("totale"),c);
				Set<LigneCommande> lc=lcDAO.findByVente(v);
				v.setLignesCmd(lc);
				return v;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void create(Vente v) {
		String sql="insert into vente(id,date,totale,idClient) values(?,?,?,?)";
		PreparedStatement statement;
		try {
			statement=dbAccess.getConnection().prepareStatement(sql);
			statement.setLong(1,v.getId());
			statement.setDate(2, v.getDate());
			statement.setDouble(3,v.getTotale());
			statement.setLong(4,v.getClient().getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(long id) {
		String sql="delete from vente where id='"+id+"'";
		lcDAO.deleteByVente(id);
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
	public boolean update(Vente v) {
		String sql="Update vente set date='"+v.getDate()+"',totale='"+v.getTotale()+"',idClient='"+v.getClient().getId()+"'where id='"+v.getId()+"'";
		
		try {
			Statement statement=dbAccess.getConnection().createStatement();
			statement.executeUpdate(sql);
			System.out.println("query executed");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("query Failed");
			e.printStackTrace();
			return false;

		}
	}

	@Override
	public List<Vente> findAll() {
		List<Vente> l=new ArrayList();
		String sql="SELECT * FROM `vente` ";
		Statement statement=null;
		try {
			statement = dbAccess.getConnection().createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ResultSet rslt=statement.executeQuery(sql);
			while(rslt.next()) {
				lcDAO=new LigneCmdDAOIMPL();
				cDAO=new IClientDAOIMPL();
				Client c=cDAO.find(rslt.getLong("idClient"));
				Vente v=new Vente(rslt.getLong("id"),rslt.getDate("date"),rslt.getDouble("totale"),c);
				Set<LigneCommande> lc=lcDAO.findByVente(v);
				v.setLignesCmd(lc);
				l.add(v);
			}
			if(rslt!=null) return l;
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.getCause().printStackTrace();;
		}
		return null;
	}

	@Override
	public List<Vente> finDAll(String Key) {
		lcDAO=new LigneCmdDAOIMPL();
		cDAO=new IClientDAOIMPL();
		List<Vente> l=new ArrayList();
		String sql="SELECT * FROM `vente` date like '%"+Key+"%'";
		
		Statement statement=null;
		try {
			statement = dbAccess.getConnection().createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ResultSet rslt=statement.executeQuery(sql);
			while(rslt.next()) {
				lcDAO=new LigneCmdDAOIMPL();
				cDAO=new IClientDAOIMPL();
				Client c=cDAO.find(rslt.getLong("idClient"));
				Vente v=new Vente(rslt.getLong("id"),rslt.getDate("date"),rslt.getDouble("totale"),c);
				Set<LigneCommande> lc=lcDAO.findByVente(v);
				v.setLignesCmd(lc);
				
				l.add(v);
			}
			if(rslt!=null) return l;
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long maxId() {
		String sql="select `AUTO_INCREMENT` from information_schema.TABLES where TABLE_SCHEMA = 'magasinmanagement'" + 
				"and TABLE_NAME = 'vente'";
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
