package model;

import java.util.List;

public class Assignment {
    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    public Assignment(List<Question> questions) {
        this.questions = questions;
    }
}
