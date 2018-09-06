package view;

import controller.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentMain {
    private Student student;

    @FXML
    private Button btnDownloadData;

    @FXML
    private Button btnNewAssignment;

    @FXML
    private ListView<?> lstPreviousAssignments;

    @FXML
    private Label lblContentPane;

    @FXML
    private Label lblStudentNum;

    @FXML
    private Button btnLogout;

    public StudentMain() {
    }

    @FXML
    void downloadData(ActionEvent event) {
        // TODO student.getData()
    }

    @FXML
    void logout(ActionEvent event) {
        //TODO: exit
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
            //TODO
            e.printStackTrace();
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
