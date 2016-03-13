package entities;

import java.util.HashSet;
import java.util.Set;

/**
 * A single cell in a sudoku board which can have the value 0-9 or blank. Each cell keeps track of what are the
 * possible values it could be. I.e. If there is a 4 already in one of the cell's relationships then this cell cannot be
 * a 4.
 */
public class Cell {
    private Set<Relationship> relationships = new HashSet<>();
    private Set<Integer> possibleValues= new HashSet<>();
    private Integer value = null;
    int row; //debugging
    int col; //debugging

    /**
     *
     * @param value if not null, possibleValues will only contain this value. Cell value will be set to this only after
     *              processing.
     * @param row 0-8 row number (for debugging)
     * @param col 0-8 col number (for debugging)
     * @param relationships list of relationships this cell should be in. Will add itself to the relationship.
     */
    Cell(Integer value, int row, int col, Relationship... relationships) {
        this.row = row;
        this.col = col;
        for(Relationship relationship : relationships) {
            this.relationships.add(relationship);
            relationship.addCell(this);
        }
        initPossibilities(value);
    }

    Cell(int row, int col, Relationship... relationships) {
        this(null,row, col, relationships);
    }

    private void initPossibilities(Integer value) {
        if(value == null) {
            for (int i = 1; i < 10; i += 1) {
                possibleValues.add(i);
            }
        } else {
            possibleValues.add(value);
        }
    }

    /**
     * Remove value from possible values
     * @param value value this cell cannot be.
     * @return
     */
    boolean removePossibleValue(int value) {
        possibleValues.remove(value);
        return possibleValues.size() == 1;
    }

    /**
     * If the possibleValues is only 1, sets the value and then removes itself from each relationship.
     */
    void processCell() {
        if(possibleValues.size() == 1) {
            this.value = possibleValues.iterator().next();
            relationships.stream().forEach(r -> r.processAndRemove(this));
        }
    }

    public Integer getValue() {
        return this.value;
    }

    /**
     * Set this cell's value.
     * Does not set the cell's value. It just sets the remaining possibilities to 1 so it will be processed later.
     * @param possibility
     */
    public void updateValue(int possibility) {
        possibleValues.removeIf(v -> v != possibility);
    }

    /**
     * Returns whether this cell could potentially be the value provided.
     * @param possibility the possible value to check.
     * @return
     */
    public boolean canBe(Integer possibility) {
        return possibleValues.contains(possibility);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
