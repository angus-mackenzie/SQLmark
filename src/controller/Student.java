package controller;

import model.Database;
import model.Error;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Student {
    private String randomData;
    private model.Student studentModel;
    private model.Assignment assignmentModel;
    private model.Submission currentSubmission;

    public Student(String studentNum) throws Error {
        try {
            this.assignmentModel = new model.Assignment();
            this.studentModel = new model.Student(studentNum);
            this.randomData = assignmentModel.getRandomData();
        } catch (SQLException e) {
            throw new Error("Error connecting to database!", e);
        }
    }

    public String getStudentNum() {
        return studentModel.getStudentNum();
    }

    public String loadAssignment() {
        return assignmentModel.toString();
    }

    public String getData() {
        // Possibly download as File here, or in view
        return randomData;
    }

    public void createSubmission() {
        currentSubmission = new model.Submission(assignmentModel);
    }

    public String getNextQuestion() {
        return currentSubmission.getNextQuestion().getQuestionText();
    }

    public void answerQuestion(String answer) throws Error {
        currentSubmission.addAnswer(new model.Answer(answer, currentSubmission.getNextQuestion()));
    }

    public void submitAssignment() {
        currentSubmission.submit();
    }

    public int getMark() throws Error {
        return currentSubmission.getTotalMark();
    }

    public String getFeedback() throws Error {
        return currentSubmission.getFeedback();
    }

    public static void main(String[] args) {
        String studentNum = JOptionPane.showInputDialog(null, "Enter your student number:",
                "Welcome", JOptionPane.QUESTION_MESSAGE);
        Database db = new Database();
        Map<String,Object> where = new HashMap<>();

        where.put("studentNum",studentNum);
        db.prepareSelect("studentTable",where);
        db.execute();
        ResultSet rs = db.getResultSet();
        try{
            while (rs.next()) {
                ResultSetMetaData rsMetaData = rs.getMetaData();
                int numberOfColumns = rsMetaData.getColumnCount();
                for (int i = 1; i < numberOfColumns + 1; i++) {
                    System.out.println(rs.getString(i)+"\n");
                    System.out.print(rs.getString(i).equals(studentNum));
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("It worked??");
        try {
            Student student = new Student(studentNum);
            view.Student studentView = new view.Student(student);
        } catch (Error error) {
            // TODO: Show error box
            error.printStackTrace();
        }
    }
}
