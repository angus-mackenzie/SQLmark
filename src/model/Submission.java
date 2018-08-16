import java.util.ArrayList;
import java.util.List;

public class Submission {
    private List<Answer> answers;

    public int getTotalMark() {
        int totalMark = 0;
        for (Answer answer : answers) {
            totalMark += answer.getMark();
        }
        return totalMark;
    }

    public String getFeedback() {
        StringBuilder feedback = new StringBuilder();
        for (Answer answer : answers) {
            feedback.append(answer.getFeedback()).append("\n");
        }
        return feedback.toString();
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public Submission(List<Answer> answers) {
        this.answers = answers;
    }

    public Submission() {
        this.answers = new ArrayList<>();
    }
}
