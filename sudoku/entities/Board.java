package entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * Reads board, which is a 9x9 sudoku board from a file with values separated by commas. If there is a 10th line, it
 * assumes it is an assertion row of comma separated ints which specify the solution for this board.
 * Board contains a queue of cells which must be processed due to the cell value being set, or a fellow cell in their
 * relationship being set.
 */
public class Board {
    private HashSet<Cell> cellsInQueue = new HashSet<>();
    private Queue<Cell> cellQueue = new LinkedList<>();
    private List<Cell> allCells = new ArrayList<>();
    private int[] assertionValues = null;

    /**
     * Create board from reader. All cells are initially added to the queue to be processed.
     *
     * @param bufferedReader the reader to read lines from
     * @throws IOException
     */
    public Board(BufferedReader bufferedReader) throws IOException {
        String line = null;
        Relationship[] rows = new Relationship[9];
        Relationship[] cols = new Relationship[9];
        Relationship[] boxes = new Relationship[9];
        for (int i = 0; i < 9; i += 1) {
            rows[i] = new Relationship(this, "row" + i);
            cols[i] = new Relationship(this, "col" + i);
            boxes[i] = new Relationship(this, "box" + i);
        }
        int box = 0;
        int row = 0;
        while ((line = bufferedReader.readLine()) != null) {
            if (row == 9 && !line.trim().isEmpty()) {
                //assertion row
                assertionValues = new int[81];
                String[] assertionString = line.split(",");
                for (int i = 0; i < assertionString.length; i += 1) {
                    assertionValues[i] = Integer.valueOf(assertionString[i]);
                }
                break;
            }
            box = (row / 3) * 3;
            String[] cellStrings = line.split(",");
            int col = 0;
            for (String cellString : cellStrings) {
                Cell cell;
                if (cellString.equals("-")) {
                    cell = new Cell(row, col, rows[row], cols[col], boxes[box]);
                } else {
                    cell = new Cell(Integer.valueOf(cellString), row, col, rows[row], cols[col], boxes[box]);
                }
                allCells.add(cell);
                addToQueue(cell);
                if ((col + 1) % 3 == 0) {
                    box += 1;
                }
                col += 1;
            }
            row += 1;
        }
    }

    /**
     * Verify board solution is equal to the assertion row.
     */
    public void assertBoard() {
        if (assertionValues != null) {
            int i = 0;
            for (Cell cell : allCells) {
                if (cell.getValue() != assertionValues[i]) {
                    throw new AssertionError("Expected " + assertionValues[i] + " but got " + cell.getValue() + " at " + cell.row + "," + cell.col);
                }
                i += 1;
            }
        } else {
            System.err.println("No assertion row to assert board against.");
        }
    }

    /**
     * convenience, adds all cells in the set to the queue.
     *
     * @param cellsToProcess
     */
    void addToQueue(HashSet<Cell> cellsToProcess) {
        cellsToProcess.stream().forEach(c -> addToQueue(c));
    }

    /**
     * Adds cell to the queue. If the cell is already in the queue, then it is ignored.
     */
    void addToQueue(Cell cellToProcess) {
        if (!cellsInQueue.contains(cellToProcess)) {
            cellsInQueue.add(cellToProcess);
            cellQueue.add(cellToProcess);
        }
    }

    /**
     * Process cells until there is nothing left to process. Does not make guesses, and only sets a cell's value
     * if it has one possible value.
     */
    public void solve() {
        Cell processingCell;
        while (cellQueue.size() > 0) {
            processingCell = cellQueue.remove();
            cellsInQueue.remove(processingCell);
            processingCell.processCell();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Cell cell : allCells) {
            if (i % 9 == 0) {
                sb.append("\n");
            } else {
                sb.append(",");
            }
            if (cell.getValue() == null)
                sb.append("-");
            else
                sb.append(cell.getValue());
            i += 1;
        }
        return sb.toString();
    }
}
