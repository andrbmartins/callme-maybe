package org.academiadecodigo.hackaton.client;

import javafx.fxml.Initializable;
import org.academiadecodigo.hackaton.client.controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author by André Martins <Code Cadet>
 *         Project stalkers (13/07/17)
 *         <Academia de Código_>
 */
public class Navigation {

    private static Navigation instance = null;

    private LinkedList<Scene> scenes = new LinkedList<Scene>();
    private Map<String, Controller> controllers = new HashMap<>();
    private Stage stage;

    private Navigation() {
    }

    public synchronized static Navigation getInstance() {

        if (instance == null) {

            synchronized (Navigation.class) {

                if (instance == null) {
                    instance = new Navigation();
                }

            }

        }

        return instance;

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void loadScreen(String view) {

        try {
            FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource("/view/" + view + ".fxml"));
            Parent root = loader.load(); // load the view, instantiate the controller, call initialize
            root.getStylesheets().add("/css/callMe.css");

            controllers.put(view, loader.<Controller>getController());
            controllers.get(view).setStage(stage);

            Scene scene = new Scene(root);
            scenes.push(scene);

            setScene(scene);

        } catch (IOException e) {
            System.out.println("Failure to load view " + view);
            e.printStackTrace();
        }

    }

    public void setScene(Scene scene) {
        stage.setScene(scene);
        stage.show();
    }

    public void back() {

        if (scenes.size() == 1) {
            return;
        }

        scenes.pop();
        setScene(scenes.peek());

    }

    public void close() {
        stage.close();
    }

    public Initializable getController(String view) {
        return controllers.get(view);
    }

}
