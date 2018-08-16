import java.io.File;
import java.util.List;

public class Student {
    private String studentNum;
    private List<Submission> submissions;
    private Dataset personalDataset;

    public String getStudentNum() {
        return studentNum;
    }

    public File getPersonalDataset() {
        return personalDataset.exportDataSQL();
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

    public Student(String studentNum, Dataset personalDataset) {
        this.studentNum = studentNum;
        this.personalDataset = personalDataset;
    }

}
