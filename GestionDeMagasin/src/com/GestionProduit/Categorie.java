package com.GestionProduit;

import java.util.ArrayList;
import java.util.List;

public class Categorie {
	private  long id=7;
	private String title;
	private List<Produit> lp=new ArrayList();
	
	public Categorie(long id, String title) {
		this.id = id;
		this.title = title;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Produit> getLp() {
		return lp;
	}
	public void setLp(List<Produit> lp) {
		this.lp = lp;
	}
	

}
