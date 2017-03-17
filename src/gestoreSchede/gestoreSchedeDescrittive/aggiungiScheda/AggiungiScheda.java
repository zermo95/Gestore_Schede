package gestoreSchede.gestoreSchedeDescrittive.aggiungiScheda;

import gestoreSchede.GUI.schermataPrincipale.GestoreDiSistema;
import gestoreSchede.Main;
import gestoreSchede.gestoreSchedeDescrittive.GestoreSchede;
import gestoreSchede.gestoreSchedeDescrittive.aggiungiScheda.aggDipendenti.AggiungiDipendenteSchedaController;
import gestoreSchede.gestoreSchedeDescrittive.aggiungiScheda.aggSpazi.AggiungiSpaziSchedaController;
import gestoreSchede.gestoreSchedeDescrittive.aggiungiScheda.aggStrumentazioni.AggiungiStrumentazioniSchedaController;
import gestoreSchede.oggetti.SchedaDescrittiva;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/*******************
 * © APT Software *
 *******************/
public class AggiungiScheda {


    private static Stage aggiungiSchedaStage;
    private static BorderPane aggSchedaPane;
    private static BorderPane aggDipendentiPane;
    private static BorderPane aggStrumentiPane;
    private static BorderPane aggSpaziPane;
    private static BorderPane aggTestoFinalePane;
    private static BorderPane pannelloContenitorePane;
    private static String nomeScheda;
    private static String enteRichedente;
    private static String testoIntroduttivo;

    static {
        aggiungiSchedaStage = new Stage();
        aggSchedaPane = new BorderPane();
        aggDipendentiPane = new BorderPane();
        aggStrumentiPane = new BorderPane();
        aggSpaziPane = new BorderPane();
        aggTestoFinalePane = new BorderPane();
        pannelloContenitorePane = new BorderPane();
        nomeScheda = "";
        enteRichedente = "";
        testoIntroduttivo = "";
    }

   /* ------ Benvenuto Creazione Scheda ------- */

    @FXML
    private TextField campoNomeScheda;
    @FXML
    private TextField campoEnteRichiedente;
    @FXML
    private TextArea campoTestoIntroduttivo;

    /* ------ Aggiungi Testo Finale ------- */

    @FXML
    private TextArea campoTestoFinale;


    public static void mostraAggiungiScheda() throws IOException {

        caricaTutteLeSchermateAggiungiScheda();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("gestoreSchedeDescrittive/aggiungiScheda/pannelloContenitore.fxml"));
        pannelloContenitorePane = loader.load();

        aggiungiSchedaStage = new Stage();
        aggiungiSchedaStage.initModality(Modality.WINDOW_MODAL);
        aggiungiSchedaStage.initOwner(Main.stagePrincipale);

        Scene scene = new Scene(pannelloContenitorePane);
        aggiungiSchedaStage.setScene(scene);
        aggiungiSchedaStage.setTitle("Creazione nuova scheda");
        aggiungiSchedaStage.setResizable(false);
        pannelloContenitorePane.setCenter(aggSchedaPane);
        aggiungiSchedaStage.showAndWait();
    }

    private static void caricaTutteLeSchermateAggiungiScheda() throws IOException {

        //Carica Creazione Scheda
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("gestoreSchedeDescrittive/aggiungiScheda/benvenutoCreazioneScheda.fxml"));
        aggSchedaPane = loader.load();

        // Carica Aggiungi Dipendenti
        FXMLLoader loaderAggDipendenti = new FXMLLoader();
        loaderAggDipendenti.setLocation(Main.class.getResource("gestoreSchedeDescrittive/aggiungiScheda/aggDipendenti/aggDipendenti.fxml"));
        aggDipendentiPane = loaderAggDipendenti.load();

        // Carica Aggiungi Strumenti
        FXMLLoader loaderAggStrumenti = new FXMLLoader();
        loaderAggStrumenti.setLocation(Main.class.getResource("gestoreSchedeDescrittive/aggiungiScheda/aggStrumentazioni/aggStrumentazioni.fxml"));
        aggStrumentiPane = loaderAggStrumenti.load();

        // Carica Aggiungi Spazi
        FXMLLoader loaderAggSpazi = new FXMLLoader();
        loaderAggSpazi.setLocation(Main.class.getResource("gestoreSchedeDescrittive/aggiungiScheda/aggSpazi/aggSpazi.fxml"));
        aggSpaziPane = loaderAggSpazi.load();

        // Carica Aggiungi testo finale
        FXMLLoader loaderAggTestoFinale = new FXMLLoader();
        loaderAggTestoFinale.setLocation(Main.class.getResource("gestoreSchedeDescrittive/aggiungiScheda/aggTestoFinale.fxml"));
        aggTestoFinalePane = loaderAggTestoFinale.load();


    }


    public static void mostraCrezioneScheda() {
        pannelloContenitorePane.setCenter(aggSchedaPane);
    }

    public static void mostraAggDipendente() {
        pannelloContenitorePane.setCenter(aggDipendentiPane);
    }

    public static void mostraAggStrumentazione() {
        pannelloContenitorePane.setCenter(aggStrumentiPane);
    }

    public static void mostraAggSpazi() {
        pannelloContenitorePane.setCenter(aggSpaziPane);
    }

    public static void mostraAggiungiTestoFinale() {
        pannelloContenitorePane.setCenter(aggTestoFinalePane);
    }

    @FXML
    private void mostraAggiungiDipendente() throws IOException {
        nomeScheda = campoNomeScheda.getText();
        enteRichedente = campoEnteRichiedente.getText();
        testoIntroduttivo = campoTestoIntroduttivo.getText();
        pannelloContenitorePane.setCenter(aggDipendentiPane);
    }


    @FXML
    private void mostraAggiungiSpazi() {
        pannelloContenitorePane.setCenter(aggSpaziPane);
    }

    @FXML
    private void chiudiSchermataAggiungiScheda() {
        aggiungiSchedaStage.close();
    }

    @FXML
    private void salvaScheda() {
        SchedaDescrittiva scheda = new SchedaDescrittiva(nomeScheda, enteRichedente,
                testoIntroduttivo, campoTestoFinale.getText());


        scheda.setListaDipendenti(AggiungiDipendenteSchedaController.getListaDipendenti());
        scheda.setListaSpazi(AggiungiSpaziSchedaController.getListaSpazi());
        scheda.setListaStrumentazioni(AggiungiStrumentazioniSchedaController.getListaStrumentazioni());
        //AGGIUNTA DEL MAINCONTROLLER COME OSSERVATORE DELL'OGGETTO
        GestoreDiSistema GestoreDiSistema = gestoreSchede.GUI.schermataPrincipale.GestoreDiSistema.getInstance();
        scheda.addObserver(GestoreDiSistema);
        GestoreDiSistema.aggiungiScheda(scheda);
        GestoreSchede.aggiungiSchedaDb(scheda);
        //salva e poi chiudi*/
        chiudiSchermataAggiungiScheda();
    }



}
