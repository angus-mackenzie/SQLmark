package controller;

import model.Error;
import model.Submission;

/**
 * Class for each student, created when the student logs ins
 *
 * @author Matthew Poulter
 * @version 04/09/2018
 */
public class Student {
    private String randomData;
    private model.Student studentModel;
    private model.Assignment assignmentModel;
    private model.Submission currentSubmission;

    /**
     * Creates a Student instance given a student number
     *
     * @param studentNum to create the assignment with
     * @throws Error if there is a problem connecting to the database
     */
    public Student(String studentNum) throws Error {
        this.assignmentModel = new model.Assignment();
        this.studentModel = new model.Student(studentNum, assignmentModel);
        this.randomData = assignmentModel.getRandomData();
    }

    /**
     * Gets the student number
     *
     * @return the given student number
     */
    public String getStudentNum() {
        return studentModel.getStudentNum();
    }

    /**
     * Loads the assignment
     *
     * @return a string representation of the assignment
     */
    public String loadAssignment() {
        return assignmentModel.toString();
    }

    /**
     * Get the data given to the student for the assignment
     *
     * @return a string representation of the data
     */
    public String getData() throws Error{
        // Possibly download as File here, or in vieww
        return getPastSubmissions();
    }

    /**
     * Creates a submission for the student
     */
    public void createSubmission() {
        currentSubmission = new model.Submission(assignmentModel);
    }

    /**
     * Gets the next question from the assignment
     *
     * @return next question or null if there are no more questions
     */
    public String getNextQuestion() {
        try {
            return currentSubmission.getNextQuestion().getQuestionText();
        } catch (NullPointerException ex) {
            return null;
        }
    }

    /**
     * Adds the student's answer to the students, gets the next question
     *
     * @param answer the student entered
     * @throws Error if there is an issue getting the next question, or storing the answer
     */
    public void answerQuestion(String answer) throws Error {
        currentSubmission.addAnswer(new model.Answer(answer, currentSubmission.getNextQuestion()));
    }

    /**
     * Submits a whole assignment to the student
     *
     * @throws Error if it cannot submit the assignment
     */
    public void submitAssignment() throws Error {
        studentModel.addSubmission(currentSubmission.submit(getStudentNum()));
    }

    /**
     * Gets the total mark for the current submission
     *
     * @return current mark
     * @throws Error if there are no submissions
     */
    public int getMark() throws Error {
        return currentSubmission.getTotalMark();
    }

    /**
     * Gets the feedback for the answered questions
     *
     * @return a string representation of the feedback
     * @throws Error if there is no feedback
     */
    public String getFeedback() throws Error {
        return currentSubmission.getFeedback();
    }

    /**
     * Loads the previous submissions
     *
     * @return string representation of the previous submissions
     * @throws Error if there are no submissions
     */
    public String getPastSubmissions() throws Error {
        StringBuilder returnString = new StringBuilder();
        for (Submission submission : studentModel.getSubmissions()) {
            returnString.append(String.format("%s: %d\n", submission.getDate(), submission.getTotalMark()));
        }
        return returnString.toString();
    }
}
