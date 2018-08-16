package model;

import java.util.List;

public class Student {
    private String studentNum;
    private List<Submission> submissions;

    public String getStudentNum() {
        return studentNum;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public int getHighestMark() {
        int mark = 0;
        for (Submission submission : submissions) {
            try {
                if (mark < submission.getTotalMark()) {
                    mark = submission.getTotalMark();
                }
            } catch (Error error) {
                // Not included as incomplete
            }
        }
        return mark;
    }

    public Student(String studentNum) {
        this.studentNum = studentNum;
    }

}
