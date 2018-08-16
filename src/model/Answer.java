package model;

public class Answer {
    private String answer;
    private Dataset output;
    private int mark;
    private Question question;

    public int getQuestionNum() {
        return question.getQuestionNum();
    }

    public int getMark() {
        return mark;
    }

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

    public Answer(String answer, Question question) {
        this.answer = answer;
        this.question = question;

        this.output = new Dataset(answer);
        this.mark = calculateMark();
    }
}
