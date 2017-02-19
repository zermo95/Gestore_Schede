package gestoreSchede.gestoreSchedeDescrittive.aggiungiScheda.aggSpazi;

import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.GUI.schermataPrincipale.GestoreDiSistema;
import gestoreSchede.GUI.utility.CampoRicerca;
import gestoreSchede.gestoreSchedeDescrittive.aggiungiScheda.AggiungiScheda;
import gestoreSchede.oggetti.elementi.Spazio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/*******************
 * © APT Software *
 *******************/
public class AggiungiSpaziSchedaController implements Initializable{

    private static ObservableList<Spazio> listaSpazi = FXCollections.observableArrayList();
    private static ObservableList<Spazio> listaSpaziAggiunti = FXCollections.observableArrayList();
    /* ------ Tabella Spazi ------- */
    @FXML
    private TableView<Spazio> tabellaSpazi;
    @FXML
    private TableColumn<Spazio, String> colonnaNomeSpazio;
    @FXML
    private TableColumn<Spazio, String> colonnaUbicazioneSpazio;
    @FXML
    private TableColumn<Spazio, Integer> colonnaCapienzaSpazio;
    @FXML
    private TextField campoDiRicercaElementi;

    /* ------ Tabella Spazi Aggiunti ------- */
    @FXML
    private TableView<Spazio> tabellaSpaziAggiunti;
    @FXML
    private TableColumn<Spazio, String> colonnaNomeSpazioAggiunto;
    @FXML
    private TableColumn<Spazio, String> colonnaUbicazioneSpazioAggiunto;
    @FXML
    private TableColumn<Spazio, Integer> colonnaCapienzaSpazioAggiunto;
    @FXML
    private TextField campoDiRicercaElementiAggiunti;

    @FXML
    private void aggiungiSpazio(){

        Spazio s = tabellaSpazi.getSelectionModel().getSelectedItem();
        if(s==null) GestoreAvvisi.nessunElementoSelezionato();
        else {
            listaSpaziAggiunti.add(s);
            listaSpazi.remove(s);
        }
    }

    @FXML
    private void rimuoviSpazio(){
        Spazio s = tabellaSpaziAggiunti.getSelectionModel().getSelectedItem();
        if(s==null) GestoreAvvisi.nessunElementoSelezionato();
        else {
            listaSpazi.add(s);
            listaSpaziAggiunti.remove(s);
        }
    }

    @FXML
    private void aggiungiTuttiGliSpazi(){
        for (Spazio spazio : listaSpazi) {
            listaSpaziAggiunti.add(spazio);
        }
        listaSpazi.clear();

    }

    public static ObservableList<Spazio> getListaSpazi(){
        return listaSpaziAggiunti;
    }

    private void impostaTabellaSpazi() {


        ObservableList<Spazio> lista = GestoreDiSistema.getListaSpazi();
        listaSpazi = FXCollections.observableArrayList(lista);

        // Imposta tabella per dipendenti
        colonnaNomeSpazio.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaUbicazioneSpazio.setCellValueFactory(new PropertyValueFactory<>("ubicazione"));
        colonnaCapienzaSpazio.setCellValueFactory(new PropertyValueFactory<>("capienza"));

        // Aggiungi i dipendenti alla tabella (non abilitato perchè
        // ci pensa la funzione di ricerca ad aggiungere gli elementi nella tabella)


    }

    private void attivaCampoRicercaSpazi() {

        CampoRicerca.attivaCampoDiRicercaSpazi(listaSpazi,campoDiRicercaElementi,tabellaSpazi);

    }

    private void impostaTabellaSpaziAggiunti() {


        // Imposta tabella per dipendenti
        colonnaNomeSpazioAggiunto.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaUbicazioneSpazioAggiunto.setCellValueFactory(new PropertyValueFactory<>("ubicazione"));
        colonnaCapienzaSpazioAggiunto.setCellValueFactory(new PropertyValueFactory<>("capienza"));

        // Aggiungi i dipendenti alla tabella (non abilitato perchè
        // ci pensa la funzione di ricerca ad aggiungere gli elementi nella tabella)

    }

    private void attivaCampoRicercaSpaziAggiunti() {

        CampoRicerca.attivaCampoDiRicercaSpazi(listaSpaziAggiunti,campoDiRicercaElementiAggiunti,tabellaSpaziAggiunti);

    }

    private void inizializzaListaSpaziAggiunti(){
        listaSpaziAggiunti.clear();
    }

    @FXML
    private void indietro()  {
        AggiungiScheda.mostraAggStrumentazione();


    }
    @FXML
    private void avanti(){
        AggiungiScheda.mostraAggiungiTestoFinale();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        impostaTabellaSpazi();
        impostaTabellaSpaziAggiunti();
        inizializzaListaSpaziAggiunti();
        attivaCampoRicercaSpazi();
        attivaCampoRicercaSpaziAggiunti();
    }


}
