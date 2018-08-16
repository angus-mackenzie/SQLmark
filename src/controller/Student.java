package controller;

import model.Error;

import java.io.File;
import java.sql.SQLException;

public class Student {
    private model.Student studentModel;
    private model.Assignment assignmentModel;
    private model.Submission currentSubmission;

    public Student(String studentNum) throws Error {
        try {
            this.assignmentModel = new model.Assignment();
            this.studentModel = new model.Student(studentNum, assignmentModel.getRandomData());
        } catch (SQLException e) {
            // TODO: Error handling
        }
    }

    public String loadAssignment() {
        return assignmentModel.toString();
    }

    public File getData() {
        return studentModel.getPersonalDataset();
    }

    public void createSubmission() {
        currentSubmission = new model.Submission(assignmentModel);
    }

    public String getNextQuestion() {
        return currentSubmission.getNextQuestion().getQuestionText();
    }

    public void answerQuestion(String answer, model.Question question) throws Error {
        currentSubmission.addAnswer(new model.Answer(answer, question));
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
