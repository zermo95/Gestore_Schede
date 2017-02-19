package gestoreSchede.gestoreElementi.selezionaElemento.Dipendente.Aggiungi;

import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.gestoreElementi.aggiungiElemento.aggiungiDipendente.CreaDipendente;
import gestoreSchede.oggetti.elementi.Spazio;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/*******************
 * © APT Software *
 *******************/

public class AssegnaSpazioToDipendente extends gestoreSchede.gestoreElementi.selezionaElemento.SelezionaSpazioController implements Initializable {
    protected void selezionaSpazio() {
        Spazio s = tabellaSpazi.getSelectionModel().getSelectedItem();
        if (s == null) {
            GestoreAvvisi.nessunElementoSelezionato();
        } else {
            codiceSpazioSelezionato = s.getCodiceSpazio();
            nomeSpazioSelezionato = s.getNome();
            CreaDipendente.instance.setCampoSpazioSelezionato(nomeSpazioSelezionato);
            CreaDipendente.mostraSchermataPrincipale();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        impostaTabellaSpazi();
        attivaCampoRicercaSpazi();
        indietroSpaziBtn.setOnAction(event -> CreaDipendente.mostraSchermataPrincipale());
        selezSpazioBtn.setOnAction(event -> selezionaSpazio());
    }

}
