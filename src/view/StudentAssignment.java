package view;

import controller.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Error;

import java.io.IOException;

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

    @FXML
    void downloadData(ActionEvent event) {
        try{
            student.getData();
        }catch(Error e){
            StudentMain.createAlert("Could not download the data!",e, Alert.AlertType.ERROR);
        }

    }

    @FXML
    void logout(ActionEvent event) {
        StudentMain.createAlert("Are you sure you want to logout?",null, Alert.AlertType.CONFIRMATION);
    }

    @FXML
    void submitAnswer(ActionEvent event) {
        try {
            student.answerQuestion(txaAnswer.getText());
            showNextQuestion();
        } catch (Error error) {
            StudentMain.createAlert("Failed to save answer!",error, Alert.AlertType.ERROR).showAndWait();

        }
    }
    private void showNextQuestion() {
        String question;
        if ((question = student.getNextQuestion()) != null) {
            lblQuestion.setText(question);
        } else {
            finishAssignment();
        }
    }

    private void finishAssignment() {
        try {
            // TODO: Message box with mark and say feedback on next screen
            System.out.println("Your mark: " + student.getMark());
            student.submitAssignment();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudentMain.fxml"));
            Parent root = fxmlLoader.load();
            StudentMain controller = fxmlLoader.getController();
            controller.setStudent(student);

            Stage stage = (Stage) btnSubmitAnswer.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Error error) {
            //TODO
            error.printStackTrace();
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
        }
    }

    public void setStudent(Student student) {
        this.student = student;
        lblStudentNum.setText(this.student.getStudentNum());
        this.student.createSubmission();
        showNextQuestion();
    }
}
