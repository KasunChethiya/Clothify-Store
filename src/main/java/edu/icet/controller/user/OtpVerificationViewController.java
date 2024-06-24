package edu.icet.controller.user;

import edu.icet.bo.user.ForgotPasswordService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OtpVerificationViewController implements Initializable {

    public Text txtResetEmail;
    public TextField codeTxtField;
    public Text txtFieldOtpErrorMsg;
    String email = ForgotPasswordViewController.recipientEmail;

    public void btnVerifyCodeOnAction(ActionEvent actionEvent) throws IOException {
        if(codeTxtField.getText().equals(ForgotPasswordService.otpCode)){
            Parent root = FXMLLoader.load(getClass().getResource("/view/password-reset-view.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else {
            txtFieldOtpErrorMsg.setText("The OTP is incorrect. Please re-enter the correct OTP");
        }
    }

    public void linkReSendEmailOnAction(ActionEvent actionEvent) {
        try {
            ForgotPasswordService.sendEmail(email,ForgotPasswordService.genarateOtp());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtResetEmail.setText("We sent an OTP code link to "+email+" 4 digit code that mentioned in the email");
    }
}
