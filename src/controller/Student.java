package controller;

import javafx.stage.FileChooser;
import javafx.stage.Window;
import model.Error;
import model.Submission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Class for each student, created when the student logs in
 *
 * @author Matthew Poulter
 * @version 04/09/2018
 */
public class Student {
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
     * @param window pop up interface
     * @throws Error if there is an issue making the .sql file
     */
    public void getData(Window window) throws Error {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose location to save SQL file");
        fileChooser.setInitialFileName("exampleData.sql");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("sql file (*.sql)", "*.sql"));
        File file = fileChooser.showSaveDialog(window);
        if (file != null) {
            try (PrintWriter outFile = new PrintWriter(file)) {
                outFile.print(assignmentModel.getRandomData());
            } catch (FileNotFoundException e) {
                throw new Error(e);
            }
        }
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
     */
    public void answerQuestion(String answer) {
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
     */
    public List<Submission> getPastSubmissions() {
        return studentModel.getSubmissions();
    }
}
