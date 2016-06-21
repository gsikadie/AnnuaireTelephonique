package modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Adresse {
	private final StringProperty adresse;

	/**
	 * Constructor with some initial data.
	 * 
	 * @param firstName
	 * @param lastName
	 */
	public Adresse(String adresse) {
		this.adresse = new SimpleStringProperty(adresse);

	}

	public String getAdresse() {
		return adresse.get();
	}

	public void setAdresse(String adresse) {
		this.adresse.set(adresse);
	}

	public StringProperty adresseProperty() {
		return adresse;
	}

}
