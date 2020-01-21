package com.GestionPaiement;

import com.GestionClient.Client;

public class Cheque {
	private long id;
	private String num;
	private double montant;
	private Client client;
	private Bank bank;
	private Paiement p;
	
	
	public Cheque(long id, String num, double montant, Client client, Bank bank,Paiement p) {
		this.id = id;
		this.num = num;
		this.montant = montant;
		this.client = client;
		this.bank = bank;
		this.p=p;
	}
	
	public Paiement getP() {
		return p;
	}

	public void setP(PaiCheque p) {
		this.p = p;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
	
	

}
