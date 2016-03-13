package entities;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Relationship can either be a row, column, or a box which contains 9 cells. Each relationship maintains it's
 * own list of possible values that are remaining for it. Cells in the relationship are only ones which don't have a
 * set value, so as more cells are discovered, the relationship gets smaller and the possible values gets smaller.
 * Possible values should be equal to the number of cells.
 */
public class Relationship {
    private Set<Cell> cells = new HashSet<>();
    private Set<Integer> possibleValues = new HashSet<>();
    private Board board;
    private String name; //debugging

    Relationship(Board board, String name) {
        this.board = board;
        this.name = name;
        initPossibilities();
    }

    private void initPossibilities() {
        for (int i = 1; i < 10; i += 1) {
            possibleValues.add(i);
        }
    }

    /**
     * Add a cell to this relationship.
     *
     * @param cell
     */
    void addCell(Cell cell) {
        cells.add(cell);
    }

    /**
     * Cell value has been discovered.
     * Remove it from the relationship.
     * Mark the other cells in the relationship to remove the found value from their potential values.
     * Remove the found cell's value from the relationship potential values.
     * Then find all cells which only have one possible value based on this relationship's remaining possibilities.
     *
     * @param cell to remove from relationship.
     */
    void processAndRemove(Cell cell) {
        if (!cells.contains(cell)) { //cell already removed from relationship
            return;
        }
        cells.remove(cell);
        possibleValues.remove(cell.getValue());
        HashSet<Cell> cellsToProcess = cells.stream().filter(c -> c.removePossibleValue(cell.getValue())).collect(Collectors.toCollection(HashSet::new));
        cellsToProcess.addAll(findSinglePossibilitiesInRelationship());
        board.addToQueue(cellsToProcess);
    }

    /**
     * Iterate over the remaining possible values for this relationship, and see if there is only a single cell in this
     * relationship which can be that value. If so, update that cell's value and return it.
     * @return the set of cells which have been updated.
     */
    private Set<Cell> findSinglePossibilitiesInRelationship() {
        HashSet<Cell> updates = new HashSet<>();

        for (Integer possibility : possibleValues) {
            Cell potentialUpdate = null;
            int count = 0;
            for (Cell potentialCell : cells) {
                if (potentialCell.canBe(possibility)) {
                    count += 1;
                    potentialUpdate = potentialCell;
                    if (count > 1) {
                        potentialUpdate = null;
                        break;
                    }
                }
            }
            if (potentialUpdate != null) {
                potentialUpdate.updateValue(possibility);
                updates.add(potentialUpdate);
            }
        }
        return updates;
    }
}
