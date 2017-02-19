package gestoreSchede.gestoreElementi.aggiungiElemento.aggiungiSpazio;


import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.GUI.utility.UtilityGrafiche;
import gestoreSchede.Main;
import gestoreSchede.gestoreElementi.GestoreSpazi;
import gestoreSchede.oggetti.elementi.Spazio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/*******************
 * © APT Software *
 *******************/
public class CreaSpazio implements Initializable {

    private static Stage aggSpazioStage;
    private static FXMLLoader loader ;

    static {
        aggSpazioStage = new Stage();
        loader = new FXMLLoader();
    }

    /*-----------FXML-----------*/
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


    public static void mostraAggiungiSpazio() throws IOException {
        loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("gestoreElementi/aggiungiElemento/aggiungiSpazio/aggiungiSpazio.fxml"));
        BorderPane aggSpazioPane = loader.load();

        aggSpazioStage = new Stage();
        aggSpazioStage.initModality(Modality.WINDOW_MODAL);
        aggSpazioStage.initOwner(Main.stagePrincipale);

        Scene scene = new Scene(aggSpazioPane);
        aggSpazioStage.setScene(scene);
        aggSpazioStage.setResizable(false);
        aggSpazioStage.setTitle("Aggiungi Spazio");
        aggSpazioStage.showAndWait();
    }

    @FXML
    void annullaOperazione() {
        aggSpazioStage.close();
    }

    @FXML
    private void aggiungiSpazio() throws IOException {
        if (campoNomeSpazio.getText().isEmpty() || campoUbicazioneSpazio.getText().isEmpty()) {
            GestoreAvvisi.avvisoCampoVuoto();
        } else {
            String nomeSpazio = campoNomeSpazio.getText();
            String ubicazioneSpazio = campoUbicazioneSpazio.getText();
            int capienzaSpazio;
            if (!Objects.equals(campoCapienzaSpazio.getText(), "")) {
                capienzaSpazio = Integer.parseInt(campoCapienzaSpazio.getText());
            } else {
                capienzaSpazio = 0;
            }
            int numeroPorte;
            if (!Objects.equals(campoNumPorteSpazio.getText(), "")) {
                numeroPorte = Integer.parseInt(campoNumPorteSpazio.getText());
            } else {
                numeroPorte = 0;
            }
            int numeroFinestre;
            if (!Objects.equals(campoNumFinestreSpazio.getText(), "")) {
                numeroFinestre = Integer.parseInt(campoNumFinestreSpazio.getText());
            } else {
                numeroFinestre = 0;
            }
            String altreInfo = campoAltreInfoSpazio.getText();

            // Creazione del nuovo spazio
            Spazio nuovoSpazio = new Spazio(nomeSpazio, ubicazioneSpazio,
                    capienzaSpazio, numeroPorte, numeroFinestre, altreInfo);

            // Aggiunta al database
            GestoreSpazi.aggiungiSpazioAlDatabase(nuovoSpazio);
            aggSpazioStage.close();

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inizializzaGraficaPulsanti();
    }

    private void inizializzaGraficaPulsanti() {

        /* ---- SalvaDipendenteBtn ----*/
        UtilityGrafiche.impostaIconaPulsante(salvaSpazioBtn, UtilityGrafiche.SAVE_ICON);

        /* --- annullaOperazioneBtn ---*/
        UtilityGrafiche.impostaIconaPulsante(annullaBtn, UtilityGrafiche.ANNULLA_ICON);

    }

}
