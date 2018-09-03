package controller;

import model.Error;
import model.Submission;

import java.sql.SQLException;
import java.util.Scanner;

public class Student {
    private String randomData;
    private model.Student studentModel;
    private model.Assignment assignmentModel;
    private model.Submission currentSubmission;

    public Student(String studentNum) throws Error {
        try {
            this.assignmentModel = new model.Assignment();
            this.studentModel = new model.Student(studentNum, assignmentModel);
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
        studentModel.addSubmission(currentSubmission.submit(getStudentNum()));
    }

    public int getMark() throws Error {
        return currentSubmission.getTotalMark();
    }

    public String getFeedback() throws Error {
        return currentSubmission.getFeedback();
    }

    public static void main(String[] args) {
        System.out.print("Enter your student number: ");
        Scanner sc = new Scanner(System.in);
        String studentNum = sc.nextLine();

        try {
            Student student = new Student(studentNum);
            view.Student studentView = new view.Student(student, sc);
        } catch (Error error) {
            //TODO: Show error box
            error.printStackTrace();
        }
    }

    public String getPastSubmissions() throws Error {
        StringBuilder returnString = new StringBuilder();
        for (Submission submission : studentModel.getSubmissions()) {
            returnString.append(String.format("%s: %d\n", submission.getDate(), submission.getTotalMark()));
        }
        return returnString.toString();
    }
}
