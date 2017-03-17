package gestoreSchede.oggetti.elementi;

/*******************
 * © APT Software *
 *******************/
public class Spazio extends Elemento {

    private static int counter = 1;
    private String nome;
    private String ubicazione;
    private int capienza=-1;
    private int numeroPorte=-1;
    private int numeroFinestre=-1;
    private String altreInformazioni;

    public Spazio() {
        setId_elemento(counter++);
    }

    public Spazio(String nome, String ubicazione, int capienza, int numeroPorte,
                  int numeroFinestre, String altreInformazioni) {
        this();
        this.nome = nome;
        this.ubicazione = ubicazione;
        this.capienza = capienza;
        this.numeroPorte = numeroPorte;
        this.numeroFinestre = numeroFinestre;
        this.altreInformazioni = altreInformazioni;
    }

    //Costruttore per database
    public Spazio(int codiceSpazio, String nome, String ubicazione, int capienza,
                  int numeroPorte, int numeroFinestre, String altreInformazioni) {
        setId_elemento(codiceSpazio);
        this.nome = nome;
        this.ubicazione = ubicazione;
        this.capienza = capienza;
        this.numeroPorte = numeroPorte;
        this.numeroFinestre = numeroFinestre;
        this.altreInformazioni = altreInformazioni;
    }

    public static void setCounter(int c) {
        counter = c;
    }

    public int getCodiceSpazio() {
        return getId_elemento();
    }

    void setCodiceSpazio(int codiceSpazio) {
        setId_elemento(codiceSpazio);
        notificaCambiamenti();
    }

    public String getAltreInformazioni() {
        return altreInformazioni;
    }

    public void setAltreInformazioni(String altreInformazioni) {
        this.altreInformazioni = altreInformazioni;
        notificaCambiamenti();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        notificaCambiamenti();
    }

    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
        notificaCambiamenti();
    }

    public int getNumeroPorte() {
        return numeroPorte;
    }

    public void setNumeroPorte(int numeroPorte) {
        this.numeroPorte = numeroPorte;
        notificaCambiamenti();
    }

    public int getNumeroFinestre() {
        return numeroFinestre;
    }

    public void setNumeroFinestre(int numeroFinestre) {
        this.numeroFinestre = numeroFinestre;
        notificaCambiamenti();
    }

    public String getUbicazione() {
        return ubicazione;
    }

    public void setUbicazione(String ubicazione) {
        this.ubicazione = ubicazione;
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

        Spazio spazio = (Spazio) o;

        return getId_elemento() == spazio.getId_elemento();

    }

    @Override
    public int hashCode() {
        return getId_elemento();
    }

    @Override
    public String toString() {
        return nome + ", " + ubicazione;
    }
}
