package com.GestionClient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.DAO.AbstractDAO;
import com.GestionProduit.Categorie;
import com.GestionProduit.Produit;

public class IClientDAOIMPL extends AbstractDAO implements IClientDAO{
    
	public IClientDAOIMPL(){
		super();
 	}

	@Override
	public Client find(long id) {
		String sql="SELECT * FROM `client` INNER join ville WHERE client.id_ville=ville.id and client.id=?";
		PreparedStatement statement;
		ResultSet r;
		try {
			statement=dbAccess.getConnection().prepareStatement(sql);
			statement.setLong(1,id);
			r=statement.executeQuery();
			while(r.next()) return new Client(r.getLong("id"),r.getString("nom"),r.getString("prenom"),r.getString("tel"),r.getString("email"),
			r.getInt("numMaison"),r.getString("rue"),new Ville(r.getLong("id_ville"),r.getString("nomVille")));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void create(Client c) {
		String sql="insert into client(nom,prenom,tel,email,numMaison,rue,id_ville) values(?,?,?,?,?,?,?)";
		PreparedStatement statement;
		try {
			statement=dbAccess.getConnection().prepareStatement(sql);
			statement.setString(1,c.getNom());
			statement.setString(2,c.getPrenom());
			statement.setString(3, c.getTel());
			statement.setString(4,c.getEmail());
			statement.setInt(5,c.getNumMaison());
			statement.setString(6,c.getRue());
			statement.setLong(7,c.getVille().getId());

			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void delete(long id) {
		String sql="delete from client where id='"+id+"'";
		
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
	public boolean update(Client c) {
	String sql="Update client set nom='"+c.getNom()+"',prenom='"+c.getPrenom()+"',tel='"+c.getTel()+"',email='"
			+c.getEmail() +"',numMaison='"+c.getNumMaison()+"',rue='"+c.getRue()+"',id_ville='"+c.getVille().getId()+"' where id='"+c.getId()+"'";
		
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
	public List<Client> findAll() {
		List<Client> l=new ArrayList();
		String sql="SELECT * FROM `client` INNER join ville WHERE client.id_ville=ville.id";
		Statement statement=null;
		try {
		statement=dbAccess.getConnection().createStatement();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ResultSet r=statement.executeQuery(sql);
			while(r.next()) {
				l.add(new Client(r.getLong("id"),r.getString("nom"),r.getString("prenom"),r.getString("tel"),r.getString("email"),
						r.getInt("numMaison"),r.getString("rue"),new Ville(r.getLong("id_ville"),r.getString("nomVille"))));
			}
			if(r!=null) return l;
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Client> finDAll(String Key) {
		List<Client> l=new ArrayList();
		String sql="SELECT * FROM `client` INNER join ville WHERE client.id_ville=ville.id and nom like %'"+Key+"'";
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
				l.add(new Client(r.getLong("id"),r.getString("nom"),r.getString("prenom"),r.getString("tel"),r.getString("email"),
						r.getInt("numMaison"),r.getString("rue"),new Ville(r.getLong("id_ville"),r.getString("nomVille"))));
			}
			if(r!=null) return l;
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long maxId(){
		String sql= "select `AUTO_INCREMENT` from information_schema.TABLES where TABLE_SCHEMA = 'magasinmanagement'" + 
				"and TABLE_NAME = 'client'";
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
