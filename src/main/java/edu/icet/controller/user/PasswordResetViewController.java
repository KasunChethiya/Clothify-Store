package edu.icet.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class PasswordResetViewController {
    public PasswordField txtFieldConfirmPassword;
    public PasswordField txtFieldPassword;
    public Text txtPasswordError;

    public void BtnUpdatePasswordOnAction(ActionEvent actionEvent) throws IOException {
        if (!txtFieldPassword.getText().equals(txtFieldConfirmPassword.getText())){
            txtPasswordError.setText("Your password is not matching each other. Please re-enter the correct password");
        } else if (txtFieldPassword.getText().isEmpty() || txtFieldConfirmPassword.getText().isEmpty()) {
            txtPasswordError.setText("Please type your password.");
        }else {
            txtPasswordError.setText("");

            //---------------------------//
//            database codes
            //---------------------------//

            System.out.println("Password update successfull.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Account Center");
            alert.setHeaderText(null);
            alert.setContentText("Your password is successfully updated. Please login with your account.");
            alert.showAndWait();
            Parent root = FXMLLoader.load(getClass().getResource("/view/signin-view.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}
