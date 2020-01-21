package com.GestionVente;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.GestionClient.*;
import com.GestionProduit.FormProduct;
import com.GestionProduit.FormeCategorie;
import com.GestionProduit.Produit;
public class FormeVente extends Application{
	//pane
	BorderPane root=new BorderPane();
	BorderPane subRoot=new BorderPane();
	Scene scene=new Scene(root);
	GridPane gridpane=new GridPane();
	Pane titlePane=new Pane();
	Pane buttonPane=new Pane();
	Pane centerPane=new Pane();
	Pane tableviewsPane=new Pane();

	
	//button
	Button ajouterVente=new Button("Ajouter");
	Button modifierVente=new Button("Modifier");
	Button supprimerVente=new Button("Supprimer");
	Button ajouterClient=new Button("+ Client");
	Button ajouterLigneCommande=new Button("+ Ligne Commande");
	Button clear=new Button("Clear & Refresh");
	
	//hbox
	HBox hboxTitle=new HBox();
	//vBox
	VBox vboxButtons=new VBox();
	VBox vboxRLV=new VBox();
	VBox vboxRLC=new VBox();


	
	//label
	Label title=new Label("Gestion des ventes");
	Label idVente=new Label("ID : ");
	Label dateVente=new Label("Date : ");
	Label clientVente=new Label("Client : ");
	Label ligneCommandeVente=new Label("Ligne Commande : ");
	Label totaleVente=new Label("Totale : ");
	
	//TextFields
	
	TextField idV=new TextField();
	DatePicker dateV=new DatePicker();
	static TextField Client=new TextField();
	TextField totale=new TextField();
	TextField rechercheVente=new TextField();
	TextField rechercheLigneCommande=new TextField();
	
	//TableView 
	TableView<Vente> dataVente=new TableView<Vente>();
	TableView<LigneCommande> dataLigneCommande=new TableView<LigneCommande>();
	//Columns
	TableColumn<Vente,Long> idvente;
	TableColumn<Vente,Date> datevente;
	TableColumn<Vente,String> ventClient;
	TableColumn<Vente,Double> Totalevente;
	
	TableColumn<LigneCommande,Long> idLC;
	TableColumn<LigneCommande,Integer> qteLC;
	TableColumn<LigneCommande,String> produitLC;
	TableColumn<LigneCommande,Double> sousTotaleLC;
	TableColumn<LigneCommande,Vente> venteLC;

	
	//data
	VenteDAOIMPL vDAO;
    LigneCmdDAOIMPL lcDAO;
    static Client c;
    Vente v;

	
	
	public void initPane() throws SQLException {
		root.setTop(titlePane);
		root.setLeft(buttonPane);
		root.setCenter(centerPane);
		root.setRight(subRoot);
		subRoot.setLeft(vboxRLV);
		subRoot.setCenter(vboxRLC);
		titlePane.getChildren().add(hboxTitle);
		buttonPane.getChildren().add(vboxButtons);
		centerPane.getChildren().add(gridpane);
		buttonPane.setBackground(new Background(new BackgroundFill(Color.BLUE,  
                CornerRadii.EMPTY, Insets.EMPTY)));
		centerPane.setBackground(new Background(new BackgroundFill(Color.WHITE,  
                CornerRadii.EMPTY, Insets.EMPTY)));
	    ObservableList<Vente> list = getVenteList();
	    dataVente.setItems(list);
	    dataVente.getColumns().addAll(idvente,datevente,Totalevente,ventClient);
	}
	
	public void initElements() {
		hboxTitle.getChildren().add(title);
		title.setTextFill(Color.BLACK);
		title.setFont(Font.font(20));
		title.setAlignment(Pos.CENTER);
		vboxButtons.getChildren().addAll(ajouterVente,modifierVente,supprimerVente,clear);
		vboxRLV.getChildren().addAll(rechercheVente, dataVente);
		vboxRLC.getChildren().addAll(rechercheLigneCommande, dataLigneCommande);
		rechercheLigneCommande.setDisable(true);
		ajouterVente.setPrefSize(80, 50);
		modifierVente.setPrefSize(80, 50);
		supprimerVente.setPrefSize(80, 50);
		clear.setPrefSize(80, 50);

		
		gridpane.add(idVente, 1, 1);
		gridpane.add(idV, 2, 1);
		idV.setDisable(true);
		
		gridpane.add(dateVente, 1, 2);
		gridpane.add(dateV, 2, 2);
		
		gridpane.add(clientVente, 1, 3);
		gridpane.add(Client, 2, 3);
		Client.setDisable(true);
		
		gridpane.add(ligneCommandeVente, 1, 4);
		gridpane.add(ajouterLigneCommande, 2, 4);
		
		gridpane.add(totaleVente, 1, 5);
		gridpane.add(totale, 2, 5);
		totale.setDisable(true);
		
		gridpane.add(ajouterClient, 3, 3);

		
		gridpane.setHgap(10);
		gridpane.setVgap(10);
		//vente columns 
		idvente= new TableColumn<Vente, Long>("ID");
		idvente.setCellValueFactory(new PropertyValueFactory<>("id"));
		idvente.setPrefWidth(50);
		
		datevente= new TableColumn<Vente, Date>("DATE");
		datevente.setCellValueFactory(new PropertyValueFactory<>("date"));
		datevente.setPrefWidth(80);
		
		Totalevente= new TableColumn<Vente, Double>("Totale");
		Totalevente.setCellValueFactory(new PropertyValueFactory<>("totale"));
		Totalevente.setPrefWidth(50);
		
		ventClient=new TableColumn<>("Client");
		ventClient.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Vente, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TableColumn.CellDataFeatures<Vente, String> param) {
	            return new SimpleObjectProperty<>(param.getValue().getClient().getNom());
	        }
	    });
		
		//ligne Commande columns
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
		
		dataLigneCommande.getColumns().addAll(idLC,qteLC,sousTotaleLC,produitLC);

	
		
	
		
		
		
	}
	
	public ObservableList<Vente> getVenteList() throws SQLException {
		  vDAO=new VenteDAOIMPL();
	      ObservableList<Vente> list = FXCollections.observableArrayList(vDAO.findAll());
	      return list;
	  }
	public ObservableList<LigneCommande> getLcList(Vente v) throws SQLException {
		  lcDAO=new LigneCmdDAOIMPL();
	      ObservableList<LigneCommande> list = FXCollections.observableArrayList(lcDAO.findByVente(v));
	      return list;
	  }
	
	public static void getSelectedClient(Client c) {
		if(c!=null) {
			FormeVente.c=c;
			Client.clear();
			Client.setText(c.getNom()+" "+c.getPrenom());
		}
	}
	
	public void ajouterV() {
		ajouterVente.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(dateV.getEditor().getText() != null  && c!=null) {
				Vente v=new Vente(vDAO.maxId(),Date.valueOf(dateV.getValue()),0,c);
				vDAO.create(v);
				updateTableContent();
				}
				
				
			}
		});
	}
	public void ajouterLC() {
		ajouterLigneCommande.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				FormLigneCmd fLC=new FormLigneCmd();
				Stage stage=(Stage)ajouterLigneCommande.getScene().getWindow();
				
				fLC.initModality(Modality.WINDOW_MODAL);
				fLC.initOwner(stage);
				
				if(v!=null) fLC.initStage(v);
			}
		});
	}
	
	public void ajouterClient() {
		ajouterClient.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				ListClient listClient=new ListClient();
				Stage stage=(Stage)ajouterClient.getScene().getWindow();
				
				listClient.initModality(Modality.WINDOW_MODAL);
				listClient.initOwner(stage);
				listClient.initStage();
				
			}
		});
	}
	
	public void updateTableContent() {
		try {
		    ObservableList<Vente> list;
			list = getVenteList();
		    dataVente.setItems(list);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void clearFields() {
		clear.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				idV.clear();
				dateV.getEditor().clear();
				Client.clear();
				c=null;
				v=null;
				totale.clear();
				updateTableContent();
				
			}
		});
	}
	public  void updateLCTableView() {
		try {
		    ObservableList<LigneCommande> lclist=getLcList(v);   
			lclist=getLcList(v);
			dataLigneCommande.getItems().clear();
			dataLigneCommande.setItems(lclist);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void recupererVente() {
		dataVente.setOnMouseClicked(new EventHandler<Event>(){
			@Override
			public void handle(Event event) {
				v=(Vente)dataVente.getSelectionModel().selectedItemProperty().get();
				System.out.println(v);
				idV.clear();
				dateV.getEditor().clear();
				totale.clear();
				Client.clear();
				idV.setText(String.valueOf(v.getId()));
				dateV.getEditor().setText(String.valueOf(v.getDate()));
				totale.setText(String.valueOf(v.getTotale()));
				c=v.getClient();
				Client.setText(v.getClient().getNom());
				totale.setText(String.valueOf(v.getTotale()));
				updateLCTableView(); 
				
			}
		});
	}
	
	public void modifierVente() {
		modifierVente.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(idV.getText() != null && !(idV.getText().trim().isEmpty())) {
				Vente v=new Vente(Long.valueOf(idV.getText()),Date.valueOf(dateV.getValue()),Double.valueOf(totale.getText()),c);
				vDAO.update(v);
				updateTableContent();
				}

			}
		});
	}
	
	public void search() {
		rechercheVente.setOnKeyReleased(new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent event) {
			    List<Vente> rList=vDAO.findAll();
			    List<Vente> filtredList=new ArrayList();
			    for(Vente v:rList) {
			    	if(v.getClient().getNom().contains(rechercheVente.getText())) filtredList.add(v);
			    }
			    ObservableList<Vente> list;
				list=FXCollections.observableArrayList(filtredList);
				dataVente.setItems(list);
				
				
			}
		});
		rechercheLigneCommande.setOnKeyReleased(new EventHandler<KeyEvent>() {
			 ObservableList<LigneCommande> lclist;
			@Override
			public void handle(KeyEvent event) {
				if(v!=null) {
				Set<LigneCommande> rList=lcDAO.findByVente(v);
			    Set<LigneCommande> filtredList=new HashSet();
			   
				try {
					lclist = getLcList(v);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
			    for(LigneCommande lc:lclist) {
			    	if(lc.getProduit().getDesignation().contains(rechercheLigneCommande.getText())
			    			/*|| v.getClient().getPrenom().contains(rechercheVente.getText())*/) filtredList.add(lc);
			    }
			    lclist=FXCollections.observableArrayList(filtredList);
				dataLigneCommande.setItems(lclist);
				}
				
			}
		});
	}
	
	public void deleteVente() {
		supprimerVente.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(idV.getText() != null && !(idV.getText().trim().isEmpty())) {
				vDAO.delete(Long.valueOf(idV.getText()));
				updateTableContent();
				}

				
			}
		});
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch();

	}

	@Override
	public void start(Stage window) throws Exception {
		// TODO Auto-generated method stub
		window.setTitle("Gestion des ventes");
		window.setHeight(400);
		window.setWidth(1000);
		window.setScene(scene);
		initElements();
		initPane();
		recupererVente();
		ajouterClient();
		ajouterV();
		ajouterLC();
		deleteVente(); 
		clearFields();
		search();
		modifierVente();
		window.show();

		
	}

}
