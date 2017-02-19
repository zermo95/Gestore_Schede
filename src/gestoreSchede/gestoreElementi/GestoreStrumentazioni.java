package gestoreSchede.gestoreElementi;

import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.GUI.schermataPrincipale.GestoreDiSistema;
import gestoreSchede.Main;
import gestoreSchede.database.Database;
import gestoreSchede.gestoreElementi.modificaElemento.modificaStrumentazione.ModificaStrumentazione;
import gestoreSchede.oggetti.elementi.Strumentazione;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;

/*******************
 * © APT Software *
 *******************/

public class GestoreStrumentazioni {

    /* -- ATTENZIONE , NESSUN CONTROLLO, I CONTROLLI VANNO EFFETTUATI NEI RISPETTIVI CONTROLLER -- */

    public static void aggiungiStrumentazioneAlDatabase(Strumentazione s){

        Connection con = Database.getConnessione();

        String queryInserimento = "INSERT INTO Strumentazione(CodiceStrumentazione,NomeStrumentazione," +
                "DescrizioneStrumentazione, Marca, Modello," +
                " NumeroPezzi, Tipologia, AnnoAcquisto, CodiceSpazio, CodiceDipendente) "
                + "VALUES(" +
                + s.getCodiceStrumentazione() + ",'"
                + s.getNome() + "','"
                + s.getDescrizione() + "','"
                + s.getMarca() + "','"
                + s.getModello() + "',"
                + s.getNumeroPezzi() + ",'"
                + s.getTipologia() + "',"
                + s.getAnnoAcquisto() + ","
                + s.getCodiceSpazio() + ","
                + s.getCodiceDipendente() + ");";

        try {
            Statement insertStatement = con.createStatement();
            insertStatement.execute(queryInserimento);
        } catch (SQLException e) {
            GestoreAvvisi.mostraErrore("Impossibile aggiungere la strumentazione al database");
        }

        //AGGIUNTA DEL MAINCONTROLLER COME OSSERVATORE DELL'OGGETTO
        GestoreDiSistema GestoreDiSistema = gestoreSchede.GUI.schermataPrincipale.GestoreDiSistema.getInstance();
        s.addObserver(GestoreDiSistema);

        //AGGIUNTA STRUMENTAZIONE ALLA LISTA DELLE STRUMENTAZIONI
        GestoreDiSistema.aggiungiStrumentazione(s);

    }

    public static void aggiornaStrumentazione(Strumentazione s){
        Connection con = Database.getConnessione();

        String queryUpdate = "UPDATE Strumentazione\n" +
                "SET DescrizioneStrumentazione='" + s.getDescrizione() + "', \n" +
                " Marca='" + s.getMarca() + "', \n" +
                " Modello='" + s.getModello() + "', \n" +
                " NumeroPezzi='" + s.getNumeroPezzi() + "', \n" +
                " Tipologia='" + s.getTipologia() + "', \n" +
                " AnnoAcquisto='" + s.getAnnoAcquisto() + "', \n" +
                " NomeStrumentazione='" + s.getNome() + "' \n" +
                " WHERE CodiceStrumentazione=" + s.getCodiceStrumentazione() + ";";
        try {
            Statement insertStatement = con.createStatement();
            insertStatement.execute(queryUpdate);
        } catch (SQLException e) {
            GestoreAvvisi.mostraErrore("Impossibile modificare la strumentazione nel database");
        }
    }

    public static void rimuoviStrumentazine(Strumentazione s){
        Connection con = Database.getConnessione();

        String cancellaStr = "DELETE FROM Strumentazione\n" +
                "WHERE CodiceStrumentazione=" + s.getCodiceStrumentazione() + ";";
        try {

            Statement istruzione = con.createStatement();
            istruzione.execute(cancellaStr);
            GestoreDiSistema.getListaStrumentazioni().remove(s);
            //listaStrumentazioni.remove(s);

        } catch (SQLException e) {
            GestoreAvvisi.mostraErrore("Impossibile rimuovere la strumentazione dal database");
        }



    }

    /* CONSULTA STRUMENTAZIONE */

    /* ------- FXML ------- */
    @FXML
    private Text campoTesto;
    @FXML
    private Text campoNome;
    @FXML
    private Text campoModello;
    @FXML
    private Text campoMarca;
    @FXML
    private Text campoNumeroPezzi;
    @FXML
    private Text campoTipologia;
    @FXML
    private Text campoAnnoAcquisto;
    @FXML
    private Label campoDescrizione;
    @FXML
    private Text campoSpazioSelezionato;
    @FXML
    private Text campoDipendenteSelezionato;


    private static BorderPane infoStrPane;
    private static Stage infoStrStage;

    public void mostraInfoStrumentazione(Strumentazione strumentazione,BorderPane infoStr) throws IOException {

        infoStrPane=infoStr;

        infoStrStage = new Stage();
        infoStrStage.initModality(Modality.WINDOW_MODAL);
        infoStrStage.initOwner(Main.stagePrincipale);

        Scene scene = new Scene(infoStrPane);
        infoStrStage.setScene(scene);
        infoStrStage.setResizable(false);
        infoStrStage.setTitle("Informazioni Strumentazione");
        infoStrStage.show();
        campoTesto.setText("Informazioni relative a "+strumentazione.getNome());
        campoNome.setText(strumentazione.getNome());
        if(strumentazione.getModello().isEmpty())
            campoModello.setText("NON INSERITO");
        else campoModello.setText(strumentazione.getModello());
        if(strumentazione.getMarca().isEmpty())
            campoMarca.setText("NON INSERITO");
        else campoMarca.setText(strumentazione.getMarca());
        campoNumeroPezzi.setText(String.valueOf(strumentazione.getNumeroPezzi()));
        if(strumentazione.getTipologia().isEmpty())
            campoTipologia.setText("NON INSERITO");
        else campoTipologia.setText(strumentazione.getTipologia());
        if(strumentazione.getAnnoAcquisto()==0)
            campoAnnoAcquisto.setText("NON INSERITO");
        else campoAnnoAcquisto.setText(String.valueOf(strumentazione.getAnnoAcquisto()));
        campoDescrizione.setText(strumentazione.getDescrizione());
        try{
            campoSpazioSelezionato.setText(ModificaStrumentazione.cercaSpazioSelezionato(strumentazione.getCodiceSpazio()).get().getNome());
        }catch (NoSuchElementException e) {
            campoSpazioSelezionato.setText("NON INSERITO");
        }
        try{
            campoDipendenteSelezionato.setText(ModificaStrumentazione.cercaDipendenteSelezionato(strumentazione.getCodiceDipendente()).get().getCognome());
        }catch (NoSuchElementException e) {
            campoDipendenteSelezionato.setText("NON INSERITO");
        }

    }

    @FXML
    private void close(){
        infoStrStage.close();
    }


}
