package gestoreSchede.gestoreElementi.modificaElemento.modificaStrumentazione;

import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.GUI.schermataPrincipale.GestoreDiSistema;
import gestoreSchede.GUI.utility.UtilityGrafiche;
import gestoreSchede.Main;
import gestoreSchede.gestoreElementi.GestoreStrumentazioni;
import gestoreSchede.gestoreElementi.selezionaElemento.Strumentazione.Modifica.ModificaAssegnazioneDipendenteToStrumentazione;
import gestoreSchede.gestoreElementi.selezionaElemento.Strumentazione.Modifica.ModificaAssegnazioneSpazioToStrumentazione;
import gestoreSchede.oggetti.elementi.Dipendente;
import gestoreSchede.oggetti.elementi.Spazio;
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
import java.util.Optional;
import java.util.ResourceBundle;

/*******************
 * © APT Software *
 *******************/

public class ModificaStrumentazione implements Initializable {
    private static final int SPAZIO_VUOTO = 0;
    private static final int DIPENDENTE_VUOTO = 0;

    private static Stage modStrumentazioneStage;
    private static BorderPane pannelloContenitorePane;
    private static BorderPane selezSpazioPane;
    private static BorderPane selezDipPane;
    private static BorderPane modStrumentazionePane;

    static {
        modStrumentazioneStage = new Stage();
        pannelloContenitorePane = new BorderPane();
        selezSpazioPane = new BorderPane();
        selezDipPane = new BorderPane();
        modStrumentazionePane = new BorderPane();
        instance = new ModificaStrumentazione();
    }


    /**
     * Istanza del controller per la modifica di una strumentazione
     **/
    public static ModificaStrumentazione instance = null;

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
    private Button apportaModificheBtn;
    @FXML
    private Button annullaBtn;
    @FXML
    private Label campoSpazioSelezionato;
    @FXML
    private Button selezSpazioBtn;
    @FXML
    private Label campoDipendenteSelezionato;
    @FXML
    private Button selezDipendenteBtn;

    public void setCampoSpazioSelezionato(String s) {
        campoSpazioSelezionato.setText(s);
    }

    public void setCampoDipendenteSelezionato(String s) {
        campoDipendenteSelezionato.setText(s);
    }

    private static void mostraModificaStrumentazione() throws IOException {

        caricaTutteLeSchermateModificaStrumentazione();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("gestoreElementi/modificaElemento/modificaStrumentazione/pannelloContenitore.fxml"));
        pannelloContenitorePane = loader.load();

        modStrumentazioneStage = new Stage();
        modStrumentazioneStage.initModality(Modality.WINDOW_MODAL);
        modStrumentazioneStage.initOwner(Main.stagePrincipale);

        Scene scene = new Scene(pannelloContenitorePane);
        modStrumentazioneStage.setScene(scene);
        modStrumentazioneStage.setResizable(false);
        modStrumentazioneStage.setTitle("Modifica Strumentazione");
        pannelloContenitorePane.setCenter(modStrumentazionePane);
        modStrumentazioneStage.showAndWait();
    }

    private static void caricaTutteLeSchermateModificaStrumentazione() throws IOException {
        FXMLLoader loaderSelezSpazi = new FXMLLoader();
        loaderSelezSpazi.setLocation(Main.class.getResource("gestoreElementi/selezionaElemento/Strumentazione/Modifica/selezSpazio.fxml"));
        selezSpazioPane = loaderSelezSpazi.load();

        FXMLLoader loaderSelezDipendenti = new FXMLLoader();
        loaderSelezDipendenti.setLocation(Main.class.getResource("gestoreElementi/selezionaElemento/Strumentazione/Modifica/selezDipendente.fxml"));
        selezDipPane = loaderSelezDipendenti.load();
    }

    public static void mostraSchermataPrincipale() {
        pannelloContenitorePane.setCenter(modStrumentazionePane);
    }

    private static void mostraSelezSpazio() {
        pannelloContenitorePane.setCenter(selezSpazioPane);
    }

    private static void mostraSelezDipendente() {
        pannelloContenitorePane.setCenter(selezDipPane);
    }

    // Funzione chiamata dal GestoreDiSistema per la modifica di un dipendente
    public void initData(Strumentazione strumentazione, BorderPane modStrumentazione) throws IOException {
        modStrumentazionePane = modStrumentazione;

        campoNome.setText(strumentazione.getNome());
        campoModello.setText(strumentazione.getModello());
        campoMarca.setText(strumentazione.getMarca());
        campoNumeroPezzi.setText(String.valueOf(strumentazione.getNumeroPezzi()));
        campoTipologia.setText(strumentazione.getTipologia());
        campoAnnoAcquisto.setText(String.valueOf(strumentazione.getAnnoAcquisto()));
        campoDescrizione.setText(strumentazione.getDescrizione());

        if (strumentazione.getCodiceSpazio() == SPAZIO_VUOTO) {
            campoSpazioSelezionato.setText("NESSUNO");
        } else {
            campoSpazioSelezionato.setText(cercaSpazioSelezionato(strumentazione.getCodiceSpazio()).get().getNome());
        }

        if (strumentazione.getCodiceDipendente() == DIPENDENTE_VUOTO) {
            campoDipendenteSelezionato.setText("NESSUNO");
        } else {
            Dipendente dipendente = cercaDipendenteSelezionato(strumentazione.getCodiceDipendente()).get();
            campoDipendenteSelezionato.setText(dipendente.getNome() + " " + dipendente.getCognome());
        }


        // Azione del pulsante Apporta Modifiche Dipendente
        apportaModificheBtn.setOnAction(event -> {
            try {
                apportaModificheStrumentazione(strumentazione);
            } catch (IOException e) {
                GestoreAvvisi.mostraErrore("");
            }
        });
        mostraModificaStrumentazione();
    }

    public static Optional<Spazio> cercaSpazioSelezionato(int codiceS) {
        for (Spazio s : GestoreDiSistema.getListaSpazi()) {
            if (s.getCodiceSpazio() == codiceS) {
                return Optional.of(s);
            }
        }
        return Optional.empty();
    }

    public static Optional<Dipendente> cercaDipendenteSelezionato(int codiceD) {
        for (Dipendente d : GestoreDiSistema.getListaDipendenti()) {
            if (d.getCodiceDipendente() == codiceD) {
                return Optional.of(d);
            }
        }
        return Optional.empty();
    }

    private void apportaModificheStrumentazione(Strumentazione strumentazione) throws IOException {
        if (campoDescrizione.getText().isEmpty() || campoNumeroPezzi.getText().isEmpty() || campoNome.getText().isEmpty()) {
            GestoreAvvisi.avvisoCampoVuoto();
        } else {
            strumentazione.setDescrizione(campoDescrizione.getText());
            strumentazione.setNome(campoNome.getText());
            int annoAcquisto = 0;
            if (!campoAnnoAcquisto.getText().equals("")) {
                annoAcquisto = Integer.parseInt(campoAnnoAcquisto.getText());
            }
            strumentazione.setAnnoAcquisto(annoAcquisto);
            strumentazione.setModello(campoModello.getText());
            strumentazione.setMarca(campoMarca.getText());
            strumentazione.setNumeroPezzi(Integer.parseInt(campoNumeroPezzi.getText()));
            strumentazione.setTipologia(campoTipologia.getText());

            int codiceDipendente = ModificaAssegnazioneDipendenteToStrumentazione.getCodiceDipendenteSelezionato();
            int codiceSpazio = ModificaAssegnazioneSpazioToStrumentazione.getCodiceSpazioSelezionato();

            strumentazione.setCodiceDipendente(codiceDipendente);
            strumentazione.setCodiceSpazio(codiceSpazio);

            GestoreStrumentazioni.aggiornaStrumentazione(strumentazione);
            modStrumentazioneStage.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inizializzaGraficaPulsanti();
        selezDipendenteBtn.setOnAction(event -> mostraSelezDipendente());
        selezSpazioBtn.setOnAction(event -> mostraSelezSpazio());
        annullaBtn.setOnAction(event -> modStrumentazioneStage.close());
    }

    private void inizializzaGraficaPulsanti() {

        /* ---- SalvaDipendenteBtn ----*/
        UtilityGrafiche.impostaIconaPulsante(apportaModificheBtn, UtilityGrafiche.SAVE_ICON);

        /* --- annullaOperazioneBtn ---*/
        UtilityGrafiche.impostaIconaPulsante(annullaBtn, UtilityGrafiche.ANNULLA_ICON);
    }

}
