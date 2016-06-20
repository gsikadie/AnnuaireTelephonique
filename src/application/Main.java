/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import controller.ScreensController;
import javafx.application.Application;
import javafx.scene.Scene;
//import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Sikadie
 */
public class Main extends Application {

    public static final String MAIN_SCREEN = "main";
    public static final String MAIN_SCREEN_FXML = "uifxml/FXMLAcceuil.fxml";
    public static final String ACCEUIL_SAVEPERSONNE = "enregistrerPersonne";
    public static final String ACCEUIL_SAVEPERSONNE_FXML = "uifxml/FXMLEnregistrerPersonne.fxml";
    public static final String CONTACT_SCREEN = "contact";
    public static final String CONTACT_SCREEN_FXML = "uifxml/FXMLMesContacts.fxml";
    public static final String OPERATEUR_SCREEN = "operateur";
    public static final String OPERATEUR_SCREEN_FXML = "uifxml/FXMLOperateur.fxml";

    public static ScreensController mainContainer = new ScreensController();
    public static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        stage.centerOnScreen();
        mainContainer.loadScreen(MAIN_SCREEN, MAIN_SCREEN_FXML);
        mainContainer.loadScreen(ACCEUIL_SAVEPERSONNE, ACCEUIL_SAVEPERSONNE_FXML);
        mainContainer.loadScreen(CONTACT_SCREEN, CONTACT_SCREEN_FXML);
        mainContainer.loadScreen(OPERATEUR_SCREEN, OPERATEUR_SCREEN_FXML);
        mainContainer.setScreen(MAIN_SCREEN);

        StackPane root = new StackPane();

        root.getChildren().add(mainContainer);

        Scene scene = new Scene(root);
        this.stage= stage;
        this.stage.setScene(scene);
        // Set the application icon.

       // stage.getIcons().add(new Image(PanoFXMLLogin.class.getResourceAsStream("circle.png")));

        this.stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}