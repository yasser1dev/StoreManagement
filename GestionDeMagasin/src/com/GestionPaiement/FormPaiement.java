package com.GestionPaiement;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.GestionClient.Client;
import com.GestionClient.FormVille;
import com.GestionVente.Vente;
import com.GestionVente.VenteDAOIMPL;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class FormPaiement extends Application{
	//pane
	BorderPane root=new BorderPane();
	Pane leftPane=new Pane();
	Pane upPane=new Pane();
	Pane rightPane=new Pane();
	
	Scene scene=new Scene(root);
	
	//Boxes
	VBox boxButton=new VBox();
	HBox boxTV=new HBox();
	HBox boxTitle=new HBox();
	
	//label
	Label title=new Label("Gestion de Paiements");
	Label id=new Label("ID");
	Label dateEff=new Label("Date Effective");
	Label montant=new Label("Montant");
	Label type=new Label("type");
	Label venteLabel=new Label("Vente");
	
	//Button
	Button ajouter=new Button("Ajouter");
	Button update=new Button("Modifier");
	Button supprimer=new Button("Supprimer");
	Button clear=new Button("Clear");
	Button payer=new Button("Payer");
	
	//Textfields
	TextField idTF=new TextField();
	DatePicker dateEffTF=new DatePicker();
	TextField montantTF=new TextField();
	ComboBox<String> typeList=new ComboBox<String>();
	TextField venteTF=new TextField();
	
	//table view
	TableView<Vente> venteDATA=new TableView<Vente>();
	//columns
	TableColumn<Vente,Long> idVente=new TableColumn<Vente,Long>();
	TableColumn<Vente,Date> dateVente=new TableColumn<Vente,Date>();
	TableColumn<Vente,String> clientVente=new TableColumn<Vente,String>();
	TableColumn<Vente,Double> totaleVente=new TableColumn<Vente,Double>();

	
	//Table view 
	TableView<Paiement> paiementDATA=new TableView<Paiement>();
	TableColumn<Paiement,Date>	datePaiement=new TableColumn<Paiement,Date>();
	TableColumn<Paiement,String>	etat=new TableColumn<Paiement,String>();
	TableColumn<Paiement,String>	typePaiement=new TableColumn<Paiement,String>();
	TableColumn<Paiement,Double>	montantData=new TableColumn<Paiement,Double>();


	//grid
	GridPane gridPane=new GridPane();
	//Paiement
	Paiement paiement;
	//vente
	Vente v;
	//type of paiement 
	String typeOfpaiement;
	//data
	PaiementDAO paiDAO=new PaiementDAO(); 
	public void initElement() throws SQLException {
		 root.setRight(rightPane);
		 root.setTop(upPane);
		 root.setCenter(gridPane);
		 root.setLeft(leftPane);
		 
		 rightPane.getChildren().add(boxTV);
		 boxTV.getChildren().addAll(venteDATA,paiementDATA);
 		 leftPane.getChildren().add(boxButton);
		 boxButton.getChildren().addAll(clear);
		 ajouter.setPrefSize(80, 50);
		 update.setPrefSize(80, 50);
		 supprimer.setPrefSize(80, 50);
		 clear.setPrefSize(80, 50);
		 
		 idTF.setDisable(true);
		 venteTF.setDisable(true);
		 montantTF.setDisable(true);
		 //type
		 typeList.getItems().addAll("Espece","Carte","Cheque","Traite");
 
		 upPane.getChildren().add(boxTitle);
		 boxTitle.getChildren().add(title);
		 
		 gridPane.add(id, 1, 1);
		 gridPane.add(dateEff, 1, 2);
		 gridPane.add(montant, 1, 3);
		 gridPane.add(venteLabel, 1, 4);
		 gridPane.add(type, 1, 5);
		 gridPane.add(payer, 2, 6);
		 
		 gridPane.add(idTF, 2, 1);
		 gridPane.add(dateEffTF, 2, 2);
		 gridPane.add(montantTF, 2, 3);
		 gridPane.add(typeList, 2, 5);
		 gridPane.add(venteTF, 2, 4);
		 
		 gridPane.setHgap(10);
		 gridPane.setVgap(10);
			
		 gridPane.setBackground(new Background(new BackgroundFill(Color.WHITE,  
	                CornerRadii.EMPTY, Insets.EMPTY)));
		 
		 ObservableList<Vente> list = getVenteList();
		 venteDATA.setItems(list);
		 venteDATA.getColumns().addAll(idVente,dateVente,totaleVente,clientVente);
		 
		  
		 paiementDATA.getColumns().addAll(datePaiement,etat,typePaiement,montantData);


	}
	
	public void initColums() {
		//Vente
		idVente= new TableColumn<Vente, Long>("ID");
		idVente.setCellValueFactory(new PropertyValueFactory<>("id"));
		idVente.setPrefWidth(50);
		
		dateVente= new TableColumn<Vente, Date>("DATE");
		dateVente.setCellValueFactory(new PropertyValueFactory<>("date"));
		dateVente.setPrefWidth(80);
		
		totaleVente= new TableColumn<Vente, Double>("Totale");
		totaleVente.setCellValueFactory(new PropertyValueFactory<>("totale"));
		totaleVente.setPrefWidth(50);
		
		clientVente=new TableColumn<>("Client");
		clientVente.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Vente, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TableColumn.CellDataFeatures<Vente, String> param) {
	            return new SimpleObjectProperty<>(param.getValue().getClient().getNom());
	        }
	    });
		
		
		datePaiement=new TableColumn<Paiement,Date>("DateEffe");
		datePaiement.setCellValueFactory(new PropertyValueFactory<>("dateEffet"));
		datePaiement.setPrefWidth(50);
		
		etat=new TableColumn<>("Etat");
		etat.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Paiement, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TableColumn.CellDataFeatures<Paiement, String> param) {
	        	if(param.getValue()!=null){
	            if(param.getValue().isEtat()==true) return new SimpleObjectProperty<>("payé");
	            else return new SimpleObjectProperty<>("Non payé");
	        	}
	        	else return new SimpleObjectProperty<>();

	        }
	    });
		etat.setPrefWidth(50);
		
		typePaiement=new TableColumn<>("Type Paiement");
		typePaiement.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Paiement, String>, ObservableValue<String>>() {
	        @Override
	        public ObservableValue<String> call(TableColumn.CellDataFeatures<Paiement, String> param) {
	        	if(param.getValue()!=null)  return new SimpleObjectProperty<>(param.getValue().getClass().getSimpleName());
	        	else return new SimpleObjectProperty<>();
	        }
	    });
		typePaiement.setPrefWidth(50);
		
		montantData=new TableColumn<Paiement,Double>("Montant");
		montantData.setCellValueFactory(new PropertyValueFactory<>("montant"));
		montantData.setPrefWidth(50);
		
		

		
	}
	
	public void clear() {
		clear.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				idTF.clear();
				dateEffTF.getEditor().clear();
				montantTF.clear();
				venteTF.clear();
				typeList.setDisable(false);
				
				
			}
		});
	}
	
	public void typeView() {
		typeList.setOnAction((e) -> {
			typeOfpaiement=typeList.getSelectionModel().getSelectedItem();
			typeList.setDisable(true);
			switch (typeList.getSelectionModel().getSelectedItem()) {
			case "Espece":
				break;
			case "Carte":
				FormCarte fc=new FormCarte();
				Stage stage=(Stage)typeList.getScene().getWindow();
				fc.initModality(Modality.WINDOW_MODAL);
				fc.initOwner(stage);
				if(dateEffTF.getValue()!=null && v!=null) {
				Paiement p=new PaiCheque(paiDAO.maxId()+1,Date.valueOf(dateEffTF.getValue()),null, v);
				fc.initCarteForm(p);
				}
				break;
			case "Cheque":
				FormCheque fcheque=new FormCheque();
				Stage stage1=(Stage)typeList.getScene().getWindow();
				fcheque.initModality(Modality.WINDOW_MODAL);
				fcheque.initOwner(stage1);
				if(dateEffTF.getValue()!=null && v!=null) {
				Paiement p1=new PaiCheque(paiDAO.maxId()+1,Date.valueOf(dateEffTF.getValue()),null, v);
				fcheque.initCheque(p1);
				}
				break;
			case "Traite":
				FormTraite ft=new FormTraite();
				Stage stage2=(Stage)typeList.getScene().getWindow();
				ft.initModality(Modality.WINDOW_MODAL);
				ft.initOwner(stage2);
				if(dateEffTF.getValue()!=null && v!=null) {
				Paiement p2=new PaiTraite(paiDAO.maxId()+1,Date.valueOf(dateEffTF.getValue()),null, v);
				ft.initTraite(p2);
				}
				break;
				

			default:
				break;
			}
		});
	}
	public void showListPaiement() throws SQLException {
		
		ObservableList<Paiement> list=getPaiList();
		
		paiementDATA.setItems(list);
		
	}
	public void afficherDetailPaiement() {
		paiementDATA.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
				Paiement p=(Paiement)paiementDATA.getSelectionModel().selectedItemProperty().get();
				Paiement p1;
				if(p!=null) {
					p1=paiDAO.findByVente(p.getVente().getId());
				
					switch (p1.getClass().getSimpleName()) {
					case "PaiEspece":
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Paiement");
						alert.setHeaderText("Type Paiement : "+"Espece");
						alert.setContentText("ID :"+p1.getId()+"\n"
								+ "Date Effet :"+p1.getDateEffet()+"\n"
								+ "Montant Paiement :"+p1.getMontant()+"\n"
								+ "ID Vente :"+p1.getVente().getId());

						alert.showAndWait();
						break;
					case "PaiEnLigne":
						FormCarte fc=new FormCarte();
						Stage stage=(Stage)typeList.getScene().getWindow();
						fc.initModality(Modality.WINDOW_MODAL);
						fc.initOwner(stage);
						fc.initInfoCarte(p1);
						break;
					case "PaiCheque":
						FormCheque fcheque=new FormCheque();
						Stage stage1=(Stage)typeList.getScene().getWindow();
						fcheque.initModality(Modality.WINDOW_MODAL);
						fcheque.initOwner(stage1);
						fcheque.initChequeInfos(p1);
						break;
					case "PaiTraite":
						FormTraite ft=new FormTraite();
						Stage stage2=(Stage)typeList.getScene().getWindow();
						ft.initModality(Modality.WINDOW_MODAL);
						ft.initOwner(stage2);
						ft.initTraiteState(p);
						break;
			
					default:
						break;
					}
							
						}
			}
		});
	}
	public void recupererVente() {
		venteDATA.setOnMouseClicked(new EventHandler<Event>(){
			@Override
			public void handle(Event event) {
				v=(Vente)venteDATA.getSelectionModel().selectedItemProperty().get();
				if(v!=null) {
					montantTF.setText(String.valueOf(v.getTotale()));
					venteTF.setText(String.valueOf(v.getId())); 
					try {
						showListPaiement();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				}
			}
		});
	}
	public ObservableList<Vente> getVenteList() throws SQLException {
		  VenteDAOIMPL vDAO=new VenteDAOIMPL();
	      ObservableList<Vente> list = FXCollections.observableArrayList(vDAO.findAll());
	      return list;
	  }
	
	
	
	public ObservableList<Paiement> getPaiList() throws SQLException {
		Paiement p=paiDAO.findByVente(v.getId());
		if(p!=null) {
		ObservableList<Paiement> list = FXCollections.observableArrayList(p);
		typeList.setDisable(true);
		dateEffTF.setDisable(true);
		payer.setDisable(true);
		
		return list;
		}
		typeList.setDisable(false);
		dateEffTF.setDisable(false);
		payer.setDisable(false);
		return null;

	  }
	
	public void payer() {
		payer.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				switch (typeOfpaiement) {
				case "Espece":
					IPaiEspeceDAOImlp paiEsp=new IPaiEspeceDAOImlp();
					if(dateEffTF.getValue()!=null && v!=null) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("CONFIRMATION");
						alert.setContentText("Voulez-vous confirmer ce paiement ?");
						alert.showAndWait().ifPresent(rs -> {
						    if (rs == ButtonType.OK) {
						    	Paiement paiement=new PaiEspece(paiEsp.maxId()+1,Date.valueOf(dateEffTF.getValue()),Double.valueOf(montantTF.getText()),v);
								paiEsp.create((PaiEspece)paiement);						    }
						});
					
					}
					break;

				default:
					break;
				}
			}
		});
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setWidth(1000);
		primaryStage.setHeight(400);
		initColums();
		initElement();
		typeView();
		recupererVente();
		afficherDetailPaiement();
		payer();
		clear();
		primaryStage.setScene(scene);
		primaryStage.show();

		
	}

}
