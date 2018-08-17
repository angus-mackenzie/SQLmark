package model;

import java.util.List;

public class Student {
    private String studentNum;
    private List<Submission> submissions;

    public String getStudentNum() {
        return studentNum;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public int getHighestMark() {
        int mark = 0;
        for (Submission submission : submissions) {
            try {
                if (mark < submission.getTotalMark()) {
                    mark = submission.getTotalMark();
                }
            } catch (Error error) {
                // Not included as incomplete
            }
        }
        return mark;
    }

    public Student(String studentNum) {
        this.studentNum = studentNum;

        //        Database db = new Database();
//        Map<String,Object> where = new HashMap<>();
//
//        where.put("studentNum",studentNum);
//        db.prepareSelect("studentTable",where);
//        db.execute();
//        ResultSet rs = db.getResultSet();
//        try{
//            while (rs.next()) {
//                ResultSetMetaData rsMetaData = rs.getMetaData();
//                int numberOfColumns = rsMetaData.getColumnCount();
//                for (int i = 1; i < numberOfColumns + 1; i++) {
//                    System.out.println(rs.getString(i)+"\n");
//                    System.out.print(rs.getString(i).equals(studentNum));
//                }
//            }
//        }catch(SQLException e){
//            e.printStackTrace();
//        }
//        System.out.println("It worked??");
    }

}
