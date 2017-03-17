package gestoreSchede.oggetti.elementi;

/*******************
 * © APT Software *
 *******************/

public class Strumentazione extends Elemento {

    private static int counter = 1;
    private String nome;
    private String descrizione;
    private String marca;
    private String modello;
    private int numeroPezzi;
    private String tipologia;
    private int annoAcquisto;
    private int codiceSpazio;
    private int codiceDipendente;

    private Strumentazione() {
        setId_elemento(counter++);
    }

    private Strumentazione(String nome, String descrizione, String marca, String modello, int numeroPezzi, String tipologia, int annoAcquisto) {
        this();
        this.nome = nome;
        this.descrizione = descrizione;
        this.marca = marca;
        this.modello = modello;
        this.numeroPezzi = numeroPezzi;
        this.tipologia = tipologia;
        this.annoAcquisto = annoAcquisto;
    }

    private Strumentazione(String nome, String descrizione, String marca, String modello, int numeroPezzi, String tipologia, int annoAcquisto, int codiceDipendente) {
        this(nome, descrizione, marca, modello, numeroPezzi, tipologia, annoAcquisto);
        this.codiceDipendente = codiceDipendente;
    }

    public Strumentazione(String nome, String descrizione, String marca, String modello, int numeroPezzi, int codiceSpazio, String tipologia, int annoAcquisto) {
        this(nome, descrizione, marca, modello, numeroPezzi, tipologia, annoAcquisto);
        this.codiceSpazio = codiceSpazio;
    }

    public Strumentazione(String nome, String descrizione, String marca, String modello, int numeroPezzi, String tipologia, int annoAcquisto, int codiceDipendente, int codiceSpazio) {
        this(nome, descrizione, marca, modello, numeroPezzi, tipologia, annoAcquisto, codiceDipendente);
        this.codiceSpazio = codiceSpazio;
    }

    public Strumentazione(int codiceStrumentazione, String nome, String descrizione, String marca, String modello, int numeroPezzi, String tipologia, int annoAcquisto, int codiceDipendente, int codiceSpazio) {
        this(nome, descrizione, marca, modello, numeroPezzi, tipologia, annoAcquisto, codiceDipendente, codiceSpazio);
        setId_elemento(codiceStrumentazione);
    }

    // Costruttore per database
    public Strumentazione(int codiceStrumentazione, String nome, String descrizione,
                          String marca, String modello, int numeroPezzi, String tipologia,
                          int annoAcquisto) {
        this();
        setId_elemento(codiceStrumentazione);
        this.nome = nome;
        this.descrizione = descrizione;
        this.marca = marca;
        this.modello = modello;
        this.numeroPezzi = numeroPezzi;
        this.tipologia = tipologia;
        this.annoAcquisto = annoAcquisto;
    }

    public static void setCounter(int c) {
        counter = c;
    }

    public int getCodiceStrumentazione() {
        return getId_elemento();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        notificaCambiamenti();
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
        notificaCambiamenti();
    }


    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
        notificaCambiamenti();
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
        notificaCambiamenti();
    }

    public int getNumeroPezzi() {
        return numeroPezzi;
    }

    public void setNumeroPezzi(int numeroPezzi) {
        this.numeroPezzi = numeroPezzi;
        notificaCambiamenti();
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
        notificaCambiamenti();
    }

    public int getAnnoAcquisto() {
        return annoAcquisto;
    }

    public void setAnnoAcquisto(int annoAcquisto) {
        this.annoAcquisto = annoAcquisto;
        notificaCambiamenti();
    }

    public int getCodiceSpazio() {
        return codiceSpazio;
    }

    public void setCodiceSpazio(int codiceSpazio) {
        this.codiceSpazio = codiceSpazio;
        notificaCambiamenti();
    }

    public int getCodiceDipendente() {
        return codiceDipendente;
    }

    public void setCodiceDipendente(int codiceDipendente) {
        this.codiceDipendente = codiceDipendente;
        notificaCambiamenti();
    }

    private void notificaCambiamenti() {
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Strumentazione that = (Strumentazione) o;

        return getId_elemento() == that.getId_elemento();

    }

    @Override
    public int hashCode() {
        return getId_elemento();
    }

    @Override
    public String toString() {
        return nome + ", " + descrizione;
    }
}
