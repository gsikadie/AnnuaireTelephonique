/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sikadie
 */
public class DataBase {

	private String urlConnexion;
	private int port;
	private String host;
	private String databaseName;
	private String login;
	private String password;
	private String pilotString;
	private String strURL = "";

	/**
	 *
	 * @param idCuve
	 * @param date
	 * @param date1
	 * @return the station name
	 * @throws java.sql.SQLException
	 */
	public String getPersonne(String chaine) throws SQLException {
		String result = "";
		Connection con = null;

		con = DriverManager.getConnection(strURL, "root", "root");
		String requete;
		java.sql.Statement stmt = con.createStatement();

		requete = "select * from personne where nom  like ?";
		PreparedStatement ps = con.prepareStatement(requete);
		ps.setString(1, chaine + "%");
		ResultSet rs1 = ps.executeQuery();

		while (rs1.next()) {
			String nom = rs1.getString("nom");
			String prenom = rs1.getString("prenom");
			if (nom.length() == 0)
				nom = " ";
			if (prenom.length() == 0)
				prenom = " ";
			result = result + nom + ";" + prenom;
			result = result + "&";
		}

		con.close();

		return result;
	}

	/**
	 *
	 * @param idCuve
	 * @param date
	 * @param date1
	 * @return the station name
	 * @throws java.sql.SQLException
	 */
	public String getPersonne() throws SQLException {
		String result = "";
		Connection con = null;

		con = DriverManager.getConnection(strURL, "root", "root");
		String requete;
		java.sql.Statement stmt = con.createStatement();

		requete = "SELECT * FROM personne";

		ResultSet rs1 = stmt.executeQuery(requete);

		while (rs1.next()) {
			String nom = rs1.getString("nom");
			String prenom = rs1.getString("prenom");
			if (nom.length() == 0)
				nom = " ";
			if (prenom.length() == 0)
				prenom = " ";
			result = result + nom + ";" + prenom;
			result = result + "&";
		}

		con.close();

		return result;
	}

	public DataBase(int port, String host, String databaseName, String login,
			String password, String pilotString) throws SQLException {

		this.port = port;
		this.host = host;
		this.databaseName = databaseName;
		this.login = login;
		this.password = password;
		this.pilotString = pilotString;
		try {

			Class.forName(pilotString);
			String chaine = null;
			if (pilotString.compareToIgnoreCase("com.mysql.jdbc.Driver") == 0) {
				chaine = "jdbc:mysql";
			}
			this.strURL = chaine + "://" + host + ":" + port + "/"
					+ databaseName;
			// Connection con = DriverManager.getConnection(this.strURL, login,
			// password);
			// . . .
			// con.close();
		} catch (ClassNotFoundException e) {
			System.err.println("Driver non chargé !");
			e.printStackTrace();

		}// catch (SQLException e) {
			// e.printStackTrace();
			// }

	}

	public void insertData(String requete) throws SQLException {

		Connection con = DriverManager.getConnection(this.strURL, login,
				password);
		java.sql.Statement stmt = con.createStatement();
		int n = stmt.executeUpdate(requete);
		con.close();
		System.out.println("voici le nombre de lignes" + n);

	}

	public void insertTelephone(String telephone, String personne_nom,
			String operateur_nomOperateur) throws SQLException {
		Connection con = null;
		String requete = "insert into telephone (telephone,operateur_idoperateur,personne_nom,operateur_nomOperateur) values (?,?,?,?)";

		con = DriverManager.getConnection(this.strURL, login, password);

		/*
		 * java.sql.Statement stmt = con.createStatement(); int n =
		 * stmt.executeUpdate(requete);
		 */
		// con.close();
		PreparedStatement ps = con.prepareStatement(requete);
		ps.setString(1, telephone);
		ps.setInt(2, 1);
		ps.setString(3, personne_nom);
		ps.setString(4, operateur_nomOperateur);

		System.out.println(ps.toString());
		if (ps.executeUpdate() != 0) {

		} else {
			System.out.println("Echec enregistrement");
		}
		con.close();
	}

	public void insertAdresse(String adresse, String nom) throws SQLException {
		Connection con = null;
		String requete = "insert into adresse (adresse,personne_nom) values (?,?)";

		con = DriverManager.getConnection(this.strURL, login, password);

		/*
		 * java.sql.Statement stmt = con.createStatement(); int n =
		 * stmt.executeUpdate(requete);
		 */
		// con.close();
		PreparedStatement ps = con.prepareStatement(requete);
		ps.setString(1, adresse);
		ps.setString(2, nom);

		System.out.println(ps.toString());
		if (ps.executeUpdate() != 0) {

		} else {
			System.out.println("Echec enregistrement");
		}
		con.close();
	}

	public void insertPersonne(String nom, String prenom) throws SQLException {
		Connection con = null;
		String requete = "insert into personne (nom,prenom) values (?,?)";

		con = DriverManager.getConnection(this.strURL, login, password);

		/*
		 * java.sql.Statement stmt = con.createStatement(); int n =
		 * stmt.executeUpdate(requete);
		 */
		// con.close();
		PreparedStatement ps = con.prepareStatement(requete);
		ps.setString(1, nom);
		ps.setString(2, prenom);

		System.out.println(ps.toString());
		if (ps.executeUpdate() != 0) {

		} else {
			System.out.println("Echec enregistrement");
		}
		con.close();
	}

	public void insertOperateur(String operateur) throws SQLException {
		Connection con = null;
		String requete = "insert into operateur (nomOPerateur) values (?)";

		con = DriverManager.getConnection(this.strURL, login, password);

		/*
		 * java.sql.Statement stmt = con.createStatement(); int n =
		 * stmt.executeUpdate(requete);
		 */
		// con.close();
		PreparedStatement ps = con.prepareStatement(requete);
		ps.setString(1, operateur);

		System.out.println(ps.toString());
		if (ps.executeUpdate() != 0) {

		} else {
			System.out.println("Echec enregistrement");
		}
		con.close();
	}

	public void deleteOperateur(String nomOperateur) throws SQLException {
		Connection con = null;
		String requete = "delete from operateur where nomOperateur = ?";

		con = DriverManager.getConnection(this.strURL, this.login,
				this.password);
		PreparedStatement ps = con.prepareStatement(requete);
		ps.setString(1, nomOperateur);
		if (ps.executeUpdate() != 0) {

		} else {
			System.out.println("Echec Suppression");
		}

	}

	public void deletePersonne(String nom) throws SQLException {
		Connection con = null;
		String requete = "delete from personne where nom = ?";

		con = DriverManager.getConnection(this.strURL, this.login,
				this.password);
		PreparedStatement ps = con.prepareStatement(requete);
		ps.setString(1, nom);
		if (ps.executeUpdate() != 0) {

		} else {
			System.out.println("Echec Suppression");
		}

	}

	public void deleteTelephone(String nom) throws SQLException {
		Connection con = null;
		String requete = "delete from telephone where personne_nom = ?";

		con = DriverManager.getConnection(this.strURL, this.login,
				this.password);
		PreparedStatement ps = con.prepareStatement(requete);
		ps.setString(1, nom);
		if (ps.executeUpdate() != 0) {

		} else {
			System.out.println("Echec Suppression");
		}

	}

	public void deleteTelephoneTel(String telephone) throws SQLException {
		Connection con = null;
		String requete = "delete from telephone where telephone = ?";

		con = DriverManager.getConnection(this.strURL, this.login,
				this.password);
		PreparedStatement ps = con.prepareStatement(requete);
		ps.setString(1, telephone);
		if (ps.executeUpdate() != 0) {

		} else {
			System.out.println("Echec Suppression");
		}

	}

	public void deleteAdresse(String nom) throws SQLException {
		Connection con = null;
		String requete = "delete from adresse where personne_nom = ?";

		con = DriverManager.getConnection(this.strURL, this.login,
				this.password);
		PreparedStatement ps = con.prepareStatement(requete);
		ps.setString(1, nom);
		if (ps.executeUpdate() != 0) {

		} else {
			System.out.println("Echec Suppression");
		}

	}

	public void deleteAdresseAdr(String adresse) throws SQLException {
		Connection con = null;
		String requete = "delete from adresse where adresse = ?";

		con = DriverManager.getConnection(this.strURL, this.login,
				this.password);
		PreparedStatement ps = con.prepareStatement(requete);
		ps.setString(1, adresse);
		if (ps.executeUpdate() != 0) {

		} else {
			System.out.println("Echec Suppression");
		}

	}

	/**
	 *
	 * @param codeStation
	 * @return the station name
	 */
	public String getNumeroPersone(String nomPersonne) throws SQLException {
		String result = "";
		Connection con = null;
		try {
			con = DriverManager.getConnection(strURL, "root", "root");

			java.sql.Statement stmt = con.createStatement();
			String requete = "SELECT * FROM telephone WHERE personne_nom = \'"
					+ nomPersonne + "\'";

			ResultSet rs1 = stmt.executeQuery(requete);

			while (rs1.next()) {

				result = result + rs1.getString("telephone") + ";"
						+ rs1.getString("operateur_nomOperateur");
				result = result + "&";
			}

			con.close();
		} catch (SQLException ex) {
			Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null,
					ex);
			con.close();
		}
		return result;
	}

	/**
	 *
	 * @param codeStation
	 * @return the station name
	 */
	public String getAdressePersone(String nomPersonne) throws SQLException {
		String result = "";
		Connection con = null;
		try {
			con = DriverManager.getConnection(strURL, "root", "root");

			java.sql.Statement stmt = con.createStatement();
			String requete = "SELECT * FROM adresse WHERE personne_nom = \'"
					+ nomPersonne + "\'";

			ResultSet rs1 = stmt.executeQuery(requete);

			while (rs1.next()) {

				result = result + rs1.getString("adresse");
				result = result + "&";
			}

			con.close();
		} catch (SQLException ex) {
			Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null,
					ex);
			con.close();
		}
		return result;
	}

	/**
	 *
	 * @param codeStation
	 * @return the station name
	 */
	public String getAdressePersone(int idPersonne) throws SQLException {
		String result = "";
		Connection con = null;
		try {
			con = DriverManager.getConnection(strURL, "root", "root");

			java.sql.Statement stmt = con.createStatement();
			String requete = "SELECT * FROM adresse WHERE personne_idpersonne = \'"
					+ idPersonne + "\'";

			ResultSet rs1 = stmt.executeQuery(requete);

			while (rs1.next()) {
				result = result + rs1.getString("adresse");
				result = result + "&";
			}

			con.close();
		} catch (SQLException ex) {
			Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null,
					ex);
			con.close();
		}
		return result;
	}

	/**
	 *
	 * @param codeStation
	 * @return the station name
	 */
	public String getOperateurs() throws SQLException {
		String result = "";
		Connection con = null;
		try {
			con = DriverManager.getConnection(strURL, "root", "root");

			java.sql.Statement stmt = con.createStatement();
			String requete = "SELECT * FROM operateur ";

			ResultSet rs1 = stmt.executeQuery(requete);

			while (rs1.next()) {
				result = result + rs1.getString("nomOperateur");
				result = result + "&";
			}

			con.close();
		} catch (SQLException ex) {
			Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null,
					ex);
			con.close();
		}
		return result;
	}

	public String getUrlConnexion() {
		return urlConnexion;
	}

	public void setUrlConnexion(String urlConnexion) {
		this.urlConnexion = urlConnexion;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getStrURL() {
		return strURL;
	}

	public void setStrURL(String strURL) {
		this.strURL = strURL;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPilotString() {
		return pilotString;
	}

	public void setPilotString(String pilotString) {
		this.pilotString = pilotString;
	}

}
