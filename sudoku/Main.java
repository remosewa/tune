import entities.Board;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Chase Wilson on 3/12/2016.
 */
public class Main {
    /**
     * Reads from stdin 9 times. Each time expecting another csv row defining a sudoku board. Then solves the sudoku
     * puzzle and prints the result.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        sb.append(scanner.next()).append("\n");
        sb.append(scanner.next()).append("\n");
        sb.append(scanner.next()).append("\n");
        sb.append(scanner.next()).append("\n");
        sb.append(scanner.next()).append("\n");
        sb.append(scanner.next()).append("\n");
        sb.append(scanner.next()).append("\n");
        sb.append(scanner.next()).append("\n");
        sb.append(scanner.next()).append("\n");
        CharArrayReader charArrayReader = new CharArrayReader(sb.toString().toCharArray());
        BufferedReader bufferedReader = new BufferedReader(charArrayReader);
        Board board = new Board(bufferedReader);
        board.solve();
        System.out.println(board);
    }
}
