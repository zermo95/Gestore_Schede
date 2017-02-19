package gestoreSchede.gestoreSchedeDescrittive.modificaScheda.modificaSpaziScheda;

import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.GUI.schermataPrincipale.GestoreDiSistema;
import gestoreSchede.GUI.utility.CampoRicerca;
import gestoreSchede.gestoreSchedeDescrittive.modificaScheda.ModificaScheda;
import gestoreSchede.oggetti.SchedaDescrittiva;
import gestoreSchede.oggetti.elementi.Spazio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;

/*******************
 * © APT Software *
 *******************/
public class ModSpaziSchedaController {
    private static ObservableList<Spazio> listaSpazi = null;
    private static ObservableList<Spazio> listaSpaziAggiunti = null;
    private SchedaDescrittiva scheda = null;

    static {
        listaSpazi = FXCollections.observableArrayList();
        listaSpaziAggiunti = FXCollections.observableArrayList();
    }


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
    private void aggiungiSpazio() {

        Spazio s = tabellaSpazi.getSelectionModel().getSelectedItem();
        if (s == null) GestoreAvvisi.nessunElementoSelezionato();
        else {
            listaSpaziAggiunti.add(s);
            listaSpazi.remove(s);
        }
    }

    @FXML
    private void rimuoviSpazio() {
        Spazio s = tabellaSpaziAggiunti.getSelectionModel().getSelectedItem();
        if (s == null) GestoreAvvisi.nessunElementoSelezionato();
        else {
            listaSpazi.add(s);
            listaSpaziAggiunti.remove(s);
        }
    }

    @FXML
    private void aggiungiTuttiGliSpazi() {
        listaSpaziAggiunti.addAll(listaSpazi);
        listaSpazi.clear();

    }

    public void initData(SchedaDescrittiva s) {
        scheda = s;
        // Qua va fatta l'inizializzazione
        listaSpaziAggiunti = FXCollections.observableArrayList(scheda.getListaSpazi());
        impostaTabellaSpaziAggiunti();
        attivaCampoRicercaSpaziAggiunti();
        impostaTabellaSpazi();
        attivaCampoRicercaSpazi();
    }

    public static ObservableList<Spazio> getListaSpazi() {
        return listaSpaziAggiunti;
    }

    private void impostaTabellaSpazi() {


        ObservableList<Spazio> lista = GestoreDiSistema.getListaSpazi();
        listaSpazi = FXCollections.observableArrayList(lista);
        for (Spazio spazioScheda : listaSpaziAggiunti) {
            for (int j = 0; j < listaSpazi.size(); j++) {
                Spazio spazio = listaSpazi.get(j);
                if (spazioScheda.equals(spazio))
                    listaSpazi.remove(spazio);
            }
        }

        // Imposta tabella per dipendenti
        colonnaNomeSpazio.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaUbicazioneSpazio.setCellValueFactory(new PropertyValueFactory<>("ubicazione"));
        colonnaCapienzaSpazio.setCellValueFactory(new PropertyValueFactory<>("capienza"));

        // Aggiungi i dipendenti alla tabella (non abilitato perchè
        // ci pensa la funzione di ricerca ad aggiungere gli elementi nella tabella)


    }

    private void attivaCampoRicercaSpazi() {

        CampoRicerca.attivaCampoDiRicercaSpazi(listaSpazi, campoDiRicercaElementi, tabellaSpazi);

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

        CampoRicerca.attivaCampoDiRicercaSpazi(listaSpaziAggiunti, campoDiRicercaElementiAggiunti, tabellaSpaziAggiunti);

    }


    @FXML
    private void annulla() throws IOException {
        ModificaScheda.mostraModScheda();


    }

    @FXML
    private void salvaSpazi() throws SQLException {
        scheda.setListaSpazi(listaSpaziAggiunti);
        ModificaScheda.mostraModScheda();

    }

}
