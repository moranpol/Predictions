package newExecution;

public class GridDto {
    private final Integer rows;
    private final Integer cols;

    public GridDto(Integer rows, Integer cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public Integer getRows() {
        return rows;
    }

    public Integer getCols() {
        return cols;
    }
}
