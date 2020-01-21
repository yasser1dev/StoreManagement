package com.GestionPaiement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.DAO.AbstractDAO;
import com.DAO.DAO;
import com.GestionVente.VenteDAOIMPL;

public class IPaiTraiteImpl extends AbstractDAO implements DAO<PaiTraite>{
	VenteDAOIMPL vDAO=new VenteDAOIMPL();
	private final String type="traite";
	@Override
	public PaiTraite find(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(PaiTraite p) {
		String sql="insert into paiement(id,dateEffet,etat,type,idVente,montant) values(?,?,?,?,?,?)";
		IChequeDAOIMPL chDAO=new IChequeDAOIMPL();
		if(p.getCheque()!=null) {
			chDAO.create(p.getCheque());
			for(Traite t:p.getListeTraite()){
				ITraiteImpl tDAO=new ITraiteImpl();
				tDAO.createTraite(t,"cheque",p.getId());
			}
			
		}
		else {
			ITraiteImpl tDAO=new ITraiteImpl();
			List<Traite> l=p.getListeTraite();
			tDAO.createTraite(l.get(0),"espece",p.getId());
			for(Traite t:l.subList(1,p.getListeTraite().size())){
				tDAO.createTraite(t,"cheque",p.getId());
			}
		}
		PreparedStatement statement;
		try {
			statement=dbAccess.getConnection().prepareStatement(sql);
			statement.setLong(1,p.getId());
			statement.setDate(2,p.getDateEffet());
			statement.setBoolean(3,false);
			statement.setString(4,type);
			statement.setLong(5,p.getVente().getId());
			statement.setDouble(6,p.getMontant());


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
		
	}

	@Override
	public boolean update(PaiTraite p) {
		ITraiteImpl tDAO=new ITraiteImpl();
		System.out.println(p);
		List<Traite> list=tDAO.findByPaiement(p.getId());
		Double totalePayer=0.0;
		for(Traite t:list) {
			if(t.isEtat())  totalePayer+=t.getMontantAPayer();
		}
		if(totalePayer>=p.getMontant()) {
			p.setEtat(true);
			int etatInt=p.isEtat() ? 1:0;
			String sql="Update paiement set etat='"+etatInt+
					"' where id='"+p.getId()+"'";
			
			try {
				Statement statement=dbAccess.getConnection().createStatement();
				statement.executeUpdate(sql);
				System.out.println("Paiement Traite updated");
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("¨Paiement Traite update Failed");
				e.printStackTrace();
				return false;
	
			}
		}
		return false;
	}

	@Override
	public List<PaiTraite> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PaiTraite> finDAll(String Key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long maxId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
