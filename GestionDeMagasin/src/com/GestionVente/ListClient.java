package com.GestionVente;

import java.util.ArrayList;
import java.util.List;

import com.GestionClient.Client;
import com.GestionClient.IClientDAOIMPL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListClient extends Stage{
	Stage subWindow=new Stage();
	Pane subPane=new Pane();
	Scene subScene=new Scene(subPane);
	//vbox
	VBox vbox=new VBox();
	//TexTfield
	TextField search=new TextField();
	//ville tab
	TableView<Client>  ClientDATA=new TableView<Client>();
	//columns
	TableColumn<Client,String> nom=new TableColumn<Client,String>();
	TableColumn<Client,String> prenom=new TableColumn<Client,String>();

	//list ville
    IClientDAOIMPL cDAO=new IClientDAOIMPL();
    Client c;
	ObservableList<Client> clientList;
	
	public void  initSubElements() {
		
		subPane.getChildren().add(vbox);
		vbox.getChildren().addAll(search,ClientDATA);
		
		nom= new TableColumn<Client, String>("NOM");
	    nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
	    nom.setPrefWidth(100);
	    
	    prenom= new TableColumn<Client, String>("PRENOM");
	    prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
	    prenom.setPrefWidth(100);
	    
	    ClientDATA.getColumns().addAll(nom,prenom);
	    ClientDATA.setItems(getListClient());
	    
	}
	
	public ObservableList<Client> getListClient(){
		
		ObservableList<Client> list=FXCollections.observableArrayList(cDAO.findAll());
		return list;
	}
	
	public void search() {
		search.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				String rehcerche=search.getText();
				rehcerche.toLowerCase();
			    List<Client> rList=cDAO.findAll();
			    List<Client> filtredList=new ArrayList();
			    for(Client c:rList) {
			    	if(c.getNom().contains(rehcerche)) filtredList.add(c);
			    }
			    clientList=FXCollections.observableArrayList(filtredList);
				ClientDATA.setItems(clientList);
				
			}
		});
		
	}
	
	public void getClientFromList() {
		ClientDATA.setOnMouseClicked(new EventHandler<Event>(){
			@Override
			public void handle(Event event) {
				c=(Client)ClientDATA.getSelectionModel().selectedItemProperty().get();
				if(c!=null) {
					FormeVente.getSelectedClient(c);
				}
				
			}
		});
	}
	
	public void initStage() {
		initSubElements();
		search();
		getClientFromList();
		this.setTitle("Selectionner Client");
		this.setScene(subScene);
		this.show();
	}

}
