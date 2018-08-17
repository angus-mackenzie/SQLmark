package controller;

import model.Error;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

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
        System.out.print("Enter your student number: ");
        String studentNum = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            studentNum = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Student student = new Student(studentNum);
            view.Student studentView = new view.Student(student);
        } catch (Error error) {
            // TODO: Show error box
            error.printStackTrace();
        }
    }
}
