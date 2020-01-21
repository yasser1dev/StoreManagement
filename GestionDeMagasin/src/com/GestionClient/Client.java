package com.GestionClient;

import java.io.Serializable;

public class Client implements Serializable{
	private long id;
	private String nom;
	private String prenom;
	private String tel;
	private String email;
	private int numMaison;
	private String rue;
	private Ville ville;
	

	public Client(long id, String nom, String prenom, String tel, String email, int numMaison, String rue,
			Ville ville) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.tel = tel;
		this.email = email;
		this.numMaison = numMaison;
		this.rue = rue;
		this.ville = ville;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getTel() {
		return tel;
	}


	public void setTel(String tel) {
		this.tel = tel;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public int getNumMaison() {
		return numMaison;
	}


	public void setNumMaison(int numMaison) {
		this.numMaison = numMaison;
	}


	public String getRue() {
		return rue;
	}


	public void setRue(String rue) {
		this.rue = rue;
	}


	public Ville getVille() {
		return ville;
	}


	public void setVille(Ville ville) {
		this.ville = ville;
	}


	public String toString(){
		return this.getNom()+" "+this.getPrenom();
				  
	}
	

}
