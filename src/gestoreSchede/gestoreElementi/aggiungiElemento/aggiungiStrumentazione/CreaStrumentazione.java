package gestoreSchede.gestoreElementi.aggiungiElemento.aggiungiStrumentazione;


import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.GUI.utility.UtilityGrafiche;
import gestoreSchede.Main;
import gestoreSchede.gestoreElementi.GestoreStrumentazioni;
import gestoreSchede.gestoreElementi.selezionaElemento.Strumentazione.Aggiungi.AssegnaDipendenteToStrumentazione;
import gestoreSchede.gestoreElementi.selezionaElemento.Strumentazione.Aggiungi.AssegnaSpazioToStrumentazione;
import gestoreSchede.oggetti.elementi.Strumentazione;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/*******************
 * © APT Software *
 *******************/
public class CreaStrumentazione implements Initializable {
    private static Stage aggStrStage;
    private static BorderPane pannelloContenitorePane;
    private static BorderPane selezDipendentePane;
    private static BorderPane selezSpazioPane;
    private static BorderPane aggStrPane;

    static {
        aggStrStage = new Stage();
        pannelloContenitorePane = new BorderPane();
        selezDipendentePane = new BorderPane();
        selezSpazioPane = new BorderPane();
        aggStrPane = new BorderPane();
        instance = new CreaStrumentazione();
    }


    /**
     * Istanza del controller per l'aggiunta di una strumentazione
     **/
    public static CreaStrumentazione instance = null;

    /* ------- FXML ------- */
    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoModello;
    @FXML
    private TextField campoMarca;
    @FXML
    private TextField campoNumeroPezzi;
    @FXML
    private TextField campoTipologia;
    @FXML
    private TextField campoAnnoAcquisto;
    @FXML
    private TextArea campoDescrizione;
    @FXML
    private Label campoDipendenteSelezionato;
    @FXML
    private Label campoSpazioSelezionato;
    @FXML
    private Button salvaStrumentazioneBtn;
    @FXML
    private Button annullaBtn;
    @FXML
    private Button selezDipendenteBtn;
    @FXML
    private Button selezSpazioBtn;

    public static void mostraAggiungiStrumentazione() throws IOException {

        caricaTutteLeSchermateAggiungiStrumentazione();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("gestoreElementi/aggiungiElemento/aggiungiStrumentazione/pannelloContenitore.fxml"));
        pannelloContenitorePane = loader.load();

        aggStrStage = new Stage();
        aggStrStage.initModality(Modality.WINDOW_MODAL);
        aggStrStage.initOwner(Main.stagePrincipale);

        Scene scene = new Scene(pannelloContenitorePane);
        aggStrStage.setScene(scene);
        aggStrStage.setResizable(false);
        aggStrStage.setTitle("Aggiungi Strumentazione");
        pannelloContenitorePane.setCenter(aggStrPane);
        aggStrStage.showAndWait();

    }

    public static void mostraSchermataPrincipale() {
        pannelloContenitorePane.setCenter(aggStrPane);

    }

    public void setCampoDipendenteSelezionato(String s) {
        campoDipendenteSelezionato.setText(s);
    }

    public void setCampoSpazioSelezionato(String s) {
        campoSpazioSelezionato.setText(s);
    }


    private static void caricaTutteLeSchermateAggiungiStrumentazione() throws IOException {

        FXMLLoader loaderPrincipale = new FXMLLoader();
        loaderPrincipale.setLocation(Main.class.getResource("gestoreElementi/aggiungiElemento/aggiungiStrumentazione/aggiungiStrumentazione.fxml"));
        aggStrPane = loaderPrincipale.load();
        CreaStrumentazione.instance = loaderPrincipale.getController();

        FXMLLoader loaderSelezDipendenti = new FXMLLoader();
        loaderSelezDipendenti.setLocation(Main.class.getResource("gestoreElementi/selezionaElemento/Strumentazione/Aggiungi/selezDipendente.fxml"));
        selezDipendentePane = loaderSelezDipendenti.load();

        FXMLLoader loaderSelezSpazi = new FXMLLoader();
        loaderSelezSpazi.setLocation(Main.class.getResource("gestoreElementi/selezionaElemento/Strumentazione/Aggiungi/selezSpazio.fxml"));
        selezSpazioPane = loaderSelezSpazi.load();
    }

    private static void mostraSelezDipendente() {
        pannelloContenitorePane.setCenter(selezDipendentePane);
    }

    private static void mostraSelezSpazio() {
        pannelloContenitorePane.setCenter(selezSpazioPane);
    }

    @FXML
    private void annullaOperazione() {
        aggStrStage.close();
    }

    @FXML
    private void aggiungiStrumentazione() throws IOException {
        if (campoDescrizione.getText().isEmpty() || campoNumeroPezzi.getText().isEmpty() || campoNome.getText().isEmpty()) {
            GestoreAvvisi.avvisoCampoVuoto();
        } else {
            String nome = campoNome.getText();
            String modello = campoModello.getText();
            String marca = campoMarca.getText();
            int numeroPezzi = Integer.parseInt(campoNumeroPezzi.getText());
            String tipologia = campoTipologia.getText();

            int annoAcquisto = 0;
            if (!campoAnnoAcquisto.getText().equals("")) {
                annoAcquisto = Integer.parseInt(campoAnnoAcquisto.getText());
            }

            String descrizione = campoDescrizione.getText();

            int codiceDipendente = AssegnaDipendenteToStrumentazione.getCodiceDipendenteSelezionato();
            int codiceSpazio = AssegnaSpazioToStrumentazione.getCodiceSpazioSelezionato();

            Strumentazione nuovaStrumentazione = new Strumentazione(nome, descrizione, marca, modello, numeroPezzi, tipologia, annoAcquisto, codiceDipendente, codiceSpazio);
            GestoreStrumentazioni.aggiungiStrumentazioneAlDatabase(nuovaStrumentazione);
            aggStrStage.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inizializzaGraficaPulsanti();
        campoDipendenteSelezionato.setText("NESSUNO");
        campoSpazioSelezionato.setText("NESSUNO");
        selezDipendenteBtn.setOnAction(event -> mostraSelezDipendente());
        selezSpazioBtn.setOnAction(event -> mostraSelezSpazio());
    }

    private void inizializzaGraficaPulsanti() {
        /* ---- SalvaStrumentazioneBtn ----*/
        UtilityGrafiche.impostaIconaPulsante(salvaStrumentazioneBtn, UtilityGrafiche.SAVE_ICON);

        /* --- annullaOperazioneBtn ---*/
        UtilityGrafiche.impostaIconaPulsante(annullaBtn, UtilityGrafiche.ANNULLA_ICON);
    }
}
