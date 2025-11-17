# GridNeighborhoods
Interactive console application that implements the Manhattan Distance problem<br><br>

This repo assumes you have Java installed and are on Linux. Project is built using make:<br>
```bash
# build the application
make
make app

# build unit test
make test

# clean out class files
make clean
make cleanTest
```

```./app``` runs the interactive console app to calculate Manhattan Distance. It starts off with<br>
grid creation via input x and y sizes. You then have the option to add sources, calculate<br>
the Manhattan Distance, or create a new grid.

```bash
./app
Enter row and column amount to make a grid
row: 5
column: 5
Your grid:
[ ][ ][ ][ ][ ]
[ ][ ][ ][ ][ ]
[ ][ ][ ][ ][ ]
[ ][ ][ ][ ][ ]
[ ][ ][ ][ ][ ]
You can add sources 's', calculate 'c' neighbors, or make a new grid 'g': 
```
```./test``` simply runs the unit tests.
```bash
./test
--- Starting Grid Manual Tests ---
✅ PASS: Constructor initialization test
✅ PASS: Constructor failure test for input under 1 (Expected exception IllegalArgumentException thrown)
✅ PASS: Constructor failure test for input that will cause memory error (Expected exception IllegalArgumentException thrown)
✅ PASS: Add source test return value
✅ PASS: Add source test execution (Expected: 1, Actual: 1)
✅ PASS: Duplicate source shouldn't be added
✅ PASS: Duplicate source not added (Expected: 1, Actual: 1)
✅ PASS: Out of bounds source shouldn't be added
✅ PASS: Out of bounds source not added (Expected: 1, Actual: 1)
✅ PASS: Calculate neighbors test (Expected: 5, Actual: 5)
✅ PASS: Calculate neighbors test (Expected: 19, Actual: 19)
✅ PASS: Calculate neighbors test (Expected: 0, Actual: 0)
✅ PASS: Calculate neighbors should return -1 on input under 0 (Expected: -1, Actual: -1)
✅ PASS: Calculate neighbors should return -1 on input above int max (Expected: -1, Actual: -1)
--- Testing Complete ---
```

For the sake of this problem, here is the function in question:

```java
 /**
    * @param size number of cells in neighborhood  
    * @return number of neighbors or -1 in case of failure
    */
public int calculateNeighbors(int size) {
      // Sources count so we default to how many there are
      int result = this.sources.size();

      // Fail if size is massive or negative otherwise a positive count should 
      // always be returned
      if(size > Integer.MAX_VALUE || size < 0) {
         return -1;
      }

      for(SimpleEntry<Integer, Integer> source : this.sources) {
         // Note: java.util class pair used for cell position but function names
         // don't reflect actual purpose rather for example using getFirst/ 
         // Second
         int sourceRow = source.getKey();
         int sourceColumn = source.getValue();
         
         // Optimization variables to check only cells within diamond region
         int minRow = Math.max(0, sourceRow - size);
         int maxRow = Math.min(this.cells.length - 1, sourceRow + size);

         for(int row = minRow; row <= maxRow; row++) {
            int verticalCoverage = Math.abs(row - sourceRow);
            int remainingHorizontal = size - verticalCoverage;
            int minColumn = Math.max(0, sourceColumn - remainingHorizontal);
            int maxColumn = Math.min(this.cells[0].length - 1, 
               sourceColumn + remainingHorizontal);

            for(int column = minColumn; column <= maxColumn; column++) {
               // Valid cell reached if vacant otherwise, it's a source and 
               // shouldn't be double counted
               if(this.cells[row][column] == VACANCY.get()) {
                  this.cells[row][column] = NEIGHBOR.get();
                  result++;
               }
            }
         }           
      } 

     return result;
   }
```
