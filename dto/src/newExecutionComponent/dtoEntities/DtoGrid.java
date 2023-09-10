package newExecutionComponent.dtoEntities;

public class DtoGrid {
    private final Integer rows;
    private final Integer cols;

    public DtoGrid(Integer rows, Integer cols) {
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
