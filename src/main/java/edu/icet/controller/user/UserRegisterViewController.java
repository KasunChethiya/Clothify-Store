package edu.icet.controller.user;

import com.jfoenix.controls.JFXComboBox;
import edu.icet.dao.user.UserDaoImpl;
import edu.icet.model.User;
import edu.icet.util.IdGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class UserRegisterViewController implements Initializable {

    public TextField firstNameTxtField;
    public PasswordField passwordTxtField;
    public TextField lastNameTxtField;
    public TextField phoneNoTxtField;
    public TextField addressTxtField;
    public TextField emailTxtField;
    public PasswordField reEnterPasswordTxtField;
    public JFXComboBox roleComboBox;



    public void SignUpBtnOnAction(ActionEvent actionEvent) throws IOException {
        UserDaoImpl userDaoImpl = new UserDaoImpl();

        String password = null;
        if (passwordTxtField.getText().equals(reEnterPasswordTxtField.getText())){
            password = reEnterPasswordTxtField.getText();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Password Matching");
            alert.setHeaderText(null);
            alert.setContentText("Your password is not match. Please re enter the password");

            alert.showAndWait();
        }

        if (firstNameTxtField.getText() == null || firstNameTxtField.getText().isEmpty() ||
                lastNameTxtField.getText() == null || lastNameTxtField.getText().isEmpty() ||
                addressTxtField.getText() == null || addressTxtField.getText().isEmpty() ||
                phoneNoTxtField.getText() == null || phoneNoTxtField.getText().isEmpty() ||
                emailTxtField.getText() == null || emailTxtField.getText().isEmpty() ||
                roleComboBox.getValue() == null || password == null || password.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        User user = new User(
                IdGenerator.genarateUserId(),
                firstNameTxtField.getText(),
                lastNameTxtField.getText(),
                addressTxtField.getText(),
                phoneNoTxtField.getText(),
                emailTxtField.getText(),
                roleComboBox.getValue().toString(),
                passwordEncryption(password)
        );

        userDaoImpl.persist(user);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Account Center");
        alert.setHeaderText(null);
        alert.setContentText("Your account is successfully created. Please login with your account.");
        alert.showAndWait();

        Parent root = FXMLLoader.load(getClass().getResource("/view/signin-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void LogInLinkOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/signin-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void loadRole() {
        ObservableList<Object> roles = FXCollections.observableArrayList();
        roles.add("Employee");
        roles.add("Admin");
        roleComboBox.setItems(roles);
    }

    private String passwordEncryption(String password){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            md5.update(password.getBytes());
            byte[] digest = md5.digest();

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                stringBuilder.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }

            return stringBuilder.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRole();
    }
}
