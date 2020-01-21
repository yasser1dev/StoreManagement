package com.SocketPaiement;

import java.io.Serializable;

import com.GestionClient.Client;

public class PaiementData implements Serializable{
	Client c;
	String numCard;
	String codeCard;
	Double montant;
	public PaiementData(Client c, String numCard, String codeCard, Double montant) {
		this.c = c;
		this.numCard = numCard;
		this.codeCard = codeCard;
		this.montant = montant;
	}
	
	
}
