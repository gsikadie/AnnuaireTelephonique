/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import data.DataBase;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


/**
 *
 * @author Sikadie
 */
public class FXMLControllerMain implements Initializable, ControlledScreen {

    ScreensController myController;
    DataBase dataBase;
    @FXML
    private Text actiontarget;


    public FXMLControllerMain() {
        actiontarget = new Text("Acces denied, please check");
    }

    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {

        try {

            this.dataBase = new DataBase(3306, "localhost", "panofxdb", "root", "root", "com.mysql.jdbc.Driver");

        } catch (SQLException ex) {
            Logger.getLogger(FXMLControllerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
           
            
        } catch (Exception ex) {
            Logger.getLogger(FXMLControllerMain.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

}
