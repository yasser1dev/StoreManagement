package com.GestionProduit;

public class Produit {
		private long id;
		private String designation;
		private double prix;
		private Categorie categorie;
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getDesignation() {
			return designation;
		}
		public void setDesignation(String designation) {
			this.designation = designation;
		}
		public double getPrix() {
			return prix;
		}
		public void setPrix(double prix) {
			this.prix = prix;
		}
		
		public Categorie getCategorie() {
			return categorie;
		}
		public void setCategorie(Categorie categorie) {
			this.categorie = categorie;
		}
		public Produit(long id, String designation, double prix,Categorie categorie) {
			this.id = id;
			this.designation = designation;
			this.prix = prix;
			this.categorie=categorie;
		}
		
		
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (id ^ (id >>> 32));
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
			Produit other = (Produit) obj;
			if (id != other.id)
				return false;
			return true;
		}
		public String toString(){
			return "Id =" + this.getId() +
					  " : designation = " + this.getDesignation() +
					  ", Prix =  " + this.getPrix();	
		}
}
