package model;

import java.util.List;

/**
 * The different student's taking the SQL test
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

    /**
     * Creates a student with the given number
     * @param studentNum
     */
    public Student(String studentNum) {
        this.studentNum = studentNum;
    }

}
