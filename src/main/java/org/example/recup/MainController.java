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
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainController {

    @FXML
    private AnchorPane anchorpane1;

    @FXML
    private Button bouton_declarer;

    @FXML
    private Button bouton_renseigner;

    @FXML
    void declarerPerte(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("mainDeclarer.fxml"));
            Scene scene = bouton_declarer.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void renseignerAppareil(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("mainRenseigner.fxml"));
            Scene scene = bouton_declarer.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
