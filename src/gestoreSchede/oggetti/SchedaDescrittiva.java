package gestoreSchede.oggetti;

import gestoreSchede.oggetti.elementi.Dipendente;
import gestoreSchede.oggetti.elementi.Spazio;
import gestoreSchede.oggetti.elementi.Strumentazione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Observable;

/*******************
 * © APT Software *
 *******************/
public class SchedaDescrittiva extends Observable {

    private static int id_globale = 1;
    private int id_scheda;
    private String nomeScheda;
    private String enteRichiedente;
    private String testoIntroduttivo;
    private String testoFinale;
    private String dataCreazioneScheda;
    private ObservableList<Dipendente> listaDipendenti;
    private ObservableList<Spazio> listaSpazi;
    private ObservableList<Strumentazione> listaStrumentazioni;


    public SchedaDescrittiva(String nomeScheda, String enteRichiedente, String testoIntroduttivo, String testoFinale) {
        this.enteRichiedente = enteRichiedente;
        this.nomeScheda = nomeScheda;
        this.testoIntroduttivo = testoIntroduttivo;
        this.testoFinale = testoFinale;
        id_scheda = id_globale++;
        dataCreazioneScheda = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public SchedaDescrittiva(int id_scheda, String nomeScheda, String testoIntroduttivo, String testoFinale, String enteRichiedente, String dataCreazioneScheda) {
        this.id_scheda = id_scheda;
        this.enteRichiedente = enteRichiedente;
        this.nomeScheda = nomeScheda;
        this.testoIntroduttivo = testoIntroduttivo;
        this.testoFinale = testoFinale;
        this.dataCreazioneScheda = dataCreazioneScheda;
    }

    public static void setId_globale(int id) {
        id_globale = id;
    }

    public void setListaDipendenti(ObservableList<Dipendente> lista) {
        listaDipendenti = FXCollections.observableArrayList(lista);
        //notificaCambiamenti(); provoca crash
    }

    public void setListaSpazi(ObservableList<Spazio> lista) {
        listaSpazi = FXCollections.observableArrayList(lista);
        //notificaCambiamenti(); provoca crash
    }

    public void setListaStrumentazioni(ObservableList<Strumentazione> lista) {
        listaStrumentazioni = FXCollections.observableArrayList(lista);
        //notificaCambiamenti(); provoca crash
    }


    public ObservableList<Dipendente> getListaDipendenti() {
        return listaDipendenti;
    }

    public ObservableList<Strumentazione> getListaStrumentazioni() {
        return listaStrumentazioni;
    }

    public ObservableList<Spazio> getListaSpazi() {
        return listaSpazi;
    }

    public void setNomeScheda(String nome) {
        nomeScheda = nome;
        notificaCambiamenti();
    }

    public void setEnteRichiedente(String ente) {
        enteRichiedente = ente;
        notificaCambiamenti();
    }

    public void setTestoIntroduttivo(String testo) {
        testoIntroduttivo = testo;
        notificaCambiamenti();
    }

    public void setTestoFinale(String testo) {
        testoFinale = testo;
        notificaCambiamenti();
    }

    public int getId_scheda() {
        return id_scheda;
    }

    public String getEnteRichiedente() {
        return enteRichiedente;
    }

    public String getNomeScheda() {
        return nomeScheda;
    }

    public String getTestoIntroduttivo() {
        return testoIntroduttivo;
    }

    public String getTestoFinale() {
        return testoFinale;
    }

    public String getDataCreazioneScheda() {
        return dataCreazioneScheda;
    }

    @Override
    public SchedaDescrittiva clone(){
        SchedaDescrittiva scheda = new SchedaDescrittiva(this.id_scheda,this.nomeScheda,
                this.testoIntroduttivo,this.testoFinale, this.enteRichiedente,this.dataCreazioneScheda);

        scheda.setListaDipendenti(this.getListaDipendenti());
        scheda.setListaSpazi(this.getListaSpazi());
        scheda.setListaStrumentazioni(this.getListaStrumentazioni());

        return scheda;

    }



    private void notificaCambiamenti() {
        setChanged();
        notifyObservers();
    }
}
