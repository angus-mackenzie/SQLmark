package model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dataset {
    private List<List<Object>> dataset;
    private String compileMessage;
    private Database.CompileStatus compileStatus;


    public boolean equals(Dataset dataset) {
        return Arrays.deepEquals(dataset.convertList(), convertList());
    }

    public String getCompileMessage() {
        return compileMessage;
    }

    public Database.CompileStatus getCompileStatus() {
        return compileStatus;
    }

    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder();
        if (dataset != null) {
            for (List<Object> row : dataset) {
                for (Object cell : row) {
                    returnString.append(String.format("%-15s|", cell.toString()));
                }
                returnString.setLength(Math.max(returnString.length() - 1, 0));
                returnString.append("/n");
            }
        }
        return returnString.toString();
    }

    private Object[][] convertList() {
        Object[][] array = new Object[dataset.size()][];
        for (int i = 0; i < dataset.size(); i++) {
            List<Object> row = dataset.get(i);
            array[i] = row.toArray(new Object[0]);
        }
        return array;
    }

    private List<List<Object>> convertResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columns = metaData.getColumnCount();
        List<List<Object>> dataset = new ArrayList<>();

        List<Object> row = new ArrayList<>(columns);
        for (int i = 0; i < columns; i++) {
            row.add(metaData.getColumnName(i));
        }
        dataset.add(row);

        while (rs.next()) {
            row = new ArrayList<>(columns);
            for (int i = 0; i < columns; i++) {
                row.add(rs.getObject(i));
            }
            dataset.add(row);
        }

        return dataset;
    }

    // Is this needed?
    public Dataset(List<List<Object>> dataset) {
        this.compileMessage = null;
        this.compileStatus = null;
        this.dataset = dataset;
    }

    public Dataset(String sql) {
        Database db = new Database();
        db.execute(sql);

        this.compileMessage = db.getLastMessage();
        this.compileStatus = db.getLastStatus();
        this.dataset = null;
        if (this.compileStatus == Database.CompileStatus.SUCCESS) {
            try {
                this.dataset = convertResultSet(db.getResultSet());
            } catch (SQLException e) {
                this.compileStatus = Database.CompileStatus.FAILURE;
            }
        }
    }
}
