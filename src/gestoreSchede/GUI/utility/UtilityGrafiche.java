package gestoreSchede.GUI.utility;


import gestoreSchede.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*******************
 * © APT Software *
 *******************/
public class UtilityGrafiche {

    /**
     * PATH ICONA SALVATAGGIO
     **/
    public static final String SAVE_ICON = "GUI/img/imgButton/saveIcon.png";
    /**
     * PATH ICONA ANNULLA
     **/
    public static final String ANNULLA_ICON = "GUI/img/imgButton/annullaIcon.png";
    /**
     * PATH ICONA CANCELLA
     **/
    public static final String GARBAGE_ICON = "GUI/img/imgWarning/garbage.png";
    /**
     * PATH ICONA OK
     **/
    public static final String OK_ICON = "GUI/img/imgWarning/okIcon.png";
    /**
     * PATH ICONA INDIETRO
     **/
    public static final String TORNA_INDIETRO_ICON = "GUI/img/imgButton/backArrow.png";


    public static void impostaIconaPulsante(Button btn, String percorsoIcona) {
        // percorsoIcona = "GUI/img/ ...."
        Image image = new Image(Main.class.getResourceAsStream(percorsoIcona));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(25);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        btn.setGraphic(imageView);
    }

    public static void impostaIconaAlert(Alert alert, String percorsoIcona) {
        Image image = new Image(Main.class.getResourceAsStream(percorsoIcona));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        alert.setGraphic(imageView);
    }


}
