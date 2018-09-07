package view;

import controller.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Error;

import java.io.IOException;
import java.util.Optional;

public class StudentAssignment {
    private Student student;

    @FXML
    private Button btnDownloadData;

    @FXML
    private Label lblQuestion;

    @FXML
    private TextArea txaAnswer;

    @FXML
    private Button btnSubmitAnswer;

    @FXML
    private Label lblStudentNum;

    @FXML
    private Button btnLogout;

    public StudentAssignment() {
    }

    public static Alert createAlert(String title, String message, Error error, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(message);
        if (error != null) {
            alert.setContentText(error.toString());
        }
        return alert;
    }

    public static Alert createAlert(String message, Error error, Alert.AlertType type) {
        return createAlert("Error", message, error, type);
    }

    @FXML
    void downloadData(ActionEvent event) {
        try {
            student.getData(btnDownloadData.getScene().getWindow());
        } catch (Error error) {
            Alert a = createAlert("Problem creating file", error, Alert.AlertType.ERROR);
        }
    }

    @FXML
    void logout(ActionEvent event) {
        Alert a = createAlert("Are you sure you want to logout?", null, Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    void submitAnswer(ActionEvent event) {
        student.answerQuestion(txaAnswer.getText());
        showNextQuestion();
    }

    private void showNextQuestion() {
        String question;
        if ((question = student.getNextQuestion()) != null) {
            lblQuestion.setText(question);
            txaAnswer.setText("");
        } else {
            finishAssignment();
        }
    }

    private void finishAssignment() {
        try {
            createAlert("Complete!", "Your mark: " + student.getMark(), null, Alert.AlertType.INFORMATION);
            student.submitAssignment();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudentMain.fxml"));
            Parent root = fxmlLoader.load();
            StudentMain controller = fxmlLoader.getController();
            controller.setStudent(student);

            Stage stage = (Stage) btnSubmitAnswer.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Error error) {
            createAlert("Problem finishing assignment", error, Alert.AlertType.ERROR);
        } catch (IOException e) {
            createAlert("Problem finishing assignment", new Error(e), Alert.AlertType.ERROR);
        }
    }

    public void setStudent(Student student) {
        this.student = student;
        lblStudentNum.setText(this.student.getStudentNum());
        this.student.createSubmission();
        showNextQuestion();
    }
}
