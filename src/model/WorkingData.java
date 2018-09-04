package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the data in the database
 */
public class WorkingData {
    /**
     * Gets a list of all the tables in the database
     * @return a list of tables
     * @throws SQLException
     */
    public static List<String> getTables() throws SQLException {
        Database db = new Database();
        List<String> tables = new ArrayList<String>();

        db.prepareSelect("table_list");
        db.execute();
        ResultSet rs = db.getResultSet();
        while (rs != null && rs.next()) {
            tables.add(rs.getString("table_name"));
        }
        if(rs!=null){
            db.closeRS();
        }

        db.close();
        return tables;
    }

    /**
     * Gets the students from the students table
     * @return a list of students
     * @throws SQLException
     */
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

    /**
     * Gets all the questions in the question database
     * @return a list of questions
     * @throws SQLException
     */
    public static List<Question> getQuestions() throws SQLException {
        Database db = new Database("admin_data");
        List<Question> questions = new ArrayList<>();

        db.prepareSelect("questions");
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
                    rs.getInt("question_num"),
                    rs.getString("question_text"),
                    rs.getString("answer"),
                    feedbackType);

            questions.add(question);
        }

        db.closeRS();
        db.close();
        return questions;
    }
}
