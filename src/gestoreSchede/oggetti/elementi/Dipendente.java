package gestoreSchede.oggetti.elementi;

import java.sql.Date;

/*******************
 * © APT Software *
 *******************/
public class Dipendente extends Elemento {

    private static int counter = 1;
    private String nome;
    private String cognome;
    private String numTelefono;
    private String mansione;
    private Date dataNascita;
    private String sesso;
    private String codiceFiscale;
    private String cittaNascita;
    private String cittaDomicilio;
    private String indirizzoDomicilio;
    private String email;
    private int codiceSpazio;


    private Dipendente() {
        setId_elemento(counter++);
    }

    public Dipendente(int codiceDipendente, String nome, String cognome, String sesso, String numTelefono, String mansione, Date dataNascita, String codiceFiscale, String cittaNascita, String cittaDomicilio, String indirizzoDomicilio, String email, int codiceSpazio) {
        setId_elemento(codiceDipendente);
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.numTelefono = numTelefono;
        this.mansione = mansione;
        this.dataNascita = dataNascita;
        this.codiceFiscale = codiceFiscale;
        this.cittaNascita = cittaNascita;
        this.cittaDomicilio = cittaDomicilio;
        this.indirizzoDomicilio = indirizzoDomicilio;
        this.email = email;
        this.codiceSpazio = codiceSpazio;
    }

    public Dipendente(String nome, String cognome, String sesso, String numTelefono, String mansione, Date dataNascita, String codiceFiscale, String cittaNascita, String cittaDomicilio, String indirizzoDomicilio, String email, int codiceSpazio) {
        this();
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.numTelefono = numTelefono;
        this.mansione = mansione;
        this.dataNascita = dataNascita;
        this.codiceFiscale = codiceFiscale;
        this.cittaNascita = cittaNascita;
        this.cittaDomicilio = cittaDomicilio;
        this.indirizzoDomicilio = indirizzoDomicilio;
        this.email = email;
        this.codiceSpazio = codiceSpazio;
    }



    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
        notificaCambiamenti();
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
        notificaCambiamenti();
    }

    public String getCittaNascita() {
        return cittaNascita;
    }

    public void setCittaNascita(String cittaNascita) {
        this.cittaNascita = cittaNascita;
        notificaCambiamenti();
    }

    public String getCittaDomicilio() {
        return cittaDomicilio;
    }

    public void setCittaDomicilio(String cittaDomicilio) {
        this.cittaDomicilio = cittaDomicilio;
        notificaCambiamenti();
    }

    public String getIndirizzoDomicilio() {
        return indirizzoDomicilio;
    }

    public void setIndirizzoDomicilio(String indirizzoDomicilio) {
        this.indirizzoDomicilio = indirizzoDomicilio;
        notificaCambiamenti();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notificaCambiamenti();
    }

    public void setCodiceSpazio(int codiceSpazio) {
        this.codiceSpazio = codiceSpazio;
        notificaCambiamenti();
    }

    public static void setCounter(int c) {
        counter = c;
    }

    public int getCodiceDipendente() {
        return getId_elemento();
    }

    public int getCodiceSpazio() {
        return codiceSpazio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        notificaCambiamenti();
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
        notificaCambiamenti();
    }

    public String getMansione() {
        return mansione;
    }

    public void setMansione(String mansione) {
        this.mansione = mansione;
        notificaCambiamenti();
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
        notificaCambiamenti();
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
        notificaCambiamenti();
    }

    private void notificaCambiamenti() {
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        } else {
            Dipendente that = (Dipendente) o;
            return  getId_elemento()== that.getId_elemento();
        }

    }

    @Override
    public int hashCode() {
        return getId_elemento();
    }

    @Override
    public String toString() {
        return nome + " " + cognome + ", " + mansione;
    }
}
