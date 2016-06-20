/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
    public String getVehicule() throws SQLException {
        String result = "";
        Connection con = null;

        con = DriverManager.getConnection(strURL, "root", "root");
        String requete;
        java.sql.Statement stmt = con.createStatement();

        requete = "SELECT * FROM vehicule";

        ResultSet rs1 = stmt.executeQuery(requete);

        while (rs1.next()) {
            result = result + rs1.getString("matricule") + ";" + rs1.getString("driverName") + ";" + rs1.getString("cni") + ";" + rs1.getString("company");
            result = result + "&";
        }

        con.close();

        return result;
    }

    public DataBase(int port, String host, String databaseName, String login, String password, String pilotString) throws SQLException {

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
            this.strURL = chaine + "://" + host + ":" + port + "/" + databaseName;
            //  Connection con = DriverManager.getConnection(this.strURL, login, password);
            // . . . 
            // con.close();
        } catch (ClassNotFoundException e) {
            System.err.println("Driver non chargé !");
            e.printStackTrace();

        }// catch (SQLException e) {
        //   e.printStackTrace();
        //}

    }

    public String getPointDay() {
        String result = "";

        return result;
    }

    public void insertData(String requete) throws SQLException {

        Connection con = DriverManager.getConnection(this.strURL, login, password);
        java.sql.Statement stmt = con.createStatement();
        int n = stmt.executeUpdate(requete);
        con.close();
        System.out.println("voici le nombre de lignes" + n);

    }

    public String getData(String requete) {
        String re = "";
        try {
            Connection con = DriverManager.getConnection(this.strURL, login, password);
            java.sql.Statement stmt = con.createStatement();

            ResultSet rs1 = stmt.executeQuery(requete);

            while (rs1.next()) {
                re = re + rs1.getString(1) + ";;;" + rs1.getString(2) + ";;;" + rs1.getString(3) + ";;;" + rs1.getString(4) + ";;;" + rs1.getString(5) + ";;;" + rs1.getString(6) + ";;;" + rs1.getString(7) + ";;;" + rs1.getString(7) + ";;;" + rs1.getString(8) + ";;;" + rs1.getString(9) + ";;;" + rs1.getInt(10) + ";;;" + rs1.getString(11) + ";;;" + rs1.getString(12) + ";;;" + rs1.getString(13) + ";;;" + rs1.getString(14) + ";;;" + rs1.getString(15);
                re = re + ":::";
            }

            con.close();
            System.out.println("voici le nombre de lignes" + re);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return re;
    }

    /**
     *
     * @param login
     * @return the index with represent from where we should send data
     */
    public boolean getIfUserExists(String login, String password) throws SQLException {
        boolean result = false;
        Connection con = null;
        try {
            con = DriverManager.getConnection(strURL, "root", "root");
            String passwordEncode = Md5.encode(password);
            java.sql.Statement stmt = con.createStatement();
            String requete = "SELECT * FROM user_client WHERE id_user = \'" + login + "\'" + " and password = \'" + passwordEncode + "\'";

            ResultSet rs1 = stmt.executeQuery(requete);
            int i = 0;
            while (rs1.next()) {
                i++;
            }
            if (i == 0) {
                result = false;
            } else {
                result = true;
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            con.close();
        }
        return result;
    }

    /**
     *
     * @param login
     * @return the code of the station
     */
    public String getClientType(String login) throws SQLException {
        String result = "";
        Connection con = null;
        try {
            con = DriverManager.getConnection(strURL, "root", "root");

            java.sql.Statement stmt = con.createStatement();
            String requete = "SELECT * FROM user_client WHERE id_user = \'" + login + "\'";

            ResultSet rs1 = stmt.executeQuery(requete);

            while (rs1.next()) {

                result = rs1.getString("client_type");
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            con.close();
        }
        return result;
    }

    /**
     *
     * @param idSousRegion
     * @return the sous Region
     */
    public String getSousRegion(String idSousRegion) throws SQLException {
        String result = "";
        Connection con = null;
        try {
            con = DriverManager.getConnection(strURL, "root", "root");

            java.sql.Statement stmt = con.createStatement();
            String requete = "SELECT * FROM sous_zone WHERE num_sous_zone = \'" + idSousRegion + "\'";

            ResultSet rs1 = stmt.executeQuery(requete);

            rs1.next();

            result = rs1.getString("nom_sous_zone");

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            con.close();
        }
        return result;
    }

    /**
     *
     * @param idSousRegion
     * @return the sous Region
     */
    public String getIdSousRegion(String idStation) throws SQLException {
        String result = "";
        Connection con = null;
        try {
            con = DriverManager.getConnection(strURL, "root", "root");

            java.sql.Statement stmt = con.createStatement();
            String requete = "SELECT * FROM station WHERE codestation = \'" + idStation + "\'";

            ResultSet rs1 = stmt.executeQuery(requete);

            while (rs1.next()) {

                result = rs1.getString("id_sous_zone");
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            con.close();
        }
        return result;
    }

    /**
     *
     * @param idSousRegion
     * @return the id_zone the id of the zone
     */
    public String getIdZone(String idSousRegion) throws SQLException {
        String result = "";
        Connection con = null;
        try {
            con = DriverManager.getConnection(strURL, "root", "root");

            java.sql.Statement stmt = con.createStatement();
            String requete = "SELECT * FROM sous_zone WHERE num_sous_zone = \'" + idSousRegion + "\'";

            ResultSet rs1 = stmt.executeQuery(requete);

            while (rs1.next()) {

                result = rs1.getString("id_zone");
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            con.close();
        }
        return result;
    }

    /**
     *
     * @param codeStation
     * @return the station name
     */
    public String getSiteName(String codeStation) throws SQLException {
        String result = "";
        Connection con = null;
        try {
            con = DriverManager.getConnection(strURL, "root", "root");

            java.sql.Statement stmt = con.createStatement();
            String requete = "SELECT * FROM station WHERE codestation = \'" + codeStation + "\'";

            ResultSet rs1 = stmt.executeQuery(requete);

            while (rs1.next()) {

                result = rs1.getString("libelle");
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            con.close();
        }
        return result;
    }

    /**
     *
     * @param clientType
     * @return the station name
     */
    public String getCodeStation(String clientType) throws SQLException {
        String result = "";
        Connection con = null;
        try {
            con = DriverManager.getConnection(strURL, "root", "root");

            java.sql.Statement stmt = con.createStatement();
            String requete = "SELECT * FROM station WHERE id_client = \'" + clientType + "\'";

            ResultSet rs1 = stmt.executeQuery(requete);

            while (rs1.next()) {

                result = rs1.getString("codestation");
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            con.close();
        }
        return result;

    }

    /**
     *
     * @param codeStation
     * @return the station name
     */
    public String getCuveSite(String codeStation) throws SQLException {
        String result = "";
        Connection con = null;
        try {
            con = DriverManager.getConnection(strURL, "root", "root");

            java.sql.Statement stmt = con.createStatement();
            String requete = "SELECT * FROM cuve WHERE station = \'" + codeStation + "\'";

            ResultSet rs1 = stmt.executeQuery(requete);

            while (rs1.next()) {
                result = result + rs1.getString("id_cuve") + ";" + rs1.getString("libelle") + ";" + rs1.getFloat("capacite_total") + ";" + rs1.getFloat("hauteur") + ";" + rs1.getFloat("capacite_actuelle") + ";" + rs1.getString("id_produit");
                result = result + "&";
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            con.close();
        }
        return result;
    }

    /**
     *
     * @param codeStatio
     * @return the list of the products
     */
    public String getProduit() throws SQLException {
        String result = "";
        Connection con = null;
        try {
            con = DriverManager.getConnection(strURL, "root", "root");

            java.sql.Statement stmt = con.createStatement();
            String requete = "SELECT * FROM produit";

            ResultSet rs1 = stmt.executeQuery(requete);

            while (rs1.next()) {
                result = result + rs1.getString("libelle");
                result = result + "&";
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            con.close();
        }
        return result;
    }

    /**
     *
     * @param @return the list of the Company
     */
    public String getCompany() throws SQLException {
        String result = "";
        Connection con = null;
        try {
            con = DriverManager.getConnection(strURL, "root", "root");

            java.sql.Statement stmt = con.createStatement();
            String requete = "SELECT * FROM company";

            ResultSet rs1 = stmt.executeQuery(requete);

            while (rs1.next()) {
                result = result + rs1.getString("company");
                result = result + "&";
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            con.close();
        }
        return result;
    }

    /**
     *
     * @param codeStation
     * @return the number of stations from a codeStation
     */
    public String getNumberCuveSite(String codeStation) throws SQLException {
        String result = "";
        Connection con = null;
        int i = 0;
        try {
            con = DriverManager.getConnection(strURL, "root", "root");

            java.sql.Statement stmt = con.createStatement();
            String requete = "SELECT * FROM cuve WHERE station = \'" + codeStation + "\'";

            ResultSet rs1 = stmt.executeQuery(requete);

            while (rs1.next()) {
                i++;
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            con.close();
        }
        return "" + i;
    }

    /**
     *
     * @param codeStation
     * @return the number of stations from a codeStation
     */
    public String getListePoint(String idCuve, Date heureReleve1, Date heureReleve2) throws SQLException {
        String result = "";
        Connection con = null;
        // int i = 0;
        try {
            con = DriverManager.getConnection(strURL, "root", "root");

            java.sql.Statement stmt = con.createStatement();
            String requete = "SELECT *  FROM  releve_capacite_sonde  WHERE id_cuve = '" + idCuve + "' AND heure_releve BETWEEN '" + heureReleve1 + "'" + " AND '" + heureReleve2 + "' ORDER BY heure_releve" + " ASC";

            ResultSet rs1 = stmt.executeQuery(requete);

            while (rs1.next()) {
                result = result + rs1.getTimestamp("heure_releve") + "," + rs1.getFloat("capacite") + "&";

            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            con.close();
        }
        return result;
    }

    /**
     *
     * @param codeStation
     * @return the number of stations from a codeStation
     */
    public String getListePointTemperature(String idCuve, Date heureReleve1, Date heureReleve2) throws SQLException {
        String result = "";
        Connection con = null;
        // int i = 0;
        try {
            con = DriverManager.getConnection(strURL, "root", "root");

            java.sql.Statement stmt = con.createStatement();
            String requete = "SELECT *  FROM  releve_capacite_sonde  WHERE id_cuve = '" + idCuve + "' AND heure_releve BETWEEN '" + heureReleve1 + "'" + " AND '" + heureReleve2 + "' ORDER BY heure_releve" + " ASC";

            ResultSet rs1 = stmt.executeQuery(requete);

            while (rs1.next()) {
                result = result + rs1.getTimestamp("heure_releve") + "," + rs1.getFloat("capacite") + "&";

            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            con.close();
        }
        return result;
    }

    /**
     *
     * @param codeStation
     * @return the number of stations from a codeStation
     */
    public Double getConsommationJournee(String idCuve, Date heureReleve1, Date heureReleve2) throws SQLException {
        Double result = null;
        Connection con = null;
        // int i = 0;
        try {
            con = DriverManager.getConnection(strURL, "root", "root");

            java.sql.Statement stmt = con.createStatement();
            String requete = "SELECT SUM(quantite) FROM  distributeur_ventes_general  WHERE id_cuve = '" + idCuve + "' AND heure_vente_ini BETWEEN '" + heureReleve1 + "'" + " AND '" + heureReleve2 + "'";

            ResultSet rs1 = stmt.executeQuery(requete);

            while (rs1.next()) {
                result = rs1.getDouble(1);

            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            con.close();
        }
        return result;
    }

    /**
     *
     * @param i
     * @return the id of the i eme cuve of a station
     */
    public String getIdCuve(int i) {
        String result = "";
        Connection con = null;
        int j = 0;
        try {
            con = DriverManager.getConnection(strURL, "root", "root");

            java.sql.Statement stmt = con.createStatement();
            String requete = "SELECT *  FROM  cuve";

            ResultSet rs1 = stmt.executeQuery(requete);
            if (!rs1.isLast()) {
                while (rs1.next() && (j != i)) {
                    j++;
                }
                result = rs1.getString("id_cuve");
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            try {
                con.close();
            } catch (SQLException ex1) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return result;
    }

    /**
     *
     * @param idCuve
     * @param date
     * @param date1
     * @return the station name
     */
    public String getAlerte(String idCuve, Date date, Date date1, String coche) throws SQLException {
        String result = "";
        Connection con = null;
        try {
            con = DriverManager.getConnection(strURL, "root", "root");
            String requete;
            java.sql.Statement stmt = con.createStatement();
            if (idCuve.compareToIgnoreCase("all") != 0) {
                if (coche.compareToIgnoreCase("all") == 0) {
                    requete = "SELECT * FROM alertes_all WHERE id_device_cuve = \'" + idCuve + "\' AND heure_alerte BETWEEN \'" + date + "\' AND " + "\'" + date1 + "\'";
                } else {
                    requete = "SELECT * FROM alertes_all WHERE id_device_cuve = \'" + idCuve + "\' AND heure_alerte BETWEEN \'" + date + "\' AND " + "\'" + date1 + "\' AND type_alerte = \'" + coche + "\'";
                }
            } else {

                if (coche.compareToIgnoreCase("all") == 0) {
                    requete = "SELECT * FROM alertes_all WHERE heure_alerte between  \'" + date + "\' AND " + "\'" + date1 + "\'";
                } else {
                    requete = "SELECT * FROM alertes_all WHERE heure_alerte BETWEEN \'" + date + "\' AND " + "\'" + date1 + "\' AND type_alerte = \'" + coche + "\'";
                }
            }
            ResultSet rs1 = stmt.executeQuery(requete);

            while (rs1.next()) {
                result = result + rs1.getString("id_alerte") + ";" + rs1.getTimestamp("heure_alerte") + ";" + rs1.getString("id_device_cuve") + ";" + rs1.getString("type_alerte") + ";" + rs1.getString("libelle") + ";" + rs1.getString("valeur_actuelle");
                result = result + "&";
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            con.close();
        }
        return result;
    }

    /**
     *
     * @param id
     * @return a vehicule with his matricule
     */
    public String getVehiculeID(String motCle) throws SQLException {
        String result = "";
        Connection con = null;

        con = DriverManager.getConnection(strURL, "root", "root");

        java.sql.Statement stmt = con.createStatement();
        String requete = "SELECT * FROM vehicule WHERE matricule =  \'" + motCle + "\'";

        ResultSet rs1 = stmt.executeQuery(requete);

        while (rs1.next()) {
            result = result + rs1.getString("matricule") + ";" + rs1.getString("driverName") + ";" + rs1.getString("cni") + ";" + rs1.getString("company");
            result = result + "&";

        }

        con.close();

        return result;
    }

    /**
     *
     * @param id
     * @return a vehicule WHOSE matricule is like the one passed in parameter
     */
    public String getVehiculeLike(String motCle) throws SQLException {
        String result = "";
        Connection con = null;
        String requete;
        con = DriverManager.getConnection(strURL, "root", "root");

        java.sql.Statement stmt = con.createStatement();
        if (motCle.length() > 0) {
            requete = "SELECT * FROM vehicule WHERE matricule LIKE  \'" + motCle + "%" + "\'";
        } else {
            requete = "SELECT * FROM vehicule";
        }

        ResultSet rs1 = stmt.executeQuery(requete);

        while (rs1.next()) {
            result = result + rs1.getString("matricule") + ";" + rs1.getString("driverName") + ";" + rs1.getString("cni") + ";" + rs1.getString("company");
            result = result + "&";

        }

        con.close();

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
