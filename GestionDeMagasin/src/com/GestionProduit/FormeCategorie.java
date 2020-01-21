package com.GestionProduit;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FormeCategorie extends Stage{
	//Pane
	BorderPane catRoot=new BorderPane();
	Pane leftPane=new Pane();
	Pane rightPane=new Pane();
	Scene sc=new Scene(catRoot,500,500);
	//Vbox
	//Labels
	Label name=new Label("Gestion des Categories");
	Label nvCategorie=new Label("Nouvelle Catégorie");
	//label
	Label id=new Label("ID : ");
	Label title=new Label("Title : ");
	//TextFields
	TextField rechercher=new TextField();
	TextField idCategorie=new TextField();
	TextField titleCategorie=new TextField();
	//Button
	Button ajouter=new Button("Ajouter");
	Button modifier=new Button("Modifier");
	// TableView
	TableView<Categorie> categorieData=new TableView<Categorie>();
	//Columns
    TableColumn<Categorie, Long> idCat;
    TableColumn<Categorie, String> titleCat;
    //cat DATA
    Categorie categorie;

    int i=0;
    CategorieImpl catDAO=new CategorieImpl();
    ObservableList<Categorie> list=null; 
	public void initElementCat(){
		VBox leftZone=new VBox();
		GridPane rightZone=new GridPane();
		catRoot.setLeft(leftZone);
		catRoot.setCenter(rightZone);
		leftZone.getChildren().addAll(name,rechercher,categorieData);
		rightZone.add(id,1,1);
		rightZone.add(idCategorie,2,1);
		rightZone.add(title, 1, 2);
		rightZone.add(titleCategorie, 2, 2);
		rightZone.add(ajouter, 1, 3);
		rightZone.add(modifier, 2, 3);
		rightZone.setHgap(10);
		rightZone.setVgap(10);
		
		//table

		idCat= new TableColumn<Categorie, Long>("ID");
	    idCat.setCellValueFactory(new PropertyValueFactory<>("id"));
	    idCat.setPrefWidth(50);
	    
	    titleCat= new TableColumn<Categorie, String>("Title");
	    titleCat.setCellValueFactory(new PropertyValueFactory<>("title"));
	    titleCat.setPrefWidth(100);
	    if(i<=1) {
		categorieData.getColumns().addAll(idCat,titleCat);
		categorieData.setItems(getCategorieList());
	    }
		

	}
	public ObservableList<Categorie> getCategorieList() {
		 
	      list= FXCollections.observableArrayList(catDAO.findAll());
	      return list;
	  }
	
	public void events() {
		rechercher.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				String rehcerche=rechercher.getText();
				rehcerche.toLowerCase();
			    List<Categorie> rList=catDAO.findAll();
			    List<Categorie> filtredList=new ArrayList();
			    for(Categorie c:rList) {
			    	if(c.getTitle().contains(rehcerche)) filtredList.add(c);
			    }
				list=FXCollections.observableArrayList(filtredList);
				categorieData.setItems(list);
				
			}
		});
		
		ajouter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
	           catDAO.create(new Categorie(catDAO.maxId()+1,titleCategorie.getText()));
	   		   list = getCategorieList();
			   categorieData.setItems(list);
            }
        });
		modifier.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
	           catDAO.update(new Categorie(Long.valueOf(idCategorie.getText()),titleCategorie.getText()));
	   		   list = getCategorieList();
			   categorieData.setItems(list);
            }
        });
		categorieData.setOnMouseClicked(new EventHandler<Event>(){
			@Override
			public void handle(Event event) {

				categorie=(Categorie)categorieData.getSelectionModel().selectedItemProperty().get();
				if(categorie!=null) FormProduct.getSelectedCategorie(categorie);
				
				
			}
		});
	}
	

    public void initStage() {
    			
		initElementCat();
    	events();
    	
    	this.setTitle("Selectionner Catégorie");
    	this.setScene(sc);
    	this.show();
	}
	
	

}

