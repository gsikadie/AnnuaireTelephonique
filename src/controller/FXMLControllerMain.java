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

import modele.Telephone;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
	
	public FXMLControllerMain() {
		// tableViewNumero.setItems(telephoneData);
	}

	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
		}
	

	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;
	}

}
