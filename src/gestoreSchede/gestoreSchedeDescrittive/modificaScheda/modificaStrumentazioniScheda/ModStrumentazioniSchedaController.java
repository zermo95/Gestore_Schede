package gestoreSchede.gestoreSchedeDescrittive.modificaScheda.modificaStrumentazioniScheda;

import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.GUI.schermataPrincipale.GestoreDiSistema;
import gestoreSchede.GUI.utility.CampoRicerca;
import gestoreSchede.gestoreSchedeDescrittive.modificaScheda.ModificaScheda;
import gestoreSchede.oggetti.SchedaDescrittiva;
import gestoreSchede.oggetti.elementi.Strumentazione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

/*******************
 * © APT Software *
 *******************/
public class ModStrumentazioniSchedaController {


    private static ObservableList<Strumentazione> listaStrumentazioni;
    private static ObservableList<Strumentazione> listaStrumentazioniAggiunte;
    private SchedaDescrittiva scheda;

    static {
        listaStrumentazioni = FXCollections.observableArrayList();
        listaStrumentazioniAggiunte = FXCollections.observableArrayList();
    }


    /* ------ Tabella Strumentazioni ------- */
    @FXML
    private TableView<Strumentazione> tabellaStrumentazioni;
    @FXML
    private TableColumn<Strumentazione, String> colonnaNomeStrumentazione;
    @FXML
    private TableColumn<Strumentazione, String> colonnaModelloStrumentazione;
    @FXML
    private TableColumn<Strumentazione, String> colonnaTipologiaStrumentazione;
    @FXML
    private TableColumn<Strumentazione, String> colonnaMarcaStrumentazione;
    @FXML
    private TableColumn<Strumentazione, Integer> colonnaNumeroPezziStrumentazione;
    @FXML
    private TableColumn<Strumentazione, Integer> colonnaAnnoAcquistoStrumentazione;
    @FXML
    private TextField campoDiRicercaElementi;

    /* ------ Tabella Strumentazioni Aggiunte ------- */
    @FXML
    private TableView<Strumentazione> tabellaStrumentazioniAggiunte;
    @FXML
    private TableColumn<Strumentazione, String> colonnaNomeStrumentazioneAggiunta;
    @FXML
    private TableColumn<Strumentazione, String> colonnaModelloStrumentazioneAggiunta;
    @FXML
    private TableColumn<Strumentazione, String> colonnaTipologiaStrumentazioneAggiunta;
    @FXML
    private TableColumn<Strumentazione, String> colonnaMarcaStrumentazioneAggiunta;
    @FXML
    private TableColumn<Strumentazione, Integer> colonnaNumeroPezziStrumentazioneAggiunta;
    @FXML
    private TableColumn<Strumentazione, Integer> colonnaAnnoAcquistoStrumentazioneAggiunta;
    @FXML
    private TextField campoDiRicercaElementiAggiunti;

    @FXML
    private void aggiungiStrumentazione(){

        Strumentazione s = tabellaStrumentazioni.getSelectionModel().getSelectedItem();
        if(s==null) GestoreAvvisi.nessunElementoSelezionato();
        else {
            listaStrumentazioniAggiunte.add(s);
            listaStrumentazioni.remove(s);
        }
    }

    @FXML
    private void rimuoviStrumentazione(){
        Strumentazione s = tabellaStrumentazioniAggiunte.getSelectionModel().getSelectedItem();
        if(s==null) GestoreAvvisi.nessunElementoSelezionato();
        else {
            listaStrumentazioni.add(s);
            listaStrumentazioniAggiunte.remove(s);
        }
    }
    public void initData(SchedaDescrittiva s) {
        scheda = s;
        // Qua va fatta l'inizializzazione
        listaStrumentazioniAggiunte = FXCollections.observableArrayList(scheda.getListaStrumentazioni());
        impostaTabellaStrumentazioniAggiunte();
        attivaCampoRicercaStrumentazioniAggiunte();
        impostaTabellaStrumentazioni();
        attivaCampoRicercaStrumentazioni();
    }

    private void impostaTabellaStrumentazioni() {

        ObservableList<Strumentazione> lista = GestoreDiSistema.getListaStrumentazioni();
        listaStrumentazioni = FXCollections.observableArrayList(lista);
        for (Strumentazione strumentazioneScheda : listaStrumentazioniAggiunte) {
            for (int j = 0; j < listaStrumentazioni.size(); j++) {
                Strumentazione strumentazione = listaStrumentazioni.get(j);
                if (strumentazioneScheda.equals(strumentazione))
                    listaStrumentazioni.remove(strumentazione);
            }
        }

        // Imposta la tabella per le strumentazioni
        colonnaNomeStrumentazione.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaMarcaStrumentazione.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colonnaModelloStrumentazione.setCellValueFactory(new PropertyValueFactory<>("modello"));
        colonnaTipologiaStrumentazione.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
        colonnaNumeroPezziStrumentazione.setCellValueFactory(new PropertyValueFactory<>("numeroPezzi"));
        colonnaAnnoAcquistoStrumentazione.setCellValueFactory(new PropertyValueFactory<>("annoAcquisto"));


    }

    private void attivaCampoRicercaStrumentazioni() {

        CampoRicerca.attivaCampoRicercaStrumentazioni(listaStrumentazioni, campoDiRicercaElementi, tabellaStrumentazioni);

    }

    private void impostaTabellaStrumentazioniAggiunte() {
        // Imposta la tabella per le strumentazioni
        colonnaNomeStrumentazioneAggiunta.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaMarcaStrumentazioneAggiunta.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colonnaModelloStrumentazioneAggiunta.setCellValueFactory(new PropertyValueFactory<>("modello"));
        colonnaTipologiaStrumentazioneAggiunta.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
        colonnaNumeroPezziStrumentazioneAggiunta.setCellValueFactory(new PropertyValueFactory<>("numeroPezzi"));
        colonnaAnnoAcquistoStrumentazioneAggiunta.setCellValueFactory(new PropertyValueFactory<>("annoAcquisto"));


    }

    private void attivaCampoRicercaStrumentazioniAggiunte() {

        CampoRicerca.attivaCampoRicercaStrumentazioni(listaStrumentazioniAggiunte, campoDiRicercaElementiAggiunti, tabellaStrumentazioniAggiunte);

    }

    @FXML
    private void aggiungiTutteLeStrumentazioni(){
        for (Strumentazione strumentazione : listaStrumentazioni) {
            listaStrumentazioniAggiunte.add(strumentazione);
        }
        listaStrumentazioni.clear();
    }

    @FXML
    private void annulla() throws IOException {
        ModificaScheda.mostraModScheda();
    }

    @FXML
    private void salvaStrumentazioni() {
        //set della lista strumentazioni della copia della scheda
        scheda.setListaStrumentazioni(listaStrumentazioniAggiunte);
        ModificaScheda.mostraModScheda();

    }
}
