package gestoreSchede.gestoreSchedeDescrittive;

import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.GUI.schermataPrincipale.GestoreDiSistema;
import gestoreSchede.database.Database;
import gestoreSchede.oggetti.SchedaDescrittiva;
import gestoreSchede.oggetti.elementi.Dipendente;
import gestoreSchede.oggetti.elementi.Spazio;
import gestoreSchede.oggetti.elementi.Strumentazione;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/*******************
 * © APT Software *
 *******************/
public class GestoreSchede {

    public static void aggiungiSchedaDb(SchedaDescrittiva scheda) {

        Connection con = Database.getConnessione();
        ObservableList<Dipendente> listaDipendentiScheda = scheda.getListaDipendenti();
        ObservableList<Strumentazione> listaStrumentazioniScheda = scheda.getListaStrumentazioni();
        ObservableList<Spazio> listaSpaziScheda = scheda.getListaSpazi();

        String queryInserimento = "INSERT INTO scheda(CodiceScheda,NomeScheda,TestoIntroduttivo,TestoFinale," +
                "EnteRichiedente,DataCreazioneScheda) "
                + "VALUES("
                + scheda.getId_scheda() + ",'"
                + scheda.getNomeScheda() + "','"
                + scheda.getTestoIntroduttivo() + "','"
                + scheda.getTestoFinale() + "','"
                + scheda.getEnteRichiedente() + "','"
                + scheda.getDataCreazioneScheda() + "');";


        try {
            Statement insertStatement = con.createStatement();
            //Inserimento della nuova scheda
            insertStatement.execute(queryInserimento);

            //Inserimento dei vincoli referenziali nella tabella Dipendente_Scheda
            for (Dipendente dipendente : listaDipendentiScheda) {
                String queryDipendenteScheda = "INSERT INTO Dipendente_Scheda(CodiceDipendente,CodiceScheda) "
                        + "VALUES("
                        + dipendente.getCodiceDipendente() + ",'"
                        + scheda.getId_scheda() + "');";
                try {
                    insertStatement.execute(queryDipendenteScheda);
                } catch (SQLException e) {
                    GestoreAvvisi.mostraErrore("Impossibile effettuare l'inserimento nella tabella Dipendente_Scheda");

                }
            }

            //Inserimento dei vincoli referenziali nella tabella Strumentazione_Scheda
            for (Strumentazione strumentazione : listaStrumentazioniScheda) {
                String queryStrumentazioneScheda = "INSERT INTO Strumentazione_Scheda(CodiceStrumentazione,CodiceScheda) "
                        + "VALUES("
                        + strumentazione.getCodiceStrumentazione() + ",'"
                        + scheda.getId_scheda() + "');";
                try {
                    insertStatement.execute(queryStrumentazioneScheda);
                } catch (SQLException e) {
                    GestoreAvvisi.mostraErrore("Impossibile effettuare l'inserimento nella tabella Strumentazione_Scheda");
                }
            }


            //Inserimento dei vincoli referenziali nella tabella Spazio_Scheda
            for (Spazio spazio : listaSpaziScheda) {
                String querySpazioScheda = "INSERT INTO Spazio_Scheda(CodiceSpazio,CodiceScheda) "
                        + "VALUES("
                        + spazio.getCodiceSpazio() + ",'"
                        + scheda.getId_scheda() + "');";
                try {
                    insertStatement.execute(querySpazioScheda);
                } catch (SQLException e) {
                    GestoreAvvisi.mostraErrore("Impossibile effettuare l'inserimento nella tabella Spazio_Scheda");
                }
            }


        } catch (SQLException e) {
            GestoreAvvisi.mostraErrore("Impossibile effettuare l'inserimento nella tabella Scheda");
        }


    }

    public static void aggiornaSchedaDb(SchedaDescrittiva scheda) throws SQLException {
        Connection con = Database.getConnessione();
        Statement updateStatement = con.createStatement();
        String updateScheda = "UPDATE Scheda " +
                "SET NomeScheda='" + scheda.getNomeScheda() + "', " +
                "TestoIntroduttivo='" + scheda.getTestoIntroduttivo() + "', " +
                "TestoFinale='" + scheda.getTestoFinale() + "', " +
                "EnteRichiedente='" + scheda.getEnteRichiedente() + "' " +
                "WHERE CodiceScheda=" + scheda.getId_scheda() + ";";
        updateStatement.execute(updateScheda);

    }

    public static void rimuoviSchedaDB() throws IOException {
        GestoreDiSistema m = GestoreDiSistema.getInstance();
        m.rimuoviElementoSelezionatoDalDatabase();
    }
/*--------------------------------------------------------------------------------------


    /* ------- FXML ------- */

    @FXML
    private ListView<Dipendente> listViewDipendente;
    @FXML
    private ListView<Strumentazione> listViewStrumentazione;
    @FXML
    private ListView<Spazio> listViewSpazio;
    @FXML
    private TextArea testoIntroduttivo;
    @FXML
    private TextArea testoFinale;
    @FXML
    private Text campoTesto;

    private SchedaDescrittiva schedaDescrittiva;

    // Funziona chiamata dal GestoreDiSistema per il passaggio dei dati
    public void initData(SchedaDescrittiva scheda, Stage stage) {
        schedaDescrittiva = scheda;
        inizializzaListe();
        inizializzaTesti();
    }

    private void inizializzaListe() {
        //if (schedaDescrittiva.getListaDipendenti() != null)
        listViewDipendente.getItems().addAll(schedaDescrittiva.getListaDipendenti());

        //if (schedaDescrittiva.getListaStrumentazioni() != null)
        listViewStrumentazione.getItems().addAll(schedaDescrittiva.getListaStrumentazioni());

        //if (schedaDescrittiva.getListaSpazi() != null)
        listViewSpazio.getItems().addAll(schedaDescrittiva.getListaSpazi());

    }

    private void inizializzaTesti() {
        campoTesto.setText("Dettagli relativi alla scheda descrittiva "+schedaDescrittiva.getNomeScheda());
        testoIntroduttivo.setText(schedaDescrittiva.getTestoIntroduttivo());
        testoFinale.setText(schedaDescrittiva.getTestoFinale());
    }
}

