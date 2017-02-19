package gestoreSchede.gestoreElementi.modificaElemento.modificaDipendente;

import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.GUI.schermataPrincipale.GestoreDiSistema;
import gestoreSchede.GUI.utility.UtilityGrafiche;
import gestoreSchede.Main;
import gestoreSchede.gestoreElementi.GestoreDipendenti;
import gestoreSchede.gestoreElementi.selezionaElemento.Dipendente.Modifica.ModificaAssegnazioneSpazioToDipendente;
import gestoreSchede.oggetti.elementi.Dipendente;
import gestoreSchede.oggetti.elementi.Spazio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/*******************
 * © APT Software *
 *******************/

public class ModificaDipendente implements Initializable {
    private static final int SPAZIO_VUOTO = 0;

    /* ------------ Modifica Dipendente ------------*/
    private static Stage modDipStage;
    private static BorderPane pannelloContenitorePane;
    private static BorderPane selezSpazioPane;
    private static BorderPane modDipPane;

    static {
        modDipStage = new Stage();
        pannelloContenitorePane = new BorderPane();
        selezSpazioPane = new BorderPane();
        modDipPane = new BorderPane();
        instance = new ModificaDipendente();
    }

    /**
     * Istanza del controller per la modifica di un dipendente
     **/
    public static ModificaDipendente instance;

    /* ---------- FXML -------------*/
    /* ---- campi di testo ----*/
    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoCognome;
    @FXML
    private RadioButton maleBtn;
    @FXML
    private RadioButton femaleBtn;
    @FXML
    private TextField campoCittaNascita;
    @FXML
    private TextField campoCodiceFiscale;
    @FXML
    private DatePicker campoData;
    @FXML
    private TextField campoNumTelefono;
    @FXML
    private Text textNumeroNonValido;
    @FXML
    private TextField campoEmail;
    @FXML
    private TextField campoResidenza;
    @FXML
    private TextField campoDomicilio;
    @FXML
    private TextField campoMansione;
    @FXML
    private Label campoSpazioSelezionato;
    @FXML
    private Button selezSpazioBtn;

    @FXML
    private Button salvaDipendenteBtn;
    @FXML
    private Button annullaOperazioneBtn;

    private static void mostraModificaDipendente() throws IOException {

        caricaTutteLeSchermateModificaDipendente();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("gestoreElementi/modificaElemento/modificaDipendente/pannelloContenitore.fxml"));
        pannelloContenitorePane = loader.load();

        modDipStage = new Stage();
        modDipStage.initModality(Modality.WINDOW_MODAL);
        modDipStage.initOwner(Main.stagePrincipale);

        Scene scene = new Scene(pannelloContenitorePane);
        modDipStage.setScene(scene);
        modDipStage.setResizable(false);
        modDipStage.setTitle("Modifica Dipendente");
        pannelloContenitorePane.setCenter(modDipPane);
        modDipStage.showAndWait();
    }

    private static void caricaTutteLeSchermateModificaDipendente() throws IOException {
        FXMLLoader loaderSelezSpazi = new FXMLLoader();
        loaderSelezSpazi.setLocation(Main.class.getResource("gestoreElementi/selezionaElemento/Dipendente/Modifica/selezSpazio.fxml"));
        selezSpazioPane = loaderSelezSpazi.load();
    }

    public static void mostraSchermataPrincipale() {
        pannelloContenitorePane.setCenter(modDipPane);
    }

    private static void mostraSelezSpazio() {
        pannelloContenitorePane.setCenter(selezSpazioPane);
    }

    public void setCampoSpazioSelezionato(String s) {
        campoSpazioSelezionato.setText(s);
    }

    // Funzione chiamata dal GestoreDiSistema per la modifica di un dipendente
    public void initData(Dipendente d, BorderPane modDip) throws IOException {
        modDipPane = modDip;

        // Anagrafica
        campoNome.setText(d.getNome());
        campoCognome.setText(d.getCognome());
        // Settaggio del sesso
        if (Objects.equals(d.getSesso(), "F")) {
            femaleBtn.setSelected(true);
            maleBtn.setSelected(false);
        } else {
            maleBtn.setSelected(true);
            femaleBtn.setSelected(false);
        }
        campoCittaNascita.setText(d.getCittaNascita());
        campoCodiceFiscale.setText(d.getCodiceFiscale());
        campoData.setValue(d.getDataNascita().toLocalDate());
        campoNumTelefono.setText(d.getNumTelefono());
        campoEmail.setText(d.getEmail());
        campoResidenza.setText(d.getCittaDomicilio());
        campoDomicilio.setText(d.getIndirizzoDomicilio());
        campoMansione.setText(d.getMansione());
        if (d.getCodiceSpazio() == SPAZIO_VUOTO) {
            campoSpazioSelezionato.setText("NESSUNO");
        } else {
            campoSpazioSelezionato.setText(cercaSpazioSelezionato(d.getCodiceSpazio()).get().getNome());
        }

        // Azione del pulsante Apporta Modifiche Dipendente
        salvaDipendenteBtn.setOnAction(event -> {
            try {
                apportaModificheDipendente(d);
            } catch (IOException e) {
                GestoreAvvisi.mostraErrore("");
            }
        });

        mostraModificaDipendente();
    }

    public static Optional<Spazio> cercaSpazioSelezionato(int codiceS) {
        for (Spazio s : GestoreDiSistema.getListaSpazi()) {
            if (s.getCodiceSpazio() == codiceS) {
                return Optional.of(s);
            }
        }
        return Optional.empty();
    }

    private boolean isAlmenoUnCampoVuoto() {

               /* Controlli anagrafica */
        return campoNome.getText().isEmpty() ||
                campoCognome.getText().isEmpty() ||
                campoCodiceFiscale.getText().isEmpty() ||
                campoData.getValue() == null ||

                /* Controllo contatti/dati lavorativi */
                campoNumTelefono.getText().isEmpty() ||
                campoMansione.getText().isEmpty();
    }

    private void apportaModificheDipendente(Dipendente d) throws IOException {
        if (isAlmenoUnCampoVuoto()) {
            GestoreAvvisi.avvisoCampoVuoto();
        } else {
            d.setNome(campoNome.getText());
            d.setCognome(campoCognome.getText());
            if (maleBtn.isSelected()) {
                d.setSesso("M");
            } else {
                d.setSesso("F");
            }
            d.setCittaNascita(campoCittaNascita.getText());
            d.setCodiceFiscale(campoCodiceFiscale.getText());
            d.setDataNascita(Date.valueOf(campoData.getValue()));
            d.setEmail(campoEmail.getText());
            d.setCittaDomicilio(campoResidenza.getText());
            d.setIndirizzoDomicilio(campoDomicilio.getText());
            d.setMansione(campoMansione.getText());
            d.setCodiceSpazio(ModificaAssegnazioneSpazioToDipendente.getCodiceSpazioSelezionato());

            // Controllo numero di telefono e avvio delle modifiche
            if (GestoreDipendenti.isNumeroDiTelefonoCorretto(campoNumTelefono.getText())) {
                d.setNumTelefono(campoNumTelefono.getText());
                GestoreDipendenti.aggiornaDipendente(d);
                modDipStage.close();
            }
        }
    }

    @FXML
    private void apportaModifiche() {
        modDipStage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inizializzaGraficaPulsanti();
        inizializzaControlloNumeroTelefono();
        selezSpazioBtn.setOnAction(event -> mostraSelezSpazio());
        annullaOperazioneBtn.setOnAction(event -> modDipStage.close());
    }

    private void inizializzaControlloNumeroTelefono() {

        // Controllo continuo del numero di telefono
        campoNumTelefono.textProperty().addListener((observable, oldValue, newValue) -> {
            if (campoNumTelefono.getText().equals("")) {
                textNumeroNonValido.setText("");
            } else if (GestoreDipendenti.isNumeroDiTelefonoCorretto(campoNumTelefono.getText())) {
                textNumeroNonValido.setText("");
            } else {
                textNumeroNonValido.setText("Numero non valido");
            }
        });
    }

    private void inizializzaGraficaPulsanti() {

        /* ---- SalvaDipendenteBtn ----*/
        UtilityGrafiche.impostaIconaPulsante(salvaDipendenteBtn, UtilityGrafiche.SAVE_ICON);

        /* --- annullaOperazioneBtn ---*/
        UtilityGrafiche.impostaIconaPulsante(annullaOperazioneBtn, UtilityGrafiche.ANNULLA_ICON);

    }

}
