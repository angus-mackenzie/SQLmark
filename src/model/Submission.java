package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Tracks submissions for assignments
 * @author Matthew Poulter
 * @version 15/08/2019
 */
public class Submission {
    private final Assignment assignment;
    private List<Answer> answers;
    private int currentQuestion;
    private Date date;

    public Submission(Assignment assignment, int submissionID) throws Error {
        this(assignment);
        Database db = new Database();

        db.prepareSelect("student_answers", Map.of("submission_id", submissionID));
        db.execute();
        ResultSet rs = db.getResultSet();
        try {
            while (rs.next()) {
                answers.add(new Answer(rs.getString("answer"), assignment.getQuestion(rs.getInt("question_num"))));
            }
        } catch (SQLException e) {
            db.closeRS();
            db.close();
            throw (new Error("Submission load error!", e));
        }
        db.closeRS();

        db.close();
    }

    /**
     * returns the total mark for the student's submission
     * @return total mark
     * @throws Error
     */
    public int getTotalMark() throws Error {
        if (checkComplete()) {
            int totalMark = 0;
            for (Answer answer : answers) {
                totalMark += answer.getMark();
            }
            return totalMark;
        } else {
            throw new Error("Assignment not complete!");
        }
    }

    /**
     * Gets the feedback for the entire submissions
     * @return feedback
     * @throws Error
     */
    public String getFeedback() throws Error {
        if (checkComplete()) {
            StringBuilder feedback = new StringBuilder();
            for (Answer answer : answers) {
                feedback.append("Question ")
                        .append(answer.getQuestionNum())
                        .append(":\n")
                        .append(answer.getFeedback())
                        .append("\n\n");
            }
            return feedback.toString();
        } else {
            throw new Error("Assignment not complete!");
        }
    }

    /**
     * Checks whether the assignment has been completed
     * @return true or false
     */
    public boolean checkComplete() {
        return assignment.getTotalQuestions() == answers.size();
    }

    /**
     * Creates a submission given an assignment
     * @param assignment
     */
    public Submission(Assignment assignment) {
        this.assignment = assignment;
        this.answers = new ArrayList<>();
        this.currentQuestion = 1;
    }

    /**
     * Gets the next question
     * @return
     */
    public Question getNextQuestion() {
        try {
            return assignment.getQuestion(currentQuestion);
        } catch (ArrayIndexOutOfBoundsException ex) {
            return null;
        }
    }

    /**
     * Adds the student's answer to the answers
     * @param answer
     * @throws Error
     */
    public void addAnswer(Answer answer) throws Error {
        if (!checkComplete()) {
            answers.add(answer);
            currentQuestion++;
        } else {
            throw new Error("Too many answers submitted!");
        }
    }

    /**
     * Submits the student's submission and saves it to the db
     */
    public Submission submit() {
        // TODO: Submit and save to database
        return this;
    }

    public String getDate() {
        return date.toString();
    }
}
