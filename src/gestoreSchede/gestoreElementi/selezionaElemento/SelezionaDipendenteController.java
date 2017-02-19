package gestoreSchede.gestoreElementi.selezionaElemento;

import gestoreSchede.GUI.schermataPrincipale.GestoreDiSistema;
import gestoreSchede.GUI.utility.CampoRicerca;
import gestoreSchede.oggetti.elementi.Dipendente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

/*******************
 * © APT Software *
 *******************/

public abstract class SelezionaDipendenteController implements Initializable {
    protected static ObservableList<Dipendente> listaDipendenti = FXCollections.observableArrayList();

    protected static int codiceDipendenteSelezionato = 0;
    protected static String nomeDipendenteSelezionato = "";

    public static int getCodiceDipendenteSelezionato() {
        return codiceDipendenteSelezionato;
    }

    /* ------ Tabella Dipendenti -------*/
    @FXML
    protected TableView<Dipendente> tabellaDipendenti;
    @FXML
    protected TableColumn<Dipendente, String> colonnaNomeDipendente;
    @FXML
    protected TableColumn<Dipendente, String> colonnaCognomeDipendente;
    @FXML
    protected TableColumn<Dipendente, Date> colonnaDataNascitaDipendente;
    @FXML
    protected TableColumn<Dipendente, String> colonnaMansioneDipendente;
    @FXML
    protected TableColumn<Dipendente, String> colonnaNumTelefonoDipendente;
    @FXML
    protected TextField campoDiRicercaDipendenti;
    @FXML
    protected Button indietroDipendentiBtn;
    @FXML
    protected Button selezDipendenteBtn;

    protected void impostaTabellaDipendenti() {
        ObservableList<Dipendente> lista = GestoreDiSistema.getListaDipendenti();
        listaDipendenti = FXCollections.observableArrayList(lista);

        // Imposta la tabella
        colonnaNomeDipendente.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaCognomeDipendente.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        colonnaDataNascitaDipendente.setCellValueFactory(new PropertyValueFactory<>("dataNascita"));
        colonnaMansioneDipendente.setCellValueFactory(new PropertyValueFactory<>("mansione"));
        colonnaNumTelefonoDipendente.setCellValueFactory(new PropertyValueFactory<>("numTelefono"));

    }

    protected void attivaCampoRicercaDipendenti() {
        CampoRicerca.attivaCampoRicercaDipendenti(listaDipendenti,campoDiRicercaDipendenti,tabellaDipendenti);

    }

    protected abstract void selezionaDipendente();

    @Override
    public abstract void initialize(URL location, ResourceBundle resources);

}
