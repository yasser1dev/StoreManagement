package com.GestionClient;

import java.util.ArrayList;
import java.util.List;

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

public class FormVille extends Stage{
	Stage subWindow=new Stage();
	Pane subPane=new Pane();
	Scene subScene=new Scene(subPane);
	//vbox
	VBox vbox=new VBox();
	//TexTfield
	TextField search=new TextField();
	//ville tab
	TableView<Ville>  villeDATA=new TableView<Ville>();
	//columns
	TableColumn<Ville,String> nomV=new TableColumn<Ville,String>();
	//list ville
    VilleImpl vDAO=new VilleImpl();
    Ville v;
	ObservableList<Ville> villeList;
	
	public void  initSubElements() {
		
		subPane.getChildren().add(vbox);
		vbox.getChildren().addAll(search,villeDATA);
		
		nomV= new TableColumn<Ville, String>("Ville");
	    nomV.setCellValueFactory(new PropertyValueFactory<>("nomVille"));
	    nomV.setPrefWidth(100);
	    
	    villeDATA.getColumns().add(nomV);
	    villeDATA.setItems(getListVille());
	    
	}
	
	public ObservableList<Ville> getListVille(){
		
		ObservableList<Ville> list=FXCollections.observableArrayList(vDAO.findAll());
		return list;
	}
	
	public void search() {
		search.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				String rehcerche=search.getText();
				rehcerche.toLowerCase();
			    List<Ville> rList=vDAO.findAll();
			    List<Ville> filtredList=new ArrayList();
			    for(Ville v:rList) {
			    	if(v.getNomVille().contains(rehcerche)) filtredList.add(v);
			    }
			    villeList=FXCollections.observableArrayList(filtredList);
				villeDATA.setItems(villeList);
				
			}
		});
		
	}
	
	public void getVilleFromList() {
		villeDATA.setOnMouseClicked(new EventHandler<Event>(){
			@Override
			public void handle(Event event) {
				v=(Ville)villeDATA.getSelectionModel().selectedItemProperty().get();
				if(v!=null) {
					FormeClient.getSelectedVille(v);;
				}
				
			}
		});
	}
	
	public void initForm() {
		initSubElements();
		search();
		getVilleFromList();
		this.setTitle("Selectionner la ville");
		this.setScene(subScene);
		this.show();
	}

}
