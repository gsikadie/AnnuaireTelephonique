package modele;

	import javafx.beans.property.IntegerProperty;
	import javafx.beans.property.ObjectProperty;
	import javafx.beans.property.SimpleIntegerProperty;
	import javafx.beans.property.SimpleObjectProperty;
	import javafx.beans.property.SimpleStringProperty;
	import javafx.beans.property.StringProperty;


public class Personne {
	    private final StringProperty nom;
	    private final StringProperty prenom;
	 

	    /**
	     * Constructor with some initial data.
	     * 
	     * @param firstName
	     * @param lastName
	     */
	    public Personne(String nom, String prenom) {
	        this.nom = new SimpleStringProperty(nom);
	        this.prenom = new SimpleStringProperty(prenom);
	    }

	    public String getNom() {
	        return nom.get();
	    }

	    public void setNom(String nom) {
	        this.nom.set(nom);
	    }

	    public StringProperty nomProperty() {
	        return nom;
	    }

	    public String getPrenom() {
	        return prenom.get();
	    }

	    public void setPrenom(String prenom) {
	        this.prenom.set(prenom);
	    }

	    public StringProperty prenomProperty() {
	        return prenom;
	    }

	  
	}

