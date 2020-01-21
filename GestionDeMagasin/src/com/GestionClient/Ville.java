package com.GestionClient;

import java.io.Serializable;

public class Ville implements Serializable{
	private long id;
	private String nomVille;
	Ville(long id,String nomVille){
		this.id=id;
		this.nomVille=nomVille;
		
	}
	public long getId() {
		return id;
	}

	 
	public String getNomVille() {
		return nomVille;
	}

	public void setNomVille(String nomVille) {
		this.nomVille = nomVille;
	}
	

}
