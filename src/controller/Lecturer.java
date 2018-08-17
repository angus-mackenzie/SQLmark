package controller;

import model.CSV;
import model.Database;
import model.Error;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Lecturer {
    private model.Assignment assignmentModel;
    private List<model.Student> studentModels;

    public Lecturer() throws Error {
//        try {
//            this.assignmentModel = new model.Assignment();
//
//            this.studentModels = WorkingData.getStudents();
//        } catch (SQLException e) {
//            throw new Error("Error connecting to database!", e);
//        }
    }

    public void clear() {
        // TODO: Clear all data, questions and students
        throw new UnsupportedOperationException();
    }
    //TODO: Add table name
    public void loadData(String filename) {
        CSV csvReader = new CSV(filename);
        try{
            List<String> columNames = csvReader.parseLine();
            Database db = new Database();
            db.prepareCreate(columNames, "dataTable");
            db.execute();
            List<String> input = csvReader.parseLine();
            while(input!=null){
                db.prepareInsert(input);
                db.execute();
                input = csvReader.parseLine();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loadQuestions(String filename) {
        CSV csvReader = new CSV(filename);
        try{
            List<String> columNames = csvReader.parseLine();
            Database db = new Database();
            String tableName = "questionTable";
            db.prepareCreate(columNames, tableName);
            db.execute();
            List<String> input = csvReader.parseLine();
            while(input!=null){
                db.prepareInsert(input);
                db.execute();
                input = csvReader.parseLine();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loadStudents(String filename) {
        CSV csvReader = new CSV(filename);
        try{
            List<String> columNames = csvReader.parseLine();
            Database db = new Database();
            String tableName = "studentTable";
            db.prepareCreate(columNames, tableName);
            db.execute();
            List<String> input = csvReader.parseLine();
            while(input!=null){
                db.prepareInsert(input);
                db.execute();
                input = csvReader.parseLine();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public File exportStudents() {
        // TODO: Export students and marks to CSV
        /* for(model.Student student : studentModels) {
            System.out.println(student.getStudentNum() + ": " + student.getHighestMark());
        } */
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
//        String studentNum = JOptionPane.showInputDialog(null, "Enter admin password:",
//                "Welcome", JOptionPane.QUESTION_MESSAGE);
//
//        // TODO: Check password

        try {
            Lecturer lecturer = new Lecturer();
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the data filename:");
            String filename = sc.nextLine();
            lecturer.loadData(filename);
            System.out.println("Data loaded successfully!");
            System.out.println("Enter the student marks filename:");
            String studentFile = sc.nextLine();
            lecturer.loadStudents(studentFile);
            System.out.println("Loaded students successfully");
            System.out.println("Enter question and answer filename:");
            String qnaFile = sc.nextLine();
            lecturer.loadQuestions(qnaFile);
            System.out.println("Successful");
            //view.Lecturer lecturerView = new view.Lecturer(lecturer);
        } catch (Error error) {
            // TODO: Show error box
            error.printStackTrace();
        }
    }
}
