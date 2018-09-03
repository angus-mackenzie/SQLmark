package model;

import org.junit.Test;
public class TestDatabase {


    @Test
    public void testDB(){
        Database db = new Database();
        db.execute("SELECT *;");
        System.out.println(db.getLastStatus());
    }
}
