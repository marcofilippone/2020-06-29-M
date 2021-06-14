/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.DirectorPeso;
import it.polito.tdp.imdb.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	private boolean creato = false;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaAffini"
    private Button btnCercaAffini; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxRegista"
    private ComboBox<Director> boxRegista; // Value injected by FXMLLoader

    @FXML // fx:id="txtAttoriCondivisi"
    private TextField txtAttoriCondivisi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	if(creato)
    		boxRegista.getItems().removeAll(model.getVertexSet());
    	Integer anno = boxAnno.getValue();
    	if(anno == null || anno == 0) {
    		txtResult.setText("Selezionare un anno dalla tendina");
    		return;
    	}
    	model.creaGrafo(anno);
    	creato = true;
    	txtResult.setText("Grafo creato:\n"+model.getVertexSet().size()+" vertici e "+model.getEdgeSet().size()+" archi");
    	boxRegista.getItems().addAll(model.getVertexSet());
    }

    @FXML
    void doRegistiAdiacenti(ActionEvent event) {
    	if(!creato) {
    		txtResult.setText("Devi prima creare il grafo!");
    		return;
    	}
    	Director d = boxRegista.getValue();
    	if(d==null) {
    		txtResult.setText("Selezionare un regista dalla tendina");
    		return;
    	}
    	List<DirectorPeso> lista = model.getAdiacenti(d);
    	txtResult.setText("REGISTI ADIACENTI A "+d+":\n");
    	for(DirectorPeso dir : lista) {
    		txtResult.appendText(dir.getD()+" - attori condivisi: "+dir.getPeso()+"\n");
    	}
    }

    @FXML
    void doRicorsione(ActionEvent event) {
    	if(!creato) {
    		txtResult.setText("Devi prima creare il grafo!");
    		return;
    	}
    	Director d = boxRegista.getValue();
    	if(d==null) {
    		txtResult.setText("Selezionare un regista dalla tendina");
    		return;
    	}
    	String testo = txtAttoriCondivisi.getText();
    	Integer max;
    	try {
    		max = Integer.parseInt(testo);
    	} catch(NumberFormatException e) {
    		txtResult.setText("Inserire un numero intero nell'area di testo");
    		return;
    	}
    	List<Director> percorso = model.trovaPercorso(d, max);
    	txtResult.setText("Ricerca percorso a partire dal regista "+d+"\n\n");
    	if(percorso.size()==1) {
    		txtResult.appendText("Percorso non esistente");
    	}
    	else {
    		txtResult.appendText("Percorso trovato, numero di attori condivisi = "+model.getSommaAttoriFinale()+":\n");
    		for(Director dir : percorso) {
    			txtResult.appendText(dir+"\n");
    		}
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaAffini != null : "fx:id=\"btnCercaAffini\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxRegista != null : "fx:id=\"boxRegista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAttoriCondivisi != null : "fx:id=\"txtAttoriCondivisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
   public void setModel(Model model) {
    	
    	this.model = model;
    	boxAnno.getItems().addAll(2004, 2005, 2006);
    }
    
}
