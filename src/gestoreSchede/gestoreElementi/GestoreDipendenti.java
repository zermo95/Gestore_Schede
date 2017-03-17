package gestoreSchede.gestoreElementi;

import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.GUI.schermataPrincipale.GestoreDiSistema;
import gestoreSchede.Main;
import gestoreSchede.database.Database;
import gestoreSchede.gestoreElementi.modificaElemento.modificaDipendente.ModificaDipendente;
import gestoreSchede.oggetti.elementi.Dipendente;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;

/*******************
 * © APT Software *
 *******************/

public class GestoreDipendenti {
    private static final int SPAZIO_VUOTO = 0;

    /* -- ATTENZIONE , NESSUN CONTROLLO, I CONTROLLI VANNO EFFETTUATI NEI RISPETTIVI CONTROLLER -- */

    /* -------------- GESTIONE DATABASE ----------------*/

    public static void aggiungiDipendenteAlDatabase(Dipendente d) {
        Connection con = Database.getConnessione();
        String queryInserimento;
        int codiceSpazio = d.getCodiceSpazio();

        if (codiceSpazio != SPAZIO_VUOTO) {
            queryInserimento = "INSERT INTO Dipendente(CodiceDipendente,CodiceFiscale,Nome,Cognome," +
                    "DataNascita,Mansione,Sesso,CittaNascita,CittaDomicilio," +
                    "IndirizzoDomicilio,Email,NumeroTelefono,CodiceSpazio) "
                    + "VALUES("
                    + d.getCodiceDipendente() + ",'"
                    + d.getCodiceFiscale() + "','"
                    + d.getNome() + "','"
                    + d.getCognome() + "','"
                    + d.getDataNascita() + "','"
                    + d.getMansione() + "','"
                    + d.getSesso() + "','"
                    + d.getCittaNascita() + "','"
                    + d.getCittaDomicilio() + "','"
                    + d.getIndirizzoDomicilio() + "','"
                    + d.getEmail() + "','"
                    + d.getNumTelefono() + "',"
                    + d.getCodiceSpazio() + ");";
        } else {
            queryInserimento = "INSERT INTO Dipendente(CodiceDipendente,CodiceFiscale,Nome,Cognome," +
                    "DataNascita,Mansione,Sesso,CittaNascita,CittaDomicilio," +
                    "IndirizzoDomicilio,Email,NumeroTelefono) "
                    + "VALUES("
                    + d.getCodiceDipendente() + ",'"
                    + d.getCodiceFiscale() + "','"
                    + d.getNome() + "','"
                    + d.getCognome() + "','"
                    + d.getDataNascita() + "','"
                    + d.getMansione() + "','"
                    + d.getSesso() + "','"
                    + d.getCittaNascita() + "','"
                    + d.getCittaDomicilio() + "','"
                    + d.getIndirizzoDomicilio() + "','"
                    + d.getEmail() + "','"
                    + d.getNumTelefono() + "');";
        }

        try {
            Statement insertStatement = con.createStatement();
            insertStatement.execute(queryInserimento);
        } catch (SQLException e) {
            GestoreAvvisi.mostraErrore("Impossibile aggiungere il dipendente al database");
        }

        //AGGIUNTA DEL MAINCONTROLLER COME OSSERVATORE DELL'OGGETTO
        GestoreDiSistema GestoreDiSistema = gestoreSchede.GUI.schermataPrincipale.GestoreDiSistema.getInstance();
        d.addObserver(GestoreDiSistema);


        //AGGIUNTA DIPENDENTE ALLA LISTA DEI DIPENDENTI
        GestoreDiSistema.aggiungiDipendente(d);


    }

    public static void aggiornaDipendente(Dipendente d) {
        Connection con = Database.getConnessione();
        String queryUpdate;
        int codiceSpazio = d.getCodiceSpazio();
        if (codiceSpazio != SPAZIO_VUOTO) {
            queryUpdate = "UPDATE Dipendente\n" +
                    "SET CodiceFiscale='" + d.getCodiceFiscale() + "',\n" +
                    " Nome='" + d.getNome() + "',\n" +
                    " Cognome='" + d.getCognome() + "',\n" +
                    " DataNascita='" + d.getDataNascita() + "',\n" +
                    " Mansione='" + d.getMansione() + "',\n" +
                    " Sesso='" + d.getSesso() + "',\n" +
                    " CittaNascita='" + d.getCittaNascita() + "',\n" +
                    " IndirizzoDomicilio='" + d.getIndirizzoDomicilio() + "',\n" +
                    " Email='" + d.getEmail() + "',\n" +
                    " NumeroTelefono='" + d.getNumTelefono() + "',\n" +
                    " CodiceSpazio = " + d.getCodiceSpazio() + " \n" +
                    " WHERE CodiceDipendente= " + d.getCodiceDipendente() + ";";
        } else {
            queryUpdate = "UPDATE Dipendente\n" +
                    "SET CodiceFiscale='" + d.getCodiceFiscale() + "',\n" +
                    " Nome='" + d.getNome() + "',\n" +
                    " Cognome='" + d.getCognome() + "',\n" +
                    " DataNascita='" + d.getDataNascita() + "',\n" +
                    " Mansione='" + d.getMansione() + "',\n" +
                    " Sesso='" + d.getSesso() + "',\n" +
                    " CittaNascita='" + d.getCittaNascita() + "',\n" +
                    " IndirizzoDomicilio='" + d.getIndirizzoDomicilio() + "',\n" +
                    " Email='" + d.getEmail() + "',\n" +
                    " NumeroTelefono='" + d.getNumTelefono() + "'\n" +
                    " WHERE CodiceDipendente= " + d.getCodiceDipendente() + ";";
        }
        try {
            Statement insertStatement = con.createStatement();
            insertStatement.execute(queryUpdate);
        } catch (SQLException e) {
            GestoreAvvisi.mostraErrore("Impossibile modificare il dipendente nel database");
        }
    }

    public static boolean isNumeroDiTelefonoCorretto(String numeroTelefono) {

        String regexStr = "^[+0-9]*$"; // formato +393314445567 (max 16 caratteri)
        return numeroTelefono.matches(regexStr) &
                numeroTelefono.length() > 9 &
                numeroTelefono.length() < 16;


    }

    public static void rimuoviDipendente(Dipendente d){
        Connection con = Database.getConnessione();

        String cancellaDip = "DELETE FROM Dipendente\n" +
                "WHERE CodiceDipendente=" + d.getCodiceDipendente() + ";";
        try {

            Statement istruzione = con.createStatement();
            istruzione.execute(cancellaDip);
            GestoreDiSistema.getListaDipendenti().remove(d);

            //listaDipendenti.remove(d);

        } catch (SQLException e) {
            GestoreAvvisi.mostraErrore("Impossibile rimuovere il dipendente dal database");
        }
    }

    /* MOSTA DETTAGLI DIPENDENTE */


    /* ---------- FXML -------------*/
    /* ---- campi di testo ----*/
    @FXML
    private  Text campoTesto;
    @FXML
    private  Text campoNome;
    @FXML
    private Text campoCognome;
    @FXML
    private Text campoSesso;
    @FXML
    private  Text campoLuogoNascita;
    @FXML
    private Text campoCodiceFiscale;
    @FXML
    private Text campoData;
    @FXML
    private Text campoNumTelefono;
    @FXML
    private  Text campoEmail;
    @FXML
    private  Text campoResidenza;
    @FXML
    private  Text campoIndirizzo;
    @FXML
    private Text campoMansione;
    @FXML
    private  Text campoSpazioOccupato;


    private static BorderPane infoDipPane;
    private static Stage infoDipStage;

    public void mostraInfoDipendente(Dipendente d,BorderPane infoDip) throws IOException {

        infoDipPane=infoDip;
      /*  FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("gestoreElementi/pannelloContenitore.fxml"));
        pannelloContenitorePane = loader.load();*/

        infoDipStage = new Stage();
        infoDipStage.initModality(Modality.WINDOW_MODAL);
        infoDipStage.initOwner(Main.stagePrincipale);

        Scene scene = new Scene(infoDipPane);
        infoDipStage.setScene(scene);
        infoDipStage.setResizable(false);
        infoDipStage.setTitle("Informazioni Dipendente");
        infoDipStage.show();
        campoTesto.setText("Informazioni relative a "+d.getNome()+" "+d.getCognome());
        campoNome.setText(d.getNome());
        campoCognome.setText(d.getCognome());
        campoSesso.setText(d.getSesso());
        campoCodiceFiscale.setText(d.getCodiceFiscale());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        campoData.setText(df.format(d.getDataNascita()));
        campoNumTelefono.setText(d.getNumTelefono());
        campoMansione.setText(d.getMansione());
        if(d.getCittaNascita().isEmpty())
            campoLuogoNascita.setText("NON INSERITO");
        else campoLuogoNascita.setText(d.getCittaNascita());
        if(d.getEmail().isEmpty())
            campoEmail.setText("NON INSERITO");
        else campoEmail.setText(d.getEmail());
        if(d.getCittaDomicilio().isEmpty())
            campoResidenza.setText("NON INSERITO");
        else campoResidenza.setText(d.getCittaDomicilio());
        if(d.getIndirizzoDomicilio().isEmpty())
            campoIndirizzo.setText("NON INSERITO");
        else campoIndirizzo.setText(d.getIndirizzoDomicilio());
       try{
           campoSpazioOccupato.setText(ModificaDipendente.cercaSpazioSelezionato(d.getCodiceSpazio()).get().getNome());
       }catch (NoSuchElementException e) {
           campoSpazioOccupato.setText("NON INSERITO");
       }

    }

    @FXML
    private void close(){
        infoDipStage.close();
    }

}
