package gestoreSchede.gestoreSchedeDescrittive.esportaScheda;

import gestoreSchede.oggetti.SchedaDescrittiva;
import gestoreSchede.oggetti.elementi.Dipendente;
import gestoreSchede.oggetti.elementi.Spazio;
import gestoreSchede.oggetti.elementi.Strumentazione;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

/*******************
 * © APT Software *
 *******************/
public class AnteprimaScheda implements Initializable {

    @FXML
    private TextArea textArea;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void initData(SchedaDescrittiva schedaDescrittiva) {
        textArea.setText(creaStringaDaScheda(schedaDescrittiva));
    }


    private String creaStringaDaScheda(SchedaDescrittiva schedaDescrittiva) {
        String stringaScheda = "";

        stringaScheda = stringaScheda.concat("Data Scheda: " + schedaDescrittiva.getDataCreazioneScheda() + "\n\n");
        stringaScheda = stringaScheda.concat(schedaDescrittiva.getNomeScheda() + "\n\n");
        stringaScheda = stringaScheda.concat(schedaDescrittiva.getTestoIntroduttivo() + "\n\n");

        stringaScheda = stringaScheda.concat("Elenco Dipendenti:\n\n");
        for (Dipendente d : schedaDescrittiva.getListaDipendenti()) {
            stringaScheda = stringaScheda.concat("- " + d + "\n");
        }
        stringaScheda = stringaScheda.concat("\n\n");
        stringaScheda = stringaScheda.concat("Elenco Strumentazioni:\n\n");
        for (Strumentazione s : schedaDescrittiva.getListaStrumentazioni()) {
            stringaScheda = stringaScheda.concat("- " + s + "\n");
        }
        stringaScheda = stringaScheda.concat("\n\n");
        stringaScheda = stringaScheda.concat("Elenco Spazi:\n\n");
        for (Spazio s : schedaDescrittiva.getListaSpazi()) {
            stringaScheda = stringaScheda.concat("- " + s + "\n");
        }
        stringaScheda = stringaScheda.concat("\n\n");
        stringaScheda = stringaScheda.concat(schedaDescrittiva.getTestoFinale() + "\n\n");


        return stringaScheda;
    }
}
