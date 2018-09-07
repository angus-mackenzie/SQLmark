package view;

import controller.Student;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
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

public class StudentMain {
    private Student student;
    private ListProperty<model.Submission> listProperty = new SimpleListProperty<>();

    @FXML
    private Button btnDownloadData;

    @FXML
    private Button btnNewAssignment;

    @FXML
    private ListView<model.Submission> lstPreviousAssignments;

    @FXML
    private TextArea txaContentPane;

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
        try {
            student.getData(btnDownloadData.getScene().getWindow());
        } catch (Error error) {
            createAlert("Problem creating file", error, Alert.AlertType.ERROR);
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
        txaContentPane.setText(txaContentPane.getText() + text);
    }

    private void println(String text) {
        print(text + "\n");
    }

    private void println() {
        println("");
    }

    private void clean() {
        txaContentPane.setText("");
    }

    public void setStudent(Student student) {
        this.student = student;

        lblStudentNum.setText(this.student.getStudentNum());

        lstPreviousAssignments.itemsProperty().bind(listProperty);
        listProperty.set(FXCollections.observableArrayList(student.getPastSubmissions()));


        lstPreviousAssignments.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                Platform.runLater(() -> {
                    try {
                        clean();
                        println("Total mark: " + newValue.getTotalMark());
                        println();
                        println(newValue.getFeedback());
                    } catch (Error error) {
                        createAlert("Problem displaying feedback", error, Alert.AlertType.ERROR);
                    }
                });
            }
        });
    }
}
