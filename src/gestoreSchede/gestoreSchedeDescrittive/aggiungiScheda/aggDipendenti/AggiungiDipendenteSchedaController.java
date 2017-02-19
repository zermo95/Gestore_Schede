package gestoreSchede.gestoreSchedeDescrittive.aggiungiScheda.aggDipendenti;

import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.GUI.schermataPrincipale.GestoreDiSistema;
import gestoreSchede.GUI.utility.CampoRicerca;
import gestoreSchede.gestoreSchedeDescrittive.aggiungiScheda.AggiungiScheda;
import gestoreSchede.oggetti.elementi.Dipendente;
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
public class AggiungiDipendenteSchedaController implements Initializable {

    private static ObservableList<Dipendente> listaDipendenti = FXCollections.observableArrayList();
    private static ObservableList<Dipendente> listaDipendentiAggiunti = FXCollections.observableArrayList();

    /* ------ Tabella Dipendenti -------*/
    @FXML
    private TableView<Dipendente> tabellaDipendenti;
    @FXML
    private TableColumn<Dipendente, String> colonnaNomeDipendente;
    @FXML
    private TableColumn<Dipendente, String> colonnaCognomeDipendente;
    @FXML
    private TableColumn<Dipendente, Integer> colonnaEta;
    @FXML
    private TableColumn<Dipendente, String> colonnaMansioneDipendente;
    @FXML
    private TableColumn<Dipendente, String> colonnaNumTelefonoDipendente;
    @FXML
    private TextField campoDiRicercaElementi;

    /* ------ Tabella Dipendenti Aggiunti -------*/
    @FXML
    private TableView<Dipendente> tabellaDipendentiAggiunti;
    @FXML
    private TableColumn<Dipendente, String> colonnaNomeDipendenteAggiunti;
    @FXML
    private TableColumn<Dipendente, String> colonnaCognomeDipendenteAggiunti;
    @FXML
    private TableColumn<Dipendente, Integer> colonnaEtaAggiunti;
    @FXML
    private TableColumn<Dipendente, String> colonnaMansioneDipendenteAggiunti;
    @FXML
    private TableColumn<Dipendente, String> colonnaNumTelefonoDipendenteAggiunti;
    @FXML
    private TextField campoDiRicercaElementiAggiunti;

    @FXML
    private void aggiungiDipendente() {

        Dipendente d = tabellaDipendenti.getSelectionModel().getSelectedItem();
        if (d == null) GestoreAvvisi.nessunElementoSelezionato();
        else {
            listaDipendentiAggiunti.add(d);
            listaDipendenti.remove(d);
        }
    }

    @FXML
    private void aggiungiTuttiDipendenti() {
        listaDipendentiAggiunti.addAll(listaDipendenti);
        listaDipendenti.clear();
    }

    @FXML
    private void rimuoviDipendente() {
        Dipendente d = tabellaDipendentiAggiunti.getSelectionModel().getSelectedItem();
        if (d == null) GestoreAvvisi.nessunElementoSelezionato();
        else {
            listaDipendenti.add(d);
            listaDipendentiAggiunti.remove(d);
        }
    }

    public static ObservableList<Dipendente> getListaDipendenti() {
        return listaDipendentiAggiunti;
    }

    private void impostaTabellaDipendenti() {


        ObservableList<Dipendente> lista = GestoreDiSistema.getListaDipendenti();
        listaDipendenti = FXCollections.observableArrayList(lista);

        // Imposta tabella per dipendenti
        colonnaNomeDipendente.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaCognomeDipendente.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        colonnaEta.setCellValueFactory(new PropertyValueFactory<>("dataNascita"));
        colonnaMansioneDipendente.setCellValueFactory(new PropertyValueFactory<>("mansione"));
        colonnaNumTelefonoDipendente.setCellValueFactory(new PropertyValueFactory<>("numTelefono"));

        // Aggiungi i dipendenti alla tabella (non abilitato perchè
        // ci pensa la funzione di ricerca ad aggiungere gli elementi nella tabella)

    }

    private void impostaTabellaDipendentiAggiunti() {


        // Imposta tabella per dipendenti
        colonnaNomeDipendenteAggiunti.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaCognomeDipendenteAggiunti.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        colonnaEtaAggiunti.setCellValueFactory(new PropertyValueFactory<>("dataNascita"));
        colonnaMansioneDipendenteAggiunti.setCellValueFactory(new PropertyValueFactory<>("mansione"));
        colonnaNumTelefonoDipendenteAggiunti.setCellValueFactory(new PropertyValueFactory<>("numTelefono"));

        // Aggiungi i dipendenti alla tabella (non abilitato perchè
        // ci pensa la funzione di ricerca ad aggiungere gli elementi nella tabella)


    }


    private void attivaCampoRicercaDipendentiAggiunti() {
        CampoRicerca.attivaCampoRicercaDipendenti(listaDipendentiAggiunti, campoDiRicercaElementiAggiunti, tabellaDipendentiAggiunti);

    }


    private void attivaCampoRicercaDipendenti() {

        CampoRicerca.attivaCampoRicercaDipendenti(listaDipendenti,campoDiRicercaElementi,tabellaDipendenti);

    }

    private void inizializzaDipendentiAggiunti() {
        listaDipendentiAggiunti.clear();
    }


    @FXML
    private void indietro() {
        AggiungiScheda.mostraCrezioneScheda();
    }

    @FXML
    private void avanti() {
        AggiungiScheda.mostraAggStrumentazione();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        impostaTabellaDipendenti();
        impostaTabellaDipendentiAggiunti();
        attivaCampoRicercaDipendenti();
        attivaCampoRicercaDipendentiAggiunti();
        inizializzaDipendentiAggiunti();
    }


}
