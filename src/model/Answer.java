public class Answer {
    private String answer;
    private Dataset output;
    private int mark;
    private String feedback;
    private Question question;

    public int getMark() {
        return mark;
    }

    public String getFeedback() {
        return feedback;
    }

    private int calculateMark() {
        throw (new UnsupportedOperationException());
    }

    public Answer(String answer, Question question) {
        this.answer = answer;
        this.question = question;

        this.output = new Dataset(answer);

        throw (new UnsupportedOperationException());
    }
}
