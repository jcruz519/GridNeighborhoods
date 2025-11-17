import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

// Interactive console application to run grid calculations
public class App
{
   public static void main(String[] args) {
      Grid grid = null;
      Scanner scanner = new Scanner(System.in);
      
      // Control flow to remove neighbors after calculation has displayed
      // for future calculations and requiring user to create a grid before
      // doing anything else
      boolean isClean = true;
      boolean isFirstGrid = true;

      try {
         // Graceful shutdown when SIGINT is received by standard.in
         Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n--- Application shutting down. " + 
               "Cleaning resources. ---");
            scanner.close();
         }));

         // Main loop allowing user to create a grid, add sources, calculate
         // neighbors, and handling any wrong input
         while(true) {
            // User input instruction
            String command;

            // User must create a grid before execution flow moves further to
            // other functionality
            if(isFirstGrid) {
               command = "g";
            } else {
               System.out.print("You can add sources \'s\', calculate \'c\' " + 
                  "neighbors, or make a new grid \'g\': ");

               command = scanner.next();
            }

            // Application functionality
            switch(command) {
               // Add source
               case "s":
                  System.out.println("Enter row and column positions");
                  System.out.print("row: ");

                  int sourceRow = validNumericInput(scanner, 0);
                  System.out.print("column: ");

                  int sourceColumn = validNumericInput(scanner, 0);
               
                  if(!grid.addSource(sourceRow, sourceColumn)) {
                     System.out.println("Out of bounds or source already " +
                     "exists!");
                  }
               
               break;

               // Calculate neighbors: GOAL FOR THIS ASSIGNMENT
               case "c":
                  System.out.print("Enter size: ");
                  int size = validNumericInput(scanner, 0);
                  int result = grid.calculateNeighbors(size);
               
                  if(result == -1) {
                     System.out.println("Invalid size detected! Should be " + 
                        "between 0 - max int!");
                  } else {
                     // Let driver know board needs to be cleaned after 
                     // calculation
                     isClean = false;

                     System.out.printf("The number of cells within the area " +
                        "is %d.\n", result);
                  }
               break;

               // Create a new X x Y grid clearing the previous grid
               case "g":
                  System.out.println("Enter row and column amount to make a " +
                     "grid");
                  System.out.print("row: ");
      
                  int gridRow = validNumericInput(scanner, 1);
                  System.out.print("column: ");
               
                  int gridColumn = validNumericInput(scanner, 1);
                  
                  // Check whether user-input row and column will cause a memory
                  // error
                  Runtime runtime = Runtime.getRuntime();

                  if(gridRow * gridColumn * Byte.SIZE > runtime.freeMemory() ||
                     gridRow * gridColumn * Byte.SIZE < 0) {
                     System.out.println("Row and column rejected! Not enough " +
                        "memory!");

                     // Tell user to create a grid if one has yet to exist 
                     // (only happens when first grid isn't created)
                     if(isFirstGrid) {
                        System.out.println("Grid not created! Try again!");
                     }
                  } else {
                     grid = new Grid(gridRow, gridColumn);
                     isFirstGrid = false;
                  }
               break;
               
               // Any other user input
               default:
                  System.out.println("Invalid input detected! ");
            }

            // At this point, a grid always exists, simply output it to track it
            if(!isFirstGrid) {   
               System.out.println("Your grid:");
               System.out.print(grid.toString());
            }

            // Clean board after calculate neighbor operation
            if(!isClean) {
               grid.clean();
               isClean = true;
            }
         }
      // Take care of user input CNTL-D
      } catch(NoSuchElementException e) {
         scanner.close();
      }
   }

   /**
    * @param scanner standard input from user
    * @param min either 0 or 1 based on context
    * @return result number if valid otherwise, loop
    */
   private static int validNumericInput(Scanner scanner, int min) {
      int result;

      // Input out of range or bad type will continue loop until corrected
      while(true) {
         try {
            result = scanner.nextInt();
            
            if(result >= min && result <= Integer.MAX_VALUE) {
               break;
            } else {
               System.out.printf("Enter a number between %d - max int: ", min);
            }
         } catch (InputMismatchException e) {
            System.out.print("Non-numeric input detected! Try again: ");
            scanner.next();
         }
      }

      return result;
   }
}
