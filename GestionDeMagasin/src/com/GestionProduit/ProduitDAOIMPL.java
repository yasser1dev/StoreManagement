package com.GestionProduit;
import com.DAO.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ProduitDAOIMPL extends AbstractDAO implements IProduitDAO{
	
	public void create(Produit p) {
		//select * from produits inner JOIN categorie where produits.id_cat=categorie.id

		String sql="insert into Produits(designation,prix,id_cat) values(?,?,?)";
		PreparedStatement statement;
		try {
			statement=dbAccess.getConnection().prepareStatement(sql);
			statement.setString(1,p.getDesignation());
			statement.setDouble(2,p.getPrix());
			statement.setLong(3, p.getCategorie().getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public Produit find(long id) {
		String sql="SELECT * FROM `produits` INNER join categorie WHERE produits.id_cat=categorie.id and produits.id=?";
		PreparedStatement statement;
		ResultSet r;
		try {
			statement=dbAccess.getConnection().prepareStatement(sql);
			statement.setLong(1,id);
			r=statement.executeQuery();
			while(r.next()) return new Produit(r.getLong("id"),r.getString("designation"),r.getDouble("prix"),
					new Categorie(r.getLong("id_cat"),r.getString("title")));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public void delete(long id) {
		String sql="delete from Produits where id='"+id+"'";
		
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
	public boolean update(Produit p) {
		String sql="Update Produits set designation='"+p.getDesignation()+"',prix='"+p.getPrix()+"',id_cat='"+p.getCategorie().getId()+"' where id='"+p.getId()+"'";
		
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
	
	public List<Produit> findAll(){
		List<Produit> l=new ArrayList();
		String sql="SELECT * FROM `produits` INNER join categorie WHERE produits.id_cat=categorie.id";
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
				l.add(new Produit(rslt.getLong("id"),rslt.getString("designation"),rslt.getDouble("prix"),
						new Categorie(rslt.getLong("id_cat"),rslt.getString("title"))));
			}
			if(rslt!=null) return l;
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public List<Produit> finDAll(String key){
		List<Produit> l=new ArrayList();
		String sql="SELECT * FROM `produits` INNER join categorie WHERE produits.id_cat=categorie.id and designation like '%"+key+"%'";
		
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
				l.add(new Produit(rslt.getLong("id"),rslt.getString("designation"),rslt.getDouble("prix"),
						new Categorie(rslt.getLong("id_cat"),rslt.getString("title"))));
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
				"and TABLE_NAME = 'produits'";
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
