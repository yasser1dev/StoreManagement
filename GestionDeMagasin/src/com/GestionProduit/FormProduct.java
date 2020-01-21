package com.GestionProduit;

import java.util.ArrayList;
import java.util.List;

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

public class FormProduct extends Application{
	Scene scene=null;
	//Pane
	BorderPane root=new BorderPane();
	Pane paneTitle=new Pane();
	Pane paneButton=new Pane();
	GridPane gridpane=new GridPane();
	GridPane centerGrid=new GridPane();
	Pane rightPane=new Pane();
	//HBOX
	HBox titleZone=new HBox();
	//VBOX
	VBox buttonZone=new VBox();
	VBox centerZone=new VBox();
	VBox rightZone=new VBox();
	//Label
	Label name=new Label();
	Label labelId=new Label("ID:");
	Label labelDesignation=new Label("Designation:");
	Label labelPrix=new Label("PRIX:");
	Label labelCategorie=new Label("Categorie :");
	//button
	Button ajouter=new Button("AJOUTER");
	Button modifier=new Button("MODIFIER");
	Button supprimer=new Button("SUPPRIMER");
	Button ajouterCategorie=new Button("Categorie");
	//text area
	TextField id = new TextField();
	TextField designation = new TextField();
	TextField prix = new TextField();
	TextField rechercher = new TextField();
	static TextField categorie= new TextField();

	//TexT
	TableView<Produit> ProductData=new TableView<Produit>();
	//Columns
    TableColumn<Produit, Long> idProduct;
    TableColumn<Produit, String> designationColumn;
    TableColumn<Produit, Double> prixColumn;
    TableColumn<Produit, String> categorieColumn;

    //Connection with database
    ProduitDAOIMPL pDAO=new ProduitDAOIMPL();
    static Categorie c;
    ObservableList<Produit> list;

    

	private void initPane(){
		scene=new Scene(root);
		root.setTop(paneTitle);
		root.setCenter(centerGrid);
		root.setLeft(paneButton);
		root.setRight(rightPane);
		//title
		
		paneTitle.getChildren().add(titleZone);
		titleZone.getChildren().add(name);
		//Button
		paneButton.setBackground(new Background(new BackgroundFill(Color.BLUE,  
                CornerRadii.EMPTY, Insets.EMPTY)));
		paneButton.getChildren().add(buttonZone);
		buttonZone.getChildren().add(ajouter);
		buttonZone.getChildren().add(modifier);
		buttonZone.getChildren().add(supprimer);
		
		//center texarea
		centerGrid.setBackground(new Background(new BackgroundFill(Color.WHITE,  
                CornerRadii.EMPTY, Insets.EMPTY)));
		//centerGrid.setAlignment(Pos.TOP_CENTER);
		centerGrid.add(labelId, 1, 1);
		centerGrid.add(id,2, 1);
		centerGrid.add(labelDesignation, 1, 2);
		centerGrid.add(designation,2, 2);
		centerGrid.add(labelPrix, 1, 3);
		centerGrid.add(prix,2, 3);
		centerGrid.add(ajouterCategorie, 2, 5);
		centerGrid.add(categorie, 2, 4);
		categorie.setDisable(true);
		centerGrid.add(labelCategorie, 1, 4);
		centerGrid.setHgap(10);
		centerGrid.setVgap(10);


		//centerZone.getChildren().addAll(id,designation,prix);
		
		
		//rightZone
		list = getProductList();
	    ProductData.setItems(list);
		ProductData.getColumns().addAll(idProduct,designationColumn,prixColumn,categorieColumn);
		rightPane.getChildren().add(rightZone);
		rightZone.getChildren().add(rechercher);
		rightZone.getChildren().add(ProductData);
	}
		//Button action 
	
	private void initElements() {
		name.setText("Gestion des produits");
		name.setTextFill(Color.BLACK);
		name.setFont(Font.font(20));
		name.setAlignment(Pos.CENTER);
		
		ajouter.setPrefSize(80, 50);
		modifier.setPrefSize(80, 50);
		supprimer.setPrefSize(80, 50);
		
		id.setMinWidth(120);
		id.setDisable(true);
		designation.setMinWidth(120);
		prix.setMinWidth(120);
		rechercher.setMinWidth(120);
		
		idProduct= new TableColumn<Produit, Long>("ID");
	    idProduct.setCellValueFactory(new PropertyValueFactory<>("id"));
	    idProduct.setPrefWidth(50);
	    
	    designationColumn= new TableColumn<Produit, String>("Designation");
	    designationColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
	    designationColumn.setPrefWidth(100);
	    
	    prixColumn= new TableColumn<Produit, Double>("Prix");
	    prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
	    prixColumn.setPrefWidth(80);
	    
	    categorieColumn=new TableColumn<>("Catégorie");
	    categorieColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Produit, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TableColumn.CellDataFeatures<Produit, String> param) {
	            return new SimpleObjectProperty<>(param.getValue().getCategorie().getTitle());
	        }
	    });
		
	}
	
	public void updateTableView() {
		 list = getProductList();
		 //for(Produit p:list) System.out.println("ID :"+p.getId()+" Prix : "+p.getPrix());
		 ProductData.setItems(list);
	}
	public ObservableList<Produit> getProductList() {
		 
	      ObservableList<Produit> list = FXCollections.observableArrayList(pDAO.findAll());
	      return list;
	  }
	
	public void ajouterProduit() {
		ajouter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(designation.getText() != null && !(designation.getText().trim().isEmpty())) {
               String prixString=prix.getText();
               Produit p=new Produit(pDAO.maxId()+1,designation.getText(),Double.parseDouble(prixString),c);	
	           pDAO.create(p);
	           updateTableView();
            	}


            }
        });
	}
	
	public void modifierProduit() {
		modifier.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(id.getText() != null && !(id.getText().trim().isEmpty())) {
				String idString=id.getText();
	            String prixString=prix.getText();
	            Produit p=new Produit(Long.parseLong(idString),designation.getText(),Double.parseDouble(prixString),c);
				pDAO.update(p);
		        updateTableView();

				}
			}
		});
	}
	
	
	public void supprimerProduit() {
		
		supprimer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				if(id.getText() != null && !(id.getText().trim().isEmpty())) {
				String idString=id.getText();
				pDAO.delete(Long.parseLong(idString));
		        updateTableView();

				}
				
			}
		});
	}
	public void chercherProduit() {
		rechercher.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				String rehcerche=rechercher.getText();
				rehcerche.toLowerCase();
			    List<Produit> rList=pDAO.findAll();
			    List<Produit> filtredList=new ArrayList();
			    for(Produit p:rList) {
			    	if(p.getDesignation().contains(rehcerche)) filtredList.add(p);
			    }
				list=FXCollections.observableArrayList(filtredList);
				ProductData.setItems(list);
				
			}
		});
	}
	public void recupererProduit() {
		ProductData.setOnMouseClicked(new EventHandler<Event>(){
			@Override
			public void handle(Event event) {
				Produit p=(Produit)ProductData.getSelectionModel().selectedItemProperty().get();
				id.clear();
				prix.clear();
				designation.clear();
				categorie.clear();
				id.setText(String.valueOf(p.getId()));
				prix.setText(String.valueOf(p.getPrix()));
				designation.setText(p.getDesignation());
				c=p.getCategorie();
				categorie.setText(p.getCategorie().getTitle());

			}
		});
	}

	public void ajouterCategorie() {
		ajouterCategorie.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				FormeCategorie fc=new FormeCategorie();
				Stage stage=(Stage)ajouterCategorie.getScene().getWindow();
				
				fc.initModality(Modality.WINDOW_MODAL);
				fc.initOwner(stage);
				fc.initStage();

				
				
				
			}
			
			
			
			});
		
	}
			
	public static void getSelectedCategorie(Categorie cat) {
		if(cat!=null) {
			FormProduct.c=cat;
			categorie.clear();
			categorie.setText(c.getTitle());
			}
		
	}
	
	public static void main(String[] args) {
		Application.launch();
	}
	public void start(Stage window) throws Exception {
		// TODO Auto-generated method stub
		window.setWidth(700);
		window.setHeight(500);
		try {
		ajouterProduit();
		modifierProduit();
		supprimerProduit();
		chercherProduit();
		recupererProduit();
		ajouterCategorie();
		}catch(Exception e) {}
		initElements();
		initPane();
		//scene.getStylesheets().add("style.css");
		window.setScene(scene);
		window.show();
		
	}

}
