import java.io.File;

public class Dataset {
    private Object dataset;
    private String compileMessage;
    private CompileStatus compileStatus;

    public enum CompileStatus {
        SUCCESS, FAILURE
    }

    public boolean compareTo(Dataset dataset) {
        // TODO: Compare two datasets
        throw new UnsupportedOperationException();
    }

    public File exportData() {
        // TODO: Export data to file
        throw new UnsupportedOperationException();
    }

    public String getCompileMessage() {
        return compileMessage;
    }

    public CompileStatus getCompileStatus() {
        return compileStatus;
    }

    @Override
    public String toString() {
        // TODO: To string of the dataset
        throw new UnsupportedOperationException();
    }

    // Is this needed?
    public Dataset(Object dataset) {
        this.compileMessage = null;
        this.dataset = dataset;
    }

    public Dataset(String sql) {
        // TODO: Create dataset by running against database
        // this.compileMessage =
        // this.compileStatus =
        throw new UnsupportedOperationException();
    }
}
