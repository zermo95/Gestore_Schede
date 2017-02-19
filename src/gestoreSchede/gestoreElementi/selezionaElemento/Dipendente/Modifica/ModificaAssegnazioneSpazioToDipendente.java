package gestoreSchede.gestoreElementi.selezionaElemento.Dipendente.Modifica;

import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.gestoreElementi.modificaElemento.modificaDipendente.ModificaDipendente;
import gestoreSchede.oggetti.elementi.Spazio;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/*******************
 * © APT Software *
 *******************/

public class ModificaAssegnazioneSpazioToDipendente extends gestoreSchede.gestoreElementi.selezionaElemento.SelezionaSpazioController implements Initializable {
    protected void selezionaSpazio() {
        Spazio s = tabellaSpazi.getSelectionModel().getSelectedItem();
        if (s == null) {
            GestoreAvvisi.nessunElementoSelezionato();
        } else {
            codiceSpazioSelezionato = s.getCodiceSpazio();
            nomeSpazioSelezionato = s.getNome();
            ModificaDipendente.instance.setCampoSpazioSelezionato(nomeSpazioSelezionato);
            ModificaDipendente.mostraSchermataPrincipale();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        impostaTabellaSpazi();
        attivaCampoRicercaSpazi();
        indietroSpaziBtn.setOnAction(event -> ModificaDipendente.mostraSchermataPrincipale());
        selezSpazioBtn.setOnAction(event -> selezionaSpazio());
    }

}
