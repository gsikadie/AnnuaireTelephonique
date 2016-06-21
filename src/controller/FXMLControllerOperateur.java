/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import data.DataBase;
import intfield.IntField;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.dialog.Dialogs;

import modele.Adresse;
import modele.Operateur;
import modele.Telephone;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
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
public class FXMLControllerOperateur implements Initializable, ControlledScreen {
	private ObservableList<modele.Telephone> telephoneData = FXCollections
			.observableArrayList();
	ScreensController myController;
	DataBase dataBase;
	@FXML
	private TextField textFieldOperateur;
	@FXML
	private Label message;
	@FXML
	private TableView tableViewOperateur;

	public FXMLControllerOperateur() {
	
	}

	@FXML
	private void handleAddOperateurActionButton(ActionEvent event) {

		try {

			this.dataBase = new DataBase(3306, "localhost", "carnet_contact",
					"root", "root", "com.mysql.jdbc.Driver");
			if (textFieldOperateur.getText().trim().length() > 0) {

				// je vérifie si le operateur à ajouter n'existe pas déjà
				boolean trouve = false;
				List<Operateur> liste = tableViewOperateur.getItems();
				int i = 0;
				while ((!trouve) && (i < liste.size())) {
					if (liste
							.get(i)
							.getOperateur()
							.compareToIgnoreCase(
									textFieldOperateur.getText().trim()) == 0) {
						trouve = true;
					}
					i++;
				}

				try {
					dataBase.insertOperateur(textFieldOperateur.getText());
				} catch (Exception e) {
					trouve = true;
				}
				if (trouve == false) {
					tableViewOperateur.getItems().add(
							new Operateur(textFieldOperateur.getText()));
				} else
					message.setText("Les opérateurs enregistrés  doivent être différents");

			} else
				message.setText("Veuillez saisir l'opérateur");

		} catch (SQLException ex) {
			Logger.getLogger(FXMLControllerOperateur.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		try {

		} catch (Exception ex) {
			Logger.getLogger(FXMLControllerOperateur.class.getName()).log(
					Level.SEVERE, null, ex);
		}

	}

	@FXML
	private void handleRemoveOperateurActionButton(ActionEvent event) {

		try {

			this.dataBase = new DataBase(3306, "localhost", "carnet_contact",
					"root", "root", "com.mysql.jdbc.Driver");

			// Je recupère les informations
			List<Operateur> operateurs = tableViewOperateur.getSelectionModel()
					.getSelectedItems();
			if ((operateurs == null) || (operateurs.size() == 0)) {

				message.setText("Veuillez selectionner un opérateur pour la suppression");

			} else {

				for (Operateur operateur : operateurs) {
					try {
						dataBase.deleteOperateur(operateur.getOperateur());
						tableViewOperateur.getItems().remove(operateur);
						tableViewOperateur.refresh();
					} catch (Exception e) {
						message.setText("Impossible de supprimer l'opérateur "
								+ operateur.getOperateur()
								+ "Utilisé par le système");
					}
				}

			}
		} catch (SQLException ex) {
			Logger.getLogger(FXMLControllerOperateur.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		try {

		} catch (Exception ex) {
			Logger.getLogger(FXMLControllerOperateur.class.getName()).log(
					Level.SEVERE, null, ex);
		}

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
		try {
			this.dataBase = new DataBase(3306, "localhost", "carnet_contact",
					"root", "root", "com.mysql.jdbc.Driver");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Je configure la tableView à la reception de données
		ObservableList listeColonnes = tableViewOperateur.getColumns();
		String[] tableProperties = new String[1];
		tableProperties[0] = "operateur";

		for (int i = 0; i < listeColonnes.size(); i++) {
			TableColumn column = (TableColumn) listeColonnes.get(i);
			column.setCellValueFactory(new PropertyValueFactory<>(
					tableProperties[i]));
		}
		try {
			String result = dataBase.getOperateurs();
			String[] operateurs = result.split("&");
			for (int i = 0; i < operateurs.length; i++) {
				tableViewOperateur.getItems().add(new Operateur(operateurs[i]));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;
	}

}
