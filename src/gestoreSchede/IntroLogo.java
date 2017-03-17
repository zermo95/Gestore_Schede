package gestoreSchede;

import javafx.animation.FadeTransition;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/*******************
 * © APT Software *
 *******************/
public class IntroLogo extends Preloader {
    private Stage stage;

    private Scene createPreloaderScene() {
        // settaggi grafici
        Image logoImage = new Image(Main.class.getResourceAsStream("GUI/img/imgLogo/logoAPT.png"));
        ImageView logoIcon = new ImageView();
        logoIcon.setImage(logoImage);
        logoIcon.setFitWidth(250);
        logoIcon.setPreserveRatio(true);
        logoIcon.setSmooth(true);
        logoIcon.setCache(true);

        //bar = new ProgressBar();
        BorderPane p = new BorderPane();
        p.setCenter(logoIcon);
        p.backgroundProperty().setValue(Background.EMPTY);
        FadeTransition ft = new FadeTransition(Duration.millis(1500), p);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
        return new Scene(p, 300, 200);
    }

    public void start(Stage s) throws Exception {
        stage = s;
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(createPreloaderScene());
        stage.show();
    }

    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        //bar.setProgress(pn.getProgress());
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    }


}