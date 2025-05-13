package org.example.recup;

/* Auteur: NZIKO Felix Andre
   Niveau: 3GI
   TP POO 2*/

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;

public class MainApplication extends Application {
    private static HikariDataSource dataSource;

//    initialisation du pool de connexion HikariCP

    public static void initConnectionPool() {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://127.0.0.1:3307/recuperation");
            config.setUsername("root");
            config.setPassword("felixtanzi12");
            config.setMaximumPoolSize(10);

            dataSource = new HikariDataSource(config);
            System.out.println("Pool de connexion initialise !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    Recuperation de la connexion depuis le pool

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

//    fermeture du pool de connexion

    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()){
            dataSource.close();
        }
    }

    @Override
    public void start(Stage primarystage) throws IOException {

//        initialisation du pool de connexion

        initConnectionPool();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        primarystage.setTitle("Rav");
        primarystage.setScene(scene);
        primarystage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

//Ce logiciel aidera a avoir les informations sur le statut d'un telephone vendu seconde main.  Ici, il prend en compte deux situations, celui qui perd son appareil ou qui s'est fait voler peut venir declarer ledit appareil et celui qui achete un telephone seconde main, peut se renseigner si le telephone a ete vole, perdu ou pas et avoir les informations du proprietaire si necessaire.  Nous sommes encore a la version beta 1.0, nous comptons bien continuer a developper davantage par la suite.