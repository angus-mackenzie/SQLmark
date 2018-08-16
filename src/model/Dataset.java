import java.io.File;

public class Dataset {
    private Object dataset;

    public boolean compareTo(Dataset dataset) {
        throw (new UnsupportedOperationException());
    }

    public File exportData() {
        throw (new UnsupportedOperationException());
    }

    public Dataset(Object dataset) {
        this.dataset = dataset;
    }

    public Dataset(String sql) {
        throw (new UnsupportedOperationException());
    }
}
