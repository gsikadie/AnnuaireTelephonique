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
import modele.Personne;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 *
 * @author Sikadie
 */
public class FXMLControllerChercherPersonne implements Initializable,
		ControlledScreen {
	private ObservableList<modele.Telephone> telephoneData = FXCollections
			.observableArrayList();
	ScreensController myController;
	DataBase dataBase;
	@FXML
	private ComboBox comboOperateur;
	@FXML
	private TextField textFieldNom;
	@FXML
	private IntField numeroIntField;
	@FXML
	private TextField textFieldAdresse;
	@FXML
	private TableView tableViewNumero;
	@FXML
	private TableView tableViewAdresse;
	@FXML
	private TableView tableInfoPersonne;
	@FXML
	private Label message;
	@FXML
	private Label messageAdresse;
	@FXML
	private Label messageNumero;

	public FXMLControllerChercherPersonne() {
		// tableViewNumero.setItems(telephoneData);
	}

	@FXML
	private void handleRechercherPersonne(KeyEvent event) {
		//if(event.getEventType()==KeyEvent.KEY_RELEASED){

		try {
			this.dataBase = new DataBase(3306, "localhost", "carnet_contact",
					"root", "root", "com.mysql.jdbc.Driver");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tableInfoPersonne.getItems().clear();
		tableViewNumero.getItems().clear();
		tableViewAdresse.getItems().clear();
		// Je configure la tableView à la reception de données
		ObservableList listeColonnesInfo = tableInfoPersonne.getColumns();
		String[] tablePropertiesInfo = new String[2];
		tablePropertiesInfo[0] = "nom";
		tablePropertiesInfo[1] = "prenom";

		for (int i = 0; i < listeColonnesInfo.size(); i++) {
			TableColumn column = (TableColumn) listeColonnesInfo.get(i);
			column.setCellValueFactory(new PropertyValueFactory<>(
					tablePropertiesInfo[i]));
		}
		try {
			String chaine = textFieldNom.getText();
			String result = dataBase.getPersonne(chaine);
			if (result.length() > 0) {
				String[] personnes = result.split("&");
				for (int i = 0; i < personnes.length; i++) {
					String[] infoPers = personnes[i].split(";");
					tableInfoPersonne.getItems().add(
							new Personne(infoPers[0], infoPers[1]));
				}
			}
			tableInfoPersonne.refresh();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//}
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
	private void handleAddNumeroActionButton(KeyEvent event) {

		try {

			this.dataBase = new DataBase(3306, "localhost", "carnet_contact",
					"root", "root", "com.mysql.jdbc.Driver");
			if ((numeroIntField.getText().trim().length() > 0)
					&& (tableInfoPersonne.getSelectionModel().getSelectedItem() != null)) {

				// je vérifie si le numéro à ajouter n'existe pas déjà
				boolean trouve = false;
				List<Telephone> liste = tableViewNumero.getItems();
				int i = 0;
				while ((!trouve) && (i < liste.size())) {
					if (liste
							.get(i)
							.getTelephone()
							.compareToIgnoreCase(
									numeroIntField.getText().trim()) == 0) {
						trouve = true;
					}
					i++;
				}
				if (trouve == false) {
					/*
					 * tableViewNumero.getItems().add( new
					 * Telephone(numeroIntField.getText().trim(),
					 * comboOperateur.getSelectionModel()
					 * .getSelectedItem().toString()));
					 */

					try {
						dataBase.insertTelephone(numeroIntField.getText(),
								((Personne) tableInfoPersonne
										.getSelectionModel().getSelectedItem())
										.getNom(), comboOperateur
										.getSelectionModel().getSelectedItem()
										.toString());
					} catch (Exception e) {
						message.setText("Ce numéro existe déjà, veuillez entrer un autre");
					}

					List<Personne> personnes = tableInfoPersonne
							.getSelectionModel().getSelectedItems();
					if ((personnes == null) || (personnes.size() == 0)) {
						message.setText("Veuillez selectionner une personne");
					} else {
						for (Personne personne : personnes) {
							// je cherche les contacts d'une personne

							String numerosList = this.dataBase
									.getNumeroPersone(personne.getNom());
							if (numerosList.length() > 0) {
								tableViewNumero.getItems().clear();
								tableViewAdresse.getItems().clear();
								String[] numeros = numerosList.split("&");
								for (int k = 0; k < numeros.length; k++) {
									String[] infoNum = numeros[k].split(";");
									tableViewNumero.getItems().add(
											new Telephone(infoNum[0],
													infoNum[1]));
								}
							}
							// je cherche les adresses d'une persone
							String adressesString = this.dataBase
									.getAdressePersone(personne.getNom());
							if (adressesString.length() > 0) {
								String[] adresses = adressesString.split("&");
								for (int j = 0; j < adresses.length; j++) {

									tableViewAdresse.getItems().add(
											new Adresse(adresses[j]));
								}
							}
							tableViewAdresse.refresh();
							tableViewNumero.refresh();
							// je cherche les adressses d'une personne
						}

					}
				} else
					message.setText("Les numéros enregistrés pour une personne doivent être différents");

			} else
				message.setText("contact non sélectionné ou numéro non entré");

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

			} else if (tableViewNumero.getItems().size() > 1) {
				for (Telephone telephone : telephones) {
					dataBase.deleteTelephoneTel(telephone.getTelephone());
					tableViewNumero.getItems().remove(telephone);
					tableViewNumero.refresh();
					messageNumero.setText("Numéro supprimé");
				}

			} else {

				messageNumero
						.setText("Il reste un seul numéro, vous ne pouvez pas le supprimer");

			}
		} catch (SQLException ex) {
			Logger.getLogger(FXMLControllerChercherPersonne.class.getName())
					.log(Level.SEVERE, null, ex);
		}
		try {

		} catch (Exception ex) {
			Logger.getLogger(FXMLControllerChercherPersonne.class.getName())
					.log(Level.SEVERE, null, ex);
		}

	}

	@FXML
	private void handleSupprimerPersonneActionButton(ActionEvent event) {

		try {

			this.dataBase = new DataBase(3306, "localhost", "carnet_contact",
					"root", "root", "com.mysql.jdbc.Driver");

			List<Personne> personnes = tableInfoPersonne.getSelectionModel()
					.getSelectedItems();
			if ((personnes == null) || (personnes.size() == 0)) {
				message.setText("Veuillez selectionner une personne");
			} else {
				for (Personne personne : personnes) {
					try {
						dataBase.deleteAdresse(personne.getNom());
						dataBase.deleteTelephone(personne.getNom());
						dataBase.deletePersonne(personne.getNom());

					} catch (Exception e) {
						message.setText("Erreur lors de la suppression de "
								+ personne.getNom());
					}

				}
				tableInfoPersonne.getItems().clear();
				String result = dataBase.getPersonne();
				if (result.length() > 0) {
					String[] infoPerss = null;
					String[] personness = result.split("&");
					for (int i = 0; i < personness.length; i++) {
						infoPerss = personness[i].split(";");
						tableInfoPersonne.getItems().add(
								new Personne(infoPerss[0], infoPerss[1]));

						// je cherche les adressses d'une personne
					}
					message.setText("Le contact " + infoPerss[0]
							+ " a été supprimé avec success");
				}
				tableInfoPersonne.refresh();
				tableViewAdresse.getItems().clear();
				tableViewAdresse.refresh();
				tableViewNumero.getItems().clear();
				tableViewNumero.refresh();
			}
		} catch (SQLException ex) {
			Logger.getLogger(FXMLControllerChercherPersonne.class.getName())
					.log(Level.SEVERE, null, ex);

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
				if (trouve == false) {

					try {
						dataBase.insertAdresse(textFieldAdresse.getText(),
								((Personne) tableInfoPersonne
										.getSelectionModel().getSelectedItem())
										.getNom());
					} catch (Exception e) {
						messageAdresse
								.setText("Cette adresse existe déjà impossible de la créer");
					}

					List<Personne> personnes = tableInfoPersonne
							.getSelectionModel().getSelectedItems();
					if ((personnes == null) || (personnes.size() == 0)) {
						message.setText("Veuillez selectionner une personne");
					} else {
						for (Personne personne : personnes) {

							tableViewAdresse.getItems().clear();

							// je cherche les adresses d'une persone
							String adressesString = this.dataBase
									.getAdressePersone(personne.getNom());
							String[] adresses = adressesString.split("&");
							for (int k = 0; k < adresses.length; k++) {

								tableViewAdresse.getItems().add(
										new Adresse(adresses[k]));
							}
							tableViewAdresse.refresh();

							// je cherche les adressses d'une personne
						}

					}

				}

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

				try {
					dataBase.deleteAdresseAdr(((Adresse) tableViewAdresse
							.getSelectionModel().getSelectedItem())
							.getAdresse());
				} catch (SQLException e) {
					e.printStackTrace();
					messageAdresse
							.setText("Suppression de l'adresse impossible");
				}

				// je cherche les adresses d'une persone

				String adressesString = this.dataBase
						.getAdressePersone(((Personne) tableInfoPersonne
								.getSelectionModel().getSelectedItem())
								.getNom());
				tableViewAdresse.getItems().clear();
				if (adressesString.length() > 0) {

					String[] adressess = adressesString.split("&");
					for (int i = 0; i < adressess.length; i++) {

						tableViewAdresse.getItems().add(
								new Adresse(adressess[i]));
					}
				}
				tableViewAdresse.refresh();

			}

		} catch (SQLException ex) {
			Logger.getLogger(FXMLControllerChercherPersonne.class.getName())
					.log(Level.SEVERE, null, ex);
		}
		try {

		} catch (Exception ex) {
			Logger.getLogger(FXMLControllerChercherPersonne.class.getName())
					.log(Level.SEVERE, null, ex);
		}

	}

	@FXML
	private void handleAfficherToutActionButton(ActionEvent event) {

		try {
			this.dataBase = new DataBase(3306, "localhost", "carnet_contact",
					"root", "root", "com.mysql.jdbc.Driver");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// textFieldNom.setText("");
		tableInfoPersonne.getItems().clear();
		tableViewNumero.getItems().clear();
		tableViewAdresse.getItems().clear();
		// Je configure la tableView à la reception de données
		ObservableList listeColonnesInfo = tableInfoPersonne.getColumns();
		String[] tablePropertiesInfo = new String[2];
		tablePropertiesInfo[0] = "nom";
		tablePropertiesInfo[1] = "prenom";

		for (int i = 0; i < listeColonnesInfo.size(); i++) {
			TableColumn column = (TableColumn) listeColonnesInfo.get(i);
			column.setCellValueFactory(new PropertyValueFactory<>(
					tablePropertiesInfo[i]));
		}
		try {
			String result = dataBase.getPersonne();
			if (result.length() > 0) {
				String[] personnes = result.split("&");
				for (int i = 0; i < personnes.length; i++) {
					String[] infoPers = personnes[i].split(";");
					tableInfoPersonne.getItems().add(
							new Personne(infoPers[0], infoPers[1]));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Affichage du numéro et de l'adresse d'une personne en cas de selection
	@FXML
	private void handleSelectPersonne(MouseEvent event) {

		try {

			this.dataBase = new DataBase(3306, "localhost", "carnet_contact",
					"root", "root", "com.mysql.jdbc.Driver");

			List<Personne> personnes = tableInfoPersonne.getSelectionModel()
					.getSelectedItems();
			if ((personnes == null) || (personnes.size() == 0)) {
				message.setText("Veuillez selectionner une personne");
			} else {
				for (Personne personne : personnes) {
					// je cherche les contacts d'une personne
					String numerosList = this.dataBase
							.getNumeroPersone(personne.getNom());
					tableViewNumero.getItems().clear();
					tableViewAdresse.getItems().clear();
					String[] numeros = numerosList.split("&");
					for (int i = 0; i < numeros.length; i++) {
						String[] infoNum = numeros[i].split(";");
						tableViewNumero.getItems().add(
								new Telephone(infoNum[0], infoNum[1]));
					}
					// je cherche les adresses d'une persone
					String adressesString = this.dataBase
							.getAdressePersone(personne.getNom());
					String[] adresses = adressesString.split("&");
					for (int i = 0; i < adresses.length; i++) {

						tableViewAdresse.getItems().add(
								new Adresse(adresses[i]));
					}
					tableViewAdresse.refresh();
					tableViewNumero.refresh();
					// je cherche les adressses d'une personne
				}

			}

		} catch (SQLException ex) {
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
		ObservableList listeColonnesInfo = tableInfoPersonne.getColumns();
		String[] tablePropertiesInfo = new String[2];
		tablePropertiesInfo[0] = "nom";
		tablePropertiesInfo[1] = "prenom";

		for (int i = 0; i < listeColonnesInfo.size(); i++) {
			TableColumn column = (TableColumn) listeColonnesInfo.get(i);
			column.setCellValueFactory(new PropertyValueFactory<>(
					tablePropertiesInfo[i]));
		}

		// Je configure la tableView à la reception de données
		ObservableList listeColonnesNum = tableViewNumero.getColumns();
		String[] tablePropertiesNum = new String[2];
		tablePropertiesNum[0] = "telephone";
		tablePropertiesNum[1] = "operateur";

		for (int i = 0; i < listeColonnesNum.size(); i++) {
			TableColumn column = (TableColumn) listeColonnesNum.get(i);
			column.setCellValueFactory(new PropertyValueFactory<>(
					tablePropertiesNum[i]));
		}

		// Je configure la tableView à la reception de données
		ObservableList listeColonnesAdr = tableViewAdresse.getColumns();
		String[] tablePropertiesAdr = new String[1];
		tablePropertiesAdr[0] = "adresse";

		for (int i = 0; i < listeColonnesAdr.size(); i++) {
			TableColumn column = (TableColumn) listeColonnesAdr.get(i);
			column.setCellValueFactory(new PropertyValueFactory<>(
					tablePropertiesAdr[i]));
		}

		try {
			String result = dataBase.getPersonne();
			if (result.length() > 0) {
				String[] personnes = result.split("&");
				for (int i = 0; i < personnes.length; i++) {
					String[] infoPers = personnes[i].split(";");
					tableInfoPersonne.getItems().add(
							new Personne(infoPers[0], infoPers[1]));
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
