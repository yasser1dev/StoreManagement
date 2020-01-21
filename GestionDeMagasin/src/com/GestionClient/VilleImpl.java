package com.GestionClient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.DAO.AbstractDAO;
import com.DAO.DAO;
import com.GestionProduit.Categorie;

public class VilleImpl extends AbstractDAO implements DAO<Ville>{

	@Override
	public Ville find(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(Ville p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean update(Ville p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Ville> findAll() {
		List<Ville> l=new ArrayList();
		String sql="select * from Ville";
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
				l.add(new Ville(rslt.getLong("id"),rslt.getString("nomVille")));
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
	public List<Ville> finDAll(String Key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long maxId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
