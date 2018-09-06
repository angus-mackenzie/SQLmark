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
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Error;

import java.io.IOException;
import java.util.Optional;

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
            try{
                System.out.println(student.getData());
            }catch(Error e){
                createAlert("Could not download the data!",e, Alert.AlertType.INFORMATION);
            }

    }


    public static Alert createAlert(String message, Error error, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        if(error==null){
            //do nothing
        }else{
            alert.setContentText(error.toString());
        }
        return alert;
    }
    @FXML
    void logout(ActionEvent event) {
        Alert a = createAlert("Are you sure you want to logout?",null, Alert.AlertType.WARNING);
        Optional<Boolean> result =a.showAndWait();
        if (result.get()){
            // ... user chose OK
            System.exit(0);
        } else {
            // ... user chose CANCEL or closed the dialog
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
