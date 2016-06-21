package modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Operateur {
	private final StringProperty operateur;

	/**
	 * Constructor with some initial data.
	 * 
	 * @param firstName
	 * @param lastName
	 */
	public Operateur(String operateur) {
		this.operateur = new SimpleStringProperty(operateur);

	}

	public String getOperateur() {
		return operateur.get();
	}

	public void setAdresse(String operateur) {
		this.operateur.set(operateur);
	}

	public StringProperty operateurProperty() {
		return operateur;
	}

}
