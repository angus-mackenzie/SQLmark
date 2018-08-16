package model;

import java.util.ArrayList;
import java.util.List;

public class Submission {
    private final Assignment assignment;
    private List<Answer> answers;
    private int currentQuestion;

    public int getTotalMark() throws Error {
        if (checkComplete()) {
            int totalMark = 0;
            for (Answer answer : answers) {
                totalMark += answer.getMark();
            }
            return totalMark;
        } else {
            throw new Error("Assignment not complete!");
        }
    }

    public String getFeedback() throws Error {
        if (checkComplete()) {
            StringBuilder feedback = new StringBuilder();
            for (Answer answer : answers) {
                feedback.append("Question ")
                        .append(answer.getQuestionNum())
                        .append(":\n")
                        .append(answer.getFeedback())
                        .append("\n\n");
            }
            return feedback.toString();
        } else {
            throw new Error("Assignment not complete!");
        }
    }

    // Might not be needed
    public Submission(Assignment assignment, List<Answer> answers) {
        this.assignment = assignment;
        this.answers = answers;
    }

    public boolean checkComplete() {
        return assignment.getTotalQuestions() == answers.size();
    }

    public Submission(Assignment assignment) {
        this.assignment = assignment;
        this.answers = new ArrayList<>();
        this.currentQuestion = 1;
    }

    public Question getNextQuestion() {
        try {
            return assignment.getQuestion(currentQuestion);
        } catch (ArrayIndexOutOfBoundsException ex) {
            return null;
        }
    }

    public void addAnswer(Answer answer) throws Error {
        if (!checkComplete()) {
            answers.add(answer);
            currentQuestion++;
        } else {
            throw new Error("Too many answers submitted!");
        }
    }

    public void submit() {
        // TODO: Submit and save to database
    }
}