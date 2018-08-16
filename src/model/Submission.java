package model;

import java.util.ArrayList;
import java.util.List;

public class Submission {
    private final Assignment assignment;
    private List<Answer> answers;

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

    public boolean checkComplete() {
        return assignment.getTotalQuestions() == answers.size();
    }

    public void addAnswer(Answer answer) throws Error {
        if (!checkComplete()) {
            answers.add(answer);
        } else {
            throw new Error("Too many answers submitted!");
        }
    }

    public Submission(Assignment assignment, List<Answer> answers) {
        this.assignment = assignment;
        this.answers = answers;
    }

    public Submission(Assignment assignment) {
        this.assignment = assignment;
        this.answers = new ArrayList<>();
    }
}
