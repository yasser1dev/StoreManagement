package com.GestionVente;

import java.util.HashSet;
import java.util.Set;

import com.GestionProduit.Produit;

public class testSet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vente v=new Vente(1,null,0,null);
		Set<LigneCommande> lc=new HashSet<LigneCommande>();
		
		Produit p1=new Produit(1,"a",100,null);
		Produit p2=new Produit(2,"b",200,null);
		Produit p3=new Produit(1,"a",100,null);

		lc.add(new LigneCommande(1,2,p1,v));
		lc.add(new LigneCommande(2,3,p1,v));
		lc.add(new LigneCommande(3,3,p1,v));

		
		v.setLignesCmd(lc);
		
		for(LigneCommande l:v.getLignesCmd()) {
			System.out.println("ID : "+l.getId()+" qte: "+l.getQte()+" ssT : "+l.getSousTotale());
		}


	}

}
