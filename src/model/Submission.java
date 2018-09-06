package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
        Database db = new Database("admin_data");

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
     * @throws Error if assignment not completed
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
     * @throws Error if assignment not completed
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
     * @param assignment that was submitted
     */
    public Submission(Assignment assignment) {
        this.date = new Date();
        this.assignment = assignment;
        this.answers = new ArrayList<>();
        this.currentQuestion = 0;
    }

    /**
     * Gets the next question
     * @return the next question, or null if there are no more questions
     */
    public Question getNextQuestion() {
        try {
            return assignment.getQuestion(currentQuestion);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    /**
     * Adds the student's answer to the answers
     * @param answer to be added
     * @throws Error if there are too many answers
     */
    public void addAnswer(Answer answer) throws Error {
        if (!checkComplete()) {
            answers.add(answer);
            currentQuestion++;
        } else {
            throw new Error("Too many answers submitted!");
        }
    }
    //TODO Fix this
    /**
     * Submits the student's submission and saves it to the db
     * @param  studentNum to submit
     * @return the current submission
     */
    public Submission submit(String studentNum) throws Error{

        Database db = new Database("admin_data");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        List<String> row = new ArrayList<String>();
        this.date = new Date();
        List<String> columns = new ArrayList<>();
        columns.add("student_num");
        columns.add("submission_date");
        row.add(studentNum);
        row.add(dateFormat.format(this.date));
        String tableName = "student_submissions";
        db.prepareInsert(tableName,columns,row);
        db.execute();
        int mark = this.getTotalMark();
        Map<String,Object> where = new HashMap<>();
        where.put("student_num",studentNum);
        db.prepareSelect(tableName,where);
        db.execute();
        ResultSet rs = db.getResultSet();
        List<Integer> IDs = new ArrayList<>();
        try {
            while (rs.next()) {
                IDs.add(Integer.parseInt(rs.getString("submission_id")));
            }
        } catch (SQLException e) {
            throw new Error("Can't get previous submission",e);
        }
        int submissionID = Collections.max(IDs);//gets the most recent ID for the student
        System.out.println(mark);
        System.out.println(submissionID);
        return this;
    }

    public String getDate() {
        return date.toString();
    }
}
