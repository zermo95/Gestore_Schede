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
    
    /* -------- Indici della ChoiceBox ---------*/
    private static final int CHOICEBOX_INDEX_NESSUNO = 0;
    private static final int CHOICEBOX_INDEX_DIRETTORE = 1;
    private static final int CHOICEBOX_INDEX_PROGRAMMATORE = 2;
    private static final int CHOICEBOX_INDEX_MANAGER = 3;
    private static final int CHOICEBOX_INDEX_SEGRETARIO = 4;
    private static final int CHOICEBOX_INDEX_CONTABILE = 5;
    private static final int CHOICEBOX_INDEX_BOSS = 6;

    /* -------- Valori della ChoicheBox ---------*/
    private static final String CHOICHEBOX_STRING_NESSUNO = "Seleziona";
    private static final String CHOICHEBOX_STRING_DIRETTORE = "Direttore";
    private static final String CHOICHEBOX_STRING_PROGRAMMATORE = "Programmatore";
    private static final String CHOICHEBOX_STRING_MANAGER = "Manager";
    private static final String CHOICHEBOX_STRING_SEGRETARIO = "Segretario";
    private static final String CHOICHEBOX_STRING_CONTABILE = "Contabile";
    private static final String CHOICHEBOX_STRING_BOSS = "Boss";

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
    private ChoiceBox<String> choiceBoxMansione = new ChoiceBox<>();
    @FXML
    private Label campoSpazioSelezionato;
    @FXML
    private Button selezSpazioBtn;

    @FXML
    private Button salvaDipendenteBtn;
    @FXML
    private Button annullaOperazioneBtn;
    
    private void inizializzaChoiceBox() {
        // Imposta la ChoicheBox
    	choiceBoxMansione.getItems().addAll(CHOICHEBOX_STRING_NESSUNO, CHOICHEBOX_STRING_DIRETTORE,
    			CHOICHEBOX_STRING_PROGRAMMATORE, CHOICHEBOX_STRING_MANAGER, CHOICHEBOX_STRING_SEGRETARIO, CHOICHEBOX_STRING_CONTABILE, CHOICHEBOX_STRING_BOSS);
    	
    	// Imposta elemento di default
    	choiceBoxMansione.setValue(CHOICHEBOX_STRING_NESSUNO);
    	
    }


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
        choiceBoxMansione.setValue(d.getMansione());
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
                (choiceBoxMansione.getSelectionModel().getSelectedIndex() == CHOICEBOX_INDEX_NESSUNO);
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
            
            int indexChoiceBoxSelezionato = choiceBoxMansione.getSelectionModel().getSelectedIndex();
            switch (indexChoiceBoxSelezionato) {
                case CHOICEBOX_INDEX_DIRETTORE:
                	d.setMansione(CHOICHEBOX_STRING_DIRETTORE);
                    break;
                case CHOICEBOX_INDEX_PROGRAMMATORE:
                	d.setMansione(CHOICHEBOX_STRING_PROGRAMMATORE);            	
				    break;
				case CHOICEBOX_INDEX_MANAGER:
					d.setMansione(CHOICHEBOX_STRING_MANAGER);            	
				    break;
				case CHOICEBOX_INDEX_SEGRETARIO:
					d.setMansione(CHOICHEBOX_STRING_SEGRETARIO);
				    break;
				case CHOICEBOX_INDEX_CONTABILE:
					d.setMansione(CHOICHEBOX_STRING_CONTABILE);
				    break;
				case CHOICEBOX_INDEX_BOSS:
					d.setMansione(CHOICHEBOX_STRING_BOSS);
				    break;
                default:
                	d.setMansione("");
                    break;
            }
            
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
        inizializzaChoiceBox();
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
