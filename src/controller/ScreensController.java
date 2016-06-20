package controller;

import java.util.Random;

import data.DataBase;

import java.io.File;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.Main;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sikadie
 */
public class ScreensController extends StackPane {
	// Définition des boutons sur lesquels je ferai des animations
	private VBox enTeteBox;
	private HBox hboxListeButton;
	private HBox buttonAjoutContact;

	private HBox buttonCreerOperateur;

	private HBox buttonMesContact;
	private HBox buttonQuitter;

	private DataBase data;
	private int state = 0;

	public void gererEtatButton() {
		if (state == 1) {
			buttonAjoutContact.setDisable(false);
		} else if (state == 2) {
			buttonMesContact.setDisable(false);
		} else if (state == 3) {

			buttonCreerOperateur.setDisable(false);
		}
	}

	public ScreensController() {
		try {
			// setId("stackpane");
			data = new DataBase(3306, "localhost", "carnet_contact", "root",
					"root", "com.mysql.jdbc.Driver");
		} catch (SQLException ex) {
			Logger.getLogger(ScreensController.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	public HashMap<String, Node> getScreens() {
		return screens;
	}

	public void setScreens(HashMap<String, Node> screens) {
		this.screens = screens;
	}

	public static HashMap<String, Node> screens = new HashMap<>();

	public void addScreen(String name, Node screen) {
		screens.put(name, screen);
	}

	public boolean loadScreen(String name, String resource) {
		try {

			/*
			 * FXMLLoader myLoader = new FXMLLoader(getClass().getResource(
			 * resource));
			 */

			FXMLLoader fxmlLoader = new FXMLLoader(new File(resource).toURI()
					.toURL());
			StackPane loadScreen = (StackPane) fxmlLoader.load();

			ControlledScreen myScreenControler = ((ControlledScreen) fxmlLoader
					.getController());
			myScreenControler.setScreenParent(this);

			if (name.compareToIgnoreCase(Main.MAIN_SCREEN) == 0) {
				// Je recupère les menus et je les anime

				BorderPane borderPane = (BorderPane) loadScreen.getChildren()
						.get(0);
				// Définition des boutons sur lesquels je ferai des animations
				enTeteBox = (VBox) borderPane.getTop();
				hboxListeButton = (HBox) enTeteBox.getChildren().get(1);
				buttonAjoutContact = (HBox) hboxListeButton.getChildren()
						.get(1);
				buttonCreerOperateur = (HBox) hboxListeButton.getChildren()
						.get(3);
				buttonMesContact = (HBox) hboxListeButton.getChildren().get(5);
				buttonQuitter = (HBox) hboxListeButton.getChildren().get(7);

				// Animations pour les menus de l'application

				buttonAjoutContact
						.setOnMouseClicked(new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event) {

								setScreen(Main.ACCEUIL_SAVEPERSONNE);

							}
						});

				buttonAjoutContact
						.setOnMouseExited(new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event) {
								FadeTransition fadeTransition = new FadeTransition(
										Duration.millis(200),
										buttonAjoutContact);
								fadeTransition.setFromValue(0.8f);
								fadeTransition.setToValue(1.0f);

								fadeTransition.setAutoReverse(true);

								ScaleTransition scaleTransition = new ScaleTransition(
										Duration.millis(200),
										buttonAjoutContact);
								scaleTransition.setToX(1.0f);
								scaleTransition.setToY(1.0f);

								ParallelTransition parallelTransition = new ParallelTransition();
								parallelTransition.getChildren().addAll(
										fadeTransition, scaleTransition);
								parallelTransition.play();
							}
						});
				buttonAjoutContact
						.setOnMouseEntered(new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event) {
								FadeTransition fadeTransition = new FadeTransition(
										Duration.millis(200),
										buttonAjoutContact);
								fadeTransition.setFromValue(1.0f);
								fadeTransition.setToValue(0.8f);

								fadeTransition.setAutoReverse(true);

								ScaleTransition scaleTransition = new ScaleTransition(
										Duration.millis(200),
										buttonAjoutContact);
								scaleTransition.setToX(0.9f);
								scaleTransition.setToY(0.9f);

								ParallelTransition parallelTransition = new ParallelTransition();
								parallelTransition.getChildren().addAll(
										fadeTransition, scaleTransition);
								parallelTransition.play();
							}
						});

				buttonCreerOperateur
						.setOnMouseClicked(new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event) {

								setScreen(Main.OPERATEUR_SCREEN);
							}
						});

				buttonCreerOperateur
						.setOnMouseExited(new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event) {
								FadeTransition fadeTransition = new FadeTransition(
										Duration.millis(200),
										buttonCreerOperateur);
								fadeTransition.setFromValue(0.8f);
								fadeTransition.setToValue(1.0f);

								fadeTransition.setAutoReverse(true);

								ScaleTransition scaleTransition = new ScaleTransition(
										Duration.millis(200),
										buttonCreerOperateur);
								scaleTransition.setToX(1.0f);
								scaleTransition.setToY(1.0f);

								ParallelTransition parallelTransition = new ParallelTransition();
								parallelTransition.getChildren().addAll(
										fadeTransition, scaleTransition);
								parallelTransition.play();
							}
						});
				buttonCreerOperateur
						.setOnMouseEntered(new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event) {
								FadeTransition fadeTransition = new FadeTransition(
										Duration.millis(200),
										buttonCreerOperateur);
								fadeTransition.setFromValue(1.0f);
								fadeTransition.setToValue(0.8f);

								fadeTransition.setAutoReverse(true);

								ScaleTransition scaleTransition = new ScaleTransition(
										Duration.millis(200),
										buttonCreerOperateur);
								scaleTransition.setToX(0.9f);
								scaleTransition.setToY(0.9f);

								ParallelTransition parallelTransition = new ParallelTransition();
								parallelTransition.getChildren().addAll(
										fadeTransition, scaleTransition);
								parallelTransition.play();
							}
						});
				buttonMesContact
						.setOnMouseClicked(new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event) {

								setScreen(Main.CONTACT_SCREEN);
							}
						});

				buttonMesContact
						.setOnMouseExited(new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event) {
								FadeTransition fadeTransition = new FadeTransition(
										Duration.millis(200), buttonMesContact);
								fadeTransition.setFromValue(0.8f);
								fadeTransition.setToValue(1.0f);

								fadeTransition.setAutoReverse(true);

								ScaleTransition scaleTransition = new ScaleTransition(
										Duration.millis(200), buttonMesContact);
								scaleTransition.setToX(1.0f);
								scaleTransition.setToY(1.0f);

								ParallelTransition parallelTransition = new ParallelTransition();
								parallelTransition.getChildren().addAll(
										fadeTransition, scaleTransition);
								parallelTransition.play();
							}
						});
				buttonMesContact
						.setOnMouseEntered(new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event) {
								FadeTransition fadeTransition = new FadeTransition(
										Duration.millis(200), buttonMesContact);
								fadeTransition.setFromValue(1.0f);
								fadeTransition.setToValue(0.8f);

								fadeTransition.setAutoReverse(true);

								ScaleTransition scaleTransition = new ScaleTransition(
										Duration.millis(200), buttonMesContact);
								scaleTransition.setToX(0.9f);
								scaleTransition.setToY(0.9f);

								ParallelTransition parallelTransition = new ParallelTransition();
								parallelTransition.getChildren().addAll(
										fadeTransition, scaleTransition);
								parallelTransition.play();
							}
						});

				buttonQuitter.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						Main.stage.close();
					}
				});

				buttonQuitter.setOnMouseExited(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						FadeTransition fadeTransition = new FadeTransition(
								Duration.millis(200), buttonQuitter);
						fadeTransition.setFromValue(0.8f);
						fadeTransition.setToValue(1.0f);

						fadeTransition.setAutoReverse(true);

						ScaleTransition scaleTransition = new ScaleTransition(
								Duration.millis(200), buttonQuitter);
						scaleTransition.setToX(1.0f);
						scaleTransition.setToY(1.0f);

						ParallelTransition parallelTransition = new ParallelTransition();
						parallelTransition.getChildren().addAll(fadeTransition,
								scaleTransition);
						parallelTransition.play();
					}
				});
				buttonQuitter.setOnMouseEntered(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						FadeTransition fadeTransition = new FadeTransition(
								Duration.millis(200), buttonQuitter);
						fadeTransition.setFromValue(1.0f);
						fadeTransition.setToValue(0.8f);

						fadeTransition.setAutoReverse(true);

						ScaleTransition scaleTransition = new ScaleTransition(
								Duration.millis(200), buttonQuitter);
						scaleTransition.setToX(0.9f);
						scaleTransition.setToY(0.9f);

						ParallelTransition parallelTransition = new ParallelTransition();
						parallelTransition.getChildren().addAll(fadeTransition,
								scaleTransition);
						parallelTransition.play();
					}
				});

			} else if (name.compareToIgnoreCase(Main.ACCEUIL_SAVEPERSONNE) == 0) {

			} else if (name.compareToIgnoreCase(Main.CONTACT_SCREEN) == 0) {

			} else if (name.compareToIgnoreCase(Main.OPERATEUR_SCREEN) == 0) {

			}

			addScreen(name, loadScreen);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean setScreen(final String name) {

		if (screens.get(name) != null) {

			if (name.compareToIgnoreCase(Main.MAIN_SCREEN) == 0) {
				// Initialisation du parent du screen utile dans la classe des
				// boutons package morceaux
				gererEtatButton();
				if (getChildren().size() > 0)
					getChildren().clear();
				getChildren().add(screens.get(Main.MAIN_SCREEN));

			} else if (name.compareToIgnoreCase(Main.ACCEUIL_SAVEPERSONNE) == 0) {

				StackPane stackAcceuil = (StackPane) getChildren().get(0);
				BorderPane border = (BorderPane) stackAcceuil.getChildren()
						.get(0);
				StackPane stack = (StackPane) border.getCenter();
				gererEtatButton();
				state = 1;
				if (stack.getChildren().size() > 0)
					stack.getChildren().clear();
				buttonAjoutContact.setDisable(true);
				stack.getChildren().add(screens.get(Main.ACCEUIL_SAVEPERSONNE));

			} else if (name.compareToIgnoreCase(Main.CONTACT_SCREEN) == 0) {
				StackPane stackAcceuil = (StackPane) getChildren().get(0);
				BorderPane border = (BorderPane) stackAcceuil.getChildren()
						.get(0);
				StackPane stack = (StackPane) border.getCenter();
				gererEtatButton();
				state = 2;
				if (stack.getChildren().size() > 0)
					stack.getChildren().clear();
				buttonMesContact.setDisable(true);
				stack.getChildren().add(screens.get(Main.CONTACT_SCREEN));
			} else if (name.compareToIgnoreCase(Main.OPERATEUR_SCREEN) == 0) {
				StackPane stackAcceuil = (StackPane) getChildren().get(0);
				BorderPane border = (BorderPane) stackAcceuil.getChildren()
						.get(0);
				StackPane stack = (StackPane) border.getCenter();
				gererEtatButton();
				state = 3;
				if (stack.getChildren().size() > 0)
					stack.getChildren().clear();
				buttonCreerOperateur.setDisable(true);
				stack.getChildren().add(screens.get(Main.OPERATEUR_SCREEN));
			} else {
				System.out.println("screen has'nt been loaded!\n");
				return false;
			}

		}
		return true;
	}

	public boolean unloadScreen(String name) {
		if (screens.remove(name) == null) {
			System.out.println("Screen didn't exist");
			return false;
		} else {
			return true;
		}
	}

}
