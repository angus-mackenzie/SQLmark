package model;

/**
 * A question object for the lecturer's questions
 * @author Matthew Poulter
 * @version 15/08/2018
 */
public class Question {
    private int questionNum;
    private String questionText;
    private Dataset correctAnswer;
    private FeedbackType feedbackType;

    /**
     * An enum that allows the type of feedback to be monitored easily
     */
    public enum FeedbackType {
        NONE, COMPILE, VERBOSE
    }

    /**
     * Returns the question number
     * @return question number
     */
    public int getQuestionNum() {
        return questionNum;
    }

    /**
     * Returns the question text
     * @return question text
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Get the correct answer
     * @return correct answer
     */
    public Dataset getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Get feedback type
     * @return feedback type
     */
    public FeedbackType getFeedbackType() {
        return feedbackType;
    }

    /**
     * Creates a question with its text, correct answer and feedback type
     * @param questionNum
     * @param questionText
     * @param correctAnswer
     * @param feedbackType
     */
    public Question(int questionNum, String questionText, String correctAnswer, FeedbackType feedbackType) throws Error{
        this.questionNum = questionNum;
        this.questionText = questionText;
        this.correctAnswer = new Dataset(correctAnswer, "data_store");
        this.feedbackType = feedbackType;
    }
}