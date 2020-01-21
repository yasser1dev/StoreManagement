package com.GestionPaiement;

import java.sql.Date;

public class Traite implements Comparable<Traite>{
	private long id;
	private Date datePrevu;
	private Date dateEffet;
	private double montantAPayer;
	private boolean etat=false;
	private Cheque cheque=null;
	
	public Traite(long id, Date datePrevu,double montantAPayer) {
		this.id = id;
		this.datePrevu = datePrevu;
		this.montantAPayer = montantAPayer;
	}
	public long getId() {
		return id;
	}
	
	public Date getDateEffet() {
		return dateEffet;
	}
	public void setDateEffet(Date dateEffet) {
		this.dateEffet = dateEffet;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDatePrevu() {
		return datePrevu;
	}
	public void setDatePrevu(Date datePrevu) {
		this.datePrevu = datePrevu;
	}
	public double getMontantAPayer() {
		return montantAPayer;
	}
	public void setMontantAPayer(double montantAPayer) {
		this.montantAPayer = montantAPayer;
	}
	public boolean isEtat() {
		return etat;
	}
	public void setEtat(boolean etat) {
		this.etat = etat;
	}
	
	public Cheque getCheque() {
		return cheque;
	}
	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}
	@Override
	public int compareTo(Traite t) {
		// TODO Auto-generated method stub
		
		return datePrevu.compareTo(t.datePrevu);
	}
	
	
	
	
}
