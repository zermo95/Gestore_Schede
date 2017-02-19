package gestoreSchede.gestoreElementi.selezionaElemento.Strumentazione.Modifica;

import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.gestoreElementi.modificaElemento.modificaStrumentazione.ModificaStrumentazione;
import gestoreSchede.gestoreElementi.selezionaElemento.SelezionaDipendenteController;
import gestoreSchede.oggetti.elementi.Dipendente;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/*******************
 * © APT Software *
 *******************/

public class ModificaAssegnazioneDipendenteToStrumentazione extends SelezionaDipendenteController implements Initializable {
    protected void selezionaDipendente() {
        Dipendente d = tabellaDipendenti.getSelectionModel().getSelectedItem();
        if (d == null) {
            GestoreAvvisi.nessunElementoSelezionato();
        } else {
            codiceDipendenteSelezionato = d.getCodiceDipendente();
            nomeDipendenteSelezionato = d.getNome() + " " + d.getCognome();
            ModificaStrumentazione.instance.setCampoDipendenteSelezionato(nomeDipendenteSelezionato);
            ModificaStrumentazione.mostraSchermataPrincipale();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        impostaTabellaDipendenti();
        attivaCampoRicercaDipendenti();
        indietroDipendentiBtn.setOnAction(event -> ModificaStrumentazione.mostraSchermataPrincipale());
        selezDipendenteBtn.setOnAction(event -> selezionaDipendente());
    }
}
