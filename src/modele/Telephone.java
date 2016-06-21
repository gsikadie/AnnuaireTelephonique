package modele;

	import javafx.beans.property.IntegerProperty;
	import javafx.beans.property.ObjectProperty;
	import javafx.beans.property.SimpleIntegerProperty;
	import javafx.beans.property.SimpleObjectProperty;
	import javafx.beans.property.SimpleStringProperty;
	import javafx.beans.property.StringProperty;


public class Telephone {
	    private final StringProperty telephone;
	    private final StringProperty operateur;
	 

	    /**
	     * Constructor with some initial data.
	     * 
	     * @param firstName
	     * @param lastName
	     */
	    public Telephone(String telephone, String operateur) {
	        this.telephone = new SimpleStringProperty(telephone);
	        this.operateur = new SimpleStringProperty(operateur);
	    }

	    public String getTelephone() {
	        return telephone.get();
	    }

	    public void setTelephone(String telephone) {
	        this.telephone.set(telephone);
	    }

	    public StringProperty telephoneProperty() {
	        return telephone;
	    }

	    public String getOperateur() {
	        return operateur.get();
	    }

	    public void setOperateur(String operateur) {
	        this.operateur.set(operateur);
	    }

	    public StringProperty operateurProperty() {
	        return operateur;
	    }

	  
	}

