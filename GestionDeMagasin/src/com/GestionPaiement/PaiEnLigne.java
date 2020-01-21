package com.GestionPaiement;

import java.sql.Date;

import com.GestionVente.Vente;

public class PaiEnLigne extends Paiement{
	private String numCard;
	private String codeCard;
	
	
	public PaiEnLigne(long id, Date dateEffet, String numCard, double montant, String codeCard,Vente v) {
		super(id, dateEffet,montant,v);
		this.numCard = numCard;
		this.codeCard = codeCard;
	}
	
	public String getNumCard() {
		return numCard;
	}
	public void setNumCard(String numCard) {
		this.numCard = numCard;
	}
	public String getCodeCard() {
		return codeCard;
	}
	public void setCodeCard(String codeCard) {
		this.codeCard = codeCard;
	}
	
	
}
