package controller;

import model.Error;

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
}
