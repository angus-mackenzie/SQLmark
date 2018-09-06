package view;

import controller.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Error;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class StudentMain {
    private Student student;

    @FXML
    private Button btnDownloadData;

    @FXML
    private Button btnNewAssignment;

    @FXML
    private ListView<String> lstPreviousAssignments;

    @FXML
    private Label lblContentPane;

    @FXML
    private Label lblStudentNum;

    @FXML
    private Button btnLogout;

    public StudentMain() {
    }

    public static Alert createAlert(String message, Error error, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        if (error != null) {
            alert.setContentText(error.toString());
        }
        return alert;
    }

    @FXML
    void downloadData(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose location to save SQL file");
        fileChooser.setInitialFileName("exampleData.sql");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("sql file (*.sql)", "*.sql"));
        File file = fileChooser.showSaveDialog(btnDownloadData.getScene().getWindow());
        if (file != null) {
            try (PrintWriter outFile = new PrintWriter(file)) {
                outFile.print(student.getData());
            } catch (FileNotFoundException e) {
                createAlert("Problem creating file", new Error(e), Alert.AlertType.ERROR);
            } catch (Error error) {
                createAlert("Problem creating file", error, Alert.AlertType.ERROR);
            }
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
    void newAssignment(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudentAssignment.fxml"));
            Parent root = fxmlLoader.load();
            StudentAssignment controller = fxmlLoader.getController();
            controller.setStudent(student);

            Stage stage = (Stage) btnNewAssignment.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            createAlert("Problem starting assignment", new Error(e), Alert.AlertType.ERROR);
        }
    }

    private void print(String text) {
        lblContentPane.setText(lblContentPane.getText() + text);
    }

    private void println(String text) {
        print(text + "\n");
    }

    private void println() {
        println("");
    }

    public void setStudent(Student student) {
        this.student = student;

        lblStudentNum.setText(this.student.getStudentNum());

        //TODO: show previous assignments in list
    }

    //TODO: show feedback and mark when list item clicked
}
