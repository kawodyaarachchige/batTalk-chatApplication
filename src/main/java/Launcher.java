import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

    public class Launcher extends Application {

        @Override
        public void start(Stage stage) throws IOException {

            Parent root = FXMLLoader.load(this.getClass().getResource("/view/welcome_form.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("welcome Form");
            stage.centerOnScreen();
            stage.show();
        }

        public static void main(String[] args) {
            launch  ();
        }
    }

