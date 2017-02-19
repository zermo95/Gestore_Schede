package gestoreSchede.gestoreElementi.selezionaElemento.Strumentazione.Modifica;

import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.gestoreElementi.modificaElemento.modificaStrumentazione.ModificaStrumentazione;
import gestoreSchede.gestoreElementi.selezionaElemento.SelezionaSpazioController;
import gestoreSchede.oggetti.elementi.Spazio;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/*******************
 * © APT Software *
 *******************/

public class ModificaAssegnazioneSpazioToStrumentazione extends SelezionaSpazioController implements Initializable {
    protected void selezionaSpazio() {
        Spazio s = tabellaSpazi.getSelectionModel().getSelectedItem();
        if (s == null) {
            GestoreAvvisi.nessunElementoSelezionato();
        } else {
            codiceSpazioSelezionato = s.getCodiceSpazio();
            nomeSpazioSelezionato = s.getNome();
            ModificaStrumentazione.instance.setCampoSpazioSelezionato(nomeSpazioSelezionato);
            ModificaStrumentazione.mostraSchermataPrincipale();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        impostaTabellaSpazi();
        attivaCampoRicercaSpazi();
        indietroSpaziBtn.setOnAction(event -> ModificaStrumentazione.mostraSchermataPrincipale());
        selezSpazioBtn.setOnAction(event -> selezionaSpazio());
    }

}
