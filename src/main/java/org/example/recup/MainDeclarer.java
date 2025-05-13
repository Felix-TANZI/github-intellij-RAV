package org.example.recup;

/* Auteur: NZIKO Felix Andre
   Niveau: 3GI
   TP POO 2*/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.PreparedStatement;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainDeclarer {

    @FXML
    private TextField adresse;

    @FXML
    private AnchorPane anchorPane2;

    @FXML
    private Button bouton_reinitialiser;

    @FXML
    private Button bouton_retour;

    @FXML
    private Button bouton_valider;

    @FXML
    private TextField dateDeclaration;

    @FXML
    private TextField description;

    @FXML
    private TextField email;

    @FXML
    private TextField estVole;

    @FXML
    private TextField marque;

    @FXML
    private TextField modele;

    @FXML
    private TextField nom;

    @FXML
    private TextField numeroSerie;

    @FXML
    private TextField statut;

    @FXML
    private TextField tel;

    @FXML
    private TextField type;

    @FXML
    void reinitialiser(ActionEvent event) {
        nom.clear();
        tel.clear();
        email.clear();
        adresse.clear();
        numeroSerie.clear();
        marque.clear();
        modele.clear();
        statut.clear();
        description.clear();
        type.clear();
        estVole.clear();
        dateDeclaration.clear();


        nom.setPromptText("Entrez votre nom");
        tel.setPromptText("Entrez votre tel");
        email.setPromptText("Entrez votre email");
        adresse.setPromptText("Entrez votre adresse");
        numeroSerie.setPromptText("Entrez le numero de serie");
        marque.setPromptText("Entrez la marque");
        modele.setPromptText("Entrez le modele");
        statut.setPromptText("Entrez le statut (perdu/vole)");
        description.setPromptText("Entrez la description");
        type.setPromptText("Entrez le type");
        estVole.setPromptText("oui ou non");
        dateDeclaration.setPromptText(LocalDate.now().format(DateTimeFormatter.ofPattern("jj/mm/aaaa")));

    }

    @FXML
    void retour(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage) nom.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void valider(ActionEvent event) {
        // Validation des champs obligatoires
        if (nom.getText().isEmpty() || tel.getText().isEmpty() || numeroSerie.getText().isEmpty() || marque.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs obligatoires");
            return;
        }

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Récupération d'une connexion depuis le pool
            connection = MainApplication.getConnection();

            String sql = "INSERT INTO objets (type, marque, modele, numeroSerie, voler, proprietaire, emailProprio, adresseProprio, declaration, telNumber, description, statut)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?)";

            statement = connection.prepareStatement(sql);


            // Remplir les paramètres de la requête
            statement.setString(1, type.getText());
            statement.setString(2, marque.getText());
            statement.setString(3, modele.getText());
            statement.setString(4, numeroSerie.getText());
            statement.setBoolean(5, "oui".equalsIgnoreCase(estVole.getText()));
            statement.setString(6, nom.getText());
            statement.setString(7, email.getText());
            statement.setString(8, adresse.getText());
            statement.setString(9, dateDeclaration.getText());
            statement.setString(10, tel.getText());
            statement.setString(11, description.getText());
            statement.setString(12, statut.getText());

            // Exécuter la requête
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                showAlert(AlertType.INFORMATION, "Succès", "Déclaration enregistrée avec succès.");
                reinitialiserChamps(); // Optionnel: réinitialiser après succès
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erreur", "Erreur lors de l'enregistrement: " + e.getMessage());
        } finally {
            // Fermeture des ressources dans le bloc finally
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close(); // La connexion retourne au pool
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void reinitialiserChamps() {
        nom.clear();
        tel.clear();
        email.clear();
        adresse.clear();
        numeroSerie.clear();
        marque.clear();
        modele.clear();
        description.clear();
        statut.clear();
        type.clear();
        estVole.clear();
        dateDeclaration.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void initialize() {
        dateDeclaration.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
    }

    }


