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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 *
 * @author Sikadie
 */
public class FXMLControllerCreatePerson implements Initializable,
		ControlledScreen {
	private ObservableList<modele.Telephone> telephoneData = FXCollections
			.observableArrayList();
	ScreensController myController;
	DataBase dataBase;
	@FXML
	private TextField textFieldNom;
	@FXML
	private TextField textFieldPrenom;
	@FXML
	private IntField textFieldNumero;
	@FXML
	private ComboBox comboOperateur;
	@FXML
	private TableView tableViewNumero;
	@FXML
	private TextField textFieldAdresse;
	@FXML
	private TableView tableViewAdresse;
	@FXML
	private Label message;
	@FXML
	private Label messageAdresse;

	public FXMLControllerCreatePerson() {
		// tableViewNumero.setItems(telephoneData);
	}

	@FXML
	private void handleLoadOperateur(MouseEvent event) {

		try {
			this.dataBase = new DataBase(3306, "localhost", "carnet_contact",
					"root", "root", "com.mysql.jdbc.Driver");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		comboOperateur.getItems().clear();
		try {
			String result = dataBase.getOperateurs();
			String[] operateurs = result.split("&");

			for (int i = 0; i < operateurs.length; i++) {
				comboOperateur.getItems().add(operateurs[i]);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void handleAddNumeroActionButton(ActionEvent event) {

		try {

			this.dataBase = new DataBase(3306, "localhost", "carnet_contact",
					"root", "root", "com.mysql.jdbc.Driver");
			if (textFieldNumero.getText().trim().length() > 0) {

				// je vérifie si le numéro à ajouter n'existe pas déjà
				boolean trouve = false;
				List<Telephone> liste = tableViewNumero.getItems();
				int i = 0;
				while ((!trouve) && (i < liste.size())) {
					if (liste
							.get(i)
							.getTelephone()
							.compareToIgnoreCase(
									textFieldNumero.getText().trim()) == 0) {
						trouve = true;
					}
					i++;
				}
				if (trouve == false)
					tableViewNumero.getItems().add(
							new Telephone(textFieldNumero.getText().trim(),
									comboOperateur.getSelectionModel()
											.getSelectedItem().toString()));
				else
					message.setText("Les numéros enregistrés pour une persone doivent être différents");

			} else
				message.setText("Veuillez sasir le numéro");

		} catch (SQLException ex) {
			Logger.getLogger(FXMLControllerCreatePerson.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		try {

		} catch (Exception ex) {
			Logger.getLogger(FXMLControllerCreatePerson.class.getName()).log(
					Level.SEVERE, null, ex);
		}

	}

	@FXML
	private void handleRemoveNumeroActionButton(ActionEvent event) {

		try {

			this.dataBase = new DataBase(3306, "localhost", "carnet_contact",
					"root", "root", "com.mysql.jdbc.Driver");

			// Je recupère les informations
			List<Telephone> telephones = tableViewNumero.getSelectionModel()
					.getSelectedItems();
			if ((telephones == null) || (telephones.size() == 0)) {

				message.setText("Veuillez selectionner un numéro pour la suppression");

			} else {
				for (Telephone telephone : telephones) {
					tableViewNumero.getItems().remove(telephone);
					tableViewNumero.refresh();
				}

			}
		} catch (SQLException ex) {
			Logger.getLogger(FXMLControllerCreatePerson.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		try {

		} catch (Exception ex) {
			Logger.getLogger(FXMLControllerCreatePerson.class.getName()).log(
					Level.SEVERE, null, ex);
		}

	}



	@FXML
	private void handleEnregistrerPersonneActionButton(ActionEvent event) {

		try {

			this.dataBase = new DataBase(3306, "localhost", "carnet_contact",
					"root", "root", "com.mysql.jdbc.Driver");
			if ((textFieldNom.getText().length() == 0)
					|| (tableViewNumero.getItems().size() == 0)) {
				message.setText("Veuillez fournir le nom, le prénom et au moins un numéro de téléphone");

			} else {

				// on renregistre la personne
				try {
					dataBase.insertPersonne(textFieldNom.getText(),
							textFieldPrenom.getText());

				} catch (SQLException ex) {
					message.setText("Erreur lors de l'enregistrement, une personne de ce nom existe déja");
				}

				// on renregistre les numéros de la personne
				try {
					List<Telephone> telephones = tableViewNumero.getItems();
					for (Telephone telephone : telephones) {
						dataBase.insertTelephone(telephone.getTelephone(),
								textFieldNom.getText(),
								telephone.getOperateur());
					}

				} catch (SQLException ex) {
					message.setText("Erreur lors de l'enregistrement, un numéro existe déja");
				}

				// on renregistre les numéros de la personne
				try {
					List<Adresse> adressses = tableViewAdresse.getItems();
					for (Adresse adresse : adressses) {
						dataBase.insertAdresse(adresse.getAdresse(),
								textFieldNom.getText());
					}

				} catch (SQLException ex) {
					message.setText("Erreur lors de l'enregistrement, un numéro existe déja");
				}

				message.setText("Le contact: " + textFieldNom.getText()
						+ " a été crée");
			}
			textFieldNom.setText("");
			textFieldPrenom.setText("");
			tableViewNumero.getItems().clear();
			tableViewAdresse.getItems().clear();

		} catch (SQLException ex) {
			Logger.getLogger(FXMLControllerCreatePerson.class.getName()).log(
					Level.SEVERE, null, ex);

		}

	}

	@FXML
	private void handleAddAdresseActionButton(ActionEvent event) {

		try {
			this.dataBase = new DataBase(3306, "localhost", "carnet_contact",
					"root", "root", "com.mysql.jdbc.Driver");
			if (textFieldAdresse.getText().trim().length() > 0) {
				// je vérifie si le numéro à ajouter n'existe pas déjà
				boolean trouve = false;
				List<Adresse> liste = tableViewAdresse.getItems();
				int i = 0;
				while ((!trouve) && (i < liste.size())) {
					if (liste
							.get(i)
							.getAdresse()
							.compareToIgnoreCase(
									textFieldAdresse.getText().trim()) == 0) {
						trouve = true;
					}
					i++;
				}
				if (trouve == false)
					tableViewAdresse.getItems().add(
							new Adresse(textFieldAdresse.getText()));
				else
					messageAdresse
							.setText("Veuillez saisir des adresses différentes");

			} else {
				messageAdresse.setText("Veuillez saisir l'adresse");
			}

		} catch (SQLException ex) {
			Logger.getLogger(FXMLControllerCreatePerson.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		try {

		} catch (Exception ex) {
			Logger.getLogger(FXMLControllerCreatePerson.class.getName()).log(
					Level.SEVERE, null, ex);
		}

	}

	@FXML
	private void handleRemoveAdresseActionButton(ActionEvent event) {

		try {

			this.dataBase = new DataBase(3306, "localhost", "carnet_contact",
					"root", "root", "com.mysql.jdbc.Driver");
			// Je recupère les informations
			List<Adresse> adresses = tableViewAdresse.getSelectionModel()
					.getSelectedItems();
			if ((adresses == null) || (adresses.size() == 0)) {
				messageAdresse
						.setText("Veuillez selectionner une adresse pour la suppression");
			} else {
				for (Adresse adresse : adresses) {
					tableViewAdresse.getItems().remove(adresse);
					tableViewAdresse.refresh();
				}

			}

		} catch (SQLException ex) {
			Logger.getLogger(FXMLControllerCreatePerson.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		try {

		} catch (Exception ex) {
			Logger.getLogger(FXMLControllerCreatePerson.class.getName()).log(
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
		ObservableList listeColonnesAdresse = tableViewAdresse.getColumns();
		String[] tablePropertiesAdresse = new String[1];
		tablePropertiesAdresse[0] = "adresse";

		for (int i = 0; i < listeColonnesAdresse.size(); i++) {
			TableColumn column = (TableColumn) listeColonnesAdresse.get(i);
			column.setCellValueFactory(new PropertyValueFactory<>(
					tablePropertiesAdresse[i]));
		}
		// Je configure la tableView à la reception de données
		ObservableList listeColonnes = tableViewNumero.getColumns();
		String[] tableProperties = new String[2];
		tableProperties[0] = "telephone";
		tableProperties[1] = "operateur";

		for (int i = 0; i < listeColonnes.size(); i++) {
			TableColumn column = (TableColumn) listeColonnes.get(i);
			column.setCellValueFactory(new PropertyValueFactory<>(
					tableProperties[i]));
		}
		try {
			String result = dataBase.getOperateurs();
			String[] operateurs = result.split("&");
			for (int i = 0; i < operateurs.length; i++) {
				comboOperateur.getItems().add(operateurs[i]);
			}
			comboOperateur.getSelectionModel().select(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setScreenParent(ScreensController screenPage) {
		myController = screenPage;
	}

}
