import java.util.List;

public class Assignment {
    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }

    public Assignment(List<Question> questions) {
        this.questions = questions;
    }
}
