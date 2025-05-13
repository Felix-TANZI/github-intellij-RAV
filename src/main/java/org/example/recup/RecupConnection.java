package org.example.recup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


public class RecupConnection {
        // URL de connexion à la base MySQL : on précise le nom de la base, l'hôte et le port
        private static final String URL = "jdbc:mysql://127.0.0.1:3307/recuperation";
        // Nom d'utilisateur MySQL
        private static final String USER = "root";
        // Mot de passe MySQL
        private static final String PASSWORD = "felixtanzi12";


//         * Méthode statique pour obtenir un objet Connection
//         * @return Connection vers la base MySQL
//         * @throws SQLException si la connexion échoue

        public static Connection getConnection() throws SQLException {
            // DriverManager.getConnection tente la connexion à la base via JDBC
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
}
