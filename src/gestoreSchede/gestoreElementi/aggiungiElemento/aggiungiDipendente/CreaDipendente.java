package gestoreSchede.gestoreElementi.aggiungiElemento.aggiungiDipendente;

import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.GUI.utility.UtilityGrafiche;
import gestoreSchede.Main;
import gestoreSchede.gestoreElementi.GestoreDipendenti;
import gestoreSchede.gestoreElementi.selezionaElemento.Dipendente.Aggiungi.AssegnaSpazioToDipendente;
import gestoreSchede.oggetti.elementi.Dipendente;
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
import java.util.ResourceBundle;

/*******************
 * © APT Software *
 *******************/
public class CreaDipendente implements Initializable {

    private static Stage aggDipStage;
    private static BorderPane pannelloContenitorePane;
    private static BorderPane selezSpazioPane;
    private static BorderPane aggDipPane;

    static {
        aggDipStage = new Stage();
        pannelloContenitorePane = new BorderPane();
        selezSpazioPane = new BorderPane();
        aggDipPane = new BorderPane();
        instance = new CreaDipendente();
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
     * Istanza del controller per l'aggiunta di un dipendente
     **/
    public static CreaDipendente instance = null;

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
    private Text textNumeroNonValido;
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


    public static void mostraAggiungiDipendente() throws IOException {

        caricaTutteLeSchermateAggiungiDipendente();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("gestoreElementi/aggiungiElemento/aggiungiDipendente/pannelloContenitore.fxml"));
        pannelloContenitorePane = loader.load();

        aggDipStage = new Stage();
        aggDipStage.initModality(Modality.WINDOW_MODAL);
        aggDipStage.initOwner(Main.stagePrincipale);

        Scene scene = new Scene(pannelloContenitorePane);
        aggDipStage.setScene(scene);
        aggDipStage.setResizable(false);
        aggDipStage.setTitle("Aggiungi Dipendente");
        pannelloContenitorePane.setCenter(aggDipPane);
        aggDipStage.showAndWait();
    }

    private static void caricaTutteLeSchermateAggiungiDipendente() throws IOException {

        FXMLLoader loaderPrincipale = new FXMLLoader();
        loaderPrincipale.setLocation(Main.class.getResource("gestoreElementi/aggiungiElemento/aggiungiDipendente/aggiungiDipendente.fxml"));
        aggDipPane = loaderPrincipale.load();
        CreaDipendente.instance = loaderPrincipale.getController();

        FXMLLoader loaderSelezSpazi = new FXMLLoader();
        loaderSelezSpazi.setLocation(Main.class.getResource("gestoreElementi/selezionaElemento/Dipendente/Aggiungi/selezSpazio.fxml"));
        selezSpazioPane = loaderSelezSpazi.load();
    }

    public static void mostraSchermataPrincipale() {
        pannelloContenitorePane.setCenter(aggDipPane);
    }

    private static void mostraSelezSpazio() {
        pannelloContenitorePane.setCenter(selezSpazioPane);
    }

    public void setCampoSpazioSelezionato(String s) {
        campoSpazioSelezionato.setText(s);
    }

    private boolean isAlmenoUnCampoVuoto() {

               /* Controlli anagrafica */
        return campoNome.getText().isEmpty() ||
                campoCognome.getText().isEmpty() ||
                campoCodiceFiscale.getText().isEmpty() ||
                campoData.getValue() == null ||

                /* Non c'è bisogno di effettuare controlli sul campo del sesso dato che
                per default è selezionato "M"
                 */

                /* Controllo contatti/dati lavorativi */
                campoNumTelefono.getText().isEmpty() ||
                choiceBoxMansione.getSelectionModel().getSelectedIndex()==CHOICEBOX_INDEX_NESSUNO;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inizializzaGraficaPulsanti();
        inizializzaControlloNumeroTelefono();
        inizializzaChoiceBox();
        campoSpazioSelezionato.setText("NESSUNO");
        selezSpazioBtn.setOnAction(event -> mostraSelezSpazio());
    }

    private void inizializzaControlloNumeroTelefono() {
        textNumeroNonValido.setText("");
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

    @FXML
    private void annullaOperazione() {
        aggDipStage.close();
    }


    @FXML
    private void aggiungiDipendente() throws IOException {
        if (isAlmenoUnCampoVuoto()) {

            if (GestoreDipendenti.isNumeroDiTelefonoCorretto(campoNumTelefono.getText())) {
                textNumeroNonValido.setText("");
            } else {
                textNumeroNonValido.setText("Numero non valido");
            }
            GestoreAvvisi.avvisoCampoVuoto();

        } else {
            String codiceFiscaleDipendente = campoCodiceFiscale.getText();
            String nomeDipendente = campoNome.getText();
            String cognomeDipendente = campoCognome.getText();
            String sessoDipendente;
            if (maleBtn.isSelected()) {
                sessoDipendente = "M";
            } else {
                sessoDipendente = "F";
            }
            String cittaNascitaDipendente = campoCittaNascita.getText();
            String cittaDomicilioDipendente = campoDomicilio.getText();
            String indirizzoDomicilioDipendente = campoResidenza.getText();
            String emailDipendente = campoEmail.getText();
            
            String mansioneDipendente;
            
            int indexChoiceBoxSelezionato = choiceBoxMansione.getSelectionModel().getSelectedIndex();
            switch (indexChoiceBoxSelezionato) {
                case CHOICEBOX_INDEX_DIRETTORE:
                	mansioneDipendente = CHOICHEBOX_STRING_DIRETTORE;
                    break;
                case CHOICEBOX_INDEX_PROGRAMMATORE:
                	mansioneDipendente = CHOICHEBOX_STRING_PROGRAMMATORE;            	
				    break;
				case CHOICEBOX_INDEX_MANAGER:
					mansioneDipendente = CHOICHEBOX_STRING_MANAGER;            	
				    break;
				case CHOICEBOX_INDEX_SEGRETARIO:
					mansioneDipendente = CHOICHEBOX_STRING_SEGRETARIO;
				    break;
				case CHOICEBOX_INDEX_CONTABILE:
					mansioneDipendente = CHOICHEBOX_STRING_CONTABILE;
				    break;
				case CHOICEBOX_INDEX_BOSS:
					mansioneDipendente = CHOICHEBOX_STRING_BOSS;
				    break;
                default:
                	mansioneDipendente = "";
                    break;
            }

            String numTelefonoDipendente = campoNumTelefono.getText();
            Date dataNascitaDipendente = Date.valueOf(campoData.getValue());
            int codiceSpazioDipendente = AssegnaSpazioToDipendente.getCodiceSpazioSelezionato();

            // Controllo numero di telefono
            if (GestoreDipendenti.isNumeroDiTelefonoCorretto(campoNumTelefono.getText())) {
                Dipendente nuovoDipendente = new Dipendente(nomeDipendente, cognomeDipendente, sessoDipendente, numTelefonoDipendente,
                        mansioneDipendente, dataNascitaDipendente, codiceFiscaleDipendente, cittaNascitaDipendente,
                        cittaDomicilioDipendente, indirizzoDomicilioDipendente, emailDipendente, codiceSpazioDipendente);

                GestoreDipendenti.aggiungiDipendenteAlDatabase(nuovoDipendente);
                aggDipStage.close();

            } else {
                textNumeroNonValido.setText("Numero non valido");
            }
        }
    }

}
