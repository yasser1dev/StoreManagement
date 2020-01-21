package com.GestionVente;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.GestionClient.Client;

public class Vente {
	private long id;
	private Date date;
	private Set<LigneCommande> lignesCmd=new HashSet<LigneCommande>();
	private Client client;
	private double totale;
	
	
	public Vente(long id, Date date,double totale/*, Set<LigneCommande> lignesCmd*/, Client client) {
		this.id = id;
		this.date = date;
		//this.lignesCmd = lignesCmd;
		this.totale=totale;
		this.client = client;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Set<LigneCommande> getLignesCmd() {
		return lignesCmd;
	}
	public void setLignesCmd(Set<LigneCommande> lignesCmd) {
		this.lignesCmd = lignesCmd;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public double getTotale() {
		return totale;
	}
	public void setTotale(double totale) {
		this.totale = totale;
	}
	
	public double calculeTotale(){
		double totale=0;
		for(LigneCommande lcs:this.lignesCmd) {
			totale+=lcs.getSousTotale();
		}
		return totale;
		
	}
	
	
	
}
