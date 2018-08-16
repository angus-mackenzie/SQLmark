public class Question {
    private String questionText;
    private Dataset correctAnswer;

    public String getQuestionText() {
        return questionText;
    }

    public Dataset getCorrectAnswer() {
        return correctAnswer;
    }

    public Question(String questionText, String correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = new Dataset(correctAnswer);
    }
}