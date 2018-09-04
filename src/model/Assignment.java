package model;

import java.sql.SQLException;
import java.util.List;

/**
 * Creates an assignment of questions
 * @Author Matthew Poulter
 * @Version 13/08/2018
 */
public class Assignment {
    private List<Question> questions;

    /**
     * Gets the questions from working data
     * @throws SQLException
     */
    public Assignment() throws SQLException {
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
     *
     * @return the number of questions
     */
    public int getTotalQuestions() {
        return questions.size();
    }

    /**
     * Gets the current question
     *
     * @param currentQuestion
     * @return the question at that index
     */
    public Question getQuestion(int currentQuestion) {
        return questions.get(currentQuestion);
    }

    /**
     * Gets a string of the questions
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
     *
     * @return
     * @throws SQLException
     */
    public String getRandomData() throws SQLException {
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
