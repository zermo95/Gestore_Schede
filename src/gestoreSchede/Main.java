package gestoreSchede;

import com.sun.javafx.application.LauncherImpl;
import gestoreSchede.GUI.avvisi.GestoreAvvisi;
import gestoreSchede.GUI.schermataPrincipale.GestoreDiSistema;
import gestoreSchede.database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/*******************
 * © APT Software *
 *******************/
public class Main extends Application {

    // Dimensioni minime della finestra
    private static final int DIM_MIN_ALTEZZA_FINESTRA = 630;
    private static final int DIM_MIN_LARGHEZZA_FINESTRA = 900;

    /**
     * Stage che rappresenta l'applicazione
     **/
    public static Stage stagePrincipale;

    // i BorderPane sono le varie schermate
    private static BorderPane schermataPrincipale;
    private static BorderPane aggiungiElemento;
    private static BorderPane pannelloContenitore;

    // inizializzazione (maggiore affidabilità secondo Kiuwan)
    static {
        //stagePrincipale = new Stage(); provoca crash
        schermataPrincipale = new BorderPane();
        aggiungiElemento = new BorderPane();
        pannelloContenitore = new BorderPane();
    }

    public static void mostraSchermataPrincipale() {
        stagePrincipale.setTitle("Schermata Principale");
        pannelloContenitore.setCenter(schermataPrincipale);
    }

    public static void mostraAggiungiElemento() {
        stagePrincipale.setTitle("Aggiungi Elemento");
        pannelloContenitore.setCenter(aggiungiElemento);
    }

    public static void main(String[] args) {
        //launch(args);
        LauncherImpl.launchApplication(Main.class, IntroLogo.class, args);
    }

    @Override
    public void init() throws Exception {
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        Thread.sleep(1800);
    }

    // Avvio dell'applicazione
    @Override
    public void start(Stage primaryStage) throws Exception {
        Database.creaDB();
        stagePrincipale = primaryStage;
        stagePrincipale.setTitle("GestoreDiSistema");
        mostraPannelloContenitore();
        mostraSchermataPrincipale();

        // Tema JavaFX
        //setUserAgentStylesheet(STYLESHEET_CASPIAN);
    }

    private void mostraPannelloContenitore() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("GUI/schermataPrincipale/pannelloContenitore.fxml"));
        pannelloContenitore = loader.load();
        Scene scene = new Scene(pannelloContenitore);
        stagePrincipale.setScene(scene);
        stagePrincipale.setMinWidth(DIM_MIN_LARGHEZZA_FINESTRA);
        stagePrincipale.setMinHeight(DIM_MIN_ALTEZZA_FINESTRA);
        caricaTutteLeSchermate(); // carica tutte le schermate (così non si inizializzano ogni volta)
        stagePrincipale.show(); // mostra la schermata principale

    }

    private void caricaTutteLeSchermate() throws IOException {
        // GestoreDiSistema
        FXMLLoader loaderSchermataPrincipale = new FXMLLoader();

        loaderSchermataPrincipale.setLocation(Main.class.getResource("GUI/schermataPrincipale/schermataPrincipale.fxml"));
        Main.schermataPrincipale = loaderSchermataPrincipale.load();

        //Salvataggio dell'istanza GestoreDiSistema creata per utilizzi futuri
        GestoreDiSistema GestoreDiSistema = loaderSchermataPrincipale.getController();
        GestoreDiSistema.setInstance(GestoreDiSistema);

        try {
            GestoreDiSistema.dbCaricaListe();
        } catch (SQLException e) {
            GestoreAvvisi.mostraErrore("Errore caricamento dati");
        }

        //Schermata Aggiungi Elemento
        FXMLLoader loaderAggiungiElemento = new FXMLLoader();
        loaderAggiungiElemento.setLocation(Main.class.getResource("gestoreElementi/aggiungiElemento/aggiungiElemento.fxml"));
        aggiungiElemento = loaderAggiungiElemento.load();

    }

    public static void mostraSchermataInfo() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("GUI/info/info.fxml"));
        BorderPane infoPane = loader.load();

        Stage infoStage = new Stage();
        infoStage.initModality(Modality.WINDOW_MODAL);
        infoStage.initOwner(Main.stagePrincipale);

        Scene scene = new Scene(infoPane);
        infoStage.setScene(scene);
        infoStage.setResizable(false);
        infoStage.showAndWait();

    }

}
