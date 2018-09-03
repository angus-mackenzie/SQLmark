package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Student {
    private String studentNum;
    private List<Submission> submissions;

    public String getStudentNum() {
        return studentNum;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public Student(String studentNum, Assignment assignment) throws Error {
        this.studentNum = studentNum;
        this.submissions = new ArrayList<>();

        Database db = new Database();
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


}
