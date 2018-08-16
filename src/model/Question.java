package model;

public class Question {
    private int questionNum;
    private String questionText;
    private Dataset correctAnswer;
    private FeedbackType feedbackType;

    public enum FeedbackType {
        NONE, COMPILE, VERBOSE
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public String getQuestionText() {
        return questionText;
    }

    public Dataset getCorrectAnswer() {
        return correctAnswer;
    }

    public FeedbackType getFeedbackType() {
        return feedbackType;
    }

    public Question(String questionText, String correctAnswer, FeedbackType feedbackType) {
        this.questionText = questionText;
        this.correctAnswer = new Dataset(correctAnswer);
        this.feedbackType = feedbackType;
    }
}