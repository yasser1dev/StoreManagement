package com.GestionVente;

import com.GestionProduit.Produit;

public class LigneCommande {
	private long id;
	private int qte;
	private Produit produit;
	private Vente vente;
	private double sousTotale;
	public LigneCommande(long id, int qte, Produit produit, Vente vente) {
		this.id = id;
		this.qte = qte;
		this.sousTotale=produit.getPrix()*qte;
		this.produit = produit;
		this.vente = vente;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getQte() {
		return qte;
	}
	public void setQte(int qte) {
		this.qte = qte;
	}
	public Produit getProduit() {
		return produit;
	}
	public void setProduit(Produit produit) {
		this.produit = produit;
	}
	public Vente getVente() {
		return vente;
	}
	public void setVente(Vente vente) {
		this.vente = vente;
	}
	public double getSousTotale() {
		return sousTotale;
	}
	public void setSousTotale(double sousTotale) {
		this.sousTotale = sousTotale;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produit == null) ? 0 : produit.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LigneCommande other = (LigneCommande) obj;
	//	if(id==other.getId()) return true;
		if (produit == null) {
			if (other.produit != null)
				return false;
		} else if (!produit.equals(other.produit))
			return false;
		
		other.qte+=this.qte;
		other.calculeSousTotale();
		return true;
	}
	public void calculeSousTotale() {
		 sousTotale=qte*produit.getPrix();
	}
	
	
}
