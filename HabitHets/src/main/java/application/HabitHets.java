package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.SaveOnShutDown;

/**
 * HabitHets Class of application
 * extends javafx application and runs HabitHets as a java fx application through start method
 */
public class HabitHets extends Application {

    /**
     * Is inherited by Application
     * Sets up root for the whole application
     * After this is run initialize in controller calendar is called
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/window.fxml"));
        primaryStage.setTitle("HabitHets");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /**
     * HabitHets method for HabitHets application
     * Includes a runtime shutDownHook that calls saveAll method in SaveOnShutDown class on shutdown
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() { SaveOnShutDown.saveAll();}
        }, "Shutdown-thread"));
    }
}