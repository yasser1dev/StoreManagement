package com.GestionClient;

import java.util.ArrayList;
import java.util.List;

import com.GestionProduit.Categorie;
import com.GestionProduit.CategorieImpl;
import com.GestionProduit.Produit;
import com.GestionProduit.ProduitDAOIMPL;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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

public class FormeClient extends Application{

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
	Label labelNom=new Label("NOM :");
	Label labelPrenom=new Label("PRENOM:");
	Label labelTel=new Label("TEL :");
	Label labelEmail=new Label("Email :");
	Label labelnumMaison=new Label("NumMaison :");
	Label labelrue=new Label("RUE :");
	Label labelville=new Label("Ville :");

	//button
	Button ajouter=new Button("AJOUTER");
	Button modifier=new Button("MODIFIER");
	Button supprimer=new Button("SUPPRIMER");
	Button ajouterVille=new Button("Ville");
	//text area
	TextField id = new TextField();
	TextField nom = new TextField();
	TextField prenom = new TextField();
	TextField tel = new TextField();
	TextField email = new TextField();
	TextField numMaison = new TextField();
	TextField rue = new TextField();
	static TextField ville = new TextField();
	TextField rechercher = new TextField();
	//ComboBox
	final ComboBox<Ville> villeComboBox = new ComboBox<Ville>();
	//TexT
	TableView<Client> ClientDATA=new TableView<Client>();
	//Columns
    TableColumn<Client, Long> idClient;
    TableColumn<Client, String> nomColumn;
    TableColumn<Client, String> prenomColumn;
    TableColumn<Client, String> telColumn;
    TableColumn<Client, String> emailColumn;
    TableColumn<Client, Integer> numMaisonColumn;
    TableColumn<Client, String> rueColumn;
    TableColumn<Client, String> villeColumn;
    //Connection with database
    IClientDAOIMPL cDAO=new IClientDAOIMPL();
    VilleImpl vDAO=new VilleImpl();

    static Ville v;
    ObservableList<Client> list;

   
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
		//
		
	   
		

		//centerGrid.setAlignment(Pos.TOP_CENTER);
		centerGrid.add(labelId, 1, 1);
		centerGrid.add(id,2, 1);
		centerGrid.add(labelNom, 1, 2);
		centerGrid.add(nom,2, 2);
		centerGrid.add(labelPrenom, 1, 3);
		centerGrid.add(prenom,2, 3);
		centerGrid.add(labelTel, 1, 4);
		centerGrid.add(tel, 2, 4);
		centerGrid.add(labelEmail, 1, 5);
		centerGrid.add(email, 2, 5);
		centerGrid.add(labelnumMaison, 1, 6);
		centerGrid.add(numMaison, 2, 6);
		centerGrid.add(labelrue, 1, 7);
		centerGrid.add(rue, 2, 7);
		centerGrid.add(labelville, 1, 8);
		centerGrid.add(ville, 2, 8);
		ville.setDisable(true);
		centerGrid.add(ajouterVille, 2, 9);
		centerGrid.setHgap(10);
		centerGrid.setVgap(10);
		
		
		//rightZone
		list = getClientList();
	    ClientDATA.setItems(list);
	    
		ClientDATA.getColumns().add(idClient);
		ClientDATA.getColumns().add(nomColumn);
		ClientDATA.getColumns().add(prenomColumn);
		ClientDATA.getColumns().add(telColumn);
		ClientDATA.getColumns().add(emailColumn);
		ClientDATA.getColumns().add(numMaisonColumn);
		ClientDATA.getColumns().add(rueColumn);
		//ClientDATA.getColumns().add(villeColumn);
		
		rightPane.getChildren().add(rightZone);
		rightZone.getChildren().add(rechercher);
		rightZone.getChildren().add(ClientDATA);
	}
		//Button action 
	
	private void initElements() {
		name.setText("Gestion des Clients");
		name.setTextFill(Color.BLACK);
		name.setFont(Font.font(20));
		name.setAlignment(Pos.CENTER);
		
		ajouter.setPrefSize(80, 50);
		modifier.setPrefSize(80, 50);
		supprimer.setPrefSize(80, 50);
		
		id.setMinWidth(120);
		id.setDisable(true);
		nom.setMinWidth(120);
		prenom.setMinWidth(120);
		tel.setMinWidth(120);
		email.setMinWidth(120);
		numMaison.setMinWidth(120);
		rue.setMinWidth(120);
		ville.setMinWidth(120);
		rechercher.setMinWidth(120);
		
		idClient= new TableColumn<Client, Long>("ID");
	    idClient.setCellValueFactory(new PropertyValueFactory<>("id"));
	    idClient.setPrefWidth(50);
	    
	    nomColumn= new TableColumn<Client, String>("Nom");
	    nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
	    nomColumn.setPrefWidth(100);
	    
	    prenomColumn= new TableColumn<Client, String>("Prenom");
	    prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
	    prenomColumn.setPrefWidth(80);
	    
	    telColumn= new TableColumn<Client, String>("Tel");
	    telColumn.setCellValueFactory(new PropertyValueFactory<>("tel"));
	    telColumn.setPrefWidth(80);
	    
	    emailColumn= new TableColumn<Client, String>("Email");
	    emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
	    emailColumn.setPrefWidth(80);
	    
	    numMaisonColumn= new TableColumn<Client, Integer>("NumMaison");
	    numMaisonColumn.setCellValueFactory(new PropertyValueFactory<>("numMaison"));
	    numMaisonColumn.setPrefWidth(80);
	    
	    rueColumn= new TableColumn<Client, String>("Rue");
	    rueColumn.setCellValueFactory(new PropertyValueFactory<>("rue"));
	    rueColumn.setPrefWidth(80);
	    
	    villeColumn= new TableColumn<Client, String>("Ville");
	    villeColumn.setCellValueFactory(new PropertyValueFactory<>("nomVille"));
	    villeColumn.setPrefWidth(80);
		
	}
	public ObservableList<Client> getClientList() {
		 
	      ObservableList<Client> list = FXCollections.observableArrayList(cDAO.findAll());
	   
	      return list;
	  }
	public ObservableList<Ville> getVilleList() {
		 
	      ObservableList<Ville> list = FXCollections.observableArrayList(vDAO.findAll());
	   
	      return list;
	  }
	
	public void ajouterClient() {
		ajouter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            if (nom.getText() != null && !(nom.getText().trim().isEmpty())) {	
            String nomV,prenomV,telV,emailV,rueV;
            int numMaisonV;
            Long idVille;
            
            nomV=nom.getText();
            prenomV=prenom.getText();
            telV=tel.getText();
            emailV=email.getText();
            rueV=rue.getText();
            numMaisonV=Integer.valueOf(numMaison.getText());
            //idVille=v.getId();
            
            cDAO.create(new Client(cDAO.maxId()+1,nomV,prenomV,telV,emailV,numMaisonV,rueV,v));
            list=getClientList();
            ClientDATA.setItems(list);
            }
            }
        });
	}
	
	public void modifierClient() {
		modifier.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if (id.getText() != null && !(id.getText().trim().isEmpty())){


				 String nomV,prenomV,telV,emailV,rueV;
		         int numMaisonV;
		            Long idVille;
		            
		            nomV=nom.getText();
		            prenomV=prenom.getText();
		            telV=tel.getText();
		            emailV=email.getText();
		            rueV=rue.getText();
		            numMaisonV=Integer.valueOf(numMaison.getText());
		            
		            
		            Client c=new Client(Long.valueOf(id.getText()),nomV,prenomV,telV,emailV,numMaisonV,rueV,v);

		            cDAO.update(c);
		            list=getClientList();
		            ClientDATA.setItems(list);	
				}
			}
		});
	}
	public void supprimerClient() {
		supprimer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (id.getText() != null && !(id.getText().trim().isEmpty())) {

				 long idClient;
				 idClient=Long.valueOf(id.getText());
				 cDAO.delete(idClient);
				 list=getClientList();
		         ClientDATA.setItems(list);	
				}
			}
		});
	}
	public void chercherClient() {
		rechercher.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				String rehcerche=rechercher.getText();
				rehcerche.toLowerCase();
			    List<Client> rList=cDAO.findAll();
			    List<Client> filtredList=new ArrayList();
			    for(Client c:rList) {
			    	if(c.getNom().contains(rehcerche)) filtredList.add(c);
			    }
				list=FXCollections.observableArrayList(filtredList);
				ClientDATA.setItems(list);
			}
		});
	}
	public void recupererClient() {
		ClientDATA.setOnMouseClicked(new EventHandler<Event>(){
			@Override
			public void handle(Event event) {
				Client c=(Client)ClientDATA.getSelectionModel().selectedItemProperty().get();
				id.clear();
				nom.clear();
				prenom.clear();
				tel.clear();
				email.clear();
				rue.clear();
				numMaison.clear();
				ville.clear();
				if(c!=null) {
				id.setText(String.valueOf(c.getId()));
				nom.setText(c.getNom());
				prenom.setText(c.getPrenom());
				tel.setText(c.getTel());
				email.setText(c.getEmail());
				rue.setText(c.getRue());
				numMaison.setText(String.valueOf(c.getNumMaison()));
				v=c.getVille();
				ville.setText(c.getVille().getNomVille());
				} 
				
			}
		});
	}
	
	public void ajouterVille() {
		ajouterVille.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				FormVille fv=new FormVille();
				Stage stage=(Stage)ajouterVille.getScene().getWindow();
				
				fv.initModality(Modality.WINDOW_MODAL);
				fv.initOwner(stage);
				fv.initForm();
				
				
			}
		});
	}
	
	public static void getSelectedVille(Ville ville) {
		if(ville!=null) {
			
			FormeClient.v=ville;
			FormeClient.ville.clear();
			FormeClient.ville.setText(ville.getNomVille());
		}
	}

	public static void main(String[] args) {
		Application.launch();
	}
	public void start(Stage window) throws Exception {
		// TODO Auto-generated method stub
		window.setWidth(1100);
		window.setHeight(500);	
		initElements();
		initPane();
		ajouterClient();
		modifierClient();
		supprimerClient();
		recupererClient();
		chercherClient();
		ajouterVille();
		
		window.setScene(scene);
		window.show();
		
	}

}
