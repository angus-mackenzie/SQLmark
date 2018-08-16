import java.util.ArrayList;
import java.util.List;

public class Submission {
    private List<Answer> answers;

    public int getTotalMark() {
        throw (new UnsupportedOperationException());
    }

    public String getFeedback() {
        throw (new UnsupportedOperationException());
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
