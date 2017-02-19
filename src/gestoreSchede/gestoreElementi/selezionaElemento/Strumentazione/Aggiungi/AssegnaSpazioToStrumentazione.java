package gestoreSchede.gestoreElementi.selezionaElemento.Strumentazione.Aggiungi;

import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.gestoreElementi.aggiungiElemento.aggiungiStrumentazione.CreaStrumentazione;
import gestoreSchede.oggetti.elementi.Spazio;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/*******************
 * © APT Software *
 *******************/

public class AssegnaSpazioToStrumentazione extends gestoreSchede.gestoreElementi.selezionaElemento.SelezionaSpazioController implements Initializable {
    protected void selezionaSpazio() {
        Spazio s = tabellaSpazi.getSelectionModel().getSelectedItem();
        if (s == null) {
            GestoreAvvisi.nessunElementoSelezionato();
        } else {
            codiceSpazioSelezionato = s.getCodiceSpazio();
            nomeSpazioSelezionato = s.getNome();
            CreaStrumentazione.instance.setCampoSpazioSelezionato(nomeSpazioSelezionato);
            CreaStrumentazione.mostraSchermataPrincipale();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        impostaTabellaSpazi();
        attivaCampoRicercaSpazi();
        indietroSpaziBtn.setOnAction(event -> CreaStrumentazione.mostraSchermataPrincipale());
        selezSpazioBtn.setOnAction(event -> selezionaSpazio());
    }

}
