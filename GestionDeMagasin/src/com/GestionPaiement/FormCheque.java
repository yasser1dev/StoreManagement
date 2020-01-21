package com.GestionPaiement;

import com.GestionClient.Client;
import com.GestionClient.IClientDAOIMPL;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FormCheque extends Stage{
	Paiement paiement;
	Stage subStage=new Stage();
	GridPane grid=new GridPane();
	Scene subScene=new Scene(grid);
	//labels
	Label idCheque=new Label("ID Cheque");
	Label idPaiement=new Label("ID Paiement");
	Label client=new Label("Client");
	Label bank=new Label("Bank");
	Label montant=new Label("Montant");
	Label numero=new Label("Numero du Cheque");
	// txt fields
	TextField idChequeTF=new TextField();
	TextField idPaiementTF=new TextField();
	ComboBox<Client> clientList=new ComboBox();
	ComboBox<Bank> bankList=new ComboBox();
	TextField montantTF=new TextField();
	TextField numeroTF=new TextField();
	//button
	Button enregistrer=new Button("Enregistrer");
	
	//DAO
	IClientDAOIMPL cDAO=new IClientDAOIMPL();
	IBankDAOIMPL bDAO=new IBankDAOIMPL();
	PaiementDAO paiDAO=new PaiementDAO();
	

	
	public void initElement() {
		grid.add(idCheque, 1, 1);
		grid.add(idPaiement, 1, 2);
		grid.add(client, 1, 3);
		grid.add(bank, 1, 4);
		grid.add(montant, 1, 5);
		grid.add(numero, 1, 6);
		
		
		clientList.getItems().addAll(cDAO.findAll());
		bankList.getItems().addAll(bDAO.findAll());
		idChequeTF.setDisable(true);
		idPaiementTF.setDisable(true);
		if(paiement!=null)idPaiementTF.setText(String.valueOf(paiement.getId()));

		grid.add(idChequeTF, 2, 1);
		grid.add(idPaiementTF, 2, 2);
		grid.add(clientList, 2, 3);
		grid.add(bankList, 2, 4);
		grid.add(montantTF, 2, 5);
		grid.add(numeroTF, 2, 6);
		grid.add(enregistrer, 2, 7);
		
		grid.setVgap(10);
		grid.setHgap(10);


	}
	
	public void ajouterCheque() {
		enregistrer.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				IChequeDAOIMPL chDAO=new IChequeDAOIMPL();
				IPaiChequeImpl pChDao=new IPaiChequeImpl();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("CONFIRMATION");
				alert.setContentText("Voulez-vous confirmer ce paiement ?");
				alert.showAndWait().ifPresent(rs -> {
				    if (rs == ButtonType.OK) {
				Cheque cheque=new Cheque(chDAO.maxId()+1,numeroTF.getText(),Double.valueOf(montantTF.getText()),clientList.getValue(),
						bankList.getValue(),(PaiCheque)paiement);
				PaiCheque p=new PaiCheque(paiement.getId(), paiement.getDateEffet(),cheque,paiement.getVente());
				pChDao.create(p);
				    }
				});
				
			}
		});
	}
	
	public void ajouterChequeTraiteAvance() {
		enregistrer.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				IChequeDAOIMPL chDAO=new IChequeDAOIMPL();
				IPaiTraiteImpl pTDao=new IPaiTraiteImpl();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("CONFIRMATION");
				alert.setContentText("Voulez-vous confirmer ce paiement ?");
				alert.showAndWait().ifPresent(rs -> {
				    if (rs == ButtonType.OK) {
				Cheque cheque=new Cheque(chDAO.maxId()+1,numeroTF.getText(),Double.valueOf(montantTF.getText()),clientList.getValue(),
						bankList.getValue(),paiement);
				PaiTraite p=new PaiTraite(paiement.getId(), paiement.getDateEffet(), ((PaiTraite)paiement).getListeTraite(), 
						paiement.getVente());
				p.setCheque(cheque);
				pTDao.create(p);
				    }
				});
				
			}
		});
	}
	public void initChequeTraiteAvance(Paiement p) {
		paiement=p;
		this.setTitle("Information Cheque Traite");
		this.setScene(subScene);
		this.setHeight(300);
		this.setWidth(300);
		ajouterChequeTraiteAvance();
		initElement();
		this.show();
	}
	
	public void ajouterChequeTraite(Traite t,Paiement p) {
		enregistrer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				IChequeDAOIMPL chDAO=new IChequeDAOIMPL();
				ITraiteImpl tDAO=new ITraiteImpl();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("CONFIRMATION");
				alert.setContentText("Voulez-vous confirmer ce paiement ?");
				alert.showAndWait().ifPresent(rs -> {
				    if (rs == ButtonType.OK) {
				    	Cheque cheque=new Cheque(chDAO.maxId()+1,numeroTF.getText(),Double.valueOf(montantTF.getText()),clientList.getValue(),
								bankList.getValue(),p);
						chDAO.create(cheque);
						t.setCheque(cheque);
						tDAO.update(t);
						IPaiTraiteImpl paiTDAO=new IPaiTraiteImpl();
						paiTDAO.update((PaiTraite)p);

				    						    }
				});
				
				

			}
		});
		

	}
	public void initChequeTraite(Traite t,Paiement p) {
		
		this.setTitle("Information Cheque Traite");
		this.setScene(subScene);
		this.setHeight(300);
		this.setWidth(300);
		initElement();
		ajouterChequeTraite(t,p);
		this.show();
	}
	
	public void initElementsInfos(Paiement p) {
		Label id=new Label("ID");
		TextField idTF=new TextField();
		
		Label date=new Label("Date Effet");
		TextField dateTF=new TextField();
		
		Label montantP=new Label("montant Paiement");
		TextField montantTFP=new TextField();
		
		Label idVente=new Label("ID Vente");
		TextField idVenteTF=new TextField();
		
		TextField clientTF=new TextField();
		TextField bankTF=new TextField();

		grid.add(id, 1, 1);
		grid.add(date, 1, 2);
		grid.add(montantP,1,3);
		grid.add(idVente, 1, 4);
		grid.add(idCheque, 1, 5);
		grid.add(client, 1, 7);
		grid.add(bank, 1, 8);
		grid.add(montant, 1, 9);
		grid.add(numero, 1, 10);
		
		grid.add(idTF, 2, 1);
		grid.add(dateTF, 2, 2);
		grid.add(montantTFP,2,3);
		grid.add(idVenteTF, 2, 4);
		grid.add(idChequeTF, 2, 5);
		grid.add(clientTF, 2, 7);
		grid.add(bankTF, 2, 8);
		grid.add(montantTF, 2, 9);
		grid.add(numeroTF, 2, 10);
		
		grid.setVgap(10);
		grid.setHgap(10);
		
		 idTF.setDisable(true);
		 idTF.setText(String.valueOf(p.getId()));
		 
		 dateTF.setDisable(true);
		 dateTF.setText(String.valueOf(p.getDateEffet()));
		 
		 montantTFP.setDisable(true);
		 montantTFP.setText(String.valueOf(p.getMontant()));
		 
		 idVenteTF.setDisable(true);
		 idVenteTF.setText(String.valueOf(p.getVente().getId()));
		 
		 Cheque cheque=((PaiCheque)p).getCheque();
		 idChequeTF.setDisable(true);
		 idChequeTF.setText(String.valueOf(cheque.getId()));
		 
		 clientTF.setDisable(true);
		 clientTF.setText(String.valueOf(cheque.getClient()));
		 
		 bankTF.setDisable(true);
		 bankTF.setText(String.valueOf(cheque.getBank()));
		 
		 montantTF.setDisable(true);
		 montantTF.setText(String.valueOf(cheque.getMontant()));
		 
		 numeroTF.setDisable(true);
		 numeroTF.setText(String.valueOf(cheque.getNum()));
		 
		 
		 
	}
	public void initChequeInfos(Paiement p) {
		paiement=p;
		this.setTitle("Information Paiement en Cheque");
		this.setScene(subScene);
		this.setHeight(500);
		this.setWidth(300);
		initElementsInfos(p);
		this.show();
	}
	
	public void initCheque(Paiement p) {
		paiement=p;
		this.setTitle("Information Cheque");
		this.setScene(subScene);
		this.setHeight(300);
		this.setWidth(300);
		ajouterCheque();
		initElement();
		this.show();
	}
}
