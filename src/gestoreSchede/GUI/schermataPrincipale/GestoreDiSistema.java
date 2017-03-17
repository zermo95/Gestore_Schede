package gestoreSchede.GUI.schermataPrincipale;

import gestoreSchede.Main;
import gestoreSchede.gestoreElementi.modificaElemento.modificaDipendente.ModificaDipendente;
import gestoreSchede.gestoreElementi.modificaElemento.modificaSpazio.ModificaSpazio;
import gestoreSchede.gestoreElementi.modificaElemento.modificaStrumentazione.ModificaStrumentazione;
import gestoreSchede.gestoreSchedeDescrittive.aggiungiScheda.AggiungiScheda;
import gestoreSchede.gestoreSchedeDescrittive.esportaScheda.EsportaScheda;
import gestoreSchede.gestoreSchedeDescrittive.modificaScheda.ModificaScheda;
import gestoreSchede.oggetti.SchedaDescrittiva;
import gestoreSchede.oggetti.elementi.Dipendente;
import gestoreSchede.oggetti.elementi.Spazio;
import gestoreSchede.oggetti.elementi.Strumentazione;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/*******************
 * © APT Software *
 *******************/
public class GestoreDiSistema implements Initializable, Observer {

    private static GestoreDiSistema instance = new GestoreDiSistema();

    /* -------- Indici della ChoiceBox ---------*/
    private static final int CHOICEBOX_INDEX_DIPENDENTI = 0;
    private static final int CHOICEBOX_INDEX_STRUMENTAZIONI = 1;
    private static final int CHOICEBOX_INDEX_SPAZI = 2;

    /* -------- Valori della ChoicheBox ---------*/
    private static final String CHOICHEBOX_STRING_DIPENDENTI = "DIPENDENTI";
    private static final String CHOICHEBOX_STRING_STRUMENTAZIONI = "STRUMENTAZIONI";
    private static final String CHOICHEBOX_STRING_SPAZI = "SPAZI";

    /*------------------- Lista Schede ---------------------*/
    private static ObservableList<SchedaDescrittiva> listaSchede = FXCollections.observableArrayList();

    /*------------------- Lista Dipendenti ---------------------*/
    private static ObservableList<Dipendente> listaDipendenti = FXCollections.observableArrayList();

    /*------------------- Lista Spazi ---------------------*/
    private static ObservableList<Spazio> listaSpazi = FXCollections.observableArrayList();

    /*----------------- Lista Strumentazioni ---------------------*/
    private static ObservableList<Strumentazione> listaStrumentazioni = FXCollections.observableArrayList();


    /* ------------------------------ FXML ------------------------------ */
    @FXML
    private TextField campoDiRicercaElementi;
    @FXML
    private TextField campoDiRicercaSchede;
    @FXML
    private BorderPane borderPaneElementi;
    @FXML
    private ChoiceBox<String> choiceBoxElementi = new ChoiceBox<>();
    @FXML
    private Tab tabSchede;

    /* ------ Tabella Schede -------*/
    @FXML
    private TableView<SchedaDescrittiva> tabellaSchede;
    @FXML
    private TableColumn<SchedaDescrittiva, Integer> colonnaIDScheda;
    @FXML
    private TableColumn<SchedaDescrittiva, String> colonnaNomeScheda;
    @FXML
    private TableColumn<SchedaDescrittiva, Date> colonnaDataCreazioneScheda;
    @FXML
    private TableColumn<SchedaDescrittiva, String> colonnaEnteRichiedente;

    /* ------ Tabella Dipendenti -------*/
    @FXML
    private TableView<Dipendente> tabellaDipendenti;
    @FXML
    private TableColumn<Dipendente, String> colonnaNomeDipendente;
    @FXML
    private TableColumn<Dipendente, String> colonnaCognomeDipendente;
    @FXML
    private TableColumn<Dipendente, Date> colonnaDataNascitaDipendente;
    @FXML
    private TableColumn<Dipendente, String> colonnaMansioneDipendente;
    @FXML
    private TableColumn<Dipendente, String> colonnaNumTelefonoDipendente;

    /* ------ Tabella Strumentazioni ------- */
    //  Le colonne sono dichiarate all'interno di impostaTabellaStrumentazioni()
    private TableView<Strumentazione> tabellaStrumentazioni = new TableView<>();

    /* ------ Tabella Spazi ------- */
    //  Le colonne sono dichiarate all'interno di impostaTabellaSpazi()
    private TableView<Spazio> tabellaSpazi = new TableView<>();

    /* ----- selezione tema da parte dell'utente ---- */
    @FXML
    private void setLookModena() {
        Main.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
    }

    @FXML
    private void setLookCaspian() {
        Main.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
    }

    @FXML
    private void mostraSchermataInfo() {
        try {
            Main.mostraSchermataInfo();
        } catch (IOException e) {
            gestoreSchede.GUI.avvisi.GestoreAvvisi.mostraErrore("Si è verificato un errore");
        }
    }

    @FXML
    private void chiudiApplicazione() throws SQLException {
        Main.stagePrincipale.close();
        Connection con = gestoreSchede.database.Database.getConnessione();
        try {
            con.close();
        } catch (SQLException e) {
            gestoreSchede.GUI.avvisi.GestoreAvvisi.mostraErrore("Si è verificato un errore durante la chiusura dell'applicazione");
        }
    }

    public static ObservableList<Dipendente> getListaDipendenti() {
        return listaDipendenti;
    }

    public static ObservableList<Strumentazione> getListaStrumentazioni() {
        return listaStrumentazioni;
    }

    public static ObservableList<Spazio> getListaSpazi() {
        return listaSpazi;
    }

    public static GestoreDiSistema getInstance() {
        return instance;
    }

    public static void setInstance(GestoreDiSistema m) {
        instance = m;
    }


    public static void aggiungiStrumentazione(Strumentazione s) {
        listaStrumentazioni.add(s);
        //Dopo aver aggiungo il dipendente torna alla schermata principale
        Main.mostraSchermataPrincipale();
    }

    public static void aggiungiScheda(SchedaDescrittiva s) {
        listaSchede.add(s);
        //Dopo aver aggiungo il dipendente torna alla schermata principale
        Main.mostraSchermataPrincipale();
    }

    public static void aggiungiSpazio(Spazio s) {
        listaSpazi.add(s);
        //Dopo aver aggiungo il dipendente torna alla schermata principale
        Main.mostraSchermataPrincipale();
    }

    public static void aggiungiDipendente(Dipendente d) {
        listaDipendenti.add(d);
        //Dopo aver aggiungo il dipendente torna alla schermata principale
        Main.mostraSchermataPrincipale();
    }

    /*------------- Inizializzazione --------------*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        impostaTabellaSchedeDescrittive();
        impostaTabellaDipendenti();
        impostaTabellaStrumentazioni();
        impostaTabellaSpazi();
        inizializzaChoiceBox();
        attivaCampoRicercaERiempiTabelle();
    }

    private void impostaTabellaSchedeDescrittive() {

        // Imposta tabella per le schede
        colonnaIDScheda.setCellValueFactory(new PropertyValueFactory<>("id_scheda"));
        colonnaNomeScheda.setCellValueFactory(new PropertyValueFactory<>("nomeScheda"));
        colonnaDataCreazioneScheda.setCellValueFactory(new PropertyValueFactory<>("dataCreazioneScheda"));
        colonnaEnteRichiedente.setCellValueFactory(new PropertyValueFactory<>("enteRichiedente"));


        // Aggiungi le schede alla tabella
        // tabellaSchede.setItems(listaSchede);
    }

    private void impostaTabellaDipendenti() {

        // Imposta tabella per dipendenti
        colonnaNomeDipendente.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaCognomeDipendente.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        colonnaDataNascitaDipendente.setCellValueFactory(new PropertyValueFactory<>("dataNascita"));
        colonnaMansioneDipendente.setCellValueFactory(new PropertyValueFactory<>("mansione"));
        colonnaNumTelefonoDipendente.setCellValueFactory(new PropertyValueFactory<>("numTelefono"));

        // Aggiungi i dipendenti alla tabella (non abilitato perchè
        // ci pensa la funzione di ricerca ad aggiungere gli elementi nella tabella)

        // tabellaDipendenti.setItems(listaDipendenti);
    }

    private void impostaTabellaStrumentazioni() {

        // Crea la tabella
        TableColumn<Strumentazione, String> colonnaNomeStrumentazione = new TableColumn<>("Nome");
        TableColumn<Strumentazione, String> colonnaMarcaStrumentazione = new TableColumn<>("Marca");
        TableColumn<Strumentazione, String> colonnaModelloStrumentazione = new TableColumn<>("Modello");
        TableColumn<Strumentazione, String> colonnaTipologiaStrumentazione = new TableColumn<>("Tipologia");
        TableColumn<Strumentazione, Integer> colonnaNumeroPezziStrumentazione = new TableColumn<>("Numero Pezzi");
        TableColumn<Strumentazione, Integer> colonnaAnnoAcquistoStrumentazione = new TableColumn<>("Anno Acquisto");

        // Imposta la tabella per le strumentazioni
        colonnaNomeStrumentazione.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaMarcaStrumentazione.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colonnaModelloStrumentazione.setCellValueFactory(new PropertyValueFactory<>("modello"));
        colonnaTipologiaStrumentazione.setCellValueFactory(new PropertyValueFactory<>("tipologia"));
        colonnaNumeroPezziStrumentazione.setCellValueFactory(new PropertyValueFactory<>("numeroPezzi"));
        colonnaAnnoAcquistoStrumentazione.setCellValueFactory(new PropertyValueFactory<>("annoAcquisto"));

        // Aggiungi le colonne alla tabella
        //noinspection unchecked
        tabellaStrumentazioni.getColumns().addAll(colonnaNomeStrumentazione, colonnaMarcaStrumentazione,
                colonnaModelloStrumentazione, colonnaTipologiaStrumentazione,
                colonnaNumeroPezziStrumentazione, colonnaAnnoAcquistoStrumentazione);

        // Imposta le dimensioni della tabella in modo da occupare tutta la finestra
        tabellaStrumentazioni.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Aggiungi strumentazioni alla tabella
        // tabellaStrumentazioni.setItems(listaStrumentazioni);

    }

    private void impostaTabellaSpazi() {
        // Crea la tabella
        TableColumn<Spazio, String> colonnaNomeSpazio = new TableColumn<>("Nome");
        TableColumn<Spazio, String> colonnaUbicazioneSpazio = new TableColumn<>("Ubicazione");
        TableColumn<Spazio, Integer> colonnaCapienzaSpazio = new TableColumn<>("Capienza");
        //colonnaNumPorteSpazio = new TableColumn<>("Numero Porte");
        //colonnaNumFinestreSpazio = new TableColumn<>("Numero Finestre");

        // Imposta la tabella per le strumentazioni
        colonnaNomeSpazio.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colonnaUbicazioneSpazio.setCellValueFactory(new PropertyValueFactory<>("ubicazione"));
        colonnaCapienzaSpazio.setCellValueFactory(new PropertyValueFactory<>("capienza"));

        // Aggiungi le colonne alla tabella
        //noinspection unchecked
        tabellaSpazi.getColumns().addAll(colonnaNomeSpazio, colonnaUbicazioneSpazio, colonnaCapienzaSpazio);

        // Imposta le dimensioni della tabella in modo da occupare tutta la finestra
        tabellaSpazi.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Aggiungi strumentazioni alla tabella
        // tabellaSpazi.setItems(listaSpazi);

    }

    public static void dbCaricaListe() throws SQLException {
        gestoreSchede.database.Database.caricaListaDipendenti(listaDipendenti);
        gestoreSchede.database.Database.caricaListaSpazi(listaSpazi);
        gestoreSchede.database.Database.caricaListaStrumentazioni(listaStrumentazioni);
        gestoreSchede.database.Database.caricaListaSchedeDescrittive(listaSchede);
    }


    private void inizializzaChoiceBox() {
        // Imposta la ChoicheBox
        choiceBoxElementi.getItems().addAll(CHOICHEBOX_STRING_DIPENDENTI,
                CHOICHEBOX_STRING_STRUMENTAZIONI, CHOICHEBOX_STRING_SPAZI);

        // Imposta elemento di default
        choiceBoxElementi.setValue(CHOICHEBOX_STRING_DIPENDENTI);

        // Cambia tabella al cambio di selezione del tipo dell'elemento (Dipendenti, Strum, Spazi)
        choiceBoxElementi.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {

            int indexChoiceBoxSelezionato = choiceBoxElementi.getSelectionModel().getSelectedIndex();
            switch (indexChoiceBoxSelezionato) {
                case CHOICEBOX_INDEX_DIPENDENTI:
                    borderPaneElementi.setCenter(tabellaDipendenti);
                    break;
                case CHOICEBOX_INDEX_STRUMENTAZIONI:
                    borderPaneElementi.setCenter(tabellaStrumentazioni);
                    break;
                case CHOICEBOX_INDEX_SPAZI:
                    borderPaneElementi.setCenter(tabellaSpazi);
                    break;
                default:
                    break;
            }
        });
    }

    private void attivaCampoRicercaERiempiTabelle() {
        gestoreSchede.GUI.utility.CampoRicerca.attivaCampoDiRicercaSchede(listaSchede, campoDiRicercaSchede, tabellaSchede);
        gestoreSchede.GUI.utility.CampoRicerca.attivaCampoRicercaDipendenti(listaDipendenti, campoDiRicercaElementi, tabellaDipendenti);
        gestoreSchede.GUI.utility.CampoRicerca.attivaCampoRicercaStrumentazioni(listaStrumentazioni, campoDiRicercaElementi, tabellaStrumentazioni);
        gestoreSchede.GUI.utility.CampoRicerca.attivaCampoDiRicercaSpazi(listaSpazi, campoDiRicercaElementi, tabellaSpazi);
    }

    /*-------- APERTURA SCHERMATE MODIFICA --------*/

    private void apriSchermataModificaDip(Dipendente d) throws IOException {
        //Carico la schermata
        FXMLLoader loaderPrincipale = new FXMLLoader();
        loaderPrincipale.setLocation(Main.class.getResource("gestoreElementi/modificaElemento/modificaDipendente/modificaDipendente.fxml"));
        BorderPane modDipPane = loaderPrincipale.load();
        ModificaDipendente dipController = loaderPrincipale.getController();
        ModificaDipendente.instance = dipController;

        //Passo la schermata ed il dipendente a ModificaDipendente
        dipController.initData(d, modDipPane);

    }

    private void apriSchermataModificaSpazio(Spazio s) throws IOException {

        FXMLLoader loaderModificaSpazio = new FXMLLoader(
                Main.class.getResource("gestoreElementi/modificaElemento/modificaSpazio/modificaSpazio.fxml"));
        Stage modificaSpazioStage = new Stage(StageStyle.DECORATED);
        modificaSpazioStage.initModality(Modality.WINDOW_MODAL);
        modificaSpazioStage.initOwner(Main.stagePrincipale);
        modificaSpazioStage.setScene(new Scene(loaderModificaSpazio.load()));
        modificaSpazioStage.setResizable(false);

        // Serve per passare i parametri all'altro Controller
        ModificaSpazio controller = loaderModificaSpazio.getController();
        controller.initData(s, modificaSpazioStage);
        modificaSpazioStage.setTitle("Modifica Spazio");
        modificaSpazioStage.show();

    }

    private void apriSchermataModificaStrumentazione(Strumentazione s) throws IOException {
        //Carico la schermata
        FXMLLoader loaderPrincipale = new FXMLLoader();
        loaderPrincipale.setLocation(Main.class.getResource("gestoreElementi/modificaElemento/modificaStrumentazione/modificaStrumentazione.fxml"));
        BorderPane modStrumentazionePane = loaderPrincipale.load();
        ModificaStrumentazione strumentazioneController = loaderPrincipale.getController();
        ModificaStrumentazione.instance = strumentazioneController;

        //Passo la schermata e la strumetazione a ModificaStrumentazione
        strumentazioneController.initData(s, modStrumentazionePane);
    }

    /* -------------------- ESPORTAZIONE SCHEDE ------------------------- */

    private void apriSchermataEsportaScheda(SchedaDescrittiva s) throws IOException {
        FXMLLoader loaderEsportaScheda = new FXMLLoader(
                Main.class.getResource("gestoreSchedeDescrittive/esportaScheda/esportaScheda.fxml"));
        Stage esportaSchedaStage = new Stage(StageStyle.DECORATED);
        esportaSchedaStage.initModality(Modality.WINDOW_MODAL);
        esportaSchedaStage.initOwner(Main.stagePrincipale);
        esportaSchedaStage.setScene(new Scene(loaderEsportaScheda.load()));


        // Serve per passare i parametri all'altro Controller
        EsportaScheda controller = loaderEsportaScheda.getController();
        controller.initData(s, esportaSchedaStage);
        esportaSchedaStage.setTitle("Anteprima esportazione Scheda");
        esportaSchedaStage.setMinWidth(800);
        esportaSchedaStage.setMinHeight(600);
        esportaSchedaStage.show();

    }

    @FXML
    private void mostraEsportaScheda() throws IOException {
        SchedaDescrittiva scheda = tabellaSchede.getSelectionModel().getSelectedItem();
        if (scheda == null) {
            gestoreSchede.GUI.avvisi.GestoreAvvisi.nessunElementoSelezionato();
        } else {
            apriSchermataEsportaScheda(scheda);
        }
    }

    @FXML
    private void mostraAggiungiScheda() throws IOException {
        AggiungiScheda.mostraAggiungiScheda();
    }

    @FXML
    private void mostraAggiungiElemento() {
        Main.mostraAggiungiElemento();
    }

    @FXML
    private void rimuoviElemento() throws IOException {
        if (nessunElementoSelezionato()) {
            lanciaNessunElementoSelezionato();
        } else {
            gestoreSchede.GUI.avvisi.GestoreAvvisi.chiediConfermaEliminazione(this);
        }
    }

    @FXML
    private void rimuoviScheda() throws IOException {
        SchedaDescrittiva scheda = tabellaSchede.getSelectionModel().getSelectedItem();
        if (scheda == null) {
            lanciaNessunElementoSelezionato();
        } else {
            gestoreSchede.GUI.avvisi.GestoreAvvisi.chiediConfermaEliminazione(this);
        }
    }

    @FXML
    private void modificaScheda() throws IOException {
        SchedaDescrittiva scheda = tabellaSchede.getSelectionModel().getSelectedItem();
        if (scheda == null) {
            lanciaNessunElementoSelezionato();
        } else {
            apriSchermatamodificaScheda(scheda);

        }
    }

    private void apriSchermatamodificaScheda(SchedaDescrittiva scheda) throws IOException {

        ModificaScheda.mostraModificaScheda();
        // Serve per passare i parametri all'altro Controller
        ModificaScheda controller = ModificaScheda.getController();
        controller.initData(scheda);
    }


    public void rimuoviElementoSelezionatoDalDatabase() throws IOException {

        Connection con = gestoreSchede.database.Database.getConnessione();
        if (tabSchede.isSelected()) {
            SchedaDescrittiva scheda = tabellaSchede.getSelectionModel().getSelectedItem();
            String cancellaScheda = "DELETE FROM Scheda\n" +
                    "WHERE CodiceScheda=" + scheda.getId_scheda() + ";";
            try {

                Statement istruzione = con.createStatement();
                istruzione.execute(cancellaScheda);
                listaSchede.remove(scheda);
                refreshListe();

            } catch (SQLException e) {
                gestoreSchede.GUI.avvisi.GestoreAvvisi.mostraErrore("Impossibile rimuovere l'elemento selezionato dal database");
            }


        } else {
            // Serve a distinguere la tabella visualizzata
            int indiceSelezionato = choiceBoxElementi.getSelectionModel().getSelectedIndex();

            switch (indiceSelezionato) {
                case CHOICEBOX_INDEX_DIPENDENTI:
                    Dipendente dipendente = tabellaDipendenti.getSelectionModel().getSelectedItem();
                    gestoreSchede.gestoreElementi.GestoreDipendenti.rimuoviDipendente(dipendente);
                    refreshListe();
                    break;
                case CHOICEBOX_INDEX_SPAZI:
                    Spazio spazio = tabellaSpazi.getSelectionModel().getSelectedItem();
                    gestoreSchede.gestoreElementi.GestoreSpazi.rimuoviSpazio(spazio);
                    refreshListe();
                    break;
                case CHOICEBOX_INDEX_STRUMENTAZIONI:
                    Strumentazione strumentazione = tabellaStrumentazioni.getSelectionModel().getSelectedItem();
                    gestoreSchede.gestoreElementi.GestoreStrumentazioni.rimuoviStrumentazine(strumentazione);
                    refreshListe();
                    break;
                default:
                    lanciaNessunElementoSelezionato();
                    break;
            }
        }
    }

    private boolean nessunElementoSelezionato() {
        int indiceSelezionato = choiceBoxElementi.getSelectionModel().getSelectedIndex();

        switch (indiceSelezionato) {
            case CHOICEBOX_INDEX_DIPENDENTI:
                Dipendente dipendente = tabellaDipendenti.getSelectionModel().getSelectedItem();
                if (dipendente == null) return true;
                break;
            case CHOICEBOX_INDEX_STRUMENTAZIONI:
                Strumentazione strumentazione = tabellaStrumentazioni.getSelectionModel().getSelectedItem();
                if (strumentazione == null) return true;
                break;
            case CHOICEBOX_INDEX_SPAZI:
                Spazio spazio = tabellaSpazi.getSelectionModel().getSelectedItem();
                if (spazio == null) return true;
                break;
            default:
                return false;
        }
        return false;
    }

    @FXML
    private void modificaElemento() throws IOException {

        if (nessunElementoSelezionato())
            lanciaNessunElementoSelezionato();
        else {

            int indiceSelezionato = choiceBoxElementi.getSelectionModel().getSelectedIndex();

            switch (indiceSelezionato) {
                case CHOICEBOX_INDEX_DIPENDENTI:
                    Dipendente dipendente = tabellaDipendenti.getSelectionModel().getSelectedItem();
                    apriSchermataModificaDip(dipendente);
                    break;
                case CHOICEBOX_INDEX_SPAZI:
                    Spazio spazio = tabellaSpazi.getSelectionModel().getSelectedItem();
                    apriSchermataModificaSpazio(spazio);
                    break;
                case CHOICEBOX_INDEX_STRUMENTAZIONI:
                    Strumentazione s = tabellaStrumentazioni.getSelectionModel().getSelectedItem();
                    apriSchermataModificaStrumentazione(s);
                    break;
                default:
                    lanciaNessunElementoSelezionato();
                    break;
            }

        }
    }

    @FXML
    public void mostraInfoElemento() throws IOException {

        if (nessunElementoSelezionato())
            lanciaNessunElementoSelezionato();
        else {

            int indiceSelezionato = choiceBoxElementi.getSelectionModel().getSelectedIndex();

            switch (indiceSelezionato) {

                case CHOICEBOX_INDEX_DIPENDENTI:
                    Dipendente dipendente = tabellaDipendenti.getSelectionModel().getSelectedItem();

                    FXMLLoader loaderInfoDip = new FXMLLoader();
                    loaderInfoDip.setLocation(Main.class.getResource("gestoreElementi/mostraInfoDipendente.fxml"));
                    BorderPane infoDipPane = loaderInfoDip.load();

                    gestoreSchede.gestoreElementi.GestoreDipendenti dipController = loaderInfoDip.getController();

                    //Passo la schermata ed il dipendente al GestoreDipendenti
                    dipController.mostraInfoDipendente(dipendente, infoDipPane);
                    break;


                case CHOICEBOX_INDEX_SPAZI:
                    Spazio spazio = tabellaSpazi.getSelectionModel().getSelectedItem();

                    FXMLLoader loaderInfoSpazio = new FXMLLoader();
                    loaderInfoSpazio.setLocation(Main.class.getResource("gestoreElementi/mostraInfoSpazio.fxml"));
                    BorderPane infoSpazioPane = loaderInfoSpazio.load();

                    gestoreSchede.gestoreElementi.GestoreSpazi spaziController = loaderInfoSpazio.getController();

                    //Passo la schermata ed il dipendente al GestoreDipendenti
                    spaziController.mostraInfoSpazio(spazio, infoSpazioPane);

                    break;
                case CHOICEBOX_INDEX_STRUMENTAZIONI:
                    Strumentazione s = tabellaStrumentazioni.getSelectionModel().getSelectedItem();

                    FXMLLoader loaderInfoStr = new FXMLLoader();
                    loaderInfoStr.setLocation(Main.class.getResource("gestoreElementi/mostraInfoStrumentazione.fxml"));
                    BorderPane infoStrPane = loaderInfoStr.load();

                    gestoreSchede.gestoreElementi.GestoreStrumentazioni strController = loaderInfoStr.getController();

                    //Passo la schermata ed il dipendente al GestoreDipendenti
                    strController.mostraInfoStrumentazione(s, infoStrPane);
                    break;
                default:
                    lanciaNessunElementoSelezionato();
                    break;
            }

        }
    }


    @FXML
    public void mostraInfoScheda() throws IOException {

        if (tabellaSchede.getSelectionModel().getSelectedItem() != null) {
            SchedaDescrittiva scheda = tabellaSchede.getSelectionModel().getSelectedItem();
            FXMLLoader loaderScheda = new FXMLLoader(
                    Main.class.getResource("gestoreSchedeDescrittive/InfoScheda.fxml"));
            Stage schedaStage = new Stage(StageStyle.DECORATED);
            schedaStage.initModality(Modality.WINDOW_MODAL);
            schedaStage.initOwner(Main.stagePrincipale);
            schedaStage.setScene(new Scene(loaderScheda.load()));
            gestoreSchede.gestoreSchedeDescrittive.GestoreSchede controller = loaderScheda.getController();
            controller.initData(scheda, schedaStage);
            schedaStage.setTitle("Informazioni scheda");
            schedaStage.setMinWidth(800);
            schedaStage.setMinHeight(600);
            schedaStage.show();

        } else lanciaNessunElementoSelezionato();
    }

    private void lanciaNessunElementoSelezionato() {
        gestoreSchede.GUI.avvisi.GestoreAvvisi.nessunElementoSelezionato();
    }

    private void refreshListe() {
        listaDipendenti.clear();
        listaStrumentazioni.clear();
        listaSpazi.clear();
        listaSchede.clear();
        try {
            dbCaricaListe();
        } catch (SQLException e) {
            gestoreSchede.GUI.avvisi.GestoreAvvisi.mostraErrore("Si è verificato un errore con il caricamento dei dati");
        }
    }

    // Refresh della tabella ogni volta che viene modificato un elemento
    @Override
    public void update(Observable o, Object arg) {
        tabellaDipendenti.refresh();
        tabellaSpazi.refresh();
        tabellaStrumentazioni.refresh();
        tabellaSchede.refresh();
    }
}
