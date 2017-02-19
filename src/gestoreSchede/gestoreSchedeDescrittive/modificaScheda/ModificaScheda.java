package gestoreSchede.gestoreSchedeDescrittive.modificaScheda;

import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.GUI.utility.UtilityGrafiche;
import gestoreSchede.Main;
import gestoreSchede.database.Database;
import gestoreSchede.gestoreSchedeDescrittive.GestoreSchede;
import gestoreSchede.gestoreSchedeDescrittive.modificaScheda.modificaDipendentiScheda.ModDipendenteSchedaController;
import gestoreSchede.gestoreSchedeDescrittive.modificaScheda.modificaSpaziScheda.ModSpaziSchedaController;
import gestoreSchede.gestoreSchedeDescrittive.modificaScheda.modificaStrumentazioniScheda.ModStrumentazioniSchedaController;
import gestoreSchede.oggetti.SchedaDescrittiva;
import gestoreSchede.oggetti.elementi.Dipendente;
import gestoreSchede.oggetti.elementi.Spazio;
import gestoreSchede.oggetti.elementi.Strumentazione;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/*******************
 * © APT Software *
 *******************/
public class ModificaScheda implements Initializable {
    private static final int SET_VUOTO = 0;

    private SchedaDescrittiva schedaDescrittiva;
    private SchedaDescrittiva schedaCopia;
    private static Stage modificaSchedaStage;
    private static BorderPane modDipendentePane;
    private static BorderPane modStrumentazionePane;
    private static BorderPane pannelloContenitorePane;
    private static BorderPane modSchedaPane;
    private static BorderPane modSpazioPane;
    private static ModificaScheda schedaController;
    private static ModDipendenteSchedaController dipController;
    private static ModStrumentazioniSchedaController strController;
    private static ModSpaziSchedaController spaziController;
    private static Scene scene;

    // inizializzazione campi statici per Kiuwan
    static {
        modificaSchedaStage = new Stage();
        modDipendentePane = new BorderPane();
        modStrumentazionePane = new BorderPane();
        pannelloContenitorePane = new BorderPane();
        modSchedaPane = new BorderPane();
        modSpazioPane = new BorderPane();
        schedaController = new ModificaScheda();
        dipController = new ModDipendenteSchedaController();
        strController = new ModStrumentazioniSchedaController();
        spaziController = new ModSpaziSchedaController();
        scene = new Scene(new BorderPane());
    }


    @FXML
    private TextField campoNomeScheda;
    @FXML
    private TextField campoEnteRichiedente;
    @FXML
    private TextArea campoTestoIntroduttivo;
    @FXML
    private TextArea campoTestoFinale;
    @FXML
    private Button salvaSchedaBtn;
    @FXML
    private Button annullaBtn;


    public static void mostraModificaScheda() throws IOException {
        caricaTutteLeSchermate();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("gestoreSchedeDescrittive/modificaScheda/pannelloContenitore.fxml"));
        pannelloContenitorePane = loader.load();

        modificaSchedaStage = new Stage();
        modificaSchedaStage.initModality(Modality.WINDOW_MODAL);
        modificaSchedaStage.initOwner(Main.stagePrincipale);

        scene = new Scene(pannelloContenitorePane);
        modificaSchedaStage.setScene(scene);
        modificaSchedaStage.setResizable(false);
        modificaSchedaStage.setTitle("Modifica Scheda");
        pannelloContenitorePane.setCenter(modSchedaPane);
        modificaSchedaStage.show();

    }


    // Funzione chiamata dal GestoreDiSistema per la modifica di un dipendente
    public void initData(SchedaDescrittiva scheda) throws IOException {
        campoNomeScheda.setText(scheda.getNomeScheda());
        campoEnteRichiedente.setText(scheda.getEnteRichiedente());
        campoTestoIntroduttivo.setText(scheda.getTestoIntroduttivo());
        campoTestoFinale.setText(scheda.getTestoFinale());
        schedaDescrittiva = scheda;
        schedaCopia = scheda.clone();
        annullaBtn.setOnAction(event -> modificaSchedaStage.close());
    }

    public static ModificaScheda getController() {
        return schedaController;
    }


    private static void caricaTutteLeSchermate() throws IOException {

        FXMLLoader loaderPrincipale = new FXMLLoader();
        loaderPrincipale.setLocation(Main.class.getResource("gestoreSchedeDescrittive/modificaScheda/modificaScheda.fxml"));
        modSchedaPane = loaderPrincipale.load();
        schedaController = loaderPrincipale.getController();

        FXMLLoader loaderModificaDipendenti = new FXMLLoader();
        loaderModificaDipendenti.setLocation(Main.class.getResource("gestoreSchedeDescrittive/modificaScheda/modificaDipendentiScheda/modDipendentiScheda.fxml"));
        modDipendentePane = loaderModificaDipendenti.load();
        dipController = loaderModificaDipendenti.getController();

        FXMLLoader loaderModificaStrumentazioni = new FXMLLoader();
        loaderModificaStrumentazioni.setLocation(Main.class.getResource("gestoreSchedeDescrittive/modificaScheda/modificaStrumentazioniScheda/modStrumentazioniScheda.fxml"));
        modStrumentazionePane = loaderModificaStrumentazioni.load();
        strController = loaderModificaStrumentazioni.getController();

        FXMLLoader loaderModificaSpazi = new FXMLLoader();
        loaderModificaSpazi.setLocation(Main.class.getResource("gestoreSchedeDescrittive/modificaScheda/modificaSpaziScheda/modSpaziScheda.fxml"));
        modSpazioPane = loaderModificaSpazi.load();
        spaziController = loaderModificaSpazi.getController();
    }


    @FXML
    private void modificaDipendentiScheda() throws IOException {
        dipController.initData(schedaCopia);
        pannelloContenitorePane.setCenter(modDipendentePane);

    }

    @FXML
    private void modificaStrumentazioniScheda() throws IOException {
        strController.initData(schedaCopia);
        pannelloContenitorePane.setCenter(modStrumentazionePane);

    }

    @FXML
    private void modificaSpaziScheda() throws IOException {
        spaziController.initData(schedaCopia);
        pannelloContenitorePane.setCenter(modSpazioPane);

    }

    @FXML
    private void apportaModifiche() throws SQLException {
        apportaModificheScheda();
        apportaModificheDipendenti();
        apportaModificheStrumentazioni();
        apportaModificheSpazi();
        modificaSchedaStage.close();
    }

    private void apportaModificheScheda() throws SQLException {
        schedaDescrittiva.setNomeScheda(campoNomeScheda.getText());
        schedaDescrittiva.setTestoIntroduttivo(campoTestoIntroduttivo.getText());
        schedaDescrittiva.setTestoFinale(campoTestoFinale.getText());
        schedaDescrittiva.setEnteRichiedente(campoEnteRichiedente.getText());
        GestoreSchede.aggiornaSchedaDb(schedaDescrittiva);
    }

    private void apportaModificheDipendenti() {
        schedaDescrittiva.setListaDipendenti(schedaCopia.getListaDipendenti());
        ObservableList<Dipendente> listaDipendentiAggiunti = schedaDescrittiva.getListaDipendenti();
        try {
            Connection con = Database.getConnessione();
            Statement insertStatement = con.createStatement();
            Statement queryStatement = con.createStatement();

            eliminaDipendente_Scheda(queryStatement, insertStatement, listaDipendentiAggiunti);
            aggiornaDipendente_Scheda(insertStatement, listaDipendentiAggiunti);

        } catch (SQLException e) {
            GestoreAvvisi.mostraErrore("Impossibile effettuare l'aggiornamento nella tabella Dipendente_Scheda");
        }
    }


    private void eliminaDipendente_Scheda(Statement queryStatement, Statement insertStatement, ObservableList<Dipendente> listaDipendentiAggiunti) throws SQLException {

        //eliminazioni dalla tabella Dipendente_Scheda
        String queryGetDip = "SELECT CodiceDipendente FROM Dipendente_Scheda WHERE CodiceScheda=" + schedaDescrittiva.getId_scheda() + ";";
        ResultSet getDip = queryStatement.executeQuery(queryGetDip);
        int codiceDip;
        boolean presente;
        while (getDip.next()) {
            codiceDip = getDip.getInt(1);
            presente = false;
            for (Dipendente dipendente : listaDipendentiAggiunti) {
                if (codiceDip == dipendente.getCodiceDipendente()) {
                    presente = true;
                    break;
                }
            }
            if (!presente) {
                String queryEliminaDip = "DELETE FROM Dipendente_Scheda" +
                        " WHERE CodiceDipendente=" + codiceDip + " AND CodiceScheda=" + schedaDescrittiva.getId_scheda() + ";";
                insertStatement.execute(queryEliminaDip);
            }

        }

    }

    private void aggiornaDipendente_Scheda(Statement insertStatement, ObservableList<Dipendente> listaDipendentiAggiunti) throws SQLException {
        //update della tabella Dipendente_Scheda
        for (Dipendente dipendente : listaDipendentiAggiunti) {
            String queryControlloDip = "SELECT Count(*) FROM Dipendente_Scheda " +
                    "WHERE CodiceDipendente=" + dipendente.getCodiceDipendente() + " AND CodiceScheda=" + schedaDescrittiva.getId_scheda() + ";";
            ResultSet controlloDip = insertStatement.executeQuery(queryControlloDip);

            int numeroRighe = 0;
            while (controlloDip.next())
                numeroRighe = controlloDip.getInt(1);
            if (numeroRighe == SET_VUOTO) {
                String queryDipendenteScheda = "INSERT INTO Dipendente_Scheda(CodiceDipendente,CodiceScheda) "
                        + "VALUES("
                        + dipendente.getCodiceDipendente() + ",'"
                        + schedaDescrittiva.getId_scheda() + "');";
                insertStatement.execute(queryDipendenteScheda);
            }
        }
    }

    private void apportaModificheStrumentazioni() {
        schedaDescrittiva.setListaStrumentazioni(schedaCopia.getListaStrumentazioni());
        ObservableList<Strumentazione> listaStrumentazioniAggiunte = schedaDescrittiva.getListaStrumentazioni();
        try {
            Connection con = Database.getConnessione();
            Statement insertStatement = con.createStatement();
            Statement queryStatement = con.createStatement();

            eliminaStrumentazione_Scheda(queryStatement, insertStatement, listaStrumentazioniAggiunte);
            aggiornaStrumentazione_Scheda(insertStatement, listaStrumentazioniAggiunte);


        } catch (SQLException e) {
            GestoreAvvisi.mostraErrore("Impossibile effettuare l'aggiornamento della tabella Strumentazione_Scheda");
        }
    }


    private void eliminaStrumentazione_Scheda(Statement queryStatement, Statement insertStatement, ObservableList<Strumentazione> listaStrumentazioniAggiunte) throws SQLException {

        //eliminazioni dalla tabella Strumentazione_Scheda
        String queryGetStr = "SELECT CodiceStrumentazione FROM Strumentazione_Scheda WHERE CodiceScheda=" + schedaDescrittiva.getId_scheda() + ";";
        ResultSet getStr = queryStatement.executeQuery(queryGetStr);
        int codiceStr;
        boolean presente;
        while (getStr.next()) {
            codiceStr = getStr.getInt(1);
            presente = false;
            for (Strumentazione strumentazione : listaStrumentazioniAggiunte) {
                if (codiceStr == strumentazione.getCodiceStrumentazione()) {
                    presente = true;
                    break;
                }
            }
            if (!presente) {
                String queryEliminaStr = "DELETE FROM Strumentazione_Scheda" +
                        " WHERE CodiceStrumentazione=" + codiceStr + " AND CodiceScheda=" + schedaDescrittiva.getId_scheda() + ";";
                insertStatement.execute(queryEliminaStr);
            }

        }

    }

    private void aggiornaStrumentazione_Scheda(Statement insertStatement, ObservableList<Strumentazione> listaStrumentazioniAggiunte) throws SQLException {
        //update della tabella Strumentazione_Scheda
        for (Strumentazione strumentazione : listaStrumentazioniAggiunte) {
            String queryControlloStr = "SELECT Count(*) FROM Strumentazione_Scheda " +
                    "WHERE CodiceStrumentazione=" + strumentazione.getCodiceStrumentazione() + " AND CodiceScheda=" + schedaDescrittiva.getId_scheda() + ";";
            ResultSet controlloStr = insertStatement.executeQuery(queryControlloStr);

            int numeroRighe = 0;
            while (controlloStr.next())
                numeroRighe = controlloStr.getInt(1);
            if (numeroRighe == 0) {
                String queryStrumentazioneScheda = "INSERT INTO Strumentazione_Scheda(CodiceStrumentazione,CodiceScheda) "
                        + "VALUES("
                        + strumentazione.getCodiceStrumentazione() + ",'"
                        + schedaDescrittiva.getId_scheda() + "');";
                insertStatement.execute(queryStrumentazioneScheda);
            }
        }
    }


    private void eliminaSpazio_Scheda(Statement queryStatement, Statement insertStatement, ObservableList<Spazio> listaSpaziAggiunti) throws SQLException {
        //eliminazioni dalla tabella Spazio_Scheda
        String queryGetSpazi = "SELECT CodiceSpazio FROM Spazio_Scheda WHERE CodiceScheda=" + schedaDescrittiva.getId_scheda() + ";";
        ResultSet getSpazi = queryStatement.executeQuery(queryGetSpazi);
        int codiceSpazio;
        boolean presente;
        while (getSpazi.next()) {
            codiceSpazio = getSpazi.getInt(1);
            presente = false;
            for (Spazio spazio : listaSpaziAggiunti) {
                if (codiceSpazio == spazio.getCodiceSpazio()) {
                    presente = true;
                    break;
                }
            }
            if (!presente) {
                String queryEliminaSpazio = "DELETE FROM Spazio_Scheda" +
                        " WHERE CodiceSpazio=" + codiceSpazio + " AND CodiceScheda=" + schedaDescrittiva.getId_scheda() + ";";
                insertStatement.execute(queryEliminaSpazio);
            }

        }

    }

    private void aggiornaSpazio_Scheda(Statement insertStatement, ObservableList<Spazio> listaSpaziAggiunti) throws SQLException {
        //update della tabella Spazio_Scheda
        for (Spazio spazio : listaSpaziAggiunti) {
            String queryControlloSpazi = "SELECT Count(*) FROM Spazio_Scheda " +
                    "WHERE CodiceSpazio=" + spazio.getCodiceSpazio() + " AND CodiceScheda=" + schedaDescrittiva.getId_scheda() + ";";
            ResultSet controlloSpazio = insertStatement.executeQuery(queryControlloSpazi);

            int numeroRighe = 0;
            while (controlloSpazio.next())
                numeroRighe = controlloSpazio.getInt(1);
            if (numeroRighe == 0) {
                String querySpazioScheda = "INSERT INTO Spazio_Scheda(CodiceSpazio,CodiceScheda) "
                        + "VALUES("
                        + spazio.getCodiceSpazio() + ",'"
                        + schedaDescrittiva.getId_scheda() + "');";
                insertStatement.execute(querySpazioScheda);
            }
        }
    }


    private void apportaModificheSpazi() throws SQLException {
        schedaDescrittiva.setListaSpazi(schedaCopia.getListaSpazi());
        ObservableList<Spazio> listaSpaziAggiunti = schedaDescrittiva.getListaSpazi();
        try {
            Connection con = Database.getConnessione();
            Statement insertStatement = con.createStatement();
            Statement queryStatement = con.createStatement();

            eliminaSpazio_Scheda(queryStatement, insertStatement, listaSpaziAggiunti);
            aggiornaSpazio_Scheda(insertStatement, listaSpaziAggiunti);

        } catch (SQLException e) {
            GestoreAvvisi.mostraErrore("Impossibile effettuare l'aggiornamento della tabella Spazio_Scheda");
        }
    }


    public static void mostraModScheda() {
        pannelloContenitorePane.setCenter(modSchedaPane);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inizializzaGraficaPulsanti();
    }

    private void inizializzaGraficaPulsanti() {

        /* ---- SalvaSchedaBtn ----*/
        UtilityGrafiche.impostaIconaPulsante(salvaSchedaBtn, UtilityGrafiche.SAVE_ICON);

        /* --- annullaOperazioneBtn ---*/
        UtilityGrafiche.impostaIconaPulsante(annullaBtn, UtilityGrafiche.ANNULLA_ICON);
    }
}
