package com.GestionPaiement;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

class TraiteView{
	static int i=0;
	Label t;
	Label dateP;
	Label dateE;
	Label montantAP;
	Label typeT;
	//Button 
	Button payer;
	Button payerenCheque;
	
	ComboBox<String> typeTraite;
	TextField nbTraite;

	TextField datePTF;
	DatePicker dateETF;
	TextField montantAPTF;
	
	TraiteView(){
		i++;
		t=new Label("Traite "+i);
		dateP=new Label("Date Prevue :");
		dateE=new Label("Date Effet :");
		montantAP=new Label("Montant A Payer :");

		typeT=new Label("Type paiement : ");
		//Button 
		payer=new Button("Payer");
		payerenCheque=new Button("Payer en Cheque");

		
		typeTraite=new ComboBox<String>();
		nbTraite=new TextField();

		datePTF=new TextField();
		dateETF=new DatePicker();
		montantAPTF=new TextField();
		
		montantAPTF.setDisable(true);
		datePTF.setDisable(true);
		
		typeTraite=new ComboBox<String>();
		typeTraite.getItems().addAll("Espece","Cheque");

		
	}
}

public class FormTraite extends Stage{
	Paiement paiement;
	Stage subStage=new Stage();
	GridPane grid=new GridPane();
	Scene subScene=new Scene(grid);
	//label
	Label numTraite=new Label("Nombre de traite :");
	Label t1=new Label("Traite 1 :");
	Label dateP=new Label("Date Prevue :");
	Label dateE=new Label("Date Effet :");
	Label montantAP=new Label("Montant A Payer :");
	Label typeT=new Label("Type paiement : ");
	//Button 
	Button valider=new Button("Valider");
	//txt fields
	ComboBox<String> typeTraite=new ComboBox<String>();
	TextField nbTraite=new TextField();
	TextField typeTF=new TextField();
	TextField datePTF=new TextField();
	DatePicker dateETF=new DatePicker();
	TextField montantAPTF=new TextField();
	//
	Map<TraiteView,Traite> MapViewTraite=new HashMap();
	//DAO
	PaiementDAO paiDAO=new PaiementDAO();
	
	
	
	
	public void initElement() {
		grid.add(numTraite,1, 1);
		grid.add(nbTraite,2, 1);
		grid.add(valider,3,1);
		grid.setVgap(10);
		grid.setHgap(10);
		
	}
	public void showTriates() {
		valider.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
 				Date d=new Date(System.currentTimeMillis());
				int nb=Integer.valueOf(nbTraite.getText());
				if(nb<=((PaiTraite)paiement).getMaxTraite()) {
				List<Traite> listTraite=new ArrayList();
				valider.setDisable(true);
				nbTraite.setDisable(true);


				
				traiteAvance(d,nb,listTraite);
				
				for(int i=1;i<nb;i++) {
					Date dateP=new Date(d.getYear(),d.getMonth()+i,d.getDay());
					//==========
					Traite t=new Traite(i,dateP, paiement.getMontant()/nb);
					TraiteView tV=new TraiteView();
					MapViewTraite.put(tV,t); 
					tV.datePTF.setText(String.valueOf(MapViewTraite.get(tV).getDatePrevu()));
					tV.montantAPTF.setText(String.valueOf(MapViewTraite.get(tV).getMontantAPayer()));
					//================
					grid.add(tV.t,i+1, 2);
					grid.add(tV.dateP,i+1, 3);
					grid.add(tV.datePTF,i+1, 4);
					grid.add(tV.dateE,i+1, 5);
					grid.add(tV.dateETF,i+1, 6);
					grid.add(tV.montantAP,i+1, 7);
					grid.add(tV.montantAPTF,i+1, 8);
					grid.add(tV.payerenCheque,i+1, 10);
					//==========================
					listTraite.add(t);
					 
				}
				

				PaiTraite p=(PaiTraite)paiement;
				p.setListeTraite(listTraite);
				}
				else {
					System.out.println("il faut pas depasser "+((PaiTraite)paiement).getMaxTraite()+" traites");
				}
				
			}
		});
		
		
	}
	public void showStateTraite(Paiement p) {
		List<Traite> listTraite=new ArrayList();
		List<Traite> dataTraites=((PaiTraite)p).getListeTraite();
		Collections.sort(dataTraites);
		//traiteAvance(d,nb,listTraite);
			initTraiteAvance(dataTraites.get(0),p);
			int i=1;
			for(Traite t:dataTraites.subList(1, dataTraites.size())){
				//==========
				//Traite t=new Traite(i,dateP, paiement.getMontant()/nb);
				TraiteView tV=new TraiteView();
				MapViewTraite.put(tV,t); 
				//================
				grid.add(tV.t,i+1, 2);
				grid.add(tV.dateP,i+1, 3);
				grid.add(tV.datePTF,i+1, 4);
				grid.add(tV.dateE,i+1, 5);
				grid.add(tV.dateETF,i+1, 6);
				grid.add(tV.montantAP,i+1, 7);
				grid.add(tV.montantAPTF,i+1, 8);
				grid.add(tV.payerenCheque,i+1, 10);
				i++;
				//==========================
				tV.datePTF.setText(String.valueOf(t.getDatePrevu()));
				tV.montantAPTF.setText(String.valueOf(t.getMontantAPayer()));
				tV.montantAPTF.setAccessibleText(String.valueOf(t.getMontantAPayer()));
				//==========================
				if(t.isEtat()==true) {
					tV.dateETF.setDisable(true);
					tV.dateETF.setPromptText(String.valueOf(t.getDateEffet()));
					tV.payerenCheque.setDisable(true);

				}
				//==========================
				//listTraite.add(t);
			 
			}
		

		//PaiTraite p=(PaiTraite)paiement;
		//p.setListeTraite(listTraite);
		
	}
	
	public void initTraiteAvance(Traite t,Paiement p) {
		TraiteView tV=new TraiteView();
		MapViewTraite.put(tV,t); 
		//================
		grid.add(tV.t,1, 2);
		grid.add(tV.dateP,1, 3);
		grid.add(tV.datePTF,1, 4);
		grid.add(tV.dateE,1, 5);
		grid.add(tV.dateETF,1, 6);
		grid.add(tV.montantAP,1, 7);
		grid.add(tV.montantAPTF,1, 8);
		grid.add(tV.typeT, 1, 9);
		grid.add(typeTF,1, 10);
		//==========================
		if(((PaiTraite)p).getCheque()!=null) typeTF.setText("Cheque");
		typeTF.setText("Espece");
		tV.datePTF.setText(String.valueOf(t.getDatePrevu()));
		tV.montantAPTF.setText(String.valueOf(t.getMontantAPayer()));
		tV.dateETF.setPromptText(String.valueOf(t.getDateEffet()));
		tV.montantAPTF.setAccessibleText(String.valueOf(t.getMontantAPayer()));
		//==========================
		if(t.isEtat()==true) {
			List<Node> l=new ArrayList();
			tV.dateETF.setDisable(true);
			l.add(typeTF);
			disableAllFields(l);
			}
		//listTraite.add(t);
	}
	
	public void payerChequeTraite() {
		for(TraiteView tV:MapViewTraite.keySet()) {
			tV.payerenCheque.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					Long id=MapViewTraite.get(tV).getId();
					Traite t=new Traite(id,Date.valueOf(tV.datePTF.getText()),Double.valueOf(tV.montantAPTF.getText()));
					t.setEtat(true);
					t.setDateEffet(Date.valueOf(tV.dateETF.getValue()));
					//add info cheque
					FormCheque fcheque=new FormCheque();
					Stage stage1=(Stage)tV.payerenCheque.getScene().getWindow();
					fcheque.initModality(Modality.WINDOW_MODAL);
					fcheque.initOwner(stage1);
					fcheque.initChequeTraite(t,paiement);	
					
				}
			});
		}
	}
	public void initTraiteState(Paiement p) {
		paiement=p;
		this.setTitle("Info Traite");
		this.setScene(subScene);
		showStateTraite(p);
		payerChequeTraite();
		grid.setVgap(10);
		grid.setHgap(10);
		this.setHeight(500);
		this.setWidth(900);

		this.show();
	}
	public void disableAllFields(List<Node> l) {
		for(Node n:l) n.setDisable(true);
	}
	public void traiteAvance(Date d,int nb,List<Traite> list) {
		Traite t0=new Traite(0,d, paiement.getMontant()/nb);
		list.add(t0);
		t0.setDateEffet(d);
		TraiteView tV0=new TraiteView();
		MapViewTraite.put(tV0, t0);
		tV0.datePTF.setText(String.valueOf(MapViewTraite.get(tV0).getDatePrevu()));
		tV0.montantAPTF.setText(String.valueOf(MapViewTraite.get(tV0).getMontantAPayer()));
		tV0.dateETF.setPromptText(String.valueOf(t0.getDateEffet()));
		
		grid.add(tV0.t,1, 2);
		grid.add(tV0.dateP,1, 3);
		grid.add(tV0.datePTF,1, 4);
		grid.add(tV0.dateE,1, 5);
		grid.add(tV0.dateETF,1, 6);
		grid.add(tV0.montantAP,1, 7);
		grid.add(tV0.montantAPTF,1, 8);
		grid.add(tV0.typeT,1, 9);
		grid.add(tV0.typeTraite,1, 10);
		grid.add(tV0.payer,1, 11);
		tV0.typeTraite.setOnAction((e) -> {
			String typeOfpaiement=tV0.typeTraite.getSelectionModel().getSelectedItem();
			t0.setEtat(true);
			tV0.typeTraite.setDisable(true);
			switch (tV0.typeTraite.getSelectionModel().getSelectedItem()) {
			case "Espece":
				break;
			case "Cheque":
				FormCheque fcheque=new FormCheque();
				Stage stage1=(Stage)tV0.typeTraite.getScene().getWindow();
				fcheque.initModality(Modality.WINDOW_MODAL);
				fcheque.initOwner(stage1);
				Paiement p1=(PaiTraite)paiement;
				fcheque.initChequeTraiteAvance(p1);
				break;
			default:
				break;
			}
		
	});
		tV0.payer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				PaiTraite p=new PaiTraite(paiement.getId(), paiement.getDateEffet(), ((PaiTraite)paiement).getListeTraite(), 
						 paiement.getVente());
				IPaiTraiteImpl pTDao=new IPaiTraiteImpl();
				pTDao.create(p);
			}
		});
		
	}
	
	
	
	

	public void initTraite(Paiement p) {
		paiement=p;
		this.setTitle("Info Traite");
		this.setScene(subScene);
		initElement();
		showTriates();
		this.setHeight(500);
		this.setWidth(900);

		this.show();
		
	}
}
