package controller;

import model.CSV;
import model.Database;
import model.Error;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The class the lecturer uses to enter in information pertaining to the assignment
 * @author Angus Mackenzie
 * @version 04/09/2018
 */
public class Lecturer {
    private model.Assignment assignmentModel;
    private List<model.Student> studentModels;

    //TODO Javadocs for this
    public Lecturer() throws Error {
        //TODO Is this necessary?
//        try {
//            this.assignmentModel = new model.Assignment();
//
//            this.studentModels = WorkingData.getStudents();
//        } catch (SQLException e) {
//            throw new Error("Error connecting to database!", e);
//        }
    }

    /**
     * Clears the data from the databases
     * @param tableName to clear data from
     * @return output from delete query
     * @throws Error if cannot delete
     */
    public String clear(String tableName) throws Error{
        // TODO Fix
        Database db = new Database();
        return db.clear(tableName);
    }

    /**
     * Takes in a file and loads the data to run the assignment with
     * @param filename to load in
     * @throws Exception if the file reader or DB break
     */
    public void loadData(String filename) throws Exception{
        CSV csvReader = new CSV(filename);
        List<String> columNames = csvReader.parseLine();
        Database db = new Database();
        db.prepareCreate(columNames, "data_store");
        db.execute();
        List<String> input = csvReader.parseLine();
        while(input!=null){
            db.prepareInsert(input);
            db.execute();
            input = csvReader.parseLine();
        }
    }

    /**
     * Takes in the file with questions and answers
     * @param filename to read
     * @throws Exception if the file reader or DB break
     */
    private void loadQuestions(String filename) throws Exception{
        CSV csvReader = new CSV(filename);
        List<String> columNames = csvReader.parseLine();
        Database db = new Database("admin_data");
        String tableName = "questions";
        db.prepareCreate(columNames, tableName);
        db.execute();
        List<String> input = csvReader.parseLine();
        while(input!=null){
            db.prepareInsert(input);
            db.execute();
            input = csvReader.parseLine();
        }
    }

    /**
     * Takes in the filename for the students
     * @param filename students to read
     * @throws Exception if the file reader or DB break
     */
    private void loadStudents(String filename) throws Exception{
        CSV csvReader = new CSV(filename);
        List<String> columNames = csvReader.parseLine();
        Database db = new Database("admin_data");
        String tableName = "students";
        db.prepareCreate(columNames, tableName);
        db.execute();
        List<String> input = csvReader.parseLine();
        while(input!=null){
            db.prepareInsert(input);
            db.execute();
            input = csvReader.parseLine();
        }

    }

    //TODO Fix this
    /**
     * Outputs all the students with their highest mark to the filename inputted
     * @param filename to write to
     * @throws Error if there is an issue writing to the file
     */
    public void exportStudents(String filename) throws Error{
        List<String> heading = new ArrayList<>();
        heading.add("student_num");
        heading.add("highest_mark");
        try{
            CSV csv = new CSV(filename,heading);
            // TODO: Export students and marks to CSV
            for(model.Student student : studentModels) {
                List<String> row = new ArrayList<>();
                row.add(student.getStudentNum());
                row.add(student.getHighestMark()+"");
                csv.writeLine(row);
            }
        }catch(Exception e){
            throw new Error("Couldn't find the file "+filename, e.getCause());
        }
    }

    /**
     * The driver that runs when the lecturer starts the application
     * @param args for commandline arguments (none)
     */
    //TODO We need more options: export data, import data and clear data - some menu is necessary
    public static void main(String[] args) {
        try {
            Lecturer lecturer = new Lecturer();
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the data filename:");
            String filename = sc.nextLine();
            lecturer.loadData(filename);
            System.out.println("Data loaded successfully!");
            System.out.println("Enter the students filename:");
            String studentFile = sc.nextLine();
            lecturer.loadStudents(studentFile);
            System.out.println("Loaded students successfully");
            System.out.println("Enter question and answer filename:");
            String qnaFile = sc.nextLine();
            lecturer.loadQuestions(qnaFile);
            System.out.println("Successful");
        } catch (Exception error) {
            error.printStackTrace();
            System.exit(-1);
        }
    }
}
