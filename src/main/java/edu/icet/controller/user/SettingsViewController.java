package edu.icet.controller.user;

import edu.icet.bo.user.SettingsService;
import edu.icet.bo.user.SignInService;
import edu.icet.entity.UserEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SettingsViewController implements Initializable {
    public Button btnSettings;
    public TextField firstNameTxtField;
    public TextField addressTxtField;
    public TextField phoneNoTxtField;
    public TextField newPasswordTxtField;
    public TextField lastNameTxtField;
    public TextField jobRoleTxtField;
    public TextField emailTxtField;
    public TextField confirmPasswordTxtField;

    SettingsService settingsService = new SettingsService();
    public void btnDashboardOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/employee-dashboard-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        Image image = new Image("icon.png");
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();
        Stage currentStage = (Stage) btnSettings.getScene().getWindow();
        currentStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<UserEntity> accountDetails = settingsService.loadAccountDetails(SignInService.emailFromDatabase);
        for (UserEntity account :accountDetails){
            firstNameTxtField.setText(account.getFirstName());
            lastNameTxtField.setText(account.getLastName());
            addressTxtField.setText(account.getAddress());
            phoneNoTxtField.setText(account.getPhoneNo());
            emailTxtField.setText(account.getEmail());
            jobRoleTxtField.setText(account.getRole());
        }
    }

    public void btnUpdatePasswordOnAction(ActionEvent actionEvent) {
        if (!newPasswordTxtField.getText().equals(confirmPasswordTxtField.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Password Does not match each other. Please enter again.");
            alert.showAndWait();
        }else {
            settingsService.updatePassword(confirmPasswordTxtField.getText());
        }
    }
}
