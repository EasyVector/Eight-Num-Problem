package UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

public class Window extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL location = this.getClass().getClassLoader().getResource("AI.fxml");
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        Controller controller = fxmlLoader.getController();
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setTitle("A* and A Algorithm : Eight Number Question");
        primaryStage.show();
        primaryStage.setResizable(false);
        controller.init();
    }
}
