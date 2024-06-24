package edu.icet.controller.user;

import edu.icet.bo.user.ForgotPasswordService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgotPasswordViewController {
    public TextField emailField;
    public static String recipientEmail;
    public void btnSendCodeOnAction(ActionEvent actionEvent) {
        ForgotPasswordService forgotPasswordService = new ForgotPasswordService();
        forgotPasswordService.sendOtp(emailField.getText());
        recipientEmail = emailField.getText();

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/otp-verification-view.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void linkSigninOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/signin-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
