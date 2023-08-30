package grid;

public class Location {
    private Integer row;
    private Integer col;

    public Location(Integer row, Integer col) {
        this.row = row;
        this.col = col;
    }

    public Integer getRow() {
        return row;
    }

    public Integer getCol() {
        return col;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public void setCol(Integer col) {
        this.col = col;
    }
}
