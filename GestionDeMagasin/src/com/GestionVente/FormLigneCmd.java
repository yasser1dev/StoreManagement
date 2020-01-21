package com.GestionVente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.GestionProduit.Produit;
import com.GestionProduit.ProduitDAOIMPL;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class FormLigneCmd extends Stage{
	//pane
	BorderPane borderPane=new BorderPane();
	Scene scene=new Scene(borderPane);
	GridPane gridPane=new GridPane();
	Pane titlePane=new Pane();
	Pane leftPane=new Pane();
	Pane centerPane=new Pane();
	Pane rightPane=new Pane();
	//hbox
	HBox titleBox=new HBox();
	//vbox
	VBox rightBox=new VBox();
	VBox leftBox=new VBox();
	//label
	Label title=new Label("Gestion des lignes de commandes");
	Label id=new Label("ID");
	Label qte=new Label("Quantité");
	Label produitL=new Label("Produit ");
	Label sousTotal=new Label("Sous Totale");
	Label idVente=new Label("ID Vente");
	//Button
	Button ajouterLC=new Button("Ajouter");
	Button modifierLC=new Button("Modifier");
	Button supprimerLC=new Button("Supprimer");
	Button clear=new Button("Clear");
	
	//TableView & columns
	TableView<Produit> produitData=new TableView<Produit>();
	TableColumn<Produit, Long> idProduct;
    TableColumn<Produit, String> designationColumn;
    TableColumn<Produit, Double> prixColumn;
	//TableView & columns
	TableView<LigneCommande> ligneCommandeData=new TableView<LigneCommande>();
	TableColumn<LigneCommande,Long> idLC;
	TableColumn<LigneCommande,Integer> qteLC;
	TableColumn<LigneCommande,String> produitLC;
	TableColumn<LigneCommande,Double> sousTotaleLC;
	
	//Textfields
	//TextField idLigneCmd=new TextField();
	TextField idvente=new TextField();
	TextField quantity=new TextField();
	TextField produitTxt=new TextField();
	TextField ssTotale=new TextField();
	TextField idLigneC=new TextField();
	TextField rechercheProduit=new TextField();
	TextField rechercheLC=new TextField();
	//vente & produit
	Vente vente;
	Produit p;
	LigneCommande lc;
	//lists
	ObservableList<Produit> listProduit;
	LigneCmdDAOIMPL lcDAO=new LigneCmdDAOIMPL();
	VenteDAOIMPL vDAO=new VenteDAOIMPL();

	ProduitDAOIMPL pDAO;
	
	
	
	public void initElements() {
		borderPane.setRight(rightPane);
		borderPane.setLeft(leftPane);
		borderPane.setCenter(gridPane);
		borderPane.setTop(titlePane);
		
		rightPane.getChildren().add(rightBox);
		titlePane.getChildren().add(titleBox);
		leftPane.getChildren().add(leftBox);
		
		titleBox.getChildren().add(title);
		title.setTextFill(Color.BLACK);
		title.setFont(Font.font(20));
		title.setAlignment(Pos.CENTER);
		leftBox.getChildren().addAll(rechercheProduit,produitData);
		rightBox.getChildren().addAll(rechercheLC,ligneCommandeData);
		rechercheLC.setDisable(true);
		gridPane.add(id,1, 1);
		gridPane.add(idVente,1, 2);
		gridPane.add(qte,1, 3);
		gridPane.add(produitL,1, 4);
		gridPane.add(sousTotal,1, 5);
		gridPane.add(ajouterLC,1, 6);
		gridPane.add(clear, 1, 7);
		
		gridPane.add(idLigneC,2, 1);
		idLigneC.setDisable(true);
		gridPane.add(idvente,2, 2);
		idvente.setDisable(true);
		idvente.setText(String.valueOf(vente.getId()));

		gridPane.add(quantity,2, 3);
		gridPane.add(produitTxt,2, 4);
		produitTxt.setDisable(true);
		gridPane.add(ssTotale,2, 5);
		ssTotale.setDisable(true);
		gridPane.add(modifierLC,2, 6);
		gridPane.add(supprimerLC,3, 6);


		gridPane.setHgap(10);
		gridPane.setVgap(10);
		
		idLC= new TableColumn<LigneCommande, Long>("ID");
		idLC.setCellValueFactory(new PropertyValueFactory<>("id"));
		idLC.setPrefWidth(50);
		
		qteLC= new TableColumn<LigneCommande, Integer>("Quantité");
		qteLC.setCellValueFactory(new PropertyValueFactory<>("qte"));
		qteLC.setPrefWidth(80);
		
		sousTotaleLC= new TableColumn<LigneCommande, Double>("SousTotale");
		sousTotaleLC.setCellValueFactory(new PropertyValueFactory<>("sousTotale"));
		sousTotaleLC.setPrefWidth(50);
		
		produitLC=new TableColumn<>("Produit");
		produitLC.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LigneCommande, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TableColumn.CellDataFeatures<LigneCommande, String> param) {
	            return new SimpleObjectProperty<>(param.getValue().getProduit().getDesignation());
	        }
	    });
		
		idProduct= new TableColumn<Produit, Long>("ID");
	    idProduct.setCellValueFactory(new PropertyValueFactory<>("id"));
	    idProduct.setPrefWidth(50);
	    
	    designationColumn= new TableColumn<Produit, String>("Designation");
	    designationColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
	    designationColumn.setPrefWidth(100);
	    
	    prixColumn= new TableColumn<Produit, Double>("Prix");
	    prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
	    prixColumn.setPrefWidth(80);
	    
	    listProduit=getListProducts();
	    produitData.setItems(listProduit);
	    produitData.getColumns().addAll(idProduct,designationColumn,prixColumn);
		ObservableList<LigneCommande> listCmd;

	    listCmd=getListLC();
	    ligneCommandeData.setItems(listCmd);
	    ligneCommandeData.getColumns().addAll(idLC,qteLC,sousTotaleLC,produitLC);
	}
	
	public ObservableList<Produit> getListProducts(){
		ProduitDAOIMPL pDAO=new ProduitDAOIMPL();
		ObservableList<Produit> list=FXCollections.observableArrayList(pDAO.findAll());
		return list;
	}
	
	public ObservableList<LigneCommande> getListLC(){
		LigneCmdDAOIMPL lcDAO=new LigneCmdDAOIMPL();
		ObservableList<LigneCommande> list=FXCollections.observableArrayList(lcDAO.findByVente(vente));
		return list;
	}
	
	public void getSelectedProduct() {
		produitData.setOnMouseClicked(new EventHandler<Event>(){
			@Override
			public void handle(Event event) {
				p=(Produit)produitData.getSelectionModel().selectedItemProperty().get();
				System.out.println(p);
				produitTxt.clear();
				ssTotale.clear();
				
				if(p!=null) produitTxt.setText(p.getDesignation());
			}
		});
	}
	
	public void clear() {
		clear.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				clearAll();
				updateTableContent();

			}
		});
	}
	public void clearAll() {
		idLigneC.clear();
		quantity.clear();
		produitTxt.clear();
		ssTotale.clear();
	}
	public void getSelectedLC() {
		ligneCommandeData.setOnMouseClicked(new EventHandler<Event>(){
			@Override
			public void handle(Event event) {
				lc=(LigneCommande)ligneCommandeData.getSelectionModel().selectedItemProperty().get();
				clearAll();
				if(lc!=null) {
					idLigneC.setText(String.valueOf(lc.getId()));
					quantity.setText(String.valueOf(lc.getQte()));
					produitTxt.setText(lc.getProduit().getDesignation());
					p=lc.getProduit();
					ssTotale.setText(String.valueOf(lc.getSousTotale()));
				}
				
			}
		});
	}
	
	public void updateTableContent() {
		ObservableList<LigneCommande> listCmd=null;
		listCmd=getListLC();
		ligneCommandeData.getItems().clear();
		ligneCommandeData.setItems(listCmd);
		
		 
	}
	public void ajouterLC() {
		ajouterLC.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				int q=Integer.valueOf(quantity.getText());
				LigneCommande lc=new LigneCommande(lcDAO.maxId(),q,p,vente);
				ssTotale.setText(String.valueOf(lc.getSousTotale()));
				vente.setLignesCmd(lcDAO.findByVente(vente));

				vente.getLignesCmd().add(lc);
				for(LigneCommande l:vente.getLignesCmd()) {
					if(lcDAO.find(l.getId())!=null) {
						System.out.println("I'LL update ");
						lcDAO.update(l);
					}
					else {
						System.out.println("I'LL add ");
						lcDAO.create(l);
					}
				}
				
				vente.setTotale(vente.calculeTotale());
				VenteDAOIMPL vDAO=new VenteDAOIMPL();
				vDAO.update(vente);
				updateTableContent();
				
				
			}
		});
	}
	public void chercherProduit() {
		rechercheProduit.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				pDAO=new ProduitDAOIMPL();
			    List<Produit> rList=pDAO.findAll();
			    List<Produit> filtredList=new ArrayList();
			    for(Produit p:rList) {
			    	if(p.getDesignation().contains(rechercheProduit.getText())) filtredList.add(p);
			    }
			    listProduit=FXCollections.observableArrayList(filtredList);
				produitData.setItems(listProduit);
				
			}
		});
	}
	
	public void delete() {
		supprimerLC.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				lcDAO.delete(Long.valueOf(idLigneC.getText()));
				//vente.setLignesCmd(lcDAO.findByVente(vente));
				vente.getLignesCmd().removeIf(lc -> lc.getId()==Long.valueOf(idLigneC.getText()));
				vente.setTotale(vente.calculeTotale());
				vDAO.update(vente);
				updateTableContent();

			}
		});
	}
	
	public void update() {
		modifierLC.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				LigneCommande lc=new LigneCommande(
						Long.valueOf(idLigneC.getText()),
						Integer.valueOf(quantity.getText()),
						p,
						vDAO.find(Integer.valueOf(idvente.getText())));
				
				ssTotale.setText(String.valueOf(lc.getSousTotale()));
			    lcDAO.update(lc);
			    vente.setLignesCmd(lcDAO.findByVente(vente));
				vente.setTotale(vente.calculeTotale());
				vDAO.update(vente);
				updateTableContent();

			
				
			}
		});
	}
	
	public void initStage(Vente v) {
		vente=v;
		System.out.println(vente.getLignesCmd());

		getSelectedProduct();
		getSelectedLC();
		ajouterLC();
		delete();
		clear();
		update();
		chercherProduit();
		initElements();
    	this.setScene(scene);
    	this.show();
	}
	

	
}
