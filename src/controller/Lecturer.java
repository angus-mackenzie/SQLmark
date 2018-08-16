package controller;

import model.CSV;
import model.Error;
import model.WorkingData;

import javax.swing.*;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class Lecturer {
    private model.Assignment assignmentModel;
    private List<model.Student> studentModels;

    public Lecturer() throws Error {
        try {
            this.assignmentModel = new model.Assignment();

            this.studentModels = WorkingData.getStudents();
        } catch (SQLException e) {
            throw new Error("Error connecting to database!", e);
        }
    }

    public void clear() {
        // TODO: Clear all data, questions and students
        throw new UnsupportedOperationException();
    }
    //TODO: Add table name
    public void loadData(String filename) {
        CSV csvReader = new CSV(filename);
        try{
            List<String> input = csvReader.parseLine();
            while(input!=null){
                System.out.println(input.get(0));
                input = csvReader.parseLine();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loadQuestions(String filename) {
        // TODO: Load questions and answers from CSV
        throw new UnsupportedOperationException();
    }

    public void loadStudents(String filename) {
        // TODO: Load students from CSV
        throw new UnsupportedOperationException();
    }

    public File exportStudents() {
        // TODO: Export students and marks to CSV
        /* for(model.Student student : studentModels) {
            System.out.println(student.getStudentNum() + ": " + student.getHighestMark());
        } */
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        String studentNum = JOptionPane.showInputDialog(null, "Enter admin password:",
                "Welcome", JOptionPane.QUESTION_MESSAGE);

        // TODO: Check password

        try {
            Lecturer lecturer = new Lecturer();

            view.Lecturer lecturerView = new view.Lecturer(lecturer);
        } catch (Error error) {
            // TODO: Show error box
            error.printStackTrace();
        }
    }
}
