import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

// Grid via char matrix of enums also tracking sources on the grid
public class Grid {
   // Possible cell elements
   private static final Element SOURCE = Element.SOURCE;
   private static final Element NEIGHBOR = Element.NEIGHBOR;
   private static final Element VACANCY = Element.VACANCY;

   // Representation of the grid
   private char[][] cells;

   // Keep track of the sources to plot for neighbor calculation
   private ArrayList<SimpleEntry<Integer, Integer>> sources;

   public Grid(int rows, int columns) {
      // Fail on any input under 1 or if memory overflow is detected
      Runtime runtime = Runtime.getRuntime();

      if(rows <= 0 || columns <= 0 || 
         rows * columns * Byte.SIZE > runtime.freeMemory() ||
         rows * columns * Byte.SIZE < 0) {
         throw new IllegalArgumentException("Row and' or column size " + 
            "rejected!");
      }

      this.cells = new char[rows][columns];

      // Default every cell to vacant space
      for(char[] row : this.cells) {
         Arrays.fill(row, VACANCY.get());
      }

      this.sources = new ArrayList<SimpleEntry<Integer, Integer>>();
   }

   /**
    * @param row  
    * @param column
    * @return whether a source is added
    */
   public boolean addSource(int row, int column) {
      SimpleEntry<Integer, Integer> source = new SimpleEntry<Integer, 
         Integer>(row, column);
      
      // Fail if out of bounds of grid already contains source
      if((row < 0 || column < 0) || 
         (row > this.cells.length - 1 || column > this.cells[row].length - 1) ||
         (this.sources.contains(source))) {
         return false;
      }
      // Otherwise, add source and plot it on the grid
      this.sources.add(source);
      this.cells[source.getKey()][source.getValue()] = SOURCE.get();

      return true;
   }

   //---------------------------------------------------------------------------
   //-----------THIS IS THE FUNCTION THAT WAS ASKED TO BE IMPLEMENTED-----------
   //---------------------------------------------------------------------------
   //                                    ||
   //                                    ||
   //                                    ||
   //                                  ------   
   //                                  \    /   
   //                                   \  /  
   //                                    \/
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
   //                                    /\
   //                                   /  \
   //                                  /    \
   //                                  ------ 
   //                                    ||
   //                                    ||
   //                                    ||
   //---------------------------------------------------------------------------
   //-----------THIS IS THE FUNCTION THAT WAS ASKED TO BE IMPLEMENTED-----------
   //---------------------------------------------------------------------------

   //--------------Getters currently used only for unit testing-----------------
   public char[][] getCells() {
      return this.cells;
   }

   public ArrayList<SimpleEntry<Integer, Integer>> getSources() {
      return this.sources;
   }
   //--------------Getters currently used only for unit testing-----------------

   // Board needs to be cleaned after neighbor calculation display
   public void clean() {
      for(char[] row : this.cells) {
         for(int i = 0; i < row.length; i++) {
            if(row[i] == NEIGHBOR.get()) {
               row[i] = VACANCY.get();
            }
         }
      }
   }

   // Show each cell as '[ ]' with 3 possible enums
   public String toString() {
      StringBuilder result = new StringBuilder();

      for(int row = 0; row < this.cells.length; row++) {
         for(int column = 0; column < this.cells[row].length; column++) {
            result.append("[").append(this.cells[row][column]).append("]");
         }

         result.append('\n');
      }

      return result.toString();
   } 
}
