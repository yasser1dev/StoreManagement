package com.GestionPaiement;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Date;

import com.GestionClient.Client;
import com.SocketPaiement.ClientProgram;
import com.SocketPaiement.PaiementData;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FormCarte extends Stage{
	Paiement paiement;
	Stage subStage=new Stage();
	Pane root=new Pane();
	GridPane grid=new GridPane();
	Scene subScene=new Scene(grid);
	//button
	Button ajouterCarte=new Button("ajouter transaction");
	//label 
	Label numCarte=new Label("Numero Carte");
	Label codeCarte=new Label("Code Carte");
	//txt field
	TextField numCarteTF=new TextField();
	TextField codeCarteTF=new TextField();
	//DAO
	PaiementDAO paiDAO=new PaiementDAO();
	IPaiEnLinge paiEnLDAO=new IPaiEnLinge();
	
	
	public void initElement() {
		grid.add(numCarte,1,1);
		grid.add(codeCarte,1,2);
		grid.add(numCarteTF,2,1);
		grid.add(codeCarteTF,2,2);
		grid.add(ajouterCarte,2,3);
		grid.setBackground(new Background(new BackgroundFill(Color.WHITE,  
                CornerRadii.EMPTY, Insets.EMPTY)));
		 grid.setHgap(10);
		 grid.setVgap(10);

		
	}
	
	public void initElementsInfo(Paiement p) {
		Label id=new Label("ID");
		TextField idTF=new TextField();
		
		Label date=new Label("Date Effet");
		TextField dateTF=new TextField();
		
		Label montant=new Label("montant Paiement");
		TextField montantTF=new TextField();
		
		Label idVente=new Label("ID Vente");
		TextField idVenteTF=new TextField();
		
		grid.add(id, 1, 1);
		grid.add(date, 1, 2);
		grid.add(montant,1,3);
		grid.add(idVente, 1, 4);
		grid.add(numCarte,1,5);
		grid.add(codeCarte,1,6);
		
		grid.add(idTF, 2, 1);
		grid.add(dateTF, 2, 2);
		grid.add(montantTF,2,3);
		grid.add(idVenteTF, 2, 4);
		grid.add(numCarteTF,2,5);
		grid.add(codeCarteTF,2,6);
		grid.setBackground(new Background(new BackgroundFill(Color.WHITE,  
                CornerRadii.EMPTY, Insets.EMPTY)));
		 grid.setHgap(10);
		 grid.setVgap(10);
		 
		 idTF.setDisable(true);
		 idTF.setText(String.valueOf(p.getId()));
		 
		 dateTF.setDisable(true);
		 dateTF.setText(String.valueOf(p.getDateEffet()));
		 
		 montantTF.setDisable(true);
		 montantTF.setText(String.valueOf(p.getMontant()));
		 
		 idVenteTF.setDisable(true);
		 idVenteTF.setText(String.valueOf(p.getVente().getId()));
		 
		 numCarteTF.setDisable(true);
		 numCarteTF.setText(String.valueOf(((PaiEnLigne)p).getNumCard()));
		 
		 codeCarteTF.setDisable(true);
		 codeCarteTF.setText(String.valueOf(((PaiEnLigne)p).getCodeCard()));
		
	}
	
	public void initInfoCarte(Paiement p) {
		paiement=p;
		this.setTitle("Paiement En Ligne Information");
		this.setHeight(300);
		this.setWidth(300);
		initElementsInfo(p);
		this.setScene(subScene);
		this.show();
	}
	
	public void ajouterCarte() {
		ajouterCarte.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("CONFIRMATION");
				alert.setContentText("Voulez-vous confirmer ce paiement ?");
				alert.showAndWait().ifPresent(rs -> {
				    if (rs == ButtonType.OK) {
				    	Client c=paiement.getVente().getClient();
				    	PaiementData pD=new PaiementData(c, numCarteTF.getText(), codeCarteTF.getText(), paiement.getMontant());
				    	try {
							ClientProgram cp=new ClientProgram(pD);
							cp.sendData();
							try {
								if(cp.getData()) {
									PaiEnLigne p=new PaiEnLigne(paiement.getId(), paiement.getDateEffet(),numCarteTF.getText(),paiement.getMontant(),
											codeCarteTF.getText(),paiement.getVente());
									paiEnLDAO.create(p);
								}
								else System.out.println("Solde client insuffisant");
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    	

				    						    }
				});
				
				
			}
		});
	}
	
	public void initCarteForm(Paiement p) {
		paiement=p;
		this.setTitle("Carte Information");
		this.setHeight(300);
		this.setWidth(300);
		ajouterCarte();
		initElement();
		this.setScene(subScene);
		this.show();
	}
}
