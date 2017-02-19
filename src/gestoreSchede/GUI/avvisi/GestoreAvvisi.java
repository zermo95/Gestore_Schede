package gestoreSchede.GUI.avvisi;

import gestoreSchede.GUI.schermataPrincipale.GestoreDiSistema;
import gestoreSchede.GUI.utility.UtilityGrafiche;
import gestoreSchede.gestoreSchedeDescrittive.GestoreSchede;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.Optional;

/*******************
 * © APT Software *
 *******************/
public class GestoreAvvisi {

    public static void avvisoCampoVuoto() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attenzione");
        alert.setHeaderText("Ops, hai dimenticato qualcosa :)");
        alert.setContentText("Tutti i campi contrassegnati con * devono essere compilati");
        alert.showAndWait();
    }

    public static void erroreConnessioneDatabase() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText("Errore di connessione con il database");
        alert.setContentText("Assicurati che MySQL sia in esecuzione");

        alert.showAndWait();
    }

    public static void nessunElementoSelezionato() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attenzione");
        alert.setHeaderText("Nessun elemento selezionato");
        alert.setContentText("Prima di procedere, seleziona un elemento dalla tabella");

        alert.showAndWait();
    }


    public static void chiediConfermaEliminazione(GestoreDiSistema m) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Attenzione");
        alert.setHeaderText("Conferma eliminazione");
        alert.setContentText("Sei sicuro di volere eliminare definitivamente l'elemento selezionato?");

        // settaggi grafici
        UtilityGrafiche.impostaIconaAlert(alert, UtilityGrafiche.GARBAGE_ICON);

       /* ButtonType eliminaBtn = new ButtonType("Elimina");
        ButtonType annullaBtn = new ButtonType("Annulla");
        ButtonType closeBtn = new ButtonType("Chiudi", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(annullaBtn,eliminaBtn);*/

        // gestione azione dei pulsanti
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                //m.rimuoviElementoSelezionatoDalDatabase();
                GestoreSchede.rimuoviSchedaDB();
            } catch (IOException ignored) {
            }
        } else {
            alert.close();
        }

    }

    public static void schedaEsportataCorrettamente(String percorsoOutput) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informazione");
        alert.setHeaderText("Scheda esportata correttamente");
        alert.setContentText("Puoi trovare la tua scheda in\n" + percorsoOutput);

        // settaggi grafici
        UtilityGrafiche.impostaIconaAlert(alert, UtilityGrafiche.OK_ICON);

        alert.showAndWait();

    }

    public static void mostraErrore(String messaggio){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText("Si è verificato un errore");
        alert.setContentText(messaggio);

        alert.showAndWait();
    }

}
