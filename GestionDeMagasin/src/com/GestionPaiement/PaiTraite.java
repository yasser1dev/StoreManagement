package com.GestionPaiement;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.GestionVente.Vente;

public class PaiTraite extends Paiement{
	private static int maxTraite=10;
	private int nbTraite;
	private Cheque cheque=null;
	
	
	private List<Traite> listeTraite=new ArrayList();
	public PaiTraite(long id, Date dateEffet,List<Traite> listeTraite,Vente v) {
		super(id, dateEffet,v.getTotale(),v);
		this.listeTraite = listeTraite; 
	}
	
	public Cheque getCheque() {
		return cheque;
	}

	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}

	public int getNbTraite() {
		return nbTraite;
	}

	public void setNbTraite(int nbTraite) {
		this.nbTraite = nbTraite;
	}

	public int getMaxTraite() {
		return maxTraite;
	}
	public void setMaxTraite(int maxTraite) {
		this.maxTraite = maxTraite;
	}
	public List<Traite> getListeTraite() {
		return listeTraite;
	}
	public void setListeTraite(List<Traite> listeTraite) {
		this.listeTraite = listeTraite;
	}
	

	public double calculeTotale() {
		double somme=0;
		for(Traite t:listeTraite) {
			if(t.isEtat()) somme+=t.getMontantAPayer();
		}
		return somme;
	}
	
}
