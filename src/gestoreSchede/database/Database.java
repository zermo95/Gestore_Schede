package gestoreSchede.database;


import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.GUI.schermataPrincipale.GestoreDiSistema;
import gestoreSchede.oggetti.SchedaDescrittiva;
import gestoreSchede.oggetti.elementi.Dipendente;
import gestoreSchede.oggetti.elementi.Spazio;
import gestoreSchede.oggetti.elementi.Strumentazione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/*******************
 * © APT Software *
 *******************/

public class Database {

    private static Connection connessione;

    static {
        try {
            connessione = DriverManager.getConnection("jdbc:mysql://localhost/?user=root&password=root");
        } catch (SQLException e) {
            GestoreAvvisi.mostraErrore("Errore di connessione");
        }
    }

    public static Connection getConnessione() {
        return connessione;
    }

    public static void creaDB() {

        String creaDB = "CREATE DATABASE GestoreSchedeDB;";

        String usaDB = "USE GestoreSchedeDB;";

        String spazio = "CREATE TABLE IF NOT EXISTS Spazio(\n" +
                "\tCodiceSpazio INTEGER (5) PRIMARY KEY,\n" +
                "\tNomeSpazio VARCHAR (255) NOT NULL,\n" +
                "\tUbicazione VARCHAR (255) NOT NULL,\n" +
                "\tCapienza INTEGER (5),\n" +
                "\tNumeroPorte INTEGER (5),\n" +
                "\tNumeroFinestre INTEGER (5),\n" +
                "\tAltreInformazioni VARCHAR (255)\n" +
                ")ENGINE= Innodb;";

        String dipendente = "CREATE TABLE IF NOT EXISTS Dipendente(\n" +
                "\tCodiceDipendente INTEGER (5) PRIMARY KEY,\n" +
                "\tCodiceFiscale VARCHAR (255) NOT NULL,\n" +
                "\tNome VARCHAR (255) NOT NULL,\n" +
                "\tCognome VARCHAR (255) NOT NULL,\n" +
                "\tDataNascita DATE NOT NULL,\n" +
                "\tMansione VARCHAR (255) NOT NULL,\n" +
                "\tSesso VARCHAR (55) NOT NULL,\n" +
                "\tCittaNascita VARCHAR (255),\n" +
                "\tCittaDomicilio VARCHAR (255),\n" +
                "\tIndirizzoDomicilio VARCHAR (255),\n" +
                "\tEmail VARCHAR (255),\n" +
                "\tNumeroTelefono VARCHAR (15) NOT NULL,\n" +
                "\tCodiceSpazio INTEGER (5),\n" +
                "\tFOREIGN KEY (CodiceSpazio) REFERENCES Spazio(CodiceSpazio)\n" +
                "\t\tON DELETE SET NULL\n" +
                "\t\tON UPDATE CASCADE\n" +
                ")ENGINE= Innodb;";

        String scheda = "CREATE TABLE IF NOT EXISTS Scheda(\n" +
                "\tCodiceScheda INTEGER (5) PRIMARY KEY,\n" +
                "\tNomeScheda VARCHAR (255) NOT NULL,\n" +
                "\tTestoIntroduttivo VARCHAR (255) NOT NULL,\n" +
                "\tTestoFinale VARCHAR (255) NOT NULL,\n" +
                "\tEnteRichiedente VARCHAR (255) NOT NULL,\n" +
                "\tDataCreazioneScheda DATETIME NOT NULL\n" +
                ")ENGINE= Innodb;";

        String strumentazione = "CREATE TABLE IF NOT EXISTS Strumentazione(\n" +
                "\tCodiceStrumentazione INTEGER (5) PRIMARY KEY,\n" +
                "\tNomeStrumentazione VARCHAR (255) NOT NULL,\n" +
                "\tDescrizioneStrumentazione VARCHAR(255) NOT NULL,\n" +
                "\tMarca VARCHAR (255),\n" +
                "\tModello VARCHAR (255),\n" +
                "\tNumeroPezzi INTEGER NOT NULL DEFAULT '0',\n" +
                "\tTipologia VARCHAR (255),\n" +
                "\tAnnoAcquisto INTEGER,\n" +
                "\tCodiceSpazio INTEGER (5),\n" +
                "\tCodiceDipendente INTEGER (5),\n" +
                "\tFOREIGN KEY (CodiceSpazio) REFERENCES Spazio(CodiceSpazio)\n" +
                "\t\tON DELETE SET NULL\n" +
                "\t\tON UPDATE CASCADE,\n" +
                "\tFOREIGN KEY (CodiceDipendente) REFERENCES Dipendente(CodiceDipendente)\n" +
                "\t\tON DELETE SET NULL\n" +
                "\t\tON UPDATE CASCADE\n" +
                ")ENGINE= Innodb;";

        String dipendente_scheda = "CREATE TABLE IF NOT EXISTS Dipendente_Scheda(\n" +
                "\tCodiceScheda INTEGER (5),\n" +
                "\tCodiceDipendente INTEGER (5),\n" +
                "\tPRIMARY KEY (CodiceScheda, CodiceDipendente),\n" +
                "\tFOREIGN KEY (CodiceScheda) REFERENCES Scheda(CodiceScheda) ON UPDATE CASCADE ON DELETE CASCADE,\n" +
                "\tFOREIGN KEY (CodiceDipendente) REFERENCES Dipendente(CodiceDipendente) ON UPDATE CASCADE ON DELETE CASCADE\n" +
                ")ENGINE= Innodb;";

        String spazio_scheda = "CREATE TABLE IF NOT EXISTS Spazio_Scheda(\n" +
                "\tCodiceScheda INTEGER (5),\n" +
                "\tCodiceSpazio INTEGER (5),\n" +
                "\tPRIMARY KEY (CodiceScheda, CodiceSpazio),\n" +
                "\tFOREIGN KEY (CodiceScheda) REFERENCES Scheda(CodiceScheda) ON UPDATE CASCADE ON DELETE CASCADE,\n" +
                "\tFOREIGN KEY (CodiceSpazio) REFERENCES Spazio(CodiceSpazio) ON UPDATE CASCADE ON DELETE CASCADE\n" +
                ")ENGINE= Innodb; ";

        String strumentazione_scheda = "CREATE TABLE IF NOT EXISTS Strumentazione_Scheda(\n" +
                "\tCodiceScheda INTEGER (5),\n" +
                "\tCodiceStrumentazione INTEGER (5),\n" +
                "\tPRIMARY KEY (CodiceScheda, CodiceStrumentazione),\n" +
                "\tFOREIGN KEY (CodiceScheda) REFERENCES Scheda(CodiceScheda) ON UPDATE CASCADE ON DELETE CASCADE,\n" +
                "\tFOREIGN KEY (CodiceStrumentazione) REFERENCES Strumentazione(CodiceStrumentazione) ON UPDATE CASCADE ON DELETE CASCADE\n" +
                ")ENGINE= Innodb;";
        try {
            // caricamento del driver
            new com.mysql.jdbc.Driver();
            /*
              creazione di una connessione al database con credenziali di accesso appropriate
			 */
            connessione = DriverManager.getConnection("jdbc:mysql://localhost/?user=root&password=root");
        } catch (SQLException e) {
            GestoreAvvisi.erroreConnessioneDatabase();
        }
        try {

            Statement istruzione = connessione.createStatement();
            try {
                istruzione.execute(creaDB);
            } catch (SQLException creaDbException) {
                istruzione.execute(usaDB);
                return;
            }
            istruzione.execute(usaDB);
            istruzione.execute(spazio);
            istruzione.execute(dipendente);
            istruzione.execute(scheda);
            istruzione.execute(strumentazione);
            istruzione.execute(dipendente_scheda);
            istruzione.execute(spazio_scheda);
            istruzione.execute(strumentazione_scheda);
        } catch (SQLException e) {
            GestoreAvvisi.mostraErrore("Impossibile creare il database");
        }
    }

    public static void caricaListaSchedeDescrittive(ObservableList<SchedaDescrittiva> listaSchede) throws SQLException {
        Connection con = getConnessione();
        int id = 1;
        String querySchede = "SELECT * FROM Scheda ORDER BY CodiceScheda;";
        boolean tabVuota = true;
        Statement insertSchede = con.createStatement();
        ResultSet selectSchede = insertSchede.executeQuery(querySchede);
        while (selectSchede.next()) {
            id = selectSchede.getInt(1);
            SchedaDescrittiva scheda = new SchedaDescrittiva(
                    id, // codice scheda
                    selectSchede.getString(2), // nome
                    selectSchede.getString(3), // testoIntro
                    selectSchede.getString(4),// testoFinale
                    selectSchede.getString(5),// enteRichiedente
                    selectSchede.getString(6) // dataCreazione
            );

            scheda.addObserver(GestoreDiSistema.getInstance());
            listaSchede.add(scheda);
            tabVuota = false;
            caricaDipendentiNellaScheda(scheda);
            caricaStrumentazioniNellaScheda(scheda);
            caricaSpaziNellaScheda(scheda);
        }
        id++;
        if (!tabVuota) SchedaDescrittiva.setId_globale(id);
    }

    private static void caricaDipendentiNellaScheda(SchedaDescrittiva scheda) throws SQLException {

        ObservableList<Dipendente> listaDipendentiScheda = FXCollections.observableArrayList();
        ObservableList<Dipendente> listaDipendenti = GestoreDiSistema.getListaDipendenti();

        Connection con = getConnessione();
        Statement query = con.createStatement();
        String queryJoinDipScheda = "SELECT CodiceDipendente FROM Dipendente NATURAL JOIN Dipendente_Scheda " +
                "WHERE CodiceScheda = " + scheda.getId_scheda() + ";";
        ResultSet joinDipScheda = query.executeQuery(queryJoinDipScheda);

        while (joinDipScheda.next()) {
            int idDipendente = joinDipScheda.getInt(1);
            //RICERCA DIPENDENTE NELLA LISTA DEI DIPENDENTI
            for (Dipendente dipendente : listaDipendenti) {
                if (dipendente.getCodiceDipendente() == idDipendente)
                    listaDipendentiScheda.add(dipendente);
            }
            //System.out.println(listaDipendentiScheda.size());
            //scheda.setListaDipendenti(listaDipendentiScheda);
        }
        scheda.setListaDipendenti(listaDipendentiScheda);
    }

    private static void caricaStrumentazioniNellaScheda(SchedaDescrittiva scheda) throws SQLException {

        Connection con = getConnessione();
        Statement query = con.createStatement();

        ObservableList<Strumentazione> listaStrumentazioniScheda = FXCollections.observableArrayList();
        ObservableList<Strumentazione> listaStrumentazioni = GestoreDiSistema.getListaStrumentazioni();

        String queryJoinStrScheda = "SELECT CodiceStrumentazione FROM Strumentazione NATURAL JOIN Strumentazione_Scheda " +
                "WHERE CodiceScheda = " + scheda.getId_scheda() + ";";
        ResultSet joinStrScheda = query.executeQuery(queryJoinStrScheda);
        while (joinStrScheda.next()) {
            int idStr = joinStrScheda.getInt(1);
            //RICERCA STRUMENTAZIONE NELLA LISTA DELLE STRUMENTAZIONI
            for (Strumentazione strumentazione : listaStrumentazioni) {
                if (strumentazione.getCodiceStrumentazione() == idStr)
                    listaStrumentazioniScheda.add(strumentazione);
            }
            //scheda.setListaStrumentazioni(listaStrumentazioniScheda);
        }
        scheda.setListaStrumentazioni(listaStrumentazioniScheda);
    }

    private static void caricaSpaziNellaScheda(SchedaDescrittiva scheda) throws SQLException {

        Connection con = getConnessione();
        Statement query = con.createStatement();

        ObservableList<Spazio> listaSpaziScheda = FXCollections.observableArrayList();
        ObservableList<Spazio> listaSpazi = GestoreDiSistema.getListaSpazi();

        String queryJoinSpaziScheda = "SELECT CodiceSpazio FROM Spazio NATURAL JOIN Spazio_Scheda " +
                "WHERE CodiceScheda = " + scheda.getId_scheda() + ";";
        ResultSet joinSpazioScheda = query.executeQuery(queryJoinSpaziScheda);
        while (joinSpazioScheda.next()) {
            int idSpazio = joinSpazioScheda.getInt(1);
            //RICERCA SPAZIO NELLA LISTA DEGLI SPAZI
            for (Spazio spazio : listaSpazi) {
                if (spazio.getCodiceSpazio() == idSpazio)
                    listaSpaziScheda.add(spazio);
            }
            //scheda.setListaSpazi(listaSpaziScheda);
        }
        scheda.setListaSpazi(listaSpaziScheda);


    }


    public static void caricaListaDipendenti(ObservableList<Dipendente> listaDipendenti) throws SQLException {
        Connection con = Database.getConnessione();
        String queryDipendente = "SELECT * FROM Dipendente ORDER BY CodiceDipendente;";

        int id = 1;
        boolean tabVuota = true;
        Statement insertDipendenti = con.createStatement();
        ResultSet selectDipendenti = insertDipendenti.executeQuery(queryDipendente);
        while (selectDipendenti.next()) {
            id = selectDipendenti.getInt(1);
            Dipendente dipendente = new Dipendente(
                    id,
                    selectDipendenti.getString(3), // Nome
                    selectDipendenti.getString(4), // Cognome
                    selectDipendenti.getString(7), // Sesso
                    selectDipendenti.getString(12), // Telefono
                    selectDipendenti.getString(6), // Mansione
                    selectDipendenti.getDate(5), // Data di nascita
                    selectDipendenti.getString(2), // CodiceFiscale
                    selectDipendenti.getString(8), // Citta Nascita
                    selectDipendenti.getString(9), // Citta Domicilio
                    selectDipendenti.getString(10), // Indirizzo Domicilio
                    selectDipendenti.getString(11), // Email
                    selectDipendenti.getInt(13) // codiceSpazio
            );
            dipendente.addObserver(GestoreDiSistema.getInstance());
            listaDipendenti.add(dipendente);
            tabVuota = false;
        }
        id++;
        if (!tabVuota) Dipendente.setCounter(id);

    }

    public static void caricaListaSpazi(ObservableList<Spazio> listaSpazi) throws SQLException {

        Connection con = Database.getConnessione();
        boolean tabVuota;
        int id = 1;

        String querySpazio = "SELECT * FROM Spazio ORDER BY CodiceSpazio;";

        tabVuota = true;
        Statement insertSpazi = con.createStatement();
        ResultSet selectSpazi = insertSpazi.executeQuery(querySpazio);
        while (selectSpazi.next()) {
            id = selectSpazi.getInt(1);
            Spazio spazio = new Spazio(id,
                    selectSpazi.getString(2), // nome spazio
                    selectSpazi.getString(3), // ubicazione
                    selectSpazi.getInt(4), // capienza
                    selectSpazi.getInt(5), // numero porte
                    selectSpazi.getInt(6), // numero finestre
                    selectSpazi.getString(7)); // altre info

            listaSpazi.add(spazio);

            GestoreDiSistema GestoreDiSistema = gestoreSchede.GUI.schermataPrincipale.GestoreDiSistema.getInstance();
            spazio.addObserver(GestoreDiSistema);
            //System.out.println(spazio + " aggiunto");

            tabVuota = false;
        }
        id++;
        if (!tabVuota) Spazio.setCounter(id);

    }

    public static void caricaListaStrumentazioni(ObservableList<Strumentazione> listaStrumentazioni) throws SQLException {

        Connection con = Database.getConnessione();
        boolean tabVuota;
        int id = 1;

        String queryStrumentazione = "SELECT * FROM Strumentazione ORDER BY CodiceStrumentazione;";

        tabVuota = true;
        Statement insertStrumentazioni = con.createStatement();
        ResultSet selectStrumentazioni = insertStrumentazioni.executeQuery(queryStrumentazione);
        while (selectStrumentazioni.next()) {
            id = selectStrumentazioni.getInt(1);
            Strumentazione strumentazione = new Strumentazione(
                    id, // codice strumentazione
                    selectStrumentazioni.getString(2), // nome
                    selectStrumentazioni.getString(3), // descrizione
                    selectStrumentazioni.getString(4),// marca
                    selectStrumentazioni.getString(5),// modello
                    selectStrumentazioni.getInt(6), // nPezzi
                    selectStrumentazioni.getString(7), // tipologia
                    selectStrumentazioni.getInt(8), // anno acquisto
                    selectStrumentazioni.getInt(10), // codice dipendente
                    selectStrumentazioni.getInt(9)); // codice spazio

            strumentazione.addObserver(GestoreDiSistema.getInstance());
            listaStrumentazioni.add(strumentazione);
            tabVuota = false;
        }
        id++;
        if (!tabVuota) Strumentazione.setCounter(id);

    }


}

