package com.GestionPaiement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.DAO.AbstractDAO;
import com.DAO.DAO;
import com.GestionClient.IClientDAOIMPL;

public class IChequeDAOIMPL extends AbstractDAO implements DAO<Cheque>{
	IClientDAOIMPL cDAO=new IClientDAOIMPL();
	IPaiChequeImpl pcDAO=null;
	@Override
	public Cheque find(long id) {
		String sql="SELECT * FROM `cheque` inner join bank where cheque.idBank=bank.id and cheque.id=?";
		PreparedStatement statement;
		ResultSet r;
		try {
			statement=dbAccess.getConnection().prepareStatement(sql);
			statement.setLong(1,id);
			r=statement.executeQuery();
			while(r.next()) {
				Cheque pCheque=new Cheque(r.getLong("id"),r.getString("num"),r.getDouble("montant"),cDAO.find(r.getLong("idClient")),new Bank(r.getLong("idBank"),r.getString("nom")),null);
				pcDAO=new IPaiChequeImpl();
				//PaiCheque p=pcDAO.find(r.getLong("idPaiement"));
				//pCheque.setP(p);
				return pCheque;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void create(Cheque c) {
				//penser à créer paimentDAOimpl
				String sqlCheque="insert into cheque(id,idBank,idClient,montant,idPaiement,num) values(?,?,?,?,?,?)";

				PreparedStatement statement;
				try {
					statement=dbAccess.getConnection().prepareStatement(sqlCheque);
					statement.setLong(1,c.getId());
					statement.setLong(2,c.getBank().getId());
					statement.setLong(3,c.getClient().getId());
					statement.setDouble(4,c.getMontant());
					statement.setLong(5,c.getP().getId());
					statement.setString(6,c.getNum());
					statement.executeUpdate();
					System.out.println("Cheque added");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
		
	}

	@Override
	public void delete(long id) {
		String sql="delete from cheque where idPaiement='"+id+"'";
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
	public boolean update(Cheque p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Cheque> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cheque> finDAll(String Key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long maxId() {
		String sql= "select `AUTO_INCREMENT` from information_schema.TABLES where TABLE_SCHEMA = 'magasinmanagement'" + 
				"and TABLE_NAME = 'cheque'";
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

	public Cheque findByPaiement(Long idPaiement) {
		String sql="SELECT * FROM `cheque` inner join bank where cheque.idBank=bank.id and cheque.idPaiement=?";
		PreparedStatement statement;
		ResultSet r;
		try {
			statement=dbAccess.getConnection().prepareStatement(sql);
			statement.setLong(1,idPaiement);
			r=statement.executeQuery();
			while(r.next()) {
				Cheque pCheque=new Cheque(r.getLong("id"),r.getString("num"),r.getDouble("montant"),cDAO.find(r.getLong("idClient")),new Bank(r.getLong("idBank"),r.getString("nom")),null);
				IPaiChequeImpl pcDAO=new IPaiChequeImpl();
				pCheque.setP(pcDAO.find(r.getLong("idPaiement")));
				return pCheque;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
