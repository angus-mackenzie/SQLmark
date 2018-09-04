package model;

import java.util.List;

/**
 * Creates an assignment of questions
 * @author Matthew Poulter
 * @version 13/08/2018
 */
public class Assignment {
    private List<Question> questions;

    /**
     * Gets the questions from working data
     * @throws Error it can't fetch the questions
     */
    public Assignment() throws Error {
        this.questions = WorkingData.getQuestions();
    }

    /**
     * Gets the questions
     * @return a list of questions
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Gets the number of questions
     * @return the number of questions
     */
    public int getTotalQuestions() {
        return questions.size();
    }

    /**
     * Gets the current question
     * @param currentQuestion to fetch
     * @return the question at that index
     */
    public Question getQuestion(int currentQuestion) {
        return questions.get(currentQuestion);
    }

    /**
     * Gets a string representation of the questions
     * @return String representation of the question
     */
    public String toString() {
        String output = "";
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            output += question.getQuestionNum() + ": " + question.getQuestionText() + "\n";
        }
        return output;
    }

    /**
     * Creates random data for the user
     * TODO
     * @return random data
     * @throws Error cannot get data from DB
     */
    public String getRandomData() throws Error {
        Database db = new Database();
        StringBuilder sqlString = new StringBuilder();

        for (String table : WorkingData.getTables()) {
            db.prepareSelect(table, null, 30);
            db.execute();
            sqlString.append(db.exportToSQL());
            db.closeRS();
        }
        db.close();
        return sqlString.toString();
    }


}
