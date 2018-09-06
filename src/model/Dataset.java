package model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A dataset that holds a result for a question/answer
 *
 * @author Matthew Poulter
 * @version 15/09/2018
 */
public class Dataset {
    private List<List<Object>> dataset;
    private List<Dataset> datasets;
    private Integer rowsUpdated;
    private String compileMessage;
    private Database.CompileStatus compileStatus;

    /**
     * Creates a dataSet with the given SQL statement
     *
     * @param sql to be executed
     * @throws Error if it cannot connect to DB
     */
    public Dataset(String sql) throws Error {
        Database db = new Database("");
        String newDB = db.duplicateDB();
        db.changeDB(newDB);

        boolean type = db.execute(sql);
        this.compileMessage = db.getLastMessage();
        this.compileStatus = db.getLastStatus();
        this.dataset = null;
        if (this.compileStatus == Database.CompileStatus.SUCCESS) {
            try {
                if (type) {
                    this.dataset = convertResultSet(db.getResultSet());
                    db.closeRS();
                } else {
                    this.rowsUpdated = db.getUpdateCount();
                    this.datasets = new ArrayList<>();

                    for (String table : db.getAllTables()) {
                        this.datasets.add(new Dataset("SELECT * FROM " + table + ";"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                this.compileStatus = Database.CompileStatus.FAILURE;
            }
        }

        db.deleteDB(newDB);
        db.close();
    }

    /**
     * Compares two datasets to one another
     *
     * @param dataset to be compared to
     * @return true or false
     */
    public boolean equals(Dataset dataset) {
        if (rowsUpdated == null && dataset.rowsUpdated == null && Arrays.deepEquals(dataset.convertList(), convertList())) {
            return true;
        } else if (rowsUpdated != null && dataset.rowsUpdated != null && rowsUpdated.equals(dataset.rowsUpdated) && datasets.size() == dataset.datasets.size()) {
            boolean check = true;

            for (int i = 0; i < datasets.size(); i++) {
                if (!datasets.get(i).equals(dataset.datasets.get(i))) {
                    check = false;
                    break;
                }
            }

            return check;
        }
        return false;
    }

    /**
     * Return the compile time message
     *
     * @return compile message
     */
    public String getCompileMessage() {
        return compileMessage;
    }

    /**
     * Returns the compile status
     *
     * @return compile status
     */
    public Database.CompileStatus getCompileStatus() {
        return compileStatus;
    }

    /**
     * Creates a list of the dataset
     *
     * @return an Object[][] array, null if there are no values in the dataset
     */
    private Object[][] convertList() {
        if (!(dataset == null)) {
            Object[][] array = new Object[dataset.size()][];
            for (int i = 0; i < dataset.size(); i++) {
                List<Object> row = dataset.get(i);
                array[i] = row.toArray(new Object[0]);
            }
            return array;
        }
        return null;
    }

    /**
     * Creates a string representation of the data
     *
     * @return output
     */
    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder();
        if (dataset != null) {
            for (List<Object> row : dataset) {
                for (Object cell : row) {
                    returnString.append(String.format("%-15s|", cell.toString()));
                }
                returnString.setLength(Math.max(returnString.length() - 1, 0));
                returnString.append("\n");
            }
        }
        return returnString.toString();
    }

    /**
     * Converts a result set to a list
     *
     * @param rs result set
     * @return a list of the result set
     * @throws SQLException
     */
    private List<List<Object>> convertResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columns = metaData.getColumnCount();
        List<List<Object>> dataset = new ArrayList<>();

        List<Object> row = new ArrayList<>(columns);
        for (int i = 1; i <= columns; i++) {
            row.add(metaData.getColumnName(i));
        }
        dataset.add(row);

        while (rs.next()) {
            row = new ArrayList<>(columns);
            for (int i = 1; i <= columns; i++) {
                row.add(rs.getObject(i));
            }
            dataset.add(row);
        }

        return dataset;
    }
}
