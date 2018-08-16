public class Answer {
    private String answer;
    private Dataset output;
    private int mark;
    private Question question;

    public int getMark() {
        return mark;
    }

    public String getFeedback() {
        switch (this.question.getFeedbackType()) {
            case COMPILE:
                return this.output.getCompileMessage();
            case VERBOSE:
                return "";
            default:
                return "";
        }
    }

    private int calculateMark() {
        // TODO: Calculate mark by comparing datasets
        throw (new UnsupportedOperationException());
    }

    public Answer(String answer, Question question) {
        this.answer = answer;
        this.question = question;

        this.output = new Dataset(answer);
        this.mark = calculateMark();
    }
}
