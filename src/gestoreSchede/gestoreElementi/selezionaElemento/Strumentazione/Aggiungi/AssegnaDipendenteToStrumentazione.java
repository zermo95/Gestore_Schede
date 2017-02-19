package gestoreSchede.gestoreElementi.selezionaElemento.Strumentazione.Aggiungi;

import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.gestoreElementi.aggiungiElemento.aggiungiStrumentazione.CreaStrumentazione;
import gestoreSchede.gestoreElementi.selezionaElemento.SelezionaDipendenteController;
import gestoreSchede.oggetti.elementi.Dipendente;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/*******************
 * © APT Software *
 *******************/

public class AssegnaDipendenteToStrumentazione extends SelezionaDipendenteController implements Initializable {
    protected void selezionaDipendente() {
        Dipendente d = tabellaDipendenti.getSelectionModel().getSelectedItem();
        if (d == null) {
            GestoreAvvisi.nessunElementoSelezionato();
        } else {
            codiceDipendenteSelezionato = d.getCodiceDipendente();
            nomeDipendenteSelezionato = d.getNome() +" " + d.getCognome();
            CreaStrumentazione.instance.setCampoDipendenteSelezionato(nomeDipendenteSelezionato);
            CreaStrumentazione.mostraSchermataPrincipale();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        impostaTabellaDipendenti();
        attivaCampoRicercaDipendenti();
        indietroDipendentiBtn.setOnAction(event -> CreaStrumentazione.mostraSchermataPrincipale());
        selezDipendenteBtn.setOnAction(event -> selezionaDipendente());
    }
}
