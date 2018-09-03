package model;

/**
 * Holds an answer a student has entered
 * @author Matthew Poulter
 * @version 13/08/2018
 */
public class Answer {
    private String answer;
    private Dataset output;
    private int mark;
    private Question question;

    /**
     * Question number
     * @return question number
     */
    public int getQuestionNum() {
        return question.getQuestionNum();
    }

    /**
     * Returns mark for question
     * @return mark
     */
    public int getMark() {
        return mark;
    }

    /**
     * Returns the feedback for the student, dependent on the level of verbosity specified by the lecturer.
     * @return feedback (string)
     */
    public String getFeedback() {
        switch (question.getFeedbackType()) {
            case COMPILE:
                return output.getCompileMessage();
            case VERBOSE:
                return String.format("%s\nExpected output:\n%s\nYour output:\n%s",
                        output.getCompileMessage(),
                        question.getCorrectAnswer().toString(),
                        output.toString());
            default:
                return "";
        }
    }

    /**
     * Calculates the student's mark for the question
     * @return value
     */
    private int calculateMark() {
        switch (output.getCompileStatus()) {
            case SUCCESS:
                if (output.equals(question.getCorrectAnswer())) {
                    return 2;
                }
                return 1;
            default:
                return 0;
        }
    }

    /**
     * Takes in an answer and a question, stores them
     * @param answer
     * @param question
     */
    public Answer(String answer, Question question) {
        this.answer = answer;
        this.question = question;

        this.output = new Dataset(answer);
        this.mark = calculateMark();
    }
}
