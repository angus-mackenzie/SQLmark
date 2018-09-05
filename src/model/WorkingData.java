package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the data in the database
 * @author Matthew Poulter
 * @version 01/04/2018
 */
public class WorkingData {
    /**
     * Gets a list of all the tables
     * @return a list of tables
     * @throws Error if DB cannot fetch table, or ResultSet is incorrect
     */
    public static List<String> getTables() throws Error {
        Database db = new Database("admin_data");
        List<String> tables = new ArrayList<String>();

        db.prepareSelect("table_list");
        db.execute();
        ResultSet rs = db.getResultSet();
        try {
            while (rs != null && rs.next()) {
                tables.add(rs.getString("table_name"));
            }
        }catch(Exception e){
            throw new Error("Can't go through table ResultSet",e.getCause());
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
     * @throws Error if it cannot fetch students or DB cannot execute
     */
    public static List<Student> getStudents() throws Error {
        Database db = new Database("admin_data");
        List<Student> students = new ArrayList<>();

        db.prepareSelect("students");
        db.execute();
        ResultSet rs = db.getResultSet();
        try{
            while (rs.next()) {
                students.add(new Student(rs.getString("student_num")));
            }
        }catch(Exception e){
            throw new Error("Cannot add students",e.getCause());
        }


        db.closeRS();
        db.close();
        return students;
    }

    /**
     * Gets all the questions in the question database
     * @return a list of questions
     * @throws Error if can't get feedback type or query doesn't work
     */
    public static List<Question> getQuestions() throws Error {
        Database db = new Database("admin_data");
        List<Question> questions = new ArrayList<>();

        db.prepareSelect("questions");
        db.execute();
        ResultSet rs = db.getResultSet();
        try {
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
        }catch(Exception e){
            throw new Error("Can't create assignment",e);
        }
        db.closeRS();
        db.close();
        return questions;
    }
}
