package com.GestionPaiement;

import java.sql.Date;

import com.GestionVente.Vente;

public class PaiCheque extends Paiement{
	private Cheque cheque;
	
	public PaiCheque(long id, Date dateEffet,Cheque cheque,Vente v) {
		super(id, dateEffet,v.getTotale(),v);
		this.cheque=cheque;
		// TODO Auto-generated constructor stub
	}

	public Cheque getCheque() {
		return cheque;
	}

	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}
	

	
}
