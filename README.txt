1. How does your algorithm work?
There are many comments in the code itself, but essentially each 'cell' in the sodoku board has 9 possible values 1-9. It also has 3 'relationships' which determine its
value. The row, column, and 3x3 box it is in. Each relationship also has 9 possible values for all it's cells 1-9. Whenever a cell has a value set, either from when the
board is first read in, or when we deterministically decide a cell must be a value, all other cells in the updated cell's relationships are updated. Each cell and 
relationship removes a possible value when a cell in the same relationship is set. When a cell can only be one possible value (which it can determine itself, or the 
relationship can determine that), then it's value is updated and the cycle continues.The program ends when the queue of cells for processing is empty. 

2. Give and explain the big-O notation of the best, worst, and expected run-time of your program.
In reality, this problem is constant time, since the number of visits is finite.
Every cell is processed once in the beginning. Each blank cell is also processed whenever another cell in its relationships is updated. There can be a total of 3*9=27 cells
in a given cell's relationships. So a given cell can be processed theoretically 27 times. Since there are 81 cells, a maximum runtime would be 81*27=2187 comparisons.
For average, if there are n cells, then there will be an average of n/3 blank cells in each cell's relationship. As each cell is updated, there are less cells to compare.
So it will be n*(n/3) + (n-1)*((n-1)/3), (n-2)*((n-2)/3)... I believe this still reduces to O(n^2).

3. Why did you design your program the way you did?
Each board, relationship, and cell can take care of it's own logic for determining its values. This actually makes the solve() function in Board.java very simple. The queue
is a nice way to prevent concurrent modifications and deep stacks.

4. What are some other decisions you could have made. How might they have been better? How might they have been worse?
I'm sure there are other techniques to solving sudoku puzzles besides what I'm doing here. For example, one way is to guess what a particular value might be and then 
continue on solving the board until you either reach a solution, or come up with an invalid board due to an incorrect guess. This would require a lot of memory to save
the board prior to making a guess so that you can backtrack in the event your guess is incorrect. This also would require me to do extra validation on every update to make
sure I'm not violating any of the relationship conditions. I just decided not to do this, because I felt it was out of scope for this program.