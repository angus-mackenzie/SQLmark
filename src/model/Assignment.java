package model;

import java.sql.SQLException;
import java.util.List;

public class Assignment {
    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }

    public Assignment() throws SQLException {
        this.questions = WorkingData.getQuestions();
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    public Question getQuestion(int currentQuestion) {
        return questions.get(currentQuestion);
    }

    public String getRandomData() throws Error, SQLException {
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
