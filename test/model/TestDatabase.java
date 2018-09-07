package model;

import org.junit.Test;

import java.sql.SQLException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class TestDatabase {


    @Test
    public void testCorrectDB() throws Exception{
        Database db = new Database("");
        assertTrue("The DB should be connected",db.isConnected());
    }

    @Test(expected = Error.class)
    public void testIncorrectDB() throws Exception{
        Database db = new Database("does not exist");
        assertFalse("Database should not be connected",db.isConnected());
    }


}
