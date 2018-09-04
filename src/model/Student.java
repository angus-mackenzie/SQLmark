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
     * @return
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
     * Returns the student's highest mark out of all the students
     * @return highest mark
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

    public void addSubmission(Submission submission) {
        submissions.add(submission);
    }

    public int getHighestMark() {
        int mark = 0;
        for (Submission submission : submissions) {
            try {
                if (mark < submission.getTotalMark()) {
                    mark = submission.getTotalMark();
                }
            } catch (Error error) {
                // Mark not included as incomplete
            }
        }
        return mark;
    }

    /**
     * Creates a student with the given number
     * @param studentNum
     */
    public Student(String studentNum) {
        this.studentNum = studentNum;
    }

}
