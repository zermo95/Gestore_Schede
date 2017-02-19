package gestoreSchede.gestoreSchedeDescrittive.modificaScheda.modificaDipendentiScheda;

import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.GUI.schermataPrincipale.GestoreDiSistema;
import gestoreSchede.GUI.utility.CampoRicerca;
import gestoreSchede.gestoreSchedeDescrittive.modificaScheda.ModificaScheda;
import gestoreSchede.oggetti.SchedaDescrittiva;
import gestoreSchede.oggetti.elementi.Dipendente;
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
public class ModDipendenteSchedaController {

    private static ObservableList<Dipendente> listaDipendenti;
    private static ObservableList<Dipendente> listaDipendentiAggiunti;
    private SchedaDescrittiva scheda;

    static {
        listaDipendenti = FXCollections.observableArrayList();
        listaDipendentiAggiunti = FXCollections.observableArrayList();
    }

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

    public void initData(SchedaDescrittiva s) {
        scheda = s;
        // Qua va fatta l'inizializzazione
        listaDipendentiAggiunti = FXCollections.observableArrayList(scheda.getListaDipendenti());
        impostaTabellaDipendentiAggiunti();
        attivaCampoRicercaDipendentiAggiunti();
        impostaTabellaDipendenti();
        attivaCampoRicercaDipendenti();
    }

    public static ObservableList<Dipendente> getListaDipendenti() {
        return listaDipendentiAggiunti;
    }

    private void impostaTabellaDipendenti() {

        ObservableList<Dipendente> lista = GestoreDiSistema.getListaDipendenti();
        listaDipendenti = FXCollections.observableArrayList(lista);
        for (Dipendente dipendenteScheda : listaDipendentiAggiunti) {
            for (int j = 0; j < listaDipendenti.size(); j++) {
                Dipendente dipendente = listaDipendenti.get(j);
                if (dipendenteScheda.equals(dipendente))
                    listaDipendenti.remove(dipendente);
            }
        }

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

        CampoRicerca.attivaCampoRicercaDipendenti(listaDipendenti, campoDiRicercaElementi, tabellaDipendenti);

    }


    @FXML
    private void annulla() throws IOException {
        ModificaScheda.mostraModScheda();


    }

    @FXML
    private void salvaDipendenti() throws SQLException {
        scheda.setListaDipendenti(listaDipendentiAggiunti);
        ModificaScheda.mostraModScheda();

    }

}