package com.GestionPaiement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.DAO.AbstractDAO;
import com.DAO.DAO;
import com.GestionClient.Ville;

public class IBankDAOIMPL extends AbstractDAO implements DAO<Bank>{

	@Override
	public Bank find(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(Bank p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean update(Bank p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Bank> findAll() {
		List<Bank> l=new ArrayList();
		String sql="select * from Bank";
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
				l.add(new Bank(rslt.getLong("id"),rslt.getString("nom")));
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
	public List<Bank> finDAll(String Key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long maxId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
