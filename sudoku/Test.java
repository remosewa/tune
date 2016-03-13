import entities.Board;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Remosewa on 3/12/2016.
 */
public class Test {

    public static void main(String[] args) throws IOException {
        //Test... read from file instead of stin. 10th line is assertion line.
       FileReader fileReader = new FileReader(args[0]);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        Board board = new Board(bufferedReader);
        board.solve();
        System.out.println(board);
        board.assertBoard();
    }
}
