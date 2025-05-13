package org.example.recup;

/* Auteur: NZIKO Felix Andre
   Niveau: 3GI
   TP POO 2*/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;

import java.io.IOException;

public class MainRenseigner {

    @FXML
    private Button bouton_reinitialiser;

    @FXML
    private Button bouton_retour;

    @FXML
    private Button bouton_rechercher;

    @FXML
    private TextField marque;

    @FXML
    private TextField modele;

    @FXML
    private TextField numeroSerie;

    @FXML
    private TextField type;

    @FXML
    void reinitialiser(ActionEvent event) {
        numeroSerie.clear();
        marque.clear();
        modele.clear();
        type.clear();

        numeroSerie.setPromptText("Entrez le numero de serie de l'appareil (champ obligatoire");
        marque.setPromptText("Entrez la marque de l'appareil (champ facultatif)");
        modele.setPromptText("Entrez le modele de l'appareil (champ facultatif)");
        type.setPromptText("Entrez le type de l'appareil (champ facultatif)");

    }

    @FXML
    void retour(ActionEvent event) {
        try {
            // Charger le fichier FXML de la vue principale
            Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
            Stage stage = (Stage) bouton_retour.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger la page principale", AlertType.ERROR);
        }
    }

    @FXML
    void rechercher(ActionEvent event) {
        String numSerie = numeroSerie.getText().trim();

        if (numSerie.isEmpty()) {
            showAlert("Champ obligatoire", "Le numéro de série est obligatoire pour la recherche", AlertType.WARNING);
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Récupération de la connexion depuis le pool
            conn = MainApplication.getConnection();

//            Ici, il y a encore un probleme, je dois revoir la connexion a la base de donnee pour pouvoir faire la reherche a partir du numero de serie

            String query = "SELECT * FROM objets WHERE numeroSerie = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, numSerie);

            rs = stmt.executeQuery();

            if (rs.next()) {
                boolean estVole = rs.getBoolean("estVole");

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Résultat de la recherche");
                alert.setHeaderText("Informations sur l'appareil");

                StringBuilder content = new StringBuilder();
                content.append("Numéro de série: ").append(rs.getString("numeroSerie")).append("\n");
                content.append("Marque: ").append(rs.getString("marque")).append("\n");
                content.append("Modèle: ").append(rs.getString("modele")).append("\n");
                content.append("Type: ").append(rs.getString("type")).append("\n");
                content.append("Statut: ").append(estVole ? "Volé" : "Non volé").append("\n");

                if (estVole) {
                    content.append("\n**Informations sur le vol**\n");
                    content.append("Propriétaire: ").append(rs.getString("proprietaire")).append("\n");
                    content.append("Email: ").append(rs.getString("emailProprio")).append("\n");
                    content.append("Adresse: ").append(rs.getString("adresseProprio")).append("\n");
                    content.append("Téléphone: ").append(rs.getString("telNumber")).append("\n");
                    content.append("Date déclaration: ").append(rs.getDate("dateDeclaration")).append("\n");
                    content.append("Description: ").append(rs.getString("description")).append("\n");
                }

                alert.setContentText(content.toString());
                alert.showAndWait();

                // Mise à jour des champs avec les informations trouvées
                marque.setText(rs.getString("marque"));
                modele.setText(rs.getString("modele"));
                type.setText(rs.getString("type"));
            } else {
                showAlert("Non trouvé", "Aucun appareil trouvé avec ce numéro de série", AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur base de données", "Erreur lors de l'accès à la base de données: " + e.getMessage(), AlertType.ERROR);
        } finally {
            // Fermeture des ressources dans l'ordre inverse de leur création
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close(); // La connexion retourne au pool
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}


// felix tanzi