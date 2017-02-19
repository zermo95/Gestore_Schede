package gestoreSchede.gestoreElementi.aggiungiElemento;


import gestoreSchede.GUI.utility.UtilityGrafiche;
import gestoreSchede.Main;
import gestoreSchede.gestoreElementi.aggiungiElemento.aggiungiDipendente.CreaDipendente;
import gestoreSchede.gestoreElementi.aggiungiElemento.aggiungiSpazio.CreaSpazio;
import gestoreSchede.gestoreElementi.aggiungiElemento.aggiungiStrumentazione.CreaStrumentazione;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/*******************
 * © APT Software *
 *******************/
public class AggiungiElementoController implements Initializable {

    @FXML
    private Button tornaIndietroBtn;

    @FXML
    private void tornaIndietro() {
        Main.mostraSchermataPrincipale();
    }

    @FXML
    private void aggiungiDipendente() throws IOException {
        CreaDipendente.mostraAggiungiDipendente();
    }

    @FXML
    private void aggiungiStrumentazione() throws IOException {
        CreaStrumentazione.mostraAggiungiStrumentazione();
    }

    @FXML
    private void aggiungiSpazio() throws IOException {
        CreaSpazio.mostraAggiungiSpazio();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inizializzaGraficaPulsanti();

    }

    private void inizializzaGraficaPulsanti(){
        /* ---- tornaIndietroBtn ----*/
        UtilityGrafiche.impostaIconaPulsante(tornaIndietroBtn, UtilityGrafiche.TORNA_INDIETRO_ICON);
    }
}
