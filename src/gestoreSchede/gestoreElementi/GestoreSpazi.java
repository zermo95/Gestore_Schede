package gestoreSchede.gestoreElementi;

import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.GUI.schermataPrincipale.GestoreDiSistema;
import gestoreSchede.Main;
import gestoreSchede.database.Database;
import gestoreSchede.oggetti.elementi.Spazio;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/*******************
 * © APT Software *
 *******************/
public class GestoreSpazi {

    /* -- ATTENZIONE , NESSUN CONTROLLO, I CONTROLLI VANNO EFFETTUATI NEI RISPETTIVI CONTROLLER -- */

    public static void aggiungiSpazioAlDatabase(Spazio s) {
        Connection con = Database.getConnessione();

        String queryInserimento = "INSERT INTO Spazio(CodiceSpazio, NomeSpazio," +
                "Ubicazione, Capienza, NumeroPorte, NumeroFinestre, AltreInformazioni) " +
                "VALUES("
                + s.getCodiceSpazio() + ", '"
                + s.getNome() + "', '"
                + s.getUbicazione() + "',"
                + s.getCapienza() + ", "
                + s.getNumeroPorte() + ", "
                + s.getNumeroFinestre() + ", '"
                + s.getAltreInformazioni() + "');";
        try {
            Statement insertStatement = con.createStatement();
            insertStatement.execute(queryInserimento);
        } catch (SQLException e) {
            GestoreAvvisi.mostraErrore("Impossibile aggiungere lo spazio al database");
        }

        //AGGIUNTA DEL MAINCONTROLLER COME OSSERVATORE DELL'OGGETTO
        GestoreDiSistema GestoreDiSistema = gestoreSchede.GUI.schermataPrincipale.GestoreDiSistema.getInstance();
        s.addObserver(GestoreDiSistema);

        //AGGIUNTA DIPENDENTE ALLA LISTA DEI DIPENDENTI
        GestoreDiSistema.aggiungiSpazio(s);
    }

    public static void aggiornaSpazio(Spazio s) {
        Connection con = Database.getConnessione();

        String queryUpdate = "UPDATE Spazio\n" +
                "SET NomeSpazio='" + s.getNome() + "', \n" +
                "Ubicazione='" + s.getUbicazione() + "', \n" +
                "Capienza=" + s.getCapienza() + ", \n" +
                "NumeroPorte=" + s.getNumeroPorte() + ", \n" +
                "NumeroFinestre=" + s.getNumeroFinestre() + ", \n" +
                "AltreInformazioni='" + s.getAltreInformazioni() + "' \n" +
                "WHERE CodiceSpazio=" + s.getCodiceSpazio() + ";";
        try {
            Statement insertStatement = con.createStatement();
            insertStatement.execute(queryUpdate);
        } catch (SQLException e) {
            GestoreAvvisi.mostraErrore("Impossibile aggiornare lo spazio nel database");
        }


    }

    public static void rimuoviSpazio(Spazio s) {

        Connection con = Database.getConnessione();

        String cancellaDip = "DELETE FROM Spazio\n" +
                "WHERE CodiceSpazio=" + s.getCodiceSpazio() + ";";
        try {

            Statement istruzione = con.createStatement();
            istruzione.execute(cancellaDip);
            GestoreDiSistema.getListaSpazi().remove(s);
            //listaSpazi.remove(s);

        } catch (SQLException e) {
            GestoreAvvisi.mostraErrore("Impossibile rimuovere lo spazio dal database");
        }


    }


       /* CONSULTA STRUMENTAZIONE */

    /* ------- FXML ------- */
    @FXML
    private Text campoTesto;
    @FXML
    private Text campoNome;
    @FXML
    private Text campoUbicazione;
    @FXML
    private Text campoCapienza;
    @FXML
    private Text campoNumeroPorte;
    @FXML
    private Text campoNumeroFinestre;
    @FXML
    private Label campoAltreInfo;



    private static BorderPane infoSpazioPane;
    private static Stage infoSpazioStage;

    public void mostraInfoSpazio(Spazio spazio,BorderPane infoSpazio) {

        infoSpazioPane=infoSpazio;

        infoSpazioStage = new Stage();
        infoSpazioStage.initModality(Modality.WINDOW_MODAL);
        infoSpazioStage.initOwner(Main.stagePrincipale);

        Scene scene = new Scene(infoSpazioPane);
        infoSpazioStage.setScene(scene);
        infoSpazioStage.setResizable(false);
        infoSpazioStage.setTitle("Informazioni Spazio");
        infoSpazioStage.show();
        campoTesto.setText("Informazioni relative a "+spazio.getNome());
        campoNome.setText(spazio.getNome());
        campoUbicazione.setText(spazio.getUbicazione());
        if(spazio.getCapienza()==0)
            campoCapienza.setText("NON INSERITO");
        else campoCapienza.setText(String.valueOf(spazio.getCapienza()));
        if(spazio.getNumeroPorte()==0)
            campoNumeroPorte.setText("NON INSERITO");
        else campoNumeroPorte.setText(String.valueOf(spazio.getNumeroPorte()));
        if(spazio.getNumeroFinestre()==-1)
            campoNumeroFinestre.setText("NON INSERITO");
        else campoNumeroFinestre.setText(String.valueOf(spazio.getNumeroFinestre()));
        if(spazio.getAltreInformazioni().isEmpty())
            campoAltreInfo.setText("NON INSERITO");
        else campoAltreInfo.setText(String.valueOf(spazio.getAltreInformazioni()));

        System.out.println(spazio.getAltreInformazioni());
    }

    @FXML
    private void close(){
        infoSpazioStage.close();
    }

}
