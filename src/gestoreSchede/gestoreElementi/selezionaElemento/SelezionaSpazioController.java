package gestoreSchede.gestoreElementi.selezionaElemento;

import gestoreSchede.GUI.schermataPrincipale.GestoreDiSistema;
import gestoreSchede.GUI.utility.CampoRicerca;
import gestoreSchede.oggetti.elementi.Spazio;
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
import java.util.ResourceBundle;

/*******************
 * © APT Software *
 *******************/

public abstract class SelezionaSpazioController implements Initializable {
    protected static ObservableList<Spazio> listaSpazi = FXCollections.observableArrayList();

    protected static int codiceSpazioSelezionato = 0;
    protected static String nomeSpazioSelezionato = "";


    public static int getCodiceSpazioSelezionato() {
        return codiceSpazioSelezionato;
    }

    /* ------ Tabella Spazi ------- */
    @FXML
    protected TableView<Spazio> tabellaSpazi;
    @FXML
    protected TableColumn<Spazio, String> colonnaNomeSpazio;
    @FXML
    protected TableColumn<Spazio, String> colonnaUbicazioneSpazio;
    @FXML
    protected TableColumn<Spazio, Integer> colonnaCapienzaSpazio;
    @FXML
    protected TextField campoDiRicercaSpazi;
    @FXML
    protected Button indietroSpaziBtn;
    @FXML
    protected Button selezSpazioBtn;

    protected void impostaTabellaSpazi() {
        ObservableList<Spazio> lista = GestoreDiSistema.getListaSpazi();
        listaSpazi = FXCollections.observableArrayList(lista);

        // Imposta la tabella
        colonnaNomeSpazio.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaUbicazioneSpazio.setCellValueFactory(new PropertyValueFactory<>("ubicazione"));
        colonnaCapienzaSpazio.setCellValueFactory(new PropertyValueFactory<>("capienza"));

    }

    protected void attivaCampoRicercaSpazi() {

        CampoRicerca.attivaCampoDiRicercaSpazi(listaSpazi,campoDiRicercaSpazi,tabellaSpazi);

    }

    protected abstract void selezionaSpazio();

    @Override
    public abstract void initialize(URL location, ResourceBundle resources);
}
