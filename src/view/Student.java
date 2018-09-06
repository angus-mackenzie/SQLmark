package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import model.Error;

import java.util.Optional;

/**
 * We are still implementing a better UI, hence this will not contain javadocs
 */
public class Student extends Application {
    private controller.Student student;

    /**
     * The driver for our program
     *
     * @param args given by commandline, not used
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Welcome to the SQL Automarker");
        dialog.setContentText("Please enter your student number:");

        Optional<String> result = dialog.showAndWait();
        try {
            if (result.isPresent()) {
                student = new controller.Student(result.get());
            } else {
                //TODO: exit
            }
        } catch (Error error) {
            //TODO: error
            error.printStackTrace();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudentMain.fxml"));
        Parent root = fxmlLoader.load();
        StudentMain controller = fxmlLoader.getController();
        controller.setStudent(student);

        primaryStage.setTitle("SQL Automarker");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
