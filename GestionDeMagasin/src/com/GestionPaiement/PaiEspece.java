package com.GestionPaiement;

import java.sql.Date;

import com.GestionVente.Vente;

public class PaiEspece extends Paiement{
	//private Espece espece;
	public PaiEspece(long id, Date dateEffet,double montant,Vente v) {
		super(id, dateEffet,montant,v);
 	}
	 
	
	
	
}
