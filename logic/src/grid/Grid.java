package grid;

import entity.EntityInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid {
    private final Integer rows;
    private final Integer cols;
    private final EntityInstance[][] grid;
    private Integer freeSpaces;

    public Grid(Integer rows, Integer cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new EntityInstance[rows][cols];
        initializeGrid();
        freeSpaces = rows * cols;
    }

    public void initializeGrid() {
        for(int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                grid[row][col] = null;
            }
        }
    }

    public Integer getRows() {
        return rows;
    }

    public Integer getCols() {
        return cols;
    }

    public EntityInstance[][] getGrid() {
        return grid;
    }

    public void updateNewInstanceInRandomLocation(EntityInstance entityInstance) {
        Location randomLocation;
        boolean useList = ((double) freeSpaces / (rows * cols)) > 0.5;
        if (useList) {
            List<Location> freeSpaces = getListOfFreeSpaces();
            Random random = new Random();
            int randomIndex = random.nextInt(freeSpaces.size());
            randomLocation = freeSpaces.get(randomIndex);
        } else{
            Random random = new Random();
            int randomRow, randomCol;
            do {
                randomRow = random.nextInt(rows);
                randomCol = random.nextInt(cols);
            } while (grid[randomRow][randomCol] != null);
            randomLocation = new Location(randomRow, randomCol);
        }

        entityInstance.setLocation(randomLocation);
        freeSpaces--;
        grid[randomLocation.getRow()][randomLocation.getCol()] = entityInstance;
    }

    private List<Location> getListOfFreeSpaces(){
        List<Location> freeSpaces = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == null) {
                    freeSpaces.add(new Location(row, col));
                }
            }
        }

        return freeSpaces;
    }

    public void removeInstance(Location location) {
        grid[location.getRow()][location.getCol()] = null;
        freeSpaces++;
    }

    public void moveInstance(EntityInstance entityInstance){
        Integer entityRow = entityInstance.getLocation().getRow();
        Integer entityCol = entityInstance.getLocation().getCol();
        if(grid[(entityRow - 1 + rows) % rows][entityCol % cols] == null){
            entityInstance.updateLocation((entityRow - 1 + rows) % rows, entityCol % cols);
            grid[entityRow][entityCol] = null;
            grid[(entityRow - 1 + rows) % rows][entityCol % cols] = entityInstance;
        } else if (grid[(entityRow) % rows][(entityCol - 1 + cols) % cols] == null) {
            entityInstance.updateLocation(entityRow % rows, (entityCol - 1 + cols) % cols);
            grid[entityRow][entityCol] = null;
            grid[entityRow % rows][(entityCol - 1 + cols) % cols] = entityInstance;
        } else if (grid[(entityRow) % rows][(entityCol + 1) % cols] == null) {
            entityInstance.updateLocation(entityRow % rows, (entityCol + 1) % cols);
            grid[entityRow][entityCol] = null;
            grid[entityRow % rows][(entityCol + 1) % cols] = entityInstance;
        } else if (grid[(entityRow + 1) % rows][entityCol % cols] == null){
            entityInstance.updateLocation((entityRow + 1) % rows, entityCol % cols);
            grid[entityRow][entityCol] = null;
            grid[(entityRow + 1) % rows][entityCol % cols] = entityInstance;
        }
    }
}
