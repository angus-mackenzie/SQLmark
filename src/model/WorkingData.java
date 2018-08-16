package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkingData {
    public static List<String> getTables() throws SQLException {
        Database db = new Database();
        List<String> tables = new ArrayList<>();

        db.prepareSelect("table_list");
        db.execute();
        ResultSet rs = db.getResultSet();

        while (rs.next()) {
            tables.add(rs.getString("table_name"));
        }

        db.closeRS();
        db.close();
        return tables;
    }

    public static List<Student> getStudents() throws SQLException {
        Database db = new Database();
        List<Student> students = new ArrayList<>();

        db.prepareSelect("students");
        db.execute();
        ResultSet rs = db.getResultSet();

        while (rs.next()) {
            students.add(new Student(rs.getString("student_num")));
        }

        db.closeRS();
        db.close();
        return students;
    }

    public static List<Question> getQuestions() throws SQLException {
        Database db = new Database();
        List<Question> questions = new ArrayList<>();

        db.prepareSelect("table_list");
        db.execute();
        ResultSet rs = db.getResultSet();

        while (rs.next()) {
            Question.FeedbackType feedbackType;
            switch (rs.getInt("feedback_type")) {
                case 1:
                    feedbackType = Question.FeedbackType.COMPILE;
                    break;
                case 2:
                    feedbackType = Question.FeedbackType.VERBOSE;
                    break;
                default:
                    feedbackType = Question.FeedbackType.NONE;
                    break;
            }
            Question question = new Question(
                    rs.getString("question_text"),
                    rs.getString("correct_answer"),
                    feedbackType);

            questions.add(question);
        }

        db.closeRS();
        db.close();
        return questions;
    }
}
