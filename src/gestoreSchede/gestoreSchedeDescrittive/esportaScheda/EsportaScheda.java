package gestoreSchede.gestoreSchedeDescrittive.esportaScheda;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.Main;
import gestoreSchede.oggetti.SchedaDescrittiva;
import gestoreSchede.oggetti.elementi.Dipendente;
import gestoreSchede.oggetti.elementi.Spazio;
import gestoreSchede.oggetti.elementi.Strumentazione;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/*******************
 * © APT Software *
 *******************/

public class EsportaScheda implements Initializable {

    private SchedaDescrittiva schedaDescrittiva;
    private String percorsoDiOutput;
    private Stage stageDelMainController;

    private static final int INDENTAZIONE_LISTE = 30;

    /* ------- FXML ------- */

    @FXML
    private Button esportaBtn;
    @FXML
    private Button annullaBtn;
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
    private void mostraAnteprimaScheda() throws IOException {

        FXMLLoader loaderAnteprimaScheda = new FXMLLoader();
        loaderAnteprimaScheda.setLocation(Main.class.getResource("gestoreSchedeDescrittive/esportaScheda/mostraAnteprimaScheda.fxml"));
        //BorderPane anteprimaSchedaPane = loaderAnteprimaScheda.load();

        Stage anteprimaSchedaStage = new Stage(StageStyle.DECORATED);
        anteprimaSchedaStage.initModality(Modality.WINDOW_MODAL);
        anteprimaSchedaStage.initOwner(stageDelMainController);
        anteprimaSchedaStage.setScene(new Scene(loaderAnteprimaScheda.load()));

        // Serve per passare i parametri all'altro Controller
        AnteprimaScheda controller = loaderAnteprimaScheda.getController();
        controller.initData(schedaDescrittiva);

        // Mostra schermata
        anteprimaSchedaStage.setTitle("Anteprima Scheda Descrittiva");
        anteprimaSchedaStage.setMinWidth(400);
        anteprimaSchedaStage.setMinHeight(600);
        anteprimaSchedaStage.setResizable(false);
        anteprimaSchedaStage.show();



    }


    // Funziona chiamata dal GestoreDiSistema per il passaggio dei dati
    public void initData(SchedaDescrittiva scheda, Stage stage) {
        schedaDescrittiva = scheda;
        stageDelMainController = stage;
        inizializzaListe();
        inizializzaTesti();
    }

    private void apriSchermataSelezioneOutput() {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleziona il percorso di output della scheda");
        File selectedDirectory = directoryChooser.showDialog(stageDelMainController);

        if (selectedDirectory != null) {
            percorsoDiOutput = selectedDirectory.getAbsolutePath();
            if (isWindows()) {
                percorsoDiOutput = percorsoDiOutput.concat("\\" + schedaDescrittiva.getNomeScheda() + ".pdf");
            } else {
                percorsoDiOutput = percorsoDiOutput.concat("/" + schedaDescrittiva.getNomeScheda() + ".pdf");
            }
        } else {
            percorsoDiOutput = "";
        }
    }

    private void esportaScheda() throws IOException {

        apriSchermataSelezioneOutput();

        // Crea il documento pdf solo se è stato selezionato un percorso di output
        if (!percorsoDiOutput.equals("")) {

            File file = new File(percorsoDiOutput);
            file.getParentFile().mkdirs();


            // Inizializza il PDF Writer
            PdfWriter writer = new PdfWriter(percorsoDiOutput);

            // Inizializza il documento PDF
            PdfDocument pdf = new PdfDocument(writer);

            // Inizializza il documento
            Document document = new Document(pdf);

            // Crea un PdfFont
            PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);

            // Paragrafo data
            document.add(new Paragraph("data documento : " + schedaDescrittiva.getDataCreazioneScheda()));

            // Paragrafo testo introduttivo
            document.add(new Paragraph(schedaDescrittiva.getTestoIntroduttivo()));

            // Paragrafo elenco dipendenti
            document.add(new Paragraph("Elenco Dipendenti:").setFont(font));

            // Crea la lista dei dipendenti
            List listaDipendenti = new List()
                    .setSymbolIndent(INDENTAZIONE_LISTE)
                    .setListSymbol("\u2022")
                    .setFont(font);

            // Aggiungi i dipendenti alla lista
            for (Dipendente dipendente : schedaDescrittiva.getListaDipendenti()) {
                listaDipendenti.add(new ListItem(String.valueOf(dipendente)));
            }

            // Aggiungi la lista al documento
            document.add(listaDipendenti);

            // Paragrafo strumentazioni
            document.add(new Paragraph("Elenco Strumentazioni:").setFont(font));

            // Crea la lista strumentazioni
            List listaStrumentazioni = new List()
                    .setSymbolIndent(INDENTAZIONE_LISTE)
                    .setListSymbol("\u2022")
                    .setFont(font);

            // Aggiungi le strumentazioni alla lista
            for (Strumentazione strumentazione : schedaDescrittiva.getListaStrumentazioni()) {
                listaStrumentazioni.add(new ListItem(String.valueOf(strumentazione)));
            }

            // Aggiungi la lista al documento
            document.add(listaStrumentazioni);


            // Paragrafo spazi
            document.add(new Paragraph("Elenco Spazi:").setFont(font));

            // Crea la lista spazi
            List listaSpazi = new List()
                    .setSymbolIndent(INDENTAZIONE_LISTE)
                    .setListSymbol("\u2022")
                    .setFont(font);

            // Aggiungi gli spazi alla lista
            for (Spazio spazio : schedaDescrittiva.getListaSpazi()) {
                listaSpazi.add(new ListItem(String.valueOf(spazio)));
            }


            // Aggiungi la lista al documento
            document.add(listaSpazi);

            // Paragrafo testo finale
            document.add(new Paragraph(schedaDescrittiva.getTestoFinale()));

            //Chiudi il documento
            document.close();


            GestoreAvvisi.schedaEsportataCorrettamente(percorsoDiOutput);
            stageDelMainController.close();

        }
    }


    private String getSistemaOperativo() {
        return System.getProperty("os.name");
    }

    private boolean isWindows() {
        return getSistemaOperativo().startsWith("Windows");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        esportaBtn.setOnAction(event -> {
            try {
                esportaScheda();
            } catch (IOException e) {
                GestoreAvvisi.mostraErrore("Errore inizializzazione dell'esportazione della scheda");
            }
        });
        annullaBtn.setOnAction(event -> stageDelMainController.close());
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
        testoIntroduttivo.setText(schedaDescrittiva.getTestoIntroduttivo());
        testoFinale.setText(schedaDescrittiva.getTestoFinale());
    }
}
