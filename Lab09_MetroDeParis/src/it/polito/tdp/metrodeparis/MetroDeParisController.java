package it.polito.tdp.metrodeparis;

import java.net.URL;
import java.util.*;

import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;


	public class MetroDeParisController {

	    @FXML // ResourceBundle that was given to the FXMLLoader
	    private ResourceBundle resources;

	    @FXML // URL location of the FXML file that was given to the FXMLLoader
	    private URL location;

	    @FXML // fx:id="cmbPrtenza"
	    private ComboBox<Fermata> cmbPartenza; // Value injected by FXMLLoader

	    @FXML // fx:id="cmbArrivo"
	    private ComboBox<Fermata> cmbArrivo; // Value injected by FXMLLoader

	    @FXML // fx:id="btnCerca"
	    private Button btnCerca; // Value injected by FXMLLoader

	    @FXML // fx:id="textArea"
	    private TextArea textArea; // Value injected by FXMLLoader
		
	    private Model model;

		public void setModel(Model model) {
			this.model = model;
			this.cmbPartenza.getItems().addAll(this.model.getFermate());
			this.cmbArrivo.getItems().addAll(this.model.getFermate());
		}

		
/*“Calcola percorso”, il programma deve calcolare il cammino minimo, 
 * ignorando i possibili cambi di linea, tra la stazione di partenza e di arrivo selezionate. 
 * Visualizzare tutte le fermate intermedie ed il tempo di percorrenza totale 
 * (assumendo una sosta di 30 secondi per ogni fermata).
*/
	    @FXML
	    void doCercaPercorso(ActionEvent event) {
	    	
	    	Fermata partenza = cmbPartenza.getValue();
	    	Fermata arrivo = cmbArrivo.getValue();
	    	
	    	if(partenza==null || arrivo ==null){
	    		textArea.setText("Scegliere le stazioni di partenza e arrivo\n");
	    		return;
	    	}
	    	model.createGraph();
			double tempo=model.pesoCamminoMinimo(partenza, arrivo)/60;
	    	
			textArea.appendText(model.camminoMinimo());	
			textArea.appendText("Tempo di percorrenza stimato in minuti: "+Double.toString(tempo)+"\n" );
			
	    }

	    @FXML // This method is called by the FXMLLoader when initialization is complete
	    void initialize() {
	        assert cmbPartenza != null : "fx:id=\"cmbPartenza\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
	        assert cmbArrivo != null : "fx:id=\"cmbArrivo\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
	        assert btnCerca != null : "fx:id=\"btnCerca\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
	        assert textArea != null : "fx:id=\"textArea\" was not injected: check your FXML file 'MetroDeParis.fxml'.";

	    }
	}

