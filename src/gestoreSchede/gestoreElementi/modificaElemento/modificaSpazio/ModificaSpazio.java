package gestoreSchede.gestoreElementi.modificaElemento.modificaSpazio;

import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.GUI.utility.UtilityGrafiche;
import gestoreSchede.gestoreElementi.GestoreSpazi;
import gestoreSchede.oggetti.elementi.Spazio;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/*******************
 * © APT Software *
 *******************/

public class ModificaSpazio implements Initializable {

    /* ------------ FXML -------------*/
    @FXML
    private TextField campoNomeSpazio;
    @FXML
    private TextField campoUbicazioneSpazio;
    @FXML
    private TextField campoCapienzaSpazio;
    @FXML
    private TextField campoNumPorteSpazio;
    @FXML
    private TextField campoNumFinestreSpazio;
    @FXML
    private TextArea campoAltreInfoSpazio;

    @FXML
    private Button salvaSpazioBtn;
    @FXML
    private Button annullaBtn;


    // Funzione chiamata dal GestoreDiSistema per la modifica di un dipendente
    public void initData(Spazio spazio, Stage s) {
        campoNomeSpazio.setText(spazio.getNome());
        campoUbicazioneSpazio.setText(spazio.getUbicazione());
        campoCapienzaSpazio.setText(String.valueOf(spazio.getCapienza()));
        campoNumPorteSpazio.setText(String.valueOf(spazio.getNumeroPorte()));
        campoNumFinestreSpazio.setText(String.valueOf(spazio.getNumeroFinestre()));
        campoAltreInfoSpazio.setText(spazio.getAltreInformazioni());

        // Azione del pulsante Apporta Modifiche Dipendente
        salvaSpazioBtn.setOnAction(event -> {
            try {
                apportaModificheSpazio(spazio, s);
            } catch (IOException e) {
                GestoreAvvisi.mostraErrore("");
            }
        });

        annullaBtn.setOnAction(event -> s.close());
    }

    @FXML
    private void apportaModificheSpazio(Spazio spazio, Stage s) throws IOException {
        if (campoNomeSpazio.getText().isEmpty() || campoUbicazioneSpazio.getText().isEmpty()) {
            GestoreAvvisi.avvisoCampoVuoto();
        } else {
            spazio.setNome(campoNomeSpazio.getText());
            spazio.setUbicazione(campoUbicazioneSpazio.getText());
            if (!Objects.equals(campoCapienzaSpazio.getText(), "")) {
                spazio.setCapienza(Integer.parseInt(campoCapienzaSpazio.getText()));
            } else {
                spazio.setCapienza(0);
            }
            if (!Objects.equals(campoNumPorteSpazio.getText(), "")) {
                spazio.setNumeroPorte(Integer.parseInt(campoNumPorteSpazio.getText()));
            } else {
                spazio.setNumeroPorte(0);
            }
            if (!Objects.equals(campoNumFinestreSpazio.getText(), "")) {
                spazio.setNumeroFinestre(Integer.parseInt(campoNumFinestreSpazio.getText()));
            } else {
                spazio.setNumeroFinestre(0);
            }
            spazio.setAltreInformazioni(campoAltreInfoSpazio.getText());
            GestoreSpazi.aggiornaSpazio(spazio);
            s.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inizializzaGraficaPulsanti();
    }

    private void inizializzaGraficaPulsanti() {
        /* ---- SalvaBtn ----*/
        UtilityGrafiche.impostaIconaPulsante(salvaSpazioBtn, UtilityGrafiche.SAVE_ICON);

        /* --- annullaOperazioneBtn ---*/
        UtilityGrafiche.impostaIconaPulsante(annullaBtn, UtilityGrafiche.ANNULLA_ICON);
    }
}
