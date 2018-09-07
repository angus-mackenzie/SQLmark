package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The different students taking the SQL test
 * @author Matthew Poulter
 * @version 15/08/2018
 */
public class Student {
    private String studentNum;
    private List<Submission> submissions;

    /**
     * Returns the student number
     * @return student number
     */
    public String getStudentNum() {
        return studentNum;
    }

    /**
     * Returns the submissions of the student
     * @return the submissions
     */
    public List<Submission> getSubmissions() {
        return submissions;
    }

    /**
     * Creates a student given a student number and assignment
     * @param studentNum of the student
     * @param assignment of questions
     * @throws Error if cannot select student from table
     */
    public Student(String studentNum, Assignment assignment) throws Error {
        this.studentNum = studentNum;
        this.submissions = new ArrayList<>();

        Database db = new Database("admin_data");
        db.prepareSelect("students", Map.of("student_num", studentNum));
        db.execute();
        ResultSet rs = db.getResultSet();
        boolean userFound = false;
        try {
            while (rs.next()) {
                userFound = true;
            }
        } catch (SQLException e) {
            db.closeRS();
            db.close();
            throw (new Error("Student load error!", e));
        }

        if (!userFound) {
            db.closeRS();
            db.close();
            throw (new Error("Student not found!"));
        }
        db.closeRS();

        db.prepareSelect("student_submissions", Map.of("student_num", studentNum));
        db.execute();
        rs = db.getResultSet();
        try {
            while (rs.next()) {
                submissions.add(new Submission(assignment, rs.getInt("submission_id")));
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
     * Adds the submission to the submissions list
     * @param submission to be added
     * @throws Error if can't save and then add submission
     */
    public void addSubmission(Submission submission) throws Error{
        submissions.add(submission);
    }

    /**
     * Returns the highest mark for a student
     * @return highest mark for the student
     */
    public int getHighestMark() {
        int mark = 0;
        for (Submission submission : submissions) {
            try {
                if (mark < submission.getTotalMark()) {
                    mark = submission.getTotalMark();
                }
            } catch (Error error) {
                System.out.println("No Assignments to get");
                //throw new Error("Assignment not completed",error);
                // Mark not included as incomplete
            }
        }
        return mark;
    }

    /**
     * Creates a student with the given number
     * @param studentNum to create student with
     */
    public Student(String studentNum) {
        this.studentNum = studentNum;
        this.submissions = new ArrayList<>();
    }

}
