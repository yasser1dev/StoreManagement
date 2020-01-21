package com.GestionPaiement;

import java.sql.Date;

import com.GestionVente.Vente;

public class Paiement {
	private long id;
	private Date dateEffet;
	private boolean etat=false;
	private double montant;
	private Vente vente;
	private static double TVA=0.20;
	
	public Paiement(long id, Date dateEffet,double montant,Vente v) {
		this.id = id;
		this.dateEffet = dateEffet;
		this.montant=montant;
		vente=v;
	}
	
	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDateEffet() {
		return dateEffet;
	}
	public void setDateEffet(Date dateEffet) {
		this.dateEffet = dateEffet;
	}
	public boolean isEtat() {
		return etat;
	}
	public void setEtat(boolean etat) {
		this.etat = etat;
	}
	public Vente getVente() {
		return vente;
	}
	public void setVente(Vente vente) {
		this.vente = vente;
	}
	
	
	
}
